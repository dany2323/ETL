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
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryCfWorkitem;
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
import com.mysema.query.types.expr.StringExpression;
import com.mysema.query.types.template.StringTemplate;

public class SireHistoryCfWorkitemDAO {
	private static Logger logger = Logger
			.getLogger(SireHistoryCfWorkitemDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryCfWorkitem fonteCFWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryCfWorkitem.cfWorkitem;
	private static QSireHistoryCfWorkitem stgCFWorkItems = QSireHistoryCfWorkitem.sireHistoryCfWorkitem;

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem.workitem;

	public static void deleteNotMatchingCFS() throws Exception {

		QueryManager.getInstance().executeQueryFromFile(
				DmAlmConstants.DELETE_NOT_MATCHING_CFS_SIRE);

	}

	public static void delete(Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryCfWorkitem stgCFWorkItems = QSireHistoryCfWorkitem.sireHistoryCfWorkitem;

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

			QSireHistoryCfWorkitem stgCFWorkItems = QSireHistoryCfWorkitem.sireHistoryCfWorkitem;

			new SQLDeleteClause(connection, dialect, stgCFWorkItems).where(
					stgCFWorkItems.dataCaricamento.eq(date)).execute();

		} catch (Exception e) {

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
	
	/**
	 * Importo tutti i CF collegati a Workitem del tipo w_type e con C_NAME
	 * uguale a CFName
	 */
	public static void fillSireHistoryCfWorkitemByWorkitemType(
			long minRevision, long maxRevision, Workitem_Type w_type,
			List<String> CFName) throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> cfWorkitem = null;
		String customFieldName = null;
		boolean scaricaCF = false;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;

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
					pgConnection = cm.getConnectionSIREHistory();

					connOracle.setAutoCommit(true);

					SQLQuery query = new SQLQuery(pgConnection, dialect);
					
					cfWorkitem = query
							.from(fonteHistoryWorkItems)
							.join(fonteCFWorkItems)
							.on(fonteCFWorkItems.fkWorkitem.eq(fonteHistoryWorkItems.cPk))
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
									fonteCFWorkItems.fkUriWorkitem,
									StringTemplate.create("(select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSireHistory() + ".workitem where workitem.c_pk = fk_workitem) as fk_workitem"),
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

						count_batch++;
						Object[] val = row.toArray();
						SQLQuery queryConnOracle = new SQLQuery(connOracle, dialect);
						String fkUriWorkitem = queryConnOracle.from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(val[6].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).list(stgSubterra.cPk).get(0);
						String fkWorkitem = fkUriWorkitem+"%"+val[7];
						
						//Applico il cast a timespent solo se esistono dei valori data 
						StringExpression dateOnlyValue = null;
						if(val[0] != null) {
							dateOnlyValue = StringTemplate.create("to_timestamp('"+val[0]+"', 'YYYY-MM-DD')");
						}
						StringExpression dateValue = null;
						if(val[3] != null) {
							dateValue = StringTemplate.create("to_timestamp('"+val[3]+"', 'YYYY-MM-DD HH24:MI:SS.FF')");
						}

						insert.columns(
								stgCFWorkItems.cDateonlyValue,
								stgCFWorkItems.cFloatValue,
								stgCFWorkItems.cStringValue,
								stgCFWorkItems.cDateValue,
								stgCFWorkItems.cBooleanValue, stgCFWorkItems.cName,
								stgCFWorkItems.fkUriWorkitem,
								stgCFWorkItems.fkWorkitem,
								stgCFWorkItems.cLongValue,
								stgCFWorkItems.cDurationtimeValue,
								stgCFWorkItems.cCurrencyValue,
								stgCFWorkItems.dataCaricamento,
								stgCFWorkItems.dmalmHistoryCfWorkItemPk)
								.values(
										dateOnlyValue,
										val[1],
										val[2],
										dateValue,
										val[4],
										val[5],
										fkUriWorkitem,
										fkWorkitem,
										val[8],
										val[9],
										val[10],
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
			
			//connOracle.commit();
			
			ErrorManager.getInstance().resetCFDeadlock();
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

	public static void updateCFonWorkItem() throws Exception {
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
							DmAlmConstants.UPDATE_CF_AOID_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CA:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_CA_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CLASSE_DOC:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_CLASSE_DOC_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CODICE:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_CODICE_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_CODINTERVENTO:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_CODINTERVENTO_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_COSTOSVILUPPO:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_COSTOSVILUPPO_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_INIZIO:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_DATA_INIZIO_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_DISP:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_DATA_DISP_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_INIZIO_EFF:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_DATA_INIZIO_EFF_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_FORNITURA:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_FORNITURA_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_FR:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_FR_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_STCHIUSO:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_STCHIUSO_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_TICKETID:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_TICKETID_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_TIPO_DOC:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_TIPO_DOC_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_VERSIONE:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_VERSIONE_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_DISPOK:
					sql = QueryManager
							.getInstance()
							.getQuery(
									DmAlmConstants.UPDATE_CF_DATA_DISPONIBILITA_EFF_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_FINE:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_DATA_FINE_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_DATA_FINE_EFF:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_DATA_FINE_EFF_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
					ps.executeUpdate();
					ps.close();
					logger.debug("[Update done SIRE]: " + scf.toString());
					break;
				case DmAlmConstants.CF_WORKITEM_CNAME_VERSION:
					sql = QueryManager.getInstance().getQuery(
							DmAlmConstants.UPDATE_CF_VERSION_SIRE);
					ps = connOracle.prepareStatement(sql);
					ps.setTimestamp(1, data_caricamento);
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