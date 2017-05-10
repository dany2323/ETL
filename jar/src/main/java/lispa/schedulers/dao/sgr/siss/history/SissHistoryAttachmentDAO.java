package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryAttachment;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryAttachment;
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

public class SissHistoryAttachmentDAO {

	private static Logger logger = Logger
			.getLogger(SissHistoryAttachmentDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryAttachment fonteAttachment = SissHistoryAttachment.attachment;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap fonteSireSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap.urimap;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryAttachment stgAttachment = QSissHistoryAttachment.dmalmSissHistoryAttachment;

	public static void fillSissHistoryAttachment(long minRevision, long maxRevision) throws SQLException, DAOException {
		
		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> attachments = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSISSHistory();
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
							StringTemplate.create("(SELECT a.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " a WHERE a.c_id = " + fonteAttachment.cUri + ") || '%' || c_rev as c_pk"),
							fonteAttachment.cRev,
							fonteAttachment.cTitle,
							fonteAttachment.cUpdated,
							StringTemplate.create("(SELECT b.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " b WHERE b.c_id = " + fonteAttachment.cUri + ") as c_uri"),
							fonteAttachment.cUrl,
							StringTemplate.create("(SELECT c.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " c WHERE c.c_id = " + fonteAttachment.fkUriAuthor + ") || '%' || c_rev as fk_author"),
							StringTemplate.create("(SELECT d.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " d WHERE d.c_id = " + fonteAttachment.fkUriProject + ") || '%' || c_rev as fk_project"),
							StringTemplate.create("(SELECT e.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " e WHERE e.c_id = " + fonteAttachment.fkUriAuthor + ") as fk_uri_author"),
							StringTemplate.create("(SELECT f.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " f WHERE f.c_id = " + fonteAttachment.fkUriProject + ") as fk_uri_project"),
							StringTemplate.create("(SELECT g.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " g WHERE g.c_id = " + fonteAttachment.fkUriWorkitem + ") as fk_uri_workitem"),
							StringTemplate.create("(SELECT h.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " h WHERE h.c_id = " + fonteAttachment.fkUriWorkitem + ") || '%' || c_rev as fk_workitem")
							);

			for (Tuple row : attachments) {
				Object[] vals = row.toArray();
				
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
								stgAttachment.sissHistoryAttachmentPk
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
								DataEsecuzione.getInstance().getDataEsecuzione(),
								StringUtils.getMaskedValue((String)vals[11]),
								vals[12],
								StringUtils.getMaskedValue((String)vals[13]),
								vals[14],
								vals[15],
								vals[16],
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
	
	public static void recoverSissHistoryAttachement() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryAttachment stgHistoryAttachment = QSissHistoryAttachment.dmalmSissHistoryAttachment;
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
