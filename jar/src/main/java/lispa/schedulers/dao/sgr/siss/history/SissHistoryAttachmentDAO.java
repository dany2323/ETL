package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryAttachment;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryAttachment;

public class SissHistoryAttachmentDAO {

	private static Logger logger = Logger
			.getLogger(SissHistoryAttachmentDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryAttachment fonteAttachment = HistoryAttachment.attachment;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryAttachment stgAttachment = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryAttachment.attachment;

	public static void fillSissHistoryAttachment(long minRevision,
			long maxRevision) throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> attachments = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSISSHistory();
			attachments = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query = new SQLQuery(pgConnection, dialect);

			attachments = query.from(fonteAttachment)
					.where(fonteAttachment.cRev.gt(minRevision))
					.where(fonteAttachment.cRev.loe(maxRevision))
					.list(fonteAttachment.all());

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgAttachment);
			int batchCounter = 0;

			for (Tuple row : attachments) {

				insert.columns(stgAttachment.cDeleted, stgAttachment.cFilename,
						stgAttachment.cId,
						stgAttachment.cLength, stgAttachment.cPk,
						stgAttachment.cRev, stgAttachment.cTitle,
						stgAttachment.cUpdated, stgAttachment.cUri,
						stgAttachment.cUrl, stgAttachment.fkAuthor,
						stgAttachment.fkProject, stgAttachment.fkUriAuthor,
						stgAttachment.fkUriProject, stgAttachment.fkUriWorkitem,
						stgAttachment.fkWorkitem)

						.values(row.get(fonteAttachment.cDeleted),
								row.get(fonteAttachment.cFilename),
								row.get(fonteAttachment.cId),
								row.get(fonteAttachment.cLength),
								row.get(fonteAttachment.cPk),
								row.get(fonteAttachment.cRev),
								row.get(fonteAttachment.cTitle),
								row.get(fonteAttachment.cUpdated),
								row.get(fonteAttachment.cUri),
								row.get(fonteAttachment.cUrl),
								row.get(fonteAttachment.fkAuthor),
								row.get(fonteAttachment.fkProject),
								row.get(fonteAttachment.fkUriAuthor),
								row.get(fonteAttachment.fkUriProject),
								row.get(fonteAttachment.fkUriWorkitem),
								row.get(fonteAttachment.fkWorkitem))
						.addBatch();

				batchCounter++;

				if (!insert.isEmpty()) {
					if (batchCounter
							% lispa.schedulers.constant.DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						connOracle.commit();
						insert = new SQLInsertClause(connOracle, dialect,
								stgAttachment);
					}
				}

			}

			if (!insert.isEmpty()) {
				insert.execute();
				connOracle.commit();
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(pgConnection);
			if (cm != null)
				cm.closeConnection(connOracle);
		}
	}

	public static long getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;
		lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryAttachment stgAttachment = QSissHistoryAttachment.dmalmSissHistoryAttachment;

		List<Long> max = new ArrayList<Long>();
		try {

			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);

			max = query.from(stgAttachment).list(stgAttachment.cRev.max());

			if (max == null || max.size() == 0 || max.get(0) == null) {
				return 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(oracle);
		}

		return max.get(0).longValue();
	}

	public static void recoverSissHistoryAttachement() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryAttachment stgHistoryAttachment = QSissHistoryAttachment.dmalmSissHistoryAttachment;
			// Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00",
			// "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgHistoryAttachment)
					.where(stgHistoryAttachment.dataCaricamento.eq(
							DataEsecuzione.getInstance().getDataEsecuzione()))
					.execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}
	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			new SQLDeleteClause(connection, dialect, stgAttachment).execute();
			connection.commit();
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}
	private static SQLQuery queryConnOracle(Connection connOracle,
			PostgresTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}

}
