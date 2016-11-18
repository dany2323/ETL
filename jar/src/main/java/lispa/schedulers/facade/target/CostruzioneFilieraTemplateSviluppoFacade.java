package lispa.schedulers.facade.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto;
import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.bean.target.fatti.DmalmManutenzione;
import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoSvil;
import lispa.schedulers.bean.target.fatti.DmalmReleaseDiProgetto;
import lispa.schedulers.bean.target.fatti.DmalmTask;
import lispa.schedulers.bean.target.fatti.DmalmTestcase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.FilieraTemplateDocumentiDAO;
import lispa.schedulers.dao.target.FilieraTemplateSviluppoDAO;
import lispa.schedulers.dao.target.LinkedWorkitemsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.queryimplementation.target.QDmalmFilieraTemplateSviluppo;
import lispa.schedulers.utils.DateUtils;

/***
 * Elaborazione post Target: DM_ALM-191 Revisione universo partendo dalla
 * tabella LINKED_WORKITEMS tipologie di workitem da prendere in considerazione
 * per il template Demand: 
 * a. Progetto sviluppo sviluppo (srqs) 
 * b. Manutenzione (sman) 
 * c. Release di progetto (release) 
 * d. Testcase (testcase) 
 * e. Task (task)
 * f. Anomalia (anomalia) 
 * g. Difetto (defect) 
 * x. Documento (documento)
 */

public class CostruzioneFilieraTemplateSviluppoFacade {

	private static Logger logger = Logger
			.getLogger(CostruzioneFilieraTemplateServiziFacade.class);

