package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmRichiestaGestione;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.RichiestaGestioneOdsDAO;
import lispa.schedulers.dao.target.fatti.RichiestaGestioneDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmRichiestaGestione;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class RichiestaGestioneFacade {

	private static Logger logger = Logger.getLogger(RichiestaGestioneFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmRichiestaGestione> staging_richieste = new ArrayList<DmalmRichiestaGestione>();
		List<Tuple> target_richieste = new ArrayList<Tuple>();
		QDmalmRichiestaGestione rcgs = QDmalmRichiestaGestione.dmalmRichiestaGestione;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmRichiestaGestione rich_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_richieste  = RichiestaGestioneDAO.getAllRichiestaGestione(dataEsecuzione);
			
			RichiestaGestioneOdsDAO.delete();
			
			logger.debug("START -> Popolamento Richiesta Gestione ODS, "+staging_richieste.size()+ " richieste");
			
			RichiestaGestioneOdsDAO.insert(staging_richieste, dataEsecuzione);
			
			List<DmalmRichiestaGestione> x = RichiestaGestioneOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Richiesta Gestione ODS, "+staging_richieste.size()+ " richieste");
			
			for(DmalmRichiestaGestione richiesta : x)
			{   			
				
				
				rich_tmp = richiesta;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_richieste = RichiestaGestioneDAO.getRichiestaGestione(richiesta);

				// se non trovo almento un record, inserisco il project nel target
				if(target_richieste.size()==0)
				{
					righeNuove++;
					richiesta.setDtCambioStatoRichiestaGest(richiesta.getDtModificaRichiestaGest());
					RichiestaGestioneDAO.insertRichiestaGestione(richiesta);
				}
				else
				{
					
					
					
					boolean modificato = false;

					for(Tuple row : target_richieste)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(rcgs.dmalmStatoWorkitemFk03), richiesta.getDmalmStatoWorkitemFk03()))
							{
								richiesta.setDtCambioStatoRichiestaGest(richiesta.getDtModificaRichiestaGest());
								modificato = true;
							}
							else {
								richiesta.setDtCambioStatoRichiestaGest(row.get(rcgs.dtCambioStatoRichiestaGest));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rcgs.dmalmProjectFk02), richiesta.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rcgs.dtScadenzaRichiestaGest), richiesta.getDtScadenzaRichiestaGest()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rcgs.descrizioneRichiestaGest), richiesta.getDescrizioneRichiestaGest()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rcgs.dmalmUserFk06), richiesta.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rcgs.uri), richiesta.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rcgs.annullato), richiesta.getAnnullato()))
							{
								modificato = true;
							}

							if(modificato)
							{
								
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								RichiestaGestioneDAO.updateRank(richiesta, new Double(0));

								// inserisco un nuovo record
								RichiestaGestioneDAO.insertRichiestaGestioneUpdate(dataEsecuzione, richiesta, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								RichiestaGestioneDAO.updateTask(richiesta);
							}
						}
					}
				}
			}
			
			
//			RichiestaGestioneDAO.updateUOFK();
//
//			RichiestaGestioneDAO.updateRankInMonth();
//			
//			RichiestaGestioneDAO.updateTempoFK();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(rich_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(rich_tmp));
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
							DmAlmConstants.TARGET_RICHIESTA_GESTIONE, 
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
