package lispa.schedulers.dao.sfera;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sfera.DmAlmMisura;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.MisuraUtils;
import lispa.schedulers.utils.StringUtils;
import org.apache.log4j.Logger;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMisuraDAO {

	private static Logger logger = Logger.getLogger(StgMisuraDAO.class);
	private static DmAlmMisura stg_Misura = DmAlmMisura.dmalmStgMisura;
	private static final String pathCSV = MisuraUtils.currentSferaFile();
	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;

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

	public static void fillStgMisura() throws PropertiesReaderException, DAOException {
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

					new SQLInsertClause(connection, dialect, stg_Misura)
					.columns(stg_Misura.a1Num, stg_Misura.a1Ufp,
							stg_Misura.a2Num, stg_Misura.a2Ufp,
							stg_Misura.adjmax, stg_Misura.adjmin,
							stg_Misura.adjufp, stg_Misura.ambito,
							stg_Misura.noteAsm,
							stg_Misura.pAppAccAuthLastUpdate,
							stg_Misura.pAppCdAltreAsmCommonServ,
							stg_Misura.pAppCodAsmConfinanti,
							stg_Misura.pAppCodDirezioneDemand,
							stg_Misura.pAppCodFlussiIoAsm,
							stg_Misura.pAppCdAmbitoManAttuale,
							stg_Misura.pAppCodAmbitoManFuturo,
							stg_Misura.pAppDataFineValiditaAsm,
							stg_Misura.pAppDataInizioValiditaAsm,
							stg_Misura.pAppDataUltimoAggiorn,
							stg_Misura.pAppDenomSistTerziConfin,
							stg_Misura.pAppDenomUtentiFinaliAsm,
							stg_Misura.pAppDenominazioneAsm,
							stg_Misura.pAppFlagDamisurarePatrFp,
							stg_Misura.pAppFlagMisurareSvimevFp,
							stg_Misura.pAppFlagInManutenzione,
							stg_Misura.pAppFlagServizioComune,
							stg_Misura.pAppIndicValidazioneAsm,
							stg_Misura.pAppNomeAuthLastUpdate,
							stg_Misura.appCls, stg_Misura.applicazione,
							stg_Misura.approccio, stg_Misura.b1Num,
							stg_Misura.b1Ufp, stg_Misura.b2Num,
							stg_Misura.b2Ufp, stg_Misura.b3Num,
							stg_Misura.b3Ufp, stg_Misura.b4Num,
							stg_Misura.b4Ufp, stg_Misura.b5Num,
							stg_Misura.b5Ufp, stg_Misura.bfpNum,
							stg_Misura.bfpUfp, stg_Misura.c1Num,
							stg_Misura.c1Ufp, stg_Misura.c2Num,
							stg_Misura.c2Ufp, stg_Misura.cicloDiVita,
							stg_Misura.confine, stg_Misura.costo,
							stg_Misura.crudNum, stg_Misura.crudUfp,
							stg_Misura.d1Num, stg_Misura.d1Ufp,
							stg_Misura.d2Num, stg_Misura.d2Ufp,
							stg_Misura.dataAvvio,
							stg_Misura.dataConsolidamento,
							stg_Misura.dataCreazione,
							stg_Misura.dataDismissione,
							stg_Misura.dataRiferimento,
							stg_Misura.dataFineEffettiva,
							stg_Misura.dataInizioEsercizio,
							stg_Misura.durataEffettiva, stg_Misura.eiNum,
							stg_Misura.eiUfp, stg_Misura.eifNum,
							stg_Misura.eifUfp, stg_Misura.eoNum,
							stg_Misura.eoUfp, stg_Misura.eqNum,
							stg_Misura.eqUfp, stg_Misura.esperienza,
							stg_Misura.faseCicloDiVita, stg_Misura.fonti,
							stg_Misura.fpNonPesatiMax,
							stg_Misura.fpNonPesatiMin,
							stg_Misura.fpNonPesatiUfp,
							stg_Misura.fpPesatiMax,
							stg_Misura.fpPesatiMin,
							stg_Misura.fpPesatiUfp,
							stg_Misura.frequenzaUtilizzo,
							stg_Misura.fuNum, stg_Misura.fuUfp,
							stg_Misura.gdgNum, stg_Misura.gdgUfp,
							stg_Misura.geiNum, stg_Misura.geiUfp,
							stg_Misura.geifNum, stg_Misura.geifUfp,
							stg_Misura.geoNum, stg_Misura.geoUfp,
							stg_Misura.geqNum, stg_Misura.geqUfp,
							stg_Misura.gilfNum, stg_Misura.gilfUfp,
							stg_Misura.gpNum, stg_Misura.gpUfp,
							stg_Misura.idAsm, stg_Misura.idMsr,
							stg_Misura.idProgetto, stg_Misura.ifpNum,
							stg_Misura.ifpUfp, stg_Misura.ilfNum,
							stg_Misura.ilfUfp,
							stg_Misura.impegnoEffettivo,
							stg_Misura.includeInBenchmarkingDb,
							stg_Misura.includiInDbPatrimonio,
							stg_Misura.ldgNum, stg_Misura.ldgUfp,
							stg_Misura.linkDocumentale,
							stg_Misura.noteMsr, stg_Misura.metodo,
							stg_Misura.mfNum, stg_Misura.mfUfp,
							stg_Misura.nomeMisura, stg_Misura.mldgNum,
							stg_Misura.mldgUfp, stg_Misura.modello,
							stg_Misura.mpNum, stg_Misura.mpUfp,
							stg_Misura.numeroUtenti,
							stg_Misura.permissions, stg_Misura.pfNum,
							stg_Misura.pfUfp, stg_Misura.postVerAddCfp,
							stg_Misura.postVerChg, stg_Misura.postVerDel,
							stg_Misura.postVerFp,
							stg_Misura.preVerAddCfp,
							stg_Misura.preVerChg, stg_Misura.preVerDel,
							stg_Misura.preVerFp, stg_Misura.notePrj,
							stg_Misura.pPrjADisposizione01,
							stg_Misura.pPrjADisposizione02,
							stg_Misura.pPrjAuditIndexVerify,
							stg_Misura.pPrjAuditMonitore,
							stg_Misura.pPrjCodRdi,
							stg_Misura.pPrjFccFattCorrezTotal,
							stg_Misura.pPrjFlAmbTecnPiatEnterpr,
							stg_Misura.pPrjFlAmbitoTecnFuturo01,
							stg_Misura.pPrjFlAmbitoTecnFuturo02,
							stg_Misura.pPrjFlagAmbTecnGis,
							stg_Misura.pPrjAmbTecnPortali,
							stg_Misura.pPrjFlAmbTecTransBatRep,
							stg_Misura.pPrjFlApplicLgFpDwh,
							stg_Misura.pPrjFlApplicLgFpFuturo01,
							stg_Misura.pPrjFlApplicLgFpFuturo02,
							stg_Misura.pPrjFlApplLgFpWeb,
							stg_Misura.pPrjFlApplLgFpEdma,
							stg_Misura.pPrjFlagApplLgFpGis,
							stg_Misura.pPrjFlagApplLgFpMware,
							stg_Misura.pPrjFornitoreMpp,
							stg_Misura.pPrjFornitoreSviluppoMev,
							stg_Misura.pPrjImportoAConsuntivo,
							stg_Misura.pPrjImportoRdiAPreventivo,
							stg_Misura.pPrjIndexAlmValidProgAsm,
							stg_Misura.pPrjMfcAConsuntivo,
							stg_Misura.pPrjMfcAPreventivo,
							stg_Misura.pPrjMpPercentCicloDiVita,
							stg_Misura.pPrjPunPrezzoUnitNominal,
							stg_Misura.prjCls, stg_Misura.nomeProgetto,
							stg_Misura.proprietaLegale,
							stg_Misura.responsabile, stg_Misura.scopo,
							stg_Misura.staffMedio,
							stg_Misura.statoMisura,
							stg_Misura.tipoProgetto, stg_Misura.tpNum,
							stg_Misura.tpUfp, stg_Misura.ugdgNum,
							stg_Misura.ugdgUfp, stg_Misura.ugepNum,
							stg_Misura.ugepUfp, stg_Misura.ugoNum,
							stg_Misura.ugoUfp, stg_Misura.ugpNum,
							stg_Misura.ugpUfp,
							stg_Misura.utenteMisuratore,
							stg_Misura.utilizzata,
							stg_Misura.vafPredefinito,
							stg_Misura.verDelta,
							stg_Misura.verDeltaPercent,
							stg_Misura.verEnd, stg_Misura.versioneMsr,
							stg_Misura.versionePrj, stg_Misura.verStart,
							stg_Misura.dtPrevistaProssimaMpp,
							stg_Misura.fip01InizioEsercizio,
							stg_Misura.fip02IndiceQualita,
							stg_Misura.fip03ComplessEserc,
							stg_Misura.fip04NrPiattaforma,
							stg_Misura.fip07LingProgPrincipale,
							stg_Misura.fip10GradoAccessibilita,
							stg_Misura.fip11GradoQualitaCod,
							stg_Misura.fip12UtilizzoFramework,
							stg_Misura.fip13ComplessitaAlg,
							stg_Misura.fip15LivelloCura)
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
				ErrorManager.getInstance().exceptionOccurred(true, e);
			} finally {
				cm.closeConnection(connection);
			}
		}
	}
}
