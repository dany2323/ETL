package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * QStructWorkitemHyperlinks is a Querydsl query type for StructWorkitemHyperlinks
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissHistoryHyperlink extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryHyperlink> {

    private static final long serialVersionUID = -42223250;

    public static final SissHistoryHyperlink structWorkitemHyperlinks = new SissHistoryHyperlink("DM_ALM_H_SISS_STRUCT_WI_HYLINK");

    public final StringPath cRole = createString("C_ROLE");

    public final StringPath cUrl = createString("C_URL");

    public final StringPath fkPWorkitem = createString("FK_P_WORKITEM");

    public final StringPath fkUriPWorkitem = createString("FK_URI_P_WORKITEM");

    public SissHistoryHyperlink(String variable) {
        super(lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryHyperlink.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_STRUCT_WI_HYLINK");
    }

    public SissHistoryHyperlink(Path<? extends lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryHyperlink> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_STRUCT_WI_HYLINK");
    }

    public SissHistoryHyperlink(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryHyperlink.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_STRUCT_WI_HYLINK");
    }

}

