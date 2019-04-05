package lispa.schedulers.runnable.staging.siss.history;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;


	public class SissHistoryDescriptionRunnable implements Runnable
	{
		private EnumWorkitemType type;
		private long minRevision;
		private long maxRevision;
		
		
		
		
		public SissHistoryDescriptionRunnable(EnumWorkitemType type, long minRevision,	long maxRevision) {

			this.type = type;
			this.minRevision = minRevision;
			this.maxRevision = maxRevision;
		}

		@Override
		public void run() {
		
			SissHistoryWorkitemDAO.updateDescriptions(minRevision, maxRevision, type.toString());
			
		}


}
