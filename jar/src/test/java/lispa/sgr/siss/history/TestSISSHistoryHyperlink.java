package lispa.sgr.siss.history;
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
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryHyperlink;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryHyperlink;

public class TestSISSHistoryHyperlink extends TestCase {
	
	

	public void testFillStaging() throws Exception {
		Connection OracleConnection = null;
		Connection H2Connection = null;

		try{

			ConnectionManager cm = ConnectionManager.getInstance();
 
			H2Connection = cm.getConnectionSISSHistory();
			OracleConnection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates()
			{ {
			    setPrintSchema(true);
			}};
			SQLQuery query = new SQLQuery(H2Connection, dialect); 

			SissHistoryHyperlink fonteHyperlink = SissHistoryHyperlink.structWorkitemHyperlinks;
			QSissHistoryHyperlink stgHyperlink = QSissHistoryHyperlink.dmalmSissHistoryHyperlink;
			OracleConnection.setAutoCommit(false);
			List<Tuple> hyperlinks = query.limit(10).from(fonteHyperlink).list(fonteHyperlink.all());
								
			for (Tuple hyperlink : hyperlinks) {
				new SQLInsertClause(OracleConnection, dialect, stgHyperlink).columns(
						stgHyperlink.fkPWorkitem, 
						stgHyperlink.fkUriPWorkitem,
						stgHyperlink.cUri, 
						stgHyperlink.cRole, 
						stgHyperlink.dataCaricamento, 
						stgHyperlink.sissHistoryHyperlinkPk 
					
						).values(
						hyperlink.get(fonteHyperlink.fkPWorkitem),		
						hyperlink.get(fonteHyperlink.fkUriPWorkitem),
						hyperlink.get(fonteHyperlink.cUrl),
						hyperlink.get(fonteHyperlink.cRole),
						DataEsecuzione.getInstance().getDataEsecuzione(),
						StringTemplate.create("HISTORY_HYPERLINK_SEQ.nextval")
						).execute();
				OracleConnection.commit();
				
			}



		}
		catch (Exception e) {

			
		}

	}
}
