package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;

import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.target.QDmalmFilieraAnomalie;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaAssistenza;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class FilieraAnomalieDAO {
	private static Logger logger = Logger.getLogger(FilieraAnomalieDAO.class);

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QDmalmFilieraAnomalie filieraAnomalie = QDmalmFilieraAnomalie.dmalmFilieraAnomalie;

			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLDeleteClause(connection, dialect, filieraAnomalie)
					.execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insert(Integer idFiliera, Integer livello,
			Integer sottoLivello, boolean lastWorkitem,
			DmalmLinkedWorkitems linkedWorkitem) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			QDmalmFilieraAnomalie filieraAnomalie = QDmalmFilieraAnomalie.dmalmFilieraAnomalie;

			SQLTemplates dialect = new HSQLDBTemplates();

			connection.setAutoCommit(false);

			// uriWi padre e figlio sono invertiti nel linkedworkitems, quindi
			// qui sotto sono scritti al contrario

			new SQLInsertClause(connection, dialect, filieraAnomalie)
					.columns(filieraAnomalie.idFiliera,
							filieraAnomalie.livello,
							filieraAnomalie.sottoLivello,
							filieraAnomalie.fkWi, filieraAnomalie.codiceWi,
							filieraAnomalie.tipoWi,
							filieraAnomalie.idRepository,
							filieraAnomalie.uriWi,
							filieraAnomalie.codiceProject,
							filieraAnomalie.ruolo,
							filieraAnomalie.dataCaricamento)
					.values(idFiliera, livello, sottoLivello,
							linkedWorkitem.getFkWiPadre(),
							linkedWorkitem.getCodiceWiPadre(),
							linkedWorkitem.getTipoWiPadre(),
							linkedWorkitem.getIdRepositoryPadre(),
							linkedWorkitem.getUriWiFiglio(),
							linkedWorkitem.getCodiceProjectPadre(),
							linkedWorkitem.getRuolo(),
							DataEsecuzione.getInstance().getDataEsecuzione())
					.execute();

			if (lastWorkitem) {
				livello++;
				
				if(linkedWorkitem.getTipoWiFiglio().equals(linkedWorkitem.getTipoWiPadre())) {
					sottoLivello++;
				}

				new SQLInsertClause(connection, dialect, filieraAnomalie)
						.columns(filieraAnomalie.idFiliera,
								filieraAnomalie.livello,
								filieraAnomalie.sottoLivello,
								filieraAnomalie.fkWi,
								filieraAnomalie.codiceWi,
								filieraAnomalie.tipoWi,
								filieraAnomalie.idRepository,
								filieraAnomalie.uriWi,
								filieraAnomalie.codiceProject,
								filieraAnomalie.ruolo,
								filieraAnomalie.dataCaricamento)
						.values(idFiliera,
								livello,
								sottoLivello,
								linkedWorkitem.getFkWiFiglio(),
								linkedWorkitem.getCodiceWiFiglio(),
								linkedWorkitem.getTipoWiFiglio(),
								linkedWorkitem.getIdRepositoryFiglio(),
								linkedWorkitem.getUriWiPadre(),
								linkedWorkitem.getCodiceProjectFiglio(),
								linkedWorkitem.getRuolo(),
								DataEsecuzione.getInstance()
										.getDataEsecuzione()).execute();
			}

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertNoLinked(Integer idFiliera, Integer livello,
			Integer sottoLivello, Tuple row)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			QDmalmFilieraAnomalie filieraAnomalie = QDmalmFilieraAnomalie.dmalmFilieraAnomalie;
			QDmalmAnomaliaAssistenza anomaliaAssistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
			QDmalmProject project = QDmalmProject.dmalmProject; 

			SQLTemplates dialect = new HSQLDBTemplates();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, filieraAnomalie)
					.columns(filieraAnomalie.idFiliera,
							filieraAnomalie.livello,
							filieraAnomalie.sottoLivello,
							filieraAnomalie.fkWi, 
							filieraAnomalie.codiceWi,
							filieraAnomalie.tipoWi,
							filieraAnomalie.idRepository,
							filieraAnomalie.uriWi,
							filieraAnomalie.codiceProject,
							filieraAnomalie.ruolo,
							filieraAnomalie.dataCaricamento)
					.values(idFiliera, livello, sottoLivello,
							row.get(anomaliaAssistenza.dmalmAnomaliaAssPk),
							row.get(anomaliaAssistenza.cdAnomaliaAss),
							"anomalia_assistenza",
							row.get(anomaliaAssistenza.idRepository),
							row.get(anomaliaAssistenza.uri),
							row.get(project.idProject),
							null,
							DataEsecuzione.getInstance().getDataEsecuzione())
					.execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
}
