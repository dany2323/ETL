package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

import lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmAnomaliaProdottoOds;

public class AnomaliaProdottoOdsDAO {

	private static Logger logger = Logger
			.getLogger(AnomaliaProdottoOdsDAO.class);

	private static QDmalmAnomaliaProdottoOds anomaliaODS = QDmalmAnomaliaProdottoOds.dmalmAnomaliaProdottoOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void insert(List<DmalmAnomaliaProdotto> anomalie,
			Timestamp dataesecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		List <Integer> listPk= new ArrayList<>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmAnomaliaProdotto anomalia : anomalie) {
				
				if(listPk.contains(anomalia.getDmalmAnomaliaProdottoPk()))
					logger.info("Trovata DmalmAnomaliaProdottoPk DUPLICATA!!!"+anomalia.getDmalmAnomaliaProdottoPk());
				else{
				listPk.add(anomalia.getDmalmAnomaliaProdottoPk());
				
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
								anomaliaODS.dtDisponibilita,
								anomaliaODS.priority)
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
								anomalia.getDtDisponibilita(),
								anomalia.getPriority()).execute();
				}
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

		List<Tuple> list = null;
		List<DmalmAnomaliaProdotto> resultListEl = new LinkedList<DmalmAnomaliaProdotto>();


		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(anomaliaODS)
					.orderBy(anomaliaODS.cdAnomalia.asc())
					.orderBy(anomaliaODS.dtModificaRecordAnomalia.asc())
					.list(anomaliaODS.all());
			
			for (Tuple result : list) {
				DmalmAnomaliaProdotto resultEl = new DmalmAnomaliaProdotto();
				resultEl.setCdAnomalia(result.get(anomaliaODS.cdAnomalia));
				resultEl.setDescrizioneAnomalia(result.get(anomaliaODS.descrizioneAnomalia));
				resultEl.setDmalmAnomaliaProdottoPk(result.get(anomaliaODS.dmalmAnomaliaProdottoPk));
				resultEl.setDmalmUserFk06(result.get(anomaliaODS.dmalmUserFk06));
				resultEl.setDmalmAreaTematicaFk05(result.get(anomaliaODS.dmalmAreaTematicaFk05));
				resultEl.setDmalmProjectFk02(result.get(anomaliaODS.dmalmProjectFk02));
				resultEl.setDmalmStatoWorkitemFk03(result.get(anomaliaODS.dmalmStatoWorkitemFk03));
				resultEl.setDmalmStrutturaOrgFk01(result.get(anomaliaODS.dmalmStrutturaOrgFk01));
				resultEl.setDmalmTempoFk04(result.get(anomaliaODS.dmalmTempoFk04));
				resultEl.setDsAnomalia(result.get(anomaliaODS.dsAnomalia));
				resultEl.setDsAutoreAnomalia(result.get(anomaliaODS.dsAutoreAnomalia));
				resultEl.setDtAperturaTicket(result.get(anomaliaODS.dtAperturaTicket));
				resultEl.setDtCambioStatoAnomalia(result.get(anomaliaODS.dtCambioStatoAnomalia));
				resultEl.setDtCaricamentoRecordAnomalia(result.get(anomaliaODS.dtCaricamentoRecordAnomalia));
				resultEl.setDtChiusuraAnomalia(result.get(anomaliaODS.dtChiusuraAnomalia));
				resultEl.setDtChiusuraTicket(result.get(anomaliaODS.dtChiusuraTicket));
				resultEl.setDtCreazioneAnomalia(result.get(anomaliaODS.dtCreazioneAnomalia));
				resultEl.setDtModificaRecordAnomalia(result.get(anomaliaODS.dtModificaRecordAnomalia));
				resultEl.setDtRisoluzioneAnomalia(result.get(anomaliaODS.dtRisoluzioneAnomalia));
				resultEl.setDtStoricizzazione(result.get(anomaliaODS.dtStoricizzazione));
				resultEl.setEffortAnalisi(result.get(anomaliaODS.effortAnalisi));
				resultEl.setEffortCostoSviluppo(result.get(anomaliaODS.effortCostoSviluppo));
				resultEl.setIdAnomaliaAssistenza(result.get(anomaliaODS.idAnomaliaAssistenza));
				resultEl.setIdAutoreAnomalia(result.get(anomaliaODS.idAutoreAnomalia));
				resultEl.setIdRepository(result.get(anomaliaODS.idRepository));
				resultEl.setMotivoRisoluzioneAnomalia(result.get(anomaliaODS.motivoRisoluzioneAnomalia));
				resultEl.setNrGiorniFestivi(result.get(anomaliaODS.nrGiorniFestivi));
				resultEl.setNumeroLineaAnomalia(result.get(anomaliaODS.numeroLineaAnomalia));
				resultEl.setNumeroTestataAnomalia(result.get(anomaliaODS.numeroTestataAnomalia));
				resultEl.setRankStatoAnomalia(result.get(anomaliaODS.rankStatoAnomalia));
				resultEl.setRankStatoAnomaliaMese(result.get(anomaliaODS.rankStatoAnomaliaMese));
				resultEl.setSeverity(result.get(anomaliaODS.severity));
				resultEl.setStgPk(result.get(anomaliaODS.stgPk));
				resultEl.setUri(result.get(anomaliaODS.uri));
				resultEl.setTempoTotRisoluzioneAnomalia(result.get(anomaliaODS.tempoTotRisoluzioneAnomalia));
				resultEl.setTicketSiebelAnomaliaAss(result.get(anomaliaODS.ticketSiebelAnomaliaAss));
				resultEl.setFlagUltimaSituazione(result.get(anomaliaODS.flagUltimaSituazione));
				resultEl.setContestazione(result.get(anomaliaODS.contestazione));
				resultEl.setNoteContestazione(result.get(anomaliaODS.noteContestazione));
				resultEl.setDtDisponibilita(result.get(anomaliaODS.dtDisponibilita));
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