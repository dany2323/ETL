package lispa.schedulers.facade.sgr.staging;

import lispa.schedulers.constant.DmAlmConstants;
import org.apache.log4j.Logger;

public class FillSGRCMFacade {

	public static void execute(String repo, String schema, Logger logger) {
		switch(repo){
		
			// SIRE
			case DmAlmConstants.REPOSITORY_SIRE:
				switch(schema) {
					case DmAlmConstants.SCHEMA_CURRENT:
						FillSIRECurrentFacade.execute(logger);
						break;
					case DmAlmConstants.SCHEMA_HISTORY:
						FillSIREHistoryFacade.execute(logger);
						break;
					default:
						break;
				}
				break;
				
			// SISS
			case DmAlmConstants.REPOSITORY_SISS:	
				switch(schema) {
					case DmAlmConstants.SCHEMA_CURRENT:
						FillSISSCurrentFacade.execute(logger);
						break;
					case DmAlmConstants.SCHEMA_HISTORY:
						FillSISSHistoryFacade.execute(logger);
						break;
					default:
						break;
			}
				break;
			default:
				break;
		}
	}
}