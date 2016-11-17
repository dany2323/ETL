package lispa.schedulers.facade.cleaning;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.elettra.StgElFunzionalitaDAO;
import lispa.schedulers.dao.elettra.StgElModuliDAO;
import lispa.schedulers.dao.elettra.StgElPersonaleDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElFunzionalita;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElModuli;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElPersonale;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.ElettraToStringUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckElettraModuliFacade implements Runnable {

	static QStgElModuli stgElModuli = QStgElModuli.stgElModuli;
	private Logger logger;
	private Timestamp dataEsecuzione;
	private boolean isAlive = true;;

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	@Override
	public void run() {

		execute(logger, dataEsecuzione);
		setAlive(false);
	}

	public CheckElettraModuliFacade(Logger log, Timestamp date) {
		this.logger = log;
		this.dataEsecuzione = date;
	}

	public static void execute(Logger logger, Timestamp dataEsecuzione) {
		int erroriNonBloccanti = 0;

		try {
			logger.debug("START CheckElettraModuliFacade.execute");

			// //il campo è obbligatorio (NOT NULL)

			erroriNonBloccanti = checkSiglaModuliNull(logger, dataEsecuzione);
			
			// non devono esistere SIGLE duplicate nell'insieme delle sigle di
			// modulo figli di uno specifico Prodotto, attenzione, un modulo può essere figlio
			// diretto di un Prodotto senza passare per un Sottosistema, quando ciò si
			// verifica la sigla del sottosistema dovrebbe diventare asterisco (*)
			erroriNonBloccanti+=checkSiglaModuloDuplicatiInsiemeProdotto(logger, dataEsecuzione);

			// non devono esistere SIGLE duplicate nell'insieme delle sigle di
			// modulo figli di uno specifico Sottosistema
			erroriNonBloccanti+=checkSiglaModuloDuplicatiInsiemeSottosistema(logger, dataEsecuzione);
			
			// il campo è obbligatorio (NOT NULL) 
	
			erroriNonBloccanti += checkNomeModuliNull(logger, dataEsecuzione);

			// non devono esistere nomi duplicati nell'insieme dei nomi di
			// modulo figli di uno specifico sottosistema
			erroriNonBloccanti+=checkNomiModuloDuplicatiInsiemeSottosistema(logger, dataEsecuzione);
			
			// non devono esistere nomi duplicati nell'insieme dei nomi di
			// modulo figli di uno specifico Prodotto
			erroriNonBloccanti+=checkNomiModuloDuplicatiInsiemeProdotto(logger, dataEsecuzione);

			erroriNonBloccanti += checkNomiModuliAnnullatiLogicamente(logger, dataEsecuzione);

			erroriNonBloccanti += checkResponsabileModulo(logger, dataEsecuzione);

			EsitiCaricamentoDAO.insert(dataEsecuzione,
					DmAlmConstants.TARGET_ELETTRA_MODULI,
					DmAlmConstants.CARICAMENTO_IN_ATTESA, null, null, 0, 0,
					erroriNonBloccanti, 0);

		} catch (DAOException e) {
			logger.error(e.getMessage(), e);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

		logger.debug("STOP CheckElettraModuliFacade.execute");
	}

	public static int checkSiglaModuloDuplicatiInsiemeProdotto(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		List<Tuple> siglaModuloDuplicate = new ArrayList<Tuple>();

		siglaModuloDuplicate = StgElModuliDAO.getDuplicateSiglaModuloInsiemeProdotto(stgElModuli,
						dataEsecuzione);

		int errori = 0;

		for (Tuple row : siglaModuloDuplicate) {

			errori++;

			ErroriCaricamentoDAO
					.insert(DmAlmConstants.FONTE_ELETTRA_MODULI,
							DmAlmConstants.TARGET_ELETTRA_MODULI,
							ElettraToStringUtils.moduloElettraToString(row),
							"Modulo.id "
									+ row.get(stgElModuli.idModulo)
									+ " - "
									+ DmAlmConstants.MODULO_SIGLA_DUPLICATO_INSIEME_PRODOTTO
									+ row.get(stgElModuli.siglaProdotto),
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
		}

		return errori;
	}

	public static int checkSiglaModuloDuplicatiInsiemeSottosistema(Logger logger, Timestamp dataEsecuzione) throws Exception{

		List<Tuple> siglaModuloDuplicate = new ArrayList<Tuple>();

		siglaModuloDuplicate = StgElModuliDAO.getDuplicateSiglaModuloInsiemeSottosistema(stgElModuli, dataEsecuzione);

		int errore = 0;

		for(Tuple row : siglaModuloDuplicate)
		{

			errore++;

			ErroriCaricamentoDAO.insert(
					DmAlmConstants.FONTE_ELETTRA_MODULI, 
					DmAlmConstants.TARGET_ELETTRA_MODULI, 
					ElettraToStringUtils.moduloElettraToString(row),
					"Modulo.id "+row.get(stgElModuli.idModulo) +" - "+DmAlmConstants.MODULO_SIGLA_DUPLICATO_INSIEME_SOTTOSISTEMA + row.get(stgElModuli.siglaSottosistema), 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}

		return errore;
	}

	public static int checkSiglaModuliNull(Logger logger, Timestamp dataEsecuzione ) throws Exception{

		List<Tuple> moduli = new ArrayList<Tuple>();
		int errori = 0;

		moduli = StgElModuliDAO.getSiglaModuliNull(dataEsecuzione);

		for(Tuple row : moduli)
		{
			errori++;
			ErroriCaricamentoDAO.insert
			(
					DmAlmConstants.FONTE_ELETTRA_MODULI, 
					DmAlmConstants.TARGET_ELETTRA_MODULI, 
					ElettraToStringUtils.moduloElettraToString(row),
					DmAlmConstants.MODULO_SIGLA_NULL, 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}

		return errori;
	}
	public static int checkNomiModuloDuplicatiInsiemeSottosistema(Logger logger, Timestamp dataEsecuzione) throws Exception{

		List<Tuple> nomeModuloDuplicate = new ArrayList<Tuple>();

		nomeModuloDuplicate = StgElModuliDAO.getDuplicateNomeModuloInsiemeSottosistema(stgElModuli, dataEsecuzione);

		int errori = 0;

		for(Tuple row : nomeModuloDuplicate)
		{
			errori++;

			ErroriCaricamentoDAO.insert
			(
					DmAlmConstants.FONTE_ELETTRA_MODULI, 
					DmAlmConstants.TARGET_ELETTRA_MODULI, 
					ElettraToStringUtils.moduloElettraToString(row),
					"Modulo.id "+row.get(stgElModuli.idModulo) +" - "+DmAlmConstants.MODULO_NOME_DUPLICATO_INSIEME_SOTTOSISTEMA + row.get(stgElModuli.siglaSottosistema), 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}

		return errori;
	}

	public static int checkNomiModuloDuplicatiInsiemeProdotto(Logger logger, Timestamp dataEsecuzione) throws Exception{

		List<Tuple> nomeModuloDuplicate  = new ArrayList<Tuple>();

		nomeModuloDuplicate = StgElModuliDAO.getDuplicateNomeModuloInsiemeProdotto(stgElModuli, dataEsecuzione);

		int errori = 0;

		for(Tuple row : nomeModuloDuplicate)
		{

			errori++;			
			ErroriCaricamentoDAO.insert
			(
					DmAlmConstants.FONTE_ELETTRA_MODULI, 
					DmAlmConstants.TARGET_ELETTRA_MODULI, 
					ElettraToStringUtils.moduloElettraToString(row),
					"Modulo.id "+row.get(stgElModuli.idModulo) +" - "+DmAlmConstants.MODULO_NOME_DUPLICATO_INSIEME_PRODOTTO + row.get(stgElModuli.siglaProdotto), 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}

		return errori;
	}

	public static int checkNomeModuliNull(Logger logger, Timestamp dataEsecuzione) throws Exception{

		List<Tuple> moduli = new ArrayList<Tuple>();

		moduli = StgElModuliDAO.getNomeModuliNull(dataEsecuzione);

		int errori = 0;

		for(Tuple row : moduli)
		{
			errori++;
			ErroriCaricamentoDAO.insert
			(
					DmAlmConstants.FONTE_ELETTRA_MODULI, 
					DmAlmConstants.TARGET_ELETTRA_MODULI, 
					ElettraToStringUtils.moduloElettraToString(row),
					DmAlmConstants.MODULO_NOME_NULL, 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}

		return errori;
	}

	public static int checkNomiModuliAnnullatiLogicamente(Logger logger, Timestamp dataEsecuzione) throws Exception{

		QStgElFunzionalita stgElFunzionalita = QStgElFunzionalita.stgElFunzionalita;

		List<Tuple> moduliAnnnullati 	 			= new ArrayList<Tuple>();
		List<Tuple> funzionalitaNonAnnullate 		= new ArrayList<Tuple>();
		List<Tuple> funzionalitaAnnullate			= new ArrayList<Tuple>();

		boolean checkStringaAnnullamento 	= true;
		boolean checkAnnullamentoDateFormat = true;

		moduliAnnnullati = StgElModuliDAO.getModuliAnnullati(stgElModuli, dataEsecuzione);

		Date dataAnnullamentoModulo = null;
		int errori = 0;

		for(Tuple row : moduliAnnnullati)
		{

			//			Se un Modulo è ANNULLATO LOGICAMENTE: 
			//			La DATA FINE VALIDITA 
			//			- deve essere racchiusa tra due ## che si trovano subito dopo la scritta convenzionale indicante l'Annullamento Logico 		
			//			- deve essere in formato AAAAMMGG

			checkStringaAnnullamento = StringUtils.checkStringAnnullamentoFormat(row.get(stgElModuli.nome), logger);

			if(!checkStringaAnnullamento)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ELETTRA_MODULI, 
						DmAlmConstants.TARGET_ELETTRA_MODULI, 
						ElettraToStringUtils.moduloElettraToString(row),
						"Modulo.id "+row.get(stgElModuli.idModulo)+" - "+DmAlmConstants.MODULO_DATAFINEVALIDITA_NO_CANCELLETTI, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			checkAnnullamentoDateFormat = DateUtils.checkDataAnnullamentoFormat(row.get(stgElModuli.nome), logger);

			if(!checkAnnullamentoDateFormat)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ELETTRA_MODULI, 
						DmAlmConstants.TARGET_ELETTRA_MODULI, 
						ElettraToStringUtils.moduloElettraToString(row),
						"Modulo.id "+row.get(stgElModuli.idModulo)+" - "+DmAlmConstants.MODULO_DATAFINEVALIDITA_FORMATO_NON_VALIDO, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// Data annullamento modulo
			dataAnnullamentoModulo = DateUtils.getDataAnnullamento(row.get(stgElModuli.nome), logger);

			//			Se un Modulo di Prodotto/ Arch Appl Elettra è ANNULLATO LOGICAMENTE 
			//			tutti i suoi discendenti di ogni livello (eccetto AMBIENTE TECNOLOGICO) devono in corrispondenza recare la scritta 
			//			#ANNULLATO LOGICAMENTE L’ASCENDENTE# oppure #ANNULLATO LOGICAMENTE#

			if(row.get(stgElModuli.siglaModulo)  !=null)
			{

				funzionalitaNonAnnullate  = StgElFunzionalitaDAO.checkStatoFunzionalitaModuloAnnullato(stgElFunzionalita, row.get(stgElModuli.siglaModulo), dataEsecuzione);

				if(funzionalitaNonAnnullate!=null && funzionalitaNonAnnullate.size()>0)
				{
					errori++;
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_ELETTRA_MODULI, 
							DmAlmConstants.TARGET_ELETTRA_MODULI, 
							ElettraToStringUtils.moduloElettraToString(row),
							"Modulo.id "+row.get(stgElModuli.idModulo)+" - "+DmAlmConstants.MODULO_ANNULLATO_FUNZIONALITA_NON_ANNULLATA, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}

				//			Se un Prodotto/ Arch Appl Elettra è #ANNULLATO  LOGICAMENTE# 
				//			2)  la DATA FINE VALIDITA' del discendente 
				//			- deve essere racchiusa tra due ## che si trovano subito dopo 
				//			la scritta convenzionale indicante l'Annullamento Logico 
				//			- deve essere in formato AAAAMMGG, valore valido e =< 
				//			della DATA FINE VALIDITA’ del Prodotto/ Arch Appl 



				//CONTROLLI SU DISCENDENTI FUNZIONALITA

				funzionalitaAnnullate = StgElFunzionalitaDAO.getFunzionalitaAnnullateByModulo(stgElFunzionalita, row.get(stgElModuli.siglaModulo), dataEsecuzione);

				Date dataAnnullamentoFunzionalita = null;
				for(Tuple el : funzionalitaAnnullate)
				{

					checkStringaAnnullamento = StringUtils.checkStringAnnullamentoFormat(el.get(stgElFunzionalita.nome), logger);
					if(!checkStringaAnnullamento)
					{
						errori++;
						ErroriCaricamentoDAO.insert
						(

								DmAlmConstants.FONTE_ELETTRA_MODULI, 
								DmAlmConstants.TARGET_ELETTRA_MODULI, 
								ElettraToStringUtils.moduloElettraToString(row),
								"Funzionalita.id "+el.get(stgElFunzionalita.idFunzionalia)+" - "+DmAlmConstants.MODULO_DISCENDENTE_DATAFINEVALIDITA_NO_CANCELLETTI, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}

					checkAnnullamentoDateFormat = DateUtils.checkDataAnnullamentoFormat(el.get(stgElFunzionalita.nome), logger);

					if(!checkAnnullamentoDateFormat)
					{
						errori++;
						ErroriCaricamentoDAO.insert
						(
								DmAlmConstants.FONTE_ELETTRA_MODULI, 
								DmAlmConstants.TARGET_ELETTRA_MODULI, 
								ElettraToStringUtils.moduloElettraToString(row),
								"Funzionalita.id "+el.get(stgElFunzionalita.idFunzionalia)+" - "+DmAlmConstants.MODULO_DISCENDENTE_DATAFINEVALIDITA_FORMATO_NON_VALIDO, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}
					else
					{

						//Il formato della data di annullamento del modulo è corretta
						//se esiste ed è valida anche la data di annullamento del Sottosistema
						//vado ad effettuare il controllo
						if(dataAnnullamentoModulo!=null)

							//recupero la data di annullamento della funzionalita
							dataAnnullamentoFunzionalita = DateUtils.getDataAnnullamento(el.get(stgElFunzionalita.nome), logger);

						if(dataAnnullamentoFunzionalita!=null && dataAnnullamentoFunzionalita.after(dataAnnullamentoModulo))
						{
							errori++;
							ErroriCaricamentoDAO.insert
							(
									DmAlmConstants.FONTE_ELETTRA_MODULI, 
									DmAlmConstants.TARGET_ELETTRA_MODULI, 
									ElettraToStringUtils.moduloElettraToString(row),
									"Funzionalita.id "+el.get(stgElFunzionalita.idFunzionalia)+" - "+DmAlmConstants.FUNZIONALITA_DATAFINEVALIDITA_SUCCESSIVA_MODULO, 
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

	public static int checkResponsabileModulo(Logger logger, Timestamp dataEsecuzione) throws Exception {

//		QStgPersonale             qstgPersonale				 = QStgPersonale.stgPersonale;
		QStgElPersonale stgElPersonale = QStgElPersonale.stgElPersonale;

		List<Tuple> moduli;
		moduli = StgElModuliDAO.getAllModuli(stgElModuli, dataEsecuzione);

		int errori = 0;

		for(Tuple row : moduli) {

			String codiceResponsabile = getCodiceResponsabileModulo(row.get(stgElModuli.responsabile));

			if(codiceResponsabile != null )
			{	
				// richiesta Rebottaro : quando le parentesi graffe contengono NULL non va effettuata nessuna segnalazione
				if(!codiceResponsabile.equals("NULL"))
				{
					// se l'oggetto Elettra Prodotto è diverso da NULL e NON è annullato (#ANNULLATO LOGICAMENTE## ecc)
					if(row!=null && row.get(stgElModuli.nome)!= null 
							&& ( !row.get(stgElModuli.nome).toLowerCase().contains("ANNULLATO".toLowerCase()) ))
					{
						if(StgElPersonaleDAO.getPersonaleByCodice(stgElPersonale, codiceResponsabile, dataEsecuzione).size()==0)
						{
							errori++;
							ErroriCaricamentoDAO.insert
							(
									DmAlmConstants.FONTE_ELETTRA_MODULI, 
									DmAlmConstants.TARGET_ELETTRA_MODULI, 
									ElettraToStringUtils.moduloElettraToString(row),
									"Modulo.id "+row.get(stgElModuli.idModulo)+" - "+DmAlmConstants.RESPONSABILE_MODULO_NON_PERSONAFISICA + row.get(stgElModuli.responsabile), 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}
					}
				}
			}
			else
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ELETTRA_MODULI, 
						DmAlmConstants.TARGET_ELETTRA_MODULI, 
						ElettraToStringUtils.moduloElettraToString(row),
						"Modulo.id "+row.get(stgElModuli.idModulo)+" - "+DmAlmConstants.RESPONSABILE_MODULO_NON_PRESENTE , 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}
		}

		return errori;
	}

	public static String getCodiceResponsabileModulo(String responsabile) throws Exception
	{
		String codice = "";

		//MASTRANGELO GIUSEPPE MARIA (LIA303) {GMASTRANGELO}
		//diventa
		//GMASTRANGELO

		if(responsabile!=null && !responsabile.equals(""))
		{	
			if(responsabile.indexOf("{")!= -1 && responsabile.indexOf("}")!=-1)
			{
				codice = responsabile.split("\\{")[1].split("\\}")[0] ;

				codice = codice.equals("") ? null : codice;
			}
			else
			{
				codice = null;
			}
		}
		else
		{
			codice = null;
		}

		return codice;
	}
	
}
