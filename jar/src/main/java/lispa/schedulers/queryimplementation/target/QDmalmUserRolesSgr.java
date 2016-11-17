package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmUserRolesSgr;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmUserRolesSgr is a Querydsl query type for DmalmUserRolesSgr
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmUserRolesSgr extends com.mysema.query.sql.RelationalPathBase<DmalmUserRolesSgr> {

    private static final long serialVersionUID = 803235991;

    public static final QDmalmUserRolesSgr dmalmUserRolesSgr = new QDmalmUserRolesSgr("DMALM_USER_ROLES_SGR");

    public final NumberPath<Integer> dmalmUserRolesPk = createNumber("DMALM_USER_ROLES_PK", Integer.class);
    
    public final NumberPath<Integer> dmalmProjectFk01 = createNumber("DMALM_PROJECT_FK_01", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final StringPath origine = createString("ORIGINE");

    public final StringPath ruolo = createString("RUOLO");

    public final StringPath userid = createString("USERID");

    public final StringPath repository = createString("REPOSITORY");
    
    public final com.mysema.query.sql.PrimaryKey<DmalmUserRolesSgr> dmalmUserRolesPrimaryKey = createPrimaryKey(dmalmUserRolesPk);

    public QDmalmUserRolesSgr(String variable) {
        super(DmalmUserRolesSgr.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_USER_ROLES_SGR");
    }

    public QDmalmUserRolesSgr(Path<? extends DmalmUserRolesSgr> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_USER_ROLES_SGR");
    }

    public QDmalmUserRolesSgr(PathMetadata<?> metadata) {
        super(DmalmUserRolesSgr.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_USER_ROLES_SGR");
    }

}

