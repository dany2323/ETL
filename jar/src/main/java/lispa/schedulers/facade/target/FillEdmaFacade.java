package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;

import lispa.schedulers.dao.edma.PersonaleDAO;
import lispa.schedulers.dao.edma.UnitaOrganizzativaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.runnable.staging.edma.PersonaleRunnable;
import lispa.schedulers.runnable.staging.edma.UnitaOrganizzativaRunnable;

import org.apache.log4j.Logger;

public class FillEdmaFacade {

	/**
	 * Provvede al caricamento delle tabelle di staging
	 * -DMALM_UNITA_ORGANIZZATIVA -DMALM_PERSONALE con i dati presenti nelle
	 * relative fonti presenti alla data di elaborazione
	 *
	 * @param logger
	 */

	public static void execute(final Logger logger) {

		Thread unita, personale;

		unita = new Thread(new UnitaOrganizzativaRunnable(logger));

		personale = new Thread(new PersonaleRunnable(logger));

		try {
			unita.start();
			personale.start();
			unita.join();
			personale.join();
		} catch (InterruptedException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

	/**
	 * Provvede al cancellamento delle tabelle di staging
	 * -DMALM_UNITA_ORGANIZZATIVA -DMALM_PERSONALE di tutti i record che
	 * presentano data_caricamento = dataEsecuzione
	 * 
	 * @param logger
	 * @param dataEsecuzione
	 */
	public static void delete(Logger logger, Timestamp dataEsecuzioneDelete) {

		try {

			UnitaOrganizzativaDAO.delete(dataEsecuzioneDelete);

			PersonaleDAO.delete(dataEsecuzioneDelete);

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

}