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
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class TestCompareDbs extends TestCase {
	
	@Rule
    public ErrorCollector collector = new ErrorCollector();
	
	@Test
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
		
		selects.add("SELECT * FROM DMALM_PROJECT_ROLES ORDER BY ruolo;project_roles_pk");
		//selects.add("SELECT * FROM DMALM_SIRE_CURRENT_PROJECT ORDER BY C_TRACKERPREFIX;data_caricamento;sire_current_project_pk");
		selects.add("SELECT * FROM DMALM_SIRE_CURRENT_PROJECT ORDER BY C_PK");
		//selects.add("SELECT * FROM DMALM_SIRE_CURRENT_WORK_LINKED ORDER BY fk_uri_p_workitem, fk_uri_workitem, fk_workitem, fk_p_workitem;data_caricamento;sire_current_work_linked_pk");
		selects.add("SELECT * FROM DMALM_SIRE_CURRENT_WORK_LINKED ORDER BY fk_uri_p_workitem, fk_uri_workitem, fk_workitem, fk_p_workitem");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_ATTACHMENT order by c_pk");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_CF_WORKITEM order by c_name, fk_workitem, fk_uri_workitem");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_HYPERLINK order by fk_p_workitem, c_role");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_PROJECT order by c_pk");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_PROJGROUP order by c_pk");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_REVISION order by c_pk");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_USER order by c_pk");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_WORK_LINKED order by fk_p_workitem, fk_uri_workitem");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_WORKITEM order by c_pk");
		selects.add("SELECT * FROM DMALM_SIRE_HISTORY_WORKUSERASS order by fk_workitem, fk_user");
		selects.add("SELECT * FROM DMALM_SISS_CURRENT_PROJECT ORDER BY C_TRACKERPREFIX;data_caricamento;siss_current_project_pk order by c_pk");
		selects.add("SELECT * FROM DMALM_SISS_CURRENT_WORK_LINKED ORDER BY fk_uri_p_workitem, fk_uri_workitem, fk_workitem, fk_p_workitem");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_ATTACHMENT order by c_pk");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_CF_WORKITEM order by c_name, fk_workitem, fk_uri_workitem");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_HYPERLINK order by fk_p_workitem, c_role");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_PROJECT order by c_pk");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_PROJGROUP order by c_pk");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_REVISION order by c_pk");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_USER order by c_pk");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_WORK_LINKED order by fk_p_workitem, fk_uri_workitem");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_WORKITEM order by c_pk");
		selects.add("SELECT * FROM DMALM_SISS_HISTORY_WORKUSERASS order by fk_workitem, fk_user");
		selects.add("SELECT * FROM DMALM_STATO_WORKITEM_SVN order by id");
		selects.add("SELECT * FROM DMALM_STG_CURRENT_WORKITEMS order by stg_pk");
		selects.add("SELECT * FROM DMALM_STG_SCHEDE_SERVIZIO order by id");
		selects.add("SELECT * FROM DMALM_USER_ROLES order by id_project");
		
		
		
		/*
		selects.add("SELECT * FROM DMALM_SISS_CURRENT_WORK_LINKED");
		selects.add("SELECT * FROM DMALM_SISS_CURRENT_PROJECT ORDER BY c_id");
		selects.add("SELECT * FROM DMALM_SIRE_CURRENT_WORK_LINKED");
		selects.add("SELECT * FROM DMALM_SIRE_CURRENT_PROJECT ORDER BY c_id");
		//history
		selects.add("SELECT c_active,c_deleted,c_finish,c_id,c_location,c_lockworkrecordsdate,c_name,c_rev,c_start,c_trackerprefix FROM DMALM_SISS_HISTORY_PROJECT ORDER BY c_id");
		*/
		
		for(String s : selects)
		{
			String[] split = s.split(";");
			String select = split[0];
			
			Statement stPgOr = pgOr.createStatement();
	    	ResultSet rsPgOr = stPgOr.executeQuery(select);
	    	
	    	Statement stH2Or = h2Or.createStatement();
	    	ResultSet rsH2Or = stH2Or.executeQuery(select);
	    	
	    	int sloupcu = rsPgOr.getMetaData().getColumnCount();
	    	if(rsH2Or.getMetaData().getColumnCount() != sloupcu)
	    	{
	    		System.out.println("Diferent column count. " + select);
	    		return;
	    	}

	    	int rowIndex = 1;
	    	while(true)
	    	{
		    	boolean h2Next = rsH2Or.next();
		    	boolean pgNext = rsPgOr.next();
		    	
		    	if(pgNext != h2Next)
		    	{
		    		System.out.println("Different count of records: " + rowIndex + " " + select);
		    		break;
		    	}
		    	
		    	if(!pgNext || !h2Next)
		    	{
		    		System.out.println("OK " + select);
		    		break;
		    	}
		    	
		    	
		    	//boolean broken = false;
		    	for(int i = 1; i <= sloupcu; i++)
		    	{
		    		Object firstVal = rsPgOr.getObject(i);
		    		Object secondVal = rsH2Or.getObject(i);
		    		
		    		
					//collector.checkThat("Column " + rsPgOr.getMetaData().getColumnLabel(i) + " has to have on row " + rowIndex + " equal values.", firstVal, CoreMatchers.equalTo(secondVal));
					
					boolean ignored = false;
					for(int index = 1; index < split.length; index++)
					{
						if(rsPgOr.getMetaData().getColumnLabel(i).toLowerCase().equals(split[index].toLowerCase()))
						{
							ignored = true;
							break;
						}
					}
					if(ignored)
						continue;
					
		    		/*
		    		try
		    		{*/
		    		//	TestCase.assertEquals("Column " + rsPgOr.getMetaData().getColumnLabel(i) + " has to have on row " + rowIndex + " equal values." + select , firstVal, secondVal);
		    		/*}
		    		catch(Exception exc)
		    		{
		    			this.collector.addError(exc);
		    			
		    		}
		    		*/
		    		
		    		
		    		
		    		
		    		
		    		if(firstVal == null && secondVal == null)
		    			continue;
		    		
		    		if(firstVal == null || secondVal == null || !rsPgOr.getObject(i).toString().equals(rsH2Or.getObject(i).toString()))
		    		{
		    			System.out.println("Different data - row: " + rowIndex + " first val: " + firstVal + " second val: " + secondVal + " columnName: " + rsPgOr.getMetaData().getColumnLabel(i) + " " + select);
		    			
		    			for(int ii = 1; ii <= sloupcu; ii++)
				    	{
		    				System.out.println(rsPgOr.getObject(ii) + " - " + rsH2Or.getObject(ii));
				    	}
/*
		    			broken = true;
		    			break;*/
		    		}
		    	}
		    	
		    	System.out.println();
		    	/*
		    	if(broken)
		    		break;
		    	*/
		    	rowIndex++;
	    	}  	
		}
	}
}