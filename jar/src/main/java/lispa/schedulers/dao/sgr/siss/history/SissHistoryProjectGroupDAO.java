package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryProjectgroup;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SissHistoryProjectGroupDAO {
	
	private static Logger logger = Logger.getLogger(SissHistoryProjectGroupDAO.class); 
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProjectgroup fonteProjectGroups = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProjectgroup.projectgroup;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryProjectgroup stg_ProjectGroups = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryProjectgroup.projectgroup;
	
	public static void fillSissHistoryProjectGroup() throws Exception {
		
		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        connH2 = null;
		List<Tuple>       projectgroups = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSISSHistory();
			projectgroups = new ArrayList<Tuple>();
			
			connOracle.setAutoCommit(false);
			
			SQLTemplates dialect = new HSQLDBTemplates()
			{ {
			    setPrintSchema(true);
			}};
			
			SQLQuery query 		 = new SQLQuery(connH2, dialect); 
			
			projectgroups = query.from(fonteProjectGroups)
					.list(
							fonteProjectGroups.all()
							);
			
			for(Tuple row : projectgroups) {
				new SQLInsertClause(connOracle, dialect, stg_ProjectGroups)
				.columns(
						stg_ProjectGroups.cLocation,
						stg_ProjectGroups.cIsLocal,
						stg_ProjectGroups.cPk,
						stg_ProjectGroups.fkUriParent,
						stg_ProjectGroups.fkParent,
						stg_ProjectGroups.cName,
						stg_ProjectGroups.cDeleted,
						stg_ProjectGroups.cRev,
						stg_ProjectGroups.cUri
					).values(								
							row.get(fonteProjectGroups.cLocation),
							row.get(fonteProjectGroups.cIsLocal),
							row.get(fonteProjectGroups.cPk),
							row.get(fonteProjectGroups.fkUriParent),
							row.get(fonteProjectGroups.fkParent),
							row.get(fonteProjectGroups.cName),
							row.get(fonteProjectGroups.cDeleted),
							row.get(fonteProjectGroups.cRev),
							row.get(fonteProjectGroups.cUri)
					).execute();
			}
			connOracle.commit();
		} catch(Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw new DAOException(e);
		} finally {
			if(cm != null) cm.closeConnection(connH2);
			if(cm != null) cm.closeConnection(connOracle);
		}
	}

	public static long getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		try{
			
			QSissHistoryProjectgroup stgProjectGroups = QSissHistoryProjectgroup.sissHistoryProjectgroup;
			cm 	   = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			SQLTemplates dialect 				 = new HSQLDBTemplates();
			SQLQuery query 						 = new SQLQuery(oracle, dialect); 

			max = query.from(stgProjectGroups).list(stgProjectGroups.cRev.max());

			if(max == null || max.size() == 0 || max.get(0) == null) {
				return 0;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new DAOException(e);
		} finally {
			if(cm != null) cm.closeConnection(oracle);
		}

		return max.get(0).longValue();
	} 
}