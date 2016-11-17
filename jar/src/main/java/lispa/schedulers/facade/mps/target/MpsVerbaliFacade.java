package lispa.schedulers.facade.mps.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsVerbali;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.mps.DmAlmMpsVerbaliDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

public class MpsVerbaliFacade {
	private static Logger logger = Logger.getLogger(MpsVerbaliFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		List<DmalmMpsVerbali> mpsVerbalieStg = new ArrayList<DmalmMpsVerbali>();

		int righeNuove = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
		DmalmMpsVerbali mpsVerbaliTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			mpsVerbalieStg = DmAlmMpsVerbaliDAO
					.getAllMpsVerbalie(dataEsecuzione);

			for (DmalmMpsVerbali mpsVerbalie : mpsVerbalieStg) {
				mpsVerbaliTmp = mpsVerbalie;

				righeNuove++;
				DmAlmMpsVerbaliDAO.insertMpsVerbalie(dataEsecuzione, mpsVerbalie);
			}

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(mpsVerbaliTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(mpsVerbaliTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_MPS_BO_VERBALI, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						0, 0, 0);
			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);

			}
		}
	}
}
