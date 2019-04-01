package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoDem;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProgettoSviluppoDOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ProgettoSviluppoDemandOdsDAO {

	private static Logger logger = Logger
			.getLogger(ProgettoSviluppoSviluppoOdsDAO.class);

	private static QDmalmProgettoSviluppoDOds progettoSvilDODS = QDmalmProgettoSviluppoDOds.dmalmProgettoSviluppoDOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, progettoSvilDODS)
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

	public static void insert(
			List<DmalmProgettoSviluppoDem> staging_progettoSviluppoSvil,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmProgettoSviluppoDem progettoSviluppo : staging_progettoSviluppoSvil) {
				new SQLInsertClause(connection, dialect, progettoSvilDODS)
						.columns(progettoSvilDODS.cdProgSvilD,
								progettoSvilDODS.cfCodice,
								progettoSvilDODS.cfDataDispEffettiva,
								progettoSvilDODS.cfDataDispPianificata,
								progettoSvilDODS.cfDataInizio,
								progettoSvilDODS.cfDataInizioEff,
								progettoSvilDODS.cfFornitura,
								progettoSvilDODS.descrizioneProgSvilD,
								progettoSvilDODS.dmalmProgSvilDPk,
								progettoSvilDODS.dmalmProjectFk02,
								progettoSvilDODS.dmalmStatoWorkitemFk03,
								progettoSvilDODS.dmalmStrutturaOrgFk01,
								progettoSvilDODS.dmalmTempoFk04,
								progettoSvilDODS.dsAutoreProgSvilD,
								progettoSvilDODS.dtCambioStatoProgSvilD,
								progettoSvilDODS.dtCaricamentoProgSvilD,
								progettoSvilDODS.dtCreazioneProgSvilD,
								progettoSvilDODS.dtModificaProgSvilD,
								progettoSvilDODS.dtPassaggioEsercizio,
								progettoSvilDODS.dtRisoluzioneProgSvilD,
								progettoSvilDODS.dtScadenzaProgSvilD,
								progettoSvilDODS.dtStoricizzazione,
								progettoSvilDODS.idAutoreProgSvilD,
								progettoSvilDODS.idRepository,
								progettoSvilDODS.motivoRisoluzioneProgSvilD,
								progettoSvilDODS.priorityProgettoSvilDemand,
								progettoSvilDODS.rankStatoProgSvilD,
								progettoSvilDODS.severityProgettoSvilDemand,
								progettoSvilDODS.tempoTotaleRisoluzione,
								progettoSvilDODS.titoloProgSvilD,
								progettoSvilDODS.stgPk,
								progettoSvilDODS.dmalmUserFk06,
								progettoSvilDODS.uri,
								progettoSvilDODS.tagAlm, progettoSvilDODS.tsTagAlm)
						.values(progettoSviluppo.getCdProgSvilD(),
								progettoSviluppo.getCfCodice(),
								progettoSviluppo.getCfDataDispEffettiva(),
								progettoSviluppo.getCfDataDispPianificata(),
								progettoSviluppo.getCfDataInizio(),
								progettoSviluppo.getCfDataInizioEff(),
								progettoSviluppo.getCfFornitura(),
								progettoSviluppo.getDescrizioneProgSvilD(),
								progettoSviluppo.getDmalmProgSvilDPk(),
								progettoSviluppo.getDmalmProjectFk02(),
								progettoSviluppo.getDmalmStatoWorkitemFk03(),
								progettoSviluppo.getDmalmStrutturaOrgFk01(),
								progettoSviluppo.getDmalmTempoFk04(),
								progettoSviluppo.getDsAutoreProgSvilD(),
								progettoSviluppo.getDtCambioStatoProgSvilD(),
								progettoSviluppo.getDtCaricamentoProgSvilD(),
								progettoSviluppo.getDtCreazioneProgSvilD(),
								progettoSviluppo.getDtModificaProgSvilD(),
								progettoSviluppo.getDtPassaggioEsercizio(),
								progettoSviluppo.getDtRisoluzioneProgSvilD(),
								progettoSviluppo.getDtScadenzaProgSvilD(),
								progettoSviluppo.getDtModificaProgSvilD(),
								progettoSviluppo.getIdAutoreProgSvilD(),
								progettoSviluppo.getIdRepository(),
								progettoSviluppo
										.getMotivoRisoluzioneProgSvilD(),
								progettoSviluppo
										.getPriorityProgettoSvilDemand(),
								new Double(1),
								progettoSviluppo
										.getSeverityProgettoSvilDemand(),
								progettoSviluppo.getTempoTotaleRisoluzione(),
								progettoSviluppo.getTitoloProgSvilD(),
								progettoSviluppo.getStgPk(),
								progettoSviluppo.getDmalmUserFk06(),
								progettoSviluppo.getUri(),
								progettoSviluppo.getTagAlm(), progettoSviluppo.getTsTagAlm()).execute();
				connection.commit();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmProgettoSviluppoDem> getAll() throws SQLException,
			DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmProgettoSviluppoDem> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(progettoSvilDODS)
					.orderBy(progettoSvilDODS.cdProgSvilD.asc())
					.orderBy(progettoSvilDODS.dtModificaProgSvilD.asc())
					.list(Projections.bean(DmalmProgettoSviluppoDem.class,
							progettoSvilDODS.all()));
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
