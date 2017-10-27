package lispa.schedulers.dao.target.fatti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmBuild;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmBuild;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class BuildDAO {

	private static Logger logger = Logger.getLogger(BuildDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmBuild build = QDmalmBuild.dmalmBuild;

	public static List<DmalmBuild> getAllBuild(Timestamp dataEsecuzione)
			throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmBuild bean = null;
		List<DmalmBuild> builds = new ArrayList<DmalmBuild>(
				QueryUtils.getCountByWIType(Workitem_Type.build));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_BUILD);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);
			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmBuild();

				bean.setCdBuild(rs.getString("CD_BUILD"));
				bean.setDescrizioneBuild(rs.getString("DESCRIPTION"));
				bean.setDmalmBuildPk(rs.getInt("DMALM_BUILD_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreBuild(rs.getString("NOME_AUTORE_BUILD"));
				bean.setDtCaricamentoBuild(dataEsecuzione);
				bean.setDtCreazioneBuild(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaBuild(rs.getTimestamp("DATA_MODIFICA_BUILD"));
				bean.setIdAutoreBuild(rs.getString("ID_AUTORE_BUILD"));
				bean.setDtRisoluzioneBuild(rs
						.getTimestamp("DATA_RISOLUZIONE_BUILD"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setDtScadenzaBuild(rs.getTimestamp("DATA_SCADENZA_BUILD"));
				bean.setMotivoRisoluzioneBuild(rs
						.getString("MOTIVO_RISOLUZIONE_BUILD"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setTitoloBuild(rs.getString("TITOLO_BUILD"));
				bean.setStgPk(rs.getString("STG_BUILD_PK"));
				bean.setUri(rs.getString("URI_WI"));
				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));

				builds.add(bean);
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

		return builds;
	}

	public static List<Tuple> getBuild(DmalmBuild build) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> builds = new LinkedList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			builds = query
					.from(BuildDAO.build)
					.where(BuildDAO.build.cdBuild.equalsIgnoreCase(build
							.getCdBuild()))
					.where(BuildDAO.build.idRepository.equalsIgnoreCase(build
							.getIdRepository()))
					.where(BuildDAO.build.rankStatoBuild.eq(new Double(1)))
					.list(BuildDAO.build.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return builds;

	}

	public static void insertBuild(DmalmBuild build) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, BuildDAO.build)
					.columns(BuildDAO.build.cdBuild,
							BuildDAO.build.descrizioneBuild,
							BuildDAO.build.dmalmBuildPk,
							BuildDAO.build.dmalmProjectFk02,
							BuildDAO.build.dmalmStatoWorkitemFk03,
							BuildDAO.build.dmalmStrutturaOrgFk01,
							BuildDAO.build.dmalmTempoFk04,
							BuildDAO.build.dsAutoreBuild,
							BuildDAO.build.dtCambioStatoBuild,
							BuildDAO.build.dtCaricamentoBuild,
							BuildDAO.build.dtCreazioneBuild,
							BuildDAO.build.dtModificaBuild,
							BuildDAO.build.dtRisoluzioneBuild,
							BuildDAO.build.dtScadenzaBuild,
							BuildDAO.build.dtStoricizzazione,
							BuildDAO.build.idAutoreBuild,
							BuildDAO.build.idRepository,
							BuildDAO.build.motivoRisoluzioneBuild,
							BuildDAO.build.rankStatoBuild,
							BuildDAO.build.titoloBuild, BuildDAO.build.stgPk,
							BuildDAO.build.codice,
							BuildDAO.build.dmalmUserFk06, BuildDAO.build.uri,
							BuildDAO.build.dtAnnullamento,
							BuildDAO.build.severity, BuildDAO.build.priority)
					.values(build.getCdBuild(), build.getDescrizioneBuild(),
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
							build.getIdAutoreBuild(), build.getIdRepository(),
							build.getMotivoRisoluzioneBuild(), new Double(1),
							build.getTitoloBuild(), build.getStgPk(),
							build.getCodice(), build.getDmalmUserFk06(),
							build.getUri(), build.getDtAnnullamento(),
							build.getSeverity(), build.getPriority())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmBuild build, Double double1)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, BuildDAO.build)
					.where(BuildDAO.build.cdBuild.equalsIgnoreCase(build
							.getCdBuild()))
					.where(BuildDAO.build.idRepository.equalsIgnoreCase(build
							.getIdRepository()))
					.set(BuildDAO.build.rankStatoBuild, double1).execute();
			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertBuildUpdate(Timestamp dataEsecuzione,
			DmalmBuild build, boolean pkValue) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, BuildDAO.build)
					.columns(BuildDAO.build.cdBuild,
							BuildDAO.build.descrizioneBuild,
							BuildDAO.build.dmalmBuildPk,
							BuildDAO.build.dmalmProjectFk02,
							BuildDAO.build.dmalmStatoWorkitemFk03,
							BuildDAO.build.dmalmStrutturaOrgFk01,
							BuildDAO.build.dmalmTempoFk04,
							BuildDAO.build.dsAutoreBuild,
							BuildDAO.build.dtCambioStatoBuild,
							BuildDAO.build.dtCaricamentoBuild,
							BuildDAO.build.dtCreazioneBuild,
							BuildDAO.build.dtModificaBuild,
							BuildDAO.build.dtRisoluzioneBuild,
							BuildDAO.build.dtScadenzaBuild,
							BuildDAO.build.dtStoricizzazione,
							BuildDAO.build.idAutoreBuild,
							BuildDAO.build.idRepository,
							BuildDAO.build.motivoRisoluzioneBuild,
							BuildDAO.build.rankStatoBuild,
							BuildDAO.build.titoloBuild, BuildDAO.build.stgPk,
							BuildDAO.build.codice,
							BuildDAO.build.dmalmUserFk06, BuildDAO.build.uri,
							BuildDAO.build.dtAnnullamento,
							BuildDAO.build.changed, BuildDAO.build.annullato,
							BuildDAO.build.severity, BuildDAO.build.priority)
					.values(build.getCdBuild(),
							build.getDescrizioneBuild(),
							pkValue == true ? build.getDmalmBuildPk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
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
							pkValue == true ? build.getDtModificaBuild()
									: build.getDtStoricizzazione(),
							build.getIdAutoreBuild(), build.getIdRepository(),
							build.getMotivoRisoluzioneBuild(), 
							pkValue == true ? new Short("1")  : build.getRankStatoBuild(),
							build.getTitoloBuild(), build.getStgPk(),
							build.getCodice(), build.getDmalmUserFk06(),
							build.getUri(), build.getDtAnnullamento(),
							build.getChanged(), build.getAnnullato(),
							build.getSeverity(), build.getPriority()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateBuild(DmalmBuild build) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			new SQLUpdateClause(connection, dialect, BuildDAO.build)
					.where(BuildDAO.build.cdBuild.equalsIgnoreCase(build
							.getCdBuild()))
					.where(BuildDAO.build.idRepository.equalsIgnoreCase(build
							.getIdRepository()))
					.where(BuildDAO.build.rankStatoBuild.eq(new Double(1)))
					.set(BuildDAO.build.cdBuild, build.getCdBuild())
					.set(BuildDAO.build.descrizioneBuild,
							build.getDescrizioneBuild())
					.set(BuildDAO.build.dmalmProjectFk02,
							build.getDmalmProjectFk02())
					.set(BuildDAO.build.dmalmStatoWorkitemFk03,
							build.getDmalmStatoWorkitemFk03())
					.set(BuildDAO.build.dmalmStrutturaOrgFk01,
							build.getDmalmStrutturaOrgFk01())
					.set(BuildDAO.build.dmalmTempoFk04,
							build.getDmalmTempoFk04())
					.set(BuildDAO.build.dsAutoreBuild, build.getDsAutoreBuild())
					.set(BuildDAO.build.dtCreazioneBuild,
							build.getDtCreazioneBuild())
					.set(BuildDAO.build.dtModificaBuild,
							build.getDtModificaBuild())
					.set(BuildDAO.build.dtRisoluzioneBuild,
							build.getDtRisoluzioneBuild())
					.set(BuildDAO.build.dtScadenzaBuild,
							build.getDtScadenzaBuild())
					.set(BuildDAO.build.idAutoreBuild, build.getIdAutoreBuild())
					.set(BuildDAO.build.idRepository, build.getIdRepository())
					.set(BuildDAO.build.motivoRisoluzioneBuild,
							build.getMotivoRisoluzioneBuild())
					.set(BuildDAO.build.titoloBuild, build.getTitoloBuild())
					.set(BuildDAO.build.stgPk, build.getStgPk())
					.set(BuildDAO.build.uri, build.getUri())
					.set(BuildDAO.build.codice, build.getCodice())
					.set(BuildDAO.build.dtAnnullamento,
							build.getDtAnnullamento())
					.set(BuildDAO.build.annullato, build.getAnnullato())
					.set(BuildDAO.build.severity, build.getSeverity())
					.set(BuildDAO.build.priority, build.getPriority())
					.execute();
					
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmBuild getBuild(Integer pk) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> builds = new LinkedList<Tuple>();
		QDmalmBuild bb = QDmalmBuild.dmalmBuild;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			builds = query.from(bb).where(bb.dmalmBuildPk.eq(pk))
					.list(BuildDAO.build.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (builds.size() > 0) {
			Tuple t = builds.get(0);
			DmalmBuild b = new DmalmBuild();

			b.setAnnullato(t.get(bb.annullato));
			b.setCdBuild(t.get(bb.cdBuild));
			b.setChanged(t.get(bb.changed));
			b.setCodice(t.get(bb.codice));
			b.setDescrizioneBuild(t.get(bb.descrizioneBuild));
			b.setDmalmBuildPk(t.get(bb.dmalmBuildPk));
			b.setDmalmProjectFk02(t.get(bb.dmalmProjectFk02));
			b.setDmalmStatoWorkitemFk03(t.get(bb.dmalmStatoWorkitemFk03));
			b.setDmalmStrutturaOrgFk01(t.get(bb.dmalmStrutturaOrgFk01));
			b.setDmalmTempoFk04(t.get(bb.dmalmTempoFk04));
			b.setDmalmUserFk06(t.get(bb.dmalmUserFk06));
			b.setDsAutoreBuild(t.get(bb.dsAutoreBuild));
			b.setDtAnnullamento(t.get(bb.dtAnnullamento));
			b.setDtCambioStatoBuild(t.get(bb.dtCambioStatoBuild));
			b.setDtCaricamentoBuild(t.get(bb.dtCaricamentoBuild));
			b.setDtCreazioneBuild(t.get(bb.dtCreazioneBuild));
			b.setDtModificaBuild(t.get(bb.dtModificaBuild));
			b.setDtRisoluzioneBuild(t.get(bb.dtRisoluzioneBuild));
			b.setDtScadenzaBuild(t.get(bb.dtScadenzaBuild));
			b.setDtStoricizzazione(t.get(bb.dtStoricizzazione));
			b.setIdAutoreBuild(t.get(bb.idAutoreBuild));
			b.setIdRepository(t.get(bb.idRepository));
			b.setMotivoRisoluzioneBuild(t.get(bb.motivoRisoluzioneBuild));
			b.setRankStatoBuild(t.get(bb.rankStatoBuild));
			b.setRankStatoBuildMese(t.get(bb.rankStatoBuildMese));
			b.setStgPk(t.get(bb.stgPk));
			b.setTitoloBuild(t.get(bb.titoloBuild));
			b.setUri(t.get(bb.uri));
			//DM_ALM-320
			b.setSeverity(t.get(bb.severity));
			b.setPriority(t.get(bb.priority));

			return b;

		} else
			return null;
	}

	public static boolean checkEsistenzaBuild(DmalmBuild b, DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(build)
					.where(build.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(build.cdBuild.eq(b.getCdBuild()))
					.where(build.idRepository.eq(b.getIdRepository()))
					.list(build.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
