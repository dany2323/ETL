package lispa.schedulers.dao.sgr.siss.current;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

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
		Connection h2Connection = null;

		try {

			cm = ConnectionManager.getInstance();

			h2Connection = cm.getConnectionSISSCurrent();

			oracleConnection = cm.getConnectionOracle();
			oracleConnection.setAutoCommit(false);

			QSissCurrentWorkitemLinked workItemLinked = QSissCurrentWorkitemLinked.sissCurrentWorkitemLinked;
			lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems fonteWorkitemLinked = lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};
			SQLQuery query = new SQLQuery(h2Connection, dialect);

			List<Tuple> cfworkitems = query.from(fonteWorkitemLinked).list(
					new QTuple(
							StringTemplate.create("CASEWHEN ("
									+ fonteWorkitemLinked.cSuspect
									+ "= 'true', 1,0 )"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkitemLinked.cRole
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkitemLinked.fkPWorkitem
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkitemLinked.cRevision
											+ ",0,4000)"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkitemLinked.fkUriPWorkitem
									+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkitemLinked.fkUriWorkitem
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkitemLinked.fkWorkitem
											+ ",0,4000)")));

			logger.debug("fillSissCurrentWorkitemLinked - cfworkitems.sizw: "
					+ cfworkitems.size());

			Iterator<Tuple> i = cfworkitems.iterator();
			Object[] el = null;

			while (i.hasNext()) {

				el = ((Tuple) i.next()).toArray();

				new SQLInsertClause(oracleConnection, dialect, workItemLinked)
						.columns(workItemLinked.cSuspect, workItemLinked.cRole,
								workItemLinked.fkPWorkitem,
								workItemLinked.cRevision,
								workItemLinked.fkUriPWorkitem,
								workItemLinked.fkUriWorkitem,
								workItemLinked.fkWorkitem,
								workItemLinked.dataCaricamento,
								workItemLinked.dmalmCurrentWorkitemLinkedPk)
						.values(el[0],
								el[1],
								el[2],
								el[3],
								el[4],
								el[5],
								el[6],
								DataEsecuzione.getInstance()
										.getDataEsecuzione(),
								StringTemplate
										.create("CURRENT_WORK_LINKED_SEQ.nextval"))
						.execute();

			}

			oracleConnection.commit();

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
			if (cm != null) {
				cm.closeConnection(h2Connection);
			}
		}

	}
}