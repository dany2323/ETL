package lispa.schedulers.dao.sgr.siss.history;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

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
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

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
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryWorkitem;
import lispa.schedulers.queryimplementation.target.QDmalmStatoWorkitem;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

public class SissHistoryWorkitemDAO {

	private static Logger logger = Logger
			.getLogger(SissHistoryWorkitemDAO.class);

	private static QSissHistoryWorkitem stgWorkItems = QSissHistoryWorkitem.sissHistoryWorkitem;

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;

	private static Timestamp dataEsecuzione = DataEsecuzione.getInstance()
			.getDataEsecuzione();

	private static String url = "";
	private static String name = "";
	private static String psw = "";

	private static SVNRepository repository;
	private static ISVNAuthenticationManager authManager;

	public static void delete(Timestamp dataEsecuzioneDeleted) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			// elimina le righe vecchie dallo Staging lasciando la max(revision)
			// per ogni tipologia di workitem
			// sono inoltre lasciate le revision relative a:
			// 'chiuso', 'chiuso_falso', 'in_esercizio', 'in_esecuzione', 'completo', 'eseguito'
			// i "defect" non sono eliminati altrimenti la query non riesce a calcolare la data chiusura
			// nei casi di riapertura e nuova chiusura
			
			String sql = QueryManager
					.getInstance()
					.getQuery(
							DmAlmConfigReaderProperties.SQL_DELETE_SISS_HISTORY_WORKITEM);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzioneDeleted);
			ps.setTimestamp(2, DataEsecuzione.getInstance().getDataEsecuzione());

			ps.executeUpdate();
			ps.close();

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

	public static Map<Workitem_Type, Long> getMinRevisionByType()
			throws Exception {

		EnumMap<Workitem_Type, Long> map = new EnumMap<>(Workitem_Type.class);

		SQLTemplates dialect = new HSQLDBTemplates();

		ConnectionManager cm = null;
		Connection oracle = null;

		try {
				cm = ConnectionManager.getInstance();
				oracle = cm.getConnectionOracle();

			for (Workitem_Type type : Workitem_Type.values()) {

				List<Long> max = new ArrayList<Long>();

				SQLQuery query = new SQLQuery(oracle, dialect);

				max = query.from(stgWorkItems)
						.where(stgWorkItems.cType.eq(type.toString()))
						.list(stgWorkItems.cRev.max());

				if (max == null || max.size() == 0 || max.get(0) == null) {
					map.put(type, 0L);
				} else {
					map.put(type, Long.valueOf(max.get(0)));
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
		PreparedStatement ps = null;
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

				ps = connOracle.prepareStatement(drop);
				ps.execute();
				ps.close();

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

				ps = connOracle.prepareStatement(rebuild);
				ps.execute();
				ps.close();

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
			Map<Workitem_Type, Long> minRevisionsByType, long maxRevision,
			Workitem_Type type) throws Exception {

		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection SissHistoryConnection = null;

		List<Tuple> historyworkitems = new ArrayList<Tuple>();
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;

		try {

			cm = ConnectionManager.getInstance();

			SissHistoryConnection = cm.getConnectionSISSHistory();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(true);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery queryHistory = null;

			if (!ConnectionManager.getInstance().isAlive(SissHistoryConnection)) {
				if (cm != null)
					cm.closeConnection(SissHistoryConnection);
				SissHistoryConnection = cm.getConnectionSISSHistory();
			}

			queryHistory = new SQLQuery(SissHistoryConnection, dialect);

			historyworkitems = queryHistory
					.from(fonteHistoryWorkItems)
					.where(fonteHistoryWorkItems.cType.eq(type.toString()))
					.where(fonteHistoryWorkItems.cRev.gt(minRevisionsByType
							.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
					.list(
							fonteHistoryWorkItems.fkUriModule,
							StringTemplate.create("(select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".module where module.c_pk = fk_module) as fk_module"),
							StringTemplate.create("0 as c_is_local"),
							fonteHistoryWorkItems.cPriority,
							StringTemplate.create("false"),
							fonteHistoryWorkItems.cResolution, 
							fonteHistoryWorkItems.cCreated,
							fonteHistoryWorkItems.cOutlinenumber,
							fonteHistoryWorkItems.fkUriProject,
							StringTemplate.create("(select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".project where project.c_pk = fk_project) as fk_project"),
							fonteHistoryWorkItems.cDeleted,
							fonteHistoryWorkItems.cPlannedend,
							fonteHistoryWorkItems.cUpdated,
							fonteHistoryWorkItems.fkUriAuthor,
							StringTemplate.create("(select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".t_user where t_user.c_pk = fk_author) as fk_author"),
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
							StringTemplate.create("(select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".timepoint where timepoint.c_pk = fk_timepoint) as fk_timepoint"),
							fonteHistoryWorkItems.cInitialestimate,
							fonteHistoryWorkItems.cPreviousstatus
							);

			logger.debug("TYPE: SISS " + type.toString() + "  SIZE: "
					+ historyworkitems.size());

			SQLInsertClause insert = new SQLInsertClause(OracleConnection,
					dialect, stgWorkItems);
			int batch_size_counter = 0;

			for (Tuple hist : historyworkitems) {
				String fkUriModule = hist.get(fonteHistoryWorkItems.fkUriModule) != null ? (queryConnOracle(OracleConnection, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(hist.get(fonteHistoryWorkItems.fkUriModule)))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(OracleConnection, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(hist.get(fonteHistoryWorkItems.fkUriModule)))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") :"";
				String fkModule = fkUriModule != "" ? (fkUriModule +"%"+(hist.get(fonteHistoryWorkItems.fkModule)!= null ? hist.get(fonteHistoryWorkItems.fkModule): "")) : "";
				String fkUriProject = hist.get(fonteHistoryWorkItems.fkUriProject) != null ? (queryConnOracle(OracleConnection, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(hist.get(fonteHistoryWorkItems.fkUriProject)))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(OracleConnection, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(hist.get(fonteHistoryWorkItems.fkUriProject)))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") :"";
				String fkProject = fkUriProject != "" ? (fkUriProject +"%"+(hist.get(fonteHistoryWorkItems.fkProject) != null ? hist.get(fonteHistoryWorkItems.fkProject): "")) : "";
				String fkUriAuthor = hist.get(fonteHistoryWorkItems.fkUriAuthor) != null ? (queryConnOracle(OracleConnection, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(hist.get(fonteHistoryWorkItems.fkUriAuthor)))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(OracleConnection, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(hist.get(fonteHistoryWorkItems.fkUriAuthor)))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") :"";
				String fkAuthor = fkUriAuthor != "" ? (fkUriAuthor +"%"+(hist.get(fonteHistoryWorkItems.fkAuthor) != null ? hist.get(fonteHistoryWorkItems.fkAuthor) : "")) : "";
				String cUri = hist.get(fonteHistoryWorkItems.cUri) != null ? (queryConnOracle(OracleConnection, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(hist.get(fonteHistoryWorkItems.cUri)))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(OracleConnection, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(hist.get(fonteHistoryWorkItems.cUri)))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") :"";
				String cPk = cUri != "" ? (cUri +"%"+(hist.get(fonteHistoryWorkItems.cRev) != null ? hist.get(fonteHistoryWorkItems.cRev) : "")) : "";
				String fkUriTimepoint = hist.get(fonteHistoryWorkItems.fkUriTimepoint) != null ? (queryConnOracle(OracleConnection, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(hist.get(fonteHistoryWorkItems.fkUriTimepoint)))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(OracleConnection, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(hist.get(fonteHistoryWorkItems.fkUriTimepoint)))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") :"";
				String fkTimepoint = fkUriTimepoint != "" ? (fkUriTimepoint +"%"+(hist.get(fonteHistoryWorkItems.fkTimepoint) != null ? hist.get(fonteHistoryWorkItems.fkTimepoint) : "")) : "";
				
				
				batch_size_counter++;
				insert.columns(
						stgWorkItems.fkModule, 
						stgWorkItems.cIsLocal,
						stgWorkItems.cPriority, 
						stgWorkItems.cResolution, 
						stgWorkItems.cCreated,
						stgWorkItems.cOutlinenumber,
						stgWorkItems.fkProject,
						stgWorkItems.cDeleted,
						stgWorkItems.cPlannedend,
						stgWorkItems.cUpdated, 
						stgWorkItems.fkAuthor,
						stgWorkItems.cUri, 
						stgWorkItems.fkUriModule,
						stgWorkItems.cTimespent,
						stgWorkItems.cStatus,
						stgWorkItems.cSeverity,
						stgWorkItems.cResolvedon,
						stgWorkItems.fkUriProject,
						stgWorkItems.cTitle,
						stgWorkItems.cId, 
						stgWorkItems.cRev,
						stgWorkItems.cPlannedstart, 
						stgWorkItems.fkUriAuthor,
						stgWorkItems.cDuedate, 
						stgWorkItems.cRemainingestimate,
						stgWorkItems.cType,
						stgWorkItems.cPk,
						stgWorkItems.cLocation,
						stgWorkItems.fkTimepoint,
						stgWorkItems.cInitialestimate,
						stgWorkItems.fkUriTimepoint,
						stgWorkItems.cPreviousstatus,
						stgWorkItems.dataCaricamento,
						stgWorkItems.dmalmWorkitemPk)
						.values(
								fkModule,
								hist.get(fonteHistoryWorkItems.cIsLocal),
								hist.get(fonteHistoryWorkItems.cPriority),
								hist.get(fonteHistoryWorkItems.cResolution),
								hist.get(fonteHistoryWorkItems.cCreated),
								hist.get(fonteHistoryWorkItems.cOutlinenumber),
								fkProject,
								hist.get(fonteHistoryWorkItems.cDeleted),
								hist.get(fonteHistoryWorkItems.cPlannedend),
								hist.get(fonteHistoryWorkItems.cUpdated),
								StringUtils.getMaskedValue(fkAuthor),
								cUri,
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
								hist.get(fonteHistoryWorkItems.cRemainingestimate),
								hist.get(fonteHistoryWorkItems.cType),
								cPk,
								hist.get(fonteHistoryWorkItems.cLocation),
								fkTimepoint,
								hist.get(fonteHistoryWorkItems.cInitialestimate),
								fkUriTimepoint,
								hist.get(fonteHistoryWorkItems.cPreviousstatus),
								dataEsecuzione,
								StringTemplate.create("HISTORY_WORKITEM_SEQ.nextval")

						).addBatch();
				if (!historyworkitems.isEmpty()
						&& batch_size_counter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					insert = new SQLInsertClause(OracleConnection, dialect,
							stgWorkItems);
					batch_size_counter = 0;

				}

			}

			if (!insert.isEmpty()) {
				insert.execute();
			}

			logger.debug("STOP TYPE: SISS " + type.toString() + "  SIZE: "
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
				updateDescriptions(minRevisionsByType.get(type), maxRevision,
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
				cm.closeConnection(SissHistoryConnection);
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

			url = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_URL);
			name = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
			psw = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_PSW);

			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			authManager = SVNWCUtil.createDefaultAuthenticationManager(name,
					psw);
			repository.setAuthenticationManager(authManager);

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
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (cm != null) {
					cm.closeConnection(oracleConnection);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
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
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(oracleConnection);
			}
		}
	}

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
	
	private static SQLQuery queryConnOracle(Connection connOracle, PostgresTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}
}