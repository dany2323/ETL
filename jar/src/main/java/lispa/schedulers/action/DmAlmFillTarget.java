package lispa.schedulers.action;

import static lispa.schedulers.constant.DmAlmConstants.SCHEDULAZIONE_BO_ENABLE;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_TARGET_LOG_DELETE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

import lispa.schedulers.bean.target.Total;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.TotalDao;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckProjectStorFacade;
import lispa.schedulers.facade.elettra.target.ElettraAmbienteTecnologicoClassificatoriFacade;
import lispa.schedulers.facade.elettra.target.ElettraAmbienteTecnologicoFacade;
import lispa.schedulers.facade.elettra.target.ElettraClassificatoriFacade;
import lispa.schedulers.facade.elettra.target.ElettraFunzionalitaFacade;
import lispa.schedulers.facade.elettra.target.ElettraModuliFacade;
import lispa.schedulers.facade.elettra.target.ElettraPersonaleFacade;
import lispa.schedulers.facade.elettra.target.ElettraProdottiArchitettureFacade;
import lispa.schedulers.facade.elettra.target.ElettraUnitaOrganizzativeFacade;
import lispa.schedulers.facade.mps.target.TargetMpsFacade;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaProdottiArchFacade;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaStrutturaOrganizzativaFacade;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaUnitaOrganizzativaFacade;
import lispa.schedulers.facade.sfera.target.AsmFacade;
import lispa.schedulers.facade.sfera.target.MisuraFacade;
import lispa.schedulers.facade.sfera.target.ProgettoSferaFacade;
import lispa.schedulers.facade.target.AreaTematicaSgrCmFacade;
import lispa.schedulers.facade.target.AttachmentFacade;
import lispa.schedulers.facade.target.CheckLinkPersonaleUnitaOrganizzativaFacade;
import lispa.schedulers.facade.target.HyperlinkFacade;
import lispa.schedulers.facade.target.LinkedWorkitemsFacade;
import lispa.schedulers.facade.target.PersonaleEdmaFacade;
import lispa.schedulers.facade.target.ProjectRolesSgrFacade;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.facade.target.SchedeServizioFacade;
import lispa.schedulers.facade.target.StatoWorkitemSgrCmFacade;
import lispa.schedulers.facade.target.StrutturaOrganizzativaEdmaFacade;
import lispa.schedulers.facade.target.UserRolesSgrFacade;
import lispa.schedulers.facade.target.UserSgrCmFacade;
import lispa.schedulers.facade.target.WorkitemUserAssigneeFacade;
import lispa.schedulers.facade.target.fatti.AnomaliaAssistenzaFacade;
import lispa.schedulers.facade.target.fatti.AnomaliaProdottoFacade;
import lispa.schedulers.facade.target.fatti.BuildFacade;
import lispa.schedulers.facade.target.fatti.ClassificatoreFacade;
import lispa.schedulers.facade.target.fatti.DifettoProdottoFacade;
import lispa.schedulers.facade.target.fatti.DocumentoFacade;
import lispa.schedulers.facade.target.fatti.FaseFacade;
import lispa.schedulers.facade.target.fatti.ManutenzioneFacade;
import lispa.schedulers.facade.target.fatti.PeiFacade;
import lispa.schedulers.facade.target.fatti.ProgettoDemandFacade;
import lispa.schedulers.facade.target.fatti.ProgettoEseFacade;
import lispa.schedulers.facade.target.fatti.ProgettoSviluppoDemandFacade;
import lispa.schedulers.facade.target.fatti.ProgettoSviluppoSviluppoFacade;
import lispa.schedulers.facade.target.fatti.ProgrammaFacade;
import lispa.schedulers.facade.target.fatti.ReleaseDiProgettoFacade;
import lispa.schedulers.facade.target.fatti.ReleaseItFacade;
import lispa.schedulers.facade.target.fatti.ReleaseServiziFacade;
import lispa.schedulers.facade.target.fatti.RichiestaGestioneFacade;
import lispa.schedulers.facade.target.fatti.RichiestaManutenzioneFacade;
import lispa.schedulers.facade.target.fatti.RichiestaSupportoFacade;
import lispa.schedulers.facade.target.fatti.SottoprogrammaFacade;
import lispa.schedulers.facade.target.fatti.TaskFacade;
import lispa.schedulers.facade.target.fatti.TaskItFacade;
import lispa.schedulers.facade.target.fatti.TestCaseFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.manager.RecoverManager;
import lispa.schedulers.queryimplementation.staging.QDmalmEsitiCaricamenti;

