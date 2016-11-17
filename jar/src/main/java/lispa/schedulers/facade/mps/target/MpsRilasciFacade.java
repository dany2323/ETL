package lispa.schedulers.facade.mps.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsRilasci;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.mps.DmAlmMpsRilasciDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

public class MpsRilasciFacade {
	private static Logger logger = Logger.getLogger(MpsRilasciFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		List<DmalmMpsRilasci> mpsRilascieStg = new ArrayList<DmalmMpsRilasci>();

		int righeNuove = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
		DmalmMpsRilasci mpsRilasciTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			mpsRilascieStg = DmAlmMpsRilasciDAO
					.getAllMpsRilascie(dataEsecuzione);

			for (DmalmMpsRilasci mpsRilascie : mpsRilascieStg) {
				mpsRilasciTmp = mpsRilascie;

				righeNuove++;
				DmAlmMpsRilasciDAO.insertMpsRilascie(dataEsecuzione, mpsRilascie);
			}

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(mpsRilasciTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(mpsRilasciTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_MPS_BO_RILASCI, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						0, 0, 0);
			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);

			}
		}
	}
}
