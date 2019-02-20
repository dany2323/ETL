package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaSupporto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.RichiestaSupportoOdsDAO;
import lispa.schedulers.dao.target.fatti.RichiestaSupportoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
//import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

public class RichiestaSupportoFacade {

private static Logger logger = Logger.getLogger(RichiestaSupportoFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	
		

		List<DmalmRichiestaSupporto> staging_richieste = new ArrayList<DmalmRichiestaSupporto>();
//		List<DmalmRichiestaSupporto> target_richieste = new ArrayList<DmalmRichiestaSupporto>();
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmRichiestaSupporto richieste_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try	{
			staging_richieste  = RichiestaSupportoDAO.getAllRichiestaSupporto(dataEsecuzione);
			
			RichiestaSupportoOdsDAO.delete();
			
			logger.debug("START -> Popolamento Richiesta Supporto, "+staging_richieste.size()+ " richiese di supporto");
			
			RichiestaSupportoOdsDAO.insert(staging_richieste, dataEsecuzione);
			
			List<DmalmRichiestaSupporto> richieste = RichiestaSupportoOdsDAO.getAll();
			
			logger.debug("STOP -> Richiesta Supporto, "+staging_richieste.size()+ " richiese di supporto");
			
			RichiestaSupportoDAO.checkAlteredRichiestaSupporto(richieste, dataEsecuzione);
			/*for(DmalmRichiestaSupporto richiesta : richieste) {
				
				RichiestaSupportoDAO.checkAlteredRichiestaSupporto(richiesta, dataEsecuzione);
				
				if(target_richieste.size()==0) {
					righeNuove++;
					richiesta.setDataCambioStatoRichSupp(richiesta.getDataModificaRecord());
					RichiestaSupportoDAO.insertRichiestaSupporto(richiesta, dataEsecuzione);
				} else {
					boolean modificato = false;

					for(DmalmRichiestaSupporto row : target_richieste) {
						
						if(row !=null) {
							
							if(!modificato && BeanUtils.areDifferent(row.getDmalmStatoWorkitemFk03(), richiesta.getDmalmStatoWorkitemFk03())) {
								richiesta.setDataCambioStatoRichSupp(richiesta.getDataModificaRecord());
								modificato = true;
							} else {
								richiesta.setDataCambioStatoRichSupp(row.getDataCambioStatoRichSupp());
							}
							if(!modificato && BeanUtils.areDifferent(row.getUriRichiestaSupporto(), richiesta.getUriRichiestaSupporto())) {
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getDmalmProjectFk02(), richiesta.getDmalmProjectFk02())) {
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getCdRichiestaSupporto(), richiesta.getCdRichiestaSupporto())) {
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getDataRisoluzioneRichSupporto(), richiesta.getDataRisoluzioneRichSupporto())) {
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getNrGiorniFestivi(), richiesta.getNrGiorniFestivi())) {
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getTempoTotRichSupporto(), richiesta.getTempoTotRichSupporto())) {
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getAnnullato(), richiesta.getAnnullato())) {
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getSeverityRichSupporto(), richiesta.getSeverityRichSupporto())) {
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getPriorityRichSupporto(), richiesta.getPriorityRichSupporto())) {
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getDataModificaRecord(), richiesta.getDataModificaRecord())) {
								modificato=true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getDataChiusRichSupporto(), richiesta.getDataChiusRichSupporto())) {
								modificato=true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getUseridRichSupporto(), richiesta.getUseridRichSupporto())) {
								modificato=true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getNomeRichSupporto(), richiesta.getNomeRichSupporto())) {
								modificato=true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getMotivoRisoluzione(), richiesta.getMotivoRisoluzione())) {
								modificato=true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getDescrizioneRichSupporto(), richiesta.getDescrizioneRichSupporto())) {
								modificato=true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getNumeroTestataRdi(), richiesta.getNumeroTestataRdi())) {
								modificato=true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getDataCambioStatoRichSupp(), richiesta.getDataCambioStatoRichSupp())) {
								modificato=true;
							}
							if(!modificato && BeanUtils.areDifferent(row.getDataDisponibilita(), richiesta.getDataDisponibilita())) {
								modificato=true;
							}
							
							if(modificato) {
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								RichiestaSupportoDAO.updateRank(richiesta, new Double(0));

								// inserisco un nuovo record
								RichiestaSupportoDAO.insertRichiestaSupportoUpdate(dataEsecuzione, richiesta, true);	
							} else {
    							 // Aggiorno lo stesso
								RichiestaSupportoDAO.updateRichiestaSupporto(richiesta);
							}
						}
					}
				}
			}*/
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(richieste_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(richieste_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally {
			dtFineCaricamento = new Date();

			try {
				
				EsitiCaricamentoDAO.insert (
							dataEsecuzione,
							DmAlmConstants.TARGET_RICHIESTA_SUPPORTO, 
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