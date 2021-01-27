package lispa.schedulers.facade.mps.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsResponsabiliOfferta;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.mps.DmAlmMpsResponsabiliOffertaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

public class MpsResponsabiliOffertaFacade {
	private static Logger logger = Logger.getLogger(MpsResponsabiliOffertaFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		List<DmalmMpsResponsabiliOfferta> mpsResponsabiliOffertaeStg = new ArrayList<DmalmMpsResponsabiliOfferta>();

		int righeNuove = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
		DmalmMpsResponsabiliOfferta mpsResponsabiliOffertaTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			dtInizioCaricamento = new Date();

			mpsResponsabiliOffertaeStg = DmAlmMpsResponsabiliOffertaDAO
					.getAllMpsResponsabiliOffertae(dataEsecuzione); 

			for (DmalmMpsResponsabiliOfferta mpsResponsabiliOffertae : mpsResponsabiliOffertaeStg) {
				mpsResponsabiliOffertaTmp = mpsResponsabiliOffertae;

				righeNuove++;
				DmAlmMpsResponsabiliOffertaDAO.insertMpsResponsabiliOffertae(dataEsecuzione,mpsResponsabiliOffertae);
			}

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(mpsResponsabiliOffertaTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(mpsResponsabiliOffertaTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {

				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_MPS_BO_RESPONSABILI_OFFERTA, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						0, 0, 0);
			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);

			}
		}
	}
}
