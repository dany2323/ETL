package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryHyperlink;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryHyperlink;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;


public class SireHistoryHyperlinkDAO {

	private static Logger logger = Logger
			.getLogger(SireHistoryHyperlinkDAO.class);

	
	private static lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryHyperlink fonteHyperlink = HistoryHyperlink.structWorkitemHyperlinks;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryHyperlink stgHyperlink = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryHyperlink.structWorkitemHyperlinks;
	private static lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryWorkitem  fonteHistoryWorkItems  = lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryWorkitem.workitem;

	public static void fillSireHistoryHyperlink(EnumWorkitemType type,
			Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision)
			throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> hyperlinks = null;

		try {

			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();
			hyperlinks = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			if (pgConnection.isClosed()) {
				if (cm != null)
					cm.closeConnection(pgConnection);
				pgConnection = cm.getConnectionSIREHistory();
			}

			SQLQuery query = new SQLQuery(pgConnection, dialect);

			hyperlinks = query.from(fonteHyperlink).join(fonteHistoryWorkItems)
					.on(fonteHistoryWorkItems.cPk
							.eq(fonteHyperlink.fkPWorkitem))
					.where(fonteHistoryWorkItems.cType.eq(type.toString()))
					.where(fonteHistoryWorkItems.cRev
							.gt(minRevisionByType.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision)).list(
							fonteHistoryWorkItems.all());

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgHyperlink);

			int batchcounter = 0;

			for (Tuple row : hyperlinks) {

				batchcounter++;
				insert.columns(

						stgHyperlink.cRole, stgHyperlink.cUrl,
						stgHyperlink.fkPWorkitem, stgHyperlink.fkUriPWorkitem
				)

						.values(
								row.get(fonteHyperlink.cRole), 
								row.get(fonteHyperlink.cUrl),
								row.get(fonteHyperlink.fkPWorkitem),
								row.get(fonteHyperlink.fkUriPWorkitem)
						).addBatch();

				if (batchcounter % DmAlmConstants.BATCH_SIZE == 0
						&& !insert.isEmpty()) {
					insert.execute();
					connOracle.commit();
					insert = new SQLInsertClause(connOracle, dialect,
							stgHyperlink);
				}

			}

			if(!insert.isEmpty()) {
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

	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stgHyperlink)
					.execute();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(OracleConnection);
			}
		}
	}
	private static SQLQuery queryConnOracle(Connection connOracle,
			PostgresTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}

}