package lispa.schedulers.facade.cleaning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.queryimplementation.target.QTotal;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaAssistenza;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdotto;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdottoDummy;
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

public class WorkItemChangingThread extends Thread {

	private List<Tuple> t;
	private Connection connection;
	private static QTotal qTotal = QTotal.total;
	private static Logger logger = Logger
			.getLogger(CheckChangingWorkitemFacade.class);
	private TreeMap<Date, String> map;
	private String codice;
	
	
	public WorkItemChangingThread(List<Tuple> t, Connection connection, String codice) {
		this.t = t;
		this.connection = connection;
		this.codice = codice;
	}
	
	private void chechDate() throws SQLException {
		
		map = new TreeMap<Date, String>();
		Date dt;
		String dataModifica = null, tabella = null, cCodice = null;
		boolean dummyCheck = false;
		for(Tuple tt: t) {
			String type = tt.get(qTotal.type);
			if(map.containsValue(type))
				continue;
			switch(type) {
			case "anomalia":
				tabella = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.getTableName();
				cCodice = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.cdAnomalia.toString();
				dataModifica = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.dtModificaRecordAnomalia.toString();
				dummyCheck = true;
				break;
			case "defect":
				tabella = QDmalmDifettoProdotto.dmalmDifettoProdotto.getTableName();
				cCodice = QDmalmDifettoProdotto.dmalmDifettoProdotto.cdDifetto.toString();
				dataModifica = QDmalmDifettoProdotto.dmalmDifettoProdotto.dtModificaRecordDifetto.toString();
				dummyCheck = true;
				break;
			case "documento":
				tabella = QDmalmDocumento.dmalmDocumento.getTableName();
				cCodice = QDmalmDocumento.dmalmDocumento.cdDocumento.toString();
				dataModifica = QDmalmDocumento.dmalmDocumento.dtModificaDocumento.toString();
				break;
			case "testcase":
				tabella = QDmalmTestcase.dmalmTestcase.getTableName();
				cCodice = QDmalmTestcase.dmalmTestcase.cdTestcase.toString();
				dataModifica = QDmalmTestcase.dmalmTestcase.dtModificaTestcase.toString();
				break;
			case "pei":
				tabella = QDmalmPei.dmalmPei.getTableName();
				cCodice = QDmalmPei.dmalmPei.cdPei.toString();
				dataModifica = QDmalmPei.dmalmPei.dtModificaPei.toString();
				break;
			case "build":
				tabella = QDmalmBuild.dmalmBuild.getTableName();
				cCodice = QDmalmBuild.dmalmBuild.cdBuild.toString();
				dataModifica = QDmalmBuild.dmalmBuild.dtModificaBuild.toString();
				break;
			case "progettoese":
				tabella = QDmalmProgettoEse.dmalmProgettoEse.getTableName();
				cCodice = QDmalmProgettoEse.dmalmProgettoEse.cdProgettoEse.toString();
				dataModifica = QDmalmProgettoEse.dmalmProgettoEse.dtModificaProgettoEse.toString();
				break;
			case "fase":
				tabella = QDmalmFase.dmalmFase.getTableName();
				cCodice = QDmalmFase.dmalmFase.cdFase.toString();
				dataModifica = QDmalmFase.dmalmFase.dtModificaFase.toString();
				break;
			case "release":
				tabella = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.getTableName();
				cCodice = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.cdReleasediprog.toString();
				dataModifica = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.dtModificaReleasediprog.toString();
				break;
			case "sottoprogramma":
				tabella = QDmalmSottoprogramma.dmalmSottoprogramma.getTableName();
				cCodice = QDmalmSottoprogramma.dmalmSottoprogramma.cdSottoprogramma.toString();
				dataModifica = QDmalmSottoprogramma.dmalmSottoprogramma.dtModificaSottoprogramma.toString();
				break;
			case "programma":
				tabella = QDmalmProgramma.dmalmProgramma.getTableName();
				cCodice = QDmalmProgramma.dmalmProgramma.cdProgramma.toString();
				dataModifica = QDmalmProgramma.dmalmProgramma.dtModificaProgramma.toString();
				break;
			case "taskit":
				tabella = QDmalmTaskIt.dmalmTaskIt.getTableName();
				cCodice = QDmalmTaskIt.dmalmTaskIt.cdTaskIt.toString();
				dataModifica = QDmalmTaskIt.dmalmTaskIt.dtModificaTaskIt.toString();
				break;
			case "anomalia_assistenza":
				tabella = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.getTableName();
				cCodice = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.cdAnomaliaAss.toString();
				dataModifica = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.dtModificaAnomaliaAss.toString();
				break;
			case "release_it":
				tabella = QDmalmReleaseIt.dmalmReleaseIt.getTableName();
				cCodice = QDmalmReleaseIt.dmalmReleaseIt.cdReleaseIt.toString();
				dataModifica = QDmalmReleaseIt.dmalmReleaseIt.dtModificaReleaseIt.toString();
				break;
			case "sman":
				tabella = QDmalmManutenzione.dmalmManutenzione.getTableName();
				cCodice = QDmalmManutenzione.dmalmManutenzione.cdManutenzione.toString();
				dataModifica = QDmalmManutenzione.dmalmManutenzione.dtModificaManutenzione.toString();
				break;
			case "release_ser":
				tabella = QDmalmReleaseServizi.dmalmReleaseServizi.getTableName();
				cCodice = QDmalmReleaseServizi.dmalmReleaseServizi.cdRelServizi.toString();
				dataModifica = QDmalmReleaseServizi.dmalmReleaseServizi.dtModificaRelServizi.toString();
				break;
			case "drqs":
				tabella = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.getTableName();
				cCodice = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.cdProgSvilD.toString();
				dataModifica = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.dtModificaProgSvilD.toString();
				break;
			case "dman":
				tabella = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.getTableName();
				cCodice = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.cdRichiestaManutenzione.toString();
				dataModifica = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.dtModificaRichManutenzione.toString();
				break;
			case "rqd":
				tabella = QDmalmProgettoDemand.dmalmProgettoDemand.getTableName();
				cCodice = QDmalmProgettoDemand.dmalmProgettoDemand.cdProgettoDemand.toString();
				dataModifica = QDmalmProgettoDemand.dmalmProgettoDemand.dtModificaProgettoDemand.toString();
				break;
			case "richiesta_gestione":
				tabella = QDmalmRichiestaGestione.dmalmRichiestaGestione.getTableName();
				cCodice = QDmalmRichiestaGestione.dmalmRichiestaGestione.cdRichiestaGest.toString();
				dataModifica = QDmalmRichiestaGestione.dmalmRichiestaGestione.dtModificaRichiestaGest.toString();
				break;
			case "srqs":
				tabella = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.getTableName();
				cCodice = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.cdProgSvilS.toString();
				dataModifica = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.dtModificaProgSvilS.toString();
				break;
			case "task":
				tabella = QDmalmTask.dmalmTask.getTableName();
				cCodice = QDmalmTask.dmalmTask.cdTask.toString();
				dataModifica = QDmalmTask.dmalmTask.dtModificaTask.toString();
				break;
			case "classificatore_demand":
			case "classificatore":
				tabella = QDmalmClassificatore.dmalmClassificatore.getTableName();
				cCodice = QDmalmClassificatore.dmalmClassificatore.cd_classificatore.toString();
				dataModifica = QDmalmClassificatore.dmalmClassificatore.dtModificaClassif.toString();
				break;
			case "sup": 
				tabella = "DMALM_RICHIESTA_SUPPORTO";
				cCodice = "CD_RICHIESTA_SUPPORTO";
				dataModifica = "DATA_MODIFICA_RECORD";
				break;
			}
			if(isAllreadyChanged(tabella, cCodice)) {
				map = null;
				return;
			}
			dt = getDate(dataModifica, tabella, cCodice);
			map.put(dt, type);
		}
		if(dummyCheck) {
			dt = getDate(QDmalmAnomaliaProdottoDummy.dmalmAnomaliaProdottoDummy.dtModificaRecordAnomalia.toString(), 
					QDmalmAnomaliaProdottoDummy.dmalmAnomaliaProdottoDummy.getTableName(), 
					QDmalmAnomaliaProdottoDummy.dmalmAnomaliaProdottoDummy.cdAnomalia.toString());
			if(dt!=null)
				map.put(dt, "anomalia_dummy");
			dt = getDate("DT_MODIFICA_RECORD_DIFETTO", "DMALM_DIFETTO_PRODOTTO_DUMMY", "CD_DIFETTO");
			if(dt!=null)
				map.put(dt, "defect_dummy");
		}
	}
	
