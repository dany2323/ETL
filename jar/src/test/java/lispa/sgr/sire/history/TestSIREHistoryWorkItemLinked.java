package lispa.sgr.sire.history;


import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemLinkedDAO;

public class TestSIREHistoryWorkItemLinked
extends TestCase 
{

	public void testFillStaging() throws Exception
	{
		
//		FK_MODULE	VARCHAR2
//		C_IS_LOCAL	NUMBER
//		C_PRIORITY	VARCHAR2
//		C_AUTOSUSPECT	NUMBER
//		C_RESOLUTION	VARCHAR2
//		C_CREATED	TIMESTAMP(6)
//		C_OUTLINENUMBER	VARCHAR2
//		FK_PROJECT	VARCHAR2
//		C_DELETED	NUMBER
//		C_PLANNEDEND	TIMESTAMP(6)
//		C_UPDATED	TIMESTAMP(6)
//		FK_AUTHOR	VARCHAR2
//		C_URI	VARCHAR2
//		FK_URI_MODULE	VARCHAR2
//		C_TIMESPENT	FLOAT
//		C_STATUS	VARCHAR2
//		C_SEVERITY	VARCHAR2
//		C_RESOLVEDON	TIMESTAMP(6)
//		FK_URI_PROJECT	VARCHAR2
//		C_TITLE	VARCHAR2
//		C_ID	VARCHAR2
//		C_REV	NUMBER
//		C_PLANNEDSTART	TIMESTAMP(6)
//		FK_URI_AUTHOR	VARCHAR2
//		C_DUEDATE	DATE
//		C_REMAININGESTIMATE	FLOAT
//		C_TYPE	VARCHAR2
//		C_PK	VARCHAR2
//		C_LOCATION	VARCHAR2
//		FK_TIMEPOINT	VARCHAR2
//		C_INITIALESTIMATE	FLOAT
//		FK_URI_TIMEPOINT	VARCHAR2
//		C_PREVIOUSSTATUS	VARCHAR2
//		SireHistoryWorkitemLinkedDAO.fillSireHistoryWorkitemLinked(0);
		
	}
	public static void testRecoverSireHistoryWorkItemLinked() throws Exception {
		SireHistoryWorkitemLinkedDAO.recoverSireHistoryWorkItemLinked();
	}
	
}