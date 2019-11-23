package lispa.schedulers.facade.sgr.staging;

import java.sql.Timestamp;

import lispa.schedulers.constant.DmAlmConstants;

import org.apache.log4j.Logger;

public class FillSGRCMFacade {

	public static void execute(String repo, String schema, Logger logger) {
		switch(repo){
		
			// SIRE
			case DmAlmConstants.REPOSITORY_SIRE:
				switch(schema) {
					case DmAlmConstants.SCHEMA_CURRENT:
						{
							fillSireCurrent(logger);
							break;
						}
					case DmAlmConstants.SCHEMA_HISTORY:
						{
							fillSireHistory(logger);
							break;
						}
					default:
						break;
				}
				break;
				
			// SISS
			case DmAlmConstants.REPOSITORY_SISS:	
				switch(schema) {
					case DmAlmConstants.SCHEMA_CURRENT:
					{
						fillSissCurrent(logger);
						break;
					}
					case DmAlmConstants.SCHEMA_HISTORY:
					{
						fillSissHistory(logger);
						break;
					}
					default:
						break;
			}
				break;
			default:
				break;
		}
	}
	
	public static void delete(String repo, String schema, Logger logger, Timestamp dataEsecuzioneDeleted) {
		switch(repo){
			case DmAlmConstants.REPOSITORY_SIRE:
				switch(schema) {
					case DmAlmConstants.SCHEMA_CURRENT:
					{
						deleteSireCurrent(logger, dataEsecuzioneDeleted);
						break;
					}
					case DmAlmConstants.SCHEMA_HISTORY:
					{
						deleteSireHistory(logger, dataEsecuzioneDeleted);
						break;
					}
				}
				break;
			case DmAlmConstants.REPOSITORY_SISS:	
				switch(schema) {
					case DmAlmConstants.SCHEMA_CURRENT:
					{
						deleteSissCurrent(logger, dataEsecuzioneDeleted);
						break;
					}
					case DmAlmConstants.SCHEMA_HISTORY:
					{
						deleteSissHistory(logger, dataEsecuzioneDeleted);
						break;
					}
			}
				break;
			default:
				break;
		}
	}
	
	private static void fillSireCurrent (Logger logger) {	
		FillSIRECurrentFacade.execute(logger);
	}
	
	private static void deleteSireCurrent (Logger logger, Timestamp dataEsecuzione) {	
		FillSIRECurrentFacade.delete(logger, dataEsecuzione);
	}
	
	private static void fillSireHistory(Logger logger)
	{
		FillSIREHistoryFacade.execute(logger);
	}

	private static void deleteSireHistory (Logger logger, Timestamp dataEsecuzione) {	
		FillSIREHistoryFacade.delete(logger, dataEsecuzione);
	}
	
	private static void fillSissCurrent (Logger logger) {	
		FillSISSCurrentFacade.execute(logger);
	}
	
	private static void deleteSissCurrent (Logger logger, Timestamp dataEsecuzione) {	
		FillSISSCurrentFacade.delete(logger, dataEsecuzione);
	}
	
	private static void fillSissHistory( Logger logger)
	{
			FillSISSHistoryFacade.execute(logger);
	}
	
	private static void deleteSissHistory (Logger logger, Timestamp dataEsecuzione) {	
			FillSISSHistoryFacade.delete(logger, dataEsecuzione);
	}
	
}
