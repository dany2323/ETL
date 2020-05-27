package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryHyperlink;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryHyperlink;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SissHistoryHyperlinkDAO {

	private static Logger logger = Logger
			.getLogger(SissHistoryHyperlinkDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryHyperlink fonteHyperlink = SissHistoryHyperlink.structWorkitemHyperlinks;

	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryHyperlink stgHyperlink = QSissHistoryHyperlink.dmalmSissHistoryHyperlink;

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;

	public static void fillSissHistoryHyperlink(
			Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision)
			throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		List<Tuple> hyperlinks = null;

		try {

			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			hyperlinks = new ArrayList<>();

			connOracle.setAutoCommit(false);

			OracleTemplates dialect = new OracleTemplates();

			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType
					.values()) {

				SQLQuery query = new SQLQuery(connOracle, dialect);

				hyperlinks = query.from(fonteHyperlink)
						.join(fonteHistoryWorkItems)
						.on(fonteHistoryWorkItems.cPk
								.eq(fonteHyperlink.fkPWorkitem))
						.where(fonteHistoryWorkItems.cType.eq(type.toString()))
						.where(fonteHistoryWorkItems.cRev
								.gt(minRevisionByType.get(type)))
						.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
						.list(fonteHyperlink.all());

				SQLInsertClause insert = new SQLInsertClause(connOracle,
						dialect, stgHyperlink);

				int batchcounter = 0;

				for (Tuple row : hyperlinks) {

					batchcounter++;

					insert.columns(

							stgHyperlink.cRole, stgHyperlink.cUri,
							stgHyperlink.fkPWorkitem,
							stgHyperlink.fkUriPWorkitem,
							stgHyperlink.dataCaricamento,
							stgHyperlink.sissHistoryHyperlinkPk

					)

							.values(

									row.get(fonteHyperlink.cRole),
									row.get(fonteHyperlink.cUrl),
									row.get(fonteHyperlink.fkPWorkitem),
									row.get(fonteHyperlink.fkUriPWorkitem),
									DataEsecuzione.getInstance()
											.getDataEsecuzione(),
									StringTemplate.create(
											"HISTORY_HYPERLINK_SEQ.nextval")

							).addBatch();

					if (batchcounter % DmAlmConstants.BATCH_SIZE == 0
							&& !insert.isEmpty()) {
						insert.execute();
						connOracle.commit();
						insert = new SQLInsertClause(connOracle, dialect,
								stgHyperlink);
					}

				}

				if (!insert.isEmpty()) {
					insert.execute();
					connOracle.commit();

				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connOracle);
		}
	}
	public static void recoverSissHistoryHyperlink() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryHyperlink stgHyperlink = QSissHistoryHyperlink.dmalmSissHistoryHyperlink;
			// Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00",
			// "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgHyperlink)
					.where(stgHyperlink.dataCaricamento.eq(
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

}
