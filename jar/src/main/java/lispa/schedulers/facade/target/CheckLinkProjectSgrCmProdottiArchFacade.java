package lispa.schedulers.facade.target;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProjectProdottiArchitetture;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.target.DmAlmProjectProdottiArchitettureDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.QDmalmProjectProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckLinkProjectSgrCmProdottiArchFacade {
	private static Logger logger = Logger
			.getLogger(CheckLinkProjectSgrCmProdottiArchFacade.class);
	private static QDmalmProject dmalmProject = QDmalmProject.dmalmProject;
	private static QDmalmElProdottiArchitetture dmalmProdottiArchitetture = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static QDmalmProjectProdottiArchitetture dmalmProjectProdotto = QDmalmProjectProdottiArchitetture.qDmalmProjectProdottiArchitetture;

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {
		try {
			if (ErrorManager.getInstance().hasError())
				return;

			logger.info("START CheckLinkProjectSgrCmProdottiArchFacade");

			boolean projectLegatoTappo = false;
			String[] multiSiglaProject = null;
			List<Tuple> relList = new ArrayList<Tuple>();
			List<Tuple> productList = new ArrayList<Tuple>();
			List<String> prjLinkedList = new ArrayList<String>();

			/**
			 * step 1 - Chiusura associazioni Prodotti chiusi: leggo tutte le
			 * relazioni "aperte" che hanno un puntamento ad un prodotto chiuso
			 * (è stato storicizzato il prodotto), queste relazioni le chiudo;
			 * lo step 3 partendo dai Project creerà una relazione nuova verso
			 * il prodotto "aperto"
			 */

			// tutti i Prodotti chiusi con relazione a Project ancora aperta
			relList = DmAlmProjectProdottiArchitettureDAO
					.getAllRelationsToCloseCauseProductsClosed();

			// update DataFineValidita su tabella relazione
			for (Tuple projectProductRow : relList) {
				if (projectProductRow != null) {
					DmalmProjectProdottiArchitetture relazione = new DmalmProjectProdottiArchitetture();
					relazione.setDmalmProjectPk(projectProductRow
							.get(dmalmProjectProdotto.dmalmProjectPk));
					relazione.setDmalmProdottoPk(projectProductRow
							.get(dmalmProjectProdotto.dmalmProdottoPk));
					relazione.setDataInizioValidita(projectProductRow
							.get(dmalmProjectProdotto.dtInizioValidita));
					relazione.setDataFineValidita(projectProductRow
							.get(dmalmProdottiArchitetture.dataFineValidita));

					DmAlmProjectProdottiArchitettureDAO
							.closeRelProjectProdotto(relazione);
				}
			}

			/** step 2 - Chiusura associazioni Project chiusi */

			// legge tutte i Project chiusi con associazioni aperte da chiudere
			relList = DmAlmProjectProdottiArchitettureDAO
					.getAllRelationsToCloseCauseProjectClosed();

			// update DataFineValidita su tabella relazione
			for (Tuple projectProductRow : relList) {
				if (projectProductRow != null) {
					DmalmProjectProdottiArchitetture relazione = new DmalmProjectProdottiArchitetture();
					relazione.setDmalmProjectPk(projectProductRow
							.get(dmalmProjectProdotto.dmalmProjectPk));
					relazione.setDmalmProdottoPk(projectProductRow
							.get(dmalmProjectProdotto.dmalmProdottoPk));
					relazione.setDataInizioValidita(projectProductRow
							.get(dmalmProjectProdotto.dtInizioValidita));
					relazione.setDataFineValidita(projectProductRow
							.get(dmalmProject.dtFineValidita));

					DmAlmProjectProdottiArchitettureDAO
							.closeRelProjectProdotto(relazione);
				}
			}

			/** step 3 - Associazione Project Aperti con Prodotto Aperto */

			// legge tutti i project non associati
			relList = DmAlmProjectProdottiArchitettureDAO
					.getAllProjectToLinkWithProduct();
			logger.debug("Project letti: " + relList.size());

			// insert su tabella relazione
			for (Tuple projectRow : relList) {
				if (projectRow != null) {
					String siglaProject = projectRow
							.get(dmalmProject.siglaProject) == null ? "NULL"
							: projectRow.get(dmalmProject.siglaProject);

					// gestione multi siglaProject
					projectLegatoTappo = false;
					prjLinkedList = new ArrayList<String>();
					multiSiglaProject = siglaProject.split("\\.\\.");

					for (String siglaPrj : multiSiglaProject) {
						// toglie eventuali moduli dopo il nome Project
						if (siglaPrj.contains(".")) {
							siglaPrj = siglaPrj.substring(0,
									siglaPrj.indexOf("."));
						}

						// scarta nomi duplicati
						if (!prjLinkedList.contains(siglaPrj)) {
							prjLinkedList.add(siglaPrj);

							// ricerca Prodotto per Project (siglaProject =
							// siglaProdotto)
							productList = ElettraProdottiArchitettureDAO
									.getProductByAcronym(siglaPrj);

							if (productList.size() == 0) {
								if (projectRow
										.get(dmalmProjectProdotto.dmalmProjectPk) == null
										&& !projectLegatoTappo) {
									projectLegatoTappo = true;
									
									DmalmProjectProdottiArchitetture relazione = new DmalmProjectProdottiArchitetture();
									relazione
											.setDmalmProjectPk(projectRow
													.get(dmalmProject.dmalmProjectPrimaryKey));
									relazione.setDmalmProdottoPk(0);

									DmAlmProjectProdottiArchitettureDAO
											.insertRelProjectProdotto(
													dataEsecuzione, relazione);
								}

								// segnalo solo una volta
								if (!projectLegatoTappo) {
									ErroriCaricamentoDAO
											.insert(DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
													DmAlmConstants.TARGET_DMALM_PROJECT_PRODOTTIARCHITETTURE,
													"SIGLA PROJECT: "
															+ siglaPrj
															+ "§ NAME PROJECT: "
															+ projectRow
																	.get(dmalmProject.nomeCompletoProject),
													DmAlmConstants.WRONG_LINK_PROJECT_PRODOTTO,
													DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
													dataEsecuzione);
								}
							} else {
								for (Tuple productRow : productList) {
									if (productRow != null) {
										if (projectRow
												.get(dmalmProjectProdotto.dmalmProjectPk) != null) {
											// chiudo la vecchia relazione al
											// tappo (dmalmProdottoSeq = 0)
											DmalmProjectProdottiArchitetture relazione = new DmalmProjectProdottiArchitetture();
											relazione
													.setDmalmProjectPk(projectRow
															.get(dmalmProjectProdotto.dmalmProjectPk));
											relazione
													.setDmalmProdottoPk(projectRow
															.get(dmalmProjectProdotto.dmalmProdottoPk));
											relazione
													.setDataInizioValidita(projectRow
															.get(dmalmProjectProdotto.dtInizioValidita));
											relazione
													.setDataFineValidita(DateUtils
															.addSecondsToTimestamp(
																	dataEsecuzione,
																	-1));

											DmAlmProjectProdottiArchitettureDAO
													.closeRelProjectProdotto(relazione);
										}

										DmalmProjectProdottiArchitetture relazione = new DmalmProjectProdottiArchitetture();
										relazione
												.setDmalmProjectPk(projectRow
														.get(dmalmProject.dmalmProjectPrimaryKey));
										relazione
												.setDmalmProdottoPk(productRow
														.get(dmalmProdottiArchitetture.prodottoPk));

										DmAlmProjectProdottiArchitettureDAO
												.insertRelProjectProdotto(
														dataEsecuzione,
														relazione);
									}
								}
							}
						}
					}
				}
			}

			/**
			 * step 4 - Associazione di tutti i Prodotti Aperti non associabili
			 * (relazionandoli al Tappo del Project)
			 */

			// legge tutti i project non associati
			relList = ElettraProdottiArchitettureDAO
					.getAllProductToLinkWithProject();
			logger.debug("Prodotti letti: " + relList.size());

			// insert su tabella relazione
			for (Tuple productRow : relList) {
				if (productRow != null) {
					DmalmProjectProdottiArchitetture relazione = new DmalmProjectProdottiArchitetture();
					relazione.setDmalmProjectPk(0);
					relazione.setDmalmProdottoPk(productRow
							.get(dmalmProdottiArchitetture.prodottoPk));

					DmAlmProjectProdottiArchitettureDAO
							.insertRelProjectProdotto(dataEsecuzione, relazione);
				}
			}

			/**
			 * step 5 - chiude i prodotti legati al tappo per i quali esiste
			 * anche una relazione a Project
			 */

			DmAlmProjectProdottiArchitettureDAO.closeProductDuplicate();

			logger.info("STOP CheckLinkProjectSgrCmProdottiArchFacade");
		} catch (DAOException e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		}
	}
}
