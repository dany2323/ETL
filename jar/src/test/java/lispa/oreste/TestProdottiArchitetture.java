package lispa.oreste;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.facade.target.ProdottoFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmProdottiArchitetture;
import lispa.schedulers.queryimplementation.staging.oreste.QStgProdottiArchitetture;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class TestProdottiArchitetture extends TestCase {
	private static Logger logger = Logger
			.getLogger(TestProdottiArchitetture.class);

	public void testTrimSigleInOreste() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		cm = ConnectionManager.getInstance();
		connection = cm.getConnectionOracle();

		lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmModuli viewModuli = lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmModuli.dmAlmModuli;

		SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

		SQLQuery query = new SQLQuery(connection, dialect);

		List<Tuple> prodotti = query.from(viewModuli).list(viewModuli.all());

		logger.debug(prodotti.size());
	}

	public void testStoricizzazione() throws Exception {

		ProdottoFacade
				.execute(DataEsecuzione.getInstance().getDataEsecuzione());
	}

	public void testFil() throws Exception {
		ProdottiArchitettureDAO.fillProdottiArchitetture();
	}

	public void testFillProdottiArchitetture() throws Exception {

		try {
			ProdottiArchitettureDAO.fillProdottiArchitetture();
		} catch (Exception e) {

		}
	}

	// public void testProdottoAnnullatoMaNonDiscendenti() throws Exception {
	// Logger logger = Logger.getLogger(DmAlmCleaning.class);
	// CheckOresteProdottiFacade.checkNameProdottoAnnullatoLogicamente(logger,
	// DataEsecuzione.getInstance().getDataEsecuzione());
	// }
	//
	// public void testDataAnnullamentoFormat() throws Exception {
	// Logger logger = Logger.getLogger(DmAlmCleaning.class);
	//
	// CheckOresteProdottiFacade.checkNameProdottoAnnullatoLogicamente(logger,
	// DataEsecuzione.getInstance().getDataEsecuzione());
	// }

	public void testFillStaging() throws Exception {

		try {
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection connection = cm.getConnectionOracle();

			QStgProdottiArchitetture qStgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;
			OresteDmAlmProdottiArchitetture viewProdottiArchitetture = OresteDmAlmProdottiArchitetture.dmAlmProdottiArchitetture;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			// ID_EDMA_PRODOTTO NUMBER
			// ID_PRODOTTO VARCHAR2
			// TIPO_OGGETTO VARCHAR2
			// SIGLA_PRODOTTO VARCHAR2
			// NOME_PRODOTTO VARCHAR2
			// DESCRIZIONE_PRODOTTO VARCHAR2
			// EDMA_AREA_PRODOTTO VARCHAR2
			// EDMA_RESP_PRODOTTO VARCHAR2
			// PRODOTTO_ANNULLATO VARCHAR2
			// DFV_PRODOTTO_ANNULLATO VARCHAR2
			// CLASF_AMBITO_MANUTENZIONE VARCHAR2
			// CLASF_AREA_TEMATICA VARCHAR2
			// CLASF_BASE_DATI_ETL VARCHAR2
			// CLASF_BASE_DATI_LETTURA VARCHAR2
			// CLASF_BASE_DATI_SCRITTURA VARCHAR2
			// CLASF_FORN_RIS_EST_GARA VARCHAR2
			// CLASF_CATEGORIA VARCHAR2
			// DATA_CARICAMENTO TIMESTAMP(6)

			new SQLInsertClause(connection, dialect, qStgProdottiArchitetture)
					.columns(qStgProdottiArchitetture.idEdmaProdotto,
							qStgProdottiArchitetture.idProdotto,
							qStgProdottiArchitetture.tipoOggetto,
							qStgProdottiArchitetture.siglaProdotto,
							qStgProdottiArchitetture.nomeProdotto,
							qStgProdottiArchitetture.descrizioneProdotto,
							qStgProdottiArchitetture.edmaAreaProdotto,
							qStgProdottiArchitetture.edmaRespProdotto,
							qStgProdottiArchitetture.prodottoAnnullato,
							qStgProdottiArchitetture.dfvProdottoAnnullato,
							qStgProdottiArchitetture.clasfAmbitoManutenzione,
							qStgProdottiArchitetture.clasfAreaTematica,
							qStgProdottiArchitetture.clasfBaseDatiEtl,
							qStgProdottiArchitetture.clasfBaseDatiLettura,
							qStgProdottiArchitetture.clasfBaseDatiScrittura,
							qStgProdottiArchitetture.clasfFornRisEstGara,
							qStgProdottiArchitetture.clasfCategoria,
							qStgProdottiArchitetture.dataCaricamento)
					.select(new SQLSubQuery()
							.from(viewProdottiArchitetture)
							.list(viewProdottiArchitetture.idEdmaProdotto,
									viewProdottiArchitetture.idProdotto,
									viewProdottiArchitetture.tipoOggetto,
									viewProdottiArchitetture.siglaProdotto,
									viewProdottiArchitetture.nomeProdotto,
									viewProdottiArchitetture.descrizioneProdotto,
									viewProdottiArchitetture.edmaAreaProdotto,
									viewProdottiArchitetture.edmaRespProdotto,
									viewProdottiArchitetture.prodottoAnnullato,
									viewProdottiArchitetture.dfvProdottoAnnullato,
									viewProdottiArchitetture.clasfAmbitoManutenzione,
									viewProdottiArchitetture.clasfAreaTematica,
									viewProdottiArchitetture.clasfBaseDatiEtl,
									viewProdottiArchitetture.clasfBaseDatiLettura,
									viewProdottiArchitetture.clasfBaseDatiScrittura,
									viewProdottiArchitetture.clasfFornRisEstGara,
									viewProdottiArchitetture.clasfCategoria,
									StringTemplate
											.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))")

							)).execute();

		} catch (Throwable e) {

		}

	}

	public static void testGetProdottiAnnullati() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> prodottiAnnullati = new ArrayList<Tuple>();

		// select * from DMALM_STG_PROD_ARCHITETTURE where NOME_PRODOTTO is null

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			prodottiAnnullati =

			query.from(qstgProdottiArchitetture)
					.where(qstgProdottiArchitetture.nomeProdotto
							.like(DmAlmConstants.ANNULLATO_LOGICAMENTE + "%"))
					.list(qstgProdottiArchitetture.all());

			logger.debug(prodottiAnnullati.size());
		} catch (Exception e) {

		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public boolean testStringaAnnullamento() {

		String annullamento = "#ANNULLATO LOGICAMENTE#2013326#Sperimentazione Tossicodipendenze";

		String a = "#ASCENDENTE ANNULLATO LOGICAMENTE##20110513#GEOSIG_M-Modulo di amministrazione utenze";

		annullamento = a;

		try {

			if (annullamento != null && !annullamento.equals("")) {
				if (annullamento.toLowerCase().contains(
						"ANNULLATO".toLowerCase())) {
					if (annullamento.toLowerCase().contains(
							"ASCENDENTE".toLowerCase())) {
						if (annullamento.indexOf("#", 1) != -1
								&& annullamento.indexOf("#", 37) != -1)
							annullamento = annullamento.substring(
									annullamento.indexOf("#", 1) + 1,
									annullamento.indexOf("#", 37) + 1);
						else
							annullamento = annullamento.substring(36, 45);
					} else {
						if (annullamento.indexOf("#", 1) != -1
								&& annullamento.indexOf("#", 24) != -1)
							annullamento = annullamento.substring(
									annullamento.indexOf("#", 1) + 1,
									annullamento.indexOf("#", 24) + 1);
						else
							annullamento = annullamento.substring(23, 32);
					}

					annullamento = annullamento.replaceAll("#", "");

					if (annullamento.length() == 8) {

						SimpleDateFormat format = new SimpleDateFormat(
								"yyyyMMdd");
						try {

							if (annullamento.length() != 8)
								throw new ParseException("", 0);

							format.setLenient(false);
							format.parse(annullamento);

							return true;
						} catch (ParseException exception) {

							exception.printStackTrace();
							return false;
						}
					} else {

						return false;
					}

				} else {

					return false;
				}
			} else {

				return false;
			}
		} catch (Exception e) {

		}
		return false;
	}

	public boolean testValidateDataAnnullamento() {

		String annullamento = "#ANNULLATO LOGICAMENTE##20110513#Censimento DB Cartografia";

		String a = "#ANNULLATO LOGICAMENTE L'ASCENDENTE##20130614FMM-I_F-Ric";

		annullamento = a;

		if (annullamento != null && !annullamento.equals("")) {
			if (annullamento.toLowerCase().contains("ANNULLATO".toLowerCase())) {
				if (annullamento.toLowerCase().contains(
						"ASCENDENTE".toLowerCase())) {
					if (annullamento.indexOf("#", 1) != -1
							&& annullamento.indexOf("#", 37) != -1)
						annullamento = annullamento.substring(
								annullamento.indexOf("#", 1) + 1,
								annullamento.indexOf("#", 37) + 1);
					else
						annullamento = annullamento.substring(36, 45);
				} else {
					if (annullamento.indexOf("#", 1) != -1
							&& annullamento.indexOf("#", 24) != -1)
						annullamento = annullamento.substring(
								annullamento.indexOf("#", 1) + 1,
								annullamento.indexOf("#", 24) + 1);
					else
						annullamento = annullamento.substring(23, 32);
				}

				annullamento = annullamento.replaceAll("#", "");

				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				try {

					if (annullamento.length() != 8)
						throw new ParseException("", 0);

					format.setLenient(false);
					format.parse(annullamento);

					return true;
				} catch (ParseException exception) {

					exception.printStackTrace();
					return false;
				}

			} else {

				return false;
			}
		} else {

			return false;
		}

	}

	public void testGetProdottoBySiglaProdotto() throws Exception {
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2014-03-20 10:50:00", "yyyy-MM-dd HH:mm:00");

		try {
			List<Tuple> prodotti = ProdottiArchitettureDAO
					.getProdottoBySiglaProdotto("OAPPS", dataEsecuzione);

			logger.debug(prodotti.size());

		} catch (Exception e) {

		}
	}

	public void testGetResponsabileProdotto() {
		String s = "ambarabaci.0_-??ci99{}00998877665544cocò";

		if (s.indexOf("{") != -1 && s.indexOf("}") != -1) {

		} else {

		}

	}
}