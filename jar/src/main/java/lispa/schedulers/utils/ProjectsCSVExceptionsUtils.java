package lispa.schedulers.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.SQLQuery;

import lispa.schedulers.bean.utils.ProjectsCSVExceptionsBean;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeDAO;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.CsvReader;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProject;

public class ProjectsCSVExceptionsUtils {
	
	private static Logger logger = Logger.getLogger(ProjectsCSVExceptionsUtils.class);
	private static OracleTemplates dialect = new OracleTemplates();
	private static QDmalmProject proj = QDmalmProject.dmalmProject;
	private static ConnectionManager cm;
	private static Connection connection;
	
	private static final String REPOSITORY = "REPOSITORY";
	private static final String ID_PROJECT = "ID_PROJECT";
	private static final String NOME_COMPLETO_PROJECT = "NOME_COMPLETO_PROJECT";
	private static final String TIPO_TEMPLATE = "TIPO_TEMPLATE";
	private static final String DATA_UPDATE_PROGRAMMATO = "DATA_UPDATE_PROGRAMMATO";
	private static final String cd_uo_diriferimento_project = "cd_uo_diriferimento_project";
	private static final String ID_REFERENTE_LISPA_PROJECT = "ID_REFERENTE_LISPA_PROJECT";
	private static final String NOME_COGNOME_REFERENTE_LISPA_PROJECT = "nome_cognome_referente_lispa_project";
	private static final String SISS = "SISS";
	private static final String SIRE = "SIRE";
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String[] ALLOWED_TEMPLATE = new String[] {"SVILUPPO", "IT", "DEMAND", "DEMAND2016", "ASSISTENZA", "SERDEP"};
	private static final String NAME_SURNAME_SPLITTER = "\\s+";
	
	public static HashMap<String,ProjectsCSVExceptionsBean> projectsExceptions = null;

