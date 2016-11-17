package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmBuild;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmBuild is a Querydsl query type for DmalmBuild
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmBuild extends com.mysema.query.sql.RelationalPathBase<DmalmBuild> {

    private static final long serialVersionUID = 1894783427;

    public static final QDmalmBuild dmalmBuild = new QDmalmBuild("DMALM_BUILD");

    public final StringPath cdBuild = createString("CD_BUILD");
    
    public final StringPath annullato = createString("ANNULLATO");

    public final StringPath codice = createString("CODICE");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final StringPath descrizioneBuild = createString("DESCRIZIONE_BUILD");

    public final NumberPath<Integer> dmalmBuildPk = createNumber("DMALM_BUILD_PK", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreBuild = createString("DS_AUTORE_BUILD");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoBuild = createDateTime("DT_CAMBIO_STATO_BUILD", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoBuild = createDateTime("DT_CARICAMENTO_BUILD", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneBuild = createDateTime("DT_CREAZIONE_BUILD", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaBuild = createDateTime("DT_MODIFICA_BUILD", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneBuild = createDateTime("DT_RISOLUZIONE_BUILD", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaBuild = createDateTime("DT_SCADENZA_BUILD", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreBuild = createString("ID_AUTORE_BUILD");

    public final StringPath idRepository = createString("ID_REPOSITORY");
    
    public final StringPath motivoRisoluzioneBuild = createString("MOTIVO_RISOLUZIONE_BUILD");

    public final NumberPath<Double> rankStatoBuild = createNumber("RANK_STATO_BUILD", Double.class);

    public final NumberPath<Short> rankStatoBuildMese = createNumber("RANK_STATO_BUILD_MESE", Short.class);

    public final StringPath stgPk = createString("STG_PK");
    public final StringPath changed = createString("CHANGED");    

    public final StringPath titoloBuild = createString("TITOLO_BUILD");
    
    public final StringPath uri = createString("URI_BUILD");

    public final com.mysema.query.sql.PrimaryKey<DmalmBuild> sysC0022478 = createPrimaryKey(dmalmBuildPk);

    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
    
    public QDmalmBuild(String variable) {
        super(DmalmBuild.class, forVariable(variable), "DMALM", "DMALM_BUILD");
    }

    public QDmalmBuild(Path<? extends DmalmBuild> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_BUILD");
    }

    public QDmalmBuild(PathMetadata<?> metadata) {
        super(DmalmBuild.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_BUILD");
    }

}

