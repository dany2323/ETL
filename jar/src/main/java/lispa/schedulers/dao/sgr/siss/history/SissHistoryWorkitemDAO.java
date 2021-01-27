package lispa.schedulers.dao.sgr.siss.history;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryWorkitem;
import lispa.schedulers.queryimplementation.target.QDmalmStatoWorkitem;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class SissHistoryWorkitemDAO {

	private static Logger logger = Logger
			.getLogger(SissHistoryWorkitemDAO.class);

	private static QSissHistoryWorkitem stgWorkItems = QSissHistoryWorkitem.sissHistoryWorkitem;

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;

	private static Timestamp dataEsecuzione = DataEsecuzione.getInstance()
			.getDataEsecuzione();

	public static void delete(Timestamp dataEsecuzioneDeleted) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List <Integer> listPk = new ArrayList<>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			// elimina le righe vecchie dallo Staging lasciando la max(revision)
			// per ogni tipologia di workitem
			// sono inoltre lasciate le revision relative a:
			// 'chiuso', 'chiuso_falso', 'in_esercizio', 'in_esecuzione', 'completo', 'eseguito'
			// i "defect" non sono eliminati altrimenti la query non riesce a calcolare la data chiusura
			// nei casi di riapertura e nuova chiusura
			String subSql=QueryManager
					.getInstance()
					.getQuery(
							DmAlmConfigReaderProperties.SQL_GET_MAX_PK_WORKITEM_TYPE_SISS);
			
			try(PreparedStatement ps2=connection.prepareStatement(subSql);){
	
				try(ResultSet rs = ps2.executeQuery();){
					while (rs.next()) {
						
						listPk.add(rs.getInt(1));
						
					}
					int numberInParameters = listPk.size();
					String parameters = "";
					while (numberInParameters > 0) {
						parameters += "?,";
						numberInParameters--;
					}
					parameters = parameters.substring(0, parameters.length()-1);
					String query=QueryManager
					.getInstance()
					.getQuery(
							DmAlmConfigReaderProperties.SQL_DELETE_SISS_HISTORY_WORKITEM);
					String sql = query.replace("!", parameters);
					try(PreparedStatement ps = connection.prepareStatement(sql);){
						ps.setTimestamp(1, dataEsecuzioneDeleted);
						//Array array=connection.createArrayOf("NUMBER", listPk.toArray());
						for (int i=0;i<listPk.size();i++) {
							ps.setInt(i+2, listPk.get(i));
						}
						ps.setTimestamp(listPk.size()+2, DataEsecuzione.getInstance().getDataEsecuzione());
						
						ps.execute();
						ps.close();
			
						connection.commit();
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}


	public static Map<EnumWorkitemType, Long> getMinRevisionByType()
			throws Exception {

		HashMap<EnumWorkitemType, Long> map = new HashMap<EnumWorkitemType, Long>();

		SQLTemplates dialect = new HSQLDBTemplates();

		ConnectionManager cm = null;
		Connection oracle = null;

		try {
			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {

				List<Long> max = new ArrayList<Long>();

				cm = ConnectionManager.getInstance();
				oracle = cm.getConnectionOracle();

				SQLQuery query = new SQLQuery(oracle, dialect);

				max = query.from(stgWorkItems)
						.where(stgWorkItems.cType.eq(type.toString()))
						.list(stgWorkItems.cRev.max());

				if (max == null || max.size() == 0 || max.get(0) == null) {
					map.put(type, 0L);
				} else {
					map.put(type, max.get(0).longValue());
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(oracle);
			}
		}

		return map;

	}

	public static void deleteInDate(Timestamp date) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSissHistoryWorkitem stgWorkItems = QSissHistoryWorkitem.sissHistoryWorkitem;

			new SQLDeleteClause(connection, dialect, stgWorkItems).where(
					stgWorkItems.dataCaricamento.eq(date)).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void dropIndexes() {

		ConnectionManager cm = null;
		Connection connOracle = null;
		List<String> drops = new ArrayList<String>();

		try {

			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();

			String drop1 = "DROP INDEX SISS_HISTORY_WORKITEM_IDX_1";
			drops.add(drop1);
			String drop2 = "DROP INDEX SISS_HISTORY_WORKITEM_IDX_3";
			drops.add(drop2);
			String drop4 = "DROP INDEX SISS_HISTORY_WORKITEM_IDX_4";
			drops.add(drop4);
			String drop5 = "DROP INDEX SISS_HISTORY_WORKITEM_IDX_5";
			drops.add(drop5);
			String drop6 = "DROP INDEX SISS_HISTORY_WORKITEM_IDX_6";
			drops.add(drop6);
			String drop7 = "DROP INDEX SISS_HISTORY_WORKITEM_IDX_7";
			drops.add(drop7);

			String drop8 = "DROP INDEX SISS_HISTORY_CF_WORKITEM_IDX_1";
			drops.add(drop8);
			String drop9 = "DROP INDEX SISS_HISTORY_CF_WORKITEM_IDX_2";
			drops.add(drop9);
			String drop10 = "DROP INDEX SISS_HISTORY_CF_WORKITEM_IDX_4";
			drops.add(drop10);

			for (String drop : drops) {

				try(PreparedStatement ps = connOracle.prepareStatement(drop);){
				ps.execute();
				ps.close();
				}
			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

		} finally {

			try {
				if (cm != null) {
					cm.closeConnection(connOracle);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		}

	}

	public static void rebuildIndexes() {

		ConnectionManager cm = null;
		Connection connOracle = null;
		List<String> rebuilds = new ArrayList<String>();

		try {

			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();

			String rebuild1 = "CREATE INDEX SISS_HISTORY_WORKITEM_IDX_1 ON DMALM_SISS_HISTORY_WORKITEM(DATA_CARICAMENTO)";
			rebuilds.add(rebuild1);
			String rebuild2 = "CREATE INDEX SISS_HISTORY_WORKITEM_IDX_3 ON DMALM_SISS_HISTORY_WORKITEM(C_URI)";
			rebuilds.add(rebuild2);
			String rebuild4 = "CREATE INDEX SISS_HISTORY_WORKITEM_IDX_4 ON DMALM_SISS_HISTORY_WORKITEM(C_STATUS)";
			rebuilds.add(rebuild4);
			String rebuild5 = "CREATE INDEX SISS_HISTORY_WORKITEM_IDX_5 ON DMALM_SISS_HISTORY_WORKITEM(FK_AUTHOR)";
			rebuilds.add(rebuild5);
			String rebuild6 = "CREATE INDEX SISS_HISTORY_WORKITEM_IDX_6 ON DMALM_SISS_HISTORY_WORKITEM(FK_PROJECT)";
			rebuilds.add(rebuild6);
			String rebuild7 = "CREATE INDEX SISS_HISTORY_WORKITEM_IDX_7 ON DMALM_SISS_HISTORY_WORKITEM(C_TYPE)";
			rebuilds.add(rebuild7);

			String rebuild8 = "CREATE INDEX SISS_HISTORY_CF_WORKITEM_IDX_1 ON DMALM_SISS_HISTORY_CF_WORKITEM(DATA_CARICAMENTO)";
			rebuilds.add(rebuild8);
			String rebuild9 = "CREATE INDEX SISS_HISTORY_CF_WORKITEM_IDX_2 ON DMALM_SISS_HISTORY_CF_WORKITEM(FK_URI_WORKITEM)";
			rebuilds.add(rebuild9);
			String rebuild10 = "CREATE INDEX SISS_HISTORY_CF_WORKITEM_IDX_4 ON DMALM_SISS_HISTORY_CF_WORKITEM(C_NAME)";
			rebuilds.add(rebuild10);

			for (String rebuild : rebuilds) {

				try(PreparedStatement ps = connOracle.prepareStatement(rebuild);){ 
				ps.execute();
				ps.close();
				}

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

		} finally {

			try {
				if (cm != null) {
					cm.closeConnection(connOracle);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		}

	}

	public static void fillSissHistoryWorkitem(
			Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision,
			EnumWorkitemType type) throws Exception {

		ConnectionManager cm = null;
		Connection OracleConnection = null;

		List<Tuple> historyworkitems = new ArrayList<Tuple>();
		lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject fonteProjects = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject.project;
		lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryUser fonteUser = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryUser.user;
		lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentSubterraUriMap fonteSissSubterraUriMap = lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentSubterraUriMap.urimap;

		try {

			cm = ConnectionManager.getInstance();

			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(true);

			OracleTemplates dialect = new OracleTemplates();

			SQLQuery queryHistory = null;

			
			queryHistory = new SQLQuery(OracleConnection, dialect);

			historyworkitems = queryHistory.from(fonteHistoryWorkItems)
					.where(fonteHistoryWorkItems.cType.eq(type.toString()))
					.where(fonteHistoryWorkItems.cRev
							.gt(minRevisionByType.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision)).list(
							fonteHistoryWorkItems.fkUriModule,
							StringTemplate.create("0 as c_is_local"),
							fonteHistoryWorkItems.cPriority,
							fonteHistoryWorkItems.cResolution,
							fonteHistoryWorkItems.cCreated,
							fonteHistoryWorkItems.cOutlinenumber,
							fonteHistoryWorkItems.fkUriProject,
							StringTemplate.create("(select c_rev from "
									+ fonteProjects.getSchemaName() + "."
									+ fonteProjects.getTableName()
									+ " where "+fonteProjects.getTableName()+".c_pk = fk_project) as FK_PROJECT"),
							fonteHistoryWorkItems.cDeleted,
							fonteHistoryWorkItems.cPlannedend,
							fonteHistoryWorkItems.cUpdated,
							fonteHistoryWorkItems.fkUriAuthor,
							StringTemplate.create("(select c_rev from "
									+ fonteUser.getSchemaName() + "."
									+ fonteUser.getTableName()
									+ " where "+fonteUser.getTableName()+".c_pk = fk_author) as fk_author"),
							fonteHistoryWorkItems.cUri,
							fonteHistoryWorkItems.cTimespent,
							fonteHistoryWorkItems.cStatus,
							fonteHistoryWorkItems.cSeverity,
							fonteHistoryWorkItems.cResolvedon,
							fonteHistoryWorkItems.cTitle,
							fonteHistoryWorkItems.cId,
							fonteHistoryWorkItems.cRev,
							fonteHistoryWorkItems.cPlannedstart,
							fonteHistoryWorkItems.cDuedate,
							fonteHistoryWorkItems.cRemainingestimate,
							fonteHistoryWorkItems.cType,
							fonteHistoryWorkItems.cLocation,
							fonteHistoryWorkItems.fkUriTimepoint,
							fonteHistoryWorkItems.cInitialestimate,
							fonteHistoryWorkItems.cPreviousstatus
							);

			logger.debug("TYPE: SISS " + type.toString() + "  SIZE: "
					+ historyworkitems.size() + " minRevision: "
					+ minRevisionByType.get(type) + " - maxRevision: "
					+ maxRevision);

			SQLInsertClause insert = new SQLInsertClause(OracleConnection,
					dialect, stgWorkItems);

			int batchSizeCounter = 0;

			for (Tuple hist : historyworkitems) {
				batchSizeCounter++;
				
				String fkUriModule = hist
						.get(fonteHistoryWorkItems.fkUriModule) != null
								? (QueryUtils
										.queryConnOracle(OracleConnection,
												dialect)
										.from(fonteSissSubterraUriMap)
										.where(fonteSissSubterraUriMap.cId
												.eq(Long.valueOf(hist.get(
														fonteHistoryWorkItems.fkUriModule)
														.toString())))
										.count() > 0
												? QueryUtils.queryConnOracle(
														OracleConnection,
														dialect)
														.from(fonteSissSubterraUriMap)
														.where(fonteSissSubterraUriMap.cId
																.eq(Long.valueOf(
																		hist.get(
																				fonteHistoryWorkItems.fkUriModule)
																				.toString())))
														.list(fonteSissSubterraUriMap.cPk)
														.get(0)
												: "")
								: "";
				String fkModule = fkUriModule != ""
						? (fkUriModule + "%" + (hist
								.get(fonteHistoryWorkItems.fkModule) != null
										? hist.get(
												fonteHistoryWorkItems.fkModule)
												.toString()
										: ""))
						: "";
				String fkUriProject = hist
						.get(fonteHistoryWorkItems.fkUriProject) != null
								? (QueryUtils
										.queryConnOracle(OracleConnection,
												dialect)
										.from(fonteSissSubterraUriMap)
										.where(fonteSissSubterraUriMap.cId
												.eq(Long.valueOf(hist.get(
														fonteHistoryWorkItems.fkUriProject))))
										.count() > 0
												? QueryUtils.queryConnOracle(
														OracleConnection,
														dialect)
														.from(fonteSissSubterraUriMap)
														.where(fonteSissSubterraUriMap.cId
																.eq(Long.valueOf(
																		hist.get(
																				fonteHistoryWorkItems.fkUriProject)
																				.toString())))
														.list(fonteSissSubterraUriMap.cPk)
														.get(0)
												: "")
								: "";
				String fkProject = fkUriProject != ""
						? (fkUriProject + "%" + (hist.toArray()[7] != null
										? hist.toArray()[7]
										: ""))
						: "";
				String fkUriAuthor = hist
						.get(fonteHistoryWorkItems.fkUriAuthor) != null
								? (QueryUtils
										.queryConnOracle(OracleConnection,
												dialect)
										.from(fonteSissSubterraUriMap)
										.where(fonteSissSubterraUriMap.cId
												.eq(Long.valueOf(hist.get(
														fonteHistoryWorkItems.fkUriAuthor)
														.toString())))
										.count() > 0
												? QueryUtils.queryConnOracle(
														OracleConnection,
														dialect)
														.from(fonteSissSubterraUriMap)
														.where(fonteSissSubterraUriMap.cId
																.eq(Long.valueOf(
																		hist.get(
																				fonteHistoryWorkItems.fkUriAuthor)
																				.toString())))
														.list(fonteSissSubterraUriMap.cPk)
														.get(0)
												: "")
								: "";
				String fkAuthor = fkUriAuthor != ""
						? (fkUriAuthor + "%" + (hist.toArray()[12] != null
										? hist.toArray()[12]
										: ""))
						: "";
				String cUri = hist.get(fonteHistoryWorkItems.cUri) != null
						? (QueryUtils.queryConnOracle(OracleConnection, dialect)
								.from(fonteSissSubterraUriMap)
								.where(fonteSissSubterraUriMap.cId
										.eq(Long.valueOf(hist
												.get(fonteHistoryWorkItems.cUri)
												.toString())))
								.count() > 0
										? QueryUtils
												.queryConnOracle(
														OracleConnection,
														dialect)
												.from(fonteSissSubterraUriMap)
												.where(fonteSissSubterraUriMap.cId
														.eq(Long.valueOf(hist
																.get(fonteHistoryWorkItems.cUri)
																.toString())))
												.list(fonteSissSubterraUriMap.cPk)
												.get(0)
										: "")
						: "";
				String cPk = cUri != ""
						? (cUri + "%"
								+ (hist.get(fonteHistoryWorkItems.cRev) != null
										? hist.get(fonteHistoryWorkItems.cRev)
												.toString()
										: ""))
						: "";
				String fkUriTimepoint = hist
						.get(fonteHistoryWorkItems.fkUriTimepoint) != null
								? (QueryUtils
										.queryConnOracle(OracleConnection,
												dialect)
										.from(fonteSissSubterraUriMap)
										.where(fonteSissSubterraUriMap.cId
												.eq(Long.valueOf(hist.get(
														fonteHistoryWorkItems.fkUriTimepoint)
														.toString())))
										.count() > 0
												? QueryUtils.queryConnOracle(
														OracleConnection,
														dialect)
														.from(fonteSissSubterraUriMap)
														.where(fonteSissSubterraUriMap.cId
																.eq(Long.valueOf(
																		hist.get(
																				fonteHistoryWorkItems.fkUriTimepoint)
																				.toString())))
														.list(fonteSissSubterraUriMap.cPk)
														.get(0)
												: "")
								: "";
				String fkTimepoint = fkUriTimepoint != ""
						? (fkUriTimepoint + "%" + (hist
								.get(fonteHistoryWorkItems.fkTimepoint) != null
										? hist.get(
												fonteHistoryWorkItems.fkTimepoint)
												.toString()
										: ""))
						: "";
				
				insert.columns(stgWorkItems.fkModule, stgWorkItems.cIsLocal,
						stgWorkItems.cPriority, stgWorkItems.cResolution,
						stgWorkItems.cCreated, stgWorkItems.cOutlinenumber,
						stgWorkItems.fkProject, stgWorkItems.cDeleted,
						stgWorkItems.cPlannedend, stgWorkItems.cUpdated,
						stgWorkItems.fkAuthor, stgWorkItems.cUri,
						stgWorkItems.fkUriModule, stgWorkItems.cTimespent,
						stgWorkItems.cStatus, stgWorkItems.cSeverity,
						stgWorkItems.cResolvedon, stgWorkItems.fkUriProject,
						stgWorkItems.cTitle, stgWorkItems.cId,
						stgWorkItems.cRev, stgWorkItems.cPlannedstart,
						stgWorkItems.fkUriAuthor, stgWorkItems.cDuedate,
						stgWorkItems.cRemainingestimate, stgWorkItems.cType,
						stgWorkItems.cPk, stgWorkItems.cLocation,
						stgWorkItems.fkTimepoint, stgWorkItems.cInitialestimate,
						stgWorkItems.fkUriTimepoint,
						stgWorkItems.cPreviousstatus,
						stgWorkItems.dataCaricamento,
						stgWorkItems.dmalmWorkitemPk)
						.values(fkModule, 0,
								hist.get(fonteHistoryWorkItems.cPriority),
								hist.get(fonteHistoryWorkItems.cResolution),
								hist.get(fonteHistoryWorkItems.cCreated),
								hist.get(fonteHistoryWorkItems.cOutlinenumber),
								fkProject,
								hist.get(fonteHistoryWorkItems.cDeleted),
								hist.get(fonteHistoryWorkItems.cPlannedend),
								hist.get(fonteHistoryWorkItems.cUpdated),
								StringUtils.getMaskedValue(fkAuthor), cUri,
								fkUriModule,
								hist.get(fonteHistoryWorkItems.cTimespent),
								hist.get(fonteHistoryWorkItems.cStatus),
								hist.get(fonteHistoryWorkItems.cSeverity),
								hist.get(fonteHistoryWorkItems.cResolvedon),
								fkUriProject,
								hist.get(fonteHistoryWorkItems.cTitle),
								hist.get(fonteHistoryWorkItems.cId),
								hist.get(fonteHistoryWorkItems.cRev),
								hist.get(fonteHistoryWorkItems.cPlannedstart),
								StringUtils.getMaskedValue(fkUriAuthor),
								hist.get(fonteHistoryWorkItems.cDuedate),
								hist.get(
										fonteHistoryWorkItems.cRemainingestimate),
								hist.get(fonteHistoryWorkItems.cType), cPk,
								hist.get(fonteHistoryWorkItems.cLocation),
								fkTimepoint,
								hist.get(
										fonteHistoryWorkItems.cInitialestimate),
								fkUriTimepoint,
								hist.get(fonteHistoryWorkItems.cPreviousstatus),
								dataEsecuzione, StringTemplate
										.create("HISTORY_WORKITEM_SEQ.nextval")

						).addBatch();
				if (!historyworkitems.isEmpty()
						&& batchSizeCounter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					OracleConnection.commit();
					batchSizeCounter = 0;
					insert = new SQLInsertClause(OracleConnection, dialect,
							stgWorkItems);
				}

			}

			if (!insert.isEmpty()) {
				insert.execute();
				OracleConnection.commit();
			}

			logger.debug("STOP TYPE: SISS " + type.toString() + "  SIZE: "
					+ historyworkitems.size());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(OracleConnection);
			}
		}

	}

	/**
	 * Questo metodo parte dopo aver eseguito il FillStaging delle tabelle
	 * WORKITEM. Per tutti i nuovi Workitem aggiunti, recupera la descrizione
	 * dai file XML
	 * 
	 * @throws DAOException
	 */

	public static void recoverSissHistoryWorkitem() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryWorkitem stgWorkItems = QSissHistoryWorkitem.sissHistoryWorkitem;
			// Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00",
			// "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgWorkItems).where(
					stgWorkItems.dataCaricamento.eq(DataEsecuzione
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

	public static List<Tuple> getAllSgrWiStatusInvalid(Timestamp dataEsecuzione)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> workitems = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmStatoWorkitem statoWorkitem = QDmalmStatoWorkitem.dmalmStatoWorkitem;

			workitems = query
					.from(stgWorkItems)
					.distinct()
					.leftJoin(statoWorkitem)
					.on(statoWorkitem.cdStato.eq(stgWorkItems.cStatus).and(
							statoWorkitem.origineStato.eq(stgWorkItems.cType)))
					.where(stgWorkItems.dataCaricamento.eq(dataEsecuzione))
					.where(statoWorkitem.dmalmStatoWorkitemPrimaryKey.isNull())
					.orderBy(stgWorkItems.cType.asc(), stgWorkItems.cId.asc(),
							stgWorkItems.cStatus.asc())
					.list(stgWorkItems.cType, stgWorkItems.cId,
							stgWorkItems.cStatus);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return workitems;
	}
}