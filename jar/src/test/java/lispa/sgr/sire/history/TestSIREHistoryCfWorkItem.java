package lispa.sgr.sire.history;


import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.VSireHistoryWorkitemLinkDAO;
import lispa.schedulers.dao.sgr.siss.history.VSissHistoryWorkitemLinkDAO;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryCfWorkitem;

public class TestSIREHistoryCfWorkItem
extends TestCase 
{
	
	public void testFillVSireSissWorkitemLink() throws Exception {
		//VSireHistoryWorkitemLinkDAO.fillVSireWorkitemLink();
		//VSissHistoryWorkitemLinkDAO.fillVSissWorkitemLink();
		VSissHistoryWorkitemLinkDAO.delete();
		VSireHistoryWorkitemLinkDAO.delete();
		
	}
	
	public void testFillStagingNewLogic() throws Exception {
//		SireHistoryWorkitemDAO.fillSireHistoryWorkitem(0);
//		SireHistoryCfWorkitemDAO.fillSireHistoryCfWorkitem(0);
//		SissHistoryWorkitemDAO.fillSissHistoryWorkitem(0);
//		SissHistoryCfWorkitemDAO.fillSissHistoryCfWorkitem(0);
	}

	public void testFillStaging() throws Exception
	{

		//		C_DATEONLY_VALUE
		//		C_FLOAT_VALUE
		//		C_STRING_VALUE
		//		C_DATE_VALUE
		//		C_BOOLEAN_VALUE
		//		C_NAME
		//		FK_URI_WORKITEM
		//		FK_WORKITEM
		//		C_LONG_VALUE
		//		C_DURATIONTIME_VALUE
		//		C_CURRENCY_VALUE
		//		DATA_CARICAMENTO
		
		Connection OracleConnection = null;
		Connection H2Connection = null;

		try{

			ConnectionManager cm = ConnectionManager.getInstance();

			H2Connection = cm.getConnectionSIREHistory();
			OracleConnection = cm.getConnectionOracle();

			QSireHistoryCfWorkitem   stgCFWorkItems  = QSireHistoryCfWorkitem.sireHistoryCfWorkitem;
			lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryCfWorkitem  fonteCFWorkItems  = 
					lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryCfWorkitem.sireHistoryCfWorkitem;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(H2Connection, dialect); 

			List<Tuple> cfworkitems = query.from(fonteCFWorkItems)
					.list(new QTuple(
							StringTemplate.create("FORMATDATETIME("+fonteCFWorkItems.cDateonlyValue+", 'yyyy-MM-dd 00:00:00')"),
							fonteCFWorkItems.cFloatValue ,
							StringTemplate.create("SUBSTRING("+fonteCFWorkItems.cStringValue+",0,4000)") ,
							StringTemplate.create("FORMATDATETIME("+fonteCFWorkItems.cDateValue+", 'yyyy-MM-dd HH:mm:ss')"),
							StringTemplate.create("CASEWHEN ("+fonteCFWorkItems.cBooleanValue+"= 'true', 1,0 )") ,
							StringTemplate.create("SUBSTRING("+fonteCFWorkItems.cName+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+fonteCFWorkItems.fkUriWorkitem+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+fonteCFWorkItems.fkWorkitem+",0,4000)") ,
							fonteCFWorkItems.cLongValue,
							fonteCFWorkItems.cDurationtimeValue ,
							fonteCFWorkItems.cCurrencyValue
							)
							);

			Iterator<Tuple> i = cfworkitems.iterator();
			Object[] el= null;
			
			OracleConnection.setAutoCommit(false);
			
			while (i.hasNext()) {

				el= ((Tuple)i.next()).toArray();

				//				
				//				
				//				
				//				
				//				
				//				
				//				
				//				
				//				
				//				
				//				
				//				

				new SQLInsertClause(OracleConnection, dialect, stgCFWorkItems)
				.columns(
						stgCFWorkItems.cDateonlyValue ,
						stgCFWorkItems.cFloatValue ,
						stgCFWorkItems.cStringValue,
						stgCFWorkItems.cDateValue ,
						stgCFWorkItems.cBooleanValue ,
						stgCFWorkItems.cName ,
						stgCFWorkItems.fkUriWorkitem,
						stgCFWorkItems.fkWorkitem,
						stgCFWorkItems.cLongValue,
						stgCFWorkItems.cDurationtimeValue ,
						stgCFWorkItems.cCurrencyValue,
						stgCFWorkItems.dataCaricamento
						)
				.values(
						el[0] , 
						el[1] , 
						el[2] , 
						el[3] , 
						el[4] , 
						el[5] , 
						el[6] , 
						el[7] , 
						el[8] , 
						el[9] , 
						el[10], 
						StringTemplate.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))")
						)
						.execute();


			}
			
			OracleConnection.commit();
			
			

		}
		catch(Exception e)
		{
			
			
			
		}



	}
}