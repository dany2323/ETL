package lispa.schedulers.queryimplementation.staging.sgr.xml;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.xml.DmAlmWorkitemLinkRoles;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmWorkitemLinkRoles is a Querydsl query type for DmalmWorkitemLinkRoles
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmAlmWorkitemLinkRoles extends com.mysema.query.sql.RelationalPathBase<DmAlmWorkitemLinkRoles> {

    private static final long serialVersionUID = 1178157647;

    public static final QDmAlmWorkitemLinkRoles dmAlmWorkitemLinkRoles = new QDmAlmWorkitemLinkRoles("DMALM_WORKITEM_LINK_ROLES");

    public final DateTimePath<java.sql.Timestamp> datacaricamento = createDateTime("DATACARICAMENTO", java.sql.Timestamp.class);

    public final StringPath descrizione = createString("DESCRIZIONE");

    public final StringPath idRuolo = createString("ID_RUOLO");

    public final StringPath nomeRuolo = createString("NOME_RUOLO");

    public final StringPath nomeRuoloInverso = createString("NOME_RUOLO_INVERSO");

    public final BooleanPath parent = createBoolean("PARENT");

    public final StringPath repository = createString("REPOSITORY");

    public final StringPath templates = createString("TEMPLATES");

    public final NumberPath<Long> workitemLinkRolesPk = createNumber("WORKITEM_LINK_ROLES_PK", Long.class);

    public final com.mysema.query.sql.PrimaryKey<DmAlmWorkitemLinkRoles> sysC0022062 = createPrimaryKey(workitemLinkRolesPk);

    public QDmAlmWorkitemLinkRoles(String variable) {
        super(DmAlmWorkitemLinkRoles.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_WORKITEM_LINK_ROLES");
    }

    public QDmAlmWorkitemLinkRoles(Path<? extends DmAlmWorkitemLinkRoles> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_WORKITEM_LINK_ROLES");
    }

    public QDmAlmWorkitemLinkRoles(PathMetadata<?> metadata) {
        super(DmAlmWorkitemLinkRoles.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_WORKITEM_LINK_ROLES");
    }

}

