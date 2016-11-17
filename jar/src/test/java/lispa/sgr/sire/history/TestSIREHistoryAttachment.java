package lispa.sgr.sire.history;

import java.sql.Connection;
import java.util.List;


import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

import junit.framework.TestCase;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryAttachment;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryAttachment;

public class TestSIREHistoryAttachment extends TestCase {

	public void testFillStaging() throws Exception {
		Connection OracleConnection = null;
		Connection H2Connection = null;

		try{

			ConnectionManager cm = ConnectionManager.getInstance();
 
			H2Connection = cm.getConnectionSIREHistory();
			OracleConnection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates()
			{ {
			    setPrintSchema(true);
			}};
			SQLQuery query = new SQLQuery(H2Connection, dialect); 

			SireHistoryAttachment fonteAttachment = SireHistoryAttachment.attachment;
			QSireHistoryAttachment stgAttachment = QSireHistoryAttachment.dmalmSireHistoryAttachment;
			OracleConnection.setAutoCommit(false);
			List<Tuple> attachments = query.limit(10).from(fonteAttachment).list(fonteAttachment.all());
								
			for (Tuple attachment : attachments) {
				new SQLInsertClause(OracleConnection, dialect, stgAttachment).columns(
						stgAttachment.cPk, 
						stgAttachment.cUri,
						stgAttachment.cRev, 
						stgAttachment.cDeleted, 
						stgAttachment.cIsLocal, 
						stgAttachment.fkAuthor, 
						stgAttachment.fkUriAuthor, 
						stgAttachment.cFilename, 
						stgAttachment.cId,
						stgAttachment.fkProject,
						stgAttachment.fkUriProject,
						stgAttachment.cTitle,
						stgAttachment.cUpdated,
						stgAttachment.cUrl, 
						stgAttachment.fkWorkitem, 
						stgAttachment.fkUriWorkitem,
						stgAttachment.dataCaricamento,
						stgAttachment.sireHistoryAttachmentPk,
						stgAttachment.cLength
						).values(
						attachment.get(fonteAttachment.cPk),		
						attachment.get(fonteAttachment.cUri),
						attachment.get(fonteAttachment.cRev),
						attachment.get(fonteAttachment.cDeleted),
						attachment.get(fonteAttachment.cIsLocal),
						attachment.get(fonteAttachment.fkAuthor),
						attachment.get(fonteAttachment.fkUriAuthor),
						attachment.get(fonteAttachment.cFilename),
						attachment.get(fonteAttachment.cId),
						attachment.get(fonteAttachment.fkProject),
						attachment.get(fonteAttachment.fkUriProject),
						attachment.get(fonteAttachment.cTitle),
						attachment.get(fonteAttachment.cUpdated),
						attachment.get(fonteAttachment.cUrl),
						attachment.get(fonteAttachment.fkWorkitem),
						attachment.get(fonteAttachment.fkUriWorkitem),
						DataEsecuzione.getInstance().getDataEsecuzione(),
						StringTemplate.create("HISTORY_ATTACHMENT_SEQ.nextval"),
						attachment.get(fonteAttachment.cLength)
						).execute();
				OracleConnection.commit();
				
			}



		}
		catch (Exception e) {

			
		}

	}
}
