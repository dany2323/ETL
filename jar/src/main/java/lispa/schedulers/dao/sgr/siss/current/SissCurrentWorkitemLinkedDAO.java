package lispa.schedulers.dao.sgr.siss.current;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.siss.current.QSissCurrentWorkitemLinked;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class SissCurrentWorkitemLinkedDAO {

	private static Logger logger = Logger
			.getLogger(SissCurrentWorkitemLinkedDAO.class);

	public static void delete(Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};
			QSissCurrentWorkitemLinked workItemLinked = QSissCurrentWorkitemLinked.sissCurrentWorkitemLinked;

			new SQLDeleteClause(connection, dialect, workItemLinked).where(
					workItemLinked.dataCaricamento.lt(dataEsecuzione).or(
							workItemLinked.dataCaricamento.eq(DataEsecuzione
									.getInstance().getDataEsecuzione())))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverSissCurrentWorkitemLinked() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSissCurrentWorkitemLinked workItemLinked = QSissCurrentWorkitemLinked.sissCurrentWorkitemLinked;

			new SQLDeleteClause(connection, dialect, workItemLinked).where(
					workItemLinked.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void fillSissCurrentWorkitemLinked() throws Exception {

		ConnectionManager cm = null;
		Connection oracleConnection = null;
		long nRigheInserite=0;
		try {

			cm = ConnectionManager.getInstance();

			oracleConnection = cm.getConnectionOracle();
			oracleConnection.setAutoCommit(false);

			QSissCurrentWorkitemLinked stgSissCurrentWorkLinked = QSissCurrentWorkitemLinked.sissCurrentWorkitemLinked;
			lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems fonteWorkitemLinked = lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;

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
					dialect, stgSissCurrentWorkLinked);

			for (Tuple el : sireCurrentWorkitem) {

				insert.columns(stgSissCurrentWorkLinked.cSuspect,
						stgSissCurrentWorkLinked.cRole,
						stgSissCurrentWorkLinked.fkPWorkitem,
						stgSissCurrentWorkLinked.cRevision,
						stgSissCurrentWorkLinked.fkUriPWorkitem,
						stgSissCurrentWorkLinked.fkUriWorkitem,
						stgSissCurrentWorkLinked.fkWorkitem,
						stgSissCurrentWorkLinked.dataCaricamento,
						stgSissCurrentWorkLinked.dmalmCurrentWorkitemLinkedPk)
						.values(el.get(fonteWorkitemLinked.cSuspect),
								el.get(fonteWorkitemLinked.cRole),
								el.get(fonteWorkitemLinked.fkPWorkitem),
								el.get(fonteWorkitemLinked.cRevision),
								el.get(fonteWorkitemLinked.fkUriPWorkitem),
								el.get(stgSissCurrentWorkLinked.fkUriWorkitem),
								el.get(stgSissCurrentWorkLinked.fkWorkitem),
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
								stgSissCurrentWorkLinked);
					}
				}

			}
			if (!insert.isEmpty()) {
				insert.execute();
				oracleConnection.commit();
			}


		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			if (oracleConnection != null) {
				oracleConnection.rollback();
			}
			
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(oracleConnection);
			}
		}

	}
}