package lispa.schedulers.queryimplementation.staging.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.current.SissCurrentProject;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissCurrentProject is a Querydsl query type for SissCurrentProject
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissCurrentProject extends com.mysema.query.sql.RelationalPathBase<SissCurrentProject> {

    private static final long serialVersionUID = 1134669713;

    public static final QSissCurrentProject sissCurrentProject = new QSissCurrentProject("DMALM_SISS_CURRENT_PROJECT");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final NumberPath<Integer> cActive = createNumber("C_ACTIVE",Integer.class);

    public final NumberPath<Integer> cDeleted = createNumber("C_DELETED",Integer.class);

    public final DateTimePath<java.sql.Timestamp> cFinish = createDateTime("C_FINISH", java.sql.Timestamp.class);

    public final StringPath cId = createString("C_ID");

    public final NumberPath<Integer> cIsLocal = createNumber("C_IS_LOCAL",Integer.class);

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
    
    public final StringPath sissCurrentProjectPk = createString("SISS_CURRENT_PROJECT_PK");

 
    public QSissCurrentProject(String variable) {
        super(SissCurrentProject.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_PROJECT");
    }

    public QSissCurrentProject(Path<? extends SissCurrentProject> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_PROJECT");
    }

    public QSissCurrentProject(PathMetadata<?> metadata) {
        super(SissCurrentProject.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_PROJECT");
    }

}

