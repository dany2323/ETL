package lispa.schedulers.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmReleaseDiProgetto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.ReleaseDiProgettoOdsDAO;
import lispa.schedulers.dao.target.fatti.ReleaseDiProgettoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckAnomaliaDifettoProdottoFacade;
import lispa.schedulers.facade.target.SvecchiamentoFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.RecoverManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseDiProgetto;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;
import lispa.schedulers.utils.MailUtil;
//import lispa.sgr.TestFillWorkItemFromSource;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class DmAlmETL {

	private static Logger logger = Logger.getLogger(DmAlmETL.class);

	/**
	 * La classe principale, che contiene il metodo main(), è DmAlmETL, la quale
	 * invoca sequenzialmente i metodi doWork() delle tre classi
	 * DmAlmFillStaging, DmAlmCleaning e DmAlmFillTarget, che rappresentano
	 * ciascuna rispettivamente le tre fasi del processo ETL.
	 * 
	 * @param args
	 * @throws PropertiesReaderException
	 */
	
	public static void main(String[] args) throws PropertiesReaderException {
		Log4JConfiguration.inizialize();
		testFillWorkItemsFromSource();
		
//		String ambiente = DmAlmConfigReader.getInstance().getProperty(
//				DmAlmConfigReaderProperties.DM_ALM_AMBIENTE);
//
//		logger.info("*** Eseguo DmAlmEtl v"
//				+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");
//
//		logger.info("Ambiente: " + ambiente);
//
//		logger.info("Esecuzione SFERA: "
//				+ ExecutionManager.getInstance().isExecutionSfera());
//		/*
//		logger.info("Esecuzione EDMA/ORESTE/SGR_CM: "
//				+ ExecutionManager.getInstance().isExecutionElettraSgrcm());
//		*/
//		logger.info("Esecuzione EDMA/SGR_CM: "
//				+ ExecutionManager.getInstance().isExecutionElettraSgrcm());
//		
//		logger.info("Esecuzione MPS: "
//				+ ExecutionManager.getInstance().isExecutionMps());
//
//		logger.info("START DmAlmFillStaging.doWork()");
//		DmAlmFillStaging.doWork(); //Commentato Thread ORESTE all'interno
//		logger.info("STOP DmAlmFillStaging.doWork()");
//
//		if (!RecoverManager.getInstance().isRecovered()) {
//			try {
//				ConnectionManager.getInstance().dismiss();
//			} catch (Exception e) {
//				logger.debug(e);
//			}
//
//			logger.info("START DmAlmCleaning.doWork()");
//			DmAlmCleaning.doWork(); //Commentati Cleaning Oreste all'interno
//			logger.info("STOP DmAlmCleaning.doWork()");
//
//			try {
//				ConnectionManager.getInstance().dismiss();
//			} catch (Exception e) {
//				logger.debug(e);
//			}
//
//			logger.info("START DmAlmFillTarget.doWork()");
//			DmAlmFillTarget.doWork();
//			logger.info("STOP DmAlmFillTarget.doWork()");
//
//			// ATTIVITA' POST TARGET
//			// se non è stato eseguito il recover passo agli step successivi
//			if (!RecoverManager.getInstance().isRecovered()) {
//				logger.info("START DmAlmCheckAnnullamenti.doWork()");
//				DmAlmCheckAnnullamenti.doWork();
//				logger.info("STOP DmAlmCheckAnnullamenti.doWork()");
//
//				// se errore non eseguo gli step successivi
//				if (!ErrorManager.getInstance().hasError()) {
//					logger.info("START DmAlmCheckLinkSferaSgrCmElettra.doWork()");
//					DmAlmCheckLinkSferaSgrCmElettra.doWork();
//					logger.info("STOP DmAlmCheckLinkSferaSgrCmElettra.doWork()");
//				}
//
//				// se errore non eseguo gli step successivi
//				if (!ErrorManager.getInstance().hasError()) {
//					logger.info("START DmAlmCheckChangedWorkitem.doWork()");
//					DmAlmCheckChangedWorkitem.doWork();
//					logger.info("STOP DmAlmCheckChangedWorkitem.doWork()");
//				}
//
//				// gestione esecuzione effettuata direttamente nel facade
//				CheckAnomaliaDifettoProdottoFacade.execute();
//
//				// Gestione delle filiere, gestione esecuzione effettuata direttamente nel facade
//				DmAlmFiliere.doWork();
//				
//				// pulizia tabelle esiti e errori caricamento
//				SvecchiamentoFacade.execute();
//
//				// se errore in uno degli step precedenti eseguo il ripristino
//				// di tutto
//				if (ErrorManager.getInstance().hasError()) {
//					// SFERA/ELETTRA/SGRCM
//					if (ExecutionManager.getInstance().isExecutionSfera()
//							|| ExecutionManager.getInstance()
//									.isExecutionElettraSgrcm()) {
//						RecoverManager.getInstance().startRecoverTarget();
//						RecoverManager.getInstance().startRecoverStaging();
//					}
//
//					// MPS
//					if (ExecutionManager.getInstance().isExecutionMps()) {
//						RecoverManager.getInstance().startRecoverTrgMps();
//						RecoverManager.getInstance().startRecoverStgMps();
//					}
//				}
//			}
//		}
//
//		// gestione della mail di notifica
//		String esito = "";
//		String esitoBody = "";
//
//		if (RecoverManager.getInstance().isRecovered()) {
//			esito = DmAlmConstants.ETLKO;
//			esitoBody = esito + "\n\nRollback Effettuato. Causa Errore: "
//					+ "\n\n" + ErrorManager.getInstance().getException();
//		} else {
//			esito = DmAlmConstants.ETLOK;
//
//			if (RecoverManager.getInstance().isRecoveredStagingMps())
//				esito += DmAlmConstants.ETLMPSKO;
//
//			esitoBody = DmAlmConstants.ETLOK;
//			esitoBody += "\nEDMA/SGR_CM: " + (ExecutionManager.getInstance().isExecutionElettraSgrcm()?"Eseguito":"NON eseguito");
//			esitoBody += "\nSFERA: " + (ExecutionManager.getInstance().isExecutionSfera()?"Eseguito":"NON eseguito");
//			esitoBody += "\nMPS: " + (ExecutionManager.getInstance().isExecutionMps()?"Eseguito":"NON eseguito");
//			if (RecoverManager.getInstance().isRecoveredStagingMps())
//				esitoBody += DmAlmConstants.ETLMPSKO;
//		}
//
//		logger.info("START MailUtil.sendMail: " + ambiente + " - " + esito);
//
//		logger.info("*** Fine Esecuzione DmAlmEtl v"
//				+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");
//
//		MailUtil.sendMail(ambiente + " - " + DmAlmConstants.SUBJECT
//				+ new Timestamp(System.currentTimeMillis()) + " - " + esito,
//				ambiente + " - " + esitoBody, DataEsecuzione.getInstance()
//						.getDataEsecuzione());
	}
	
	
