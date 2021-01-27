package lispa.schedulers.facade.target;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.QDmalmEsitiCaricamenti;
import lispa.schedulers.queryimplementation.staging.QErroriCaricamentoDmAlm;
import lispa.schedulers.queryimplementation.target.QDmalmParamDeleteEsiti;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

/*
 * Questa classe permette di eseguire la cancellazione programmata
 * dalle tabelle DMALM_ERRORI_CARICAMENTO e DMALM_ESITI_CARICAMENTI
 * secondo i parametri specificati dalla tabella DMALM_PARAM_DELETE_ESITI
 * che, a sua volta, è valorizzata tramite un file .csv
 */

public class SvecchiamentoFacade {

	private static Logger logger = Logger.getLogger(SvecchiamentoFacade.class);

	public static void execute() {
		try {
			logger.info("START SvecchiamentoFacade.execute()");
			String[] nextLine;
			String[] rowToDB = null;
			List<String[]> s = new ArrayList<>();
			int rigaCsv = 1;
			String riempitivo = " ;";
			CSVReader reader = null;
			Timestamp dataCaricamento = DataEsecuzione.getInstance()
					.getDataEsecuzione();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmParamDeleteEsiti pde = QDmalmParamDeleteEsiti.dmalmParamDeleteEsiti;
			
			logger.info("Path " + DmAlmConstants.SFERA_CSV
					+ DmAlmConstants.SVECCHIAMENTO
					+ DmAlmConstants.MPS_CSV_EXTENSION);
			try {
				reader = new CSVReader(new InputStreamReader(
						new FileInputStream(DmAlmConstants.SFERA_CSV
								+ DmAlmConstants.SVECCHIAMENTO
								+ DmAlmConstants.MPS_CSV_EXTENSION),
						"ISO-8859-1"), '\t');
				
				nextLine = reader.readNext(); // riga intestazione da scartare

				while ((nextLine = reader.readNext()) != null) {
					s.add(nextLine);
				}
			} catch (Exception e) {
				logger.error("Errore gestito " + e + " Path o file csv non validi");
			}
			
			/*
			 * Gestione errori nel file csv
			 */
			for (String[] string : s) {
				rigaCsv++;
				
				for (String string2 : string) {
					if(!StringUtils.hasText(string2)) {
						break;
					}
					
					rowToDB = string2.split(";");
					if (rowToDB.length < 9) {
						rowToDB = string2.concat(riempitivo).split(";");
					}
					
					for (int i = 0; i < 9; i++) {
						try {
							if (!rowToDB[i].isEmpty()) {
								rowToDB[i] = rowToDB[i].trim();
							}
						} catch (Exception e) {
							logger.error("Errore gestito " + e	+ " - Riga " + rigaCsv + " del csv non valida: " + string2);

							rowToDB = null;
							break;
						}
					}
					
					/*
					 * Fine gestione errori nel file csv
					 */
					if (rowToDB != null) {
						try {
							Timestamp dCheck = DateUtils.stringToTimestamp(rowToDB[0] +  " 23:59:59", "dd/MM/yyyy HH:mm:ss");
							
							if (!dCheck.equals(null) && !dCheck.before(dataCaricamento)) {
								ConnectionManager cm = ConnectionManager
										.getInstance();
								Connection connection = cm.getConnectionOracle();
								SQLQuery query = new SQLQuery(connection, dialect);
								List<Tuple> project = new ArrayList<Tuple>();
	
								project = query
										.from(pde)
										.where(pde.dtesecuzioneOp
												.eq(new java.sql.Date(DateUtils
														.stringToDate(rowToDB[0],
																"dd/MM/yyyy")
														.getTime())))
										.where(pde.codOperazione.eq(rowToDB[1]))
										.where(pde.entita.eq(rowToDB[2]))
										.where(pde.dtDa.eq(new java.sql.Date(
												DateUtils.stringToDate(rowToDB[3],
														"dd/MM/yyyy").getTime())))
										.where(pde.dtA.eq(new java.sql.Date(
												DateUtils.stringToDate(rowToDB[4],
														"dd/MM/yyyy").getTime())))
										.where(pde.dtCaricamento.isNotNull())
										.where((pde.entitaTarget.eq(rowToDB[7]).or(
												pde.entitaTarget.isNull())
												.and(pde.entitaSorgente.eq(
														rowToDB[8])
														.or(pde.entitaSorgente
																.isNull())))
												.or(pde.entitaTarget.isNull()
														.and(pde.entitaSorgente
																.isNull())))
										.list(pde.all());
	
								if (project.size() == 0) {
									// il record del csv non esiste nel DB quindi viene inserito
									new SQLInsertClause(connection, dialect, pde)
											.columns(pde.id, pde.dtesecuzioneOp,
													pde.codOperazione, pde.entita,
													pde.dtDa, pde.dtA,
													pde.delAllDa, pde.delAllA,
													pde.entitaTarget,
													pde.entitaSorgente,
													pde.dtCaricamento)
											.values(StringTemplate
													.create("DMALM_PARAM_DELETE_ESITI_SEQ.nextval"),
													DateUtils.stringToDate(
															rowToDB[0],
															"dd/MM/yyyy"),
													rowToDB[1],
													rowToDB[2],
													DateUtils.stringToDate(
															rowToDB[3],
															"dd/MM/yyyy"),
													DateUtils.stringToDate(
															rowToDB[4],
															"dd/MM/yyyy"),
													DateUtils.stringToDate(
															rowToDB[5],
															"dd/MM/yyyy"),
													DateUtils.stringToDate(
															rowToDB[6],
															"dd/MM/yyyy"),
													rowToDB[7], rowToDB[8],
													dataCaricamento).execute();
									
									connection.commit();
								}
							}
						} catch (Exception e) {
							logger.error("Errore gestito " + e + " - Riga " + rigaCsv + " del csv non valida: " + string2 + " - Impossibile caricare nel DB");
						}
					}
				}
			}
			
			// Finito il caricamento della tabella dei parametri, si può
			// effettuare
			// la cancellazione
			eseguiSvecchiamento();
			
			logger.info("STOP SvecchiamentoFacade.execute()");
		} catch (Exception e) {
			logger.error("Errore non gestito " + e, e);
		}
	}// execute()

