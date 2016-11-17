package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmAnomaliaAssistenza;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.AnomaliaAssistenzaOdsDAO;
import lispa.schedulers.dao.target.fatti.AnomaliaAssistenzaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaAssistenza;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class AnomaliaAssistenzaFacade {

	private static Logger logger = Logger.getLogger(AnomaliaAssistenzaFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmAnomaliaAssistenza> staging_anomalie_assistenza = new ArrayList<DmalmAnomaliaAssistenza>();
		List<Tuple> target_anomalie_assistenza = new ArrayList<Tuple>();
		QDmalmAnomaliaAssistenza anom_ass = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmAnomaliaAssistenza anomalia_assistenza_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_anomalie_assistenza  = AnomaliaAssistenzaDAO.getAllAnomaliaAssistenza(dataEsecuzione);
			
			AnomaliaAssistenzaOdsDAO.delete();
			
			logger.debug("START -> Popolamento Anomalia Assistenza ODS, "+staging_anomalie_assistenza.size()+ " anomalie");
			
			AnomaliaAssistenzaOdsDAO.insert(staging_anomalie_assistenza, dataEsecuzione);
			
			List<DmalmAnomaliaAssistenza> x = AnomaliaAssistenzaOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Anomalia Assistenza ODS, "+staging_anomalie_assistenza.size()+ " anomalie");
			
			for(DmalmAnomaliaAssistenza anomalia_assistenza : x)
			{   			
				
				
				anomalia_assistenza_tmp = anomalia_assistenza;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_anomalie_assistenza = AnomaliaAssistenzaDAO.getAnomaliaAssistenza(anomalia_assistenza);

				// se non trovo almento un record, inserisco il project nel target
				if(target_anomalie_assistenza.size()==0)
				{
					righeNuove++;
					anomalia_assistenza.setDtCambioStatoAnomaliaAss(anomalia_assistenza.getDtModificaAnomaliaAss());
					AnomaliaAssistenzaDAO.insertAnomaliaAssistenza(anomalia_assistenza);
				}
				else
				{
					
					
					
					boolean modificato = false;

					for(Tuple row : target_anomalie_assistenza)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(anom_ass.dmalmStatoWorkitemFk03), anomalia_assistenza.getDmalmStatoWorkitemFk03()))
							{
								anomalia_assistenza.setDtCambioStatoAnomaliaAss(anomalia_assistenza.getDtModificaAnomaliaAss());
								modificato = true;
							}
							else {
								anomalia_assistenza.setDtCambioStatoAnomaliaAss(row.get(anom_ass.dtCambioStatoAnomaliaAss));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(anom_ass.descrizioneAnomaliaAss), anomalia_assistenza.getDescrizioneAnomaliaAss()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(anom_ass.dmalmProjectFk02), anomalia_assistenza.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(anom_ass.tempoTotaleRisoluzione), anomalia_assistenza.getTempoTotaleRisoluzione()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(anom_ass.dmalmUserFk06), anomalia_assistenza.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(anom_ass.uri), anomalia_assistenza.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(anom_ass.annullato), anomalia_assistenza.getAnnullato()))
							{
								modificato = true;
							}

							if(modificato)
							{
								
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								AnomaliaAssistenzaDAO.updateRank(anomalia_assistenza, new Double(0));

								// inserisco un nuovo record
								AnomaliaAssistenzaDAO.insertAnomaliaAssistenzaUpdate(dataEsecuzione, anomalia_assistenza, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								AnomaliaAssistenzaDAO.updateAnomaliaAssistenza(anomalia_assistenza);
							}
						}
					}
				}
			}

			
//			AnomaliaAssistenzaDAO.updateUOFK();
//			
//			AnomaliaAssistenzaDAO.updateRankInMonth();
//			
//			AnomaliaAssistenzaDAO.updateTempoFK();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(anomalia_assistenza_tmp));
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(anomalia_assistenza_tmp));
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
							DmAlmConstants.TARGET_ANOMALIA_ASSISTENZA, 
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
