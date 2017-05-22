package lispa.schedulers.facade.target;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.DmalmProjectUnitaOrganizzativaEccezioni;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.dao.target.ProjectUnitaOrganizzativaEccezioniDAO;
import lispa.schedulers.dao.target.StrutturaOrganizzativaEdmaLispaDAO;
import lispa.schedulers.dao.target.UnitaOrganizzativeDAO;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.CsvReader;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
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
			

			DmAlmConfigReader cfgReader = DmAlmConfigReader.getInstance();
			String csvFileName = cfgReader.getProperty(DmAlmConfigReaderProperties.ELETTRA_PROJECT_LOOKUP_CSV);
			File csvFile = new File(csvFileName);
			CsvReader csvReader = null;
			if(csvFile.exists())
			{
				csvReader = new CsvReader();
				csvReader.ReadFile(csvFileName);
				
				for(int i = 0; i < csvReader.getRowCount(); i++)
				{
					String csvRepository = csvReader.getCell(i,  "REPOSITORY");
					String csvIdProject = csvReader.getCell(i,  "ID_PROJECT");
					String csvNomeCompletoProject = csvReader.getCell(i, "NOME_COMPLETO_PROJECT");
					String csvTipoTemplate = csvReader.getCell(i, "TIPO_TEMPLATE");
					String csvDataUpdateProgrammato = csvReader.getCell(i, "DATA_UPDATE_PROGRAMMATO");
					String csvCdUoDiriferimentoProject = csvReader.getCell(i, "cd_uo_diriferimento_project");
					String csvIdReferenteLispaProject = csvReader.getCell(i, "ID_REFERENTE_LISPA_PROJECT");
					String csvNomeCogLispaProject = csvReader.getCell(i, "NOME_COGNOMEREFERENTE_LISPA_PROJECT");
					
					//repository
					boolean isValid = true;
					if(csvRepository != "SIRE" && csvRepository != "SISS")
					{
						ErroriCaricamentoDAO.insert("DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"REPOSITORY: " + csvRepository,
								"Repository " + csvRepository + " is not valid.",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
						isValid = false;
					}
					
					//idProject
					DmalmProject pr = null;
					boolean existsProject = false;
					for(DmalmProject project : staging_projects)
					{
						if(project.getAnnullato() == null && project.getIdProject().equals(csvIdProject) && project.getIdRepository().equals(csvRepository))
						{
							pr = project;
							existsProject = true;
							break;
						}
					
					}
					if(!existsProject)
					{
						isValid = false;
					
						ErroriCaricamentoDAO.insert("DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"ID_PROJECT: " + csvIdProject,
								"Project " + csvIdProject+ " is not valid.",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}
					
					//csvNomeCompletoProject
					existsProject = false;
					for(DmalmProject project : staging_projects)
					{
						if(project.getNomeCompletoProject().equals(csvNomeCompletoProject) && project.getIdRepository().equals(csvRepository) && project.getIdProject().equals(csvIdProject))
						{
							existsProject = true;
							break;
						}
					
					}
					if(!existsProject)
					{
						isValid = false;
					
						ErroriCaricamentoDAO.insert("DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"NOME_COMPLETO_PROJECT: " + csvNomeCompletoProject,
								"NOME_COMPLETO_PROJECT " + csvNomeCompletoProject+ " is not valid.",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}
					
					//csvTipoTemplate
					String[] tipoTemplates = new String[] {"SVILUPPO", "IT", "DEMAND", "DEMAND2016", "ASSISTENZA", "SERDEP"};
					if(Arrays.asList(tipoTemplates).indexOf(csvTipoTemplate) < 0)
					{
						isValid = false;
						
						ErroriCaricamentoDAO.insert("DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"TIPO_TEMPLATE: " + csvTipoTemplate,
								"TIPO_TEMPLATE " + csvTipoTemplate+ " is not valid.",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}
					
					
					//csvDataUpdateProgrammato
					boolean isDateValid = true;
					
					SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
			        sdfrmt.setLenient(false);
			        /* Create Date object */
			        Date javaDate = null;
			        /* parse the string into date form */
			        try
			        {
			            javaDate = sdfrmt.parse(csvDataUpdateProgrammato); 
			        }
			        /* Date format is invalid */
			        catch (Exception e)
			        {
			            isDateValid = false;
			        }
			        
			        if(!isDateValid)
			        {
			        	isValid = false;
			        	
						ErroriCaricamentoDAO.insert("DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"DATA_UPDATE_PROGRAMMATO: " + csvDataUpdateProgrammato,
								"DATA_UPDATE_PROGRAMMATO " + csvDataUpdateProgrammato+ " is not valid.",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			        }
			        else
			        {
			        	if(pr != null)	//TODO: move to the end and check isValid ?
			        	{
				        	if(dataEsecuzione.equals(javaDate))
				        	{
				        		//ProjectSgrCmDAO.updateDataFineValidita(dataEsecuzione, pr);
				        		//ProjectSgrCmDAO.insertProjectUpdate()
				        	}
			        	}
			        }
			        
			        //csvCdUoDiriferimentoProject
			        Integer unitaOrgPk = UnitaOrganizzativeDAO.FindByCdArea(dataEsecuzione, csvCdUoDiriferimentoProject);
			        if(unitaOrgPk == null)
			        {
						ErroriCaricamentoDAO.insert("DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"cd_uo_diriferimento_project: " + csvCdUoDiriferimentoProject,
								"cd_uo_diriferimento_project " + csvCdUoDiriferimentoProject+ " is not valid.",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			        }
			        else
			        {
			        	pr.setDmalmUnitaOrganizzativaFk(unitaOrgPk);
			        }
			        
			        //csvIdReferenteLispaProject
			        Integer iCsvIdRef;
			        try
			        {
			        	iCsvIdRef = Integer.parseInt(csvIdReferenteLispaProject);
			        }
			        catch(NumberFormatException exc)
			        {
			        	iCsvIdRef = null;
			        }
			        
			        if(iCsvIdRef == null || !ElettraPersonaleDAO.ExistsWithAnnullatoNull(iCsvIdRef))
			        {
			        	ErroriCaricamentoDAO.insert("DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"DMALM_SOURCE_PROJECT_ECCEZIONI.csv",
								"ID_REFERENTE_LISPA_PROJECT: " + csvIdReferenteLispaProject,
								"ID_REFERENTE_LISPA_PROJECT " + csvIdReferenteLispaProject+ " is not valid.",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			        }
			        
			        //csvNomeCogLispaProject
			        
					
					//rest stuff
					if(!isValid)
					{
						csvReader.RemoveRow(i);
					}
				}
			}

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