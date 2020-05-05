package lispa.schedulers.queryimplementation.fonte.sgr.sire.current;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireCurrentStructWorkitemLinkedworkitems is a Querydsl query type for SireCurrentStructWorkitemLinkedworkitems
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SireCurrentStructWorkitemLinkedworkitems extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems> {

    private static final long serialVersionUID = -1264613616;

    public static final SireCurrentStructWorkitemLinkedworkitems structWorkitemLinkedworkitems = new SireCurrentStructWorkitemLinkedworkitems("STRUCT_WORKITEM_LINKEDWORKITEMS");

    public final StringPath cRevision = createString("C_REVISION");

    public final StringPath cRole = createString("C_ROLE");

    public final BooleanPath cSuspect = createBoolean("C_SUSPECT");

    public final NumberPath<Long> fkPWorkitem = createNumber("FK_P_WORKITEM",Long.class);

    public final NumberPath<Long> fkUriPWorkitem = createNumber("FK_URI_P_WORKITEM",Long.class);

    public final NumberPath<Long> fkUriWorkitem = createNumber("FK_URI_WORKITEM",Long.class);

    public final NumberPath<Long> fkWorkitem = createNumber("FK_WORKITEM",Long.class);

    public SireCurrentStructWorkitemLinkedworkitems(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_LINKEDWORKITEMS");
    }

    public SireCurrentStructWorkitemLinkedworkitems(Path<? extends lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_LINKEDWORKITEMS");
    }

    public SireCurrentStructWorkitemLinkedworkitems(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems.class, metadata, DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_LINKEDWORKITEMS");
    }

}

