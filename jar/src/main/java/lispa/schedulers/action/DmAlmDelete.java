package lispa.schedulers.action;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.ProjectRolesDAO;
import lispa.schedulers.dao.StatoWorkitemDAO;
import lispa.schedulers.dao.UserRolesDAO;
import lispa.schedulers.dao.edma.PersonaleDAO;
import lispa.schedulers.dao.edma.UnitaOrganizzativaDAO;
import lispa.schedulers.dao.oreste.AmbienteTecnologicoDAO;
import lispa.schedulers.dao.oreste.ClassificatoriDAO;
import lispa.schedulers.dao.oreste.FunzionalitaDAO;
import lispa.schedulers.dao.oreste.ModuliDAO;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.dao.oreste.SottosistemiDAO;
import lispa.schedulers.dao.sgr.sire.current.SireCurrentProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;

import org.apache.log4j.Logger;

public class DmAlmDelete {

	// formato input = java -jar <nomejar> <mainclass> data nometabella
	// formato data = dd-MM-yyyy
	// tabella = * significa tutte le tabelle
	
	private static Logger logger = Logger.getLogger(DmAlmDelete.class); 

	public static void main(String[] args) {
		
		Timestamp dataEsecuzione = null;

		if (args.length != 3) 
		{
			logger.error("L'input deve avere il formato: ./delete.sh dd-MM-yyyy nometabella");
			return;
		}
		
		try
		{	
			Date data = stringToDate(args[1], "dd-MM-yyyy");
			dataEsecuzione = new Timestamp(data.getTime());
		
		} catch (ParseException exception)
		{
			logger.error(exception.getMessage(), exception);
			return;
		}

		String tabella = args[2];
		
		
		try {
		
			switch(tabella) {
			
			case "all":
				ErroriCaricamentoDAO.deleteInDate(dataEsecuzione);
				EsitiCaricamentoDAO.deleteInDate(dataEsecuzione);
				PersonaleDAO.deleteInDate(dataEsecuzione);
				UnitaOrganizzativaDAO.deleteInDate(dataEsecuzione);
				ProdottiArchitettureDAO.deleteInDate(dataEsecuzione);
				ModuliDAO.deleteInDate(dataEsecuzione);
				FunzionalitaDAO.deleteInDate(dataEsecuzione);
				SottosistemiDAO.deleteInDate(dataEsecuzione);
				ClassificatoriDAO.deleteInDate(dataEsecuzione);
				AmbienteTecnologicoDAO.deleteInDate(dataEsecuzione);
				SireCurrentProjectDAO.deleteInDate(dataEsecuzione);
				ProjectRolesDAO.deleteInDate(dataEsecuzione);
				StatoWorkitemDAO.deleteInDate(dataEsecuzione);
				UserRolesDAO.deleteInDate(dataEsecuzione);	
				SireHistoryWorkitemDAO.deleteInDate(dataEsecuzione);
				SireHistoryCfWorkitemDAO.deleteInDate(dataEsecuzione);
				SissCurrentProjectDAO.deleteInDate(dataEsecuzione);
				SissHistoryWorkitemDAO.deleteInDate(dataEsecuzione);
				SissHistoryCfWorkitemDAO.deleteInDate(dataEsecuzione);
				break;
			
			case "DMALM_ERRORI_CARICAMENTO":
				ErroriCaricamentoDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_ESITI_CARICAMENTI":
				EsitiCaricamentoDAO.deleteInDate(dataEsecuzione);
				
			case "DMALM_STG_PERSONALE":
				PersonaleDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_STG_UNITA_ORGANIZZATIVE":
				UnitaOrganizzativaDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_STG_PROD_ARCHITETTURE":
				ProdottiArchitettureDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_STG_MODULI":
				ModuliDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_STG_FUNZIONALITA":
				FunzionalitaDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_STG_SOTTOSISTEMI":
				SottosistemiDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_STG_CLASSIFICATORI":
				ClassificatoriDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_STG_AMBIENTE_TECNOLOGICO":
				AmbienteTecnologicoDAO.deleteInDate(dataEsecuzione);
				break;
				

				
			case "DMALM_SIRE_CURRENT_PROJECT":	
				SireCurrentProjectDAO.deleteInDate(dataEsecuzione);
				break;
				

				
			case "DMALM_PROJECT_ROLES":	
				ProjectRolesDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_STATO_WORKITEM":	
				StatoWorkitemDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_USER_ROLES":			
				UserRolesDAO.deleteInDate(dataEsecuzione);	
				break;
				
			case "DMALM_SIRE_HISTORY_WORKITEM":
				SireHistoryWorkitemDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_SIRE_HISTORY_CF_WORKITEM":
				SireHistoryCfWorkitemDAO.deleteInDate(dataEsecuzione);
				break;

				
			case "DMALM_SISS_CURRENT_PROJECT":	
				SissCurrentProjectDAO.deleteInDate(dataEsecuzione);
				break;
				

				
			case "DMALM_SISS_HISTORY_WORKITEM":
				SissHistoryWorkitemDAO.deleteInDate(dataEsecuzione);
				break;
				
			case "DMALM_SISS_HISTORY_CF_WORKITEM":
				SissHistoryCfWorkitemDAO.deleteInDate(dataEsecuzione);
				break;
				
			default :
				return;
			}

		}
		catch(Exception e) 
		{
			logger.error(e.getMessage(), e);
			
		}
	}	
	
	public static Date stringToDate(String data, String ft) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat(ft);

		Date dataFineValidita = null;
		format.setLenient(false);
		dataFineValidita=  format.parse(data);


		return dataFineValidita;
	}

}
