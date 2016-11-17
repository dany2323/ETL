package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmProjectRolesSgr;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmProjectRolesSgr is a Querydsl query type for DmalmProjectRolesSgr
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProjectRolesSgr extends com.mysema.query.sql.RelationalPathBase<DmalmProjectRolesSgr> {

    private static final long serialVersionUID = -1056943121;

    public static final QDmalmProjectRolesSgr dmalmProjectRolesSgr = new QDmalmProjectRolesSgr("DMALM_PROJECT_ROLES_SGR");

    public final NumberPath<Integer> dmalmProjectRolesPrimaryKey = createNumber("DMALM_PROJECT_ROLES_PK", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final StringPath ruolo = createString("RUOLO");

    public final StringPath tipologiaProject = createString("TIPOLOGIA_PROJECT");

    public final com.mysema.query.sql.PrimaryKey<DmalmProjectRolesSgr> dmalmProjectRolesPk = createPrimaryKey(dmalmProjectRolesPrimaryKey);

    public QDmalmProjectRolesSgr(String variable) {
        super(DmalmProjectRolesSgr.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROJECT_ROLES_SGR");
    }

    public QDmalmProjectRolesSgr(Path<? extends DmalmProjectRolesSgr> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROJECT_ROLES_SGR");
    }

    public QDmalmProjectRolesSgr(PathMetadata<?> metadata) {
        super(DmalmProjectRolesSgr.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROJECT_ROLES_SGR");
    }

}

