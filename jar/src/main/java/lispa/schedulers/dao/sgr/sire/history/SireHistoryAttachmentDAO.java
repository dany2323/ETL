package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConstants;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryAttachment;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryAttachment;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SireHistoryAttachmentDAO {

	private static Logger logger = Logger
			.getLogger(SireHistoryAttachmentDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryAttachment fonteAttachment = SireHistoryAttachment.attachment;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryAttachment stgAttachment = QSireHistoryAttachment.dmalmSireHistoryAttachment;

	public static void fillSireHistoryAttachment(long minRevision, long maxRevision) throws SQLException, DAOException {
		
		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> attachments = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();
			attachments = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query = new SQLQuery(pgConnection, dialect);

			attachments = query.from(fonteAttachment)
					.where(fonteAttachment.cRev.gt(minRevision))
					.where(fonteAttachment.cRev.loe(maxRevision))
					.list(
							fonteAttachment.cDeleted,
							fonteAttachment.cFilename,
							fonteAttachment.cId,
							StringTemplate.create("0 as c_is_local"),
							fonteAttachment.cLength,
							StringTemplate.create("(SELECT a.c_pk FROM " + DmAlmConstants.GetDbLinkPolarionCurrentSire() + " a WHERE a.c_id = " + fonteAttachment.cUri + ") || '%' || c_rev as c_pk"),
							fonteAttachment.cRev,
							fonteAttachment.cTitle,
							fonteAttachment.cUpdated,
							StringTemplate.create("(SELECT b.c_pk FROM " + DmAlmConstants.GetDbLinkPolarionCurrentSire() + " b WHERE b.c_id = " + fonteAttachment.cUri + ") as c_uri"),
							fonteAttachment.cUrl,
							StringTemplate.create("(SELECT c.c_pk FROM " + DmAlmConstants.GetDbLinkPolarionCurrentSire() + " c WHERE c.c_id = " + fonteAttachment.fkUriAuthor + ") || '%' || (select c_rev from " + DmAlmConstants.GetPolarionSchemaSireHistory() + ".t_user where t_user.c_pk = fk_author) as fk_author"),
							StringTemplate.create("(SELECT d.c_pk FROM " + DmAlmConstants.GetDbLinkPolarionCurrentSire() + " d WHERE d.c_id = " + fonteAttachment.fkUriProject + ") || '%' || (select c_rev from " + DmAlmConstants.GetPolarionSchemaSireHistory() + ".project where project.c_pk = fk_project) as fk_project"),
							StringTemplate.create("(SELECT e.c_pk FROM " + DmAlmConstants.GetDbLinkPolarionCurrentSire() + " e WHERE e.c_id = " + fonteAttachment.fkUriAuthor + ") as fk_uri_author"),
							StringTemplate.create("(SELECT f.c_pk FROM " + DmAlmConstants.GetDbLinkPolarionCurrentSire() + " f WHERE f.c_id = " + fonteAttachment.fkUriProject + ") as fk_uri_project"),
							StringTemplate.create("(SELECT g.c_pk FROM " + DmAlmConstants.GetDbLinkPolarionCurrentSire() + " g WHERE g.c_id = " + fonteAttachment.fkUriWorkitem + ") as fk_uri_workitem"),
							StringTemplate.create("(SELECT h.c_pk FROM " + DmAlmConstants.GetDbLinkPolarionCurrentSire() + " h WHERE h.c_id = " + fonteAttachment.fkUriWorkitem + ") || '%' || (select c_rev from " + DmAlmConstants.GetPolarionSchemaSireHistory() + ".workitem where workitem.c_pk = fk_workitem) as fk_workitem")
							);
			
			
			for (Tuple row : attachments) {
				Object[] val = row.toArray();
				
				new SQLInsertClause(connOracle, dialect, stgAttachment)
						.columns(
								stgAttachment.cDeleted,
								stgAttachment.cFilename,
								stgAttachment.cId,
								stgAttachment.cIsLocal,
								stgAttachment.cLength,
								stgAttachment.cPk,
								stgAttachment.cRev,
								stgAttachment.cTitle,
								stgAttachment.cUpdated,
								stgAttachment.cUri,
								stgAttachment.cUrl,
								stgAttachment.dataCaricamento,
								stgAttachment.fkAuthor,
								stgAttachment.fkProject,
								stgAttachment.fkUriAuthor,
								stgAttachment.fkUriProject,
								stgAttachment.fkUriWorkitem,
								stgAttachment.fkWorkitem,
								stgAttachment.sireHistoryAttachmentPk
						)
								
						.values(
								val[0],
								val[1],
								val[2],
								val[3],
								val[4],
								val[5],
								val[6],
								val[7],
								val[8],
								val[9],
								val[10],
								DataEsecuzione.getInstance().getDataEsecuzione(),
								StringUtils.getMaskedValue((String)val[11]),
								val[12],
								StringUtils.getMaskedValue((String)val[13]),
								val[14],
								val[15],
								val[16],
								StringTemplate.create("HISTORY_ATTACHMENT_SEQ.nextval")
										
						)
						.execute();
					
			}
			
			connOracle.commit();
			
		} catch (Exception e) {
ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw new DAOException(e);
		}
		finally {
			if(cm != null) cm.closeConnection(pgConnection);
			if(cm != null) cm.closeConnection(connOracle);
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
			if(cm != null) cm.closeConnection(oracle);
		}

		return max.get(0).longValue();
	}
	
	public static void recoverSireHistoryAttachement() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryAttachment stgHistoryAttachment = QSireHistoryAttachment.dmalmSireHistoryAttachment;
//			Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00", "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgHistoryAttachment).where(stgHistoryAttachment.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		}
		catch(Exception e){
			logger.error(e.getMessage(), e);
			
			
			throw new DAOException(e);
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}

}