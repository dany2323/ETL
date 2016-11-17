package lispa.schedulers.facade.cleaning;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.elettra.StgElClassificatoriDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.utils.ElettraToStringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckElettraClassificatoriFacade implements Runnable {
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
	
	public CheckElettraClassificatoriFacade(Logger log, Timestamp date) {
		this.logger = log;
		this.dataEsecuzione = date;
	}
		
	public static void execute (Logger logger, Timestamp dataEsecuzione) {	
		int erroriNonBloccanti = 0;
		
		try
		{
			logger.debug("START CheckElettraClassificatoriFacade.execute");
			
			erroriNonBloccanti = checkElettraCodiciClassificatoriDuplicati(logger, dataEsecuzione);
			
			erroriNonBloccanti += checkElettraCodiciClassificatoriNull(logger, dataEsecuzione);
			
			EsitiCaricamentoDAO.insert
			(
				dataEsecuzione,
				DmAlmConstants.TARGET_ELETTRA_CLASSIFICATORI, 
				DmAlmConstants.CARICAMENTO_IN_ATTESA, 
				null, 
				null, 
				0, 
				0, 
				erroriNonBloccanti, 
				0
			);	
			
			logger.debug("STOP CheckElettraClassificatoriFacade.execute");
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

		
	}
	
	protected static int checkElettraCodiciClassificatoriDuplicati(Logger logger, Timestamp dataEsecuzione) throws Exception{
		
		List<Tuple> classificatoriDuplicateKey = StgElClassificatoriDAO.getDuplicateCodiciClassificatori(dataEsecuzione);
				
		int errori = 0;

		for(Tuple row : classificatoriDuplicateKey)
		{
			
			errori++;

			ErroriCaricamentoDAO.insert(
									DmAlmConstants.FONTE_ELETTRA_CLASSIFICATORI, 
									DmAlmConstants.TARGET_ELETTRA_CLASSIFICATORI, 
									ElettraToStringUtils.classificatoriToString(row),
									DmAlmConstants.CLASSIFICATORE_CODICE_DUPLICATO, 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
		}
		
		return errori;
	}
	
	protected static int checkElettraCodiciClassificatoriNull(Logger logger,Timestamp dataEsecuzione) throws Exception{
				
		List<Tuple> CodiciClassificatoriNull = StgElClassificatoriDAO.getElettraCodiciClassificatoriNull(dataEsecuzione);

		int errori = 0;
		
		for(Tuple row : CodiciClassificatoriNull)
		{
			
			errori ++;

			ErroriCaricamentoDAO.insert
									(
										DmAlmConstants.FONTE_ELETTRA_CLASSIFICATORI, 
										DmAlmConstants.TARGET_ELETTRA_CLASSIFICATORI, 
										ElettraToStringUtils.classificatoriToString(row),
										DmAlmConstants.CLASSIFICATORE_CODICE_NULL, 
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
		}
		
		return errori;
	}



}
