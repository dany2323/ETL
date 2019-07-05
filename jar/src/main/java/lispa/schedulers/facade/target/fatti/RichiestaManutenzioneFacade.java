package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmRichiestaManutenzione;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.RichiestaManutenzioneOdsDAO;
import lispa.schedulers.dao.target.fatti.RichiestaManutenzioneDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmRichiestaManutenzione;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class RichiestaManutenzioneFacade {

private static Logger logger = Logger.getLogger(RichiestaManutenzioneFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmRichiestaManutenzione> staging_richieste = new ArrayList<DmalmRichiestaManutenzione>();
		List<Tuple> target_richieste = new ArrayList<Tuple>();
		QDmalmRichiestaManutenzione rich = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmRichiestaManutenzione richiesta_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_richieste  = RichiestaManutenzioneDAO.getAllRichiestaManutenzione(dataEsecuzione);
			
			RichiestaManutenzioneOdsDAO.delete();
			
			logger.debug("START -> Popolamento Richiesta Manutenzione ODS, "+staging_richieste.size()+ " richieste");
			
			RichiestaManutenzioneOdsDAO.insert(staging_richieste, dataEsecuzione);
			
			List<DmalmRichiestaManutenzione> x = RichiestaManutenzioneOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Richiesta Manutenzione ODS, "+staging_richieste.size()+ " richieste");
			
			for(DmalmRichiestaManutenzione richiesta : x)
			{   
				
				richiesta_tmp = richiesta;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_richieste = RichiestaManutenzioneDAO.getRichiestaManutenzione(richiesta);

				// se non trovo almento un record, inserisco il project nel target
				if(target_richieste.size()==0)
				{
					righeNuove++;
					richiesta.setDtCambioStatoRichManut(richiesta.getDtModificaRichManutenzione());
					RichiestaManutenzioneDAO.insertRichiestaManutenzione(richiesta);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_richieste)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(rich.dmalmStatoWorkitemFk03), richiesta.getDmalmStatoWorkitemFk03()))
							{
								richiesta.setDtCambioStatoRichManut(richiesta.getDtModificaRichManutenzione());
								modificato = true;
							}
							else {
								richiesta.setDtCambioStatoRichManut(row.get(rich.dtCambioStatoRichManut));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rich.dmalmProjectFk02), richiesta.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rich.dtScadenzaRichManutenzione), richiesta.getDtScadenzaRichManutenzione()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rich.dataChiusuraRichManut), richiesta.getDataChiusuraRichManut()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rich.durataEffettivaRichMan), richiesta.getDurataEffettivaRichMan()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rich.dmalmUserFk06), richiesta.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rich.uri), richiesta.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rich.annullato), richiesta.getAnnullato()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rich.severity), richiesta.getSeverity()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rich.priority), richiesta.getPriority()))
							{
								modificato = true;
							}

							if(modificato && row.get(rich.dmalmProjectFk02)!=0)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								RichiestaManutenzioneDAO.updateRank(richiesta, new Double(0));

								// inserisco un nuovo record
								RichiestaManutenzioneDAO.insertRichiestaManutenzioneUpdate(dataEsecuzione, richiesta, true);	
								
							}
							else
							{
								richiesta.setDtCambioStatoRichManut(row.get(rich.dtCambioStatoRichManut));
    							 // Aggiorno lo stesso
								RichiestaManutenzioneDAO.updateRichiestaManutenzione(richiesta);
							}
						}
					}
				}
			}
			
			
//			RichiestaManutenzioneDAO.updateUOFK();
//
//			RichiestaManutenzioneDAO.updateRankInMonth();
//			
//			RichiestaManutenzioneDAO.updateTempoFK();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(richiesta_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(richiesta_tmp));
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
							DmAlmConstants.TARGET_RICHIESTA_MANUTENZIONE, 
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
	
		List<DmalmRichiestaManutenzione> staging_richieste = new ArrayList<DmalmRichiestaManutenzione>();
		List<Tuple> target_richieste = new ArrayList<Tuple>();
		QDmalmRichiestaManutenzione rich = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmRichiestaManutenzione richiesta_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_richieste  = RichiestaManutenzioneDAO.getAllRichiestaManutenzione(dataEsecuzione);
			
			RichiestaManutenzioneOdsDAO.delete();
			
			logger.debug("START -> Popolamento Richiesta Manutenzione ODS, "+staging_richieste.size()+ " richieste");
			
			RichiestaManutenzioneOdsDAO.insert(staging_richieste, dataEsecuzione);
			
			List<DmalmRichiestaManutenzione> x = RichiestaManutenzioneOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Richiesta Manutenzione ODS, "+staging_richieste.size()+ " richieste");
			
			for(DmalmRichiestaManutenzione richiesta : x)
			{   
				
				richiesta_tmp = richiesta;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				RichiestaManutenzioneDAO.updateProjectAndStatus(richiesta);

			}
			
			
//			RichiestaManutenzioneDAO.updateUOFK();
//
//			RichiestaManutenzioneDAO.updateRankInMonth();
//			
//			RichiestaManutenzioneDAO.updateTempoFK();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(richiesta_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(richiesta_tmp));
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
							DmAlmConstants.TARGET_RICHIESTA_MANUTENZIONE, 
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
