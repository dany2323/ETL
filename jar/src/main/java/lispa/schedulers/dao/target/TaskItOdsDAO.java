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

import lispa.schedulers.bean.target.fatti.DmalmTaskIt;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmTaskItOds;

public class TaskItOdsDAO {

	private static Logger logger = Logger.getLogger(TaskItOdsDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmTaskItOds taskItOds = QDmalmTaskItOds.dmalmTaskItOds;

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, taskItOds).execute();

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmTaskIt> staging_taskit,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmTaskIt task : staging_taskit) {
				new SQLInsertClause(connection, dialect, taskItOds)
						.columns(taskItOds.cdTaskIt,
								taskItOds.descrizioneTaskIt,
								taskItOds.dmalmTaskItPk,
								taskItOds.dmalmProjectFk02,
								taskItOds.dmalmStatoWorkitemFk03,
								taskItOds.dmalmStrutturaOrgFk01,
								taskItOds.dmalmTempoFk04,
								taskItOds.dsAutoreTaskIt,
								taskItOds.dtCambioStatoTaskIt,
								taskItOds.dtCaricamentoTaskIt,
								taskItOds.dtCreazioneTaskIt,
								taskItOds.dtModificaTaskIt,
								taskItOds.dtRisoluzioneTaskIt,
								taskItOds.dtScadenzaTaskIt,
								taskItOds.dtStoricizzazione,
								taskItOds.idAutoreTaskIt,
								taskItOds.idRepository,
								taskItOds.motivoRisoluzioneTaskIt,
								taskItOds.rankStatoTaskIt,
								taskItOds.titoloTaskIt, taskItOds.stgPk,
								taskItOds.avanzamento, taskItOds.codice,
								taskItOds.dtFineEffettiva,
								taskItOds.dtFinePianificata,
								taskItOds.dtInizioEffettivo,
								taskItOds.dtInizioPianificato,
								taskItOds.durataEffettiva,
								taskItOds.priorityTaskIt,
								taskItOds.severityTaskIt, taskItOds.tipoTask,
								taskItOds.dmalmUserFk06, taskItOds.uri)
						.values(task.getCdTaskIt(),
								task.getDescrizioneTaskIt(),
								task.getDmalmTaskItPk(),
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
								task.getDtModificaTaskIt(),
								task.getIdAutoreTaskIt(),
								task.getIdRepository(),
								task.getMotivoRisoluzioneTaskIt(),
								new Double(1), task.getTitoloTaskIt(),
								task.getStgPk(), task.getAvanzamento(),
								task.getCodice(), task.getDtFineEffettiva(),
								task.getDtFinePianificata(),
								task.getDtInizioEffettivo(),
								task.getDtInizioPianificato(),
								task.getDurataEffettiva(),
								task.getPriorityTaskIt(),
								task.getSeverityTaskIt(), task.getTipoTask(),
								task.getDmalmUserFk06(), task.getUri())
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

	public static List<DmalmTaskIt> getAll() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmTaskIt> resultListEl = new LinkedList<DmalmTaskIt>();


		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(taskItOds).orderBy(taskItOds.cdTaskIt.asc())
					.orderBy(taskItOds.dtModificaTaskIt.asc())
					.list(taskItOds.all());
			
			for (Tuple result : list) {
				DmalmTaskIt resultEl = new DmalmTaskIt();
				resultEl.setDmalmTaskItPk(result.get(taskItOds.dmalmTaskItPk));
				resultEl.setAvanzamento(result.get(taskItOds.avanzamento));
				resultEl.setDmalmUserFk06(result.get(taskItOds.dmalmUserFk06));
                resultEl.setCdTaskIt(result.get(taskItOds.cdTaskIt));
                resultEl.setCodice(result.get(taskItOds.codice));
                resultEl.setDescrizioneTaskIt(result.get(taskItOds.descrizioneTaskIt));
                resultEl.setDmalmProjectFk02(result.get(taskItOds.dmalmProjectFk02));
                resultEl.setDmalmStatoWorkitemFk03(result.get(taskItOds.dmalmStatoWorkitemFk03));
                resultEl.setDmalmStrutturaOrgFk01(result.get(taskItOds.dmalmStrutturaOrgFk01));
                resultEl.setDmalmTempoFk04(result.get(taskItOds.dmalmTempoFk04));
                resultEl.setDsAutoreTaskIt(result.get(taskItOds.dsAutoreTaskIt));
                resultEl.setDtCambioStatoTaskIt(result.get(taskItOds.dtCambioStatoTaskIt));
                resultEl.setDtCaricamentoTaskIt(result.get(taskItOds.dtCaricamentoTaskIt));
                resultEl.setDtCreazioneTaskIt(result.get(taskItOds.dtCreazioneTaskIt));
                resultEl.setDtFineEffettiva(result.get(taskItOds.dtFineEffettiva));
                resultEl.setDtFinePianificata(result.get(taskItOds.dtFinePianificata));
                resultEl.setDtInizioEffettivo(result.get(taskItOds.dtInizioEffettivo));
                resultEl.setDtInizioPianificato(result.get(taskItOds.dtInizioPianificato));
                resultEl.setDtModificaTaskIt(result.get(taskItOds.dtModificaTaskIt));
                resultEl.setDtRisoluzioneTaskIt(result.get(taskItOds.dtRisoluzioneTaskIt));
                resultEl.setDtScadenzaTaskIt(result.get(taskItOds.dtScadenzaTaskIt));
                resultEl.setDtStoricizzazione(result.get(taskItOds.dtStoricizzazione));
                resultEl.setDurataEffettiva(result.get(taskItOds.durataEffettiva));
                resultEl.setIdRepository(result.get(taskItOds.idRepository));
                resultEl.setIdAutoreTaskIt(result.get(taskItOds.idAutoreTaskIt));
                resultEl.setMotivoRisoluzioneTaskIt(result.get(taskItOds.motivoRisoluzioneTaskIt));
                resultEl.setPriorityTaskIt(result.get(taskItOds.priorityTaskIt));             
                resultEl.setRankStatoTaskIt(result.get(taskItOds.rankStatoTaskIt));
                resultEl.setRankStatoTaskItMese(result.get(taskItOds.rankStatoTaskItMese));
                resultEl.setSeverityTaskIt(result.get(taskItOds.severityTaskIt));
                resultEl.setStgPk(result.get(taskItOds.stgPk));
                resultEl.setUri(result.get(taskItOds.uri));
                resultEl.setTipoTask(result.get(taskItOds.tipoTask));
                resultEl.setTitoloTaskIt(result.get(taskItOds.titoloTaskIt));				
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
