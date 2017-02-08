package lispa.schedulers.dao.elettra;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.fonte.elettra.ElettraModuli;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.elettra.QElettraModuli;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElModuli;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class StgElModuliDAO {
	private static Logger logger = Logger.getLogger(StgElModuliDAO.class);

	public static void deleteStaging(Timestamp dataEsecuzioneDelete)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElModuli qStgElModuli = QStgElModuli.stgElModuli;

			new SQLDeleteClause(connection, dialect, qStgElModuli).where(
					qStgElModuli.dataCaricamento.lt(dataEsecuzioneDelete).or(
							qStgElModuli.dataCaricamento.gt(DateUtils
									.addDaysToTimestamp(DataEsecuzione
											.getInstance().getDataEsecuzione(),
											-1)))).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void fillStaging() throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;
		Connection connectionFonteElettra = null;
		long righeInserite = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			connectionFonteElettra = cm.getConnectionOracleFonteElettra();

			QElettraModuli qElettraModuli = QElettraModuli.elettraModuli;
			QStgElModuli qStgElModuli = QStgElModuli.stgElModuli;

			SQLTemplates dialect = new HSQLDBTemplates(true); // use quote

			SQLQuery query = new SQLQuery(connectionFonteElettra, dialect);

			List<Tuple> moduli = query.from(qElettraModuli).list(
					qElettraModuli.all());
			List<ElettraModuli> emList = getModuliFromCSV();
			for (Tuple row : moduli) {
				righeInserite += new SQLInsertClause(connection, dialect,
						qStgElModuli)
						.columns(qStgElModuli.moduloPk,
								qStgElModuli.idModuloEdma,
								qStgElModuli.idModuloEdmaPadre,
								qStgElModuli.idModulo,
								qStgElModuli.tipoOggetto,
								qStgElModuli.siglaProdotto,
								qStgElModuli.siglaSottosistema,
								qStgElModuli.siglaModulo, qStgElModuli.nome,
								qStgElModuli.descrizioneModulo,
								qStgElModuli.annullato,
								qStgElModuli.dataAnnullamento,
								qStgElModuli.responsabile,
								qStgElModuli.sottosistema,
								qStgElModuli.tecnologie,
								qStgElModuli.tipoModulo,
								qStgElModuli.dataCaricamento)
						.values(StringTemplate.create("STG_MODULI_SEQ.nextval"),
								row.get(qElettraModuli.idEdmaModuloOreste),
								row.get(qElettraModuli.idEdmaPadreModuloOreste),
								row.get(qElettraModuli.idModuloOreste),
								row.get(qElettraModuli.tipoModuloOreste),
								row.get(qElettraModuli.siglaProdottoModulo),
								row.get(qElettraModuli.siglaSottosistemaModulo),
								row.get(qElettraModuli.siglaModuloOreste),
								row.get(qElettraModuli.nomeModuloOreste),
								row.get(qElettraModuli.descrizioneModuloOreste),
								row.get(qElettraModuli.moduloOresteAnnullato),
								row.get(qElettraModuli.dfvModuloOresteAnnullato),
								StringUtils.getMaskedValue(row.get(qElettraModuli.responsabileOreste)),
								row.get(qElettraModuli.sottosistema),
								row.get(qElettraModuli.tecnologia),
								row.get(qElettraModuli.tipoModulo),
								DataEsecuzione.getInstance()
										.getDataEsecuzione()).execute();
			}

			logger.debug("StgElModuliDAO.fillStaging - righeInserite: "
					+ righeInserite);
			
			righeInserite = 0;

			if (emList.size() > 0) {
				for (ElettraModuli em : emList) {
					righeInserite += new SQLInsertClause(connection, dialect,
							qStgElModuli)
							.columns(qStgElModuli.moduloPk,
									qStgElModuli.idModuloEdma,
									qStgElModuli.idModuloEdmaPadre,
									qStgElModuli.idModulo,
									qStgElModuli.tipoOggetto,
									qStgElModuli.siglaProdotto,
									qStgElModuli.siglaSottosistema,
									qStgElModuli.siglaModulo,
									qStgElModuli.nome,
									qStgElModuli.descrizioneModulo,
									qStgElModuli.annullato,
									qStgElModuli.dataAnnullamento,
									qStgElModuli.responsabile,
									qStgElModuli.sottosistema,
									qStgElModuli.tecnologie,
									qStgElModuli.tipoModulo,
									qStgElModuli.dataCaricamento)
							.values(StringTemplate
									.create("STG_MODULI_SEQ.nextval"),
									em.getIdEdmaModuloOreste(),
									em.getIdEdmaPadreModuloOreste(),
									em.getIdModuloOreste(),
									em.getTipoModuloOreste(),
									em.getSiglaProdottoModulo(),
									em.getSiglaSottosistemaModulo(),
									em.getSiglaModuloOreste(),
									em.getNomeModuloOreste(),
									em.getDescrizioneModuloOreste(),
									em.getModuloOresteAnnullato(),
									em.getDfvModuloOresteAnnullato(),
									em.getResponsabileOreste(),
									em.getSottosistema(),
									em.getTecnologia(),
									em.getTipoModulo(),
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
				}

				logger.debug("StgElModuliDAO.fillStaging from CSV - righeInserite: "
						+ righeInserite);
			}

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}

			if (cm != null) {
				cm.closeConnection(connectionFonteElettra);
			}
		}
	}

	public static List<Tuple> checkStatoModuliProdottoAnnullato(
			QStgElModuli stgElModuli, String siglaProdotto,
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> el = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			el = query
					.from(stgElModuli)
					.where(stgElModuli.dataCaricamento.eq(dataEsecuzione))
					.where(stgElModuli.nome.notLike("%ANNULLATO%"))
					.where(stgElModuli.siglaProdotto
							.equalsIgnoreCase(siglaProdotto))
					.list(stgElModuli.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return el;
	}

	public static List<Tuple> getModuliAnnullatiByProdotto(
			QStgElModuli stgElModuli, String siglaProdotto,
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			moduli = query.from(stgElModuli)
					.where(stgElModuli.dataCaricamento.eq(dataEsecuzione))
					.where(stgElModuli.siglaProdotto.eq(siglaProdotto))
					.where(stgElModuli.nome.like("%ANNULLATO%"))
					.list(stgElModuli.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduli;
	}

	public static List<Tuple> getDuplicateSiglaModuloInsiemeProdotto(
			QStgElModuli stgElModuli, Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			moduli = query
					.from(stgElModuli)
					.where(stgElModuli.siglaModulo.in(new SQLSubQuery()
							.from(stgElModuli)
							.where(stgElModuli.dataCaricamento
									.eq(dataEsecuzione))
							.groupBy(stgElModuli.siglaProdotto)
							.groupBy(stgElModuli.siglaModulo)
							.having(stgElModuli.siglaModulo.count().gt(1))
							.list(stgElModuli.siglaModulo)))
					.where(stgElModuli.siglaProdotto.isNotNull())
					// appartenenti ad uno specifico prodotto
					.where(stgElModuli.siglaSottosistema.isNull()
							.or(stgElModuli.siglaSottosistema
									.equalsIgnoreCase("*")))
					.where(stgElModuli.dataCaricamento.eq(dataEsecuzione))
					.list(stgElModuli.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduli;
	}

	public static List<Tuple> getDuplicateSiglaModuloInsiemeSottosistema(
			QStgElModuli stgElModuli, Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			moduli = query
					.from(stgElModuli)
					.where(stgElModuli.siglaModulo.in(new SQLSubQuery()
							.from(stgElModuli)
							.where(stgElModuli.dataCaricamento
									.eq(dataEsecuzione))
							.where(stgElModuli.siglaSottosistema.isNotNull())
							.where(stgElModuli.siglaSottosistema
									.notEqualsIgnoreCase("*"))
							.groupBy(stgElModuli.siglaSottosistema)
							.groupBy(stgElModuli.siglaModulo)
							.having(stgElModuli.siglaModulo.count().gt(1))
							.list(stgElModuli.siglaModulo)))
					.where(stgElModuli.dataCaricamento.eq(dataEsecuzione))
					.list(stgElModuli.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduli;
	}

	public static List<Tuple> getSiglaModuliNull(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgElModuli stgElModuli = QStgElModuli.stgElModuli;

			moduli = query.from(stgElModuli)
					.where(stgElModuli.dataCaricamento.eq(dataEsecuzione))
					.where(stgElModuli.siglaModulo.isNull())
					.list(stgElModuli.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduli;
	}

	public static List<Tuple> getDuplicateNomeModuloInsiemeSottosistema(
			QStgElModuli stgElModuli, Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			moduli = query
					.from(stgElModuli)
					.where(stgElModuli.nome.in(new SQLSubQuery()
							.from(stgElModuli)
							.where(stgElModuli.dataCaricamento
									.eq(dataEsecuzione))
							.where(stgElModuli.siglaSottosistema.isNotNull())
							.where(stgElModuli.siglaSottosistema
									.notEqualsIgnoreCase("*"))
							.groupBy(stgElModuli.siglaSottosistema)
							.groupBy(stgElModuli.nome)
							.having(stgElModuli.nome.count().gt(1))
							.list(stgElModuli.nome)))
					.where(stgElModuli.dataCaricamento.eq(dataEsecuzione))
					.list(stgElModuli.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduli;
	}

	public static List<Tuple> getDuplicateNomeModuloInsiemeProdotto(
			QStgElModuli stgElModuli, Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);

			moduli = query
					.from(stgElModuli)
					.where(stgElModuli.nome.in(new SQLSubQuery()
							.from(stgElModuli)
							.where(stgElModuli.dataCaricamento
									.eq(dataEsecuzione))
							.groupBy(stgElModuli.siglaProdotto)
							.groupBy(stgElModuli.nome)
							.having(stgElModuli.nome.count().gt(1))
							.list(stgElModuli.nome)))
					.where(stgElModuli.dataCaricamento.eq(dataEsecuzione))
					.where(stgElModuli.siglaProdotto.isNotNull())
					.where(stgElModuli.siglaSottosistema.isNull()
							.or(stgElModuli.siglaSottosistema
									.equalsIgnoreCase("*")))
					.where(stgElModuli.nome.notLike("%ANNULLATO%"))
					.list(stgElModuli.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduli;
	}

	public static List<Tuple> getNomeModuliNull(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgElModuli stgElModuli = QStgElModuli.stgElModuli;

			moduli = query.from(stgElModuli)
					.where(stgElModuli.dataCaricamento.eq(dataEsecuzione))
					.where(stgElModuli.nome.isNull()).list(stgElModuli.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduli;
	}

	public static List<Tuple> getModuliAnnullati(QStgElModuli stgElModuli,
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> moduliAnnnullati = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			moduliAnnnullati = query.from(stgElModuli)
					.where(stgElModuli.dataCaricamento.eq(dataEsecuzione))
					.where(stgElModuli.nome.like("%ANNULLATO%"))
					.list(stgElModuli.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduliAnnnullati;
	}

	public static List<Tuple> getAllModuli(QStgElModuli stgElModuli,
			Timestamp dataEsecuzione) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			moduli = query.from(stgElModuli)
					.where(stgElModuli.dataCaricamento.eq(dataEsecuzione))
					.list(stgElModuli.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return moduli;
	}

	public static List<ElettraModuli> getModuliFromCSV() throws NoSuchAlgorithmException, DAOException {
		List<ElettraModuli> result = new ArrayList<ElettraModuli>();
		String[] nextLine;
		String[] rowToDB = null;
		List<String[]> s = new ArrayList<String[]>();
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		CSVReader reader = null;

		try {
			// reader = new CSVReader(new InputStreamReader(new
			// FileInputStream(DmAlmConstants.SFERA_CSV + "DM_ALM_MODULI" +
			// DmAlmConstants.MPS_CSV_EXTENSION), "ISO-8859-1"), '\t');
			fileInputStream = new FileInputStream(DmAlmConstants.SFERA_CSV
					+ "DM_ALM_MODULI" + DmAlmConstants.MPS_CSV_EXTENSION);
			inputStreamReader = new InputStreamReader(fileInputStream,
					"ISO-8859-1");
			reader = new CSVReader(inputStreamReader, '\t');

			nextLine = reader.readNext();
			nextLine = reader.readNext();// salto la riga dei nomi delle
											// colonne, se il csv non ha questa
											// riga, si può rimuovere
			while (nextLine != null) {
				s.add(nextLine);
				nextLine = reader.readNext();
			}

			if (fileInputStream != null) {
				fileInputStream.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (reader != null) {
				reader.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("Errore gestito " + e.getMessage()
					+ " Path o file csv non validi");
		}

		if (s.size() > 0) {
			for (String[] string : s) {
				for (String string2 : string) {
					rowToDB = string2.split(";");
					ElettraModuli em = new ElettraModuli();
					em.setIdEdmaModuloOreste(rowToDB[0]);
					em.setIdEdmaPadreModuloOreste(rowToDB[1]);
					em.setIdModuloOreste(rowToDB[2]);
					em.setTipoModuloOreste(rowToDB[3]);
					em.setSiglaProdottoModulo(rowToDB[4]);
					em.setSiglaSottosistemaModulo(rowToDB[5]);
					em.setSiglaModuloOreste(rowToDB[6]);
					em.setNomeModuloOreste(rowToDB[7]);
					em.setDescrizioneModuloOreste(rowToDB[8]);
					em.setModuloOresteAnnullato(rowToDB[9]);
					em.setDfvModuloOresteAnnullato(rowToDB[10]);
					em.setResponsabileOreste(StringUtils.getMaskedValue(rowToDB[11]));
					em.setSottosistema(rowToDB[12]);
					em.setTecnologia(rowToDB[13]);
					em.setTipoModulo(rowToDB[14]);

					result.add(em);
				}
			}
		}

		return result;
	}

	public static void recoverElModuli() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElModuli qStgElModuli = QStgElModuli.stgElModuli;

			new SQLDeleteClause(connection, dialect, qStgElModuli).where(
					qStgElModuli.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
