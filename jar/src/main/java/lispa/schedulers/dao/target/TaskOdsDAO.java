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

import lispa.schedulers.bean.target.fatti.DmalmTask;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmTaskOds;

public class TaskOdsDAO {

	private static Logger logger = Logger.getLogger(TaskOdsDAO.class);

	private static QDmalmTaskOds taskODS = QDmalmTaskOds.dmalmTaskOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, taskODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmTask> staging_tasks,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmTask task : staging_tasks) {

				new SQLInsertClause(connection, dialect, taskODS)
						.columns(taskODS.cdTask, taskODS.codice,
								taskODS.dataChiusuraTask,
								taskODS.dataFineEffettiva,
								taskODS.dataFinePianificata,
								taskODS.dataInizioEffettivo,
								taskODS.dataInizioPianificato,
								taskODS.descrizioneTask,
								taskODS.dmalmAreaTematicaFk05,
								taskODS.dmalmProjectFk02,
								taskODS.dmalmStatoWorkitemFk03,
								taskODS.dmalmStrutturaOrgFk01,
								taskODS.dmalmTaskPk, taskODS.dmalmTempoFk04,
								taskODS.dsAutoreTask,
								taskODS.dtCambioStatoTask,
								taskODS.dtCaricamentoTask,
								taskODS.dtCreazioneTask,
								taskODS.dtModificaTask,
								taskODS.dtRisoluzioneTask,
								taskODS.dtScadenzaTask,
								taskODS.dtStoricizzazione,
								taskODS.idAutoreTask, taskODS.idRepository,
								taskODS.initialCost,
								taskODS.motivoRisoluzioneTask,
								taskODS.numeroLinea, taskODS.numeroTestata,
								taskODS.priorityTask, taskODS.rankStatoTask,
								taskODS.severityTask, taskODS.taskType,
								taskODS.tempoTotaleRisoluzione,
								taskODS.titoloTask, taskODS.stgPk,
								taskODS.dmalmUserFk06, taskODS.uri)
						.values(task.getCdTask(), task.getCodice(),
								task.getDataChiusuraTask(),
								task.getDataFineEffettiva(),
								task.getDataFinePianificata(),
								task.getDataInizioEffettivo(),
								task.getDataInizioPianificato(),
								task.getDescrizioneTask(),
								task.getDmalmAreaTematicaFk05(),
								task.getDmalmProjectFk02(),
								task.getDmalmStatoWorkitemFk03(),
								task.getDmalmStrutturaOrgFk01(),
								task.getDmalmTaskPk(),
								task.getDmalmTempoFk04(),
								task.getDsAutoreTask(),
								task.getDtCambioStatoTask(),
								task.getDtCaricamentoTask(),
								task.getDtCreazioneTask(),
								task.getDtModificaTask(),
								task.getDtRisoluzioneTask(),
								task.getDtScadenzaTask(),
								task.getDtModificaTask(),
								task.getIdAutoreTask(), task.getIdRepository(),
								task.getInitialCost(),
								task.getMotivoRisoluzioneTask(),
								task.getNumeroLinea(), task.getNumeroTestata(),
								task.getPriorityTask(), new Double(1),
								task.getSeverityTask(), task.getTaskType(),
								task.getTempoTotaleRisoluzione(),
								task.getTitoloTask(), task.getStgPk(),
								task.getDmalmUserFk06(), task.getUri())
						.execute();

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

	public static List<DmalmTask> getAll() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmTask> resultListEl = new LinkedList<DmalmTask>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(taskODS).orderBy(taskODS.cdTask.asc())
					.orderBy(taskODS.dtModificaTask.asc())
					.list(taskODS.all());

			for (Tuple result : list) {
				DmalmTask resultEl = new DmalmTask();
                resultEl.setDmalmUserFk06(result.get(taskODS.dmalmUserFk06));
				resultEl.setCdTask(result.get(taskODS.cdTask));
                resultEl.setCodice(result.get(taskODS.codice));
                resultEl.setDataChiusuraTask(result.get(taskODS.dataChiusuraTask));
                resultEl.setDataFineEffettiva(result.get(taskODS.dataFineEffettiva));
                resultEl.setDataFinePianificata(result.get(taskODS.dataFinePianificata));
                resultEl.setDataInizioEffettivo(result.get(taskODS.dataInizioEffettivo));
                resultEl.setDataInizioPianificato(result.get(taskODS.dataInizioPianificato));
                resultEl.setDescrizioneTask(result.get(taskODS.descrizioneTask));
                resultEl.setDmalmAreaTematicaFk05(result.get(taskODS.dmalmAreaTematicaFk05));
                resultEl.setDmalmProjectFk02(result.get(taskODS.dmalmProjectFk02));
                resultEl.setDmalmStatoWorkitemFk03(result.get(taskODS.dmalmStatoWorkitemFk03));
                resultEl.setDmalmStrutturaOrgFk01(result.get(taskODS.dmalmStrutturaOrgFk01));
                resultEl.setDmalmTempoFk04(result.get(taskODS.dmalmTempoFk04));
                resultEl.setDsAutoreTask(result.get(taskODS.dsAutoreTask));
                resultEl.setDtCambioStatoTask(result.get(taskODS.dtCambioStatoTask));
                resultEl.setDtCaricamentoTask(result.get(taskODS.dtCaricamentoTask));
                resultEl.setDtCreazioneTask(result.get(taskODS.dtCreazioneTask));
                resultEl.setDtModificaTask(result.get(taskODS.dtModificaTask));
                resultEl.setDtRisoluzioneTask(result.get(taskODS.dtRisoluzioneTask));
                resultEl.setDtScadenzaTask(result.get(taskODS.dtScadenzaTask));
                resultEl.setDtStoricizzazione(result.get(taskODS.dtStoricizzazione));
                resultEl.setIdAutoreTask(result.get(taskODS.idAutoreTask));
                resultEl.setIdRepository(result.get(taskODS.idRepository));
                resultEl.setInitialCost(result.get(taskODS.initialCost));
                resultEl.setMotivoRisoluzioneTask(result.get(taskODS.motivoRisoluzioneTask));
                resultEl.setNumeroLinea(result.get(taskODS.numeroLinea));
                resultEl.setNumeroTestata(result.get(taskODS.numeroTestata));
                resultEl.setPriorityTask(result.get(taskODS.priorityTask));				
                resultEl.setRankStatoTask(result.get(taskODS.rankStatoTask));
                resultEl.setRankStatoTaskMese(result.get(taskODS.rankStatoTaskMese));
                resultEl.setSeverityTask(result.get(taskODS.severityTask));
                resultEl.setStgPk(result.get(taskODS.stgPk));
                resultEl.setUri(result.get(taskODS.uri));
                resultEl.setTaskType(result.get(taskODS.taskType));
                resultEl.setTempoTotaleRisoluzione(result.get(taskODS.tempoTotaleRisoluzione));
                resultEl.setTitoloTask(result.get(taskODS.titoloTask));
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
