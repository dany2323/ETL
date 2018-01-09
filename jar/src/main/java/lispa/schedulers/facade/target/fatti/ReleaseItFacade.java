package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmReleaseIt;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ReleaseItOdsDAO;
import lispa.schedulers.dao.target.fatti.ReleaseItDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseIt;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ReleaseItFacade {
	
	private static Logger logger = Logger.getLogger(ReleaseItFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmReleaseIt> staging_release_IT = new ArrayList<DmalmReleaseIt>();
		List<Tuple> target_release_IT = new ArrayList<Tuple>();
		QDmalmReleaseIt rel_it = QDmalmReleaseIt.dmalmReleaseIt;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmReleaseIt release_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_release_IT  = ReleaseItDAO.getAllReleaseIt(dataEsecuzione);
			
			ReleaseItOdsDAO.delete();
			
			logger.debug("START -> Popolamento ReleaseIt ODS, "+staging_release_IT.size()+ " releaseIt");
			
			ReleaseItOdsDAO.insert(staging_release_IT, dataEsecuzione);
			
			List<DmalmReleaseIt> x = ReleaseItOdsDAO.getAll();
			
			logger.debug("STOP -> ReleaseIt ODS, "+staging_release_IT.size()+ " releaseIt");
			
			for(DmalmReleaseIt release : x)
			{   
				
				release_tmp = release;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_release_IT = ReleaseItDAO.getReleaseIt(release);

				// se non trovo almento un record, inserisco il project nel target
				if(target_release_IT.size()==0)
				{
					righeNuove++;
					release.setDtCambioStatoReleaseIt(release.getDtModificaReleaseIt());
					ReleaseItDAO.insertReleaseIt(release);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_release_IT)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(rel_it.dmalmStatoWorkitemFk03), release.getDmalmStatoWorkitemFk03()))
							{
								release.setDtCambioStatoReleaseIt(release.getDtModificaReleaseIt());
								modificato = true;
							}
							else {
								release.setDtCambioStatoReleaseIt(row.get(rel_it.dtCambioStatoReleaseIt));
							}
							
							
							if(!modificato && BeanUtils.areDifferent(row.get(rel_it.dtScadenzaReleaseIt), release.getDtScadenzaReleaseIt()))
							{
								modificato = true;
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(rel_it.dmalmProjectFk02), release.getDmalmProjectFk02())) {
								modificato = true;
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(rel_it.durataEffRelease), release.getDurataEffRelease()))
							{
								modificato = true;
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(rel_it.descrizioneReleaseIt), release.getDescrizioneReleaseIt())) {
								modificato = true;
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(rel_it.dmalmUserFk06), release.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel_it.uri), release.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel_it.annullato), release.getAnnullato()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel_it.severity),release.getSeverity()))
							{
								modificato=true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(rel_it.priority),release.getPriority()))
							{
								modificato=true;
							}
							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								ReleaseItDAO.updateRank(release, new Double(0));

								// inserisco un nuovo record
								ReleaseItDAO.insertReleaseItUpdate(dataEsecuzione, release, true);
								
								
							}
							else
							{
								release.setDtCambioStatoReleaseIt(row.get(rel_it.dtCambioStatoReleaseIt));
    							 // Aggiorno lo stesso
								ReleaseItDAO.updateReleaseIt(release);
								
							}
						}
					}
				}
			}
			
			
//			ReleaseItDAO.updateUOFK();
//
//			ReleaseItDAO.updateRankInMonth();
//			
//			ReleaseItDAO.updateTempoFK();
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
							DmAlmConstants.TARGET_RELEASE_IT, 
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