	private static void eseguiSvecchiamento() {
		try {
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();
			Timestamp dt1990 = DateUtils.getDtInizioValidita1900();
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmParamDeleteEsiti pde = QDmalmParamDeleteEsiti.dmalmParamDeleteEsiti;
			QDmalmEsitiCaricamenti ec = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;
			QErroriCaricamentoDmAlm err = QErroriCaricamentoDmAlm.erroriCaricamentoDmAlm;
			List<Tuple> q = new ArrayList<Tuple>();
			SQLQuery query = new SQLQuery(connection, dialect);
			q = query
					.from(pde)
					.where(pde.dtesecuzioneOp.eq(new java.sql.Date(
							dataEsecuzione.getTime())))
					.where(pde.dtEsecEffettiva.isNull())
					.list(pde.dtDa, pde.dtA, pde.entita, pde.entitaTarget,
							pde.entitaSorgente, pde.delAllA, pde.delAllDa,
							pde.id);
			for (Tuple t : q) {

				String entita = t.get(pde.entita);
				Timestamp da = DateUtils.dateToTimestamp(t.get(pde.dtDa));
				Timestamp a = DateUtils.dateToTimestamp(t.get(pde.dtA));
				// essendo timestamp, c'è bisogno dell'orario: viene
				// impostato alle 23:59:59
				logger.info("Cancello record compresi fra "+DateUtils.formatDataEsecuzione(da)+ "e "+DateUtils.formatDataEsecuzione(a));

				a.setTime(a.getTime() + (((1440 * 60) - 1) * 1000));
				String target = t.get(pde.entitaTarget);

				String sorgente = t.get(pde.entitaSorgente);

				if (entita.trim().equals("DMALM_ERRORI_CARICAMENTO")) {
					if (t.get(pde.delAllA) == null
							&& t.get(pde.delAllDa) == null) {
						// se non devo cancellare tutti record dal 1990 al 9999
						if (t.get(pde.entitaTarget) != null
								&& t.get(pde.entitaSorgente) != null) {
							if (!t.get(pde.entitaTarget).equals("ALL")
									&& !t.get(pde.entitaSorgente).equals("ALL")) {
								// se devo prendere in considerazione i valori
								// target e sorgente
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.where(err.entitaTarget.eq(target))
										.where(err.entitaSorgente.eq(sorgente))
										.execute();
								logger.info("Caso normale - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");

								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else if (t.get(pde.entitaTarget).equals("ALL")
									&& !t.get(pde.entitaSorgente).equals("ALL")) {

								// cancello tutti i record a prescindere da
								// target
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.where(err.entitaSorgente.eq(sorgente))
										.execute();
								logger.info("Caso Target ALL - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else if (t.get(pde.entitaSorgente).equals("ALL")
									&& !t.get(pde.entitaTarget).equals("ALL")) {
								// cancello tutti i record a prescindere da
								// sorgente
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.where(err.entitaTarget.eq(target))
										.execute();
								logger.info("Caso sorgente ALL - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else {
								// sono entrambi ALL
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a)).execute();
								logger.info("Caso entrambi ALL - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							}
						} else if (t.get(pde.entitaTarget) != null
								&& t.get(pde.entitaSorgente) == null) {
							
							if (t.get(pde.entitaTarget).equals("ALL")) {

								// cancello tutti i record a prescindere da
								// target
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.execute();
								logger.info("Caso Target ALL - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else {
								// cancello tutti i record a in base al target
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.where(err.entitaTarget.eq(target))
										.execute();
								logger.info("Caso Target "+ target +" - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							}
						} else if (t.get(pde.entitaTarget) == null
								&& t.get(pde.entitaSorgente) != null) {
							
							if (t.get(pde.entitaSorgente).equals("ALL")) {

								// cancello tutti i record a prescindere da
								// sorgente
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.execute();
								logger.info("Caso Target ALL - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else {
								// cancello tutti i record a in base al sorgente
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.where(err.entitaSorgente.eq(sorgente))
										.execute();
								logger.info("Caso Sorgente "+ sorgente +" - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							}
							
						} else {
							// Do nothing
							new SQLUpdateClause(connection, dialect, pde)
									.where(pde.id.eq(t.get(pde.id)))
									.set(pde.dtEsecEffettiva, dt1990).execute();
							logger.info("EntitaTarget o EntitaSorgente non permettono di eseguire la cancellazione del record");
							if (target == null)
								target = "Target non presente";
							if (sorgente == null)
								sorgente = "Sorgente non presente";
							ErroriCaricamentoDAO.insert(
									"Procedura_Svecchiamento.csv",
									"Procedura_Svecchiamento.csv",
									"DMALM_PARAM_DELETE_ESITI PK: "
											+ t.get(pde.id),
									"Valore non valido - Target: " + target
											+ " - Sorgente: " + sorgente,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}
					} else if (t.get(pde.delAllA).equals(t.get(pde.dtA))
							&& t.get(pde.delAllDa).equals(da)) {
						// se ho la conferma delle date (anche dal 1990 al 9999)
						if (t.get(pde.entitaTarget) != null
								&& t.get(pde.entitaSorgente) != null) {
							if (!t.get(pde.entitaTarget).equals("ALL")
									&& !t.get(pde.entitaSorgente).equals("ALL")) {
								// se devo prendere in considerazione i valori
								// target e sorgente
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.where(err.entitaTarget.eq(target))
										.where(err.entitaSorgente.eq(sorgente))
										.execute();
								logger.info("Caso data conferma - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else if (t.get(pde.entitaTarget).equals("ALL")
									&& !t.get(pde.entitaSorgente).equals("ALL")) {

								// cancello tutti i record a prescindere da
								// target
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.where(err.entitaSorgente.eq(sorgente))
										.execute();
								logger.info("Caso target ALL + conferma - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else if (t.get(pde.entitaSorgente).equals("ALL")
									&& !t.get(pde.entitaTarget).equals("ALL")) {
								// cancello tutti i record a prescindere da
								// sorgente
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.where(err.entitaTarget.eq(target))
										.execute();
								logger.info("Caso sorgente ALL + conferma - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else {
								// sono entrambi ALL
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a)).execute();
								logger.info("Caso entrambi ALL - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							}
						} else if (t.get(pde.entitaTarget) != null
								&& t.get(pde.entitaSorgente) == null) {
							
							if (t.get(pde.entitaTarget).equals("ALL")) {

								// cancello tutti i record a prescindere da
								// target
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.execute();
								logger.info("Caso Target ALL - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else {
								// cancello tutti i record a in base al target
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.where(err.entitaTarget.eq(target))
										.execute();
								logger.info("Caso Target "+ target +" - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							}
						} else if (t.get(pde.entitaTarget) == null
								&& t.get(pde.entitaSorgente) != null) {
							
							if (t.get(pde.entitaSorgente).equals("ALL")) {

								// cancello tutti i record a prescindere da
								// sorgente
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.execute();
								logger.info("Caso Target ALL - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else {
								// cancello tutti i record a in base al sorgente
								new SQLDeleteClause(connection, dialect, err)
										.where(err.dataCaricamento.between(da,
												a))
										.where(err.entitaSorgente.eq(sorgente))
										.execute();
								logger.info("Caso Sorgente "+ sorgente +" - Cancellazione DMALM_ERRORI_CARICAMENTO effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							}
							
						} else {
							// Do nothing
							new SQLUpdateClause(connection, dialect, pde)
									.where(pde.id.eq(t.get(pde.id)))
									.set(pde.dtEsecEffettiva, dt1990).execute();
							logger.info("EntitaTarget o EntitaSorgente non permettono di eseguire la cencellazione del record");
							if (target == null)
								target = "Target non presente";
							if (sorgente == null)
								sorgente = "Sorgente non presente";
							ErroriCaricamentoDAO.insert(
									"Procedura_Svecchiamento.csv",
									"Procedura_Svecchiamento.csv",
									"DMALM_PARAM_DELETE_ESITI PK: "
											+ t.get(pde.id),
									"Valore non valido - Target: " + target
											+ " - Sorgente: " + sorgente,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}
					}
				} else if (entita.trim().equals("DMALM_ESITI_CARICAMENTI")) {
					if (t.get(pde.delAllA) == null
							&& t.get(pde.delAllDa) == null) {
						// se non devo cancellare tutto
						if (t.get(pde.entitaTarget) != null) {
							if (!t.get(pde.entitaTarget).equals("ALL")) {
								// se devo prendere in considerazione i valori
								// target
								new SQLDeleteClause(connection, dialect, ec)
										.where(ec.dataCaricamento
												.between(da, a))
										.where(ec.entitaTarget.eq(target))
										.execute();
								logger.info("Caso Normale - Cancellazione DMALM_ESITI_CARICAMENTI effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else if (t.get(pde.entitaTarget).equals("ALL")) {

								// cancello tutti i record a prescindere da
								// target
								new SQLDeleteClause(connection, dialect, ec)
										.where(ec.dataCaricamento
												.between(da, a)).execute();
								logger.info("Caso target ALL - Cancellazione DMALM_ESITI_CARICAMENTI effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							}
						} else {
							// Do nothing
							new SQLUpdateClause(connection, dialect, pde)
									.where(pde.id.eq(t.get(pde.id)))
									.set(pde.dtEsecEffettiva, dt1990).execute();
							logger.info("EntitaTarget non permette di eseguire la cencellazione del record");
							if (target == null)
								target = "Target non presente";
							if (sorgente == null)
								sorgente = "Sorgente non presente";
							ErroriCaricamentoDAO.insert(
									"Procedura_Svecchiamento.csv",
									"Procedura_Svecchiamento.csv",
									"DMALM_PARAM_DELETE_ESITI PK: "
											+ t.get(pde.id),
									"Valore non valido - Target: " + target
											+ " - Sorgente: " + sorgente,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}
					} else if (t.get(pde.delAllA).equals(t.get(pde.dtA))
							&& t.get(pde.delAllDa).equals(da)) {
						// se ho la conferma delle date (anche dal 1990 al 9999)
						if (t.get(pde.entitaTarget) != null) {
							if (!t.get(pde.entitaTarget).equals("ALL")) {
								// se devo prendere in considerazione i valori
								// target e sorgente
								new SQLDeleteClause(connection, dialect, ec)
										.where(ec.dataCaricamento
												.between(da, a))
										.where(ec.entitaTarget.eq(target))
										.execute();
								logger.info("Caso date conferma - Cancellazione DMALM_ESITI_CARICAMENTI effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							} else if (t.get(pde.entitaTarget).equals("ALL")) {

								// cancello tutti i record a prescindere da
								// target
								new SQLDeleteClause(connection, dialect, ec)
										.where(ec.dataCaricamento
												.between(da, a)).execute();
								logger.info("Caso target ALL + conferma - Cancellazione DMALM_ESITI_CARICAMENTI effettuata");
								new SQLUpdateClause(connection, dialect, pde)
										.where(pde.id.eq(t.get(pde.id)))
										.set(pde.dtEsecEffettiva,
												dataEsecuzione).execute();
							}
						} else {
							// Do nothing
							new SQLUpdateClause(connection, dialect, pde)
									.where(pde.id.eq(t.get(pde.id)))
									.set(pde.dtEsecEffettiva, dt1990).execute();
							logger.info("EntitaTarget non permette di eseguire la cencellazione del record");
							if (target == null)
								target = "Target non presente";
							if (sorgente == null)
								sorgente = "Sorgente non presente";
							ErroriCaricamentoDAO.insert(
									"Procedura_Svecchiamento.csv",
									"Procedura_Svecchiamento.csv",
									"DMALM_PARAM_DELETE_ESITI PK: "
											+ t.get(pde.id),
									"Valore non valido - Target: " + target
											+ " - Sorgente: " + sorgente,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}

					}
				}
			}
		} catch (Exception e) {
			logger.error("Errore non gestito " + e
					+ " - Impossibile effettuare la cancellazione");
		}
	}

}