	// Return a map with 
	// Key: idProject,idRepository
	// Value: Bean
	public static void importCsv(Timestamp dataEsecuzione) throws Exception {
		DmAlmConfigReader cfgReader = DmAlmConfigReader.getInstance();
		String sourceCSVFileName = DmAlmConstants.ELETTRA_PROJECT_LOOKUP_CSV;
		Path csvFilePath = Paths.get(cfgReader.getProperty(DmAlmConfigReaderProperties.DMALM_SFERA_PATH), sourceCSVFileName);
		File csvFile = csvFilePath.toFile();
		CsvReader csvReader = null;
		projectsExceptions = new HashMap<String,ProjectsCSVExceptionsBean>();

		if(csvFile.exists()){
			logger.info("START ProjectsCSVExceptionsUtils.importCsv()");
			csvReader = new CsvReader();
			csvReader.ReadFile(csvFile);
			
			boolean isRowValid = true;
			
			for(int i = 0; i < csvReader.getRowCount(); i++) {
				logger.info("Reading row "+i+1);
				String csvRepository = csvReader.getCell(i,  REPOSITORY);
				String csvIdProject = csvReader.getCell(i,  ID_PROJECT);
				String csvNomeCompletoProject = csvReader.getCell(i, NOME_COMPLETO_PROJECT);
				String csvTipoTemplate = csvReader.getCell(i, TIPO_TEMPLATE);
				String csvDataUpdateProgrammato = csvReader.getCell(i, DATA_UPDATE_PROGRAMMATO);
				String csvCdUoDiriferimentoProject = csvReader.getCell(i, cd_uo_diriferimento_project);
				String csvIdReferenteLispaProject = csvReader.getCell(i, ID_REFERENTE_LISPA_PROJECT);
				String csvNomeCogLispaProject = csvReader.getCell(i, NOME_COGNOME_REFERENTE_LISPA_PROJECT);
				logger.info("Project ID: "+csvIdProject);
				
				// csvDataUpdateProgrammato 
				// if csvDataUpdateProgrammato is not the current date, continue
				boolean isDateValid = true;
				
				SimpleDateFormat sdfrmt = new SimpleDateFormat(DATE_FORMAT);
		        sdfrmt.setLenient(false);
		        Date parsedDate = new Date();
		        /* Parse the string into date form */
		        try {
		        	parsedDate = sdfrmt.parse(csvDataUpdateProgrammato); 
		        } catch (Exception e) {
		            isDateValid = false;
		            continue;
		        }
		        Calendar csvDateCalendar = Calendar.getInstance();
		        csvDateCalendar.setTimeInMillis(parsedDate.getTime());
		        csvDateCalendar.clear(Calendar.HOUR);
		        csvDateCalendar.clear(Calendar.HOUR_OF_DAY);
		        csvDateCalendar.clear(Calendar.MINUTE);
		        csvDateCalendar.clear(Calendar.SECOND);
				
				Calendar dataEsecuzioneCalendar = Calendar.getInstance();
				dataEsecuzioneCalendar.setTimeInMillis(dataEsecuzione.getTime());
				dataEsecuzioneCalendar.clear(Calendar.HOUR);
				dataEsecuzioneCalendar.clear(Calendar.HOUR_OF_DAY);
				dataEsecuzioneCalendar.clear(Calendar.MINUTE);
				dataEsecuzioneCalendar.clear(Calendar.SECOND);

		        if(!isDateValid || 
		        		dataEsecuzioneCalendar.get(Calendar.YEAR)!=csvDateCalendar.get(Calendar.YEAR) || 
		        		dataEsecuzioneCalendar.get(Calendar.MONTH)!=csvDateCalendar.get(Calendar.MONTH) ||
		        		dataEsecuzioneCalendar.get(Calendar.DAY_OF_MONTH)!=csvDateCalendar.get(Calendar.DAY_OF_MONTH)) {
		        	logger.info("La data programmata non coincide con la data di esecuzione");
					logger.info("Data del CSV: "+csvDateCalendar.toString());
					logger.info("Data esecuzione: "+dataEsecuzioneCalendar.toString());
					continue;
		        }
		        
				List<Tuple> projects = new ArrayList<Tuple>();
				
				try {
					cm = ConnectionManager.getInstance();
					connection = cm.getConnectionOracle();
					SQLQuery query = new SQLQuery(connection, dialect);

					projects = query
							.from(proj)
							.where(proj.idProject.equalsIgnoreCase(csvIdProject))
							.where(proj.idRepository.equalsIgnoreCase(csvRepository))
							.where(proj.annullato.isNull())
							.list(proj.all());
					
				} catch (Exception e) {
					ErrorManager.getInstance().exceptionOccurred(true, e);
				} finally {
					if (cm != null)
						cm.closeConnection(connection);
				}
				
				//repository
				if(!csvRepository.equals(SIRE) && !csvRepository.equals(SISS)) {
					ErroriCaricamentoDAO.insert(sourceCSVFileName,
							sourceCSVFileName,
							REPOSITORY + ": " + csvRepository,
							REPOSITORY + " " + csvRepository + " is not valid.",
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
		        	logger.error("Il campo "+REPOSITORY+" non contiene SISS o SIRE");
					isRowValid = false;
					continue;
				}
				
				//idProject		
				if(projects.size() == 0){
					ErroriCaricamentoDAO.insert(sourceCSVFileName,
							sourceCSVFileName,
							ID_PROJECT + ": " + csvIdProject,
							ID_PROJECT + " " + csvIdProject+ " is not valid.",
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
		        	logger.error("Non è presente nessun progetto "+csvIdProject+" in "+csvRepository+" con ANNULLATO = NULL");
					isRowValid = false;
					continue;
				}
				
				//csvNomeCompletoProject
				boolean existsProject = false;
				for(Tuple t: projects){
					if(t.get(proj.nomeCompletoProject).equals(csvNomeCompletoProject)){
						existsProject = true;
						break;
					}
				}

				if(!existsProject) {
					ErroriCaricamentoDAO.insert(sourceCSVFileName,
							sourceCSVFileName,
							NOME_COMPLETO_PROJECT + ": " + csvNomeCompletoProject,
							NOME_COMPLETO_PROJECT + " " + csvNomeCompletoProject+ " is not valid.",
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
		        	logger.error("Non è presente nessun progetto di nome "+csvNomeCompletoProject+" in "+csvRepository+" con ANNULLATO = NULL");
					isRowValid = false;
					continue;
				}
				
				//csvTipoTemplate
				String[] tipoTemplates = ALLOWED_TEMPLATE;
				if(Arrays.asList(tipoTemplates).indexOf(csvTipoTemplate) < 0) {
					ErroriCaricamentoDAO.insert(sourceCSVFileName,
							sourceCSVFileName,
							TIPO_TEMPLATE + ": " + csvTipoTemplate,
							TIPO_TEMPLATE + " " + csvTipoTemplate+ " is not valid.",
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
					logger.error("Il template '"+csvTipoTemplate+"' non è valido");
					isRowValid = false;
					continue;
				}

		        //csvCdUoDiriferimentoProject
		        Integer unitaOrgPk = ElettraUnitaOrganizzativeDAO.findByCdArea(dataEsecuzione, csvCdUoDiriferimentoProject);
		        if(unitaOrgPk == null) {
					ErroriCaricamentoDAO.insert(sourceCSVFileName,
							sourceCSVFileName,
							cd_uo_diriferimento_project + ": " + csvCdUoDiriferimentoProject,
							cd_uo_diriferimento_project + " " + csvCdUoDiriferimentoProject+ " is not valid.",
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
		        	logger.error("Non è presente nessuna area organizzativa in DMALM_EL_UNITA_ORGANIZZATIVE con il campo CD_AREA = "+csvCdUoDiriferimentoProject);

					isRowValid = false;
					continue;
		        }
		        
		        //csvIdReferenteLispaProject
		        Integer iCsvIdRef;
		        try {
		        	iCsvIdRef = Integer.parseInt(csvIdReferenteLispaProject);
		        } catch(NumberFormatException exc) {
		        	iCsvIdRef = null;
		        }
		        
		        if(iCsvIdRef == null || !ElettraPersonaleDAO.existsWithAnnullatoNull(iCsvIdRef)) {
		        	ErroriCaricamentoDAO.insert(sourceCSVFileName,
							sourceCSVFileName,
							ID_REFERENTE_LISPA_PROJECT +": " + csvIdReferenteLispaProject,
							ID_REFERENTE_LISPA_PROJECT + " " + csvIdReferenteLispaProject+ " is not valid.",
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				    logger.error("Non è presente nessuna area organizzativa in DMALM_EL_PERSONALE con il campo DMALM_PERSONALE_PK = "+iCsvIdRef);
					isRowValid = false;
					continue;
		        }
		        
		        //csvNomeCogLispaProject
		        if(csvNomeCogLispaProject != null && csvNomeCogLispaProject.trim().length() > 0) {
		        	String[] split = csvNomeCogLispaProject.split(NAME_SURNAME_SPLITTER);
		        	String name = split.length > 0 ? split[0] : "";
		        	String sname = split.length > 1 ? split[1] : "";
		        	
		        	Tuple per = ElettraPersonaleDAO.findByName(StringUtils.getMaskedValue(name), StringUtils.getMaskedValue(sname));
		        	
		        	if(per == null) {
		        		ErroriCaricamentoDAO.insert(sourceCSVFileName,
								sourceCSVFileName,
								NOME_COGNOME_REFERENTE_LISPA_PROJECT + ": " + csvNomeCogLispaProject,
									NOME_COGNOME_REFERENTE_LISPA_PROJECT + " " + csvNomeCogLispaProject+ " is not valid.",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
		        		logger.error("Non è presente nessun record in DMALM_EL_PERSONALE con NOME = "+name+" e COGNOME = "+sname);
						isRowValid = false;
						continue;
		        	}
		        }
		        
		        if(isRowValid){
		        	ProjectsCSVExceptionsBean bean = new ProjectsCSVExceptionsBean();
		        	
		        	bean.setCsvRepository(csvRepository);
		        	bean.setCsvIdProject(csvIdProject);
		        	bean.setCsvNomeCompletoProject(csvNomeCompletoProject);
		        	bean.setCsvTipoTemplate(csvTipoTemplate);
		        	bean.setCsvDataUpdateProgrammato(csvDataUpdateProgrammato);
		        	bean.setCsvCdUoDiriferimentoProject(unitaOrgPk);
		        	bean.setCsvIdReferenteLispaProject(csvIdReferenteLispaProject);
		        	bean.setCsvNomeCogLispaProject(csvNomeCogLispaProject);
		        	
		        	projectsExceptions.put(csvIdProject+","+csvRepository, bean);
		        }
			}
		        
		} else {
			projectsExceptions = null;
		}
		
		logger.info("STOP ProjectsCSVExceptionsUtils.importCsv()");
	}

	public static boolean isAnException(String idProject, String idRepository) {
		String projectKey = idProject+","+idRepository;
		if(projectsExceptions.keySet().contains(projectKey))
			return true;
		else
			return false;
	}

}
