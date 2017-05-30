package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmProject is a Querydsl query type for DmalmProject
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProject extends com.mysema.query.sql.RelationalPathBase<DmalmProject> {

    private static final long serialVersionUID = -719512274;

    public static final QDmalmProject dmalmProject = new QDmalmProject("DMALM_PROJECT");

    public final NumberPath<Integer> dmalmAreaTematicaFk01 = createNumber("DMALM_AREA_TEMATICA_FK_01", Integer.class);
    
    public final NumberPath<Integer> dmalmStrutturaOrgFk02 = createNumber("DMALM_STRUTTURA_ORG_FK_02", Integer.class);
    
    public final NumberPath<Integer> dmalmUnitaOrganizzativaFk = createNumber("DMALM_UNITAORGANIZZATIVA_FK_03", Integer.class);
    
    public final NumberPath<Integer> dmalmUnitaOrganizzativaFlatFk = createNumber("DMALM_UNITAORG_FLAT_FK_04", Integer.class);

    public final NumberPath<Integer> dmalmProjectPrimaryKey = createNumber("DMALM_PROJECT_PK", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final BooleanPath flAttivo = createBoolean("FL_ATTIVO");
    
    public final StringPath annullato = createString("ANNULLATO");

    public final StringPath idProject = createString("ID_PROJECT");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath nomeCompletoProject = createString("NOME_COMPLETO_PROJECT");

    public final StringPath pathProject = createString("PATH_PROJECT");

    public final StringPath serviceManagers = createString("SERVICE_MANAGERS");

    public final StringPath siglaProject = createString("SIGLA_PROJECT");

    public final com.mysema.query.sql.PrimaryKey<DmalmProject> dmalmProjectPk = createPrimaryKey(dmalmProjectPrimaryKey);

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.DmalmAreaTematica> dmalmProjectFk01 = createForeignKey(dmalmAreaTematicaFk01, "DMALM_AREA_TEMATICA_PK");

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto> _dmalmAnomaliaProdottoFk02 = createInvForeignKey(dmalmProjectPrimaryKey, "DMALM_PROJECT_FK_02");

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto> _dmalmDifettoProdottoFk02 = createInvForeignKey(dmalmProjectPrimaryKey, "DMALM_PROJECT_FK_02");
    
    public final StringPath cTemplate = createString("TEMPLATE");
    
    public final DateTimePath<java.sql.Timestamp> cCreated = createDateTime("DATA_MODIFICA_PROJECT", java.sql.Timestamp.class);
    
    public final StringPath cTrackerprefix = createString("C_TRACKERPREFIX");
    
    public final NumberPath<Integer> cIsLocal = createNumber("C_IS_LOCAL", Integer.class);
    
    public final StringPath cPk = createString("C_PK");

    public final StringPath fkUriLead = createString("FK_URI_LEAD");

    public final NumberPath<Integer> cDeleted = createNumber("C_DELETED", Integer.class);

    public final DateTimePath<java.sql.Timestamp> cFinish = createDateTime("C_FINISH", java.sql.Timestamp.class);

    public final StringPath cUri = createString("C_URI");
    
    public final DateTimePath<java.sql.Timestamp> cStart = createDateTime("C_START", java.sql.Timestamp.class);
    
    public final StringPath fkUriProjectgroup = createString("FK_URI_PROJECTGROUP");
    
    public final NumberPath<Integer> cActive = createNumber("C_ACTIVE", Integer.class);
    
    public final StringPath fkProjectgroup = createString("FK_PROJECTGROUP");
    
    public final StringPath fkLead = createString("FK_LEAD");
    
    public final DateTimePath<java.sql.Timestamp> cLockworkrecordsdate = createDateTime("C_LOCKWORKRECORDSDATE", java.sql.Timestamp.class);
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);
    
    public final StringPath cDescription = createString("C_DESCRIPTION");
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
    
    public QDmalmProject(String variable) {
        super(DmalmProject.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROJECT");
    }

    public QDmalmProject(Path<? extends DmalmProject> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROJECT");
    }

    public QDmalmProject(PathMetadata<?> metadata) {
        super(DmalmProject.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROJECT");
    }

}

