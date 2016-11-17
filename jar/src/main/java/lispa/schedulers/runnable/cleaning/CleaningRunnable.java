package lispa.schedulers.runnable.cleaning;

import java.sql.Timestamp;

import lispa.schedulers.facade.cleaning.CheckSgrWiStatusFacade;

import org.apache.log4j.Logger;

public class CleaningRunnable implements Runnable {
	private Logger logger;
	private Timestamp dataEsecuzione;

	public CleaningRunnable(Logger logger, Timestamp date) {
		this.logger = logger;
		this.dataEsecuzione = date;

	}

	public void launcher(Logger logger, Timestamp date) {

		Thread t1 = new Thread(new CheckClassificatoriRunnable(logger, date));
		Thread t2 = new Thread(new CheckAmbienteTecnologicoRunnable(logger,
				date));
		Thread t3 = new Thread(new CheckFunzionalitaRunnable(logger, date));
		Thread t4 = new Thread(new CheckModuliRunnable(logger, date));
		Thread t5 = new Thread(new CheckProdottiRunnable(logger, date));
		Thread t6 = new Thread(new CheckSottosistemiRunnable(logger, date));
		Thread t7 = new Thread(new CheckSIREProjectRunnable(logger, date));
		Thread t8 = new Thread(new CheckSISSProjectRunnable(logger, date));

		try {
			t1.start();
			t1.join();

			t2.start();
			t2.join();

			t3.start();
			t3.join();

			t4.start();
			t4.join();

			t5.start();
			t5.join();

			t6.start();
			t6.join();

			t7.start();
			t7.join();

			t8.start();
			t8.join();

			CheckSgrWiStatusFacade.execute(logger, date);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void run() {
		launcher(logger, dataEsecuzione);

	}

}
