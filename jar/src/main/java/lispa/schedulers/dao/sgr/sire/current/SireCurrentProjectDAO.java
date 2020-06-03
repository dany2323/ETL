package lispa.schedulers.dao.sgr.sire.current;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class SireCurrentProjectDAO {

	public static long fillSireCurrentProject() throws DAOException {

		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection H2Connection = null;
		long n_righe_inserite = 0;

		try {

			cm = ConnectionManager.getInstance();
			H2Connection = cm.getConnectionSIRECurrent();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(false);

			lispa.schedulers.queryimplementation.staging.sgr.sire.current.SireCurrentProject   stgProject  = lispa.schedulers.queryimplementation.staging.sgr.sire.current.SireCurrentProject.project;
			lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentProject fonteProjects  = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentProject.project;

			SQLTemplates dialect = new HSQLDBTemplates()
			{ {
				setPrintSchema(true);
			}};
			SQLQuery query = new SQLQuery(H2Connection, dialect); 


			List<Tuple> project = query.from(fonteProjects)
					//					.where(fonteProjects.cLocation.like("default:/Sviluppo/%"))
					.where((fonteProjects.cLocation.length().subtract(fonteProjects.cLocation.toString().replaceAll("/", "").length())).goe(4))
					.where(fonteProjects.cLocation.notLike("default:/GRACO%"))
					.list(new QTuple(
							StringTemplate.create("SUBSTRING("+fonteProjects.cTrackerprefix+",0,4000)") ,
							StringTemplate.create("CASEWHEN ("+fonteProjects.cIsLocal+"= 'true', 1,0 )") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.cPk+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.fkUriLead+",0,4000)") ,
							StringTemplate.create("CASEWHEN ("+fonteProjects.cDeleted+"= 'true', 1,0 )") ,
							fonteProjects.cFinish,
							StringTemplate.create("SUBSTRING("+fonteProjects.cUri+",0,4000)") ,
							fonteProjects.cStart,
							StringTemplate.create("SUBSTRING("+fonteProjects.fkUriProjectgroup+",0,4000)") ,
							StringTemplate.create("CASEWHEN ("+fonteProjects.cActive+"= 'true', 1,0 )") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.cLocation+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.fkProjectgroup+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.fkLead+",0,4000)") ,
							StringTemplate.create("FORMATDATETIME("+fonteProjects.cLockworkrecordsdate+", {ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("SUBSTRING("+fonteProjects.cName+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.cId+",0,4000)") 
							)
							);


			Iterator<Tuple> i = project.iterator();
			Object[] el= null;

			while (i.hasNext()) {

				el= ((Tuple)i.next()).toArray();

				new SQLInsertClause(OracleConnection, dialect, stgProject)
				.columns(
						stgProject.cTrackerprefix,
						stgProject.cIsLocal,
						stgProject.cPk,
						stgProject.fkUriLead,
						stgProject.cDeleted,
						stgProject.cFinish,
						stgProject.cUri,
						stgProject.cStart,
						stgProject.fkUriProjectgroup,
						stgProject.cActive,
						stgProject.cLocation,
						stgProject.fkProjectgroup,
						stgProject.fkLead,
						stgProject.cLockworkrecordsdate,
						stgProject.cName,
						stgProject.cId
						)
						.values(
								el[0] , 
								el[1] , 
								el[2] , 
								StringUtils.getMaskedValue((String)el[3]) , 
								el[4] , 
								el[5] , 
								el[6] , 
								el[7] , 
								el[8] , 
								el[9] , 
								el[10] ,
								el[11] ,
								StringUtils.getMaskedValue((String)el[12]) ,
								el[13] ,
								el[14] ,
								el[15]
								)
								.execute();

				n_righe_inserite++;

			}

			OracleConnection.commit();

		} catch (SQLException | NoSuchAlgorithmException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			n_righe_inserite=0;
		} finally {
			cm.closeQuietly(OracleConnection);
			cm.closeQuietly(H2Connection);
		}

		return n_righe_inserite;
	}
}