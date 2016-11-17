package lispa.schedulers.runnable.staging.siss.history;

import java.util.Map;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

public class SissHistoryWorkitemRunnable implements Runnable {

	private Map<Workitem_Type, Long> minRevisionsByType;
	private long maxRevision;
	private Logger logger;
	private Workitem_Type type;

	public SissHistoryWorkitemRunnable(
			Map<Workitem_Type, Long> minRevisionsByType, long maxRevision,
			Logger logger, Workitem_Type type) {

		this.logger = logger;
		this.minRevisionsByType = minRevisionsByType;
		this.maxRevision = maxRevision;
		this.type = type;
	}

	@Override
	public void run() {
		try {
			logger.debug("START SissHistoryWorkitem.fill(" + type.toString() + ")");
			SissHistoryWorkitemDAO.fillSissHistoryWorkitem(minRevisionsByType,	maxRevision, type);
			logger.debug("STOP SissHistoryWorkitem.fill(" + type.toString() + ")");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
