package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmTask;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.TaskOdsDAO;
import lispa.schedulers.dao.target.fatti.TaskDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTask;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class TaskFacade {
	
	private static Logger logger = Logger.getLogger(TaskFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmTask> staging_tasks = new ArrayList<DmalmTask>();
		List<Tuple> target_tasks = new ArrayList<Tuple>();
		QDmalmTask tsk = QDmalmTask.dmalmTask;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmTask task_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_tasks  = TaskDAO.getAllTask(dataEsecuzione);
			
			TaskOdsDAO.delete();
			
			logger.debug("START -> Popolamento Task ODS, "+staging_tasks.size()+ " task");
			
			TaskOdsDAO.insert(staging_tasks, dataEsecuzione);
			
			List<DmalmTask> x = TaskOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Task ODS, "+staging_tasks.size()+ " task");
			
			for(DmalmTask task : x)
			{   			
				
				
				task_tmp = task;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_tasks = TaskDAO.getTask(task);

				// se non trovo almento un record, inserisco il project nel target
				if(target_tasks.size()==0)
				{
					righeNuove++;
					task.setDtCambioStatoTask(task.getDtModificaTask());
					TaskDAO.insertTask(task);
				}
				else
				{
					
					
					
					boolean modificato = false;

					for(Tuple row : target_tasks)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(tsk.dmalmStatoWorkitemFk03), task.getDmalmStatoWorkitemFk03()))
							{
								task.setDtCambioStatoTask(task.getDtModificaTask());
								modificato = true;
							}
							else {
								task.setDtCambioStatoTask(row.get(tsk.dtCambioStatoTask));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tsk.numeroTestata), task.getNumeroTestata()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tsk.numeroLinea), task.getNumeroLinea()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tsk.dmalmProjectFk02), task.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tsk.dtScadenzaTask), task.getDtScadenzaTask()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tsk.tempoTotaleRisoluzione), task.getTempoTotaleRisoluzione()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tsk.dmalmUserFk06), task.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tsk.uri), task.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(tsk.annullato), task.getAnnullato()))
							{
								modificato = true;
							}

							if(modificato && row.get(tsk.dmalmProjectFk02)!=0)
							{
								
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								TaskDAO.updateRank(task, new Double(0));

								// inserisco un nuovo record
								TaskDAO.insertTaskUpdate(dataEsecuzione, task, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								TaskDAO.updateTask(task);
							}
						}
					}
				}
			}
			
//			TaskDAO.updateATFK();
//			
//			TaskDAO.updateUOFK();
//
//			TaskDAO.updateRankInMonth();
//			
//			TaskDAO.updateTempoFK();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(task_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(task_tmp));
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
							DmAlmConstants.TARGET_TASK, 
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
		
		List<DmalmTask> staging_tasks = new ArrayList<DmalmTask>();
		List<Tuple> target_tasks = new ArrayList<Tuple>();
		QDmalmTask tsk = QDmalmTask.dmalmTask;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmTask task_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_tasks  = TaskDAO.getAllTask(dataEsecuzione);
			
			TaskOdsDAO.delete();
			
			logger.debug("START -> Popolamento Task ODS, "+staging_tasks.size()+ " task");
			
			TaskOdsDAO.insert(staging_tasks, dataEsecuzione);
			
			List<DmalmTask> x = TaskOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Task ODS, "+staging_tasks.size()+ " task");
			
			for(DmalmTask task : x)
			{   			
				
				
				task_tmp = task;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				TaskDAO.updateProjectAndStatus(task);

			}
			
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(task_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(task_tmp));
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
							DmAlmConstants.TARGET_TASK, 
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
