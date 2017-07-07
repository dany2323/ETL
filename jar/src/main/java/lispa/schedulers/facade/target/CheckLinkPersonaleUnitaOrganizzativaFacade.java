package lispa.schedulers.facade.target;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.elettra.DmalmElPersonale;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElPersonale;
import lispa.schedulers.utils.PersonaleUtils;

public class CheckLinkPersonaleUnitaOrganizzativaFacade {
	private static Logger logger = Logger.getLogger(CheckLinkPersonaleUnitaOrganizzativaFacade.class);
	
	
	public static void execute(Timestamp dataEsecuzione) throws Exception,
	DAOException {
		try{
			if (ErrorManager.getInstance().hasError())
				return;
			
			QDmalmElPersonale qPersonale = QDmalmElPersonale.qDmalmElPersonale;		

			logger.debug("START CheckLinkPersonaleUnitaOrganizzativaFacade");
			
			// tutto il Personale Elettra per il quale la FK Unità
			// Organizzativa è variata
			ResultSet allChangedPersonale = ElettraPersonaleDAO.getAllPersonaleUnitaOrganizzativa();

			if (allChangedPersonale.last()) {
				logger.debug("Trovate "+ allChangedPersonale.getRow() + " record movimentati (nuovi+modificati) nella DMALM_EL_PERSONALE");
			} else {
				logger.debug("Non sono stati trovati record movimentati (nuovi+modificati) nella DMALM_EL_PERSONALE");
				logger.debug("STOP CheckLinkPersonaleUnitaOrganizzativaFacade");
				return;
			}
			allChangedPersonale.beforeFirst();
			
			while (allChangedPersonale.next()) {
				String cdPersonale = allChangedPersonale.getString("CD_PERSONALE");
				Integer uoFk = allChangedPersonale.getInt("UO_PK");
				
				logger.debug("DMALM_EL_PERSONALE.CD_PERSONALE: " + cdPersonale
				+ " - DMALM_EL_UNITA_ORGANIZZATIVE.DMALM_UNITAORGANIZZATIVA_FK_01: "
				+ uoFk);
				
				DmalmElPersonale personale = new DmalmElPersonale();
				personale.setCodicePersonale(cdPersonale);
								
				List<Tuple> personaleTarget = ElettraPersonaleDAO.getPersonale(personale);
				for (Tuple rowTarget : personaleTarget) {
					if (rowTarget != null) {
						personale = PersonaleUtils.tuplaToElPersonale(rowTarget);
						personale.setDataCaricamento(dataEsecuzione);
						personale.setUnitaOrganizzativaFk(uoFk);
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

							personale.setPersonalePk(ElettraPersonaleDAO.getPersonalePk());
							
							// inserisco un nuovo record
							ElettraPersonaleDAO
									.insertPersonaleUpdate(dataEsecuzione, personale);
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
