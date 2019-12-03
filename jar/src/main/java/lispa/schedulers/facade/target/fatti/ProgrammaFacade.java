package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgramma;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ProgrammaOdsDAO;
import lispa.schedulers.dao.target.fatti.ProgrammaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgramma;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ProgrammaFacade {

	private static Logger logger = Logger.getLogger(ProgrammaFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmProgramma> staging_programma = new ArrayList<DmalmProgramma>();
		List<Tuple> target_programma = new ArrayList<Tuple>();
		QDmalmProgramma prog = QDmalmProgramma.dmalmProgramma;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmProgramma programma_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_programma  = ProgrammaDAO.getAllProgramma(dataEsecuzione);
			
			ProgrammaOdsDAO.delete();
			
			logger.debug("START -> Popolamento Programma ODS, "+staging_programma.size()+ " programmi");
			
			ProgrammaOdsDAO.insert(staging_programma, dataEsecuzione);
			
			List<DmalmProgramma> x = ProgrammaOdsDAO.getAll();
			
			logger.debug("STOP -> Programma ODS, "+staging_programma.size()+ " programmi");
			
			for(DmalmProgramma programma : x)
			{   
				
				programma_tmp = programma;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_programma = ProgrammaDAO.getProgramma(programma);

				// se non trovo almento un record, inserisco il project nel target
				if(target_programma.size()==0)
				{
					righeNuove++;
					programma.setDtCambioStatoProgramma(programma.getDtModificaProgramma());
					ProgrammaDAO.insertProgramma(programma);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_programma)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(prog.dmalmStatoWorkitemFk03), programma.getDmalmStatoWorkitemFk03()))
							{
								programma.setDtCambioStatoProgramma(programma.getDtModificaProgramma());
								modificato = true;
							}
							else {
								programma.setDtCambioStatoProgramma(row.get(prog.dtCambioStatoProgramma));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.numeroTestata), programma.getNumeroTestata()))
							{	
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.dmalmProjectFk02), programma.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.numeroLinea), programma.getNumeroLinea()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.dtScadenzaProgramma), programma.getDtScadenzaProgramma()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.dmalmUserFk06), programma.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.uri), programma.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.annullato), programma.getAnnullato()))
							{
								modificato = true;
							}
							//DM_ALM-320
							if(!modificato && BeanUtils.areDifferent(row.get(prog.severity), programma.getSeverity()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.priority), programma.getPriority()))
							{
								modificato = true;
							}
							
							ProgrammaDAO.updateProgramma(programma);
								
							
						}
					}
				}
			}

			
//			ProgrammaDAO.updateUOFK();
//			
//			ProgrammaDAO.updateTempoFK();
//			
//			ProgrammaDAO.updateRankInMonth();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(programma_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(programma_tmp));
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
							DmAlmConstants.TARGET_PROGRAMMA, 
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
		List<DmalmProgramma> staging_programma = new ArrayList<DmalmProgramma>();
		List<Tuple> target_programma = new ArrayList<Tuple>();
		QDmalmProgramma prog = QDmalmProgramma.dmalmProgramma;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmProgramma programma_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_programma  = ProgrammaDAO.getAllProgramma(dataEsecuzione);
			
			ProgrammaOdsDAO.delete();
			
			logger.debug("START -> Popolamento Programma ODS, "+staging_programma.size()+ " programmi");
			
			ProgrammaOdsDAO.insert(staging_programma, dataEsecuzione);
			
			List<DmalmProgramma> x = ProgrammaOdsDAO.getAll();
			
			logger.debug("STOP -> Programma ODS, "+staging_programma.size()+ " programmi");
			
			for(DmalmProgramma programma : x)
			{   
				
				programma_tmp = programma;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				ProgrammaDAO.updateProjectAndStatus(programma);

			}

			
//			ProgrammaDAO.updateUOFK();
//			
//			ProgrammaDAO.updateTempoFK();
//			
//			ProgrammaDAO.updateRankInMonth();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(programma_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(programma_tmp));
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
							DmAlmConstants.TARGET_PROGRAMMA, 
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
