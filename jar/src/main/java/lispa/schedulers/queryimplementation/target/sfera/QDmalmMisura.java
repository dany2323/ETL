package lispa.schedulers.queryimplementation.target.sfera;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.sfera.DmalmMisura;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmMisura is a Querydsl query type for DmalmMisura
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmMisura extends com.mysema.query.sql.RelationalPathBase<DmalmMisura> {

    private static final long serialVersionUID = -1463557433;

    public static final QDmalmMisura dmalmMisura = new QDmalmMisura("DMALM_MISURA");

    public final NumberPath<Double> a1Num = createNumber("A_1_NUM", Double.class);

    public final NumberPath<Double> a1Ufp = createNumber("A_1_UFP", Double.class);

    public final NumberPath<Double> a2Num = createNumber("A_2_NUM", Double.class);

    public final NumberPath<Double> a2Ufp = createNumber("A_2_UFP", Double.class);

    public final NumberPath<Double> adjmax = createNumber("ADJMAX", Double.class);

    public final NumberPath<Double> adjmin = createNumber("ADJMIN", Double.class);

    public final NumberPath<Double> adjufp = createNumber("ADJUFP", Double.class);

    public final StringPath ambito = createString("AMBITO");

    public final StringPath approccio = createString("APPROCCIO");

    public final NumberPath<Double> b1Num = createNumber("B_1_NUM", Double.class);

    public final NumberPath<Double> b1Ufp = createNumber("B_1_UFP", Double.class);

    public final NumberPath<Double> b2Num = createNumber("B_2_NUM", Double.class);

    public final NumberPath<Double> b2Ufp = createNumber("B_2_UFP", Double.class);

    public final NumberPath<Double> b3Num = createNumber("B_3_NUM", Double.class);

    public final NumberPath<Double> b3Ufp = createNumber("B_3_UFP", Double.class);

    public final NumberPath<Double> b5Num = createNumber("B_5_NUM", Double.class);

    public final NumberPath<Double> b5Ufp = createNumber("B_5_UFP", Double.class);

    public final NumberPath<Double> bfpNum = createNumber("BFP_NUM", Double.class);

    public final NumberPath<Double> bfpUfp = createNumber("BFP_UFP", Double.class);

    public final NumberPath<Double> c1Num = createNumber("C_1_NUM", Double.class);

    public final NumberPath<Double> c1Ufp = createNumber("C_1_UFP", Double.class);

    public final NumberPath<Double> c2Num = createNumber("C_2_NUM", Double.class);

    public final NumberPath<Double> c2Ufp = createNumber("C_2_UFP", Double.class);

    public final StringPath confine = createString("CONFINE");

    public final NumberPath<Double> crudNum = createNumber("CRUD_NUM", Double.class);

    public final NumberPath<Double> crudUfp = createNumber("CRUD_UFP", Double.class);

    public final NumberPath<Double> d1Num = createNumber("D_1_NUM", Double.class);

    public final NumberPath<Double> d1Ufp = createNumber("D_1_UFP", Double.class);

    public final NumberPath<Double> d2Num = createNumber("D_2_NUM", Double.class);

    public final NumberPath<Double> d2Ufp = createNumber("D_2_UFP", Double.class);

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataConsolidamento = createDateTime("DT_CONSOLIDAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataCreazione = createDateTime("DT_CREAZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataFineVerificafp = createDateTime("DT_FINE_VERIFICA_FP", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioVerificaFp = createDateTime("DT_INIZIO_VERIFICA_FP", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataRiferimento = createDateTime("DT_RIFERIMENTO", java.sql.Timestamp.class);

    public final NumberPath<Integer> dmalmMisuraPk = createNumber("DMALM_MISURA_PK", Integer.class);

    public final NumberPath<Integer> dmalmPrjFk = createNumber("DMALM_PRJ_FK", Integer.class);

    public final NumberPath<Integer> dmalmStgMisuraPk = createNumber("DMALM_STG_MISURA_PK", Integer.class);

    public final NumberPath<Double> eiNum = createNumber("EI_NUM", Double.class);

    public final NumberPath<Double> eiUfp = createNumber("EI_UFP", Double.class);

    public final NumberPath<Double> eifNum = createNumber("EIF_NUM", Double.class);

    public final NumberPath<Double> eifUfp = createNumber("EIF_UFP", Double.class);

    public final NumberPath<Double> eoNum = createNumber("EO_NUM", Double.class);

    public final NumberPath<Double> eoUfp = createNumber("EO_UFP", Double.class);

    public final NumberPath<Double> eqNum = createNumber("EQ_NUM", Double.class);

    public final NumberPath<Double> eqUfp = createNumber("EQ_UFP", Double.class);

    public final StringPath esperienza = createString("ESPERIENZA");

    public final StringPath faseCicloDiVita = createString("FASE_CICLO_DI_VITA");

    public final StringPath fonti = createString("FONTI");

    public final NumberPath<Double> fpNonPesatiMax = createNumber("FP_NON_PESATI_MAX", Double.class);

    public final NumberPath<Double> fpNonPesatiMin = createNumber("FP_NON_PESATI_MIN", Double.class);

    public final NumberPath<Double> fpNonPesatiUfp = createNumber("FP_NON_PESATI_UFP", Double.class);

    public final NumberPath<Double> fpPesatiMax = createNumber("FP_PESATI_MAX", Double.class);

    public final NumberPath<Double> fpPesatiMin = createNumber("FP_PESATI_MIN", Double.class);

    public final NumberPath<Double> fpPesatiUfp = createNumber("FP_PESATI_UFP", Double.class);

    public final NumberPath<Double> fuNum = createNumber("FU_NUM", Double.class);

    public final NumberPath<Double> fuUfp = createNumber("FU_UFP", Double.class);

    public final NumberPath<Double> gdgNum = createNumber("GDG_NUM", Double.class);

    public final NumberPath<Double> gdgUfp = createNumber("GDG_UFP", Double.class);

    public final NumberPath<Double> geiNum = createNumber("GEI_NUM", Double.class);

    public final NumberPath<Double> geiUfp = createNumber("GEI_UFP", Double.class);

    public final NumberPath<Double> geifNum = createNumber("GEIF_NUM", Double.class);

    public final NumberPath<Double> geifUfp = createNumber("GEIF_UFP", Double.class);

    public final NumberPath<Double> geoNum = createNumber("GEO_NUM", Double.class);

    public final NumberPath<Double> geoUfp = createNumber("GEO_UFP", Double.class);

    public final NumberPath<Double> geqNum = createNumber("GEQ_NUM", Double.class);

    public final NumberPath<Double> geqUfp = createNumber("GEQ_UFP", Double.class);

    public final NumberPath<Double> gilfNum = createNumber("GILF_NUM", Double.class);

    public final NumberPath<Double> gilfUfp = createNumber("GILF_UFP", Double.class);

    public final NumberPath<Double> gpNum = createNumber("GP_NUM", Double.class);

    public final NumberPath<Double> gpUfp = createNumber("GP_UFP", Double.class);

    public final NumberPath<Integer> idMsr = createNumber("ID_MSR", Integer.class);

    public final NumberPath<Double> ifpNum = createNumber("IFP_NUM", Double.class);

    public final NumberPath<Double> ifpUfp = createNumber("IFP_UFP", Double.class);

    public final NumberPath<Double> ilfNum = createNumber("ILF_NUM", Double.class);

    public final NumberPath<Double> ilfUfp = createNumber("ILF_UFP", Double.class);

    public final NumberPath<Double> ldgNum = createNumber("LDG_NUM", Double.class);

    public final NumberPath<Double> ldgUfp = createNumber("LDG_UFP", Double.class);

    public final StringPath linkDocumentale = createString("LINK_DOCUMENTALE");

    public final StringPath metodo = createString("METODO");

    public final NumberPath<Double> mfNum = createNumber("MF_NUM", Double.class);

    public final NumberPath<Double> mfUfp = createNumber("MF_UFP", Double.class);

    public final NumberPath<Double> mldgNum = createNumber("MLDG_NUM", Double.class);

    public final NumberPath<Double> mldgUfp = createNumber("MLDG_UFP", Double.class);

    public final StringPath modello = createString("MODELLO");

    public final NumberPath<Double> mpNum = createNumber("MP_NUM", Double.class);

    public final NumberPath<Double> mpUfp = createNumber("MP_UFP", Double.class);

    public final StringPath nomeMisura = createString("NOME_MISURA");

    public final StringPath noteMsr = createString("NOTE_MSR");

    public final NumberPath<Double> percentualeDiScostamento = createNumber("PERCENTUALE_DI_SCOSTAMENTO", Double.class);

    public final NumberPath<Double> pfNum = createNumber("PF_NUM", Double.class);

    public final NumberPath<Double> pfUfp = createNumber("PF_UFP", Double.class);

    public final NumberPath<Integer> postVerAddCfp = createNumber("POST_VER_ADD_CFP", Integer.class);

    public final NumberPath<Integer> postVerChg = createNumber("POST_VER_CHG", Integer.class);

    public final NumberPath<Integer> postVerDel = createNumber("POST_VER_DEL", Integer.class);

    public final NumberPath<Integer> postVerFp = createNumber("POST_VER_FP", Integer.class);

    public final NumberPath<Integer> preVerAddCfp = createNumber("PRE_VER_ADD_CFP", Integer.class);

    public final NumberPath<Integer> preVerChg = createNumber("PRE_VER_CHG", Integer.class);

    public final NumberPath<Integer> preVerDel = createNumber("PRE_VER_DEL",  Integer.class);

    public final NumberPath<Integer> preVerFp = createNumber("PRE_VER_FP",  Integer.class);

    public final StringPath responsabile = createString("RESPONSABILE");

    public final StringPath scopo = createString("SCOPO");

    public final StringPath statoMisura = createString("STATO_MISURA");

    public final NumberPath<Double> tpNum = createNumber("TP_NUM", Double.class);

    public final NumberPath<Double> tpUfp = createNumber("TP_UFP", Double.class);

    public final NumberPath<Double> ugdgNum = createNumber("UGDG_NUM", Double.class);

    public final NumberPath<Double> ugdgUfp = createNumber("UGDG_UFP", Double.class);

    public final NumberPath<Double> ugepNum = createNumber("UGEP_NUM", Double.class);

    public final StringPath ugepUfp = createString("UGEP_UFP");

    public final NumberPath<Double> ugoNum = createNumber("UGO_NUM", Double.class);

    public final NumberPath<Double> ugoUfp = createNumber("UGO_UFP", Double.class);

    public final NumberPath<Double> ugpNum = createNumber("UGP_NUM", Double.class);

    public final NumberPath<Double> ugpUfp = createNumber("UGP_UFP", Double.class);

    public final StringPath utenteMisuratore = createString("UTENTE_MISURATORE");

    public final NumberPath<Double> valoreScostamento = createNumber("VALORE_SCOSTAMENTO", Double.class);

    public final StringPath versioneMsr = createString("VERSIONE_MSR");
    
    public final StringPath progettoSfera = createString("PROGETTO_SFERA");


    public final NumberPath<Double> rankStatoMisura = createNumber("RANK_STATO_MISURA", Double.class);
    
    public final DateTimePath<java.sql.Timestamp> dataStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataModifica = createDateTime("DT_MODIFICA", java.sql.Timestamp.class);
    
    public final NumberPath<Short> idProgetto = createNumber("ID_PROGETTO", Short.class);

    public final NumberPath<Short> idAsm = createNumber("ID_ASM", Short.class);
    
    public final com.mysema.query.sql.PrimaryKey<DmalmMisura> sysC0037755 = createPrimaryKey(dmalmMisuraPk);

    public final StringPath annullato = createString("ANNULLATO");

    public QDmalmMisura(String variable) {
        super(DmalmMisura.class, forVariable(variable), "DMALM", "DMALM_MISURA");
    }

    public QDmalmMisura(Path<? extends DmalmMisura> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_MISURA");
    }

    public QDmalmMisura(PathMetadata<?> metadata) {
        super(DmalmMisura.class, metadata, "DMALM", "DMALM_MISURA");
    }

}

