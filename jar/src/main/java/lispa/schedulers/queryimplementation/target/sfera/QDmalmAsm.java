package lispa.schedulers.queryimplementation.target.sfera;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.sfera.DmalmAsm;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmAsm is a Querydsl query type for DmalmAsm
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmAsm extends com.mysema.query.sql.RelationalPathBase<DmalmAsm> {

    private static final long serialVersionUID = 1221621181;

    public static final QDmalmAsm dmalmAsm = new QDmalmAsm("DMALM_ASM");

    public final StringPath appCls = createString("APP_CLS");

    public final StringPath applicazione = createString("APPLICAZIONE");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataDismissione = createDateTime("DT_DISMISSIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioEsercizio = createDateTime("DT_INIZIO_ESERCIZIO", java.sql.Timestamp.class);

//    public final StringPath dmalmAsmPk = createString("DMALM_ASM_PK");
    public final NumberPath<Integer> dmalmAsmPk = createNumber("DMALM_ASM_PK", Integer.class);
    
    public final NumberPath<Integer> dmalmStgMisuraPk = createNumber("DMALM_STG_MISURA_PK", Integer.class);

    public final StringPath frequenzaUtilizzo = createString("FREQUENZA_UTILIZZO");

    public final NumberPath<Short> idAsm = createNumber("ID_ASM", Short.class);

    public final NumberPath<Short> includiInDbPatrimonio = createNumber("INCLUDI_IN_DB_PATRIMONIO", Short.class);

    public final StringPath noteAsm = createString("NOTE_ASM");

    public final NumberPath<Short> numeroUtenti = createNumber("NUMERO_UTENTI", Short.class);

    public final StringPath pAppAccAuthLastUpdate = createString("P_APP_ACC_AUTH_LAST_UPDATE");

    public final StringPath pAppCdAltreAsmCommonServ = createString("P_APP_CD_ALTRE_ASM_COMMON_SERV");

    public final StringPath pAppCdAmbitoManAttuale = createString("P_APP_CD_AMBITO_MAN_ATTUALE");

    public final StringPath pAppCodAmbitoManFuturo = createString("P_APP_CD_AMBITO_MAN_FUTURO");

    public final StringPath pAppCodAsmConfinanti = createString("P_APP_CD_ASM_CONFINANTI");

    public final StringPath pAppCodDirezioneDemand = createString("P_APP_CD_DIREZIONE_DEMAND");

    public final StringPath pAppCodFlussiIoAsm = createString("P_APP_CD_FLUSSI_IO_ASM");

    public final DateTimePath<java.sql.Timestamp> pAppDataFineValiditaAsm = createDateTime("P_APP_DT_FINE_VALIDITA_ASM", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> pAppDataInizioValiditaAsm = createDateTime("P_APP_DT_INIZIO_VALIDITA_ASM", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> pAppDataUltimoAggiorn = createDateTime("P_APP_DT_ULTIMO_AGGIORN", java.sql.Timestamp.class);

    public final StringPath pAppDenomSistTerziConfin = createString("P_APP_DN_SIST_TERZI_CONFIN");

    public final StringPath pAppDenomUtentiFinaliAsm = createString("P_APP_DN_UTENTI_FINALI_ASM");

    public final StringPath pAppDenominazioneAsm = createString("P_APP_DN_ASM");

    public final StringPath pAppFlagDamisurarePatrFp = createString("P_APP_FL_DAMISURARE_PATR_FP");

    public final StringPath pAppFlagInManutenzione = createString("P_APP_FL_IN_MANUTENZIONE");

    public final NumberPath<Short> pAppFlagMisurareSvimevFp = createNumber("P_APP_FL_MISURARE_SVIMEV_FP",Short.class);

    public final NumberPath<Short> pAppFlagServizioComune = createNumber("P_APP_FL_SERVIZIO_COMUNE",Short.class);

    public final NumberPath<Short> pAppIndicValidazioneAsm = createNumber("P_APP_INDIC_VALIDAZIONE_ASM",Short.class);

    public final StringPath pAppNomeAuthLastUpdate = createString("P_APP_NOME_AUTH_LAST_UPDATE");

    public final StringPath permissions = createString("PERMISSIONS");

    public final NumberPath<Short> proprietaLegale = createNumber("PROPRIETA_LEGALE",Short.class);

    public final NumberPath<Short> utilizzata = createNumber("UTILIZZATA",Short.class);

    public final NumberPath<Short> vafPredefinito = createNumber("VAF_PREDEFINITO",Short.class);
    
    public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final StringPath annullato = createString("ANNULLATO");
    
    public final NumberPath<Integer> strutturaOrganizzativaFk = createNumber("DMALM_STRUTTURA_ORG_FK_02", Integer.class);
    
    public final NumberPath<Integer> unitaOrganizzativaFk = createNumber("DMALM_UNITAORGANIZZATIVA_FK_03", Integer.class);
    
    public final NumberPath<Integer> unitaOrganizzativaFlatFk = createNumber("DMALM_UNITAORG_FLAT_FK_04", Integer.class);

    
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
    
    public final StringPath errori = createString("ERRORI");

    public final com.mysema.query.sql.PrimaryKey<DmalmAsm> sysC0037829 = createPrimaryKey(dmalmAsmPk);

    public QDmalmAsm(String variable) {
        super(DmalmAsm.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM");
    }

    public QDmalmAsm(Path<? extends DmalmAsm> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM");
    }

    public QDmalmAsm(PathMetadata<?> metadata) {
        super(DmalmAsm.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM");
    }

}

