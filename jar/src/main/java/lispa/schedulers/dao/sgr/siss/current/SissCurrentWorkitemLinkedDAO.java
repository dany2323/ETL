package lispa.schedulers.dao.sgr.siss.current;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;

public class SissCurrentWorkitemLinkedDAO {

	private static Logger logger = Logger
			.getLogger(SissCurrentWorkitemLinkedDAO.class);

	public static void fillSissCurrentWorkitemLinked() throws Exception {

		ConnectionManager cm = null;
		Connection oracleConnection = null;
		Connection pgConnection = null;

		try {

			cm = ConnectionManager.getInstance();

			pgConnection = cm.getConnectionSISSCurrent();

			oracleConnection = cm.getConnectionOracle();
			oracleConnection.setAutoCommit(false);

			lispa.schedulers.queryimplementation.staging.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems workItemLinked = lispa.schedulers.queryimplementation.staging.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;
			lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems fonteWorkitemLinked = lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};
			SQLQuery query = new SQLQuery(pgConnection, dialect);

			List<Tuple> listWorkitemLinked = query.from(fonteWorkitemLinked)
					.list(fonteWorkitemLinked.all());

			logger.debug("fillSissCurrentWorkitemLinked - cfworkitems.size: "
					+ listWorkitemLinked.size());

			SQLInsertClause insert = new SQLInsertClause(oracleConnection,
					dialect, workItemLinked);
			long nRigheInserite = 0;

			for (Tuple el : listWorkitemLinked) {

				insert.columns(workItemLinked.cSuspect, workItemLinked.cRole,
						workItemLinked.fkPWorkitem, workItemLinked.cRevision,
						workItemLinked.fkUriPWorkitem,
						workItemLinked.fkUriWorkitem, workItemLinked.fkWorkitem)
						.values(el.get(fonteWorkitemLinked.cSuspect),
								el.get(fonteWorkitemLinked.cRole),
								el.get(fonteWorkitemLinked.fkPWorkitem),
								el.get(fonteWorkitemLinked.cRevision),
								el.get(fonteWorkitemLinked.fkUriPWorkitem),
								el.get(fonteWorkitemLinked.fkUriWorkitem),
								el.get(fonteWorkitemLinked.fkWorkitem))
						.addBatch();

				nRigheInserite++;

				if (!insert.isEmpty()) {
					if (nRigheInserite % DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						oracleConnection.commit();
						insert = new SQLInsertClause(oracleConnection, dialect,
								workItemLinked);
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
			if (cm != null) {
				cm.closeConnection(pgConnection);
			}
		}
	}
}