package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.mps.DmAlmMpsFirmatariVerbale;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsFirmatariVerbale;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsFirmatariVerbaleDAO {
	private static Logger logger = Logger.getLogger(StgMpsFirmatariVerbaleDAO.class);
	private static QDmalmStgMpsFirmatariVerbale stgMpsFirmatariVerbale = QDmalmStgMpsFirmatariVerbale.dmalmStgMpsFirmatariVerbale;
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;

	public static void fillStgMpsFirmatariVerbale()	throws DAOException, SQLException {

		DmAlmMpsFirmatariVerbale dmAlmMpsFirmatariVerbale = DmAlmMpsFirmatariVerbale.dmalmMpsFirmatariVerbale;
		List<Tuple> listMpsFirmatariVerbale = new ArrayList<Tuple>();
		
		try {
			connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);
			listMpsFirmatariVerbale = query
				.from(dmAlmMpsFirmatariVerbale)
				.list(dmAlmMpsFirmatariVerbale.all());
			
			int numRighe = 0;
			for (Tuple mpsFirmatariVerbale : listMpsFirmatariVerbale) {
				numRighe++;

				new SQLInsertClause(connection, dialect,
						stgMpsFirmatariVerbale)
						.columns(
								stgMpsFirmatariVerbale.idVerbaleValidazione,
								stgMpsFirmatariVerbale.idUtente,
								stgMpsFirmatariVerbale.firmatarioVerbale,
								stgMpsFirmatariVerbale.tipologiaResponsabile,
								stgMpsFirmatariVerbale.ordineFirma,
								stgMpsFirmatariVerbale.firmato,
								stgMpsFirmatariVerbale.dataFirma,
								stgMpsFirmatariVerbale.idEnte,
								stgMpsFirmatariVerbale.ente)
						.values(mpsFirmatariVerbale.get(dmAlmMpsFirmatariVerbale.idVerbaleValidazione),
								mpsFirmatariVerbale.get(dmAlmMpsFirmatariVerbale.idUtente),
								mpsFirmatariVerbale.get(dmAlmMpsFirmatariVerbale.firmatarioVerbale),
								mpsFirmatariVerbale.get(dmAlmMpsFirmatariVerbale.tipologiaResponsabile),
								mpsFirmatariVerbale.get(dmAlmMpsFirmatariVerbale.ordineFirma),
								mpsFirmatariVerbale.get(dmAlmMpsFirmatariVerbale.firmato),
								mpsFirmatariVerbale.get(dmAlmMpsFirmatariVerbale.dataFirma),
								mpsFirmatariVerbale.get(dmAlmMpsFirmatariVerbale.idEnte),
								mpsFirmatariVerbale.get(dmAlmMpsFirmatariVerbale.ente))
						.execute();
			}

			connection.commit();

			logger.info("StgMpsFirmatariVerbaleDAO.fillStgMpsFirmatariVerbale - righe inserite: " + numRighe);

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

			QDmalmStgMpsFirmatariVerbale qstgmpsfirmatariverbale = QDmalmStgMpsFirmatariVerbale.dmalmStgMpsFirmatariVerbale;

			new SQLDeleteClause(connection, dialect, qstgmpsfirmatariverbale)
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMpsFirmatariVerbale() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsFirmatariVerbale qstgmpsfirmatariverbale = QDmalmStgMpsFirmatariVerbale.dmalmStgMpsFirmatariVerbale;

			new SQLDeleteClause(connection, dialect, qstgmpsfirmatariverbale)
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
