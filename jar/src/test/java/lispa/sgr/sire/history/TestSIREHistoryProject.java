package lispa.sgr.sire.history;

import org.apache.log4j.Logger;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.utils.DateUtils;

public class TestSIREHistoryProject extends TestCase {

	private Logger logger = Logger.getLogger(ProjectSgrCmFacade.class);

	public void testFillStaging() throws Exception {
		logger.debug("TestSIREHistoryProject");
		
		long polarion_maxRevision = Long.MAX_VALUE; // SireHistoryRevisionDAO.getMaxRevision();

		//

		long project_minRevision = 0; // SireHistoryProjectDAO.getMinRevision();

		SissHistoryProjectDAO.fillSissHistoryProject(project_minRevision,
				polarion_maxRevision);

	}

	public void testFillProjectAndUpdateTemplate() throws Exception {

		SireHistoryProjectDAO.fillSireHistoryProject(0, Long.MAX_VALUE);

		SissHistoryProjectDAO.fillSissHistoryProject(0, Long.MAX_VALUE);

	}

	public void testETLProject() throws Exception {

		// Thread t1 = new Thread(new SireHistoryProjectRunnable(0,
		// Long.MAX_VALUE, logger));
		// Thread t2 = new Thread(new SissHistoryProjectRunnable(0,
		// Long.MAX_VALUE, logger));
		// Thread t3 = new Thread(new SireCurrentProjectRunnable(logger));
		// Thread t4 = new Thread(new SissCurrentProjectRunnable(logger));
		//
		// t1.start();
		// t2.start();
		// t3.start();
		// t4.start();
		// t1.join();
		// t2.join();
		// t3.join();
		// t4.join();
		//
		// ProjectSgrCmFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());

		// List<String> unmarked = ProjectSgrCmDAO.getDeletedProjectsPaths();
		//
		// for(String path : unmarked) {
		// ProjectSgrCmDAO.setUNMARKED(path);
		// }

		// DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2014-05-12 14:12:00",
		// "yyyy-MM-dd HH:mm:00"));

		ProjectSgrCmFacade.execute(DataEsecuzione.getInstance()
				.setDataEsecuzione(
						DateUtils.stringToTimestamp("2014-05-12 14:12:00",
								"yyyy-MM-dd HH:mm:00")));
		//
		// SireCurrentProjectDAO.fillSireCurrentProject();
		// SissCurrentProjectDAO.fillSissCurrentProject();

		ConnectionManager.printInfo();

	}

	public void testFillTarget() throws Exception {

	}

}