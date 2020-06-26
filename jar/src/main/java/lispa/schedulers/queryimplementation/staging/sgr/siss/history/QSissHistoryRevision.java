package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import java.sql.Timestamp;

import lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryRevision;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;

/**
 * QSissHistoryUser is a Querydsl query type for SissHistoryUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissHistoryRevision extends com.mysema.query.sql.RelationalPathBase<SissHistoryRevision> {

    private static final long serialVersionUID = -191188439;

    public static final QSissHistoryRevision sissHistoryRevision = new QSissHistoryRevision("DMALM_SISS_HISTORY_REVISION");

    public final StringPath cPk = createString("C_PK");
    
    public final StringPath cUri = createString("C_URI");
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);
    
    public final StringPath cAuthor = createString("C_AUTHOR");
    
    public final DateTimePath<Timestamp> cCreated = createDateTime("C_CREATED", Timestamp.class);
    
    public final StringPath cMessage = createString("C_MESSAGE");
    
//    public final NumberPath<Long> cName = createNumber("C_NAME", Long.class);
    public final StringPath cName = createString("C_NAME");
    
    public final StringPath cRepositoryname = createString("C_REPOSITORYNAME");
    
    public final NumberPath<Integer> cDeleted = createNumber("C_DELETED", Integer.class);
    
    public final NumberPath<Integer> cInternalcommit = createNumber("C_INTERNALCOMMIT", Integer.class);
    
    public final NumberPath<Integer> cIsLocal = createNumber("C_IS_LOCAL", Integer.class);
    
    public final NumberPath<Long> sissHistoryRevisionPk = createNumber("SISS_HISTORY_REVISION_PK", Long.class);
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);
    
    public QSissHistoryRevision(String variable) {
        super(SissHistoryRevision.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_REVISION");
    }

    public QSissHistoryRevision(Path<? extends SissHistoryRevision> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_REVISION");
    }

    public QSissHistoryRevision(PathMetadata<?> metadata) {
        super(SissHistoryRevision.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_REVISION");
    }

}