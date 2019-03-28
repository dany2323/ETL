package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_TASK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmTask;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTask;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class TaskDAO {

	private static Logger logger = Logger.getLogger(TaskDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmTask tsk = QDmalmTask.dmalmTask;

	public static List<DmalmTask> getAllTask(Timestamp dataEsecuzione)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmTask bean = null;
		List<DmalmTask> documenti = new ArrayList<DmalmTask>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.task));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_TASK);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmTask();

				bean.setCdTask(rs.getString("CD_TASK"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setDataChiusuraTask(rs.getTimestamp("DATA_CHIUSURA_TASK"));
				bean.setDataFineEffettiva(rs.getTimestamp("DATA_FINE_EFF"));
				bean.setDataFinePianificata(rs.getTimestamp("DATA_FINE"));
				bean.setDataInizioEffettivo(rs.getTimestamp("DATA_INIZIO_EFF"));
				bean.setDataInizioPianificato(rs.getTimestamp("DATA_INIZIO"));
				bean.setDescrizioneTask(rs.getString("DESCRIZIONE_TASK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDmalmTaskPk(rs.getInt("DMALM_TASK_PK"));
				bean.setDsAutoreTask(rs.getString("NOME_AUTORE_TASK"));
				bean.setDtCaricamentoTask(dataEsecuzione);
				bean.setDtCreazioneTask(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaTask(rs.getTimestamp("DATA_MODIFICA_TASK"));
				bean.setDtRisoluzioneTask(rs
						.getTimestamp("DATA_RISOLUZIONE_TASK"));
				bean.setDtScadenzaTask(rs.getTimestamp("DATA_SCADENZA_TASK"));
				bean.setIdAutoreTask(rs.getString("ID_AUTORE_TASK"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setInitialCost(rs.getShort("INITIAL_COST"));
				bean.setMotivoRisoluzioneTask(rs.getString("MOTIVO_RISOL_TASK"));
				bean.setNumeroLinea(StringUtils.getLinea(rs
						.getString("CODICE_INTERVENTO")));
				bean.setNumeroTestata(StringUtils.getTestata(rs
						.getString("CODICE_INTERVENTO")));
				bean.setPriorityTask(rs.getString("PRIORITY_TASK"));
				bean.setSeverityTask(rs.getString("SEVERITY_TASK"));
				bean.setTaskType(rs.getString("TASK_TYPE"));
				bean.setTempoTotaleRisoluzione(rs
						.getInt("TEMPO_TOTALE_RISOLUZIONE")
						- rs.getInt("GIORNI_FESTIVI"));
				bean.setTitoloTask(rs.getString("TITOLO_TASK"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_TASK_PK"));

				documenti.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return documenti;
	}

	public static List<Tuple> getTask(DmalmTask task) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> tasks = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			tasks = query
					.from(tsk)
					.where(tsk.cdTask.equalsIgnoreCase(task.getCdTask()))
					.where(tsk.idRepository.equalsIgnoreCase(task
							.getIdRepository()))
					.where(tsk.rankStatoTask.eq(new Double(1))).list(tsk.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return tasks;

	}

	public static void insertTask(DmalmTask task) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, tsk)
					.columns(tsk.cdTask, tsk.codice, tsk.dataChiusuraTask,
							tsk.dataFineEffettiva, tsk.dataFinePianificata,
							tsk.dataInizioEffettivo, tsk.dataInizioPianificato,
							tsk.descrizioneTask, tsk.dmalmAreaTematicaFk05,
							tsk.dmalmProjectFk02, tsk.dmalmStatoWorkitemFk03,
							tsk.dmalmStrutturaOrgFk01, tsk.dmalmTaskPk,
							tsk.dmalmTempoFk04, tsk.dsAutoreTask,
							tsk.dtCambioStatoTask, tsk.dtCaricamentoTask,
							tsk.dtCreazioneTask, tsk.dtModificaTask,
							tsk.dtRisoluzioneTask, tsk.dtScadenzaTask,
							tsk.dtStoricizzazione, tsk.idAutoreTask,
							tsk.idRepository, tsk.initialCost,
							tsk.motivoRisoluzioneTask, tsk.numeroLinea,
							tsk.numeroTestata, tsk.priorityTask,
							tsk.rankStatoTask, tsk.severityTask, tsk.taskType,
							tsk.tempoTotaleRisoluzione, tsk.titoloTask,
							tsk.stgPk, tsk.dmalmUserFk06, tsk.uri,
							tsk.dtAnnullamento)
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
							task.getDmalmTaskPk(), task.getDmalmTempoFk04(),
							task.getDsAutoreTask(),
							task.getDtCambioStatoTask(),
							task.getDtCaricamentoTask(),
							task.getDtCreazioneTask(),
							task.getDtModificaTask(),
							task.getDtRisoluzioneTask(),
							task.getDtScadenzaTask(), task.getDtModificaTask(),
							task.getIdAutoreTask(), task.getIdRepository(),
							task.getInitialCost(),
							task.getMotivoRisoluzioneTask(),
							task.getNumeroLinea(), task.getNumeroTestata(),
							task.getPriorityTask(), new Double(1),
							task.getSeverityTask(), task.getTaskType(),
							task.getTempoTotaleRisoluzione(),
							task.getTitoloTask(), task.getStgPk(),
							task.getDmalmUserFk06(), task.getUri(),
							task.getDtAnnullamento()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmTask task, Double double1)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, tsk)
					.where(tsk.cdTask.equalsIgnoreCase(task.getCdTask()))
					.where(tsk.idRepository.equalsIgnoreCase(task
							.getIdRepository()))
					.set(tsk.rankStatoTask, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertTaskUpdate(Timestamp dataEsecuzione, DmalmTask task, boolean pkValue)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, tsk)
					.columns(tsk.cdTask, tsk.codice, tsk.dataChiusuraTask,
							tsk.dataFineEffettiva, tsk.dataFinePianificata,
							tsk.dataInizioEffettivo, tsk.dataInizioPianificato,
							tsk.descrizioneTask, tsk.dmalmAreaTematicaFk05,
							tsk.dmalmProjectFk02, tsk.dmalmStatoWorkitemFk03,
							tsk.dmalmStrutturaOrgFk01, tsk.dmalmTaskPk,
							tsk.dmalmTempoFk04, tsk.dsAutoreTask,
							tsk.dtCambioStatoTask, tsk.dtCaricamentoTask,
							tsk.dtCreazioneTask, tsk.dtModificaTask,
							tsk.dtRisoluzioneTask, tsk.dtScadenzaTask,
							tsk.dtStoricizzazione, tsk.idAutoreTask,
							tsk.idRepository, tsk.initialCost,
							tsk.motivoRisoluzioneTask, tsk.numeroLinea,
							tsk.numeroTestata, tsk.priorityTask,
							tsk.rankStatoTask, tsk.severityTask, tsk.taskType,
							tsk.tempoTotaleRisoluzione, tsk.titoloTask,
							tsk.stgPk, tsk.dmalmUserFk06, tsk.uri,
							tsk.dtAnnullamento, tsk.changed, tsk.annullato)
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
							pkValue == true ? task.getDmalmTaskPk() : StringTemplate
									.create("HISTORY_WORKITEM_SEQ.nextval"), task.getDmalmTempoFk04(),
							task.getDsAutoreTask(),
							task.getDtCambioStatoTask(),
							task.getDtCaricamentoTask(),
							task.getDtCreazioneTask(),
							task.getDtModificaTask(),
							task.getDtRisoluzioneTask(),
							task.getDtScadenzaTask(), pkValue == true ? task.getDtModificaTask() : task.getDtStoricizzazione(),
							task.getIdAutoreTask(), task.getIdRepository(),
							task.getInitialCost(),
							task.getMotivoRisoluzioneTask(),
							task.getNumeroLinea(), task.getNumeroTestata(),
							task.getPriorityTask(),
							pkValue == true ? new Short("1")  : task.getRankStatoTask(),
							task.getSeverityTask(), task.getTaskType(),
							task.getTempoTotaleRisoluzione(),
							task.getTitoloTask(), task.getStgPk(),
							task.getDmalmUserFk06(), task.getUri(),
							task.getDtAnnullamento(), task.getChanged(), task.getAnnullato()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateTask(DmalmTask task) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, tsk)

					.where(tsk.cdTask.equalsIgnoreCase(task.getCdTask()))
					.where(tsk.idRepository.equalsIgnoreCase(task
							.getIdRepository()))
					.where(tsk.rankStatoTask.eq(new Double(1)))
					.set(tsk.cdTask, task.getCdTask())
					.set(tsk.codice, task.getCodice())
					.set(tsk.dataChiusuraTask, task.getDataChiusuraTask())
					.set(tsk.dataFineEffettiva, task.getDataFineEffettiva())
					.set(tsk.dataFinePianificata, task.getDataFinePianificata())
					.set(tsk.dataInizioEffettivo, task.getDataInizioEffettivo())
					.set(tsk.dataInizioPianificato,
							task.getDataInizioPianificato())
					.set(tsk.descrizioneTask, task.getDescrizioneTask())
					.set(tsk.dmalmAreaTematicaFk05,
							task.getDmalmAreaTematicaFk05())
					.set(tsk.dmalmProjectFk02, task.getDmalmProjectFk02())
					.set(tsk.dmalmStatoWorkitemFk03,
							task.getDmalmStatoWorkitemFk03())
					.set(tsk.dmalmStrutturaOrgFk01,
							task.getDmalmStrutturaOrgFk01())
					.set(tsk.dmalmTempoFk04, task.getDmalmTempoFk04())
					.set(tsk.dsAutoreTask, task.getDsAutoreTask())
					.set(tsk.dtCreazioneTask, task.getDtCreazioneTask())
					.set(tsk.dtModificaTask, task.getDtModificaTask())
					.set(tsk.dtRisoluzioneTask, task.getDtRisoluzioneTask())
					.set(tsk.dtScadenzaTask, task.getDtScadenzaTask())
					.set(tsk.idAutoreTask, task.getIdAutoreTask())
					.set(tsk.idRepository, task.getIdRepository())
					.set(tsk.initialCost, task.getInitialCost())
					.set(tsk.motivoRisoluzioneTask,
							task.getMotivoRisoluzioneTask())
					.set(tsk.numeroLinea, task.getNumeroLinea())
					.set(tsk.numeroTestata, task.getNumeroTestata())
					.set(tsk.priorityTask, task.getPriorityTask())
					.set(tsk.severityTask, task.getSeverityTask())
					.set(tsk.taskType, task.getTaskType())
					.set(tsk.tempoTotaleRisoluzione,
							task.getTempoTotaleRisoluzione())
					.set(tsk.titoloTask, task.getTitoloTask())
					.set(tsk.stgPk, task.getStgPk())
					.set(tsk.uri, task.getUri())
					.set(tsk.dtAnnullamento, task.getDtAnnullamento())
					.set(tsk.annullato, task.getAnnullato())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmTask getTask(Integer pk) throws DAOException{
		
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> tasks = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			tasks = query
					.from(tsk)
					.where(tsk.dmalmTaskPk.eq(pk)).list(tsk.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		
		if (tasks.size()>0){
			
			Tuple t = tasks.get(0);
			DmalmTask a = new DmalmTask();
			
			a.setAnnullato(t.get(tsk.annullato));
			a.setCdTask(t.get(tsk.cdTask));
			a.setChanged(t.get(tsk.changed));
			a.setCodice(t.get(tsk.codice));
			a.setDataChiusuraTask(t.get(tsk.dataChiusuraTask));
			a.setDataFineEffettiva(t.get(tsk.dataFineEffettiva));
			a.setDataFinePianificata(t.get(tsk.dataFinePianificata));
			a.setDescrizioneTask(t.get(tsk.descrizioneTask));
			a.setDmalmAreaTematicaFk05(t.get(tsk.dmalmAreaTematicaFk05));
			a.setDmalmProjectFk02(t.get(tsk.dmalmProjectFk02));
			a.setDmalmStatoWorkitemFk03(t.get(tsk.dmalmStatoWorkitemFk03));
			a.setDmalmStrutturaOrgFk01(t.get(tsk.dmalmStrutturaOrgFk01));
			a.setDmalmTaskPk(t.get(tsk.dmalmTaskPk));
			a.setDmalmTempoFk04(t.get(tsk.dmalmTempoFk04));
			a.setDmalmUserFk06(t.get(tsk.dmalmUserFk06));
			a.setDsAutoreTask(t.get(tsk.dsAutoreTask));
			a.setDtAnnullamento(t.get(tsk.dtAnnullamento));
			a.setDtCambioStatoTask(t.get(tsk.dtCambioStatoTask));
			a.setDtCaricamentoTask(t.get(tsk.dtCaricamentoTask));
			a.setDtCreazioneTask(t.get(tsk.dtCreazioneTask));
			a.setDtModificaTask(t.get(tsk.dtModificaTask));
			a.setDtRisoluzioneTask(t.get(tsk.dtRisoluzioneTask));
			a.setDtScadenzaTask(t.get(tsk.dtScadenzaTask));
			a.setDtStoricizzazione(t.get(tsk.dtStoricizzazione));
			a.setIdAutoreTask(t.get(tsk.idAutoreTask));
			a.setIdRepository(t.get(tsk.idRepository));
			a.setInitialCost(t.get(tsk.initialCost));
			a.setMotivoRisoluzioneTask(t.get(tsk.motivoRisoluzioneTask));
			a.setNumeroLinea(t.get(tsk.numeroLinea));
			a.setNumeroTestata(t.get(tsk.numeroTestata));
			a.setPriorityTask(t.get(tsk.priorityTask));
			a.setRankStatoTask(t.get(tsk.rankStatoTask));
			a.setRankStatoTaskMese(t.get(tsk.rankStatoTaskMese));
			a.setSeverityTask(t.get(tsk.severityTask));
			a.setStgPk(t.get(tsk.stgPk));
			a.setTaskType(t.get(tsk.taskType));
			a.setTempoTotaleRisoluzione(t.get(tsk.tempoTotaleRisoluzione));
			a.setTitoloTask(t.get(tsk.titoloTask));
			a.setUri(t.get(tsk.uri));
			
			return a;
			
		} else return null;

	}

	public static boolean checkEsistenzaTask(DmalmTask t, DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(tsk)
					.where(tsk.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(tsk.cdTask.eq(t.getCdTask()))
					.where(tsk.idRepository.eq(t.getIdRepository()))
					.list(tsk.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
