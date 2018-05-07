package lispa.schedulers.queryimplementation.fonte.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QsissCurrentWorkitemLinked is a Querydsl query type for sissCurrentWorkitemLinked
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissCurrentRevision extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentRevision> {

    private static final long serialVersionUID = 1317911173;

    public static final SissCurrentRevision revision = new SissCurrentRevision("REVISION");

    public final NumberPath<Long> cPk = createNumber("C_PK", Long.class);
    
    public final NumberPath<Long> cUri = createNumber("C_URI", Long.class);
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);
    
    public final BooleanPath cDeleted = createBoolean("C_DELETED");
    
    public final StringPath cAuthor = createString("C_AUTHOR");
    
    public final DateTimePath<java.sql.Timestamp> cCreated = createDateTime("C_CREATED", java.sql.Timestamp.class);
    
    public final BooleanPath cInternalCommit = createBoolean("C_INTERNALCOMMIT");
    
    public final StringPath cMessage = createString("C_MESSAGE");
    
    public final StringPath cName = createString("C_NAME");
    
    public final StringPath cRepositoryname = createString("C_REPOSITORYNAME");
    
    public SissCurrentRevision(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentRevision.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

    public SissCurrentRevision(Path<? extends lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentRevision> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

    public SissCurrentRevision(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentRevision.class, metadata, DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

}

