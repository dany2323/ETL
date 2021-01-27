package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.mps.DmAlmMpsRespContratto;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsRespContratto;
import lispa.schedulers.utils.NumberUtils;
import lispa.schedulers.utils.StringUtils;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsRespContrattoDAO {
	
	private static Logger logger = Logger.getLogger(StgMpsRespContrattoDAO.class);
	private static QDmalmStgMpsRespContratto stgMpsRespContratto = QDmalmStgMpsRespContratto.dmalmStgMpsRespContratto;
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;

	public static void fillStgMpsRespContratto() throws DAOException, SQLException {

		DmAlmMpsRespContratto dmAlmMpsRespContratto = DmAlmMpsRespContratto.dmalmMpsRespContratto;
		List<Tuple> listMpsRespContratto = new ArrayList<Tuple>();
		
		try {
			connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);
			listMpsRespContratto = query
				.from(dmAlmMpsRespContratto)
				.list(dmAlmMpsRespContratto.all());
			
			int numRighe = 0;
			for (Tuple mpsRespContratto : listMpsRespContratto) {
				numRighe++;

				new SQLInsertClause(connection, dialect,
						stgMpsRespContratto)
						.columns(
								stgMpsRespContratto.idContratto,
								stgMpsRespContratto.idUtente,
								stgMpsRespContratto.responsabile,
								stgMpsRespContratto.desTipologiaResponsabile,
								stgMpsRespContratto.firmatario,
								stgMpsRespContratto.ordineFirma,
								stgMpsRespContratto.firmato,
								stgMpsRespContratto.dataFirma,
								stgMpsRespContratto.idEnte,
								stgMpsRespContratto.ente)
						.values(mpsRespContratto.get(dmAlmMpsRespContratto.idContratto),
								NumberUtils.getMaskedValue(mpsRespContratto.get(dmAlmMpsRespContratto.idUtente)),
								StringUtils.getMaskedValue(mpsRespContratto.get(dmAlmMpsRespContratto.responsabile)),
								mpsRespContratto.get(dmAlmMpsRespContratto.desTipologiaResponsabile),
								mpsRespContratto.get(dmAlmMpsRespContratto.firmatario),
								mpsRespContratto.get(dmAlmMpsRespContratto.ordineFirma),
								mpsRespContratto.get(dmAlmMpsRespContratto.firmato),
								mpsRespContratto.get(dmAlmMpsRespContratto.dataFirma),
								NumberUtils.getMaskedValue(mpsRespContratto.get(dmAlmMpsRespContratto.idEnte)),
								StringUtils.getMaskedValue(mpsRespContratto.get(dmAlmMpsRespContratto.ente)))
						.execute();
			}

			connection.commit();

			logger.info("StgMpsRespContrattoDAO.fillStgMpsRespContratto - righe inserite: " + numRighe);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void delete() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsRespContratto qstgmpsrespcontratto = QDmalmStgMpsRespContratto.dmalmStgMpsRespContratto;

			new SQLDeleteClause(connection, dialect, qstgmpsrespcontratto)
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMpsRespContratto() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsRespContratto qstgmpsrespcontratto = QDmalmStgMpsRespContratto.dmalmStgMpsRespContratto;

			new SQLDeleteClause(connection, dialect, qstgmpsrespcontratto)
					.execute();

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
