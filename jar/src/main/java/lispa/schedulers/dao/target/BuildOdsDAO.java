package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmBuild;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmBuildOds;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Projections;

public class BuildOdsDAO {
	private static Logger logger = Logger.getLogger(BuildOdsDAO.class);

	private static QDmalmBuildOds buildODS = QDmalmBuildOds.dmalmBuildOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, buildODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmBuild> staging_build,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		List <Long> listPk= new ArrayList<>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			for (DmalmBuild build : staging_build) {
				if(listPk.contains(build.getDmalmBuildPk()))
					logger.info("Trovata DmalmBuildPk DUPLICATA!!!"+build.getDmalmBuildPk());
				else{
					listPk.add(build.getDmalmBuildPk());
					new SQLInsertClause(connection, dialect, buildODS)
							.columns(buildODS.cdBuild, buildODS.descrizioneBuild,
									buildODS.dmalmBuildPk,
									buildODS.dmalmProjectFk02,
									buildODS.dmalmStatoWorkitemFk03,
									buildODS.dmalmStrutturaOrgFk01,
									buildODS.dmalmTempoFk04,
									buildODS.dsAutoreBuild,
									buildODS.dtCambioStatoBuild,
									buildODS.dtCaricamentoBuild,
									buildODS.dtCreazioneBuild,
									buildODS.dtModificaBuild,
									buildODS.dtRisoluzioneBuild,
									buildODS.dtScadenzaBuild,
									buildODS.dtStoricizzazione,
									buildODS.idAutoreBuild, buildODS.idRepository,
									buildODS.motivoRisoluzioneBuild,
									buildODS.rankStatoBuild, buildODS.titoloBuild,
									buildODS.stgPk, buildODS.codice,
									buildODS.dmalmUserFk06, buildODS.uri,
									buildODS.severity, buildODS.priority,
									buildODS.tagAlm, buildODS.tsTagAlm)
							.values(build.getCdBuild(),
									build.getDescrizioneBuild(),
									build.getDmalmBuildPk(),
									build.getDmalmProjectFk02(),
									build.getDmalmStatoWorkitemFk03(),
									build.getDmalmStrutturaOrgFk01(),
									build.getDmalmTempoFk04(),
									build.getDsAutoreBuild(),
									build.getDtCambioStatoBuild(),
									build.getDtCaricamentoBuild(),
									build.getDtCreazioneBuild(),
									build.getDtModificaBuild(),
									build.getDtRisoluzioneBuild(),
									build.getDtScadenzaBuild(),
									build.getDtModificaBuild(),
									build.getIdAutoreBuild(),
									build.getIdRepository(),
									build.getMotivoRisoluzioneBuild(),
									new Double(1), build.getTitoloBuild(),
									build.getStgPk(), build.getCodice(),
									build.getDmalmUserFk06(), build.getUri(),
									build.getSeverity(), build.getPriority(),
									build.getTagAlm(), build.getTsTagAlm())
							.execute();
				}
			}
			
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmBuild> getAll() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<DmalmBuild> list = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(buildODS).orderBy(buildODS.cdBuild.asc())
					.orderBy(buildODS.dtModificaBuild.asc())
					.list(Projections.bean(DmalmBuild.class, buildODS.all()));

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return list;
	}

}
