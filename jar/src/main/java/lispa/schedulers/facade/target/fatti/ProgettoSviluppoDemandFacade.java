package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;










import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoDem;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;


import lispa.schedulers.dao.target.ProgettoSviluppoDemandOdsDAO;
import lispa.schedulers.dao.target.fatti.ProgettoSviluppoDemandDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoDem;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ProgettoSviluppoDemandFacade {


	private static Logger logger = Logger.getLogger(ProgettoSviluppoDemandFacade.class);


	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmProgettoSviluppoDem> staging_progettoSviluppoDem = new ArrayList<DmalmProgettoSviluppoDem>();
		List<Tuple> target_progettoSviluppoDem = new ArrayList<Tuple>();

		QDmalmProgettoSviluppoDem progetto = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;

		DmalmProgettoSviluppoDem progetto_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_progettoSviluppoDem  = ProgettoSviluppoDemandDAO.getAllProgettoSviluppoDemand(dataEsecuzione);

			ProgettoSviluppoDemandOdsDAO.delete();

			logger.debug("START -> Popolamento Progetto Sviluppo DEMAND ODS, "+staging_progettoSviluppoDem.size()+ " progetti");

			ProgettoSviluppoDemandOdsDAO.insert(staging_progettoSviluppoDem, dataEsecuzione);

			List<DmalmProgettoSviluppoDem> x = ProgettoSviluppoDemandOdsDAO.getAll();

			logger.debug("STOP -> Popolamento Progetto Sviluppo DEMAND ODS, "+staging_progettoSviluppoDem.size()+ " progetti");




			for(DmalmProgettoSviluppoDem progettoSviluppoDem : x){   				


				progetto_tmp = progettoSviluppoDem;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999




				target_progettoSviluppoDem = ProgettoSviluppoDemandDAO.getProgettoSviluppoDemand(progettoSviluppoDem);

				// se non trovo almento un record, inserisco il project nel target
				if(target_progettoSviluppoDem.size()==0)
				{
					righeNuove++;

					progettoSviluppoDem.setDtCambioStatoProgSvilD(progettoSviluppoDem.getDtModificaProgSvilD());
					ProgettoSviluppoDemandDAO.insertProgettoSviluppoDemand(progettoSviluppoDem);
				}
				else
				{



					boolean modificato = false;

					for(Tuple row : target_progettoSviluppoDem)
					{

						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(progetto.dmalmStatoWorkitemFk03), progettoSviluppoDem.getDmalmStatoWorkitemFk03())) 
							{
								progettoSviluppoDem.setDtCambioStatoProgSvilD(progettoSviluppoDem.getDtModificaProgSvilD());
								modificato = true;
							}
							else {
								progettoSviluppoDem.setDtCambioStatoProgSvilD(row.get(progetto.dtCambioStatoProgSvilD));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(progetto.tempoTotaleRisoluzione), progettoSviluppoDem.getTempoTotaleRisoluzione()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(progetto.dmalmProjectFk02), progettoSviluppoDem.getDmalmProjectFk02()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(progetto.descrizioneProgSvilD), progettoSviluppoDem.getDescrizioneProgSvilD()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(progetto.dtScadenzaProgSvilD), progettoSviluppoDem.getDtScadenzaProgSvilD()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(progetto.dmalmUserFk06), progettoSviluppoDem.getDmalmUserFk06()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(progetto.uri), progettoSviluppoDem.getUri()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(progetto.annullato), progettoSviluppoDem.getAnnullato()))
							{
								modificato = true;
							}
							


							if(modificato && row.get(progetto.dmalmProjectFk02)!=0)
							{

								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								//AnomaliaProdottoDAO.updateDataFineValidita(dataEsecuzione, anomalia);								
								ProgettoSviluppoDemandDAO.updateRank(progettoSviluppoDem, new Double(0));

								// inserisco un nuovo record
								ProgettoSviluppoDemandDAO.insertProgettoSviluppoDemUpdate(dataEsecuzione, progettoSviluppoDem, true);	

							}
							else
							{
								// Aggiorno lo stesso

								ProgettoSviluppoDemandDAO.updateProgettoSviluppoDem(progettoSviluppoDem);
							}
						}
					}
				}
			}


//			ProgettoSviluppoDemandDAO.updateUOFK();
//
//
//			ProgettoSviluppoDemandDAO.updateRankInMonth();
//
//			ProgettoSviluppoDemandDAO.updateTempoFK();
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
						DmAlmConstants.TARGET_PROGETTO_SVILUPPO_DEMAND, 
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
		
		List<DmalmProgettoSviluppoDem> staging_progettoSviluppoDem = new ArrayList<DmalmProgettoSviluppoDem>();
		List<Tuple> target_progettoSviluppoDem = new ArrayList<Tuple>();

		QDmalmProgettoSviluppoDem progetto = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;

		DmalmProgettoSviluppoDem progetto_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_progettoSviluppoDem  = ProgettoSviluppoDemandDAO.getAllProgettoSviluppoDemand(dataEsecuzione);

			ProgettoSviluppoDemandOdsDAO.delete();

			logger.debug("START -> Popolamento Progetto Sviluppo DEMAND ODS, "+staging_progettoSviluppoDem.size()+ " progetti");

			ProgettoSviluppoDemandOdsDAO.insert(staging_progettoSviluppoDem, dataEsecuzione);

			List<DmalmProgettoSviluppoDem> x = ProgettoSviluppoDemandOdsDAO.getAll();

			logger.debug("STOP -> Popolamento Progetto Sviluppo DEMAND ODS, "+staging_progettoSviluppoDem.size()+ " progetti");




			for(DmalmProgettoSviluppoDem progettoSviluppoDem : x){   				


				progetto_tmp = progettoSviluppoDem;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999

				ProgettoSviluppoDemandDAO.updateProjectAndStatus(progettoSviluppoDem);

			}

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
						DmAlmConstants.TARGET_PROGETTO_SVILUPPO_DEMAND, 
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
