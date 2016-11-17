package lispa.oreste;

import java.sql.Connection;

import junit.framework.TestCase;
import lispa.schedulers.dao.oreste.ModuliDAO;
import lispa.schedulers.facade.cleaning.CheckOresteModuliFacade;
import lispa.schedulers.facade.target.ModuloFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmModuli;
import lispa.schedulers.queryimplementation.staging.oreste.QStgModuli;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class TestModuli extends TestCase {

	Logger logger = Logger.getLogger(TestModuli.class);

	public void testConnectionLISPA() throws Exception {
		ModuliDAO.fillModuli();
	}

	public void testStoricizzazione() throws Exception {
		ModuloFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
	}

	public void testCheckResponsabileModulo() throws Exception {
		CheckOresteModuliFacade.checkNomiModuliAnnullatiLogicamente(logger,
				DataEsecuzione.getInstance().getDataEsecuzione());
	}

	public void testFillStaging() throws Exception {

		try {
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection connection = cm.getConnectionOracle();

			QStgModuli qStgModuli = QStgModuli.stgModuli;
			OresteDmAlmModuli qViewModuli = OresteDmAlmModuli.dmAlmModuli;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			// ID_EDMA_MODULO NUMBER
			// ID_EDMA_PADRE_MODULO NUMBER
			// ID_MODULO VARCHAR2
			// TIPO_OGGETTO VARCHAR2
			// SIGLA_PRODOTTO_MODULO VARCHAR2
			// SIGLA_SOTTOSISTEMA_MODULO VARCHAR2
			// SIGLA_MODULO VARCHAR2
			// NOME_MODULO VARCHAR2
			// DESCRIZIONE_MODULO VARCHAR2
			// MODULO_ANNULLATO VARCHAR2
			// DFV_MODULO_ANNULLATO VARCHAR2
			// EDMA_RESP_MODULO VARCHAR2
			// CLASF_SOTTOSISTEMA_MODULO VARCHAR2
			// CLASF_TECNOLOGIA_MODULO VARCHAR2
			// CLASF_TIPOLOGIA_MODULO VARCHAR2
			// DATA_CARICAMENTO TIMESTAMP(6)

			new SQLInsertClause(connection, dialect, qStgModuli)
					.columns(qStgModuli.idEdmaModulo,
							qStgModuli.idEdmaPadreModulo, qStgModuli.idModulo,
							qStgModuli.tipoOggetto,
							qStgModuli.siglaProdottoModulo,
							qStgModuli.siglaSottosistemaModulo,
							qStgModuli.siglaModulo, qStgModuli.nomeModulo,
							qStgModuli.descrizioneModulo,
							qStgModuli.moduloAnnullato,
							qStgModuli.dfvModuloAnnullato,
							qStgModuli.edmaRespModulo,
							qStgModuli.clasfSottosistemaModulo,
							qStgModuli.clasfTecnologiaModulo,
							qStgModuli.clasfTipologiaModulo,
							qStgModuli.dataCaricamento)
					.select(new SQLSubQuery()
							.from(qViewModuli)
							.list(qViewModuli.idEdmaModulo,
									qViewModuli.idEdmaPadreModulo,
									qViewModuli.idModulo,
									qViewModuli.tipoOggetto,
									qViewModuli.siglaProdottoModulo,
									qViewModuli.siglaSottosistemaModulo,
									qViewModuli.siglaModulo,
									qViewModuli.nomeModulo,
									qViewModuli.descrizioneModulo,
									qViewModuli.moduloAnnullato,
									qViewModuli.dfvModuloAnnullato,
									qViewModuli.edmaRespModulo,
									qViewModuli.clasfSottosistemaModulo,
									qViewModuli.clasfTecnologiaModulo,
									qViewModuli.clasfTipologiaModulo,
									StringTemplate
											.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))")

							)).execute();

		} catch (Throwable e) {

		}

	}

	// public void testFillTargetOreste() throws Exception {
	// DmAlmFillTarget.doWork();
	// }

}