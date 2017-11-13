package lispa.schedulers.dao.target;

import java.sql.Connection;
import lispa.schedulers.bean.target.DmalmUserElPersonale;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmUserElPersonale;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class UserElPersonaleDAO {

	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static Logger logger = Logger.getLogger(UserElPersonaleDAO.class);
	private static QDmalmUserElPersonale userElPersonale = QDmalmUserElPersonale.dmalmUserElPersonale;

	public static void deleteAll() throws DAOException {
		
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			
			new SQLDeleteClause(connection, dialect, userElPersonale).execute();
			
			connection.commit();
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		
	}
	
	public static void insertUser(DmalmUserElPersonale userPers) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, userElPersonale)
					.columns(userElPersonale.idUser,
							userElPersonale.dmalmElPersonalePk,
							userElPersonale.dtCaricamento)
					.values(userPers.getIdUser(),
							userPers.getDmalmPersonalePk(),
							userPers.getDtCaricamento())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	
}
