package lispa.schedulers.dao.sgr.siss.current;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SissCurrentSubterraUriMapDAO {

	private static Logger logger = Logger
			.getLogger(SissCurrentSubterraUriMapDAO.class);

	public static long fillSissCurrentSubterraUriMap() throws Exception {
		ConnectionManager cm = null;
		Connection oracleConnection = null;
		Connection pgConnection = null;
		long n_righe_inserite = 0;

		try {
			cm = ConnectionManager.getInstance();

			pgConnection = cm.getConnectionSISSCurrent();
			oracleConnection = cm.getConnectionOracle();

			oracleConnection.setAutoCommit(false);

			QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;
			lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentSubterraUriMap fontesissSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentSubterraUriMap.urimap; 

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};
			SQLQuery query = new SQLQuery(pgConnection, dialect);

			List<Tuple> subterra = query.from(fontesissSubterraUriMap).list(fontesissSubterraUriMap.all());

			logger.debug("fillSissCurrentSubterraUriMap - subterra.size: " + subterra.size());
			
			SQLInsertClause insert = new SQLInsertClause(oracleConnection, dialect, stgSubterra);
			Iterator<Tuple> i = subterra.iterator();
			Object[] el = null;
			
			while (i.hasNext()) {

				el = ((Tuple) i.next()).toArray();

				insert.columns(stgSubterra.cPk,
								stgSubterra.cRepo,
								stgSubterra.cId)
						.values(	el[0],
								DmAlmConstants.REPOSITORY_SISS,
								el[1])
						.addBatch();

				n_righe_inserite++;

				if (!insert.isEmpty()) {
					if (n_righe_inserite % DmAlmConstants.BATCH_SIZE_PG == 0) {
						insert.execute();
						oracleConnection.commit();
						insert = new SQLInsertClause(oracleConnection, dialect, stgSubterra);
					}
				}

			}
			if (!insert.isEmpty()) {
				insert.execute();
				oracleConnection.commit();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			n_righe_inserite = 0;
			if (oracleConnection != null) {
				oracleConnection.rollback();
			}

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(oracleConnection);
			}
			if (cm != null) {
				cm.closeConnection(pgConnection);
			}
		}

		return n_righe_inserite;
	}

	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;

			new SQLDeleteClause(connection, dialect, stgSubterra).where(stgSubterra.cRepo.eq(DmAlmConstants.REPOSITORY_SISS)).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
