package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentRevision;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryProject;
import lispa.schedulers.svn.ProjectTemplateINI;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Template_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.expr.StringExpression;
import com.mysema.query.types.template.StringTemplate;

public class SissHistoryProjectDAO {

	private static Logger logger = Logger
			.getLogger(SissHistoryProjectDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject fonteProjects = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject.project;
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject fonteProjects2 = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject.project;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryProject stgProjects = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryProject.project;


	public static void fillSissHistoryProject(long minRevision, long maxRevision)
			throws Exception {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> projects = null;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentRevision stgRevision = QDmalmCurrentRevision.currentRevision;

		try {
			logger.debug("SissHistoryProjectDAO.fillSissHistoryProject - minRevision: " + minRevision + ", maxRevision: " + maxRevision);
			
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSISSHistory();
			projects = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates() 
			{
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query = new SQLQuery(pgConnection, dialect);

			projects = query
					.from(fonteProjects)
					.where(fonteProjects.cRev.gt(minRevision))
					.where(fonteProjects.cRev.loe(maxRevision))
					.where(fonteProjects.cLocation.notLike("default:/GRACO%"))
					.where(fonteProjects.cId.notIn(new SQLSubQuery()
							.from(fonteProjects2)
							.where(fonteProjects2.cName.like("%{READONLY}%"))
							.list(fonteProjects2.cId)))
					.list(
							fonteProjects.cTrackerprefix, 
							StringTemplate.create("0"),
							fonteProjects.cUri,
							fonteProjects.fkUriLead,
							fonteProjects.cDeleted, 
							fonteProjects.cFinish,
							fonteProjects.cUri,
							fonteProjects.cStart,
							fonteProjects.fkUriProjectgroup,
							fonteProjects.cActive, 
							fonteProjects.cLocation,
							StringTemplate.create("(select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".t_user where t_user.c_pk = fk_lead) as fk_rev_lead"),
							fonteProjects.cLockworkrecordsdate,
							fonteProjects.cName, 
							fonteProjects.cId,
							fonteProjects.cRev, 
							fonteProjects.cDescription
							);
			
			logger.debug("SissHistoryProjectDAO.fillSissHistoryProject - projects.size: " + (projects==null?"NULL":projects.size()));
			
			for (Tuple row : projects) {
				Object[] vals = row.toArray();
				
				String cUri = vals[2] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[2].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[2].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "")  : "";
				String cPk = cUri+"%"+ vals[15] != null ? vals[15].toString() : "";
				String fkUriLead = vals[3] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[3].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[3].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") : "";
				String fkLead = fkUriLead+"%"+ (vals[11] != null ? vals[11].toString() : "");
				String fkUriProjectgroup = vals[8] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[8].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[8].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") : "";
				String fkProjectgroup = fkUriProjectgroup+"%"+ (vals[11] != null ? vals[11].toString() : "");
				String cCreated = vals[15] != null ? (queryConnOracle(connOracle, dialect).from(stgRevision).where(stgRevision.cName.eq(Long.valueOf(vals[15].toString()))).where(stgRevision.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgRevision).where(stgRevision.cName.eq(Long.valueOf(vals[15].toString()))).where(stgRevision.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgRevision.cCreated).get(0).toString() : "") : "";
				
				//Applico il cast a timespent solo se esistono dei valori data 
				StringExpression dateValue = null;
				if(cCreated != "") {
					dateValue = StringTemplate.create("to_timestamp('"+cCreated+"', 'YYYY-MM-DD HH24:MI:SS.FF')");
				}

				new SQLInsertClause(connOracle, dialect, stgProjects)
						.columns(
								stgProjects.cTrackerprefix,
								stgProjects.cIsLocal,
								stgProjects.cPk,
								stgProjects.fkUriLead,
								stgProjects.cDeleted,
								stgProjects.cFinish,
								stgProjects.cUri,
								stgProjects.cStart,
								stgProjects.fkUriProjectgroup,
								stgProjects.cActive,
								stgProjects.cLocation,
								stgProjects.fkProjectgroup, 
								stgProjects.fkLead,
								stgProjects.cLockworkrecordsdate,
								stgProjects.cName, 
								stgProjects.cId,
//								stgProjects.dataCaricamento,
//								stgProjects.dmalmProjectPk,
								stgProjects.cRev,
								stgProjects.cCreated,
								stgProjects.cDescription
								)
						.values(
								vals[0],
								vals[1],
								cPk,
								StringUtils.getMaskedValue(fkUriLead),
								vals[4],
								vals[5],
								cUri,
								vals[7],
								fkUriProjectgroup,
								vals[9],
								vals[10],
								fkProjectgroup,
								StringUtils.getMaskedValue(fkLead),
								vals[12],
								vals[13],
								vals[14],
//								dataEsecuzione,
//								StringTemplate
//										.create("HISTORY_PROJECT_SEQ.nextval"),
								vals[15],
								dateValue,
								vals[16]
								
								
								)
						.execute();
			}

			connOracle.commit();
			ConnectionManager.getInstance().dismiss();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(pgConnection);
			if (cm != null)
				cm.closeConnection(connOracle);
		}
	}

	public static long getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		try {

			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			QSissHistoryProject stgProjects = QSissHistoryProject.sissHistoryProject;
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);

			max = query.from(stgProjects).list(stgProjects.cRev.max());

			if (max == null || max.size() == 0 || max.get(0) == null) {
				return 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(oracle);
		}

		return max.get(0).longValue();
	}

	
	public static List<String> getProjectsC_Name(List<Tuple> projects) {
		List<String> allc_name = new ArrayList<String>();
		try {

			String c_name = null;
			for (Tuple project : projects) {
				c_name = project.get(stgProjects.cName);
				allc_name.add(c_name);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {

		}

		return allc_name;
	}

	public static List<String> getProjectsC_Location(List<Tuple> projects) {
		List<String> allC_location = new ArrayList<String>();
		try {

			String c_location = null;
			for (Tuple project : projects) {
				c_location = project.get(stgProjects.cLocation);
				allC_location.add(c_location);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {

		}

		return allC_location;
	}


	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stgProjects)
				.execute();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(OracleConnection);
			}
		}
	}
	private static SQLQuery queryConnOracle(Connection connOracle, PostgresTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}
}