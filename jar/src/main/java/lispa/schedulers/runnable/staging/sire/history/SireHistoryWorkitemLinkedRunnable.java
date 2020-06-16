package lispa.schedulers.runnable.staging.sire.history;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemLinkedDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import org.apache.log4j.Logger;

public class SireHistoryWorkitemLinkedRunnable implements Runnable{
	public Logger logger;


	public SireHistoryWorkitemLinkedRunnable(Logger logger) {
		this.logger = logger;
	}


	@Override
	public void run() {
		try {
			logger.debug("START SireHistoryWorkitemLinked.fill()");
			int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			
			int tentativi_deadlock = 0;
			ErrorManager.getInstance().resetDeadlock();
			boolean inDeadlock = false;

			tentativi_deadlock++;
			logger.debug("Tentativo " + tentativi_deadlock);
			SireHistoryWorkitemLinkedDAO.fillSireHistoryWorkitemLinked();
			inDeadlock = ErrorManager.getInstance().hasDeadLock();
			while(inDeadlock) {
				tentativi_deadlock++;
				logger.debug("inDeadlock, aspetto 3 minuti");
				TimeUnit.MINUTES.sleep(wait);
				logger.debug("Tentativo " + tentativi_deadlock);
				ErrorManager.getInstance().resetDeadlock();
				SireHistoryWorkitemLinkedDAO.delete();
				SireHistoryWorkitemLinkedDAO.fillSireHistoryWorkitemLinked();
				inDeadlock = ErrorManager.getInstance().hasDeadLock();
			}
			logger.debug("Fine tentativo " + tentativi_deadlock);
			
			logger.debug("STOP SireHistoryWorkitemLinked.fill()");
		}
		catch (DAOException e) {
			logger.error(e.getMessage(), e);
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}


	}

}
