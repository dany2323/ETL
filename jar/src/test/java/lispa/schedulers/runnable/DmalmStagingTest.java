package lispa.schedulers.runnable;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLUpdateClause;

import junit.framework.TestCase;
import lispa.schedulers.bean.target.DmalmProjectUnitaOrganizzativaEccezioni;
import lispa.schedulers.bean.target.fatti.DmalmClassificatore;
import lispa.schedulers.bean.target.fatti.DmalmReleaseDiProgetto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.dao.target.ProjectUnitaOrganizzativaEccezioniDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeDAO;
import lispa.schedulers.dao.target.fatti.ClassificatoreDAO;
import lispa.schedulers.dao.target.fatti.ReleaseDiProgettoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckProjectStorFacade;
import lispa.schedulers.facade.elettra.target.ElettraUnitaOrganizzativeFacade;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaProjectElFacade;
import lispa.schedulers.facade.sfera.staging.StgMisuraFacade;
import lispa.schedulers.facade.sfera.target.AsmFacade;
import lispa.schedulers.facade.sfera.target.MisuraFacade;
import lispa.schedulers.facade.sfera.target.ProgettoSferaFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateSviluppoFacade;
import lispa.schedulers.facade.target.SvecchiamentoFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.manager.RecoverManager;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdotto;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmProgettoSfera;
import lispa.schedulers.utils.DateUtils;

/**
 * Provvede al caricamento delle tabelle di staging
 * -DMALM_STG_UNITA_ORGANIZZATIVA -DMALM_STG_PERSONALE
 * -DMALM_STG_AMBIENTE_TECNOLOGICO -DMALM_STG_CLASSIFICATORI
 * -DMALM_STG_FUNZIONALITA -DMALM_STG_MODULI -DMALM_STG_PROD_ARCHITETTURE
 * -DMALM_STG_SOTTOSISTEMI con i dati presenti nelle relative fonti presenti
 * alla data di elaborazione
 **/

public class DmalmStagingTest extends TestCase {

	private static Logger logger = Logger.getLogger(DmalmStagingTest.class);

	private static int days;

	public void testDeleteHistoryWorkitem() throws PropertiesReaderException {
		Log4JConfiguration.inizialize();

		logger.debug("START testDeleteHistoryWorkitem");

		try {
			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2016-08-05 10:00:00",
							"yyyy-MM-dd HH:mm:00"));

			// Cancello i record piu vecchi di X giorni
			Timestamp dataEsecuzioneDeleted = DateUtils.stringToTimestamp("2016-07-01 10:00:00", "yyyy-MM-dd HH:mm:00");

			logger.debug("START SireHistoryWorkitemDAO.delete");
			SireHistoryWorkitemDAO.delete(dataEsecuzioneDeleted);
			
			logger.debug("START SissHistoryWorkitemDAO.delete");
			SissHistoryWorkitemDAO.delete(dataEsecuzioneDeleted);

