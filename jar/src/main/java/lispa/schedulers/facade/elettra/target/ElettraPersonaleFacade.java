package lispa.schedulers.facade.elettra.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

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

public class ElettraPersonaleFacade {
	private static Logger logger = Logger
			.getLogger(ElettraPersonaleFacade.class);
	private static QDmalmElPersonale qDmalmElPersonale = QDmalmElPersonale.qDmalmElPersonale;

	private ElettraPersonaleFacade() {}
	public static void execute(Timestamp dataEsecuzione) throws SQLException, PropertiesReaderException 
	{
		
		List<DmalmElPersonale> stagingPersonale = new ArrayList<>();
		List<Tuple> targetPersonale = new ArrayList<>();

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmElPersonale personaleTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			logger.info("START ElettraPersonaleFacade.execute");

			stagingPersonale = ElettraPersonaleDAO
					.getAllPersonale(dataEsecuzione);

			for (DmalmElPersonale personale : stagingPersonale) {
				personaleTmp = personale;

				// Ricerco nel db target un record con codiceArea =
				// unita.getCodiceArea e data fine validita max
				targetPersonale = ElettraPersonaleDAO.getPersonale(personale);

				// se non trovo almento un record, inserisco la nuova unita
				// organizzativa nel target
				if (targetPersonale.isEmpty()) {
					righeNuove++;

					ElettraPersonaleDAO.insertPersonale(personale);
				} else {

					for (Tuple row : targetPersonale) {

						if (somethingIsChange(row,personale) && row!=null) {
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

			//DMALM-237 associazione project Unità Organizzativa Flat
			//ricarica il valore della Fk ad ogni esecuzione
			updateFlatPersonale();
			//Storicizza il personale se cambiata UO DM_ALM-447
			checkFlatUoChanges();
						
		} catch (DAOException e) {
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

	private static void checkFlatUoChanges() throws DAOException, PropertiesReaderException {
		
		boolean changes=false;
		List<DmalmElPersonale> allPersonaleRecord = ElettraPersonaleDAO.getAllPersonale();
		for(DmalmElPersonale row:allPersonaleRecord){
			if(row.getUnitaOrganizzativaFlatFk()!=null){
				DmalmElUnitaOrganizzativeFlat uoFlat = ElettraPersonaleDAO.getFlatUOByPk(row.getUnitaOrganizzativaFlatFk());
				if(uoFlat.getDataFineValidita().before(row.getDataFineValidita())){
					ElettraPersonaleDAO.updateDataFineValidita(
							new Timestamp(DateUtils.addSecondsToDate(uoFlat.getDataFineValidita(),1).getTime()),
							row.getPersonalePk());

					row.setPersonalePk(null);
					ElettraPersonaleDAO.insertPersonaleUpdate(
							new Timestamp(DateUtils.addSecondsToDate(uoFlat.getDataFineValidita(),1).getTime()), row);
					changes=true;
				}
			}
			
		}
		if(changes)
			updateFlatPersonale();
		
	}
	private static boolean somethingIsChange(Tuple row, DmalmElPersonale personale) {
		ArrayList<Boolean> conditionArray= new ArrayList<>();
		conditionArray.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElPersonale.dataInizioValiditaEdma),
						personale
						.getDataInizioValiditaEdma()));
		conditionArray.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElPersonale.dataInizioValiditaEdma),
						personale
						.getDataInizioValiditaEdma()));

		conditionArray.add(BeanUtils
				.areDifferent(
						row.get(qDmalmElPersonale.dataFineValiditaEdma),
						personale.getDataFineValiditaEdma()));
		conditionArray.add(BeanUtils.areDifferent(
				row.get(qDmalmElPersonale.dataAttivazione),
				personale.getDataAttivazione()));
		conditionArray.add(BeanUtils.areDifferent(
				row.get(qDmalmElPersonale.dataAttivazione),
				personale.getDataAttivazione()));
		conditionArray.add(BeanUtils.areDifferent(row
				.get(qDmalmElPersonale.dataDisattivazione),
				personale.getDataDisattivazione()));
		conditionArray.add(BeanUtils.areDifferent(
				row.get(qDmalmElPersonale.interno),
				personale.getInterno()));
		conditionArray.add(BeanUtils.areDifferent(row
				.get(qDmalmElPersonale.codiceResponsabile),
				personale.getCodiceResponsabile()));
		conditionArray.add(BeanUtils.areDifferent(
				row.get(qDmalmElPersonale.cdSuperiore),
				personale.getCdSuperiore()));
		conditionArray.add(BeanUtils.areDifferent(
				row.get(qDmalmElPersonale.matricola),
				personale.getMatricola()));
		return conditionArray.contains(true);
	}
	private static void updateFlatPersonale() throws PropertiesReaderException, DAOException {
		QueryManager qm = QueryManager.getInstance();

		logger.info("INIZIO Update Personale Elettra UnitaOrganizzativaFlatFk");
		try {
			qm.executeMultipleStatementsFromFile(
					DmAlmConstants.M_UPDATE_PERSONALE_UOFLATFK,
					DmAlmConstants.M_SEPARATOR);
		} catch (Exception e) {
			throw new DAOException(DmAlmConstants.M_UPDATE_PERSONALE_UOFLATFK);
		}
		logger.info("FINE Update Personale Elettra UnitaOrganizzativaFlatFk");

	}
}