/**
 * I dati vengono infine memorizzati nelle tabelle del sistema di sintesi, per
 * consentire di effettuare le necessarie analisi su di essi, disponendoli
 * secondo una struttura predefinita in fase di analisi che prevede l’esistenza
 * di due tipi di tabelle: fatti e dimensioni.
 * 
 * @author fdeangel
 * 
 */
public class DmAlmFillTarget {

	private static Logger logger = Logger.getLogger(DmAlmFillTarget.class);

	private static int days;

	protected static void doWork() {
		// definisco il numero di giorni superati i quali, i regord con DATA
		// minore nella tabella di DMALM_LOG_DEBUG vengono cancellati
		boolean enableRecordSentinella = false;

		try {
			days = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_TARGET_LOG_DELETE));

			enableRecordSentinella = DmAlmConfigReader.getInstance()
					.getProperty(DmAlmConfigReaderProperties.SCHEDULAZIONE_BO)
					.equals(SCHEDULAZIONE_BO_ENABLE);
		} catch (NumberFormatException | PropertiesReaderException e1) {
			logger.error(e1.getMessage());
		}

		try {
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();
			
			boolean flag = false;
			if (ExecutionManager.getInstance().isExecutionSfera()
					|| ExecutionManager.getInstance().isExecutionElettraSgrcm()) {
				// preparo il target al Recover in caso di errori bloccanti:
				// svuoto le tabelle di backup, effettuo il backup del target
				// allo stato corrente (ultimo stato consistente)
				flag = RecoverManager.getInstance().prepareTargetForRecover(dataEsecuzione);
			}
			
			if (flag) {
				// ELETTRA/SGRCM
				if (ExecutionManager.getInstance().isExecutionElettraSgrcm()) {
					// -- Elettra inizio ------------------------------------------
					if (!alreadyExecuted(DmAlmConstants.TARGET_ELETTRA_UNITAORGANIZZATIVE)) {
						ElettraUnitaOrganizzativeFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione: "
								+ DmAlmConstants.TARGET_ELETTRA_UNITAORGANIZZATIVE);
					}
					if (!alreadyExecuted(DmAlmConstants.TARGET_ELETTRA_PERSONALE)) {
						ElettraPersonaleFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione: "
								+ DmAlmConstants.TARGET_ELETTRA_PERSONALE);
					}
					if (!alreadyExecuted(DmAlmConstants.TARGET_ELETTRA_CLASSIFICATORI)) {
						ElettraClassificatoriFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione: "
								+ DmAlmConstants.TARGET_ELETTRA_CLASSIFICATORI);
					}
					if (!alreadyExecuted(DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE)) {
						ElettraProdottiArchitettureFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione: "
								+ DmAlmConstants.TARGET_ELETTRA_PRODOTTIARCHITETTURE);
					}
					if (!alreadyExecuted(DmAlmConstants.TARGET_ELETTRA_MODULI)) {
						ElettraModuliFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione: "
								+ DmAlmConstants.TARGET_ELETTRA_MODULI);
					}
					if (!alreadyExecuted(DmAlmConstants.TARGET_ELETTRA_FUNZIONALITA)) {
						ElettraFunzionalitaFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione: "
								+ DmAlmConstants.TARGET_ELETTRA_FUNZIONALITA);
					}
					if (!alreadyExecuted(DmAlmConstants.TARGET_ELETTRA_AMBIENTETECNOLOGICO)) {
						ElettraAmbienteTecnologicoFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione: "
								+ DmAlmConstants.TARGET_ELETTRA_AMBIENTETECNOLOGICO);
					}
					if (!alreadyExecuted(DmAlmConstants.TARGET_ELETTRA_AMBIENTETECNOLOGICOCLASSIFICATORI)) {
						ElettraAmbienteTecnologicoClassificatoriFacade
								.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione: "
								+ DmAlmConstants.TARGET_ELETTRA_AMBIENTETECNOLOGICOCLASSIFICATORI);
					}
					// -- Elettra Fine ------------------------------------------
	
					logger.info("START PersonaleEdmaFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_EDMA_PERSONALE)) {
						PersonaleEdmaFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START UserSgrCmFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_USER)) {
						UserSgrCmFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START AreaTematicaSgrCmFacade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_AREATEMATICA)) {
						AreaTematicaSgrCmFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START StrutturaOrganizzativaEdmaFacade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_EDMA_UNITAORGANIZZATIVA)) {
						StrutturaOrganizzativaEdmaFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					
					// Aggiunto checkpoint di recovery in data 17/07/17
					if (ErrorManager.getInstance().hasError()) {
						RecoverManager.getInstance().startRecoverTargetByProcedure();
						RecoverManager.getInstance().startRecoverStaging();
	
						// MPS
						if (ExecutionManager.getInstance().isExecutionMps())
							RecoverManager.getInstance().startRecoverStgMps();
	
						return;
					}
	
					logger.info("START ProjectSgrCmFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT)) {
						ProjectSgrCmFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					
					// Aggiunto checkpoint di recovery in data 19/04/17
					if (ErrorManager.getInstance().hasError()) {
						RecoverManager.getInstance().startRecoverTargetByProcedure();
						RecoverManager.getInstance().startRecoverStaging();
	
						// MPS
						if (ExecutionManager.getInstance().isExecutionMps())
							RecoverManager.getInstance().startRecoverStgMps();
	
						return;
					}
	
					logger.info("START UserRolesSgrFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_USERROLES)) {
						UserRolesSgrFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START StatoWorkitemSgrCmFacade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_STATOWORKITEM)) {
						StatoWorkitemSgrCmFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					
					logger.info("START SchedeServizioFacade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_SCHEDE_SERVIZIO)) {
						SchedeServizioFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					try {
						ConnectionManager.getInstance().dismiss();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
	
					/** FATTI **/
	
					logger.info("START PeiFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_PEI)) {
						PeiFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START BuildFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_BUILD)) {
						BuildFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START ProgettoESEFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_PROGETTO_ESE)) {
						ProgettoEseFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START TestCaseFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_TESTCASE)) {
						TestCaseFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START FaseFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_FASE)) {
						FaseFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START SottoprogrammaFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_SOTTOPROGRAMMA)) {
						SottoprogrammaFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START ProgrammaFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_PROGRAMMA)) {
						ProgrammaFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START ProgettoSviluppoDemandFacade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_PROGETTO_SVILUPPO_DEMAND)) {
						ProgettoSviluppoDemandFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START TaskItFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_TASKIT)) {
						TaskItFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START ReleaseServiziFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_RELEASE_SERVIZI)) {
						ReleaseServiziFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START ProgettoDemandFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_PROGETTO_DEMAND)) {
						ProgettoDemandFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START RichiestaManutenzioneFacade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_RICHIESTA_MANUTENZIONE)) {
						RichiestaManutenzioneFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					
					logger.info("START ClassificatoreFacade.execute ");
					if (!alreadyExecuted(DmAlmConstants.TARGET_CLASSIFICATORE)) {
						ClassificatoreFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					if (ErrorManager.getInstance().hasError()) {
						RecoverManager.getInstance().startRecoverTargetByProcedure();
						RecoverManager.getInstance().startRecoverStaging();
	
						// MPS
						if (ExecutionManager.getInstance().isExecutionMps())
							RecoverManager.getInstance().startRecoverStgMps();
	
						return;
					}
	
					logger.info("START RichiestaGestioneFacade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_RICHIESTA_GESTIONE)) {
						RichiestaGestioneFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START Progetto_Svil_Svil_Facade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_PROGETTO_SVILUPPO_SVILUPPO)) {
						ProgettoSviluppoSviluppoFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START AnomaliaAssistenzaFacade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_ANOMALIA_ASSISTENZA)) {
						AnomaliaAssistenzaFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START AnomaliaFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_ANOMALIA)) {
						AnomaliaProdottoFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START TaskFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_TASK)) {
						TaskFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START ReleaseITFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_RELEASE_IT)) {
						ReleaseItFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START DifettoFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_DIFETTO)) {
						DifettoProdottoFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START ManutenzioneFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_MANUTENZIONE)) {
						ManutenzioneFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START DocumentoFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_DOCUMENTO)) {
						DocumentoFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START ReleaseDiProgettoFacade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_RELEASE_DI_PROG)) {
						ReleaseDiProgettoFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					
					// DM_ALM-350
					logger.info("START RichiestaSupporto.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_RICHIESTA_SUPPORTO)) {
						RichiestaSupportoFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					// FINE DM_ALM-350
	
					if (ErrorManager.getInstance().hasError()) {
						RecoverManager.getInstance().startRecoverTargetByProcedure();
						RecoverManager.getInstance().startRecoverStaging();
	
						// MPS
						if (ExecutionManager.getInstance().isExecutionMps())
							RecoverManager.getInstance().startRecoverStgMps();
	
						return;
					}
	
					try {
						ConnectionManager.getInstance().dismiss();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					
					//DMALM-198 Storicizzazione workitem a seguito della storicizzazione dei project
					CheckProjectStorFacade.execute();
					
					QueryManager qm = QueryManager.getInstance();
	
					qm.executeMultipleStatementsFromFile(
							DmAlmConstants.M_UPDATE_RANK_FATTI,
							DmAlmConstants.M_SEPARATOR);
					qm.executeMultipleStatementsFromFile(
							DmAlmConstants.M_UPDATE_TEMPO_FATTI,
							DmAlmConstants.M_SEPARATOR);
					qm.executeMultipleStatementsFromFile(
							DmAlmConstants.M_UPDATE_AT_FATTI,
							DmAlmConstants.M_SEPARATOR);
					qm.executeMultipleStatementsFromFile(
							DmAlmConstants.M_UPDATE_UO_FATTI,
							DmAlmConstants.M_SEPARATOR);
	
					TotalDao.refreshTable();
					// ATTRIBUTI COMPLESSI DEI FATTI
					
					
					logger.info("START LinkedWorkitemsFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_LINKEDWORKITEMS)) {
						LinkedWorkitemsFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					TotalDao.refreshTable();
					logger.info("START AttachmentFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_ATTACHMENT)) {
						AttachmentFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START HyperlinkFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_HYPERLINK)) {
						HyperlinkFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					if (ErrorManager.getInstance().hasError()) {
						RecoverManager.getInstance().startRecoverTargetByProcedure();
						RecoverManager.getInstance().startRecoverStaging();
	
						// MPS
						if (ExecutionManager.getInstance().isExecutionMps())
							RecoverManager.getInstance().startRecoverStgMps();
	
						return;
					}
	
					try {
						ConnectionManager.getInstance().dismiss();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
	
					// ORESTE
					/*
					logger.info("START ClassificatoriFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_ORESTE_CLASSIFICATORI)) {
						ClassificatoriFacade.execute(dataEsecuzione); //Modifica all'interno di un target db Oreste
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					
					logger.info("START ProdottoFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_ORESTE_PRODOTTO)) {
						ProdottoFacade.execute(dataEsecuzione); //Modifica all'interno di un target db Oreste
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					
					logger.info("START SottosistemaFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA)) {
						SottosistemaFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					
					logger.info("START ModuloFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_ORESTE_MODULO)) {
						ModuloFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					
					logger.info("START FunzionalitaFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_ORESTE_FUNZIONALITA)) {
						FunzionalitaFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					
					logger.info("START AmbienteTecnologicoFacade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_ORESTE_AMBIENTETECNOLOGICO)) {
						AmbienteTecnologicoFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
					*/
					// logger.info("START RelClassificatoriOresteFacade.execute "
					// + new Date());
					// if
					// (!alreadyExecuted(DmAlmConstants.TARGET_REL_CLASSIFICATORI))
					// {
					// RelClassificatoriOresteFacade.execute(dataEsecuzione);
					// } else {
					// logger.info("Entità già elaborata per la data di esecuzione ");
					// }
	
					if (ErrorManager.getInstance().hasError()) {
						RecoverManager.getInstance().startRecoverTargetByProcedure();
						RecoverManager.getInstance().startRecoverStaging();
	
						// MPS
						if (ExecutionManager.getInstance().isExecutionMps())
							RecoverManager.getInstance().startRecoverStgMps();
	
						return;
					}
	
					// XML
	
					logger.info("START ProjectRolesSgrFacade.execute " + new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_PROJECTROLES)) {
						ProjectRolesSgrFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					logger.info("START WorkitemUserAssigneeFacade.execute "
							+ new Date());
					if (!alreadyExecuted(DmAlmConstants.TARGET_WORKITEMASSIGNEES)) {
						WorkitemUserAssigneeFacade.execute(dataEsecuzione);
					} else {
						logger.info("Entità già elaborata per la data di esecuzione ");
					}
	
					//Se eseguito altrimenti si perde la storia delle modifiche, update di tutto e toglie gli zeri alle FK
	//				logger.info("START updateFKUOonProjects() " + new Date());
	//				// UPDATE STORICO DEI PROJECT CON UO A 0
	//				ProjectSgrCmDAO.updateFKUOonProjects();
	
					if (ErrorManager.getInstance().hasError()) {
						RecoverManager.getInstance().startRecoverTargetByProcedure();
						RecoverManager.getInstance().startRecoverStaging();
	
						// MPS
						if (ExecutionManager.getInstance().isExecutionMps())
							RecoverManager.getInstance().startRecoverStgMps();
	
						return;
					}
					
					//DM_ALM-237 - Creo legami tra Personale (EDMA) e Unità Organizzative/Flat (Elettra)
	
					CheckLinkPersonaleUnitaOrganizzativaFacade.execute(dataEsecuzione);
	
					if (ErrorManager.getInstance().hasError()) {
						RecoverManager.getInstance().startRecoverTargetByProcedure();
						RecoverManager.getInstance().startRecoverStaging();
	
						// MPS
						if (ExecutionManager.getInstance().isExecutionMps())
							RecoverManager.getInstance().startRecoverStgMps();
	
						return;
					}
	
					// Fine DM_ALM-237
	
					logger.info("DELETE ELAPSED LOG FROM DMALM_LOG_DEBUG");
					PreparedStatement ps = ConnectionManager
							.getInstance()
							.getConnectionOracle()
							.prepareStatement(
									QueryManager
											.getInstance()
											.getQuery(
													DmAlmConfigReaderProperties.SQL_TARGET_LOG_DELETE));
					ps.setInt(1, days);
					ps.execute();
				} // EDMA
	
				// SFERA
				if (ExecutionManager.getInstance().isExecutionSfera()) {
					AsmFacade.execute(dataEsecuzione);
	
					// step 1 crea legami Asm/Prodotto
					// Prodotto Oreste
					//CheckLinkAsmSferaProdottoFacade.execute(dataEsecuzione, false);
					// ProdottiArchitetture Elettra
					//CheckLinkAsmSferaProdottiArchFacade.execute(dataEsecuzione, false);
					
					// step 2 crea fk Asm/UO (Edma)
					// Struttura Organizzativa Edma
					CheckLinkAsmSferaStrutturaOrganizzativaFacade
							.execute(dataEsecuzione);
	
					// step 3 crea i nuovi legami per eventuali asm storicizzate da
					// CheckLinkAsmSferaStrutturaOrganizzativaFacade
					// Prodotto Oreste
					//CheckLinkAsmSferaProdottoFacade.execute(dataEsecuzione, false);
					// ProdottiArchitetture Elettra
					CheckLinkAsmSferaProdottiArchFacade.execute(dataEsecuzione, false);
	
					// step 4 crea fk Asm/UO (Elettra)
					// Unità Organizzativa Elettra
					CheckLinkAsmSferaUnitaOrganizzativaFacade
							.execute(dataEsecuzione);
	
					// step 5 crea i nuovi legami per eventuali asm storicizzate da
					// CheckLinkAsmSferaUnitaOrganizzativaFacade
					// Prodotto Oreste
					//CheckLinkAsmSferaProdottoFacade.execute(dataEsecuzione, true);
					// ProdottiArchitetture Elettra
					CheckLinkAsmSferaProdottiArchFacade.execute(dataEsecuzione, true);
					
					ProgettoSferaFacade.execute(dataEsecuzione);
	
					MisuraFacade.execute(dataEsecuzione);
	
					if (ErrorManager.getInstance().hasError()) {
						RecoverManager.getInstance().startRecoverTargetByProcedure();
						RecoverManager.getInstance().startRecoverStaging();
	
						// MPS
						if (ExecutionManager.getInstance().isExecutionMps())
							RecoverManager.getInstance().startRecoverStgMps();
	
						return;
					}
				}
				
				// MPS
				if (ExecutionManager.getInstance().isExecutionMps()) {
					// eseguo il target solo se non è stato fatto il recover dello
					// staging
					if (!RecoverManager.getInstance().isRecoveredStagingMps()) {
	
						TargetMpsFacade.fillTargetMps();
	
						if (ErrorManager.getInstance().hasError()) {
							logger.fatal("ERRORE: Inizio procedura di ripristino");
	
							// in caso di errore viene chiamato soltanto il recover
							// di MPS
							RecoverManager.getInstance().startRecoverTrgMps();
							RecoverManager.getInstance().startRecoverStgMps();
							ErrorManager.getInstance().resetError();
						}
					}
				}
	
				// INSERISCO IL RECORD SENTINELLA SOLO SE ESPRESSAMENTE RICHIESTO DA
				// FILE DI PROPERTIES
				if (enableRecordSentinella) {
					logger.info("INSERISCO RECORD SENTINELLA");
					EsitiCaricamentoDAO.inserisciRecordSentinella();
				}
			} else {
				logger.error(DmAlmConstants.ERROR_CARICAMENTO_BACKUP);
				if (ExecutionManager.getInstance().isExecutionSfera()
						|| ExecutionManager.getInstance()
								.isExecutionElettraSgrcm()) {
					RecoverManager.getInstance().startRecoverStaging();
				}

				// MPS
				if (ExecutionManager.getInstance().isExecutionMps()) {
					RecoverManager.getInstance().startRecoverStgMps();
				}
				
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}

	public static boolean alreadyExecuted(String target) {
		boolean res;

		QDmalmEsitiCaricamenti qEsitiCaricamento = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;

		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			List<String> esito = new SQLQuery(connection, dialect)
					.from(qEsitiCaricamento)
					.where(qEsitiCaricamento.entitaTarget.eq(target))
					.where(qEsitiCaricamento.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione()))
					.list(qEsitiCaricamento.statoEsecuzione);

			if (esito == null || (esito != null && esito.size() == 0)) {
				res = false;
			} else {
				res = esito.get(0).equals(DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			res = false;
		} finally {
			try {
				if (cm != null) {
					cm.closeConnection(connection);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return res;
	}
}
