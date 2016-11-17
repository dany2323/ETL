package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;

import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.target.QDmalmFilieraProduttiva;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class FilieraProduttivaDAO {
	private static Logger logger = Logger
			.getLogger(FilieraProduttivaDAO.class);

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QDmalmFilieraProduttiva filieraProduttiva = QDmalmFilieraProduttiva.dmalmFilieraProduttiva;

			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLDeleteClause(connection, dialect, filieraProduttiva)
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
			Integer sottoLivello, DmalmLinkedWorkitems firstWorkitem, DmalmLinkedWorkitems linkedWorkitem)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			QDmalmFilieraProduttiva filieraProduttiva = QDmalmFilieraProduttiva.dmalmFilieraProduttiva;

			SQLTemplates dialect = new HSQLDBTemplates();

			connection.setAutoCommit(false);

			// uriWi padre e figlio sono invertiti nel linkedworkitems, quindi
			// qui sotto sono scritti al contrario

			new SQLInsertClause(connection, dialect, filieraProduttiva)
					.columns(filieraProduttiva.idFiliera,
							filieraProduttiva.livello,
							filieraProduttiva.sottoLivello,
							filieraProduttiva.fkWi, filieraProduttiva.codiceWi,
							filieraProduttiva.tipoWi,
							filieraProduttiva.idRepository,
							filieraProduttiva.uriWi,
							filieraProduttiva.codiceProject,
							filieraProduttiva.fkWiFiglio,
							filieraProduttiva.codiceWiFiglio,
							filieraProduttiva.tipoWiFiglio,
							filieraProduttiva.idRepositoryFiglio,
							filieraProduttiva.uriWiFiglio,
							filieraProduttiva.codiceProjectFiglio,
							filieraProduttiva.ruolo,
							filieraProduttiva.dataCaricamento)
					.values(idFiliera, livello, sottoLivello,
							firstWorkitem.getFkWiPadre(),
							firstWorkitem.getCodiceWiPadre(),
							firstWorkitem.getTipoWiPadre(),
							firstWorkitem.getIdRepositoryPadre(),
							firstWorkitem.getUriWiFiglio(),
							firstWorkitem.getCodiceProjectPadre(),
							linkedWorkitem.getFkWiFiglio(),
							linkedWorkitem.getCodiceWiFiglio(),
							linkedWorkitem.getTipoWiFiglio(),
							linkedWorkitem.getIdRepositoryFiglio(),
							linkedWorkitem.getUriWiPadre(),
							linkedWorkitem.getCodiceProjectFiglio(),
							linkedWorkitem.getRuolo(),
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
	

	public static void insertNoLinked(Integer idFiliera, Integer livello,
			Integer sottoLivello, DmalmLinkedWorkitems linkedWorkitem)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			QDmalmFilieraProduttiva filieraProduttiva = QDmalmFilieraProduttiva.dmalmFilieraProduttiva;

			SQLTemplates dialect = new HSQLDBTemplates();

			connection.setAutoCommit(false);

			// uriWi padre e figlio sono invertiti nel linkedworkitems, quindi
			// qui sotto sono scritti al contrario

			new SQLInsertClause(connection, dialect, filieraProduttiva)
					.columns(filieraProduttiva.idFiliera,
							filieraProduttiva.livello,
							filieraProduttiva.sottoLivello,
							filieraProduttiva.fkWi, filieraProduttiva.codiceWi,
							filieraProduttiva.tipoWi,
							filieraProduttiva.idRepository,
							filieraProduttiva.uriWi,
							filieraProduttiva.codiceProject,
							filieraProduttiva.fkWiFiglio,
							filieraProduttiva.codiceWiFiglio,
							filieraProduttiva.tipoWiFiglio,
							filieraProduttiva.idRepositoryFiglio,
							filieraProduttiva.uriWiFiglio,
							filieraProduttiva.codiceProjectFiglio,
							filieraProduttiva.ruolo,
							filieraProduttiva.dataCaricamento)
					.values(idFiliera, livello, sottoLivello,
							linkedWorkitem.getFkWiPadre(),
							linkedWorkitem.getCodiceWiPadre(),
							linkedWorkitem.getTipoWiPadre(),
							linkedWorkitem.getIdRepositoryPadre(),
							linkedWorkitem.getUriWiFiglio(),
							linkedWorkitem.getCodiceProjectPadre(),
							null,
							null,
							null,
							null,
							null,
							null,
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
