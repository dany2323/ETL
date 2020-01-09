package lispa.schedulers.runnable.staging.siss.history;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemLinkedDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;
import org.apache.log4j.Logger;

public class SissHistoryWorkitemLinkedRunnable implements Runnable{
	private Map<EnumWorkitemType, Long> minRevisionByType;
	private long polarion_maxRevision;
	public Logger logger;


	public SissHistoryWorkitemLinkedRunnable(Map<EnumWorkitemType, Long> minRevisionByType ,long polarion_maxRevision,Logger logger) {
		this.minRevisionByType = minRevisionByType;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
	}


	@Override
	public void run() {
		try {
			logger.debug("START SissHistoryWorkitemLinked.fill()");
			int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			
			int tentativi_deadlock = 0;
			ErrorManager.getInstance().resetDeadlock();
			boolean inDeadlock = false;

			tentativi_deadlock++;
			logger.debug("Tentativo " + tentativi_deadlock);
			SissHistoryWorkitemLinkedDAO.fillSissHistoryWorkitemLinked();
			inDeadlock = ErrorManager.getInstance().hasDeadLock();
			while(inDeadlock) {
				tentativi_deadlock++;
				logger.debug("inDeadlock, aspetto 3 minuti");
				TimeUnit.MINUTES.sleep(wait);
				logger.debug("Tentativo " + tentativi_deadlock);
				ErrorManager.getInstance().resetDeadlock();
				SissHistoryWorkitemLinkedDAO.delete();
				SissHistoryWorkitemLinkedDAO.fillSissHistoryWorkitemLinked();
				inDeadlock = ErrorManager.getInstance().hasDeadLock();
			}
			logger.debug("Fine tentativo " + tentativi_deadlock);
			
			logger.debug("STOP SissHistoryWorkitemLinked.fill()");
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
