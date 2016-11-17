package lispa.oreste;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.action.DmAlmCleaning;
import lispa.schedulers.dao.oreste.AmbienteTecnologicoDAO;
import lispa.schedulers.facade.cleaning.CheckOresteFunzionalitaFacade;
import lispa.schedulers.facade.target.AmbienteTecnologicoFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmAmbTecnologico;
import lispa.schedulers.queryimplementation.staging.oreste.QStgAmbienteTecnologico;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class TestAmbienteTecnologico extends TestCase {
	private static Logger logger = Logger
			.getLogger(TestAmbienteTecnologico.class);

	public void foo() {
		;
		;
	}

	public void testCleaning() throws Exception {
		Logger logger = Logger.getLogger(DmAlmCleaning.class);
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2014-01-20 00:00:00", "yyyy-MM-dd 00:00:00");

		CheckOresteFunzionalitaFacade.execute(logger, dataEsecuzione);
	}

	public void testCheckDuplicatiNomeAmbTecn() throws Exception {
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2014-01-22 00:00:00", "yyyy-MM-dd 00:00:00");
		List<Tuple> rows = AmbienteTecnologicoDAO
				.getDuplicateNomeAmbienteTecnologico(dataEsecuzione);

		logger.debug(rows.size());
	}

	public void testCheckDifference() throws Exception {

	}

	public void testConnectionLISPA() throws Exception {
		AmbienteTecnologicoDAO.fillAmbienteTecnologico();
	}

	public void testStoricizzazione() throws Exception {
		AmbienteTecnologicoFacade.execute(DataEsecuzione.getInstance()
				.getDataEsecuzione());
	}

	public void testFillStaging() throws Exception {

		try {
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection connection = cm.getConnectionOracle();

			QStgAmbienteTecnologico stgAmbienteTecnologico = QStgAmbienteTecnologico.stgAmbienteTecnologico;
			OresteDmAlmAmbTecnologico viewAmbienteTecnologico = OresteDmAlmAmbTecnologico.dmAlmAmbTecnologico;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			// ID_EDMA_SOTTOSISTEMA NUMBER
			// ID_EDMA_PADRE_SOTTOSISTEMA NUMBER
			// ID_SOTTOSISTEMA VARCHAR2
			// TIPO_OGGETTO VARCHAR2
			// SIGLA_PRODOTTO_SOTTOSISTEMA VARCHAR2
			// SIGLA_SOTTOSISTEMA VARCHAR2
			// NOME_SOTTOSISTEMA VARCHAR2
			// DESCRIZIONE_SOTTOSISTEMA VARCHAR2
			// SOTTOSISTEMA_ANNULLATO VARCHAR2
			// DFV_SOTTOSISTEMA_ANNULLATO VARCHAR2
			// EDMA_RESPO_SOTTOSISTEMA VARCHAR2
			// CLASF_BASE_DATI_ETL VARCHAR2
			// CLASF_BASE_DATI_LETTURA VARCHAR2
			// CLASF_BASE_DATI_SCRITTURA VARCHAR2
			// CLASF_TIPO_SOTTOSISTEMA VARCHAR2
			// DATA_CARICAMENTO TIMESTAMP(6)

			new SQLInsertClause(connection, dialect, stgAmbienteTecnologico)
					.columns(stgAmbienteTecnologico.idAmbienteTecnologico,
							stgAmbienteTecnologico.idEdma,
							stgAmbienteTecnologico.idEdmaPadre,
							stgAmbienteTecnologico.nomeAmbienteTecnologico,
							stgAmbienteTecnologico.descrAmbienteTecnologico,
							stgAmbienteTecnologico.siglaModulo,
							stgAmbienteTecnologico.siglaProdotto,
							stgAmbienteTecnologico.clasfArchiRiferimento,
							stgAmbienteTecnologico.clasfInfrastrutture,
							stgAmbienteTecnologico.clasfSo,
							stgAmbienteTecnologico.clasfTipiLayer,
							stgAmbienteTecnologico.versioneSo,
							stgAmbienteTecnologico.dataCaricamento)
					.select(new SQLSubQuery()
							.from(viewAmbienteTecnologico)
							.list(viewAmbienteTecnologico.idAmbienteTecnologico,
									viewAmbienteTecnologico.idEdma,
									viewAmbienteTecnologico.idEdmaPadre,
									viewAmbienteTecnologico.nomeAmbienteTecnologico,
									viewAmbienteTecnologico.descrAmbienteTecnologico,
									viewAmbienteTecnologico.siglaModulo,
									viewAmbienteTecnologico.siglaProdotto,
									viewAmbienteTecnologico.clasfArchiRiferimento,
									viewAmbienteTecnologico.clasfInfrastrutture,
									viewAmbienteTecnologico.clasfSo,
									viewAmbienteTecnologico.clasfTipiLayer,
									viewAmbienteTecnologico.versioneSo,
									StringTemplate
											.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))")

							)).execute();

		} catch (Throwable e) {

		}

	}

	public void testGetDuplicateNomeAmbienteTecnologico() throws Exception {

		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2014-01-20 00:00:00", "yyyy-MM-dd 00:00:00");
		List<Tuple> list = AmbienteTecnologicoDAO
				.getDuplicateNomeAmbienteTecnologico(dataEsecuzione);

		logger.debug(list.size());
	}

	public void testFillTargetAmbienteTecnologico() throws Exception {

		Timestamp dataEsecuzione = DataEsecuzione.getInstance()
				.setDataEsecuzione(
						DateUtils.stringToTimestamp("2014-03-28 18:45:00",
								"yyyy-MM-dd HH:mm:00"));

		AmbienteTecnologicoFacade.execute(dataEsecuzione);
	}
}