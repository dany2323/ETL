package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmBuild;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.BuildOdsDAO;
import lispa.schedulers.dao.target.fatti.BuildDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmBuild;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class BuildFacade {
	
	private static Logger logger = Logger.getLogger(BuildFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmBuild> staging_build = new ArrayList<DmalmBuild>();
		List<Tuple> target_build = new ArrayList<Tuple>();
		QDmalmBuild build_it = QDmalmBuild.dmalmBuild;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmBuild build_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_build  = BuildDAO.getAllBuild(dataEsecuzione);
			
			BuildOdsDAO.delete();
			
			logger.debug("START -> Popolamento Build ODS, "+staging_build.size()+ " Build");
			
			BuildOdsDAO.insert(staging_build, dataEsecuzione);
			
			List<DmalmBuild> x = BuildOdsDAO.getAll();
			
			logger.debug("STOP -> Build ODS, "+staging_build.size()+ " Build");
			
			for(DmalmBuild build : x)
			{   
				
				build_tmp = build;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_build = BuildDAO.getBuild(build);

				// se non trovo almento un record, inserisco il project nel target
				if(target_build.size()==0)
				{
					righeNuove++;
					build.setDtCambioStatoBuild(build.getDtModificaBuild());
					BuildDAO.insertBuild(build);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_build)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(build_it.dmalmStatoWorkitemFk03), build.getDmalmStatoWorkitemFk03()))
							{
								build.setDtCambioStatoBuild(build.getDtModificaBuild());
								modificato = true;
							}
							else {
								build.setDtCambioStatoBuild(row.get(build_it.dtCambioStatoBuild));
							}
							
							
							if(!modificato && BeanUtils.areDifferent(row.get(build_it.dtScadenzaBuild), build.getDtScadenzaBuild()))
							{
								modificato = true;
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(build_it.dmalmProjectFk02), build.getDmalmProjectFk02())) {
								modificato = true;
							}
							
							
							
							if(!modificato && BeanUtils.areDifferent(row.get(build_it.descrizioneBuild), build.getDescrizioneBuild())) {
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(build_it.dmalmUserFk06), build.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(build_it.uri), build.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(build_it.annullato), build.getAnnullato()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(build_it.severity), build.getSeverity()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(build_it.priority), build.getPriority()))
							{
								modificato = true;
							}
							
							
							if(modificato && row.get(build_it.dmalmProjectFk02)!=0)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								BuildDAO.updateRank(build, new Double(0));

								// inserisco un nuovo record
								BuildDAO.insertBuildUpdate(dataEsecuzione, build, true);
								
								
							}
							else
							{
    							 // Aggiorno lo stesso
								BuildDAO.updateBuild(build);
								
							}
						}
					}
				}
			}
			
			
//			BuildDAO.updateUOFK();
//			
//			BuildDAO.updateTempoFK();
//
//			BuildDAO.updateRankInMonth();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(build_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(build_tmp));
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
							DmAlmConstants.TARGET_BUILD, 
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
		
		List<DmalmBuild> staging_build = new ArrayList<DmalmBuild>();
		List<Tuple> target_build = new ArrayList<Tuple>();
		QDmalmBuild build_it = QDmalmBuild.dmalmBuild;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmBuild build_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_build  = BuildDAO.getAllBuild(dataEsecuzione);
			
			BuildOdsDAO.delete();
			
			logger.debug("START -> Popolamento Build ODS, "+staging_build.size()+ " Build");
			
			BuildOdsDAO.insert(staging_build, dataEsecuzione);
			
			List<DmalmBuild> x = BuildOdsDAO.getAll();
			
			logger.debug("STOP -> Build ODS, "+staging_build.size()+ " Build");
			
			for(DmalmBuild build : x)
			{   
				
				build_tmp = build;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				BuildDAO.updateProjectAndStatus(build);
			}
		
		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
