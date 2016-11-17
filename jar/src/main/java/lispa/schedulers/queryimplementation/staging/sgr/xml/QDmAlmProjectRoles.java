package lispa.schedulers.queryimplementation.staging.sgr.xml;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.xml.DmAlmProjectRoles;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmAlmProjectRoles is a Querydsl query type for DmAlmProjectRoles
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmAlmProjectRoles extends com.mysema.query.sql.RelationalPathBase<DmAlmProjectRoles> {

    private static final long serialVersionUID = 693199956;

    public static final QDmAlmProjectRoles dmAlmProjectRoles = new QDmAlmProjectRoles("DMALM_PROJECT_ROLES");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath descrizione = createString("DESCRIZIONE");
    
    public final StringPath repository = createString("REPOSITORY");

    public final NumberPath<Double> dmAlmProjectRolesPk = createNumber("PROJECT_ROLES_PK", Double.class);

    public final StringPath ruolo = createString("RUOLO");

    public final com.mysema.query.sql.PrimaryKey<DmAlmProjectRoles> dmAlmProjectRoles_Pk = createPrimaryKey(dmAlmProjectRolesPk);

    public QDmAlmProjectRoles(String variable) {
        super(DmAlmProjectRoles.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_PROJECT_ROLES");
    }

    public QDmAlmProjectRoles(Path<? extends DmAlmProjectRoles> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_PROJECT_ROLES");
    }

    public QDmAlmProjectRoles(PathMetadata<?> metadata) {
        super(DmAlmProjectRoles.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_PROJECT_ROLES");
    }

}

