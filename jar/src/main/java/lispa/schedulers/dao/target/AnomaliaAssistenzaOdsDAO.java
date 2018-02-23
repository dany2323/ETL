package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

import lispa.schedulers.bean.target.fatti.DmalmAnomaliaAssistenza;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmAnomaliaAssistenzaOds;

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

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmAnomaliaAssistenza anomalia : staging_anomalie_assistenza) {
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
								anomaliaODS.dmalmUserFk06, anomaliaODS.uri)
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
								anomalia.getDmalmUserFk06(), anomalia.getUri())
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

	public static List<DmalmAnomaliaAssistenza> getAll() throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmAnomaliaAssistenza> resultListEl = new LinkedList<DmalmAnomaliaAssistenza>();


		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(anomaliaODS)
					.orderBy(anomaliaODS.cdAnomaliaAss.asc())
					.orderBy(anomaliaODS.dtModificaAnomaliaAss.asc())
					.list(anomaliaODS.all());
			
			for (Tuple result : list) {
				DmalmAnomaliaAssistenza resultEl = new DmalmAnomaliaAssistenza();
				resultEl.setDmalmAnomaliaAssPk(result.get(anomaliaODS.dmalmAnomaliaAssPk));
				resultEl.setAoid(result.get(anomaliaODS.aoid));
				resultEl.setCa(result.get(anomaliaODS.ca));
				resultEl.setCdAnomaliaAss(result.get(anomaliaODS.cdAnomaliaAss));
				resultEl.setDmalmUserFk06(result.get(anomaliaODS.dmalmUserFk06));
				resultEl.setCs(result.get(anomaliaODS.cs));
				resultEl.setDescrizioneAnomaliaAss(result.get(anomaliaODS.descrizioneAnomaliaAss));
				resultEl.setDmalmProjectFk02(result.get(anomaliaODS.dmalmProjectFk02));
				resultEl.setDmalmStatoWorkitemFk03(result.get(anomaliaODS.dmalmStatoWorkitemFk03));
				resultEl.setDmalmStrutturaOrgFk01(result.get(anomaliaODS.dmalmStrutturaOrgFk01));
				resultEl.setDmalmTempoFk04(result.get(anomaliaODS.dmalmTempoFk04));
				resultEl.setDsAutoreAnomaliaAss(result.get(anomaliaODS.dsAutoreAnomaliaAss));
				resultEl.setDtCambioStatoAnomaliaAss(result.get(anomaliaODS.dtCambioStatoAnomaliaAss));
				resultEl.setDtCaricamentoAnomaliaAss(result.get(anomaliaODS.dtCaricamentoAnomaliaAss));
				resultEl.setDtCreazioneAnomaliaAss(result.get(anomaliaODS.dtCreazioneAnomaliaAss));
				resultEl.setDtModificaAnomaliaAss(result.get(anomaliaODS.dtModificaAnomaliaAss));
				resultEl.setDtRisoluzioneAnomaliaAss(result.get(anomaliaODS.dtRisoluzioneAnomaliaAss));
				resultEl.setDtScadenzaAnomaliaAss(result.get(anomaliaODS.dtScadenzaAnomaliaAss));
				resultEl.setDtStoricizzazione(result.get(anomaliaODS.dtStoricizzazione));
				resultEl.setFrequenza(result.get(anomaliaODS.frequenza));
				resultEl.setIdAutoreAnomaliaAss(result.get(anomaliaODS.idAutoreAnomaliaAss));
				resultEl.setIdRepository(result.get(anomaliaODS.idRepository));
				resultEl.setMotivoRisoluzioneAnomaliaAs(result.get(anomaliaODS.motivoRisoluzioneAnomaliaAs));
				resultEl.setPlatform(result.get(anomaliaODS.platform));
				resultEl.setProdCod(result.get(anomaliaODS.prodCod));
				resultEl.setRankStatoAnomaliaAss(result.get(anomaliaODS.rankStatoAnomaliaAss));
				resultEl.setRankStatoAnomaliaAssMese(result.get(anomaliaODS.rankStatoAnomaliaAssMese));
				resultEl.setSegnalazioni(result.get(anomaliaODS.segnalazioni));
				resultEl.setSo(result.get(anomaliaODS.so));
				resultEl.setStChiuso(result.get(anomaliaODS.stChiuso));
				resultEl.setStgPk(result.get(anomaliaODS.stgPk));
				resultEl.setUri(result.get(anomaliaODS.uri));
				resultEl.setTempoTotaleRisoluzione(result.get(anomaliaODS.tempoTotaleRisoluzione));
				resultEl.setTicketid(result.get(anomaliaODS.ticketid));
				resultEl.setTitoloAnomaliaAss(result.get(anomaliaODS.titoloAnomaliaAss));
				resultEl.setSeverity(result.get(anomaliaODS.severity));
				resultEl.setPriority(result.get(anomaliaODS.priority));
				resultListEl.add(resultEl);
			}

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultListEl;

	}

}
