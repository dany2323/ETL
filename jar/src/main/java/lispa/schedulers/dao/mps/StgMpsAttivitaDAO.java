package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.mps.DmAlmMpsAttivita;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsAttivita;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsAttivitaDAO {

	private static Logger logger = Logger.getLogger(StgMpsAttivitaDAO.class);
	private static QDmalmStgMpsAttivita stgMpsAttivita = QDmalmStgMpsAttivita.dmalmStgMpsAttivita;
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;

	public static void fillStgMpsAttivita() throws DAOException, SQLException {
		
		DmAlmMpsAttivita dmAlmMpsAttivita = DmAlmMpsAttivita.dmalmMpsAttivita;
		List<Tuple> listMpsAttivita = new ArrayList<Tuple>();
		
		try {
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			
			SQLQuery query = new SQLQuery(connection, dialect);
			listMpsAttivita = query
					.from(dmAlmMpsAttivita)
					.list(dmAlmMpsAttivita.all());
			
			int numRighe = 0;
			for (Tuple mpsAttivita : listMpsAttivita) {
				numRighe++;

				new SQLInsertClause(connection, dialect, stgMpsAttivita)
						.columns(
								stgMpsAttivita.idAttivitaPadre,
								stgMpsAttivita.idAttivita,
								stgMpsAttivita.idContratto,
								stgMpsAttivita.codAttivita,
								stgMpsAttivita.titolo,
								stgMpsAttivita.desAttivita,
								stgMpsAttivita.dataInizio,
								stgMpsAttivita.dataFine,
								stgMpsAttivita.avanzamento,
								stgMpsAttivita.dataUltimoAvanzamento,
								stgMpsAttivita.tipoAttivita,
								stgMpsAttivita.stato,
								stgMpsAttivita.inseritoDa,
								stgMpsAttivita.inseritoIl,
								stgMpsAttivita.modificatoDa,
								stgMpsAttivita.modificatoIl,
								stgMpsAttivita.recordStatus)
						.values(
								mpsAttivita.get(dmAlmMpsAttivita.idAttivitaPadre),
								mpsAttivita.get(dmAlmMpsAttivita.idAttivita),
								mpsAttivita.get(dmAlmMpsAttivita.idContratto),
								mpsAttivita.get(dmAlmMpsAttivita.codAttivita),
								mpsAttivita.get(dmAlmMpsAttivita.titolo),
								mpsAttivita.get(dmAlmMpsAttivita.desAttivita),
								mpsAttivita.get(dmAlmMpsAttivita.dataInizio),
								mpsAttivita.get(dmAlmMpsAttivita.dataFine),
								mpsAttivita.get(dmAlmMpsAttivita.avanzamento),
								mpsAttivita.get(dmAlmMpsAttivita.dataUltimoAvanzamento),
								mpsAttivita.get(dmAlmMpsAttivita.tipoAttivita),
								mpsAttivita.get(dmAlmMpsAttivita.stato),
								mpsAttivita.get(dmAlmMpsAttivita.inseritoDa),
								mpsAttivita.get(dmAlmMpsAttivita.inseritoIl),
								mpsAttivita.get(dmAlmMpsAttivita.modificatoDa),
								mpsAttivita.get(dmAlmMpsAttivita.modificatoIl),
								mpsAttivita.get(dmAlmMpsAttivita.recordStatus))
						.execute();
			}

			connection.commit();

			logger.info("StgMpsAttivitaDAO.fillStgMpsAttivita - righe inserite: " + numRighe);
			
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

			QDmalmStgMpsAttivita qstgmpsattivita = QDmalmStgMpsAttivita.dmalmStgMpsAttivita;

			new SQLDeleteClause(connection, dialect, qstgmpsattivita).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMpsAttivita() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsAttivita qstgmpsattivita = QDmalmStgMpsAttivita.dmalmStgMpsAttivita;

			new SQLDeleteClause(connection, dialect, qstgmpsattivita).execute();

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
