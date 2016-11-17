package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryAttachment;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryAttachment;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
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
		Connection connH2 = null;
		List<Tuple> attachments = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSIREHistory();
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
								
								row.get(fonteAttachment.cDeleted),
								row.get(fonteAttachment.cFilename),
								row.get(fonteAttachment.cId),
								row.get(fonteAttachment.cIsLocal),
								row.get(fonteAttachment.cLength),
								row.get(fonteAttachment.cPk),
								row.get(fonteAttachment.cRev),
								row.get(fonteAttachment.cTitle),
								row.get(fonteAttachment.cUpdated),
								row.get(fonteAttachment.cUri),
								row.get(fonteAttachment.cUrl),
								DataEsecuzione.getInstance().getDataEsecuzione(),
								row.get(fonteAttachment.fkAuthor),
								row.get(fonteAttachment.fkProject),
								row.get(fonteAttachment.fkUriAuthor),
								row.get(fonteAttachment.fkUriProject),
								row.get(fonteAttachment.fkUriWorkitem),
								row.get(fonteAttachment.fkWorkitem),
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
			if(cm != null) cm.closeConnection(connH2);
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
