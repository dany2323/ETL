package lispa.schedulers.dao.sgr.sire.history;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryWorkitem;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryWorkitem;
import lispa.schedulers.queryimplementation.target.QDmalmStatoWorkitem;
import lispa.schedulers.queryimplementation.target.QTotal;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;
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
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class SireHistoryWorkitemDAO {

	private static Logger logger = Logger
			.getLogger(SireHistoryWorkitemDAO.class);

	private static QSireHistoryWorkitem stgWorkItems = QSireHistoryWorkitem.sireHistoryWorkitem;
	private static QTotal total=QTotal.total;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem.workitem;

	private static Timestamp dataEsecuzione = DataEsecuzione.getInstance()
			.getDataEsecuzione();

	private static String url = "";
	private static String name = "";
	private static String psw = "";

	private static SVNRepository repository;
	private static ISVNAuthenticationManager authManager;

	static {

		try {

			url = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_URL);
			name = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
			psw = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_PSW);

			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			authManager = SVNWCUtil.createDefaultAuthenticationManager(name,
					psw);

			repository.setAuthenticationManager(authManager);

		} catch (PropertiesReaderException e) {
			logger.error(e.getMessage(), e);
		} catch (SVNException e) {
			logger.error(e.getMessage(), e);
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

	// NON DROPPO INDEX SU C_PK PERCHE' SERVE SU UPDATE PER I CF!!!
	public static void dropIndexes() {

		ConnectionManager cm = null;
		Connection connOracle = null;
		PreparedStatement ps = null;
		List<String> drops = new ArrayList<String>();

		try {

			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();

			String drop1 = "DROP INDEX SIRE_HISTORY_WORKITEM_IDX_1";
			drops.add(drop1);
			String drop2 = "DROP INDEX SIRE_HISTORY_WORKITEM_IDX_2";
			drops.add(drop2);
			String drop4 = "DROP INDEX SIRE_HISTORY_WORKITEM_IDX_4";
			drops.add(drop4);
			String drop5 = "DROP INDEX SIRE_HISTORY_WORKITEM_IDX_5";
			drops.add(drop5);
			String drop6 = "DROP INDEX SIRE_HISTORY_WORKITEM_IDX_6";
			drops.add(drop6);
			String drop7 = "DROP INDEX SIRE_HISTORY_WORKITEM_IDX_7";
			drops.add(drop7);

			String drop8 = "DROP INDEX SIRE_HISTORY_CF_WORKITEM_IDX_1";
			drops.add(drop8);
			String drop9 = "DROP INDEX SIRE_HISTORY_CF_WORKITEM_IDX_2";
			drops.add(drop9);
			String drop10 = "DROP INDEX SIRE_HISTORY_CF_WORKITEM_IDX_4";
			drops.add(drop10);

			for (String drop : drops) {

				try {
					ps = connOracle.prepareStatement(drop);
					ps.execute();
					ps.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
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
		PreparedStatement ps = null;
		List<String> rebuilds = new ArrayList<String>();

		try {

			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();

			String rebuild1 = "CREATE INDEX SIRE_HISTORY_WORKITEM_IDX_1 ON DMALM_SIRE_HISTORY_WORKITEM(C_STATUS)";
			rebuilds.add(rebuild1);
			String rebuild2 = "CREATE INDEX SIRE_HISTORY_WORKITEM_IDX_2 ON DMALM_SIRE_HISTORY_WORKITEM(C_URI)";
			rebuilds.add(rebuild2);
			String rebuild4 = "CREATE INDEX SIRE_HISTORY_WORKITEM_IDX_4 ON DMALM_SIRE_HISTORY_WORKITEM(DATA_CARICAMENTO)";
			rebuilds.add(rebuild4);
			String rebuild5 = "CREATE INDEX SIRE_HISTORY_WORKITEM_IDX_5 ON DMALM_SIRE_HISTORY_WORKITEM(FK_AUTHOR)";
			rebuilds.add(rebuild5);
			String rebuild6 = "CREATE INDEX SIRE_HISTORY_WORKITEM_IDX_6 ON DMALM_SIRE_HISTORY_WORKITEM(FK_PROJECT)";
			rebuilds.add(rebuild6);
			String rebuild7 = "CREATE INDEX SIRE_HISTORY_WORKITEM_IDX_7 ON DMALM_SIRE_HISTORY_WORKITEM(C_TYPE)";
			rebuilds.add(rebuild7);

			String rebuild8 = "CREATE INDEX SIRE_HISTORY_CF_WORKITEM_IDX_1 ON DMALM_SIRE_HISTORY_CF_WORKITEM(DATA_CARICAMENTO)";
			rebuilds.add(rebuild8);
			String rebuild9 = "CREATE INDEX SIRE_HISTORY_CF_WORKITEM_IDX_2 ON DMALM_SIRE_HISTORY_CF_WORKITEM(FK_URI_WORKITEM)";
			rebuilds.add(rebuild9);
			String rebuild10 = "CREATE INDEX SIRE_HISTORY_CF_WORKITEM_IDX_4 ON DMALM_SIRE_HISTORY_CF_WORKITEM(C_NAME)";
			rebuilds.add(rebuild10);

			for (String rebuild : rebuilds) {

				try {
					ps = connOracle.prepareStatement(rebuild);
					ps.execute();
					ps.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
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

	public static void fillSireHistoryWorkitem(
			Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision,
			EnumWorkitemType type) throws Exception {

		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection SireHistoryConnection = null;

		List<Tuple> historyworkitems = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();

			SireHistoryConnection = cm.getConnectionSIREHistory();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(true);

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery queryHistory = null;

			if (!ConnectionManager.getInstance().isAlive(SireHistoryConnection)) {
				if (cm != null)
					cm.closeConnection(SireHistoryConnection);
				SireHistoryConnection = cm.getConnectionSIREHistory();
			}

			queryHistory = new SQLQuery(SireHistoryConnection, dialect);
			historyworkitems = queryHistory
					.from(fonteHistoryWorkItems)
					.where(fonteHistoryWorkItems.cType.eq(type.toString()))
					.where(fonteHistoryWorkItems.cRev.gt(minRevisionByType
							.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
					.list(fonteHistoryWorkItems.all());

			logger.debug("TYPE: SIRE " + type.toString() + "  SIZE: "
					+ historyworkitems.size() + " minRevision: "+ minRevisionByType
					.get(type) + " - maxRevision: "+ maxRevision);

			SQLInsertClause insert = new SQLInsertClause(OracleConnection,
					dialect, stgWorkItems);

			int batch_size_counter = 0;

			for (Tuple hist : historyworkitems) {
				batch_size_counter++;
				insert.columns(stgWorkItems.fkModule, stgWorkItems.cIsLocal,
						stgWorkItems.cPriority, stgWorkItems.cAutosuspect,
						stgWorkItems.cResolution, stgWorkItems.cCreated,
						stgWorkItems.cOutlinenumber, stgWorkItems.fkProject,
						stgWorkItems.cDeleted, stgWorkItems.cPlannedend,
						stgWorkItems.cUpdated, stgWorkItems.fkAuthor,
						stgWorkItems.cUri, stgWorkItems.fkUriModule,
						stgWorkItems.cTimespent, stgWorkItems.cStatus,
						stgWorkItems.cSeverity, stgWorkItems.cResolvedon,
						stgWorkItems.fkUriProject, stgWorkItems.cTitle,
						stgWorkItems.cId, stgWorkItems.cRev,
						stgWorkItems.cPlannedstart, stgWorkItems.fkUriAuthor,
						stgWorkItems.cDuedate, stgWorkItems.cRemainingestimate,
						stgWorkItems.cType, stgWorkItems.cPk,
						stgWorkItems.cLocation, stgWorkItems.fkTimepoint,
						stgWorkItems.cInitialestimate,
						stgWorkItems.fkUriTimepoint,
						stgWorkItems.cPreviousstatus,
						stgWorkItems.dataCaricamento,
						stgWorkItems.dmalmHistoryWorkitemPk)
						.values(hist.get(fonteHistoryWorkItems.fkModule),
								hist.get(fonteHistoryWorkItems.cIsLocal),
								hist.get(fonteHistoryWorkItems.cPriority),
								hist.get(fonteHistoryWorkItems.cAutosuspect),
								hist.get(fonteHistoryWorkItems.cResolution),
								hist.get(fonteHistoryWorkItems.cCreated),
								hist.get(fonteHistoryWorkItems.cOutlinenumber),
								hist.get(fonteHistoryWorkItems.fkProject),
								hist.get(fonteHistoryWorkItems.cDeleted),
								hist.get(fonteHistoryWorkItems.cPlannedend),
								hist.get(fonteHistoryWorkItems.cUpdated),
								StringUtils.getMaskedValue(hist.get(fonteHistoryWorkItems.fkAuthor)),
								hist.get(fonteHistoryWorkItems.cUri),
								hist.get(fonteHistoryWorkItems.fkUriModule),
								hist.get(fonteHistoryWorkItems.cTimespent),
								hist.get(fonteHistoryWorkItems.cStatus),
								hist.get(fonteHistoryWorkItems.cSeverity),
								hist.get(fonteHistoryWorkItems.cResolvedon),
								hist.get(fonteHistoryWorkItems.fkUriProject),
								hist.get(fonteHistoryWorkItems.cTitle)!=null?hist.get(fonteHistoryWorkItems.cTitle).substring(0, Math.min(hist.get(fonteHistoryWorkItems.cTitle).length(), 999)):null,
								hist.get(fonteHistoryWorkItems.cId),
								hist.get(fonteHistoryWorkItems.cRev),
								hist.get(fonteHistoryWorkItems.cPlannedstart),
								StringUtils.getMaskedValue(hist.get(fonteHistoryWorkItems.fkUriAuthor)),
								hist.get(fonteHistoryWorkItems.cDuedate),
								hist.get(fonteHistoryWorkItems.cRemainingestimate),
								hist.get(fonteHistoryWorkItems.cType),
								hist.get(fonteHistoryWorkItems.cPk),
								hist.get(fonteHistoryWorkItems.cLocation),
								hist.get(fonteHistoryWorkItems.fkTimepoint),
								hist.get(fonteHistoryWorkItems.cInitialestimate),
								hist.get(fonteHistoryWorkItems.fkUriTimepoint),
								hist.get(fonteHistoryWorkItems.cPreviousstatus),
								dataEsecuzione,
								StringTemplate
										.create("HISTORY_WORKITEM_SEQ.nextval")

						).addBatch();
				if (!historyworkitems.isEmpty()
						&& batch_size_counter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					batch_size_counter = 0;
					insert = new SQLInsertClause(OracleConnection, dialect,
							stgWorkItems);
				}

			}

			if (!insert.isEmpty()) {
				insert.execute();
			}

			// List<String> customFields =
			// EnumUtils.getCFEnumerationByType(type);
			// cfDeadlock = ErrorManager.getInstance().hascfDeadLock();
			// if (!cfDeadlock){
			// SireHistoryCfWorkitemDAO.fillSireHistoryCfWorkitemByWorkitemType(
			// minRevisionByType.get(type), maxRevision, type,
			// customFields);
			// }
			logger.debug("STOP TYPE: SIRE " + type.toString() + "  SIZE: "
					+ historyworkitems.size());

			/*
			 * Per ogni Workitem inserito nello staging in questa esecuzione
			 * (quindi da workitem_minRevision a polarion_maxRevision), cercare
			 * la relativa descrizione presente nel file XML presente al path
			 * indicato nel campo C_LOCATION.
			 */
			if (DmAlmConfigReader
					.getInstance()
					.getProperty(
							DmAlmConfigReaderProperties.UPDATE_DESCRIZIONE_ENABLER)
					.equals(DmAlmConstants.ENABLER)) {
				updateDescriptions(minRevisionByType.get(type), maxRevision,
						type.toString());
			}

			ErrorManager.getInstance().resetDeadlock();
		} catch (PropertiesReaderException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			Throwable cause = e;
			while (cause.getCause() != null)
				cause = cause.getCause();
			String message = cause.getMessage();
			if (StringUtils.findRegex(message, DmalmRegex.REGEXDEADLOCK)) {
				ErrorManager.getInstance().exceptionDeadlock(false, e);
			} else {
				ErrorManager.getInstance().exceptionOccurred(true, e);
			}
		} finally {
			if (cm != null) {
				cm.closeConnection(OracleConnection);
			}
			if (cm != null) {
				cm.closeConnection(SireHistoryConnection);
			}
		}
	}
	
	public static void fillSireHistoryWorkitemWithNoProjectFk() throws Exception {

		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection SireHistoryConnection = null;

		List<Tuple> historyworkitems = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();

			SireHistoryConnection = cm.getConnectionSIREHistory();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(true);

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery queryHistory = null;

			if (!ConnectionManager.getInstance().isAlive(SireHistoryConnection)) {
				if (cm != null)
					cm.closeConnection(SireHistoryConnection);
				SireHistoryConnection = cm.getConnectionSIREHistory();
			}
			Connection connOracle = cm.getConnectionOracle();
			SQLTemplates dialect2 = new HSQLDBTemplates();

			List<String> cId = new SQLQuery(connOracle,dialect2).distinct()
					.from(total)
//					.where(total.projectFk.eq(0))
					.where(total.idRepository.eq(DmAlmConstants.REPOSITORY_SIRE))
//					.where(total.stgPk.notIn(new SQLSubQuery()
//							.from(stgWorkItems)
//							.list(stgWorkItems.cId)))
					.list(total.codice);


			queryHistory = new SQLQuery(SireHistoryConnection, dialect);
			historyworkitems = queryHistory
					.from(fonteHistoryWorkItems)
					.where(fonteHistoryWorkItems.cId.notIn((cId)))
					.list(fonteHistoryWorkItems.all());

			

			SQLInsertClause insert = new SQLInsertClause(OracleConnection,
					dialect, stgWorkItems);

			int batch_size_counter = 0;

			for (Tuple hist : historyworkitems) {
				batch_size_counter++;
				logger.info("cID: "+hist.get(fonteHistoryWorkItems.cId)+ " cPk : "+hist.get(fonteHistoryWorkItems.cPk));
				insert.columns(stgWorkItems.fkModule, stgWorkItems.cIsLocal,
						stgWorkItems.cPriority, stgWorkItems.cAutosuspect,
						stgWorkItems.cResolution, stgWorkItems.cCreated,
						stgWorkItems.cOutlinenumber, stgWorkItems.fkProject,
						stgWorkItems.cDeleted, stgWorkItems.cPlannedend,
						stgWorkItems.cUpdated, stgWorkItems.fkAuthor,
						stgWorkItems.cUri, stgWorkItems.fkUriModule,
						stgWorkItems.cTimespent, stgWorkItems.cStatus,
						stgWorkItems.cSeverity, stgWorkItems.cResolvedon,
						stgWorkItems.fkUriProject, stgWorkItems.cTitle,
						stgWorkItems.cId, stgWorkItems.cRev,
						stgWorkItems.cPlannedstart, stgWorkItems.fkUriAuthor,
						stgWorkItems.cDuedate, stgWorkItems.cRemainingestimate,
						stgWorkItems.cType, stgWorkItems.cPk,
						stgWorkItems.cLocation, stgWorkItems.fkTimepoint,
						stgWorkItems.cInitialestimate,
						stgWorkItems.fkUriTimepoint,
						stgWorkItems.cPreviousstatus,
						stgWorkItems.dataCaricamento,
						stgWorkItems.dmalmHistoryWorkitemPk)
						.values(hist.get(fonteHistoryWorkItems.fkModule),
								hist.get(fonteHistoryWorkItems.cIsLocal),
								hist.get(fonteHistoryWorkItems.cPriority),
								hist.get(fonteHistoryWorkItems.cAutosuspect),
								hist.get(fonteHistoryWorkItems.cResolution),
								hist.get(fonteHistoryWorkItems.cCreated),
								hist.get(fonteHistoryWorkItems.cOutlinenumber),
								hist.get(fonteHistoryWorkItems.fkProject),
								hist.get(fonteHistoryWorkItems.cDeleted),
								hist.get(fonteHistoryWorkItems.cPlannedend),
								hist.get(fonteHistoryWorkItems.cUpdated),
								StringUtils.getMaskedValue(hist.get(fonteHistoryWorkItems.fkAuthor)),
								hist.get(fonteHistoryWorkItems.cUri),
								hist.get(fonteHistoryWorkItems.fkUriModule),
								hist.get(fonteHistoryWorkItems.cTimespent),
								hist.get(fonteHistoryWorkItems.cStatus),
								hist.get(fonteHistoryWorkItems.cSeverity),
								hist.get(fonteHistoryWorkItems.cResolvedon),
								hist.get(fonteHistoryWorkItems.fkUriProject),
								hist.get(fonteHistoryWorkItems.cTitle)!=null?hist.get(fonteHistoryWorkItems.cTitle).substring(0, Math.min(hist.get(fonteHistoryWorkItems.cTitle).length(), 999)):null,
								hist.get(fonteHistoryWorkItems.cId),
								hist.get(fonteHistoryWorkItems.cRev),
								hist.get(fonteHistoryWorkItems.cPlannedstart),
								StringUtils.getMaskedValue(hist.get(fonteHistoryWorkItems.fkUriAuthor)),
								hist.get(fonteHistoryWorkItems.cDuedate),
								hist.get(fonteHistoryWorkItems.cRemainingestimate),
								hist.get(fonteHistoryWorkItems.cType),
								hist.get(fonteHistoryWorkItems.cPk),
								hist.get(fonteHistoryWorkItems.cLocation),
								hist.get(fonteHistoryWorkItems.fkTimepoint),
								hist.get(fonteHistoryWorkItems.cInitialestimate),
								hist.get(fonteHistoryWorkItems.fkUriTimepoint),
								hist.get(fonteHistoryWorkItems.cPreviousstatus),
								dataEsecuzione,
								StringTemplate
										.create("HISTORY_WORKITEM_SEQ.nextval")
//						).execute();
						).addBatch();
				if (!historyworkitems.isEmpty()
						&& batch_size_counter > 0) {
//						&& batch_size_counter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					batch_size_counter = 0;
					insert = new SQLInsertClause(OracleConnection, dialect,
							stgWorkItems);
				}

			}

			if (!insert.isEmpty()) {
				insert.execute();
			}

 			ErrorManager.getInstance().resetDeadlock();
		} catch (Exception e) {
			Throwable cause = e;
			while (cause.getCause() != null)
				cause = cause.getCause();
			String message = cause.getMessage();
			if (StringUtils.findRegex(message, DmalmRegex.REGEXDEADLOCK)) {
				ErrorManager.getInstance().exceptionDeadlock(false, e);
			} else {
				ErrorManager.getInstance().exceptionOccurred(true, e);
			}
		} finally {
			if (cm != null) {
				cm.closeConnection(OracleConnection);
			}
			if (cm != null) {
				cm.closeConnection(SireHistoryConnection);
			}
		}
	}

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
							DmAlmConfigReaderProperties.SQL_GET_MAX_PK_WORKITEM_TYPE_SIRE);
			
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
							DmAlmConfigReaderProperties.SQL_DELETE_SIRE_HISTORY_WORKITEM);
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

	public static void deleteInDate(Timestamp date) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSireHistoryWorkitem stgWorkItems = QSireHistoryWorkitem.sireHistoryWorkitem;

			new SQLDeleteClause(connection, dialect, stgWorkItems).where(
					stgWorkItems.dataCaricamento.eq(date)).execute();

		} catch (Exception e) {

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
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
	public static void updateDescriptions(long minRevision, long maxRevision,
			String type) {

		ConnectionManager cm = null;
		Connection oracleConnection = null;

		try {

			cm = ConnectionManager.getInstance();

			oracleConnection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(oracleConnection, dialect);

			List<Tuple> locations = query.distinct().from(stgWorkItems)
					.where(stgWorkItems.cRev.gt(minRevision))
					.where(stgWorkItems.cRev.loe(maxRevision))
					.where(stgWorkItems.cType.eq(type)).list(

					stgWorkItems.cLocation, stgWorkItems.cRev

					);

			for (Tuple row : locations) {

				getDescription(row.get(stgWorkItems.cLocation),
						row.get(stgWorkItems.cRev));

			}
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			try {
				if (cm != null) {
					cm.closeConnection(oracleConnection);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

	}

	private static void getDescription(String location, long revision)
			throws DAOException {

		ConnectionManager cm = null;
		Connection oracleConnection = null;

		try {

			cm = ConnectionManager.getInstance();

			oracleConnection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();

			// Il path al file XML e' la stringa dopo "default:/"
			String filePath = location.replaceAll("default:/", "");

			SVNNodeKind nodeKind = repository.checkPath(filePath, revision);
			SVNProperties fileProperties = new SVNProperties();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			repository.getFile(filePath, -1, fileProperties, baos);
			String mimeType = fileProperties
					.getStringValue(SVNProperty.MIME_TYPE);

			boolean isTextType = SVNProperty.isTextMimeType(mimeType);
			String xmlContent = "";
			if (isTextType) {
				xmlContent = baos.toString();
			} else {
				throw new Exception("");
			}

			if (nodeKind == SVNNodeKind.FILE) {

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				Document doc = dbFactory.newDocumentBuilder().parse(
						new ByteArrayInputStream(xmlContent.getBytes("UTF-8")));
				doc.getDocumentElement().normalize();

				// Prendo tutti i tag <field>
				NodeList nList = doc.getElementsByTagName("field");

				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;

						// <field id='description'> e' cio' che stiamo cercando
						if (eElement.getAttribute("id").equals("description")) {
							String description = eElement.getTextContent();
							// da utilizzare solo se si vogliono eliminare i tag
							// HTML
							// description = description.replaceAll("\\<.*?\\>",
							// "");
							new SQLUpdateClause(oracleConnection, dialect,
									stgWorkItems)
									.set(stgWorkItems.cDescription, description)
									.where(stgWorkItems.cLocation.eq(location))
									.execute();
						}

					}

				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (cm != null) {
				cm.closeConnection(oracleConnection);
			}
		}
	}

	public static void recoverSireHistoryWorkitem() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryWorkitem stgWorkItems = QSireHistoryWorkitem.sireHistoryWorkitem;
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