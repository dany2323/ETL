package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.target.QDmalmFilieraTemplateDocumenti;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class FilieraTemplateDocumentiDAO {

	private static Logger logger = Logger
			.getLogger(FilieraTemplateDocumentiDAO.class);

	public static void insert(String template, Integer idFiliera,
			Integer livello, Integer fkWi, String tipoWi, List<Integer> fkDocumento)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			QDmalmFilieraTemplateDocumenti filiera = QDmalmFilieraTemplateDocumenti.dmalmFilieraTemplateDocumenti;

			SQLTemplates dialect = new HSQLDBTemplates();

			connection.setAutoCommit(false);
			for (Integer d : fkDocumento) {
				new SQLInsertClause(connection, dialect, filiera)
						.columns(filiera.template,
								filiera.idFiliera,
								filiera.livello, 
								filiera.fkWi,
								filiera.tipoWi,
								filiera.fkDocumento)
						.values(template, 
								idFiliera, 
								livello, 
								fkWi,
								tipoWi,
								d)
						.execute();
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

	public static void delete(String template) throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QDmalmFilieraTemplateDocumenti filiera = QDmalmFilieraTemplateDocumenti.dmalmFilieraTemplateDocumenti;

			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLDeleteClause(connection, dialect, filiera).where(
					filiera.template.eq(template)).execute();

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
