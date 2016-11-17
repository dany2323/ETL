package lispa.schedulers.facade.cleaning;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.oreste.FunzionalitaDAO;
import lispa.schedulers.dao.oreste.ModuliDAO;
import lispa.schedulers.dao.oreste.SottosistemiDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.queryimplementation.staging.oreste.QStgFunzionalita;
import lispa.schedulers.queryimplementation.staging.oreste.QStgModuli;
import lispa.schedulers.queryimplementation.staging.oreste.QStgSottosistemi;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.OresteToStringUtils;
import lispa.schedulers.utils.StringUtils;


public class CheckOresteSottosistemiFacade implements Runnable {

	private Logger logger;
	private Timestamp dataEsecuzione;
private boolean isAlive = true;;
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public CheckOresteSottosistemiFacade(Logger logger, Timestamp dataEsecuzione) {
		this.logger = logger;
		this.dataEsecuzione = dataEsecuzione;
		
		
	}
	
	@Override
	public void run() {
		
		execute(logger, dataEsecuzione);
		
		setAlive(false);
	}
	
	public static void execute (Logger logger, Timestamp dataEsecuzione) {	

		int erroriNonBloccanti = 0;
		
		try
		{
			erroriNonBloccanti += checkDuplicateNomiSottosistema(logger, dataEsecuzione);

			erroriNonBloccanti += checkNomiSottosistemaNull(logger, dataEsecuzione);

			erroriNonBloccanti += checkNomiSottosistemiAnnullatiLogicamente(logger, dataEsecuzione);
			
			EsitiCaricamentoDAO.insert
			(
				dataEsecuzione,
				DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
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

	protected static int checkDuplicateNomiSottosistema(Logger logger,  Timestamp dataEsecuzione) throws Exception{

		List<Tuple> nomisottosistemiDuplicate = SottosistemiDAO.getDuplicateNomeSottosistema(dataEsecuzione);
		
		int errori = 0;
		for(Tuple row : nomisottosistemiDuplicate)
		{
			errori++;
			ErroriCaricamentoDAO.insert(
					DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
					DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
					OresteToStringUtils.sottosistemaOrestetoString(row), 
					DmAlmConstants.SOTTOSISTEMA_NOME_DUPLICATO, 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}

		return errori;
	}

	protected static int checkNomiSottosistemaNull(Logger logger,  Timestamp dataEsecuzione) throws Exception{

		List<Tuple> nomiSottosistemiNull = SottosistemiDAO.getNomiSottosistemiNull(dataEsecuzione);
		
		int errori = 0;
		for(Tuple row : nomiSottosistemiNull)
		{
			errori++;
			ErroriCaricamentoDAO.insert
			(
					DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
					DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
					OresteToStringUtils.sottosistemaOrestetoString(row), 
					DmAlmConstants.SOTTOSISTEMA_NOME_NULL, 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}

		return errori;
	}


	protected static int checkNomiSottosistemiAnnullatiLogicamente(Logger logger,  Timestamp dataEsecuzione) throws Exception{

		QStgModuli 				  qstgModuli				 = QStgModuli.stgModuli;
		QStgSottosistemi		  qstgSottosistemi			 = QStgSottosistemi.stgSottosistemi;
		QStgFunzionalita		  qstgFunzionalita			 = QStgFunzionalita.stgFunzionalita;

		List<Tuple> sottosistemiAnnullati 	 = new ArrayList<Tuple>();
		List<Tuple> moduliNonAnnullati 	  	 = new ArrayList<Tuple>();
		List<Tuple> funzionalitaNonAnnullate = new ArrayList<Tuple>();
		List<Tuple> funzionalitaAnnullate			 = new ArrayList<Tuple>();
		List<Tuple> moduliAnnullati			 = new ArrayList<Tuple>();
		
		int errori = 0;
		
		boolean checkStringaAnnullamento 	= true;
		boolean checkAnnullamentoDateFormat = true;

		sottosistemiAnnullati = SottosistemiDAO.getSottosistemiAnnullati(qstgSottosistemi, dataEsecuzione);

		Date dataAnnullamentoSottosistema = null;

		for(Tuple row : sottosistemiAnnullati)
		{

			//			Se un Sottosistema Prodotto/ Arch Appl Oreste è ANNULLATO LOGICAMENTE: 
			//			La DATA FINE VALIDITA 
			//			- deve essere racchiusa tra due ## che si trovano subito dopo la scritta convenzionale indicante l'Annullamento Logico 		
			//			- deve essere in formato AAAAMMGG, valore valid

			checkStringaAnnullamento = StringUtils.checkStringAnnullamentoFormat(row.get(qstgSottosistemi.nomeSottosistema), logger);

			if(!checkStringaAnnullamento)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
						DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
						OresteToStringUtils.sottosistemaOrestetoString(row), 
						"Sottosistema.id "+row.get(qstgSottosistemi.idSottosistema)+" - "+DmAlmConstants.SOTTOSISTEMA_DATAFINEVALIDITA_NO_CANCELLETTI, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			checkAnnullamentoDateFormat = DateUtils.checkDataAnnullamentoFormat(row.get(qstgSottosistemi.nomeSottosistema), logger);

			if(!checkAnnullamentoDateFormat)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
						DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
						OresteToStringUtils.sottosistemaOrestetoString(row), 
						"Sottosistema.id "+row.get(qstgSottosistemi.idSottosistema)+" - "+DmAlmConstants.SOTTOSISTEMA_DATAFINEVALIDITA_FORMATO_NON_VALIDO, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// Data annullamento sottosistema
			dataAnnullamentoSottosistema = DateUtils.getDataAnnullamento(row.get(qstgSottosistemi.nomeSottosistema), logger);

			//			Se un Sottosistema di Prodotto/ Arch Appl Oreste è ANNULLATO LOGICAMENTE 
			//			tutti i suoi discendenti di ogni livello (eccetto AMBIENTE TECNOLOGICO) devono in corrispondenza recare la scritta 
			//			#ANNULLATO LOGICAMENTE L’ASCENDENTE# oppure #ANNULLATO LOGICAMENTE#

			if(row.get(qstgSottosistemi.siglaSottosistema)!=null)
			{
				
				moduliNonAnnullati = ModuliDAO.checkStatoModuliSottosistemaAnnullato(qstgModuli, row.get(qstgSottosistemi.siglaSottosistema), dataEsecuzione);
	
				if(moduliNonAnnullati!=null && moduliNonAnnullati.size()>0)
				{
					errori++;
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
							DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
							OresteToStringUtils.sottosistemaOrestetoString(row), 
							"Sottosistema.id "+row.get(qstgSottosistemi.idSottosistema)+" - "+DmAlmConstants.SOTTOSISTEMA_ANNULLATO_MODULO_NON_ANNULLATO, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
			
				funzionalitaNonAnnullate  = FunzionalitaDAO.checkStatoFunzionalitaSottosistemaAnnullato(qstgFunzionalita, row.get(qstgSottosistemi.siglaSottosistema), dataEsecuzione);
	
				if(funzionalitaNonAnnullate!=null && funzionalitaNonAnnullate.size()>0)
				{
					errori++;
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
							DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
							OresteToStringUtils.sottosistemaOrestetoString(row), 
							"Sottosistema.id "+row.get(qstgSottosistemi.idSottosistema)+" - "+DmAlmConstants.SOTTOSISTEMA_ANNULLATO_FUNZIONALITA_NON_ANNULLATA, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
			

			//			Se un Prodotto/ Arch Appl Oreste è #ANNULLATO  LOGICAMENTE# 
			//			2)  la DATA FINE VALIDITA' del discendente 
			//			- deve essere racchiusa tra due ## che si trovano subito dopo 
			//			la scritta convenzionale indicante l'Annullamento Logico 
			//			- deve essere in formato AAAAMMGG, valore valido e =< 
			//			della DATA FINE VALIDITA’ del Prodotto/ Arch Appl 

			// CONTROLLI SU DISCENDENTI MODULI
				
				moduliAnnullati = ModuliDAO.getModuliAnnullatiBySottosistema(qstgModuli, row.get(qstgSottosistemi.siglaSottosistema), dataEsecuzione);
	
				Date dataAnnullamentoModulo = null;
				for(Tuple el : moduliAnnullati)
				{
					checkStringaAnnullamento = StringUtils.checkStringAnnullamentoFormat(el.get(qstgModuli.nomeModulo), logger);
	
					if(!checkStringaAnnullamento)
					{
						errori++;
						ErroriCaricamentoDAO.insert
						(
								DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
								DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
								OresteToStringUtils.sottosistemaOrestetoString(row), 
								"Modulo.id "+el.get(qstgModuli.idModulo)+" - "+DmAlmConstants.SOTTOSISTEMA_DISCENDENTE_DATAFINEVALIDITA_NO_CANCELLETTI, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}
	
					checkAnnullamentoDateFormat = DateUtils.checkDataAnnullamentoFormat(el.get(qstgModuli.nomeModulo), logger);
	
					if(!checkAnnullamentoDateFormat)
					{
						errori++;
						ErroriCaricamentoDAO.insert
						(
								DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
								DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
								OresteToStringUtils.sottosistemaOrestetoString(row), 
								"Modulo.id "+el.get(qstgModuli.idModulo)+" - "+DmAlmConstants.SOTTOSISTEMA_DISCENDENTE_DATAFINEVALIDITA_FORMATO_NON_VALIDO, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}
					else
					{
	
						//Il formato della data di annullamento del modulo è corretta
						//se esiste ed è valida anche la data di annullamento del Sottosistema
						//vado ad effettuare il controllo
						if(dataAnnullamentoSottosistema!=null)
	
						//recupero la data di annullamento del modulo
						dataAnnullamentoModulo = DateUtils.getDataAnnullamento(el.get(qstgModuli.nomeModulo), logger);
	
						if(dataAnnullamentoModulo!=null && dataAnnullamentoModulo.after(dataAnnullamentoSottosistema))
						{
							errori++;
							ErroriCaricamentoDAO.insert
							(
									DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
									DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
									OresteToStringUtils.sottosistemaOrestetoString(row), 
									"Modulo.id "+el.get(qstgModuli.idModulo)+" - "+DmAlmConstants.MODULO_DATAFINEVALIDITA_SUCCESSIVA_SOTTOSISTEMA, 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}
	
					}
				}
			
			}

			// FINE CONTROLLI SU DESCENDENTI MODULI

			//CONTROLLI SU DISCENDENTI FUNZIONALITA
			
			if(row.get(qstgSottosistemi.siglaSottosistema)!=null)
			{

				funzionalitaAnnullate = FunzionalitaDAO.getFunzionalitaAnnullateBySottosistema(qstgFunzionalita, row.get(qstgSottosistemi.siglaSottosistema), dataEsecuzione);
	
				Date dataAnnullamentoFunzionalita = null;
				for(Tuple el : funzionalitaAnnullate)
				{
	
					checkStringaAnnullamento = StringUtils.checkStringAnnullamentoFormat(el.get(qstgFunzionalita.nomeFunzionalita), logger);
					if(!checkStringaAnnullamento)
					{
						errori++;
						ErroriCaricamentoDAO.insert
						(
								DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
								DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
								OresteToStringUtils.sottosistemaOrestetoString(row), 
								"Funzionalita.id "+el.get(qstgFunzionalita.idFunzionalita)+" - "+DmAlmConstants.SOTTOSISTEMA_DISCENDENTE_DATAFINEVALIDITA_NO_CANCELLETTI, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}
	
					checkAnnullamentoDateFormat = DateUtils.checkDataAnnullamentoFormat(el.get(qstgFunzionalita.nomeFunzionalita), logger);
	
					if(!checkAnnullamentoDateFormat)
					{
						errori++;
						ErroriCaricamentoDAO.insert
						(
								DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
								DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
								OresteToStringUtils.sottosistemaOrestetoString(row), 
								"Funzionalita.id "+el.get(qstgFunzionalita.idFunzionalita)+" - "+DmAlmConstants.SOTTOSISTEMA_DISCENDENTE_DATAFINEVALIDITA_FORMATO_NON_VALIDO, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}
					else
					{
	
						//Il formato della data di annullamento del modulo è corretta
						//se esiste ed è valida anche la data di annullamento del Sottosistema
						//vado ad effettuare il controllo
						if(dataAnnullamentoSottosistema!=null)
	
						//recupero la data di annullamento della funzionalita
						dataAnnullamentoFunzionalita = DateUtils.getDataAnnullamento(el.get(qstgFunzionalita.nomeFunzionalita), logger);
	
						if(dataAnnullamentoFunzionalita!=null && dataAnnullamentoFunzionalita.after(dataAnnullamentoSottosistema))
						{
							errori++;
							ErroriCaricamentoDAO.insert
							(
									DmAlmConstants.FONTE_ORESTE_SOTTOSISTEMA, 
									DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
									OresteToStringUtils.sottosistemaOrestetoString(row), 
									"Funzionalita.id "+el.get(qstgFunzionalita.idFunzionalita)+" - "+DmAlmConstants.FUNZIONALITA_DATAFINEVALIDITA_SUCCESSIVA_SOTTOSISTEMA, 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}
	
					}
				}			
			}

			// FINE CONTROLLI SU DESCENDENTI FUNZIONALITA
		}
		return errori;
	}


	
}