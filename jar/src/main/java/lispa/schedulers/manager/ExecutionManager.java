package lispa.schedulers.manager;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.PropertiesReaderException;

import org.apache.log4j.Logger;

public class ExecutionManager {
	private static Logger logger = Logger.getLogger(ExecutionManager.class);
	private static ExecutionManager instance;
	private boolean executionSfera;
	private boolean executionElettraSgrcm;
	private boolean executionMps;

	private ExecutionManager() {
		

		try {
			this.setExecutionSfera(DmAlmConfigReader
					.getInstance()
					.getProperty(
							DmAlmConfigReaderProperties.DM_ALM_ESECUZIONE_SFERA)
					.equalsIgnoreCase(DmAlmConstants.DM_ALM_ESECUZIONE));
		} catch (NumberFormatException | PropertiesReaderException e1) {
			logger.error(e1.getMessage());
			this.setExecutionSfera(false);
		}
		
		try {
			this.setExecutionElettraSgrcm(DmAlmConfigReader
					.getInstance()
					.getProperty(
							DmAlmConfigReaderProperties.DM_ALM_ESECUZIONE_EDMA)
					.equalsIgnoreCase(DmAlmConstants.DM_ALM_ESECUZIONE));
		} catch (NumberFormatException | PropertiesReaderException e1) {
			logger.error(e1.getMessage());
			this.setExecutionElettraSgrcm(false);
		}
		
		try {
			this.setExecutionMps(DmAlmConfigReader
					.getInstance()
					.getProperty(
							DmAlmConfigReaderProperties.DM_ALM_ESECUZIONE_MPS)
					.equalsIgnoreCase(DmAlmConstants.DM_ALM_ESECUZIONE));
		} catch (NumberFormatException | PropertiesReaderException e1) {
			logger.error(e1.getMessage());
			this.setExecutionMps(false);
		}
	}

	public synchronized static ExecutionManager getInstance() {
		if (instance == null)
			instance = new ExecutionManager();
		return instance;
	}

	public boolean isExecutionSfera() {
		return executionSfera;
	}

	public void setExecutionSfera(boolean executionSfera) {
		this.executionSfera = executionSfera;
	}

	public boolean isExecutionElettraSgrcm() {
		return executionElettraSgrcm;
	}

	public void setExecutionElettraSgrcm(boolean executionElettraSgrcm) {
		this.executionElettraSgrcm = executionElettraSgrcm;
	}

	public boolean isExecutionMps() {
		return executionMps;
	}

	public void setExecutionMps(boolean executionMps) {
		this.executionMps = executionMps;
	}

}
