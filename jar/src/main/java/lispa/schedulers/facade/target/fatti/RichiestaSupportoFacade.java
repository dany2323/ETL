package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.fatti.DmalmReleaseDiProgetto;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaSupporto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ReleaseDiProgettoOdsDAO;
import lispa.schedulers.dao.target.fatti.ReleaseDiProgettoDAO;
import lispa.schedulers.dao.target.fatti.RichiestaSupportoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseDiProgetto;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

public class RichiestaSupportoFacade {

private static Logger logger = Logger.getLogger(RichiestaSupportoFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	
		

		List<DmalmRichiestaSupporto> staging_richieste = new ArrayList<DmalmRichiestaSupporto>();
		List<Tuple> target_richieste = new ArrayList<Tuple>();
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmRichiestaSupporto richieste_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_richieste  = RichiestaSupportoDAO.getAllRichiestaSupporto(dataEsecuzione);
			
			ReleaseDiProgettoOdsDAO.delete();
			
			logger.debug("START -> Popolamento Richiesta Supporto, "+staging_richieste.size()+ " richiese di supporto");
			
//			ReleaseDiProgettoOdsDAO.insert(staging_richieste, dataEsecuzione);
//			
			List<DmalmRichiestaSupporto> x = ReleaseDiProgettoOdsDAO.getAll();
			
			logger.debug("STOP -> Richiesta Supporto, "+staging_richieste.size()+ " richiese di supporto");
			
			for(DmalmRichiestaSupporto richieste : x) {
				
				richieste_tmp = richieste;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_richieste = ReleaseDiProgettoDAO.getReleaseDiProgetto(richieste);
//
//				// se non trovo almento un record, inserisco il project nel target
//				if(target_releases.size()==0)
//				{
//					righeNuove++;
//					release.setDtCambioStatoReleasediprog(release.getDtModificaReleasediprog());
					ReleaseDiProgettoDAO.insertReleaseDiProgetto(release);
//				}
//				else
//				{
//					boolean modificato = false;
//
//					for(Tuple row : target_releases)
//					{
//						
//						if(row !=null)
//						{
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.dmalmStatoWorkitemFk03), release.getDmalmStatoWorkitemFk03()))
//							{
//								release.setDtCambioStatoReleasediprog(release.getDtModificaReleasediprog());
//								modificato = true;
//							}
//							else {
//								release.setDtCambioStatoReleasediprog(row.get(rel.dtCambioStatoReleasediprog));
//							}
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.numeroTestata), release.getNumeroTestata()))
//							{
//								modificato = true;
//							}
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.numeroLinea), release.getNumeroLinea()))
//							{
//								modificato = true;
//							}
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.dmalmProjectFk02), release.getDmalmProjectFk02()))
//							{
//								modificato = true;
//							}
//				
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.dtScadenzaReleasediprog), release.getDtScadenzaReleasediprog()))
//							{
//								modificato = true;
//							}
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.descrizioneReleasediprog), release.getDescrizioneReleasediprog()))
//							{
//								modificato = true;
//							}
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.dmalmUserFk06), release.getDmalmUserFk06()))
//							{
//								modificato = true;
//							}
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.uri), release.getUri()))
//							{
//								modificato = true;
//							}
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.annullato), release.getAnnullato()))
//							{
//								modificato = true;
//							}
//
//							//DM_ALM-320
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.severity), release.getSeverity()))
//							{
//								modificato = true;
//							}
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.priority), release.getPriority()))
//							{
//								modificato = true;
//							}
//							
//							if(!modificato && BeanUtils.areDifferent(row.get(rel.typeRelease), release.getTypeRelease()))
//							{
//								modificato=true;
//							}
//							
//							if(modificato)
//							{
//								righeModificate++;
//								// STORICIZZO
//								// aggiorno la data di fine validita sul record corrente
//								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								ReleaseDiProgettoDAO.updateRank(release, new Double(0));
//
//								// inserisco un nuovo record
								ReleaseDiProgettoDAO.insertReleaseDiProgettoUpdate(dataEsecuzione, release, true);	
//								
//							}
//							else
//							{
//    							 // Aggiorno lo stesso
								ReleaseDiProgettoDAO.updateReleaseDiProgetto(release);
//							}
//						}
//					}
//				}
			}
			
//			ReleaseDiProgettoDAO.updateATFK();
//			
//			ReleaseDiProgettoDAO.updateUOFK();
//			
//			ReleaseDiProgettoDAO.updateTempoFK();
//
//			ReleaseDiProgettoDAO.updateRankInMonth();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(release_tmp));
			logger.error(e.getMessage(), e);
			
			
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

			try {
				
				EsitiCaricamentoDAO.insert
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
		}

	}
}
