package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import java.sql.Timestamp;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SissHistoryCfWorkitem is a Querydsl query type for SissHistoryCfWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissHistoryRevision extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryRevision> {

    private static final long serialVersionUID = -1899539286;

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
    
    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");

    public SissHistoryRevision(String variable) {
        super(lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryRevision.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_REVISION");
    }

    public SissHistoryRevision(Path<? extends lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryRevision> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_REVISION");
    }

    public SissHistoryRevision(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.siss.history.DmAlmSissHistoryRevision.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_REVISION");
    }

}

