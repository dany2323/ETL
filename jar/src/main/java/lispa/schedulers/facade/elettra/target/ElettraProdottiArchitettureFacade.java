package lispa.schedulers.facade.elettra.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElProdottiArchitetture;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ElettraProdottiArchitettureFacade {
	private static Logger logger = Logger
			.getLogger(ElettraProdottiArchitettureFacade.class);

	private static QDmalmElProdottiArchitetture qDmalmElProdottiArchitetture = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;

	private ElettraProdottiArchitettureFacade() {}
	public static void execute(Timestamp dataEsecuzione) throws Exception {
		if (ErrorManager.getInstance().hasError())
			return;

		List<DmalmElProdottiArchitetture> stagingProdotti = new ArrayList<>();
		List<Tuple> targetProdotti = new ArrayList<>();

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmElProdottiArchitetture prodottiArchitettureTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			logger.info("START ElettraProdottiArchitettureFacade.execute");

			stagingProdotti = ElettraProdottiArchitettureDAO
					.getAllProdotti(dataEsecuzione);

			for (DmalmElProdottiArchitetture prodotto : stagingProdotti) {
				prodottiArchitettureTmp = prodotto;
				// Ricerco nel db target un record con siglaprodotto =
				// prodotto.getSiglaProdotto e data fine validita 31/12/9999
				targetProdotti = ElettraProdottiArchitettureDAO
						.getProdotto(prodotto);

				// se non trovo almento un record, inserisco il nuovo
				// prodotto nel target
				if (targetProdotti.isEmpty()) {
					righeNuove++;

					ElettraProdottiArchitettureDAO.insertProdotto(prodotto);
				} else {
					
					for (Tuple row : targetProdotti) {
						if (row != null) {
							
							if (somethingIsChange(row,prodotto)) {
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
			
			recalculateFlat();
			
			checkFlatOnProdotti();

		} catch (DAOException e) {
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


	private static void checkFlatOnProdotti() throws Exception {
		
		boolean modificato=false;
		
		List<DmalmElProdottiArchitetture> allProdotti = ElettraProdottiArchitettureDAO.getAllTargetProdottoNotAnnullati();
		
		for(DmalmElProdottiArchitetture prodotti:allProdotti) {
			if(prodotti.getUnitaOrgFlatFk()!=null) {
				DmalmElUnitaOrganizzativeFlat uoFlat = ElettraUnitaOrganizzativeDAO.getUOFlatByPk(prodotti.getUnitaOrgFlatFk());
				
				if(uoFlat.getDataFineValidita().before(prodotti.getDataFineValidita())){
					ElettraProdottiArchitettureDAO.updateDataFineValidita(new Timestamp(DateUtils.addSecondsToDate(uoFlat.getDataFineValidita(),1).getTime()), prodotti.getProdottoPk());

					prodotti.setProdottoPk(null);
					
					ElettraProdottiArchitettureDAO.insertProdottoUpdate(new Timestamp(DateUtils.addSecondsToDate(uoFlat.getDataFineValidita(),1).getTime()), prodotti);
					
					modificato=true;
					
				}
			}
		}
		
		if(modificato)
			recalculateFlat();
		
		
	}
	private static boolean somethingIsChange(Tuple row, DmalmElProdottiArchitetture prodotto) {
		
		List <Boolean> conditionsList= new ArrayList<>();
		
		conditionsList.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElProdottiArchitetture.sigla),
						prodotto.getSigla()));
		
		conditionsList.add(BeanUtils.areDifferent(
					row.get(qDmalmElProdottiArchitetture.nome),
					prodotto.getNome()));
		conditionsList.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElProdottiArchitetture.unitaOrganizzativaFk),
						prodotto.getUnitaOrganizzativaFk()));
		conditionsList.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElProdottiArchitetture.personaleFk),
						prodotto.getPersonaleFk()));
		
		conditionsList.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElProdottiArchitetture.sigla),
						prodotto.getSigla()));
		
		conditionsList.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElProdottiArchitetture.annullato),
						prodotto.getAnnullato()));
		conditionsList.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElProdottiArchitetture.ambitoTecnologico),
						prodotto.getAmbitoTecnologico()));
		conditionsList.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElProdottiArchitetture.ambitoManutenzioneDenom),
						prodotto.getAmbitoManutenzioneDenom()));
		conditionsList.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElProdottiArchitetture.ambitoManutenzioneCodice),
						prodotto.getAmbitoManutenzioneCodice()));
		
		conditionsList.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElProdottiArchitetture.stato),
						prodotto.getStato()));
		
		
		return conditionsList.contains(true);
		
	}
	private static void recalculateFlat() throws Exception {
		QueryManager qm = QueryManager.getInstance();

		logger.info("INIZIO Update Prodotti Architetture UnitaOrganizzativaFlatFk");
		
		qm.executeMultipleStatementsFromFile(
				DmAlmConstants.M_UPDATE_EL_PROD_ARCH_UOFLATFK,
				DmAlmConstants.M_SEPARATOR);
		
		logger.info("FINE Update Prodotti Architetture UnitaOrganizzativaFlatFk");

	}
}