	private boolean isAllreadyChanged(String tabella, String cCodice) throws SQLException {
		
		String changed = null;
		PreparedStatement ps = connection.prepareStatement("SELECT distinct CHANGED FROM "+tabella+" WHERE "+cCodice+" like ?");
		ps.setString(1, codice);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) 
			changed = rs.getString("CHANGED");
		rs.close();
		ps.close();
		return changed==null;
	}
	
	private Date getDate(String dataModifica, String tabella, String cCodice) throws SQLException {
		Date dt = null;
		PreparedStatement ps = connection.prepareStatement("SELECT max("+dataModifica+") as dataModifica FROM "+tabella+" WHERE "+cCodice+" like ?");
		ps.setString(1, codice);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) 
			dt = rs.getDate("dataModifica");
		rs.close();
		ps.close();
		return dt;
	}
	
	private void update() throws SQLException {
	
		String value = null, tabella = null, cCodice = null;
		if(map==null)
			return;
		Map<Date, String> reverseMap = map.descendingMap();
		Iterator<Map.Entry<Date, String>> iterator = reverseMap.entrySet().iterator();
		Map.Entry<Date, String> entry = null;
		while(iterator.hasNext()){
		    entry = iterator.next();
		    switch(entry.getValue()) {
			case "anomalia":
				tabella = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.getTableName();
				cCodice = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.cdAnomalia.toString();
				break;
			case "anomalia_dummy":
				tabella = QDmalmAnomaliaProdottoDummy.dmalmAnomaliaProdottoDummy.getTableName();
				cCodice = QDmalmAnomaliaProdottoDummy.dmalmAnomaliaProdottoDummy.cdAnomalia.toString();
				if(value == null)
					updateValues(tabella, cCodice, value);
				else
					updateValues(tabella, cCodice, value.contentEquals("anomalia")? null:value);
				value = "anomalia";
				continue;
			case "defect":
				tabella = QDmalmDifettoProdotto.dmalmDifettoProdotto.getTableName();
				cCodice = QDmalmDifettoProdotto.dmalmDifettoProdotto.cdDifetto.toString();
				break;
			case "defect_dummy":
				tabella = "DMALM_DIFETTO_PRODOTTO_DUMMY";
				cCodice = "CD_DIFETTO";
				if(value == null)
					updateValues(tabella, cCodice, value);
				else
					updateValues(tabella, cCodice, value.contentEquals("defect")? null:value);
				value = "defect";
				continue;
			case "documento":
				tabella = QDmalmDocumento.dmalmDocumento.getTableName();
				cCodice = QDmalmDocumento.dmalmDocumento.cdDocumento.toString();
				break;
			case "testcase":
				tabella = QDmalmTestcase.dmalmTestcase.getTableName();
				cCodice = QDmalmTestcase.dmalmTestcase.cdTestcase.toString();
				break;
			case "pei":
				tabella = QDmalmPei.dmalmPei.getTableName();
				cCodice = QDmalmPei.dmalmPei.cdPei.toString();
				break;
			case "build":
				tabella = QDmalmBuild.dmalmBuild.getTableName();
				cCodice = QDmalmBuild.dmalmBuild.cdBuild.toString();
				break;
			case "progettoese":
				tabella = QDmalmProgettoEse.dmalmProgettoEse.getTableName();
				cCodice = QDmalmProgettoEse.dmalmProgettoEse.cdProgettoEse.toString();
				break;
			case "fase":
				tabella = QDmalmFase.dmalmFase.getTableName();
				cCodice = QDmalmFase.dmalmFase.cdFase.toString();
				break;
			case "release":
				tabella = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.getTableName();
				cCodice = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.cdReleasediprog.toString();
				break;	
			case "sottoprogramma":
				tabella = QDmalmSottoprogramma.dmalmSottoprogramma.getTableName();
				cCodice = QDmalmSottoprogramma.dmalmSottoprogramma.cdSottoprogramma.toString();
				break;
			case "programma":
				tabella = QDmalmProgramma.dmalmProgramma.getTableName();
				cCodice = QDmalmProgramma.dmalmProgramma.cdProgramma.toString();
				break;
			case "taskit":
				tabella = QDmalmTaskIt.dmalmTaskIt.getTableName();
				cCodice = QDmalmTaskIt.dmalmTaskIt.cdTaskIt.toString();
				break;
			case "anomalia_assistenza":
				tabella = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.getTableName();
				cCodice = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.cdAnomaliaAss.toString();
				break;
			case "release_it":
				tabella = QDmalmReleaseIt.dmalmReleaseIt.getTableName();
				cCodice = QDmalmReleaseIt.dmalmReleaseIt.cdReleaseIt.toString();
				break;
			case "sman":
				tabella = QDmalmManutenzione.dmalmManutenzione.getTableName();
				cCodice = QDmalmManutenzione.dmalmManutenzione.cdManutenzione.toString();
				break;
			case "release_ser":
				tabella = QDmalmReleaseServizi.dmalmReleaseServizi.getTableName();
				cCodice = QDmalmReleaseServizi.dmalmReleaseServizi.cdRelServizi.toString();
				break;
			case "drqs":
				tabella = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.getTableName();
				cCodice = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.cdProgSvilD.toString();
				break;
			case "dman":
				tabella = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.getTableName();
				cCodice = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.cdRichiestaManutenzione.toString();
				break;
			case "rqd":
				tabella = QDmalmProgettoDemand.dmalmProgettoDemand.getTableName();
				cCodice = QDmalmProgettoDemand.dmalmProgettoDemand.cdProgettoDemand.toString();
				break;
			case "richiesta_gestione":
				tabella = QDmalmRichiestaGestione.dmalmRichiestaGestione.getTableName();
				cCodice = QDmalmRichiestaGestione.dmalmRichiestaGestione.cdRichiestaGest.toString();
				break;
			case "srqs":
				tabella = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.getTableName();
				cCodice = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.cdProgSvilS.toString();
				break;
			case "task":
				tabella = QDmalmTask.dmalmTask.getTableName();
				cCodice = QDmalmTask.dmalmTask.cdTask.toString();
				break;
			case "classificatore":
			case "classificatore_demand":
				tabella = QDmalmClassificatore.dmalmClassificatore.getTableName();
				cCodice = QDmalmClassificatore.dmalmClassificatore.cd_classificatore.toString();
				break;
			case "sup": 
				tabella = "DMALM_RICHIESTA_SUPPORTO";
				cCodice = "CD_RICHIESTA_SUPPORTO";
				break;
			}
			updateValues(tabella, cCodice, value);
			value = entry.getValue();
		}
	}
	
	private void updateValues(String tabella, String cCodice, String value) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE "+tabella+" SET CHANGED= ? WHERE "+cCodice+"= ?");
		ps.setString(1, value);
		ps.setString(2, codice);
		ps.executeUpdate();
		ps.close();
	}
	
	@Override
	public void run() {

		try {
			chechDate();
			update();
		} catch (SQLException e) {
			logger.error("workitem id:"+codice+"   con errore:" +e.getMessage());
		}
//		SQLQuery query = null;
//		String value = "";
//		String backValue = "";
//		Date dataModifica = null;
//		List<Tuple> dummyList = null;
//		Tuple a;
//		HashMap<String, String> help = new HashMap<String, String>();
//		for(Tuple tt: t) {
//			String type = tt.get(qTotal.type);
////			if (changed==null)
////				return;
//			if(!backValue.equalsIgnoreCase(type))
//				value = backValue;
//			Date tmp_dataModifica = null;
//			try {
//			switch(type) {
//			case "anomalia":
//				query = new SQLQuery(connection, dialect);
//				QDmalmAnomaliaProdotto anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
//				anomalia.dtModificaRecordAnomalia.toString();
//				dummyList = query.from(anomalia)
//						.where(anomalia.dmalmAnomaliaProdottoPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(anomalia.all());
//				if(dummyList.size()==0) {
//					QDmalmAnomaliaProdottoDummy anomaliaDummy = QDmalmAnomaliaProdottoDummy.dmalmAnomaliaProdottoDummy;
//					a = query.from(anomaliaDummy)
//							.where(anomaliaDummy.dmalmAnomaliaProdottoPk.eq(tt.get(qTotal.dmalmPk)))
//							.list(anomaliaDummy.all()).get(0);
//					tmp_dataModifica = a.get(anomaliaDummy.dtModificaRecordAnomalia);
//					if(help.get(type)!=null && dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
//						new SQLUpdateClause(connection, dialect,anomaliaDummy)
//						.where(anomaliaDummy.dmalmAnomaliaProdottoPk.eq(tt.get(qTotal.dmalmPk)))
//						.set(QDmalmAnomaliaProdottoDummy.dmalmAnomaliaProdottoDummy.changed, help.get(type)).execute();
//						continue;
//					}
//					dataModifica = tmp_dataModifica;
//					anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
//					new SQLUpdateClause(connection, dialect,anomalia)
//						.where(anomalia.dmalmAnomaliaProdottoPk.eq(tt.get(qTotal.dmalmPk)))
//						.set(QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.changed, value).execute();
//					backValue = type;
//					help.put(type, value);
//					continue;
//				}
//				else 
//					a=dummyList.get(0);
//				tmp_dataModifica = a.get(anomalia.dtModificaRecordAnomalia);
//				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
//					new SQLUpdateClause(connection, dialect,anomalia)
//					.where(anomalia.dmalmAnomaliaProdottoPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
//				new SQLUpdateClause(connection, dialect,anomalia)
//					.where(anomalia.dmalmAnomaliaProdottoPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "documento":
//				query = new SQLQuery(connection, dialect);
//				QDmalmDocumento documento = QDmalmDocumento.dmalmDocumento;
//				a = query.from(documento)
//						.where(documento.dmalmDocumentoPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(documento.all()).get(0);
//				tmp_dataModifica = a.get(documento.dtModificaDocumento);
//				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
//					new SQLUpdateClause(connection, dialect,documento)
//					.where(documento.dmalmDocumentoPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmDocumento.dmalmDocumento.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				documento = QDmalmDocumento.dmalmDocumento;
//				new SQLUpdateClause(connection, dialect,documento)
//					.where(documento.dmalmDocumentoPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmDocumento.dmalmDocumento.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "testcase": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmTestcase testcase = QDmalmTestcase.dmalmTestcase;
//				a = query.from(testcase)
//						.where(testcase.dmalmTestcasePk.eq(tt.get(qTotal.dmalmPk)))
//						.list(testcase.all()).get(0);
//				tmp_dataModifica = a.get(testcase.dtModificaTestcase);
//				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
//					new SQLUpdateClause(connection, dialect,testcase)
//					.where(testcase.dmalmTestcasePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmTestcase.dmalmTestcase.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				testcase = QDmalmTestcase.dmalmTestcase;
//				new SQLUpdateClause(connection, dialect,testcase)
//					.where(testcase.dmalmTestcasePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmTestcase.dmalmTestcase.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//			
//			case "pei": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmPei pei = QDmalmPei.dmalmPei;
//				a = query.from(pei)
//						.where(pei.dmalmPeiPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(pei.all()).get(0);
//				tmp_dataModifica = a.get(pei.dtModificaPei);
//				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
//					new SQLUpdateClause(connection, dialect,pei)
//					.where(pei.dmalmPeiPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmPei.dmalmPei.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				pei = QDmalmPei.dmalmPei;
//				new SQLUpdateClause(connection, dialect,pei)
//					.where(pei.dmalmPeiPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmPei.dmalmPei.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "build": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmBuild build = QDmalmBuild.dmalmBuild;
//				a = query.from(build)
//						.where(build.dmalmBuildPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(build.all()).get(0);
//				tmp_dataModifica = a.get(build.dtModificaBuild);
//				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
//					new SQLUpdateClause(connection, dialect,build)
//					.where(build.dmalmBuildPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmBuild.dmalmBuild.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				build = QDmalmBuild.dmalmBuild;
//				new SQLUpdateClause(connection, dialect,build)
//					.where(build.dmalmBuildPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmBuild.dmalmBuild.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//			
//			case "progettoese": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmProgettoEse progettoese = QDmalmProgettoEse.dmalmProgettoEse;
//				a = query.from(progettoese)
//						.where(progettoese.dmalmProgettoEsePk.eq(tt.get(qTotal.dmalmPk)))
//						.list(progettoese.all()).get(0);
//				tmp_dataModifica = a.get(progettoese.dtModificaProgettoEse);
//				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
//					new SQLUpdateClause(connection, dialect,progettoese)
//					.where(progettoese.dmalmProgettoEsePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmProgettoEse.dmalmProgettoEse.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				progettoese = QDmalmProgettoEse.dmalmProgettoEse;
//				new SQLUpdateClause(connection, dialect,progettoese)
//					.where(progettoese.dmalmProgettoEsePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmProgettoEse.dmalmProgettoEse.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "fase": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmFase fase = QDmalmFase.dmalmFase;
//				a = query.from(fase)
//						.where(fase.dmalmFasePk.eq(tt.get(qTotal.dmalmPk)))
//						.list(fase.all()).get(0);
//				tmp_dataModifica = a.get(fase.dtModificaFase);
//				if(dataModifica!=null && (tmp_dataModifica.compareTo(dataModifica)<=0)) {
//					new SQLUpdateClause(connection, dialect,fase)
//					.where(fase.dmalmFasePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmFase.dmalmFase.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				fase = QDmalmFase.dmalmFase;
//				new SQLUpdateClause(connection, dialect,fase)
//					.where(fase.dmalmFasePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmFase.dmalmFase.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "defect":
//				query = new SQLQuery(connection, dialect);
//				QDmalmDifettoProdotto DifettoProdotto = QDmalmDifettoProdotto.dmalmDifettoProdotto;
//				dummyList = query.from(DifettoProdotto)
//						.where(DifettoProdotto.dmalmDifettoProdottoPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(DifettoProdotto.all());
//				if(dummyList.size()==0) {
//					query = new SQLQuery(connection, dialect);
//					PreparedStatement ps = connection.prepareStatement("SELECT DT_MODIFICA_RECORD_DIFETTO FROM DMALM_DIFETTO_PRODOTTO_DUMMY WHERE DMALM_DIF_PROD_DUMMY_PK= ?");
//					ps.setInt(1, tt.get(qTotal.dmalmPk));
//					ResultSet rs = ps.executeQuery();
//					if(rs.next()) 
//						tmp_dataModifica = rs.getDate("DT_MODIFICA_RECORD_DIFETTO");
//					ps.close();
//					if(help.get(type)!=null && dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//						ps = connection.prepareStatement("UPDATE DMALM_DIFETTO_PRODOTTO_DUMMY SET CHANGED= ? WHERE DMALM_DIF_PROD_DUMMY_PK= ?");
//						ps.setString(1, help.get(type));
//						ps.setInt(2, tt.get(qTotal.dmalmPk));
//						ps.executeUpdate();
//						ps.close();
//						continue;
//					}
//					dataModifica = tmp_dataModifica;
//					PreparedStatement ps1 = connection.prepareStatement("UPDATE DMALM_DIFETTO_PRODOTTO_DUMMY SET CHANGED= ? WHERE DMALM_DIF_PROD_DUMMY_PK= ?");
//					ps1.setString(1, value);
//					ps1.setInt(2, tt.get(qTotal.dmalmPk));
//					ps1.executeUpdate();
//					ps1.close();
//					help.put(type, value);
//					continue;
//				}
//				else
//					a = dummyList.get(0);
//				tmp_dataModifica = a.get(DifettoProdotto.dtModificaRecordDifetto);
//				if(help.get(type)!=null && dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,DifettoProdotto)
//					.where(DifettoProdotto.dmalmDifettoProdottoPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmDifettoProdotto.dmalmDifettoProdotto.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				DifettoProdotto = QDmalmDifettoProdotto.dmalmDifettoProdotto;
//				new SQLUpdateClause(connection, dialect,DifettoProdotto)
//					.where(DifettoProdotto.dmalmDifettoProdottoPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmDifettoProdotto.dmalmDifettoProdotto.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "release": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmReleaseDiProgetto release = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
//				a = query.from(release)
//						.where(release.dmalmReleasediprogPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(release.all()).get(0);
//				tmp_dataModifica = a.get(release.dtModificaReleasediprog);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,release)
//					.where(release.dmalmReleasediprogPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				release = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
//				new SQLUpdateClause(connection, dialect,release)
//					.where(release.dmalmReleasediprogPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "sottoprogramma":
//				query = new SQLQuery(connection, dialect);
//				QDmalmSottoprogramma sottoprogramma = QDmalmSottoprogramma.dmalmSottoprogramma;
//				a = query.from(sottoprogramma)
//						.where(sottoprogramma.dmalmSottoprogrammaPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(sottoprogramma.all()).get(0);
//				tmp_dataModifica = a.get(sottoprogramma.dtModificaSottoprogramma);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,sottoprogramma)
//					.where(sottoprogramma.dmalmSottoprogrammaPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmSottoprogramma.dmalmSottoprogramma.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				sottoprogramma = QDmalmSottoprogramma.dmalmSottoprogramma;
//				new SQLUpdateClause(connection, dialect,sottoprogramma)
//					.where(sottoprogramma.dmalmSottoprogrammaPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmSottoprogramma.dmalmSottoprogramma.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "programma":
//				query = new SQLQuery(connection, dialect);
//				QDmalmProgramma programma = QDmalmProgramma.dmalmProgramma;
//				a = query.from(programma)
//						.where(programma.dmalmProgrammaPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(programma.all()).get(0);
//				tmp_dataModifica = a.get(programma.dtModificaProgramma);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,programma)
//					.where(programma.dmalmProgrammaPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmProgramma.dmalmProgramma.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				programma = QDmalmProgramma.dmalmProgramma;
//				new SQLUpdateClause(connection, dialect,programma)
//					.where(programma.dmalmProgrammaPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmProgramma.dmalmProgramma.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "taskit": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmTaskIt taskit = QDmalmTaskIt.dmalmTaskIt;
//				a = query.from(taskit)
//						.where(taskit.dmalmTaskItPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(taskit.all()).get(0);
//				tmp_dataModifica = a.get(taskit.dtModificaTaskIt);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,taskit)
//					.where(taskit.dmalmTaskItPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmTaskIt.dmalmTaskIt.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				taskit = QDmalmTaskIt.dmalmTaskIt;
//				new SQLUpdateClause(connection, dialect,taskit)
//					.where(taskit.dmalmTaskItPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmTaskIt.dmalmTaskIt.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "anomalia_assistenza": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmAnomaliaAssistenza anomalia_assistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
//				a = query.from(anomalia_assistenza)
//						.where(anomalia_assistenza.dmalmAnomaliaAssPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(anomalia_assistenza.all()).get(0);
//				tmp_dataModifica = a.get(anomalia_assistenza.dtModificaAnomaliaAss);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,anomalia_assistenza)
//					.where(anomalia_assistenza.dmalmAnomaliaAssPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				anomalia_assistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
//				new SQLUpdateClause(connection, dialect,anomalia_assistenza)
//					.where(anomalia_assistenza.dmalmAnomaliaAssPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "release_it": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmReleaseIt release_it = QDmalmReleaseIt.dmalmReleaseIt;
//				a = query.from(release_it)
//						.where(release_it.dmalmReleaseItPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(release_it.all()).get(0);
//				tmp_dataModifica = a.get(release_it.dtModificaReleaseIt);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,release_it)
//					.where(release_it.dmalmReleaseItPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmReleaseIt.dmalmReleaseIt.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				release_it = QDmalmReleaseIt.dmalmReleaseIt;
//				new SQLUpdateClause(connection, dialect,release_it)
//					.where(release_it.dmalmReleaseItPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmReleaseIt.dmalmReleaseIt.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "sman": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmManutenzione sman = QDmalmManutenzione.dmalmManutenzione;
//				a = query.from(sman)
//						.where(sman.dmalmManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
//						.list(sman.all()).get(0);
//				tmp_dataModifica = a.get(sman.dtModificaManutenzione);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,sman)
//					.where(sman.dmalmManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmManutenzione.dmalmManutenzione.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				sman = QDmalmManutenzione.dmalmManutenzione;
//				new SQLUpdateClause(connection, dialect,sman)
//					.where(sman.dmalmManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmManutenzione.dmalmManutenzione.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "release_ser": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmReleaseServizi releaseSer = QDmalmReleaseServizi.dmalmReleaseServizi;
//				a = query.from(releaseSer)
//						.where(releaseSer.dmalmRelServiziPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(releaseSer.all()).get(0);
//				tmp_dataModifica = a.get(releaseSer.dtModificaRelServizi);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,releaseSer)
//					.where(releaseSer.dmalmRelServiziPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmReleaseServizi.dmalmReleaseServizi.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				releaseSer = QDmalmReleaseServizi.dmalmReleaseServizi;
//				new SQLUpdateClause(connection, dialect,releaseSer)
//					.where(releaseSer.dmalmRelServiziPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmReleaseServizi.dmalmReleaseServizi.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "drqs": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmProgettoSviluppoDem drqs = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;
//				a = query.from(drqs)
//						.where(drqs.dmalmProgSvilDPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(drqs.all()).get(0);
//				tmp_dataModifica = a.get(drqs.dtModificaProgSvilD);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,drqs)
//					.where(drqs.dmalmProgSvilDPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				drqs = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;
//				new SQLUpdateClause(connection, dialect,drqs)
//					.where(drqs.dmalmProgSvilDPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "dman": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmRichiestaManutenzione dman = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
//				a = query.from(dman)
//						.where(dman.dmalmRichManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
//						.list(dman.all()).get(0);
//				tmp_dataModifica = a.get(dman.dtModificaRichManutenzione);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,dman)
//					.where(dman.dmalmRichManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				dman = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
//				new SQLUpdateClause(connection, dialect,dman)
//					.where(dman.dmalmRichManutenzionePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "rqd": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmProgettoDemand rqd = QDmalmProgettoDemand.dmalmProgettoDemand;
//				a = query.from(rqd)
//						.where(rqd.dmalmProgettoDemandPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(rqd.all()).get(0);
//				tmp_dataModifica = a.get(rqd.dtModificaProgettoDemand);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,rqd)
//					.where(rqd.dmalmProgettoDemandPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmProgettoDemand.dmalmProgettoDemand.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				rqd = QDmalmProgettoDemand.dmalmProgettoDemand;
//				new SQLUpdateClause(connection, dialect,rqd)
//					.where(rqd.dmalmProgettoDemandPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmProgettoDemand.dmalmProgettoDemand.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "richiesta_gestione": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmRichiestaGestione richiesta_gestione = QDmalmRichiestaGestione.dmalmRichiestaGestione;
//				a = query.from(richiesta_gestione)
//						.where(richiesta_gestione.dmalmRichiestaGestPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(richiesta_gestione.all()).get(0);
//				tmp_dataModifica = a.get(richiesta_gestione.dtModificaRichiestaGest);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,richiesta_gestione)
//					.where(richiesta_gestione.dmalmRichiestaGestPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmRichiestaGestione.dmalmRichiestaGestione.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				richiesta_gestione = QDmalmRichiestaGestione.dmalmRichiestaGestione;
//				new SQLUpdateClause(connection, dialect,richiesta_gestione)
//					.where(richiesta_gestione.dmalmRichiestaGestPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmRichiestaGestione.dmalmRichiestaGestione.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "srqs": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmProgettoSviluppoSvil srqs = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
//				a = query.from(srqs)
//						.where(srqs.dmalmProgSvilSPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(srqs.all()).get(0);
//				tmp_dataModifica = a.get(srqs.dtModificaProgSvilS);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,srqs)
//					.where(srqs.dmalmProgSvilSPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				srqs = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
//				new SQLUpdateClause(connection, dialect,srqs)
//					.where(srqs.dmalmProgSvilSPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "task": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmTask task = QDmalmTask.dmalmTask;
//				a = query.from(task)
//						.where(task.dmalmTaskPk.eq(tt.get(qTotal.dmalmPk)))
//						.list(task.all()).get(0);
//				tmp_dataModifica = a.get(task.dtModificaTask);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,task)
//					.where(task.dmalmTaskPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmTask.dmalmTask.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				task = QDmalmTask.dmalmTask;
//				new SQLUpdateClause(connection, dialect,task)
//					.where(task.dmalmTaskPk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmTask.dmalmTask.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "classificatore_demand": 
//				query = new SQLQuery(connection, dialect);
//				QDmalmClassificatore classificatore_demand = QDmalmClassificatore.dmalmClassificatore;
//				a = query.from(classificatore_demand)
//						.where(classificatore_demand.dmalmClassificatorePk.eq(tt.get(qTotal.dmalmPk)))
//						.list(classificatore_demand.all()).get(0);
//				tmp_dataModifica = a.get(classificatore_demand.dtModificaClassif);
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					new SQLUpdateClause(connection, dialect,classificatore_demand)
//					.where(classificatore_demand.dmalmClassificatorePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmTask.dmalmTask.changed, help.get(type)).execute();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				classificatore_demand = QDmalmClassificatore.dmalmClassificatore;
//				new SQLUpdateClause(connection, dialect,classificatore_demand)
//					.where(classificatore_demand.dmalmClassificatorePk.eq(tt.get(qTotal.dmalmPk)))
//					.set(QDmalmTask.dmalmTask.changed, value).execute();
//				backValue = type;
//				help.put(type, value);
//				break;
//				
//			case "sup": FIXME
//				
//				query = new SQLQuery(connection, dialect);
//				PreparedStatement ps = connection.prepareStatement("SELECT DATA_MODIFICA_RECORD FROM DMALM_RICHIESTA_SUPPORTO WHERE DMALM_RICH_SUPPORTO_PK= ?");
//				ps.setInt(1, tt.get(qTotal.dmalmPk));
//				ResultSet rs = ps.executeQuery();
//				if(rs.next()) 
//					tmp_dataModifica = rs.getDate("DATA_MODIFICA_RECORD");
//				ps.close();
//				if(dataModifica!=null && tmp_dataModifica.compareTo(dataModifica)<=0) {
//					ps = connection.prepareStatement("UPDATE DMALM_RICHIESTA_SUPPORTO SET CHANGED= ? WHERE DMALM_RICH_SUPPORTO_PK= ?");
//					ps.setString(1, help.get(type));
//					ps.setInt(2, tt.get(qTotal.dmalmPk));
//					ps.executeUpdate();
//					ps.close();
//					continue;
//				}
//				dataModifica = tmp_dataModifica;
//				ps = connection.prepareStatement("UPDATE DMALM_RICHIESTA_SUPPORTO SET CHANGED= ? WHERE DMALM_RICH_SUPPORTO_PK= ?");
//				ps.setString(1, value);
//				ps.setInt(2, tt.get(qTotal.dmalmPk));
//				ps.executeUpdate();
//				ps.close();
//				help.put(type, value);
//				break;
//			}
//			}catch(IndexOutOfBoundsException e) {
//				logger.error("workitem con ID:"+tt.get(qTotal.dmalmPk)+" sconosciuto");
//			}catch(SQLException e) {
//				logger.error(e.getMessage());
//			}
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
//		}
	}
}
