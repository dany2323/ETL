package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmClassificatoreDemand;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ClassificatoreDemOdsDAO;
import lispa.schedulers.dao.target.fatti.ClassificatoreDemandDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmClassificatoreDemand;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ClassificatoreDemandFacade {

private static Logger logger = Logger.getLogger(PeiFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	
		
		List <DmalmClassificatoreDemand> stg_class = new ArrayList<DmalmClassificatoreDemand>();
		List <Tuple> target_class = new ArrayList<Tuple>();
		QDmalmClassificatoreDemand c = QDmalmClassificatoreDemand.dmalmClassificatoreDemand;
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmClassificatoreDemand temp = null;
		
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;
		
		try{
			stg_class = ClassificatoreDemandDAO.getAllClassDem(dataEsecuzione);
			
			ClassificatoreDemOdsDAO.delete();
			
			logger.debug("START -> Popolamento CLASSIFICATORE DEMAND ODS, "+stg_class.size()+ " Classificatore demand");
			
			ClassificatoreDemOdsDAO.insert(stg_class, dataEsecuzione);
			
			List<DmalmClassificatoreDemand> x = ClassificatoreDemOdsDAO.getAll();
			
			logger.debug("STOP -> Popolamento CLASSIFICATORE DEMAND ODS, "+stg_class.size()+ " Classificatore demand");
			
			for (DmalmClassificatoreDemand cDem : x) {
				
				temp = cDem;
				// Ricerco nel db target un record con idProject = project.getIdProject e data fine validita uguale a 31-12-9999
				target_class = ClassificatoreDemandDAO.getClassDem(cDem);
				// se non trovo almento un record, inserisco il project nel target
				if(target_class.size()==0)
				{ 
					righeNuove++;
					cDem.setDtCambioStatoClassif(cDem.getDtModificaClassif());
					ClassificatoreDemandDAO.insert(cDem);
				} else {
					boolean modificato = false;
					
					for (Tuple row : target_class) {
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(c.dmalmStatoWorkitemFk03), cDem.getDmalmStatoWorkitemFk03()))
							{								
								cDem.setDtCambioStatoClassif(cDem.getDtModificaClassif());
								modificato = true;
							} else {
								cDem.setDtCambioStatoClassif(row.get(c.dtCambioStatoClassif));
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.titoloClassificatore), cDem.getTitoloClassificatore()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.cf_area), cDem.getCf_area()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.cf_ambito), cDem.getCf_ambito()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.cf_riferimenti), cDem.getCf_riferimenti()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.cf_scheda_servizio), cDem.getCf_scheda_servizio()))
							{
								modificato = true;
							}
							if(!modificato && BeanUtils.areDifferent(row.get(c.annullato), cDem.getAnnullato()))
							{
								modificato = true;
							}
							
							if(modificato)
							{
								// STORICIZZO
								righeModificate++;								
								// aggiorno la data di fine validita sul record corrente								
								ClassificatoreDemandDAO.updateRank(cDem, new Double(0));

								// inserisco un nuovo record
								ClassificatoreDemandDAO.insertClassDemUpdate(dataEsecuzione, cDem, true);	
								
							}
							else
							{
    							 // Aggiorno lo stesso
								ClassificatoreDemandDAO.updateClassdem(cDem);
							}
						}
					}
				}
			}
			
			
		}catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(temp));
			logger.error(e.getMessage(), e);
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
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
							DmAlmConstants.TARGET_CLASSIFICATORE_DEMAND, 
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
