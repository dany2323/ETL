package lispa.schedulers.dao.target;

import java.sql.Connection;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.utils.QueryUtils;
import oracle.jdbc.OracleCallableStatement;
import org.apache.log4j.Logger;

public class FilieraProduttivaAvanzataDAO {
	private static Logger logger = Logger
			.getLogger(FilieraProduttivaAvanzataDAO.class);

	public static void insert() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure("FILIERA_PRODUTTIVA_AVANZATA.REFRESH_ALL_DATA_FILIERA", 0);
			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.execute();
				connection.commit();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	

//	public static void insertNoLinked(Integer idFiliera, Integer livello,
//			Integer sottoLivello, DmalmLinkedWorkitems linkedWorkitem)
//			throws DAOException {
//
//		ConnectionManager cm = null;
//		Connection connection = null;
//
//		try {
//			cm = ConnectionManager.getInstance();
//			connection = cm.getConnectionOracle();
//
//			QDmalmFilieraProduttiva filieraProduttiva = QDmalmFilieraProduttiva.dmalmFilieraProduttiva;
//
//			SQLTemplates dialect = new HSQLDBTemplates();
//
//			connection.setAutoCommit(false);
//
//			// uriWi padre e figlio sono invertiti nel linkedworkitems, quindi
//			// qui sotto sono scritti al contrario
//
//			new SQLInsertClause(connection, dialect, filieraProduttiva)
//					.columns(filieraProduttiva.idFiliera,
//							filieraProduttiva.livello,
//							filieraProduttiva.sottoLivello,
//							filieraProduttiva.fkWi, filieraProduttiva.codiceWi,
//							filieraProduttiva.tipoWi,
//							filieraProduttiva.idRepository,
//							filieraProduttiva.uriWi,
//							filieraProduttiva.codiceProject,
//							filieraProduttiva.fkWiFiglio,
//							filieraProduttiva.codiceWiFiglio,
//							filieraProduttiva.tipoWiFiglio,
//							filieraProduttiva.idRepositoryFiglio,
//							filieraProduttiva.uriWiFiglio,
//							filieraProduttiva.codiceProjectFiglio,
//							filieraProduttiva.ruolo,
//							filieraProduttiva.dataCaricamento)
//					.values(idFiliera, livello, sottoLivello,
//							linkedWorkitem.getFkWiPadre(),
//							linkedWorkitem.getCodiceWiPadre(),
//							linkedWorkitem.getTipoWiPadre(),
//							linkedWorkitem.getIdRepositoryPadre(),
//							linkedWorkitem.getUriWiFiglio(),
//							linkedWorkitem.getCodiceProjectPadre(),
//							null,
//							null,
//							null,
//							null,
//							null,
//							null,
//							null,
//							DataEsecuzione.getInstance().getDataEsecuzione())
//					.execute();
//
//			connection.commit();
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//
//			throw new DAOException(e);
//		} finally {
//			if (cm != null)
//				cm.closeConnection(connection);
//		}
//	}
}
