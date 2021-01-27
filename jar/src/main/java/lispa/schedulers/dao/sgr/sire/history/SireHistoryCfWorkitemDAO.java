package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryCfWorkitem;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Splitted_CF;
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

public class SireHistoryCfWorkitemDAO {
	private static Logger logger = Logger.getLogger(SireHistoryCfWorkitemDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryCfWorkitem fonteCFWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryCfWorkitem.sireHistoryCfWorkitem;

	private static QSireHistoryCfWorkitem stgCFWorkItems = QSireHistoryCfWorkitem.sireHistoryCfWorkitem;

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem.workitem;

	public static void deleteNotMatchingCFS() throws Exception {

		QueryManager.getInstance().executeQueryFromFile(DmAlmConstants.DELETE_NOT_MATCHING_CFS_SIRE);

	}

	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryCfWorkitem stgCFWorkItems = QSireHistoryCfWorkitem.sireHistoryCfWorkitem;

			new SQLDeleteClause(connection, dialect, stgCFWorkItems).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void deleteInDate(Timestamp date) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSireHistoryCfWorkitem stgCFWorkItems = QSireHistoryCfWorkitem.sireHistoryCfWorkitem;

			new SQLDeleteClause(connection, dialect, stgCFWorkItems).where(stgCFWorkItems.dataCaricamento.eq(date))
					.execute();

		} catch (Exception e) {

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	/**
	 * Importo tutti i CF collegati a Workitem del tipo w_type e con C_NAME uguale a
	 * CFName
	 */
	public static void fillSireHistoryCfWorkitemByWorkitemType(long minRevision, long maxRevision,
			EnumWorkitemType w_type, List<String> CFName) throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		List<Tuple> cfWorkitem = null;
		String customFieldName = null;
		lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap fonteSireSubterraUriMap = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap.urimap;

		try {
			cm = ConnectionManager.getInstance();

			cfWorkitem = new ArrayList<>();

			OracleTemplates dialect = new OracleTemplates();

			for (String c_name : CFName) {
				// evita di scaricare nuovamente eventuali CF scaricati prima
				// del deadlock,
				// riprende lo scarico solo dal CF in deadlock in poi

				customFieldName = c_name;

				connOracle = cm.getConnectionOracle();

				connOracle.setAutoCommit(true);

				SQLQuery query = new SQLQuery(connOracle, dialect);

				cfWorkitem = query.from(fonteHistoryWorkItems).join(fonteCFWorkItems)
						.on(fonteCFWorkItems.fkWorkitem.eq(fonteHistoryWorkItems.cPk))
						.where(fonteHistoryWorkItems.cType.eq(w_type.toString()))
						.where(fonteHistoryWorkItems.cRev.goe(minRevision))
						.where(fonteHistoryWorkItems.cRev.loe(maxRevision)).where(fonteCFWorkItems.cName.eq(c_name))
						.list(
								// fonteCFWorkItems.all()

								fonteCFWorkItems.cDateonlyValue, fonteCFWorkItems.cFloatValue,
								fonteCFWorkItems.cStringValue, fonteCFWorkItems.cDateValue,
								fonteCFWorkItems.cBooleanValue, fonteCFWorkItems.cName, fonteCFWorkItems.fkUriWorkitem,
								StringTemplate.create("(select c_rev from " + fonteHistoryWorkItems.getSchemaName()
										+ "." + fonteHistoryWorkItems.getTableName() + " where "
										+ fonteHistoryWorkItems.getTableName() + ".c_pk = fk_workitem) as fk_workitem"),
								fonteCFWorkItems.cLongValue, fonteCFWorkItems.cDurationtimeValue,
								fonteCFWorkItems.cCurrencyValue);

				logger.debug("CF_NAME: " + w_type.toString() + " " + c_name + "  SIZE: " + cfWorkitem.size());

				SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgCFWorkItems);
				int countBatch = 0;

				for (Tuple row : cfWorkitem) {

					countBatch++;

					String fkUriWorkitem = row.get(fonteCFWorkItems.fkUriWorkitem) != null ? (QueryUtils
							.queryConnOracle(connOracle, dialect).from(fonteSireSubterraUriMap)
							.where(fonteSireSubterraUriMap.cId
									.eq(Long.valueOf(row.get(fonteCFWorkItems.fkUriWorkitem))))
							.count() > 0
									? QueryUtils.queryConnOracle(connOracle, dialect).from(fonteSireSubterraUriMap)
											.where(fonteSireSubterraUriMap.cId
													.eq(Long.valueOf(row.get(fonteCFWorkItems.fkUriWorkitem))))
											.list(fonteSireSubterraUriMap.cPk).get(0)
									: "")
							: "";
					String fkWorkitem = fkUriWorkitem + "%"
							+ (row.toArray()[7] != null ? row.toArray()[7].toString() : "");

					insert.columns(stgCFWorkItems.cDateonlyValue, stgCFWorkItems.cFloatValue,
							stgCFWorkItems.cStringValue, stgCFWorkItems.cDateValue, stgCFWorkItems.cBooleanValue,
							stgCFWorkItems.cName, stgCFWorkItems.fkUriWorkitem, stgCFWorkItems.fkWorkitem,
							stgCFWorkItems.cLongValue, stgCFWorkItems.cDurationtimeValue, stgCFWorkItems.cCurrencyValue,
							stgCFWorkItems.dataCaricamento, stgCFWorkItems.dmalmHistoryCfWorkItemPk)
							.values(row.get(fonteCFWorkItems.cDateonlyValue), row.get(fonteCFWorkItems.cFloatValue),
									row.get(fonteCFWorkItems.cStringValue), row.get(fonteCFWorkItems.cDateValue),
									row.get(fonteCFWorkItems.cBooleanValue), row.get(fonteCFWorkItems.cName),
									fkUriWorkitem, fkWorkitem, row.get(fonteCFWorkItems.cLongValue),
									row.get(fonteCFWorkItems.cDurationtimeValue),
									row.get(fonteCFWorkItems.cCurrencyValue),
									DataEsecuzione.getInstance().getDataEsecuzione(),
									StringTemplate.create("HISTORY_CF_WORKITEM_SEQ.nextval"))
							.addBatch();

					if (!insert.isEmpty() && countBatch % DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						connOracle.commit();
						insert = new SQLInsertClause(connOracle, dialect, stgCFWorkItems);
					}

				}

				if (!insert.isEmpty()) {
					insert.execute();
					connOracle.commit();
				}
				if (cm != null) {
					cm.closeConnection(connOracle);
				}

			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connOracle);
			}
		}
	}

	public static void updateCFonWorkItem() throws Exception {
		ConnectionManager cm = null;
		Connection connOracle = null;
		Timestamp dataCaricamento = DataEsecuzione.getInstance().getDataEsecuzione();
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			String sql = null;
			PreparedStatement ps = null;

			for (Splitted_CF scf : Splitted_CF.values()) {

				switch (scf.toString()) {
				case DmAlmConstants.CF_WORKITEM_CNAME_AOID:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_AOID_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CA:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_CA_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CLASSE_DOC:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_CLASSE_DOC_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CODICE:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_CODICE_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CODINTERVENTO:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_CODINTERVENTO_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_COSTOSVILUPPO:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_COSTOSVILUPPO_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_INIZIO:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_DATA_INIZIO_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_DISP:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_DATA_DISP_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_INIZIO_EFF:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_DATA_INIZIO_EFF_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_FORNITURA:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_FORNITURA_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_FR:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_FR_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_STCHIUSO:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_STCHIUSO_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_TICKETID:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_TICKETID_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_TIPO_DOC:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_TIPO_DOC_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_VERSIONE:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_VERSIONE_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_DISPOK:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_DATA_DISPONIBILITA_EFF_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_FINE:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_DATA_FINE_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_FINE_EFF:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_DATA_FINE_EFF_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_VERSION:
					sql = QueryManager.getInstance().getQuery(DmAlmConstants.UPDATE_CF_VERSION_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, dataCaricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				}

				connOracle.commit();
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			logger.error(e.getMessage());
		} finally {
			if (cm != null) {
				cm.closeConnection(connOracle);
			}
		}

	}

	public static void recoverSireHistoryCfWorkItem() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryCfWorkitem stgCFWorkItems = QSireHistoryCfWorkitem.sireHistoryCfWorkitem;

			new SQLDeleteClause(connection, dialect, stgCFWorkItems)
					.where(stgCFWorkItems.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione()))
					.execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

	}

	public static List<String> getCustomFieldInString(String workitemCpk, String customField) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		List<String> cfWorkitem = new ArrayList<String>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connection, dialect);

			cfWorkitem = query.from(stgCFWorkItems).where(stgCFWorkItems.fkWorkitem.eq(workitemCpk))
					.where(stgCFWorkItems.cName.eq(customField)).list(stgCFWorkItems.cStringValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return cfWorkitem;
	}

	public static List<Integer> getBooleanCustomFieldInString(String workitemCpk, String customField) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> cfWorkitem = new ArrayList<Integer>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connection, dialect);

			cfWorkitem = query.from(stgCFWorkItems).where(stgCFWorkItems.fkWorkitem.eq(workitemCpk))
					.where(stgCFWorkItems.cName.eq(customField)).list(stgCFWorkItems.cBooleanValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return cfWorkitem;
	}
}