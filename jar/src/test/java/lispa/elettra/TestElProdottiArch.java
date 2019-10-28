package lispa.elettra;

import java.sql.Connection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;
import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaProjectElFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.fonte.elettra.QElettraStgProdottiArchitetture;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElProdotti;


public class TestElProdottiArch extends TestCase{
	
	private static Logger logger = Logger
			.getLogger(CheckLinkAsmSferaProjectElFacade.class);
	
	public void testElProdottiArch() throws Exception {
		DmAlmConfigReaderProperties.setFileProperties("/Users/lucaporro/LISPA/DataMart/props/dm_alm.properties");
		Log4JConfiguration.inizialize();
		ConnectionManager.getInstance().getConnectionOracle();
		ConnectionManager cm = null;
		Connection connection = null;
		long righeInserite = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			QElettraStgProdottiArchitetture qElettraProdottiArchitetture = QElettraStgProdottiArchitetture.elettraProdottiArchitetture;
			QStgElProdotti qStgElProdotti = QStgElProdotti.stgElProdotti;

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> prodotti = query.from(qElettraProdottiArchitetture)
					.list(qElettraProdottiArchitetture.all());
			logger.info("prodotti.size(): "+prodotti.size());
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
								DateUtils.stringToTimestamp("2019-07-01 00:00:00", "yyyy-MM-dd HH:mm:00"),
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
}
