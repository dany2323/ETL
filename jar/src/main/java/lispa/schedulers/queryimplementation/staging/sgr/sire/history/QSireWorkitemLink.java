package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.VSireWorkitemLink;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QVSireWorkitemLink is a Querydsl query type for VSireWorkitemLink
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireWorkitemLink extends com.mysema.query.sql.RelationalPathBase<VSireWorkitemLink> {

    private static final long serialVersionUID = 13312495;

    public static final QSireWorkitemLink vSireWorkitemLink = new QSireWorkitemLink("SIRE_WORKITEM_LINK");

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

    public QSireWorkitemLink(String variable) {
        super(VSireWorkitemLink.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "SIRE_WORKITEM_LINK");
    }

    public QSireWorkitemLink(Path<? extends VSireWorkitemLink> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "SIRE_WORKITEM_LINK");
    }

    public QSireWorkitemLink(PathMetadata<?> metadata) {
        super(VSireWorkitemLink.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "SIRE_WORKITEM_LINK");
    }

}

