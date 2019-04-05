package lispa.sgr.siss.history;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryWorkitemRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryWorkitemRunnable;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

public class TestSISSHistoryCfWorkItem
extends TestCase 
{
	private static Logger logger = Logger.getLogger(TestSISSHistoryCfWorkItem.class);
	public void testFillStagingNewLogic() throws Exception {
//		SissHistoryWorkitemDAO.fillSissHistoryWorkitem(0);
//		SissHistoryCfWorkitemDAO.fillSissHistoryCfWorkitem(0);
	}
	
	public void testCaricamentoSISSHCFWorkitem()
	{
		try 
		{
	    //    SissHistoryCfWorkitemDAO.fillFullSissHistoryCfWorkitemJoinWorkItem();
        }
        catch (Exception e) {
	        
        }
	}

	public void testFillStaging() throws Exception
	{

		Connection OracleConnection = null;
		Connection H2Connection = null;

		try{

			ConnectionManager cm = ConnectionManager.getInstance();

			H2Connection = cm.getConnectionSISSHistory();
			OracleConnection = cm.getConnectionOracle();

			//QSissHistoryCfWorkitem   stgCFWorkItems  = QSissHistoryCfWorkitem.sissHistoryCfWorkitem;
			lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryCfWorkitem  fonteCFWorkItems  
						= lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryCfWorkitem.cfWorkitem;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(H2Connection, dialect); 

			List<Tuple> cfworkitems = query.from(fonteCFWorkItems)
					.list(
							fonteCFWorkItems.cDateonlyValue,
							fonteCFWorkItems.cFloatValue ,
							fonteCFWorkItems.cStringValue,
							fonteCFWorkItems.cDateValue,
							fonteCFWorkItems.cBooleanValue,
							fonteCFWorkItems.cName ,
							fonteCFWorkItems.fkUriWorkitem ,
							fonteCFWorkItems.fkWorkitem ,
							fonteCFWorkItems.cLongValue,
							fonteCFWorkItems.cDurationtimeValue ,
							fonteCFWorkItems.cCurrencyValue
							
							);
			
			
			
			int partitionSize = 100000;
			List<List<Tuple>> partitions = new LinkedList<List<Tuple>>();
			for (int i = 0; i < cfworkitems.size(); i += partitionSize) {
			    partitions.add(cfworkitems.subList(i,
			            i + Math.min(partitionSize, cfworkitems.size() - i)));
			}
			
			for (int i = 0; i < partitions.size(); i ++) {
				
				
				
				
			}

			
			
			OracleConnection.commit();
			
			

		}
		catch(Exception e)
		{
			
			
			
		}


	}
	public void testConcurrentFillCF() {
	
		try {
			List<Thread> sireworkitems = new ArrayList<Thread>();
			List<Thread> sissworkitems = new ArrayList<Thread>();
			for(EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {
				Thread t = new Thread(new SissHistoryWorkitemRunnable(SissHistoryWorkitemDAO.getMinRevisionByType(), Long.MAX_VALUE, logger, type));
				Thread t1 = new Thread(new SireHistoryWorkitemRunnable(SireHistoryWorkitemDAO.getMinRevisionByType(), Long.MAX_VALUE, logger, type));

				sissworkitems.add(t);
				sireworkitems.add(t1);
				t.start();
				t1.start();
			}
			for(Thread t : sissworkitems)
				t.join();
			for(Thread t1 : sireworkitems)
				t1.join();
			} catch (Exception e) {
			// 
			
		}
	}
	
	public void testSplittedCF() throws Exception {
		Log4JConfiguration.inizialize();
		logger.debug("Inizio Update CF SISS/SIRE!!!!!!");
		DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2014-07-04 20:29:00", "yyyy-MM-dd HH:mm:00"));
		SissHistoryCfWorkitemDAO.updateCFonWorkItem();
		SireHistoryCfWorkitemDAO.updateCFonWorkItem();
		logger.debug("Finito Update CF SISS/SIRE!!!!!!");
		
	}
	
}