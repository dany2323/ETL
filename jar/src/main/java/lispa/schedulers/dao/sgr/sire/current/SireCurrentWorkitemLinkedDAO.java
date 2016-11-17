package lispa.schedulers.dao.sgr.sire.current;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireCurrentWorkitemLinked;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class SireCurrentWorkitemLinkedDAO {

	private static Logger logger = Logger
			.getLogger(SireCurrentWorkitemLinkedDAO.class);

	public static long fillSireCurrentWorkitemLinked() throws Exception {
		ConnectionManager cm = null;
		Connection oracleConnection = null;
		Connection h2Connection = null;
		long n_righe_inserite = 0;

		try {
			cm = ConnectionManager.getInstance();

			h2Connection = cm.getConnectionSIRECurrent();
			oracleConnection = cm.getConnectionOracle();

			oracleConnection.setAutoCommit(false);

			QSireCurrentWorkitemLinked stgProjectgroup = QSireCurrentWorkitemLinked.sireCurrentWorkitemLinked;
			lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems fonteWorkitemLinked = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;

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

			logger.debug("fillSireCurrentWorkitemLinked - cfworkitems.sizw: " + cfworkitems.size());
			
			Iterator<Tuple> i = cfworkitems.iterator();
			Object[] el = null;

			while (i.hasNext()) {

				el = ((Tuple) i.next()).toArray();

				new SQLInsertClause(oracleConnection, dialect, stgProjectgroup)
						.columns(stgProjectgroup.cSuspect,
								stgProjectgroup.cRole,
								stgProjectgroup.fkPWorkitem,
								stgProjectgroup.cRevision,
								stgProjectgroup.fkUriPWorkitem,
								stgProjectgroup.fkUriWorkitem,
								stgProjectgroup.fkWorkitem,
								stgProjectgroup.dataCaricamento,
								stgProjectgroup.dmalmCurrentWorkitemLinkedPk)
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
				cm.closeConnection(h2Connection);
			}
		}

		return n_righe_inserite;
	}

	public static void delete(Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireCurrentWorkitemLinked stgProjectgroup = QSireCurrentWorkitemLinked.sireCurrentWorkitemLinked;

			new SQLDeleteClause(connection, dialect, stgProjectgroup).where(
					stgProjectgroup.dataCaricamento.lt(dataEsecuzione).or(
							stgProjectgroup.dataCaricamento.eq(DataEsecuzione
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

	public static void recoverSireCurrentWorkitemLinked() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSireCurrentWorkitemLinked stgProjectgroup = QSireCurrentWorkitemLinked.sireCurrentWorkitemLinked;

			new SQLDeleteClause(connection, dialect, stgProjectgroup).where(
					stgProjectgroup.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
