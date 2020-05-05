package lispa.schedulers.queryimplementation.fonte.sgr.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireHistoryWorkitem is a Querydsl query type for SireHistoryWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class HistoryWorkitem extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.history.HistoryWorkitem> {

    private static final long serialVersionUID = 1747155943;

    public static final HistoryWorkitem workitem = new HistoryWorkitem("WORKITEM");
    
    public final StringPath cPk = createString("C_PK");
    
    public final StringPath cUri = createString("C_URI");
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);
    
    public final BooleanPath cDeleted = createBoolean("C_DELETED");
    
    public final StringPath fkAuthor = createString("FK_AUTHOR");
    
    public final StringPath fkUriAuthor = createString("FK_URI_AUTHOR");

    public final DateTimePath<java.sql.Timestamp> cCreated = createDateTime("C_CREATED", java.sql.Timestamp.class);

    public final DatePath<java.sql.Date> cDuedate = createDate("C_DUEDATE", java.sql.Date.class);

    public final StringPath cId = createString("C_ID");

    public final NumberPath<Float> cInitialestimate = createNumber("C_INITIALESTIMATE", Float.class);

    public final StringPath cLocation = createString("C_LOCATION");
    
    public final StringPath fkModule = createString("FK_MODULE");
    
    public final StringPath fkUriModule = createString("FK_URI_MODULE");

    public final StringPath cOutlinenumber = createString("C_OUTLINENUMBER");

    public final DateTimePath<java.sql.Timestamp> cPlannedend = createDateTime("C_PLANNEDEND", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> cPlannedstart = createDateTime("C_PLANNEDSTART", java.sql.Timestamp.class);

    public final StringPath cPreviousstatus = createString("C_PREVIOUSSTATUS");

    public final StringPath cPriority = createString("C_PRIORITY");
    
    public final StringPath fkProject = createString("FK_PROJECT");
    
    public final StringPath fkUriProject = createString("FK_URI_PROJECT");

    public final NumberPath<Float> cRemainingestimate = createNumber("C_REMAININGESTIMATE", Float.class);

    public final StringPath cResolution = createString("C_RESOLUTION");

    public final DateTimePath<java.sql.Timestamp> cResolvedon = createDateTime("C_RESOLVEDON", java.sql.Timestamp.class);

    public final StringPath cSeverity = createString("C_SEVERITY");

    public final StringPath cStatus = createString("C_STATUS");
    
    public final StringPath fkTimepoint = createString("FK_TIMEPOINT");

    public final StringPath fkUriTimepoint = createString("FK_URI_TIMEPOINT");

    public final NumberPath<Float> cTimespent = createNumber("C_TIMESPENT", Float.class);

    public final StringPath cTitle = createString("C_TITLE");

    public final StringPath cType = createString("C_TYPE");

    public final DateTimePath<java.sql.Timestamp> cUpdated = createDateTime("C_UPDATED", java.sql.Timestamp.class);

    public HistoryWorkitem(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.history.HistoryWorkitem.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "WORKITEM");
    }

    public HistoryWorkitem(Path<? extends lispa.schedulers.bean.fonte.sgr.history.HistoryWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "WORKITEM");
    }

    public HistoryWorkitem(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.history.HistoryWorkitem.class, metadata, DmAlmConstants.POLARION_SCHEMA, "WORKITEM");
    }

}

