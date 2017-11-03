package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmSottoprogramma;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.SottoprogrammaOdsDAO;
import lispa.schedulers.dao.target.fatti.SottoprogrammaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSottoprogramma;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class SottoprogrammaFacade {
	private static Logger logger = Logger.getLogger(SottoprogrammaFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmSottoprogramma> staging_sottoprogramma = new ArrayList<DmalmSottoprogramma>();
		List<Tuple> target_sottoprogramma = new ArrayList<Tuple>();
		QDmalmSottoprogramma sottoprog = QDmalmSottoprogramma.dmalmSottoprogramma;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmSottoprogramma sottoprogramma_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_sottoprogramma  = SottoprogrammaDAO.getAllSottoprogramma(dataEsecuzione);
			
			SottoprogrammaOdsDAO.delete();
			
			logger.debug("START -> Popolamento Sottoprogramma ODS, "+staging_sottoprogramma.size()+ " sottoprogrammi");
			
			SottoprogrammaOdsDAO.insert(staging_sottoprogramma, dataEsecuzione);
			
			List<DmalmSottoprogramma> x = SottoprogrammaOdsDAO.getAll();
			
			logger.debug("STOP -> Sottoprogramma ODS, "+staging_sottoprogramma.size()+ " sottoprogrammi");
			
			for(DmalmSottoprogramma sottoprogramma : x)
			{   
				
				sottoprogramma_tmp = sottoprogramma;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_sottoprogramma = SottoprogrammaDAO.getSottoprogramma(sottoprogramma);

				// se non trovo almento un record, inserisco il project nel target
				if(target_sottoprogramma.size()==0)
				{
					righeNuove++;
					sottoprogramma.setDtCambioStatoSottoprogramma(sottoprogramma.getDtModificaSottoprogramma());
					SottoprogrammaDAO.insertSottoprogramma(sottoprogramma);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_sottoprogramma)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(sottoprog.dmalmStatoWorkitemFk03), sottoprogramma.getDmalmStatoWorkitemFk03()))
							{
								sottoprogramma.setDtCambioStatoSottoprogramma(sottoprogramma.getDtModificaSottoprogramma());
								modificato = true;
							}
							else {
								sottoprogramma.setDtCambioStatoSottoprogramma(row.get(sottoprog.dtCambioStatoSottoprogramma));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(sottoprog.dmalmProjectFk02), sottoprogramma.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(sottoprog.numeroTestata), sottoprogramma.getNumeroTestata()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(sottoprog.numeroLinea), sottoprogramma.getNumeroLinea()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(sottoprog.dtScadenzaSottoprogramma), sottoprogramma.getDtScadenzaSottoprogramma()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(sottoprog.dmalmUserFk06), sottoprogramma.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(sottoprog.uri), sottoprogramma.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(sottoprog.annullato), sottoprogramma.getAnnullato()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(sottoprog.severity), sottoprogramma.getSeverity()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(sottoprog.priority), sottoprogramma.getPriority()))
							{
								modificato = true;
							}
							
							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								SottoprogrammaDAO.updateRank(sottoprogramma, new Double(0));

								// inserisco un nuovo record
								SottoprogrammaDAO.insertSottoprogrammaUpdate(dataEsecuzione, sottoprogramma, true);	
							}
							else
							{
    							 // Aggiorno lo stesso
								SottoprogrammaDAO.updateSottoprogramma(sottoprogramma);
							}
						}
					}
				}
			}
			
			
//			SottoprogrammaDAO.updateUOFK();
//			
//			SottoprogrammaDAO.updateTempoFK();
//
//			SottoprogrammaDAO.updateRankInMonth();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(sottoprogramma_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(sottoprogramma_tmp));
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
							DmAlmConstants.TARGET_SOTTOPROGRAMMA, 
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
