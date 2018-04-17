package lispa.sgr;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_RETRY;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import junit.framework.TestCase;
import lispa.schedulers.action.DmAlmETL;
import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.dao.target.DifettoProdottoOdsDAO;
import lispa.schedulers.dao.target.fatti.DifettoDAO;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDifettoProdotto;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.EnumUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

public class TestWI extends TestCase {

	private static Logger logger = Logger.getLogger(DmAlmETL.class);
	private int retry;
	private int wait;
	private Map<Workitem_Type, Long> minRevisionsByType;

	public void testProvenienzaDifetto(){
		try {
			Log4JConfiguration.inizialize();

			loadWiAndCustomFieldInStaging("sup",1310000L,2000000L);
//			
//			List<DmalmDifettoProdotto> staging_difettoprodotto = new ArrayList<DmalmDifettoProdotto>();
//			List<Tuple> target_difettoprodotto = new ArrayList<Tuple>();
//			QDmalmDifettoProdotto dif = QDmalmDifettoProdotto.dmalmDifettoProdotto;
//
//			int righeNuove = 0;
//			int righeModificate = 0;
//
//			Date dtInizioCaricamento = new Date();
//			Date dtFineCaricamento = null;
//
//			DmalmDifettoProdotto difetto_tmp = null;
//
//			String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;
//
//			Timestamp dataEsecuzione = DateUtils.stringToTimestamp("05-09-2017 10:40:00", "dd-MM-yyyy HH:mm:ss");
//			staging_difettoprodotto = DifettoDAO
//					.getAllDifettoProdotto(dataEsecuzione );
//
//			/*DifettoProdottoOdsDAO.delete();
//
//			logger.debug("START -> Popolamento Difetto ODS, "
//					+ staging_difettoprodotto.size() + " difetti");
//
//			DifettoProdottoOdsDAO.insert(staging_difettoprodotto,
//					dataEsecuzione);
//			*/
//			
//			List<DmalmDifettoProdotto> x = DifettoProdottoOdsDAO.getAll();
//
//			logger.debug("STOP -> Popolamento Difetto ODS, "
//					+ staging_difettoprodotto.size() + " difetti");
//
//				for (DmalmDifettoProdotto difetto : x) {
//					if(difetto.getCdDifetto().equals("SIMP-744")) {
//
//					// Ricerco nel db target un record con idProject =
//					// project.getIdProject e data fine validita uguale a 31-12-9999
//					difetto_tmp = difetto;
//					target_difettoprodotto = DifettoDAO.getDifettoProdotto(difetto);
//		
//					// se non trovo almento un record, inserisco il project nel
//					// target
//					if (target_difettoprodotto.size() == 0) {
//						difetto.setDtCambioStatoDifetto(difetto
//								.getDtModificaRecordDifetto());
//						righeNuove++;
//						DifettoDAO.insertDifettoProdotto(difetto);
//					} else {
//						boolean modificato = false;
//		
//						for (Tuple row : target_difettoprodotto) {
//							if (row != null) {
//		
//								if (BeanUtils.areDifferent(
//										row.get(dif.dmalmStatoWorkitemFk03),
//										difetto.getDmalmStatoWorkitemFk03())) {
//									difetto.setDtCambioStatoDifetto(difetto
//											.getDtModificaRecordDifetto());
//									modificato = true;
//								} else {
//									difetto.setDtCambioStatoDifetto(row
//											.get(dif.dtCambioStatoDifetto));
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(
//												row.get(dif.dmalmProjectFk02),
//												difetto.getDmalmProjectFk02())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(
//												row.get(dif.numeroTestataDifetto),
//												difetto.getNumeroTestataDifetto())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(
//												row.get(dif.numeroLineaDifetto),
//												difetto.getNumeroLineaDifetto())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(
//												row.get(dif.dmalmUserFk06),
//												difetto.getDmalmUserFk06())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(row.get(dif.uri),
//												difetto.getUri())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(row.get(dif.annullato),
//												difetto.getAnnullato())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(row.get(dif.provenienzaDifetto),
//												difetto.getProvenienzaDifetto())) {
//									modificato = true;
//								}
//		
//								if (modificato) {
//									righeModificate++;
//		
//									// STORICIZZO
//									// imposto a zero il rank e il flagUltimaSituazione
//									DifettoDAO.updateRankFlagUltimaSituazione(difetto,
//											new Double(0), new Short("0"));
//		
//									// inserisco un nuovo record
//									DifettoDAO.insertDifettoProdottoUpdate(
//											dataEsecuzione, difetto, true);
//								} else {
//									// Aggiorno lo stesso
//									DifettoDAO.updateDmalmDifettoProdotto(difetto);
//								}
//							}
//						}
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadWiAndCustomFieldInStaging(String typeWi,long minRev, long maxRev) throws Exception {
		Map<Workitem_Type, Long> minRevisionsByType = SissHistoryWorkitemDAO
				.getMinRevisionByType();
		// Drop degli indici prima dell'elaborazione di HISTORY_WORKITEM e
		// HISTORY_CF_WORKITEM
		logger.debug("START DROP SISS INDEXES");
		//SissHistoryWorkitemDAO.dropIndexes();
		logger.debug("STOP DROP SISS INDEXES");

		retry = Integer.parseInt(DmAlmConfigReader.getInstance()
				.getProperty(DMALM_DEADLOCK_RETRY));
		wait = Integer.parseInt(DmAlmConfigReader.getInstance()
				.getProperty(DMALM_DEADLOCK_WAIT));

		logger.debug("START SissHistoryWorkitem - numero wi: "
				+ Workitem_Type.values().length);

		Workitem_Type type = null;
		for (Workitem_Type type2 : Workitem_Type.values()) {
			if(type2.toString().equals(typeWi)){
				type = type2;
			}
		}

		logger.debug("START TYPE: SISS " + type.toString());
		int tentativi = 0;
		ErrorManager.getInstance().resetDeadlock();
		ErrorManager.getInstance().resetCFDeadlock();
		boolean inDeadLock = false;
		boolean cfDeadlock = false;

		tentativi++;
		logger.debug("Tentativo " + tentativi);
		
		for(long i=804620;i<maxRev;i=i+1)
		{
			minRevisionsByType.put(type, i);
			long j=i+1;
			logger.info("Carico da rev "+i+" a "+j);
			SissHistoryWorkitemDAO.fillSissHistoryWorkitem(
					minRevisionsByType, j, type);
			inDeadLock = ErrorManager.getInstance().hasDeadLock();
		}
//		if (!inDeadLock) {
//			List<String> customFields = EnumUtils
//					.getCFEnumerationByType(type);
//
//			SissHistoryCfWorkitemDAO
//			.fillSissHistoryCfWorkitemByWorkitemType(
//					minRevisionsByType.get(type),
//					maxRev, type, customFields);
//			cfDeadlock = ErrorManager.getInstance().hascfDeadLock();
//		}
//
//		logger.debug("Fine tentativo " + tentativi + " - WI deadlock "
//				+ inDeadLock + " - CF deadlock " + cfDeadlock);
//
//		if (inDeadLock || cfDeadlock) {
//			while (inDeadLock || cfDeadlock) {
//
//				tentativi++;
//
//				if (tentativi > retry) {
//					logger.debug("Raggiunto limite tentativi: "
//							+ tentativi);
//					Exception e = new Exception("Deadlock detected");
//					ErrorManager.getInstance().exceptionOccurred(true,
//							e);
//					return;
//				}
//
//				logger.debug("Errore, aspetto 3 minuti");
//				logger.debug("Tentativo " + tentativi);
//				TimeUnit.MINUTES.sleep(wait);
//
//				if (inDeadLock) {
//					SissHistoryWorkitemDAO.fillSissHistoryWorkitem(
//							minRevisionsByType, maxRev, type);
//					inDeadLock = ErrorManager.getInstance()
//							.hasDeadLock();
//					if (!inDeadLock) {
//						logger.debug("Non in deadlock -> provo i CF");
//						List<String> customFields = EnumUtils
//								.getCFEnumerationByType(type);
//
//						SissHistoryCfWorkitemDAO
//						.fillSissHistoryCfWorkitemByWorkitemType(
//								minRevisionsByType.get(type),
//								maxRev, type,
//								customFields);
//						cfDeadlock = ErrorManager.getInstance()
//								.hascfDeadLock();
//						logger.debug("I CF sono in deadlock "
//								+ cfDeadlock);
//					}
//				} else {
//					if (cfDeadlock) {
//						logger.debug("Scarico soltanto i CF");
//
//						List<String> customFields = EnumUtils
//								.getCFEnumerationByType(type);
//
//						SissHistoryCfWorkitemDAO
//						.fillSissHistoryCfWorkitemByWorkitemType(
//								minRevisionsByType
//								.get(type),
//								maxRev, type,
//								customFields);
//
//						cfDeadlock = ErrorManager.getInstance()
//								.hascfDeadLock();
//
//						logger.debug("I CF sono in deadlock "
//								+ cfDeadlock);
//					}
//				}
//			}
//		}

		logger.debug("START delete not matching CFs SISS");
		SissHistoryCfWorkitemDAO.deleteNotMatchingCFS();
		logger.debug("STOP delete not matching CFs SISS");

		logger.debug("START Update CF SISS");
		SissHistoryCfWorkitemDAO.updateCFonWorkItem();
		logger.debug("STOP Update CF SISS");

		// Rebuild degli indici dopo l'elaborazione di HISTORY_WORKITEM e
		// HISTORY_CF_WORKITEM
		logger.debug("START REBUILD SISS INDEXES");
		SissHistoryWorkitemDAO.rebuildIndexes();
		logger.debug("STOP REBUILD SISS INDEXES");

		ConnectionManager.getInstance().dismiss();
	}

}
