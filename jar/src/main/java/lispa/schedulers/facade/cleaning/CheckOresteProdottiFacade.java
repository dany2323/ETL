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
import lispa.schedulers.dao.edma.PersonaleDAO;
import lispa.schedulers.dao.edma.UnitaOrganizzativaDAO;
import lispa.schedulers.dao.oreste.FunzionalitaDAO;
import lispa.schedulers.dao.oreste.ModuliDAO;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.dao.oreste.SottosistemiDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.queryimplementation.staging.edma.QStgPersonale;
import lispa.schedulers.queryimplementation.staging.edma.QStgUnitaOrganizzative;
import lispa.schedulers.queryimplementation.staging.oreste.QStgFunzionalita;
import lispa.schedulers.queryimplementation.staging.oreste.QStgModuli;
import lispa.schedulers.queryimplementation.staging.oreste.QStgProdottiArchitetture;
import lispa.schedulers.queryimplementation.staging.oreste.QStgSottosistemi;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.OresteToStringUtils;
import lispa.schedulers.utils.StringUtils;


public class CheckOresteProdottiFacade implements Runnable {

	private Logger logger;
	private Timestamp dataEsecuzione;
private boolean isAlive = true;;
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public CheckOresteProdottiFacade(Logger logger, Timestamp dataEsecuzione) {
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
		
			
			erroriNonBloccanti += checkDuplicateNomiProdotto(logger, dataEsecuzione);
		

			erroriNonBloccanti += checkDuplicateSiglaProdotto(logger, dataEsecuzione);
			

			erroriNonBloccanti += checkSigleProdottoNull(logger, dataEsecuzione);
			

			erroriNonBloccanti += checkNomiProdottoNull(logger, dataEsecuzione);
		

			erroriNonBloccanti += checkNameProdottoAnnullatoLogicamente(logger, dataEsecuzione);
		
			
			erroriNonBloccanti += checkAreaProdotto(logger, dataEsecuzione);
	
			
			erroriNonBloccanti += checkResponsabileProdotto(logger, dataEsecuzione);
			
			
			EsitiCaricamentoDAO.insert
			(
				dataEsecuzione,
				DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
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

	private static int checkResponsabileProdotto(Logger logger,	Timestamp dataEsecuzione) throws Exception {
		
		QStgProdottiArchitetture  stgProdottiArchitetture   = QStgProdottiArchitetture.stgProdottiArchitetture;
		QStgPersonale             stgPersonale				 = QStgPersonale.stgPersonale;
		
		List<Tuple> prodotti = ProdottiArchitettureDAO.getAllProdottiArchitetture(stgProdottiArchitetture, dataEsecuzione);
		int errori = 0;
		
		for(Tuple row : prodotti) {
			String codiceResponsabile = getCodiceResponsabileProdotto(row.get(stgProdottiArchitetture.edmaRespProdotto));
			//
						if(codiceResponsabile != null)
						{	
							// se l'oggetto oreste Prodotto è diverso da NULL e NON è annullato (#ANNULLATO LOGICAMENTE## ecc)
							if(row!=null && row.get(stgProdottiArchitetture.nomeProdotto)!= null 
									&& ( !row.get(stgProdottiArchitetture.nomeProdotto).toLowerCase().contains("ANNULLATO".toLowerCase()) ))
							{
								if(PersonaleDAO.getPersonaleByCodice(stgPersonale, codiceResponsabile, dataEsecuzione).size()==0)
								{
									errori++;
									ErroriCaricamentoDAO.insert
									(
											DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
											DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
											OresteToStringUtils.prodottoOrestetoString(row), 
											"Prodotto.id "+row.get(stgProdottiArchitetture.idProdotto)+" - "+DmAlmConstants.RESPONSABILE_PRODOTTO_NON_PERSONAFISICA+ row.get(stgProdottiArchitetture.edmaRespProdotto), 
											DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
											dataEsecuzione
									);
								}
							}
						}
						else
						{
			
							errori++;
							ErroriCaricamentoDAO.insert
							(
									DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
									DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
									OresteToStringUtils.prodottoOrestetoString(row), 
									"Prodotto.id "+row.get(stgProdottiArchitetture.idProdotto)+" - "+DmAlmConstants.RESPONSABILE_PRODOTTO_NON_PRESENTE, 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione
							);
						}
		}
		return errori;
	}

	private static int checkAreaProdotto(Logger logger, Timestamp dataEsecuzione) throws Exception {
		
		QStgProdottiArchitetture  stgProdottiArchitetture   = QStgProdottiArchitetture.stgProdottiArchitetture;
		QStgUnitaOrganizzative    qstgUnitaOrganizzative     = QStgUnitaOrganizzative.stgUnitaOrganizzative;
		QStgPersonale             qstgPersonale				 = QStgPersonale.stgPersonale;
		
		List<Tuple> unitaOrganizzative = new ArrayList<Tuple>();
		List<Tuple> prodotti = new ArrayList<Tuple>();
		
		prodotti = ProdottiArchitettureDAO.getAllProdottiArchitetture(stgProdottiArchitetture, dataEsecuzione);
		int errori = 0;
		
		for(Tuple row : prodotti) {
			String areaEDMA = getAreaProdottoByEdmaAreaProdotto(row.get(stgProdottiArchitetture.edmaAreaProdotto));
			//
						if(areaEDMA != null && !areaEDMA.equals(""))
						{
							unitaOrganizzative = UnitaOrganizzativaDAO.getUnitaOrganizzativaByCodiceArea(qstgUnitaOrganizzative, row.get(stgProdottiArchitetture.edmaAreaProdotto), dataEsecuzione);
			
							for(Tuple area : unitaOrganizzative )
							{
								// dovrebbe (usualmente) essere un Area (attributo codice tipologia ufficio) della strauttura organizzativa
			
								if(area.get(qstgUnitaOrganizzative.dataFineValidita) !=null)
								{
			
									Date dataFineValidita = DateUtils.stringToDate(area.get(qstgUnitaOrganizzative.dataFineValidita), DmAlmConstants.DATE_FORMAT_EDMA_UO);
			
									// se già scaduta quindi DATA_FINE_ATTIVITA precede DATA ATTUALE
			
									if(dataFineValidita.before(new Date()))
									{
										errori++;
										ErroriCaricamentoDAO.insert
										(
												DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
												DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
												OresteToStringUtils.prodottoOrestetoString(row), 
												"Area_Prodotto.codice "+area.get(qstgUnitaOrganizzative.codice)+" - "+DmAlmConstants.AREA_PRODOTTO_INATTIVA, 
												DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
												dataEsecuzione);
									}
								}
			
								// Eliminato su richiesta di Rebottaro
			
								//					if(area.get(qstgUnitaOrganizzative.idTipologiaUfficio)!=null &&
								//							!area.get(qstgUnitaOrganizzative.idTipologiaUfficio).toString().equals("5"))
								//					{
								//						ErroriCaricamentoDAO.insert
								//						(
								//								DmAlmConstats.FONTE_ORESTE_PRODOTTO, 
								//								DmAlmConstats.TARGET_ORESTE_PRODOTTO, 
								//								area.toString(), 
								//								"Area Prodotto.codice "+area.get(qstgUnitaOrganizzative.codice)+" - "+DmAlmConstats.AREA_PRODOTTO_NOT_AREA_IN_EDMA, 
								//								DmAlmConstats.FLAG_ERRORE_NON_BLOCCANTE
								//						);
								//					}
			
								// non deve essere una persona fisica (attributo TIPOPERSONA) della stuttura organizzativa/personale
			
								List<Tuple> persona = PersonaleDAO.getPersonaleByCodice(qstgPersonale, area.get(qstgUnitaOrganizzative.codice), dataEsecuzione);
			
								if(persona.size() > 0)
								{
									errori++;
									ErroriCaricamentoDAO.insert
									(
											DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
											DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
											OresteToStringUtils.prodottoOrestetoString(row),  
											"Area_Prodotto_Arch_Appl " +area.get(qstgUnitaOrganizzative.codice)+ DmAlmConstants.AREA_PRODOTTO_IS_PERSONAFISICA, 
											DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
											dataEsecuzione);
								}
							}
			
						}
						else
						{
							errori++;
							ErroriCaricamentoDAO.insert
							(
									DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
									DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
									OresteToStringUtils.prodottoOrestetoString(row), 
									"Prodotto.id "+row.get(stgProdottiArchitetture.idProdotto)+" - "+DmAlmConstants.AREA_PRODOTTO_NON_PRESENTE, 
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}
		}
		
		return errori;
	}

	protected static int checkDuplicateNomiProdotto(Logger logger, Timestamp dataEsecuzione) throws Exception{

		List<Tuple> nomiProdottoDuplicate = ProdottiArchitettureDAO.getDuplicateNomeProdotto(dataEsecuzione);
		
		int errori = 0;

		for(Tuple row : nomiProdottoDuplicate)
		{
			errori++;
			ErroriCaricamentoDAO.insert(
					DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
					DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
					OresteToStringUtils.prodottoOrestetoString(row),  
					DmAlmConstants.PRODOTTO_NOME_DUPLICATO, 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}

		return errori;
	}

	protected static int checkDuplicateSiglaProdotto(Logger logger, Timestamp dataEsecuzione) throws Exception{

		List<Tuple> siglaProdottoDuplicate = ProdottiArchitettureDAO.getDuplicateSiglaProdotto(dataEsecuzione);
		
		int errori = 0;

		for(Tuple row : siglaProdottoDuplicate)
		{
			
			errori++;
			ErroriCaricamentoDAO.insert(
					DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
					DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
					OresteToStringUtils.prodottoOrestetoString(row), 
					DmAlmConstants.PRODOTTO_SIGLA_DUPLICATA, 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}

		return errori;
	}

	protected static int checkSigleProdottoNull(Logger logger, Timestamp dataEsecuzione) throws Exception{

		List<Tuple> siglaProdottoNull = ProdottiArchitettureDAO.getSiglaProdottiNull(dataEsecuzione);
		
		int errori = 0;

		for(Tuple row : siglaProdottoNull)
		{
			
			errori++;
			ErroriCaricamentoDAO.insert
			(
					DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
					DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
					OresteToStringUtils.prodottoOrestetoString(row),  
					DmAlmConstants.PRODOTTO_SIGLA_NULL, 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}

		return errori;
	}

	protected static int checkNomiProdottoNull(Logger logger, Timestamp dataEsecuzione) throws Exception{

		List<Tuple> nomiProdottoNull = ProdottiArchitettureDAO.getNomiProdottoNull(dataEsecuzione);
		
		int errori = 0;

		for(Tuple row : nomiProdottoNull)
		{
			errori++;
			ErroriCaricamentoDAO.insert
			(
					DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
					DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
					OresteToStringUtils.prodottoOrestetoString(row),  
					DmAlmConstants.PRODOTTO_NOME_NULL, 
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
					dataEsecuzione);
		}

		return errori;
	}

	protected static int checkNameProdottoAnnullatoLogicamente(Logger logger, Timestamp dataEsecuzione) throws Exception{

		QStgProdottiArchitetture  qstgProdottiArchitetture   = QStgProdottiArchitetture.stgProdottiArchitetture;
		QStgModuli 				  qstgModuli				 = QStgModuli.stgModuli;
		QStgSottosistemi		  qstgSottosistemi			 = QStgSottosistemi.stgSottosistemi;
		QStgFunzionalita		  qstgFunzionalita			 = QStgFunzionalita.stgFunzionalita;
		//QStgUnitaOrganizzative    qstgUnitaOrganizzative     = QStgUnitaOrganizzative.stgUnitaOrganizzative;
		//QStgPersonale             qstgPersonale				 = QStgPersonale.stgPersonale;
		
		int errori = 0;

		List<Tuple> prodottiAnnullati = new ArrayList<Tuple>();
		List<Tuple> moduliNonAnnullati = new ArrayList<Tuple>();
		List<Tuple> sottosistemiNonAnnullati =   new ArrayList<Tuple>();
		List<Tuple> funzionalitaNonAnnullate =   new ArrayList<Tuple>();
		List<Tuple> moduliAnnullati =  new ArrayList<Tuple>();
		List<Tuple> funzionalitaAnnullate =  new ArrayList<Tuple>();
		List<Tuple> sottosistemiAnnullati =  new ArrayList<Tuple>(); 
		//List<Tuple> unitaOrganizzative = new ArrayList<Tuple>();

		boolean checkStringaAnnullamento 	= true;
		boolean checkAnnullamentoDateFormat = true;

		prodottiAnnullati = ProdottiArchitettureDAO.getProdottiAnnullati(qstgProdottiArchitetture, dataEsecuzione);

		Date dataAnnullamentoProdotto = null;

		for(Tuple row : prodottiAnnullati)
		{

			//			Se un Prodotto/ Arch Appl Oreste è #ANNULLATO LOGICAMENTE# la DATA FINE VALIDITA':
			//			- deve essere racchiusa tra due ## che si trovano subito dopo 
			//			la scritta convenzionale indicante l'Annullamento Logico 
			//			- deve essere in formato AAAAMMGG
			//			
			//			esempio di valore valido: è #ANNULLATO 
			//			LOGICAMENTE##20130927# Nome Prodotto/ Arch Appl Oreste

			
			checkStringaAnnullamento = StringUtils.checkStringAnnullamentoFormat(row.get(qstgProdottiArchitetture.nomeProdotto), logger);

			if(!checkStringaAnnullamento)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
						DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
						OresteToStringUtils.prodottoOrestetoString(row),  
						DmAlmConstants.PRODOTTO_DATAFINEVALIDITA_NO_CANCELLETTI, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			checkAnnullamentoDateFormat = DateUtils.checkDataAnnullamentoFormat(row.get(qstgProdottiArchitetture.nomeProdotto), logger);

			if(!checkAnnullamentoDateFormat)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
						DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
						OresteToStringUtils.prodottoOrestetoString(row),  
						DmAlmConstants.PRODOTTO_DATAFINEVALIDITA_FORMATO_NON_VALIDO, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// Data annullamento prodotto
			dataAnnullamentoProdotto = DateUtils.getDataAnnullamento(row.get(qstgProdottiArchitetture.nomeProdotto), logger);

			//			Se un Prodotto/ Arch Appl Oreste è #ANNULLATO  LOGICAMENTE# 
			//			1)  tutti i suoi discendenti di ogni livello (eccetto AMBIENTE 
			//			TECNOLOGICO) devono in corrispondenza recare la 
			//			scritta #ANNULLATO LOGICAMENTE L’ASCENDENTE# 
			//			oppure #ANNULLATO LOGICAMENTE#

			moduliNonAnnullati = ModuliDAO.checkStatoModuliProdottoAnnullato(qstgModuli, row.get(qstgProdottiArchitetture.siglaProdotto), dataEsecuzione);

			if(moduliNonAnnullati!=null && moduliNonAnnullati.size()>0)
			{

				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
						DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
						OresteToStringUtils.prodottoOrestetoString(row),
						"Prodotti.id "+row.get(qstgProdottiArchitetture.idProdotto)+" - "+DmAlmConstants.PRODOTTO_ANNULLATO_MODULO_NON_ANNULLATO, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			sottosistemiNonAnnullati = SottosistemiDAO.checkStatoSottosistemiProdottoAnnullato(qstgSottosistemi, 
					row.get(qstgProdottiArchitetture.siglaProdotto), dataEsecuzione);

			if(sottosistemiNonAnnullati!=null && sottosistemiNonAnnullati.size()>0)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
						DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
						OresteToStringUtils.prodottoOrestetoString(row),  
						"Prodotti.id "+row.get(qstgProdottiArchitetture.idProdotto)+" - "+DmAlmConstants.PRODOTTO_ANNULLATO_SOTTOSISTEMA_NON_ANNULLATO, 
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			funzionalitaNonAnnullate = FunzionalitaDAO.checkStatoFunzionalitaProdottoAnnullato(qstgFunzionalita, row.get(qstgProdottiArchitetture.siglaProdotto), dataEsecuzione);

			if(funzionalitaNonAnnullate!=null && funzionalitaNonAnnullate.size()>0)
			{
				errori++;
				ErroriCaricamentoDAO.insert
				(
						DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
						DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
						OresteToStringUtils.prodottoOrestetoString(row),  
						"Prodotti.id "+row.get(qstgProdottiArchitetture.idProdotto)+" - "+DmAlmConstants.PRODOTTO_ANNULLATO_FUNZIONALITA_NON_ANNULLATA, 
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

			moduliAnnullati = ModuliDAO.getModuliAnnullatiByProdotto(qstgModuli, row.get(qstgProdottiArchitetture.siglaProdotto), dataEsecuzione);

			Date dataAnnullamentoModulo = null;
			for(Tuple el : moduliAnnullati)
			{
				checkStringaAnnullamento = StringUtils.checkStringAnnullamentoFormat(el.get(qstgModuli.nomeModulo), logger);

				if(!checkStringaAnnullamento)
				{
					errori++;
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
							DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
							OresteToStringUtils.prodottoOrestetoString(row),  
							"Modulo.id "+el.get(qstgModuli.idModulo)+" - "+DmAlmConstants.PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_NO_CANCELLETTI, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}

				checkAnnullamentoDateFormat = DateUtils.checkDataAnnullamentoFormat(el.get(qstgModuli.nomeModulo), logger);

				if(!checkAnnullamentoDateFormat)
				{
					errori++;
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
							DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
							OresteToStringUtils.prodottoOrestetoString(row),  
							"Modulo.id "+el.get(qstgModuli.idModulo)+" - "+DmAlmConstants.PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_FORMATO_NON_VALIDO, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione
					);
				}
				else
				{

					//Il formato della data di annullamento del modulo è corretta
					//se esiste ed è valida anche la data di annullamento del prodotto
					//vado ad effettuare il controllo
					if(dataAnnullamentoProdotto!=null)

						//recupero la data di annullamento del modulo
						dataAnnullamentoModulo = DateUtils.getDataAnnullamento(el.get(qstgModuli.nomeModulo), logger);

					if(dataAnnullamentoModulo!=null && dataAnnullamentoModulo.after(dataAnnullamentoProdotto))
					{
						errori++;
						ErroriCaricamentoDAO.insert
						(
								DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
								DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
								OresteToStringUtils.prodottoOrestetoString(row),  
								"Modulo.id "+el.get(qstgModuli.idModulo)+" - "+DmAlmConstants.MODULO_DATAFINEVALIDITA_SUCCESSIVA_PRODOTTO, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}

				}
			}

			// FINE CONTROLLI SU DESCENDENTI MODULI

			//CONTROLLI SU DISCENDENTI FUNZIONALITA

			funzionalitaAnnullate = FunzionalitaDAO.getFunzionalitaAnnullateByProdotto(qstgFunzionalita, row.get(qstgProdottiArchitetture.siglaProdotto), dataEsecuzione);

			Date dataAnnullamentoFunzionalita = null;
			for(Tuple el : funzionalitaAnnullate)
			{
				checkStringaAnnullamento = StringUtils.checkStringAnnullamentoFormat(el.get(qstgFunzionalita.nomeFunzionalita), logger);

				if(!checkStringaAnnullamento)
				{
					errori++;
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
							DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
							OresteToStringUtils.prodottoOrestetoString(row),  
							"Funzionalita.id "+el.get(qstgFunzionalita.idFunzionalita)+" - "+DmAlmConstants.PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_NO_CANCELLETTI, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}

				checkAnnullamentoDateFormat = DateUtils.checkDataAnnullamentoFormat(el.get(qstgFunzionalita.nomeFunzionalita), logger);

				if(!checkAnnullamentoDateFormat)
				{
					errori++;
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
							DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
							OresteToStringUtils.prodottoOrestetoString(row),  
							"Funzionalita.id "+el.get(qstgFunzionalita.idFunzionalita)+" - "+DmAlmConstants.PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_FORMATO_NON_VALIDO, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
				else
				{

					//Il formato della data di annullamento della funzionalita è corretta
					//se esiste ed è valida anche la data di annullamento del prodotto
					//vado ad effettuare il controllo
					if(dataAnnullamentoProdotto!=null)

						//recupero la data di annullamento del modulo
						dataAnnullamentoFunzionalita = DateUtils.getDataAnnullamento(el.get(qstgFunzionalita.nomeFunzionalita), logger);

					if(dataAnnullamentoFunzionalita !=null && dataAnnullamentoFunzionalita.after(dataAnnullamentoProdotto))
					{
						
						errori++;
						ErroriCaricamentoDAO.insert
						(
								DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
								DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
								OresteToStringUtils.prodottoOrestetoString(row),  
								"Funzionalita.id "+el.get(qstgFunzionalita.idFunzionalita)+" - "+DmAlmConstants.FUNZIONALITA_DATAFINEVALIDITA_SUCCESSIVA_PRODOTTO, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}

				}
			}

			// FINE CONTROLLI SU DESCENDENTI FUNZIONALITA

			//CONTROLLI SU DISCENDENTI SOTTOSISTEMA

			sottosistemiAnnullati = SottosistemiDAO.getSottosistemiAnnullatiByProdotto(qstgSottosistemi, row.get(qstgProdottiArchitetture.siglaProdotto), dataEsecuzione);

			Date dataAnnullamentoSottosistema = null;
			for(Tuple el : sottosistemiAnnullati)
			{

				checkStringaAnnullamento = StringUtils.checkStringAnnullamentoFormat(el.get(qstgSottosistemi.nomeSottosistema), logger);

				if(!checkStringaAnnullamento)
				{
					errori++;
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
							DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
							OresteToStringUtils.prodottoOrestetoString(row),  
							"Sottosistema.id "+el.get(qstgSottosistemi.idSottosistema)+" - "+DmAlmConstants.PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_NO_CANCELLETTI, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}

				checkAnnullamentoDateFormat = DateUtils.checkDataAnnullamentoFormat(el.get(qstgSottosistemi.nomeSottosistema), logger);

				if(!checkAnnullamentoDateFormat)
				{
					errori++;
					ErroriCaricamentoDAO.insert
					(
							DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
							DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
							OresteToStringUtils.prodottoOrestetoString(row),  
							"Sottosistema.id "+el.get(qstgSottosistemi.idSottosistema)+" - "+DmAlmConstants.PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_FORMATO_NON_VALIDO, 
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
				else
				{

					//Il formato della data di annullamento del sottosistema è corretta
					//se esiste ed è valida anche la data di annullamento del prodotto
					//vado ad effettuare il controllo
					if(dataAnnullamentoProdotto!=null)

						//recupero la data di annullamento del sottosistema
						dataAnnullamentoSottosistema = DateUtils.getDataAnnullamento(el.get(qstgSottosistemi.nomeSottosistema), logger);

					if(dataAnnullamentoSottosistema != null && dataAnnullamentoSottosistema.after(dataAnnullamentoProdotto))
					{
						
						errori++;
						ErroriCaricamentoDAO.insert
						(
								DmAlmConstants.FONTE_ORESTE_PRODOTTO, 
								DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
								OresteToStringUtils.prodottoOrestetoString(row),  
								"Sottosistema.id "+el.get(qstgSottosistemi.idSottosistema)+" - "+DmAlmConstants.SOTTOSISTEMA_DATAFINEVALIDITA_SUCCESSIVA_PRODOTTO, 
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}

				}
			}

			// FINE CONTROLLI SU DISCENDENTI FUNZIONALITA

//			// CONTROLLI SU AREA PRODOTTO
//
//			String areaEDMA = getAreaProdottoByEdmaAreaProdotto(row.get(qstgProdottiArchitetture.edmaAreaProdotto));
//
//			if(areaEDMA != null && !areaEDMA.equals(""))
//			{
//				unitaOrganizzative = UnitaOrganizzativaDAO.getUnitaOrganizzativaByCodiceArea(qstgUnitaOrganizzative, row.get(qstgProdottiArchitetture.edmaAreaProdotto), dataEsecuzione);
//
//				for(Tuple area : unitaOrganizzative )
//				{
//					// dovrebbe (usualmente) essere un Area (attributo codice tipologia ufficio) della strauttura organizzativa
//
//					if(area.get(qstgUnitaOrganizzative.dataFineValidita) !=null)
//					{
//
//						Date dataFineValidita = DateUtils.stringToDate(area.get(qstgUnitaOrganizzative.dataFineValidita), DmAlmConstats.DATE_FORMAT_EDMA_UO);
//
//						// se già scaduta quindi DATA_FINE_ATTIVITA precede DATA ATTUALE
//
//						if(dataFineValidita.before(new Date()))
//						{
//							errori++;
//							ErroriCaricamentoDAO.insert
//							(
//									DmAlmConstats.FONTE_ORESTE_PRODOTTO, 
//									DmAlmConstats.TARGET_ORESTE_PRODOTTO, 
//									area.toString(), 
//									"Area_Prodotto.codice "+area.get(qstgUnitaOrganizzative.codice)+" - "+DmAlmConstats.AREA_PRODOTTO_INATTIVA, 
//									DmAlmConstats.FLAG_ERRORE_NON_BLOCCANTE,
//									dataEsecuzione);
//						}
//					}
//
//					// Eliminato su richiesta di Rebottaro
//
//					//					if(area.get(qstgUnitaOrganizzative.idTipologiaUfficio)!=null &&
//					//							!area.get(qstgUnitaOrganizzative.idTipologiaUfficio).toString().equals("5"))
//					//					{
//					//						ErroriCaricamentoDAO.insert
//					//						(
//					//								DmAlmConstats.FONTE_ORESTE_PRODOTTO, 
//					//								DmAlmConstats.TARGET_ORESTE_PRODOTTO, 
//					//								area.toString(), 
//					//								"Area Prodotto.codice "+area.get(qstgUnitaOrganizzative.codice)+" - "+DmAlmConstats.AREA_PRODOTTO_NOT_AREA_IN_EDMA, 
//					//								DmAlmConstats.FLAG_ERRORE_NON_BLOCCANTE
//					//						);
//					//					}
//
//					// non deve essere una persona fisica (attributo TIPOPERSONA) della stuttura organizzativa/personale
//
//					List<Tuple> persona = PersonaleDAO.getPersonaleByCodice(qstgPersonale, area.get(qstgUnitaOrganizzative.codice), dataEsecuzione);
//
//					if(persona.size() > 0)
//					{
//						errori++;
//						ErroriCaricamentoDAO.insert
//						(
//								DmAlmConstats.FONTE_ORESTE_PRODOTTO, 
//								DmAlmConstats.TARGET_ORESTE_PRODOTTO, 
//								area.toString(), 
//								"Area_Prodotto_Arch_Appl " +area.get(qstgUnitaOrganizzative.codice)+ DmAlmConstats.AREA_PRODOTTO_IS_PERSONAFISICA, 
//								DmAlmConstats.FLAG_ERRORE_NON_BLOCCANTE,
//								dataEsecuzione);
//					}
//				}
//
//			}
//			else
//			{
//				errori++;
//				ErroriCaricamentoDAO.insert
//				(
//						DmAlmConstats.FONTE_ORESTE_PRODOTTO, 
//						DmAlmConstats.TARGET_ORESTE_PRODOTTO, 
//						row.toString(), 
//						"Prodotto.id "+row.get(qstgProdottiArchitetture.idProdotto)+" - "+DmAlmConstats.AREA_PRODOTTO_NON_PRESENTE, 
//						DmAlmConstats.FLAG_ERRORE_NON_BLOCCANTE,
//						dataEsecuzione);
//			}
//
//
//			// FINE CONTROLLI SU AREA PRODOTTO
//
//			// CONTROLLI SU RESPONSABILE PRODOTTO
//
//			String codiceResponsabile = getCodiceResponsabileProdotto(row.get(qstgProdottiArchitetture.edmaRespProdotto));
//
//			if(codiceResponsabile != null)
//			{	
//				// se l'oggetto oreste Prodotto è diverso da NULL e NON è annullato (#ANNULLATO LOGICAMENTE## ecc)
//				if(row!=null && row.get(qstgProdottiArchitetture.nomeProdotto)!= null 
//						&& ( !row.get(qstgProdottiArchitetture.nomeProdotto).toLowerCase().contains("ANNULLATO".toLowerCase()) ))
//				{
//					if(PersonaleDAO.getPersonaleByCodice(qstgPersonale, codiceResponsabile, dataEsecuzione).size()==0)
//					{
//						errori++;
//						ErroriCaricamentoDAO.insert
//						(
//								DmAlmConstats.FONTE_ORESTE_PRODOTTO, 
//								DmAlmConstats.TARGET_ORESTE_PRODOTTO, 
//								row.toString(), 
//								"Prodotto.id "+row.get(qstgProdottiArchitetture.idProdotto)+" - "+DmAlmConstats.RESPONSABILE_PRODOTTO_NON_PERSONAFISICA+ row.get(qstgProdottiArchitetture.edmaRespProdotto), 
//								DmAlmConstats.FLAG_ERRORE_NON_BLOCCANTE,
//								dataEsecuzione
//						);
//					}
//				}
//			}
//			else
//			{
//
//				errori++;
//				ErroriCaricamentoDAO.insert
//				(
//						DmAlmConstats.FONTE_ORESTE_PRODOTTO, 
//						DmAlmConstats.TARGET_ORESTE_PRODOTTO, 
//						row.toString(), 
//						"Prodotto.id "+row.get(qstgProdottiArchitetture.idProdotto)+" - "+DmAlmConstats.RESPONSABILE_PRODOTTO_NON_PRESENTE, 
//						DmAlmConstats.FLAG_ERRORE_NON_BLOCCANTE,
//						dataEsecuzione
//				);
//			}

			// FINE CONTROLLI SU RESPONSABILE
		}
		return errori;
	}

	protected static String getAreaProdottoByEdmaAreaProdotto(String edmaAreaProdotto) throws Exception
	{

			//LIA367 - Area Sistemi Direzionali / SISS
			//diventa
			//LIA367

			if(edmaAreaProdotto!=null && !edmaAreaProdotto.equals(""))
			{
				if(edmaAreaProdotto.indexOf("-")!= -1)
				{
					edmaAreaProdotto = edmaAreaProdotto.substring(0, edmaAreaProdotto.indexOf("-")).trim();
				}
				else
				{
					edmaAreaProdotto = edmaAreaProdotto.trim();
				}
			}
			else
			{
				// oppure ""
				return "";
			}

		return edmaAreaProdotto;
	}

	protected static String getCodiceResponsabileProdotto(String responsabile) throws Exception
	{
			String codice = "";

			//MASTRANGELO GIUSEPPE MARIA (LIA303) {GMASTRANGELO}
			//diventa
			//GMASTRANGELO

				if(responsabile!=null && !responsabile.equals("") && !responsabile.trim().equals("{}"))
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