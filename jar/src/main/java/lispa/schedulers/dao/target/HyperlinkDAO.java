package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_HYPERLINK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmHyperlink;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmHyperlink;
import lispa.schedulers.utils.QueryUtils;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.DefaultMapper;
import com.mysema.query.sql.dml.SQLInsertClause;

public class HyperlinkDAO {

	private static Logger logger = Logger.getLogger(AttachmentDAO.class);

	private static QDmalmHyperlink hyp = QDmalmHyperlink.dmalmHyperlink;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static List<DmalmHyperlink> getAllHyperlink(Timestamp dataEsecuzione)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DmalmHyperlink bean = null;
		List<DmalmHyperlink> hyperlinks = new ArrayList<DmalmHyperlink>(
				QueryUtils.getCountHyperlinks());

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_HYPERLINK);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(DmAlmConstants.FETCH_SIZE);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");
			while (rs.next()) {
				int fk = LinkedWorkitemsDAO.getFactPkByType(
						rs.getString("TIPO_WORKITEM"),
						rs.getString("FK_WORKITEM"));
				if (fk == 0)
					continue;
				// Elabora il risultato
				bean = new DmalmHyperlink();

				bean.setDmalmWorkitemType(rs.getString("TIPO_WORKITEM"));
				bean.setDtCaricamento(dataEsecuzione);
				bean.setRuolo(rs.getString("RUOLO"));
				bean.setUri(rs.getString("URI"));
				bean.setStgPk(rs.getString("FK_WORKITEM"));
				bean.setDmalmFkWorkitem01(fk);

				hyperlinks.add(bean);
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

		return hyperlinks;

	}

	public static boolean insertHyperlink(DmalmHyperlink hyperlink)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			long pk = LinkedWorkitemsDAO.getFactPkByType(
					hyperlink.getDmalmWorkitemType(), hyperlink.getStgPk());

			if (pk == 0)
				return false;

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, hyp)
					.columns(hyp.dmalmWorkitemType, hyp.dtCaricamento,
							hyp.ruolo, hyp.uri, hyp.dmalmFkWorkitem01)
					.values(hyperlink.getDmalmWorkitemType(),
							hyperlink.getDtCaricamento(), hyperlink.getRuolo(),
							hyperlink.getUri(), pk).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return true;

	}

	public static int insertHyperlinks(Timestamp dataEsecuzione)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;

		int res = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_HYPERLINK);
			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			res = ps.executeUpdate();

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

		return res;
	}

	public static void insertHyperlinkList(
			List<DmalmHyperlink> staging_hyperlinks) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					hyp);
			int i = 0;
			for (DmalmHyperlink bean : staging_hyperlinks) {
				insert.populate(bean, DefaultMapper.WITH_NULL_BINDINGS)
						.addBatch();
				i++;
				if (i % DmAlmConstants.BATCH_SIZE == 0) {
					insert.execute();
					insert = new SQLInsertClause(connection, dialect, hyp);
				}
			}

			if (!insert.isEmpty())
				insert.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

}
