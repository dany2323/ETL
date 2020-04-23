package lispa.schedulers.queryimplementation.staging.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.current.SissCurrentWorkitem;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissCurrentWorkitem is a Querydsl query type for SissCurrentWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissCurrentWorkitem extends com.mysema.query.sql.RelationalPathBase<SissCurrentWorkitem> {

    private static final long serialVersionUID = 1849195564;

    public static final QSissCurrentWorkitem sissCurrentWorkitem = new QSissCurrentWorkitem("DMALM_SISS_CURRENT_WORKITEM");

    public final DateTimePath<java.sql.Timestamp> cCreated = createDateTime("C_CREATED", java.sql.Timestamp.class);

    //public final BooleanPath cDeleted = createBoolean("C_DELETED");
    public final NumberPath<Integer> cDeleted = createNumber("C_DELETED", Integer.class);

    public final DateTimePath<java.sql.Timestamp> cDuedate = createDateTime("C_DUEDATE", java.sql.Timestamp.class);

    public final StringPath cId = createString("C_ID");

    public final NumberPath<Float> cInitialestimate = createNumber("C_INITIALESTIMATE", Float.class);

    //public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");
    public final NumberPath<Integer> cIsLocal = createNumber("C_IS_LOCAL", Integer.class);

    public final StringPath cLocation = createString("C_LOCATION");

    public final StringPath cOutlinenumber = createString("C_OUTLINENUMBER");

    public final StringPath cPk = createString("C_PK");

    public final DateTimePath<java.sql.Timestamp> cPlannedend = createDateTime("C_PLANNEDEND", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> cPlannedstart = createDateTime("C_PLANNEDSTART", java.sql.Timestamp.class);

    public final StringPath cPreviousstatus = createString("C_PREVIOUSSTATUS");

    public final StringPath cPriority = createString("C_PRIORITY");

    public final NumberPath<Float> cRemainingestimate = createNumber("C_REMAININGESTIMATE", Float.class);

    public final StringPath cResolution = createString("C_RESOLUTION");

    public final DateTimePath<java.sql.Timestamp> cResolvedon = createDateTime("C_RESOLVEDON", java.sql.Timestamp.class);

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cSeverity = createString("C_SEVERITY");

    public final StringPath cStatus = createString("C_STATUS");

    public final NumberPath<Float> cTimespent = createNumber("C_TIMESPENT", Float.class);

    public final StringPath cTitle = createString("C_TITLE");

    public final StringPath cType = createString("C_TYPE");

    public final DateTimePath<java.sql.Timestamp> cUpdated = createDateTime("C_UPDATED", java.sql.Timestamp.class);

    public final StringPath cUri = createString("C_URI");

    public final StringPath fkAuthor = createString("FK_AUTHOR");

    public final StringPath fkModule = createString("FK_MODULE");

    public final StringPath fkProject = createString("FK_PROJECT");

    public final StringPath fkTimepoint = createString("FK_TIMEPOINT");

    public final StringPath fkUriAuthor = createString("FK_URI_AUTHOR");

    public final StringPath fkUriModule = createString("FK_URI_MODULE");

    public final StringPath fkUriProject = createString("FK_URI_PROJECT");

    public final StringPath fkUriTimepoint = createString("FK_URI_TIMEPOINT");
    
    public final StringPath dmalmCurrentWorkitemPk = createString("SISS_CURRENT_WORKITEM_PK");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public QSissCurrentWorkitem(String variable) {
        super(SissCurrentWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_WORKITEM");
    }

    public QSissCurrentWorkitem(Path<? extends SissCurrentWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_WORKITEM");
    }

    public QSissCurrentWorkitem(PathMetadata<?> metadata) {
        super(SissCurrentWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_WORKITEM");
    }

}

