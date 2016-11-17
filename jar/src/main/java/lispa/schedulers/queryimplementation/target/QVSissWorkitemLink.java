package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.VSissWorkitemLink;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QVSissWorkitemLink is a Querydsl query type for VSissWorkitemLink
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QVSissWorkitemLink extends com.mysema.query.sql.RelationalPathBase<VSissWorkitemLink> {

    private static final long serialVersionUID = -304920932;

    public static final QVSissWorkitemLink vSissWorkitemLink = new QVSissWorkitemLink("V_SISS_WORKITEM_LINK");

    public final DateTimePath<java.sql.Timestamp> ccreatedLink = createDateTime("CCREATED_LINK", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> datacaricamento = createDateTime("DATACARICAMENTO", java.sql.Timestamp.class);

    public final StringPath idLink = createString("ID_LINK");

    public final StringPath pkLink = createString("PK_LINK");

    public final StringPath statoLink = createString("STATO_LINK");

    public final StringPath statoWorkitem = createString("STATO_WORKITEM");

    public final StringPath typeLink = createString("TYPE_LINK");

    public final StringPath typeWorkitem = createString("TYPE_WORKITEM");

    public final StringPath uriLink = createString("URI_LINK");

    public final StringPath uriWorkitem = createString("URI_WORKITEM");

    public QVSissWorkitemLink(String variable) {
        super(VSissWorkitemLink.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_SISS_WORKITEM_LINK");
    }

    public QVSissWorkitemLink(Path<? extends VSissWorkitemLink> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_SISS_WORKITEM_LINK");
    }

    public QVSissWorkitemLink(PathMetadata<?> metadata) {
        super(VSissWorkitemLink.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "V_SISS_WORKITEM_LINK");
    }

}