	public static void execute() {
		
		try {
			// ELETTRA/SGRCM
			if (!ExecutionManager.getInstance().isExecutionElettraSgrcm())
				return;

			// Se si è verificato un errore precedente non eseguo l'elaborazione
			if (ErrorManager.getInstance().hasError()) {
				return;
			}

			logger.info("START CostruzioneFilieraTemplateSviluppoFacade.execute()");

			Integer idFiliera = 0;

			// data inizio filiera: primo Gennaio del 1900
			
			Timestamp dataInizioFiliera = DateUtils.setDtInizioValidita1900();;

			logger.info("CostruzioneFilieraTemplateSviluppoFacade - dataInizioFiliera: "
					+ dataInizioFiliera);

			List<DmalmLinkedWorkitems> insertedWorkitemsList = new LinkedList<DmalmLinkedWorkitems>();

			// ad ogni esecuzione la filiera del template Sviluppo è svuotata e
			// ricaricata
			// nuovamente
			FilieraTemplateSviluppoDAO.deleteTemplateSviluppo();
			FilieraTemplateDocumentiDAO.delete(DmAlmConstants.SVILUPPO);

			// creazione filiera template Sviluppo
			List<DmalmLinkedWorkitems> startWorkitemsList = LinkedWorkitemsDAO
					.getStartWorkitemsTemplateSviluppo(dataInizioFiliera, "srqs", "release");
			logger.debug("CostruzioneFilieraTemplateSviluppoFacade - lista.size: "
					+ startWorkitemsList.size());

			if (startWorkitemsList.size() > 0) {
				idFiliera = gestisciLista(idFiliera, startWorkitemsList,
						insertedWorkitemsList);
			}
			
			
			// creazione secondo livello filiera template Sviluppo
			startWorkitemsList = LinkedWorkitemsDAO
					.getStartWorkitemsTemplateSviluppo(dataInizioFiliera, "sman", "release");
			logger.debug("CostruzioneFilieraTemplateSviluppoFacade - lista.size: "
					+ startWorkitemsList.size());

			if (startWorkitemsList.size() > 0) {
				idFiliera = gestisciLista(idFiliera, startWorkitemsList,
						insertedWorkitemsList);
			}
			
			// creazione altro secondo livello filiera template Sviluppo
			startWorkitemsList = LinkedWorkitemsDAO
					.getStartWorkitemsTemplateSviluppo(dataInizioFiliera, "release", "anomalia");
			logger.debug("CostruzioneFilieraTemplateSviluppoFacade - lista.size: "
					+ startWorkitemsList.size());

			if (startWorkitemsList.size() > 0) {
				idFiliera = gestisciLista(idFiliera, startWorkitemsList,
						insertedWorkitemsList);
			}
			
			// creazione altro secondo livello filiera template Sviluppo
			startWorkitemsList = LinkedWorkitemsDAO
					.getStartWorkitemsTemplateSviluppo(dataInizioFiliera, "release", "defect");
			logger.debug("CostruzioneFilieraTemplateSviluppoFacade - lista.size: "
					+ startWorkitemsList.size());

			if (startWorkitemsList.size() > 0) {
				idFiliera = gestisciLista(idFiliera, startWorkitemsList,
						insertedWorkitemsList);
			}
			
			//Gestisci i Work Item orfani
			getWorkItemSviluppoFuoriFiliera(dataInizioFiliera);
			

			logger.info("STOP CostruzioneFilieraTemplateSviluppoFacade.execute()");
		} catch (DAOException de) {
			logger.error(de.getMessage(), de);

			ErrorManager.getInstance().exceptionOccurred(true, de);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
	
	private static void getWorkItemSviluppoFuoriFiliera(Timestamp dataInizioFiliera) throws PropertiesReaderException, Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		
		String srqsQuery = "select * from DMALM_PROGETTO_SVILUPPO_SVIL"
				+ "where ID_REPOSITORY || URI_PROGETTO_SVILUPPO_SVIL not in "
				+ "(SELECT ID_REPOSITORY || URI_WI from DMALM_TEMPLATE_SVILUPPO);";
		String relQuery = "select * from DMALM_RELEASE_DI_PROGETTO"
				+ "where ID_REPOSITORY || URI_RELEASE_DI_PROGETTO not in "
				+ "(SELECT ID_REPOSITORY || URI_WI from DMALM_TEMPLATE_SVILUPPO);";
		String smanQuery = "select * from DMALM_MANUTENZIONE"
				+ "where ID_REPOSITORY || URI_MANUTENZIONE not in "
				+ "(SELECT ID_REPOSITORY || URI_WI from DMALM_TEMPLATE_SVILUPPO);";
		String anomaliaQuery = "select * from DMALM_ANOMALIA_PRODOTTO"
				+ "where ID_REPOSITORY || URI_ANOMALIA_PRODOTTO not in "
				+ "(SELECT ID_REPOSITORY || URI_WI from DMALM_TEMPLATE_SVILUPPO);";
		String taskQuery = "select * from DMALM_TASK"
				+ "where ID_REPOSITORY || URI_TASK not in "
				+ "(SELECT ID_REPOSITORY || URI_WI from DMALM_TEMPLATE_SVILUPPO);";
		String testQuery = "select * from DMALM_TESTCASE"
				+ "where ID_REPOSITORY || URI_TESTCASE not in "
				+ "(SELECT ID_REPOSITORY || URI_WI from DMALM_TEMPLATE_SVILUPPO);";
		String defectQuery = "select * from DMALM_DIFETTO_PRODOTTO"
				+ "where ID_REPOSITORY || URI_DIFETTO_PRODOTTO not in "
				+ "(SELECT ID_REPOSITORY || URI_WI from DMALM_TEMPLATE_SVILUPPO);";

		
		List<DmalmProgettoSviluppoSvil> resultListSrqs = new LinkedList<DmalmProgettoSviluppoSvil>();
		List<DmalmReleaseDiProgetto> resultListRelease = new LinkedList<DmalmReleaseDiProgetto>();
		List<DmalmManutenzione> resultListManutenzione = new LinkedList<DmalmManutenzione>();
		List<DmalmAnomaliaProdotto> resultListAnomalia = new LinkedList<DmalmAnomaliaProdotto>();
		List<DmalmTask> resultListTask = new LinkedList<DmalmTask>();
		List<DmalmTestcase> resultListTestCase = new LinkedList<DmalmTestcase>();
		List<DmalmDifettoProdotto> resultListDefect = new LinkedList<DmalmDifettoProdotto>();


		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			ps = connection.prepareStatement(srqsQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				DmalmProgettoSviluppoSvil sqrs = new DmalmProgettoSviluppoSvil();
				sqrs.setCdProgSvilS(rs.getString("CD_PROG_SVIL_S"));
				sqrs.setDmalmProgSvilSPk(rs.getInt("DMALM_PROG_SVIL_S_PK"));
				sqrs.setIdRepository(rs.getString("ID_REPOSITORY"));
				sqrs.setUri(rs.getString("URI_PROGETTO_SVILUPPO_SVIL"));
				sqrs.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				resultListSrqs.add(sqrs);
			}
			
			ps = connection.prepareStatement(relQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				DmalmReleaseDiProgetto release = new DmalmReleaseDiProgetto();
				release.setCdReleasediprog(rs.getString("CD_RELEASEDIPROG"));
				release.setDmalmReleasediprogPk(rs.getInt("DMALM_RELEASEDIPROG_PK"));
				release.setIdRepository(rs.getString("ID_REPOSITORY"));
				release.setUri(rs.getString("URI_RELEASE_DI_PROGETTO"));
				release.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				resultListRelease.add(release);
			}
			
			ps = connection.prepareStatement(smanQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				DmalmManutenzione man = new DmalmManutenzione();
				man.setCdManutenzione(rs.getString("CD_MANUTENZIONE"));
				man.setDmalmManutenzionePk(rs.getInt("DMALM_MANUTENZIONE_PK"));
				man.setIdRepository(rs.getString("ID_REPOSITORY"));
				man.setUri(rs.getString("URI_MANUTENZIONE"));
				man.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				resultListManutenzione.add(man);
			}
			
			ps = connection.prepareStatement(anomaliaQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				DmalmAnomaliaProdotto anomalia = new DmalmAnomaliaProdotto();
				anomalia.setCdAnomalia(rs.getString("CD_ANOMALIA"));
				anomalia.setDmalmAnomaliaProdottoPk(rs.getInt("DMALM_ANOMALIA_PRODOTTO_PK"));
				anomalia.setIdRepository(rs.getString("ID_REPOSITORY"));
				anomalia.setUri(rs.getString("URI_ANOMALIA_PRODOTTO"));
				anomalia.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				resultListAnomalia.add(anomalia);
			}
			
			ps = connection.prepareStatement(taskQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				DmalmTask task = new DmalmTask();
				task.setCdTask(rs.getString("CD_TASK"));
				task.setDmalmTaskPk(rs.getInt("DMALM_TASK_PK"));
				task.setIdRepository(rs.getString("ID_REPOSITORY"));
				task.setUri(rs.getString("URI_TASK"));
				task.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				resultListTask.add(task);
			}
			
			ps = connection.prepareStatement(testQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				DmalmTestcase test = new DmalmTestcase();
				test.setCdTestcase(rs.getString("CD_TESTCASE"));
				test.setDmalmTestcasePk(rs.getInt("DMALM_TESTCASE_PK"));
				test.setIdRepository(rs.getString("ID_REPOSITORY"));
				test.setUri(rs.getString("URI_TESTCASE"));
				test.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				resultListTestCase.add(test);
			}
			
			ps = connection.prepareStatement(defectQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				DmalmDifettoProdotto defect = new DmalmDifettoProdotto();
				defect.setCdDifetto(rs.getString("CD_DIFETTO"));
				defect.setDmalmDifettoProdottoPk(rs.getInt("DMALM_DIFETTO_PRODOTTO_PK"));
				defect.setIdRepository(rs.getString("ID_REPOSITORY"));
				defect.setUri(rs.getString("URI_DIFETTO_PRODOTTO"));
				defect.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				resultListDefect.add(defect);
			}
			
			
			// Ora inserisco i Bean delle liste nella tabella DMALM_TEMPLATE_SVILUPPO
			
			QDmalmFilieraTemplateSviluppo filiera = QDmalmFilieraTemplateSviluppo.dmalmFilieraTemplateSviluppo;
			SQLTemplates dialect = new HSQLDBTemplates();
			connection.setAutoCommit(false);
			
			for(DmalmProgettoSviluppoSvil srqs: resultListSrqs){
				new SQLInsertClause(connection, dialect, filiera)
				.columns(filiera.idFiliera,
						filiera.livello,
						filiera.sottoLivello,
						filiera.fkWi, 
						filiera.codiceWi,
						filiera.tipoWi,
						filiera.idRepository,
						filiera.uriWi,
						filiera.codiceProject,
						filiera.ruolo,
						filiera.dataCaricamento)
				.values(0, 
						1, 
						1,
						srqs.getDmalmProgSvilSPk(),
						srqs.getCdProgSvilS(),
						"srqs",
						srqs.getIdRepository(),
						srqs.getUri(),
						getDmalmCodiceProgetto(srqs.getDmalmProjectFk02()),
						"",
						DataEsecuzione.getInstance().getDataEsecuzione())
				.execute();
			}
			
			
			/*SQLQuery query = new SQLQuery(connection, dialect);
			
			QDmalmFilieraTemplateSviluppo filieraSviluppo = QDmalmFilieraTemplateSviluppo.dmalmFilieraTemplateSviluppo;
			
			QDmalmProgettoSviluppoSvil progettoSviluppoSvil = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
			QDmalmReleaseDiProgetto releaseDiProgetto = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
			QDmalmManutenzione manutenzione = QDmalmManutenzione.dmalmManutenzione;
			QDmalmAnomaliaProdotto anomaliaProdotto = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
			QDmalmDifettoProdotto difettoProdotto = QDmalmDifettoProdotto.dmalmDifettoProdotto;
			QDmalmTask task = QDmalmTask.dmalmTask;
			QDmalmTestcase testCase = QDmalmTestcase.dmalmTestcase;

			resultListSrqs = query
					.from(progettoSviluppoSvil)
					.join(filieraSviluppo)
						.on(progettoSviluppoSvil.uri.eq(filieraSviluppo.uriWi)
						.and(progettoSviluppoSvil.idRepository.eq(filieraSviluppo.idRepository)))
					
					.where(progettoSviluppoSvil.rankStatoProgSvilS.eq(new Double("1")))
					.where(progettoSviluppoSvil.dtCreazioneProgSvilS.goe(dataInizioFiliera))
					.list(Projections.bean(DmalmProgettoSviluppoSvil.class, progettoSviluppoSvil.all()));
			resultListRelease = query
					.from(releaseDiProgetto)
					.where(releaseDiProgetto.rankStatoReleasediprog.eq(new Double("1")))
					.where(releaseDiProgetto.dtCreazioneReleasediprog.goe(dataInizioFiliera))
					.list(Projections.bean(DmalmReleaseDiProgetto.class, releaseDiProgetto.all()));
			resultListManutenzione = query	
					.from(manutenzione)
					.where(manutenzione.rankStatoManutenzione.eq(new Double("1")))
					.where(manutenzione.dtCreazioneManutenzione.goe(dataInizioFiliera))
					.list(Projections.bean(DmalmManutenzione.class, manutenzione.all()));
			resultListAnomalia = query
					.from(anomaliaProdotto)
					.where(anomaliaProdotto.rankStatoAnomalia.eq(new Double("1")))
					.where(anomaliaProdotto.dtCreazioneAnomalia.goe(dataInizioFiliera))
					.list(Projections.bean(DmalmAnomaliaProdotto.class, anomaliaProdotto.all()));
			resultListTask = query
					.from(task)
					.where(task.rankStatoTask.eq(new Double("1")))
					.where(task.dtCreazioneTask.goe(dataInizioFiliera))
					.list(Projections.bean(DmalmTask.class, task.all()));
			resultListTestCase = query
					.from(testCase)
					.where(testCase.rankStatoTestcase.eq(new Double("1")))
					.where(testCase.dtCreazioneTestcase.goe(dataInizioFiliera))
					.list(Projections.bean(DmalmTestcase.class, testCase.all()));
			*/

			
			connection.commit();
		} catch (Exception e) {

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		
	}

	private static String getDmalmCodiceProgetto(Integer dmalmProjectFk02) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Integer gestisciLista(Integer idFiliera,
			List<DmalmLinkedWorkitems> linkedWorkitems,
			List<DmalmLinkedWorkitems> insertedWorkitemsList) throws Exception {

		for (DmalmLinkedWorkitems linkedWorkitem : linkedWorkitems) {
			List<DmalmLinkedWorkitems> nextWorkitemsList = new LinkedList<DmalmLinkedWorkitems>();

			// se il figlio è già presente nella catena non cerco ulteriori
			// figli per evitare cicli infiniti
			if (!figlioPresente(insertedWorkitemsList, linkedWorkitem)) {
				nextWorkitemsList = LinkedWorkitemsDAO
						.getNextWorkitemsTemplateSviluppo(linkedWorkitem, linkedWorkitem.getFkWiFiglio());
			}

			if (nextWorkitemsList.size() > 0) {
				// se ho dei figli salvo il wi nella lista degli inseriti e
				// continuo il ciclo
				insertedWorkitemsList.add(linkedWorkitem);

				idFiliera = gestisciLista(idFiliera, nextWorkitemsList,
						insertedWorkitemsList);

				// tolgo l'item dalla lista per non averlo in un ramo diverso
				// dal suo
				insertedWorkitemsList.remove(linkedWorkitem);
			} else {
				// altrimenti se non ho figli inserisco la lista e il wi corrente
				// senza salvarlo nella lista in una nuova filiera
				idFiliera++;
				inserisciLista(idFiliera, insertedWorkitemsList,
						linkedWorkitem);
			}
		}

		return idFiliera;
	}
	
	private static boolean figlioPresente(
			List<DmalmLinkedWorkitems> insertedWorkitemsList,
			DmalmLinkedWorkitems linkedWorkitem) throws Exception {
		boolean presente = false;

		for (DmalmLinkedWorkitems insertedWorkitem : insertedWorkitemsList) {
			if (linkedWorkitem.getFkWiFiglio().equals(
					insertedWorkitem.getFkWiFiglio())
					|| linkedWorkitem.getFkWiFiglio().equals(
							insertedWorkitem.getFkWiPadre())) {

				presente = true;
				break;
			}
		}

		return presente;
	}

	private static void inserisciLista(Integer idFiliera,
			List<DmalmLinkedWorkitems> insertedWorkitemsList,
			DmalmLinkedWorkitems lastWorkitem) throws Exception {

		Integer livello = 0;
		Integer sottoLivello = 1;
		String tipoWiPrecedente = "";

		for (DmalmLinkedWorkitems insertedWorkitem : insertedWorkitemsList) {
			if (insertedWorkitem.getTipoWiPadre().equalsIgnoreCase(
					tipoWiPrecedente))
				sottoLivello++;
			else {
				livello++;
				sottoLivello = 1;
			}
			
			FilieraTemplateSviluppoDAO.insert(idFiliera, livello, sottoLivello,
					false, insertedWorkitem);

			tipoWiPrecedente = insertedWorkitem.getTipoWiPadre();
		}

		livello++;

		if (lastWorkitem.getTipoWiPadre().equalsIgnoreCase(tipoWiPrecedente))
			sottoLivello++;
		else
			sottoLivello = 1;

		
		FilieraTemplateSviluppoDAO.insert(idFiliera, livello, sottoLivello,
				true, lastWorkitem);
	}

}
