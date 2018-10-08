package lispa.schedulers.facade.cleaning;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import lispa.schedulers.bean.staging.sgr.ProjectCName;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.sgr.sire.current.SireCurrentProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.queryimplementation.staging.oreste.QStgFunzionalita;
import lispa.schedulers.queryimplementation.staging.oreste.QStgModuli;
import lispa.schedulers.queryimplementation.staging.oreste.QStgProdottiArchitetture;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryProject;
import lispa.schedulers.utils.FormatCheckerUtils;
import lispa.schedulers.utils.LogUtils;
import lispa.schedulers.utils.SGRProjectUtils;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Template_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckSGRSIREProjectFacade implements Runnable {

	private Logger logger;
	private Timestamp dataEsecuzione;
	private boolean isAlive = true;
	private static QSireHistoryProject stgProjects = QSireHistoryProject.sireHistoryProject;

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public CheckSGRSIREProjectFacade(Logger logger, Timestamp dataEsecuzione) {
		this.logger = logger;
		this.dataEsecuzione = dataEsecuzione;
	}

	@Override
	public void run() {

		execute(logger, dataEsecuzione);

		setAlive(false);
	}

	public static void execute(Logger logger, Timestamp dataEsecuzione) {

		int erroriNonBloccanti = 0;

		try {
			erroriNonBloccanti = checkSIRECNameIsNull(logger, dataEsecuzione);

			erroriNonBloccanti += checkSIRECodiciIdentificativiObject(logger,
					dataEsecuzione);

			erroriNonBloccanti += FormatCheckerUtils
					.checkSIREDescrizioneFormat(logger, dataEsecuzione);

			erroriNonBloccanti += checkSIREProjectsNameFormat(logger,
					dataEsecuzione);

			EsitiCaricamentoDAO.insert(dataEsecuzione,
					DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
					DmAlmConstants.CARICAMENTO_IN_ATTESA, null, null, 0, 0,
					erroriNonBloccanti, 0);

		} catch (DAOException e) {
			logger.error(e.getMessage(), e);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}

	protected static int checkSIRECNameIsNull(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		List<Tuple> cname = SireCurrentProjectDAO.getC_NameNull(dataEsecuzione);

		int righeErrate = 0;
		for (Tuple row : cname) {

			righeErrate++;
			ErroriCaricamentoDAO.insert(
					DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT,
					DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
					LogUtils.projectSGRtoString(row),
					DmAlmConstants.SGR_PROJECT_CNAME_NULL,
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE, dataEsecuzione);
		}

		return righeErrate;
	}

	/**
	 * @author schiarav This function checks whether Projects' C_NAME Field
	 *         matches with specified formatting rules Starting from Template
	 *         different Regex Rules are applied in order to check project name
	 *         format validity
	 */
	protected static int checkSIREProjectsNameFormat(Logger logger,
			Timestamp dataEsecuzione) {
		int errori_formattazione = 0;
		String c_location;
		String[] split;
		String[] c_locationToArray;
		String target;
		String path_marker = ".polarion";
		int target_index = 0;
		boolean is_match_false;
		for (Template_Type template : Template_Type.values()) {
			if(template!=null){
				switch (template) {
				case SVILUPPO:
					try {
						List<Tuple> projects = SireHistoryProjectDAO
								.getProjectbyTemplate(template);
						List<String> c_names = SireHistoryProjectDAO
								.getProjectsC_Name(projects);
						for (String c_name : c_names) {
							if (c_name != null) {
								is_match_false = !StringUtils.matchRegex(
										c_name.trim(),
										DmalmRegex.REGEXPATTERNSVILUPPO);
								if (is_match_false) {
									ErroriCaricamentoDAO
											.insert(DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT,
													DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
													c_name + " {" + template + "}",
													DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
													DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
													dataEsecuzione);
									errori_formattazione++;
								}
							}
						}
					} catch (Exception e) {
						logger.debug(e.toString());
					}
	
					break;
				case DEMAND:
					try {
	
						List<Tuple> projects = SireHistoryProjectDAO
								.getProjectbyTemplate(template);
						for (Tuple progetto : projects) {
							String c_name = progetto.get(stgProjects.cName);
							if (c_name != null) {
								split = StringUtils.splitByRegex(c_name.trim(), "\\.");
								String uo = split[0];
								is_match_false = !StringUtils.matchRegex(uo.trim(),
										DmalmRegex.REGEXPATTERNDEMAND);
								if (split.length == 1 || is_match_false) {
									errori_formattazione++;
									/*
									 * se non matcha il regex specifico aumento il
									 * numero di errori e trovo un' ipotetica UO
									 * parsando il path in C_Location l'informazione
									 * viene salvata in Record_errore
									 */
									c_location = progetto.get(stgProjects.cLocation);
									c_locationToArray = StringUtils.splitByRegex(
											c_location, "\\/");
									target_index = StringUtils.getCloserIndex(
											c_locationToArray, path_marker,
											DmalmRegex.REGEXPATTERNDEMAND);
									target = c_locationToArray[target_index];
									if (StringUtils.matchRegex(
											StringUtils.splitByRegex(target, "\\.")[0],
											DmalmRegex.REGEXPATTERNDEMAND))
										target = StringUtils
												.splitByRegex(target, "\\.")[0];
									ErroriCaricamentoDAO
											.insert(DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT,
													DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
													c_name
															+ " {"
															+ template
															+ "}  Unità organizzativa : LI"
															+ target,
													DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
													DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
													dataEsecuzione);
								}
							}
						}
					} catch (Exception e) {
						logger.debug(e.toString());
	
					}
					break;
				case IT:
					break;
				case SERDEP:
					try {
						List<Tuple> projects = SireHistoryProjectDAO
								.getProjectbyTemplate(template);
						for (Tuple progetto : projects) {
							String c_tracker = progetto
									.get(stgProjects.cTrackerprefix);
							is_match_false = !StringUtils
									.matchRegex(c_tracker.trim(),
											DmalmRegex.REGEXPATTERNSERDEP);
							if (is_match_false) {
								/*
								 * se non matcha il regex specifico aumento il
								 * numero di errori e trovo un' ipotetica UO
								 * parsando il path l'informazione viene salvata in
								 * Record_errore
								 */
								errori_formattazione++;
								c_location = progetto.get(stgProjects.cLocation);
								c_locationToArray = StringUtils.splitByRegex(
										c_location, "\\/");
								target_index = StringUtils.getCloserIndex(
										c_locationToArray, path_marker,
										DmalmRegex.REGEXPATTERNSERDEP);
								target = c_locationToArray[target_index];
								if (StringUtils.findRegex(
										StringUtils.splitByRegex(target, "\\.")[0],
										DmalmRegex.REGEXPATTERNSERDEP)) {
									target = StringUtils
											.splitByRegex(target, "\\.")[0];
								}
								ErroriCaricamentoDAO
										.insert(DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT,
												DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
												c_tracker
														+ " {"
														+ template
														+ "}  Unità organizzativa : LI"
														+ target,
												DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
												DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
												dataEsecuzione);
	
							}
						}
					} catch (Exception e) {
						logger.debug(e.toString());
	
					}
					break;
				case ASSISTENZA:
					try {
						List<Tuple> projects = SireHistoryProjectDAO
								.getProjectbyTemplate(template);
	
						for (Tuple progetto : projects) {
							String c_name = progetto.get(stgProjects.cName);
							is_match_false = !StringUtils.matchRegex(c_name.trim(),
									DmalmRegex.REGEXPATTERNASSISTENZA);
							if (is_match_false) {
								errori_formattazione++;
								ErroriCaricamentoDAO
										.insert(DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT,
												DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
												c_name + " {" + template + "}",
												DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
												DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
												dataEsecuzione);
							}
	
						}
					} catch (Exception e) {
						logger.debug(e.toString());
	
					}
					break;
				default:
					break;
				}
			}

		}
		return errori_formattazione;
	}

	// Check {C_NAME}
	protected static void checkSIRECodiciIdentificativiFormat(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		ProjectCName cname = null;
		Vector<String> identificativi = new Vector<String>();

		List<Tuple> projectname = SireCurrentProjectDAO
				.getAllProjectC_Name(dataEsecuzione);

		for (Tuple row : projectname) {
			cname = new ProjectCName();

			cname.setcName(row.get(0, String.class));
			cname.setPrimaGraffaIndex(Integer.parseInt(row.get(1, String.class)));
			cname.setSecondaGraffaIndex(Integer.parseInt(row.get(2,
					String.class)));
			cname.setIdentificativo(row.get(3, String.class));

			if (cname.getPrimaGraffaIndex() == 0
					&& cname.getSecondaGraffaIndex() > 0) {
				// non esiste parentesi graffa aperta ma esiste lachiusa

				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT,
						DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
						LogUtils.projectSGRtoString(row),
						DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else if (cname.getPrimaGraffaIndex() > 0
					&& cname.getSecondaGraffaIndex() == 0) {
				// esiste parentesi graffa aperta ma non esiste la chiusa

				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT,
						DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
						LogUtils.projectSGRtoString(row),
						DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else if (cname.getPrimaGraffaIndex() == 0
					&& cname.getSecondaGraffaIndex() == 0) {
				// non esiste parentesi graffa aperta e non esiste la parentesi
				// graffa chiusa

				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT,
						DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
						LogUtils.projectSGRtoString(row),
						DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else if (cname.getPrimaGraffaIndex() > cname
					.getSecondaGraffaIndex()) {
				// parentesi aperta segue parentesi chiusa, esempio
				// "SW-oggetto}{-descrizione"

				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT,
						DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
						LogUtils.projectSGRtoString(row),
						DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else {
				if (cname.getIdentificativo() != null
						&& !cname.getIdentificativo().equals("")) {
					// controllo se C_NAME è DUPLICATO
					if (identificativi.contains(cname.getIdentificativo())) {
						ErroriCaricamentoDAO.insert(
								DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT,
								DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
								LogUtils.projectSGRtoString(row),
								DmAlmConstants.SGR_PROJECT_CNAME_DUPLICATO,
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					} else {
						// lo aggiungo nel VETTORE
						identificativi.add(cname.getIdentificativo());
					}
				}
			}
		}
	}

	protected static int checkSIRECodiciIdentificativiObject(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		ProjectCName cname = null;
		QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;
		QStgModuli qstgModuli = QStgModuli.stgModuli;
		QStgFunzionalita qstgFunzionalita = QStgFunzionalita.stgFunzionalita;

		List<ProjectCName> projectObjects = new ArrayList<ProjectCName>();
		List<Tuple> projectname = new ArrayList<Tuple>();

		int righeErrate = 0;

		projectname = SireCurrentProjectDAO.getAllProjectC_Name(dataEsecuzione);

		for (Tuple row : projectname) {
			cname = new ProjectCName();

			cname.setcName(row.get(0, String.class));
			cname.setPrimaGraffaIndex(Integer.parseInt(row.get(1, String.class)));
			cname.setSecondaGraffaIndex(Integer.parseInt(row.get(2,
					String.class)));
			cname.setIdentificativo(row.get(3, String.class));

			// controllo se C_NAME è DUPLICATO
			if (cname.getIdentificativo() != null
					&& !cname.getIdentificativo().equals("")) {

				projectObjects = SGRProjectUtils.identifyCNameObjects(cname
						.getIdentificativo());

				for (ProjectCName el : projectObjects) {

					// se esiste solo il prodotto
					if (el.getProdotto() != null && el.getModulo() == null
							&& el.getFunzionalita() == null) {
						// Controllo il prodotto in Oreste

						righeErrate += SGRProjectUtils.checkProdottoInOreste(
								DmAlmConstants.REPOSITORY_SIRE, el,
								qstgProdottiArchitetture, row,
								cname.getIdentificativo(), logger,
								dataEsecuzione);

					}

					if (el.getProdotto() != null && el.getModulo() != null
							&& el.getFunzionalita() == null) {
						// Controllo il prodotto in Oreste

						righeErrate += SGRProjectUtils.checkProdottoInOreste(
								DmAlmConstants.REPOSITORY_SIRE, el,
								qstgProdottiArchitetture, row,
								cname.getIdentificativo(), logger,
								dataEsecuzione);

						righeErrate += SGRProjectUtils.checkModuloInOreste(
								DmAlmConstants.REPOSITORY_SIRE, el, qstgModuli,
								row, cname.getIdentificativo(), logger,
								dataEsecuzione);

					}

					if (el.getProdotto() != null && el.getModulo() != null
							&& el.getFunzionalita() != null) {
						// Controllo il prodotto in Oreste

						righeErrate += SGRProjectUtils.checkProdottoInOreste(
								DmAlmConstants.REPOSITORY_SIRE, el,
								qstgProdottiArchitetture, row,
								cname.getIdentificativo(), logger,
								dataEsecuzione);

						righeErrate += SGRProjectUtils.checkModuloInOreste(
								DmAlmConstants.REPOSITORY_SIRE, el, qstgModuli,
								row, cname.getIdentificativo(), logger,
								dataEsecuzione);

						righeErrate += SGRProjectUtils
								.checkFunzionalitaInOreste(
										DmAlmConstants.REPOSITORY_SIRE, el,
										qstgFunzionalita, row,
										cname.getIdentificativo(), logger,
										dataEsecuzione);

					}
				}
			}
		}

		return righeErrate;
	}

}