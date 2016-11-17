package lispa.schedulers.facade.sfera;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.sfera.DmalmAsmProdottiArchitetture;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmProdottiArchitettureDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsmProdottiArchitetture;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckLinkAsmSferaProdottiArchFacade {
	private static Logger logger = Logger
			.getLogger(CheckLinkAsmSferaProdottiArchFacade.class);
	private static QDmalmAsm dmalmAsm = QDmalmAsm.dmalmAsm;
	private static QDmalmElProdottiArchitetture qDmalmElProdottiArchitetture = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static QDmalmAsmProdottiArchitetture qDmalmAsmProdottiArchitetture = QDmalmAsmProdottiArchitetture.qDmalmAsmProdottiArchitetture;

	public static void execute(Timestamp dataEsecuzione, boolean check)
			throws Exception, DAOException {
		try {
			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START CheckLinkAsmSferaProdottiArchFacade");

			boolean asmLegataTappo = false;
			String[] multiAsm = null;
			String unitaOrganizzativa = null;
			String unitaOrganizzativaDoppia = null;
			List<Tuple> asmList = new ArrayList<Tuple>();
			List<Tuple> productList = new ArrayList<Tuple>();
			List<String> asmLinkedList = new ArrayList<String>();
			List<Tuple> prodLink = new ArrayList<Tuple>();

			/**
			 * step 1 - Chiusura associazioni Prodotti chiusi: leggo tutte le
			 * relazioni "aperte" che hanno un puntamento ad un prodotto chiuso
			 * (è stato storicizzato il prodotto ma non la asm), queste
			 * relazioni le chiudo; lo step 3 partendo dalla Asm creerà una
			 * relazione nuova verso il prodotto "aperto"
			 */

			// tutti i Prodotti chiusi con relazione ad Asm ancora aperta
			asmList = DmAlmAsmProdottiArchitettureDAO
					.getAllRelationsToCloseCauseProductsClosed();

			// update DataFineValidita su tabella relazione
			for (Tuple asmProductRow : asmList) {
				if (asmProductRow != null) {
					DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
					relazione.setDmalmAsmPk(asmProductRow
							.get(qDmalmAsmProdottiArchitetture.dmalmAsmPk));
					relazione
							.setDmalmProdottoPk(asmProductRow
									.get(qDmalmAsmProdottiArchitetture.dmalmProdottoPk));
					relazione
							.setDataInizioValidita(asmProductRow
									.get(qDmalmAsmProdottiArchitetture.dtInizioValidita));
					relazione
							.setDataFineValidita(asmProductRow
									.get(qDmalmElProdottiArchitetture.dataFineValidita));

					logger.debug("update AsmProdottiArchitetture chiusura DataFineValidita per Prodotto chiuso - dmalmAsmPk: "
							+ relazione.getDmalmAsmPk()
							+ ", dmalmProdottoPk: "
							+ relazione.getDmalmProdottoPk()
							+ ", dataInizioValidita: "
							+ relazione.getDataInizioValidita()
							+ ", dataFineValidita: "
							+ relazione.getDataFineValidita());

					DmAlmAsmProdottiArchitettureDAO
							.closeRelAsmProdottiArchitetture(relazione);
				}
			}

			/** step 2 - Chiusura associazioni Asm chiuse */

			// legge tutte le asm chiuse con associazioni aperte da chiudere
			asmList = DmAlmAsmDAO.getAllRelationsToCloseCauseAsmsClosed();

			// update DataFineValidita su tabella relazione
			for (Tuple asmProductRow : asmList) {
				if (asmProductRow != null) {
					DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
					relazione.setDmalmAsmPk(asmProductRow
							.get(qDmalmAsmProdottiArchitetture.dmalmAsmPk));
					relazione
							.setDmalmProdottoPk(asmProductRow
									.get(qDmalmAsmProdottiArchitetture.dmalmProdottoPk));
					relazione
							.setDataInizioValidita(asmProductRow
									.get(qDmalmAsmProdottiArchitetture.dtInizioValidita));
					relazione.setDataFineValidita(asmProductRow
							.get(dmalmAsm.dataFineValidita));

					logger.debug("update AsmProdottiArchitetture chiusura DataFineValidita per Asm Chiusa - dmalmAsmPk: "
							+ relazione.getDmalmAsmPk()
							+ ", dmalmProdottoPk: "
							+ relazione.getDmalmProdottoPk()
							+ ", dataInizioValidita: "
							+ relazione.getDataInizioValidita()
							+ ", dataFineValidita: "
							+ relazione.getDataFineValidita());

					DmAlmAsmProdottiArchitettureDAO
							.closeRelAsmProdottiArchitetture(relazione);
				}
			}

			/** step 3 - Associazione Asm Aperte con Prodotto Aperto */

			// legge tutte le asm non associate
			asmList = DmAlmAsmDAO.getAllAsmToLinkWithProductArchitecture();

			// insert su tabella relazione
			for (Tuple asmRow : asmList) {
				if (asmRow != null) {
					String applicazione = asmRow.get(dmalmAsm.applicazione);

					// toglie #ANNULLATO LOGICAMENTE##
					if (applicazione
							.startsWith(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)) {
						applicazione = applicazione.substring(33,
								applicazione.length());
					}

					// gestione multi applicazione
					asmLegataTappo = false;
					unitaOrganizzativa = "";
					unitaOrganizzativaDoppia = "";
					asmLinkedList = new ArrayList<String>();
					multiAsm = applicazione.split("\\.\\.");

					for (String asmName : multiAsm) {
						// toglie eventuali moduli dopo il nome Asm
						if (asmName.contains(".")) {
							asmName = asmName
									.substring(0, asmName.indexOf("."));
						}

						// scarta nomi duplicati
						if (!asmLinkedList.contains(asmName)) {
							asmLinkedList.add(asmName);

							// ricerca Prodotto per Asm (Applicazione = Sigla)
							productList = ElettraProdottiArchitettureDAO
									.getProductByAcronym(asmName);

							if (productList.size() == 0) {
								if (asmRow
										.get(qDmalmAsmProdottiArchitetture.dmalmProdottoPk) == null
										&& !asmLegataTappo) {
									logger.info("Asm da legare al tappo Prodotto: "
											+ asmName
											+ ", applicazione: "
											+ asmRow.get(dmalmAsm.applicazione));

									asmLegataTappo = true;

									DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
									relazione.setDmalmAsmPk(asmRow
											.get(dmalmAsm.dmalmAsmPk));
									relazione.setDmalmProdottoPk(0);

									DmAlmAsmProdottiArchitettureDAO
											.insertRelAsmProdottiArchitetture(
													dataEsecuzione, relazione);
								}

								logger.info("Errore gestito - Nessun Prodotto per asmName: "
										+ asmName
										+ ", applicazione: "
										+ asmRow.get(dmalmAsm.applicazione));

								if (check) {
									ErroriCaricamentoDAO
											.insert(DmAlmConstants.FONTE_MISURA,
													DmAlmConstants.TARGET_ASM_PRODOTTIARCHITETTURE,
													"COD_ASM non presente in  oreste :  "
															+ asmName
															+ "§ APPLICAZIONE: "
															+ asmRow.get(dmalmAsm.applicazione),
													DmAlmConstants.WRONG_LINK_ASM_PRODOTTO,
													DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
													dataEsecuzione);
								}
							} else {
								for (Tuple productRow : productList) {
									if (productRow != null) {
										if (unitaOrganizzativa
												.equalsIgnoreCase("")) {
											unitaOrganizzativa = productRow
													.get(qDmalmElProdottiArchitetture.unitaOrganizzativaFk)
													.toString();
										} else {
											if (!unitaOrganizzativa
													.equalsIgnoreCase(productRow
															.get(qDmalmElProdottiArchitetture.unitaOrganizzativaFk)
															.toString())) {
												unitaOrganizzativaDoppia = productRow
														.get(qDmalmElProdottiArchitetture.unitaOrganizzativaFk)
														.toString();
											}
										}

										if (asmRow
												.get(qDmalmAsmProdottiArchitetture.dmalmProdottoPk) != null) {
											DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
											relazione.setDmalmAsmPk(asmRow
													.get(dmalmAsm.dmalmAsmPk));
											relazione
													.setDmalmProdottoPk(asmRow
															.get(qDmalmAsmProdottiArchitetture.dmalmProdottoPk));
											relazione
													.setDataInizioValidita(asmRow
															.get(qDmalmAsmProdottiArchitetture.dtInizioValidita));
											relazione
													.setDataFineValidita(DateUtils
															.addSecondsToTimestamp(
																	dataEsecuzione,
																	-1));

											logger.debug("update AsmProdotto chiusura relazione Tappo per Asm legata a nuovo Prodotto - dmalmAsmPk: "
													+ relazione.getDmalmAsmPk()
													+ ", dmalmProdottoSeq: "
													+ relazione
															.getDmalmProdottoPk()
													+ ", dataInizioValidita: "
													+ relazione
															.getDataInizioValidita()
													+ ", dataFineValidita: "
													+ relazione
															.getDataFineValidita());

											DmAlmAsmProdottiArchitettureDAO
													.closeRelAsmProdottiArchitetture(relazione);
										}

										DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
										relazione.setDmalmAsmPk(asmRow
												.get(dmalmAsm.dmalmAsmPk));
										relazione
												.setDmalmProdottoPk(productRow
														.get(qDmalmElProdottiArchitetture.prodottoPk));

										DmAlmAsmProdottiArchitettureDAO
												.insertRelAsmProdottiArchitetture(
														dataEsecuzione,
														relazione);
									}
								}
							}
						}

						// Nei casi di Asm multiple se nell'asociazione
						// Asm/Prodotto riscontro che i prodotti associati sono
						// relativi ad U.O. diverse segnalo l'incongruenza
						if (!unitaOrganizzativaDoppia.equalsIgnoreCase("")) {
							logger.info("Errore gestito - Asm associata a più U.O. applicazione: "
									+ asmRow.get(dmalmAsm.applicazione)
									+ ", U.O. 1: "
									+ unitaOrganizzativa
									+ ", U.O. 2: " + unitaOrganizzativaDoppia);

							ErroriCaricamentoDAO
									.insert(DmAlmConstants.FONTE_MISURA,
											DmAlmConstants.WRONG_LINK_ASM_UNITAORGANIZZATIVA,
											"APPLICAZIONE: "
													+ asmRow.get(dmalmAsm.applicazione)
													+ ", U.O 1: "
													+ unitaOrganizzativa
													+ ", U.O 2: "
													+ unitaOrganizzativaDoppia,
											DmAlmConstants.WRONG_LINK_ASM_PRODOTTO,
											DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
											dataEsecuzione);
						}

					} // multi asm
				}
			}

			/* Step 4 - Collegamento prodotti non associati alle ASM */

			prodLink = ProdottiArchitettureDAO.getAllProductArchToLinkWithAsm();
			if (prodLink.size() > 0) {
				for (Tuple row : prodLink) {
					logger.info("Collego il prodotto "
							+ row.get(qDmalmElProdottiArchitetture.prodottoPk)
							+ " al tappo ASM");
					
					DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
					relazione.setDmalmAsmPk(0);
					relazione.setDmalmProdottoPk(row
							.get(qDmalmElProdottiArchitetture.prodottoPk));

					DmAlmAsmProdottiArchitettureDAO
							.insertRelAsmProdottiArchitetture(dataEsecuzione,
									relazione);
				}
			}

			/*
			 * Step 5 chiusura prodotti architetture che hanno già una relazione
			 * con ASM
			 */
			ProdottiArchitettureDAO.closeProductArchDuplicate();

			logger.debug("STOP CheckLinkAsmSferaProdottiArchFacade");
		} catch (DAOException e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		}
	}
}
