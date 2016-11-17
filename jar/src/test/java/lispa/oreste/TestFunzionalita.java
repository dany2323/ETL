package lispa.oreste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.dao.oreste.FunzionalitaDAO;
import lispa.schedulers.facade.target.FunzionalitaFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmFunzionalita;
import lispa.schedulers.queryimplementation.staging.oreste.QStgFunzionalita;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class TestFunzionalita extends TestCase {
	private static Logger logger = Logger.getLogger(TestFunzionalita.class);

	public void testTrovaAnnullamentiFisici() throws Exception {

		ConnectionManager cm = null;
		Connection conn = null;

		cm = ConnectionManager.getInstance();
		conn = cm.getConnectionOracle();

		String sql = "select ID_EDMA_funzionalita from DMALM_STG_FUNZIONALITA where DATA_CARICAMENTO = {ts '2014-03-17'} "
				+ "minus "
				+ "select ID_EDMA_funzionalita from DMALM_STG_FUNZIONALITA where DATA_CARICAMENTO = {ts '2014-03-12'}";

		PreparedStatement ps = conn.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		int i = 0;
		while (rs.next()) {
			i++;

		}

		logger.debug("i: " + i);
	}

	// public void testLogUtils() throws Exception {
	//
	// List<Tuple> funzionalita = null;
	// ConnectionManager cm = null;
	// Connection conn = null;
	//
	// cm = ConnectionManager.getInstance();
	// conn = cm.getConnectionOracle();
	//
	// SQLTemplates dialect = new HSQLDBTemplates();
	// QStgSottosistemi qstgSottosistemi = QStgSottosistemi.stgSottosistemi;
	//
	// funzionalita = new SQLQuery(conn, dialect)
	// .from(qstgSottosistemi)
	// .list(qstgSottosistemi.all());
	//
	//
	// for(Tuple row : funzionalita)
	//
	//
	//
	// }

	public void testConnectionLISPA() throws Exception {

	}

	public void testStoricizzazione() throws Exception {
		FunzionalitaFacade.execute(DataEsecuzione.getInstance()
				.getDataEsecuzione());
	}

	public void testFillFunzionalita() throws Exception {
		FunzionalitaDAO.fillFunzionalita();
	}

	public void testFillStaging() throws Exception {

		try {
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection connection = cm.getConnectionOracle();

			QStgFunzionalita qStgFunzionalita = QStgFunzionalita.stgFunzionalita;
			OresteDmAlmFunzionalita qViewFunzionalita = OresteDmAlmFunzionalita.dmAlmFunzionalita;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			// ID_EDMA_FUNZIONALITA NUMBER
			// ID_EDMA_PADRE NUMBER
			// ID_FUNZIONALITA VARCHAR2
			// TIPO_FUNZIONALITA VARCHAR2
			// SIGLA_PRODOTTO VARCHAR2
			// SIGLA_SOTTOSISTEMA VARCHAR2
			// SIGLA_MODULO VARCHAR2
			// SIGLA_FUNZIONALITA VARCHAR2
			// NOME_FUNZIONALITA VARCHAR2
			// DESCRIZIONE VARCHAR2
			// FUNZIONALITA_ANNULLATA VARCHAR2
			// DFV_FUNZIONALITA_ANNULLATA VARCHAR2
			// CLASF_CATEGORIA VARCHAR2
			// CLASF_LINGUAGGIO_DI_PROG VARCHAR2
			// CLASF_TIPO_ELABOR VARCHAR2
			// DATA_CARICAMENTO TIMESTAMP(6)

			new SQLInsertClause(connection, dialect, qStgFunzionalita)
					.columns(qStgFunzionalita.idEdmaFunzionalita,
							qStgFunzionalita.idEdmaPadre,
							qStgFunzionalita.idFunzionalita,
							qStgFunzionalita.tipoFunzionalita,
							qStgFunzionalita.siglaProdotto,
							qStgFunzionalita.siglaSottosistema,
							qStgFunzionalita.siglaModulo,
							qStgFunzionalita.siglaFunzionalita,
							qStgFunzionalita.nomeFunzionalita,
							qStgFunzionalita.descrizione,
							qStgFunzionalita.funzionalitaAnnullata,
							qStgFunzionalita.dfvFunzionalitaAnnullata,
							qStgFunzionalita.clasfCategoria,
							qStgFunzionalita.clasfLinguaggioDiProg,
							qStgFunzionalita.clasfTipoElabor,
							qStgFunzionalita.dataCaricamento)
					.select(new SQLSubQuery()
							.from(qViewFunzionalita)
							.list(qViewFunzionalita.idEdmaFunzionalita,
									qViewFunzionalita.idEdmaPadre,
									qViewFunzionalita.idFunzionalita,
									qViewFunzionalita.tipoFunzionalita,
									qViewFunzionalita.siglaProdotto,
									qViewFunzionalita.siglaSottosistema,
									qViewFunzionalita.siglaModulo,
									qViewFunzionalita.siglaFunzionalita,
									qViewFunzionalita.nomeFunzionalita,
									qViewFunzionalita.descrizione,
									qViewFunzionalita.funzionalitaAnnullata,
									qViewFunzionalita.dfvFunzionalitaAnnullata,
									qViewFunzionalita.clasfCategoria,
									qViewFunzionalita.clasfLinguaggioDiProg,
									qViewFunzionalita.clasfTipoElabor,
									StringTemplate
											.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))")

							)).execute();

		} catch (Throwable e) {

		}

	}

	public void testCheckNomeFunzionalitaDuplicati() throws Exception {
		Date today_dt = DateUtils.stringToDate("2013-12-23 00:00:00",
				"yyyy-MM-dd 00:00:00");
		Timestamp dataCaricamento = new Timestamp(today_dt.getTime());

		List<Tuple> nomeFunzionalitaDuplicate = FunzionalitaDAO
				.getDuplicateNomeFunzionalita(dataCaricamento);

		logger.debug("nomeFunzionalitaDuplicate.size: "
				+ nomeFunzionalitaDuplicate.size());
	}
}