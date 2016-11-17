package lispa.schedulers.facade.cleaning;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.elettra.StgElFunzionalitaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElFunzionalita;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.ElettraToStringUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckElettraFunzionalitaFacade implements Runnable {
	private Logger logger;
	private Timestamp dataEsecuzione;
	private boolean isAlive = true;;

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	@Override
	public void run() { 

		execute(logger, dataEsecuzione);
		setAlive(false);
	}

	public CheckElettraFunzionalitaFacade(Logger log, Timestamp date) {
		this.logger = log;
		this.dataEsecuzione = date;
	}

	public static void execute(Logger logger, Timestamp dataEsecuzione) {
		int erroriNonBloccanti = 0;
		
		try
		{
			logger.debug("START CheckElettraFunzionalitaFacade.execute");
			
			erroriNonBloccanti += checkNomeFunzionalitaDuplicati(logger, dataEsecuzione);
			
			erroriNonBloccanti += checkNomeFunzionalitaNull(logger, dataEsecuzione);
			
			erroriNonBloccanti += checkNomiFunzionalitaAnnullatiLogicamente(logger, dataEsecuzione);
			
			EsitiCaricamentoDAO.insert
			(
				dataEsecuzione,
				DmAlmConstants.TARGET_ELETTRA_FUNZIONALITA, 
				DmAlmConstants.CARICAMENTO_IN_ATTESA, 
				null, 
				null, 
				0, 
				0, 
				erroriNonBloccanti, 
				0
			);	
		}
		catch (DAOException e) 
		{
			logger.error(e.getMessage(), e);
			
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage(), e);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		
		logger.debug("STOP CheckElettraFunzionalitaFacade.execute");
	}

	protected static int checkNomeFunzionalitaDuplicati(Logger logger, Timestamp dataEsecuzione) throws Exception{
		
		QStgElFunzionalita stgElFunzionalita = QStgElFunzionalita.stgElFunzionalita;
		
		List<Tuple> nomeFunzionalitaDuplicate = StgElFunzionalitaDAO.getDuplicateNomeFunzionalita(dataEsecuzione);
		
		int errori = 0;
		
		for(Tuple row : nomeFunzionalitaDuplicate)
		{
			
			errori++;
			ErroriCaricamentoDAO.insert(
									DmAlmConstants.FONTE_ELETTRA_FUNZIONALITA, 
									DmAlmConstants.TARGET_ELETTRA_FUNZIONALITA,  
									ElettraToStringUtils.funzionalitaElettraToString(row),
									DmAlmConstants.FUNZIONALITA_NOME_DUPLICATO + "SiglaProdotto: "+row.get(stgElFunzionalita.siglaProdotto)+", SiglaModulo: " +row.get(stgElFunzionalita.siglaModulo)+", NomeFunzionalita: "+row.get(stgElFunzionalita.nome), 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
		}
		
		return errori;
	}
	
	protected static int checkNomeFunzionalitaNull(Logger logger, Timestamp dataEsecuzione) throws Exception{
		
		
		List<Tuple> funzionalita = StgElFunzionalitaDAO.getNomeFunzionalitaNull(dataEsecuzione);
		
		int errori = 0;
		
		for(Tuple row : funzionalita)
		{
			errori++;
			ErroriCaricamentoDAO.insert
									(
										DmAlmConstants.FONTE_ELETTRA_FUNZIONALITA, 
										DmAlmConstants.TARGET_ELETTRA_FUNZIONALITA,  
										ElettraToStringUtils.funzionalitaElettraToString(row),
										DmAlmConstants.FUNZIONALITA_NOME_NULL, 
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
		}
		
		return errori;
	}

	protected static int checkNomiFunzionalitaAnnullatiLogicamente(Logger logger, Timestamp dataEsecuzione) throws Exception{

		QStgElFunzionalita stgElFunzionalita = QStgElFunzionalita.stgElFunzionalita;

		List<Tuple> funzionalitaAnnullate			 = new ArrayList<Tuple>();

		boolean checkStringaAnnullamento 	= true;
		boolean checkAnnullamentoDateFormat = true;
		
		int errori = 0;

		funzionalitaAnnullate = StgElFunzionalitaDAO.getFunzionalitaAnnullate(stgElFunzionalita, dataEsecuzione);

		for(Tuple row : funzionalitaAnnullate)
		{

			//			Se una funzionalita è ANNULLATO LOGICAMENTE: 
			//			La DATA FINE VALIDITA 
			//			- deve essere racchiusa tra due ## che si trovano subito dopo la scritta convenzionale indicante l'Annullamento Logico 		
			//			- deve essere in formato AAAAMMGG

			checkStringaAnnullamento = StringUtils.checkStringAnnullamentoFormat(row.get(stgElFunzionalita.nome), logger);

			if(!checkStringaAnnullamento)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ELETTRA_FUNZIONALITA, 
						DmAlmConstants.TARGET_ELETTRA_FUNZIONALITA,  
						ElettraToStringUtils.funzionalitaElettraToString(row),
						"Funzionalita.id "+row.get(stgElFunzionalita.idFunzionalia)+" - "+DmAlmConstants.FUNZIONALITA_DATAFINEVALIDITA_NO_CANCELLETTI, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			checkAnnullamentoDateFormat = DateUtils.checkDataAnnullamentoFormat(row.get(stgElFunzionalita.nome), logger);

			if(!checkAnnullamentoDateFormat)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ELETTRA_FUNZIONALITA, 
						DmAlmConstants.TARGET_ELETTRA_FUNZIONALITA,  
						ElettraToStringUtils.funzionalitaElettraToString(row),
						"Funzionalita.id "+row.get(stgElFunzionalita.idFunzionalia)+" - "+DmAlmConstants.FUNZIONALITA_DATAFINEVALIDITA_FORMATO_NON_VALIDO, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}
		}
		
		return errori;
	}

}
