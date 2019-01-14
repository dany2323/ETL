package lispa.schedulers.dao.sfera;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lispa.schedulers.bean.target.sfera.DmalmMisura;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.staging.sfera.QDmalmStgMisura;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.MisuraUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.collect.Lists;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class StgMisuraDAO {

	private static Logger logger = Logger.getLogger(StgMisuraDAO.class);
	private static Timestamp dataEsecuzione = DataEsecuzione.getInstance()
			.getDataEsecuzione();
	private static QDmalmStgMisura stgMisura = QDmalmStgMisura.dmalmStgMisura;
	private static final String pathCSV = MisuraUtils.currentSferaFile();
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;
	private static SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

	public static HashMap<String, Integer> mappaColonne()
			throws PropertiesReaderException {
		String[] nextLine;
		ArrayList<String> fieldsNames = null;

		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		CSVReader reader = null;

		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(
					pathCSV), "ISO-8859-1"), '\t');

			if ((nextLine = reader.readNext()) != null) {
				fieldsNames = Lists.newArrayList(nextLine);
			}

			int i = 0;
			for (String field : fieldsNames) {
				hm.put(field, i);
				i++;
			}

			if (reader != null) {
				reader.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return hm;
	}

	public static void FillStgMisura() throws PropertiesReaderException,
			DAOException, SQLException {
		if (pathCSV != null) {
			HashMap<String, Integer> mapping = mappaColonne();
			ArrayList<String> columns = null;
			ArrayList<ArrayList<String>> csvTotal = new ArrayList<ArrayList<String>>();

			CSVReader reader = null;
			String meaK;
			String projK;
			connection = cm.getConnectionOracle();

			try {
				reader = new CSVReader(new InputStreamReader(
						new FileInputStream(pathCSV), "UTF-8"), '\t');

				SQLTemplates dialect = new HSQLDBTemplates();
				String[] nextLine;
				nextLine = reader.readNext();
				while ((nextLine = reader.readNext()) != null) {
					columns = Lists.newArrayList(nextLine);

					csvTotal.add(columns);
				}

				int numRighe = 0;

				for (ArrayList<String> row : csvTotal) {
					numRighe++;

					meaK = row.get(mapping.get("IdMea"));
					projK = row.get(mapping.get("IdPrj"));
					if (meaK.equals(""))
						row.set(mapping.get("IdMea"), "0");
					if (projK.equals(""))
						row.set(mapping.get("IdPrj"), "0");

					new SQLInsertClause(connection, dialect, stgMisura)
							.columns(stgMisura.a1Num, stgMisura.a1Ufp,
									stgMisura.a2Num, stgMisura.a2Ufp,
									stgMisura.adjmax, stgMisura.adjmin,
									stgMisura.adjufp, stgMisura.ambito,
									stgMisura.noteAsm,
									stgMisura.pAppAccAuthLastUpdate,
									stgMisura.pAppCdAltreAsmCommonServ,
									stgMisura.pAppCodAsmConfinanti,
									stgMisura.pAppCodDirezioneDemand,
									stgMisura.pAppCodFlussiIoAsm,
									stgMisura.pAppCdAmbitoManAttuale,
									stgMisura.pAppCodAmbitoManFuturo,
									stgMisura.pAppDataFineValiditaAsm,
									stgMisura.pAppDataInizioValiditaAsm,
									stgMisura.pAppDataUltimoAggiorn,
									stgMisura.pAppDenomSistTerziConfin,
									stgMisura.pAppDenomUtentiFinaliAsm,
									stgMisura.pAppDenominazioneAsm,
									stgMisura.pAppFlagDamisurarePatrFp,
									stgMisura.pAppFlagMisurareSvimevFp,
									stgMisura.pAppFlagInManutenzione,
									stgMisura.pAppFlagServizioComune,
									stgMisura.pAppIndicValidazioneAsm,
									stgMisura.pAppNomeAuthLastUpdate,
									stgMisura.appCls, stgMisura.applicazione,
									stgMisura.approccio, stgMisura.b1Num,
									stgMisura.b1Ufp, stgMisura.b2Num,
									stgMisura.b2Ufp, stgMisura.b3Num,
									stgMisura.b3Ufp, stgMisura.b4Num,
									stgMisura.b4Ufp, stgMisura.b5Num,
									stgMisura.b5Ufp, stgMisura.bfpNum,
									stgMisura.bfpUfp, stgMisura.c1Num,
									stgMisura.c1Ufp, stgMisura.c2Num,
									stgMisura.c2Ufp, stgMisura.cicloDiVita,
									stgMisura.confine, stgMisura.costo,
									stgMisura.crudNum, stgMisura.crudUfp,
									stgMisura.d1Num, stgMisura.d1Ufp,
									stgMisura.d2Num, stgMisura.d2Ufp,
									stgMisura.dataAvvio,
									stgMisura.dataConsolidamento,
									stgMisura.dataCreazione,
									stgMisura.dataDismissione,
									stgMisura.dataRiferimento,
									stgMisura.dataFineEffettiva,
									stgMisura.dataInizioEsercizio,
									stgMisura.durataEffettiva, stgMisura.eiNum,
									stgMisura.eiUfp, stgMisura.eifNum,
									stgMisura.eifUfp, stgMisura.eoNum,
									stgMisura.eoUfp, stgMisura.eqNum,
									stgMisura.eqUfp, stgMisura.esperienza,
									stgMisura.faseCicloDiVita, stgMisura.fonti,
									stgMisura.fpNonPesatiMax,
									stgMisura.fpNonPesatiMin,
									stgMisura.fpNonPesatiUfp,
									stgMisura.fpPesatiMax,
									stgMisura.fpPesatiMin,
									stgMisura.fpPesatiUfp,
									stgMisura.frequenzaUtilizzo,
									stgMisura.fuNum, stgMisura.fuUfp,
									stgMisura.gdgNum, stgMisura.gdgUfp,
									stgMisura.geiNum, stgMisura.geiUfp,
									stgMisura.geifNum, stgMisura.geifUfp,
									stgMisura.geoNum, stgMisura.geoUfp,
									stgMisura.geqNum, stgMisura.geqUfp,
									stgMisura.gilfNum, stgMisura.gilfUfp,
									stgMisura.gpNum, stgMisura.gpUfp,
									stgMisura.idAsm, stgMisura.idMsr,
									stgMisura.idProgetto, stgMisura.ifpNum,
									stgMisura.ifpUfp, stgMisura.ilfNum,
									stgMisura.ilfUfp,
									stgMisura.impegnoEffettivo,
									stgMisura.includeInBenchmarkingDb,
									stgMisura.includiInDbPatrimonio,
									stgMisura.ldgNum, stgMisura.ldgUfp,
									stgMisura.linkDocumentale,
									stgMisura.noteMsr, stgMisura.metodo,
									stgMisura.mfNum, stgMisura.mfUfp,
									stgMisura.nomeMisura, stgMisura.mldgNum,
									stgMisura.mldgUfp, stgMisura.modello,
									stgMisura.mpNum, stgMisura.mpUfp,
									stgMisura.numeroUtenti,
									stgMisura.permissions, stgMisura.pfNum,
									stgMisura.pfUfp, stgMisura.postVerAddCfp,
									stgMisura.postVerChg, stgMisura.postVerDel,
									stgMisura.postVerFp,
									stgMisura.preVerAddCfp,
									stgMisura.preVerChg, stgMisura.preVerDel,
									stgMisura.preVerFp, stgMisura.notePrj,
									stgMisura.pPrjADisposizione01,
									stgMisura.pPrjADisposizione02,
									stgMisura.pPrjAuditIndexVerify,
									stgMisura.pPrjAuditMonitore,
									stgMisura.pPrjCodRdi,
									stgMisura.pPrjFccFattCorrezTotal,
									stgMisura.pPrjFlAmbTecnPiatEnterpr,
									stgMisura.pPrjFlAmbitoTecnFuturo01,
									stgMisura.pPrjFlAmbitoTecnFuturo02,
									stgMisura.pPrjFlagAmbTecnGis,
									stgMisura.pPrjAmbTecnPortali,
									stgMisura.pPrjFlAmbTecTransBatRep,
									stgMisura.pPrjFlApplicLgFpDwh,
									stgMisura.pPrjFlApplicLgFpFuturo01,
									stgMisura.pPrjFlApplicLgFpFuturo02,
									stgMisura.pPrjFlApplLgFpWeb,
									stgMisura.pPrjFlApplLgFpEdma,
									stgMisura.pPrjFlagApplLgFpGis,
									stgMisura.pPrjFlagApplLgFpMware,
									stgMisura.pPrjFornitoreMpp,
									stgMisura.pPrjFornitoreSviluppoMev,
									stgMisura.pPrjImportoAConsuntivo,
									stgMisura.pPrjImportoRdiAPreventivo,
									stgMisura.pPrjIndexAlmValidProgAsm,
									stgMisura.pPrjMfcAConsuntivo,
									stgMisura.pPrjMfcAPreventivo,
									stgMisura.pPrjMpPercentCicloDiVita,
									stgMisura.pPrjPunPrezzoUnitNominal,
									stgMisura.prjCls, stgMisura.nomeProgetto,
									stgMisura.proprietaLegale,
									stgMisura.responsabile, stgMisura.scopo,
									stgMisura.staffMedio,
									stgMisura.statoMisura,
									stgMisura.tipoProgetto, stgMisura.tpNum,
									stgMisura.tpUfp, stgMisura.ugdgNum,
									stgMisura.ugdgUfp, stgMisura.ugepNum,
									stgMisura.ugepUfp, stgMisura.ugoNum,
									stgMisura.ugoUfp, stgMisura.ugpNum,
									stgMisura.ugpUfp,
									stgMisura.utenteMisuratore,
									stgMisura.utilizzata,
									stgMisura.vafPredefinito,
									stgMisura.verDelta,
									stgMisura.verDeltaPercent,
									stgMisura.verEnd, stgMisura.versioneMsr,
									stgMisura.versionePrj, stgMisura.verStart,
									stgMisura.dataCaricamento,
									stgMisura.dmalmStgMisuraPk,
									stgMisura.dtPrevistaProssimaMpp,
									stgMisura.fip01InizioEsercizio,
									stgMisura.fip02IndiceQualita,
									stgMisura.fip03ComplessEserc,
									stgMisura.fip04NrPiattaforma,
									stgMisura.fip07LingProgPrincipale,
									stgMisura.fip10GradoAccessibilita,
									stgMisura.fip11GradoQualitaCod,
									stgMisura.fip12UtilizzoFramework,
									stgMisura.fip13ComplessitaAlg,
									stgMisura.fip15LivelloCura)
							.values(row.get(mapping.get("A.1_NUM")),
									row.get(mapping.get("A.1_UFP")),
									row.get(mapping.get("A.2_NUM")),
									row.get(mapping.get("A.2_UFP")),
									row.get(mapping.get("Adj-Max")),
									row.get(mapping.get("Adj-Min")),
									row.get(mapping.get("Adj-Ufp")),
									row.get(mapping.get("Ambito")),
									StringUtils.getMaskedValue(row.get(mapping.get("APP_Note"))),
									StringUtils.getMaskedValue(row.get(mapping
											.get("APP-ATT:ACCOUNT_AUTORE_ULTIMO_AGGIORN"))),
									row.get(mapping
											.get("APP-ATT:COD_ALTRE_ASM_UTILIZZ_COME_SERV_COMUNI")),
									row.get(mapping
											.get("APP-ATT:COD_ASM_CONFINANTI")),
									row.get(mapping
											.get("APP-ATT:COD_DIREZIONE_DEMAND")),
									row.get(mapping
											.get("APP-ATT:COD_FLUSSI_IO_ASM")),
									StringUtils.getMaskedValue(row.get(mapping
											.get("APP-ATT:CODICE_AMBITO_MANUTENZIONE_2012_ASM"))),
									StringUtils.getMaskedValue(row.get(mapping
											.get("APP-ATT:CODICE_AMBITO_MANUTENZIONE_2014_2015_ASM"))),
									row.get(mapping
											.get("APP-ATT:DATA_FINE_VALIDITA_ASM")),
									row.get(mapping
											.get("APP-ATT:DATA_INIZIO_VALIDITA_ASM")),
									row.get(mapping
											.get("APP-ATT:DATA_ULTIMO_AGGIORN")),
									row.get(mapping
											.get("APP-ATT:DENOM_SIST_TERZEPARTI_CONFINANTI")),
									row.get(mapping
											.get("APP-ATT:DENOM_UTENTI_ FINALI_ASM")),
									row.get(mapping
											.get("APP-ATT:DENOMINAZIONE_ASM")),
									row.get(mapping
											.get("APP-ATT:FLAG_ASM_DA_MISURARE_PATRIMONIALE_IN_FP")),
									row.get(mapping
											.get("APP-ATT:FLAG_ASM_DA_MISURARE_SVILUPPOMEV_IN_FP")),
									row.get(mapping
											.get("APP-ATT:FLAG_ASM_IN_MANUTENZIONE")),
									row.get(mapping
											.get("APP-ATT:FLAG_ASM_SERVIZIO_COMUNE")),
									row.get(mapping
											.get("APP-ATT:INDICATORE_ALM_PER_VALIDAZIONE_ASM")),
									StringUtils.getMaskedValue(row.get(mapping
											.get("APP-ATT:NOME_AUTORE_ULTIMO_AGGIORN"))),
									row.get(mapping.get("APP-CLS:")),
									row.get(mapping.get("Applicazione")),
									row.get(mapping.get("Approccio")),
									row.get(mapping.get("B.1_NUM")),
									row.get(mapping.get("B.1_UFP")),
									row.get(mapping.get("B.2_NUM")),
									row.get(mapping.get("B.2_UFP")),
									row.get(mapping.get("B.3_NUM")),
									row.get(mapping.get("B.3_UFP")),
									row.get(mapping.get("B.4_NUM")),
									row.get(mapping.get("B.4_UFP")),
									row.get(mapping.get("B.5_NUM")),
									row.get(mapping.get("B.5_UFP")),
									row.get(mapping.get("BFP_NUM")),
									row.get(mapping.get("BFP_UFP")),
									row.get(mapping.get("C.1_NUM")),
									row.get(mapping.get("C.1_UFP")),
									row.get(mapping.get("C.2_NUM")),
									row.get(mapping.get("C.2_UFP")),
									row.get(mapping.get("Ciclo di vita")),
									row.get(mapping.get("Confine")),
									row.get(mapping.get("Costo")),
									row.get(mapping.get("CRUD_NUM")),
									row.get(mapping.get("CRUD_UFP")),
									row.get(mapping.get("D.1_NUM")),
									row.get(mapping.get("D.1_UFP")),
									row.get(mapping.get("D.2_NUM")),
									row.get(mapping.get("D.2_UFP")),
									row.get(mapping.get("Data di avvio")),
									row.get(mapping
											.get("Data di consolidamento")),
									row.get(mapping.get("Data di creazione")),
									row.get(mapping.get("Data di dismissione")),
									row.get(mapping.get("Data di riferimento")),
									row.get(mapping.get("Data fine effettiva")),
									row.get(mapping
											.get("Data inizio esercizio")),
									row.get(mapping.get("Durata effettiva")),
									row.get(mapping.get("EI_NUM")),
									row.get(mapping.get("EI_UFP")),
									row.get(mapping.get("EIF_NUM")),
									row.get(mapping.get("EIF_UFP")),
									row.get(mapping.get("EO_NUM")),
									row.get(mapping.get("EO_UFP")),
									row.get(mapping.get("EQ_NUM")),
									row.get(mapping.get("EQ_UFP")),
									row.get(mapping.get("Esperienza")),
									row.get(mapping
											.get("Fase del ciclo di vita")),
									row.get(mapping.get("Fonti")),
									row.get(mapping.get("FP non pesati (MAX)")),
									row.get(mapping.get("FP non pesati (MIN)")),
									row.get(mapping.get("FP non pesati (UFP)")),
									row.get(mapping.get("FP pesati (MAX)")),
									row.get(mapping.get("FP pesati (MIN)")),
									row.get(mapping.get("FP pesati (UFP)")),
									row.get(mapping
											.get("Frequenza di utilizzo")),
									row.get(mapping.get("FU_NUM")),
									row.get(mapping.get("FU_UFP")),
									row.get(mapping.get("GDG_NUM")),
									row.get(mapping.get("GDG_UFP")),
									row.get(mapping.get("GEI_NUM")),
									row.get(mapping.get("GEI_UFP")),
									row.get(mapping.get("GEIF_NUM")),
									row.get(mapping.get("GEIF_UFP")),
									row.get(mapping.get("GEO_NUM")),
									row.get(mapping.get("GEO_UFP")),
									row.get(mapping.get("GEQ_NUM")),
									row.get(mapping.get("GEQ_UFP")),
									row.get(mapping.get("GILF_NUM")),
									row.get(mapping.get("GILF_UFP")),
									row.get(mapping.get("GP_NUM")),
									row.get(mapping.get("GP_UFP")),
									row.get(mapping.get("IdApp")),
									row.get(mapping.get("IdMea")),
									row.get(mapping.get("IdPrj")),
									row.get(mapping.get("IFP_NUM")),
									row.get(mapping.get("IFP_UFP")),
									row.get(mapping.get("ILF_NUM")),
									row.get(mapping.get("ILF_UFP")),
									row.get(mapping.get("Impegno effettivo")),
									row.get(mapping
											.get("Includi nel database di benchmarking")),
									row.get(mapping
											.get("Includi nel database di patrimonio")),
									row.get(mapping.get("LDG_NUM")),
									row.get(mapping.get("LDG_UFP")),
									row.get(mapping.get("Link documentale")),
									StringUtils.getMaskedValue(row.get(mapping.get("MEA_Note"))),
									row.get(mapping.get("Metodo")),
									row.get(mapping.get("MF_NUM")),
									row.get(mapping.get("MF_UFP")),
									row.get(mapping.get("Misura")),
									row.get(mapping.get("MLDG_NUM")),
									row.get(mapping.get("MLDG_UFP")),
									row.get(mapping.get("Modello")),
									row.get(mapping.get("MP_NUM")),
									row.get(mapping.get("MP_UFP")),
									row.get(mapping.get("Numero utenti")),
									StringUtils.getMaskedValue(row.get(mapping.get("Permissions"))),
									row.get(mapping.get("PF_NUM")),
									row.get(mapping.get("PF_UFP")),
									row.get(mapping.get("POST.VER.ADD/CFP")),
									row.get(mapping.get("POST.VER.CHG")),
									row.get(mapping.get("POST.VER.DEL")),
									row.get(mapping.get("POST.VER.FP")),
									row.get(mapping.get("PRE.VER.ADD/CFP")),
									row.get(mapping.get("PRE.VER.CHG")),
									row.get(mapping.get("PRE.VER.DEL")),
									row.get(mapping.get("PRE.VER.FP")),
									StringUtils.getMaskedValue(row.get(mapping.get("PRJ_Note"))),
									row.get(mapping
											.get("PRJ-ATT:A_DISPOSIZIONE_01")),
									row.get(mapping
											.get("PRJ-ATT:A_DISPOSIZIONE-02")),
									row.get(mapping
											.get("PRJ-ATT:AUDIT_INDICE_VERIFICABILITA")),
									StringUtils.getMaskedValue(row.get(mapping
											.get("PRJ-ATT:AUDIT_MONITORE"))),
									row.get(mapping.get("PRJ-ATT:COD_ RDI")),
									row.get(mapping
											.get("PRJ-ATT:FCC_FATT_CORREZ_COMPLESSIVO")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_ AMBITO_TECNOLOGICO_ PIATTAF_ SPECIAL_ ENTERPRISE")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_ futuro-01")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_ futuro-02")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_ GIS")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_PORTALI")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_TRANS_BATCH_REP")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_APPLICABILITA_LG_ FP_- DWH")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_APPLICABILITA_LG_ FP_futuro-01")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_APPLICABILITA_LG_ FP_futuro-02")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_APPLICABILITA_LG_ FP_SITIWEB")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_APPLICABILITA_LG_FP_EDMA")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_APPLICABILITA_LG_FP_GIS")),
									row.get(mapping
											.get("PRJ-ATT:FLAG_APPLICABILITA_LG_FP_MWARE")),
									StringUtils.getMaskedValue(row.get(mapping
											.get("PRJ-ATT:FORNITORE_MPP"))),
									StringUtils.getMaskedValue(row.get(mapping
											.get("PRJ-ATT:FORNITORE_SVILUPPO_MEV "))),
									row.get(mapping
											.get("PRJ-ATT:IMPORTO_A_CONSUNTIVO")),
									row.get(mapping
											.get("PRJ-ATT:IMPORTO_RDI_A_PREVENTIVO")),
									row.get(mapping
											.get("PRJ-ATT:INDICATORE_ALM_PER_VALIDAZ_PROGETTO_ASM")),
									row.get(mapping
											.get("PRJ-ATT:MFC_A_CONSUNTIVO")),
									row.get(mapping
											.get("PRJ-ATT:MFC_A_PREVENTIVO")),
									row.get(mapping
											.get("PRJ-ATT:MP_PERCENT_CICLO_DI_VITA")),
									row.get(mapping
											.get("PRJ-ATT:PUN_PREZZO_UNITARIO_NOMINALE")),
									row.get(mapping.get("PRJ-CLS:")),
									row.get(mapping.get("Progetto")),
									row.get(mapping.get("Proprietà legale")),
									StringUtils.getMaskedValue(row.get(mapping.get("Responsabile"))),
									row.get(mapping.get("Scopo")),
									row.get(mapping.get("Staff medio")),
									row.get(mapping.get("Stato misura (*)")),
									row.get(mapping.get("Tipo progetto")),
									row.get(mapping.get("TP_NUM")),
									row.get(mapping.get("TP_UFP")),
									row.get(mapping.get("UGDG_NUM")),
									row.get(mapping.get("UGDG_UFP")),
									row.get(mapping.get("UGEP_NUM")),
									row.get(mapping.get("UGEP_UFP")),
									row.get(mapping.get("UGO_NUM")),
									row.get(mapping.get("UGO_UFP")),
									row.get(mapping.get("UGP_NUM")),
									row.get(mapping.get("UGP_UFP")),
									StringUtils.getMaskedValue(row.get(mapping.get("Utente misuratore"))),
									row.get(mapping.get("Utilizzata")),
									row.get(mapping.get("VAF predefinito")),
									row.get(mapping.get("VER.DELTA")),
									row.get(mapping.get("VER.DELTA%")),
									row.get(mapping.get("VER-End")),
									row.get(mapping.get("VersioneMea")),
									row.get(mapping.get("VersionePrj")),
									row.get(mapping.get("VER-Start")),
									dataEsecuzione,
									StringTemplate
											.create("DM_ALM_STG_MISURA_SEQ.nextval"),
									(StringUtils.hasText(row.get(mapping
											.get("APP-ATT:DATA_PREVISTA_CONSEGNA_PROSSIMA_MPP_ASM"))) ? DateUtils.stringToTimestamp(row.get(mapping
											.get("APP-ATT:DATA_PREVISTA_CONSEGNA_PROSSIMA_MPP_ASM")),"dd/MM/yyyy")
											: null),
									(StringUtils.hasText(row.get(mapping
											.get("APP-ATT:FIP01_ANNO_INIZIO_ESERCIZIO_ASM"))) ? DateUtils.stringToTimestamp(
											row.get(mapping
													.get("APP-ATT:FIP01_ANNO_INIZIO_ESERCIZIO_ASM")),
											"dd/MM/yyyy")
											: null),
									row.get(mapping
											.get("APP-ATT:FIP02_INDICE_QUALITA_DOCUMENTAZ_ASM")),
									row.get(mapping
											.get("APP-ATT:FIP03_COMPLESS_PIATTAF_SVIL_ESERC_ASM")),
									row.get(mapping
											.get("APP-ATT:FIP04_NR-PIATTAF-TGT_ESERC_ASM")),
									row.get(mapping
											.get("APP-ATT:FIP07_LING_PROG_PRINCIPALE_REALIZZ_ASM")),
									row.get(mapping
											.get("APP-ATT:FIP10_GRADO_ACCESSIBILITA_ASM")),
									row.get(mapping
											.get("APP-ATT:FIP11_GRADO_QUALITA_CODICE_ASM")),
									row.get(mapping
											.get("APP-ATT:FIP12_UTILIZZO_FRAMEWORK_AZIENDALI_ASM")),
									row.get(mapping
											.get("APP-ATT:FIP13_COMPLESS_ALGORITMICA_SW_ASM")),
									row.get(mapping
											.get("APP-ATT:FIP15_LIVELLO_CURA_GRAF_INTERF_UTENTE_ASM")))
							.execute();
				}

				connection.commit();

				logger.info("StgMisuraDAO.FillStgMisura - pathCSV: " + pathCSV
						+ ", righe inserite: " + numRighe);

				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				ErrorManager.getInstance().exceptionOccurred(true, e);
				throw new DAOException(e);
			} finally {
				if (cm != null) {
					cm.closeConnection(connection);
				}
			}
		}
	}

	public static void delete(Timestamp dataEsecuzioneDelete)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMisura qstgmisura = QDmalmStgMisura.dmalmStgMisura;

			new SQLDeleteClause(connection, dialect, qstgmisura).where(
					qstgmisura.dataCaricamento.lt(dataEsecuzioneDelete).or(
							qstgmisura.dataCaricamento.gt(DateUtils
									.addDaysToTimestamp(DataEsecuzione
											.getInstance().getDataEsecuzione(),
											-1)))).execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMisura() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmStgMisura stgmisura = QDmalmStgMisura.dmalmStgMisura;
			new SQLDeleteClause(connection, dialect, stgmisura).where(
					stgmisura.dataCaricamento.eq(DataEsecuzione.getInstance()
							.getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	/**
	 * Estrae la lista degli ASM eliminati fisicamente dal sorgente: ASM
	 * presenti alla data dell'esecuzione precedente, ma non più presenti alla
	 * data di esecuzione attuale
	 * 
	 * @return List<String>
	 * @throws IOException
	 * @throws DAOException
	 */

	public static List<DmalmMisura> getRemoveFromFile(String type,
			Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<DmalmMisura> removeList = new ArrayList<DmalmMisura>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = getQueryRemoveFromFile(type);

			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);

			rs = ps.executeQuery();

			while (rs.next()) {
				DmalmMisura bean = new DmalmMisura();

				bean.setIdAsm(rs.getShort("ID_ASM"));

				if (type.equalsIgnoreCase("PRJ")) {
					bean.setIdProgetto(rs.getShort("ID_PROGETTO"));
				} else if (type.equalsIgnoreCase("MIS")) {
					bean.setIdProgetto(rs.getShort("ID_PROGETTO"));
					bean.setIdMsr(rs.getInt("ID_MSR"));
					bean.setDmalmMisuraPk(rs.getInt("MISURA_PK"));
				}

				removeList.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);

			ErrorManager.getInstance().exceptionOccurred(true, e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return removeList;
	}

	private static String getQueryRemoveFromFile(String type)
			throws PropertiesReaderException, Exception {
		String sql = null;

		if (type.equalsIgnoreCase("ASM")) {
			sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_ASM_ELIMINATE);
		} else if (type.equalsIgnoreCase("PRJ")) {
			sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_PROGETTI_ELIMINATI);
		} else if (type.equalsIgnoreCase("MIS")) {
			sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_MISURE_ELIMINATE);
		}

		return sql;
	}

	public static List<Tuple> getControlloDatiSfera(Timestamp dataEsecuzione)
			throws DAOException {
		List<Tuple> stgMisuraDati = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			stgMisuraDati = query.from(stgMisura)
					.where(stgMisura.dataCaricamento.eq(dataEsecuzione))
					.orderBy(stgMisura.idAsm.asc(), stgMisura.idProgetto.asc())
					.list(stgMisura.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return stgMisuraDati;
	}

	public static List<Tuple> checkPat002(Logger logger, Tuple row,
			Timestamp dataEsecuzione) throws DAOException {

		List<Tuple> misurePat002 = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misurePat002 = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.startsWith("PAT-002-"))
					.list(stgMisura.nomeMisura, stgMisura.statoMisura);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misurePat002;
	}

	public static List<Tuple> checkMisurePatr(Logger logger, Tuple row,
			Timestamp dataEsecuzione) throws DAOException {

		List<Tuple> misurePatr = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misurePatr = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 4).eq("PAT-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.where(stgMisura.nomeMisura.substring(7, 8).eq("-"))
					.where(stgMisura.nomeMisura.substring(8, 9).in("C", "B"))
					.list(stgMisura.nomeMisura, stgMisura.nomeProgetto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misurePatr;
	}

	public static List<String> checkCountMisurePatr(Logger logger, Tuple row,
			Timestamp dataEsecuzione) throws DAOException {

		List<String> misurePatr = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misurePatr = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 4).eq("PAT-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.where(stgMisura.nomeMisura.substring(7, 8).eq("-"))
					.where(stgMisura.nomeMisura.substring(8, 9).in("C", "B"))
					.groupBy(stgMisura.nomeMisura.substring(0, 7))
					.having(stgMisura.nomeMisura.substring(0, 7).count().gt(1))
					.list(stgMisura.nomeMisura.substring(0, 7));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misurePatr;
	}

	public static Boolean checkBucoNumerazMisurePatr(Logger logger, Tuple row,
			Timestamp dataEsecuzione) throws DAOException {

		boolean bucoNumerazione = true;

		List<Tuple> maxPatr = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			String resMaxPatr = null;

			maxPatr = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 4).eq("PAT-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.where(stgMisura.nomeMisura.substring(7, 8).eq("-"))
					.where(stgMisura.nomeMisura.substring(8, 9).in("C", "B"))
					.orderBy(stgMisura.nomeMisura.substring(4, 7).desc())
					.list(stgMisura.nomeMisura, stgMisura.statoMisura);

			if (maxPatr.size() > 0) {
				resMaxPatr = maxPatr.get(0).get(stgMisura.nomeMisura)
						.substring(4, 7);

				if (Integer.parseInt(resMaxPatr) != maxPatr.size()) {
					bucoNumerazione = false;
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return bucoNumerazione;
	}

	public static List<String> checkCountMisurePatrBas(Logger logger,
			Tuple row, Timestamp dataEsecuzione) throws DAOException {

		List<String> misurePatr = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misurePatr = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 4).eq("BAS-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.groupBy(stgMisura.nomeMisura.substring(0, 7))
					.having(stgMisura.nomeMisura.substring(0, 7).count().gt(1))
					.list(stgMisura.nomeMisura.substring(0, 7));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		return misurePatr;
	}

	public static Boolean checkBucoNumerazMisurePatrBas(Logger logger,
			Tuple row, Timestamp dataEsecuzione) throws DAOException {

		boolean bucoNumerazione = true;

		List<Tuple> maxPatr = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			String resMaxPatr = null;

			maxPatr = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 4).eq("BAS-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.orderBy(stgMisura.nomeMisura.substring(4, 7).desc())
					.list(stgMisura.nomeMisura, stgMisura.statoMisura);

			if (maxPatr.size() > 0) {
				resMaxPatr = maxPatr.get(0).get(stgMisura.nomeMisura)
						.substring(4, 7);
				if (Integer.parseInt(resMaxPatr) != maxPatr.size()) {
					bucoNumerazione = false;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return bucoNumerazione;
	}

	public static List<String> checkCountMisureSt(Logger logger, Tuple row,
			Timestamp dataEsecuzione) throws DAOException {

		List<String> misureSt = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misureSt = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 2).eq("ST"))
					.where(stgMisura.nomeMisura.substring(2, 3).between("1",
							"3"))
					.where(stgMisura.nomeMisura.substring(3, 4).eq("-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.groupBy(stgMisura.nomeMisura.substring(0, 7))
					.having(stgMisura.nomeMisura.substring(0, 7).count().gt(1))
					.list(stgMisura.nomeMisura.substring(0, 7));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misureSt;
	}

	public static List<Tuple> checkBucoNumerazMisureSt(Logger logger,
			Tuple row, Timestamp dataEsecuzione) throws DAOException {

		List<Tuple> misureSt = new ArrayList<Tuple>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			misureSt = query
					.from(stgMisura)
					.where(stgMisura.idAsm.eq(row.get(stgMisura.idAsm)))
					.where(stgMisura.idProgetto.eq(row
							.get(stgMisura.idProgetto)))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.nomeMisura.substring(0, 2).eq("ST"))
					.where(stgMisura.nomeMisura.substring(2, 3).between("1",
							"3"))
					.where(stgMisura.nomeMisura.substring(3, 4).eq("-"))
					.where(stgMisura.nomeMisura.substring(4, 7).between("001",
							"999"))
					.groupBy(stgMisura.nomeMisura.substring(0, 7))
					.having(stgMisura.nomeMisura.substring(0, 7).count().gt(1))
					.list(stgMisura.nomeMisura.substring(0, 7),
							stgMisura.nomeMisura.substring(0, 7).count());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misureSt;
	}

	public static Boolean checkAsmValida(Logger logger, Tuple row,
			Timestamp dataEsecuzione, String asm) throws DAOException {

		boolean asmValida = false;
		String appCodAsm = null;
		List<String> asmRes = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			asmRes = query
					.from(stgMisura)
					.distinct()
					.where(stgMisura.applicazione.startsWith(asm))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.list(stgMisura.applicazione);

			for (String ar : asmRes) {
				appCodAsm = ar.trim();

				if (appCodAsm.contains("#")) {
					appCodAsm = appCodAsm.substring(0,
							appCodAsm.indexOf("#") - 1);
				}

				if (asm.equals(appCodAsm)) {
					asmValida = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asmValida;
	}

	public static Boolean checkAltreAsmValida(Logger logger, Tuple row,
			Timestamp dataEsecuzione, String asm) throws DAOException {

		boolean asmValida = false;
		String appCodAsm = null;
		List<String> asmRes = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			asmRes = query
					.from(stgMisura)
					.where(stgMisura.applicazione.startsWith(asm))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.where(stgMisura.pAppFlagServizioComune.eq("SI"))
					.list(stgMisura.applicazione);

			for (String a : asmRes) {
				appCodAsm = a.trim();

				if (appCodAsm.contains("#")) {
					appCodAsm = appCodAsm.substring(0,
							appCodAsm.indexOf("#") - 1);
				}

				if (asm.equals(appCodAsm)) {
					asmValida = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return asmValida;
	}

	public static Boolean checkDenomValida(Logger logger, Tuple row,
			Timestamp dataEsecuzione, String asm) throws DAOException {

		boolean denomValida = true;

		List<String> asmRes = new ArrayList<String>();

		try {
			connection = cm.getConnectionOracle();

			// SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			asmRes = query
					.from(stgMisura)
					.where(stgMisura.applicazione.eq(asm))
					.where(stgMisura.dataCaricamento.eq(row
							.get(stgMisura.dataCaricamento)))
					.list(stgMisura.applicazione);

			if (asmRes.size() == 0) {
				denomValida = false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return denomValida;
	}
}
