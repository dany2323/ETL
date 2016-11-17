package lispa.schedulers.facade.cleaning;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class CheckElettraFacade implements Runnable {
	private Logger logger;
	private Timestamp dataEsecuzione;
	private boolean isAlive = true;

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public CheckElettraFacade(Logger logger, Timestamp dataEsecuzione) {
		this.logger = logger;
		this.dataEsecuzione = dataEsecuzione;
	}

	@Override
	public void run() {
		execute(logger, dataEsecuzione);
		setAlive(false);
	}

	public static void execute(Logger logger, Timestamp dataEsecuzione) {
		logger.info("START CheckElettraFacade.execute");
		
		CheckElettraClassificatoriFacade.execute(logger, dataEsecuzione);
		CheckElettraProdottiFacade.execute(logger, dataEsecuzione);
		CheckElettraModuliFacade.execute(logger, dataEsecuzione);
		CheckElettraFunzionalitaFacade.execute(logger, dataEsecuzione);
		CheckElettraAmbienteTecnologicoFacade.execute(logger, dataEsecuzione);

		logger.info("STOP CheckElettraFacade.execute");
	}
}
