package lispa.schedulers.queryimplementation.staging.sgr.sire.current;


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
public class SireCurrentStructWorkitemLinkedworkitems extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.sire.current.DmAlmSireCurrentStructWorkitemLinkedworkitems> {

    private static final long serialVersionUID = -1544427472;

    public static final SireCurrentStructWorkitemLinkedworkitems structWorkitemLinkedworkitems = new SireCurrentStructWorkitemLinkedworkitems("DM_ALM_C_SIRE_STRUCT_WI_LINKWI");

    public final StringPath cRevision = createString("C_REVISION");

    public final StringPath cRole = createString("C_ROLE");

    public final NumberPath<Integer> cSuspect = createNumber("C_SUSPECT",Integer.class);

    public final NumberPath<Long> fkPWorkitem = createNumber("FK_P_WORKITEM",Long.class);

    public final NumberPath<Long> fkUriPWorkitem = createNumber("FK_URI_P_WORKITEM",Long.class);

    public final NumberPath<Long> fkUriWorkitem = createNumber("FK_URI_WORKITEM",Long.class);

    public final NumberPath<Long> fkWorkitem = createNumber("FK_WORKITEM",Long.class);

    public SireCurrentStructWorkitemLinkedworkitems(String variable) {
        super(lispa.schedulers.bean.staging.sgr.sire.current.DmAlmSireCurrentStructWorkitemLinkedworkitems.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_STRUCT_WI_LINKWI");
    }

    public SireCurrentStructWorkitemLinkedworkitems(Path<? extends lispa.schedulers.bean.staging.sgr.sire.current.DmAlmSireCurrentStructWorkitemLinkedworkitems> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_STRUCT_WI_LINKWI");
    }

    public SireCurrentStructWorkitemLinkedworkitems(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.sire.current.DmAlmSireCurrentStructWorkitemLinkedworkitems.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_STRUCT_WI_LINKWI");
    }

}
