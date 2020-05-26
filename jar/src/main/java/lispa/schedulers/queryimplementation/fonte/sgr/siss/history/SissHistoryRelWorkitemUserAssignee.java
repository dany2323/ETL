package lispa.schedulers.queryimplementation.fonte.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SissHistoryRelWorkitemUserAssignee is a Querydsl query type for SissHistoryRelWorkitemUserAssignee
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissHistoryRelWorkitemUserAssignee extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee> {

    private static final long serialVersionUID = -704953430;

    public static final SissHistoryRelWorkitemUserAssignee relWorkitemUserAssignee = new SissHistoryRelWorkitemUserAssignee("DM_ALM_H_SISS_REL_WI_USER_ASS");

    public final StringPath fkUriUser = createString("FK_URI_USER");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkUser = createString("FK_USER");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");

    public SissHistoryRelWorkitemUserAssignee(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_REL_WI_USER_ASS");
    }

    public SissHistoryRelWorkitemUserAssignee(Path<? extends lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_REL_WI_USER_ASS");
    }

    public SissHistoryRelWorkitemUserAssignee(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_REL_WI_USER_ASS");
    }

}

