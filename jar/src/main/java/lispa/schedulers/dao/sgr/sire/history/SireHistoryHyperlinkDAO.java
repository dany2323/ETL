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
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryHyperlink;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryHyperlink;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;


public class SireHistoryHyperlinkDAO {

	private static Logger logger = Logger
			.getLogger(SireHistoryHyperlinkDAO.class);

	
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryHyperlink fonteHyperlink = SireHistoryHyperlink.structWorkitemHyperlinks;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryHyperlink stgHyperlink = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryHyperlink.structWorkitemHyperlinks;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem  fonteHistoryWorkItems  = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem.workitem;

	public static void fillSireHistoryHyperlink(EnumWorkitemType type,
			Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision)
			throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> hyperlinks = null;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;

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

							fonteHyperlink.cRole, fonteHyperlink.cUrl,
							fonteHyperlink.fkUriPWorkitem,
							StringTemplate.create("(select c_rev from "
									+ lispa.schedulers.manager.DmAlmConstants
											.GetPolarionSchemaSireHistory()
									+ ".workitem where workitem.c_pk = fk_p_workitem) as fk_p_workitem"));

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgHyperlink);

			int batchcounter = 0;

			for (Tuple row : hyperlinks) {

				batchcounter++;

				Object[] val = row.toArray();
				String fkUriPWorkitem = val[2] != null
						? (queryConnOracle(connOracle, dialect)
								.from(stgSubterra)
								.where(stgSubterra.cId
										.eq(Long.valueOf(val[2].toString())))
								.where(stgSubterra.cRepo.eq(
										lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
								.count() > 0
										? queryConnOracle(connOracle, dialect)
												.from(stgSubterra)
												.where(stgSubterra.cId
														.eq(Long.valueOf(val[2]
																.toString())))
												.where(stgSubterra.cRepo.eq(
														lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
												.list(stgSubterra.cPk).get(0)
										: "")
						: "";
				String fkPWorkitem = fkUriPWorkitem + "%"
						+ (val[3] != null ? val[3].toString() : "");

				insert.columns(

						stgHyperlink.cRole, stgHyperlink.cUrl,
						stgHyperlink.fkPWorkitem, stgHyperlink.fkUriPWorkitem
				)

						.values(val[0], val[1], fkPWorkitem, fkUriPWorkitem
						).addBatch();

				if (batchcounter % DmAlmConstants.BATCH_SIZE == 0
						&& !insert.isEmpty()) {
					insert.execute();
					insert = new SQLInsertClause(connOracle, dialect,
							stgHyperlink);
				}

			}

			if(!insert.isEmpty()) {
				insert.execute();
			}

			connOracle.commit();

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