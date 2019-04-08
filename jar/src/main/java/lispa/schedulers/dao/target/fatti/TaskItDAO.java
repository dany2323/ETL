package lispa.schedulers.dao.target.fatti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmTaskIt;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTaskIt;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class TaskItDAO {
	private static Logger logger = Logger.getLogger(TaskItDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmTaskIt taskit = QDmalmTaskIt.dmalmTaskIt;

	public static List<DmalmTaskIt> getAllTaskIt(Timestamp dataEsecuzione)
			throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmTaskIt bean = null;
		List<DmalmTaskIt> tasks = new ArrayList<DmalmTaskIt>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.taskit));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_TASK_IT);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmTaskIt();

				bean.setCdTaskIt(rs.getString("CD_TASKIT"));
				bean.setDescrizioneTaskIt(rs.getString("DESCRIPTION"));
				bean.setDmalmTaskItPk(rs.getInt("DMALM_TASKIT_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDsAutoreTaskIt(rs.getString("NOME_AUTORE_TASKIT"));
				bean.setDtCaricamentoTaskIt(dataEsecuzione);
				bean.setDtCreazioneTaskIt(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaTaskIt(rs
						.getTimestamp("DATA_MODIFICA_TASKIT"));
				bean.setIdAutoreTaskIt(rs.getString("ID_AUTORE_TASKIT"));
				bean.setDtRisoluzioneTaskIt(rs
						.getTimestamp("DATA_RISOLUZIONE_TASKIT"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setDtScadenzaTaskIt(rs
						.getTimestamp("DATA_SCADENZA_TASKIT"));
				bean.setMotivoRisoluzioneTaskIt(rs
						.getString("MOTIVO_RISOLUZIONE_TASKIT"));
				bean.setTitoloTaskIt(rs.getString("TITOLO_TASKIT"));
				bean.setAvanzamento(rs.getInt("AVANZAMENTO"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setDtFineEffettiva(rs.getTimestamp("DATA_FINE_EFF"));
				bean.setDtFinePianificata(rs
						.getTimestamp("DATA_FINE_PIANIFICATA"));
				bean.setDtInizioEffettivo(rs
						.getTimestamp("DATA_INIZIO_EFFETTIVO"));
				bean.setDtInizioPianificato(rs
						.getTimestamp("DATA_INIZIO_PIANIFICATO"));
				bean.setDurataEffettiva(rs.getInt("DURATA_EFFETTIVA")
						- rs.getInt("GIORNI_FESTIVI"));
				bean.setPriorityTaskIt(rs.getString("PRIORITA_TASKIT"));
				bean.setSeverityTaskIt(rs.getString("SEVERITY_TASKIT"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_TASKIT_PK"));
				bean.setTipoTask(rs.getString("TIPO"));
				bean.setTagAlm(rs.getString("TAG_ALM"));
				bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
				
				tasks.add(bean);
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

		return tasks;
	}

	public static List<Tuple> getTaskIt(DmalmTaskIt task) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> tasks = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			tasks = query
					.from(taskit)
					.where(taskit.cdTaskIt.equalsIgnoreCase(task.getCdTaskIt()))
					.where(taskit.idRepository.equalsIgnoreCase(task
							.getIdRepository()))
					.where(taskit.rankStatoTaskIt.eq(new Double(1)))
					.list(taskit.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return tasks;
	}

	public static void insertTaskIt(DmalmTaskIt task) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, taskit)
					.columns(taskit.cdTaskIt, taskit.descrizioneTaskIt,
							taskit.dmalmTaskItPk, taskit.dmalmProjectFk02,
							taskit.dmalmStatoWorkitemFk03,
							taskit.dmalmStrutturaOrgFk01,
							taskit.dmalmTempoFk04, taskit.dsAutoreTaskIt,
							taskit.dtCambioStatoTaskIt,
							taskit.dtCaricamentoTaskIt,
							taskit.dtCreazioneTaskIt, taskit.dtModificaTaskIt,
							taskit.dtRisoluzioneTaskIt,
							taskit.dtScadenzaTaskIt, taskit.dtStoricizzazione,
							taskit.idAutoreTaskIt, taskit.idRepository,
							taskit.motivoRisoluzioneTaskIt,
							taskit.rankStatoTaskIt, taskit.titoloTaskIt,
							taskit.stgPk, taskit.avanzamento, taskit.codice,
							taskit.dtFineEffettiva, taskit.dtFinePianificata,
							taskit.dtInizioEffettivo,
							taskit.dtInizioPianificato, taskit.durataEffettiva,
							taskit.priorityTaskIt, taskit.severityTaskIt,
							taskit.tipoTask, taskit.dmalmUserFk06, taskit.uri,
							taskit.dtAnnullamento,
							taskit.tsTagAlm,taskit.tagAlm)
					.values(task.getCdTaskIt(), task.getDescrizioneTaskIt(),
							task.getDmalmTaskItPk(),
							task.getDmalmProjectFk02(),
							task.getDmalmStatoWorkitemFk03(),
							task.getDmalmStrutturaOrgFk01(),
							task.getDmalmTempoFk04(), task.getDsAutoreTaskIt(),
							task.getDtCambioStatoTaskIt(),
							task.getDtCaricamentoTaskIt(),
							task.getDtCreazioneTaskIt(),
							task.getDtModificaTaskIt(),
							task.getDtRisoluzioneTaskIt(),
							task.getDtScadenzaTaskIt(),
							task.getDtModificaTaskIt(),
							task.getIdAutoreTaskIt(), task.getIdRepository(),
							task.getMotivoRisoluzioneTaskIt(), new Double(1),
							task.getTitoloTaskIt(), task.getStgPk(),
							task.getAvanzamento(), task.getCodice(),
							task.getDtFineEffettiva(),
							task.getDtFinePianificata(),
							task.getDtInizioEffettivo(),
							task.getDtInizioPianificato(),
							task.getDurataEffettiva(),
							task.getPriorityTaskIt(), task.getSeverityTaskIt(),
							task.getTipoTask(), task.getDmalmUserFk06(),
							task.getUri(), task.getDtAnnullamento(),
							task.getTsTagAlm(),task.getTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmTaskIt task, Double double1)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, taskit)
					.where(taskit.cdTaskIt.equalsIgnoreCase(task.getCdTaskIt()))
					.where(taskit.idRepository.equalsIgnoreCase(task
							.getIdRepository()))
					.set(taskit.rankStatoTaskIt, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertTaskItUpdate(Timestamp dataEsecuzione,
			DmalmTaskIt task, boolean pkValue) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, taskit)
					.columns(taskit.cdTaskIt, taskit.descrizioneTaskIt,
							taskit.dmalmTaskItPk, taskit.dmalmProjectFk02,
							taskit.dmalmStatoWorkitemFk03,
							taskit.dmalmStrutturaOrgFk01,
							taskit.dmalmTempoFk04, taskit.dsAutoreTaskIt,
							taskit.dtCambioStatoTaskIt,
							taskit.dtCaricamentoTaskIt,
							taskit.dtCreazioneTaskIt, taskit.dtModificaTaskIt,
							taskit.dtRisoluzioneTaskIt,
							taskit.dtScadenzaTaskIt, taskit.dtStoricizzazione,
							taskit.idAutoreTaskIt, taskit.idRepository,
							taskit.motivoRisoluzioneTaskIt,
							taskit.rankStatoTaskIt, taskit.titoloTaskIt,
							taskit.stgPk, taskit.avanzamento, taskit.codice,
							taskit.dtFineEffettiva, taskit.dtFinePianificata,
							taskit.dtInizioEffettivo,
							taskit.dtInizioPianificato, taskit.durataEffettiva,
							taskit.priorityTaskIt, taskit.severityTaskIt,
							taskit.tipoTask, taskit.dmalmUserFk06, taskit.uri,
							taskit.dtAnnullamento, taskit.changed,
							taskit.annullato,
							taskit.tsTagAlm,taskit.tagAlm)
					.values(task.getCdTaskIt(),
							task.getDescrizioneTaskIt(),
							pkValue == true ? task.getDmalmTaskItPk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							task.getDmalmProjectFk02(),
							task.getDmalmStatoWorkitemFk03(),
							task.getDmalmStrutturaOrgFk01(),
							task.getDmalmTempoFk04(),
							task.getDsAutoreTaskIt(),
							task.getDtCambioStatoTaskIt(),
							task.getDtCaricamentoTaskIt(),
							task.getDtCreazioneTaskIt(),
							task.getDtModificaTaskIt(),
							task.getDtRisoluzioneTaskIt(),
							task.getDtScadenzaTaskIt(),
							pkValue == true ? task.getDtModificaTaskIt() : task
									.getDtStoricizzazione(),
							task.getIdAutoreTaskIt(), task.getIdRepository(),
							task.getMotivoRisoluzioneTaskIt(), 
							pkValue == true ? new Short("1")  : task.getRankStatoTaskIt(),
							task.getTitoloTaskIt(), task.getStgPk(),
							task.getAvanzamento(), task.getCodice(),
							task.getDtFineEffettiva(),
							task.getDtFinePianificata(),
							task.getDtInizioEffettivo(),
							task.getDtInizioPianificato(),
							task.getDurataEffettiva(),
							task.getPriorityTaskIt(), task.getSeverityTaskIt(),
							task.getTipoTask(), task.getDmalmUserFk06(),
							task.getUri(), task.getDtAnnullamento(),
							task.getChanged(), task.getAnnullato(),
							task.getTsTagAlm(),task.getTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateTaskIt(DmalmTaskIt task) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, taskit)
					.where(taskit.cdTaskIt.equalsIgnoreCase(task.getCdTaskIt()))
					.where(taskit.idRepository.equalsIgnoreCase(task
							.getIdRepository()))
					.where(taskit.rankStatoTaskIt.eq(new Double(1)))
					.set(taskit.cdTaskIt, task.getCdTaskIt())
					.set(taskit.descrizioneTaskIt, task.getDescrizioneTaskIt())
					.set(taskit.dmalmProjectFk02, task.getDmalmProjectFk02())
					.set(taskit.dmalmStatoWorkitemFk03,
							task.getDmalmStatoWorkitemFk03())
					.set(taskit.dmalmStrutturaOrgFk01,
							task.getDmalmStrutturaOrgFk01())
					.set(taskit.dmalmTempoFk04, task.getDmalmTempoFk04())
					.set(taskit.dsAutoreTaskIt, task.getDsAutoreTaskIt())
					.set(taskit.dtCreazioneTaskIt, task.getDtCreazioneTaskIt())
					.set(taskit.dtModificaTaskIt, task.getDtModificaTaskIt())
					.set(taskit.dtRisoluzioneTaskIt,
							task.getDtRisoluzioneTaskIt())
					.set(taskit.dtScadenzaTaskIt, task.getDtScadenzaTaskIt())
					.set(taskit.dtStoricizzazione, task.getDtModificaTaskIt())
					.set(taskit.idAutoreTaskIt, task.getIdAutoreTaskIt())
					.set(taskit.idRepository, task.getIdRepository())
					.set(taskit.motivoRisoluzioneTaskIt,
							task.getMotivoRisoluzioneTaskIt())
					.set(taskit.rankStatoTaskIt, new Double(1))
					.set(taskit.titoloTaskIt, task.getTitoloTaskIt())
					.set(taskit.stgPk, task.getStgPk())
					.set(taskit.uri, task.getUri())
					.set(taskit.avanzamento, task.getAvanzamento())
					.set(taskit.codice, task.getCodice())
					.set(taskit.dtFineEffettiva, task.getDtFineEffettiva())
					.set(taskit.dtFinePianificata, task.getDtFinePianificata())
					.set(taskit.dtInizioEffettivo, task.getDtInizioEffettivo())
					.set(taskit.dtInizioPianificato,
							task.getDtInizioPianificato())
					.set(taskit.durataEffettiva, task.getDurataEffettiva())
					.set(taskit.priorityTaskIt, task.getPriorityTaskIt())
					.set(taskit.severityTaskIt, task.getSeverityTaskIt())
					.set(taskit.tipoTask, task.getTipoTask())
					.set(taskit.dtAnnullamento, task.getDtAnnullamento())
					.set(taskit.annullato, task.getAnnullato())
					.set(taskit.tagAlm, task.getTagAlm())
					.set(taskit.tsTagAlm, task.getTsTagAlm())
					.execute();
			connection.commit();
		}

		catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static DmalmTaskIt getTaskIt(Integer pk) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> tasks = new ArrayList<Tuple>();
		QDmalmTaskIt tsk = QDmalmTaskIt.dmalmTaskIt;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			tasks = query.from(tsk).where(tsk.dmalmTaskItPk.eq(pk))
					.list(tsk.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (tasks.size() > 0) {

			Tuple t = tasks.get(0);
			DmalmTaskIt a = new DmalmTaskIt();

			a.setAnnullato(t.get(tsk.annullato));
			a.setAvanzamento(t.get(tsk.avanzamento));
			a.setCdTaskIt(t.get(tsk.cdTaskIt));
			a.setChanged(t.get(tsk.changed));
			a.setCodice(t.get(tsk.codice));
			a.setDescrizioneTaskIt(t.get(tsk.descrizioneTaskIt));
			a.setDmalmProjectFk02(t.get(tsk.dmalmProjectFk02));
			a.setDmalmStatoWorkitemFk03(t.get(tsk.dmalmStatoWorkitemFk03));
			a.setDmalmStrutturaOrgFk01(t.get(tsk.dmalmStrutturaOrgFk01));
			a.setDmalmTaskItPk(t.get(tsk.dmalmTaskItPk));
			a.setDmalmTempoFk04(t.get(tsk.dmalmTempoFk04));
			a.setDmalmUserFk06(t.get(tsk.dmalmUserFk06));
			a.setDsAutoreTaskIt(t.get(tsk.dsAutoreTaskIt));
			a.setDtAnnullamento(t.get(tsk.dtAnnullamento));
			a.setDtCambioStatoTaskIt(t.get(tsk.dtCambioStatoTaskIt));
			a.setDtCaricamentoTaskIt(t.get(tsk.dtCaricamentoTaskIt));
			a.setDtCreazioneTaskIt(t.get(tsk.dtCreazioneTaskIt));
			a.setDtModificaTaskIt(t.get(tsk.dtModificaTaskIt));
			a.setDtRisoluzioneTaskIt(t.get(tsk.dtRisoluzioneTaskIt));
			a.setDtScadenzaTaskIt(t.get(tsk.dtScadenzaTaskIt));
			a.setDtStoricizzazione(t.get(tsk.dtStoricizzazione));
			a.setDtFineEffettiva(t.get(tsk.dtFineEffettiva));
			a.setDtFinePianificata(t.get(tsk.dtFinePianificata));
			a.setDtInizioEffettivo(t.get(tsk.dtInizioEffettivo));
			a.setDtInizioPianificato(t.get(tsk.dtInizioPianificato));
			a.setDurataEffettiva(t.get(tsk.durataEffettiva));
			a.setIdAutoreTaskIt(t.get(tsk.idAutoreTaskIt));
			a.setIdRepository(t.get(tsk.idRepository));
			a.setPriorityTaskIt(t.get(tsk.priorityTaskIt));
			a.setMotivoRisoluzioneTaskIt(t.get(tsk.motivoRisoluzioneTaskIt));
			a.setRankStatoTaskIt(t.get(tsk.rankStatoTaskIt));
			a.setRankStatoTaskItMese(t.get(tsk.rankStatoTaskItMese));
			a.setSeverityTaskIt(t.get(tsk.severityTaskIt));
			a.setStgPk(t.get(tsk.stgPk));
			a.setTipoTask(t.get(tsk.tipoTask));
			a.setTitoloTaskIt(t.get(tsk.titoloTaskIt));
			a.setUri(t.get(tsk.uri));
			a.setTagAlm(t.get(tsk.tagAlm));
			a.setTsTagAlm(t.get(tsk.tsTagAlm));
			
			return a;

		} else
			return null;

	}

	public static boolean checkEsistenzaTask(DmalmTaskIt t, DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(taskit)
					.where(taskit.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(taskit.cdTaskIt.eq(t.getCdTaskIt()))
					.where(taskit.idRepository.eq(t.getIdRepository()))
					.list(taskit.all());

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
