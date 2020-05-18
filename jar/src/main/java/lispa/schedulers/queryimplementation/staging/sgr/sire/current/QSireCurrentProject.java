package lispa.schedulers.queryimplementation.staging.sgr.sire.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.sire.current.SireCurrentProject;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireCurrentProject is a Querydsl query type for SireCurrentProject
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireCurrentProject extends com.mysema.query.sql.RelationalPathBase<SireCurrentProject> {

    private static final long serialVersionUID = 592557681;
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public static final QSireCurrentProject sireCurrentProject = new QSireCurrentProject("DMALM_SIRE_CURRENT_PROJECT");

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
    
    public final StringPath sireCurrentProjectPk = createString("SIRE_CURRENT_PROJECT_PK");

    public final StringPath fkUriProjectgroup = createString("FK_URI_PROJECTGROUP");

    public QSireCurrentProject(String variable) {
        super(SireCurrentProject.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_PROJECT");
    }

    public QSireCurrentProject(Path<? extends SireCurrentProject> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_PROJECT");
    }

    public QSireCurrentProject(PathMetadata<?> metadata) {
        super(SireCurrentProject.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_PROJECT");
    }

}

