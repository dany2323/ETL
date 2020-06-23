package lispa.schedulers.queryimplementation.target.sfera;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.sfera.DmalmProgettoSfera;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmProgettoSfera is a Querydsl query type for DmalmProgettoSfera
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProgettoSfera extends com.mysema.query.sql.RelationalPathBase<DmalmProgettoSfera> {

    private static final long serialVersionUID = -1473101953;

    public static final QDmalmProgettoSfera dmalmProgettoSfera = new QDmalmProgettoSfera("DMALM_PROGETTO_SFERA");


    public final NumberPath<Integer> costo = createNumber("COSTO", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dataAvvio = createDateTime("DT_AVVIO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataFineEffettiva = createDateTime("DT_FINE_EFFETTIVA", java.sql.Timestamp.class);

    public final NumberPath<Integer> dmalmProgettoSferaPk = createNumber("DMALM_PROGETTO_SFERA_PK", Integer.class);

    public final NumberPath<Integer> dmalmStgMisuraPk = createNumber("DMALM_STG_MISURA_PK", Integer.class);

    public final NumberPath<Integer> durataEffettiva = createNumber("DURATA_EFFETTIVA", Integer.class);

    public final NumberPath<Short> idProgetto = createNumber("ID_PROGETTO", Short.class);

    public final NumberPath<Integer> impegnoEffettivo = createNumber("IMPEGNO_EFFETTIVO", Integer.class);

    public final NumberPath<Short> includeInBenchmarkingDb = createNumber("INCLUDE_IN_BENCHMARKING_DB",Short.class);

    public final StringPath nomeProgetto = createString("NOME_PROGETTO");

    public final StringPath notePrj = createString("NOTE_PRJ");

    public final StringPath pPrjADisposizione01 = createString("P_PRJ_A_DISPOSIZIONE_01");

    public final StringPath pPrjADisposizione02 = createString("P_PRJ_A_DISPOSIZIONE_02");


    public final NumberPath<Short> pPrjAmbTecnPortali = createNumber("P_PRJ_AMB_TECN_PORTALI",Short.class);

    public final StringPath pPrjAuditIndexVerify = createString("P_PRJ_AUDIT_INDEX_VERIFY");

    public final StringPath pPrjAuditMonitore = createString("P_PRJ_AUDIT_MONITORE");

    public final NumberPath<Integer> pPrjCodRdi = createNumber("P_PRJ_CD_RDI", Integer.class);

    //public final DateTimePath<java.sql.Timestamp> pPrjDtConsegnaMppForn = createDateTime("P_PRJ_DT_CONSEGNA_MPP_FORN", java.sql.Timestamp.class);

    public final NumberPath<Double> pPrjFccFattCorrezTotal = createNumber("P_PRJ_FCC_FATT_CORREZ_TOTAL", Double.class);

    public final NumberPath<Short> pPrjFlAmbTecTransBatRep = createNumber("P_PRJ_FL_AMB_TEC_TRANS_BAT_REP",Short.class);

    public final NumberPath<Short> pPrjFlAmbTecnPiatEnterpr = createNumber("P_PRJ_FL_AMB_TECN_PIAT_ENTERPR",Short.class);

    public final NumberPath<Short> pPrjFlAmbitoTecnFuturo01 = createNumber("P_PRJ_FL_AMBITO_TECN_FUTURO01",Short.class);

    public final NumberPath<Short> pPrjFlAmbitoTecnFuturo02 = createNumber("P_PRJ_FL_AMBITO_TECN_FUTURO02",Short.class);

    public final NumberPath<Short> pPrjFlApplLgFpEdma = createNumber("P_PRJ_FL_APPL_LG_FP_EDMA",Short.class);

    public final NumberPath<Short> pPrjFlApplLgFpWeb = createNumber("P_PRJ_FL_APPL_LG_FP_WEB",Short.class);

    public final NumberPath<Short> pPrjFlApplicLgFpDwh = createNumber("P_PRJ_FL_APPLIC_LG_FP_DWH",Short.class);

    public final NumberPath<Short> pPrjFlApplicLgFpFuturo01 = createNumber("P_PRJ_FL_APPLIC_LG_FP_FUTURO01",Short.class);

    public final NumberPath<Short> pPrjFlApplicLgFpFuturo02 = createNumber("P_PRJ_FL_APPLIC_LG_FP_FUTURO02",Short.class);

    public final NumberPath<Short> pPrjFlagAmbTecnGis = createNumber("P_PRJ_FL_AMB_TECN_GIS",Short.class);

    public final NumberPath<Short> pPrjFlagApplLgFpGis = createNumber("P_PRJ_FL_APPL_LG_FP_GIS",Short.class);

    public final NumberPath<Short> pPrjFlagApplLgFpMware = createNumber("P_PRJ_FL_APPL_LG_FP_MWARE",Short.class);

    public final StringPath pPrjFornitoreMpp = createString("P_PRJ_FORNITORE_MPP");

    public final StringPath pPrjFornitoreSviluppoMev = createString("P_PRJ_FORNITORE_SVILUPPO_MEV");

    public final NumberPath<Double> pPrjImportoAConsuntivo = createNumber("P_PRJ_IMPORTO_A_CONSUNTIVO", Double.class);

    public final NumberPath<Double> pPrjImportoRdiAPreventivo = createNumber("P_PRJ_IMPORTO_RDI_A_PREVENTIVO", Double.class);

    public final NumberPath<Short> pPrjIndexAlmValidProgAsm = createNumber("P_PRJ_INDEX_ALM_VALID_PROG_ASM",Short.class);

    public final NumberPath<Double> pPrjMfcAConsuntivo = createNumber("P_PRJ_MFC_A_CONSUNTIVO", Double.class);

    public final NumberPath<Double> pPrjMfcAPreventivo = createNumber("P_PRJ_MFC_A_PREVENTIVO", Double.class);

    public final NumberPath<Double> pPrjMpPercentCicloDiVita = createNumber("P_PRJ_MP_PERCENT_CICLO_DI_VITA", Double.class);

    public final NumberPath<Double> pPrjPunPrezzoUnitNominal = createNumber("P_PRJ_PUN_PREZZO_UNIT_NOMINAL", Double.class);

    public final StringPath prjCls = createString("PRJ_CLS");

    public final NumberPath<Integer> staffMedio = createNumber("STAFF_MEDIO", Integer.class);

    public final StringPath tipoProgetto = createString("TIPO_PROGETTO");
    
    public final StringPath cicloDiVita = createString("CICLO_DI_VITA");

    public final StringPath versionePrj = createString("VERSIONE_PRJ");
    
    public final DateTimePath<java.sql.Timestamp> dataModifica = createDateTime("DT_MODIFICA", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<DmalmProgettoSfera> sysC0037833 = createPrimaryKey(dmalmProgettoSferaPk);

	public final NumberPath<Short> idAsm = createNumber("ID_ASM", Short.class); 
	
	public final NumberPath<Integer> dmalmAsmFk = createNumber("DMALM_ASM_FK", Integer.class);
	
	public final StringPath annullato = createString("ANNULLATO");

    public QDmalmProgettoSfera(String variable) {
        super(DmalmProgettoSfera.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_SFERA");
    }

    public QDmalmProgettoSfera(Path<? extends DmalmProgettoSfera> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_SFERA");
    }

    public QDmalmProgettoSfera(PathMetadata<?> metadata) {
        super(DmalmProgettoSfera.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_SFERA");
    }

}

