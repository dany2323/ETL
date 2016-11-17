package lispa.schedulers.facade.sfera;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.sfera.DmalmAsmProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmProdottoDAO;
import lispa.schedulers.dao.target.ProdottoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProdotto;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsmProdotto;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckLinkAsmSferaProdottoFacade {
	private static Logger logger = Logger
			.getLogger(CheckLinkAsmSferaProdottoFacade.class);
	private static QDmalmAsm dmalmAsm = QDmalmAsm.dmalmAsm;
	private static QDmalmProdotto dmalmProdotto = QDmalmProdotto.dmalmProdotto;
	private static QDmalmAsmProdotto dmalmAsmProdotto = QDmalmAsmProdotto.dmalmAsmProdotto;

	public static void execute(Timestamp dataEsecuzione, boolean check)
			throws Exception, DAOException {
		try {
			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START CheckLinkAsmSferaProdottoFacade");

			boolean asmLegataTappo = false;
			String[] multiAsm = null;
			String UnitaOrganizzativa = null;
			String UnitaOrganizzativaDoppia = null;
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
			asmList = DmAlmAsmProdottoDAO
					.getAllRelationsToCloseCauseProductsClosed();

			// update DataFineValidita su tabella relazione
			for (Tuple asmProductRow : asmList) {
				if (asmProductRow != null) {
					DmalmAsmProdotto relazione = new DmalmAsmProdotto();
					relazione.setDmalmAsmPk(asmProductRow
							.get(dmalmAsmProdotto.dmalmAsmPk));
					relazione.setDmalmProdottoSeq(asmProductRow
							.get(dmalmAsmProdotto.dmalmProdottoSeq));
					relazione.setDataInizioValidita(asmProductRow
							.get(dmalmAsmProdotto.dtInizioValidita));
					relazione.setDataFineValidita(asmProductRow
							.get(dmalmProdotto.dtFineValidita));

					logger.debug("update AsmProdotto chiusura DataFineValidita per Prodotto chiuso - dmalmAsmPk: "
							+ relazione.getDmalmAsmPk()
							+ ", dmalmProdottoSeq: "
							+ relazione.getDmalmProdottoSeq()
							+ ", dataInizioValidita: "
							+ relazione.getDataInizioValidita()
							+ ", dataFineValidita: "
							+ relazione.getDataFineValidita());

					DmAlmAsmProdottoDAO.closeRelAsmProdotto(relazione);
				}
			}

			/** step 2 - Chiusura associazioni Asm chiuse */

			// legge tutte le asm chiuse con associazioni aperte da chiudere
			asmList = DmAlmAsmDAO.getAllRelationsToClose();

			// update DataFineValidita su tabella relazione
			for (Tuple asmProductRow : asmList) {
				if (asmProductRow != null) {
					DmalmAsmProdotto relazione = new DmalmAsmProdotto();
					relazione.setDmalmAsmPk(asmProductRow
							.get(dmalmAsmProdotto.dmalmAsmPk));
					relazione.setDmalmProdottoSeq(asmProductRow
							.get(dmalmAsmProdotto.dmalmProdottoSeq));
					relazione.setDataInizioValidita(asmProductRow
							.get(dmalmAsmProdotto.dtInizioValidita));
					relazione.setDataFineValidita(asmProductRow
							.get(dmalmAsm.dataFineValidita));

					logger.debug("update AsmProdotto chiusura DataFineValidita per Asm Chiusa - dmalmAsmPk: "
							+ relazione.getDmalmAsmPk()
							+ ", dmalmProdottoSeq: "
							+ relazione.getDmalmProdottoSeq()
							+ ", dataInizioValidita: "
							+ relazione.getDataInizioValidita()
							+ ", dataFineValidita: "
							+ relazione.getDataFineValidita());

					DmAlmAsmProdottoDAO.closeRelAsmProdotto(relazione);
				}
			}

			/** step 3 - Associazione Asm Aperte con Prodotto Aperto */

			// legge tutte le asm non associate
			asmList = DmAlmAsmDAO.getAllAsmToLinkWithProduct();

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
					UnitaOrganizzativa = "";
					UnitaOrganizzativaDoppia = "";
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
							productList = ProdottoDAO
									.getProductByAcronym(asmName);

							if (productList.size() == 0) {
								if (asmRow
										.get(dmalmAsmProdotto.dmalmProdottoSeq) == null
										&& !asmLegataTappo) {
									logger.info("Prodotto da legare al tappo: "
											+ asmName + ", applicazione: "
											+ asmRow.get(dmalmAsm.applicazione));

									asmLegataTappo = true;

									DmalmAsmProdotto relazione = new DmalmAsmProdotto();
									relazione.setDmalmAsmPk(asmRow
											.get(dmalmAsm.dmalmAsmPk));
									relazione.setDmalmProdottoSeq(0);

									DmAlmAsmProdottoDAO.insertRelAsmProdotto(
											dataEsecuzione, relazione);
								}

								logger.info("Errore gestito - Nessun Prodotto per asmName: "
										+ asmName
										+ ", applicazione: "
										+ asmRow.get(dmalmAsm.applicazione));

								if (check) {
									ErroriCaricamentoDAO
											.insert(DmAlmConstants.FONTE_MISURA,
													DmAlmConstants.TARGET_ASM_PRODOTTO,
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

										if (UnitaOrganizzativa
												.equalsIgnoreCase("")) {
											UnitaOrganizzativa = productRow
													.get(dmalmProdotto.dmalmUnitaOrganizzativaFk01)
													.toString();
										} else {
											if (!UnitaOrganizzativa
													.equalsIgnoreCase(productRow
															.get(dmalmProdotto.dmalmUnitaOrganizzativaFk01)
															.toString())) {
												UnitaOrganizzativaDoppia = productRow
														.get(dmalmProdotto.dmalmUnitaOrganizzativaFk01)
														.toString();
											}
										}

										if (asmRow
												.get(dmalmAsmProdotto.dmalmProdottoSeq) != null) {
											DmalmAsmProdotto relazione = new DmalmAsmProdotto();
											relazione
													.setDmalmAsmPk(asmRow
															.get(dmalmAsmProdotto.dmalmAsmPk));
											relazione
													.setDmalmProdottoSeq(asmRow
															.get(dmalmAsmProdotto.dmalmProdottoSeq));
											relazione
													.setDataInizioValidita(asmRow
															.get(dmalmAsmProdotto.dtInizioValidita));
											relazione
													.setDataFineValidita(DateUtils
															.addSecondsToTimestamp(
																	dataEsecuzione,
																	-1));

											logger.debug("update AsmProdotto chiusura relazione Tappo per Asm legata a nuovo Prodotto - dmalmAsmPk: "
													+ relazione.getDmalmAsmPk()
													+ ", dmalmProdottoSeq: "
													+ relazione
															.getDmalmProdottoSeq()
													+ ", dataInizioValidita: "
													+ relazione
															.getDataInizioValidita()
													+ ", dataFineValidita: "
													+ relazione
															.getDataFineValidita());

											DmAlmAsmProdottoDAO
													.closeRelAsmProdotto(relazione);
										}
										
										DmalmAsmProdotto relazione = new DmalmAsmProdotto();
										relazione.setDmalmAsmPk(asmRow
												.get(dmalmAsm.dmalmAsmPk));
										relazione
												.setDmalmProdottoSeq(productRow
														.get(dmalmProdotto.dmalmProdottoSeq));

										DmAlmAsmProdottoDAO
												.insertRelAsmProdotto(
														dataEsecuzione,
														relazione);
									}
								}
							}
						}

						// Nei casi di Asm multiple se nell'associazione
						// Asm/Prodotto riscontro che i prodotti associati sono
						// relativi ad U.O. diverse segnalo l'incongruenza
						if (!UnitaOrganizzativaDoppia.equalsIgnoreCase("")) {
							logger.info("Errore gestito - Asm associata a più U.O. applicazione: "
									+ asmRow.get(dmalmAsm.applicazione)
									+ ", U.O. 1: "
									+ UnitaOrganizzativa
									+ ", U.O. 2: " + UnitaOrganizzativaDoppia);

							ErroriCaricamentoDAO.insert(
									DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_ASM_PRODOTTO,
									"APPLICAZIONE: "
											+ asmRow.get(dmalmAsm.applicazione)
											+ ", U.O 1: " + UnitaOrganizzativa
											+ ", U.O 2: "
											+ UnitaOrganizzativaDoppia,
									DmAlmConstants.WRONG_LINK_ASM_PRODOTTO,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}

					} // multi asm
				}
			}

			/* Step 4 - Collegamento di tutti i prodotti non collegati alle ASM */

			prodLink = ProdottoDAO.getAllProductToLinkWithAsm();
			if (prodLink.size() > 0) {
				for (Tuple row : prodLink) {
					logger.info("Collego il prodotto "
							+ row.get(dmalmProdotto.dmalmProdottoSeq)
							+ " al tappo ASM");
					
					DmalmAsmProdotto relazione = new DmalmAsmProdotto();
					relazione.setDmalmAsmPk(0);
					relazione.setDmalmProdottoSeq(row
							.get(dmalmProdotto.dmalmProdottoSeq));
					DmAlmAsmProdottoDAO.insertRelAsmProdotto(dataEsecuzione,
							relazione);
				}
			}

			/*
			 * Step 5 chiusura prodotti architetture che hanno già una relazione
			 * con ASM
			 */
			ProdottoDAO.closeProductDuplicate();

			logger.debug("STOP CheckLinkAsmSferaProdottoFacade");
		} catch (DAOException e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		}
	}
}
