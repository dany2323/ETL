package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoSvil;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ProgettoSviluppoSviluppoOdsDAO;
import lispa.schedulers.dao.target.fatti.ProgettoSviluppoSviluppoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoSvil;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ProgettoSviluppoSviluppoFacade {

	private static Logger logger = Logger.getLogger(ProgettoSviluppoSviluppoFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmProgettoSviluppoSvil> staging_progettoSviluppoSvil = new ArrayList<DmalmProgettoSviluppoSvil>();
		List<Tuple> target_progettoSviluppoSvil = new ArrayList<Tuple>();
		QDmalmProgettoSviluppoSvil prog = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmProgettoSviluppoSvil progetto_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_progettoSviluppoSvil  = ProgettoSviluppoSviluppoDAO.getAllProgettoSviluppoSviluppo(dataEsecuzione);
			
			ProgettoSviluppoSviluppoOdsDAO.delete();
			
			logger.debug("START -> Popolamento Progetto Sviluppo SVIL ODS, "+staging_progettoSviluppoSvil.size()+ " progetti");
			
			ProgettoSviluppoSviluppoOdsDAO.insert(staging_progettoSviluppoSvil, dataEsecuzione);
			
			List<DmalmProgettoSviluppoSvil> x = ProgettoSviluppoSviluppoOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento Progetto Sviluppo SVIL ODS, "+staging_progettoSviluppoSvil.size()+ " progetti");
			
			for(DmalmProgettoSviluppoSvil progettoSviluppo : x)
			{   
				progettoSviluppo.setDmalmProgettoSferaFk(new Integer(0)); //puntamento al record Tappo
				
				progetto_tmp = progettoSviluppo;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				target_progettoSviluppoSvil = ProgettoSviluppoSviluppoDAO.getProgettoSviluppoSviluppo(progettoSviluppo);

				// se non trovo almento un record, inserisco il project nel target
				if(target_progettoSviluppoSvil.size()==0)
				{
					righeNuove++;
					progettoSviluppo.setDtCambioStatoProgSvilS(progettoSviluppo.getDtModificaProgSvilS());
					ProgettoSviluppoSviluppoDAO.insertProgettoSviluppoSviluppo(progettoSviluppo);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_progettoSviluppoSvil)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(prog.dmalmStatoWorkitemFk03), progettoSviluppo.getDmalmStatoWorkitemFk03()))
							{
								progettoSviluppo.setDtCambioStatoProgSvilS(progettoSviluppo.getDtModificaProgSvilS());
								modificato = true;
							}
							else {
								progettoSviluppo.setDtCambioStatoProgSvilS(row.get(prog.dtCambioStatoProgSvilS));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.dmalmProjectFk02), progettoSviluppo.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.numeroTestata), progettoSviluppo.getNumeroTestata()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.numeroLinea), progettoSviluppo.getNumeroLinea()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.dtScadenzaProgSvilS), progettoSviluppo.getDtScadenzaProgSvilS()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.durataEffettivaProgSvilS), progettoSviluppo.getDurataEffettivaProgSvilS()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.dmalmUserFk06), progettoSviluppo.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.uri), progettoSviluppo.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(prog.annullato), progettoSviluppo.getAnnullato()))
							{
								modificato = true;
							}

							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								ProgettoSviluppoSviluppoDAO.updateRank(progettoSviluppo, new Double(0));

								// inserisco un nuovo record
								ProgettoSviluppoSviluppoDAO.insertProgettoSviluppoSvilUpdate(dataEsecuzione, progettoSviluppo, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								ProgettoSviluppoSviluppoDAO.updateProgettoSviluppoSvil(progettoSviluppo);
							}
						}
					}
				}
			}
			
//			ProgettoSviluppoSviluppoDAO.updateATFK();
//			
//			ProgettoSviluppoSviluppoDAO.updateUOFK();
//			
//			ProgettoSviluppoSviluppoDAO.updateTempoFK();
//
//			ProgettoSviluppoSviluppoDAO.updateRankInMonth();
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
							DmAlmConstants.TARGET_PROGETTO_SVILUPPO_SVILUPPO, 
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
