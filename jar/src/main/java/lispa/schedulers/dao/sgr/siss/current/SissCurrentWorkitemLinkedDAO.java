package lispa.schedulers.dao.sgr.siss.current;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class SissCurrentWorkitemLinkedDAO {

	private static Logger logger = Logger.getLogger(SissCurrentWorkitemLinkedDAO.class);

	public static void fillSissCurrentWorkitemLinked() throws Exception {

		ConnectionManager cm = null;
		Connection oracleConnection = null;
		Connection h2Connection = null;

		try {

			cm = ConnectionManager.getInstance();

			h2Connection = cm.getConnectionSISSCurrent();

			oracleConnection = cm.getConnectionOracle();
			oracleConnection.setAutoCommit(false);

			lispa.schedulers.queryimplementation.staging.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems stg_WorkItemLinked = lispa.schedulers.queryimplementation.staging.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;
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

				new SQLInsertClause(oracleConnection, dialect, stg_WorkItemLinked)
						.columns(stg_WorkItemLinked.cSuspect, stg_WorkItemLinked.cRole,
								stg_WorkItemLinked.fkPWorkitem,
								stg_WorkItemLinked.cRevision,
								stg_WorkItemLinked.fkUriPWorkitem,
								stg_WorkItemLinked.fkUriWorkitem,
								stg_WorkItemLinked.fkWorkitem)
						.values(el[0],
								el[1],
								el[2],
								el[3],
								el[4],
								el[5],
								el[6]
						).execute();
			}

			oracleConnection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			if (oracleConnection != null) {
				oracleConnection.rollback();
			}
			
			throw new DAOException(e);
		} finally {
			cm.closeConnection(oracleConnection);
			cm.closeConnection(h2Connection);
		}
	}
}