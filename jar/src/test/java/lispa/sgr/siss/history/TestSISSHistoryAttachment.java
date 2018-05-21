package lispa.sgr.siss.history;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryAttachmentDAO;
import lispa.schedulers.manager.Log4JConfiguration;

public class TestSISSHistoryAttachment extends TestCase {

	public void testFillStaging() throws Exception {
		Log4JConfiguration.inizialize();

		SissHistoryAttachmentDAO.fillSissHistoryAttachment(
				SissHistoryAttachmentDAO.getMinRevision(), Long.MAX_VALUE);

//		SireHistoryAttachmentDAO.fillSireHistoryAttachment(
//				SireHistoryAttachmentDAO.getMinRevision(), Long.MAX_VALUE);

	
//		Connection OracleConnection = null;
//		Connection H2Connection = null;
//
//		try{
//
//			ConnectionManager cm = ConnectionManager.getInstance();
// 
//			H2Connection = cm.getConnectionSISSHistory();
//			OracleConnection = cm.getConnectionOracle();
//
//			SQLTemplates dialect = new HSQLDBTemplates()
//			{ {
//			    setPrintSchema(true);
//			}};
//			SQLQuery query = new SQLQuery(H2Connection, dialect); 
//
//			SissHistoryAttachment fonteAttachment = SissHistoryAttachment.attachment;
//			QSissHistoryAttachment stgAttachment = QSissHistoryAttachment.dmalmSissHistoryAttachment;
//			OracleConnection.setAutoCommit(false);
//			List<Tuple> attachments = query.limit(10).from(fonteAttachment).list(fonteAttachment.all());
//								
//			for (Tuple attachment : attachments) {
//				new SQLInsertClause(OracleConnection, dialect, stgAttachment).columns(
//						stgAttachment.cPk, 
//						stgAttachment.cUri,
//						stgAttachment.cRev, 
//						stgAttachment.cDeleted, 
//						stgAttachment.cIsLocal, 
//						stgAttachment.fkAuthor, 
//						stgAttachment.fkUriAuthor, 
//						stgAttachment.cFilename, 
//						stgAttachment.cId,
//						stgAttachment.fkProject,
//						stgAttachment.fkUriProject,
//						stgAttachment.cTitle,
//						stgAttachment.cUpdated,
//						stgAttachment.cUrl, 
//						stgAttachment.fkWorkitem, 
//						stgAttachment.fkUriWorkitem,
//						stgAttachment.dataCaricamento,
//						stgAttachment.sissHistoryAttachmentPk,
//						stgAttachment.cLength
//						).values(
//						attachment.get(fonteAttachment.cPk),		
//						attachment.get(fonteAttachment.cUri),
//						attachment.get(fonteAttachment.cRev),
//						attachment.get(fonteAttachment.cDeleted),
//						attachment.get(fonteAttachment.cIsLocal),
//						attachment.get(fonteAttachment.fkAuthor),
//						attachment.get(fonteAttachment.fkUriAuthor),
//						attachment.get(fonteAttachment.cFilename),
//						attachment.get(fonteAttachment.cId),
//						attachment.get(fonteAttachment.fkProject),
//						attachment.get(fonteAttachment.fkUriProject),
//						attachment.get(fonteAttachment.cTitle),
//						attachment.get(fonteAttachment.cUpdated),
//						attachment.get(fonteAttachment.cUrl),
//						attachment.get(fonteAttachment.fkWorkitem),
//						attachment.get(fonteAttachment.fkUriWorkitem),
//						DataEsecuzione.getInstance().getDataEsecuzione(),
//						StringTemplate.create("HISTORY_ATTACHMENT_SEQ.nextval"),
//						attachment.get(fonteAttachment.cLength)
//						).execute();
//				OracleConnection.commit();
//				
//			}
//
//
//
//		}
//		catch (Exception e) {
//
//			
//		}

	}
}
