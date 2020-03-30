package lispa.schedulers.facade.mps.target;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.RecoverManager;

import org.apache.log4j.Logger;

public class TargetMpsFacade {
	private static Logger logger = Logger.getLogger(TargetMpsFacade.class);

	public static void fillTargetMps() {
		try {
			logger.info("START MPS fillTargetMps");

			Timestamp dataEsecuzione = new Timestamp(new Date().getTime());

			RecoverManager.getInstance().prepareMpsTargetForRecover();

			logger.debug("START MpsAttivitaFacade.execute " + new Date());
			MpsAttivitaFacade.execute(dataEsecuzione);

			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START MpsContrattiFacade.execute " + new Date());
			MpsContrattiFacade.execute(dataEsecuzione);

			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START MpsFirmatariVerbaleFacade.execute "
					+ new Date());
			MpsFirmatariVerbaleFacade.execute(dataEsecuzione);

			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START MpsRespContrattoFacade.execute " + new Date());
			MpsResponsabiliContrattoFacade.execute(dataEsecuzione);

			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START MpsVerbaliFacade.execute " + new Date());
			MpsVerbaliFacade.execute(dataEsecuzione);

			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START MpsRilasciVerbaliFacade.execute " + new Date());
			MpsRilasciVerbaliFacade.execute(dataEsecuzione);

			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START MpsRilasciFacade.execute " + new Date());
			MpsRilasciFacade.execute(dataEsecuzione);

			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START MpsResponsabiliOffertaFacade.execute "
					+ new Date());
			MpsResponsabiliOffertaFacade.execute(dataEsecuzione);

			if (ErrorManager.getInstance().hasError())
				return;

//			logger.debug("START Procedura load_mps_confronto_sgrcm "
//					+ new Date());
//
//			ConnectionManager cm = null;
//			Connection conn = null;
//			CallableStatement cstmt = null;
//
//			try {
//				cm = ConnectionManager.getInstance();
//				conn = cm.getConnectionOracle();
//
//				cstmt = conn.prepareCall("{call load_mps_confronto_sgrcm(?)}");
//				cstmt.registerOutParameter(1, Types.INTEGER);
//				cstmt.execute();
//				logger.info("Esito procedura load_mps_confronto_sgrcm: "
//						+ cstmt.getInt(1));
//
//			} catch (Exception e) {
//				logger.error(e.getMessage(), e);
//
//			} finally {
//				if (cstmt != null)
//					cstmt.close();
//				if (cm != null)
//					cm.closeConnection(conn);
//			}
//
//			logger.debug("STOP Procedura load_mps_confronto_sgrcm "
//					+ new Date());

			logger.info("STOP MPS fillTargetMps");
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}
}
