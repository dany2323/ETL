package lispa.schedulers.dao.calipso;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.List;
import lispa.schedulers.bean.staging.calipso.DmalmStgCalipsoSchedaServizio;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.QueryUtils;
import oracle.jdbc.OracleCallableStatement;
import org.apache.log4j.Logger;

public class StgCalipsoSchedaServizioDAO {
	private static Logger logger = Logger.getLogger(StgCalipsoSchedaServizioDAO.class);

	public static void deleteDmAlmStagingFromExcel() {

		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			String sql = QueryUtils.getCallProcedure("CALIPSO.DELETE_DM_ALM_STAGING", 0);

			cs = connection.prepareCall(sql);
			cs.execute();
			
			connection.commit();

		} catch (SQLException | DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			cm.closeQuietly(cs);
			cm.closeQuietly(connection);
		}
	}
	
	public static void fillDmAlmStagingFromExcel(List<DmalmStgCalipsoSchedaServizio> listExcelCalipso) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		try {
			logger.debug("START StgCalipsoSchedaServizioDAO.fillDmAlmStagingFromExcel");
			
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
    		
			for (DmalmStgCalipsoSchedaServizio excelCalipso : listExcelCalipso) {
				String sql = QueryUtils.getCallProcedure("CALIPSO.FILL_DM_ALM_STAGING", 1);
				Object [] objRichSupp = excelCalipso.getObject(excelCalipso);
			    Struct structObj = connection.createStruct("DM_ALMSTGCALIPSOSCHEDASERVTYPE", objRichSupp);
			    ocs = (OracleCallableStatement)connection.prepareCall(sql);
				ocs.setObject(1, structObj);
				ocs.execute();
				connection.commit();
				ocs.close();
			}
			
			logger.debug("STOP StgCalipsoSchedaServizioDAO.fillDmAlmStagingFromExcel");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException();
		} finally {
			cm.closeQuietly(connection);
		}
	}
}
