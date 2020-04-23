package lispa.schedulers.dao.sgr.sire.history;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

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
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryWorkitem;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class SireHistoryWorkitemDAO {

	private static Logger logger = Logger
			.getLogger(SireHistoryWorkitemDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryWorkitem stgWorkItems = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryWorkitem.workitem;

	private static Timestamp dataEsecuzione = DataEsecuzione.getInstance()
			.getDataEsecuzione();

	private static String url = "";
	private static String name = "";
	private static String psw = "";

	private static SVNRepository repository;
	private static ISVNAuthenticationManager authManager;

	static {

		try {

			url = DmAlmConfigReader.getInstance()
					.getProperty(DmAlmConfigReaderProperties.SIRE_SVN_URL);
			name = DmAlmConfigReader.getInstance()
					.getProperty(DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
			psw = DmAlmConfigReader.getInstance()
					.getProperty(DmAlmConfigReaderProperties.SIRE_SVN_PSW);

			repository = SVNRepositoryFactory
					.create(SVNURL.parseURIEncoded(url));
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
			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType
					.values()) {

				List<Long> max = new ArrayList<Long>();

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

	public static void fillSireHistoryWorkitem(
			Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision,
			EnumWorkitemType type) throws Exception {

		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection pgConnection = null;

		List<Tuple> historyworkitems = new ArrayList<Tuple>();
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;

		try {

			cm = ConnectionManager.getInstance();

			pgConnection = cm.getConnectionSIREHistory();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(true);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery queryHistory = null;

			if (!ConnectionManager.getInstance().isAlive(pgConnection)) {
				if (cm != null)
					cm.closeConnection(pgConnection);
				pgConnection = cm.getConnectionSIREHistory();
			}

			queryHistory = new SQLQuery(pgConnection, dialect);
			historyworkitems = queryHistory.from(fonteHistoryWorkItems)
					.where(fonteHistoryWorkItems.cType.eq(type.toString()))
					.where(fonteHistoryWorkItems.cRev
							.gt(minRevisionByType.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision)).list(
							// fonteHistoryWorkItems.all()
							fonteHistoryWorkItems.fkUriModule,
							StringTemplate.create("(select c_rev from "
									+ lispa.schedulers.manager.DmAlmConstants
											.GetPolarionSchemaSireHistory()
									+ ".module where module.c_pk = fk_module) as fk_module"),
							StringTemplate.create("0 as c_is_local"),
							fonteHistoryWorkItems.cPriority,
							fonteHistoryWorkItems.cResolution,
							fonteHistoryWorkItems.cCreated,
							fonteHistoryWorkItems.cOutlinenumber,
							fonteHistoryWorkItems.fkUriProject,
							StringTemplate.create("(select c_rev from "
									+ lispa.schedulers.manager.DmAlmConstants
											.GetPolarionSchemaSireHistory()
									+ ".project where project.c_pk = fk_project) as fk_project"),
							fonteHistoryWorkItems.cDeleted,
							fonteHistoryWorkItems.cPlannedend,
							fonteHistoryWorkItems.cUpdated,
							fonteHistoryWorkItems.fkUriAuthor,
							StringTemplate.create("(select c_rev from "
									+ lispa.schedulers.manager.DmAlmConstants
											.GetPolarionSchemaSireHistory()
									+ ".t_user where t_user.c_pk = fk_author) as fk_author"),
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
							StringTemplate.create("(select c_rev from "
									+ lispa.schedulers.manager.DmAlmConstants
											.GetPolarionSchemaSireHistory()
									+ ".timepoint where timepoint.c_pk = fk_timepoint) as fk_timepoint"),
							fonteHistoryWorkItems.cInitialestimate,
							fonteHistoryWorkItems.cPreviousstatus);

			logger.debug("TYPE: SIRE " + type.toString() + "  SIZE: "
					+ historyworkitems.size());

			SQLInsertClause insert = new SQLInsertClause(OracleConnection,
					dialect, stgWorkItems);

			int batchSizeCounter = 0;

			for (Tuple hist : historyworkitems) {

				Object[] vals = hist.toArray();
				String fkUriModule = hist
						.get(fonteHistoryWorkItems.fkUriModule) != null
								? (queryConnOracle(OracleConnection, dialect)
										.from(stgSubterra)
										.where(stgSubterra.cId
												.eq(Long.valueOf(hist.get(
														fonteHistoryWorkItems.fkUriModule)
														.toString())))
										.where(stgSubterra.cRepo.eq(
												lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
										.count() > 0
												? queryConnOracle(
														OracleConnection,
														dialect).from(
																stgSubterra)
																.where(stgSubterra.cId
																		.eq(Long.valueOf(
																				hist.get(
																						fonteHistoryWorkItems.fkUriModule)
																						.toString())))
																.where(stgSubterra.cRepo
																		.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
																.list(stgSubterra.cPk)
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
								? (queryConnOracle(OracleConnection, dialect)
										.from(stgSubterra)
										.where(stgSubterra.cId
												.eq(Long.valueOf(hist.get(
														fonteHistoryWorkItems.fkUriProject)
														.toString())))
										.where(stgSubterra.cRepo.eq(
												lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
										.count() > 0
												? queryConnOracle(
														OracleConnection,
														dialect).from(
																stgSubterra)
																.where(stgSubterra.cId
																		.eq(Long.valueOf(
																				hist.get(
																						fonteHistoryWorkItems.fkUriProject)
																						.toString())))
																.where(stgSubterra.cRepo
																		.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
																.list(stgSubterra.cPk)
																.get(0)
												: "")
								: "";
				String fkProject = fkUriProject != ""
						? (fkUriProject + "%" + (hist
								.get(fonteHistoryWorkItems.fkProject) != null
										? hist.get(
												fonteHistoryWorkItems.fkProject)
												.toString()
										: ""))
						: "";
				String fkUriAuthor = hist
						.get(fonteHistoryWorkItems.fkUriAuthor) != null
								? (queryConnOracle(OracleConnection, dialect)
										.from(stgSubterra)
										.where(stgSubterra.cId
												.eq(Long.valueOf(hist.get(
														fonteHistoryWorkItems.fkUriAuthor)
														.toString())))
										.where(stgSubterra.cRepo.eq(
												lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
										.count() > 0
												? queryConnOracle(
														OracleConnection,
														dialect).from(
																stgSubterra)
																.where(stgSubterra.cId
																		.eq(Long.valueOf(
																				hist.get(
																						fonteHistoryWorkItems.fkUriAuthor)
																						.toString())))
																.where(stgSubterra.cRepo
																		.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
																.list(stgSubterra.cPk)
																.get(0)
												: "")
								: "";
				String fkAuthor = fkUriAuthor != ""
						? (fkUriAuthor + "%" + (hist
								.get(fonteHistoryWorkItems.fkAuthor) != null
										? hist.get(
												fonteHistoryWorkItems.fkAuthor)
												.toString()
										: ""))
						: "";
				String cUri = hist.get(fonteHistoryWorkItems.cUri) != null
						? (queryConnOracle(OracleConnection, dialect)
								.from(stgSubterra)
								.where(stgSubterra.cId.eq(Long.valueOf(
										hist.get(fonteHistoryWorkItems.cUri)
												.toString())))
								.where(stgSubterra.cRepo.eq(
										lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
								.count() > 0
										? queryConnOracle(
												OracleConnection, dialect)
														.from(stgSubterra)
														.where(stgSubterra.cId
																.eq(Long.valueOf(
																		hist.get(
																				fonteHistoryWorkItems.cUri)
																				.toString())))
														.where(stgSubterra.cRepo
																.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
														.list(stgSubterra.cPk)
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
								? (queryConnOracle(OracleConnection, dialect)
										.from(stgSubterra)
										.where(stgSubterra.cId
												.eq(Long.valueOf(hist.get(
														fonteHistoryWorkItems.fkUriTimepoint)
														.toString())))
										.where(stgSubterra.cRepo.eq(
												lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
										.count() > 0
												? queryConnOracle(
														OracleConnection,
														dialect).from(
																stgSubterra)
																.where(stgSubterra.cId
																		.eq(Long.valueOf(
																				hist.get(
																						fonteHistoryWorkItems.fkUriTimepoint)
																						.toString())))
																.where(stgSubterra.cRepo
																		.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
																.list(stgSubterra.cPk)
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

				batchSizeCounter++;
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
						stgWorkItems.cPreviousstatus)
						.values(fkModule,
								hist.get(fonteHistoryWorkItems.cIsLocal),
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
								hist.get(fonteHistoryWorkItems.cPreviousstatus))
						.addBatch();
				if (!historyworkitems.isEmpty()
						&& batchSizeCounter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					batchSizeCounter = 0;
					insert = new SQLInsertClause(OracleConnection, dialect,
							stgWorkItems);
					OracleConnection.commit();
				}
			}

			if (!insert.isEmpty()) {
				insert.execute();
				OracleConnection.commit();
			}
			logger.debug("STOP TYPE: SIRE " + type.toString() + "  SIZE: "
					+ historyworkitems.size());

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
				cm.closeConnection(pgConnection);
			}
		}
	}

	public static void delete(EnumWorkitemType type) throws Exception {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stgWorkItems)
					.where(stgWorkItems.cType.eq(type.toString())).execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

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