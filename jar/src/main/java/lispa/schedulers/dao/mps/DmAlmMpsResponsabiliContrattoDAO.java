package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsResponsabiliContratto;
import lispa.schedulers.dao.target.PeiOdsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.mps.QDmalmMpsResponsabiliContratto;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class DmAlmMpsResponsabiliContrattoDAO {
	private static Logger logger = Logger.getLogger(PeiOdsDAO.class);

	private static QDmalmMpsResponsabiliContratto mpsResponsabiliContratto = QDmalmMpsResponsabiliContratto.dmalmMpsResponsabiliContratto;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, mpsResponsabiliContratto)
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

	public static List<DmalmMpsResponsabiliContratto> getAllMpsResponsabiliContrattoe(
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmMpsResponsabiliContratto bean = null;
		List<DmalmMpsResponsabiliContratto> mpsResponsabiliContrattoe = new ArrayList<DmalmMpsResponsabiliContratto>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager
					.getInstance()
					.getQuery(
							DmAlmConfigReaderProperties.SQL_STG_RESPONSABILI_CONTRATTOES);
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmMpsResponsabiliContratto();
				bean.setIdContratto(rs.getInt("IDCONTRATTO"));
				bean.setIdUtente(rs.getInt("IDUTENTE"));
				bean.setResponsabile(rs.getString("RESPONSABILE"));
				bean.setDesTipologiaResponsabile(rs
						.getString("DESTIPOLOGIARESPONSABILE"));
				bean.setFirmatario(rs.getString("FIRMATARIO"));
				bean.setOrdineFirma(rs.getInt("ORDINE_FIRMA"));
				bean.setFirmato(rs.getString("FIRMATO"));
				bean.setDataFirma(rs.getTimestamp("DATA_FIRMA"));
				bean.setIdEnte(rs.getInt("IDENTE"));
				bean.setEnte(rs.getString("ENTE"));

				mpsResponsabiliContrattoe.add(bean);
			}

			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mpsResponsabiliContrattoe;
	}

	public static List<Tuple> getMpsResponsabiliContratto(
			DmalmMpsResponsabiliContratto mpsResponsabiliContrattoe)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> mpsResponsabiliContrattoes = new LinkedList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			mpsResponsabiliContrattoes = query.from(mpsResponsabiliContratto)
					.list(mpsResponsabiliContratto.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mpsResponsabiliContrattoes;
	}

	public static void insertMpsResponsabiliContrattoe(
			Timestamp dataEsecuzione,
			DmalmMpsResponsabiliContratto mpsResponsabiliContrattoe)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					mpsResponsabiliContratto);
			
			insert.columns(mpsResponsabiliContratto.idContratto,
					mpsResponsabiliContratto.idUtente,
					mpsResponsabiliContratto.responsabile,
					mpsResponsabiliContratto.desTipologiaResponsabile,
					mpsResponsabiliContratto.firmatario,
					mpsResponsabiliContratto.ordineFirma,
					mpsResponsabiliContratto.firmato,
					mpsResponsabiliContratto.dataFirma,
					mpsResponsabiliContratto.idEnte,
					mpsResponsabiliContratto.ente)
			.values(mpsResponsabiliContrattoe.getIdContratto(),
					mpsResponsabiliContrattoe.getIdUtente(),
					mpsResponsabiliContrattoe.getResponsabile(),
					mpsResponsabiliContrattoe.getDesTipologiaResponsabile(),
					mpsResponsabiliContrattoe.getFirmatario(),
					mpsResponsabiliContrattoe.getOrdineFirma(),
					mpsResponsabiliContrattoe.getFirmato(),
					mpsResponsabiliContrattoe.getDataFirma(),
					mpsResponsabiliContrattoe.getIdEnte(),
					mpsResponsabiliContrattoe.getEnte()).execute();

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
