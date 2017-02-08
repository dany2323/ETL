package lispa.schedulers.dao.elettra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.elettra.QElettraProdottiArchitetture;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElProdotti;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class StgElProdottiDAO {
	private static Logger logger = Logger.getLogger(StgElProdottiDAO.class);

	public static void deleteStaging(Timestamp dataEsecuzioneDelete)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElProdotti qStgElProdotti = QStgElProdotti.stgElProdotti;

			new SQLDeleteClause(connection, dialect, qStgElProdotti).where(
					qStgElProdotti.dataCaricamento.lt(dataEsecuzioneDelete).or(
							qStgElProdotti.dataCaricamento.gt(DateUtils
									.addDaysToTimestamp(DataEsecuzione
											.getInstance().getDataEsecuzione(),
											-1)))).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
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

			QElettraProdottiArchitetture qElettraProdottiArchitetture = QElettraProdottiArchitetture.elettraProdottiArchitetture;
			QStgElProdotti qStgElProdotti = QStgElProdotti.stgElProdotti;

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connectionFonteElettra, dialect);

			List<Tuple> prodotti = query.from(qElettraProdottiArchitetture)
					.list(qElettraProdottiArchitetture.all());

			for (Tuple row : prodotti) {
				righeInserite += new SQLInsertClause(connection, dialect,
						qStgElProdotti)
						.columns(qStgElProdotti.prodottoPk,
								qStgElProdotti.idProdottoEdma,
								qStgElProdotti.idProdotto,
								qStgElProdotti.tipoOggetto,
								qStgElProdotti.sigla, qStgElProdotti.nome,
								qStgElProdotti.descrizioneProdotto,
								qStgElProdotti.areaProdotto,
								qStgElProdotti.responsabileProdotto,
								qStgElProdotti.annullato,
								qStgElProdotti.dataAnnullamento,
								qStgElProdotti.ambitoManutenzione,
								qStgElProdotti.areaTematica,
								qStgElProdotti.BaseDatiEtl,
								qStgElProdotti.BaseDatiLettura,
								qStgElProdotti.BaseDatiScrittura,
								qStgElProdotti.categoria,
								qStgElProdotti.fornituraRisorseEsterne,
								qStgElProdotti.codiceAreaProdotto,
								qStgElProdotti.dataCaricamento,
								//modificato per DM_ALM-224
								qStgElProdotti.ambitoTecnologico,
								qStgElProdotti.ambitoManutenzioneDenom,
								qStgElProdotti.ambitoManutenzioneCodice,
								qStgElProdotti.stato,
								//modificato per DM_ALM-237
								qStgElProdotti.cdResponsabileProdotto
								)
						.values(StringTemplate
								.create("STG_PROD_ARCHITETTURE_SEQ.nextval"),
								row.get(qElettraProdottiArchitetture.idEdmaProdArchApplOreste),
								row.get(qElettraProdottiArchitetture.idProdottoArchApplOreste),
								row.get(qElettraProdottiArchitetture.tipoProdottoOreste),
								row.get(qElettraProdottiArchitetture.siglaProdArchApplOreste),
								row.get(qElettraProdottiArchitetture.nomeProdArchApplOreste),
								row.get(qElettraProdottiArchitetture.descrProdArchApplOreste),
								row.get(qElettraProdottiArchitetture.areaProdArchApplOreste),
								StringUtils.getMaskedValue(row.get(qElettraProdottiArchitetture.respProdArchApplOreste)),
								row.get(qElettraProdottiArchitetture.prodArchApplOresteAnnullato),
								row.get(qElettraProdottiArchitetture.dfvAnnullamento),
								StringUtils.getMaskedValue(row.get(qElettraProdottiArchitetture.classifAmbitoDiManutenzione)),
								row.get(qElettraProdottiArchitetture.classifAreaTematica),
								row.get(qElettraProdottiArchitetture.classifBaseDatiETL),
								row.get(qElettraProdottiArchitetture.classifBaseDatiLettura),
								row.get(qElettraProdottiArchitetture.classifBaseDatiScrittura),
								row.get(qElettraProdottiArchitetture.classifCategoriaProdotto),
								row.get(qElettraProdottiArchitetture.classifFornituraDaGara),
								row.get(qElettraProdottiArchitetture.codiceAreaProdArchAppl),
								DataEsecuzione.getInstance().getDataEsecuzione(),
								//modificato per DM_ALM-224
								row.get(qElettraProdottiArchitetture.ambitoTecnologico),
								row.get(qElettraProdottiArchitetture.ambitoManutenzioneDenom),
								row.get(qElettraProdottiArchitetture.ambitoManutenzioneCodice),
								row.get(qElettraProdottiArchitetture.stato),
								//modificato per DM_ALM-237
								StringUtils.getMaskedValue(getCodiceFromResponsabile(row.get(qElettraProdottiArchitetture.respProdArchApplOreste))))
						.execute();
			}

			connection.commit();

			logger.debug("StgElProdottiDAO.fillStaging - righeInserite: "
					+ righeInserite);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
			if (cm != null)
				cm.closeConnection(connectionFonteElettra);
		}
	}

	private static String getCodiceFromResponsabile(String responsabile) {
		if (responsabile == null) {
			return null;
		}
		String match = "";
		String pattern = "\\{[a-zA-Z0-9_.*!?-]+\\}";
	    Pattern p = Pattern.compile(pattern);
	    Matcher m = p.matcher(responsabile);
	    if (m.find( )) {
	    	match = m.group(0);
	    	match = match.replace("{", "");
	    	match = match.replace("}", "");
	    }
		return match;
	}

	public static List<Tuple> getSiglaProdottiNull(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgElProdotti stgElProdotti = QStgElProdotti.stgElProdotti;

			prodotti = query.from(stgElProdotti)
					.where(stgElProdotti.dataCaricamento.eq(dataEsecuzione))
					.where(stgElProdotti.sigla.isNull())
					.list(stgElProdotti.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getDuplicateSiglaProdotto(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgElProdotti stgElProdotti = QStgElProdotti.stgElProdotti;

			prodotti = query
					.from(stgElProdotti)
					.where(stgElProdotti.sigla.in(new SQLSubQuery()
							.from(stgElProdotti)
							.where(stgElProdotti.dataCaricamento
									.eq(dataEsecuzione))
							.groupBy(stgElProdotti.sigla)
							.having(stgElProdotti.sigla.count().gt(1))
							.list(stgElProdotti.sigla)))
					.list(stgElProdotti.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getNomiProdottoNull(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgElProdotti stgElProdotti = QStgElProdotti.stgElProdotti;

			prodotti = query.from(stgElProdotti)
					.where(stgElProdotti.dataCaricamento.eq(dataEsecuzione))
					.where(stgElProdotti.nome.isNull())
					.list(stgElProdotti.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getDuplicateNomeProdotto(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgElProdotti stgElProdotti = QStgElProdotti.stgElProdotti;

			prodotti = query
					.from(stgElProdotti)
					.where(stgElProdotti.nome.in(new SQLSubQuery()
							.from(stgElProdotti)
							.where(stgElProdotti.dataCaricamento
									.eq(dataEsecuzione))
							.groupBy(stgElProdotti.nome)
							.having(stgElProdotti.nome.count().gt(1))
							.list(stgElProdotti.nome)))
					.list(stgElProdotti.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static List<Tuple> getProdottiAnnullati(
			QStgElProdotti qstgElProdotti, Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodottiAnnullati = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			prodottiAnnullati =

			query.from(qstgElProdotti)
					.where(qstgElProdotti.dataCaricamento.eq(dataEsecuzione))
					.where(qstgElProdotti.nome.like("%ANNULLATO%"))
					.list(qstgElProdotti.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodottiAnnullati;
	}

	public static List<Tuple> getAllProdottiArchitetture(
			QStgElProdotti qstgElProdotti, Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodotti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			prodotti = query.from(qstgElProdotti)
					.where(qstgElProdotti.dataCaricamento.eq(dataEsecuzione))
					.list(qstgElProdotti.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return prodotti;
	}

	public static void recoverElProdotti() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElProdotti qStgElProdotti = QStgElProdotti.stgElProdotti;

			new SQLDeleteClause(connection, dialect, qStgElProdotti).where(
					qStgElProdotti.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
