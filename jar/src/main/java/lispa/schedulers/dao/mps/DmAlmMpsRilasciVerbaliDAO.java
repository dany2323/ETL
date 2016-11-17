package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsRilasciVerbali;
import lispa.schedulers.dao.target.PeiOdsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.mps.QDmalmMpsRilasciVerbali;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class DmAlmMpsRilasciVerbaliDAO {

	private static Logger logger = Logger.getLogger(PeiOdsDAO.class);

	private static QDmalmMpsRilasciVerbali mpsRilasciVerbali = QDmalmMpsRilasciVerbali.dmalmMpsRilasciVerbali;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, mpsRilasciVerbali)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static List<DmalmMpsRilasciVerbali> getAllMpsRilasciVerbalie(
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmMpsRilasciVerbali bean = null;
		List<DmalmMpsRilasciVerbali> mpsRilasciVerbalie = new ArrayList<DmalmMpsRilasciVerbali>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_STG_RILASCI_VERBALIES);
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmMpsRilasciVerbali();
				bean.setCodVerbale(rs.getString("CODVERBALE"));
				bean.setIdVerbaleValidazione(rs.getInt("IDVERBALEVALIDAZIONE"));
				bean.setIdRilascio(rs.getInt("IDRILASCIO"));
				bean.setImporto(rs.getInt("IMPORTO"));

				mpsRilasciVerbalie.add(bean);
			}

			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mpsRilasciVerbalie;
	}

	public static List<Tuple> getMpsRilasciVerbali(
			DmalmMpsRilasciVerbali mpsRilasciVerbalie) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> mpsRilasciVerbalies = new LinkedList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			mpsRilasciVerbalies = query.from(mpsRilasciVerbali).list(
					mpsRilasciVerbali.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mpsRilasciVerbalies;
	}

	public static void insertMpsRilasciVerbalie(Timestamp dataEsecuzione,
			DmalmMpsRilasciVerbali mpsRilasciVerbalie) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					mpsRilasciVerbali);
			insert.populate(mpsRilasciVerbalie).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
