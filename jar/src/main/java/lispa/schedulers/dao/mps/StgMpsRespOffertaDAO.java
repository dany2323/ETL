package lispa.schedulers.dao.mps;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.mps.DmAlmMpsRespOfferta;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsRespOfferta;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsRespOffertaDAO {
	private static Logger logger = Logger.getLogger(StgMpsRespOffertaDAO.class);
	private static QDmalmStgMpsRespOfferta stgMpsRespOfferta = QDmalmStgMpsRespOfferta.dmalmStgMpsRespOfferta;
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;

	public static void fillStgMpsRespOfferta() throws DAOException, SQLException {

		DmAlmMpsRespOfferta dmAlmMpsRespOfferta = DmAlmMpsRespOfferta.dmalmMpsRespOfferta;
		List<Tuple> listMpsRespOfferta = new ArrayList<Tuple>();
		
		try {
			connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);
			listMpsRespOfferta = query
				.from(dmAlmMpsRespOfferta)
				.list(dmAlmMpsRespOfferta.all());
			
			int numRighe = 0;
			for (Tuple mpsRespOfferta : listMpsRespOfferta) {
				numRighe++;

				new SQLInsertClause(connection, dialect,
						stgMpsRespOfferta)
						.columns(
								stgMpsRespOfferta.idContratto,
								stgMpsRespOfferta.amNome,
								stgMpsRespOfferta.amDataFirma,
								stgMpsRespOfferta.amFirmato,
								stgMpsRespOfferta.samNome,
								stgMpsRespOfferta.samDataFirma,
								stgMpsRespOfferta.samFirmato,
								stgMpsRespOfferta.vdgNome,
								stgMpsRespOfferta.vdgDataFirma,
								stgMpsRespOfferta.vdgFirmato,
								stgMpsRespOfferta.dgNome,
								stgMpsRespOfferta.dgDataFirma,
								stgMpsRespOfferta.dgFirmato,
								stgMpsRespOfferta.cdNome,
								stgMpsRespOfferta.cdDataFirma,
								stgMpsRespOfferta.cdFirmato,
								stgMpsRespOfferta.copertinaFirmata,
								stgMpsRespOfferta.dataFirmaCopertina,
								stgMpsRespOfferta.statoRazionale,
								stgMpsRespOfferta.controller,
								stgMpsRespOfferta.dataVerifica,
								stgMpsRespOfferta.prossimoFirmatarioRazionale,
								stgMpsRespOfferta.razionaleDigitale,
								stgMpsRespOfferta.notaRazionale,
								stgMpsRespOfferta.motivazioneRigetto)
						.values(mpsRespOfferta.get(dmAlmMpsRespOfferta.idContratto),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.amNome),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.amDataFirma),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.amFirmato),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.samNome),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.samDataFirma),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.samFirmato),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.vdgNome),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.vdgDataFirma),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.vdgFirmato),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.dgNome),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.dgDataFirma),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.dgFirmato),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.cdNome),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.cdDataFirma),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.cdFirmato),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.copertinaFirmata),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.dataFirmaCopertina),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.statoRazionale),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.controller),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.dataVerifica),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.prossimoFirmatarioRazionale),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.razionaleDigitale),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.notaRazionale),
								mpsRespOfferta.get(dmAlmMpsRespOfferta.motivazioneRigetto))
						.execute();
			}
			connection.commit();

			logger.info("StgMpsRespOffertaDAO.fillStgMpsRespOfferta - righe inserite: " + numRighe);

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

			QDmalmStgMpsRespOfferta qstgmpsrespofferta = QDmalmStgMpsRespOfferta.dmalmStgMpsRespOfferta;

			new SQLDeleteClause(connection, dialect, qstgmpsrespofferta)
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMpsRespOfferta() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsRespOfferta qstgmpsrespofferta = QDmalmStgMpsRespOfferta.dmalmStgMpsRespOfferta;

			new SQLDeleteClause(connection, dialect, qstgmpsrespofferta)
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
