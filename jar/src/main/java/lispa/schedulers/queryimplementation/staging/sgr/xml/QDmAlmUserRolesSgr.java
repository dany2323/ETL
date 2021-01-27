package lispa.schedulers.queryimplementation.staging.sgr.xml;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.constant.DmAlmConstants;
import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * DmAlmStatoWorkitem is a Querydsl query type for DmAlmStatoWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmAlmUserRolesSgr extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.xml.DmAlmUserRolesSgr> {

    private static final long serialVersionUID = 1545915439;

    public static final QDmAlmUserRolesSgr dmAlmUserRolesSgr = new QDmAlmUserRolesSgr("DM_ALM_USER_ROLES_SGR");

    public final StringPath origine = createString("ORIGINE");

    public final StringPath userId = createString("USERID");
    
    public final StringPath ruolo = createString("RUOLO");
    
    public final StringPath repository = createString("REPOSITORY");
    
    public final DateTimePath<java.sql.Timestamp> dtModifica = createDateTime("DT_MODIFICA", java.sql.Timestamp.class);

    public QDmAlmUserRolesSgr(String variable) {
        super(lispa.schedulers.bean.staging.sgr.xml.DmAlmUserRolesSgr.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_USER_ROLES_SGR");
    }

    public QDmAlmUserRolesSgr(Path<? extends lispa.schedulers.bean.staging.sgr.xml.DmAlmUserRolesSgr> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_USER_ROLES_SGR");
    }

    public QDmAlmUserRolesSgr(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.xml.DmAlmUserRolesSgr.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_USER_ROLES_SGR");
    }

}

