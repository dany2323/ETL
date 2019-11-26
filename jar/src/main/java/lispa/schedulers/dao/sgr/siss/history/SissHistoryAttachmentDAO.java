package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryAttachment;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryAttachment;
import lispa.schedulers.utils.StringUtils;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SissHistoryAttachmentDAO {

	private static Logger logger = Logger.getLogger(SissHistoryAttachmentDAO.class);
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryAttachment fonteAttachment = SissHistoryAttachment.attachment;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryAttachment stg_Attachment = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryAttachment.attachment;

	public static void fillSissHistoryAttachment(long minRevision, long maxRevision) throws SQLException, DAOException {
		
		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection connH2 = null;
		List<Tuple> attachments = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSISSHistory();
			attachments = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query = new SQLQuery(connH2, dialect);

			attachments = query.from(fonteAttachment)
					.where(fonteAttachment.cRev.gt(minRevision))
					.where(fonteAttachment.cRev.loe(maxRevision))
					.list(fonteAttachment.all());

			for (Tuple row : attachments) {
				new SQLInsertClause(connOracle, dialect, stg_Attachment)
						.columns(
								stg_Attachment.cDeleted,
								stg_Attachment.cFilename,
								stg_Attachment.cId,
								stg_Attachment.cIsLocal,
								stg_Attachment.cLength,
								stg_Attachment.cPk,
								stg_Attachment.cRev,
								stg_Attachment.cTitle,
								stg_Attachment.cUpdated,
								stg_Attachment.cUri,
								stg_Attachment.cUrl,
								stg_Attachment.fkAuthor,
								stg_Attachment.fkProject,
								stg_Attachment.fkUriAuthor,
								stg_Attachment.fkUriProject,
								stg_Attachment.fkUriWorkitem,
								stg_Attachment.fkWorkitem
						).values(
								row.get(fonteAttachment.cDeleted),
								row.get(fonteAttachment.cFilename),
								row.get(fonteAttachment.cId),
								row.get(fonteAttachment.cIsLocal),
								row.get(fonteAttachment.cLength),
								row.get(fonteAttachment.cPk),
								row.get(fonteAttachment.cRev),
								StringUtils.truncate(row.get(fonteAttachment.cTitle),1000),
								row.get(fonteAttachment.cUpdated),
								row.get(fonteAttachment.cUri),
								row.get(fonteAttachment.cUrl),
								StringUtils.getMaskedValue(row.get(fonteAttachment.fkAuthor)),
								row.get(fonteAttachment.fkProject),
								StringUtils.getMaskedValue(row.get(fonteAttachment.fkUriAuthor)),
								row.get(fonteAttachment.fkUriProject),
								row.get(fonteAttachment.fkUriWorkitem),
								row.get(fonteAttachment.fkWorkitem)
						).execute();
			}
			
			connOracle.commit();
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw new DAOException(e);
		} finally {
			if(cm != null) cm.closeConnection(connH2);
			if(cm != null) cm.closeConnection(connOracle);
		}
	}

	public static long getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		try {
			
			QSissHistoryAttachment stgAttachment = QSissHistoryAttachment.dmalmSissHistoryAttachment;
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
}
