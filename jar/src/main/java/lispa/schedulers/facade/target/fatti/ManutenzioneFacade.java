package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmManutenzione;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ManutenzioneOdsDAO;
import lispa.schedulers.dao.target.fatti.ManutenzioneDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmManutenzione;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ManutenzioneFacade {

	private static Logger logger = Logger.getLogger(ManutenzioneFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	
		
		List<DmalmManutenzione> staging_manutenzioni = new ArrayList<DmalmManutenzione>();
		List<Tuple> target_manutenzioni = new ArrayList<Tuple>();
		QDmalmManutenzione man = QDmalmManutenzione.dmalmManutenzione;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmManutenzione manutenzione_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_manutenzioni  = ManutenzioneDAO.getAllManutenzione(dataEsecuzione);
			
			ManutenzioneOdsDAO.delete();
			
			logger.debug("START -> Popolamento Manutenzione ODS, "+staging_manutenzioni.size()+ " manutenzioni");
			
			ManutenzioneOdsDAO.insert(staging_manutenzioni, dataEsecuzione);
			
			List<DmalmManutenzione> x = ManutenzioneOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Manutenzione ODS, "+staging_manutenzioni.size()+ " manutenzioni");
			
			for(DmalmManutenzione manutenzione : x)
			{   
				
				manutenzione.setDmalmProgettoSferaFk(new Integer(0)); //puntamento al record Tappo

				manutenzione_tmp = manutenzione;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_manutenzioni = ManutenzioneDAO.getManutenzione(manutenzione);

				// se non trovo almento un record, inserisco il project nel target
				if(target_manutenzioni.size()==0)
				{
					righeNuove++;
					manutenzione.setDtCambioStatoManutenzione(manutenzione.getDtModificaManutenzione());
					ManutenzioneDAO.insertManutenzione(manutenzione);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_manutenzioni)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(man.dmalmStatoWorkitemFk03), manutenzione.getDmalmStatoWorkitemFk03()))
							{
								manutenzione.setDtCambioStatoManutenzione(manutenzione.getDtModificaManutenzione());
								modificato = true;
							}
							else {
								manutenzione.setDtCambioStatoManutenzione(row.get(man.dtCambioStatoManutenzione));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(man.numeroTestata), manutenzione.getNumeroTestata()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(man.numeroLinea), manutenzione.getNumeroLinea()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(man.dtScadenzaManutenzione), manutenzione.getDtScadenzaManutenzione()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(man.tempoTotaleRisoluzione), manutenzione.getTempoTotaleRisoluzione()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(man.assigneesManutenzione), manutenzione.getAssigneesManutenzione()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(man.dmalmProjectFk02), manutenzione.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(man.dmalmUserFk06), manutenzione.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(man.uri), manutenzione.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(man.annullato), manutenzione.getAnnullato()))
							{
								modificato = true;
							}

							if(modificato && row.get(man.dmalmProjectFk02)!=0)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								ManutenzioneDAO.updateRank(manutenzione, new Double(0));

								// inserisco un nuovo record
								ManutenzioneDAO.insertManutenzioneUpdate(dataEsecuzione, manutenzione, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								ManutenzioneDAO.updateManutenzione(manutenzione);
							}
						}
					}
				}
			}
			
			
//			ManutenzioneDAO.updateUOFK();
//			
//			ManutenzioneDAO.updateATFK();
//
//			ManutenzioneDAO.updateRankInMonth();
//			
//			ManutenzioneDAO.updateTempoFK();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(manutenzione_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(manutenzione_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally
		{
			dtFineCaricamento = new Date();

			try {
				
				EsitiCaricamentoDAO.insert
				(
							dataEsecuzione,
							DmAlmConstants.TARGET_MANUTENZIONE, 
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
		}

	}

	public static void updateProjectAndStatus(Timestamp dataEsecuzione) {
		
		List<DmalmManutenzione> staging_manutenzioni = new ArrayList<DmalmManutenzione>();
		List<Tuple> target_manutenzioni = new ArrayList<Tuple>();
		QDmalmManutenzione man = QDmalmManutenzione.dmalmManutenzione;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmManutenzione manutenzione_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_manutenzioni  = ManutenzioneDAO.getAllManutenzione(dataEsecuzione);
			
			ManutenzioneOdsDAO.delete();
			
			logger.debug("START -> Popolamento Manutenzione ODS, "+staging_manutenzioni.size()+ " manutenzioni");
			
			ManutenzioneOdsDAO.insert(staging_manutenzioni, dataEsecuzione);
			
			List<DmalmManutenzione> x = ManutenzioneOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Manutenzione ODS, "+staging_manutenzioni.size()+ " manutenzioni");
			
			for(DmalmManutenzione manutenzione : x)
			{   
				
				manutenzione.setDmalmProgettoSferaFk(new Integer(0)); //puntamento al record Tappo

				manutenzione_tmp = manutenzione;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				ManutenzioneDAO.updateProjectAndStatus(manutenzione);

			}
			
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(manutenzione_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(manutenzione_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally
		{
			dtFineCaricamento = new Date();

			try {
				
				EsitiCaricamentoDAO.insert
				(
							dataEsecuzione,
							DmAlmConstants.TARGET_MANUTENZIONE, 
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
		}

	}
	
}
