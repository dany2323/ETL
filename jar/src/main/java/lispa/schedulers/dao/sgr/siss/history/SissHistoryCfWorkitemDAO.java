package lispa.schedulers.dao.sgr.siss.history;

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
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryCfWorkitem;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryCfWorkitem;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Splitted_CF;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SissHistoryCfWorkitemDAO {
	private static Logger logger = Logger
			.getLogger(SissHistoryCfWorkitemDAO.class);

	private static SissHistoryCfWorkitem fonteCFWorkItems = SissHistoryCfWorkitem.cfWorkitem;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap fonteSireSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap.urimap;
	private static QSissHistoryCfWorkitem stgCFWorkItems = QSissHistoryCfWorkitem.sissHistoryCfWorkitem;

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;

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
			QSissHistoryCfWorkitem stgCFWorkItems = QSissHistoryCfWorkitem.sissHistoryCfWorkitem;

			new SQLDeleteClause(connection, dialect, stgCFWorkItems).where(
					stgCFWorkItems.dataCaricamento.lt(dataEsecuzione).or(
							stgCFWorkItems.dataCaricamento.eq(DataEsecuzione
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

	public static void deleteInDate(Timestamp date) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSissHistoryCfWorkitem stgCFWorkItems = QSissHistoryCfWorkitem.sissHistoryCfWorkitem;

			new SQLDeleteClause(connection, dialect, stgCFWorkItems).where(
					stgCFWorkItems.dataCaricamento.eq(date)).execute();

		} catch (Exception e) {

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void fillSissHistoryCfWorkitemByWorkitemType(
			long minRevision, long maxRevision, Workitem_Type w_type,
			List<String> CFName) throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> cfWorkitem = null;
		String customFieldName = null;
		boolean scaricaCF = false;

		try {
			cm = ConnectionManager.getInstance();
			cfWorkitem = new ArrayList<Tuple>();

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			for (String c_name : CFName) {
				//evita di scaricare nuovamente eventuali CF scaricati prima del deadlock, 
				// riprende lo scarico solo dal CF in deadlock in poi
				if(ErrorManager.getInstance().cfNameDeadLock() == null || c_name.equals(ErrorManager.getInstance().cfNameDeadLock())) {
					scaricaCF = true;
				}
				
				if(scaricaCF) {
					customFieldName = c_name;
					
					connOracle = cm.getConnectionOracle();
					pgConnection = cm.getConnectionSISSHistory();

					connOracle.setAutoCommit(true);

					SQLQuery query = new SQLQuery(pgConnection, dialect);

					cfWorkitem = query
							.from(fonteHistoryWorkItems)
							.join(fonteCFWorkItems)
							.on(fonteCFWorkItems.fkWorkitem
									.eq(fonteHistoryWorkItems.cPk))
							.where(fonteHistoryWorkItems.cType.eq(w_type.toString()))
							.where(fonteHistoryWorkItems.cRev.gt(minRevision))
							.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
							.where(fonteCFWorkItems.cName.eq(c_name))
							.list(
//fonteCFWorkItems.all()
									
									fonteCFWorkItems.cDateonlyValue,
									fonteCFWorkItems.cFloatValue,
									fonteCFWorkItems.cStringValue,
									fonteCFWorkItems.cDateValue,
									fonteCFWorkItems.cBooleanValue,
									fonteCFWorkItems.cName,
									StringTemplate.create("(SELECT a.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " a WHERE a.c_id = " +  fonteCFWorkItems.fkUriWorkitem + ") as fk_uri_workitem"),
									StringTemplate.create("(SELECT b.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " b WHERE b.c_id = " +  fonteCFWorkItems.fkUriWorkitem + ") as fk_workitem"),
									fonteCFWorkItems.cLongValue,
									fonteCFWorkItems.cDurationtimeValue,
									fonteCFWorkItems.cCurrencyValue
									);

					logger.debug("CF_NAME: " + w_type.toString() + " " + c_name
							+ "  SIZE: " + cfWorkitem.size());

					SQLInsertClause insert = new SQLInsertClause(connOracle,
							dialect, stgCFWorkItems);
					int count_batch = 0;

					for (Tuple row : cfWorkitem) {

						Object[] vals = row.toArray();
						
						count_batch++;

						insert.columns(
								stgCFWorkItems.cDateonlyValue,
								stgCFWorkItems.cFloatValue,
								stgCFWorkItems.cStringValue,
								stgCFWorkItems.cDateValue,
								stgCFWorkItems.cBooleanValue, 
								stgCFWorkItems.cName,
								stgCFWorkItems.fkUriWorkitem,
								stgCFWorkItems.fkWorkitem,
								stgCFWorkItems.cLongValue,
								stgCFWorkItems.cDurationtimeValue,
								stgCFWorkItems.cCurrencyValue,
								stgCFWorkItems.dataCaricamento,
								stgCFWorkItems.dmalmCfWorkitemPk
								)
								.values(
										vals[0],
										vals[1],
										vals[2],
										vals[3],
										vals[4],
										vals[5],
										vals[6],
										vals[7],
										vals[8],
										vals[9],
										vals[10],

										/*
										row.get(fonteCFWorkItems.cDateonlyValue),
										row.get(fonteCFWorkItems.cFloatValue),
										row.get(fonteCFWorkItems.cStringValue),
										row.get(fonteCFWorkItems.cDateValue),
										row.get(fonteCFWorkItems.cBooleanValue),
										row.get(fonteCFWorkItems.cName),
										row.get(fonteCFWorkItems.fkUriWorkitem),
										row.get(fonteCFWorkItems.fkWorkitem),
										row.get(fonteCFWorkItems.cLongValue),
										row.get(fonteCFWorkItems.cDurationtimeValue),
										row.get(fonteCFWorkItems.cCurrencyValue),
										*/
										DataEsecuzione.getInstance()
												.getDataEsecuzione(),
										StringTemplate
												.create("HISTORY_CF_WORKITEM_SEQ.nextval"))
								.addBatch();

						if (!insert.isEmpty()
								&& count_batch % DmAlmConstants.BATCH_SIZE == 0) {
							insert.execute();
							insert = new SQLInsertClause(connOracle, dialect,
									stgCFWorkItems);
						}

					}

					if (!insert.isEmpty()) {
						insert.execute();
					}

					if (cm != null) {
						cm.closeConnection(pgConnection);
					}
					if (cm != null) {
						cm.closeConnection(connOracle);
					}
				}
			}
		} catch (Exception e) {
			Throwable cause = e;
			while (cause.getCause() != null)
				cause = cause.getCause();
			String message = cause.getMessage();
			if (StringUtils.findRegex(message, DmalmRegex.REGEXDEADLOCK)) {
				ErrorManager.getInstance().exceptionCFDeadlock(false, e, customFieldName);
			} else {
				ErrorManager.getInstance().exceptionOccurred(true, e);
			}

		} finally {
			if (cm != null) {
				cm.closeConnection(pgConnection);
			}
			if (cm != null) {
				cm.closeConnection(connOracle);
			}
		}
	}

	public static void deleteNotMatchingCFS() throws Exception {

		QueryManager.getInstance().executeQueryFromFile(
				DmAlmConstants.DELETE_NOT_MATCHING_CFS_SISS);

	}

	public static void updateCFonWorkItem() throws DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Timestamp data_caricamento = DataEsecuzione.getInstance()
				.getDataEsecuzione();
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			String sql = null;
			PreparedStatement ps = null;

			for (Splitted_CF scf : Splitted_CF.values()) {
				switch (scf.toString()) {
				case DmAlmConstants.CF_WORKITEM_CNAME_AOID:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_AOID_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CA:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_CA_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CLASSE_DOC:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_CLASSE_DOC_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CODICE:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_CODICE_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CODINTERVENTO:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_CODINTERVENTO_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_COSTOSVILUPPO:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_COSTOSVILUPPO_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_INIZIO:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_DATA_INIZIO_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_DISP:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_DATA_DISP_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_INIZIO_EFF:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_DATA_INIZIO_EFF_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_FORNITURA:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_FORNITURA_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_FR:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_FR_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_STCHIUSO:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_STCHIUSO_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_TICKETID:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_TICKETID_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_TIPO_DOC:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_TIPO_DOC_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_VERSIONE:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_VERSIONE_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_DISPOK:
					sql = QueryManager
							.getInstance()
							.getQuery(
									DmAlmConstants.UPDATE_CF_DATA_DISPONIBILITA_EFF_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_FINE:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_DATA_FINE_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_FINE_EFF:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_DATA_FINE_EFF_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_VERSION:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_VERSION_SISS);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SISS]: " + scf.toString());
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

	public static void recoverSissHistoryCfWorkItem() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryCfWorkitem stgCFWorkItems = QSissHistoryCfWorkitem.sissHistoryCfWorkitem;

			new SQLDeleteClause(connection, dialect, stgCFWorkItems).where(
					stgCFWorkItems.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();
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

	public static List<String> getCustomFieldInString(String workitemCpk,
			String customField) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		List<String> cfWorkitem = new ArrayList<String>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connection, dialect);

			cfWorkitem = query.from(stgCFWorkItems)
					.where(stgCFWorkItems.fkWorkitem.eq(workitemCpk))
					.where(stgCFWorkItems.cName.eq(customField))
					.list(stgCFWorkItems.cStringValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return cfWorkitem;
	}
	
	public static List<Integer> getBooleanCustomFieldInString(String workitemCpk,
			String customField) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> cfWorkitem = new ArrayList<Integer>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connection, dialect);

			cfWorkitem = query.from(stgCFWorkItems)
					.where(stgCFWorkItems.fkWorkitem.eq(workitemCpk))
					.where(stgCFWorkItems.cName.eq(customField))
					.list(stgCFWorkItems.cBooleanValue);
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