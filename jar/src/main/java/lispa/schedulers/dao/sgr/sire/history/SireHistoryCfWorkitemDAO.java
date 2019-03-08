package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryCfWorkitem;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class SireHistoryCfWorkitemDAO {
	private static Logger logger = Logger.getLogger(SireHistoryCfWorkitemDAO.class);

	public static void recoverSireHistoryCfWorkItem() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryCfWorkitem stgCFWorkItems = QSireHistoryCfWorkitem.sireHistoryCfWorkitem;

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