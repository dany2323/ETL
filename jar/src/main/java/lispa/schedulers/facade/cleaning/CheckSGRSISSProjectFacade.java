package lispa.schedulers.facade.cleaning;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import lispa.schedulers.bean.staging.sgr.ProjectCName;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.queryimplementation.staging.oreste.QStgFunzionalita;
import lispa.schedulers.queryimplementation.staging.oreste.QStgModuli;
import lispa.schedulers.queryimplementation.staging.oreste.QStgProdottiArchitetture;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryProject;
import lispa.schedulers.utils.FormatCheckerUtils;
import lispa.schedulers.utils.LogUtils;
import lispa.schedulers.utils.SGRProjectUtils;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Template_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckSGRSISSProjectFacade implements Runnable {

	private Logger logger;
	private Timestamp dataEsecuzione;
	private boolean isAlive = true;
	private static QSissHistoryProject stgProjects = QSissHistoryProject.sissHistoryProject;

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public CheckSGRSISSProjectFacade(Logger logger, Timestamp dataEsecuzione) {
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
			erroriNonBloccanti = checkSISSCNameIsNull(logger, dataEsecuzione);

			erroriNonBloccanti += checkSISSCodiciIdentificativiObject(logger,
					dataEsecuzione);

			erroriNonBloccanti += FormatCheckerUtils
					.checkSISSDescrizioneFormat(logger, dataEsecuzione);

			erroriNonBloccanti += checkSISSProjectsNameFormat(logger,
					dataEsecuzione);

			EsitiCaricamentoDAO.updateCleaningInfo(dataEsecuzione,
					DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
					erroriNonBloccanti, 0);

		} catch (DAOException e) {
			logger.error(e.getMessage(), e);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

	}

	protected static int checkSISSCNameIsNull(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		List<Tuple> cname = SissCurrentProjectDAO.getC_NameNull(dataEsecuzione);

		int errore = 0;

		for (Tuple row : cname) {

			errore++;

			ErroriCaricamentoDAO.insert(
					DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT,
					DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
					LogUtils.projectSGRtoString(row),
					DmAlmConstants.SGR_PROJECT_CNAME_NULL,
					DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE, dataEsecuzione);
		}

		return errore;
	}

	protected static void checkSISSCodiciIdentificativiFormat(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		ProjectCName cname = null;
		Vector<String> identificativi = new Vector<String>();

		List<Tuple> projectname = SissCurrentProjectDAO
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
						DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT,
						DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
						LogUtils.projectSGRtoString(row),
						DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else if (cname.getPrimaGraffaIndex() > 0
					&& cname.getSecondaGraffaIndex() == 0) {
				// esiste parentesi graffa aperta ma non esiste la chiusa

				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT,
						DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
						LogUtils.projectSGRtoString(row),
						DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else if (cname.getPrimaGraffaIndex() == 0
					&& cname.getSecondaGraffaIndex() == 0) {
				// Errore: non esiste parentesi graffa aperta e non esiste la
				// parentesi graffa chiusa

				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT,
						DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
						LogUtils.projectSGRtoString(row),
						DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else if (cname.getPrimaGraffaIndex() > cname
					.getSecondaGraffaIndex()) {
				// Errore: parentesi aperta segue parentesi chiusa, esempio
				// "SW-oggetto}{-descrizione"

				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT,
						DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
						LogUtils.projectSGRtoString(row),
						DmAlmConstants.SGR_PROJECT_CNAME_MALFORMED,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else {
				// controllo se C_NAME è DUPLICATO
				if (cname.getIdentificativo() != null
						&& !cname.getIdentificativo().equals("")) {
					// Errore: se già presente nel Vettore
					if (identificativi.contains(cname.getIdentificativo())) {
						ErroriCaricamentoDAO.insert(
								DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT,
								DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
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

	/**
	 * @author schiarav This function checks whether Projects' C_NAME Field
	 *         matches with specified formatting rules Starting from Template
	 *         different Regex Rules are applied in order to check project name
	 *         format validity
	 */
	protected static int checkSISSProjectsNameFormat(Logger logger,
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
			switch (template) {
			case SVILUPPO:
				try {
					List<Tuple> projects = SissHistoryProjectDAO
							.getProjectbyTemplate(template);
					List<String> c_names = SissHistoryProjectDAO
							.getProjectsC_Name(projects);
					for (String c_name : c_names) {
						if (c_name != null) {
							is_match_false = !StringUtils.matchRegex(
									c_name.trim(),
									DmalmRegex.REGEXPATTERNSVILUPPO);
							if (is_match_false) {
								ErroriCaricamentoDAO
										.insert(DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT,
												DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
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

					List<Tuple> projects = SissHistoryProjectDAO
							.getProjectbyTemplate(template);
					for (Tuple progetto : projects) {
						String c_name = progetto.get(stgProjects.cName);
						if (c_name != null) {
							split = StringUtils.splitByRegex(c_name.trim(),
									"\\.");
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
								c_location = progetto
										.get(stgProjects.cLocation);
								c_locationToArray = StringUtils.splitByRegex(
										c_location, "\\/");
								target_index = StringUtils.getCloserIndex(
										c_locationToArray, path_marker,
										DmalmRegex.REGEXPATTERNDEMAND);
								target = c_locationToArray[target_index];
								if (StringUtils
										.matchRegex(StringUtils.splitByRegex(
												target, "\\.")[0],
												DmalmRegex.REGEXPATTERNDEMAND))
									target = StringUtils.splitByRegex(target,
											"\\.")[0];
								ErroriCaricamentoDAO
										.insert(DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT,
												DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
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
					List<Tuple> projects = SissHistoryProjectDAO
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
									.insert(DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT,
											DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
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
					List<Tuple> projects = SissHistoryProjectDAO
							.getProjectbyTemplate(template);

					for (Tuple progetto : projects) {
						String c_name = progetto.get(stgProjects.cName);
						is_match_false = !StringUtils.matchRegex(c_name.trim(),
								DmalmRegex.REGEXPATTERNASSISTENZA);
						if (is_match_false) {
							errori_formattazione++;
							ErroriCaricamentoDAO
									.insert(DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT,
											DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
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
		return errori_formattazione;
	}

	protected static int checkSISSCodiciIdentificativiObject(Logger logger,
			Timestamp dataEsecuzione) throws Exception {

		ProjectCName cname = null;
		QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;
		QStgModuli qstgModuli = QStgModuli.stgModuli;
		QStgFunzionalita qstgFunzionalita = QStgFunzionalita.stgFunzionalita;

		int errore = 0;

		List<Tuple> projectname = SissCurrentProjectDAO
				.getAllProjectC_Name(dataEsecuzione);

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

				List<ProjectCName> projectObjects = SGRProjectUtils
						.identifyCNameObjects(cname.getIdentificativo());

				for (ProjectCName el : projectObjects) {
					// se esiste solo il prodotto
					if (el.getProdotto() != null && el.getModulo() == null
							&& el.getFunzionalita() == null) {
						// Controllo il prodotto in Oreste
						errore += SGRProjectUtils.checkProdottoInOreste(
								DmAlmConstants.REPOSITORY_SISS, el,
								qstgProdottiArchitetture, row,
								cname.getIdentificativo(), logger,
								dataEsecuzione);
					}
					if (el.getProdotto() != null && el.getModulo() != null
							&& el.getFunzionalita() == null) {
						// Controllo il prodotto in Oreste
						errore += SGRProjectUtils.checkProdottoInOreste(
								DmAlmConstants.REPOSITORY_SISS, el,
								qstgProdottiArchitetture, row,
								cname.getIdentificativo(), logger,
								dataEsecuzione);

						// Controllo il modulo in Oreste, controllo che sia
						// figlio del prodotto
						errore += SGRProjectUtils.checkModuloInOreste(
								DmAlmConstants.REPOSITORY_SISS, el, qstgModuli,
								row, cname.getIdentificativo(), logger,
								dataEsecuzione);
					}
					if (el.getProdotto() != null && el.getModulo() != null
							&& el.getFunzionalita() != null) {
						// Controllo il prodotto in Oreste
						errore += SGRProjectUtils.checkProdottoInOreste(
								DmAlmConstants.REPOSITORY_SISS, el,
								qstgProdottiArchitetture, row,
								cname.getIdentificativo(), logger,
								dataEsecuzione);

						// Controllo il modulo in Oreste, controllo che sia
						// figlio del prodotto
						errore += SGRProjectUtils.checkModuloInOreste(
								DmAlmConstants.REPOSITORY_SISS, el, qstgModuli,
								row, cname.getIdentificativo(), logger,
								dataEsecuzione);

						// Controllo la funzionalita in Oreste, controllo che
						// sia figlia di prodotto e modulo
						errore += SGRProjectUtils.checkFunzionalitaInOreste(
								DmAlmConstants.REPOSITORY_SISS, el,
								qstgFunzionalita, row,
								cname.getIdentificativo(), logger,
								dataEsecuzione);
					}
				}
			}
		}

		return errore;
	}

}