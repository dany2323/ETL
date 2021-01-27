package lispa.schedulers.queryimplementation.fonte.sgr.xml;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.constant.DmAlmConstants;
import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * DmalmWorkitemLinkRoles is a Querydsl query type for DmalmWorkitemLinkRoles
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class DmAlmWorkitemLinkRoles extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.xml.DmAlmWorkitemLinkRoles> {

    private static final long serialVersionUID = 1178157647;

    public static final DmAlmWorkitemLinkRoles dmAlmWorkitemLinkRoles = new DmAlmWorkitemLinkRoles("DM_ALM_WORKITEM_LINK_ROLES");

    public final StringPath descrizione = createString("DESCRIZIONE");

    public final StringPath idRuolo = createString("ID_RUOLO");

    public final StringPath nomeRuolo = createString("NOME_RUOLO");

    public final StringPath nomeRuoloInverso = createString("NOME_RUOLO_INVERSO");

    public final BooleanPath parent = createBoolean("PARENT");

    public final StringPath repository = createString("REPOSITORY");

    public final StringPath templates = createString("TEMPLATES");

    public DmAlmWorkitemLinkRoles(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.xml.DmAlmWorkitemLinkRoles.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_WORKITEM_LINK_ROLES");
    }

    public DmAlmWorkitemLinkRoles(Path<? extends lispa.schedulers.bean.fonte.sgr.xml.DmAlmWorkitemLinkRoles> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_WORKITEM_LINK_ROLES");
    }

    public DmAlmWorkitemLinkRoles(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.xml.DmAlmWorkitemLinkRoles.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_WORKITEM_LINK_ROLES");
    }

}