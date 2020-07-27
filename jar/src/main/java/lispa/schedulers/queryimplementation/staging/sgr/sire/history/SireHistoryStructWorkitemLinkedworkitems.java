package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireHistoryStructWorkitemLinkedworkitems is a Querydsl query type for SireHistoryStructWorkitemLinkedworkitems
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SireHistoryStructWorkitemLinkedworkitems extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryStructWorkitemLinkedworkitems> {

    private static final long serialVersionUID = -1264613616;

    public static final SireHistoryStructWorkitemLinkedworkitems structWorkitemLinkedworkitems = new SireHistoryStructWorkitemLinkedworkitems("DM_ALM_H_SIRE_STRUCT_WI_LINKWI");

    public final StringPath cRevision = createString("C_REVISION");

    public final StringPath cRole = createString("C_ROLE");

    public final BooleanPath cSuspect = createBoolean("C_SUSPECT");

    public final StringPath fkPWorkitem = createString("FK_P_WORKITEM");

    public final StringPath fkUriPWorkitem = createString("FK_URI_P_WORKITEM");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");

    public SireHistoryStructWorkitemLinkedworkitems(String variable) {
        super(lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryStructWorkitemLinkedworkitems.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_STRUCT_WI_LINKWI");
    }

    public SireHistoryStructWorkitemLinkedworkitems(Path<? extends lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryStructWorkitemLinkedworkitems> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_STRUCT_WI_LINKWI");
    }

    public SireHistoryStructWorkitemLinkedworkitems(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryStructWorkitemLinkedworkitems.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_STRUCT_WI_LINKWI");
    }

}
