package lispa.schedulers.dao.target.elettra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElClassificatori;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElClassificatori;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class ElettraClassificatoriDAO {
	private static Logger logger = Logger
			.getLogger(ElettraClassificatoriDAO.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmElClassificatori qDmalmElClassificatori = QDmalmElClassificatori.qDmalmElClassificatori;

	public static List<DmalmElClassificatori> getAllClassificatori(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmElClassificatori bean = null;
		List<DmalmElClassificatori> classificatori = new ArrayList<DmalmElClassificatori>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_ELETTRACLASSIFICATORI);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				bean = new DmalmElClassificatori();

				bean.setClassificatorePk(rs
						.getInt("DMALM_STG_CLASSIFICATORI_PK"));
				bean.setIdClassificatore(rs
						.getString("ID_CLASSIFICATORE_ORESTE"));
				bean.setTipoClassificatore(rs.getString("TIPO_CLASSIFICATORE"));
				bean.setCodiceClassificatore(rs
						.getString("CODICE_CLASSIFICATORE"));
				bean.setDataCaricamento(rs.getTimestamp("DT_CARICAMENTO"));

				classificatori.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return classificatori;
	}

	public static List<Tuple> getClassificatore(DmalmElClassificatori bean)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> classificatori = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			classificatori = query
					.from(qDmalmElClassificatori)
					.where(qDmalmElClassificatori.dataFineValidita.eq(DateUtils
							.getDtFineValidita9999()))
					.where(qDmalmElClassificatori.idClassificatore.eq(bean
							.getIdClassificatore()))
					.list(qDmalmElClassificatori.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return classificatori;
	}

	public static void insertClassificatore(DmalmElClassificatori bean)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, qDmalmElClassificatori)
					.columns(qDmalmElClassificatori.classificatorePk,
							qDmalmElClassificatori.idClassificatore,
							qDmalmElClassificatori.tipoClassificatore,
							qDmalmElClassificatori.codiceClassificatore,
							qDmalmElClassificatori.dataCaricamento,
							qDmalmElClassificatori.dataInizioValidita,
							qDmalmElClassificatori.dataFineValidita)
					.values(bean.getClassificatorePk(),
							bean.getIdClassificatore(),
							bean.getTipoClassificatore(),
							bean.getCodiceClassificatore(),
							bean.getDataCaricamento(),
							DateUtils.getDtInizioValidita1900(),
							DateUtils.getDtFineValidita9999()).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			Integer classificatorePk) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, qDmalmElClassificatori)
					.where(qDmalmElClassificatori.classificatorePk
							.eq(classificatorePk))
					.set(qDmalmElClassificatori.dataFineValidita,
							DateUtils.addSecondsToTimestamp(dataFineValidita,
									-1)).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertClassificatoreUpdate(DmalmElClassificatori bean)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, qDmalmElClassificatori)
					.columns(qDmalmElClassificatori.classificatorePk,
							qDmalmElClassificatori.idClassificatore,
							qDmalmElClassificatori.tipoClassificatore,
							qDmalmElClassificatori.codiceClassificatore,
							qDmalmElClassificatori.dataCaricamento,
							qDmalmElClassificatori.dataInizioValidita,
							qDmalmElClassificatori.dataFineValidita)
					.values(bean.getClassificatorePk(),
							bean.getIdClassificatore(),
							bean.getTipoClassificatore(),
							bean.getCodiceClassificatore(),
							bean.getDataCaricamento(),
							bean.getDataCaricamento(),
							DateUtils.getDtFineValidita9999()).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateClassificatore(Integer classificatorePk,
			DmalmElClassificatori classificatore) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, qDmalmElClassificatori)
					.where(qDmalmElClassificatori.classificatorePk
							.eq(classificatorePk))
					.set(qDmalmElClassificatori.codiceClassificatore,
							classificatore.getCodiceClassificatore())
					.set(qDmalmElClassificatori.tipoClassificatore,
							classificatore.getTipoClassificatore()).execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
