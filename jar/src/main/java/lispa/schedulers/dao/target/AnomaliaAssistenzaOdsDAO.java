package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmAnomaliaAssistenza;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmAnomaliaAssistenzaOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class AnomaliaAssistenzaOdsDAO {

	private static Logger logger = Logger
			.getLogger(AnomaliaAssistenzaOdsDAO.class);
	private static QDmalmAnomaliaAssistenzaOds anomaliaODS = QDmalmAnomaliaAssistenzaOds.dmalmAnomaliaAssistenzaOds;
	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, anomaliaODS).execute();

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
			List<DmalmAnomaliaAssistenza> staging_anomalie_assistenza,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		List <Integer> listPk= new ArrayList<>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmAnomaliaAssistenza anomalia : staging_anomalie_assistenza) {
				if(listPk.contains(anomalia.getDmalmAnomaliaAssPk()))
					logger.info("Trovata DmalmAnomaliaAssPk DUPLICATA!!!"+anomalia.getDmalmAnomaliaAssPk());
				else{
					listPk.add(anomalia.getDmalmAnomaliaAssPk());
					new SQLInsertClause(connection, dialect, anomaliaODS)
							.columns(anomaliaODS.cdAnomaliaAss,
									anomaliaODS.dmalmAnomaliaAssPk,
									anomaliaODS.dmalmProjectFk02,
									anomaliaODS.dmalmStatoWorkitemFk03,
									anomaliaODS.dmalmStrutturaOrgFk01,
									anomaliaODS.dmalmTempoFk04,
									anomaliaODS.descrizioneAnomaliaAss,
									anomaliaODS.dsAutoreAnomaliaAss,
									anomaliaODS.aoid, anomaliaODS.ca,
									anomaliaODS.cs,
									anomaliaODS.dtCambioStatoAnomaliaAss,
									anomaliaODS.dtCaricamentoAnomaliaAss,
									anomaliaODS.dtCreazioneAnomaliaAss,
									anomaliaODS.dtModificaAnomaliaAss,
									anomaliaODS.dtRisoluzioneAnomaliaAss,
									anomaliaODS.dtScadenzaAnomaliaAss,
									anomaliaODS.dtStoricizzazione,
									anomaliaODS.frequenza,
									anomaliaODS.idAutoreAnomaliaAss,
									anomaliaODS.idRepository,
									anomaliaODS.motivoRisoluzioneAnomaliaAs,
									anomaliaODS.platform, anomaliaODS.priority,
									anomaliaODS.prodCod,
									anomaliaODS.rankStatoAnomaliaAss,
									anomaliaODS.segnalazioni, anomaliaODS.severity,
									anomaliaODS.so, anomaliaODS.stChiuso,
									anomaliaODS.stgPk,
									anomaliaODS.tempoTotaleRisoluzione,
									anomaliaODS.ticketid,
									anomaliaODS.titoloAnomaliaAss,
									anomaliaODS.dmalmUserFk06, anomaliaODS.uri,
									anomaliaODS.tagAlm, anomaliaODS.tsTagAlm)
							.values(anomalia.getCdAnomaliaAss(),
									anomalia.getDmalmAnomaliaAssPk(),
									anomalia.getDmalmProjectFk02(),
									anomalia.getDmalmStatoWorkitemFk03(),
									anomalia.getDmalmStrutturaOrgFk01(),
									anomalia.getDmalmTempoFk04(),
									anomalia.getDescrizioneAnomaliaAss(),
									anomalia.getDsAutoreAnomaliaAss(),
									anomalia.getAoid(), anomalia.getCa(),
									anomalia.getCs(),
									anomalia.getDtCambioStatoAnomaliaAss(),
									anomalia.getDtCaricamentoAnomaliaAss(),
									anomalia.getDtCreazioneAnomaliaAss(),
									anomalia.getDtModificaAnomaliaAss(),
									anomalia.getDtRisoluzioneAnomaliaAss(),
									anomalia.getDtScadenzaAnomaliaAss(),
									anomalia.getDtModificaAnomaliaAss(),
									anomalia.getFrequenza(),
									anomalia.getIdAutoreAnomaliaAss(),
									anomalia.getIdRepository(),
									anomalia.getMotivoRisoluzioneAnomaliaAs(),
									anomalia.getPlatform(), anomalia.getPriority(),
									anomalia.getProdCod(), new Double(1),
									anomalia.getSegnalazioni(),
									anomalia.getSeverity(), anomalia.getSo(),
									anomalia.getStChiuso(), anomalia.getStgPk(),
									anomalia.getTempoTotaleRisoluzione(),
									anomalia.getTicketid(),
									anomalia.getTitoloAnomaliaAss(),
									anomalia.getDmalmUserFk06(), anomalia.getUri(),
									anomalia.getTagAlm(), anomalia.getTsTagAlm())
							.execute();
				}
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

	public static List<DmalmAnomaliaAssistenza> getAll() throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmAnomaliaAssistenza> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(anomaliaODS)
					.orderBy(anomaliaODS.cdAnomaliaAss.asc())
					.orderBy(anomaliaODS.dtModificaAnomaliaAss.asc())
					.list(Projections.bean(DmalmAnomaliaAssistenza.class,
							anomaliaODS.all()));

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
