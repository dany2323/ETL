package lispa.schedulers.svn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.fonte.sgr.xml.DmAlmTemplateProject;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

public class ProjectTemplateINI {

	private static Logger logger = Logger.getLogger(ProjectTemplateINI.class);

	public static String getTemplateIniFile(String projectLocation,
			long revision, String repository) throws Exception {

		DmAlmTemplateProject dmAlmTemplateProject = DmAlmTemplateProject.dmAlmTemplateProject;
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;
		String templateId = "";
		List<String> listTemplateProject = new ArrayList<String>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			listTemplateProject = query
				.from(dmAlmTemplateProject)
				.where(dmAlmTemplateProject.pathLocation.equalsIgnoreCase(projectLocation))
				.where(dmAlmTemplateProject.rev.eq(revision))
				.where(dmAlmTemplateProject.idRepository.equalsIgnoreCase(repository))
				.list(dmAlmTemplateProject.templateId);
			if (listTemplateProject.size() > 0) {
				if (listTemplateProject.get(0) != null) {
					templateId = listTemplateProject.get(0);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		return templateId;
	}

	public static String getLastRevisionTemplateIniFile(String projectLocation,
			String repository) throws Exception {

		DmAlmTemplateProject dmAlmTemplateProject = DmAlmTemplateProject.dmAlmTemplateProject;
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;
		String templateId = "";
		List<String> listTemplateProject = new ArrayList<String>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			listTemplateProject = query
				.from(dmAlmTemplateProject)
				.where(dmAlmTemplateProject.pathLocation.equalsIgnoreCase(projectLocation))
				.where(dmAlmTemplateProject.rev.eq(Long.valueOf(-1)))
				.where(dmAlmTemplateProject.idRepository.equalsIgnoreCase(repository))
				.list(dmAlmTemplateProject.templateId);
			if (listTemplateProject.size() > 0) {
				if (listTemplateProject.get(0) != null) {
					templateId = listTemplateProject.get(0);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		return templateId;
	}

	public static String getProjectSVNPath(String clocation) {

		if (clocation.startsWith("default:/")) {
			int start = clocation.lastIndexOf(":/") + 1;

			int end = clocation.indexOf("polarion-project.xml") - 1;

			return clocation.substring(start, end).concat(
					"/template-version.ini");
		} else {
			return null;
		}
	}
}