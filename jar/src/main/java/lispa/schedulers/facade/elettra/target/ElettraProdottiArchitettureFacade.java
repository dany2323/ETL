package lispa.schedulers.facade.elettra.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElProdottiArchitetture;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ElettraProdottiArchitettureFacade {
	private static Logger logger = Logger
			.getLogger(ElettraProdottiArchitettureFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {
		if (ErrorManager.getInstance().hasError())
			return;

		List<DmalmElProdottiArchitetture> staging_prodotti = new ArrayList<DmalmElProdottiArchitetture>();
		List<Tuple> target_prodotti = new ArrayList<Tuple>();
		QDmalmElProdottiArchitetture qDmalmElProdottiArchitetture = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmElProdottiArchitetture prodottiArchitettureTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			logger.info("START ElettraProdottiArchitettureFacade.execute");

			staging_prodotti = ElettraProdottiArchitettureDAO
					.getAllProdotti(dataEsecuzione);

			for (DmalmElProdottiArchitetture prodotto : staging_prodotti) {
				prodottiArchitettureTmp = prodotto;
				// Ricerco nel db target un record con siglaprodotto =
				// prodotto.getSiglaProdotto e data fine validita 31/12/9999
				target_prodotti = ElettraProdottiArchitettureDAO
						.getProdotto(prodotto);

				// se non trovo almento un record, inserisco il nuovo
				// prodotto nel target
				if (target_prodotti.size() == 0) {
					righeNuove++;

					ElettraProdottiArchitettureDAO.insertProdotto(prodotto);
				} else {
					boolean modificato = false;

					for (Tuple row : target_prodotti) {
						if (row != null) {
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElProdottiArchitetture.sigla),
											prodotto.getSigla())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(qDmalmElProdottiArchitetture.nome),
									prodotto.getNome())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElProdottiArchitetture.unitaOrganizzativaFk),
											prodotto.getUnitaOrganizzativaFk())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElProdottiArchitetture.personaleFk),
											prodotto.getPersonaleFk())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElProdottiArchitetture.sigla),
											prodotto.getSigla())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElProdottiArchitetture.annullato),
											prodotto.getAnnullato())) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								ElettraProdottiArchitettureDAO
										.updateDataFineValidita(
												dataEsecuzione,
												row.get(qDmalmElProdottiArchitetture.prodottoPk));

								// inserisco un nuovo record
								ElettraProdottiArchitettureDAO
										.insertProdottoUpdate(dataEsecuzione,
												prodotto);
							} else {
								// Aggiorno comunque i dati senza storicizzare
								ElettraProdottiArchitettureDAO
										.updateProdotto(
												row.get(qDmalmElProdottiArchitetture.prodottoPk),
												prodotto);
							}
						}
					}
				}
			}
			
			//DMALM-216 associazione project Unità Organizzativa Flat
			//ricarica il valore della Fk ad ogni esecuzione
			
				QueryManager qm = QueryManager.getInstance();

				logger.info("INIZIO Update Prodotti Architetture UnitaOrganizzativaFlatFk");
				
				qm.executeMultipleStatementsFromFile(
						DmAlmConstants.M_UPDATE_EL_PROD_ARCH_UOFLATFK,
						DmAlmConstants.M_SEPARATOR);
				
				logger.info("FINE Update Prodotti Architetture UnitaOrganizzativaFlatFk");
			
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(prodottiArchitettureTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(prodottiArchitettureTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.updateETLTargetInfo(dataEsecuzione, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()),
						DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
						righeNuove, righeModificate);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("STOP ElettraProdottiArchitettureFacade.execute");
		}
	}
}
