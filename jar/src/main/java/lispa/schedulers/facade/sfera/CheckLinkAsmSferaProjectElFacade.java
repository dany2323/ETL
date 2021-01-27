package lispa.schedulers.facade.sfera;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.sfera.DmalmAsm;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.dao.target.DmAlmSourceElProdEccezDAO;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmAsmProjectEl;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;
import org.h2.util.StringUtils;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class CheckLinkAsmSferaProjectElFacade {
	private static Logger logger = Logger
			.getLogger(CheckLinkAsmSferaProjectElFacade.class);
	private static QDmalmAsm dmalmAsm = QDmalmAsm.dmalmAsm;
	private static QDmalmProject project = QDmalmProject.dmalmProject;
	private static QDmalmAsmProjectEl asmProject = QDmalmAsmProjectEl.dmalmAsmProject;
	private static QDmalmElProdottiArchitetture prodotto = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmAlmSourceElProdEccez dmAlmSourceElProdEccez= QDmAlmSourceElProdEccez.dmAlmSourceElProd;

	public static void execute() throws Exception, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			if (ErrorManager.getInstance().hasError())
				return;
			
			logger.info("START CheckLinkAsmSferaProjectElFacade");
			
			//la tabella di relazione a tre viene ricaricata da zero ad ogni run del job ETL
			deleteAll();
			
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			
			List<Tuple> asmList = new ArrayList<Tuple>();
			List<Tuple> projList = new ArrayList<Tuple>();
			List<DmalmAsm> asmFound = new ArrayList<DmalmAsm>();
			List<DmalmProject> projFound = new ArrayList<DmalmProject>();

		
			// STEP 1 Project da legare alle Asm
			SQLQuery query = new SQLQuery(connection, dialect);
			
			projList = query.from(project)
					.orderBy(project.siglaProject.asc(), project.dtInizioValidita.asc())
					.list(project.all());
			
			logger.info("STEP 1 - Elementi project: " + projList.size());
			
			for (Tuple tp : projList) {
				String siglaProject = tp.get(project.siglaProject);
				Timestamp inizio = tp.get(project.dtInizioValidita);
				Timestamp fine = tp.get(project.dtFineValidita);

				if(DmAlmConstants.SVILUPPO.equals(tp.get(project.cTemplate)) && !StringUtils.isNullOrEmpty(siglaProject)) {
					//Project SVILUPPO con sigla not NULL
					
					String[] primoSplit = siglaProject.split("\\.\\.");
					for (String siglaPrj : primoSplit) {
						asmFound = DmAlmAsmDAO.getAsmToLinkAndSplit(siglaPrj, inizio, fine);

						for (DmalmAsm found : asmFound) {
							insertProj(found, tp, siglaPrj);
						}
					}
				} else {
					//Project altri Template o con sigla NULL da legare al tappo Asm Prodotto
					insertToProjTappo(tp);
				}
			}
			
			// STEP 2 Asm da legare ai project
			query = new SQLQuery(connection, dialect);
			
			asmList = query.from(dmalmAsm)
				.orderBy(dmalmAsm.applicazione.asc(), dmalmAsm.dataInizioValidita.asc())
				.list(dmalmAsm.all());
			
			logger.info("STEP 2 - Elementi asm: " + asmList.size());
			
			for (Tuple ta : asmList) {
				String applicazione = ta.get(dmalmAsm.applicazione);
				Timestamp inizio = ta.get(dmalmAsm.dataInizioValidita);
				Timestamp fine = ta.get(dmalmAsm.dataFineValidita);

				String[] split = applicazione.split("\\.\\.");
				for (String app : split) {
					projFound = ProjectSgrCmDAO.getProjectToLinkAndSplit(app, inizio, fine);
					
					for (DmalmProject pFound : projFound) {
						insertAsm(pFound, ta, app);
					}
				}
			}

			// STEP 3 Prodotti legati ad Asm/Project con data fine validità minore del 31/12/9999: da legare al tappo Asm e Project
			query = new SQLQuery(connection, dialect);
			
			List<Tuple> prod = new ArrayList<Tuple>();
			
			prod = query.from(prodotto)
					.where(prodotto.dataFineValidita.eq(DateUtils.getDtFineValidita9999()))
					.where(prodotto.prodottoPk.notIn(new SQLSubQuery().from(
							asmProject)
							.join(prodotto).on(prodotto.prodottoPk.eq(asmProject.dmalmProdottoPk))
							.join(dmalmAsm).on(dmalmAsm.dmalmAsmPk.eq(asmProject.dmalmAsmPk))
							.join(project).on(project.dmalmProjectPrimaryKey.eq(asmProject.dmalmProjectPk))
							.where(asmProject.dmalmProdottoPk.gt(0))
							.where(prodotto.dataFineValidita.eq(DateUtils.getDtFineValidita9999()))
							.where(dmalmAsm.dataFineValidita.eq(DateUtils.getDtFineValidita9999()))
							.where(project.dtFineValidita.eq(DateUtils.getDtFineValidita9999()))
							.list(asmProject.dmalmProdottoPk)))
					.list(prodotto.all());
			
			logger.info("STEP 3 - Prodotti legati ad Asm/Project con data fine validità minore del 31/12/9999: da legare al tappo Asm e Project: " + prod.size());
			if (prod.size() > 0) {
				for (Tuple t : prod) {
					insertSingleProd(t);
				}
			}
			
			// STEP 4 Prodotti non legati da legare al tappo Asm e project
			query = new SQLQuery(connection, dialect);
			
			prod = new ArrayList<Tuple>();
			
			prod = query.from(prodotto)
					.where(prodotto.prodottoPk.notIn(new SQLSubQuery().from(
							asmProject).list(asmProject.dmalmProdottoPk)))
					.list(prodotto.all());
			
			logger.info("STEP 4 - Prodotti non legati da legare al tappo: " + prod.size());
			if (prod.size() > 0) {
				for (Tuple t : prod) {
					insertSingleProd(t);
				}
			}
			
			logger.info("STOP CheckLinkAsmSferaProjectElFacade");
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	
	private static void insertSingleProd(Tuple t) throws DAOException {
		try {
			List<Tuple> check = new ArrayList<Tuple>();
			check = checkRelazione(t.get(prodotto.prodottoPk), 0, 0);
			if (check.size() == 0) {
				insert(t.get(prodotto.prodottoPk), 0, 0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	private static void insertToProjTappo(Tuple tp) throws DAOException {

		try {
			List<Tuple> check = new ArrayList<Tuple>();
			check = checkRelazione(0, 0, tp.get(project.dmalmProjectPrimaryKey));
			if (check.size() == 0) {
				insert(0, 0, tp.get(project.dmalmProjectPrimaryKey));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void insertAsm(DmalmProject pFound, Tuple ta, String sigla)
			throws DAOException {
		try {
			List<Tuple> r = new ArrayList<Tuple>();
			List<Tuple> dmAlmSourceElProdEccezzRow=DmAlmSourceElProdEccezDAO.getRow(sigla);
			if(!(dmAlmSourceElProdEccezzRow!=null && dmAlmSourceElProdEccezzRow.size()==1 && dmAlmSourceElProdEccezzRow.get(0).get(dmAlmSourceElProdEccez.tipoElProdEccezione).equals(1))){

				if (sigla.contains(".")) {
					sigla = sigla.substring(0, sigla.indexOf("."));
				}
			}
			//per cercare il Prodotto prendo il range temporale minore equivalente alla validità sia di Asm che di project
			Timestamp inizioVal = ta.get(dmalmAsm.dataInizioValidita);
			Timestamp fineVal = ta.get(dmalmAsm.dataFineValidita);

			if(pFound.getDtInizioValidita().after(ta.get(dmalmAsm.dataInizioValidita))) {
				inizioVal = pFound.getDtInizioValidita();
			}
			Timestamp pFoundFineValidita = DateUtils.stringToTimestamp(pFound.getDtFineValidita().toString().substring(0, 19), "yyyy-MM-dd HH:mm:ss");
			if(pFoundFineValidita.before(ta.get(dmalmAsm.dataInizioValidita))) {
				fineVal = pFoundFineValidita;
			}
			
			r = ProdottiArchitettureDAO.getProductBySigla(sigla, inizioVal, fineVal);

			if (r.size() > 0) {
				// prodotto trovato
				for (Tuple t : r) {
					List<Tuple> check = new ArrayList<Tuple>();
					List<Tuple> old = new ArrayList<Tuple>();
					List<Tuple> oldAsm = new ArrayList<Tuple>();
					List<Tuple> oldProj = new ArrayList<Tuple>();
					check = checkRelazione(t.get(prodotto.prodottoPk),
							ta.get(dmalmAsm.dmalmAsmPk),
							pFound.getDmalmProjectPk());
					if (check.size() == 0) {
						// devo inserirlo
						old = new ArrayList<Tuple>();
						// se esiste già al tappo prodotto
						old = checkRelazione(0, ta.get(dmalmAsm.dmalmAsmPk),
								pFound.getDmalmProjectPk());
						if (old.size() == 0) {
							oldAsm = new ArrayList<Tuple>();
							// se esiste già al tappo asm
							oldAsm = checkRelazione(t.get(prodotto.prodottoPk),
									0, pFound.getDmalmProjectPk());
							if (oldAsm.size() == 0) {
								if (pFound.getDmalmProjectPk() != 0) {
									oldProj = new ArrayList<Tuple>();
									// se esiste già al tappo proj
									oldProj = checkRelazione(
											t.get(prodotto.prodottoPk),
											ta.get(dmalmAsm.dmalmAsmPk), 0);
									if (oldProj.size() == 0) {
										// sicuro che non esiste quindi
										// inserisco
										insert(t.get(prodotto.prodottoPk),
												ta.get(dmalmAsm.dmalmAsmPk),
												pFound.getDmalmProjectPk());
									} else {
										delete(t.get(prodotto.prodottoPk),
												ta.get(dmalmAsm.dmalmAsmPk), 0);
										insert(t.get(prodotto.prodottoPk),
												ta.get(dmalmAsm.dmalmAsmPk),
												pFound.getDmalmProjectPk());
									}
								}// insert
								else {
									oldProj = checkRelazione(
											t.get(prodotto.prodottoPk),
											ta.get(dmalmAsm.dmalmAsmPk), 0);
									if (oldProj.size() == 0) {
										// sicuro che non esiste quindi
										// inserisco
										insert(t.get(prodotto.prodottoPk),
												ta.get(dmalmAsm.dmalmAsmPk), 0);
									}
								}
							}// oldAsm
							else {
								delete(t.get(prodotto.prodottoPk), 0,
										pFound.getDmalmProjectPk());
								insert(t.get(prodotto.prodottoPk),
										ta.get(dmalmAsm.dmalmAsmPk),
										pFound.getDmalmProjectPk());
							}
						}// old
						else {
							delete(0, ta.get(dmalmAsm.dmalmAsmPk),
									pFound.getDmalmProjectPk());
							insert(t.get(prodotto.prodottoPk),
									ta.get(dmalmAsm.dmalmAsmPk),
									pFound.getDmalmProjectPk());
						}
					}// check
				}
			} else {
				// insert al prodotto tappo
				List<Tuple> check = new ArrayList<Tuple>();
				check = checkRelazione(0, ta.get(dmalmAsm.dmalmAsmPk),
						pFound.getDmalmProjectPk());
				if (check.size() == 0) {
					List<Tuple> parz = new ArrayList<Tuple>();
					int asm = ta.get(dmalmAsm.dmalmAsmPk);
					int proj = pFound.getDmalmProjectPk();
					parz = checkRelazioneParz(asm, proj);
					if (parz.size() == 0) {
						insert(0, asm, proj);
					}

				}
			}
			
			// controlli
			List<Tuple> checkProdTappo = new ArrayList<Tuple>();
			List<Tuple> checkAsmTappo = new ArrayList<Tuple>();
			List<Tuple> checkProjTappo = new ArrayList<Tuple>();
			List<Tuple> toCheck = new ArrayList<Tuple>();

			if (ta.get(dmalmAsm.dmalmAsmPk) != 0
					&& pFound.getDmalmProjectPk() != 0) {
				toCheck = checkRelazione(0, ta.get(dmalmAsm.dmalmAsmPk),
						pFound.getDmalmProjectPk());
				checkProdTappo = checkRelazioneParz(
						ta.get(dmalmAsm.dmalmAsmPk), pFound.getDmalmProjectPk());

				if (toCheck.size() > 0 && checkProdTappo.size() > 0) {
					delete(0, ta.get(dmalmAsm.dmalmAsmPk),
							pFound.getDmalmProjectPk());
				}
			}

			if (r.size() > 0 && pFound.getDmalmProjectPk() != 0) {
				toCheck = checkRelazione(r.get(0).get(prodotto.prodottoPk), 0,
						pFound.getDmalmProjectPk());
				checkAsmTappo = checkRelazioneParzAsm(
						r.get(0).get(prodotto.prodottoPk),
						pFound.getDmalmProjectPk());
				if (toCheck.size() > 0 && checkAsmTappo.size() > 0) {
					delete(r.get(0).get(prodotto.prodottoPk), 0,
							pFound.getDmalmProjectPk());
				}
			}
			if (r.size() > 0 && ta.get(dmalmAsm.dmalmAsmPk) != 0) {
				toCheck = checkRelazione(r.get(0).get(prodotto.prodottoPk),
						ta.get(dmalmAsm.dmalmAsmPk), 0);
				checkProjTappo = checkRelazioneParzProj(
						r.get(0).get(prodotto.prodottoPk),
						ta.get(dmalmAsm.dmalmAsmPk));
				if (toCheck.size() > 0 && checkProjTappo.size() > 0) {
					delete(r.get(0).get(prodotto.prodottoPk),
							ta.get(dmalmAsm.dmalmAsmPk), 0);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void insertProj(DmalmAsm found, Tuple tp, String sigla)
			throws DAOException {
		try {
			List<Tuple> r = new ArrayList<Tuple>();
			List<Tuple> dmAlmSourceElProdEccezzRow=DmAlmSourceElProdEccezDAO.getRow(sigla);
			if(!(dmAlmSourceElProdEccezzRow!=null && dmAlmSourceElProdEccezzRow.size()==1 && dmAlmSourceElProdEccezzRow.get(0).get(dmAlmSourceElProdEccez.tipoElProdEccezione).equals(1))){
				if (sigla.contains(".")) {
					sigla = sigla.substring(0, sigla.indexOf("."));
				}
			}
			//per cercare il Prodotto prendo il range temporale minore equivalente alla validità sia di Asm che di project
			Timestamp inizioVal = tp.get(project.dtInizioValidita);
			Timestamp fineVal = tp.get(project.dtFineValidita);
			if(found.getDataInizioValidita().after(tp.get(project.dtInizioValidita))) {
				inizioVal = found.getDataInizioValidita();
			}
			if(found.getDataFineValidita().before(tp.get(project.dtFineValidita))) {
				fineVal = found.getDataFineValidita();
			}
			
			r = ProdottiArchitettureDAO.getProductBySigla(sigla, inizioVal, fineVal);
			
			if (r.size() > 0) {
				// prodotto trovato
				for (Tuple t : r) {
					List<Tuple> check = new ArrayList<Tuple>();
					List<Tuple> old = new ArrayList<Tuple>();
					List<Tuple> oldAsm = new ArrayList<Tuple>();
					List<Tuple> oldProj = new ArrayList<Tuple>();
					check = checkRelazione(t.get(prodotto.prodottoPk),
							Integer.parseInt(found.getDmalmAsmPk()),
							tp.get(project.dmalmProjectPrimaryKey));
					if (check.size() == 0) {
						// devo inserirlo
						// se esiste già al tappo prodotto
						old = checkRelazione(0,
								Integer.parseInt(found.getDmalmAsmPk()),
								tp.get(project.dmalmProjectPrimaryKey));
						if (old.size() == 0) {
							// se esiste già al tappo proj
							oldAsm = checkRelazione(t.get(prodotto.prodottoPk),
									Integer.parseInt(found.getDmalmAsmPk()), 0);
							if (oldAsm.size() == 0) {
								if (Integer.parseInt(found.getDmalmAsmPk()) != 0) {
									// se esiste già al tappo asm
									oldProj = checkRelazione(
											t.get(prodotto.prodottoPk),
											0,
											tp.get(project.dmalmProjectPrimaryKey));
									if (oldProj.size() == 0) {
										// sicuro che non esiste quindi
										// inserisco
										insert(t.get(prodotto.prodottoPk),
												Integer.parseInt(found
														.getDmalmAsmPk()),
												tp.get(project.dmalmProjectPrimaryKey));
									} else {
										if (t.get(prodotto.prodottoPk) != 0) {
											delete(t.get(prodotto.prodottoPk),
													Integer.parseInt(found
															.getDmalmAsmPk()),
													0);
											insert(t.get(prodotto.prodottoPk),
													Integer.parseInt(found
															.getDmalmAsmPk()),
													tp.get(project.dmalmProjectPrimaryKey));
										}
									}
								}// insert
								else {
									oldProj = checkRelazione(
											t.get(prodotto.prodottoPk),
											0,
											tp.get(project.dmalmProjectPrimaryKey));
									if (oldProj.size() == 0) {

										// sicuro che non esiste quindi
										// inserisco
										insert(t.get(prodotto.prodottoPk),
												Integer.parseInt(found
														.getDmalmAsmPk()),
												tp.get(project.dmalmProjectPrimaryKey));
									}
								}
							}// oldAsm
							else {
								delete(t.get(prodotto.prodottoPk), 0,
										tp.get(project.dmalmProjectPrimaryKey));
								insert(t.get(prodotto.prodottoPk),
										Integer.parseInt(found.getDmalmAsmPk()),
										tp.get(project.dmalmProjectPrimaryKey));
							}
						}// old
						else {
							delete(0, Integer.parseInt(found.getDmalmAsmPk()),
									tp.get(project.dmalmProjectPrimaryKey));
							insert(t.get(prodotto.prodottoPk),
									Integer.parseInt(found.getDmalmAsmPk()),
									tp.get(project.dmalmProjectPrimaryKey));
						}
					}// check
				}
			} else {
				// insert al prodotto tappo
				List<Tuple> check = new ArrayList<Tuple>();
				check = checkRelazione(0,
						Integer.parseInt(found.getDmalmAsmPk()),
						tp.get(project.dmalmProjectPrimaryKey));
				if (check.size() == 0) {
					List<Tuple> parz = new ArrayList<Tuple>();
					int asm = Integer.parseInt(found.getDmalmAsmPk());
					int proj = tp.get(project.dmalmProjectPrimaryKey);
					parz = checkRelazioneParz(asm, proj);
					if (parz.size() == 0) {
						insert(0, Integer.parseInt(found.getDmalmAsmPk()),
								tp.get(project.dmalmProjectPrimaryKey));
					}

				}
			}
			
			// controlli
			List<Tuple> checkProdTappo = new ArrayList<Tuple>();
			List<Tuple> checkAsmTappo = new ArrayList<Tuple>();
			List<Tuple> checkProjTappo = new ArrayList<Tuple>();
			List<Tuple> toCheck = new ArrayList<Tuple>();
			if (Integer.parseInt(found.getDmalmAsmPk()) != 0
					&& tp.get(project.dmalmProjectPrimaryKey) != 0) {
				toCheck = checkRelazione(0,
						Integer.parseInt(found.getDmalmAsmPk()),
						tp.get(project.dmalmProjectPrimaryKey));
				checkProdTappo = checkRelazioneParz(
						Integer.parseInt(found.getDmalmAsmPk()),
						tp.get(project.dmalmProjectPrimaryKey));

				if (toCheck.size() > 0 && checkProdTappo.size() > 0) {
					delete(0, Integer.parseInt(found.getDmalmAsmPk()),
							tp.get(project.dmalmProjectPrimaryKey));
				}
			}
			if (r.size() > 0 && tp.get(project.dmalmProjectPrimaryKey) != 0) {
				toCheck = checkRelazione(r.get(0).get(prodotto.prodottoPk), 0,
						tp.get(project.dmalmProjectPrimaryKey));
				checkAsmTappo = checkRelazioneParzAsm(
						r.get(0).get(prodotto.prodottoPk),
						tp.get(project.dmalmProjectPrimaryKey));
				if (toCheck.size() > 0 && checkAsmTappo.size() > 0) {
					delete(r.get(0).get(prodotto.prodottoPk), 0,
							tp.get(project.dmalmProjectPrimaryKey));
				}
			}
			if (r.size() > 0 && Integer.parseInt(found.getDmalmAsmPk()) != 0) {
				toCheck = checkRelazione(r.get(0).get(prodotto.prodottoPk),
						Integer.parseInt(found.getDmalmAsmPk()), 0);
				checkProjTappo = checkRelazioneParzProj(
						r.get(0).get(prodotto.prodottoPk),
						Integer.parseInt(found.getDmalmAsmPk()));
				if (toCheck.size() > 0 && checkProjTappo.size() > 0) {
					delete(r.get(0).get(prodotto.prodottoPk),
							Integer.parseInt(found.getDmalmAsmPk()), 0);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static List<Tuple> checkRelazione(int prod, int asm, int proj)
			throws DAOException {
		List<Tuple> check = new ArrayList<Tuple>();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connection, dialect);
			check = query.from(asmProject).where(asmProject.dmalmAsmPk.eq(asm))
					.where(asmProject.dmalmProdottoPk.eq(prod))
					.where(asmProject.dmalmProjectPk.eq(proj))
					.list(asmProject.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return check;
	}

	private static void insert(int prod, int asm, int proj) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			new SQLInsertClause(connection, dialect, asmProject)
					.columns(asmProject.dmalmAsmPk, asmProject.dmalmProdottoPk,
							asmProject.dmalmProjectPk).values(asm, prod, proj)
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	private static void delete(int prod, int asm, int proj) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			new com.mysema.query.sql.dml.SQLDeleteClause(connection, dialect,
					asmProject).where(asmProject.dmalmAsmPk.eq(asm))
					.where(asmProject.dmalmProdottoPk.eq(prod))
					.where(asmProject.dmalmProjectPk.eq(proj)).execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	private static List<Tuple> checkRelazioneParz(int asm, int proj)
			throws DAOException {
		List<Tuple> check = new ArrayList<Tuple>();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connection, dialect);
			check = query.from(asmProject).where(asmProject.dmalmAsmPk.eq(asm))
					.where(asmProject.dmalmProjectPk.eq(proj))
					.where(asmProject.dmalmProdottoPk.ne(0))
					.list(asmProject.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return check;
	}

	private static List<Tuple> checkRelazioneParzAsm(int prod, int proj)
			throws DAOException {
		List<Tuple> check = new ArrayList<Tuple>();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connection, dialect);
			check = query.from(asmProject)
					.where(asmProject.dmalmProdottoPk.eq(prod))
					.where(asmProject.dmalmProjectPk.eq(proj))
					.where(asmProject.dmalmAsmPk.ne(0))
					.list(asmProject.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return check;
	}

	private static List<Tuple> checkRelazioneParzProj(int prod, int asm)
			throws DAOException {
		List<Tuple> check = new ArrayList<Tuple>();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connection, dialect);
			check = query.from(asmProject)
					.where(asmProject.dmalmProdottoPk.eq(prod))
					.where(asmProject.dmalmAsmPk.eq(asm))
					.where(asmProject.dmalmProjectPk.ne(0))
					.list(asmProject.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return check;
	}
	
	private static void deleteAll() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			
			new com.mysema.query.sql.dml.SQLDeleteClause(connection, dialect,
					asmProject).execute();
			
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
}
