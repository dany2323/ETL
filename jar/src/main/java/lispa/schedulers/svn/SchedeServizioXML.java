package lispa.schedulers.svn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.sgr.xml.DmAlmSchedeServizio;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSchedeServizio;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SchedeServizioXML {

	private static Logger logger = Logger.getLogger(SchedeServizioXML.class);

	public static String getProjectSVNPath(String clocation) {
		if (clocation.startsWith("default:/")) {
			int start = clocation.indexOf(":/") + 1;

			int end = clocation.indexOf(".polarion") - 1;

			return clocation.substring(start, end);

		} else {
			return null;
		}
	}

	public static void fillSchedeServizio(String repository) throws DAOException {
		DmAlmSchedeServizio schedeServizio = DmAlmSchedeServizio.dmalmSchedeServizio;
		QDmalmSchedeServizio qSchedeServizio = QDmalmSchedeServizio.dmalmSchedeServizio;
		
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		List<Tuple> listSchedeServizio = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			listSchedeServizio = query
				.from(schedeServizio)
				.where(schedeServizio.repository.equalsIgnoreCase(repository))
				.list(schedeServizio.all());

			for (Tuple ss : listSchedeServizio) {

				new SQLInsertClause(connection, dialect, qSchedeServizio)
					.columns(qSchedeServizio.dmalm_schedeServizio_Pk, 
							qSchedeServizio.id,
							qSchedeServizio.name, 
							qSchedeServizio.sorter,
							qSchedeServizio.dtCaricamento, 
							qSchedeServizio.repository)
					.values(StringTemplate
								.create("DM_ALM_STG_SCHEDE_SERVIZIO_SEQ.nextval"),
							ss.get(schedeServizio.id),
							ss.get(schedeServizio.name),
							ss.get(schedeServizio.sort_order),
							DataEsecuzione.getInstance().getDataEsecuzione(),
							repository)
					.execute();
			}
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverSchedeServizio(String repository) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmSchedeServizio qDmalmSchedeServizio = QDmalmSchedeServizio.dmalmSchedeServizio;

			new SQLDeleteClause(connection, dialect, qDmalmSchedeServizio)
					.where(qDmalmSchedeServizio.dtCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione()))
					.where(qDmalmSchedeServizio.repository.eq(repository))
					.execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
