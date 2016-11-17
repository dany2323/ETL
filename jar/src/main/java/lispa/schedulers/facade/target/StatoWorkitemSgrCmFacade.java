package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.DmalmStatoWorkitem;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.StatoWorkitemSgrCmDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class StatoWorkitemSgrCmFacade {

	private static Logger logger = Logger
			.getLogger(StatoWorkitemSgrCmFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		List<DmalmStatoWorkitem> staging_statoworkitem = new ArrayList<DmalmStatoWorkitem>();
		List<Tuple> target_statoworkitem = new ArrayList<Tuple>();

		int righeNuove = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			staging_statoworkitem = StatoWorkitemSgrCmDAO
					.getAllStatoWorkitem(dataEsecuzione);

			for (DmalmStatoWorkitem statoworkitem : staging_statoworkitem) {
				// Ricerco nel db target un record con cdstato =
				// stato.getCdStato()
				target_statoworkitem = StatoWorkitemSgrCmDAO
						.getStatoWorkitem(statoworkitem);

				// se non trovo almento un record, inserisco lo stato nel target
				if (target_statoworkitem.size() == 0) {
					righeNuove++;
					StatoWorkitemSgrCmDAO.insertStatoWorkitem(statoworkitem);
				}

			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {

				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_STATOWORKITEM, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						0, 0, 0);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}