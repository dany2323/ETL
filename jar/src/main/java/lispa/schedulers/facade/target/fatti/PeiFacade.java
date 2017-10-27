package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmPei;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.PeiOdsDAO;
import lispa.schedulers.dao.target.fatti.PeiDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmPei;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class PeiFacade {

	private static Logger logger = Logger.getLogger(PeiFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmPei> staging_peis = new ArrayList<DmalmPei>();
		List<Tuple> target_peis = new ArrayList<Tuple>();
		QDmalmPei p = QDmalmPei.dmalmPei;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmPei pei_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_peis  = PeiDAO.getAllPei(dataEsecuzione);
			
			PeiOdsDAO.delete();
			
			logger.debug("START -> Popolamento PEI ODS, "+staging_peis.size()+ " pei");
			
			PeiOdsDAO.insert(staging_peis, dataEsecuzione);
			
			List<DmalmPei> x = PeiOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento PEI ODS, "+staging_peis.size()+ " pei");
			
			for(DmalmPei pei : x)
			{   			
				
				
				pei_tmp = pei;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_peis = PeiDAO.getPei(pei);

				// se non trovo almento un record, inserisco il project nel target
				if(target_peis.size()==0)
				{
					righeNuove++;
					pei.setDtCambioStatoPei(pei.getDtModificaPei());
					PeiDAO.insertPei(pei);
				}
				else
				{
					
					
					
					boolean modificato = false;

					for(Tuple row : target_peis)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(p.dmalmStatoWorkitemFk03), pei.getDmalmStatoWorkitemFk03()))
							{
								pei.setDtCambioStatoPei(pei.getDtModificaPei());
								modificato = true;
							}
							else {
								pei.setDtCambioStatoPei(row.get(p.dtCambioStatoPei));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(p.dtScadenzaPei), pei.getDtScadenzaPei()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(p.dtPrevistaComplReq), pei.getDtPrevistaComplReq()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(p.dtPrevistaPassInEs), pei.getDtPrevistaPassInEs()))
							{
								modificato = true;
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(p.dmalmProjectFk02), pei.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(p.descrizionePei), pei.getDescrizionePei()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(p.dmalmUserFk06), pei.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(p.uri), pei.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(p.annullato), pei.getAnnullato()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(p.severity), pei.getSeverity()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(p.priority), pei.getPriority()))
							{
								modificato = true;
							}
							
							if(modificato)
							{
								
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								PeiDAO.updateRank(pei, new Double(0));

								// inserisco un nuovo record
								PeiDAO.insertPeiUpdate(dataEsecuzione, pei, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								PeiDAO.updatePei(pei);
							}
						}
					}
				}
			}
			
			
//			PeiDAO.updateUOFK();
//			
//			PeiDAO.updateTempoFK();
//
//			PeiDAO.updateRankInMonth();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(pei_tmp));
			logger.error(e.getMessage(), e);
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(pei_tmp));
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
							DmAlmConstants.TARGET_PEI, 
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
