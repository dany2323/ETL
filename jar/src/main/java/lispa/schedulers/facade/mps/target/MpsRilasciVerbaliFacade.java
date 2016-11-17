package lispa.schedulers.facade.mps.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsRilasciVerbali;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.mps.DmAlmMpsRilasciVerbaliDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

public class MpsRilasciVerbaliFacade {

	private static Logger logger = Logger.getLogger(MpsRilasciVerbaliFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		List<DmalmMpsRilasciVerbali> mpsRilasciVerbalieStg = new ArrayList<DmalmMpsRilasciVerbali>();

		int righeNuove = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
		DmalmMpsRilasciVerbali mpsRilasciVerbaliTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			mpsRilasciVerbalieStg = DmAlmMpsRilasciVerbaliDAO
					.getAllMpsRilasciVerbalie(dataEsecuzione);

			for (DmalmMpsRilasciVerbali mpsRilasciVerbalie : mpsRilasciVerbalieStg) {
				mpsRilasciVerbaliTmp = mpsRilasciVerbalie;

				righeNuove++;
				DmAlmMpsRilasciVerbaliDAO.insertMpsRilasciVerbalie(dataEsecuzione, mpsRilasciVerbalie);
			}

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(mpsRilasciVerbaliTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(mpsRilasciVerbaliTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_MPS_BO_RILASCI_VERBALI, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						0, 0, 0);
			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);

			}
		}
	}
}
