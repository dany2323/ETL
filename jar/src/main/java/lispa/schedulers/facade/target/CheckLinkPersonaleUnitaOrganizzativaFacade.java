package lispa.schedulers.facade.target;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.elettra.DmalmElPersonale;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProdotto;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElPersonale;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.utils.PersonaleUtils;

public class CheckLinkPersonaleUnitaOrganizzativaFacade {
	private static Logger logger = Logger.getLogger(CheckLinkPersonaleUnitaOrganizzativaFacade.class);
	
	
	public static void execute(Timestamp dataEsecuzione) throws Exception,
	DAOException {
		try{
			if (ErrorManager.getInstance().hasError())
				return;
			
			QDmalmElPersonale qPersonale = QDmalmElPersonale.qDmalmElPersonale;		
			QDmalmElProdottiArchitetture qProdotto = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;

			List<Tuple> personaleList = new ArrayList<Tuple>();
			logger.debug("START CheckLinkPersonaleUnitaOrganizzativaFacade");
			
			// tutto il Personale Elettra per il quale la FK Unità
			// Organizzativa è variata
			personaleList = ElettraPersonaleDAO.getAllPersonaleUnitaOrganizzativa(dataEsecuzione);

			logger.debug("personaleList.size: " + personaleList.size());
			for (Tuple persRow : personaleList) {
				if (persRow != null) {
					logger.debug("CdPersonale: " + persRow.get(qPersonale.codicePersonale)
					+ " - dmalmUnitaOrganizzativaFk01: "
					+ persRow.get(qProdotto.unitaOrganizzativaFk));
					
					DmalmElPersonale personale = new DmalmElPersonale();
					personale.setCodicePersonale(persRow.get(qPersonale.codicePersonale));
									
					List<Tuple> personaleTarget = ElettraPersonaleDAO.getPersonale(personale);
					for (Tuple rowTarget : personaleTarget) {
						if (rowTarget != null) {
							personale = PersonaleUtils.tuplaToElPersonale(rowTarget);
							personale.setDataCaricamento(dataEsecuzione);
							personale.setUnitaOrganizzativaFk(persRow.get(qProdotto.unitaOrganizzativaFk));
							Timestamp dt = rowTarget.get(qPersonale.dataCaricamento);
							if (dt.equals(dataEsecuzione)) {
								// update del valore della FK in quanto la riga
								// è nuova o storicizzata in questa esecuzione
								logger.debug("Eseguita update semplice della Fk di unita organizzativa");
								ElettraPersonaleDAO.updateFkUnitaOrganizzativa(personale);
							} else {
								logger.debug("Eseguita storicizzazione");
								// Storicizzo la vecchia FK ed inserisco il
								// nuovo valore
								ElettraPersonaleDAO.updateDataFineValidita(
										dataEsecuzione, personale);

								// inserisco un nuovo record
								ElettraPersonaleDAO
										.insertPersonaleUpdate(dataEsecuzione, personale);
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
