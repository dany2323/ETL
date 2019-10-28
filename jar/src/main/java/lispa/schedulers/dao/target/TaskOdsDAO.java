package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmTask;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmTaskOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

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
		List <Integer> listPk= new ArrayList<Integer>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmTask task : staging_tasks) {
				if(listPk.contains(task.getDmalmTaskPk()))
					logger.info("Trovata DmalmTaskPk DUPLICATA!!!"+task.getDmalmTaskPk());
				else{
					listPk.add(task.getDmalmTaskPk());
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
									taskODS.dmalmUserFk06, taskODS.uri,
									taskODS.tagAlm, taskODS.tsTagAlm)
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
									task.getDmalmUserFk06(), task.getUri(),
									task.getTagAlm(), task.getTsTagAlm())
							.execute();
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

	public static List<DmalmTask> getAll() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmTask> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(taskODS).orderBy(taskODS.cdTask.asc())
					.orderBy(taskODS.dtModificaTask.asc())
					.list(Projections.bean(DmalmTask.class, taskODS.all()));

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
