package lispa.schedulers.runnable.staging.sire.history;

import java.util.Map;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

import org.apache.log4j.Logger;

public class SireHistoryWorkitemRunnable implements Runnable {
	Map<EnumWorkitemType, Long> minRevisionsByType;
	private long polarion_maxRevision;
	public Logger logger;
	private EnumWorkitemType type;

	public SireHistoryWorkitemRunnable(
			Map<EnumWorkitemType, Long> minRevisionsByType,
			long polarion_maxRevision, Logger logger, EnumWorkitemType type) {
		this.minRevisionsByType = minRevisionsByType;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
		this.type = type;
	}

	@Override
	public void run() {
		try {
			logger.debug("START SireHistoryWorkitem.fill(" + type.toString() + ")");
			
			SireHistoryWorkitemDAO.fillSireHistoryWorkitem(minRevisionsByType,	polarion_maxRevision, type);
			
			logger.debug("STOP SireHistoryWorkitem.fill(" + type.toString()	+ ")");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
