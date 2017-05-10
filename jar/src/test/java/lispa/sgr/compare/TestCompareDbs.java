package lispa.sgr.compare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import junit.framework.TestCase;
import lispa.schedulers.bean.staging.sgr.ProjectCName;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireCurrentProject;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class TestCompareDbs extends TestCase {
	private static Logger logger = Logger
			.getLogger(TestCompareDbs.class);

	
	public void testCompareDbs() throws Exception
	{

		Class.forName("oracle.jdbc.driver.OracleDriver");

		Connection pgOr = null, h2Or = null;

		pgOr = DriverManager.getConnection(
				"jdbc:oracle:thin:@10.0.0.114:1521:orcl",
				"DM_ALM_STG_PSQL_SV",
				"DM_ALM_STG_PSQL_SV"
				);
		
		h2Or = DriverManager.getConnection(
				"jdbc:oracle:thin:@10.0.0.114:1521:orcl",
				"DM_ALM_STG_SV",
				"DM_ALM_STG_SV"
				);
		
			
		LinkedList<String> selects = new LinkedList<String>();
		
		selects.add("SELECT * FROM DMALM_PROJECT_ROLES");
		selects.add("SELECT * FROM DMALM_SIRE_CURRENT_PROJECT");
		selects.add("SELECT * FROM DMALM_SIRE_CURRENT_WORK_LINKED");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_ATTACHMENT");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_CF_WORKITEM");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_HYPERLINK");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_PROJECT");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_PROJGROUP");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_REVISION");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_USER");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_WORK_LINKED");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_WORKITEM");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_WORKUSERASS");
		selects.add("SELECT * FROM DMALM_SISS_CURRENT_PROJECT");
		selects.add("SELECT * FROM DMALM_SISS_CURRENT_WORK_LINKED");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_ATTACHMENT");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_CF_WORKITEM");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_HYPERLINK");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_PROJECT");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_PROJGROUP");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_REVISION");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_USER");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_WORK_LINKED");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_WORKITEM");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_WORKUSERASS");
		selects.add("SELECT * FROM DMALM_STATO_WORKITEM_SVN");
		selects.add("SELECT * FROM DMALM_STG_CURRENT_WORKITEMS");
		selects.add("SELECT * FROM DMALM_STG_SCHEDE_SERVIZIO");
		selects.add("SELECT * FROM DMALM_USER_ROLES");
		
		
		
		/*
		selects.add("SELECT * FROM DMALM_SISS_CURRENT_WORK_LINKED");
		selects.add("SELECT * FROM DMALM_SISS_CURRENT_PROJECT ORDER BY c_id");
		selects.add("SELECT * FROM DMALM_SIRE_CURRENT_WORK_LINKED");
		selects.add("SELECT * FROM DMALM_SIRE_CURRENT_PROJECT ORDER BY c_id");
		//history
		selects.add("SELECT c_active,c_deleted,c_finish,c_id,c_location,c_lockworkrecordsdate,c_name,c_rev,c_start,c_trackerprefix FROM DMALM_SISS_HISTORY_PROJECT ORDER BY c_id");
		*/
		
		for(String select : selects)
		{
			Statement stPgOr = pgOr.createStatement();
	    	ResultSet rsPgOr = stPgOr.executeQuery(select);
	    	
	    	Statement stH2Or = h2Or.createStatement();
	    	ResultSet rsH2Or = stH2Or.executeQuery(select);
	    	
	    	int sloupcu = rsPgOr.getMetaData().getColumnCount();
	    	if(rsH2Or.getMetaData().getColumnCount() != sloupcu)
	    	{
	    		logger.error("Diferent column count. " + select);
	    		return;
	    	}

	    	int rowIndex = 1;
	    	while(true)
	    	{
		    	boolean h2Next = rsH2Or.next();
		    	boolean pgNext = rsPgOr.next();
		    	
		    	if(pgNext != h2Next)
		    	{
		    		logger.error("Different count of records: " + rowIndex + " " + select);
		    		break;
		    	}
		    	
		    	if(!pgNext || !h2Next)
		    	{
		    		logger.info("OK " + select);
		    		break;
		    	}
		    	
		    	
		    	//boolean broken = false;
		    	for(int i = 1; i <= sloupcu; i++)
		    	{
		    		Object firstVal = rsPgOr.getObject(i);
		    		Object secondVal = rsH2Or.getObject(i);
		    		
		    		TestCase.assertEquals("Column " + rsPgOr.getMetaData().getColumnLabel(i) + " has to have on row " + rowIndex + " equal values." , firstVal, secondVal);
		    					 
		    		/*
		    		if(firstVal == null && secondVal == null)
		    			continue;
		    		
		    		if(firstVal == null || secondVal == null || !rsPgOr.getObject(i).toString().equals(rsH2Or.getObject(i).toString()))
		    		{
		    			logger.error("Different data - row: " + rowsCount + " first val: " + firstVal + " second val: " + secondVal + " columnName: " + rsPgOr.getMetaData().getColumnLabel(i) + " " + select);
		    			
		    			for(int ii = 1; ii <= sloupcu; ii++)
				    	{
		    				System.out.println(rsPgOr.getObject(ii) + " - " + rsH2Or.getObject(ii));
				    	}

		    			broken = true;
		    			//break;
		    			throw new Exception();
		    		}*/
		    	}
		    	
		    	//System.out.println();
		    	/*
		    	if(broken)
		    		break;
		    	*/
		    	rowIndex++;
	    	}  	
		}
	}
}