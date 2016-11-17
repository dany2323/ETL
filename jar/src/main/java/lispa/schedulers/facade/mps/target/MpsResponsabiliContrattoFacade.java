package lispa.schedulers.facade.mps.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.mps.DmalmMpsResponsabiliContratto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.mps.DmAlmMpsResponsabiliContrattoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

public class MpsResponsabiliContrattoFacade {
	private static Logger logger = Logger.getLogger(MpsResponsabiliContrattoFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		List<DmalmMpsResponsabiliContratto> mpsResponsabiliContrattoeStg = new ArrayList<DmalmMpsResponsabiliContratto>();

		int righeNuove = 0;
	
		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
		DmalmMpsResponsabiliContratto mpsResponsabiliContrattoTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			mpsResponsabiliContrattoeStg = DmAlmMpsResponsabiliContrattoDAO
					.getAllMpsResponsabiliContrattoe(dataEsecuzione);

			for (DmalmMpsResponsabiliContratto mpsResponsabiliContrattoe : mpsResponsabiliContrattoeStg) {
				mpsResponsabiliContrattoTmp = mpsResponsabiliContrattoe;

					righeNuove++;
					DmAlmMpsResponsabiliContrattoDAO.insertMpsResponsabiliContrattoe(dataEsecuzione,mpsResponsabiliContrattoe);
			}

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(mpsResponsabiliContrattoTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(mpsResponsabiliContrattoTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {

				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_MPS_BO_RESPONS_CONTRATTO, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						0, 0, 0);
			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);

			}
		}
	}
}
