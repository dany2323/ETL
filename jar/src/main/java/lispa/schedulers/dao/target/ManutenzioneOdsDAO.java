package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmManutenzione;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmManutenzioneOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class ManutenzioneOdsDAO {

	private static Logger logger = Logger.getLogger(DocumentoOdsDAO.class);

	private static QDmalmManutenzioneOds manutenzioneODS = QDmalmManutenzioneOds.dmalmManutenzioneOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, manutenzioneODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmManutenzione> staging_manutenzioni,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmManutenzione manutenzione : staging_manutenzioni) {

				new SQLInsertClause(connection, dialect, manutenzioneODS)
						.columns(manutenzioneODS.assigneesManutenzione,
								manutenzioneODS.cdManutenzione,
								manutenzioneODS.codice,
								manutenzioneODS.dataDispEff,
								manutenzioneODS.dataDisponibilita,
								manutenzioneODS.dataInizio,
								manutenzioneODS.dataInizioEff,
								manutenzioneODS.dataRilascioInEs,
								manutenzioneODS.descrizioneManutenzione,
								manutenzioneODS.dmalmAreaTematicaFk05,
								manutenzioneODS.dmalmManutenzionePk,
								manutenzioneODS.dmalmProjectFk02,
								manutenzioneODS.dmalmStatoWorkitemFk03,
								manutenzioneODS.dmalmStrutturaOrgFk01,
								manutenzioneODS.dmalmTempoFk04,
								manutenzioneODS.dsAutoreManutenzione,
								manutenzioneODS.dtCambioStatoManutenzione,
								manutenzioneODS.dtCaricamentoManutenzione,
								manutenzioneODS.dtCreazioneManutenzione,
								manutenzioneODS.dtModificaManutenzione,
								manutenzioneODS.dtRisoluzioneManutenzione,
								manutenzioneODS.dtScadenzaManutenzione,
								manutenzioneODS.dtStoricizzazione,
								manutenzioneODS.fornitura,
								manutenzioneODS.idAutoreManutenzione,
								manutenzioneODS.idRepository,
								manutenzioneODS.motivoRisoluzioneManutenzion,
								manutenzioneODS.numeroLinea,
								manutenzioneODS.numeroTestata,
								manutenzioneODS.priorityManutenzione,
								manutenzioneODS.rankStatoManutenzione,
								manutenzioneODS.severityManutenzione,
								manutenzioneODS.tempoTotaleRisoluzione,
								manutenzioneODS.titoloManutenzione,
								manutenzioneODS.stgPk,
								manutenzioneODS.dmalmUserFk06,
								manutenzioneODS.uri,
								manutenzioneODS.tagAlm, manutenzioneODS.tsTagAlm)
						.values(manutenzione.getAssigneesManutenzione(),
								manutenzione.getCdManutenzione(),
								manutenzione.getCodice(),
								manutenzione.getDataDispEff(),
								manutenzione.getDataDisponibilita(),
								manutenzione.getDataInizio(),
								manutenzione.getDataInizioEff(),
								manutenzione.getDataRilascioInEs(),
								manutenzione.getDescrizioneManutenzione(),
								manutenzione.getDmalmAreaTematicaFk05(),
								manutenzione.getDmalmManutenzionePk(),
								manutenzione.getDmalmProjectFk02(),
								manutenzione.getDmalmStatoWorkitemFk03(),
								manutenzione.getDmalmStrutturaOrgFk01(),
								manutenzione.getDmalmTempoFk04(),
								manutenzione.getDsAutoreManutenzione(),
								manutenzione.getDtCambioStatoManutenzione(),
								manutenzione.getDtCaricamentoManutenzione(),
								manutenzione.getDtCreazioneManutenzione(),
								manutenzione.getDtModificaManutenzione(),
								manutenzione.getDtRisoluzioneManutenzione(),
								manutenzione.getDtScadenzaManutenzione(),
								DataEsecuzione.getInstance()
										.getDataEsecuzione(),
								manutenzione.getFornitura(),
								manutenzione.getIdAutoreManutenzione(),
								manutenzione.getIdRepository(),
								manutenzione.getMotivoRisoluzioneManutenzion(),
								manutenzione.getNumeroLinea(),
								manutenzione.getNumeroTestata(),
								manutenzione.getPriorityManutenzione(),
								new Double(1),
								manutenzione.getSeverityManutenzione(),
								manutenzione.getTempoTotaleRisoluzione(),
								manutenzione.getTitoloManutenzione(),
								manutenzione.getStgPk(),
								manutenzione.getDmalmUserFk06(),
								manutenzione.getUri(),
								manutenzione.getTagAlm(), manutenzione.getTsTagAlm()
								).execute();

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

	public static List<DmalmManutenzione> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmManutenzione> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(manutenzioneODS)
					.orderBy(manutenzioneODS.cdManutenzione.asc())
					.orderBy(manutenzioneODS.dtModificaManutenzione.asc())
					.list(Projections.bean(DmalmManutenzione.class,
							manutenzioneODS.all()));

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
