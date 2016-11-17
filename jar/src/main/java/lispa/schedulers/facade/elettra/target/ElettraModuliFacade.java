package lispa.schedulers.facade.elettra.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElModuli;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.elettra.ElettraModuliDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElModuli;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ElettraModuliFacade {
	private static Logger logger = Logger.getLogger(ElettraModuliFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {
		if (ErrorManager.getInstance().hasError())
			return;

		List<DmalmElModuli> staging_moduli = new ArrayList<DmalmElModuli>();
		List<Tuple> target_moduli = new ArrayList<Tuple>();
		QDmalmElModuli qDmalmElModuli = QDmalmElModuli.qDmalmElModuli;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmElModuli moduloTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			logger.info("START ElettraModuliFacade.execute");

			staging_moduli = ElettraModuliDAO.getAllModuli(dataEsecuzione);

			for (DmalmElModuli modulo : staging_moduli) {
				moduloTmp = modulo;
				// Ricerco nel db target un record con idModulo =
				// modulo.getIdModulo e data fine validita
				// 31/12/9999
				target_moduli = ElettraModuliDAO.getModulo(modulo);

				// se non trovo almento un record, inserisco il nuovo
				// modulo nel target
				if (target_moduli.size() == 0) {
					righeNuove++;

					ElettraModuliDAO.insertModulo(modulo);
				} else {
					boolean modificato = false;

					for (Tuple row : target_moduli) {
						if (row != null) {
							if (BeanUtils.areDifferent(
									row.get(qDmalmElModuli.siglaModulo),
									modulo.getSiglaModulo())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(qDmalmElModuli.nome),
									modulo.getNome())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(qDmalmElModuli.personaleFk),
									modulo.getPersonaleFk())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(qDmalmElModuli.annullato),
									modulo.getAnnullato())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(qDmalmElModuli.dataAnnullamento),
									modulo.getDataAnnullamento())) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								ElettraModuliDAO.updateDataFineValidita(
										dataEsecuzione, row.get(qDmalmElModuli.moduloPk));

								// inserisco un nuovo record
								ElettraModuliDAO.insertModuloUpdate(
										dataEsecuzione, modulo);
							} else {
								// Aggiorno comunque i dati senza storicizzare
								ElettraModuliDAO.updateModulo(row.get(qDmalmElModuli.moduloPk), modulo);
							}
						}
					}
				}
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(moduloTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(moduloTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.updateETLTargetInfo(dataEsecuzione, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()),
						DmAlmConstants.TARGET_ELETTRA_MODULI, righeNuove,
						righeModificate);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("STOP ElettraModuliFacade.execute");
		}
	}
}
