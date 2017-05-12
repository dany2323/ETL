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
		
		selects.add("DMALM_SISS_HISTORY_WORK_LINKED;DATA_CARICAMENTO;SISS_HISTORY_WORK_LINKED_PK");
		

		
		
		//selects.add("DMALM_PROJECT_ROLES;ruolo;project_roles_pk");
		//selects.add("DMALM_SIRE_CURRENT_PROJECT;C_TRACKERPREFIX;data_caricamento;sire_current_project_pk");
		selects.add("DMALM_SIRE_CURRENT_PROJECT;SIRE_CURRENT_PROJECT_PK;DATA_CARICAMENTO");
		selects.add("DMALM_SISS_CURRENT_PROJECT;data_caricamento;siss_current_project_pk");
		//selects.add("DMALM_SIRE_CURRENT_WORK_LINKED;fk_uri_p_workitem, fk_uri_workitem, fk_workitem, fk_p_workitem;data_caricamento;sire_current_work_linked_pk");
		
		selects.add("DMALM_SIRE_CURRENT_WORK_LINKED;DATA_CARICAMENTO;SIRE_CURRENT_WORK_LINKED_PK");
		selects.add("DMALM_SISS_CURRENT_WORK_LINKED;DATA_CARICAMENTO;SISS_CURRENT_WORK_LINKED_PK");
		
		
		
		//selects.add("DMALM_SIRE_HISTORY_WORK_LINKED;fk_uri_p_workitem, fk_uri_workitem, fk_workitem, fk_p_workitem;DATA_CARICAMENTO");
		
		
		selects.add("DMALM_SIRE_HISTORY_ATTACHMENT;SIRE_HISTORY_ATTACHMENT_PK;DATA_CARICAMENTO;C_URL");
		selects.add("DMALM_SISS_HISTORY_ATTACHMENT;SISS_HISTORY_ATTACHMENT_PK;DATA_CARICAMENTO;C_URL");
		
		
		//skipped
		selects.add("DMALM_SIRE_HISTORY_CF_WORKITEM;c_name, fk_workitem, fk_uri_workitem;SIRE_HISTORY_CF_WORKITEM_PK;DATA_CARICAMENTO");
		selects.add("DMALM_SISS_HISTORY_CF_WORKITEM;c_name, fk_workitem, fk_uri_workitem;SISS_HISTORY_CF_WORKITEM_PK;DATA_CARICAMENTO");
		
		//skipped
		selects.add("DMALM_SIRE_HISTORY_HYPERLINK;fk_p_workitem, c_role, c_uri;DATA_CARICAMENTO;SIRE_HISTORY_HYPERLINK_PK");
		selects.add("DMALM_SISS_HISTORY_HYPERLINK;fk_p_workitem, c_role, c_uri;DATA_CARICAMENTO;SISS_HISTORY_HYPERLINK_PK");
		
		
		
		
		
		selects.add("DMALM_SIRE_HISTORY_PROJECT;SIRE_HISTORY_PROJECT_PK;DATA_CARICAMENTO");
		selects.add("DMALM_SIRE_HISTORY_PROJGROUP;DATA_CARICAMENTO;SIRE_HISTORY_PROJGROUP_PK");
		selects.add("DMALM_SIRE_HISTORY_REVISION");
		selects.add("DMALM_SIRE_HISTORY_USER");
		
		selects.add("DMALM_SIRE_HISTORY_WORKITEM");
		selects.add("DMALM_SIRE_HISTORY_WORKUSERASS;fk_workitem, fk_user");
		selects.add("DMALM_SISS_CURRENT_PROJECT;C_TRACKERPREFIX;data_caricamento;siss_current_project_pk");
		
		
		
		
		selects.add("DMALM_SISS_HISTORY_PROJECT;SISS_HISTORY_PROJECT_PK;DATA_CARICAMENTO");
		selects.add("DMALM_SISS_HISTORY_PROJGROUP;DATA_CARICAMENTO;SISS_HISTORY_PROJECTGRP_PK");
		selects.add("DMALM_SISS_HISTORY_REVISION");
		selects.add("DMALM_SISS_HISTORY_USER");
		selects.add("DMALM_SISS_HISTORY_WORK_LINKED;fk_p_workitem, fk_uri_workitem");
		selects.add("DMALM_SISS_HISTORY_WORKITEM");
		selects.add("DMALM_SISS_HISTORY_WORKUSERASS;fk_workitem, fk_user");
		selects.add("DMALM_STATO_WORKITEM_SVN;id,WORKITEM_TYPE,TEMPLATE;DATA_CARICAMENTO;STATO_WORKITEM_PK");
		selects.add("DMALM_STG_CURRENT_WORKITEMS;stg_pk");
		selects.add("DMALM_STG_SCHEDE_SERVIZIO;id;DT_CARICAMENTO;DMALM_SCHEDE_SERVIZIO_PK");
		selects.add("DMALM_USER_ROLES;utente,ruolo;user_roles_pk,repository;user_roles_pk;DATA_CARICAMENTO;DATA_MODIFICA");
		
		/*
		
		
		
		//selects.add("DMALM_PROJECT_ROLES;ruolo;project_roles_pk");
		//selects.add("DMALM_SIRE_CURRENT_PROJECT;C_TRACKERPREFIX;data_caricamento;sire_current_project_pk");
		selects.add("DMALM_SIRE_CURRENT_PROJECT;C_PK;SIRE_CURRENT_PROJECT_PK;DATA_CARICAMENTO");
		selects.add("DMALM_SISS_CURRENT_PROJECT;C_PK;data_caricamento;siss_current_project_pk");
		//selects.add("DMALM_SIRE_CURRENT_WORK_LINKED;fk_uri_p_workitem, fk_uri_workitem, fk_workitem, fk_p_workitem;data_caricamento;sire_current_work_linked_pk");
		
		selects.add("DMALM_SIRE_CURRENT_WORK_LINKED;fk_uri_p_workitem, fk_uri_workitem, fk_workitem, fk_p_workitem;DATA_CARICAMENTO;SIRE_CURRENT_WORK_LINKED_PK");
		selects.add("DMALM_SISS_CURRENT_WORK_LINKED;fk_uri_p_workitem, fk_uri_workitem, fk_workitem, fk_p_workitem;DATA_CARICAMENTO;SISS_CURRENT_WORK_LINKED_PK");
		
		
		
		//selects.add("DMALM_SIRE_HISTORY_WORK_LINKED;fk_uri_p_workitem, fk_uri_workitem, fk_workitem, fk_p_workitem;DATA_CARICAMENTO");
		
		
		selects.add("DMALM_SIRE_HISTORY_ATTACHMENT;c_pk;SIRE_HISTORY_ATTACHMENT_PK;DATA_CARICAMENTO;C_URL");
		selects.add("DMALM_SISS_HISTORY_ATTACHMENT;c_pk;SISS_HISTORY_ATTACHMENT_PK;DATA_CARICAMENTO;C_URL");
		
		
		//skipped
		selects.add("DMALM_SIRE_HISTORY_CF_WORKITEM;c_name, fk_workitem, fk_uri_workitem;SIRE_HISTORY_CF_WORKITEM_PK;DATA_CARICAMENTO");
		selects.add("DMALM_SISS_HISTORY_CF_WORKITEM;c_name, fk_workitem, fk_uri_workitem;SISS_HISTORY_CF_WORKITEM_PK;DATA_CARICAMENTO");
		
		//skipped
		selects.add("DMALM_SIRE_HISTORY_HYPERLINK;fk_p_workitem, c_role, c_uri;DATA_CARICAMENTO;SIRE_HISTORY_HYPERLINK_PK");
		selects.add("DMALM_SISS_HISTORY_HYPERLINK;fk_p_workitem, c_role, c_uri;DATA_CARICAMENTO;SISS_HISTORY_HYPERLINK_PK");
		
		
		
		
		
		selects.add("DMALM_SIRE_HISTORY_PROJECT;c_pk;SIRE_HISTORY_PROJECT_PK;DATA_CARICAMENTO");
		selects.add("DMALM_SIRE_HISTORY_PROJGROUP;c_pk;DATA_CARICAMENTO;SIRE_HISTORY_PROJGROUP_PK");
		selects.add("DMALM_SIRE_HISTORY_REVISION;c_pk");
		selects.add("DMALM_SIRE_HISTORY_USER;c_pk");
		
		selects.add("DMALM_SIRE_HISTORY_WORKITEM;c_pk");
		selects.add("DMALM_SIRE_HISTORY_WORKUSERASS;fk_workitem, fk_user");
		selects.add("DMALM_SISS_CURRENT_PROJECT;C_TRACKERPREFIX;data_caricamento;siss_current_project_pk;c_pk");
		
		
		
		
		selects.add("DMALM_SISS_HISTORY_PROJECT;c_pk;SISS_HISTORY_PROJECT_PK;DATA_CARICAMENTO");
		selects.add("DMALM_SISS_HISTORY_PROJGROUP;c_pk;DATA_CARICAMENTO;SISS_HISTORY_PROJECTGRP_PK");
		selects.add("DMALM_SISS_HISTORY_REVISION;c_pk");
		selects.add("DMALM_SISS_HISTORY_USER;c_pk");
		selects.add("DMALM_SISS_HISTORY_WORK_LINKED;fk_p_workitem, fk_uri_workitem");
		selects.add("DMALM_SISS_HISTORY_WORKITEM;c_pk");
		selects.add("DMALM_SISS_HISTORY_WORKUSERASS;fk_workitem, fk_user");
		selects.add("DMALM_STATO_WORKITEM_SVN;id,WORKITEM_TYPE,TEMPLATE;DATA_CARICAMENTO;STATO_WORKITEM_PK");
		selects.add("DMALM_STG_CURRENT_WORKITEMS;stg_pk");
		selects.add("DMALM_STG_SCHEDE_SERVIZIO;id;DT_CARICAMENTO;DMALM_SCHEDE_SERVIZIO_PK");
		selects.add("DMALM_USER_ROLES;utente,ruolo;user_roles_pk,repository;user_roles_pk;DATA_CARICAMENTO;DATA_MODIFICA");
		*/
		
		
		/*
		selects.add("DMALM_SISS_CURRENT_WORK_LINKED");
		selects.add("DMALM_SISS_CURRENT_PROJECT;c_id");
		selects.add("DMALM_SIRE_CURRENT_WORK_LINKED");
		selects.add("DMALM_SIRE_CURRENT_PROJECT;c_id");
		//history
		selects.add("SELECT c_active,c_deleted,c_finish,c_id,c_location,c_lockworkrecordsdate,c_name,c_rev,c_start,c_trackerprefix FROM DMALM_SISS_HISTORY_PROJECT;c_id");
		*/
		
		for(String s : selects)
		{
			String[] split = s.split(";");
			String select = "SELECT * FROM " + split[0];
			
			System.out.println("**************************** Table " + split[0] + " *********************************");
			
			Statement stPgOr = pgOr.createStatement();
	    	ResultSet rsPgOr = stPgOr.executeQuery(select);
	    	
	    	Statement stH2Or = h2Or.createStatement();
	    	ResultSet rsH2Or = stH2Or.executeQuery(select);
	    	
	    	int sloupcu = rsPgOr.getMetaData().getColumnCount();
	    	if(rsH2Or.getMetaData().getColumnCount() != sloupcu)
	    	{
	    		System.out.println("Diferent column count. " + select);
	    		continue;
	    	}
	    	
	    	ResultSet cPg = pgOr.prepareStatement("SELECT COUNT(*) FROM (" + select + ") tbl").executeQuery();
	    	cPg.next();
	    	ResultSet cH2 = h2Or.prepareStatement("SELECT COUNT(*) FROM (" + select + ") tbl").executeQuery();
	    	cH2.next();
	    	
	    	System.out.println("H2 rows: " + cH2.getInt(1));
	    	System.out.println("PG rows: " + cPg.getInt(1));
	    	
	    	if(cPg.getInt(1) != cH2.getInt(1))
	    	{
	    		System.out.println("Diferent rows count. " + select);
	    		//continue;
	    	}
	    	
	    	System.out.println("...");
	    	System.out.println();
	    	
	    	
	    	LinkedList<String[]> pgData = new LinkedList<String[]>();
	    	LinkedList<String[]> h2Data = new LinkedList<String[]>();
	    	
	    	while(rsPgOr.next())
    		{
	    		LinkedList<String> list = new LinkedList<String>();
	    		int clmCount = rsPgOr.getMetaData().getColumnCount();
	    		for(int i = 0; i < clmCount; i++)
	    		{
	    			list.add(rsPgOr.getString(i + 1));
	    		}
	    		pgData.add(list.toArray(new String[list.size()]));
    		}
	    	
	    	while(rsH2Or.next())
    		{
	    		LinkedList<Object> list = new LinkedList<Object>();
	    		int clmCount = rsH2Or.getMetaData().getColumnCount();
	    		for(int i = 0; i < clmCount; i++)
	    		{
	    			list.add(rsH2Or.getString(i + 1));
	    		}
	    		h2Data.add(list.toArray(new String[list.size()]));
    		}
	    	
	    	for(int pgIndex = 0; pgIndex < pgData.size(); pgIndex++)
	    	{
	    		for(int h2Index = 0; h2Index < h2Data.size(); h2Index++)
	    		{
	    			boolean isSameData = true;
	    			for(int clmIndex = 0; clmIndex < sloupcu; clmIndex++)
	    			{
	    				String currentColumnName = rsH2Or.getMetaData().getColumnName(clmIndex + 1).toLowerCase().trim();
	    				
	    				boolean ignored = false;
	    				for(int i = 1; i < split.length; i++)
	    				{
	    					String sColumnName = split[i].toLowerCase().trim();
	    					if(sColumnName.equals(currentColumnName))
	    					{
	    						ignored = true;
	    						break;
	    					}
	    				}
	    				
	    				if(ignored)
	    					continue;
	    				else
	    				{
		    				String pgVal = pgData.get(pgIndex)[clmIndex];
		    				String h2Val = h2Data.get(h2Index)[clmIndex];
		    				
		    				if(pgVal == null && h2Val == null)
		    					continue;
	    				
		    				if(pgVal == null || h2Val == null || !pgVal.equals(h2Val))
		    					isSameData = false;
	    				}
	    			}
	    			
	    			if(isSameData)
	    			{
	    				h2Data.remove(h2Index);
	    				pgData.remove(pgIndex);
	    				pgIndex--;
	    				break;
	    			}
	    		}
	    	}
	    	
	    	
	    	System.out.println("Rows that don't match (POSTGRESS)");
	    	for(int i = 0; i < pgData.size(); i++)
	    	{
	    		for(int clmIndex = 0; clmIndex < sloupcu; clmIndex++)
	    		{
	    			System.out.print(pgData.get(i)[clmIndex]);
	    			System.out.print("\t");
	    		}
	    		System.out.println();
	    	}
	    	
	    	System.out.println();
	    	System.out.println();
	    	System.out.println();
	    	System.out.println("Rows that don't match (H2)");
	    	for(int i = 0; i < h2Data.size(); i++)
	    	{
	    		for(int clmIndex = 0; clmIndex < sloupcu; clmIndex++)
	    		{
	    			System.out.print(h2Data.get(i)[clmIndex]);
	    			System.out.print("\t");
	    		}
	    		System.out.println();
	    	}
	    	
	    	
	    	/*
	    	boolean error = false;
	    	int rowIndex = 1;
	    	while(true)
	    	{
		    	boolean h2Next = rsH2Or.next();
		    	boolean pgNext = rsPgOr.next();
		    	
		    	if(pgNext != h2Next)
		    	{
		    		//System.out.println("Different count of records: " + rowIndex + " " + select);
		    		break;
		    	}
		    	
		    	if(!pgNext || !h2Next)
		    	{
		    		if(!error)
		    		{
		    			System.out.println("OK " + split[0]);
		    			System.out.println();
		    			System.out.println();
		    		}
		    		break;
		    	}
		    	
		    	
		    	boolean broken = false;
		    	for(int i = 1; i <= sloupcu; i++)
		    	{
		    		Object pgVal = rsPgOr.getString(i);
		    		Object h2Val = rsH2Or.getString(i);

					boolean ignored = false;
					for(int index = 2; index < split.length; index++)
					{
						if(rsPgOr.getMetaData().getColumnLabel(i).toLowerCase().trim().equals(split[index].toLowerCase().trim()))
						{
							ignored = true;
							break;
						}
					}
					if(ignored)
						continue;
					
		    		if(pgVal == null && h2Val == null)
		    			continue;
		    		
		    		if(pgVal == null || h2Val == null || !pgVal.toString().equals(h2Val.toString()))
		    		{
		    			error = true;
		    			System.out.println("Table " + split[0]);
		    			System.out.println("row: " + rowIndex + " - column " + rsPgOr.getMetaData().getColumnLabel(i));
		    			System.out.println("H2 value: " + h2Val);
		    			System.out.println("PG value: " + pgVal);
		    			System.out.println();
		    		}
		    	}
	    	
		    	
		    	if(broken)
		    		break;
		    	
		    	rowIndex++;
	    	}
	    	*/  	
		}
		
		
		System.out.println("End.");
	}
}