package lispa.schedulers.queryimplementation.staging.sgr;

import static com.mysema.query.types.PathMetadataFactory.*;

import lispa.schedulers.bean.staging.sgr.DmalmCurrentRevision;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireCurrentWorkitemLinked is a Querydsl query type for SireCurrentWorkitemLinked
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmCurrentRevision extends com.mysema.query.sql.RelationalPathBase<DmalmCurrentRevision> {

    private static final long serialVersionUID = 1317911173;

    public static final QDmalmCurrentRevision currentRevision = new QDmalmCurrentRevision("DMALM_CURRENT_REVISION");

    public final NumberPath<Long> cPk = createNumber("C_PK", Long.class);
    
    public final StringPath cRepo = createString("C_REPOSITORY");
    
    public final NumberPath<Long> cUri = createNumber("C_URI", Long.class);
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);
    
    public final StringPath cDeleted = createString("C_DELETED");
    
    public final StringPath cAuthor = createString("C_AUTHOR");
    
    public final DateTimePath<java.sql.Timestamp> cCreated = createDateTime("C_CREATED", java.sql.Timestamp.class);
    
    public final StringPath cInternalCommit = createString("C_INTERNALCOMMIT");
    
    public final StringPath cMessage = createString("C_MESSAGE");
    
    public final NumberPath<Long> cName = createNumber("C_NAME", Long.class);
    
    public final StringPath cRepositoryname = createString("C_REPOSITORYNAME");
    
    public final StringPath dmalmCurrentRevisionPk = createString("DMALM_REVISION_PK");


    public QDmalmCurrentRevision(String variable) {
        super(DmalmCurrentRevision.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_CURRENT_REVISION");
    }

    public QDmalmCurrentRevision(Path<? extends DmalmCurrentRevision> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_CURRENT_REVISION");
    }

    public QDmalmCurrentRevision(PathMetadata<?> metadata) {
        super(DmalmCurrentRevision.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_CURRENT_REVISION");
    }

}

