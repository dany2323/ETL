package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmReleaseServizi;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ReleaseServiziOdsDAO;
import lispa.schedulers.dao.target.fatti.ReleaseServiziDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseServizi;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ReleaseServiziFacade {
	
	private static Logger logger = Logger.getLogger(ReleaseServiziFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmReleaseServizi> staging_releaseservizi = new ArrayList<DmalmReleaseServizi>();
		List<Tuple> target_releaseservizi = new ArrayList<Tuple>();
		QDmalmReleaseServizi releaseservizi = QDmalmReleaseServizi.dmalmReleaseServizi;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmReleaseServizi releaseservizi_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_releaseservizi  = ReleaseServiziDAO.getAllReleaseServizi(dataEsecuzione);
			
			ReleaseServiziOdsDAO.delete();
			
			logger.debug("START -> Popolamento ReleaseServizi ODS, "+staging_releaseservizi.size()+ " ReleaseServizi");
			
			ReleaseServiziOdsDAO.insert(staging_releaseservizi, dataEsecuzione);
			
			List<DmalmReleaseServizi> x = ReleaseServiziOdsDAO.getAll();
			
			logger.debug("STOP -> ReleaseServizi ODS, "+staging_releaseservizi.size()+ " ReleaseServizi");
			
			for(DmalmReleaseServizi release : x)
			{   
				
				releaseservizi_tmp = release;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_releaseservizi = ReleaseServiziDAO.getReleaseServizi(release);

				// se non trovo almento un record, inserisco il project nel target
				if(target_releaseservizi.size()==0)
				{
					righeNuove++;
					release.setDtCambioStatoRelServizi(release.getDtModificaRelServizi());
					ReleaseServiziDAO.insertReleaseServizi(release);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_releaseservizi)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(releaseservizi.dmalmStatoWorkitemFk03), release.getDmalmStatoWorkitemFk03()))
							{
								release.setDtCambioStatoRelServizi(release.getDtModificaRelServizi());
								modificato = true;
							}
							else {
								release.setDtCambioStatoRelServizi(row.get(releaseservizi.dtCambioStatoRelServizi));
							}
							
							
							if(!modificato && BeanUtils.areDifferent(row.get(releaseservizi.dtScadenzaRelServizi), release.getDtScadenzaRelServizi()))
							{
								modificato = true;
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(releaseservizi.dmalmProjectFk02), release.getDmalmProjectFk02())) {
								modificato = true;
							}
							
							
							
							if(!modificato && BeanUtils.areDifferent(row.get(releaseservizi.descrizioneRelServizi), release.getDescrizioneRelServizi())) {
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(releaseservizi.dmalmUserFk06), release.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(releaseservizi.uri), release.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(releaseservizi.annullato), release.getAnnullato()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(releaseservizi.severity), release.getSeverity()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(releaseservizi.priority), release.getPriority()))
							{
								modificato = true;
							}
														
							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								ReleaseServiziDAO.updateRank(release, new Double(0));

								// inserisco un nuovo record
								ReleaseServiziDAO.insertReleaseServiziUpdate(dataEsecuzione, release, true);
								
								
							}
							else
							{
								release.setDtCambioStatoRelServizi(row.get(releaseservizi.dtCambioStatoRelServizi));
    							 // Aggiorno lo stesso
								ReleaseServiziDAO.updateReleaseServizi(release);
								
							}
						}
					}
				}
			}
			
			
//			ReleaseServiziDAO.updateUOFK();
//
//			ReleaseServiziDAO.updateRankInMonth();
//			
//			ReleaseServiziDAO.updateTempoFK();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(releaseservizi_tmp));
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(releaseservizi_tmp));
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
							DmAlmConstants.TARGET_RELEASE_SERVIZI, 
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
