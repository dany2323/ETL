package lispa.schedulers.dao.sgr.siss.current;

import java.sql.Connection; 
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentWorkitem;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.exception.DAOException; 
import lispa.schedulers.manager.ConnectionManager;
import com.mysema.query.Tuple; 
import com.mysema.query.sql.HSQLDBTemplates; 
import com.mysema.query.sql.SQLQuery; 
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class SissCurrentWorkitemDAO {

	private static Logger logger = Logger.getLogger(SissCurrentWorkitemDAO.class);
	private static SissCurrentWorkitem sissCurrentWorkitem = SissCurrentWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.current.SissCurrentWorkitem stg_CurrentWorkitems = lispa.schedulers.queryimplementation.staging.sgr.siss.current.SissCurrentWorkitem.workitem; 
 
	public static boolean fillSissCurrentWorkitems() throws SQLException, DAOException { 
		ConnectionManager cm = null; 
		Connection H2connSiss = null; 
		Connection connection = null; 
		boolean flag = true;
		try { 
			cm = ConnectionManager.getInstance(); 
			H2connSiss = cm.getConnectionSISSCurrent(); 
			connection = cm.getConnectionOracle();
			SQLTemplates  dialect = new HSQLDBTemplates(){ { 
				setPrintSchema(true); 
			}}; 
			SQLQuery query = new SQLQuery(H2connSiss, dialect); 

			List<Tuple> workitems = query.distinct() 
					.from(sissCurrentWorkitem) 
					.where(sissCurrentWorkitem.cId.isNotNull())
					.list(new QTuple(
							StringTemplate.create("CASEWHEN ("+sissCurrentWorkitem.cAutosuspect+"= 'true', 1,0 )") ,
							sissCurrentWorkitem.cCreated,
							StringTemplate.create("CASEWHEN ("+sissCurrentWorkitem.cDeleted+"= 'true', 1,0 )") ,
							sissCurrentWorkitem.cDuedate,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cId+",0,4000)") ,
							sissCurrentWorkitem.cInitialestimate,
							StringTemplate.create("CASEWHEN ("+sissCurrentWorkitem.cIsLocal+"= 'true', 1,0 )") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cLocation+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cOutlinenumber+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cPk+",0,4000)") ,
							sissCurrentWorkitem.cPlannedend,
							sissCurrentWorkitem.cPlannedstart,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cPreviousstatus+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cPriority+",0,4000)") ,
							sissCurrentWorkitem.cRemainingestimate,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cResolution+",0,4000)") ,
							sissCurrentWorkitem.cResolvedon,
							sissCurrentWorkitem.cRev,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cSeverity+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cStatus+",0,4000)") ,
							sissCurrentWorkitem.cTimespent,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cTitle+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cType+",0,4000)") ,
							sissCurrentWorkitem.cUpdated,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.cUri+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.fkAuthor+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.fkModule+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.fkProject+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.fkTimepoint+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.fkUriAuthor+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.fkUriModule+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.fkUriProject+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sissCurrentWorkitem.fkUriTimepoint+",0,4000)")
							)
			);
			Iterator<Tuple> i = workitems.iterator();
			Object[] el= null;
			while (i.hasNext()) {

				el= ((Tuple)i.next()).toArray();
				new SQLInsertClause(connection, dialect, stg_CurrentWorkitems).columns(stg_CurrentWorkitems.cAutosuspect,
						stg_CurrentWorkitems.cCreated,
						stg_CurrentWorkitems.cDeleted,
						stg_CurrentWorkitems.cDuedate,
						stg_CurrentWorkitems.cId,
						stg_CurrentWorkitems.cInitialestimate,
						stg_CurrentWorkitems.cIsLocal,
						stg_CurrentWorkitems.cLocation,
						stg_CurrentWorkitems.cOutlinenumber,
						stg_CurrentWorkitems.cPk,
						stg_CurrentWorkitems.cPlannedend,
						stg_CurrentWorkitems.cPlannedstart,
						stg_CurrentWorkitems.cPreviousstatus,
						stg_CurrentWorkitems.cPriority,
						stg_CurrentWorkitems.cRemainingestimate,
						stg_CurrentWorkitems.cResolution,
						stg_CurrentWorkitems.cResolvedon,
						stg_CurrentWorkitems.cRev,
						stg_CurrentWorkitems.cSeverity,
						stg_CurrentWorkitems.cStatus,
						stg_CurrentWorkitems.cTimespent,
						stg_CurrentWorkitems.cTitle,
						stg_CurrentWorkitems.cType,
						stg_CurrentWorkitems.cUpdated,
						stg_CurrentWorkitems.cUri,
						stg_CurrentWorkitems.fkAuthor,
						stg_CurrentWorkitems.fkModule,
						stg_CurrentWorkitems.fkProject,
						stg_CurrentWorkitems.fkTimepoint,
						stg_CurrentWorkitems.fkUriAuthor,
						stg_CurrentWorkitems.fkUriModule,
						stg_CurrentWorkitems.fkUriProject,
						stg_CurrentWorkitems.fkUriTimepoint) 
					.values(
							el[0],
							el[1],
							el[2],
							el[3],
							el[4],
							el[5],
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
							StringUtils.getMaskedValue((String)el[25]),
							el[26],
							el[27],
							el[28],
							StringUtils.getMaskedValue((String)el[29]),
							el[30],
							el[31],
							el[32]
						).execute(); 
			} 
			connection.commit();
			flag = false;
		} catch (Exception e) { 
			logger.error(e.getMessage(), e);
			throw new DAOException(e); 
		} finally { 
			cm.closeQuietly(connection);
			cm.closeQuietly(H2connSiss);
		} 
		
		return flag;
	}
	
	public static void delete() throws DAOException {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stg_CurrentWorkitems).execute();
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			cm.closeConnection(OracleConnection);
		}
	}
}