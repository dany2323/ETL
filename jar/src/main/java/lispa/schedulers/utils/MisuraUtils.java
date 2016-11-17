package lispa.schedulers.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lispa.schedulers.bean.staging.sfera.DmalmStgMisura;
import lispa.schedulers.bean.target.sfera.DmalmAsm;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.queryimplementation.staging.sfera.QDmalmStgMisura;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmProgettoSfera;

import org.apache.log4j.Logger;

import com.google.common.io.Files;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.path.StringPath;

public class MisuraUtils {
	private static Logger logger = Logger.getLogger(MisuraUtils.class);
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
	private static QDmalmStgMisura stgMisura = QDmalmStgMisura.dmalmStgMisura;
	private static Timestamp dataEsecuzione = DataEsecuzione.getInstance()
			.getDataEsecuzione();

	/**
	 * 
	 * @param row
	 *            Tupla di una misura il cui contenuto verrà formattato in modo
	 *            da essere più leggibile.
	 * @return una stringa più leggibile rappresentante la misura
	 */
	public static String MisuraToString(Tuple row) {
		QDmalmStgMisura misura = QDmalmStgMisura.dmalmStgMisura;

		String record = "";

		if (row != null) {
			record += "[PK: " + row.get(misura.dmalmStgMisuraPk) + "§ ";
			record += "ID_APPLICAZIONE: " + row.get(misura.idAsm) + "§ ";
			record += "ID_PROGETTO: " + row.get(misura.idProgetto) + "§ ";
			record += "ID_MSR: " + row.get(misura.idMsr) + "§ ";
			record += "APPLICAZIONE: " + row.get(misura.applicazione) + "§ ";
			record += "NOME_PROGETTO: " + row.get(misura.nomeProgetto) + "§ ";
			record += "TIPO_PROGETTO: " + row.get(misura.tipoProgetto) + "§ ";
			record += "NOME_MISURA: " + row.get(misura.nomeMisura) + "§ ";
			record += "STATO_MISURA: " + row.get(misura.statoMisura) + "§ ";
			record += "MODELLO: " + row.get(misura.modello) + "§ ";
			record += "DATA_CARICAMENTO: " + row.get(misura.dataCaricamento)
					+ "] ";
		}

		return record;
	}

	/**
	 * 
	 * @param row
	 *            Tupla di un progetto Sfera il cui contenuto verrà formattato
	 *            in modo da essere più leggibile.
	 * @return una stringa più leggibile rappresentante la misura
	 */

	public static String ProgettoSferaToString(Tuple row) {
		QDmalmProgettoSfera prog = QDmalmProgettoSfera.dmalmProgettoSfera;

		String record = "";

		if (row != null) {
			record += "[PK: " + row.get(prog.dmalmProgettoSferaPk) + "§ ";
			record += "ID_APPLICAZIONE: " + row.get(prog.idAsm) + "§ ";
			record += "ID_PROGETTO: " + row.get(prog.idProgetto) + "§ ";
			record += "NOME_PROGETTO: " + row.get(prog.nomeProgetto) + "§ ";
			record += "TIPO_PROGETTO: " + row.get(prog.tipoProgetto) + "§ ";
			record += "DATA_CARICAMENTO: " + row.get(prog.dataCaricamento)
					+ "] ";
		}

		return record;
	}

