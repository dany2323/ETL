package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.mps.DmAlmMpsRilasciVerbali;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsRilasciVerbali;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsRilasciVerbaliDAO {

	private static Logger logger = Logger.getLogger(StgMpsRilasciVerbaliDAO.class);
	private static QDmalmStgMpsRilasciVerbali stgMpsRilasciVerbali = QDmalmStgMpsRilasciVerbali.dmalmStgMpsRilasciVerbali;
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;

	public static void fillStgMpsRilasciVerbali() throws DAOException, SQLException {
		DmAlmMpsRilasciVerbali dmAlmMpsRilasciVerbali = DmAlmMpsRilasciVerbali.dmalmMpsRilasciVerbali;
		List<Tuple> listMpsRilasciVerbali = new ArrayList<Tuple>();
		
		try {
			connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);
			listMpsRilasciVerbali = query
				.from(dmAlmMpsRilasciVerbali)
				.list(dmAlmMpsRilasciVerbali.all());
			
			int numRighe = 0;
			for (Tuple mpsRespRilasciVerbali : listMpsRilasciVerbali) {
				numRighe++;

				new SQLInsertClause(connection, dialect, stgMpsRilasciVerbali)
						.columns(
								stgMpsRilasciVerbali.codVerbale,
								stgMpsRilasciVerbali.idVerbaleValidazione,
								stgMpsRilasciVerbali.idRilascio,
								stgMpsRilasciVerbali.importo)
						.values(mpsRespRilasciVerbali.get(dmAlmMpsRilasciVerbali.codVerbale),
								mpsRespRilasciVerbali.get(dmAlmMpsRilasciVerbali.idVerbaleValidazione),
								mpsRespRilasciVerbali.get(dmAlmMpsRilasciVerbali.idRilascio),
								mpsRespRilasciVerbali.get(dmAlmMpsRilasciVerbali.importo))
						.execute();
			}

			connection.commit();

			logger.info("StgMpsRilasciVerbaliDAO.fillStgMpsRilasciVerbali righe inserite: " + numRighe);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void delete() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsRilasciVerbali qstgmpsrilasciverbali = QDmalmStgMpsRilasciVerbali.dmalmStgMpsRilasciVerbali;

			new SQLDeleteClause(connection, dialect, qstgmpsrilasciverbali)
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMpsRilasciVerbali() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsRilasciVerbali qstgmpsrilasciverbali = QDmalmStgMpsRilasciVerbali.dmalmStgMpsRilasciVerbali;

			new SQLDeleteClause(connection, dialect, qstgmpsrilasciverbali)
					.execute();

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
