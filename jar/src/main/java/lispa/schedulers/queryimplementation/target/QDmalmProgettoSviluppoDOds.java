package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmProgettoSviluppoDOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmProgettoSviluppoDOds is a Querydsl query type for DmalmProgettoSviluppoDOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProgettoSviluppoDOds extends com.mysema.query.sql.RelationalPathBase<DmalmProgettoSviluppoDOds> {

    private static final long serialVersionUID = -1073317917;

    public static final QDmalmProgettoSviluppoDOds dmalmProgettoSviluppoDOds = new QDmalmProgettoSviluppoDOds("DMALM_PROGETTO_SVILUPPO_D_ODS");

    public final StringPath assignee = createString("ASSIGNEE");

    public final StringPath cdProgSvilD = createString("CD_PROG_SVIL_D");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final StringPath cfCodice = createString("CF_CODICE");

    public final DateTimePath<java.sql.Timestamp> cfDataDispEffettiva = createDateTime("CF_DATA_DISP_EFFETTIVA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> cfDataDispPianificata = createDateTime("CF_DATA_DISP_PIANIFICATA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> cfDataInizio = createDateTime("CF_DATA_INIZIO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> cfDataInizioEff = createDateTime("CF_DATA_INIZIO_EFF", java.sql.Timestamp.class);

    public final StringPath cfFornitura = createString("CF_FORNITURA");

    public final StringPath descrizioneProgSvilD = createString("DESCRIZIONE_PROG_SVIL_D");

    public final NumberPath<Integer> dmalmProgSvilDPk = createNumber("DMALM_PROG_SVIL_D_PK", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreProgSvilD = createString("DS_AUTORE_PROG_SVIL_D");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoProgSvilD = createDateTime("DT_CAMBIO_STATO_PROG_SVIL_D", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoProgSvilD = createDateTime("DT_CARICAMENTO_PROG_SVIL_D", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneProgSvilD = createDateTime("DT_CREAZIONE_PROG_SVIL_D", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaProgSvilD = createDateTime("DT_MODIFICA_PROG_SVIL_D", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtPassaggioEsercizio = createDateTime("DT_PASSAGGIO_ESERCIZIO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneProgSvilD = createDateTime("DT_RISOLUZIONE_PROG_SVIL_D", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaProgSvilD = createDateTime("DT_SCADENZA_PROG_SVIL_D", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreProgSvilD = createString("ID_AUTORE_PROG_SVIL_D");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneProgSvilD = createString("MOTIVO_RISOLUZIONE_PROG_SVIL_D");

    public final StringPath priorityProgettoSvilDemand = createString("PRIORITY_PROGETTO_SVIL_DEMAND");

    public final NumberPath<Double> rankStatoProgSvilD = createNumber("RANK_STATO_PROG_SVIL_D", Double.class);

    public final NumberPath<Short> rankStatoProgSvilDMese = createNumber("RANK_STATO_PROG_SVIL_D_MESE", Short.class);

    public final StringPath severityProgettoSvilDemand = createString("SEVERITY_PROGETTO_SVIL_DEMAND");

    public final StringPath stgPk = createString("STG_PK");
 
        public final StringPath uri = createString("URI_PROGETTO_SVILUPPO_D");


    public final NumberPath<Integer> tempoTotaleRisoluzione = createNumber("TEMPO_TOTALE_RISOLUZIONE", Integer.class);

    public final StringPath titoloProgSvilD = createString("TITOLO_PROG_SVIL_D");

    public final com.mysema.query.sql.PrimaryKey<DmalmProgettoSviluppoDOds> sysC0023552 = createPrimaryKey(dmalmProgSvilDPk);

    public QDmalmProgettoSviluppoDOds(String variable) {
        super(DmalmProgettoSviluppoDOds.class, forVariable(variable), "DMALM", "DMALM_PROGETTO_SVILUPPO_D_ODS");
    }

    public QDmalmProgettoSviluppoDOds(Path<? extends DmalmProgettoSviluppoDOds> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_PROGETTO_SVILUPPO_D_ODS");
    }

    public QDmalmProgettoSviluppoDOds(PathMetadata<?> metadata) {
        super(DmalmProgettoSviluppoDOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_SVILUPPO_D_ODS");
    }

}