			logger.debug("START testDeleteHistoryWorkitem");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testReleaseDiProgetto() throws PropertiesReaderException {
		Log4JConfiguration.inizialize();

		logger.info("START testReleaseDiProgetto");

		List<Tuple> target_releases = new ArrayList<Tuple>();

		DataEsecuzione.getInstance().setDataEsecuzione(
				DateUtils.stringToTimestamp("2016-06-07 23:45:00",
						"yyyy-MM-dd HH:mm:00"));

		try {
			List<DmalmReleaseDiProgetto> staging_releases = ReleaseDiProgettoDAO
					.getAllReleaseDiProgetto(DataEsecuzione.getInstance()
							.getDataEsecuzione());

			for (DmalmReleaseDiProgetto release : staging_releases) {
				target_releases = ReleaseDiProgettoDAO
						.getReleaseDiProgetto(release);

				for (Tuple row : target_releases) {
					if (row != null) {
						if (release.getDtInizioQF() != null) {
							ReleaseDiProgettoDAO
									.updateReleaseDiProgetto(release);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		logger.info("STOP testReleaseDiProgetto");
	}

	public void testSISSHistoryUserRoles() throws DAOException,
			PropertiesReaderException {
		Log4JConfiguration.inizialize();

		logger.info("*** Eseguo Test DmalmStagingTest.testSISSHistoryUserRoles **** ");

		logger.info("SISSUserRolesXML.fillSISSHistoryUserRoles()");

		//SISSUserRolesXML.fillSISSHistoryUserRoles();
	}

	public void testCfWorkitem() throws Exception {
		Log4JConfiguration.inizialize();

		logger.info("*** Eseguo testCfWorkitem ***");

		DataEsecuzione.getInstance().setDataEsecuzione(
				DateUtils.stringToTimestamp("2016-05-25 23:45:00",
						"yyyy-MM-dd HH:mm:00"));

		List<DmalmClassificatore> stg_class = new ArrayList<DmalmClassificatore>();
		stg_class = ClassificatoreDAO.getAllClassDem(DataEsecuzione
				.getInstance().getDataEsecuzione());
		logger.info("stg_class.size: " + stg_class.size());

		logger.info("*** Fine testCfWorkitem ***");
	}

	public void testSubquery() throws DAOException, PropertiesReaderException {
		Log4JConfiguration.inizialize();

		logger.debug("START testSubquery");

		ConnectionManager cm = null;
		Connection connection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		QDmalmAnomaliaProdotto anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
		QDmalmAnomaliaProdotto anomMax = new QDmalmAnomaliaProdotto("anomMax");

		List<Tuple> strutture = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			// strutture = query.from(anomalia)
			// .where(anomalia.dmalmProjectFk02.eq(15348))
			// .where(anomalia.dtStoricizzazione
			// .in(new SQLSubQuery()
			// .from(anomMax)
			// .where(anomMax.dmalmProjectFk02.eq(15348).and(anomMax.cdAnomalia.eq(anomalia.cdAnomalia)))
			// .list(anomMax.dtStoricizzazione.max())))
			// .list(anomalia.all());

			strutture = query
					.from(anomalia)
					.where(anomalia.dmalmProjectFk02.eq(15348))
					.where(anomalia.dtStoricizzazione.in(new SQLSubQuery()
							.from(anomMax)
							.where(anomalia.dmalmProjectFk02
									.eq(anomMax.dmalmProjectFk02))
							.where(anomalia.cdAnomalia.eq(anomMax.cdAnomalia))
							.list(anomMax.dtStoricizzazione.max())))
					.list(anomalia.all());

			logger.debug("listra anomalia: " + strutture.size());

			for (Tuple row : strutture) {
				if (row != null) {
					logger.debug("anomalia: "
							+ row.get(anomalia.dmalmAnomaliaProdottoPk)
							+ ", dtStoricizzazione: "
							+ row.get(anomalia.dtStoricizzazione));
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		logger.debug("STOP testSubquery");
	}

	public void testProjectSgrCmFacade() throws DAOException,
			PropertiesReaderException {

		try {
			Log4JConfiguration.inizialize();
			logger.info("*** Eseguo Test testProjectSgrCmFacade ***");

			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2016-03-18 10:00:00.0",
							"yyyy-MM-dd HH:mm:00"));

			SireHistoryProjectDAO.fillSireHistoryProject(912777, 923058);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testSwitch() throws DAOException, PropertiesReaderException {
		try {
			Log4JConfiguration.inizialize();

			logger.info("*** Eseguo Test testSwitch ***");

			QDmalmProject proj = QDmalmProject.dmalmProject;
			
			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2016-08-01 09:00:00.0",
							"yyyy-MM-dd HH:mm:00"));

			// verifica delle UO per i Project non scaricati in History
			List<Tuple> listaProgettiNonMovimentati = ProjectSgrCmDAO
					.getAllProjectNotInHistory(DataEsecuzione.getInstance()
							.getDataEsecuzione());

			logger.debug("listaProgettiNonMovimentati.size: "
					+ listaProgettiNonMovimentati.size());

			for (Tuple row : listaProgettiNonMovimentati) {
				if (row != null) {
					switch (row.get(proj.cTemplate)) {
					case DmAlmConstants.SVILUPPO:
						// Template SVILUPPO

						break;

					case DmAlmConstants.DEMAND:
					case DmAlmConstants.DEMAND2016:
						// Template DEMAND e DEMAND2016
						logger.debug("template: " + row.get(proj.cTemplate));
						break;

					case DmAlmConstants.ASSISTENZA:
						// Template ASSISTENZA

						break;

					case DmAlmConstants.IT:
						// Template IT (sempre 'LIA352' - Area Integrazione
						// Tecnica)

						break;

					case DmAlmConstants.SERDEP:
						// Template SERDEP

						break;

					default:
						// Template non gestito

						break;
					}
				}
			}

			logger.info("*** Fine Test testSwitch ***");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testDeleteStaging() throws DAOException,
			PropertiesReaderException {
		try {
			Log4JConfiguration.inizialize();

			logger.info("*** Eseguo Test testDeleteStaging ***");

			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2017-11-16 20:45:00.0",
							"yyyy-MM-dd HH:mm:00"));

			RecoverManager.getInstance().startRecoverStaging();

			logger.info("*** Fine Test testDeleteStaging ***");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testString() throws Exception {
		try {
			Log4JConfiguration.inizialize();

			String pAppIndicValidazioneAsm = "2";

			String controlloAsm = pAppIndicValidazioneAsm == null ? "0"
					: pAppIndicValidazioneAsm == "00" ? "0"
							: pAppIndicValidazioneAsm;

			if (!controlloAsm.equalsIgnoreCase("0")
					&& !controlloAsm.equalsIgnoreCase("1")) {
				logger.debug("errore: " + controlloAsm);
			} else
				logger.debug("OK: " + controlloAsm);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testExecuteMultipleStatementsFromFile() throws Exception {
		try {
			Log4JConfiguration.inizialize();

			logger.debug("START testExecuteMultipleStatementsFromFile");

			ExecutionManager.getInstance().setExecutionElettraSgrcm(true);

			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2016-07-15 10:00:00.0",
							"yyyy-MM-dd HH:mm:00"));

			// DMALM-191 associazione project Unità Organizzativa Flat
			// ricarica il valore della Fk ad ogni esecuzione
			try {
				logger.info("INIZIO Update Project UnitaOrganizzativaFlatFk");

				QueryManager qm = QueryManager.getInstance();

				qm.executeMultipleStatementsFromFile(
						DmAlmConstants.M_UPDATE_PROJECT_UOFLATFK,
						DmAlmConstants.M_SEPARATOR);

				logger.info("FINE Update Project UnitaOrganizzativaFlatFk");
			} catch (Exception e) {
				// non viene emesso un errore bloccante in quanto la Fk è
				// recuperabile dopo l'esecuzione
				logger.error("ERRORE: " + e.getMessage(), e);
			}

			logger.debug("STOP testExecuteMultipleStatementsFromFile");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testSvecchiamentoFacade() throws Exception {
		try {
			Log4JConfiguration.inizialize();

			ExecutionManager.getInstance().setExecutionElettraSgrcm(true);

			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2016-06-24 10:00:00.0",
							"yyyy-MM-dd HH:mm:00"));

			logger.debug("START testSvecchiamentoFacade");

			SvecchiamentoFacade.execute();

			logger.debug("STOP testSvecchiamentoFacade");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testFilieraTemplateSviluppo() throws Exception {
		try {
			Log4JConfiguration.inizialize();

			logger.debug("START testFilieraTemplateSviluppo");

			ExecutionManager.getInstance().setExecutionElettraSgrcm(true);

			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2016-07-21 23:45:00.0",
							"yyyy-MM-dd HH:mm:00"));

			CostruzioneFilieraTemplateSviluppoFacade.execute();

			logger.debug("STOP testFilieraTemplateSviluppo");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testSfera() throws Exception {
		try {
			Log4JConfiguration.inizialize();
			logger.debug("START testSfera");

			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2016-07-15 10:00:00.0",
							"yyyy-MM-dd HH:mm:00"));
			Timestamp dataEsecuzioneDeleted = DateUtils.getAddDayToDate(-days);

			if (ExecutionManager.getInstance().isExecutionSfera()) {
				StgMisuraFacade.deleteStgMisura(logger, dataEsecuzioneDeleted);
				StgMisuraFacade.fillStgMisura();
			}

			AsmFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());

			ProgettoSferaFacade.execute(DataEsecuzione.getInstance()
					.getDataEsecuzione());

			MisuraFacade.execute(DataEsecuzione.getInstance()
					.getDataEsecuzione());

			logger.debug("STOP testSfera");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void updateDataFineValiditaProgettoSfera(
			Timestamp dataFineValidita, Integer projectPk) throws DAOException

	{
		SQLTemplates dialect = new HSQLDBTemplates();
		QDmalmProgettoSfera prog = QDmalmProgettoSfera.dmalmProgettoSfera;
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			logger.debug("   updateDataFineValiditaProgettoSfera ProgettoPk: "
					+ projectPk + ", dataFineValidita: "
					+ DateUtils.addDaysToTimestamp(dataFineValidita, -1));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, prog)
					.where(prog.dmalmProgettoSferaPk.eq(projectPk))
					.set(prog.dataFineValidita,
							DateUtils.addDaysToTimestamp(dataFineValidita, -1))
					.execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public void testUtils() throws Exception {
		try {
			Log4JConfiguration.inizialize();
			logger.debug("START testUtils");

			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2016-03-15 21:00:00.0",
							"yyyy-MM-dd HH:mm:00"));

			logger.debug("DataEsecuzione: "
					+ DataEsecuzione.getInstance().getDataEsecuzione());
			logger.debug("DateUtils.addDaysToTimestamp: "
					+ DateUtils.addDaysToTimestamp(DataEsecuzione.getInstance()
							.getDataEsecuzione(), -1));
			logger.debug("DateUtils.addSecondsToTimestamp: "
					+ DateUtils.addSecondsToTimestamp(DataEsecuzione
							.getInstance().getDataEsecuzione(), -1));

			logger.debug("STOP testUtils");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testLinkAsmSferaProjectEl() throws Exception {
		try {
			Log4JConfiguration.inizialize();

			logger.debug("START testLinkAsmSferaProjectEl");

			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2016-07-17 23:45:00",
							"yyyy-MM-dd HH:mm:00"));

			CheckLinkAsmSferaProjectElFacade.execute();

			logger.debug("STOP testLinkAsmSferaProjectEl");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testElettraUnitaOrganizzativeFlat() throws Exception {
		try {
			Log4JConfiguration.inizialize();

			logger.debug("START testElettraUnitaOrganizzativeFlat");

			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2016-07-15 09:00:00.0",
							"yyyy-MM-dd HH:mm:00"));

			ElettraUnitaOrganizzativeFacade.execute(DataEsecuzione
					.getInstance().getDataEsecuzione());

			logger.debug("STOP testElettraUnitaOrganizzativeFlat");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	public void testProgettiNonMovimentati() throws Exception {
		try {
			Log4JConfiguration.inizialize();

			logger.debug("START testProgettiNonMovimentati");

			QDmalmProject proj = QDmalmProject.dmalmProject;

			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2016-07-14 09:00:00.0",
							"yyyy-MM-dd HH:mm:00"));

			// verifica delle UO per i Project non scaricati in History
			List<Tuple> listaProgettiNonMovimentati = ProjectSgrCmDAO
					.getAllProjectNotInHistory(DataEsecuzione.getInstance()
							.getDataEsecuzione());

			logger.debug("listaProgettiNonMovimentati.size: "
					+ listaProgettiNonMovimentati.size());

			// lista delle eccezioni Project/Unita organizzativa
			List<DmalmProjectUnitaOrganizzativaEccezioni> eccezioniProjectUO = ProjectUnitaOrganizzativaEccezioniDAO
					.getAllProjectUOException();

			// Integer strutturaOrgFk02;
			Integer unitaOrganizzativaFk;

			for (Tuple row : listaProgettiNonMovimentati) {
				if (row != null) {
					// Elettra
					// FK Unità Organizzativa
					String codiceAreaUOElettra = ProjectSgrCmDAO
							.gestioneCodiceAreaUO(eccezioniProjectUO, row
									.get(proj.idProject), row
									.get(proj.idRepository), row
									.get(proj.nomeCompletoProject), row
									.get(proj.cTemplate), row
									.get(proj.fkProjectgroup), DataEsecuzione
									.getInstance().getDataEsecuzione(), true);

					if (codiceAreaUOElettra.equals(DmAlmConstants.NON_PRESENTE)) {
						unitaOrganizzativaFk = 0;
					} else {
						Map<Timestamp, Integer> map = ElettraUnitaOrganizzativeDAO
								.getUnitaOrganizzativaByCodiceArea(
										codiceAreaUOElettra, DataEsecuzione
										.getInstance()
										.getDataEsecuzione());
						unitaOrganizzativaFk=0;
						for(Timestamp t:map.keySet()) {
							unitaOrganizzativaFk = map.get(t);
						}
					}

					logger.debug("update dmalm_project set DMALM_UNITAORGANIZZATIVA_FK_03 = "
							+ unitaOrganizzativaFk
							+ " where DMALM_PROJECT_PK = "
							+ row.get(proj.dmalmProjectPrimaryKey) + ";");
				}
			}

			logger.debug("STOP testProgettiNonMovimentati");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void testStorTotale() throws Exception {
		Log4JConfiguration.inizialize();
		logger.info("*** Eseguo testStorTotale **** ");
		QDmalmProject p = QDmalmProject.dmalmProject;
		ConnectionManager cm = null;
		Connection connection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		List<Timestamp> lista = new ArrayList<Timestamp>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			lista = query.from(p).distinct().orderBy(p.dtCaricamento.asc())
					.list(p.dtCaricamento);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (lista.size() > 0) {
			logger.debug("Trovate " + lista.size());
		}

		for (Timestamp t : lista) {
			logger.debug("Check storicizzazione per " + t.toString());
			DataEsecuzione.getInstance().setDataEsecuzione(t);
			CheckProjectStorFacade.execute();

		}
		logger.info("*** Fine testStorTotale **** ");
	}
}
