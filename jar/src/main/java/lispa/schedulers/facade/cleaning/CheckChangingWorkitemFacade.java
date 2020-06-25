package lispa.schedulers.facade.cleaning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.Total;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.TotalDao;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.QueryManager;

public class CheckChangingWorkitemFacade {

	private static Logger logger = Logger
			.getLogger(CheckChangingWorkitemFacade.class);
	
	
	public static void setChangedFieldByRepo(List<Total> totals, String idRepo)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			logger.info("START CheckChangingWorkitemFacade.execute()");
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			List<Tuple> cwihistory = null;
			List<Tuple> filteredcwhistory = null;
			connection.setAutoCommit(true);
			ExecutorService executor = Executors.newFixedThreadPool(32);
			for (Total t : totals) {
				cwihistory = TotalDao.getHistorySingleChangedWI(t.getCodice(),
						idRepo);
				filteredcwhistory = TotalDao.filterChangedWiHisory(cwihistory);
				//Collections.reverse(filteredcwhistory);
				WorkItemChangingThread wct = new WorkItemChangingThread(filteredcwhistory, connection, t.getCodice());
				executor.execute(wct);
			}
			executor.shutdown();
			logger.info("STOP CheckChangingWorkitemFacade.execute()");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void execute() throws DAOException {
		List<Total> totalsire = TotalDao
				.changedWorkitemList(DmAlmConstants.REPOSITORY_SIRE);
		List<Total> totalsiss = TotalDao
				.changedWorkitemList(DmAlmConstants.REPOSITORY_SISS);

		setChangedFieldByRepo(totalsire, DmAlmConstants.REPOSITORY_SIRE);
		setChangedFieldByRepo(totalsiss, DmAlmConstants.REPOSITORY_SISS);
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		String sql;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			sql = QueryManager.getInstance().getQuery(
					DmAlmConstants.DMALM_CHANGED_WORKITEM);
			ps = connection.prepareStatement(sql);
			ps.executeUpdate();
			connection.commit();
			logger.info("FINITO");
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

	}

//	private static String findType(String codice) throws DAOException {
//
//		String ret = null;
//		ConnectionManager cm = null;
//		Connection connection = null;
//		List<Tuple> t = new ArrayList<Tuple>();
//
//		try {
//			cm = ConnectionManager.getInstance();
//			connection = cm.getConnectionOracle();
//
//			SQLQuery query = new SQLQuery(connection, dialect);
//
//			t = query.from(qTotal).where(qTotal.codice.eq(codice))
//					.orderBy(qTotal.dtStoricizzazione.desc())
//					.list(qTotal.all());
//			
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			throw new DAOException(e);
//
//		} finally {
//			if (cm != null) {
//				cm.closeConnection(connection);
//			}
//		}
//
//		if (t.size() > 0) {
//			ret = t.get(0).get(qTotal.type);
//		}
//
//		return ret;
//	}
}
