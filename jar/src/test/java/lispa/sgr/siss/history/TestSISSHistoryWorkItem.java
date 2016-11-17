package lispa.sgr.siss.history;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.action.DmAlmETL;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryWorkitem;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class TestSISSHistoryWorkItem extends TestCase {
	private static Logger logger = Logger.getLogger(DmAlmETL.class);

	// public void testFillStaging2() throws Exception {
	// Log4JConfiguration.inizialize();
	// SissHistoryWorkitemDAO.fillSissHistoryWorkitem(0);
	// }
	//
	// public void testMaxRevision() throws Exception {
	//
	// }
	//
	// public void testFill() throws Exception {
	// SissHistoryWorkitemDAO.fillFullSissHistoryWorkitem();
	// }
	//
	// public void testFillStagingNewLogic() throws Exception {
	//
	// long start = System.currentTimeMillis();
	//
	// SireHistoryCfWorkitemDAO.fillSireHistoryCfWorkitem(0);
	//
	// SireHistoryProjectDAO.fillSireHistoryProject(0);
	//
	// SireHistoryProjectGroupDAO.fillSireHistoryProjectGroup(0);
	//
	// SireHistoryUserDAO.fillSireHistoryUser(0);
	//
	// SireHistoryWorkitemLinkedDAO.fillSireHistoryWorkitemLinked(0);
	//
	// SireHistoryWorkitemUserAssignedDAO.fillSireHistoryWorkitemUserAssigned(0);
	//
	//
	//
	// start = System.currentTimeMillis();
	//
	// SissHistoryCfWorkitemDAO.fillSissHistoryCfWorkitem(0);
	//
	// SissHistoryProjectDAO.fillSissHistoryProject(0);
	//
	// SissHistoryProjectGroupDAO.fillSissHistoryProjectGroup(0);
	//
	// SissHistoryUserDAO.fillSissHistoryUser(0);
	//
	// SissHistoryWorkitemLinkedDAO.fillSissHistoryWorkitemLinked(0);
	//
	// SissHistoryWorkitemUserAssignedDAO.fillSissHistoryWorkitemUserAssigned(0);
	//
	//
	// }

	public void testFillStaging() throws Exception {

		// FK_MODULE VARCHAR2
		// C_IS_LOCAL NUMBER
		// C_PRIORITY VARCHAR2
		// C_AUTOSUSPECT NUMBER
		// C_RESOLUTION VARCHAR2
		// C_CREATED TIMESTAMP(6)
		// C_OUTLINENUMBER VARCHAR2
		// FK_PROJECT VARCHAR2
		// C_DELETED NUMBER
		// C_PLANNEDEND TIMESTAMP(6)
		// C_UPDATED TIMESTAMP(6)
		// FK_AUTHOR VARCHAR2
		// C_URI VARCHAR2
		// FK_URI_MODULE VARCHAR2
		// C_TIMESPENT FLOAT
		// C_STATUS VARCHAR2
		// C_SEVERITY VARCHAR2
		// C_RESOLVEDON TIMESTAMP(6)
		// FK_URI_PROJECT VARCHAR2
		// C_TITLE VARCHAR2
		// C_ID VARCHAR2
		// C_REV NUMBER
		// C_PLANNEDSTART TIMESTAMP(6)
		// FK_URI_AUTHOR VARCHAR2
		// C_DUEDATE DATE
		// C_REMAININGESTIMATE FLOAT
		// C_TYPE VARCHAR2
		// C_PK VARCHAR2
		// C_LOCATION VARCHAR2
		// FK_TIMEPOINT VARCHAR2
		// C_INITIALESTIMATE FLOAT
		// FK_URI_TIMEPOINT VARCHAR2
		// C_PREVIOUSSTATUS VARCHAR2

		Connection OracleConnection = null;
		Connection H2Connection = null;

		try {

			ConnectionManager cm = ConnectionManager.getInstance();

			H2Connection = cm.getConnectionSISSHistory();

			OracleConnection = cm.getConnectionOracle();
			OracleConnection.setAutoCommit(false);

			QSissHistoryWorkitem stgWorkItems = QSissHistoryWorkitem.sissHistoryWorkitem;
			lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(H2Connection, dialect);

			List<Tuple> cfworkitems = query
					.from(fonteWorkItems)
					.list(new QTuple(
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.fkModule + ",0,4000)"),
							StringTemplate.create("CASEWHEN ("
									+ fonteWorkItems.cIsLocal
									+ "= 'true', 1,0 )"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.cPriority + ",0,4000)"),
							StringTemplate.create("CASEWHEN ("
									+ fonteWorkItems.cAutosuspect
									+ "= 'true', 1,0 )"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.cResolution + ",0,4000)"),
							StringTemplate.create("FORMATDATETIME("
									+ fonteWorkItems.cCreated
									+ ", {ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.cOutlinenumber
									+ ",0,4000)"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.fkProject + ",0,4000)"),
							StringTemplate.create("CASEWHEN ("
									+ fonteWorkItems.cDeleted
									+ "= 'true', 1,0 )"),
							StringTemplate.create("FORMATDATETIME("
									+ fonteWorkItems.cPlannedend
									+ ", {ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("FORMATDATETIME("
									+ fonteWorkItems.cUpdated
									+ ",{ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.fkAuthor + ",0,4000)"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.cUri + ",0,4000)"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.fkUriModule + ",0,4000)"),
							fonteWorkItems.cTimespent,
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.cStatus + ",0,4000)"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.cSeverity + ",0,4000)"),
							StringTemplate.create("FORMATDATETIME("
									+ fonteWorkItems.cResolvedon
									+ ",{ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.fkUriProject + ",0,4000)"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.cTitle + ",0,4000)"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.cId + ",0,4000)"),
							fonteWorkItems.cRev, StringTemplate
									.create("FORMATDATETIME("
											+ fonteWorkItems.cPlannedstart
											+ ", {ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("SUBSTRING("
									+ fonteWorkItems.fkUriAuthor + ",0,4000)"),
							StringTemplate.create("FORMATDATETIME("
									+ fonteWorkItems.cDuedate
									+ ", {ts 'yyyy-MM-dd 00:00:00'})"),
							fonteWorkItems.cRemainingestimate, StringTemplate
									.create("SUBSTRING(" + fonteWorkItems.cType
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING(" + fonteWorkItems.cPk
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkItems.cLocation
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkItems.fkTimepoint
											+ ",0,4000)"),
							fonteWorkItems.cInitialestimate, StringTemplate
									.create("SUBSTRING("
											+ fonteWorkItems.fkUriTimepoint
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteWorkItems.cPreviousstatus
											+ ",0,4000)")));

			Iterator<Tuple> i = cfworkitems.iterator();
			Object[] el = null;

			while (i.hasNext()) {

				el = ((Tuple) i.next()).toArray();

				// //
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
				//
				//
				//
				//
				//
				//
				//
				//
				//

				new SQLInsertClause(OracleConnection, dialect, stgWorkItems)
						.columns(stgWorkItems.fkModule, stgWorkItems.cIsLocal,
								stgWorkItems.cPriority,
								stgWorkItems.cAutosuspect,
								stgWorkItems.cResolution,
								stgWorkItems.cCreated,
								stgWorkItems.cOutlinenumber,
								stgWorkItems.fkProject, stgWorkItems.cDeleted,
								stgWorkItems.cPlannedend,
								stgWorkItems.cUpdated, stgWorkItems.fkAuthor,
								stgWorkItems.cUri, stgWorkItems.fkUriModule,
								stgWorkItems.cTimespent, stgWorkItems.cStatus,
								stgWorkItems.cSeverity,
								stgWorkItems.cResolvedon,
								stgWorkItems.fkUriProject, stgWorkItems.cTitle,
								stgWorkItems.cId, stgWorkItems.cRev,
								stgWorkItems.cPlannedstart,
								stgWorkItems.fkUriAuthor,
								stgWorkItems.cDuedate,
								stgWorkItems.cRemainingestimate,
								stgWorkItems.cType, stgWorkItems.cPk,
								stgWorkItems.cLocation,
								stgWorkItems.fkTimepoint,
								stgWorkItems.cInitialestimate,
								stgWorkItems.fkUriTimepoint,
								stgWorkItems.cPreviousstatus,
								stgWorkItems.dataCaricamento)
						.values(el[0],
								el[1],
								el[2],
								el[3],
								el[4],
								el[5].toString(),
								el[6],
								el[7],
								el[8],
								el[9],
								el[10],
								el[11],
								el[12],
								el[13],
								el[14],
								el[15],
								el[16],
								el[17],
								el[18],
								el[19],
								el[20],
								el[21],
								el[22],
								el[23],
								el[24],
								el[25],
								el[26],
								el[27],
								el[28],
								el[29],
								el[30],
								el[31],
								el[32],
								StringTemplate
										.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))"))
						.execute();

			}

			OracleConnection.commit();

		} catch (Throwable e) {

		}

	}

	public void testFillSissHistoryWorkItem() throws Exception {

		// long sissminimum = SissHistoryWorkitemDAO.getMinRevision();
		java.util.Date dateInizio = new java.util.Date();

		// SissHistoryWorkitemDAO.fillSissHistoryWorkitem(sissminimum, 2000);
		java.util.Date dateFine = new java.util.Date();

		long diff = dateFine.getTime() - dateInizio.getTime();
		logger.debug("diff: " + diff);
	}
}