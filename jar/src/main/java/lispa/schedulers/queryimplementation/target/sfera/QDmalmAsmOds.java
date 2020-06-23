package lispa.schedulers.queryimplementation.target.sfera;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.sfera.DmalmAsmOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDmalmAsmOds is a Querydsl query type for DmalmAsmOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmAsmOds extends com.mysema.query.sql.RelationalPathBase<DmalmAsmOds> {

    private static final long serialVersionUID = 2058783297;

    public static final QDmalmAsmOds dmalmAsmOds = new QDmalmAsmOds("DMALM_ASM_ODS");

    public final StringPath appCls = createString("APP_CLS");

    public final StringPath applicazione = createString("APPLICAZIONE");

    public final NumberPath<Long> chiaveNaturale = createNumber("CHIAVE_NATURALE", Long.class);

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataDismissione = createDateTime("DATA_DISMISSIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioEsercizio = createDateTime("DATA_INIZIO_ESERCIZIO", java.sql.Timestamp.class);

    public final NumberPath<Integer> dmalmAsmPk = createNumber("DMALM_ASM_PK", Integer.class);

    public final NumberPath<Integer> dmalmStgMisuraPk = createNumber("DMALM_STG_MISURA_PK", Integer.class);

    public final StringPath frequenzaUtilizzo = createString("FREQUENZA_UTILIZZO");

    public final NumberPath<Short> idAsm = createNumber("ID_ASM", Short.class);

    public final BooleanPath includiInDbPatrimonio = createBoolean("INCLUDI_IN_DB_PATRIMONIO");

    public final StringPath noteAsm = createString("NOTE_ASM");

    public final NumberPath<Short> numeroUtenti = createNumber("NUMERO_UTENTI", Short.class);

    public final StringPath pAppAccAuthLastUpdate = createString("P_APP_ACC_AUTH_LAST_UPDATE");

    public final StringPath pAppCdAltreAsmCommonServ = createString("P_APP_CD_ALTRE_ASM_COMMON_SERV");

    public final StringPath pAppCdAmbitoManAttuale = createString("P_APP_CD_AMBITO_MAN_ATTUALE");

    public final StringPath pAppCodAmbitoManFuturo = createString("P_APP_COD_AMBITO_MAN_FUTURO");

    public final StringPath pAppCodAsmConfinanti = createString("P_APP_COD_ASM_CONFINANTI");

    public final StringPath pAppCodDirezioneDemand = createString("P_APP_COD_DIREZIONE_DEMAND");

    public final StringPath pAppCodFlussiIoAsm = createString("P_APP_COD_FLUSSI_IO_ASM");

    public final DateTimePath<java.sql.Timestamp> pAppDataFineValiditaAsm = createDateTime("P_APP_DATA_FINE_VALIDITA_ASM", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> pAppDataInizioValiditaAsm = createDateTime("P_APP_DATA_INIZIO_VALIDITA_ASM", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> pAppDataUltimoAggiorn = createDateTime("P_APP_DATA_ULTIMO_AGGIORN", java.sql.Timestamp.class);

    public final StringPath pAppDenomSistTerziConfin = createString("P_APP_DENOM_SIST_TERZI_CONFIN");

    public final StringPath pAppDenomUtentiFinaliAsm = createString("P_APP_DENOM_UTENTI_FINALI_ASM");

    public final StringPath pAppDenominazioneAsm = createString("P_APP_DENOMINAZIONE_ASM");

    public final BooleanPath pAppFlagDamisurarePatrFp = createBoolean("P_APP_FLAG_DAMISURARE_PATR_FP");

    public final StringPath pAppFlagInManutenzione = createString("P_APP_FLAG_IN_MANUTENZIONE");

    public final BooleanPath pAppFlagMisurareSvimevFp = createBoolean("P_APP_FLAG_MISURARE_SVIMEV_FP");

    public final BooleanPath pAppFlagServizioComune = createBoolean("P_APP_FLAG_SERVIZIO_COMUNE");

    public final BooleanPath pAppIndicValidazioneAsm = createBoolean("P_APP_INDIC_VALIDAZIONE_ASM");

    public final StringPath pAppNomeAuthLastUpdate = createString("P_APP_NOME_AUTH_LAST_UPDATE");

    public final StringPath permissions = createString("PERMISSIONS");

    public final BooleanPath proprietaLegale = createBoolean("PROPRIETA_LEGALE");

    public final BooleanPath utilizzata = createBoolean("UTILIZZATA");

    public final BooleanPath vafPredefinito = createBoolean("VAF_PREDEFINITO");

    public final com.mysema.query.sql.PrimaryKey<DmalmAsmOds> sysC0037842 = createPrimaryKey(dmalmAsmPk);

    public QDmalmAsmOds(String variable) {
        super(DmalmAsmOds.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_ODS");
    }

    public QDmalmAsmOds(Path<? extends DmalmAsmOds> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_ODS");
    }

    public QDmalmAsmOds(PathMetadata<?> metadata) {
        super(DmalmAsmOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_ODS");
    }

}

