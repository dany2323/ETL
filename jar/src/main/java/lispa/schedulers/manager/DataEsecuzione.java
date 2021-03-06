package lispa.schedulers.manager;

import java.sql.Timestamp;

import lispa.schedulers.utils.DateUtils;

public class DataEsecuzione {
	//Singleton che contiene il timestamp utilizzato come DATA_CARICAMENTO durante la fase fillstaging
	
	private Timestamp dataEsecuzione;
	private static DataEsecuzione instance;
	
	private DataEsecuzione(){
		dataEsecuzione = DateUtils.getDataEsecuzione();
	}
	
	public synchronized static DataEsecuzione getInstance() {
		if(instance == null) {
			instance = new DataEsecuzione();
		}
		return instance;
	}
	
	public synchronized Timestamp getDataEsecuzione() {
		return dataEsecuzione;
	}
	
	/**
	 * !!!Warning!!!
	 * To be used only for testing purposes
	 * 
	 * @param timeForTest
	 * @return
	 */
	public synchronized Timestamp setDataEsecuzione(Timestamp timeForTest) {
		dataEsecuzione = timeForTest;
		return dataEsecuzione;
	}
}
