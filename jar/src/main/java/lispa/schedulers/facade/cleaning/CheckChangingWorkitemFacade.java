package lispa.schedulers.facade.cleaning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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
	
	private static HashMap<Integer, List<Tuple>> getTableNames(List<Tuple> t, Connection connection) throws DAOException{
		
		SQLQuery query = null;
		HashMap<Integer, List<Tuple>> resultCombined = new HashMap<Integer, List<Tuple>>();
		List<Tuple> result = null;
		int i = 0;
		for(Tuple tt: t) {
			switch(tt.get(qTotal.type)) {
			case "anomalia":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmAnomaliaProdotto anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
				result= query.from(anomalia)
						.where(anomalia.cdAnomalia.like(tt.get(qTotal.codice)))
						.orderBy(anomalia.dtModificaRecordAnomalia.asc())
						.list(anomalia.all());
				break;
				
			case "documento":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmDocumento documento = QDmalmDocumento.dmalmDocumento;
				result= query.from(documento)
						.where(documento.cdDocumento.like(tt.get(qTotal.codice)))
						.orderBy(documento.dtModificaDocumento.asc())
						.list(documento.all());
				break;
				
			case "testcase":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmTestcase testcase = QDmalmTestcase.dmalmTestcase;
				result= query.from(testcase)
						.where(testcase.cdTestcase.like(tt.get(qTotal.codice)))
						.orderBy(testcase.dtModificaTestcase.asc())
						.list(testcase.all());
				break;
			
			case "pei":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmPei pei = QDmalmPei.dmalmPei;
				result= query.from(pei)
						.where(pei.cdPei.like(tt.get(qTotal.codice)))
						.orderBy(pei.dtModificaPei.asc())
						.list(pei.all());
				break;
				
			case "build":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmBuild build = QDmalmBuild.dmalmBuild;
				result= query.from(build)
						.where(build.cdBuild.like(tt.get(qTotal.codice)))
						.orderBy(build.dtModificaBuild.asc())
						.list(build.all());
				break;
			
			case "progettoese":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmProgettoEse progettoese = QDmalmProgettoEse.dmalmProgettoEse;
				result= query.from(progettoese)
						.where(progettoese.cdProgettoEse.like(tt.get(qTotal.codice)))
						.orderBy(progettoese.dtModificaProgettoEse.asc())
						.list(progettoese.all());
				break;
				
			case "fase":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmFase fase = QDmalmFase.dmalmFase;
				result= query.from(fase)
						.where(fase.cdFase.like(tt.get(qTotal.codice)))
						.orderBy(fase.dtModificaFase.asc())
						.list(fase.all());
				break;
				
			case "defect":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmDifettoProdotto DifettoProdotto = QDmalmDifettoProdotto.dmalmDifettoProdotto;
				result= query.from(DifettoProdotto)
						.where(DifettoProdotto.cdDifetto.like(tt.get(qTotal.codice)))
						.orderBy(DifettoProdotto.dtModificaRecordDifetto.asc())
						.list(DifettoProdotto.all());
				break;
				
			case "release":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmReleaseDiProgetto release = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
				result= query.from(release)
						.where(release.cdReleasediprog.like(tt.get(qTotal.codice)))
						.orderBy(release.dtModificaReleasediprog.asc())
						.list(release.all());
				break;
				
			case "sottoprogramma":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmSottoprogramma sottoprogramma = QDmalmSottoprogramma.dmalmSottoprogramma;
				result= query.from(sottoprogramma)
						.where(sottoprogramma.cdSottoprogramma.like(tt.get(qTotal.codice)))
						.orderBy(sottoprogramma.dtModificaSottoprogramma.asc())
						.list(sottoprogramma.all());
				break;
				
			case "programma":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmProgramma programma = QDmalmProgramma.dmalmProgramma;
				result= query.from(programma)
						.where(programma.cdProgramma.like(tt.get(qTotal.codice)))
						.orderBy(programma.dtModificaProgramma.asc())
						.list(programma.all());
				break;
				
			case "taskit":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmTaskIt taskit = QDmalmTaskIt.dmalmTaskIt;
				result= query.from(taskit)
						.where(taskit.cdTaskIt.like(tt.get(qTotal.codice)))
						.orderBy(taskit.dtModificaTaskIt.asc())
						.list(taskit.all());
				break;
				
			case "anomalia_assistenza":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmAnomaliaAssistenza anomalia_assistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
				result= query.from(anomalia_assistenza)
						.where(anomalia_assistenza.cdAnomaliaAss.like(tt.get(qTotal.codice)))
						.orderBy(anomalia_assistenza.dtModificaAnomaliaAss.asc())
						.list(anomalia_assistenza.all());
				break;
				
			case "release_it":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmReleaseIt release_it = QDmalmReleaseIt.dmalmReleaseIt;
				result= query.from(release_it)
						.where(release_it.cdReleaseIt.like(tt.get(qTotal.codice)))
						.orderBy(release_it.dtModificaReleaseIt.asc())
						.list(release_it.all());
				break;
				
			case "sman":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmManutenzione sman = QDmalmManutenzione.dmalmManutenzione;
				result= query.from(sman)
						.where(sman.cdManutenzione.like(tt.get(qTotal.codice)))
						.orderBy(sman.dtModificaManutenzione.asc())
						.list(sman.all());
				break;
				
			case "release_ser":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmReleaseServizi release_ser = QDmalmReleaseServizi.dmalmReleaseServizi;
				result= query.from(release_ser)
						.where(release_ser.cdRelServizi.like(tt.get(qTotal.codice)))
						.orderBy(release_ser.dtModificaRelServizi.asc())
						.list(release_ser.all());
				break;
				
			case "drqs":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmProgettoSviluppoDem drqs = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;
				result= query.from(drqs)
						.where(drqs.cdProgSvilD.like(tt.get(qTotal.codice)))
						.orderBy(drqs.dtModificaProgSvilD.asc())
						.list(drqs.all());
				break;
				
			case "dman":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmRichiestaManutenzione dman = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
				result= query.from(dman)
						.where(dman.cdRichiestaManutenzione.like(tt.get(qTotal.codice)))
						.orderBy(dman.dtModificaRichManutenzione.asc())
						.list(dman.all());
				break;
				
			case "rqd":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmProgettoDemand rqd = QDmalmProgettoDemand.dmalmProgettoDemand;
				result= query.from(rqd)
						.where(rqd.cdProgettoDemand.like(tt.get(qTotal.codice)))
						.orderBy(rqd.dtModificaProgettoDemand.asc())
						.list(rqd.all());
				break;
				
			case "richiesta_gestione":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmRichiestaGestione richiesta_gestione = QDmalmRichiestaGestione.dmalmRichiestaGestione;
				result= query.from(richiesta_gestione)
						.where(richiesta_gestione.cdRichiestaGest.like(tt.get(qTotal.codice)))
						.orderBy(richiesta_gestione.dtModificaRichiestaGest.asc())
						.list(richiesta_gestione.all());
				break;
				
			case "srqs":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmProgettoSviluppoSvil srqs = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
				result= query.from(srqs)
						.where(srqs.cdProgSvilS.like(tt.get(qTotal.codice)))
						.orderBy(srqs.dtModificaProgSvilS.asc())
						.list(srqs.all());
				break;
				
			case "task":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmTask task = QDmalmTask.dmalmTask;
				result= query.from(task)
						.where(task.cdTask.like(tt.get(qTotal.codice)))
						.orderBy(task.dtModificaTask.asc())
						.list(task.all());
				break;
				
			case "classificatore_demand":
				query = new SQLQuery(connection, dialect);
				result = new ArrayList<Tuple>();
				QDmalmClassificatore classificatore_demand = QDmalmClassificatore.dmalmClassificatore;
				result= query.from(classificatore_demand)
						.where(classificatore_demand.cd_classificatore.like(tt.get(qTotal.codice)))
						.orderBy(classificatore_demand.dtModificaClassif.asc())
						.list(classificatore_demand.all());
				break;
			}
			resultCombined.put(i, result);
			i++;
		}
		return resultCombined;
	}
	
	private static void updateValues(Connection connection, HashMap<Integer, List<Tuple>> hash) throws DAOException{
		
		for(int i=0; i<hash.keySet().size();i++) {
			List<Tuple> t = hash.get(i);
			for(Tuple tt: t) {
				String value = i==0?"null":tt.get(qTotal.type);
				switch(tt.get(qTotal.type)) {
				case "anomalia":
					QDmalmAnomaliaProdotto anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
					new SQLUpdateClause(connection, dialect,anomalia)
						.where(anomalia.dmalmAnomaliaProdottoPk.eq(tt.get(anomalia.dmalmAnomaliaProdottoPk)))
						.set(QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.changed, value);
					break;
					
				case "documento":
					QDmalmDocumento documento = QDmalmDocumento.dmalmDocumento;
					new SQLUpdateClause(connection, dialect, documento)
						.where(documento.dmalmDocumentoPk.eq(tt.get(documento.dmalmDocumentoPk)))
						.set(QDmalmDocumento.dmalmDocumento.changed, value);
					break;
					
				case "testcase":
					QDmalmTestcase testcase = QDmalmTestcase.dmalmTestcase;
					new SQLUpdateClause(connection, dialect, testcase)
						.where(testcase.dmalmTestcasePk.eq(tt.get(testcase.dmalmTestcasePk)))
						.set(QDmalmTestcase.dmalmTestcase.changed, value);
					break;
					
				case "pei":
					QDmalmPei pei = QDmalmPei.dmalmPei;
					new SQLUpdateClause(connection, dialect, pei)
						.where(pei.dmalmPeiPk.eq(tt.get(pei.dmalmPeiPk)))
						.set(QDmalmTestcase.dmalmTestcase.changed, value);
					break;
				
				case "build":
					QDmalmBuild build = QDmalmBuild.dmalmBuild;
					new SQLUpdateClause(connection, dialect, build)
						.where(build.dmalmBuildPk.eq(tt.get(build.dmalmBuildPk)))
						.set(QDmalmBuild.dmalmBuild.changed, value);
					break;
					
				case "progettoese":
					QDmalmProgettoEse progettoese = QDmalmProgettoEse.dmalmProgettoEse;
					new SQLUpdateClause(connection, dialect, progettoese)
						.where(progettoese.dmalmProgettoEsePk.eq(tt.get(progettoese.dmalmProgettoEsePk)))
						.set(QDmalmProgettoEse.dmalmProgettoEse.changed, value);
					break;
					
				case "fase":
					QDmalmFase fase = QDmalmFase.dmalmFase;
					new SQLUpdateClause(connection, dialect, fase)
						.where(fase.dmalmFasePk.eq(tt.get(fase.dmalmFasePk)))
						.set(QDmalmFase.dmalmFase.changed, value);
					break;
					
				case "defect":
					QDmalmDifettoProdotto defect = QDmalmDifettoProdotto.dmalmDifettoProdotto;
					new SQLUpdateClause(connection, dialect, defect)
						.where(defect.dmalmDifettoProdottoPk.eq(tt.get(defect.dmalmDifettoProdottoPk)))
						.set(QDmalmDifettoProdotto.dmalmDifettoProdotto.changed, value);
					break;
					
				case "release":
					QDmalmReleaseDiProgetto release = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
					new SQLUpdateClause(connection, dialect, release)
						.where(release.dmalmReleasediprogPk.eq(tt.get(release.dmalmReleasediprogPk)))
						.set(QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.changed, value);
					break;
					
				case "sottoprogramma":
					QDmalmSottoprogramma sottoprogramma = QDmalmSottoprogramma.dmalmSottoprogramma;
					new SQLUpdateClause(connection, dialect, sottoprogramma)
						.where(sottoprogramma.dmalmSottoprogrammaPk.eq(tt.get(sottoprogramma.dmalmSottoprogrammaPk)))
						.set(QDmalmSottoprogramma.dmalmSottoprogramma.changed, value);
					break;
					
				case "programma":
					QDmalmProgramma programma = QDmalmProgramma.dmalmProgramma;
					new SQLUpdateClause(connection, dialect, programma)
						.where(programma.dmalmProgrammaPk.eq(tt.get(programma.dmalmProgrammaPk)))
						.set(QDmalmProgramma.dmalmProgramma.changed, value);
					break;
					
				case "taskit":
					QDmalmTaskIt taskit = QDmalmTaskIt.dmalmTaskIt;
					new SQLUpdateClause(connection, dialect, taskit)
						.where(taskit.dmalmTaskItPk.eq(tt.get(taskit.dmalmTaskItPk)))
						.set(QDmalmTaskIt.dmalmTaskIt.changed, value);
					break;
					
				case "anomalia_assistenza":
					QDmalmAnomaliaAssistenza anomalia_assistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
					new SQLUpdateClause(connection, dialect, anomalia_assistenza)
						.where(anomalia_assistenza.dmalmAnomaliaAssPk.eq(tt.get(anomalia_assistenza.dmalmAnomaliaAssPk)))
						.set(QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.changed, value);
					break;
					
				case "release_it":
					QDmalmReleaseIt release_it = QDmalmReleaseIt.dmalmReleaseIt;
					new SQLUpdateClause(connection, dialect, release_it)
						.where(release_it.dmalmReleaseItPk.eq(tt.get(release_it.dmalmReleaseItPk)))
						.set(QDmalmReleaseIt.dmalmReleaseIt.changed, value);
					break;
					
				case "sman":
					QDmalmManutenzione sman = QDmalmManutenzione.dmalmManutenzione;
					new SQLUpdateClause(connection, dialect, sman)
						.where(sman.dmalmManutenzionePk.eq(tt.get(sman.dmalmManutenzionePk)))
						.set(QDmalmManutenzione.dmalmManutenzione.changed, value);
					break;
					
				case "release_ser":
					QDmalmReleaseServizi release_ser = QDmalmReleaseServizi.dmalmReleaseServizi;
					new SQLUpdateClause(connection, dialect, release_ser)
						.where(release_ser.dmalmRelServiziPk.eq(tt.get(release_ser.dmalmRelServiziPk)))
						.set(QDmalmReleaseServizi.dmalmReleaseServizi.changed, value);
					break;
					
				case "drqs":
					QDmalmProgettoSviluppoDem drqs = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;
					new SQLUpdateClause(connection, dialect, drqs)
						.where(drqs.dmalmProgSvilDPk.eq(tt.get(drqs.dmalmProgSvilDPk)))
						.set(QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.changed, value);
					break;
					
				case "dman":
					QDmalmRichiestaManutenzione dman = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
					new SQLUpdateClause(connection, dialect, dman)
						.where(dman.dmalmRichManutenzionePk.eq(tt.get(dman.dmalmRichManutenzionePk)))
						.set(QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.changed, value);
					break;
					
				case "rqd":
					QDmalmProgettoDemand rqd = QDmalmProgettoDemand.dmalmProgettoDemand;
					new SQLUpdateClause(connection, dialect, rqd)
						.where(rqd.dmalmProgettoDemandPk.eq(tt.get(rqd.dmalmProgettoDemandPk)))
						.set(QDmalmProgettoDemand.dmalmProgettoDemand.changed, value);
					break;
					
				case "richiesta_gestione":
					QDmalmRichiestaGestione richiesta_gestione = QDmalmRichiestaGestione.dmalmRichiestaGestione;
					new SQLUpdateClause(connection, dialect, richiesta_gestione)
						.where(richiesta_gestione.dmalmRichiestaGestPk.eq(tt.get(richiesta_gestione.dmalmRichiestaGestPk)))
						.set(QDmalmRichiestaGestione.dmalmRichiestaGestione.changed, value);
					break;
					
				case "srqs":
					QDmalmProgettoSviluppoSvil srqs = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
					new SQLUpdateClause(connection, dialect, srqs)
						.where(srqs.dmalmProgSvilSPk.eq(tt.get(srqs.dmalmProgSvilSPk)))
						.set(QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.changed, value);
					break;
					
				case "task":
					QDmalmTask task = QDmalmTask.dmalmTask;
					new SQLUpdateClause(connection, dialect, task)
						.where(task.dmalmTaskPk.eq(tt.get(task.dmalmTaskPk)))
						.set(QDmalmTask.dmalmTask.changed, value);
					break;
					
				case "classificatore_demand":
					QDmalmClassificatore classificatore_demand = QDmalmClassificatore.dmalmClassificatore;
					new SQLUpdateClause(connection, dialect, classificatore_demand)
						.where(classificatore_demand.dmalmClassificatorePk.eq(tt.get(classificatore_demand.dmalmClassificatorePk)))
						.set(QDmalmClassificatore.dmalmClassificatore.changed, value);
					break;
				}
			}
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
/*
 * algoritmo da fare:
 * step 1: trovare tutti i WI modificati modificare getHistorySingleChangedWI ----- (potrebbe andar bene)
 * step 2: trovare tutte tabelle impattate                                    -----
 * step 3: ordinare per data di modifica
 * step 4: valorizzare la colonna 'CHANGED'
 */
			for (Total t : totals) {
				cwihistory = TotalDao.getHistorySingleChangedWI(t.getCodice(),
						idRepo);
				filteredcwhistory = TotalDao.filterChangedWiHisory(cwihistory);
				HashMap<Integer, List<Tuple>> hash = getTableNames(filteredcwhistory, connection);
				updateValues(connection, hash);
//				for (Tuple tt : filteredcwhistory) {
//					switch (tt.get(qTotal.type)) {

//					case "anomalia":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("anomalia")) {
//							QDmalmAnomaliaProdotto anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
//							SQLQuery query = new SQLQuery(connection, dialect);
//							List<Tuple> strutture = new ArrayList<Tuple>();
//							
//							strutture = query
//									.from(anomalia)
//									.where(anomalia.cdAnomalia.like(tt.get(qTotal.codice)))
//									.orderBy(anomalia.dtModificaRecordAnomalia.asc())
//									.list(anomalia.all());
//							
//							for(Tuple ttt: strutture) {
//								String value = null;
//								new SQLUpdateClause(connection, dialect,anomalia)
//								.where(anomalia.dmalmAnomaliaProdottoPk.eq(ttt.get(anomalia.dmalmAnomaliaProdottoPk)))
//								.set(QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.changed, value);
//							}
//							
//							new SQLUpdateClause(
//									connection,
//									dialect,
//									QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto)
//									.where(QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.dmalmAnomaliaProdottoPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.changed,
//											type).execute();
						}
//						break;
//					case "documento":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("documento")) {
//
//							new SQLUpdateClause(connection, dialect,
//									QDmalmDocumento.dmalmDocumento)
//									.where(QDmalmDocumento.dmalmDocumento.dmalmDocumentoPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmDocumento.dmalmDocumento.changed,
//											type).execute();
//						}
//						break;

//					case "testcase":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("testcase")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmTestcase.dmalmTestcase)
//									.where(QDmalmTestcase.dmalmTestcase.dmalmTestcasePk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmTestcase.dmalmTestcase.changed,
//											type).execute();
//						}
//						break;
//					case "pei":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("pei")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmPei.dmalmPei)
//									.where(QDmalmPei.dmalmPei.dmalmPeiPk.eq(tt
//											.get(qTotal.dmalmPk)))
//									.set(QDmalmPei.dmalmPei.changed,
//											type).execute();
//						}
//						break;
//					case "build":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("build")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmBuild.dmalmBuild)
//									.where(QDmalmBuild.dmalmBuild.dmalmBuildPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmBuild.dmalmBuild.changed, type)
//									.execute();
//						}
//						break;
//					case "progettoese":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("progettoese")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmProgettoEse.dmalmProgettoEse)
//									.where(QDmalmProgettoEse.dmalmProgettoEse.dmalmProgettoEsePk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmProgettoEse.dmalmProgettoEse.changed,
//											type).execute();
//						}
//						break;
//					case "fase":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("fase")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmFase.dmalmFase)
//									.where(QDmalmFase.dmalmFase.dmalmFasePk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmFase.dmalmFase.changed, type)
//									.execute();
//						}
//						break;
//					case "defect":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("defect")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmDifettoProdotto.dmalmDifettoProdotto)
//									.where(QDmalmDifettoProdotto.dmalmDifettoProdotto.dmalmDifettoProdottoPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmDifettoProdotto.dmalmDifettoProdotto.changed,
//											type).execute();
//						}
//						break;
//					case "release":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("release")) {
//							new SQLUpdateClause(
//									connection,
//									dialect,
//									QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto)
//									.where(QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.dmalmReleasediprogPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.changed,
//											type).execute();
//						}
//						break;
//					case "sottoprogramma":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("sottoprogramma")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmSottoprogramma.dmalmSottoprogramma)
//									.where(QDmalmSottoprogramma.dmalmSottoprogramma.dmalmSottoprogrammaPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmSottoprogramma.dmalmSottoprogramma.changed,
//											type).execute();
//						}
//						break;
//					case "programma":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("programma")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmProgramma.dmalmProgramma)
//									.where(QDmalmProgramma.dmalmProgramma.dmalmProgrammaPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmProgramma.dmalmProgramma.changed,
//											type).execute();
//						}
//						break;
//					case "taskit":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("taskit")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmTaskIt.dmalmTaskIt)
//									.where(QDmalmTaskIt.dmalmTaskIt.dmalmTaskItPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmTaskIt.dmalmTaskIt.changed, type)
//									.execute();
//						}
//						break;
//					case "anomalia_assistenza":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("anomalia_assistenza")) {
//							new SQLUpdateClause(
//									connection,
//									dialect,
//									QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza)
//									.where(QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.dmalmAnomaliaAssPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.changed,
//											type).execute();
//						}
//						break;
//					case "release_it":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("release_it")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmReleaseIt.dmalmReleaseIt)
//									.where(QDmalmReleaseIt.dmalmReleaseIt.dmalmReleaseItPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmReleaseIt.dmalmReleaseIt.changed,
//											type).execute();
//						}
//						break;
//					case "sman":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("sman")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmManutenzione.dmalmManutenzione)
//									.where(QDmalmManutenzione.dmalmManutenzione.dmalmManutenzionePk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmManutenzione.dmalmManutenzione.changed,
//											type).execute();
//						}
//						break;
//
//					case "release_ser":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("release_ser")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmReleaseServizi.dmalmReleaseServizi)
//									.where(QDmalmReleaseServizi.dmalmReleaseServizi.dmalmRelServiziPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmReleaseServizi.dmalmReleaseServizi.changed,
//											type).execute();
//						}
//						break;
//
//					case "drqs":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("drqs")) {
//							new SQLUpdateClause(
//									connection,
//									dialect,
//									QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem)
//									.where(QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.dmalmProgSvilDPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.changed,
//											type).execute();
//						}
//						break;
//
//					case "dman":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("dman")) {
//							new SQLUpdateClause(
//									connection,
//									dialect,
//									QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione)
//									.where(QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.dmalmRichManutenzionePk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.changed,
//											type).execute();
//						}
//						break;
//
//					case "rqd":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("rqd")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmProgettoDemand.dmalmProgettoDemand)
//									.where(QDmalmProgettoDemand.dmalmProgettoDemand.dmalmProgettoDemandPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmProgettoDemand.dmalmProgettoDemand.changed,
//											type).execute();
//						}
//						break;
//
//					case "richiesta_gestione":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("richiesta_gestione")) {
//							new SQLUpdateClause(
//									connection,
//									dialect,
//									QDmalmRichiestaGestione.dmalmRichiestaGestione)
//									.where(QDmalmRichiestaGestione.dmalmRichiestaGestione.dmalmRichiestaGestPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmRichiestaGestione.dmalmRichiestaGestione.changed,
//											type).execute();
//						}
//						break;
//
//					case "srqs":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("srqs")) {
//							new SQLUpdateClause(
//									connection,
//									dialect,
//									QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil)
//									.where(QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.dmalmProgSvilSPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.changed,
//											type).execute();
//						}
//						break;
//
//					case "task":
//
//						type = findType(t.getCodice());
//						if (type != null && !type.equals("task")) {
//							new SQLUpdateClause(connection, dialect,
//									QDmalmTask.dmalmTask)
//									.where(QDmalmTask.dmalmTask.dmalmTaskPk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmTask.dmalmTask.changed, type)
//									.execute();
//						}
//						break;
//
//					case "classificatore_demand":
//
//						type = findType(t.getCodice());
//						if (type != null
//								&& !type.equals("classificatore_demand")) {
//
//							new SQLUpdateClause(
//									connection,
//									dialect,
//									QDmalmClassificatore.dmalmClassificatore)
//									.where(QDmalmClassificatore.dmalmClassificatore.dmalmClassificatorePk
//											.eq(tt.get(qTotal.dmalmPk)))
//									.set(QDmalmClassificatore.dmalmClassificatore.changed,
//											type).execute();
//						}
//						break;

//					}
//				}
//			}

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

	private static String findType(String codice) throws DAOException {

		String ret = null;
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> t = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			t = query.from(qTotal).where(qTotal.codice.eq(codice))
					.orderBy(qTotal.dtStoricizzazione.desc())
					.list(qTotal.all());
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (t.size() > 0) {
			ret = t.get(0).get(qTotal.type);
		}

		return ret;
	}

}
