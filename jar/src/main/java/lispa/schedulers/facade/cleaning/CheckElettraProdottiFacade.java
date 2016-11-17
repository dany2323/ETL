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
import lispa.schedulers.dao.elettra.StgElProdottiDAO;
import lispa.schedulers.dao.elettra.StgElUnitaOrganizzativeDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElFunzionalita;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElModuli;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElPersonale;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElProdotti;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElUnitaOrganizzative;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.ElettraToStringUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckElettraProdottiFacade implements Runnable {
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

	public CheckElettraProdottiFacade(Logger log, Timestamp date) {
		this.logger = log;
		this.dataEsecuzione = date;
	}

	public static void execute(Logger logger, Timestamp dataEsecuzione) {
		int erroriNonBloccanti = 0;

		try {
			logger.debug("START CheckElettraProdottiFacade.execute");

			erroriNonBloccanti += checkElettraSigleProdottoNull(logger,
					dataEsecuzione);

			erroriNonBloccanti += checkElettraDuplicateSiglaProdotto(logger,
					dataEsecuzione);

			erroriNonBloccanti += checkElettraNomiProdottoNull(logger,
					dataEsecuzione);

			erroriNonBloccanti += checkElettraDuplicateNomiProdotto(logger,
					dataEsecuzione);

			erroriNonBloccanti += checkElettraNameProdottoAnnullatoLogicamente(
					logger, dataEsecuzione);

			erroriNonBloccanti += checkElettraAreaProdotto(logger,
					dataEsecuzione);

			erroriNonBloccanti += checkElettraResponsabileProdotto(logger,
					dataEsecuzione);

			EsitiCaricamentoDAO.insert(dataEsecuzione,
					DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
					DmAlmConstants.CARICAMENTO_IN_ATTESA, null, null, 0, 0,
					erroriNonBloccanti, 0);

		} catch (DAOException e) {
			logger.error(e.getMessage(), e);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		logger.debug("STOP CheckElettraProdottiFacade.execute");
	}

	protected static int checkElettraSigleProdottoNull(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		List<Tuple> siglaProdottoNull = StgElProdottiDAO
				.getSiglaProdottiNull(dataEsecuzione);

		int errori = 0;

		for (Tuple row : siglaProdottoNull) {

			errori++;
			ErroriCaricamentoDAO.insert(
					DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
					DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
					ElettraToStringUtils.prodottoToString(row),
					DmAlmConstants.PRODOTTO_SIGLA_NULL,
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE, dataEsecuzione);
		}

		return errori;
	}

	protected static int checkElettraDuplicateSiglaProdotto(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		List<Tuple> siglaProdottoDuplicate = StgElProdottiDAO
				.getDuplicateSiglaProdotto(dataEsecuzione);

		int errori = 0;

		for (Tuple row : siglaProdottoDuplicate) {

			errori++;
			ErroriCaricamentoDAO.insert(
					DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
					DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
					ElettraToStringUtils.prodottoToString(row),
					DmAlmConstants.PRODOTTO_SIGLA_DUPLICATA,
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE, dataEsecuzione);
		}

		return errori;
	}

	protected static int checkElettraNomiProdottoNull(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		List<Tuple> nomiProdottoNull = StgElProdottiDAO
				.getNomiProdottoNull(dataEsecuzione);

		int errori = 0;

		for (Tuple row : nomiProdottoNull) {
			errori++;
			ErroriCaricamentoDAO.insert(
					DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
					DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
					ElettraToStringUtils.prodottoToString(row),
					DmAlmConstants.PRODOTTO_NOME_NULL,
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE, dataEsecuzione);
		}

		return errori;
	}

	protected static int checkElettraDuplicateNomiProdotto(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		List<Tuple> nomiProdottoDuplicate = StgElProdottiDAO
				.getDuplicateNomeProdotto(dataEsecuzione);

		int errori = 0;

		for (Tuple row : nomiProdottoDuplicate) {
			errori++;
			ErroriCaricamentoDAO.insert(
					DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
					DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
					ElettraToStringUtils.prodottoToString(row),
					DmAlmConstants.PRODOTTO_NOME_DUPLICATO,
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE, dataEsecuzione);
		}

		return errori;
	}

	protected static int checkElettraNameProdottoAnnullatoLogicamente(
			Logger logger, Timestamp dataEsecuzione) throws Exception {

		QStgElProdotti stgElProdotti = QStgElProdotti.stgElProdotti;
		QStgElModuli stgElModuli = QStgElModuli.stgElModuli;
		QStgElFunzionalita stgElFunzionalita = QStgElFunzionalita.stgElFunzionalita;

		int errori = 0;

		List<Tuple> prodottiAnnullati = new ArrayList<Tuple>();
		List<Tuple> moduliNonAnnullati = new ArrayList<Tuple>();
		List<Tuple> funzionalitaNonAnnullate = new ArrayList<Tuple>();
		List<Tuple> moduliAnnullati = new ArrayList<Tuple>();
		List<Tuple> funzionalitaAnnullate = new ArrayList<Tuple>();

		boolean checkStringaAnnullamento = true;
		boolean checkAnnullamentoDateFormat = true;

		prodottiAnnullati = StgElProdottiDAO.getProdottiAnnullati(
				stgElProdotti, dataEsecuzione);

		Date dataAnnullamentoProdotto = null;

		for (Tuple row : prodottiAnnullati) {

			// Se un Prodotto/ Arch Appl Elettra è #ANNULLATO LOGICAMENTE# la
			// DATA FINE VALIDITA':
			// - deve essere racchiusa tra due ## che si trovano subito dopo
			// la scritta convenzionale indicante l'Annullamento Logico
			// - deve essere in formato AAAAMMGG
			//
			// esempio di valore valido: è #ANNULLATO
			// LOGICAMENTE##20130927# Nome Prodotto/ Arch Appl Elettra

			checkStringaAnnullamento = StringUtils
					.checkStringAnnullamentoFormat(row.get(stgElProdotti.nome),
							logger);

			if (!checkStringaAnnullamento) {
				errori++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
								DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
								ElettraToStringUtils.prodottoToString(row),
								DmAlmConstants.PRODOTTO_DATAFINEVALIDITA_NO_CANCELLETTI,
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}

			checkAnnullamentoDateFormat = DateUtils
					.checkDataAnnullamentoFormat(row.get(stgElProdotti.nome),
							logger);

			if (!checkAnnullamentoDateFormat) {
				errori++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
								DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
								ElettraToStringUtils.prodottoToString(row),
								DmAlmConstants.PRODOTTO_DATAFINEVALIDITA_FORMATO_NON_VALIDO,
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}

			// Data annullamento prodotto
			dataAnnullamentoProdotto = DateUtils.getDataAnnullamento(
					row.get(stgElProdotti.nome), logger);

			// Se un Prodotto/ Arch Appl Elettra è #ANNULLATO LOGICAMENTE#
			// 1) tutti i suoi discendenti di ogni livello (eccetto AMBIENTE
			// TECNOLOGICO) devono in corrispondenza recare la
			// scritta #ANNULLATO LOGICAMENTE L’ASCENDENTE#
			// oppure #ANNULLATO LOGICAMENTE#

			moduliNonAnnullati = StgElModuliDAO
					.checkStatoModuliProdottoAnnullato(stgElModuli,
							row.get(stgElProdotti.sigla), dataEsecuzione);

			if (moduliNonAnnullati != null && moduliNonAnnullati.size() > 0) {

				errori++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
								DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
								ElettraToStringUtils.prodottoToString(row),
								"Prodotti.id "
										+ row.get(stgElProdotti.idProdotto)
										+ " - "
										+ DmAlmConstants.PRODOTTO_ANNULLATO_MODULO_NON_ANNULLATO,
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}

			funzionalitaNonAnnullate = StgElFunzionalitaDAO
					.checkStatoFunzionalitaProdottoAnnullato(stgElFunzionalita,
							row.get(stgElProdotti.sigla), dataEsecuzione);

			if (funzionalitaNonAnnullate != null
					&& funzionalitaNonAnnullate.size() > 0) {
				errori++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
								DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
								ElettraToStringUtils.prodottoToString(row),
								"Prodotti.id "
										+ row.get(stgElProdotti.idProdotto)
										+ " - "
										+ DmAlmConstants.PRODOTTO_ANNULLATO_FUNZIONALITA_NON_ANNULLATA,
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}

			// Se un Prodotto/ Arch Appl Elettra è #ANNULLATO LOGICAMENTE# 
			// 2) la DATA FINE VALIDITA' del discendente
			// - deve essere racchiusa tra due ## che si trovano subito dopo
			// la scritta convenzionale indicante l'Annullamento Logico
			// - deve essere in formato AAAAMMGG, valore valido e =<
			// della DATA FINE VALIDITA’ del Prodotto/ Arch Appl

			// CONTROLLI SU DISCENDENTI MODULI

			moduliAnnullati = StgElModuliDAO.getModuliAnnullatiByProdotto(
					stgElModuli, row.get(stgElProdotti.sigla), dataEsecuzione);

			Date dataAnnullamentoModulo = null;
			for (Tuple el : moduliAnnullati) {
				checkStringaAnnullamento = StringUtils
						.checkStringAnnullamentoFormat(
								el.get(stgElModuli.nome), logger);

				if (!checkStringaAnnullamento) {
					errori++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
									DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
									ElettraToStringUtils.prodottoToString(row),
									"Modulo.id "
											+ el.get(stgElModuli.idModulo)
											+ " - "
											+ DmAlmConstants.PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_NO_CANCELLETTI,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}

				checkAnnullamentoDateFormat = DateUtils
						.checkDataAnnullamentoFormat(el.get(stgElModuli.nome),
								logger);

				if (!checkAnnullamentoDateFormat) {
					errori++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
									DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
									ElettraToStringUtils.prodottoToString(row),
									"Modulo.id "
											+ el.get(stgElModuli.idModulo)
											+ " - "
											+ DmAlmConstants.PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_FORMATO_NON_VALIDO,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				} else {

					// Il formato della data di annullamento del modulo è
					// corretta
					// se esiste ed è valida anche la data di annullamento del
					// prodotto
					// vado ad effettuare il controllo
					if (dataAnnullamentoProdotto != null)

						// recupero la data di annullamento del modulo
						dataAnnullamentoModulo = DateUtils.getDataAnnullamento(
								el.get(stgElModuli.nome), logger);

					if (dataAnnullamentoModulo != null
							&& dataAnnullamentoModulo
									.after(dataAnnullamentoProdotto)) {
						errori++;
						ErroriCaricamentoDAO
								.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
										DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
										ElettraToStringUtils
												.prodottoToString(row),
										"Modulo.id "
												+ el.get(stgElModuli.idModulo)
												+ " - "
												+ DmAlmConstants.MODULO_DATAFINEVALIDITA_SUCCESSIVA_PRODOTTO,
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
					}
				}
			}

			// FINE CONTROLLI SU DESCENDENTI MODULI

			// CONTROLLI SU DISCENDENTI FUNZIONALITA

			funzionalitaAnnullate = StgElFunzionalitaDAO
					.getFunzionalitaAnnullateByProdotto(stgElFunzionalita,
							row.get(stgElProdotti.sigla), dataEsecuzione);

			Date dataAnnullamentoFunzionalita = null;
			for (Tuple el : funzionalitaAnnullate) {
				checkStringaAnnullamento = StringUtils
						.checkStringAnnullamentoFormat(
								el.get(stgElFunzionalita.nome), logger);

				if (!checkStringaAnnullamento) {
					errori++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
									DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
									ElettraToStringUtils.prodottoToString(row),
									"Funzionalita.id "
											+ el.get(stgElFunzionalita.idFunzionalia)
											+ " - "
											+ DmAlmConstants.PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_NO_CANCELLETTI,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}

				checkAnnullamentoDateFormat = DateUtils
						.checkDataAnnullamentoFormat(
								el.get(stgElFunzionalita.nome), logger);

				if (!checkAnnullamentoDateFormat) {
					errori++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
									DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
									ElettraToStringUtils.prodottoToString(row),
									"Funzionalita.id "
											+ el.get(stgElFunzionalita.idFunzionalia)
											+ " - "
											+ DmAlmConstants.PRODOTTO_DISCENDENTE_DATAFINEVALIDITA_FORMATO_NON_VALIDO,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				} else {

					// Il formato della data di annullamento della funzionalita
					// è corretta
					// se esiste ed è valida anche la data di annullamento del
					// prodotto
					// vado ad effettuare il controllo
					if (dataAnnullamentoProdotto != null)

						// recupero la data di annullamento del modulo
						dataAnnullamentoFunzionalita = DateUtils
								.getDataAnnullamento(
										el.get(stgElFunzionalita.nome), logger);

					if (dataAnnullamentoFunzionalita != null
							&& dataAnnullamentoFunzionalita
									.after(dataAnnullamentoProdotto)) {

						errori++;
						ErroriCaricamentoDAO
								.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
										DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
										ElettraToStringUtils
												.prodottoToString(row),
										"Funzionalita.id "
												+ el.get(stgElFunzionalita.idFunzionalia)
												+ " - "
												+ DmAlmConstants.FUNZIONALITA_DATAFINEVALIDITA_SUCCESSIVA_PRODOTTO,
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
					}

				}
			}

			// FINE CONTROLLI SU DESCENDENTI FUNZIONALITA

		}
		return errori;
	}

	private static int checkElettraAreaProdotto(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		QStgElProdotti stgElProdotti = QStgElProdotti.stgElProdotti;
		QStgElUnitaOrganizzative stgElUnitaOrganizzative = QStgElUnitaOrganizzative.stgElUnitaOrganizzative;
		QStgElPersonale stgElPersonale = QStgElPersonale.stgElPersonale;

		List<Tuple> unitaOrganizzative = new ArrayList<Tuple>();
		List<Tuple> prodotti = new ArrayList<Tuple>();

		prodotti = StgElProdottiDAO.getAllProdottiArchitetture(stgElProdotti,
				dataEsecuzione);
		int errori = 0;

		for (Tuple row : prodotti) {
			String areaEDMA = getAreaProdottoByEdmaAreaProdotto(row
					.get(stgElProdotti.areaProdotto));
			//
			if (areaEDMA != null && !areaEDMA.equals("")) {
				unitaOrganizzative = StgElUnitaOrganizzativeDAO
						.getUnitaOrganizzativaByCodiceArea(
								stgElUnitaOrganizzative,
								row.get(stgElProdotti.areaProdotto),
								dataEsecuzione);

				for (Tuple area : unitaOrganizzative) {
					// dovrebbe (usualmente) essere un Area (attributo codice
					// tipologia ufficio) della strauttura organizzativa

					if (area.get(stgElUnitaOrganizzative.dataFineValiditaEdma ) != null) {

						Date dataFineValidita = area
								.get(stgElUnitaOrganizzative.dataFineValiditaEdma );

						// se già scaduta quindi DATA_FINE_ATTIVITA precede DATA
						// ATTUALE

						if (dataFineValidita.before(new Date())) {
							errori++;
							ErroriCaricamentoDAO
									.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
											DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
											ElettraToStringUtils
													.prodottoToString(row),
											"Area_Prodotto.codice "
													+ area.get(stgElUnitaOrganizzative.codiceArea)
													+ " - "
													+ DmAlmConstants.AREA_PRODOTTO_INATTIVA,
											DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
											dataEsecuzione);
						}
					}

					List<Tuple> persona = StgElPersonaleDAO
							.getPersonaleByCodice(stgElPersonale, area
									.get(stgElUnitaOrganizzative.codiceArea),
									dataEsecuzione);

					if (persona.size() > 0) {
						errori++;
						ErroriCaricamentoDAO
								.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
										DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
										ElettraToStringUtils
												.prodottoToString(row),
										"Area_Prodotto_Arch_Appl "
												+ area.get(stgElUnitaOrganizzative.codiceArea)
												+ DmAlmConstants.AREA_PRODOTTO_IS_PERSONAFISICA,
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
					}
				}

			} else {
				errori++;
				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
						DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
						ElettraToStringUtils.prodottoToString(row),
						"Prodotto.id " + row.get(stgElProdotti.idProdotto)
								+ " - "
								+ DmAlmConstants.AREA_PRODOTTO_NON_PRESENTE,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}
		}

		return errori;
	}

	protected static String getAreaProdottoByEdmaAreaProdotto(
			String edmaAreaProdotto) throws Exception {

		// LIA367 - Area Sistemi Direzionali / SISS
		// diventa
		// LIA367

		if (edmaAreaProdotto != null && !edmaAreaProdotto.equals("")) {
			if (edmaAreaProdotto.indexOf("-") != -1) {
				edmaAreaProdotto = edmaAreaProdotto.substring(0,
						edmaAreaProdotto.indexOf("-")).trim();
			} else {
				edmaAreaProdotto = edmaAreaProdotto.trim();
			}
		} else {
			// oppure ""
			return "";
		}

		return edmaAreaProdotto;
	}

	private static int checkElettraResponsabileProdotto(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		QStgElProdotti stgElProdotti = QStgElProdotti.stgElProdotti;
		QStgElPersonale stgElPersonale = QStgElPersonale.stgElPersonale;

		List<Tuple> prodotti = StgElProdottiDAO.getAllProdottiArchitetture(
				stgElProdotti, dataEsecuzione);

		// prodotti = StgElProdottiDAO.getAllProdottiArchitetture(stgElProdotti,
		// dataEsecuzione);

		int errori = 0;

		for (Tuple row : prodotti) {
			String codiceResponsabile = getCodiceResponsabileProdotto(row
					.get(stgElProdotti.responsabileProdotto));
			//
			if (codiceResponsabile != null) {
				// se l'oggetto elettra Prodotto è diverso da NULL e NON è
				// annullato (#ANNULLATO LOGICAMENTE## ecc)
				if (row != null
						&& row.get(stgElProdotti.nome) != null
						&& (!row.get(stgElProdotti.nome).toLowerCase()
								.contains("ANNULLATO".toLowerCase()))) {
					if (StgElPersonaleDAO.getPersonaleByCodice(stgElPersonale,
							codiceResponsabile, dataEsecuzione).size() == 0) {
						errori++;
						ErroriCaricamentoDAO
								.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
										DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
										ElettraToStringUtils
												.prodottoToString(row),
										"Prodotto.id "
												+ row.get(stgElProdotti.idProdotto)
												+ " - "
												+ DmAlmConstants.RESPONSABILE_PRODOTTO_NON_PERSONAFISICA
												+ row.get(stgElProdotti.responsabileProdotto),
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
					}
				}
			} else {

				errori++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_ELETTRA_PRODOTTIARCHITETTURE,
								DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE,
								ElettraToStringUtils.prodottoToString(row),
								"Prodotto.id "
										+ row.get(stgElProdotti.idProdotto)
										+ " - "
										+ DmAlmConstants.RESPONSABILE_PRODOTTO_NON_PRESENTE,
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}
		}
		return errori;
	}

	protected static String getCodiceResponsabileProdotto(String responsabile)
			throws Exception {
		String codice = "";

		// MASTRANGELO GIUSEPPE MARIA (LIA303) {GMASTRANGELO}
		// diventa
		// GMASTRANGELO

		if (responsabile != null && !responsabile.equals("")
				&& !responsabile.trim().equals("{}")) {
			if (responsabile.indexOf("{") != -1
					&& responsabile.indexOf("}") != -1) {
				codice = responsabile.split("\\{")[1].split("\\}")[0];

				codice = codice.equals("") ? null : codice;

			} else {
				codice = null;
			}
		} else {
			codice = null;
		}

		return codice;
	}

}
