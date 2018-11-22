package lispa.schedulers.facade.elettra.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElPersonale;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElPersonale;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ElettraPersonaleFacade {
	private static Logger logger = Logger
			.getLogger(ElettraPersonaleFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {
		if (ErrorManager.getInstance().hasError())
			return;

		List<DmalmElPersonale> staging_personale = new ArrayList<DmalmElPersonale>();
		List<Tuple> target_personale = new ArrayList<Tuple>();
		QDmalmElPersonale qDmalmElPersonale = QDmalmElPersonale.qDmalmElPersonale;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmElPersonale personaleTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			logger.info("START ElettraPersonaleFacade.execute");

			staging_personale = ElettraPersonaleDAO
					.getAllPersonale(dataEsecuzione);

			for (DmalmElPersonale personale : staging_personale) {
				personaleTmp = personale;

				// Ricerco nel db target un record con codiceArea =
				// unita.getCodiceArea e data fine validita max
				target_personale = ElettraPersonaleDAO.getPersonale(personale);

				// se non trovo almento un record, inserisco la nuova unita
				// organizzativa nel target
				if (target_personale.size() == 0) {
					righeNuove++;

					ElettraPersonaleDAO.insertPersonale(personale);
				} else {
					boolean modificato = false;

					for (Tuple row : target_personale) {
						if (row != null) {
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElPersonale.dataInizioValiditaEdma),
											personale
													.getDataInizioValiditaEdma())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElPersonale.dataFineValiditaEdma),
											personale.getDataFineValiditaEdma())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(qDmalmElPersonale.dataAttivazione),
									personale.getDataAttivazione())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(row
									.get(qDmalmElPersonale.dataDisattivazione),
									personale.getDataDisattivazione())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(qDmalmElPersonale.interno),
									personale.getInterno())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(row
									.get(qDmalmElPersonale.codiceResponsabile),
									personale.getCodiceResponsabile())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(qDmalmElPersonale.cdSuperiore),
									personale.getCdSuperiore())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(qDmalmElPersonale.matricola),
									personale.getMatricola())) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								ElettraPersonaleDAO.updateDataFineValidita(
										dataEsecuzione,
										row.get(qDmalmElPersonale.personalePk));

								// inserisco un nuovo record
								ElettraPersonaleDAO.insertPersonaleUpdate(
										dataEsecuzione, personale);
							} else {
								// Aggiorno comunque i dati senza storicizzare
								ElettraPersonaleDAO.updatePersonale(
										row.get(qDmalmElPersonale.personalePk),
										personale);
							}
						}
					}
				}
			}
			
			//DMALM-237 associazione project Unità Organizzativa Flat
			//ricarica il valore della Fk ad ogni esecuzione
			updateFlatPersonale();
			
			//Storicizza il personale se DM_ALM-447
			List<DmalmElPersonale> allPersonaleRecord = ElettraPersonaleDAO.getAllPersonale();
			int recordStoricizzati=0;
			for(DmalmElPersonale row:allPersonaleRecord){
				if(row.getUnitaOrganizzativaFlatFk()!=null){
					DmalmElUnitaOrganizzativeFlat UOFlat = ElettraPersonaleDAO.getFlatUOByPk(row.getUnitaOrganizzativaFlatFk());
//					System.out.println("Chiavi "+row.get(qDmalmElPersonale.personalePk));
					if(UOFlat.getDataFineValidita().before(row.getDataFineValidita())){
						System.out.println("Attenzione, qualcosa non va su personale "+row.getPersonalePk());
						ElettraPersonaleDAO.updateDataFineValidita(
								new Timestamp(DateUtils.addSecondsToDate(UOFlat.getDataFineValidita(),1).getTime()),
								row.getPersonalePk());

						// inserisco un nuovo record
						row.setPersonalePk(null);
						ElettraPersonaleDAO.insertPersonaleUpdate(
								new Timestamp(DateUtils.addSecondsToDate(UOFlat.getDataFineValidita(),1).getTime()), row);
						recordStoricizzati++;
					}
				}

			}
			if(recordStoricizzati>0){
				updateFlatPersonale();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(personaleTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(personaleTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_ELETTRA_PERSONALE, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("STOP ElettraPersonaleFacade.execute");
		}

	}

	private static void updateFlatPersonale() throws Exception {
		QueryManager qm = QueryManager.getInstance();

		logger.info("INIZIO Update Personale Elettra UnitaOrganizzativaFlatFk");
		
		qm.executeMultipleStatementsFromFile(
				DmAlmConstants.M_UPDATE_PERSONALE_UOFLATFK,
				DmAlmConstants.M_SEPARATOR);
		
		logger.info("FINE Update Personale Elettra UnitaOrganizzativaFlatFk");
		
	}
}
