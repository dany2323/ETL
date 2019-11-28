package lispa.schedulers.manager;

public class RecoverManager {

	private static RecoverManager instance;
	private static boolean isRecovered;
	private static boolean isRecoveredStagingMps;

	public synchronized boolean isRecovered() {
		return isRecovered;
	}

	public synchronized void setRecovered(boolean isRecovered) {
		RecoverManager.isRecovered = isRecovered;
	}

	public synchronized boolean isRecoveredStagingMps() {
		return isRecoveredStagingMps;
	}

	public synchronized void setRecoveredStagingMps(
			boolean isRecoveredStagingMps) {
		RecoverManager.isRecoveredStagingMps = isRecoveredStagingMps;
	}

	private RecoverManager() {
		setRecovered(false);
		setRecoveredStagingMps(false);
	}

	public synchronized static RecoverManager getInstance() {

		if (instance == null)
			instance = new RecoverManager();
		return instance;

	}
}
