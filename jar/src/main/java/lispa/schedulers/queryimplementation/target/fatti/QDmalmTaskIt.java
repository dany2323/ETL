package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmTaskIt;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmTaskIt is a Querydsl query type for DmalmTaskIt
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmTaskIt extends com.mysema.query.sql.RelationalPathBase<DmalmTaskIt> {

    private static final long serialVersionUID = -894105381;

    public static final QDmalmTaskIt dmalmTaskIt = new QDmalmTaskIt("DMALM_TASK_IT");
    
    public final StringPath annullato = createString("ANNULLATO");

    public final NumberPath<Integer> avanzamento = createNumber("AVANZAMENTO", Integer.class);

    public final StringPath cdTaskIt = createString("CD_TASK_IT");

    public final StringPath codice = createString("CODICE");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final StringPath descrizioneTaskIt = createString("DESCRIZIONE_TASK_IT");

    public final NumberPath<Long> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Long.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Long> dmalmTaskItPk = createNumber("DMALM_TASK_IT_PK", Long.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreTaskIt = createString("DS_AUTORE_TASK_IT");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoTaskIt = createDateTime("DT_CAMBIO_STATO_TASK_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoTaskIt = createDateTime("DT_CARICAMENTO_TASK_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneTaskIt = createDateTime("DT_CREAZIONE_TASK_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtFineEffettiva = createDateTime("DT_FINE_EFFETTIVA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtFinePianificata = createDateTime("DT_FINE_PIANIFICATA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioEffettivo = createDateTime("DT_INIZIO_EFFETTIVO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioPianificato = createDateTime("DT_INIZIO_PIANIFICATO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaTaskIt = createDateTime("DT_MODIFICA_TASK_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneTaskIt = createDateTime("DT_RISOLUZIONE_TASK_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaTaskIt = createDateTime("DT_SCADENZA_TASK_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final NumberPath<Integer> durataEffettiva = createNumber("DURATA_EFFETTIVA", Integer.class);

    public final StringPath idAutoreTaskIt = createString("ID_AUTORE_TASK_IT");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneTaskIt = createString("MOTIVO_RISOLUZIONE_TASK_IT");

    public final StringPath priorityTaskIt = createString("PRIORITY_TASK_IT");

    public final NumberPath<Double> rankStatoTaskIt = createNumber("RANK_STATO_TASK_IT", Double.class);

    public final NumberPath<Short> rankStatoTaskItMese = createNumber("RANK_STATO_TASK_IT_MESE", Short.class);

    public final StringPath severityTaskIt = createString("SEVERITY_TASK_IT");

    public final StringPath stgPk = createString("STG_PK");
 
    public final StringPath uri = createString("URI_TASK_IT");

    public final StringPath changed = createString("CHANGED");  
        
    public final StringPath tipoTask = createString("TIPO_TASK");

    public final StringPath titoloTaskIt = createString("TITOLO_TASK_IT");

    public final com.mysema.query.sql.PrimaryKey<DmalmTaskIt> sysC0022480 = createPrimaryKey(dmalmTaskItPk);
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);

public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmTaskIt(String variable) {
        super(DmalmTaskIt.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TASK_IT");
    }

    public QDmalmTaskIt(Path<? extends DmalmTaskIt> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TASK_IT");
    }

    public QDmalmTaskIt(PathMetadata<?> metadata) {
        super(DmalmTaskIt.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TASK_IT");
    }

}

