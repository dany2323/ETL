package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.Total;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QTotal;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

public class TotalDao {
	private static Logger logger = Logger.getLogger(TotalDao.class);
	private static SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
	private static QTotal qTotal = QTotal.total;

	/**
	 * Ritorna la Lista di tutti gli ID di WI che hanno subito un cambiamento di
	 * C_TYPE
	 * 
	 * @param idRepository
	 *            la repository di interesse
	 * @return lista degli ID di interesse
	 */

	public static List<Total> changedWorkitemList(String idRepository) {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Total total = null;
		List<Total> totals = new ArrayList<Total>();

		String sql;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			if (idRepository.equalsIgnoreCase(DmAlmConstants.REPOSITORY_SIRE)) {
				sql = QueryManager.getInstance().getQuery(
						DmAlmConstants.GET_CHANGED_WI_SIRE);
			} else {
				sql = QueryManager.getInstance().getQuery(
						DmAlmConstants.GET_CHANGED_WI_SISS);
			}

			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				total = new Total();
				total.setCodice(rs.getString("CODICE"));
				total.setIdRepository(rs.getString("ID_REPOSITORY"));
				totals.add(total);
			}

			connection.commit();

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (cm != null) {
				cm.closeConnection(connection);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return totals;
	}

	/**
	 * Ritorna la Storia di un singolo WI partendo dal C_ID
	 * 
	 * @param changedwi
	 * @return
	 * @throws DAOException
	 */
	public static List<Tuple> getHistorySingleChangedWI(String changedwi,
			String idRepository) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> cwihistory = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);
			cwihistory = query.from(qTotal)
					.orderBy(qTotal.dtStoricizzazione.desc())
					.where(qTotal.codice.eq(changedwi))
					.where(qTotal.idRepository.eq(idRepository))
					.list(qTotal.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return cwihistory;

	}

	public static List<Tuple> filterChangedWiHisory(List<Tuple> cwiHistory) {
		boolean changed = false;
		List<Tuple> filteredcwi = new ArrayList<Tuple>();
		String c_type = cwiHistory.get(0).get(qTotal.type);
		for (Tuple t : cwiHistory) {
			if (!c_type.equals(t.get(qTotal.type)))
				changed = true;
			if (changed)
				filteredcwi.add(t);
		}

		return filteredcwi;
	}
}
