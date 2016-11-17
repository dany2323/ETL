package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmPersonale;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.PersonaleEdmaLispaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmPersonale;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

public class PersonaleEdmaFacade {

	private static Logger logger = Logger.getLogger(PersonaleEdmaFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) {	

		List<DmalmPersonale> staging = new ArrayList<DmalmPersonale>();
		List<Tuple> target = new ArrayList<Tuple>();
		QDmalmPersonale pers = QDmalmPersonale.dmalmPersonale;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		DmalmPersonale personale_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;


		try
		{
			staging  = PersonaleEdmaLispaDAO.getAllPersonale(dataEsecuzione);

			for(DmalmPersonale personale : staging)
			{   
				personale_tmp = personale;
				target = PersonaleEdmaLispaDAO.getPersonaleEdmaLispa(personale);

				// se non trovo almento un record, inserisco la nuova struttura organizzativa nel target
				if(target.size()==0)
				{
					righeNuove++;

					PersonaleEdmaLispaDAO.insertPersonaleEdmaLispa(personale);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target)
					{
						// aggiorno la data di fine validita del record corrente

						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(pers.dtInizioValiditaEdma), personale.getDtInizioValiditaEdma()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(pers.dtFineValiditaEdma), personale.getDtFineValiditaEdma()))
							{
								modificato = true;
							}							
							if(BeanUtils.areDifferent(row.get(pers.dtAttivazione), personale.getDtAttivazione()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(pers.dtDisattivazione), personale.getDtDisattivazione()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(pers.interno), personale.getInterno()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(pers.cdResponsabile), personale.getCdResponsabile()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(pers.cdSuperiore), personale.getCdSuperiore()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(pers.matricola), personale.getMatricola()))
							{
								modificato = true;
							}


							if(modificato)
							{
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								PersonaleEdmaLispaDAO.updateDataFineValidita(dataEsecuzione, personale);

								// inserisco un nuovo record
								PersonaleEdmaLispaDAO.insertPersonaleEdmaLispaUpdate(dataEsecuzione, personale);
							}
							else
							{
								// Aggiorno lo stesso
								PersonaleEdmaLispaDAO.updatePersonaleEdmaLispa(personale);
							}
						}

					}
				}

			}

		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(personale_tmp));
			logger.error(e.getMessage(), e);
			

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(personale_tmp));
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
						DmAlmConstants.TARGET_EDMA_PERSONALE, 
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