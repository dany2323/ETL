package lispa.schedulers.facade.cleaning;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.oreste.ClassificatoriDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.utils.OresteToStringUtils;


public class CheckOresteClassificatoriFacade implements Runnable {
	private Logger logger;
	private Timestamp dataEsecuzione;
	private boolean isAlive = true;;
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public CheckOresteClassificatoriFacade(Logger log, Timestamp date) {
		this.logger = log;
		this.dataEsecuzione = date;
	}
		
	public static void execute (Logger logger, Timestamp dataEsecuzione) {	
		int erroriNonBloccanti = 0;
		
		try
		{
			erroriNonBloccanti = checkCodiciClassificatoriDuplicati(logger, dataEsecuzione);
			
			erroriNonBloccanti += checkCodiciClassificatoriNull(logger, dataEsecuzione);
			
			EsitiCaricamentoDAO.insert
			(
				dataEsecuzione,
				DmAlmConstants.TARGET_ORESTE_CLASSIFICATORI, 
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

		
	}
	
	protected static int checkCodiciClassificatoriDuplicati(Logger logger, Timestamp dataEsecuzione) throws Exception{
		
		List<Tuple> classificatoriDuplicateKey = ClassificatoriDAO.getDuplicateCodiciClassificatori(dataEsecuzione);
				
		int errori = 0;

		for(Tuple row : classificatoriDuplicateKey)
		{
			
			errori++;

			ErroriCaricamentoDAO.insert(
									DmAlmConstants.FONTE_ORESTE_CLASSIFICATORI, 
									DmAlmConstants.TARGET_ORESTE_CLASSIFICATORI, 
									OresteToStringUtils.classificatoreOrestetoString(row),
									DmAlmConstants.CLASSIFICATORE_CODICE_DUPLICATO, 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
		}
		
		return errori;
	}
	
	protected static int checkCodiciClassificatoriNull(Logger logger,Timestamp dataEsecuzione) throws Exception{
				
		List<Tuple> CodiciClassificatoriNull = ClassificatoriDAO.getCodiciClassificatoriNull(dataEsecuzione);

		int errori = 0;
		
		for(Tuple row : CodiciClassificatoriNull)
		{
			
			errori ++;

			ErroriCaricamentoDAO.insert
									(
										DmAlmConstants.FONTE_ORESTE_CLASSIFICATORI, 
										DmAlmConstants.TARGET_ORESTE_CLASSIFICATORI, 
										OresteToStringUtils.classificatoreOrestetoString(row),
										DmAlmConstants.CLASSIFICATORE_CODICE_NULL, 
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
		}
		
		return errori;
	}

@Override
public void run() {
	
	execute(logger, dataEsecuzione);
	
	setAlive(false);
	
}
	
}