package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import java.sql.Timestamp;

import lispa.schedulers.bean.staging.sgr.sire.history.SireHistoryRevision;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;

/**
 * QSireHistoryUser is a Querydsl query type for SireHistoryUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireHistoryRevision extends com.mysema.query.sql.RelationalPathBase<SireHistoryRevision> {

    private static final long serialVersionUID = -191188439;

    public static final QSireHistoryRevision sireHistoryRevision = new QSireHistoryRevision("DMALM_SIRE_HISTORY_REVISION");

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
    
    public final NumberPath<Long> sireHistoryRevisionPk = createNumber("SIRE_HISTORY_REVISION_PK", Long.class);
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);
    
    public QSireHistoryRevision(String variable) {
        super(SireHistoryRevision.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_REVISION");
    }

    public QSireHistoryRevision(Path<? extends SireHistoryRevision> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_REVISION");
    }

    public QSireHistoryRevision(PathMetadata<?> metadata) {
        super(SireHistoryRevision.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_REVISION");
    }

}