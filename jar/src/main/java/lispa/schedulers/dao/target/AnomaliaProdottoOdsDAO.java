package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmAnomaliaProdottoOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class AnomaliaProdottoOdsDAO {

	private static Logger logger = Logger
			.getLogger(AnomaliaProdottoOdsDAO.class);

	private static QDmalmAnomaliaProdottoOds anomaliaODS = QDmalmAnomaliaProdottoOds.dmalmAnomaliaProdottoOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void insert(List<DmalmAnomaliaProdotto> anomalie,
			Timestamp dataesecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmAnomaliaProdotto anomalia : anomalie) {

				new SQLInsertClause(connection, dialect, anomaliaODS)
						.columns(anomaliaODS.dtCambioStatoAnomalia,
								anomaliaODS.dtCaricamentoRecordAnomalia,
								anomaliaODS.dtChiusuraAnomalia,
								anomaliaODS.dtCreazioneAnomalia,
								anomaliaODS.dtModificaRecordAnomalia,
								anomaliaODS.dtRisoluzioneAnomalia,
								anomaliaODS.dsAnomalia,
								anomaliaODS.dmalmAnomaliaProdottoPk,
								anomaliaODS.nrGiorniFestivi,
								anomaliaODS.cdAnomalia,
								anomaliaODS.idRepository,
								anomaliaODS.motivoRisoluzioneAnomalia,
								anomaliaODS.dsAutoreAnomalia,
								anomaliaODS.numeroLineaAnomalia,
								anomaliaODS.numeroTestataAnomalia,
								anomaliaODS.severity,
								anomaliaODS.dmalmStatoWorkitemFk03,
								anomaliaODS.tempoTotRisoluzioneAnomalia,
								anomaliaODS.idAutoreAnomalia,
								anomaliaODS.effortAnalisi,
								anomaliaODS.effortCostoSviluppo,
								anomaliaODS.idAnomaliaAssistenza,
								anomaliaODS.ticketSiebelAnomaliaAss,
								anomaliaODS.dtAperturaTicket,
								anomaliaODS.dtChiusuraTicket,
								anomaliaODS.dmalmTempoFk04,
								anomaliaODS.dmalmAreaTematicaFk05,
								anomaliaODS.dmalmStrutturaOrgFk01,
								anomaliaODS.dmalmProjectFk02,
								anomaliaODS.stgPk, anomaliaODS.dmalmUserFk06,
								anomaliaODS.uri, anomaliaODS.contestazione,
								anomaliaODS.noteContestazione,
								anomaliaODS.dtDisponibilita)
						.values(anomalia.getDtCambioStatoAnomalia(),
								anomalia.getDtCaricamentoRecordAnomalia(),
								anomalia.getDtChiusuraAnomalia(),
								anomalia.getDtCreazioneAnomalia(),
								anomalia.getDtModificaRecordAnomalia(),
								anomalia.getDtRisoluzioneAnomalia(),
								// anomalia.getDescrizioneAnomalia(), mail
								// Scravaglieri 04/08/2015 17:51
								anomalia.getDsAnomalia(),
								anomalia.getDmalmAnomaliaProdottoPk(),
								anomalia.getNrGiorniFestivi(),
								anomalia.getCdAnomalia(),
								anomalia.getIdRepository(),
								anomalia.getMotivoRisoluzioneAnomalia(),
								anomalia.getDsAutoreAnomalia(),
								anomalia.getNumeroLineaAnomalia(),
								anomalia.getNumeroTestataAnomalia(),
								anomalia.getSeverity(),
								anomalia.getDmalmStatoWorkitemFk03(),
								anomalia.getTempoTotRisoluzioneAnomalia(),
								anomalia.getIdAutoreAnomalia(),
								anomalia.getEffortAnalisi(),
								anomalia.getEffortCostoSviluppo(),
								anomalia.getIdAnomaliaAssistenza(),
								anomalia.getTicketSiebelAnomaliaAss(),
								anomalia.getDtAperturaTicket(),
								anomalia.getDtChiusuraTicket(),
								anomalia.getDmalmTempoFk04(),
								anomalia.getDmalmAreaTematicaFk05(),
								anomalia.getDmalmStrutturaOrgFk01(),
								anomalia.getDmalmProjectFk02(),
								anomalia.getStgPk(),
								anomalia.getDmalmUserFk06(), anomalia.getUri(),
								anomalia.getContestazione(),
								anomalia.getNoteContestazione(),
								anomalia.getDtDisponibilita()).execute();

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

	public static void delete() throws DAOException, SQLException {

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

	public static List<DmalmAnomaliaProdotto> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmAnomaliaProdotto> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(anomaliaODS)
					.orderBy(anomaliaODS.cdAnomalia.asc())
					.orderBy(anomaliaODS.dtModificaRecordAnomalia.asc())
					.list(Projections.bean(DmalmAnomaliaProdotto.class,
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