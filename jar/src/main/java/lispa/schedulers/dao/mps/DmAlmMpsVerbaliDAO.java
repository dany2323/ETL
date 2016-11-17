package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsVerbali;
import lispa.schedulers.dao.target.PeiOdsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.mps.QDmalmMpsVerbali;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class DmAlmMpsVerbaliDAO {
	private static Logger logger = Logger.getLogger(PeiOdsDAO.class);

	private static QDmalmMpsVerbali mpsVerbali = QDmalmMpsVerbali.dmalmMpsVerbali;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, mpsVerbali).execute();

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

	public static List<DmalmMpsVerbali> getAllMpsVerbalie(
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmMpsVerbali bean = null;
		List<DmalmMpsVerbali> mpsVerbalie = new ArrayList<DmalmMpsVerbali>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_STG_VERBALIES);
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmMpsVerbali();
				bean.setCodVerbale(rs.getString("CODVERBALE"));
				bean.setDataVerbale(rs.getTimestamp("DATA_VERBALE"));
				bean.setDataFirma(rs.getTimestamp("DATA_FIRMA"));
				bean.setNote(rs.getString("NOTE"));
				bean.setConforme(rs.getString("CONFORME"));
				bean.setTipoVerbale(rs.getString("TIPO_VERBALE"));
				bean.setStatoVerbale(rs.getString("STATO_VERBALE"));
				bean.setTotaleVerbale(rs.getInt("TOTALE_VERBALE"));
				bean.setFatturatoVerbale(rs.getInt("FATTURATO_VERBALE"));
				bean.setProssimoFirmatario(rs.getString("PROSSIMO_FIRMATARIO"));
				bean.setFirmaDigitale(rs.getString("FIRMA_DIGITALE"));
				bean.setIdVerbaleValidazione(rs.getInt("IDVERBALEVALIDAZIONE"));

				mpsVerbalie.add(bean);
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

		return mpsVerbalie;
	}

	public static List<Tuple> getMpsVerbali(DmalmMpsVerbali mpsVerbalie)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> mpsVerbalies = new LinkedList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			mpsVerbalies = query.from(mpsVerbali).list(mpsVerbali.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mpsVerbalies;
	}

	public static void insertMpsVerbalie(Timestamp dataEsecuzione,
			DmalmMpsVerbali mpsVerbalie) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					mpsVerbali);
			insert.populate(mpsVerbalie).execute();

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
