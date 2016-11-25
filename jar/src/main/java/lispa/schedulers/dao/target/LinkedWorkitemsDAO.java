package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_LINKEDWORKITEMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.bean.target.fatti.DmalmProgettoDemand;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.target.fatti.ProgettoDemandDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmFilieraAnomalie;
import lispa.schedulers.queryimplementation.target.QDmalmFilieraProduttiva;
import lispa.schedulers.queryimplementation.target.QDmalmLinkedWorkitems;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaAssistenza;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdotto;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmBuild;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmClassificatoreDemand;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDifettoProdotto;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDocumento;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmFase;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmManutenzione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmPei;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoDemand;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoEse;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoDem;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoSvil;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgramma;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseDiProgetto;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseIt;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseServizi;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmRichiestaGestione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmRichiestaManutenzione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSottoprogramma;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTask;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTaskIt;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTestcase;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.DefaultMapper;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class LinkedWorkitemsDAO {

	private static Logger logger = Logger.getLogger(LinkedWorkitemsDAO.class);

	private static QDmalmLinkedWorkitems link = QDmalmLinkedWorkitems.dmalmLinkedWorkitems;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	// TUTTE LE TABELLE FATTO

	private static QDmalmDifettoProdotto difetto = QDmalmDifettoProdotto.dmalmDifettoProdotto;
	private static QDmalmAnomaliaProdotto anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
	private static QDmalmProgettoSviluppoSvil progettoSviluppoSvil = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
	private static QDmalmDocumento documento = QDmalmDocumento.dmalmDocumento;
	private static QDmalmManutenzione manutenzione = QDmalmManutenzione.dmalmManutenzione;
	private static QDmalmTestcase testcase = QDmalmTestcase.dmalmTestcase;
	private static QDmalmTask task = QDmalmTask.dmalmTask;
	private static QDmalmReleaseDiProgetto releaseDiProgetto = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
	private static QDmalmProgramma programma = QDmalmProgramma.dmalmProgramma;
	private static QDmalmSottoprogramma sottoprogramma = QDmalmSottoprogramma.dmalmSottoprogramma;
	private static QDmalmProgettoDemand progettoDemand = QDmalmProgettoDemand.dmalmProgettoDemand;
	private static QDmalmProgettoSviluppoDem progettoSviluppoDem = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;
	private static QDmalmRichiestaManutenzione richiestaManutenzione = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
	private static QDmalmFase fase = QDmalmFase.dmalmFase;
	private static QDmalmPei pei = QDmalmPei.dmalmPei;
	private static QDmalmProgettoEse progettoEse = QDmalmProgettoEse.dmalmProgettoEse;
	private static QDmalmReleaseIt releaseIT = QDmalmReleaseIt.dmalmReleaseIt;
	private static QDmalmBuild build = QDmalmBuild.dmalmBuild;
	private static QDmalmTaskIt taskIT = QDmalmTaskIt.dmalmTaskIt;
	private static QDmalmRichiestaGestione richiestaGestione = QDmalmRichiestaGestione.dmalmRichiestaGestione;
	private static QDmalmAnomaliaAssistenza anomaliaAssistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
	private static QDmalmReleaseServizi releaseServizi = QDmalmReleaseServizi.dmalmReleaseServizi;
	private static QDmalmFilieraProduttiva filieraProduttiva = QDmalmFilieraProduttiva.dmalmFilieraProduttiva;
	private static QDmalmFilieraAnomalie filieraAnomalie = QDmalmFilieraAnomalie.dmalmFilieraAnomalie;
	private static QDmalmClassificatoreDemand classificatoredem = QDmalmClassificatoreDemand.dmalmClassificatoreDemand;
	
	private static QDmalmProject project = QDmalmProject.dmalmProject; 

	public static boolean isToBeInserted(DmalmLinkedWorkitems link) {

		try {
			boolean res = (getFactPkByType(link.getTipoWiFiglio(),
					link.getPkFiglio()) != 0 && getFactPkByType(
					link.getTipoWiPadre(), link.getPkPadre()) != 0);

			return res;

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			return false;
		}

	}

	protected static int getFactPkByType(String workitemType, String cPk)
			throws DAOException {

		ConnectionManager cm = null;
		Connection conn = null;
		List<Integer> pk = new ArrayList<Integer>();

		try {

			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(conn, dialect);

			switch (workitemType) {

			case "anomalia":
				pk = query.from(anomalia).where(anomalia.stgPk.eq(cPk))
						.list(anomalia.dmalmAnomaliaProdottoPk);
				break;
			case "defect":
				pk = query.from(difetto).where(difetto.stgPk.eq(cPk))
						.list(difetto.dmalmDifettoProdottoPk);
				break;
			case "srqs":
				pk = query.from(progettoSviluppoSvil)
						.where(progettoSviluppoSvil.stgPk.eq(cPk))
						.list(progettoSviluppoSvil.dmalmProgSvilSPk);
				break;
			case "documento":
				pk = query.from(documento).where(documento.stgPk.eq(cPk))
						.list(documento.dmalmDocumentoPk);
				break;
			case "sman":
				pk = query.from(manutenzione).where(manutenzione.stgPk.eq(cPk))
						.list(manutenzione.dmalmManutenzionePk);
				break;
			case "testcase":
				pk = query.from(testcase).where(testcase.stgPk.eq(cPk))
						.list(testcase.dmalmTestcasePk);
				break;
			case "task":
				pk = query.from(task).where(task.stgPk.eq(cPk))
						.list(task.dmalmTaskPk);
				break;
			case "release":
				pk = query.from(releaseDiProgetto)
						.where(releaseDiProgetto.stgPk.eq(cPk))
						.list(releaseDiProgetto.dmalmReleasediprogPk);
				break;
			case "programma":
				pk = query.from(programma).where(programma.stgPk.eq(cPk))
						.list(programma.dmalmProgrammaPk);
				break;
			case "sottoprogramma":
				pk = query.from(sottoprogramma)
						.where(sottoprogramma.stgPk.eq(cPk))
						.list(sottoprogramma.dmalmSottoprogrammaPk);
				break;
			case "rqd":
				pk = query.from(progettoDemand)
						.where(progettoDemand.stgPk.eq(cPk))
						.list(progettoDemand.dmalmProgettoDemandPk);
				break;
			case "drqs":
				pk = query.from(progettoSviluppoDem)
						.where(progettoSviluppoDem.stgPk.eq(cPk))
						.list(progettoSviluppoDem.dmalmProgSvilDPk);
				break;
			case "dman":
				pk = query.from(richiestaManutenzione)
						.where(richiestaManutenzione.stgPk.eq(cPk))
						.list(richiestaManutenzione.dmalmRichManutenzionePk);
				break;
			case "fase":
				pk = query.from(fase).where(fase.stgPk.eq(cPk))
						.list(fase.dmalmFasePk);
				break;
			case "pei":
				pk = query.from(pei).where(pei.stgPk.eq(cPk))
						.list(pei.dmalmPeiPk);
				break;
			case "progettoese":
				pk = query.from(progettoEse).where(progettoEse.stgPk.eq(cPk))
						.list(progettoEse.dmalmProgettoEsePk);
				break;
			case "release_it":
				pk = query.from(releaseIT).where(releaseIT.stgPk.eq(cPk))
						.list(releaseIT.dmalmReleaseItPk);
				break;
			case "build":
				pk = query.from(build).where(build.stgPk.eq(cPk))
						.list(build.dmalmBuildPk);
				break;
			case "taskit":
				pk = query.from(taskIT).where(taskIT.stgPk.eq(cPk))
						.list(taskIT.dmalmTaskItPk);
				break;
			case "richiesta_gestione":
				pk = query.from(richiestaGestione)
						.where(richiestaGestione.stgPk.eq(cPk))
						.list(richiestaGestione.dmalmRichiestaGestPk);
				break;
			case "anomalia_assistenza":
				pk = query.from(anomaliaAssistenza)
						.where(anomaliaAssistenza.stgPk.eq(cPk))
						.list(anomaliaAssistenza.dmalmAnomaliaAssPk);
				break;
			case "release_ser":
				pk = query.from(releaseServizi)
						.where(releaseServizi.stgPk.eq(cPk))
						.list(releaseServizi.dmalmRelServiziPk);
				break;
				
			case "classificatore_demand":
				pk = query.from(classificatoredem)
						.where(classificatoredem.stgPk.eq(cPk))
						.list(classificatoredem.dmalmClassificatorePk);
				break;

			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(conn);
		}

		return (pk != null && pk.size() != 0) ? pk.get(0).intValue() : 0;

	}

	public static void insertLinkList(
			List<DmalmLinkedWorkitems> staging_linkedWorkitems)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					link);
			int i = 0;
			for (DmalmLinkedWorkitems bean : staging_linkedWorkitems) {
				insert.populate(bean, DefaultMapper.WITH_NULL_BINDINGS)
						.addBatch();
				i++;
				if (i % DmAlmConstants.BATCH_SIZE == 0) {
					insert.execute();
					insert = new SQLInsertClause(connection, dialect, link);
				}
			}

			if (!insert.isEmpty())
				insert.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static int insertLinkedWorkitems(Timestamp dataEsecuzione)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;

		QDmalmLinkedWorkitems linkedWorkitemsTarget = QDmalmLinkedWorkitems.dmalmLinkedWorkitems;

		int res = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			// I LinkedWorkitems vengono cancellati e reinseriti ogni giorno da
			// zero
			new SQLDeleteClause(connection, dialect, linkedWorkitemsTarget)
					.execute();

			String sql = QueryManager.getInstance().getQuery(
					SQL_LINKEDWORKITEMS);
			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);
			res = ps.executeUpdate();
			ps.close();
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return res;
	}

	public static List<DmalmLinkedWorkitems> getStartWorkitems(
			Timestamp dataInizioFiliera) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> list = null;
		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(link)
					.join(programma)
					.on(programma.cdProgramma.eq(link.codiceWiPadre).and(
							programma.idRepository.eq(link.idRepositoryPadre)))
					.where(link.tipoWiPadre.eq("programma"))
					.where(link.ruolo.eq("parent"))
					.where(link.tipoWiFiglio
							.in("sottoprogramma", "rqd", "dman"))
					.where(programma.rankStatoProgramma.eq(new Double("1")))
					.where(programma.dtCreazioneProgramma
							.goe(dataInizioFiliera))
					.orderBy(link.idRepositoryPadre.asc())
					.orderBy(link.fkWiPadre.asc())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();

			for (DmalmLinkedWorkitems linkedWorkitem : list) {
				// Progetto Demand può essere legato direttamente ad un
				// Programma bypassando il Sottoprogramma, solo se il
				// sottoprogramma non esiste
				// se trovo il sottoprogramma scarto completamente questa
				// filiera, stessa cosa per Richiesta di Manutenzione (dman)
				if (linkedWorkitem.getTipoWiPadre().equalsIgnoreCase(
						"programma")
						&& linkedWorkitem.getTipoWiFiglio().equalsIgnoreCase(
								"rqd")) {
					if (!LinkedWorkitemsDAO
							.progettoDemandConSottoprogramma(linkedWorkitem)) {

						// se il figlio è un Progetto demand devo verificare se
						// il figlio è anche figlio di un altro Progetto Demand.
						// Se così fosse devo escudere tutte le possibili
						// filiere che legano il secondo progetto demand
						// direttamente al padre del primo Progetto demand
						if (!LinkedWorkitemsDAO
								.progettoDemandConProgettoDemandPadre(linkedWorkitem))
							resultList.add(linkedWorkitem);
					}
				} else {
					if (linkedWorkitem.getTipoWiPadre().equalsIgnoreCase(
							"programma")
							&& linkedWorkitem.getTipoWiFiglio()
									.equalsIgnoreCase("dman")) {
						if (!LinkedWorkitemsDAO
								.richiestaManutenzioneConSottoprogramma(linkedWorkitem))
							resultList.add(linkedWorkitem);
					} else {
						resultList.add(linkedWorkitem);
					}
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}

	public static boolean progettoDemandConSottoprogramma(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		boolean trovatoSottoprogramma = false;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> list = query.from(link)
					.where(link.tipoWiPadre.eq("sottoprogramma"))
					.where(link.tipoWiFiglio.eq("rqd"))
					.where(link.ruolo.eq("parent"))
					.where(link.fkWiFiglio.eq(linkedWorkitem.getFkWiFiglio()))
					.list(link.all());

			connection.commit();

			if (list != null && list.size() > 0)
				trovatoSottoprogramma = true;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return trovatoSottoprogramma;
	}

	public static boolean richiestaManutenzioneConSottoprogramma(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		boolean trovatoSottoprogramma = false;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> list = query.from(link)
					.where(link.tipoWiPadre.eq("sottoprogramma"))
					.where(link.tipoWiFiglio.eq("dman"))
					.where(link.ruolo.eq("parent"))
					.where(link.fkWiFiglio.eq(linkedWorkitem.getFkWiFiglio()))
					.list(link.all());

			connection.commit();

			if (list != null && list.size() > 0)
				trovatoSottoprogramma = true;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return trovatoSottoprogramma;
	}

	public static List<DmalmLinkedWorkitems> getNextWorkitems(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> list = null;
		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(link)
					.where(link.fkWiPadre.eq(linkedWorkitem.getFkWiFiglio()))
					.where(link.tipoWiFiglio.in("rqd", "srqs", "drqs", "dman",
							"sman"))
					.where(link.ruolo.isNotNull())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();

			for (DmalmLinkedWorkitems lw : list) {
				// Combinazioni ammesse
				// Solo Progetto Demand può essere legato direttamente ad un wi
				// dello stesso tipo
				if ((lw.getTipoWiPadre().equalsIgnoreCase("sottoprogramma") && lw
						.getTipoWiFiglio().equalsIgnoreCase("rqd"))
						|| (lw.getTipoWiPadre().equalsIgnoreCase("rqd") && lw
								.getTipoWiFiglio().equalsIgnoreCase("rqd"))
						|| (lw.getTipoWiPadre().equalsIgnoreCase("rqd") && lw
								.getTipoWiFiglio().equalsIgnoreCase("drqs"))
						|| (lw.getTipoWiPadre().equalsIgnoreCase("drqs") && lw
								.getTipoWiFiglio().equalsIgnoreCase("srqs"))
						|| (lw.getTipoWiPadre().equalsIgnoreCase("rqd") && lw
								.getTipoWiFiglio().equalsIgnoreCase("srqs"))
						|| (lw.getTipoWiPadre().equalsIgnoreCase(
								"sottoprogramma") && lw.getTipoWiFiglio()
								.equalsIgnoreCase("dman"))
						|| (lw.getTipoWiPadre().equalsIgnoreCase("dman") && lw
								.getTipoWiFiglio().equalsIgnoreCase("sman"))
						|| (lw.getTipoWiPadre().equalsIgnoreCase("drqs") && lw
								.getTipoWiFiglio().equalsIgnoreCase("sman"))
						|| (lw.getTipoWiPadre().equalsIgnoreCase("dman") && lw
								.getTipoWiFiglio().equalsIgnoreCase("srqs"))) {

					if (lw.getTipoWiPadre().equalsIgnoreCase("rqd")
							&& lw.getTipoWiFiglio().equalsIgnoreCase("srqs")) {
						// Il Progetto Sviluppo Sviluppo può essere collegato
						// direttamente ad un Progetto Demand bypassando il
						// Progetto
						// Sviluppo Demand
						// solo e solo se non è legato direttamente anche ad un
						// workitem Progetto Sviluppo Demand (sulla stessa
						// catena)
						if (!LinkedWorkitemsDAO
								.progettoSviluppoSviluppoConProgettoSviluppoDemand(lw)) {

							// se inserisco ProgettoDemand padre verifico
							// l'eccezione
							controllaEccezioneProgettoDemandClassificazione(lw);

							resultList.add(lw);
						}
					} else if (lw.getTipoWiPadre().equalsIgnoreCase("rqd")
							&& lw.getTipoWiFiglio().equalsIgnoreCase("rqd")) {
						// Se il padre del padre è anche esso un Progetto Demand
						// lo scarto in quanto il massimo Progetti Demand
						// ammessi in filiera è 2
						if (!linkedWorkitem.getTipoWiPadre().equalsIgnoreCase(
								"rqd")) {
							// se inserisco ProgettoDemand padre verifico
							// l'eccezione
							controllaEccezioneProgettoDemandClassificazione(lw);

							resultList.add(lw);
						}
					} else if (!lw.getTipoWiPadre().equalsIgnoreCase("rqd")
							&& lw.getTipoWiFiglio().equalsIgnoreCase("rqd")) {
						// se il figlio è un Progetto demand e il padre non lo è
						// devo verificare se il figlio è anche figlio di un
						// altro Progetto Demand. Se così fosse devo escudere
						// tutte le possibili filiere che legano il secondo
						// progetto demand direttamente al padre del primo
						// Progetto demand
						if (!LinkedWorkitemsDAO
								.progettoDemandConProgettoDemandPadre(lw))
							resultList.add(lw);
					} else {
						if ((lw.getTipoWiPadre().equalsIgnoreCase("drqs") && lw
								.getTipoWiFiglio().equalsIgnoreCase("sman"))
								|| (lw.getTipoWiPadre()
										.equalsIgnoreCase("dman") && lw
										.getTipoWiFiglio().equalsIgnoreCase(
												"srqs"))) {
							// eccezioni da segnalare
							String eccezione = "";
							if (lw.getTipoWiPadre().equalsIgnoreCase("drqs")
									&& lw.getTipoWiFiglio().equalsIgnoreCase(
											"sman")) {
								eccezione = DmAlmConstants.ECCEZIONE_FILIERA_DRQS_SMAN;
							} else {
								eccezione = DmAlmConstants.ECCEZIONE_FILIERA_DMAN_SRQS;
							}

							ErroriCaricamentoDAO.insert(
									DmAlmConstants.TARGET_LINKEDWORKITEMS,
									DmAlmConstants.TARGET_FILIERA_PRODUTTIVA,
									"codicePadre: " + lw.getCodiceWiPadre()
											+ "§ codiceFiglio: "
											+ lw.getCodiceWiFiglio(),
									eccezione,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									DataEsecuzione.getInstance()
											.getDataEsecuzione());
						} else if (lw.getTipoWiPadre().equalsIgnoreCase("rqd")) {
							// se inserisco ProgettoDemand padre verifico
							// l'eccezione
							controllaEccezioneProgettoDemandClassificazione(lw);
						}

						resultList.add(lw);
					}
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}

	public static void controllaEccezioneProgettoDemandClassificazione(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		// il campo classificazione non è valorizzato ma al progetto demand è
		// collegata una filiera

		QDmalmProgettoDemand progDemand = QDmalmProgettoDemand.dmalmProgettoDemand;
		List<Tuple> target_progettoDemand = new ArrayList<Tuple>();

		DmalmProgettoDemand progetto = new DmalmProgettoDemand();
		progetto.setCdProgettoDemand(linkedWorkitem.getCodiceWiPadre());
		progetto.setIdRepository(linkedWorkitem.getIdRepositoryPadre());

		target_progettoDemand = ProgettoDemandDAO.getProgettoDemand(progetto);

		if (target_progettoDemand.size() > 0) {
			Tuple row = target_progettoDemand.get(0);

			if (row.get(progDemand.cfClassificazione) == null) {
				ErroriCaricamentoDAO.insert(
						DmAlmConstants.TARGET_LINKEDWORKITEMS,
						DmAlmConstants.TARGET_FILIERA_PRODUTTIVA,
						"codiceProgettoDemand: "
								+ progetto.getCdProgettoDemand()
								+ "§ idRepository: "
								+ progetto.getIdRepository()
								+ "§ cfClassificazione: NULL",
						DmAlmConstants.ECCEZIONE_FILIERA_RQD_CLASSIFICAZIONE,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						DataEsecuzione.getInstance().getDataEsecuzione());
			}
		}
	}

	public static boolean progettoSviluppoSviluppoConProgettoSviluppoDemand(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		// Il Progetto Sviluppo Sviluppo può essere collegato
		// direttamente ad un Progetto Demand bypassando il
		// Progetto
		// Sviluppo Demand
		// solo e solo se non è legato direttamente anche ad un
		// workitem Progetto Sviluppo Demand (sulla stessa catena)

		ConnectionManager cm = null;
		Connection connection = null;

		boolean trovatoProgettoSviluppoDemand = false;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> list = query.from(link)
					.where(link.tipoWiPadre.eq("drqs"))
					.where(link.tipoWiFiglio.eq("srqs"))
					.where(link.ruolo.isNotNull())
					.where(link.fkWiFiglio.eq(linkedWorkitem.getFkWiFiglio()))
					.list(link.all());

			connection.commit();

			if (list != null && list.size() > 0) {
				for (Tuple row : list) {
					if (row != null) {
						// verifico se è sulla stessa catena

						query = new SQLQuery(connection, dialect);

						List<Tuple> listRqd = query
								.from(link)
								.where(link.tipoWiPadre.eq("rqd"))
								.where(link.codiceWiPadre.eq(linkedWorkitem
										.getCodiceWiPadre()))
								.where(link.tipoWiFiglio.eq("drqs"))
								.where(link.codiceWiFiglio.eq(row
										.get(link.codiceWiPadre)))
								.where(link.ruolo.isNotNull()).list(link.all());

						if (listRqd != null && listRqd.size() > 0) {
							trovatoProgettoSviluppoDemand = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return trovatoProgettoSviluppoDemand;
	}

	public static boolean progettoDemandConProgettoDemandPadre(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		boolean trovatoProgettoDemand = false;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> list = query.from(link)
					.where(link.tipoWiPadre.eq("rqd"))
					.where(link.tipoWiFiglio.eq("rqd"))
					.where(link.ruolo.isNotNull())
					.where(link.fkWiFiglio.eq(linkedWorkitem.getFkWiFiglio()))
					.list(link.all());

			connection.commit();

			if (list != null && list.size() > 0)
				trovatoProgettoDemand = true;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return trovatoProgettoDemand;
	}

	public static List<DmalmLinkedWorkitems> getStartWorkitemsAscending(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> list = null;
		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();
		List<DmalmLinkedWorkitems> reverseList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(link)
					.where(link.fkWiFiglio.eq(linkedWorkitem.getFkWiFiglio()))
					.where(link.tipoWiFiglio.in("srqs", "drqs", "sman"))
					.where(link.tipoWiPadre.eq("release"))
					.where(link.ruolo.in("rilasciato_in", "incluso_in",
							"relates_to"))
					.orderBy(link.fkWiPadre.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();

			for (DmalmLinkedWorkitems lw : list) {
				// Combinazioni ammesse
				// ruolo relates_to solo per release/srqs
				if (lw.getRuolo().equalsIgnoreCase("relates_to")) {
					if (lw.getTipoWiPadre().equalsIgnoreCase("release")
							&& lw.getTipoWiFiglio().equalsIgnoreCase("srqs")) {
						resultList.add(lw);
					}
				} else {
					resultList.add(lw);
				}
			}

			// inverte padre/figlio
			reverseList = swapList(resultList);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return reverseList;
	}

	private static List<DmalmLinkedWorkitems> swapList(
			List<DmalmLinkedWorkitems> list) throws Exception {
		List<DmalmLinkedWorkitems> reverseList = new LinkedList<DmalmLinkedWorkitems>();

		// inverte padre/figlio
		for (DmalmLinkedWorkitems workitem : list) {
			DmalmLinkedWorkitems reverseWorkitem = new DmalmLinkedWorkitems();
			reverseWorkitem.setFkWiFiglio(workitem.getFkWiPadre());
			reverseWorkitem.setCodiceWiFiglio(workitem.getCodiceWiPadre());
			reverseWorkitem.setTipoWiFiglio(workitem.getTipoWiPadre());
			reverseWorkitem.setIdRepositoryFiglio(workitem
					.getIdRepositoryPadre());
			reverseWorkitem.setUriWiFiglio(workitem.getUriWiPadre());
			reverseWorkitem.setCodiceProjectFiglio(workitem
					.getCodiceProjectPadre());
			reverseWorkitem.setFkWiPadre(workitem.getFkWiFiglio());
			reverseWorkitem.setCodiceWiPadre(workitem.getCodiceWiFiglio());
			reverseWorkitem.setTipoWiPadre(workitem.getTipoWiFiglio());
			reverseWorkitem.setIdRepositoryPadre(workitem
					.getIdRepositoryFiglio());
			reverseWorkitem.setUriWiPadre(workitem.getUriWiFiglio());
			reverseWorkitem.setCodiceProjectPadre(workitem
					.getCodiceProjectFiglio());
			reverseWorkitem.setRuolo(workitem.getRuolo());

			reverseList.add(reverseWorkitem);
		}

		return reverseList;
	}

	public static List<DmalmLinkedWorkitems> getNextWorkitemsAscending(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> list = null;
		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();
		List<DmalmLinkedWorkitems> reverseList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(link)
					.where(link.fkWiFiglio.eq(linkedWorkitem.getFkWiFiglio()))
					.where(link.tipoWiPadre.in("release_ser", "release_it"))
					.where(link.ruolo.in("rilasciato", "rilasciato_in",
							"incluso_in"))
					.orderBy(link.fkWiPadre.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();

			for (DmalmLinkedWorkitems lw : list) {
				// Combinazioni ammesse
				if ((lw.getTipoWiPadre().equalsIgnoreCase("release_it") && lw
						.getTipoWiFiglio().equalsIgnoreCase("release"))
						|| (lw.getTipoWiPadre().equalsIgnoreCase("release_ser") && lw
								.getTipoWiFiglio().equalsIgnoreCase(
										"release_it"))) {
					resultList.add(lw);
				}
			}

			// inverte padre/figlio
			reverseList = swapList(resultList);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return reverseList;
	}

	public static List<DmalmLinkedWorkitems> getNoLinkedProgramWorkitems(
			Timestamp dataInizioFiliera) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(link)
					.join(programma)
					.on(programma.cdProgramma.eq(link.codiceWiPadre).and(
							programma.idRepository.eq(link.idRepositoryPadre)))
					.leftJoin(filieraProduttiva)
					.on(filieraProduttiva.codiceWi.eq(link.codiceWiPadre).and(
							filieraProduttiva.idRepository.eq(
									link.idRepositoryPadre).and(
									filieraProduttiva.tipoWi
											.eq(link.tipoWiPadre))))
					.where(link.tipoWiPadre.eq("programma"))
					.where(programma.rankStatoProgramma.eq(new Double("1")))
					.where(programma.dtCreazioneProgramma
							.goe(dataInizioFiliera))
					.where(filieraProduttiva.idFiliera.isNull())
					.orderBy(link.idRepositoryPadre.asc())
					.orderBy(link.fkWiPadre.asc())
					.distinct()
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.codiceWiPadre, link.tipoWiPadre,
							link.fkWiPadre, link.uriWiFiglio,
							link.idRepositoryPadre, link.codiceProjectPadre));

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return list;
	}

	public static List<DmalmLinkedWorkitems> getStartWorkitemsAnomalie(
			Timestamp dataInizioFiliera) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.join(anomaliaAssistenza)
					.on(anomaliaAssistenza.cdAnomaliaAss.eq(link.codiceWiPadre)
							.and(anomaliaAssistenza.idRepository
									.eq(link.idRepositoryPadre)))
					.where(link.tipoWiPadre.eq("anomalia_assistenza"))
					.where((link.tipoWiFiglio.eq("anomalia").and(link.ruolo.eq("relates_to"))).or(link.tipoWiFiglio.eq("anomalia_assistenza").and(link.ruolo.in("riassegnata_da", "coincide_con"))))
					.where(anomaliaAssistenza.rankStatoAnomaliaAss
							.eq(new Double("1")))
					.where(anomaliaAssistenza.dtCreazioneAnomaliaAss
							.goe(dataInizioFiliera))
					.orderBy(link.idRepositoryPadre.asc())
					.orderBy(link.fkWiPadre.asc())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}

	public static List<DmalmLinkedWorkitems> getStartWorkitemsAnomalieCorta(
			Timestamp dataInizioFiliera) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.join(anomalia)
					.on(anomalia.cdAnomalia.eq(link.codiceWiPadre)
							.and(anomalia.idRepository.eq(link.idRepositoryPadre)))
					.leftJoin(filieraAnomalie)
					.on(filieraAnomalie.codiceWi.eq(link.codiceWiPadre)
							.and(filieraAnomalie.idRepository.eq(link.idRepositoryPadre)))
					.where(link.tipoWiPadre.eq("anomalia"))
					.where(link.tipoWiFiglio.in("sman", "srqs"))
					.where(link.ruolo.isNotNull())
					.where(anomalia.rankStatoAnomalia.eq(new Double("1")))
					.where(anomalia.dtCreazioneAnomalia.goe(dataInizioFiliera))
					.orderBy(link.idRepositoryPadre.asc())
					.orderBy(link.fkWiPadre.asc())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<DmalmLinkedWorkitems> getNextWorkitemsAnomalie(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.where(link.fkWiPadre.eq(linkedWorkitem.getFkWiFiglio()))
					.where(link.tipoWiPadre.in("anomalia_assistenza", "anomalia").and(link.tipoWiFiglio.in("anomalia", "srqs", "sman")))
					.where(link.ruolo.isNotNull())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}

	public static List<DmalmLinkedWorkitems> getStartWorkitemsAscendingAnomalie(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> list = null;
		List<DmalmLinkedWorkitems> reverseList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(link)
					.where(link.fkWiFiglio.eq(linkedWorkitem.getFkWiFiglio()))
					.where(link.tipoWiFiglio.in("anomalia", "sman", "srqs"))
					.where(link.tipoWiPadre.eq("release"))
					.where(link.ruolo
							.in("follow_up", "refines", "rilasciato_in",
									"rilevato", "parent", "relates_to"))
					.orderBy(link.fkWiPadre.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();

			// inverte padre/figlio
			reverseList = swapList(list);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return reverseList;
	}

	public static List<Tuple> getNoLinkedAnomalieWorkitems(
			Timestamp dataInizioFiliera) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(anomaliaAssistenza)
					.join(project).on(project.dmalmProjectPrimaryKey.eq(anomaliaAssistenza.dmalmProjectFk02))
					.leftJoin(filieraAnomalie)
					.on(filieraAnomalie.codiceWi.eq(anomaliaAssistenza.cdAnomaliaAss).and(
							filieraAnomalie.idRepository.eq(
									anomaliaAssistenza.idRepository).and(
									filieraAnomalie.tipoWi
											.eq("anomalia_assistenza"))))
					.where(anomaliaAssistenza.rankStatoAnomaliaAss
							.eq(new Double("1")))
					.where(anomaliaAssistenza.dtCreazioneAnomaliaAss
							.goe(dataInizioFiliera))
					.where(filieraAnomalie.idFiliera.isNull())
					.orderBy(anomaliaAssistenza.idRepository.asc())
					.orderBy(anomaliaAssistenza.dmalmAnomaliaAssPk.asc())
					.distinct()
					.list(anomaliaAssistenza.cdAnomaliaAss, anomaliaAssistenza.dmalmAnomaliaAssPk,
							anomaliaAssistenza.uri,
							anomaliaAssistenza.idRepository, project.idProject);

			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return list;
	}
	
	public static List<DmalmLinkedWorkitems> getStartWorkitemsTemplateDemand(
			Timestamp dataInizioFiliera) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.join(programma)
					.on(programma.cdProgramma.eq(link.codiceWiPadre)
						.and(programma.idRepository.eq(link.idRepositoryPadre)))
					.where(link.tipoWiPadre.eq("programma"))
					.where(link.tipoWiFiglio.eq("sottoprogramma").or(link.tipoWiFiglio.eq("pei")))
					.where(link.ruolo.isNotNull())
					.where(programma.rankStatoProgramma.eq(new Double("1")))
					.where(programma.dtCreazioneProgramma.goe(dataInizioFiliera))
					.orderBy(link.idRepositoryPadre.asc())
					.orderBy(link.fkWiPadre.asc())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class, link.all()));
			
			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<DmalmLinkedWorkitems> getNextWorkitemsTemplateDemand(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.where(link.fkWiPadre.eq(linkedWorkitem.getFkWiFiglio()))
					.where(link.tipoWiPadre.in("sottoprogramma", "rqd", "dman", "progettoese", "drqs", "pei")
					.and(link.tipoWiFiglio.in("rqd", "dman", "progettoese", "drqs", "pei", "fase")))
					.where(link.ruolo.isNotNull())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<DmalmLinkedWorkitems> getStartWorkitemsTemplateSviluppo(
			Timestamp dataInizioFiliera, String tipoWIPadre, String tipoWIFiglio) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.join(progettoSviluppoSvil)
					.on(progettoSviluppoSvil.cdProgSvilS.eq(link.codiceWiPadre)
						.and(progettoSviluppoSvil.idRepository.eq(link.idRepositoryPadre)))
					.where(link.tipoWiPadre.eq(tipoWIPadre))
					.where(link.tipoWiFiglio.eq(tipoWIFiglio))
					.where(link.ruolo.isNotNull())
					.where(progettoSviluppoSvil.rankStatoProgSvilS.eq(new Double("1")))
					.where(progettoSviluppoSvil.dtCreazioneProgSvilS.goe(dataInizioFiliera))
					.orderBy(link.idRepositoryPadre.asc())
					.orderBy(link.fkWiPadre.asc())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class, link.all()));
			
			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<DmalmLinkedWorkitems> getNextWorkitemsTemplateSviluppo(
			DmalmLinkedWorkitems linkedWorkitem, Integer fkWiFiglio) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.where(link.fkWiPadre.eq(fkWiFiglio))
					.where(link.tipoWiPadre.in("release", "testcase", "task", "anomalia")
					.and(link.tipoWiFiglio.in("testcase", "task", "anomalia", "defect")))
					.where(link.ruolo.isNotNull())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<DmalmLinkedWorkitems> getStartWorkitemsTemplateIntTecnica(
			Timestamp dataInizioFiliera) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.join(releaseIT)
					.on(releaseIT.cdReleaseIt.eq(link.codiceWiPadre)
						.and(releaseIT.idRepository.eq(link.idRepositoryPadre)))
					.where(link.tipoWiPadre.eq("release_it"))
					.where(link.tipoWiFiglio.eq("build").or(link.tipoWiFiglio.eq("taskit")))
					.where(link.ruolo.isNotNull())
					.where(releaseIT.rankStatoReleaseIt.eq(new Double("1")))
					.where(releaseIT.dtCreazioneReleaseIt.goe(dataInizioFiliera))
					.orderBy(link.idRepositoryPadre.asc())
					.orderBy(link.fkWiPadre.asc())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class, link.all()));
			
			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<DmalmLinkedWorkitems> getNextWorkitemsTemplateIntTecnica(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.where(link.fkWiPadre.eq(linkedWorkitem.getFkWiFiglio()))
					.where(link.tipoWiPadre.in("build", "taskit")
					.and(link.tipoWiFiglio.in("taskit", "defect")))
					.where(link.ruolo.isNotNull())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<DmalmLinkedWorkitems> getStartWorkitemsTemplateServizi(
			Timestamp dataInizioFiliera) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.join(releaseServizi)
					.on(releaseServizi.cdRelServizi.eq(link.codiceWiPadre)
						.and(releaseServizi.idRepository.eq(link.idRepositoryPadre)))
					.where(link.tipoWiPadre.eq("release_ser"))
					.where(link.tipoWiFiglio.eq("task"))
					.where(link.ruolo.isNotNull())
					.where(releaseServizi.rankStatoRelServizi.eq(new Double("1")))
					.where(releaseServizi.dtCreazioneRelServizi.goe(dataInizioFiliera))
					.orderBy(link.idRepositoryPadre.asc())
					.orderBy(link.fkWiPadre.asc())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class, link.all()));
			
			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<DmalmLinkedWorkitems> getNextWorkitemsTemplateServizi(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.where(link.fkWiPadre.eq(linkedWorkitem.getFkWiFiglio()))
					.where(link.tipoWiPadre.in("task"))
					.where(link.ruolo.isNotNull())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<DmalmLinkedWorkitems> getStartWorkitemsTemplateAssFunzionale(
			Timestamp dataInizioFiliera) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.join(anomaliaAssistenza)
					.on(anomaliaAssistenza.cdAnomaliaAss.eq(link.codiceWiPadre)
						.and(anomaliaAssistenza.idRepository.eq(link.idRepositoryPadre)))
					.where(link.tipoWiPadre.eq("anomalia_assistenza"))
					.where(link.tipoWiFiglio.eq("richiesta_gestione"))
					.where(link.ruolo.isNotNull())
					.where(anomaliaAssistenza.rankStatoAnomaliaAss.eq(new Double("1")))
					.where(anomaliaAssistenza.dtCreazioneAnomaliaAss.goe(dataInizioFiliera))
					.orderBy(link.idRepositoryPadre.asc())
					.orderBy(link.fkWiPadre.asc())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class, link.all()));
			
			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<DmalmLinkedWorkitems> getNextWorkitemsTemplateAssFunzionale(
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmLinkedWorkitems> resultList = new LinkedList<DmalmLinkedWorkitems>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			resultList = query
					.from(link)
					.where(link.fkWiPadre.eq(linkedWorkitem.getFkWiFiglio()))
					.where(link.tipoWiPadre.in("richiesta_gestione"))
					.where(link.ruolo.isNotNull())
					.orderBy(link.fkWiFiglio.asc())
					.list(Projections.bean(DmalmLinkedWorkitems.class,
							link.all()));

			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
	
	public static List<Integer> getFkDocumento(
			Integer linkedWorkitem) throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> resultList = new LinkedList<Integer>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);
			
			resultList = query
					.from(link)
					.where(link.fkWiPadre.eq(linkedWorkitem))
					.where(link.tipoWiFiglio.eq("documento"))
					.where(link.ruolo.isNotNull())
					.orderBy(link.fkWiFiglio.asc())
					.list(link.fkWiFiglio);
		
			
			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultList;
	}
}
