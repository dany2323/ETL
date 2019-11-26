package lispa.schedulers.queryimplementation.staging.sgr.sire.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.sire.current.SireCurrentProjectgroup;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireCurrentProjectgroup is a Querydsl query type for SireCurrentProjectgroup
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireCurrentProjectgroup extends com.mysema.query.sql.RelationalPathBase<SireCurrentProjectgroup> {

    private static final long serialVersionUID = -1610309970;
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public static final QSireCurrentProjectgroup sireCurrentProjectgroup = new QSireCurrentProjectgroup("DMALM_SIRE_CURRENT_PROJGROUP");

    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");

    public final StringPath cLocation = createString("C_LOCATION");

    public final StringPath cName = createString("C_NAME");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cUri = createString("C_URI");

    public final StringPath fkParent = createString("FK_PARENT");

    public final StringPath fkUriParent = createString("FK_URI_PARENT");
    
    public final StringPath sireCurrentProjgroupPk = createString("SIRE_CURRENT_PROJGROUP_PK");

    public QSireCurrentProjectgroup(String variable) {
        super(SireCurrentProjectgroup.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_PROJGROUP");
    }

    public QSireCurrentProjectgroup(Path<? extends SireCurrentProjectgroup> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_PROJGROUP");
    }

    public QSireCurrentProjectgroup(PathMetadata<?> metadata) {
        super(SireCurrentProjectgroup.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_PROJGROUP");

    }
}

