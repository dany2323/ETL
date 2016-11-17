package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsAttivita;
import lispa.schedulers.dao.target.PeiOdsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.mps.QDmalmMpsAttivita;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class DmAlmMpsAttivitaDAO {

	private static Logger logger = Logger.getLogger(PeiOdsDAO.class);

	private static QDmalmMpsAttivita mpsAttivita = QDmalmMpsAttivita.dmalmMpsAttivita;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, mpsAttivita).execute();

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

	public static List<DmalmMpsAttivita> getAllMpsAttivitae(
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmMpsAttivita bean = null;
		List<DmalmMpsAttivita> mpsAttivitae = new ArrayList<DmalmMpsAttivita>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_STG_ATTIVITAES);
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmMpsAttivita();
				bean.setIdAttivitaPadre(rs.getInt("IDATTIVITAPADRE"));
				bean.setIdAttivita(rs.getInt("IDATTIVITA"));
				bean.setIdContratto(rs.getInt("IDCONTRATTO"));
				bean.setCodAttivita(rs.getString("CODATTIVITA"));
				bean.setTitolo(rs.getString("TITOLO"));
				bean.setDesAttivita(rs.getString("DESATTIVITA"));
				bean.setDataInizio(rs.getTimestamp("DATA_INIZIO"));
				bean.setDataFine(rs.getTimestamp("DATA_FINE"));
				bean.setAvanzamento(rs.getInt("AVANZAMENTO"));
				bean.setDataUltimoAvanzamento(rs
						.getTimestamp("DATA_ULTIMO_AVANZAMENTO"));
				bean.setTipoAttivita(rs.getString("TIPO_ATTIVITA"));
				bean.setStato(rs.getString("STATO"));
				bean.setInseritoDa(rs.getInt("INSERITO_DA"));
				bean.setInseritoIl(rs.getTimestamp("INSERITO_IL"));
				bean.setModificatoDa(rs.getInt("MODIFICATO_DA"));
				bean.setModificatoIl(rs.getTimestamp("MODIFICATO_IL"));
				bean.setRecordStatus(rs.getString("RECORDSTATUS"));

				mpsAttivitae.add(bean);
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

		return mpsAttivitae;
	}

	public static List<Tuple> getMpsAttivita(DmalmMpsAttivita mpsAttivitae)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> mpsAttivitaes = new LinkedList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			mpsAttivitaes = query.from(mpsAttivita).list(mpsAttivita.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mpsAttivitaes;
	}

	public static void insertMpsAttivitae(Timestamp dataEsecuzione,
			DmalmMpsAttivita mpsAttivitae) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					mpsAttivita);
			insert.populate(mpsAttivitae).execute();

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
