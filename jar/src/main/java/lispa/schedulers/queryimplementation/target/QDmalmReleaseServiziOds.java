package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmReleaseServiziOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmReleaseServiziOds is a Querydsl query type for DmalmReleaseServiziOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmReleaseServiziOds extends com.mysema.query.sql.RelationalPathBase<DmalmReleaseServiziOds> {

    private static final long serialVersionUID = 1294649240;

    public static final QDmalmReleaseServiziOds dmalmReleaseServiziOds = new QDmalmReleaseServiziOds("DMALM_RELEASE_SERVIZI_ODS");

    public final StringPath cdRelServizi = createString("CD_REL_SERVIZI");

    public final StringPath descrizioneRelServizi = createString("DESCRIZIONE_REL_SERVIZI");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmRelServiziPk = createNumber("DMALM_REL_SERVIZI_PK", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreRelServizi = createString("DS_AUTORE_REL_SERVIZI");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoRelServizi = createDateTime("DT_CAMBIO_STATO_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoRelServizi = createDateTime("DT_CARICAMENTO_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneRelServizi = createDateTime("DT_CREAZIONE_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaRelServizi = createDateTime("DT_MODIFICA_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneRelServizi = createDateTime("DT_RISOLUZIONE_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaRelServizi = createDateTime("DT_SCADENZA_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreRelServizi = createString("ID_AUTORE_REL_SERVIZI");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneRelServizi = createString("MOTIVO_RISOLUZIONE_REL_SERVIZI");

    public final StringPath motivoSospensioneReleaseSer = createString("MOTIVO_SOSPENSIONE_RELEASE_SER");

    public final StringPath previstoFermoServizioRel = createString("PREVISTO_FERMO_SERVIZIO_REL");

    public final NumberPath<Double> rankStatoRelServizi = createNumber("RANK_STATO_REL_SERVIZI", Double.class);

    public final NumberPath<Short> rankStatoRelServiziMese = createNumber("RANK_STATO_REL_SERVIZI_MESE", Short.class);

    public final StringPath richiestaAnalisiImpattiRel = createString("RICHIESTA_ANALISI_IMPATTI_REL");

    public final StringPath stgPk = createString("STG_PK");
 
        public final StringPath uri = createString("URI_RELEASE_SERVIZI");


    public final StringPath titoloRelServizi = createString("TITOLO_REL_SERVIZI");

    public final com.mysema.query.sql.PrimaryKey<DmalmReleaseServiziOds> sysC0024515 = createPrimaryKey(dmalmRelServiziPk);

  //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
        
    public final StringPath priority = createString("PRIORITY");
    
    
    public QDmalmReleaseServiziOds(String variable) {
        super(DmalmReleaseServiziOds.class, forVariable(variable), "DMALM", "DMALM_RELEASE_SERVIZI_ODS");
    }

    public QDmalmReleaseServiziOds(Path<? extends DmalmReleaseServiziOds> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_RELEASE_SERVIZI_ODS");
    }

    public QDmalmReleaseServiziOds(PathMetadata<?> metadata) {
        super(DmalmReleaseServiziOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RELEASE_SERVIZI_ODS");
    }

}

