package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryProjectgroup;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SireHistoryProjectGroupDAO {

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryProjectgroup fonteProjectGroups = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryProjectgroup.projectgroup;

	private static QSireHistoryProjectgroup stgProjectGroups = QSireHistoryProjectgroup.sireHistoryProjectgroup;

	private static Logger logger = Logger
			.getLogger(SireHistoryProjectGroupDAO.class);

	public static void fillSireHistoryProjectGroup()
			throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		List<Tuple> projectgroups = null;
		lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap fonteSireSubterraUriMap = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap.urimap;
		long nRigheInserite = 0;
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			projectgroups = new ArrayList<>();

			connOracle.setAutoCommit(false);

			OracleTemplates dialect = new OracleTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query = new SQLQuery(connOracle, dialect);

			projectgroups = query.from(fonteProjectGroups)
					.join(fonteSireSubterraUriMap)
					.on(fonteProjectGroups.cUri.castToNum(Long.class)
							.eq(fonteSireSubterraUriMap.cId))
					.list(fonteProjectGroups.cLocation,
							StringTemplate.create("0 as c_is_local"),
							fonteSireSubterraUriMap.cPk,
							fonteProjectGroups.fkUriParent,
							fonteProjectGroups.cName,
							fonteProjectGroups.cDeleted,
							fonteProjectGroups.cRev, StringTemplate.create(
									fonteSireSubterraUriMap.cPk + " as c_uri"));

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgProjectGroups);

			for (Tuple row : projectgroups) {

				String fkUriParent = row
						.get(fonteProjectGroups.fkUriParent) != null
								? (queryConnOracle(connOracle, dialect)
										.from(fonteSireSubterraUriMap)
										.where(fonteSireSubterraUriMap.cId
												.eq(Long.valueOf(row.get(
														fonteProjectGroups.fkUriParent))))
										.count() > 0
												? queryConnOracle(connOracle,
														dialect).from(
																fonteSireSubterraUriMap)
																.where(fonteSireSubterraUriMap.cId
																		.eq(Long.valueOf(
																				row.get(fonteProjectGroups.fkUriParent))))
																.list(fonteSireSubterraUriMap.cPk)
																.get(0)
												: "")
								: "";

				insert.columns(stgProjectGroups.cLocation,
						stgProjectGroups.cIsLocal, stgProjectGroups.cPk,
						stgProjectGroups.fkUriParent, stgProjectGroups.fkParent,
						stgProjectGroups.cName, stgProjectGroups.cDeleted,
						stgProjectGroups.cRev, stgProjectGroups.cUri,
						stgProjectGroups.dataCaricamento,
						stgProjectGroups.sireHistoryProjGroupPk)
						.values(row.get(fonteProjectGroups.cLocation),
								row.get(fonteProjectGroups.cIsLocal),
								row.get(fonteSireSubterraUriMap.cPk),
								fkUriParent,
								fkUriParent,
								row.get(fonteProjectGroups.cName),
								row.get(fonteProjectGroups.cDeleted),
								row.get(fonteProjectGroups.cRev),
								row.get(fonteSireSubterraUriMap.cPk),
								DataEsecuzione.getInstance()
										.getDataEsecuzione(),
								StringTemplate.create(
										"HISTORY_PROJGROUP_SEQ.nextval"))
						.addBatch();
				nRigheInserite++;
				if (!insert.isEmpty()) {
					if (nRigheInserite
							% lispa.schedulers.constant.DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						connOracle.commit();
						insert = new SQLInsertClause(connOracle, dialect,
								stgProjectGroups);
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
				cm.closeConnection(connOracle);
		}

	}

	public static long getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		try {

			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);

			max = query.from(stgProjectGroups)
					.list(stgProjectGroups.cRev.max());

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

	public static void delete() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			new SQLDeleteClause(connection, dialect, stgProjectGroups)
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	public static void recoverSireHistoryProjectGroup() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryProjectgroup stgProjectGroups = QSireHistoryProjectgroup.sireHistoryProjectgroup;
			// Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00",
			// "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgProjectGroups)
					.where(stgProjectGroups.dataCaricamento.eq(
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
	private static SQLQuery queryConnOracle(Connection connOracle,
			OracleTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}

}
