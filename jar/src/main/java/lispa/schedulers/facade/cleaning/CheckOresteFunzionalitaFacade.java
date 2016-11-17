package lispa.schedulers.facade.cleaning;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.oreste.FunzionalitaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.queryimplementation.staging.oreste.QStgFunzionalita;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.OresteToStringUtils;
import lispa.schedulers.utils.StringUtils;

public class CheckOresteFunzionalitaFacade implements Runnable {

	static 	QStgFunzionalita  qstgFunzionalita   = QStgFunzionalita.stgFunzionalita;
	private  Logger logger;
	private  Timestamp dataEsecuzione;
private boolean isAlive = true;;
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public CheckOresteFunzionalitaFacade (Logger log, Timestamp date) {
		this.logger = log;
		this.dataEsecuzione = date;
	}
	public static void execute (Logger logger, Timestamp dataEsecuzione) {	
		
		int erroriNonBloccanti = 0;
		
		try
		{
			// eliminato come condiviso con Rebottaro
			//checkDuplicatiSiglaInsiemeProdotto(logger, dataEsecuzione);
			//checkDuplicatiSiglaInsiemeSottosistema(logger, dataEsecuzione);
			
			erroriNonBloccanti += checkNomeFunzionalitaDuplicati(logger, dataEsecuzione);
			
			// eliminato come condiviso con Rebottaro
			//checkSiglaFunzionalitaNull(logger, dataEsecuzione);
			//checkSiglaFunzionalitaNull(logger, dataEsecuzione);
			
			erroriNonBloccanti += checkNomeFunzionalitaNull(logger, dataEsecuzione);
			
			erroriNonBloccanti += checkNomiFunzionalitaAnnullatiLogicamente(logger, dataEsecuzione);
			
			EsitiCaricamentoDAO.insert
			(
				dataEsecuzione,
				DmAlmConstants.TARGET_ORESTE_FUNZIONALITA, 
				DmAlmConstants.CARICAMENTO_IN_ATTESA, 
				null, 
				null, 
				0, 
				0, 
				erroriNonBloccanti, 
				0
			);	
		}
		catch (DAOException e) 
		{
			logger.error(e.getMessage(), e);
			
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage(), e);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}

		
	}
	
	protected static void checkDuplicatiSiglaInsiemeProdotto(Logger logger, Timestamp dataEsecuzione) throws Exception{
		
		List<Tuple> siglaFunzionalitaDuplicate = new ArrayList<Tuple>();
		
		siglaFunzionalitaDuplicate = FunzionalitaDAO.getDuplicateSiglaFunzionalitaInsiemeProdotto(qstgFunzionalita, dataEsecuzione);
		
		for(Tuple row : siglaFunzionalitaDuplicate)
		{
			
			ErroriCaricamentoDAO.insert(
									DmAlmConstants.FONTE_ORESTE_FUNZIONALITA, 
									DmAlmConstants.TARGET_ORESTE_FUNZIONALITA, 
									OresteToStringUtils.funzionalitaOrestetoString(row),
									DmAlmConstants.FUNZIONALITA_SIGLA_DUPLICATA_INSIEME_PRODOTTO, 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
		}
	}
	
	
	protected static void checkDuplicatiSiglaInsiemeSottosistema(Logger logger, Timestamp dataEsecuzione) throws Exception{
		
		List<Tuple> siglaFunzionalitaDuplicate = FunzionalitaDAO.getDuplicateSiglaFunzionalita(dataEsecuzione);
		
		for(Tuple row : siglaFunzionalitaDuplicate)
		{
			
			ErroriCaricamentoDAO.insert(
									DmAlmConstants.FONTE_ORESTE_FUNZIONALITA, 
									DmAlmConstants.TARGET_ORESTE_FUNZIONALITA, 
									OresteToStringUtils.funzionalitaOrestetoString(row),
									DmAlmConstants.FUNZIONALITA_SIGLA_DUPLICATA_INSIEME_SOTTOSISTEMA, 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
		}
	}
	
	protected static int checkNomeFunzionalitaDuplicati(Logger logger, Timestamp dataEsecuzione) throws Exception{
		
		QStgFunzionalita  qstgFunzionalita   = QStgFunzionalita.stgFunzionalita;
		
		List<Tuple> nomeFunzionalitaDuplicate = FunzionalitaDAO.getDuplicateNomeFunzionalita(dataEsecuzione);
		
		int errori = 0;
		
		for(Tuple row : nomeFunzionalitaDuplicate)
		{
			
			errori++;
			ErroriCaricamentoDAO.insert(
									DmAlmConstants.FONTE_ORESTE_FUNZIONALITA, 
									DmAlmConstants.TARGET_ORESTE_FUNZIONALITA,  
									OresteToStringUtils.funzionalitaOrestetoString(row), 
									DmAlmConstants.FUNZIONALITA_NOME_DUPLICATO + "SiglaProdotto: "+row.get(qstgFunzionalita.siglaProdotto)+", SiglaModulo: " +row.get(qstgFunzionalita.siglaModulo)+", NomeFunzionalita: "+row.get(qstgFunzionalita.nomeFunzionalita), 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
		}
		
		return errori;
	}
	
	protected static int checkSiglaFunzionalitaNull(Logger logger, Timestamp dataEsecuzione) throws Exception{
		
		List<Tuple> funzionalita = FunzionalitaDAO.getSiglaFunzionalitaNull(dataEsecuzione);
		
		int errori = 0;
		
		for(Tuple row : funzionalita)
		{
			errori++;
			ErroriCaricamentoDAO.insert
									(
										DmAlmConstants.FONTE_ORESTE_FUNZIONALITA, 
										DmAlmConstants.TARGET_ORESTE_FUNZIONALITA, 
										OresteToStringUtils.funzionalitaOrestetoString(row), 
										DmAlmConstants.FUNZIONALITA_SIGLA_NULL, 
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
		}
		
		return errori;
	}
	
	protected static int checkNomeFunzionalitaNull(Logger logger, Timestamp dataEsecuzione) throws Exception{
		
		
		List<Tuple> funzionalita = FunzionalitaDAO.getNomeFunzionalitaNull(dataEsecuzione);
		
		int errori = 0;
		
		for(Tuple row : funzionalita)
		{
			errori++;
			ErroriCaricamentoDAO.insert
									(
										DmAlmConstants.FONTE_ORESTE_FUNZIONALITA, 
										DmAlmConstants.TARGET_ORESTE_FUNZIONALITA,  
										OresteToStringUtils.funzionalitaOrestetoString(row),
										DmAlmConstants.FUNZIONALITA_NOME_NULL, 
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
		}
		
		return errori;
	}
	
	protected static int checkNomiFunzionalitaAnnullatiLogicamente(Logger logger, Timestamp dataEsecuzione) throws Exception{

		QStgFunzionalita		  qstgFunzionalita			 = QStgFunzionalita.stgFunzionalita;

		List<Tuple> funzionalitaAnnullate			 = new ArrayList<Tuple>();

		boolean checkStringaAnnullamento 	= true;
		boolean checkAnnullamentoDateFormat = true;
		
		int errori = 0;

		funzionalitaAnnullate = FunzionalitaDAO.getFunzionalitaAnnullate(qstgFunzionalita, dataEsecuzione);

		for(Tuple row : funzionalitaAnnullate)
		{

			//			Se una funzionalita è ANNULLATO LOGICAMENTE: 
			//			La DATA FINE VALIDITA 
			//			- deve essere racchiusa tra due ## che si trovano subito dopo la scritta convenzionale indicante l'Annullamento Logico 		
			//			- deve essere in formato AAAAMMGG

			checkStringaAnnullamento = StringUtils.checkStringAnnullamentoFormat(row.get(qstgFunzionalita.nomeFunzionalita), logger);

			if(!checkStringaAnnullamento)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ORESTE_FUNZIONALITA, 
						DmAlmConstants.TARGET_ORESTE_FUNZIONALITA, 
						OresteToStringUtils.funzionalitaOrestetoString(row),
						"Funzionalita.id "+row.get(qstgFunzionalita.idFunzionalita)+" - "+DmAlmConstants.FUNZIONALITA_DATAFINEVALIDITA_NO_CANCELLETTI, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			checkAnnullamentoDateFormat = DateUtils.checkDataAnnullamentoFormat(row.get(qstgFunzionalita.nomeFunzionalita), logger);

			if(!checkAnnullamentoDateFormat)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ORESTE_FUNZIONALITA, 
						DmAlmConstants.TARGET_ORESTE_FUNZIONALITA, 
						OresteToStringUtils.funzionalitaOrestetoString(row),
						"Funzionalita.id "+row.get(qstgFunzionalita.idFunzionalita)+" - "+DmAlmConstants.FUNZIONALITA_DATAFINEVALIDITA_FORMATO_NON_VALIDO, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}
		}
		
		return errori;
	}

	@Override
	public void run() {
		
		execute(logger, dataEsecuzione);
		
		setAlive(false);
		
	}
}