package lispa.schedulers.dao.sgr.sire.current;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireCurrentWorkitemLinked;

public class SireCurrentWorkitemLinkedDAO {

	private static Logger logger = Logger
			.getLogger(SireCurrentWorkitemLinkedDAO.class);

	public static long fillSireCurrentWorkitemLinked() throws Exception {
		ConnectionManager cm = null;
		Connection oracleConnection = null;
		long nRigheInserite = 0;

		try {
			cm = ConnectionManager.getInstance();

			oracleConnection = cm.getConnectionOracle();

			oracleConnection.setAutoCommit(false);

			QSireCurrentWorkitemLinked stgSireCurrentWorkLinked = QSireCurrentWorkitemLinked.sireCurrentWorkitemLinked;
			lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems fonteWorkitemLinked = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};
			SQLQuery query = new SQLQuery(oracleConnection, dialect);

			List<Tuple> sireCurrentWorkitem = query.from(fonteWorkitemLinked)
					.list(fonteWorkitemLinked.all());

			logger.debug("fillSireCurrentWorkitemLinked - size: "
					+ sireCurrentWorkitem.size());

			SQLInsertClause insert = new SQLInsertClause(oracleConnection,
					dialect, stgSireCurrentWorkLinked);

			for (Tuple el : sireCurrentWorkitem) {

				insert.columns(
						stgSireCurrentWorkLinked.cSuspect,
						stgSireCurrentWorkLinked.cRole,
						stgSireCurrentWorkLinked.fkPWorkitem,
						stgSireCurrentWorkLinked.cRevision,
						stgSireCurrentWorkLinked.fkUriPWorkitem,
						stgSireCurrentWorkLinked.fkUriWorkitem,
						stgSireCurrentWorkLinked.fkWorkitem,
						stgSireCurrentWorkLinked.dataCaricamento,
						stgSireCurrentWorkLinked.dmalmCurrentWorkitemLinkedPk)
						.values(
								el.get(fonteWorkitemLinked.cSuspect),
								el.get(fonteWorkitemLinked.cRole),
								el.get(fonteWorkitemLinked.fkPWorkitem),
								el.get(fonteWorkitemLinked.cRevision),
								el.get(fonteWorkitemLinked.fkUriPWorkitem),
								el.get(stgSireCurrentWorkLinked.fkUriWorkitem),
								el.get(stgSireCurrentWorkLinked.fkWorkitem),
								DataEsecuzione.getInstance()
										.getDataEsecuzione(),
								StringTemplate.create(
										"CURRENT_WORK_LINKED_SEQ.nextval"))
						.addBatch();

				nRigheInserite++;

				if (!insert.isEmpty()) {
					if (nRigheInserite % DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						oracleConnection.commit();
						insert = new SQLInsertClause(oracleConnection, dialect,
								stgSireCurrentWorkLinked);
					}
				}

			}
			if (!insert.isEmpty()) {
				insert.execute();
				oracleConnection.commit();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			nRigheInserite = 0;
			if (oracleConnection != null) {
				oracleConnection.rollback();
			}

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(oracleConnection);
			}
		}

		return nRigheInserite;
	}

	public static void delete(Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireCurrentWorkitemLinked stgProjectgroup = QSireCurrentWorkitemLinked.sireCurrentWorkitemLinked;

			new SQLDeleteClause(connection, dialect, stgProjectgroup)
					.where(stgProjectgroup.dataCaricamento.lt(dataEsecuzione)
							.or(stgProjectgroup.dataCaricamento
									.eq(DataEsecuzione.getInstance()
											.getDataEsecuzione())))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverSireCurrentWorkitemLinked() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSireCurrentWorkitemLinked stgProjectgroup = QSireCurrentWorkitemLinked.sireCurrentWorkitemLinked;

			new SQLDeleteClause(connection, dialect, stgProjectgroup)
					.where(stgProjectgroup.dataCaricamento.eq(
							DataEsecuzione.getInstance().getDataEsecuzione()))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
