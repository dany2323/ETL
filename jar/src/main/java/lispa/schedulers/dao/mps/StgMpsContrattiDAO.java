package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.mps.DmAlmMpsContratti;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsContratti;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsContrattiDAO {

	private static Logger logger = Logger.getLogger(StgMpsContrattiDAO.class);
	private static QDmalmStgMpsContratti stgMpsContratti = QDmalmStgMpsContratti.dmalmStgMpsContratti;
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;

	public static void fillStgMpsContratti() throws DAOException, SQLException {

		DmAlmMpsContratti dmAlmMpsContratti = DmAlmMpsContratti.dmalmMpsContratti;
		List<Tuple> listMpsContratti = new ArrayList<Tuple>();
		
		try {
			connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);
			listMpsContratti = query
				.from(dmAlmMpsContratti)
				.list(dmAlmMpsContratti.all());
			
			int numRighe = 0;
			for (Tuple mpsContratti : listMpsContratti) {
				numRighe++;

				new SQLInsertClause(connection, dialect, stgMpsContratti)
						.columns(stgMpsContratti.idContratto,
								stgMpsContratti.codContratto,
								stgMpsContratti.titoloContratto,
								stgMpsContratti.annoRiferimento,
								stgMpsContratti.dataInizio,
								stgMpsContratti.dataFine,
								stgMpsContratti.tipo,
								stgMpsContratti.stato,
								stgMpsContratti.firmaDigitale,
								stgMpsContratti.variato,
								stgMpsContratti.numVariazioni,
								stgMpsContratti.codDirezione,
								stgMpsContratti.desDirezione,
								stgMpsContratti.codUo,
								stgMpsContratti.desUo,
								stgMpsContratti.codStruttura,
								stgMpsContratti.desStruttura,
								stgMpsContratti.totaleContratto,
								stgMpsContratti.totaleImpegnato,
								stgMpsContratti.totaleSpalmato,
								stgMpsContratti.totaleVerbalizzato,
								stgMpsContratti.totaleRichiesto,
								stgMpsContratti.totaleFatturato,
								stgMpsContratti.totaleFatturabile,
								stgMpsContratti.prossimoFirmatario,
								stgMpsContratti.inCorsoIl,
								stgMpsContratti.numeroRilasci,
								stgMpsContratti.numeroRilasciForfait,
								stgMpsContratti.numeroRilasciCanone,
								stgMpsContratti.numeroRilasciConsumo,
								stgMpsContratti.numeroVerbali,
								stgMpsContratti.numeroVerbaliForfait,
								stgMpsContratti.numeroVerbaliConsumo,
								stgMpsContratti.desMotivoVariazione,
								stgMpsContratti.repository,
								stgMpsContratti.priorita,
								stgMpsContratti.idSm,
								stgMpsContratti.serviceManager)
						.values(mpsContratti.get(dmAlmMpsContratti.idContratto),
								mpsContratti.get(dmAlmMpsContratti.codContratto),
								mpsContratti.get(dmAlmMpsContratti.titoloContratto),
								mpsContratti.get(dmAlmMpsContratti.annoRiferimento),
								mpsContratti.get(dmAlmMpsContratti.dataInizio),
								mpsContratti.get(dmAlmMpsContratti.dataFine),
								mpsContratti.get(dmAlmMpsContratti.tipo),
								mpsContratti.get(dmAlmMpsContratti.stato),
								mpsContratti.get(dmAlmMpsContratti.firmaDigitale),
								mpsContratti.get(dmAlmMpsContratti.variato),
								mpsContratti.get(dmAlmMpsContratti.numVariazioni),
								mpsContratti.get(dmAlmMpsContratti.codDirezione),
								mpsContratti.get(dmAlmMpsContratti.desDirezione),
								mpsContratti.get(dmAlmMpsContratti.codUo),
								mpsContratti.get(dmAlmMpsContratti.desUo),
								mpsContratti.get(dmAlmMpsContratti.codStruttura),
								mpsContratti.get(dmAlmMpsContratti.desStruttura),
								mpsContratti.get(dmAlmMpsContratti.totaleContratto),
								mpsContratti.get(dmAlmMpsContratti.totaleImpegnato),
								mpsContratti.get(dmAlmMpsContratti.totaleSpalmato),
								mpsContratti.get(dmAlmMpsContratti.totaleVerbalizzato),
								mpsContratti.get(dmAlmMpsContratti.totaleRichiesto),
								mpsContratti.get(dmAlmMpsContratti.totaleFatturato),
								mpsContratti.get(dmAlmMpsContratti.totaleFatturabile),
								mpsContratti.get(dmAlmMpsContratti.prossimoFirmatario),
								mpsContratti.get(dmAlmMpsContratti.inCorsoIl),
								mpsContratti.get(dmAlmMpsContratti.numeroRilasci),
								mpsContratti.get(dmAlmMpsContratti.numeroRilasciForfait),
								mpsContratti.get(dmAlmMpsContratti.numeroRilasciCanone),
								mpsContratti.get(dmAlmMpsContratti.numeroRilasciConsumo),
								mpsContratti.get(dmAlmMpsContratti.numeroVerbali),
								mpsContratti.get(dmAlmMpsContratti.numeroVerbaliForfait),
								mpsContratti.get(dmAlmMpsContratti.numeroVerbaliConsumo),
								mpsContratti.get(dmAlmMpsContratti.desMotivoVariazione),
								mpsContratti.get(dmAlmMpsContratti.repository),
								mpsContratti.get(dmAlmMpsContratti.priorita),
								mpsContratti.get(dmAlmMpsContratti.idSm),
								mpsContratti.get(dmAlmMpsContratti.serviceManager))
						.execute();
			}

			connection.commit();
			
			logger.info("StgMpsContrattiDAO.fillStgMpsContratti - righe inserite: " + numRighe);
			
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

			QDmalmStgMpsContratti qstgmpscontratti = QDmalmStgMpsContratti.dmalmStgMpsContratti;

			new SQLDeleteClause(connection, dialect, qstgmpscontratti)
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMpsContratti() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsContratti qstgmpscontratti = QDmalmStgMpsContratti.dmalmStgMpsContratti;

			new SQLDeleteClause(connection, dialect, qstgmpscontratti)
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
