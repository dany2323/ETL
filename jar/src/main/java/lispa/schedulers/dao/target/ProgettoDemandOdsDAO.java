package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgettoDemand;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProgettoDemandOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ProgettoDemandOdsDAO {

	private static Logger logger = Logger.getLogger(ProgettoDemandOdsDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProgettoDemandOds progettoDemandODS = QDmalmProgettoDemandOds.dmalmProgettoDemandOds;

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, progettoDemandODS)
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

	public static void insert(List<DmalmProgettoDemand> staging_progettoDemand,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmProgettoDemand progettoDemand : staging_progettoDemand) {
				new SQLInsertClause(connection, dialect, progettoDemandODS)
						.columns(progettoDemandODS.cdProgettoDemand,
								progettoDemandODS.descrizioneProgettoDemand,
								progettoDemandODS.dmalmProgettoDemandPk,
								progettoDemandODS.dmalmProjectFk02,
								progettoDemandODS.dmalmStatoWorkitemFk03,
								progettoDemandODS.dmalmStrutturaOrgFk01,
								progettoDemandODS.dmalmTempoFk04,
								progettoDemandODS.dsAutoreProgettoDemand,
								progettoDemandODS.dtCambioStatoProgettoDem,
								progettoDemandODS.dtCaricamentoProgettoDemand,
								progettoDemandODS.dtCreazioneProgettoDemand,
								progettoDemandODS.dtModificaProgettoDemand,
								progettoDemandODS.dtRisoluzioneProgettoDemand,
								progettoDemandODS.dtScadenzaProgettoDemand,
								progettoDemandODS.dtStoricizzazione,
								progettoDemandODS.idAutoreProgettoDemand,
								progettoDemandODS.idRepository,
								progettoDemandODS.motivoRisoluzioneProgDem,
								progettoDemandODS.rankStatoProgettoDemand,
								progettoDemandODS.tempoTotaleRisoluzione,
								progettoDemandODS.titoloProgettoDemand,
								progettoDemandODS.dtChiusuraProgettoDemand,
								progettoDemandODS.codice,
								progettoDemandODS.cfOwnerDemand,
								progettoDemandODS.cfDtEnunciazione,
								progettoDemandODS.cfDtDisponibilitaEff,
								progettoDemandODS.cfDtDisponibilita,
								progettoDemandODS.cfDtValidazione,
								progettoDemandODS.cfReferenteSviluppo,
								progettoDemandODS.cfReferenteEsercizio,
								progettoDemandODS.aoid,
								progettoDemandODS.stgPk,
								progettoDemandODS.fornitura,
								progettoDemandODS.dmalmUserFk06,
								progettoDemandODS.uri,
								progettoDemandODS.codObiettivoAziendale,
								progettoDemandODS.codObiettivoUtente,
								progettoDemandODS.cfClassificazione)
						.values(progettoDemand.getCdProgettoDemand(),
								progettoDemand.getDescrizioneProgettoDemand(),
								progettoDemand.getDmalmProgettoDemandPk(),
								progettoDemand.getDmalmProjectFk02(),
								progettoDemand.getDmalmStatoWorkitemFk03(),
								progettoDemand.getDmalmStrutturaOrgFk01(),
								progettoDemand.getDmalmTempoFk04(),
								progettoDemand.getDsAutoreProgettoDemand(),
								progettoDemand.getDtCambioStatoProgettoDem(),
								progettoDemand.getDtCaricamentoProgettoDemand(),
								progettoDemand.getDtCreazioneProgettoDemand(),
								progettoDemand.getDtModificaProgettoDemand(),
								progettoDemand.getDtRisoluzioneProgettoDemand(),
								progettoDemand.getDtScadenzaProgettoDemand(),
								progettoDemand.getDtModificaProgettoDemand(),
								progettoDemand.getIdAutoreProgettoDemand(),
								progettoDemand.getIdRepository(),
								progettoDemand.getMotivoRisoluzioneProgDem(),
								new Double(1),
								progettoDemand.getTempoTotaleRisoluzione(),
								progettoDemand.getTitoloProgettoDemand(),
								progettoDemand.getDtChiusuraProgettoDemand(),
								progettoDemand.getCodice(),
								progettoDemand.getCfOwnerDemand(),
								progettoDemand.getCfDtEnunciazione(),
								progettoDemand.getCfDtDisponibilitaEff(),
								progettoDemand.getCfDtDisponibilita(),
								progettoDemand.getCfDtValidazione(),
								progettoDemand.getCfReferenteSviluppo(),
								progettoDemand.getCfReferenteEsercizio(),
								progettoDemand.getAoid(),
								progettoDemand.getStgPk(),
								progettoDemand.getFornitura(),
								progettoDemand.getDmalmUserFk06(),
								progettoDemand.getUri(),
								progettoDemand.getCodObiettivoAziendale(),
								progettoDemand.getCodObiettivoUtente(),
								progettoDemand.getCfClassificazione())
						.execute();

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

	public static List<DmalmProgettoDemand> getAll() throws SQLException,
			DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmProgettoDemand> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(progettoDemandODS)
					.orderBy(progettoDemandODS.cdProgettoDemand.asc())
					.orderBy(progettoDemandODS.dtModificaProgettoDemand.asc())
					.list(Projections.bean(DmalmProgettoDemand.class,
							progettoDemandODS.all()));

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
