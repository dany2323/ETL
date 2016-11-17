package lispa.schedulers.queryimplementation.staging.sgr.xml;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.xml.DmAlmUserRoles;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmAlmUserRoles is a Querydsl query type for DmAlmUserRoles
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmAlmUserRoles extends com.mysema.query.sql.RelationalPathBase<DmAlmUserRoles> {

    private static final long serialVersionUID = -1041670526;

    public static final QDmAlmUserRoles dmAlmUserRoles = new QDmAlmUserRoles("DMALM_USER_ROLES");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final NumberPath<Double> dmAlmUserRolesPk = createNumber("USER_ROLES_PK", Double.class);

    public final StringPath idProject = createString("ID_PROJECT");

    public final StringPath ruolo = createString("RUOLO");
    
    public final NumberPath<Long> revision = createNumber("REVISION", Long.class);
    
    public final StringPath repository = createString("REPOSITORY");

    public final StringPath utente = createString("UTENTE");
    
    public final DateTimePath<java.sql.Timestamp> dataModifica = createDateTime("DATA_MODIFICA", java.sql.Timestamp.class);
    

    public final com.mysema.query.sql.PrimaryKey<DmAlmUserRoles> dmAlmUserRoles_Pk = createPrimaryKey(dmAlmUserRolesPk);

    public QDmAlmUserRoles(String variable) {
        super(DmAlmUserRoles.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_USER_ROLES");
    }

    public QDmAlmUserRoles(Path<? extends DmAlmUserRoles> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_USER_ROLES");
    }

    public QDmAlmUserRoles(PathMetadata<?> metadata) {
        super(DmAlmUserRoles.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_USER_ROLES");
    }

}