	public static String makeListCorrectFormat(String lista,
			StringPath attributoListaUpdate, Tuple t) throws DAOException {
		Connection conn = null;
		
		try {
			conn = cm.getConnectionOracle();

			boolean wrongform = false;
			if (lista != null) {
				if (lista.matches(DmalmRegex.REGEXLOWERCASE)) {
					lista = lista.toUpperCase();
					wrongform = true;
				}
				if (lista.matches(DmalmRegex.NESSUNO)) {
					lista = null;
					wrongform = true;

				}

				if (wrongform) {
					new SQLUpdateClause(conn, dialect, stgMisura)
							.where(stgMisura.dmalmStgMisuraPk.eq(t
									.get(stgMisura.dmalmStgMisuraPk)))
							.set(attributoListaUpdate, lista).execute();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}
		
		return lista;
	}

	public static String trimString(String stringa, String symbol) {
		stringa = stringa.substring(stringa.lastIndexOf(symbol),
				stringa.length());
		return stringa;
	}

	public static boolean equalLists(List<String> one, List<String> two) {
		if (one == null && two == null) {
			return true;
		}

		if ((one == null && two != null) || one != null && two == null
				|| one.size() != two.size()) {
			return false;
		}

		// to avoid messing the order of the lists we will use a copy
		// as noted in comments by A. R. S.
		ArrayList<String> three = new ArrayList<String>();
		ArrayList<String> four = new ArrayList<String>();
		for (String s : one) {
			three.add(s.trim());

		}
		Collections.sort(three);

		for (String s : two) {
			four.add(s.trim());

		}
		Collections.sort(four);
		
		return three.equals(four);
	}

	public static String MisuraToString(DmalmStgMisura row) {
		String record = "";

		if (row != null) {

			record += "[PK: " + row.getDmalmStgMisuraPk() + "§ ";
			record += "ID_APPLICAZIONE: " + row.getIdAsm() + "§ ";
			record += "ID_PROGETTO: " + row.getIdProgetto() + "§ ";
			record += "ID_MSR: " + row.getIdMsr() + "§ ";
			record += "APPLICAZIONE: " + row.getApplicazione() + "§ ";
			record += "NOME_PROGETTO: " + row.getNomeProgetto() + "§ ";
			record += "TIPO_PROGETTO: " + row.getTipoProgetto() + "§ ";
			record += "NOME_MISURA: " + row.getNomeMisura() + "§ ";
			record += "STATO_MISURA: " + row.getStatoMisura() + "§ ";
			record += "MODELLO: " + row.getModello() + "§ ";
			record += "DATA_CARICAMENTO: " + row.getDataCaricamento() + "] ";

		}

		return record;
	}

	/**
	 * Prende IdAsm, IdPrj e IdMsr e li concatena attraverso un carattere che
	 * manterrà la chiave leggibile il primo blocco rappresenta l'IdAsm il
	 * secondo l'IdProgetto e il terzo quello di Misura
	 * 
	 * @param separator
	 *            il carattere che permette di identificare i tre id costituenti
	 *            la chiave naturale
	 * @return
	 */
	public static String chiaveNaturaleGenerator(String separator,
			Timestamp dataEsecuzione, int stgPk, String type) {

		Connection conn = null;
		String chiaveNaturale = "";
		List<Tuple> ids = null;
		
		try {
			conn = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(conn, dialect);
			switch (type) {
			case "Msr":
				ids = query
						.from(stgMisura)
						.where(stgMisura.dataCaricamento.eq(dataEsecuzione))
						.where(stgMisura.dmalmStgMisuraPk.eq(stgPk))
						.list(stgMisura.idAsm, stgMisura.idProgetto,
								stgMisura.idMsr, stgMisura.dmalmStgMisuraPk);
				chiaveNaturale = ids.get(0).get(stgMisura.idAsm) + separator
						+ ids.get(0).get(stgMisura.idProgetto) + separator
						+ ids.get(0).get(stgMisura.idMsr);
				break;
			case "Prj":
				ids = query
						.from(stgMisura)
						.where(stgMisura.dataCaricamento.eq(dataEsecuzione))
						.where(stgMisura.dmalmStgMisuraPk.eq(stgPk))
						.list(stgMisura.idAsm, stgMisura.idProgetto,
								stgMisura.dmalmStgMisuraPk);
				chiaveNaturale = ids.get(0).get(stgMisura.idAsm) + separator
						+ ids.get(0).get(stgMisura.idProgetto);
				break;

			case "Asm":
				ids = query.from(stgMisura)
						.where(stgMisura.dataCaricamento.eq(dataEsecuzione))
						.where(stgMisura.dmalmStgMisuraPk.eq(stgPk))
						.list(stgMisura.idAsm, stgMisura.dmalmStgMisuraPk);
				chiaveNaturale = ids.get(0).get(stgMisura.idAsm).toString();
				break;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return chiaveNaturale;
	}

	public static String chiaveNaturaleExtractor(String chiavenaturale,
			String id, String separator) {

		String estratto = null;
		
		try {

			estratto = chiavenaturale.endsWith(separator + id) ? chiavenaturale
					.substring(0, chiavenaturale.length() - (id.length() + 1))
					: chiavenaturale;

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return estratto;
	}

	public static String currentSferaFile() {
		logger.debug("START currentSferaFile");

		String sfera_current_path = DmAlmConstants.SFERA_CURRENT;
		File sfera_directory = new File(DmAlmConstants.SFERA_PATH);

		final String file_name_date = new SimpleDateFormat(
				DmAlmConstants.DATE_SFERA_FILENAME).format(dataEsecuzione);

		logger.debug("currentSferaFile - file_name_date: " + file_name_date);

		final String complete_name = DmAlmConstants.FILENAME_EXPORT
				+ file_name_date;

		logger.debug("currentSferaFile - complete_name: " + complete_name);

		FilenameFilter fnf = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.contains(file_name_date);
			}
		};

		Timestamp mytime = null;
		Timestamp mysecondtime = null;
		String selectedTime = null;
		String generalTime = null;
		File myselectedfile = null;
		String[] current_list = sfera_directory.list(fnf);

		if (current_list == null || current_list.length == 0) {
			logger.info("Attenzione nessun file SFERA da elaborare : "
					+ complete_name);
			logger.debug("STOP currentSferaFile");
			// Se non viene trovato un file da elaborare inutile proseguire con
			// l'etl di SFERA
			ExecutionManager.getInstance().setExecutionSfera(false);

			return null;
		} else {
			for (String current : current_list) {
				if (selectedTime == null) {
					myselectedfile = new File(sfera_directory
							+ System.getProperty("file.separator") + current);
					selectedTime = current.replace(
							DmAlmConstants.FILENAME_EXPORT, "").replace(
							DmAlmConstants.EXP_SFERA_EXTENSION, "");
					logger.debug("FILE :" + myselectedfile);
					mytime = DateUtils.stringToTimestamp(selectedTime,
							"yyyy_MM_dd_HH_mm_ss");

				}

				generalTime = current.replace(DmAlmConstants.FILENAME_EXPORT,
						"").replace(DmAlmConstants.EXP_SFERA_EXTENSION, "");
				mysecondtime = DateUtils.stringToTimestamp(generalTime,
						"yyyy_MM_dd_HH_mm_ss");

				if (mysecondtime.after(mytime)) {
					myselectedfile = new File(sfera_directory
							+ System.getProperty("file.separator") + current);
				}

			}
			
			File mytargetfile = new File(
					sfera_current_path
							+ System.getProperty("file.separator")
							+ DmAlmConstants.FILENAME_EXPORT
							+ new SimpleDateFormat(
									DmAlmConstants.TIMESTAMP_SFERA_FILENAME)
									.format(mysecondtime)
							+ DmAlmConstants.EXP_SFERA_EXTENSION);
			
			try {
				Files.copy(myselectedfile, mytargetfile);

			} catch (IOException e) {
				logger.error(e.getMessage());
			}

			logger.debug("STOP currentSferaFile");
			
			return mytargetfile.getAbsolutePath();
		}
	}
	
	public static DmalmAsm tuplaToAsm(Tuple row) {
		QDmalmAsm dmalmAsm = QDmalmAsm.dmalmAsm;
		
		DmalmAsm asm = new DmalmAsm();
		
		
		asm.setIdAsm(row
				.get(dmalmAsm.idAsm));
		asm.setDmalmStgMisuraPk(row
				.get(dmalmAsm.dmalmStgMisuraPk));
		asm.setNoteAsm(row.get(dmalmAsm.noteAsm));
		asm.setpAppAccAuthLastUpdate(row
				.get(dmalmAsm.pAppAccAuthLastUpdate));
		asm.setpAppCdAltreAsmCommonServ(row
				.get(dmalmAsm.pAppCdAltreAsmCommonServ));
		asm.setpAppCdAmbitoManAttuale(row
				.get(dmalmAsm.pAppCdAmbitoManAttuale));
		asm.setpAppCodAmbitoManFuturo(row
				.get(dmalmAsm.pAppCodAmbitoManFuturo));
		asm.setpAppCodAsmConfinanti(row
				.get(dmalmAsm.pAppCodAsmConfinanti));
		asm.setpAppCodDirezioneDemand(row
				.get(dmalmAsm.pAppCodDirezioneDemand));
		asm.setpAppCodFlussiIoAsm(row
				.get(dmalmAsm.pAppCodFlussiIoAsm));
		asm.setpAppDataFineValiditaAsm(row
				.get(dmalmAsm.pAppDataFineValiditaAsm));
		asm.setpAppDataInizioValiditaAsm(row
				.get(dmalmAsm.pAppDataInizioValiditaAsm));
		asm.setpAppDataUltimoAggiorn(row
				.get(dmalmAsm.pAppDataUltimoAggiorn));
		asm.setpAppDenomSistTerziConfin(row
				.get(dmalmAsm.pAppDenomSistTerziConfin));
		asm.setpAppDenomUtentiFinaliAsm(row
				.get(dmalmAsm.pAppDenomUtentiFinaliAsm));
		asm.setpAppDenominazioneAsm(row
				.get(dmalmAsm.pAppDenominazioneAsm));
		asm.setpAppFlagDamisurarePatrFp(row
				.get(dmalmAsm.pAppFlagDamisurarePatrFp));
		asm.setpAppFlagInManutenzione(row
				.get(dmalmAsm.pAppFlagInManutenzione));
		asm.setpAppFlagMisurareSvimevFp(row
				.get(dmalmAsm.pAppFlagMisurareSvimevFp));
		asm.setpAppFlagServizioComune(row
				.get(dmalmAsm.pAppFlagServizioComune));
		asm.setpAppIndicValidazioneAsm(row
				.get(dmalmAsm.pAppIndicValidazioneAsm));
		asm.setpAppNomeAuthLastUpdate(row
				.get(dmalmAsm.pAppNomeAuthLastUpdate));
		asm.setAppCls(row.get(dmalmAsm.appCls));
		asm.setApplicazione(row
				.get(dmalmAsm.applicazione));
		asm.setDataDismissione(row
				.get(dmalmAsm.dataDismissione));
		asm.setDataInizioEsercizio(row
				.get(dmalmAsm.dataInizioEsercizio));
		asm.setDataCaricamento(row
				.get(dmalmAsm.dataCaricamento));
		asm.setFrequenzaUtilizzo(row
				.get(dmalmAsm.frequenzaUtilizzo));
		asm.setIncludiInDbPatrimonio(row
				.get(dmalmAsm.includiInDbPatrimonio));
		asm.setNumeroUtenti(row
				.get(dmalmAsm.numeroUtenti));
		asm.setPermissions(row
				.get(dmalmAsm.permissions));
		asm.setProprietaLegale(row
				.get(dmalmAsm.proprietaLegale));
		asm.setUtilizzata(row
				.get(dmalmAsm.utilizzata));
		asm.setVafPredefinito(row
				.get(dmalmAsm.vafPredefinito));
		asm.setDataInizioValidita(row
				.get(dmalmAsm.dataInizioValidita));
		asm.setDataFineValidita(row
				.get(dmalmAsm.dataFineValidita));
		asm.setAnnullato(row
				.get(dmalmAsm.annullato));
		asm.setStrutturaOrganizzativaFk(row
				.get(dmalmAsm.strutturaOrganizzativaFk));
		asm.setUnitaOrganizzativaFk(row
				.get(dmalmAsm.unitaOrganizzativaFk));

		return asm;
	}
}
