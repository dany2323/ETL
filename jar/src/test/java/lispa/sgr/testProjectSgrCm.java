package lispa.sgr;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiSGRCMFacade;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

public class testProjectSgrCm extends TestCase {

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject fonteProjects = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject.project;
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRevision fonteRevisions = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRevision.revision;

	public void testProject() {

		try {
			ProjectSgrCmFacade.execute(DateUtils.stringToTimestamp(
					"2014-07-18 10:44:00", "yyyy-MM-dd HH:mm:00"));
		} catch (DAOException e) {

		} catch (Exception e) {

		}
	}

	public void testFiltroSuProject() throws Exception {

		ConnectionManager cm = ConnectionManager.getInstance();
		Connection connH2 = cm.getConnectionSISSHistory();
		List<Tuple> projects = new ArrayList<Tuple>();

		SQLTemplates dialect = new HSQLDBTemplates() {
			{
				setPrintSchema(true);
			}
		};

		SQLQuery query = new SQLQuery(connH2, dialect);

		projects = query
				.from(fonteProjects, fonteRevisions)
				.where(fonteRevisions.cName.castToNum(Long.class).eq(
						fonteProjects.cRev)).where(fonteProjects.cRev.gt(0))
				.where(fonteProjects.cRev.loe(Long.MAX_VALUE))
				.where(fonteProjects.cLocation.notLike("default:/GRACO%"))
				// .where(fonteProjects.cId.notIn(
				// new SQLSubQuery()
				// .from(fonteProjects2)
				// .where(fonteProjects2.cName.like("%{READONLY}%"))
				// .list(fonteProjects2.cId)
				// )
				// )
				.list(fonteProjects.cTrackerprefix, fonteProjects.cIsLocal,
						fonteProjects.cPk, fonteProjects.fkUriLead,
						fonteProjects.cDeleted, fonteProjects.cFinish,
						fonteProjects.cUri, fonteProjects.cStart,
						fonteProjects.fkUriProjectgroup, fonteProjects.cActive,
						fonteProjects.cLocation, fonteProjects.fkProjectgroup,
						fonteProjects.fkLead,
						fonteProjects.cLockworkrecordsdate,
						fonteProjects.cName, fonteProjects.cId,
						fonteProjects.cRev, fonteRevisions.cName,
						fonteRevisions.cCreated);

		System.out.println(projects.size());

	}

	public static void testUnmarkedProjects() throws Exception {
		Log4JConfiguration.inizialize();

		CheckAnnullamentiSGRCMFacade.execute();

	}
}
