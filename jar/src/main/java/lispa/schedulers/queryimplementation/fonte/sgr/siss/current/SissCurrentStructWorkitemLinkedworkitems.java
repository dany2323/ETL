package lispa.schedulers.queryimplementation.fonte.sgr.siss.current;


import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SissCurrentStructWorkitemLinkedworkitems is a Querydsl query type for SissCurrentStructWorkitemLinkedworkitems
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissCurrentStructWorkitemLinkedworkitems extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems> {

    private static final long serialVersionUID = -1544427472;

    public static final SissCurrentStructWorkitemLinkedworkitems structWorkitemLinkedworkitems = new SissCurrentStructWorkitemLinkedworkitems("STRUCT_WORKITEM_LINKEDWORKITEMS");

    public final StringPath cRevision = createString("C_REVISION");

    public final StringPath cRole = createString("C_ROLE");

    public final BooleanPath cSuspect = createBoolean("C_SUSPECT");

    public final NumberPath<Long> fkPWorkitem = createNumber("FK_P_WORKITEM",Long.class);

    public final NumberPath<Long> fkUriPWorkitem = createNumber("FK_URI_P_WORKITEM",Long.class);

    public final NumberPath<Long> fkUriWorkitem = createNumber("FK_URI_WORKITEM",Long.class);

    public final NumberPath<Long> fkWorkitem = createNumber("FK_WORKITEM",Long.class);

    public SissCurrentStructWorkitemLinkedworkitems(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_LINKEDWORKITEMS");
    }

    public SissCurrentStructWorkitemLinkedworkitems(Path<? extends lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_LINKEDWORKITEMS");
    }

    public SissCurrentStructWorkitemLinkedworkitems(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentStructWorkitemLinkedworkitems.class, metadata, DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_LINKEDWORKITEMS");
    }

}
