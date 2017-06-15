package lispa.schedulers.facade.target;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.elettra.DmAlmSourceElProdEccez;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.target.DmAlmProjectProdottiArchitettureDAO;
import lispa.schedulers.dao.target.DmAlmSourceElProdEccezDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.QErroriCaricamentoDmAlm;
import lispa.schedulers.queryimplementation.target.QDmalmProjectProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;

public class CheckDmalmSourceElProdFacade {

	private static Logger logger = Logger.getLogger(CheckDmalmSourceElProdFacade.class);
	private static QDmAlmSourceElProdEccez dmAlmSourceElProd = QDmAlmSourceElProdEccez.dmAlmSourceElProd;
	
	public static void execute(Timestamp dataEsecuzione) {
		
		if (ErrorManager.getInstance().hasError())
			return;

		logger.info("START CheckDmalmSourceElProdFacade");
		
		try{
		List<Tuple> relList = new ArrayList<Tuple>();

		
		relList=DmAlmSourceElProdEccezDAO.getData();
		
		for(Tuple row: relList)
		{
			String sigla=row.get(dmAlmSourceElProd.siglaOggettoElettra);
			String [] splitting=sigla.split(".");
			if(splitting.length>2)
			{
				ErroriCaricamentoDAO.insert("DMALM_SOURCE_EL_PROD_ECCEZ", "DMALM_SOURCE_EL_PROD_ECCEZ", "SIGLA_OGGETTO_ELETTRA", "Il campo SIGLA_OGGETTO_ELETTRA non è compliant alle specifiche, ci sono più di due punti: "+sigla, DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE, dataEsecuzione);
			}
			Integer tipo=row.get(dmAlmSourceElProd.tipoElProdEccezione);
			if(tipo< 0 && tipo>1)
			{
				ErroriCaricamentoDAO.insert("DMALM_SOURCE_EL_PROD_ECCEZ", "DMALM_SOURCE_EL_PROD_ECCEZ", "TIPO_EL_PROD_ECCEZIONE", "Il campo TIPO_EL_PROD_ECCEZIONE, con chiave "+sigla+" non è valido : "+tipo, DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE, dataEsecuzione);
			}
		}
		logger.info("STOP CheckDmalmSourceElProdFacade");
		} catch (DAOException e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		}
	}

}
