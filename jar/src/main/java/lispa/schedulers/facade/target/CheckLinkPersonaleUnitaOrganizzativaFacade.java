package lispa.schedulers.facade.target;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmPersonale;
import lispa.schedulers.dao.target.PersonaleEdmaLispaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmPersonale;
import lispa.schedulers.queryimplementation.target.QDmalmProdotto;
import lispa.schedulers.utils.PersonaleUtils;

public class CheckLinkPersonaleUnitaOrganizzativaFacade {
	private static Logger logger = Logger.getLogger(CheckLinkPersonaleUnitaOrganizzativaFacade.class);
	
	
	public static void execute(Timestamp dataEsecuzione) throws Exception,
	DAOException {
		try{
			if (ErrorManager.getInstance().hasError())
				return;
			
			QDmalmPersonale qPersonale = QDmalmPersonale.dmalmPersonale;		
			QDmalmProdotto qProdotto = QDmalmProdotto.dmalmProdotto;

			List<Tuple> personaleList = new ArrayList<Tuple>();
			logger.debug("START CheckLinkPersonaleUnitaOrganizzativaFacade");
			
			// tutto il Personale per il quale la FK Unità
			// Organizzativa è variata
			personaleList = PersonaleEdmaLispaDAO.getAllPersonaleUnitaOrganizzativa(dataEsecuzione);

			logger.debug("personaleList.size: " + personaleList.size());
			for (Tuple persRow : personaleList) {
				if (persRow != null) {
					logger.debug("CdPersonale: " + persRow.get(qPersonale.cdPersonale)
					+ " - dmalmUnitaOrganizzativaFk01: "
					+ persRow.get(qProdotto.dmalmUnitaOrganizzativaFk01));
					
					DmalmPersonale personale = new DmalmPersonale();
					personale.setCdPersonale(persRow.get(qPersonale.cdPersonale));
									
					List<Tuple> personaleTarget = PersonaleEdmaLispaDAO.getPersonaleEdmaLispa(personale);
					for (Tuple rowTarget : personaleTarget) {
						if (rowTarget != null) {
							personale = PersonaleUtils.tuplaToPersonale(rowTarget);
							personale.setDtCaricamento(dataEsecuzione);
							personale.setUnitaOrganizzativaFk(persRow.get(qProdotto.dmalmUnitaOrganizzativaFk01));
							Timestamp dt = rowTarget.get(qPersonale.dtCaricamento);
							if (dt.equals(dataEsecuzione)) {
								// update del valore della FK in quanto la riga
								// è nuova o storicizzata in questa esecuzione
								logger.debug("Eseguita update semplice della Fk di unita organizzativa");
								PersonaleEdmaLispaDAO.updateFkUnitaOrganizzativa(personale);
							} else {
								logger.debug("Eseguita storicizzazione");
								// Storicizzo la vecchia FK ed inserisco il
								// nuovo valore
								PersonaleEdmaLispaDAO.updateDataFineValidita(
										dataEsecuzione, personale);

								// inserisco un nuovo record
								PersonaleEdmaLispaDAO
										.insertPersonaleEdmaLispaUpdate(dataEsecuzione, personale);
							}		
						}
					}
										
				}
			}

			logger.debug("STOP CheckLinkPersonaleUnitaOrganizzativaFacade");

		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