public static void testFillWorkItemsFromSource(){
		
		try {

		
			Log4JConfiguration.inizialize();
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.setDataEsecuzione(
							DateUtils.stringToTimestamp("2018-03-28 12:30:00",
									"yyyy-MM-dd HH:mm:00"));
			
			//SireHistoryWorkitemDAO.fillSireHistoryWorkitem(workItemToLoad, maxRev, Workitem_Type.release);;
			execute(dataEsecuzione);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	
	

	List<DmalmReleaseDiProgetto> staging_releases = new ArrayList<DmalmReleaseDiProgetto>();
	List<Tuple> target_releases = new ArrayList<Tuple>();
	QDmalmReleaseDiProgetto rel = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
	
	int righeNuove = 0;
	int righeModificate = 0;

	Date dtInizioCaricamento = new Date();
	Date dtFineCaricamento 	 = null;
	
	DmalmReleaseDiProgetto release_tmp = null;

	String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

	try
	{
		int maxrev=1800000;
		for(int i=1000000;i<maxrev; i=i+10000)
		{
			int j=i+10000;
			logger.info("Da Rev "+i+" a rev "+j);
			staging_releases  = ReleaseDiProgettoDAO.getAllReleaseDiProgettoNotInTarget(dataEsecuzione,i,i+10000);
			logger.info("Trovate "+staging_releases.size());

			ReleaseDiProgettoOdsDAO.delete();
			
			//logger.debug("START -> Popolamento Release ODS, "+staging_releases.size()+ " release");
			
			ReleaseDiProgettoOdsDAO.insert(staging_releases, dataEsecuzione);
			
			List<DmalmReleaseDiProgetto> x = ReleaseDiProgettoOdsDAO.getAll();
			
			//logger.debug("STOP -> Popolamento Release ODS, "+staging_releases.size()+ " release");
			
			for(DmalmReleaseDiProgetto release : x)
			{   
				
				release_tmp = release;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_releases = ReleaseDiProgettoDAO.getReleaseDiProgetto(release);
				release.setDtCaricamentoReleasediprog(release.getDtStoricizzazione());

				// se non trovo almento un record, inserisco il project nel target
				if(target_releases.size()==0)
				{
					righeNuove++;
					release.setDtCambioStatoReleasediprog(release.getDtModificaReleasediprog());
					logger.debug("Cerco di inserire release con ID : "+release.getDmalmReleasediprogPk()+" CD_RELEASE : "+release.getCdReleasediprog());
					ReleaseDiProgettoDAO.insertReleaseDiProgetto(release);
					//logger.debug("Inserito release con ID : "+release.getDmalmReleasediprogPk());
					
				}
				else
				{
					logger.debug("Cerco di modificare release con ID : "+release.getDmalmReleasediprogPk()+" CD_RELEASE : "+release.getCdReleasediprog());
					boolean modificato = false;

					for(Tuple row : target_releases)
					{
						Timestamp targetMod=row.get(rel.dtModificaReleasediprog);
						Timestamp stagingMod=release.getDtModificaReleasediprog();
						logger.info("Target release ha data"+row.get(rel.dtModificaReleasediprog)+ "Staging :"+release.getDtModificaReleasediprog());
						if(row !=null && stagingMod.getNanos()> targetMod.getNanos())
						{
							if(!modificato && BeanUtils.areDifferent(row.get(rel.dmalmStatoWorkitemFk03), release.getDmalmStatoWorkitemFk03()))
							{
								release.setDtCambioStatoReleasediprog(release.getDtModificaReleasediprog());
								modificato = true;
							}
							else {
								release.setDtCambioStatoReleasediprog(row.get(rel.dtCambioStatoReleasediprog));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel.numeroTestata), release.getNumeroTestata()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel.numeroLinea), release.getNumeroLinea()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel.dmalmProjectFk02), release.getDmalmProjectFk02()))
							{
								modificato = true;
							}
				
							if(!modificato && BeanUtils.areDifferent(row.get(rel.dtScadenzaReleasediprog), release.getDtScadenzaReleasediprog()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel.descrizioneReleasediprog), release.getDescrizioneReleasediprog()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel.dmalmUserFk06), release.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel.uri), release.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel.annullato), release.getAnnullato()))
							{
								modificato = true;
							}

							//DM_ALM-320
							if(!modificato && BeanUtils.areDifferent(row.get(rel.severity), release.getSeverity()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel.priority), release.getPriority()))
							{
								modificato = true;
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(rel.typeRelease), release.getTypeRelease()))
							{
								modificato=true;
							}
							
							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								ReleaseDiProgettoDAO.updateRank(release, new Double(0));

								// inserisco un nuovo record
								ReleaseDiProgettoDAO.insertReleaseDiProgettoUpdate(dataEsecuzione, release, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								ReleaseDiProgettoDAO.updateReleaseDiProgetto(release);
							}
						}
						else 
							logger.info("NO modifica");
					}
				}
			}
			
