package lispa.schedulers.queryimplementation.fonte.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SissCurrentProject is a Querydsl query type for SissCurrentProject
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissCurrentProject extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentProject> {

    private static final long serialVersionUID = 502430732;

    public static final SissCurrentProject project = new SissCurrentProject("PROJECT");

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

    public final com.mysema.query.sql.PrimaryKey<lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentProject> projectPk = createPrimaryKey(cPk);

    public SissCurrentProject(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentProject.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "PROJECT");
    }

    public SissCurrentProject(Path<? extends lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentProject> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "PROJECT");
    }

    public SissCurrentProject(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentProject.class, metadata, DmAlmConstants.POLARION_SCHEMA, "PROJECT");
    }

}

