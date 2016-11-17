package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_STATOWORKITEM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmStatoWorkitem;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmStatoWorkitem;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StatoWorkitemSgrCmDAO {

	private static Logger logger = Logger
			.getLogger(StatoWorkitemSgrCmDAO.class);

	public static List<DmalmStatoWorkitem> getAllStatoWorkitem(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmStatoWorkitem bean = null;
		List<DmalmStatoWorkitem> stati = new ArrayList<DmalmStatoWorkitem>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_STATOWORKITEM);
			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmStatoWorkitem();

				bean.setDmalmStatoWorkitemPk(rs
						.getInt("DMALM_STATO_WORKITEM_PK"));
				bean.setCdStato(rs.getString("CD_STATO_ANOMALIA"));
				bean.setDsStato(rs.getString("DS_STATO_ANOMALIA"));
				bean.setOrigineStato(rs.getString("ORIGINE_STATO"));
				bean.setDtCaricamento(rs.getTimestamp("DT_CARICAMENTO"));
				bean.setTemplate(rs.getString("TEMPLATE"));

				stati.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return stati;
	}

	public static void insertStatoWorkitem(DmalmStatoWorkitem statoWorkitem)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			QDmalmStatoWorkitem stato = QDmalmStatoWorkitem.dmalmStatoWorkitem;

			new SQLInsertClause(connection, dialect, stato)
					.columns(stato.dmalmStatoWorkitemPrimaryKey, stato.cdStato,
							stato.dsStato, stato.dtCaricamento,
							stato.origineStato, stato.template)
					.values(statoWorkitem.getDmalmStatoWorkitemPk(),
							statoWorkitem.getCdStato(),
							statoWorkitem.getDsStato(),
							statoWorkitem.getDtCaricamento(),
							statoWorkitem.getOrigineStato(),
							statoWorkitem.getTemplate()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static List<Tuple> getStatoWorkitem(DmalmStatoWorkitem st)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> stati = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmStatoWorkitem stato = QDmalmStatoWorkitem.dmalmStatoWorkitem;

			stati = query
					.from(stato)
					.where(stato.cdStato.equalsIgnoreCase((st.getCdStato())))
					.where(stato.origineStato.equalsIgnoreCase((st
							.getOrigineStato())))
					.where(stato.template.equalsIgnoreCase((st.getTemplate())))
					.list(stato.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return stati;
	}
}