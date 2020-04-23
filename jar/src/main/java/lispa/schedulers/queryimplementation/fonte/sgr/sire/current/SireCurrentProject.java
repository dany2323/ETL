package lispa.schedulers.queryimplementation.fonte.sgr.sire.current;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireCurrentProject is a Querydsl query type for SireCurrentProject
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SireCurrentProject extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentProject> {

    private static final long serialVersionUID = -39681300;
    
    public static final SireCurrentProject project = new SireCurrentProject("PROJECT");
    
    public final NumberPath<Long> cPk = createNumber("C_PK", Long.class);
    
    public final NumberPath<Long> cUri = createNumber("C_URI", Long.class);
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);
    
    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final BooleanPath cActive = createBoolean("C_ACTIVE");

    public final StringPath cDescription = createString("C_DESCRIPTION");
    
    public final DatePath<java.sql.Date> cFinish = createDate("C_FINISH", java.sql.Date.class);

    public final StringPath cId = createString("C_ID");
    
    public final NumberPath<Long> fkLead = createNumber("FK_LEAD", Long.class);
    
    public final NumberPath<Long> fkUriLead = createNumber("FK_URI_LEAD", Long.class);
        
    public final StringPath cLocation = createString("C_LOCATION");

    public final DatePath<java.sql.Date> cLockworkrecordsdate = createDate("C_LOCKWORKRECORDSDATE", java.sql.Date.class);

    public final StringPath cName = createString("C_NAME");

    public final NumberPath<Long> fkUriProjectgroup = createNumber("FK_URI_PROJECTGROUP", Long.class);

    public final NumberPath<Long> fkProjectgroup = createNumber("FK_PROJECTGROUP", Long.class);

    public final DatePath<java.sql.Date> cStart = createDate("C_START", java.sql.Date.class);

    public final StringPath cTrackerprefix = createString("C_TRACKERPREFIX");

    
    public SireCurrentProject(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentProject.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "PROJECT");
    }

    public SireCurrentProject(Path<? extends lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentProject> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "PROJECT");
    }

    public SireCurrentProject(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentProject.class, metadata, DmAlmConstants.POLARION_SCHEMA, "PROJECT");
    }

}

