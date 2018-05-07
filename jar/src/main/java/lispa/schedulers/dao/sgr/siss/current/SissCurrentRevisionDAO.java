package lispa.schedulers.dao.sgr.siss.current;

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
import com.mysema.query.types.template.StringTemplate;

public class SissCurrentRevisionDAO {

	private static Logger logger = Logger
			.getLogger(SissCurrentRevisionDAO.class);

	public static long fillSissCurrentRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracleConnection = null;
		Connection pgConnection = null;
		long n_righe_inserite = 0;

		try {
			cm = ConnectionManager.getInstance();

			pgConnection = cm.getConnectionSISSCurrent();
			oracleConnection = cm.getConnectionOracle();

			oracleConnection.setAutoCommit(false);

			QDmalmCurrentRevision stgRevision = QDmalmCurrentRevision.currentRevision;
			lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentRevision fonteSissRevision =lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentRevision.revision; 

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};
			SQLQuery query = new SQLQuery(pgConnection, dialect);

			List<Tuple> revision = query.from(fonteSissRevision).list(fonteSissRevision.all());

			logger.debug("fillSissCurrentRevision - revision.size: " + revision.size());
			
			Iterator<Tuple> i = revision.iterator();
			Object[] el = null;

			while (i.hasNext()) {

				el = ((Tuple) i.next()).toArray();

				new SQLInsertClause(oracleConnection, dialect, stgRevision)
						.columns(stgRevision.cPk,
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
								StringTemplate.create(DmAlmConstants.REPOSITORY_SISS),
								el[1],
								el[2],
								el[3],
								el[4],
								el[5],
								el[6],
								el[7],
								el[8],
								el[9])
						.execute();

				n_righe_inserite++;

			}

			oracleConnection.commit();

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

			new SQLDeleteClause(connection, dialect, stgRevision).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
