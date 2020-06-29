package lispa.schedulers.queryimplementation.staging.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissCurrentWorkitem extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.siss.current.DmAlmSissCurrentWorkitem> {

    private static final long serialVersionUID = -570343663;

    public static final SissCurrentWorkitem workitem = new SissCurrentWorkitem("DM_ALM_C_SISS_WORKITEM");

    public final BooleanPath cAutosuspect = createBoolean("C_AUTOSUSPECT");

    public final DatePath<java.sql.Date> cCreated = createDate("C_CREATED", java.sql.Date.class);

    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final DatePath<java.sql.Date> cDuedate = createDate("C_DUEDATE", java.sql.Date.class);

    public final StringPath cId = createString("C_ID");

    public final NumberPath<Float> cInitialestimate = createNumber("C_INITIALESTIMATE", Float.class);

    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");

    public final StringPath cLocation = createString("C_LOCATION");

    public final StringPath cOutlinenumber = createString("C_OUTLINENUMBER");

    public final StringPath cPk = createString("C_PK");

    public final DatePath<java.sql.Date> cPlannedend = createDate("C_PLANNEDEND", java.sql.Date.class);

    public final DatePath<java.sql.Date> cPlannedstart = createDate("C_PLANNEDSTART", java.sql.Date.class);

    public final StringPath cPreviousstatus = createString("C_PREVIOUSSTATUS");

    public final StringPath cPriority = createString("C_PRIORITY");

    public final NumberPath<Float> cRemainingestimate = createNumber("C_REMAININGESTIMATE", Float.class);

    public final StringPath cResolution = createString("C_RESOLUTION");

    public final DatePath<java.sql.Date> cResolvedon = createDate("C_RESOLVEDON", java.sql.Date.class);

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cSeverity = createString("C_SEVERITY");

    public final StringPath cStatus = createString("C_STATUS");

    public final NumberPath<Float> cTimespent = createNumber("C_TIMESPENT", Float.class);

    public final StringPath cTitle = createString("C_TITLE");

    public final StringPath cType = createString("C_TYPE");

    public final DatePath<java.sql.Date> cUpdated = createDate("C_UPDATED", java.sql.Date.class);

    public final StringPath cUri = createString("C_URI");

    public final StringPath fkAuthor = createString("FK_AUTHOR");

    public final StringPath fkModule = createString("FK_MODULE");

    public final StringPath fkProject = createString("FK_PROJECT");

    public final StringPath fkTimepoint = createString("FK_TIMEPOINT");

    public final StringPath fkUriAuthor = createString("FK_URI_AUTHOR");

    public final StringPath fkUriModule = createString("FK_URI_MODULE");

    public final StringPath fkUriProject = createString("FK_URI_PROJECT");

    public final StringPath fkUriTimepoint = createString("FK_URI_TIMEPOINT");

    public SissCurrentWorkitem(String variable) {
        super(lispa.schedulers.bean.staging.sgr.siss.current.DmAlmSissCurrentWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SISS_WORKITEM");
    }

    public SissCurrentWorkitem(Path<? extends lispa.schedulers.bean.staging.sgr.siss.current.DmAlmSissCurrentWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SISS_WORKITEM");
    }

    public SissCurrentWorkitem(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.siss.current.DmAlmSissCurrentWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SISS_WORKITEM");
    }
}