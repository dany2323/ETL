package lispa.schedulers.facade.cleaning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLUpdateClause;

import lispa.schedulers.bean.target.Total;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.TotalDao;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QTotal;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaAssistenza;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdotto;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmBuild;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmClassificatore;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDifettoProdotto;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDocumento;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmFase;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmManutenzione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmPei;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoDemand;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoEse;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoDem;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoSvil;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgramma;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseDiProgetto;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseIt;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseServizi;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmRichiestaGestione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmRichiestaManutenzione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSottoprogramma;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTask;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTaskIt;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTestcase;

public class CheckChangingWorkitemFacade {

	private static Logger logger = Logger
			.getLogger(CheckChangingWorkitemFacade.class);
	private static SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
	private static QTotal qTotal = QTotal.total;
	
	private static void updateChanging(List<Tuple> t, Connection connection) throws DAOException, SQLException{
		
		SQLQuery query = null;
		String value = "";
		String backValue = "";
		Date dataModifica = null;
		Tuple a;
		HashMap<String, String> help = new HashMap<String, String>();
		for(Tuple tt: t) {
			String type = tt.get(qTotal.type);
			if(!backValue.equalsIgnoreCase(type))
				value = backValue;
			Date tmp_dataModifica = null;
			try {
			switch(type) {
			case "anomalia":
				query = new SQLQuery(connection, dialect);
				QDmalmAnomaliaProdotto anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
				a = query.from(anomalia)
						.where(anomalia.dmalmAnomaliaProdottoPk.eq(tt.get(qTotal.dmalmPk)))
						.list(anomalia.all()).get(0);
				tmp_dataModifica = a.get(anomalia.dtModificaRecordAnomalia);
				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
					new SQLUpdateClause(connection, dialect,anomalia)
					.where(anomalia.dmalmAnomaliaProdottoPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
				new SQLUpdateClause(connection, dialect,anomalia)
					.where(anomalia.dmalmAnomaliaProdottoPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "documento":
				query = new SQLQuery(connection, dialect);
				QDmalmDocumento documento = QDmalmDocumento.dmalmDocumento;
				a = query.from(documento)
						.where(documento.dmalmDocumentoPk.eq(tt.get(qTotal.dmalmPk)))
						.list(documento.all()).get(0);
				tmp_dataModifica = a.get(documento.dtModificaDocumento);
				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
					new SQLUpdateClause(connection, dialect,documento)
					.where(documento.dmalmDocumentoPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmDocumento.dmalmDocumento.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				documento = QDmalmDocumento.dmalmDocumento;
				new SQLUpdateClause(connection, dialect,documento)
					.where(documento.dmalmDocumentoPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmDocumento.dmalmDocumento.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "testcase":
				query = new SQLQuery(connection, dialect);
				QDmalmTestcase testcase = QDmalmTestcase.dmalmTestcase;
				a = query.from(testcase)
						.where(testcase.dmalmTestcasePk.eq(tt.get(qTotal.dmalmPk)))
						.list(testcase.all()).get(0);
				tmp_dataModifica = a.get(testcase.dtModificaTestcase);
				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
					new SQLUpdateClause(connection, dialect,testcase)
					.where(testcase.dmalmTestcasePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmTestcase.dmalmTestcase.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				testcase = QDmalmTestcase.dmalmTestcase;
				new SQLUpdateClause(connection, dialect,testcase)
					.where(testcase.dmalmTestcasePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmTestcase.dmalmTestcase.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
			
			case "pei":
				query = new SQLQuery(connection, dialect);
				QDmalmPei pei = QDmalmPei.dmalmPei;
				a = query.from(pei)
						.where(pei.dmalmPeiPk.eq(tt.get(qTotal.dmalmPk)))
						.list(pei.all()).get(0);
				tmp_dataModifica = a.get(pei.dtModificaPei);
				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
					new SQLUpdateClause(connection, dialect,pei)
					.where(pei.dmalmPeiPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmPei.dmalmPei.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				pei = QDmalmPei.dmalmPei;
				new SQLUpdateClause(connection, dialect,pei)
					.where(pei.dmalmPeiPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmPei.dmalmPei.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "build":
				query = new SQLQuery(connection, dialect);
				QDmalmBuild build = QDmalmBuild.dmalmBuild;
				a = query.from(build)
						.where(build.dmalmBuildPk.eq(tt.get(qTotal.dmalmPk)))
						.list(build.all()).get(0);
				tmp_dataModifica = a.get(build.dtModificaBuild);
				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
					new SQLUpdateClause(connection, dialect,build)
					.where(build.dmalmBuildPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmBuild.dmalmBuild.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				build = QDmalmBuild.dmalmBuild;
				new SQLUpdateClause(connection, dialect,build)
					.where(build.dmalmBuildPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmBuild.dmalmBuild.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
			
			case "progettoese":
				query = new SQLQuery(connection, dialect);
				QDmalmProgettoEse progettoese = QDmalmProgettoEse.dmalmProgettoEse;
				a = query.from(progettoese)
						.where(progettoese.dmalmProgettoEsePk.eq(tt.get(qTotal.dmalmPk)))
						.list(progettoese.all()).get(0);
				tmp_dataModifica = a.get(progettoese.dtModificaProgettoEse);
				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
					new SQLUpdateClause(connection, dialect,progettoese)
					.where(progettoese.dmalmProgettoEsePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmProgettoEse.dmalmProgettoEse.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				progettoese = QDmalmProgettoEse.dmalmProgettoEse;
				new SQLUpdateClause(connection, dialect,progettoese)
					.where(progettoese.dmalmProgettoEsePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmProgettoEse.dmalmProgettoEse.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "fase":
				query = new SQLQuery(connection, dialect);
				QDmalmFase fase = QDmalmFase.dmalmFase;
				a = query.from(fase)
						.where(fase.dmalmFasePk.eq(tt.get(qTotal.dmalmPk)))
						.list(fase.all()).get(0);
				tmp_dataModifica = a.get(fase.dtModificaFase);
				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
					new SQLUpdateClause(connection, dialect,fase)
					.where(fase.dmalmFasePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmFase.dmalmFase.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				fase = QDmalmFase.dmalmFase;
				new SQLUpdateClause(connection, dialect,fase)
					.where(fase.dmalmFasePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmFase.dmalmFase.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "defect":
				
				query = new SQLQuery(connection, dialect);
				QDmalmDifettoProdotto DifettoProdotto = QDmalmDifettoProdotto.dmalmDifettoProdotto;
				a = query.from(DifettoProdotto)
						.where(DifettoProdotto.dmalmDifettoProdottoPk.eq(tt.get(qTotal.dmalmPk)))
						.list(DifettoProdotto.all()).get(0);
				tmp_dataModifica = a.get(DifettoProdotto.dtModificaRecordDifetto);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,DifettoProdotto)
					.where(DifettoProdotto.dmalmDifettoProdottoPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmDifettoProdotto.dmalmDifettoProdotto.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				DifettoProdotto = QDmalmDifettoProdotto.dmalmDifettoProdotto;
				new SQLUpdateClause(connection, dialect,DifettoProdotto)
					.where(DifettoProdotto.dmalmDifettoProdottoPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmDifettoProdotto.dmalmDifettoProdotto.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "release":
				query = new SQLQuery(connection, dialect);
				QDmalmReleaseDiProgetto release = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
				a = query.from(release)
						.where(release.dmalmReleasediprogPk.eq(tt.get(qTotal.dmalmPk)))
						.list(release.all()).get(0);
				tmp_dataModifica = a.get(release.dtModificaReleasediprog);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,release)
					.where(release.dmalmReleasediprogPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				release = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
				new SQLUpdateClause(connection, dialect,release)
					.where(release.dmalmReleasediprogPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "sottoprogramma":
				query = new SQLQuery(connection, dialect);
				QDmalmSottoprogramma sottoprogramma = QDmalmSottoprogramma.dmalmSottoprogramma;
				a = query.from(sottoprogramma)
						.where(sottoprogramma.dmalmSottoprogrammaPk.eq(tt.get(qTotal.dmalmPk)))
						.list(sottoprogramma.all()).get(0);
				tmp_dataModifica = a.get(sottoprogramma.dtModificaSottoprogramma);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,sottoprogramma)
					.where(sottoprogramma.dmalmSottoprogrammaPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmSottoprogramma.dmalmSottoprogramma.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				sottoprogramma = QDmalmSottoprogramma.dmalmSottoprogramma;
				new SQLUpdateClause(connection, dialect,sottoprogramma)
					.where(sottoprogramma.dmalmSottoprogrammaPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmSottoprogramma.dmalmSottoprogramma.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "programma":
				query = new SQLQuery(connection, dialect);
				QDmalmProgramma programma = QDmalmProgramma.dmalmProgramma;
				a = query.from(programma)
						.where(programma.dmalmProgrammaPk.eq(tt.get(qTotal.dmalmPk)))
						.list(programma.all()).get(0);
				tmp_dataModifica = a.get(programma.dtModificaProgramma);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,programma)
					.where(programma.dmalmProgrammaPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmProgramma.dmalmProgramma.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				programma = QDmalmProgramma.dmalmProgramma;
				new SQLUpdateClause(connection, dialect,programma)
					.where(programma.dmalmProgrammaPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmProgramma.dmalmProgramma.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "taskit":
				query = new SQLQuery(connection, dialect);
				QDmalmTaskIt taskit = QDmalmTaskIt.dmalmTaskIt;
				a = query.from(taskit)
						.where(taskit.dmalmTaskItPk.eq(tt.get(qTotal.dmalmPk)))
						.list(taskit.all()).get(0);
				tmp_dataModifica = a.get(taskit.dtModificaTaskIt);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,taskit)
					.where(taskit.dmalmTaskItPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmTaskIt.dmalmTaskIt.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				taskit = QDmalmTaskIt.dmalmTaskIt;
				new SQLUpdateClause(connection, dialect,taskit)
					.where(taskit.dmalmTaskItPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmTaskIt.dmalmTaskIt.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "anomalia_assistenza":
				query = new SQLQuery(connection, dialect);
				QDmalmAnomaliaAssistenza anomalia_assistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
				a = query.from(anomalia_assistenza)
						.where(anomalia_assistenza.dmalmAnomaliaAssPk.eq(tt.get(qTotal.dmalmPk)))
						.list(anomalia_assistenza.all()).get(0);
				tmp_dataModifica = a.get(anomalia_assistenza.dtModificaAnomaliaAss);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,anomalia_assistenza)
					.where(anomalia_assistenza.dmalmAnomaliaAssPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				anomalia_assistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
				new SQLUpdateClause(connection, dialect,anomalia_assistenza)
					.where(anomalia_assistenza.dmalmAnomaliaAssPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "release_it":
				query = new SQLQuery(connection, dialect);
				QDmalmReleaseIt release_it = QDmalmReleaseIt.dmalmReleaseIt;
				a = query.from(release_it)
						.where(release_it.dmalmReleaseItPk.eq(tt.get(qTotal.dmalmPk)))
						.list(release_it.all()).get(0);
				tmp_dataModifica = a.get(release_it.dtModificaReleaseIt);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,release_it)
					.where(release_it.dmalmReleaseItPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmReleaseIt.dmalmReleaseIt.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				release_it = QDmalmReleaseIt.dmalmReleaseIt;
				new SQLUpdateClause(connection, dialect,release_it)
					.where(release_it.dmalmReleaseItPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmReleaseIt.dmalmReleaseIt.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "sman":
				query = new SQLQuery(connection, dialect);
				QDmalmManutenzione sman = QDmalmManutenzione.dmalmManutenzione;
				a = query.from(sman)
						.where(sman.dmalmManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
						.list(sman.all()).get(0);
				tmp_dataModifica = a.get(sman.dtModificaManutenzione);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,sman)
					.where(sman.dmalmManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmManutenzione.dmalmManutenzione.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				sman = QDmalmManutenzione.dmalmManutenzione;
				new SQLUpdateClause(connection, dialect,sman)
					.where(sman.dmalmManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmManutenzione.dmalmManutenzione.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "release_ser":
				query = new SQLQuery(connection, dialect);
				QDmalmReleaseServizi releaseSer = QDmalmReleaseServizi.dmalmReleaseServizi;
				a = query.from(releaseSer)
						.where(releaseSer.dmalmRelServiziPk.eq(tt.get(qTotal.dmalmPk)))
						.list(releaseSer.all()).get(0);
				tmp_dataModifica = a.get(releaseSer.dtModificaRelServizi);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,releaseSer)
					.where(releaseSer.dmalmRelServiziPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmReleaseServizi.dmalmReleaseServizi.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				releaseSer = QDmalmReleaseServizi.dmalmReleaseServizi;
				new SQLUpdateClause(connection, dialect,releaseSer)
					.where(releaseSer.dmalmRelServiziPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmReleaseServizi.dmalmReleaseServizi.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "drqs":
				query = new SQLQuery(connection, dialect);
				QDmalmProgettoSviluppoDem drqs = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;
				a = query.from(drqs)
						.where(drqs.dmalmProgSvilDPk.eq(tt.get(qTotal.dmalmPk)))
						.list(drqs.all()).get(0);
				tmp_dataModifica = a.get(drqs.dtModificaProgSvilD);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,drqs)
					.where(drqs.dmalmProgSvilDPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				drqs = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;
				new SQLUpdateClause(connection, dialect,drqs)
					.where(drqs.dmalmProgSvilDPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "dman":
				query = new SQLQuery(connection, dialect);
				QDmalmRichiestaManutenzione dman = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
				a = query.from(dman)
						.where(dman.dmalmRichManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
						.list(dman.all()).get(0);
				tmp_dataModifica = a.get(dman.dtModificaRichManutenzione);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,dman)
					.where(dman.dmalmRichManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				dman = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
				new SQLUpdateClause(connection, dialect,dman)
					.where(dman.dmalmRichManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "rqd":
				query = new SQLQuery(connection, dialect);
				QDmalmProgettoDemand rqd = QDmalmProgettoDemand.dmalmProgettoDemand;
				a = query.from(rqd)
						.where(rqd.dmalmProgettoDemandPk.eq(tt.get(qTotal.dmalmPk)))
						.list(rqd.all()).get(0);
				tmp_dataModifica = a.get(rqd.dtModificaProgettoDemand);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,rqd)
					.where(rqd.dmalmProgettoDemandPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmProgettoDemand.dmalmProgettoDemand.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				rqd = QDmalmProgettoDemand.dmalmProgettoDemand;
				new SQLUpdateClause(connection, dialect,rqd)
					.where(rqd.dmalmProgettoDemandPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmProgettoDemand.dmalmProgettoDemand.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "richiesta_gestione":
				query = new SQLQuery(connection, dialect);
				QDmalmRichiestaGestione richiesta_gestione = QDmalmRichiestaGestione.dmalmRichiestaGestione;
				a = query.from(richiesta_gestione)
						.where(richiesta_gestione.dmalmRichiestaGestPk.eq(tt.get(qTotal.dmalmPk)))
						.list(richiesta_gestione.all()).get(0);
				tmp_dataModifica = a.get(richiesta_gestione.dtModificaRichiestaGest);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,richiesta_gestione)
					.where(richiesta_gestione.dmalmRichiestaGestPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmRichiestaGestione.dmalmRichiestaGestione.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				richiesta_gestione = QDmalmRichiestaGestione.dmalmRichiestaGestione;
				new SQLUpdateClause(connection, dialect,richiesta_gestione)
					.where(richiesta_gestione.dmalmRichiestaGestPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmRichiestaGestione.dmalmRichiestaGestione.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "srqs":
				query = new SQLQuery(connection, dialect);
				QDmalmProgettoSviluppoSvil srqs = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
				a = query.from(srqs)
						.where(srqs.dmalmProgSvilSPk.eq(tt.get(qTotal.dmalmPk)))
						.list(srqs.all()).get(0);
				tmp_dataModifica = a.get(srqs.dtModificaProgSvilS);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,srqs)
					.where(srqs.dmalmProgSvilSPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				srqs = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
				new SQLUpdateClause(connection, dialect,srqs)
					.where(srqs.dmalmProgSvilSPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "task":
				query = new SQLQuery(connection, dialect);
				QDmalmTask task = QDmalmTask.dmalmTask;
				a = query.from(task)
						.where(task.dmalmTaskPk.eq(tt.get(qTotal.dmalmPk)))
						.list(task.all()).get(0);
				tmp_dataModifica = a.get(task.dtModificaTask);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,task)
					.where(task.dmalmTaskPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmTask.dmalmTask.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				task = QDmalmTask.dmalmTask;
				new SQLUpdateClause(connection, dialect,task)
					.where(task.dmalmTaskPk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmTask.dmalmTask.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "classificatore_demand":
				query = new SQLQuery(connection, dialect);
				QDmalmClassificatore classificatore_demand = QDmalmClassificatore.dmalmClassificatore;
				a = query.from(classificatore_demand)
						.where(classificatore_demand.dmalmClassificatorePk.eq(tt.get(qTotal.dmalmPk)))
						.list(classificatore_demand.all()).get(0);
				tmp_dataModifica = a.get(classificatore_demand.dtModificaClassif);
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					new SQLUpdateClause(connection, dialect,classificatore_demand)
					.where(classificatore_demand.dmalmClassificatorePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmTask.dmalmTask.changed, help.get(type)).execute();
					continue;
				}
				dataModifica = tmp_dataModifica;
				classificatore_demand = QDmalmClassificatore.dmalmClassificatore;
				new SQLUpdateClause(connection, dialect,classificatore_demand)
					.where(classificatore_demand.dmalmClassificatorePk.eq(tt.get(qTotal.dmalmPk)))
					.set(QDmalmTask.dmalmTask.changed, value).execute();
				backValue = type;
				help.put(type, value);
				break;
				
			case "sup":
				
				query = new SQLQuery(connection, dialect);
				PreparedStatement ps = connection.prepareStatement("SELECT DATA_MODIFICA_RECORD FROM DMALM_RICHIESTA_SUPPORTO WHERE DMALM_RICH_SUPPORTO_PK= ?");
				ps.setInt(1, tt.get(qTotal.dmalmPk));
				ResultSet rs = ps.executeQuery();
				if(rs.next()) 
					tmp_dataModifica = rs.getDate("DATA_MODIFICA_RECORD");
				ps.close();
				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
					ps = connection.prepareStatement("UPDATE DMALM_RICHIESTA_SUPPORTO SET CHANGED= ? WHERE DMALM_RICH_SUPPORTO_PK= ?");
					ps.setString(1, help.get(type));
					ps.setInt(2, tt.get(qTotal.dmalmPk));
					ps.executeUpdate();
					ps.close();
					continue;
				}
				dataModifica = tmp_dataModifica;
				ps = connection.prepareStatement("UPDATE DMALM_RICHIESTA_SUPPORTO SET CHANGED= ? WHERE DMALM_RICH_SUPPORTO_PK= ?");
				ps.setString(1, value);
				ps.setInt(2, tt.get(qTotal.dmalmPk));
				ps.executeUpdate();
				ps.close();
				help.put(type, value);
				break;
			}
			}catch(IndexOutOfBoundsException e) {
				logger.error("workitem con ID:"+tt.get(qTotal.dmalmPk)+" sconosciuto");
			}
			/* questa parte dovrebbe essere la prossima versione ottimizzare la manutenzione del codice
			 * 
			 * 
			 * 
			String tabella = null, cond=null, data = null;
			switch(type) {
			case "sup":	tabella = DmAlmConstants.TARGET_RICHIESTA_SUPPORTO; cond = "DMALM_RICH_SUPPORTO_PK"; data = "DATA_MODIFICA_RECORD"; break;
			case "classificatore_demand": 
				tabella = QDmalmClassificatore.dmalmClassificatore.getTableName(); 
				cond = QDmalmClassificatore.dmalmClassificatore.dmalmClassificatorePk.toString();
				data = QDmalmClassificatore.dmalmClassificatore.dtModificaClassif.toString();
				break;   
			}
			PreparedStatement ps = connection.prepareStatement("SELECT "+data+" FROM" + tabella + " WHERE " + cond +"= ?");
			ps.setInt(1, tt.get(qTotal.dmalmPk));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				tmp_dataModifica = rs.getDate(data);
			}
			if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
				logger.debug("-----Dato di storicizzazione-----");
				logger.debug("-----Skip-----");
				continue;
			}
			dataModifica = tmp_dataModifica;
			ps = connection.prepareStatement("UPDATE " + tabella + " SET CHANGED = ? WHERE " + cond +"= ?");
			ps.setString(1, value);
			ps.setInt(2, tt.get(qTotal.dmalmPk));
			ps.executeUpdate();
			*/
		}
	}
	
	
	public static void setChangedFieldByRepo(List<Total> totals, String idRepo)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
//		String type = null;

		try {
			logger.info("START CheckChangingWorkitemFacade.execute()");

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			List<Tuple> cwihistory = null;
			List<Tuple> filteredcwhistory = null;
			connection.setAutoCommit(true);
			for (Total t : totals) {
				cwihistory = TotalDao.getHistorySingleChangedWI(t.getCodice(),
						idRepo);
				filteredcwhistory = TotalDao.filterChangedWiHisory(cwihistory);
				Collections.reverse(filteredcwhistory);
				updateChanging(filteredcwhistory, connection);
			}

			logger.info("STOP CheckChangingWorkitemFacade.execute()");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void execute() throws DAOException {
		List<Total> totalsire = TotalDao
				.changedWorkitemList(DmAlmConstants.REPOSITORY_SIRE);
		List<Total> totalsiss = TotalDao
				.changedWorkitemList(DmAlmConstants.REPOSITORY_SISS);

		setChangedFieldByRepo(totalsire, DmAlmConstants.REPOSITORY_SIRE);
		setChangedFieldByRepo(totalsiss, DmAlmConstants.REPOSITORY_SISS);
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		String sql;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			sql = QueryManager.getInstance().getQuery(
					DmAlmConstants.DMALM_CHANGED_WORKITEM);
			ps = connection.prepareStatement(sql);

			ps.executeUpdate();

			connection.commit();

			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

	}

//	private static String findType(String codice) throws DAOException {
//
//		String ret = null;
//		ConnectionManager cm = null;
//		Connection connection = null;
//		List<Tuple> t = new ArrayList<Tuple>();
//
//		try {
//			cm = ConnectionManager.getInstance();
//			connection = cm.getConnectionOracle();
//
//			SQLQuery query = new SQLQuery(connection, dialect);
//
//			t = query.from(qTotal).where(qTotal.codice.eq(codice))
//					.orderBy(qTotal.dtStoricizzazione.desc())
//					.list(qTotal.all());
//			
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			throw new DAOException(e);
//
//		} finally {
//			if (cm != null) {
//				cm.closeConnection(connection);
//			}
//		}
//
//		if (t.size() > 0) {
//			ret = t.get(0).get(qTotal.type);
//		}
//
//		return ret;
//	}
}
