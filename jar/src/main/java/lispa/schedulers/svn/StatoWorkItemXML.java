package lispa.schedulers.svn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.xml.DmAlmStatoWorkitem;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmStatoWorkitem;
import lispa.schedulers.utils.EnumUtils;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class StatoWorkItemXML {

	private static Logger logger = Logger.getLogger(StatoWorkItemXML.class);

	public static void fillStatoWorkItem(String repository, EnumWorkitemType workitemType) throws Exception {

		DmAlmStatoWorkitem dmAlmStatoWorkitem = DmAlmStatoWorkitem.dmAlmStatoWorkitem;
		QDmAlmStatoWorkitem qDmAlmStatoWorkitem = QDmAlmStatoWorkitem.dmAlmStatoWorkitem;
		
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		List<Tuple> listStatoWorkitem = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			listStatoWorkitem = query
				.from(dmAlmStatoWorkitem)
				.where(dmAlmStatoWorkitem.repository.equalsIgnoreCase(repository))
				.where(dmAlmStatoWorkitem.workitemType.equalsIgnoreCase(EnumUtils.getTemplateByWorkItem(workitemType)))
				.list(dmAlmStatoWorkitem.all());

			for (Tuple statoWorkitem : listStatoWorkitem) {

				new SQLInsertClause(connection, dialect, qDmAlmStatoWorkitem)
					.columns(
							qDmAlmStatoWorkitem.dmAlmStatoWorkitemPk,
							qDmAlmStatoWorkitem.descrizione,
							qDmAlmStatoWorkitem.iconUrl,
							qDmAlmStatoWorkitem.id,
							qDmAlmStatoWorkitem.name,
							qDmAlmStatoWorkitem.sortOrder,
							qDmAlmStatoWorkitem.repository,
							qDmAlmStatoWorkitem.workitemType,
							qDmAlmStatoWorkitem.dataCaricamento,
							qDmAlmStatoWorkitem.template)
					.values(StringTemplate.create("DM_ALM_STATO_WORKITEM_SEQ.nextval"),
							null,
							null,
							statoWorkitem.get(dmAlmStatoWorkitem.id),
							statoWorkitem.get(dmAlmStatoWorkitem.name),
							null,
							repository,
							workitemType,
							DataEsecuzione.getInstance().getDataEsecuzione(),
							EnumUtils.getTemplateByWorkItem(workitemType)
					).execute();
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

	public static void recoverStatoWorkitem() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmAlmStatoWorkitem qDmAlmStatoWorkitem = QDmAlmStatoWorkitem.dmAlmStatoWorkitem;

			new SQLDeleteClause(connection, dialect, qDmAlmStatoWorkitem)
					.where(qDmAlmStatoWorkitem.dataCaricamento
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