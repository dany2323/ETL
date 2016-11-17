package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.DmalmProjectUnitaOrganizzativaEccezioni;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.dao.target.ProjectUnitaOrganizzativaEccezioniDAO;
import lispa.schedulers.dao.target.StrutturaOrganizzativaEdmaLispaDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ProjectSgrCmFacade {

	private static Logger logger = Logger.getLogger(ProjectSgrCmFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		List<DmalmProject> staging_projects = new ArrayList<DmalmProject>();
		List<Tuple> target_projects = new ArrayList<Tuple>();
		QDmalmProject proj = QDmalmProject.dmalmProject;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
		DmalmProject project_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			staging_projects = ProjectSgrCmDAO.getAllProject(dataEsecuzione);

			for (DmalmProject project : staging_projects) {
				project_tmp = project;
				// Ricerco nel db target un record con idProject =
				// project.getIdProject e data fine validita uguale a 31-12-9999

				target_projects = ProjectSgrCmDAO.getProject(project);
				logger.debug("target_projects size: "+target_projects.size());
				
				// se non trovo almento un record, inserisco il project nel
				// target
				if (target_projects.size() == 0) {
					righeNuove++;
					ProjectSgrCmDAO.insertProject(project);
					logger.debug("Project insert done");
				} else {
					boolean modificato = false;

					for (Tuple row : target_projects) {
						if (row != null) {
							if (BeanUtils.areDifferent(
									row.get(proj.nomeCompletoProject),
									project.getNomeCompletoProject())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(proj.siglaProject),
									project.getSiglaProject())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(proj.pathProject),
									project.getPathProject())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(proj.serviceManagers),
									project.getServiceManagers())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(proj.annullato),
									project.getAnnullato())) {
								modificato = true;
							}
							// Edma
							if (BeanUtils.areDifferent(
									row.get(proj.dmalmStrutturaOrgFk02),
									project.getDmalmStrutturaOrgFk02())) {
								modificato = true;
							}
							// Elettra
							if (BeanUtils.areDifferent(
									row.get(proj.dmalmUnitaOrganizzativaFk),
									project.getDmalmUnitaOrganizzativaFk())) {
								modificato = true;
							}

							if (modificato) {
								logger.debug("Project modified");
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								ProjectSgrCmDAO.updateDataFineValidita(
										dataEsecuzione, project);
								logger.debug("Old Project updated");
								// inserisco un nuovo record
								ProjectSgrCmDAO.insertProjectUpdate(
										dataEsecuzione, project, true);
								logger.debug("New Project inserted");
							} else {
								// Aggiorno lo stesso
								logger.debug("Project not modified");
								ProjectSgrCmDAO.updateDmalmProject(project);
								logger.debug("Project updated");
							}
						}

					}
				}
			}

			// verifica delle UO per i Project non scaricati in History
			List<Tuple> listaProgettiNonMovimentati = ProjectSgrCmDAO
					.getAllProjectNotInHistory(dataEsecuzione);

			logger.debug("listaProgettiNonMovimentati.size: "
					+ listaProgettiNonMovimentati.size());

			// lista delle eccezioni Project/Unita organizzativa
			List<DmalmProjectUnitaOrganizzativaEccezioni> eccezioniProjectUO = ProjectUnitaOrganizzativaEccezioniDAO
					.getAllProjectUOException();

			Integer strutturaOrgFk02;
			Integer unitaOrganizzativaFk;

			for (Tuple row : listaProgettiNonMovimentati) {
				if (row != null) {
					//Edma
					// FK Struttura Organizzativa
					String codiceAreaUOEdma = ProjectSgrCmDAO.gestioneCodiceAreaUO(
							eccezioniProjectUO, row.get(proj.idProject),
							row.get(proj.idRepository),
							row.get(proj.nomeCompletoProject),
							row.get(proj.cTemplate),
							row.get(proj.fkProjectgroup), dataEsecuzione, false);

					if (codiceAreaUOEdma.equals(DmAlmConstants.NON_PRESENTE)) {
						strutturaOrgFk02 = 0;
					} else {
						// UO Edma
						strutturaOrgFk02 = StrutturaOrganizzativaEdmaLispaDAO
								.getIdStrutturaOrganizzativaByCodiceUpdate(
										codiceAreaUOEdma, dataEsecuzione);
					}

					//Elettra
					// FK Unità Organizzativa
					String codiceAreaUOElettra = ProjectSgrCmDAO.gestioneCodiceAreaUO(
							eccezioniProjectUO, row.get(proj.idProject),
							row.get(proj.idRepository),
							row.get(proj.nomeCompletoProject),
							row.get(proj.cTemplate),
							row.get(proj.fkProjectgroup), dataEsecuzione, true);

					if (codiceAreaUOElettra.equals(DmAlmConstants.NON_PRESENTE)) {
						unitaOrganizzativaFk = 0;
					} else {
						unitaOrganizzativaFk = ElettraUnitaOrganizzativeDAO
								.getUnitaOrganizzativaByCodiceArea(
										codiceAreaUOElettra, dataEsecuzione);
					}
					
					if (BeanUtils.areDifferent(
							row.get(proj.dmalmStrutturaOrgFk02),
							strutturaOrgFk02)
							|| BeanUtils.areDifferent(
									row.get(proj.dmalmUnitaOrganizzativaFk),
									unitaOrganizzativaFk)) {

						righeModificate++;

						DmalmProject bean = new DmalmProject();

						bean.setIdProject(row.get(proj.idProject));
						bean.setIdRepository(row.get(proj.idRepository));
						bean.setcTemplate(row.get(proj.cTemplate));
						bean.setDmalmAreaTematicaFk01(row
								.get(proj.dmalmAreaTematicaFk01));
						bean.setDmalmStrutturaOrgFk02(strutturaOrgFk02);
						bean.setDmalmUnitaOrganizzativaFk(unitaOrganizzativaFk);
						bean.setDmalmUnitaOrganizzativaFlatFk(row.get(proj.dmalmUnitaOrganizzativaFlatFk));
						bean.setFlAttivo(row.get(proj.flAttivo));
						bean.setPathProject(row.get(proj.pathProject));
						bean.setDtInizioValidita(dataEsecuzione);
						bean.setcCreated(dataEsecuzione);
						bean.setServiceManagers(row.get(proj.serviceManagers));
						bean.setcTrackerprefix(row.get(proj.cTrackerprefix));
						bean.setcIsLocal(row.get(proj.cIsLocal));
						bean.setcPk(row.get(proj.cPk));
						bean.setFkUriLead(row.get(proj.fkUriLead));
						bean.setcDeleted(row.get(proj.cDeleted));
						bean.setcFinish(row.get(proj.cFinish));
						bean.setcUri(row.get(proj.cUri));
						bean.setcStart(row.get(proj.cStart));
						bean.setFkUriProjectgroup(row
								.get(proj.fkUriProjectgroup));
						bean.setcActive(row.get(proj.cActive));
						bean.setFkProjectgroup(row.get(proj.fkProjectgroup));
						bean.setFkLead(row.get(proj.fkLead));
						bean.setcLockworkrecordsdate(row
								.get(proj.cLockworkrecordsdate));
						bean.setcRev(row.get(proj.cRev));
						bean.setcDescription(row.get(proj.cDescription));
						bean.setSiglaProject(row.get(proj.siglaProject));
						bean.setNomeCompletoProject(row
								.get(proj.nomeCompletoProject));
						bean.setDtCaricamento(dataEsecuzione);

						// STORICIZZO
						// aggiorno la data di fine validita sul record
						// corrente
						ProjectSgrCmDAO.updateDataFineValidita(dataEsecuzione,
								bean);

						// inserisco un nuovo record
						ProjectSgrCmDAO.insertProjectUpdate(dataEsecuzione,
								bean, false);
					}
				}
			}
			
			//DMALM-191 associazione project Unità Organizzativa Flat
			//ricarica il valore della Fk ad ogni esecuzione
			try {
				QueryManager qm = QueryManager.getInstance();

				logger.info("INIZIO Update Project UnitaOrganizzativaFlatFk");
				
				qm.executeMultipleStatementsFromFile(
						DmAlmConstants.M_UPDATE_PROJECT_UOFLATFK,
						DmAlmConstants.M_SEPARATOR);
				
				logger.info("FINE Update Project UnitaOrganizzativaFlatFk");
			} catch (Exception e) {
				//non viene emesso un errore bloccante in quanto la Fk è recuperabile dopo l'esecuzione
				logger.error(e.getMessage(), e);
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(project_tmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(project_tmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.updateETLTargetInfo(dataEsecuzione, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()),
						DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
						righeNuove, righeModificate);

			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);

			}
		}
	}
}