package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmSottoprogramma;
import lispa.schedulers.dao.target.fatti.SottoprogrammaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmSottoprogrammaOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class SottoprogrammaOdsDAO {

	private static Logger logger = Logger.getLogger(SottoprogrammaDAO.class);

	private static QDmalmSottoprogrammaOds sottoprogrammaODS = QDmalmSottoprogrammaOds.dmalmSottoprogrammaOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, sottoprogrammaODS)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmSottoprogramma> staging_sottoprogramma,
			Timestamp dataEsecuzione) throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmSottoprogramma sottoprogramma : staging_sottoprogramma) {
				new SQLInsertClause(connection, dialect, sottoprogrammaODS)
						.columns(sottoprogrammaODS.idRepository,
								sottoprogrammaODS.dmalmSottoprogrammaPk,
								sottoprogrammaODS.cdSottoprogramma,
								sottoprogrammaODS.dtCreazioneSottoprogramma,
								sottoprogrammaODS.dmalmProjectFk02,
								sottoprogrammaODS.dmalmStatoWorkitemFk03,
								sottoprogrammaODS.dtCambioStatoSottoprogramma,
								sottoprogrammaODS.dtScadenzaSottoprogramma,
								sottoprogrammaODS.dtModificaSottoprogramma,
								sottoprogrammaODS.idAutoreSottoprogramma,
								sottoprogrammaODS.dsAutoreSottoprogramma,
								sottoprogrammaODS.titoloSottoprogramma,
								sottoprogrammaODS.motivoRisoluzioneSottoprogr,
								sottoprogrammaODS.dtRisoluzioneSottoprogramma,
								sottoprogrammaODS.descrizioneSottoprogramma,
								sottoprogrammaODS.codice,
								sottoprogrammaODS.numeroLinea,
								sottoprogrammaODS.numeroTestata,
								sottoprogrammaODS.dtCompletamento,
								sottoprogrammaODS.stgPk,
								sottoprogrammaODS.dtCaricamentoSottoprogramma,
								sottoprogrammaODS.dmalmTempoFk04,
								sottoprogrammaODS.dmalmUserFk06,
								sottoprogrammaODS.uri,
								sottoprogrammaODS.severity, sottoprogrammaODS.priority)
						.values(sottoprogramma.getIdRepository(),
								sottoprogramma.getDmalmSottoprogrammaPk(),
								sottoprogramma.getCdSottoprogramma(),
								sottoprogramma.getDtCreazioneSottoprogramma(),
								sottoprogramma.getDmalmProjectFk02(),
								sottoprogramma.getDmalmStatoWorkitemFk03(),
								sottoprogramma.getDtCambioStatoSottoprogramma(),
								sottoprogramma.getDtScadenzaSottoprogramma(),
								sottoprogramma.getDtModificaSottoprogramma(),
								sottoprogramma.getIdAutoreSottoprogramma(),
								sottoprogramma.getDsAutoreSottoprogramma(),
								sottoprogramma.getTitoloSottoprogramma(),
								sottoprogramma.getMotivoRisoluzioneSottoprogr(),
								sottoprogramma.getDtRisoluzioneSottoprogramma(),
								sottoprogramma.getDescrizioneSottoprogramma(),
								sottoprogramma.getCodice(),
								sottoprogramma.getNumeroLinea(),
								sottoprogramma.getNumeroTestata(),
								sottoprogramma.getDtCompletamento(),
								sottoprogramma.getStgPk(),
								sottoprogramma.getDtCaricamentoSottoprogramma(),
								sottoprogramma.getDmalmTempoFk04(),
								sottoprogramma.getDmalmUserFk06(),
								sottoprogramma.getUri(),
								//DM_ALM-320
								sottoprogramma.getSeverity(), sottoprogramma.getPriority()).execute();
			}
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmSottoprogramma> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmSottoprogramma> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(sottoprogrammaODS)
					.orderBy(sottoprogrammaODS.cdSottoprogramma.asc())
					.orderBy(sottoprogrammaODS.dtModificaSottoprogramma.asc())
					.list(Projections.bean(DmalmSottoprogramma.class,
							sottoprogrammaODS.all()));

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return list;

	}
}
