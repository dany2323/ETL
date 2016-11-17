package lispa.schedulers.runnable.staging.siss.history;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.utils.enums.Workitem_Type;


	public class SissHistoryDescriptionRunnable implements Runnable
	{
		private Workitem_Type type;
		private long minRevision;
		private long maxRevision;
		
		
		
		
		public SissHistoryDescriptionRunnable(Workitem_Type type, long minRevision,	long maxRevision) {

			this.type = type;
			this.minRevision = minRevision;
			this.maxRevision = maxRevision;
		}

		@Override
		public void run() {
		
			SissHistoryWorkitemDAO.updateDescriptions(minRevision, maxRevision, type.toString());
			
		}


}
