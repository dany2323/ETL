package lispa.schedulers.svn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.sgr.xml.DmAlmWorkitemLinkRoles;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmWorkitemLinkRoles;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class LinkedWorkItemRolesXML {

	private static final Logger logger = Logger
			.getLogger(LinkedWorkItemRolesXML.class);

	public LinkedWorkItemRolesXML() {

	}

	public static void fillLinkedWorkItemRoles(String myrepository)
			throws Exception {

		try {
			fillTemplateLinkedWorkItemRoles(myrepository,
					DmAlmConstants.SVILUPPO);
			fillTemplateLinkedWorkItemRoles(myrepository, DmAlmConstants.DEMAND);
			fillTemplateLinkedWorkItemRoles(myrepository, DmAlmConstants.DEMAND2016);
			fillTemplateLinkedWorkItemRoles(myrepository,
					DmAlmConstants.ASSISTENZA);
			fillTemplateLinkedWorkItemRoles(myrepository, DmAlmConstants.IT);
			fillTemplateLinkedWorkItemRoles(myrepository, DmAlmConstants.SERDEP);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}

	public static void fillTemplateLinkedWorkItemRoles(String myrepository,
			String template) throws SQLException, DAOException {

		DmAlmWorkitemLinkRoles dmAlmLinkRoles = DmAlmWorkitemLinkRoles.dmAlmWorkitemLinkRoles;
		QDmAlmWorkitemLinkRoles qDmAlmLinkRoles = QDmAlmWorkitemLinkRoles.dmAlmWorkitemLinkRoles;
		
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		List<Tuple> listLinkedWiRoles = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			listLinkedWiRoles = query
				.from(dmAlmLinkRoles)
				.where(dmAlmLinkRoles.templates.equalsIgnoreCase(template))
				.where(dmAlmLinkRoles.repository.equalsIgnoreCase(myrepository))
				.list(dmAlmLinkRoles.all());

			for (Tuple linkedWiRoles : listLinkedWiRoles) {

				new SQLInsertClause(connection, dialect,
						qDmAlmLinkRoles)
						.columns(qDmAlmLinkRoles.workitemLinkRolesPk,
								qDmAlmLinkRoles.datacaricamento,
								qDmAlmLinkRoles.idRuolo,
								qDmAlmLinkRoles.descrizione,
								qDmAlmLinkRoles.repository,
								qDmAlmLinkRoles.templates,
								qDmAlmLinkRoles.nomeRuolo,
								qDmAlmLinkRoles.nomeRuoloInverso,
								qDmAlmLinkRoles.parent)
						.values(StringTemplate
								.create("DM_ALM_WORKITEM_LINK_ROLES_SEQ.nextval"),
								DataEsecuzione.getInstance().getDataEsecuzione(),
								linkedWiRoles.get(dmAlmLinkRoles.idRuolo),
								linkedWiRoles.get(dmAlmLinkRoles.descrizione),
								myrepository.toString(), 
								template,
								linkedWiRoles.get(dmAlmLinkRoles.nomeRuolo),
								linkedWiRoles.get(dmAlmLinkRoles.nomeRuoloInverso),
								linkedWiRoles.get(dmAlmLinkRoles.parent))
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

	public static void recoverLinkedWorkItemRoles() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmAlmWorkitemLinkRoles qDmAlmWorkitemLinkRoles = QDmAlmWorkitemLinkRoles.dmAlmWorkitemLinkRoles;
			new SQLDeleteClause(connection, dialect, qDmAlmWorkitemLinkRoles)
					.where(qDmAlmWorkitemLinkRoles.datacaricamento
							.eq(DataEsecuzione.getInstance()
									.getDataEsecuzione())).execute();
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
