package lispa.schedulers.queryimplementation.staging.sfera;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sfera.DmalmStgMisura;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmStgMisura is a Querydsl query type for DmalmStgMisura
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmStgMisura extends com.mysema.query.sql.RelationalPathBase<DmalmStgMisura> {

    private static final long serialVersionUID = -1808180199;

    public static final QDmalmStgMisura dmalmStgMisura = new QDmalmStgMisura("DMALM_STG_MISURA");

    public final StringPath a1Num = createString("A_1_NUM");

    public final StringPath a1Ufp = createString("A_1_UFP");

    public final StringPath a2Num = createString("A_2_NUM");

    public final StringPath a2Ufp = createString("A_2_UFP");

    public final StringPath adjmax = createString("ADJMAX");

    public final StringPath adjmin = createString("ADJMIN");

    public final StringPath adjufp = createString("ADJUFP");

    public final StringPath ambito = createString("AMBITO");

    public final StringPath appCls = createString("APP_CLS");

    public final StringPath applicazione = createString("APPLICAZIONE");

    public final StringPath approccio = createString("APPROCCIO");

    public final StringPath b1Num = createString("B_1_NUM");

    public final StringPath b1Ufp = createString("B_1_UFP");

    public final StringPath b2Num = createString("B_2_NUM");

    public final StringPath b2Ufp = createString("B_2_UFP");

    public final StringPath b3Num = createString("B_3_NUM");

    public final StringPath b3Ufp = createString("B_3_UFP");
    
    public final StringPath b4Num = createString("B_4_NUM");

    public final StringPath b4Ufp = createString("B_4_UFP");

    public final StringPath b5Num = createString("B_5_NUM");

    public final StringPath b5Ufp = createString("B_5_UFP");

    public final StringPath bfpNum = createString("BFP_NUM");

    public final StringPath bfpUfp = createString("BFP_UFP");

    public final StringPath c1Num = createString("C_1_NUM");

    public final StringPath c1Ufp = createString("C_1_UFP");

    public final StringPath c2Num = createString("C_2_NUM");

    public final StringPath c2Ufp = createString("C_2_UFP");

    public final StringPath cicloDiVita = createString("CICLO_DI_VITA");

    public final StringPath confine = createString("CONFINE");

    public final StringPath costo = createString("COSTO");

    public final StringPath crudNum = createString("CRUD_NUM");

    public final StringPath crudUfp = createString("CRUD_UFP");

    public final StringPath d1Num = createString("D_1_NUM");

    public final StringPath d1Ufp = createString("D_1_UFP");

    public final StringPath d2Num = createString("D_2_NUM");

    public final StringPath d2Ufp = createString("D_2_UFP");

    public final StringPath dataAvvio = createString("DT_AVVIO");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath dataConsolidamento = createString("DT_CONSOLIDAMENTO");

    public final StringPath dataCreazione = createString("DT_CREAZIONE");

    public final StringPath dataDismissione = createString("DT_DISMISSIONE");

    public final StringPath dataFineEffettiva = createString("DT_FINE_EFFETTIVA");

    public final StringPath dataInizioEsercizio = createString("DT_INIZIO_ESERCIZIO");

    public final StringPath dataRiferimento = createString("DT_RIFERIMENTO");

    public final NumberPath<Integer> dmalmStgMisuraPk = createNumber("DMALM_STG_MISURA_PK", Integer.class);

    public final StringPath durataEffettiva = createString("DURATA_EFFETTIVA");

    public final StringPath eiNum = createString("EI_NUM");

    public final StringPath eiUfp = createString("EI_UFP");

    public final StringPath eifNum = createString("EIF_NUM");

    public final StringPath eifUfp = createString("EIF_UFP");

    public final StringPath eoNum = createString("EO_NUM");

    public final StringPath eoUfp = createString("EO_UFP");

    public final StringPath eqNum = createString("EQ_NUM");

    public final StringPath eqUfp = createString("EQ_UFP");

    public final StringPath esperienza = createString("ESPERIENZA");

    public final StringPath faseCicloDiVita = createString("FASE_CICLO_DI_VITA");

    public final StringPath fonti = createString("FONTI");

    public final StringPath fpNonPesatiMax = createString("FP_NON_PESATI_MAX");

    public final StringPath fpNonPesatiMin = createString("FP_NON_PESATI_MIN");

    public final StringPath fpNonPesatiUfp = createString("FP_NON_PESATI_UFP");

    public final StringPath fpPesatiMax = createString("FP_PESATI_MAX");

    public final StringPath fpPesatiMin = createString("FP_PESATI_MIN");

    public final StringPath fpPesatiUfp = createString("FP_PESATI_UFP");

    public final StringPath frequenzaUtilizzo = createString("FREQUENZA_UTILIZZO");

    public final StringPath fuNum = createString("FU_NUM");

    public final StringPath fuUfp = createString("FU_UFP");

    public final StringPath gdgNum = createString("GDG_NUM");

    public final StringPath gdgUfp = createString("GDG_UFP");

    public final StringPath geiNum = createString("GEI_NUM");

    public final StringPath geiUfp = createString("GEI_UFP");

    public final StringPath geifNum = createString("GEIF_NUM");

    public final StringPath geifUfp = createString("GEIF_UFP");

    public final StringPath geoNum = createString("GEO_NUM");

    public final StringPath geoUfp = createString("GEO_UFP");

    public final StringPath geqNum = createString("GEQ_NUM");

    public final StringPath geqUfp = createString("GEQ_UFP");

    public final StringPath gilfNum = createString("GILF_NUM");

    public final StringPath gilfUfp = createString("GILF_UFP");

    public final StringPath gpNum = createString("GP_NUM");

    public final StringPath gpUfp = createString("GP_UFP");
    
    public final NumberPath<Integer> idAsm = createNumber("ID_ASM",Integer.class);

    public final NumberPath<Integer> idMsr = createNumber("ID_MSR",Integer.class);

    public final NumberPath<Integer> idProgetto = createNumber("ID_PROGETTO",Integer.class);

    public final StringPath ifpNum = createString("IFP_NUM");

    public final StringPath ifpUfp = createString("IFP_UFP");

    public final StringPath ilfNum = createString("ILF_NUM");

    public final StringPath ilfUfp = createString("ILF_UFP");

    public final StringPath impegnoEffettivo = createString("IMPEGNO_EFFETTIVO");

    public final StringPath includeInBenchmarkingDb = createString("INCLUDE_IN_BENCHMARKING_DB");

    public final StringPath includiInDbPatrimonio = createString("INCLUDI_IN_DB_PATRIMONIO");

    public final StringPath ldgNum = createString("LDG_NUM");

    public final StringPath ldgUfp = createString("LDG_UFP");

    public final StringPath linkDocumentale = createString("LINK_DOCUMENTALE");

    public final StringPath metodo = createString("METODO");

    public final StringPath mfNum = createString("MF_NUM");

    public final StringPath mfUfp = createString("MF_UFP");

    public final StringPath mldgNum = createString("MLDG_NUM");

    public final StringPath mldgUfp = createString("MLDG_UFP");

    public final StringPath modello = createString("MODELLO");

    public final StringPath mpNum = createString("MP_NUM");

    public final StringPath mpUfp = createString("MP_UFP");

    public final StringPath nomeMisura = createString("NOME_MISURA");

    public final StringPath nomeProgetto = createString("NOME_PROGETTO");

    public final StringPath noteAsm = createString("NOTE_ASM");

    public final StringPath noteMsr = createString("NOTE_MSR");

    public final StringPath notePrj = createString("NOTE_PRJ");

    public final StringPath numeroUtenti = createString("NUMERO_UTENTI");

    public final StringPath pAppAccAuthLastUpdate = createString("P_APP_ACC_AUTH_LAST_UPDATE");

    public final StringPath pAppCdAltreAsmCommonServ = createString("P_APP_CD_ALTRE_ASM_COMMON_SERV");

    public final StringPath pAppCdAmbitoManAttuale = createString("P_APP_CD_AMBITO_MAN_ATTUALE");

    public final StringPath pAppCodAmbitoManFuturo = createString("P_APP_CD_AMBITO_MAN_FUTURO");

    public final StringPath pAppCodAsmConfinanti = createString("P_APP_CD_ASM_CONFINANTI");

    public final StringPath pAppCodDirezioneDemand = createString("P_APP_CD_DIREZIONE_DEMAND");

    public final StringPath pAppCodFlussiIoAsm = createString("P_APP_CD_FLUSSI_IO_ASM");

    public final StringPath pAppDataFineValiditaAsm = createString("P_APP_DT_FINE_VALIDITA_ASM");

    public final StringPath pAppDataInizioValiditaAsm = createString("P_APP_DT_INIZIO_VALIDITA_ASM");

    public final StringPath pAppDataUltimoAggiorn = createString("P_APP_DT_ULTIMO_AGGIORN");

    public final StringPath pAppDenomSistTerziConfin = createString("P_APP_DN_SIST_TERZI_CONFIN");

    public final StringPath pAppDenomUtentiFinaliAsm = createString("P_APP_DN_UTENTI_FINALI_ASM");

    public final StringPath pAppDenominazioneAsm = createString("P_APP_DN_ASM");

    public final StringPath pAppFlagDamisurarePatrFp = createString("P_APP_FL_DAMISURARE_PATR_FP");

    public final StringPath pAppFlagInManutenzione = createString("P_APP_FL_IN_MANUTENZIONE");

    public final StringPath pAppFlagMisurareSvimevFp = createString("P_APP_FL_MISURARE_SVIMEV_FP");

    public final StringPath pAppFlagServizioComune = createString("P_APP_FL_SERVIZIO_COMUNE");

    public final StringPath pAppIndicValidazioneAsm = createString("P_APP_INDIC_VALIDAZIONE_ASM");

    public final StringPath pAppNomeAuthLastUpdate = createString("P_APP_NOME_AUTH_LAST_UPDATE");

    public final StringPath pPrjADisposizione01 = createString("P_PRJ_A_DISPOSIZIONE_01");

    public final StringPath pPrjADisposizione02 = createString("P_PRJ_A_DISPOSIZIONE_02");

    public final StringPath pPrjAmbTecnPortali = createString("P_PRJ_AMB_TECN_PORTALI");

    public final StringPath pPrjAuditIndexVerify = createString("P_PRJ_AUDIT_INDEX_VERIFY");

    public final StringPath pPrjAuditMonitore = createString("P_PRJ_AUDIT_MONITORE");

    public final StringPath pPrjCodRdi = createString("P_PRJ_CD_RDI");

    //public final StringPath pPrjDtConsegnaMppForn = createString("P_PRJ_DT_CONSEGNA_MPP_FORN");

    public final StringPath pPrjFccFattCorrezTotal = createString("P_PRJ_FCC_FATT_CORREZ_TOTAL");

    public final StringPath pPrjFlAmbTecTransBatRep = createString("P_PRJ_FL_AMB_TEC_TRANS_BAT_REP");

    public final StringPath pPrjFlAmbTecnPiatEnterpr = createString("P_PRJ_FL_AMB_TECN_PIAT_ENTERPR");

    public final StringPath pPrjFlAmbitoTecnFuturo01 = createString("P_PRJ_FL_AMBITO_TECN_FUTURO01");

    public final StringPath pPrjFlAmbitoTecnFuturo02 = createString("P_PRJ_FL_AMBITO_TECN_FUTURO02");

    public final StringPath pPrjFlApplLgFpEdma = createString("P_PRJ_FL_APPL_LG_FP_EDMA");

    public final StringPath pPrjFlApplLgFpWeb = createString("P_PRJ_FL_APPL_LG_FP_WEB");

    public final StringPath pPrjFlApplicLgFpDwh = createString("P_PRJ_FL_APPLIC_LG_FP_DWH");

    public final StringPath pPrjFlApplicLgFpFuturo01 = createString("P_PRJ_FL_APPLIC_LG_FP_FUTURO01");

    public final StringPath pPrjFlApplicLgFpFuturo02 = createString("P_PRJ_FL_APPLIC_LG_FP_FUTURO02");

    public final StringPath pPrjFlagAmbTecnGis = createString("P_PRJ_FL_AMB_TECN_GIS");

    public final StringPath pPrjFlagApplLgFpGis = createString("P_PRJ_FL_APPL_LG_FP_GIS");

    public final StringPath pPrjFlagApplLgFpMware = createString("P_PRJ_FL_APPL_LG_FP_MWARE");

    public final StringPath pPrjFornitoreMpp = createString("P_PRJ_FORNITORE_MPP");

    public final StringPath pPrjFornitoreSviluppoMev = createString("P_PRJ_FORNITORE_SVILUPPO_MEV");

    public final StringPath pPrjImportoAConsuntivo = createString("P_PRJ_IMPORTO_A_CONSUNTIVO");

    public final StringPath pPrjImportoRdiAPreventivo = createString("P_PRJ_IMPORTO_RDI_A_PREVENTIVO");

    public final StringPath pPrjIndexAlmValidProgAsm = createString("P_PRJ_INDEX_ALM_VALID_PROG_ASM");

    public final StringPath pPrjMfcAConsuntivo = createString("P_PRJ_MFC_A_CONSUNTIVO");

    public final StringPath pPrjMfcAPreventivo = createString("P_PRJ_MFC_A_PREVENTIVO");

    public final StringPath pPrjMpPercentCicloDiVita = createString("P_PRJ_MP_PERCENT_CICLO_DI_VITA");

    public final StringPath pPrjPunPrezzoUnitNominal = createString("P_PRJ_PUN_PREZZO_UNIT_NOMINAL");

    public final StringPath permissions = createString("PERMISSIONS");

    public final StringPath pfNum = createString("PF_NUM");

    public final StringPath pfUfp = createString("PF_UFP");

    public final StringPath postVerAddCfp = createString("POST_VER_ADD_CFP");

    public final StringPath postVerChg = createString("POST_VER_CHG");

    public final StringPath postVerDel = createString("POST_VER_DEL");

    public final StringPath postVerFp = createString("POST_VER_FP");

    public final StringPath preVerAddCfp = createString("PRE_VER_ADD_CFP");

    public final StringPath preVerChg = createString("PRE_VER_CHG");

    public final StringPath preVerDel = createString("PRE_VER_DEL");

    public final StringPath preVerFp = createString("PRE_VER_FP");

    public final StringPath prjCls = createString("PRJ_CLS");

    public final StringPath proprietaLegale = createString("PROPRIETA_LEGALE");

    public final StringPath responsabile = createString("RESPONSABILE");

    public final StringPath scopo = createString("SCOPO");

    public final StringPath staffMedio = createString("STAFF_MEDIO");

    public final StringPath statoMisura = createString("STATO_MISURA");

    public final StringPath tipoProgetto = createString("TIPO_PROGETTO");

    public final StringPath tpNum = createString("TP_NUM");

    public final StringPath tpUfp = createString("TP_UFP");

    public final StringPath ugdgNum = createString("UGDG_NUM");

    public final StringPath ugdgUfp = createString("UGDG_UFP");

    public final StringPath ugepNum = createString("UGEP_NUM");

    public final StringPath ugepUfp = createString("UGEP_UFP");

    public final StringPath ugoNum = createString("UGO_NUM");

    public final StringPath ugoUfp = createString("UGO_UFP");

    public final StringPath ugpNum = createString("UGP_NUM");

    public final StringPath ugpUfp = createString("UGP_UFP");

    public final StringPath utenteMisuratore = createString("UTENTE_MISURATORE");

    public final StringPath utilizzata = createString("UTILIZZATA");

    public final StringPath vafPredefinito = createString("VAF_PREDEFINITO");

    public final StringPath verDelta = createString("VER_DELTA");

    public final StringPath verDeltaPercent = createString("VER_DELTA_PERCENT");

    public final StringPath verEnd = createString("VER_END");

    public final StringPath verStart = createString("VER_START");

    public final StringPath versioneMsr = createString("VERSIONE_MSR");

    public final StringPath versionePrj = createString("VERSIONE_PRJ");
    
    public final NumberPath<Long> chiaveNaturale = createNumber("CHIAVE_NATURALE", Long.class);
    
    public final DateTimePath<java.sql.Timestamp> dtPrevistaProssimaMpp = createDateTime("DT_PREVISTA_PROSSIMA_MPP_ASM", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> fip01InizioEsercizio = createDateTime("FIP01_INIZIO_ESERCIZIO_ASM", java.sql.Timestamp.class);
    
    public final NumberPath<Integer> fip02IndiceQualita = createNumber("FIP02_INDICE_QUALITA_DOC_ASM", Integer.class);
    
    public final StringPath fip03ComplessEserc = createString("FIP03_COMP_PT_SVIL_ESERC_ASM");
    
    public final NumberPath<Integer> fip04NrPiattaforma = createNumber("FIP04_NR_PT_TGT_ESERC_ASM", Integer.class);
    
    public final StringPath fip07LingProgPrincipale = createString("FIP07_LING_PROG_PRIN_REAL_ASM");
    
    public final NumberPath<Integer> fip10GradoAccessibilita = createNumber("FIP10_GRADO_ACCESSIBILITA_ASM", Integer.class);
    
    public final StringPath fip11GradoQualitaCod = createString("FIP11_GRADO_QUALITA_COD_ASM");
    
    public final StringPath fip12UtilizzoFramework = createString("FIP12_UT_FRAMEWORK_AZ_ASM");
    
    public final NumberPath<Integer> fip13ComplessitaAlg = createNumber("FIP13_COMP_ALG_SW_ASM", Integer.class);
    
    public final StringPath fip15LivelloCura = createString("FIP15_LV_CURA_GRAF_INT_UT_ASM");
    
    public final com.mysema.query.sql.PrimaryKey<DmalmStgMisura> PK_DMALM_STG_MISURA = createPrimaryKey(dmalmStgMisuraPk);

    public QDmalmStgMisura(String variable) {
        super(DmalmStgMisura.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_MISURA");
    }

    public QDmalmStgMisura(Path<? extends DmalmStgMisura> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_MISURA");
    }

    public QDmalmStgMisura(PathMetadata<?> metadata) {
        super(DmalmStgMisura.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_MISURA");
    }

}

