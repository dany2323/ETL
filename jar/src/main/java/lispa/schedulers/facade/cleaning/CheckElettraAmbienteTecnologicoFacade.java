package lispa.schedulers.facade.cleaning;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.elettra.StgElAmbienteTecnologicoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.utils.ElettraToStringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckElettraAmbienteTecnologicoFacade implements Runnable {
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

	public CheckElettraAmbienteTecnologicoFacade(Logger log, Timestamp date) {
		this.logger = log;
		this.dataEsecuzione = date;
	}

	public static void execute(Logger logger, Timestamp dataEsecuzione) {
		int erroriNonBloccanti = 0;
		
		try
		{
			logger.debug("START CheckElettraAmbienteTecnologicoFacade.execute");

			erroriNonBloccanti += checkNomiAmbienteTecnologicoDuplicati(logger, dataEsecuzione);
			
			erroriNonBloccanti += checkNomiAmbienteTecnologicoNull(logger, dataEsecuzione);
			
			EsitiCaricamentoDAO.insert
			(
				dataEsecuzione,
				DmAlmConstants.TARGET_ELETTRA_AMBIENTETECNOLOGICO, 
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

		
		logger.debug("STOP CheckElettraAmbienteTecnologicoFacade.execute");
	}
	protected static int checkNomiAmbienteTecnologicoDuplicati(Logger logger, Timestamp dataEsecuzione) throws Exception{
		
		List<Tuple> nomiDuplicati = StgElAmbienteTecnologicoDAO.getDuplicateNomeAmbienteTecnologico(dataEsecuzione);
		
		int errori = 0;
		
		for(Tuple row : nomiDuplicati)
		{
			errori++;
			
			ErroriCaricamentoDAO.insert(
					DmAlmConstants.FONTE_ELETTRA_AMBIENTETECNOLOGICO, 
					DmAlmConstants.TARGET_ELETTRA_AMBIENTETECNOLOGICO, 
					ElettraToStringUtils.ambienteTecnologicoElettraToString(row), 
					DmAlmConstants.AMBIENTETECNOLOGICO_NOME_DUPLICATO, 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}
		
		return errori;
	}
	
	protected static int checkNomiAmbienteTecnologicoNull(Logger logger, Timestamp dataEsecuzione) throws Exception{
		
		List<Tuple> ambientiNomeNull = StgElAmbienteTecnologicoDAO.getNomeAmbienteTecnologicoNull(dataEsecuzione);
		
		int errori = 0;
		
		for(Tuple row : ambientiNomeNull)
		{
			errori++;
			ErroriCaricamentoDAO.insert
									(
										DmAlmConstants.FONTE_ELETTRA_AMBIENTETECNOLOGICO, 
										DmAlmConstants.TARGET_ELETTRA_AMBIENTETECNOLOGICO, 
										ElettraToStringUtils.ambienteTecnologicoElettraToString(row), 
										DmAlmConstants.AMBIENTETECNOLOGICO_NOME_NULL, 
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
		}
		
		return errori;
	}

}
