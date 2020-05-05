package lispa.schedulers.queryimplementation.fonte.sgr.current;

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
public class CurrentStructWorkitemLinkedworkitems extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.current.CurrentStructWorkitemLinkedworkitems> {

    private static final long serialVersionUID = -1264613616;

    public static final CurrentStructWorkitemLinkedworkitems structWorkitemLinkedworkitems = new CurrentStructWorkitemLinkedworkitems("STRUCT_WORKITEM_LINKEDWORKITEMS");

    public final NumberPath<Long> fkPWorkitem = createNumber("FK_P_WORKITEM",Long.class);
    
    public final NumberPath<Long> fkUriPWorkitem = createNumber("FK_URI_P_WORKITEM",Long.class);

    public final StringPath cRole = createString("C_ROLE");
   
    public final NumberPath<Long> fkWorkitem = createNumber("FK_WORKITEM",Long.class);

    public final NumberPath<Long> fkUriWorkitem = createNumber("FK_URI_WORKITEM",Long.class);

    public final StringPath cRevision = createString("C_REVISION");

    public final BooleanPath cSuspect = createBoolean("C_SUSPECT");

    public CurrentStructWorkitemLinkedworkitems(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.current.CurrentStructWorkitemLinkedworkitems.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_LINKEDWORKITEMS");
    }

    public CurrentStructWorkitemLinkedworkitems(Path<? extends lispa.schedulers.bean.fonte.sgr.current.CurrentStructWorkitemLinkedworkitems> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_LINKEDWORKITEMS");
    }

    public CurrentStructWorkitemLinkedworkitems(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.current.CurrentStructWorkitemLinkedworkitems.class, metadata, DmAlmConstants.POLARION_SCHEMA, "STRUCT_WORKITEM_LINKEDWORKITEMS");
    }

}

