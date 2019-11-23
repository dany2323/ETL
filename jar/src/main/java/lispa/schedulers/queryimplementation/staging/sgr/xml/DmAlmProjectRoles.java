package lispa.schedulers.queryimplementation.staging.sgr.xml;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmAlmProjectRoles is a Querydsl query type for DmAlmProjectRoles
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class DmAlmProjectRoles extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.xml.DmAlmProjectRoles> {

    private static final long serialVersionUID = 693199956;

    public static final DmAlmProjectRoles dmAlmProjectRoles = new DmAlmProjectRoles("DM_ALM_PROJECT_ROLES");

    public final StringPath descrizione = createString("DESCRIZIONE");
    
    public final StringPath repository = createString("REPOSITORY");

    public final StringPath ruolo = createString("RUOLO");

    public DmAlmProjectRoles(String variable) {
        super(lispa.schedulers.bean.staging.sgr.xml.DmAlmProjectRoles.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_PROJECT_ROLES");
    }

    public DmAlmProjectRoles(Path<? extends lispa.schedulers.bean.staging.sgr.xml.DmAlmProjectRoles> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_PROJECT_ROLES");
    }

    public DmAlmProjectRoles(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.xml.DmAlmProjectRoles.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_PROJECT_ROLES");
    }

}

