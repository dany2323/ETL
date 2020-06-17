package lispa.schedulers.facade.target;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProjectProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.target.DmAlmProjectProdottoDAO;
import lispa.schedulers.dao.target.DmAlmSourceElProdEccezDAO;
import lispa.schedulers.dao.target.ProdottoDAO;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProdotto;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.QDmalmProjectProdotto;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckLinkProjectSgrCmProdottoFacade {
	private static Logger logger = Logger
			.getLogger(CheckLinkProjectSgrCmProdottoFacade.class);
	private static QDmalmProject dmalmProject = QDmalmProject.dmalmProject;
	private static QDmalmProdotto dmalmProdotto = QDmalmProdotto.dmalmProdotto;
	private static QDmalmProjectProdotto dmalmProjectProdotto = QDmalmProjectProdotto.dmalmProjectProdotto;
	private static QDmAlmSourceElProdEccez dmAlmSourceElProdEccez= QDmAlmSourceElProdEccez.dmAlmSourceElProd;
	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {
		try {
			if (ErrorManager.getInstance().hasError())
				return;

			logger.info("START CheckLinkProjectSgrCmProdottoFacade");

			boolean projectLegatoTappo = false;
			String[] multiSiglaProject = null;
			List<Tuple> relList = new ArrayList<Tuple>();
			List<Tuple> productList = new ArrayList<Tuple>();
			List<String> prjLinkedList = new ArrayList<String>();

			/**
			 * step 1 - Chiusura associazioni Prodotti chiusi: leggo tutte le
			 * relazioni "aperte" che hanno un puntamento ad un prodotto chiuso
			 * (è stato storicizzato il prodotto), queste relazioni le chiudo;
			 * lo step 3 partendo dai Project creerà una relazione nuova verso
			 * il prodotto "aperto"
			 */

			// tutti i Prodotti chiusi con relazione a Project ancora aperta
			relList = DmAlmProjectProdottoDAO
					.getAllRelationsToCloseCauseProductsClosed();

			// update DataFineValidita su tabella relazione
			for (Tuple projectProductRow : relList) {
				if (projectProductRow != null) {
					DmalmProjectProdotto relazione = new DmalmProjectProdotto();
					relazione.setDmalmProjectPk(projectProductRow
							.get(dmalmProjectProdotto.dmalmProjectPk));
					relazione.setDmalmProdottoSeq(projectProductRow
							.get(dmalmProjectProdotto.dmalmProdottoSeq));
					relazione.setDataInizioValidita(projectProductRow
							.get(dmalmProjectProdotto.dtInizioValidita));
					relazione.setDataFineValidita(projectProductRow
							.get(dmalmProdotto.dtFineValidita));

					DmAlmProjectProdottoDAO.closeRelProjectProdotto(relazione);
				}
			}

			/** step 2 - Chiusura associazioni Project chiusi */

			// legge tutte i Project chiusi con associazioni aperte da chiudere
			relList = DmAlmProjectProdottoDAO
					.getAllRelationsToCloseCauseProjectClosed();

			// update DataFineValidita su tabella relazione
			for (Tuple projectProductRow : relList) {
				if (projectProductRow != null) {
					DmalmProjectProdotto relazione = new DmalmProjectProdotto();
					relazione.setDmalmProjectPk(projectProductRow
							.get(dmalmProjectProdotto.dmalmProjectPk));
					relazione.setDmalmProdottoSeq(projectProductRow
							.get(dmalmProjectProdotto.dmalmProdottoSeq));
					relazione.setDataInizioValidita(projectProductRow
							.get(dmalmProjectProdotto.dtInizioValidita));
					relazione.setDataFineValidita(projectProductRow
							.get(dmalmProject.dtFineValidita));

					DmAlmProjectProdottoDAO.closeRelProjectProdotto(relazione);
				}
			}

			/** step 3 - Associazione Project Aperti con Prodotto Aperto */

			// legge tutti i project non associati
			relList = ProjectSgrCmDAO.getAllProjectToLinkWithProduct();
			logger.debug("Project letti: " + relList.size());

			// insert su tabella relazione
			for (Tuple projectRow : relList) {
				if (projectRow != null) {
					String siglaProject = projectRow
							.get(dmalmProject.siglaProject) == null ? "NULL"
							: projectRow.get(dmalmProject.siglaProject);

					// gestione multi siglaProject
					projectLegatoTappo = false;
					prjLinkedList = new ArrayList<String>();
					multiSiglaProject = siglaProject.split("\\.\\.");

					for (String siglaPrj : multiSiglaProject) {
						List<Tuple> dmAlmSourceElProdEccezzRow=DmAlmSourceElProdEccezDAO.getRow(siglaPrj);
						if(!(dmAlmSourceElProdEccez!=null && dmAlmSourceElProdEccezzRow.size()==1 && dmAlmSourceElProdEccezzRow.get(0).get(dmAlmSourceElProdEccez.tipoElProdEccezione).equals(1))){
						// toglie eventuali moduli dopo il nome Project
						if (siglaPrj.contains(".")) {
							siglaPrj = siglaPrj.substring(0,
									siglaPrj.indexOf("."));
						}
						}
						// scarta nomi duplicati
						if (!prjLinkedList.contains(siglaPrj)) {
							prjLinkedList.add(siglaPrj);

							// ricerca Prodotto per Project (siglaProject =
							// siglaProdotto)
							productList = ProdottoDAO
									.getProductByAcronym(siglaPrj);

							if (productList.size() == 0) {
								if (projectRow
										.get(dmalmProjectProdotto.dmalmProdottoSeq) == null
										&& !projectLegatoTappo) {
									projectLegatoTappo = true;
									
									DmalmProjectProdotto relazione = new DmalmProjectProdotto();
									relazione
											.setDmalmProjectPk(projectRow
													.get(dmalmProject.dmalmProjectPrimaryKey));
									relazione.setDmalmProdottoSeq(0);

									DmAlmProjectProdottoDAO
											.insertRelProjectProdotto(
													dataEsecuzione, relazione);
								}

								//segnalo solo una volta
								if (!projectLegatoTappo) {
									ErroriCaricamentoDAO
											.insert(DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
													DmAlmConstants.TARGET_DMALM_PROJECT_PRODOTTO,
													"SIGLA PROJECT: "
															+ siglaPrj
															+ "§ NAME PROJECT: "
															+ projectRow
																	.get(dmalmProject.nomeCompletoProject),
													DmAlmConstants.WRONG_LINK_PROJECT_PRODOTTO,
													DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
													dataEsecuzione);
								}
							} else {
								for (Tuple productRow : productList) {
									if (productRow != null) {
										if (projectRow
												.get(dmalmProjectProdotto.dmalmProdottoSeq) != null) {
											// chiudo la vecchia relazione al
											// tappo (dmalmProdottoSeq = 0)
											DmalmProjectProdotto relazione = new DmalmProjectProdotto();
											relazione
													.setDmalmProjectPk(projectRow
															.get(dmalmProjectProdotto.dmalmProjectPk));
											relazione
													.setDmalmProdottoSeq(projectRow
															.get(dmalmProjectProdotto.dmalmProdottoSeq));
											relazione
													.setDataInizioValidita(projectRow
															.get(dmalmProjectProdotto.dtInizioValidita));
											relazione
													.setDataFineValidita(DateUtils
															.addSecondsToTimestamp(
																	dataEsecuzione,
																	-1));

											DmAlmProjectProdottoDAO
													.closeRelProjectProdotto(relazione);
										}

										DmalmProjectProdotto relazione = new DmalmProjectProdotto();
										relazione
												.setDmalmProjectPk(projectRow
														.get(dmalmProject.dmalmProjectPrimaryKey));
										relazione
												.setDmalmProdottoSeq(productRow
														.get(dmalmProdotto.dmalmProdottoSeq));

										DmAlmProjectProdottoDAO
												.insertRelProjectProdotto(
														dataEsecuzione,
														relazione);
									}
								}
							}
						}
					}
				}
			}

			/**
			 * step 4 - Associazione di tutti i Prodotti Aperti non associabili
			 * (relazionandoli al Tappo del Project)
			 */

			// legge tutti i project non associati
			relList = ProdottoDAO.getAllProductToLinkWithProject();
			logger.debug("Prodotti letti: " + relList.size());

			// insert su tabella relazione
			for (Tuple productRow : relList) {
				if (productRow != null) {
					DmalmProjectProdotto relazione = new DmalmProjectProdotto();
					relazione.setDmalmProjectPk(0L);
					relazione.setDmalmProdottoSeq(productRow
							.get(dmalmProdotto.dmalmProdottoSeq));

					DmAlmProjectProdottoDAO.insertRelProjectProdotto(
							dataEsecuzione, relazione);
				}
			}

			/**
			 * step 5 - chiude i prodotti legati al tappo per i quali esiste
			 * anche una relazione a Project
			 */

			DmAlmProjectProdottoDAO.closeProductDuplicate();

			logger.info("STOP CheckLinkProjectSgrCmProdottoFacade");
		} catch (DAOException e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		}
	}
}
