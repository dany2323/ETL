package lispa.sgr.siss.history;

import junit.framework.TestCase;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryDescriptionRunnable;
import lispa.schedulers.utils.enums.Workitem_Type;

public class TestSISSUpdateDescription extends TestCase {

	public static void testDescription() throws InterruptedException {
		Thread anomAssSISS = new Thread(new SissHistoryDescriptionRunnable(
				Workitem_Type.EnumWorkitemType.anomalia_assistenza, 0, Long.MAX_VALUE));

		anomAssSISS.start();
		anomAssSISS.join();
	}
}
