package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryAttachment;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryAttachment;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.StringUtils;

public class SissHistoryAttachmentDAO {

	private static Logger logger = Logger
			.getLogger(SissHistoryAttachmentDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryAttachment fonteAttachment = SissHistoryAttachment.attachment;

	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryAttachment stgAttachment = QSissHistoryAttachment.dmalmSissHistoryAttachment;

	public static void fillSissHistoryAttachment(long minRevision,
			long maxRevision) throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		List<Tuple> attachments = null;
		long nRigheInserite = 0;
		lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentSubterraUriMap fonteSissSubterraUriMap = lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentSubterraUriMap.urimap;
		lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryUser fonteUsers = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryUser.user;
		lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject fonteProjects = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject.project;
		lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			attachments = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			OracleTemplates dialect = new OracleTemplates();

			SQLQuery query = new SQLQuery(connOracle, dialect);

			attachments = query.from(fonteAttachment)
					.where(fonteAttachment.cRev.gt(minRevision))
					.where(fonteAttachment.cRev.loe(maxRevision))
					.list(fonteAttachment.cDeleted, fonteAttachment.cFilename,
							fonteAttachment.cId,
							StringTemplate.create("0 as c_is_local"),
							fonteAttachment.cLength, fonteAttachment.cUri,
							fonteAttachment.cRev, fonteAttachment.cTitle,
							fonteAttachment.cUpdated, fonteAttachment.cUrl,
							fonteAttachment.fkUriAuthor,
							StringTemplate.create("(select c_rev from "
									+ fonteUsers.getSchemaName() + "."
									+ fonteUsers.getTableName() + " where "
									+ fonteUsers.getTableName()
									+ ".c_pk = fk_author) as fk_rev_author"),
							fonteAttachment.fkUriProject,
							StringTemplate.create("(select c_rev from "
									+ fonteProjects.getSchemaName() + "."
									+ fonteProjects.getTableName() + " where "
									+ fonteProjects.getTableName()
									+ ".c_pk = fk_project) as fk_rev_project"),
							fonteAttachment.fkUriWorkitem,
							StringTemplate.create("(select c_rev from "
									+ fonteHistoryWorkItems.getSchemaName()
									+ "." + fonteHistoryWorkItems.getTableName()
									+ " where "
									+ fonteHistoryWorkItems.getTableName()
									+ ".c_pk = fk_workitem) as fk_rev_workitem"));

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgAttachment);
			for (Tuple row : attachments) {

				String cUri = row.get(fonteAttachment.cUri) != null
						? (QueryUtils.queryConnOracle(connOracle, dialect)
								.from(fonteSissSubterraUriMap)
								.where(fonteSissSubterraUriMap.cId
										.eq(Long.valueOf(
												row.get(fonteAttachment.cUri))))
								.count() > 0
										? QueryUtils
												.queryConnOracle(connOracle,
														dialect)
												.from(fonteSissSubterraUriMap)
												.where(fonteSissSubterraUriMap.cId
														.eq(Long.valueOf(row
																.get(fonteAttachment.cUri))))
												.list(fonteSissSubterraUriMap.cPk)
												.get(0)
										: "")
						: "";
				String cPk = cUri + "%"
						+ (row.get(fonteAttachment.cRev) != null
								? row.get(fonteAttachment.cRev)
								: "");
				String fkUriAuthor = row
						.get(fonteAttachment.fkUriAuthor) != null
								? (QueryUtils
										.queryConnOracle(connOracle, dialect)
										.from(fonteSissSubterraUriMap)
										.where(fonteSissSubterraUriMap.cId
												.eq(Long.valueOf(row.get(
														fonteAttachment.fkUriAuthor))))
										.count() > 0
												? QueryUtils
														.queryConnOracle(
																connOracle,
																dialect)
														.from(fonteSissSubterraUriMap)
														.where(fonteSissSubterraUriMap.cId
																.eq(Long.valueOf(
																		row.get(fonteAttachment.fkUriAuthor))))
														.list(fonteSissSubterraUriMap.cPk)
														.get(0)
												: "")
								: "";
				String fkUriProject = row
						.get(fonteAttachment.fkUriProject) != null
								? (QueryUtils
										.queryConnOracle(connOracle, dialect)
										.from(fonteSissSubterraUriMap)
										.where(fonteSissSubterraUriMap.cId
												.eq(Long.valueOf(row.get(
														fonteAttachment.fkUriProject))))
										.count() > 0
												? QueryUtils
														.queryConnOracle(
																connOracle,
																dialect)
														.from(fonteSissSubterraUriMap)
														.where(fonteSissSubterraUriMap.cId
																.eq(Long.valueOf(
																		row.get(fonteAttachment.fkUriProject))))
														.list(fonteSissSubterraUriMap.cPk)
														.get(0)
												: "")
								: "";
				String fkUriWorkitem = row
						.get(fonteAttachment.fkUriWorkitem) != null
								? (QueryUtils
										.queryConnOracle(connOracle, dialect)
										.from(fonteSissSubterraUriMap)
										.where(fonteSissSubterraUriMap.cId
												.eq(Long.valueOf(row.get(
														fonteAttachment.fkUriWorkitem))))
										.count() > 0
												? QueryUtils
														.queryConnOracle(
																connOracle,
																dialect)
														.from(fonteSissSubterraUriMap)
														.where(fonteSissSubterraUriMap.cId
																.eq(Long.valueOf(
																		row.get(fonteAttachment.fkUriWorkitem))))
														.list(fonteSissSubterraUriMap.cPk)
														.get(0)
												: "")
								: "";
				String fkAuthor = fkUriAuthor + "%"
						+ (row.get(fonteAttachment.cRev) != null
								? row.get(fonteAttachment.cRev)
								: "");
				String fkProject = fkUriProject + "%"
						+ (row.get(fonteAttachment.cRev) != null
								? row.get(fonteAttachment.cRev)
								: "");
				String fkWorkitem = fkUriWorkitem + "%"
						+ (row.get(fonteAttachment.cRev) != null
								? row.get(fonteAttachment.cRev)
								: "");

				insert.columns(stgAttachment.cDeleted, stgAttachment.cFilename,
						stgAttachment.cId, stgAttachment.cIsLocal,
						stgAttachment.cLength, stgAttachment.cPk,
						stgAttachment.cRev, stgAttachment.cTitle,
						stgAttachment.cUpdated, stgAttachment.cUri,
						stgAttachment.cUrl, stgAttachment.dataCaricamento,
						stgAttachment.fkAuthor, stgAttachment.fkProject,
						stgAttachment.fkUriAuthor, stgAttachment.fkUriProject,
						stgAttachment.fkUriWorkitem, stgAttachment.fkWorkitem,
						stgAttachment.sissHistoryAttachmentPk)

						.values(

								row.get(fonteAttachment.cDeleted),
								row.get(fonteAttachment.cFilename),
								row.get(fonteAttachment.cId), 0,
								row.get(fonteAttachment.cLength), cPk,
								row.get(fonteAttachment.cRev),
								StringUtils.truncate(
										row.get(fonteAttachment.cTitle), 1000),
								row.get(fonteAttachment.cUpdated), cUri,
								row.get(fonteAttachment.cUrl),
								DataEsecuzione.getInstance()
										.getDataEsecuzione(),
								StringUtils.getMaskedValue(fkAuthor), fkProject,
								StringUtils.getMaskedValue(fkUriAuthor),
								fkUriProject, fkUriWorkitem, fkWorkitem,
								StringTemplate.create(
										"HISTORY_ATTACHMENT_SEQ.nextval")

						).addBatch();
				nRigheInserite++;

				if (!insert.isEmpty()) {
					if (nRigheInserite
							% lispa.schedulers.constant.DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						connOracle.commit();
						insert = new SQLInsertClause(connOracle, dialect,
								stgAttachment);
					}
				}

			}
			if (!insert.isEmpty()) {
				insert.execute();
				connOracle.commit();
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connOracle);
		}
	}

	public static long getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		try {

			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);

			max = query.from(stgAttachment).list(stgAttachment.cRev.max());

			if (max == null || max.size() == 0 || max.get(0) == null) {
				return 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(oracle);
		}

		return max.get(0).longValue();
	}

	public static void recoverSissHistoryAttachement() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryAttachment stgHistoryAttachment = QSissHistoryAttachment.dmalmSissHistoryAttachment;
			// Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00",
			// "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgHistoryAttachment)
					.where(stgHistoryAttachment.dataCaricamento.eq(
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
