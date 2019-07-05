package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmFase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.FaseOdsDAO;
import lispa.schedulers.dao.target.fatti.FaseDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmFase;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class FaseFacade {

private static Logger logger = Logger.getLogger(FaseFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmFase> staging_fasi = new ArrayList<DmalmFase>();
		List<Tuple> target_fasi = new ArrayList<Tuple>();
		QDmalmFase fs = QDmalmFase.dmalmFase;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmFase fase_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_fasi  = FaseDAO.getAllFase(dataEsecuzione);
			
			FaseOdsDAO.delete();
			
			logger.debug("START -> Popolamento Fase ODS, "+staging_fasi.size()+ " fasi");
			
			FaseOdsDAO.insert(staging_fasi, dataEsecuzione);
			
			List<DmalmFase> x = FaseOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Fase ODS, "+staging_fasi.size()+ " fasi");
			
			for(DmalmFase fase : x)
			{   
				
				fase_tmp = fase;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_fasi = FaseDAO.getFase(fase);

				// se non trovo almento un record, inserisco il project nel target
				if(target_fasi.size()==0)
				{
					righeNuove++;
					fase.setDtCambioStatoFase(fase.getDtModificaFase());
					FaseDAO.insertFase(fase);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_fasi)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(fs.dmalmStatoWorkitemFk03), fase.getDmalmStatoWorkitemFk03()))
							{
								fase.setDtCambioStatoFase(fase.getDtModificaFase());
								modificato = true;
							}
							else {
								fase.setDtCambioStatoFase(row.get(fs.dtCambioStatoFase));
							}
							
							if(!modificato && BeanUtils.areDifferent(row.get(fs.dmalmProjectFk02), fase.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(fs.dtScadenzaFase), fase.getDtScadenzaFase()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(fs.durataEffettivaFase), fase.getDurataEffettivaFase()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(fs.descrizioneFase), fase.getDescrizioneFase()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(fs.dmalmUserFk06), fase.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(fs.uri), fase.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(fs.annullato), fase.getAnnullato()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(fs.severity), fase.getSeverity()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(fs.priority), fase.getPriority()))
							{
								modificato = true;
							}

							if(modificato && row.get(fs.dmalmProjectFk02)!=0)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								FaseDAO.updateRank(fase, new Double(0));

								// inserisco un nuovo record
								FaseDAO.insertFaseUpdate(dataEsecuzione, fase, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								FaseDAO.updateFase(fase);
							}
						}
					}
				}
			}
			
			
//			FaseDAO.updateUOFK();
//			
//			FaseDAO.updateTempoFK();
//
//			FaseDAO.updateRankInMonth();
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(fase_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(fase_tmp));
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
							DmAlmConstants.TARGET_FASE, 
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
		
		List<DmalmFase> staging_fasi = new ArrayList<DmalmFase>();
		List<Tuple> target_fasi = new ArrayList<Tuple>();
		QDmalmFase fs = QDmalmFase.dmalmFase;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmFase fase_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_fasi  = FaseDAO.getAllFase(dataEsecuzione);
			
			FaseOdsDAO.delete();
			
			logger.debug("START -> Popolamento Fase ODS, "+staging_fasi.size()+ " fasi");
			
			FaseOdsDAO.insert(staging_fasi, dataEsecuzione);
			
			List<DmalmFase> x = FaseOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Fase ODS, "+staging_fasi.size()+ " fasi");
			
			for(DmalmFase fase : x)
			{   
				
				fase_tmp = fase;

				FaseDAO.updateProjectAndStatus(fase);

			}
			
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(fase_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(fase_tmp));
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
							DmAlmConstants.TARGET_FASE, 
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
