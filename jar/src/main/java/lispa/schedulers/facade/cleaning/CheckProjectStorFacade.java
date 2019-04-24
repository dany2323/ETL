package lispa.schedulers.facade.cleaning;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.log4j.Logger;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class CheckProjectStorFacade {

	private CheckProjectStorFacade() {
	}

	private static Logger logger = Logger
			.getLogger(CheckProjectStorFacade.class);

	public static void execute() {

		// ELETTRA/SGRCM
		if (!ExecutionManager.getInstance().isExecutionElettraSgrcm()) {
			return;
		}

		// Se si è verificato un errore precedente non eseguo l'elaborazione
		if (ErrorManager.getInstance().hasError()) {
			return;
		}

		logger.info("START CheckProjectStorFacade.execute()");

		try {

			storicizzaWI();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
		logger.info("STOP CheckProjectStorFacade");

	}

	private static void storicizzaWI() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection conn = null;
		try {
			Iterator<EnumWorkitemType> itEnumWiTypeKeySet = Workitem_Type
					.getEnumMapWiTypeStoredProcedure().keySet().iterator();
			while (itEnumWiTypeKeySet.hasNext()) {
				EnumWorkitemType enumWorkitemType = itEnumWiTypeKeySet.next();
				logger.info("Storicizzo type: " + enumWorkitemType);
				String sql = QueryUtils.getCallProcedure(
						"STORICIZZA_WI_BY_PROJECT." + Workitem_Type
								.getEnumMapWiTypeStoredProcedure()
								.get(enumWorkitemType),
						0);
				logger.debug("Chiamo la SP "
						+ Workitem_Type.getEnumMapWiTypeStoredProcedure()
								.get(enumWorkitemType));
				cm = ConnectionManager.getInstance();
				conn = cm.getConnectionOracle();
				try (CallableStatement call = conn.prepareCall(sql);) {
					call.execute();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}
	}
}
