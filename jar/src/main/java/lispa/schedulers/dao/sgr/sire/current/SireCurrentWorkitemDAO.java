package lispa.schedulers.dao.sgr.sire.current;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentWorkitem;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.exception.DAOException; 
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import com.mysema.query.Tuple; 
import com.mysema.query.sql.HSQLDBTemplates; 
import com.mysema.query.sql.SQLQuery; 
import com.mysema.query.sql.SQLTemplates; 
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class SireCurrentWorkitemDAO {

	private static SireCurrentWorkitem sireCurrentWorkitem = SireCurrentWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.current.SireCurrentWorkitem stg_CurrentWorkitems = lispa.schedulers.queryimplementation.staging.sgr.sire.current.SireCurrentWorkitem.workitem; 
	
	public static long fillSireCurrentWorkitems() throws DAOException { 
		ConnectionManager cm = null; 
		Connection H2connSire = null; 
		Connection connection = null; 
		long n_righe_inserite = 0;
		try { 
			cm = ConnectionManager.getInstance(); 
			H2connSire = cm.getConnectionSIRECurrent(); 
			connection = cm.getConnectionOracle();
			SQLTemplates  dialect = new HSQLDBTemplates(){ { 
				setPrintSchema(true); 
			}}; 
			SQLQuery query = new SQLQuery(H2connSire, dialect); 

			List<Tuple> workitems = query.distinct() 
					.from(sireCurrentWorkitem) 
					.where(sireCurrentWorkitem.cId.isNotNull())
					.list(new QTuple(
							StringTemplate.create("CASEWHEN ("+sireCurrentWorkitem.cAutosuspect+"= 'true', 1,0 )") ,
							sireCurrentWorkitem.cCreated,
							StringTemplate.create("CASEWHEN ("+sireCurrentWorkitem.cDeleted+"= 'true', 1,0 )") ,
							sireCurrentWorkitem.cDuedate,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cId+",0,4000)") ,
							sireCurrentWorkitem.cInitialestimate,
							StringTemplate.create("CASEWHEN ("+sireCurrentWorkitem.cIsLocal+"= 'true', 1,0 )") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cLocation+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cOutlinenumber+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cPk+",0,4000)") ,
							sireCurrentWorkitem.cPlannedend,
							sireCurrentWorkitem.cPlannedstart,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cPreviousstatus+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cPriority+",0,4000)") ,
							sireCurrentWorkitem.cRemainingestimate,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cResolution+",0,4000)") ,
							sireCurrentWorkitem.cResolvedon,
							sireCurrentWorkitem.cRev,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cSeverity+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cStatus+",0,4000)") ,
							sireCurrentWorkitem.cTimespent,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cTitle+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cType+",0,4000)") ,
							sireCurrentWorkitem.cUpdated,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.cUri+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.fkAuthor+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.fkModule+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.fkProject+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.fkTimepoint+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.fkUriAuthor+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.fkUriModule+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.fkUriProject+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+sireCurrentWorkitem.fkUriTimepoint+",0,4000)")
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
				
				n_righe_inserite++;
			} 
			 
			connection.commit(); 
		} catch (SQLException | NoSuchAlgorithmException e) { 
			ErrorManager.getInstance().exceptionOccurred(true, e);
			n_righe_inserite=0;
		} finally { 
			cm.closeConnection(connection);
			cm.closeConnection(H2connSire);
		} 
		
		return n_righe_inserite;
	} 
}