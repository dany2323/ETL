package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryRevision;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SireHistoryRevisionDAO {

	private static Logger logger = Logger
			.getLogger(SireHistoryRevisionDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision fonteRevisions = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision.revision;

	private static QSireHistoryRevision stgRevisions = QSireHistoryRevision.sireHistoryRevision;

	public static long getMaxRevision() throws Exception {

		ConnectionManager cm = null;
		Connection connectionOracle = null;

		List<Integer> max = new ArrayList<>();
		try {

			cm = ConnectionManager.getInstance();
			connectionOracle = cm.getConnectionOracle();
			Timestamp lastValid = DateUtils.addSecondsToTimestamp(
					DataEsecuzione.getInstance().getDataEsecuzione(), -3600);

			SireHistoryRevision fonteRevisions = SireHistoryRevision.revision;

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};
			SQLQuery query = new SQLQuery(connectionOracle, dialect);

			max = query.from(fonteRevisions)
					.where(fonteRevisions.cCreated.before(lastValid))
					.list(fonteRevisions.cName.castToNum(Integer.class).max());

			// max =
			// query.from(fonteRevisions).list(fonteRevisions.cName.castToNum(Integer.class).max());

			if (max == null || max.size() == 0 || max.get(0) == null) {
				return 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connectionOracle);
		}
		return max.get(0).longValue();
	}

	public static Timestamp getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Timestamp> max = new ArrayList<Timestamp>();
		try {

			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			QSireHistoryRevision stgRevision = QSireHistoryRevision.sireHistoryRevision;
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);

			max = query.from(stgRevision).list(stgRevision.cCreated.max());

			if (max == null || max.size() == 0 || max.get(0) == null) {
				return DateUtils.stringToTimestamp("1900-01-01 00:00:00",
						"yyyy-MM-dd 00:00:00");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(oracle);
		}

		return max.get(0);
	}

	public static void fillSireHistoryRevision(Timestamp minRevision,
			long maxRevision) throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		List<Tuple> revisions = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			revisions = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			OracleTemplates dialect = new OracleTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query = new SQLQuery(connOracle, dialect);

			revisions = query.from(fonteRevisions)
					.where(fonteRevisions.cCreated.gt(minRevision))
					.where(fonteRevisions.cName.castToNum(Long.class)
							.loe(maxRevision))
					.list(fonteRevisions.all());
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgRevisions);

			int batchSizeCounter = 0;
			for (Tuple row : revisions) {
				batchSizeCounter++;

				insert.columns(stgRevisions.cPk, stgRevisions.cAuthor,
						stgRevisions.cCreated, stgRevisions.cDeleted,
						stgRevisions.cInternalcommit, stgRevisions.cIsLocal,
						stgRevisions.cMessage, stgRevisions.cName,
						stgRevisions.cRepositoryname, stgRevisions.cRev,
						stgRevisions.cUri, stgRevisions.sireHistoryRevisionPk,
						stgRevisions.dataCaricamento)
						.values(row.get(fonteRevisions.cPk),
								row.get(fonteRevisions.cAuthor),
								row.get(fonteRevisions.cCreated),
								row.get(fonteRevisions.cDeleted),
								row.get(fonteRevisions.cInternalcommit),
								row.get(fonteRevisions.cIsLocal),
								row.get(fonteRevisions.cMessage) != null && row
										.get(fonteRevisions.cMessage)
										.length() > 4000
												? row.get(
														fonteRevisions.cMessage)
														.substring(0, 4000)
												: row.get(
														fonteRevisions.cMessage),
								row.get(fonteRevisions.cName),
								row.get(fonteRevisions.cRepositoryname),
								row.get(fonteRevisions.cRev),
								row.get(fonteRevisions.cUri),
								StringTemplate
										.create("HISTORY_REVISION_SEQ.nextval"),
								DataEsecuzione.getInstance()
										.getDataEsecuzione());
				insert.addBatch();
				if (!revisions.isEmpty()
						&& batchSizeCounter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					connOracle.commit();
					insert = new SQLInsertClause(connOracle, dialect,
							stgRevisions);
					batchSizeCounter = 0;
				}
			}
			if (!revisions.isEmpty()) {
				insert.execute();
				connOracle.commit();
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connOracle);
		}

	}
	public static void recoverSireHistoryRevision() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryRevision stgRevisions = QSireHistoryRevision.sireHistoryRevision;
			// Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00",
			// "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgRevisions)
					.where(stgRevisions.dataCaricamento.eq(
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

}
