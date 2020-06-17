package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_AREATEMATICA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmAreaTematica;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmAreaTematica;
import lispa.schedulers.queryimplementation.target.QDmalmProject;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.sql.oracle.OracleGrammar;

public class AreaTematicaSgrCmDAO {

	private static Logger logger = Logger.getLogger(AreaTematicaSgrCmDAO.class);

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static List<DmalmAreaTematica> getAllAreaTematica(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmAreaTematica bean = null;
		List<DmalmAreaTematica> aree = new ArrayList<DmalmAreaTematica>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_AREATEMATICA);

			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmAreaTematica();

				bean.setDmalmAreaTematicaPk(rs.getInt("DMALM_AREA_TEMATICA_PK"));
				bean.setCdAreaTematica(rs.getString("CD_AREA_TEMATICA"));
				bean.setDsAreaTematica(rs.getString("DS_AREA_TEMATICA"));
				bean.setDtCaricamento(rs.getTimestamp("DT_CARICAMENTO"));

				aree.add(bean);
			}

			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
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

		return aree;
	}

	public static void updateDmalmAreaTematica(DmalmAreaTematica areaTematica)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QDmalmAreaTematica area = QDmalmAreaTematica.dmalmAreaTematica;

			new SQLUpdateClause(connection, dialect, area)
					.where(area.cdAreaTematica.eq(areaTematica
							.getCdAreaTematica()))
					.set(area.dsAreaTematica, areaTematica.getDsAreaTematica())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertAreaTematica(DmalmAreaTematica areaTematica)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QDmalmAreaTematica area = QDmalmAreaTematica.dmalmAreaTematica;

			new SQLInsertClause(connection, dialect, area)
					.columns(area.dmalmAreaTematicaPrimaryKey,
							area.cdAreaTematica, area.dsAreaTematica,
							area.dtCaricamento)
					.values(areaTematica.getDmalmAreaTematicaPk(),
							areaTematica.getCdAreaTematica(),
							areaTematica.getDsAreaTematica(),
							areaTematica.getDtCaricamento()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static List<Tuple> getAreaTematica(DmalmAreaTematica ar)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> aree = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmAreaTematica area = QDmalmAreaTematica.dmalmAreaTematica;

			aree = query
					.from(area)
					.where(area.cdAreaTematica.equalsIgnoreCase((ar
							.getCdAreaTematica()))).list(area.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return aree;
	}

	public static int getIdAreaTematicabyCodice(String cdAreaTematica)
			throws DAOException

	{
		if (cdAreaTematica == null)
			return 0;

		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> aree = new ArrayList<Integer>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmAreaTematica area = QDmalmAreaTematica.dmalmAreaTematica;

			aree = query
					.from(area)
					.where(area.cdAreaTematica.equalsIgnoreCase(cdAreaTematica))
					.where(OracleGrammar.rownum.eq(1))
					.list(area.dmalmAreaTematicaPrimaryKey);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return aree.size() > 0 ? aree.get(0).intValue() : 0;
	}

	public static int getAreaTematicaPKByProjectPK(Long projectPK)
			throws DAOException

	{
		if (projectPK == 0)
			return 0;

		ConnectionManager cm = null;
		Connection connection = null;

		List<Integer> aree = new ArrayList<Integer>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmProject project = QDmalmProject.dmalmProject;

			aree = query.from(project)
					.where(project.dmalmProjectPrimaryKey.eq(projectPK))
					.list(project.dmalmAreaTematicaFk01);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return aree.size() > 0 ? aree.get(0).intValue() : 0;
	}

}