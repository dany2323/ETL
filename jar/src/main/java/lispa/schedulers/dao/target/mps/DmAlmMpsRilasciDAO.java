package lispa.schedulers.dao.target.mps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsRilasci;
import lispa.schedulers.dao.target.PeiOdsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.mps.QDmalmMpsRilasci;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class DmAlmMpsRilasciDAO {
	private static Logger logger = Logger.getLogger(PeiOdsDAO.class);

	private static QDmalmMpsRilasci mpsRilasci = QDmalmMpsRilasci.dmalmMpsRilasci;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, mpsRilasci).execute();

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

	public static List<DmalmMpsRilasci> getAllMpsRilascie(
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmMpsRilasci bean = null;
		List<DmalmMpsRilasci> mpsRilascie = new ArrayList<DmalmMpsRilasci>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_STG_RILASCIES);
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmMpsRilasci();
				
				bean.setIdRilascio(rs.getInt("IDRILASCIO"));
				bean.setIdContratto(rs.getInt("IDCONTRATTO"));
				bean.setCodRilascio(rs.getString("CODRILASCIO"));
				bean.setTipoRilascio(rs.getString("TIPORILASCIO"));
				bean.setSottoTipoRilascio(rs.getString("SOTTOTIPORILASCIO"));
				bean.setTitoloRilascio(rs.getString("TITOLORILASCIO"));
				bean.setDesAttivita(rs.getString("DESATTIVITA"));
				bean.setDataInizio(rs.getTimestamp("DATA_INIZIO"));
				bean.setDataRilascio(rs.getTimestamp("DATA_RILASCIO"));
				bean.setDataValidazione(rs.getTimestamp("DATA_VALIDAZIONE"));
				bean.setStatoFatturazione(rs.getString("STATO_FATTURAZIONE"));
				bean.setStatoFinanziamento(rs.getString("STATO_FINANZIAMENTO"));
				bean.setImportoRilascio(rs.getInt("IMPORTO_RILASCIO"));
				bean.setTotaleSpalmato(rs.getInt("TOTALE_SPALMATO"));
				bean.setTotaleVerbalizzato(rs.getInt("TOTALE_VERBALIZZATO"));
				bean.setTotaleRichiesta(rs.getInt("TOTALE_RICHIESTA"));
				bean.setTotaleFatturato(rs.getInt("TOTALE_FATTURATO"));
				bean.setTotaleFatturabile(rs.getInt("TOTALE_FATTURABILE"));
				bean.setDataRilascioEffettivo(rs.getTimestamp("DATA_RILASCIO_EFFETTIVO"));
				bean.setVarianteMigliorativa(rs.getString("VARIANTE_MIGLIORATIVA"));
				bean.setStatoVerbalizzazione(rs.getString("STATO_VERBALIZZAZIONE"));
				
				mpsRilascie.add(bean);
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

		return mpsRilascie;
	}

	public static List<Tuple> getMpsRilasci(DmalmMpsRilasci mpsRilascie)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> mpsRilascies = new LinkedList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			mpsRilascies = query.from(mpsRilasci)
					.list(mpsRilasci.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
			
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mpsRilascies;
	}

	public static void insertMpsRilascie(Timestamp dataEsecuzione,
			DmalmMpsRilasci mpsRilascie) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					mpsRilasci);
			insert.populate(mpsRilascie).execute();

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
