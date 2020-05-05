package lispa.schedulers.queryimplementation.fonte.sgr.history;

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
public class HistoryRevision extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.history.HistoryRevision> {

    private static final long serialVersionUID = -1899539286;

    public static final HistoryRevision revision = new HistoryRevision("REVISION");

    public final StringPath cPk = createString("C_PK");
    
    public final StringPath cUri = createString("C_URI");
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);
    
    public final BooleanPath cDeleted = createBoolean("C_DELETED");
    
    public final StringPath cAuthor = createString("C_AUTHOR");
    
    public final DateTimePath<Timestamp> cCreated = createDateTime("C_CREATED", Timestamp.class);
    
    public final BooleanPath cInternalCommit = createBoolean("C_INTERNALCOMMIT");
    
    public final StringPath cMessage = createString("C_MESSAGE");
    
    public final StringPath cName = createString("C_NAME");
    
    public final StringPath cRepositoryname = createString("C_REPOSITORYNAME");
        
    public HistoryRevision(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.history.HistoryRevision.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

    public HistoryRevision(Path<? extends lispa.schedulers.bean.fonte.sgr.history.HistoryRevision> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

    public HistoryRevision(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.history.HistoryRevision.class, metadata, DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

}

