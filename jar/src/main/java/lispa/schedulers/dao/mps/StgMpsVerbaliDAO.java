package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.mps.DmAlmMpsVerbali;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsVerbali;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsVerbaliDAO {
	private static Logger logger = Logger.getLogger(StgMpsVerbaliDAO.class);
	private static QDmalmStgMpsVerbali stgMpsVerbali = QDmalmStgMpsVerbali.dmalmStgMpsVerbali;
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;

	public static void fillStgMpsVerbali() throws DAOException, SQLException {
		DmAlmMpsVerbali dmAlmMpsVerbali = DmAlmMpsVerbali.dmalmMpsVerbali;
		List<Tuple> listMpsVerbali = new ArrayList<Tuple>();
		
		try {
			connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);
			listMpsVerbali = query
				.from(dmAlmMpsVerbali)
				.list(dmAlmMpsVerbali.all());
			
			int numRighe = 0;
			for (Tuple mpsRespVerbali : listMpsVerbali) {
				numRighe++;

				new SQLInsertClause(connection, dialect, stgMpsVerbali)
						.columns(stgMpsVerbali.codVerbale,
								stgMpsVerbali.dataVerbale,
								stgMpsVerbali.dataFirma,
								stgMpsVerbali.note,
								stgMpsVerbali.conforme,
								stgMpsVerbali.tipoVerbale,
								stgMpsVerbali.statoVerbale,
								stgMpsVerbali.totaleVerbale,
								stgMpsVerbali.fatturatoVerbale,
								stgMpsVerbali.prossimoFirmatario,
								stgMpsVerbali.firmaDigitale,
								stgMpsVerbali.idVerbaleValidazione)
						.values(mpsRespVerbali.get(dmAlmMpsVerbali.codVerbale),
								mpsRespVerbali.get(dmAlmMpsVerbali.dataVerbale),
								mpsRespVerbali.get(dmAlmMpsVerbali.dataFirma),
								mpsRespVerbali.get(dmAlmMpsVerbali.note),
								mpsRespVerbali.get(dmAlmMpsVerbali.conforme),
								mpsRespVerbali.get(dmAlmMpsVerbali.tipoVerbale),
								mpsRespVerbali.get(dmAlmMpsVerbali.statoVerbale),
								mpsRespVerbali.get(dmAlmMpsVerbali.totaleVerbale),
								mpsRespVerbali.get(dmAlmMpsVerbali.fatturatoVerbale),
								mpsRespVerbali.get(dmAlmMpsVerbali.prossimoFirmatario),
								mpsRespVerbali.get(dmAlmMpsVerbali.firmaDigitale),
								mpsRespVerbali.get(dmAlmMpsVerbali.idVerbaleValidazione))
						.execute();
			}

			connection.commit();

			logger.info("StgMpsVerbaliDAO.fillStgMpsVerbali righe inserite: " + numRighe);

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

			QDmalmStgMpsVerbali qstgmpsverbali = QDmalmStgMpsVerbali.dmalmStgMpsVerbali;

			new SQLDeleteClause(connection, dialect, qstgmpsverbali).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMpsVerbali() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsVerbali qstgmpsverbali = QDmalmStgMpsVerbali.dmalmStgMpsVerbali;

			new SQLDeleteClause(connection, dialect, qstgmpsverbali).execute();

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
