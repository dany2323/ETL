package lispa.schedulers.queryimplementation.fonte.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import java.sql.Timestamp;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireHistoryCfWorkitem is a Querydsl query type for SireHistoryCfWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissHistoryRevision extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryRevision> {

    private static final long serialVersionUID = -1899539286;

    public static final SissHistoryRevision revision = new SissHistoryRevision("REVISION");

    public final StringPath cPk = createString("C_PK");
    
    public final NumberPath<Long> cUri = createNumber("C_URI",Long.class);
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);
    
    public final StringPath cAuthor = createString("C_AUTHOR");
    
    public final DateTimePath<Timestamp> cCreated = createDateTime("C_CREATED", Timestamp.class);
    
    public final StringPath cMessage = createString("C_MESSAGE");
    
//    public final NumberPath<Long> cName = createNumber("C_NAME", Long.class);
    public final StringPath cName = createString("C_NAME");
    
    public final StringPath cRepositoryname = createString("C_REPOSITORYNAME");
    
    public final BooleanPath cDeleted = createBoolean("C_DELETED");
    
    public final BooleanPath cInternalCommit = createBoolean("C_INTERNALCOMMIT");
    
    public SissHistoryRevision(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryRevision.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

    public SissHistoryRevision(Path<? extends lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryRevision> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

    public SissHistoryRevision(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryRevision.class, metadata, DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

}

