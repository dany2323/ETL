package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.mps.DmAlmMpsRilasci;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsRilasci;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsRilasciDAO {
	private static Logger logger = Logger.getLogger(StgMpsRilasciDAO.class);
	private static QDmalmStgMpsRilasci stgMpsRilasci = QDmalmStgMpsRilasci.dmalmStgMpsRilasci;
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;

	public static void fillStgMpsRilasci() throws DAOException, SQLException {
		DmAlmMpsRilasci dmAlmMpsRilasci = DmAlmMpsRilasci.dmalmMpsRilasci;
		List<Tuple> listMpsRilasci = new ArrayList<Tuple>();
		
		try {
			connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);
			listMpsRilasci = query
				.from(dmAlmMpsRilasci)
				.list(dmAlmMpsRilasci.all());
			
			int numRighe = 0;
			for (Tuple mpsRespRilasci : listMpsRilasci) {
				numRighe++;

				new SQLInsertClause(connection, dialect, stgMpsRilasci)
						.columns(stgMpsRilasci.idRilascio,
								stgMpsRilasci.idContratto,
								stgMpsRilasci.codRilascio,
								stgMpsRilasci.tipoRilascio,
								stgMpsRilasci.sottoTipoRilascio,
								stgMpsRilasci.titoloRilascio,
								stgMpsRilasci.desAttivita,
								stgMpsRilasci.dataInizio,
								stgMpsRilasci.dataRilascio,
								stgMpsRilasci.dataValidazione,
								stgMpsRilasci.statoFatturazione,
								stgMpsRilasci.statoFinanziamento,
								stgMpsRilasci.importoRilascio,
								stgMpsRilasci.totaleSpalmato,
								stgMpsRilasci.totaleVerbalizzato,
								stgMpsRilasci.totaleRichiesta,
								stgMpsRilasci.totaleFatturato,
								stgMpsRilasci.totaleFatturabile,
								stgMpsRilasci.dataRilascioEffettivo,
								stgMpsRilasci.varianteMigliorativa,
								stgMpsRilasci.statoVerbalizzazione)
						.values(mpsRespRilasci.get(dmAlmMpsRilasci.idRilascio),
								mpsRespRilasci.get(dmAlmMpsRilasci.idContratto),
								mpsRespRilasci.get(dmAlmMpsRilasci.codRilascio),
								mpsRespRilasci.get(dmAlmMpsRilasci.tipoRilascio),
								mpsRespRilasci.get(dmAlmMpsRilasci.sottoTipoRilascio),
								mpsRespRilasci.get(dmAlmMpsRilasci.titoloRilascio),
								mpsRespRilasci.get(dmAlmMpsRilasci.desAttivita),
								mpsRespRilasci.get(dmAlmMpsRilasci.dataInizio),
								mpsRespRilasci.get(dmAlmMpsRilasci.dataRilascio),
								mpsRespRilasci.get(dmAlmMpsRilasci.dataValidazione),
								mpsRespRilasci.get(dmAlmMpsRilasci.statoFatturazione),
								mpsRespRilasci.get(dmAlmMpsRilasci.statoFinanziamento),
								mpsRespRilasci.get(dmAlmMpsRilasci.importoRilascio),
								mpsRespRilasci.get(dmAlmMpsRilasci.totaleSpalmato),
								mpsRespRilasci.get(dmAlmMpsRilasci.totaleVerbalizzato),
								mpsRespRilasci.get(dmAlmMpsRilasci.totaleRichiesta),
								mpsRespRilasci.get(dmAlmMpsRilasci.totaleFatturato),
								mpsRespRilasci.get(dmAlmMpsRilasci.totaleFatturabile),
								mpsRespRilasci.get(dmAlmMpsRilasci.dataRilascioEffettivo),
								mpsRespRilasci.get(dmAlmMpsRilasci.varianteMigliorativa),
								mpsRespRilasci.get(dmAlmMpsRilasci.statoVerbalizzazione))
						.execute();
			}

			connection.commit();

			logger.info("StgMpsRilasciDAO.fillStgMpsRilasci righe inserite: " + numRighe);

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

			QDmalmStgMpsRilasci qstgmpsrilasci = QDmalmStgMpsRilasci.dmalmStgMpsRilasci;

			new SQLDeleteClause(connection, dialect, qstgmpsrilasci).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMpsRilasci() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsRilasci qstgmpsrilasci = QDmalmStgMpsRilasci.dmalmStgMpsRilasci;

			new SQLDeleteClause(connection, dialect, qstgmpsrilasci).execute();

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
