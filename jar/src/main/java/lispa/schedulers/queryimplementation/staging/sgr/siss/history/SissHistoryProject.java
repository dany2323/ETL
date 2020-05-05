package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SissHistoryProject is a Querydsl query type for SissHistoryProject
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissHistoryProject extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryProject> {

    private static final long serialVersionUID = -39681300;

    public static final SissHistoryProject project = new SissHistoryProject("DM_ALM_H_SISS_PROJECT");

    public final BooleanPath cActive = createBoolean("C_ACTIVE");

    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final DatePath<java.sql.Date> cFinish = createDate("C_FINISH", java.sql.Date.class);

    public final DatePath<java.sql.Date> cCreated = createDate("C_CREATED", java.sql.Date.class);

    public final StringPath cId = createString("C_ID");

    public final StringPath cLocation = createString("C_LOCATION");

    public final DatePath<java.sql.Date> cLockworkrecordsdate = createDate("C_LOCKWORKRECORDSDATE", java.sql.Date.class);

    public final StringPath cName = createString("C_NAME");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final DatePath<java.sql.Date> cStart = createDate("C_START", java.sql.Date.class);

    public final StringPath cTrackerprefix = createString("C_TRACKERPREFIX");

    public final StringPath cUri = createString("C_URI");

    public final StringPath fkLead = createString("FK_LEAD");

    public final StringPath fkProjectgroup = createString("FK_PROJECTGROUP");

    public final StringPath fkUriLead = createString("FK_URI_LEAD");

    public final StringPath fkUriProjectgroup = createString("FK_URI_PROJECTGROUP");
        
    public final StringPath cDescription = createString("C_DESCRIPTION");

    public SissHistoryProject(String variable) {
        super(lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryProject.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_PROJECT");
    }

    public SissHistoryProject(Path<? extends lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryProject> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_PROJECT");
    }

    public SissHistoryProject(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryProject.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_PROJECT");
    }

}