//			ReleaseDiProgettoDAO.updateATFK();
//			
//			ReleaseDiProgettoDAO.updateUOFK();
//			
//			ReleaseDiProgettoDAO.updateTempoFK();
//
//			ReleaseDiProgettoDAO.updateRankInMonth();
		}
	}
	catch (DAOException e) 
	{
		ErrorManager.getInstance().exceptionOccurred(true, e);
		System.out.println(LogUtils.objectToString(release_tmp));
		//System.out.println(e.getMessage(), e);
		
		
		stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
	}
	catch(Exception e)
	{
		ErrorManager.getInstance().exceptionOccurred(true, e);
		logger.error(LogUtils.objectToString(release_tmp));
		logger.error(e.getMessage(), e);
		
		
		stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
	}
	finally
	{
		dtFineCaricamento = new Date();
		/*
		try {
			
			/*EsitiCaricamentoDAO.insert
			(
						dataEsecuzione,
						DmAlmConstants.TARGET_RELEASE_DI_PROG, 
						stato, 
						new Timestamp(dtInizioCaricamento.getTime()), 
						new Timestamp(dtFineCaricamento.getTime()), 
						righeNuove, 
						righeModificate, 
						0, 
						0
			);	
		} catch (DAOException | SQLException e) {

			logger.error(e.getMessage(), e);
			
		}
	*/
	}

}
	
}