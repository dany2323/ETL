package lispa.schedulers.svn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.xml.DmAlmProjectRoles;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmProjectRoles;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class ProjectRolesXML {

	private static Logger logger = Logger.getLogger(ProjectRolesXML.class);

	public static void fillProjectRoles(String myrepository) throws Exception {

		try {
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.SVILUPPO);
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.DEMAND);
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.DEMAND2016);
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.ASSISTENZA);
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.IT);
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.SERDEP);

			fillGlobalProjectRoles(myrepository);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}

	public static void fillTemplatesProjectRoles(String myrepository,
			String template) throws Exception {

		DmAlmProjectRoles dmAlmProjectRoles = DmAlmProjectRoles.dmAlmProjectRoles;
		QDmAlmProjectRoles qDmAlmProjectRoles = QDmAlmProjectRoles.dmAlmProjectRoles;
		
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		List<Tuple> listProjectRoles = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			listProjectRoles = query
				.from(dmAlmProjectRoles)
				.where(dmAlmProjectRoles.descrizione.equalsIgnoreCase(template))
				.where(dmAlmProjectRoles.repository.equalsIgnoreCase(myrepository))
				.list(dmAlmProjectRoles.all());

			for (Tuple projectWiRoles : listProjectRoles) {

				new SQLInsertClause(connection, dialect,
						qDmAlmProjectRoles)
						.columns(
								qDmAlmProjectRoles.dmAlmProjectRolesPk,
								qDmAlmProjectRoles.ruolo,
								qDmAlmProjectRoles.descrizione,
								qDmAlmProjectRoles.repository,
								qDmAlmProjectRoles.dataCaricamento)
						.values(StringTemplate
								.create("DM_ALM_PROJECT_ROLES_SEQ.nextval"),
								projectWiRoles.get(dmAlmProjectRoles.ruolo),
								template,
								myrepository,
								DataEsecuzione.getInstance().getDataEsecuzione())
						.execute();

			}
			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void fillGlobalProjectRoles(String myrepository)
			throws Exception {

		DmAlmProjectRoles dmAlmProjectRoles = DmAlmProjectRoles.dmAlmProjectRoles;
		QDmAlmProjectRoles qDmAlmProjectRoles = QDmAlmProjectRoles.dmAlmProjectRoles;
		
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		List<Tuple> listProjectRoles = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			listProjectRoles = query
				.from(dmAlmProjectRoles)
				.where(dmAlmProjectRoles.descrizione.equalsIgnoreCase(DmAlmConstants.GLOBAL))
				.where(dmAlmProjectRoles.repository.equalsIgnoreCase(myrepository))
				.list(dmAlmProjectRoles.all());

			for (Tuple projectWiRoles : listProjectRoles) {

				new SQLInsertClause(connection, dialect,
						qDmAlmProjectRoles)
						.columns(
								qDmAlmProjectRoles.dmAlmProjectRolesPk,
								qDmAlmProjectRoles.ruolo,
								qDmAlmProjectRoles.descrizione,
								qDmAlmProjectRoles.repository,
								qDmAlmProjectRoles.dataCaricamento)
						.values(StringTemplate
								.create("DM_ALM_PROJECT_ROLES_SEQ.nextval"),
								projectWiRoles.get(dmAlmProjectRoles.ruolo),
								DmAlmConstants.GLOBAL,
								myrepository,
								DataEsecuzione.getInstance().getDataEsecuzione())
						.execute();

			}
			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverAllProjectRoles() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmAlmProjectRoles qDmAlmProjectRoles = QDmAlmProjectRoles.dmAlmProjectRoles;

			new SQLDeleteClause(connection, dialect, qDmAlmProjectRoles).where(
					qDmAlmProjectRoles.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();
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