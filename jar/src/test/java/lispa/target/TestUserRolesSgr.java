package lispa.target;

import java.sql.Connection;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryUserDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.facade.target.UserRolesSgrFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.target.QDmalmUserRolesSgr;
import lispa.schedulers.utils.DateUtils;

public class TestUserRolesSgr extends TestCase {
	
	public void test() {
		try {
			Log4JConfiguration.inizialize();
			//long user_minRevision = SissHistoryUserDAO.getMinRevision();
			//long project_minRevision = SissHistoryProjectDAO.getMinRevision();

			//SissHistoryProjectDAO.fillSissHistoryProject(user_minRevision, project_minRevision);
			//SireHistoryProjectDAO.fillSireHistoryProject(user_minRevision, project_minRevision);
			//ProjectSgrCmFacade.execute(DateUtils.stringToTimestamp("2017-07-13 11:00:00.0", "yyyy-MM-dd HH:mm:00"));
			UserRolesSgrFacade.execute(DateUtils.stringToTimestamp("2017-10-02 11:00:00.0", "yyyy-MM-dd HH:mm:00"));

		} catch (PropertiesReaderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void testMaxPk(){
		ConnectionManager cm = null;
		Connection connection = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
	
			QDmalmUserRolesSgr userRolesSgr = QDmalmUserRolesSgr.dmalmUserRolesSgr;
	
			SQLQuery query = new SQLQuery(connection, dialect);
			
			System.out.println(query.from(userRolesSgr).list(userRolesSgr.dmalmUserRolesPk.max()).get(0));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cm != null)
				try {
					cm.closeConnection(connection);
				} catch (DAOException e) {
					e.printStackTrace();
				}
		}
	}
	

}
