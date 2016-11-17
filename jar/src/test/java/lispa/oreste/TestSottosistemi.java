package lispa.oreste;

import java.sql.Connection;

import junit.framework.TestCase;
import lispa.schedulers.dao.oreste.SottosistemiDAO;
import lispa.schedulers.facade.target.SottosistemaFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmSottosistemi;
import lispa.schedulers.queryimplementation.staging.oreste.QStgSottosistemi;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class TestSottosistemi extends TestCase {

	public void testConnectionLISPA() throws Exception {
		SottosistemiDAO.fillSottosistemi();
	}

	public void testStoricizzazione() throws Exception {
		SottosistemaFacade.execute(DataEsecuzione.getInstance()
				.getDataEsecuzione());
	}

	public void testFillStaging() throws Exception {

		try {
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection connection = cm.getConnectionOracle();

			QStgSottosistemi stgSottositemi = QStgSottosistemi.stgSottosistemi;
			OresteDmAlmSottosistemi viewSottosistemi = OresteDmAlmSottosistemi.dmAlmSottosistemi;

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

			new SQLInsertClause(connection, dialect, stgSottositemi)
					.columns(stgSottositemi.idEdmaSottosistema,
							stgSottositemi.idEdmaPadreSottosistema,
							stgSottositemi.idSottosistema,
							stgSottositemi.tipoOggetto,
							stgSottositemi.siglaProdottoSottosistema,
							stgSottositemi.siglaSottosistema,
							stgSottositemi.nomeSottosistema,
							stgSottositemi.descrizioneSottosistema,
							stgSottositemi.sottosistemaAnnullato,
							stgSottositemi.dfvSottosistemaAnnullato,
							stgSottositemi.edmaRespoSottosistema,
							stgSottositemi.clasfBaseDatiEtl,
							stgSottositemi.clasfBaseDatiLettura,
							stgSottositemi.clasfBaseDatiScrittura,
							stgSottositemi.clasfTipoSottosistema,
							stgSottositemi.dataCaricamento)
					.select(new SQLSubQuery()
							.from(viewSottosistemi)
							.list(viewSottosistemi.idEdmaSottosistema,
									viewSottosistemi.idEdmaPadreSottosistema,
									viewSottosistemi.idSottosistema,
									viewSottosistemi.tipoOggetto,
									viewSottosistemi.siglaProdottoSottosistema,
									viewSottosistemi.siglaSottosistema,
									viewSottosistemi.nomeSottosistema,
									viewSottosistemi.descrizioneSottosistema,
									viewSottosistemi.sottosistemaAnnullato,
									viewSottosistemi.dfvSottosistemaAnnullato,
									viewSottosistemi.edmaRespoSottosistema,
									viewSottosistemi.clasfBaseDatiEtl,
									viewSottosistemi.clasfBaseDatiLettura,
									viewSottosistemi.clasfBaseDatiScrittura,
									viewSottosistemi.clasfTipoSottosistema,
									StringTemplate
											.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))")

							)).execute();

		} catch (Throwable e) {

		}

	}
}