package lispa.schedulers.dao.sgr.siss.current;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.siss.current.QSissDmalmCurrentSubterraUriMap;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SissCurrentSubterraUriMapDAO {

	private static Logger logger = Logger
			.getLogger(SissCurrentSubterraUriMapDAO.class);

	public static long fillSissCurrentSubterraUriMap() throws Exception {
		ConnectionManager cm = null;
		Connection oracleConnection = null;
		Connection pgConnection = null;
		long nRigheInserite = 0;

		try {
			cm = ConnectionManager.getInstance();

			pgConnection = cm.getConnectionSISSCurrent();
			oracleConnection = cm.getConnectionOracle();

			oracleConnection.setAutoCommit(false);

			QSissDmalmCurrentSubterraUriMap stgSubterra = QSissDmalmCurrentSubterraUriMap.currentSubterraUriMap;
			lispa.schedulers.queryimplementation.fonte.sgr.current.CurrentSubterraUriMap fontesissSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.current.CurrentSubterraUriMap.urimap; 

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};
			SQLQuery query = new SQLQuery(pgConnection, dialect);

			List<Tuple> subterra = query.from(fontesissSubterraUriMap).list(fontesissSubterraUriMap.all());

			logger.debug("fillSissCurrentSubterraUriMap - subterra.size: " + subterra.size());
			
			SQLInsertClause insert = new SQLInsertClause(oracleConnection, dialect, stgSubterra);
			
			for(Tuple row:subterra) {


				insert.columns(stgSubterra.cPk,
							   stgSubterra.cId)
						.values(row.get(fontesissSubterraUriMap.cPk),
								row.get(fontesissSubterraUriMap.cId))
						.addBatch();

				nRigheInserite++;

				if (!insert.isEmpty()) {
					if (nRigheInserite % DmAlmConstants.BATCH_SIZE_PG == 0) {
						insert.execute();
						oracleConnection.commit();
						insert = new SQLInsertClause(oracleConnection, dialect, stgSubterra);
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
			if (cm != null) {
				cm.closeConnection(pgConnection);
			}
		}

		return nRigheInserite;
	}

	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			logger.debug("START Eliminazione dati SUBTERRA_URI SISS");
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissDmalmCurrentSubterraUriMap stgSubterra = QSissDmalmCurrentSubterraUriMap.currentSubterraUriMap;

			new SQLDeleteClause(connection, dialect, stgSubterra).where().execute();
			logger.debug("STOP Eliminazione dati SUBTERRA_URI SISS");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
