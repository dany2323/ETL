package lispa.schedulers.queryimplementation.fonte.sgr.sire.current;

import static com.mysema.query.types.PathMetadataFactory.*;

import java.sql.Timestamp;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireSubterraUriMap is a Querydsl query type for SireSubterraUriMap
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SireCurrentRevision extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentRevision> {

    private static final long serialVersionUID = -39681300;

    public static final SireCurrentRevision revision = new SireCurrentRevision("DM_ALM_H_SIRE_REVISION");

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
    
    public SireCurrentRevision(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentRevision.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_SUBTERRA_URI_MAP");
    }

    public SireCurrentRevision(Path<? extends lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentRevision> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_SUBTERRA_URI_MAP");
    }

    public SireCurrentRevision(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentRevision.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_SUBTERRA_URI_MAP");
    }

}

