package lispa.schedulers.facade.cleaning;

import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryWorkitem;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryWorkitem;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckSgrWiStatusFacade implements Runnable {
	private Logger logger;
	private Timestamp dataEsecuzione;
	private boolean isAlive = true;;

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	@Override
	public void run() {

		execute(logger, dataEsecuzione);
		setAlive(false);
	}

	public CheckSgrWiStatusFacade(Logger log, Timestamp date) {
		this.logger = log;
		this.dataEsecuzione = date;
	}

	public static void execute(Logger logger, Timestamp dataEsecuzione) {
		try {
			logger.debug("START CheckSgrWiStatusFacade.execute");

			QSireHistoryWorkitem sireHistoryWorkitem = QSireHistoryWorkitem.sireHistoryWorkitem;
			QSissHistoryWorkitem sissHistoryWorkitem = QSissHistoryWorkitem.sissHistoryWorkitem;
			
			List<Tuple> sireWorkitems = SireHistoryWorkitemDAO.getAllSgrWiStatusInvalid(dataEsecuzione);
			
			List<Tuple> sissWorkitems = SissHistoryWorkitemDAO.getAllSgrWiStatusInvalid(dataEsecuzione);

			for (Tuple row : sireWorkitems) {
				String record = "repository: SIRE, cType: " + row.get(sireHistoryWorkitem.cType) + "§ cId: " + row.get(sireHistoryWorkitem.cId) + ", cStatus: " + row.get(sireHistoryWorkitem.cStatus); 
				logger.debug(record);
				
				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_SGR_SIRE_HISTORY_WORKITEM,
						DmAlmConstants.TARGET_STATOWORKITEM,
						record,
						DmAlmConstants.WRONG_WORKITEMS_STATUS,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}
			
			for (Tuple row : sissWorkitems) {
				String record = "repository: SISS, cType: " + row.get(sissHistoryWorkitem.cType) + ", cId: " + row.get(sissHistoryWorkitem.cId) + ", cStatus: " + row.get(sissHistoryWorkitem.cStatus); 
				logger.debug(record);
				
				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_SGR_SISS_HISTORY_WORKITEM,
						DmAlmConstants.TARGET_STATOWORKITEM,
						record,
						DmAlmConstants.WRONG_WORKITEMS_STATUS,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		logger.debug("STOP CheckSgrWiStatusFacade.execute");
	}
}
