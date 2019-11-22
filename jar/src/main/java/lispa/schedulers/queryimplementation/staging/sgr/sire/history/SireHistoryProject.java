package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireHistoryProject is a Querydsl query type for SireHistoryProject
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SireHistoryProject extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryProject> {

    private static final long serialVersionUID = -39681300;

    public static final SireHistoryProject project = new SireHistoryProject("DM_ALM_H_SIRE_PROJECT");

    public final BooleanPath cActive = createBoolean("C_ACTIVE");

    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final DatePath<java.sql.Date> cFinish = createDate("C_FINISH", java.sql.Date.class);

    public final StringPath cId = createString("C_ID");

    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");

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
    
    public final StringPath template = createString("TEMPLATE");
    
    public final StringPath cDescription = createString("C_DESCRIPTION");

    public SireHistoryProject(String variable) {
        super(lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryProject.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_PROJECT");
    }

    public SireHistoryProject(Path<? extends lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryProject> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_PROJECT");
    }

    public SireHistoryProject(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.sire.history.DmAlmSireHistoryProject.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_PROJECT");
    }

}

