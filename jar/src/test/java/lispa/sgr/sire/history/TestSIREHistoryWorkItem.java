package lispa.sgr.sire.history;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.utils.enums.Workitem_Type;

@SuppressWarnings("unused")
public class TestSIREHistoryWorkItem extends TestCase {

	public void testEnumerationFillWorkitem() throws Exception {

		long start = System.currentTimeMillis();
		try {
			// SireHistoryWorkitemDAO.fillSireHistoryWorkitem(150000,
			// Long.MAX_VALUE);
			// SireHistoryAttachmentDAO.fillSireHistoryAttachment(150000,
			// Long.MAX_VALUE);
			// SissHistoryWorkitemDAO.fillSissHistoryWorkitem(0,
			// Long.MAX_VALUE);
			// Thread workitem = new Thread(new SireHistoryWorkitemRunnable(0,
			// Long.MAX_VALUE, logger));

			// workitem.start();
			//
			// workitem.join();

		} catch (Exception e) {

		} finally {
		}

	}

	public static void testUpdateDescriptions() throws Exception {

		Thread t1, t2, t3, t4, t5, t6;

		t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				SireHistoryWorkitemDAO.updateDescriptions(0, Long.MAX_VALUE,
						Workitem_Type.EnumWorkitemType.anomalia_assistenza.toString());

			}

		});
		t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				SireHistoryWorkitemDAO.updateDescriptions(0, Long.MAX_VALUE,
						Workitem_Type.EnumWorkitemType.srqs.toString());

			}

		});
		t3 = new Thread(new Runnable() {

			@Override
			public void run() {
				SireHistoryWorkitemDAO.updateDescriptions(0, Long.MAX_VALUE,
						Workitem_Type.EnumWorkitemType.richiesta_gestione.toString());

			}

		});
		t4 = new Thread(new Runnable() {

			@Override
			public void run() {
				SissHistoryWorkitemDAO.updateDescriptions(0, Long.MAX_VALUE,
						Workitem_Type.EnumWorkitemType.anomalia_assistenza.toString());

			}

		});
		t5 = new Thread(new Runnable() {

			@Override
			public void run() {
				SissHistoryWorkitemDAO.updateDescriptions(0, Long.MAX_VALUE,
						Workitem_Type.EnumWorkitemType.srqs.toString());

			}

		});
		t6 = new Thread(new Runnable() {

			@Override
			public void run() {
				SissHistoryWorkitemDAO.updateDescriptions(0, Long.MAX_VALUE,
						Workitem_Type.EnumWorkitemType.richiesta_gestione.toString());

			}

		});

		//
		//
		//

		// t1.start();
		// t2.start(); t3.start();
		t4.start();
		t5.start();
		t6.start();

		// t1.join();
		// t2.join(); t3.join();
		t4.join();
		t5.join();
		t6.join();

	}

}
