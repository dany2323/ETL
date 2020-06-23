package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmProgettoSviluppoSOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmProgettoSviluppoSOds is a Querydsl query type for DmalmProgettoSviluppoSOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProgettoSviluppoSOds extends com.mysema.query.sql.RelationalPathBase<DmalmProgettoSviluppoSOds> {

    private static final long serialVersionUID = -1960897158;

    public static final QDmalmProgettoSviluppoSOds dmalmProgettoSviluppoSOds = new QDmalmProgettoSviluppoSOds("DMALM_PROGETTO_SVILUPPO_S_ODS");

    public final StringPath cdProgSvilS = createString("CD_PROG_SVIL_S");

    public final StringPath codice = createString("CODICE");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dataChiusuraProgSvilS = createDateTime("DATA_CHIUSURA_PROG_SVIL_S", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataDisponibilitaEffettiva = createDateTime("DATA_DISPONIBILITA_EFFETTIVA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataDisponibilitaPianificata = createDateTime("DATA_DISPONIBILITA_PIANIFICATA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizio = createDateTime("DATA_INIZIO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioEff = createDateTime("DATA_INIZIO_EFF", java.sql.Timestamp.class);

    public final StringPath descrizioneProgSvilS = createString("DESCRIZIONE_PROG_SVIL_S");

    public final NumberPath<Integer> dmalmAreaTematicaFk05 = createNumber("DMALM_AREA_TEMATICA_FK_05", Integer.class);

    public final NumberPath<Integer> dmalmProgSvilSPk = createNumber("DMALM_PROG_SVIL_S_PK", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreProgSvilS = createString("DS_AUTORE_PROG_SVIL_S");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoProgSvilS = createDateTime("DT_CAMBIO_STATO_PROG_SVIL_S", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoProgSvilS = createDateTime("DT_CARICAMENTO_PROG_SVIL_S", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneProgSvilS = createDateTime("DT_CREAZIONE_PROG_SVIL_S", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaProgSvilS = createDateTime("DT_MODIFICA_PROG_SVIL_S", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneProgSvilS = createDateTime("DT_RISOLUZIONE_PROG_SVIL_S", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaProgSvilS = createDateTime("DT_SCADENZA_PROG_SVIL_S", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final NumberPath<Short> durataEffettivaProgSvilS = createNumber("DURATA_EFFETTIVA_PROG_SVIL_S", Short.class);

    public final StringPath fornitura = createString("FORNITURA");

    public final StringPath idAutoreProgSvilS = createString("ID_AUTORE_PROG_SVIL_S");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneProgSvilS = createString("MOTIVO_RISOLUZIONE_PROG_SVIL_S");

    public final StringPath numeroLinea = createString("NUMERO_LINEA");

    public final StringPath numeroTestata = createString("NUMERO_TESTATA");

    public final NumberPath<Double> rankStatoProgSvilS = createNumber("RANK_STATO_PROG_SVIL_S", Double.class);

    public final NumberPath<Short> rankStatoProgSvilSMese = createNumber("RANK_STATO_PROG_SVIL_S_MESE", Short.class);

    public final StringPath stgPk = createString("STG_PK");
 
        public final StringPath uri = createString("URI_PROGETTO_SVILUPPO_S");


    public final StringPath titoloProgSvilS = createString("TITOLO_PROG_SVIL_S");

    public final com.mysema.query.sql.PrimaryKey<DmalmProgettoSviluppoSOds> sysC0022942 = createPrimaryKey(dmalmProgSvilSPk);
    
    //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
    
    public final StringPath priority = createString("PRIORITY");

    public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmProgettoSviluppoSOds(String variable) {
        super(DmalmProgettoSviluppoSOds.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_SVILUPPO_S_ODS");
    }

    public QDmalmProgettoSviluppoSOds(Path<? extends DmalmProgettoSviluppoSOds> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_SVILUPPO_S_ODS");
    }

    public QDmalmProgettoSviluppoSOds(PathMetadata<?> metadata) {
        super(DmalmProgettoSviluppoSOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_SVILUPPO_S_ODS");
    }

}

