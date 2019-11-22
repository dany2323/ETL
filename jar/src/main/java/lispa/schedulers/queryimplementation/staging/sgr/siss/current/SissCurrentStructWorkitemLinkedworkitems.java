package lispa.schedulers.queryimplementation.staging.sgr.siss.current;


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
public class SissCurrentStructWorkitemLinkedworkitems extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.siss.current.DmAlmSissCurrentStructWorkitemLinkedworkitems> {

    private static final long serialVersionUID = -1544427472;

    public static final SissCurrentStructWorkitemLinkedworkitems structWorkitemLinkedworkitems = new SissCurrentStructWorkitemLinkedworkitems("DM_ALM_C_SISS_STRUCT_WI_LINKWI");

    public final StringPath cRevision = createString("C_REVISION");

    public final StringPath cRole = createString("C_ROLE");

    public final BooleanPath cSuspect = createBoolean("C_SUSPECT");

    public final StringPath fkPWorkitem = createString("FK_P_WORKITEM");

    public final StringPath fkUriPWorkitem = createString("FK_URI_P_WORKITEM");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");

    public SissCurrentStructWorkitemLinkedworkitems(String variable) {
        super(lispa.schedulers.bean.staging.sgr.siss.current.DmAlmSissCurrentStructWorkitemLinkedworkitems.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SISS_STRUCT_WI_LINKWI");
    }

    public SissCurrentStructWorkitemLinkedworkitems(Path<? extends lispa.schedulers.bean.staging.sgr.siss.current.DmAlmSissCurrentStructWorkitemLinkedworkitems> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SISS_STRUCT_WI_LINKWI");
    }

    public SissCurrentStructWorkitemLinkedworkitems(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.siss.current.DmAlmSissCurrentStructWorkitemLinkedworkitems.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SISS_STRUCT_WI_LINKWI");
    }

}
