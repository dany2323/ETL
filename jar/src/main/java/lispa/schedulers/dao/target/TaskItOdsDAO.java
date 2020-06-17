package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmTaskIt;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmTaskItOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

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
		List <Long> listPk= new ArrayList<>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmTaskIt task : staging_taskit) {
				if(listPk.contains(task.getDmalmTaskItPk()))
					logger.info("Trovata DmalmTaskItPk DUPLICATA!!!"+task.getDmalmTaskItPk());
				else{
					listPk.add(task.getDmalmTaskItPk());
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
									taskItOds.dmalmUserFk06, taskItOds.uri,
									taskItOds.tagAlm, taskItOds.tsTagAlm)
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
									task.getDmalmUserFk06(), task.getUri(),
									task.getTagAlm(), task.getTsTagAlm())
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

	public static List<DmalmTaskIt> getAll() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmTaskIt> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(taskItOds).orderBy(taskItOds.cdTaskIt.asc())
					.orderBy(taskItOds.dtModificaTaskIt.asc())
					.list(Projections.bean(DmalmTaskIt.class, taskItOds.all()));

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
