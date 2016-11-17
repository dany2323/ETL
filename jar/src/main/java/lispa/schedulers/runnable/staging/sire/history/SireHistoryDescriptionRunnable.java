package lispa.schedulers.runnable.staging.sire.history;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.utils.enums.Workitem_Type;

public class SireHistoryDescriptionRunnable implements Runnable
{
	private Workitem_Type type;
	private long minRevision;
	private long maxRevision;

	
	
	
	public SireHistoryDescriptionRunnable(Workitem_Type type, long minRevision,	long maxRevision) {

		this.type = type;
		this.minRevision = minRevision;
		this.maxRevision = maxRevision;

	}

	@Override
	public void run() {
	
		SireHistoryWorkitemDAO.updateDescriptions(minRevision, maxRevision, type.toString());
		
	}

}
