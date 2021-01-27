package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_CLASSIFICATORI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmClassificatori;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmClassificatori;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class ClassificatoreDAO {

	private static Logger logger = Logger.getLogger(ClassificatoreDAO.class);

	private static QDmalmClassificatori dmalmClassificatori = QDmalmClassificatori.dmalmClassificatori;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static List<DmalmClassificatori> getAllClassificatori(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmClassificatori bean = null;
		List<DmalmClassificatori> classificatori = new ArrayList<DmalmClassificatori>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance()
					.getQuery(SQL_CLASSIFICATORI);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {

				bean = new DmalmClassificatori();

				bean.setCodiceClassificatore(rs
						.getString("CODICE_CLASSIFICATORE"));
				bean.setDmalmClassificatoriPk(rs
						.getInt("DMALM_CLASSIFICATORI_PK"));
				bean.setDtCaricamento(dataEsecuzione);
				bean.setIdOreste(rs.getInt("ID_ORESTE"));
				bean.setTipoClassificatore(rs.getString("TIPO_CLASSIFICATORE"));

				classificatori.add(bean);
			}
			
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return classificatori;
	}

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			DmalmClassificatori classificatore) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmClassificatori)
					.where(dmalmClassificatori.codiceClassificatore
							.equalsIgnoreCase(classificatore
									.getCodiceClassificatore()))
					.where(dmalmClassificatori.tipoClassificatore
							.equalsIgnoreCase(classificatore
									.getTipoClassificatore()))
					.where(dmalmClassificatori.dtFineValidita.eq(DateUtils
							.getDtFineValidita9999()))
					.set(dmalmClassificatori.dtFineValidita,
							DateUtils.addDaysToTimestamp(dataFineValidita, -1))
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void resetDataFineValidita9999(
			DmalmClassificatori classificatore) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmClassificatori)
					.where(dmalmClassificatori.codiceClassificatore
							.equalsIgnoreCase(classificatore
									.getCodiceClassificatore()))
					.where(dmalmClassificatori.tipoClassificatore
							.equalsIgnoreCase(classificatore
									.getTipoClassificatore()))
					.where(dmalmClassificatori.dtFineValidita.eq(DateUtils
							.getDtFineValidita9999()))
					.set(dmalmClassificatori.dtFineValidita,
							DateUtils.getDtFineValidita9999()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateClassificatore(DmalmClassificatori classificatore)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmClassificatori)
					.where(dmalmClassificatori.idOreste.eq(classificatore
							.getIdOreste()))
					.where(dmalmClassificatori.dtFineValidita.eq(DateUtils
							.getDtFineValidita9999()))
					.set(dmalmClassificatori.codiceClassificatore,
							classificatore.getCodiceClassificatore())
					.set(dmalmClassificatori.tipoClassificatore,
							classificatore.getTipoClassificatore()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertClassificatore(DmalmClassificatori bean,
			Timestamp dataesecuzione) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			// TARGET
			// DMALM_CLASSIFICATORI_PK NUMBER
			// TIPO_CLASSIFICATORE VARCHAR2
			// CODICE_CLASSIFICATORE VARCHAR2
			// ID_ORESTE NUMBER
			// DT_CARICAMENTO DATE
			// DT_INIZIO_VALIDITA DATE
			// DT_FINE_VALIDITA DATE

			new SQLInsertClause(connection, dialect, dmalmClassificatori)
					.columns(dmalmClassificatori.idOreste,
							dmalmClassificatori.codiceClassificatore,
							dmalmClassificatori.tipoClassificatore,
							dmalmClassificatori.dmalmClassificatoriPrimaryKey,
							dmalmClassificatori.dtCaricamento,
							dmalmClassificatori.dtInizioValidita,
							dmalmClassificatori.dtFineValidita,
							dmalmClassificatori.dtInserimentoRecord)
					.values(bean.getIdOreste(), bean.getCodiceClassificatore(),
							bean.getTipoClassificatore(),
							bean.getDmalmClassificatoriPk(),
							bean.getDtCaricamento(),
							DateUtils.getDtInizioValidita1900(),
							DateUtils.getDtFineValidita9999(), dataesecuzione)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertClassificatoreUpdate(Timestamp dataEsecuzione,
			DmalmClassificatori bean) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, dmalmClassificatori)
					.columns(dmalmClassificatori.idOreste,
							dmalmClassificatori.codiceClassificatore,
							dmalmClassificatori.tipoClassificatore,
							dmalmClassificatori.dmalmClassificatoriPrimaryKey,
							dmalmClassificatori.dtCaricamento,
							dmalmClassificatori.dtInizioValidita,
							dmalmClassificatori.dtFineValidita,
							dmalmClassificatori.dtInserimentoRecord)
					.values(bean.getIdOreste(), bean.getCodiceClassificatore(),
							bean.getTipoClassificatore(),
							bean.getDmalmClassificatoriPk(),
							bean.getDtCaricamento(),
							DateUtils.formatDataEsecuzione(dataEsecuzione),
							DateUtils.getDtFineValidita9999(), dataEsecuzione)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			resetDataFineValidita9999(bean);

			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<Tuple> getClassificatore(DmalmClassificatori bean)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> classificatori = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			classificatori = query
					.from(dmalmClassificatori)
					.where(dmalmClassificatori.dtFineValidita.eq(DateUtils
							.getDtFineValidita9999()))
					.where(dmalmClassificatori.idOreste.eq(bean.getIdOreste()))
					.list(dmalmClassificatori.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return classificatori;
	}

}