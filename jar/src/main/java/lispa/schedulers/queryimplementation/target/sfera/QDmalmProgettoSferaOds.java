package lispa.schedulers.queryimplementation.target.sfera;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.sfera.DmalmProgettoSferaOds;


import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDmalmProgettoSferaOds is a Querydsl query type for DmalmProgettoSferaOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProgettoSferaOds extends com.mysema.query.sql.RelationalPathBase<DmalmProgettoSferaOds> {

    private static final long serialVersionUID = 795627839;

    public static final QDmalmProgettoSferaOds dmalmProgettoSferaOds = new QDmalmProgettoSferaOds("DMALM_PROGETTO_SFERA_ODS");

    public final NumberPath<Long> chiaveNaturale = createNumber("CHIAVE_NATURALE", Long.class);

    public final NumberPath<Integer> costo = createNumber("COSTO", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dataAvvio = createDateTime("DATA_AVVIO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataFineEffettiva = createDateTime("DATA_FINE_EFFETTIVA", java.sql.Timestamp.class);

    public final NumberPath<Integer> dmalmProgettoSferaPk = createNumber("DMALM_PROGETTO_SFERA_PK", Integer.class);

    public final NumberPath<Integer> dmalmStgMisuraPk = createNumber("DMALM_STG_MISURA_PK", Integer.class);

    public final NumberPath<Integer> durataEffettiva = createNumber("DURATA_EFFETTIVA", Integer.class);

    public final NumberPath<Short> idProgetto = createNumber("ID_PROGETTO", Short.class);

    public final NumberPath<Integer> impegnoEffettivo = createNumber("IMPEGNO_EFFETTIVO", Integer.class);

    public final BooleanPath includeInBenchmarkingDb = createBoolean("INCLUDE_IN_BENCHMARKING_DB");

    public final StringPath nomeProgetto = createString("NOME_PROGETTO");

    public final StringPath notePrj = createString("NOTE_PRJ");

    public final StringPath pPrjADisposizione01 = createString("P_PRJ_A_DISPOSIZIONE_01");

    public final StringPath pPrjADisposizione02 = createString("P_PRJ_A_DISPOSIZIONE_02");

    public final StringPath pPrjADisposizione03 = createString("P_PRJ_A_DISPOSIZIONE_03");

    public final StringPath pPrjADisposizione04 = createString("P_PRJ_A_DISPOSIZIONE_04");

    public final BooleanPath pPrjAmbTecnPortali = createBoolean("P_PRJ_AMB_TECN_PORTALI");

    public final StringPath pPrjAuditIndexVerify = createString("P_PRJ_AUDIT_INDEX_VERIFY");

    public final StringPath pPrjAuditMonitore = createString("P_PRJ_AUDIT_MONITORE");

    public final NumberPath<Integer> pPrjCodRdi = createNumber("P_PRJ_COD_RDI", Integer.class);

    public final DateTimePath<java.sql.Timestamp> pPrjDtConsegnaMppForn = createDateTime("P_PRJ_DT_CONSEGNA_MPP_FORN", java.sql.Timestamp.class);

    public final NumberPath<Double> pPrjFccFattCorrezTotal = createNumber("P_PRJ_FCC_FATT_CORREZ_TOTAL", Double.class);

    public final BooleanPath pPrjFlAmbTecTransBatRep = createBoolean("P_PRJ_FL_AMB_TEC_TRANS_BAT_REP");

    public final BooleanPath pPrjFlAmbTecnPiatEnterpr = createBoolean("P_PRJ_FL_AMB_TECN_PIAT_ENTERPR");

    public final BooleanPath pPrjFlAmbitoTecnFuturo01 = createBoolean("P_PRJ_FL_AMBITO_TECN_FUTURO01");

    public final BooleanPath pPrjFlAmbitoTecnFuturo02 = createBoolean("P_PRJ_FL_AMBITO_TECN_FUTURO02");

    public final BooleanPath pPrjFlApplLgFpEdma = createBoolean("P_PRJ_FL_APPL_LG_FP_EDMA");

    public final BooleanPath pPrjFlApplLgFpWeb = createBoolean("P_PRJ_FL_APPL_LG_FP_WEB");

    public final BooleanPath pPrjFlApplicLgFpDwh = createBoolean("P_PRJ_FL_APPLIC_LG_FP_DWH");

    public final BooleanPath pPrjFlApplicLgFpFuturo01 = createBoolean("P_PRJ_FL_APPLIC_LG_FP_FUTURO01");

    public final BooleanPath pPrjFlApplicLgFpFuturo02 = createBoolean("P_PRJ_FL_APPLIC_LG_FP_FUTURO02");

    public final BooleanPath pPrjFlagAmbTecnGis = createBoolean("P_PRJ_FLAG_AMB_TECN_GIS");

    public final BooleanPath pPrjFlagApplLgFpGis = createBoolean("P_PRJ_FLAG_APPL_LG_FP_GIS");

    public final BooleanPath pPrjFlagApplLgFpMware = createBoolean("P_PRJ_FLAG_APPL_LG_FP_MWARE");

    public final StringPath pPrjFornitoreMpp = createString("P_PRJ_FORNITORE_MPP");

    public final StringPath pPrjFornitoreSviluppoMev = createString("P_PRJ_FORNITORE_SVILUPPO_MEV");

    public final NumberPath<Double> pPrjImportoAConsuntivo = createNumber("P_PRJ_IMPORTO_A_CONSUNTIVO", Double.class);

    public final NumberPath<Double> pPrjImportoRdiAPreventivo = createNumber("P_PRJ_IMPORTO_RDI_A_PREVENTIVO", Double.class);

    public final BooleanPath pPrjIndexAlmValidProgAsm = createBoolean("P_PRJ_INDEX_ALM_VALID_PROG_ASM");

    public final NumberPath<Double> pPrjMfcAConsuntivo = createNumber("P_PRJ_MFC_A_CONSUNTIVO", Double.class);

    public final NumberPath<Double> pPrjMfcAPreventivo = createNumber("P_PRJ_MFC_A_PREVENTIVO", Double.class);

    public final NumberPath<Double> pPrjMpPercentCicloDiVita = createNumber("P_PRJ_MP_PERCENT_CICLO_DI_VITA", Double.class);

    public final NumberPath<Double> pPrjPunPrezzoUnitNominal = createNumber("P_PRJ_PUN_PREZZO_UNIT_NOMINAL", Double.class);

    public final StringPath prjCls = createString("PRJ_CLS");

    public final NumberPath<Integer> staffMedio = createNumber("STAFF_MEDIO", Integer.class);

    public final StringPath tipoProgetto = createString("TIPO_PROGETTO");

    public final StringPath versionePrj = createString("VERSIONE_PRJ");

    public final com.mysema.query.sql.PrimaryKey<DmalmProgettoSferaOds> sysC0037837 = createPrimaryKey(dmalmProgettoSferaPk);

    public QDmalmProgettoSferaOds(String variable) {
        super(DmalmProgettoSferaOds.class, forVariable(variable), "DMALM", "DMALM_PROGETTO_SFERA_ODS");
    }

    public QDmalmProgettoSferaOds(Path<? extends DmalmProgettoSferaOds> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_PROGETTO_SFERA_ODS");
    }

    public QDmalmProgettoSferaOds(PathMetadata<?> metadata) {
        super(DmalmProgettoSferaOds.class, metadata, "DMALM", "DMALM_PROGETTO_SFERA_ODS");
    }

}

