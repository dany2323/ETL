package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmClassificatore;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ClassificatoreOdsDAO;
import lispa.schedulers.dao.target.fatti.ClassificatoreDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmClassificatore;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ClassificatoreFacade {

	ClassificatoreFacade() {}
private static Logger logger = Logger.getLogger(PeiFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception{	
		
		List <DmalmClassificatore> stgClassDem = new ArrayList<>();
		List <DmalmClassificatore> stgClass = new ArrayList<>();
		List <Tuple> targetClass = new ArrayList<>();
		QDmalmClassificatore c = QDmalmClassificatore.dmalmClassificatore;
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmClassificatore temp = null;
		
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;
		
		try{
			stgClassDem = ClassificatoreDAO.getAllClassDem(dataEsecuzione);
			stgClass = ClassificatoreDAO.getAllClass(dataEsecuzione);

			ClassificatoreOdsDAO.delete();
			
			logger.debug("START -> Popolamento CLASSIFICATORE ODS, "+stgClassDem.size()+ " Classificatore demand");
			ClassificatoreOdsDAO.insert(stgClassDem);
			logger.debug("STOP -> Popolamento CLASSIFICATORE DEMAND ODS, "+stgClassDem.size()+ " Classificatore demand");
			
			logger.debug("START -> Popolamento CLASSIFICATORE ODS, "+stgClass.size()+ " Classificatore");
			ClassificatoreOdsDAO.insert(stgClass);
			logger.debug("STOP -> Popolamento CLASSIFICATORE DEMAND ODS, "+stgClass.size()+ " Classificatore");
			
			List<DmalmClassificatore> x = ClassificatoreOdsDAO.getAll();

			for (DmalmClassificatore classificatore : x) {
				
				temp = classificatore;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999
				targetClass = ClassificatoreDAO.getClassificatore(classificatore);
				// se non trovo almento un record, inserisco il project nel target
				if(targetClass.isEmpty())
				{ 
					righeNuove++;
					classificatore.setDtCambioStatoClassif(classificatore.getDtModificaClassif());
					ClassificatoreDAO.insert(classificatore);
				} else {
					boolean modificato = false;
					
					for (Tuple row : targetClass) {
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(c.dmalmStatoWorkitemFk03), classificatore.getDmalmStatoWorkitemFk03()))
							{								
								classificatore.setDtCambioStatoClassif(classificatore.getDtModificaClassif());
								modificato = true;
							} else {
								classificatore.setDtCambioStatoClassif(row.get(c.dtCambioStatoClassif));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.titoloClassificatore), classificatore.getTitoloClassificatore()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.cf_area), classificatore.getCf_area()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.cf_ambito), classificatore.getCf_ambito()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.cf_riferimenti), classificatore.getCf_riferimenti()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.cf_scheda_servizio), classificatore.getCf_scheda_servizio()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.annullato), classificatore.getAnnullato()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.rmResponsabiliProgetto), classificatore.getRmResponsabiliProgetto()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.progettoInDeroga), classificatore.isProgettoInDeroga()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.assigneeProgettoItInDeroga), classificatore.getAssigneeProgettoItInDeroga()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.locationSorgenti), classificatore.getLocationSorgenti()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.codiceServizi), classificatore.getCodiceServizi()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.severity), classificatore.getSeverity()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.priority), classificatore.getPriority()))
							{
								modificato = true;
							}
							
							if(modificato)
							{
								// STORICIZZO
								righeModificate++;								
								// aggiorno la data di fine validita sul record corrente								
								ClassificatoreDAO.updateRank(classificatore,Double.valueOf(0));

								// inserisco un nuovo record
								ClassificatoreDAO.insertClassUpdate(dataEsecuzione, classificatore, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								ClassificatoreDAO.updateClass(classificatore);
							}
						}
					}
				}
			}
			
			
		}catch (Exception e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(temp));
			logger.error(e.getMessage(), e);
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally
		{
			dtFineCaricamento = new Date();

			try {
				
				EsitiCaricamentoDAO.insert
				(
							dataEsecuzione,
							DmAlmConstants.TARGET_CLASSIFICATORE, 
							stato, 
							new Timestamp(dtInizioCaricamento.getTime()), 
							new Timestamp(dtFineCaricamento.getTime()), 
							righeNuove, 
							righeModificate, 
							0, 
							0
				);	
			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);
				
			}
		}
		
		
		
		
		
		
	}
}
