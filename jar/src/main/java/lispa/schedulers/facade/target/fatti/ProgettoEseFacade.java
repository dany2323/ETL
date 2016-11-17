package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgettoEse;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ProgettoEseOdsDAO;
import lispa.schedulers.dao.target.fatti.ProgettoEseDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoEse;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ProgettoEseFacade {

	private static Logger logger = Logger.getLogger(ProgettoEseFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmProgettoEse> staging_progettoEse = new ArrayList<DmalmProgettoEse>();
		List<Tuple> target_progettoEse = new ArrayList<Tuple>();
		QDmalmProgettoEse progEse = QDmalmProgettoEse.dmalmProgettoEse;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmProgettoEse progetto_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_progettoEse  = ProgettoEseDAO.getAllProgettoEse(dataEsecuzione);
			
			ProgettoEseOdsDAO.delete();
			
			logger.debug("START -> Popolamento ProgettoEse ODS, "+staging_progettoEse.size()+ " progetti");
			
			ProgettoEseOdsDAO.insert(staging_progettoEse, dataEsecuzione);
			
			List<DmalmProgettoEse> x = ProgettoEseOdsDAO.getAll();
			
			logger.debug("STOP -> ProgettoEse ODS, "+staging_progettoEse.size()+ " progetti");
			
			for(DmalmProgettoEse progetto : x)
			{   
				
				progetto_tmp = progetto;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_progettoEse = ProgettoEseDAO.getProgettoEse(progetto);

				// se non trovo almento un record, inserisco il project nel target
				if(target_progettoEse.size()==0)
				{
					righeNuove++;
					progetto.setDtCambioStatoProgettoEse(progetto.getDtModificaProgettoEse());
					ProgettoEseDAO.insertProgettoEse(progetto);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_progettoEse)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(progEse.dmalmStatoWorkitemFk03), progetto.getDmalmStatoWorkitemFk03()))
							{
								progetto.setDtCambioStatoProgettoEse(progetto.getDtModificaProgettoEse());
								modificato = true;
							}
							else {
								progetto.setDtCambioStatoProgettoEse(row.get(progEse.dtCambioStatoProgettoEse));
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(progEse.dmalmProjectFk02), progetto.getDmalmProjectFk02())) {
								modificato = true;
							}
							
							
							if(!modificato && BeanUtils.areDifferent(row.get(progEse.dtScadenzaProgettoEse), progetto.getDtScadenzaProgettoEse()))
							{
								modificato = true;
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(progEse.descrizioneProgettoEse), progetto.getDescrizioneProgettoEse()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(progEse.dmalmUserFk06), progetto.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(progEse.uri), progetto.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(progEse.annullato), progetto.getAnnullato()))
							{
								modificato = true;
							}
							
							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								ProgettoEseDAO.updateRank(progetto, new Double(0));

								// inserisco un nuovo record
								ProgettoEseDAO.insertProgettoEseUpdate(dataEsecuzione, progetto, true);
								
								
							}
							else
							{
								progetto.setDtCambioStatoProgettoEse(row.get(progEse.dtCambioStatoProgettoEse));
    							 // Aggiorno lo stesso
								ProgettoEseDAO.updateProgettoEse(progetto);
								
							}
						}
					}
				}
			}
			
			
//			ProgettoEseDAO.updateUOFK();
//			
//			ProgettoEseDAO.updateTempoFK();
//
//			ProgettoEseDAO.updateRankInMonth();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(progetto_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(progetto_tmp));
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
							DmAlmConstants.TARGET_PROGETTO_ESE, 
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
