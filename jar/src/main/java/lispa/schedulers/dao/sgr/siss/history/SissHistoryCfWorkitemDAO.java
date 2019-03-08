package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryCfWorkitem;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class SissHistoryCfWorkitemDAO {
	private static Logger logger = Logger.getLogger(SissHistoryCfWorkitemDAO.class);

	public static void recoverSissHistoryCfWorkItem() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryCfWorkitem stgCFWorkItems = QSissHistoryCfWorkitem.sissHistoryCfWorkitem;

			new SQLDeleteClause(connection, dialect, stgCFWorkItems).where(
					stgCFWorkItems.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}