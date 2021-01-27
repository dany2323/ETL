package lispa.schedulers.dao.target.mps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsContratti;
import lispa.schedulers.dao.target.PeiOdsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.mps.QDmalmMpsContratti;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class DmAlmMpsContrattiDAO {

	private static Logger logger = Logger.getLogger(PeiOdsDAO.class);

	private static QDmalmMpsContratti mpsContratti = QDmalmMpsContratti.dmalmMpsContratti;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, mpsContratti).execute();

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

	public static List<DmalmMpsContratti> getAllMpsContrattie(
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmMpsContratti bean = null;
		List<DmalmMpsContratti> mpsContrattie = new ArrayList<DmalmMpsContratti>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_STG_CONTRATTIES);
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmMpsContratti();
				bean.setIdContratto(rs.getInt("IDCONTRATTO"));
				bean.setCodContratto(rs.getString("CODCONTRATTO"));
				bean.setTitoloContratto(rs.getString("TITOLOCONTRATTO"));
				bean.setAnnoRiferimento(rs.getInt("ANNO_RIFERIMENTO"));
				bean.setDataInizio(rs.getTimestamp("DATA_INIZIO"));
				bean.setDataFine(rs.getTimestamp("DATA_FINE"));
				bean.setTipo(rs.getString("TIPO"));
				bean.setStato(rs.getString("STATO"));
				bean.setFirmaDigitale(rs.getString("FIRMA_DIGITALE"));
				bean.setVariato(rs.getString("VARIATO"));
				bean.setNumVariazioni(rs.getInt("NUM_VARIAZIONI"));
				bean.setCodDirezione(rs.getString("CODDIREZIONE"));
				bean.setDesDirezione(rs.getString("DESDIREZIONE"));
				bean.setCodUo(rs.getString("CODUO"));
				bean.setDesUo(rs.getString("DESUO"));
				bean.setCodStruttura(rs.getString("CODSTRUTTURA"));
				bean.setDesStruttura(rs.getString("DESSTRUTTURA"));
				bean.setTotaleContratto(rs.getInt("TOTALE_CONTRATTO"));
				bean.setTotaleImpegnato(rs.getInt("TOTALE_IMPEGNATO"));
				bean.setTotaleSpalmato(rs.getInt("TOTALE_SPALMATO"));
				bean.setTotaleVerbalizzato(rs.getInt("TOTALE_VERBALIZZATO"));
				bean.setTotaleRichiesto(rs.getInt("TOTALE_RICHIESTO"));
				bean.setTotaleFatturato(rs.getInt("TOTALE_FATTURATO"));
				bean.setTotaleFatturabile(rs.getInt("TOTALE_FATTURABILE"));
				bean.setProssimoFirmatario(rs.getString("PROSSIMO_FIRMATARIO"));
				bean.setInCorsoIl(rs.getTimestamp("IN_CORSO_IL"));
				bean.setNumeroRilasci(rs.getInt("NUMERO_RILASCI"));
				bean.setNumeroRilasciForfait(rs
						.getInt("NUMERO_RILASCI_FORFAIT"));
				bean.setNumeroRilasciCanone(rs.getInt("NUMERO_RILASCI_CANONE"));
				bean.setNumeroRilasciConsumo(rs
						.getInt("NUMERO_RILASCI_CONSUMO"));
				bean.setNumeroVerbali(rs.getInt("NUMERO_VERBALI"));
				bean.setNumeroVerbaliForfait(rs
						.getInt("NUMERO_VERBALI_FORFAIT"));
				bean.setNumeroVerbaliConsumo(rs
						.getInt("NUMERO_VERBALI_CONSUMO"));
				bean.setDesMotivoVariazione(rs.getString("DESMOTIVOVARIAZIONE"));
				bean.setRepository(rs.getString("REPOSITORY"));
				bean.setPriorita(rs.getString("PRIORITA"));
				bean.setIdSm(rs.getInt("ID_SM"));
				bean.setServiceManager(rs.getString("SERVICE_MANAGER"));

				mpsContrattie.add(bean);
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

		return mpsContrattie;
	}

	public static List<Tuple> getMpsContratti(DmalmMpsContratti mpsContrattie)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> mpsContratties = new LinkedList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			mpsContratties = query.from(mpsContratti).list(mpsContratti.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
			
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mpsContratties;
	}

	public static void insertMpsContrattie(Timestamp dataEsecuzione,
			DmalmMpsContratti mpsContrattie) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					mpsContratti);
			insert.populate(mpsContrattie).execute();

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
