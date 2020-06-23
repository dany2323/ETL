package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmReleaseIt;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmReleaseIt is a Querydsl query type for DmalmReleaseIt
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmReleaseIt extends com.mysema.query.sql.RelationalPathBase<DmalmReleaseIt> {

    private static final long serialVersionUID = 1022880871;

    public static final QDmalmReleaseIt dmalmReleaseIt = new QDmalmReleaseIt("DMALM_RELEASE_IT");

    public final StringPath cdReleaseIt = createString("CD_RELEASE_IT");
    
    public final StringPath annullato = createString("ANNULLATO");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final StringPath descrizioneReleaseIt = createString("DESCRIZIONE_RELEASE_IT");

    public final NumberPath<Long> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Long.class);

    public final NumberPath<Long> dmalmReleaseItPk = createNumber("DMALM_RELEASE_IT_PK", Long.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreReleaseIt = createString("DS_AUTORE_RELEASE_IT");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoReleaseIt = createDateTime("DT_CAMBIO_STATO_RELEASE_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoReleaseIt = createDateTime("DT_CARICAMENTO_RELEASE_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneReleaseIt = createDateTime("DT_CREAZIONE_RELEASE_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtDisponibilitaEffRelease = createDateTime("DT_DISPONIBILITA_EFF_RELEASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtFineRelease = createDateTime("DT_FINE_RELEASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioRelease = createDateTime("DT_INIZIO_RELEASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaReleaseIt = createDateTime("DT_MODIFICA_RELEASE_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRilascioRelease = createDateTime("DT_RILASCIO_RELEASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneReleaseIt = createDateTime("DT_RISOLUZIONE_RELEASE_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaReleaseIt = createDateTime("DT_SCADENZA_RELEASE_IT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final NumberPath<Integer> durataEffRelease = createNumber("DURATA_EFF_RELEASE", Integer.class);

    public final StringPath idAutoreReleaseIt = createString("ID_AUTORE_RELEASE_IT");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneReleaseIt = createString("MOTIVO_RISOLUZIONE_RELEASE_IT");
    
    public final StringPath severity = createString("SEVERITY");
    
    public final StringPath priority = createString("PRIORITY");

    public final NumberPath<Double> rankStatoReleaseIt = createNumber("RANK_STATO_RELEASE_IT", Double.class);

    public final NumberPath<Short> rankStatoReleaseItMese = createNumber("RANK_STATO_RELEASE_IT_MESE", Short.class);

    public final StringPath stgPk = createString("STG_PK");
    public final StringPath changed = createString("CHANGED");    
 
    public final StringPath uri = createString("URI_RELEASE_IT");


    public final StringPath titoloReleaseIt = createString("TITOLO_RELEASE_IT");
    
    public final StringPath typeRelease = createString("TYPE_RELEASE");

    public final com.mysema.query.sql.PrimaryKey<DmalmReleaseIt> sysC0022476 = createPrimaryKey(dmalmReleaseItPk);
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);

    public final StringPath motivoSospensione = createString("MOTIVO_SOSPENSIONE");
    
    public final NumberPath<Integer> counterQf = createNumber("COUNTER_QF", Integer.class);
    
    public final NumberPath<Integer> giorniQf = createNumber("GIORNI_QF", Integer.class);
    
public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmReleaseIt(String variable) {
        super(DmalmReleaseIt.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RELEASE_IT");
    }

    public QDmalmReleaseIt(Path<? extends DmalmReleaseIt> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RELEASE_IT");
    }

    public QDmalmReleaseIt(PathMetadata<?> metadata) {
        super(DmalmReleaseIt.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RELEASE_IT");
    }

}

