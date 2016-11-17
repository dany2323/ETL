package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmTaskIt;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.TaskItOdsDAO;
import lispa.schedulers.dao.target.fatti.TaskItDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTaskIt;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class TaskItFacade {
	
	private static Logger logger = Logger.getLogger(TaskItFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmTaskIt> staging_taskit = new ArrayList<DmalmTaskIt>();
		List<Tuple> target_taskit = new ArrayList<Tuple>();
		QDmalmTaskIt taskit = QDmalmTaskIt.dmalmTaskIt;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmTaskIt taskit_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_taskit  = TaskItDAO.getAllTaskIt(dataEsecuzione);
			
			TaskItOdsDAO.delete();
			
			logger.debug("START -> Popolamento TaskIt ODS, "+staging_taskit.size()+ " TaskIt");
			
			TaskItOdsDAO.insert(staging_taskit, dataEsecuzione);
			
			List<DmalmTaskIt> x = TaskItOdsDAO.getAll();
			
			logger.debug("STOP -> TaskIt ODS, "+staging_taskit.size()+ " TaskIt");
			
			for(DmalmTaskIt task : x)
			{   
				
				taskit_tmp = task;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_taskit = TaskItDAO.getTaskIt(task);

				// se non trovo almento un record, inserisco il project nel target
				if(target_taskit.size()==0)
				{
					righeNuove++;
					task.setDtCambioStatoTaskIt(task.getDtModificaTaskIt());
					TaskItDAO.insertTaskIt(task);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_taskit)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(taskit.dmalmStatoWorkitemFk03), task.getDmalmStatoWorkitemFk03()))
							{
								task.setDtCambioStatoTaskIt(task.getDtModificaTaskIt());
								modificato = true;
							}
							else {
								task.setDtCambioStatoTaskIt(row.get(taskit.dtCambioStatoTaskIt));
							}
							
							
							if(!modificato && BeanUtils.areDifferent(row.get(taskit.dtScadenzaTaskIt), task.getDtScadenzaTaskIt()))
							{
								modificato = true;
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(taskit.dmalmProjectFk02), task.getDmalmProjectFk02())) {
								modificato = true;
							}
							
							
							
							if(!modificato && BeanUtils.areDifferent(row.get(taskit.descrizioneTaskIt), task.getDescrizioneTaskIt())) {
								modificato = true;
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(taskit.durataEffettiva), task.getDurataEffettiva()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(taskit.dmalmUserFk06), task.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(taskit.uri), task.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(taskit.annullato), task.getAnnullato()))
							{
								modificato = true;
							}
							
							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								TaskItDAO.updateRank(task, new Double(0));

								// inserisco un nuovo record
								TaskItDAO.insertTaskItUpdate(dataEsecuzione, task, true);
								
								
							}
							else
							{
								task.setDtCambioStatoTaskIt(row.get(taskit.dtCambioStatoTaskIt));
    							 // Aggiorno lo stesso
								TaskItDAO.updateTaskIt(task);
								
							}
						}
					}
				}
			}
			
			
//			TaskItDAO.updateUOFK();
//			
//			TaskItDAO.updateTempoFK();
//
//			TaskItDAO.updateRankInMonth();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(taskit_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(taskit_tmp));
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
							DmAlmConstants.TARGET_TASKIT, 
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
