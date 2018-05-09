package lispa.schedulers.dao.sgr.sire.current;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentRevision;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SireCurrentRevisionDAO {

	private static Logger logger = Logger
			.getLogger(SireCurrentRevisionDAO.class);

	public static long fillSireCurrentRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracleConnection = null;
		Connection pgConnection = null;
		long n_righe_inserite = 0;

		try {
			cm = ConnectionManager.getInstance();

			pgConnection = cm.getConnectionSIRECurrent();
			oracleConnection = cm.getConnectionOracle();

			oracleConnection.setAutoCommit(false);

			QDmalmCurrentRevision stgRevision = QDmalmCurrentRevision.currentRevision;
			lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentRevision fonteSireRevision =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentRevision.revision; 

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};
			SQLQuery query = new SQLQuery(pgConnection, dialect);

			List<Tuple> revision = query.from(fonteSireRevision).list(fonteSireRevision.all());

			logger.debug("fillSireCurrentRevision - revision.size: " + revision.size());
			
			SQLInsertClause insert = new SQLInsertClause(oracleConnection, dialect, stgRevision);
			Iterator<Tuple> i = revision.iterator();
			Object[] el = null;
			
			while (i.hasNext()) {

				el = ((Tuple) i.next()).toArray();

				insert.columns(stgRevision.cPk,
								stgRevision.cRepo,
								stgRevision.cUri,
								stgRevision.cRev,
								stgRevision.cDeleted,
								stgRevision.cAuthor,
								stgRevision.cCreated,
								stgRevision.cInternalCommit,
								stgRevision.cMessage,
								stgRevision.cName,
								stgRevision.cRepositoryname)
						.values(el[0],
								DmAlmConstants.REPOSITORY_SIRE,
								el[1],
								el[2],
								el[3],
								el[4],
								el[5],
								el[6],
								el[7],
								el[8],
								el[9])
						.addBatch();

				n_righe_inserite++;

				if (!insert.isEmpty()) {
					if (n_righe_inserite % DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						oracleConnection.commit();
						insert = new SQLInsertClause(oracleConnection, dialect, stgRevision);
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
			QDmalmCurrentRevision stgRevision = QDmalmCurrentRevision.currentRevision;

			new SQLDeleteClause(connection, dialect, stgRevision).where(stgRevision.cRepo.eq(DmAlmConstants.REPOSITORY_SIRE)).execute();
			connection.commit();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
