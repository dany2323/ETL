package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import java.sql.Timestamp;

import lispa.schedulers.bean.staging.sgr.sire.history.SireHistoryProject;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireHistoryProject is a Querydsl query type for SireHistoryProject
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireHistoryProject extends com.mysema.query.sql.RelationalPathBase<SireHistoryProject> {

    private static final long serialVersionUID = -730234885;
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public static final QSireHistoryProject sireHistoryProject = new QSireHistoryProject("DMALM_SIRE_HISTORY_PROJECT");

//    public final BooleanPath cActive = createBoolean("C_ACTIVE");
    public final NumberPath<Integer> cActive = createNumber("C_ACTIVE", Integer.class);

//    public final BooleanPath cDeleted = createBoolean("C_DELETED");
    public final NumberPath<Integer> cDeleted = createNumber("C_DELETED", Integer.class);

    public final DateTimePath<java.sql.Timestamp> cFinish = createDateTime("C_FINISH", java.sql.Timestamp.class);

    public final StringPath cId = createString("C_ID");

//    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");
    public final NumberPath<Integer> cIsLocal = createNumber("C_IS_LOCAL", Integer.class);

    public final StringPath cLocation = createString("C_LOCATION");

    public final DateTimePath<java.sql.Timestamp> cLockworkrecordsdate = createDateTime("C_LOCKWORKRECORDSDATE", java.sql.Timestamp.class);

    public final StringPath cName = createString("C_NAME");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final DateTimePath<java.sql.Timestamp> cStart = createDateTime("C_START", java.sql.Timestamp.class);

    public final StringPath cTrackerprefix = createString("C_TRACKERPREFIX");

    public final StringPath cUri = createString("C_URI");

    public final StringPath fkLead = createString("FK_LEAD");

    public final StringPath fkProjectgroup = createString("FK_PROJECTGROUP");

    public final StringPath fkUriLead = createString("FK_URI_LEAD");

    public final StringPath fkUriProjectgroup = createString("FK_URI_PROJECTGROUP");
    
    public final StringPath cTemplate = createString("TEMPLATE");
    
    public final NumberPath<Integer> sireHistoryProjectPk = createNumber("SIRE_HISTORY_PROJECT_PK", Integer.class);
    
    public final DateTimePath<Timestamp> cCreated = createDateTime("C_CREATED", Timestamp.class);
    
    public final StringPath cDescription = createString("C_DESCRIPTION");

    public QSireHistoryProject(String variable) {
        super(SireHistoryProject.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_PROJECT");
    }

    public QSireHistoryProject(Path<? extends SireHistoryProject> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_PROJECT");
    }

    public QSireHistoryProject(PathMetadata<?> metadata) {
        super(SireHistoryProject.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_PROJECT");
    }

}

