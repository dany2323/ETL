package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SEQUENCE_DMALM_PROJECT_ROLES_SGR;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_PROJECTROLES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProjectRolesSgr;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmProjectRolesSgr;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class ProjectRolesSgrDAO {

	private static Logger logger = Logger.getLogger(ProjectRolesSgrDAO.class);

	public static List<DmalmProjectRolesSgr> getAllProjectRoles(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmProjectRolesSgr bean = null;
		List<DmalmProjectRolesSgr> userroles = new ArrayList<DmalmProjectRolesSgr>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_PROJECTROLES);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			int i = 0;
			while (rs.next()) {

				i = i + 1;

				bean = new DmalmProjectRolesSgr();
				bean.setTipologiaProject(rs.getString("TIPOLOGIA_PROJECT"));
				bean.setRuolo(rs.getString("RUOLO"));
				bean.setDtCaricamento(dataEsecuzione);
				bean.setDtInizioValidita(DateUtils.setDtInizioValidita1900());
				bean.setDtFineValidita(DateUtils.setDtFineValidita9999());

				userroles.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return userroles;
	}

	public static void insertProjectRolesSgr(DmalmProjectRolesSgr bean)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			QDmalmProjectRolesSgr dmalmProjectRolesSgr = QDmalmProjectRolesSgr.dmalmProjectRolesSgr;

			new SQLInsertClause(connection, dialect, dmalmProjectRolesSgr)
					.columns(dmalmProjectRolesSgr.dmalmProjectRolesPrimaryKey,
							dmalmProjectRolesSgr.tipologiaProject,
							dmalmProjectRolesSgr.ruolo,
							dmalmProjectRolesSgr.dtCaricamento,
							dmalmProjectRolesSgr.dtInizioValidita,
							dmalmProjectRolesSgr.dtFineValidita)
					.values(StringTemplate
							.create(SEQUENCE_DMALM_PROJECT_ROLES_SGR),
							bean.getTipologiaProject(), bean.getRuolo(),
							bean.getDtCaricamento(),
							bean.getDtInizioValidita(),
							bean.getDtFineValidita()).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static List<Tuple> getDmalmProjectRolesSgr(DmalmProjectRolesSgr bean)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> classificatori = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmProjectRolesSgr dmalmProjectRolesSgr = QDmalmProjectRolesSgr.dmalmProjectRolesSgr;

			classificatori = query
					.from(dmalmProjectRolesSgr)

					.where(dmalmProjectRolesSgr.tipologiaProject
							.equalsIgnoreCase(bean.getTipologiaProject()))
					.where(dmalmProjectRolesSgr.ruolo.equalsIgnoreCase(bean
							.getRuolo()))
					.where(dmalmProjectRolesSgr.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))

					.list(dmalmProjectRolesSgr.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return classificatori;
	}

}