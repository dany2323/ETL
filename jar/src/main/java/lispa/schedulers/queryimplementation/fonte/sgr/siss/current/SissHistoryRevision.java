package lispa.schedulers.queryimplementation.fonte.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;

import java.sql.Timestamp;


/**
 * SissCurrentProject is a Querydsl query type for SissCurrentProject
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissHistoryRevision extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentProject> {

    private static final long serialVersionUID = 502430732;

    public static final SissHistoryRevision revision = new SissHistoryRevision("DM_ALM_H_SISS_REVISION");

    public final StringPath cPk = createString("C_PK");
    
    public final StringPath cUri = createString("C_URI");
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);
    
    public final StringPath cAuthor = createString("C_AUTHOR");
    
    public final DateTimePath<Timestamp> cCreated = createDateTime("C_CREATED", Timestamp.class);
    
    public final StringPath cMessage = createString("C_MESSAGE");
    
//    public final NumberPath<Long> cName = createNumber("C_NAME", Long.class);
    public final StringPath cName = createString("C_NAME");
    
    public final StringPath cRepositoryname = createString("C_REPOSITORYNAME");
    
    public final BooleanPath cDeleted = createBoolean("C_DELETED");
    
    public final BooleanPath cInternalcommit = createBoolean("C_INTERNALCOMMIT");

    public SissHistoryRevision(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentProject.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_REVISION");
    }

    public SissHistoryRevision(Path<? extends lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentProject> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_REVISION");
    }

    public SissHistoryRevision(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentProject.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_REVISION");
    }

}

