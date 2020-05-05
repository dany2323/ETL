package lispa.schedulers.queryimplementation.fonte.sgr.sire.history;

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
public class SireHistoryRevision extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryRevision> {

    private static final long serialVersionUID = -1899539286;

    public static final SireHistoryRevision revision = new SireHistoryRevision("REVISION");

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
    
    public final BooleanPath cInternalCommit = createBoolean("C_INTERNALCOMMIT");
    
    public SireHistoryRevision(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryRevision.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

    public SireHistoryRevision(Path<? extends lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryRevision> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

    public SireHistoryRevision(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryRevision.class, metadata, DmAlmConstants.POLARION_SCHEMA, "REVISION");
    }

}

