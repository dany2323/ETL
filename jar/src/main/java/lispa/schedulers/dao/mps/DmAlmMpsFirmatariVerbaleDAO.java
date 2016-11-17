package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsFirmatariVerbale;
import lispa.schedulers.dao.target.PeiOdsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.mps.QDmalmMpsFirmatariVerbale;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class DmAlmMpsFirmatariVerbaleDAO {

	private static Logger logger = Logger.getLogger(PeiOdsDAO.class);

	private static QDmalmMpsFirmatariVerbale mpsFirmatariVerbale = QDmalmMpsFirmatariVerbale.dmalmMpsFirmatariVerbale;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, mpsFirmatariVerbale)
					.execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static List<DmalmMpsFirmatariVerbale> getAllMpsFirmatariVerbalee(
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmMpsFirmatariVerbale bean = null;
		List<DmalmMpsFirmatariVerbale> mpsFirmatariVerbalee = new ArrayList<DmalmMpsFirmatariVerbale>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_STG_FIRMATARI_VERBALIES);
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmMpsFirmatariVerbale();
				bean.setIdVerbaleValidazione(rs.getInt("IDVERBALEVALIDAZIONE"));
				bean.setIdUtente(rs.getInt("IDUTENTE"));
				bean.setFirmatarioVerbale(rs.getString("FIRMATARIO_VERBALE"));
				bean.setTipologiaResponsabile(rs
						.getString("TIPOLOGIA_RESPONSABILE"));
				bean.setOrdineFirma(rs.getInt("ORDINE_FIRMA"));
				bean.setFirmato(rs.getString("FIRMATO"));
				bean.setDataFirma(rs.getTimestamp("DATA_FIRMA"));
				bean.setIdEnte(rs.getInt("IDENTE"));
				bean.setEnte(rs.getString("ENTE"));

				mpsFirmatariVerbalee.add(bean);
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

		return mpsFirmatariVerbalee;
	}

	public static List<Tuple> getMpsFirmatariVerbale(
			DmalmMpsFirmatariVerbale mpsFirmatariVerbalee) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> mpsFirmatariVerbalees = new LinkedList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			mpsFirmatariVerbalees = query.from(mpsFirmatariVerbale).list(
					mpsFirmatariVerbale.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mpsFirmatariVerbalees;
	}

	public static void insertMpsFirmatariVerbalee(Timestamp dataEsecuzione,
			DmalmMpsFirmatariVerbale mpsFirmatariVerbalee) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					mpsFirmatariVerbale);
			insert.populate(mpsFirmatariVerbalee).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
