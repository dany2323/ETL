package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmTaskOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmTaskOds is a Querydsl query type for DmalmTaskOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmTaskOds extends com.mysema.query.sql.RelationalPathBase<DmalmTaskOds> {

    private static final long serialVersionUID = -1947457650;

    public static final QDmalmTaskOds dmalmTaskOds = new QDmalmTaskOds("DMALM_TASK_ODS");

    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);
    
    public final StringPath cdTask = createString("CD_TASK");

    public final StringPath codice = createString("CODICE");

    public final DateTimePath<java.sql.Timestamp> dataChiusuraTask = createDateTime("DATA_CHIUSURA_TASK", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataFineEffettiva = createDateTime("DATA_FINE_EFFETTIVA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataFinePianificata = createDateTime("DATA_FINE_PIANIFICATA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioEffettivo = createDateTime("DATA_INIZIO_EFFETTIVO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioPianificato = createDateTime("DATA_INIZIO_PIANIFICATO", java.sql.Timestamp.class);

    public final StringPath descrizioneTask = createString("DESCRIZIONE_TASK");

    public final NumberPath<Integer> dmalmAreaTematicaFk05 = createNumber("DMALM_AREA_TEMATICA_FK_05", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTaskPk = createNumber("DMALM_TASK_PK", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreTask = createString("DS_AUTORE_TASK");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoTask = createDateTime("DT_CAMBIO_STATO_TASK", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoTask = createDateTime("DT_CARICAMENTO_TASK", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneTask = createDateTime("DT_CREAZIONE_TASK", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaTask = createDateTime("DT_MODIFICA_TASK", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneTask = createDateTime("DT_RISOLUZIONE_TASK", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaTask = createDateTime("DT_SCADENZA_TASK", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreTask = createString("ID_AUTORE_TASK");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final NumberPath<Short> initialCost = createNumber("INITIAL_COST", Short.class);

    public final StringPath motivoRisoluzioneTask = createString("MOTIVO_RISOLUZIONE_TASK");

    public final StringPath numeroLinea = createString("NUMERO_LINEA");

    public final StringPath numeroTestata = createString("NUMERO_TESTATA");

    public final StringPath priorityTask = createString("PRIORITY_TASK");

    public final NumberPath<Double> rankStatoTask = createNumber("RANK_STATO_TASK", Double.class);

    public final NumberPath<Short> rankStatoTaskMese = createNumber("RANK_STATO_TASK_MESE", Short.class);

    public final StringPath severityTask = createString("SEVERITY_TASK");

    public final StringPath stgPk = createString("STG_PK");
 
        public final StringPath uri = createString("URI_TASK");


    public final StringPath taskType = createString("TASK_TYPE");

    public final NumberPath<Integer> tempoTotaleRisoluzione = createNumber("TEMPO_TOTALE_RISOLUZIONE", Integer.class);

    public final StringPath titoloTask = createString("TITOLO_TASK");

    public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public final com.mysema.query.sql.PrimaryKey<DmalmTaskOds> sysC0023172 = createPrimaryKey(dmalmTaskPk);

    public QDmalmTaskOds(String variable) {
        super(DmalmTaskOds.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TASK_ODS");
    }

    public QDmalmTaskOds(Path<? extends DmalmTaskOds> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TASK_ODS");
    }

    public QDmalmTaskOds(PathMetadata<?> metadata) {
        super(DmalmTaskOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TASK_ODS");
    }

}

