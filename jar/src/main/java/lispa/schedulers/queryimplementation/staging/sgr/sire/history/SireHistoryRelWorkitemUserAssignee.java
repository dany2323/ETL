package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireHistoryRelWorkitemUserAssignee is a Querydsl query type for SireHistoryRelWorkitemUserAssignee
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SireHistoryRelWorkitemUserAssignee extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryRelWorkitemUserAssignee> {

    private static final long serialVersionUID = -444495222;

    public static final SireHistoryRelWorkitemUserAssignee relWorkitemUserAssignee = new SireHistoryRelWorkitemUserAssignee("DM_ALM_H_SIRE_REL_WI_USER_ASS");

    public final StringPath fkUriUser = createString("FK_URI_USER");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkUser = createString("FK_USER");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");

    public SireHistoryRelWorkitemUserAssignee(String variable) {
        super(lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryRelWorkitemUserAssignee.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_REL_WI_USER_ASS");
    }

    public SireHistoryRelWorkitemUserAssignee(Path<? extends lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryRelWorkitemUserAssignee> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_REL_WI_USER_ASS");
    }

    public SireHistoryRelWorkitemUserAssignee(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryRelWorkitemUserAssignee.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_REL_WI_USER_ASS");
    }

}

