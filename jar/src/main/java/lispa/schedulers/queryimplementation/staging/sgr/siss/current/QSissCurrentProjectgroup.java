package lispa.schedulers.queryimplementation.staging.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.current.SissCurrentProjectgroup;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissCurrentProjectgroup is a Querydsl query type for SissCurrentProjectgroup
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissCurrentProjectgroup extends com.mysema.query.sql.RelationalPathBase<SissCurrentProjectgroup> {

    private static final long serialVersionUID = 1986222478;

    public static final QSissCurrentProjectgroup sissCurrentProjectgroup = new QSissCurrentProjectgroup("DMALM_SISS_CURRENT_PROJGROUP");

    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");

    public final StringPath cLocation = createString("C_LOCATION");

    public final StringPath cName = createString("C_NAME");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cUri = createString("C_URI");

    public final StringPath fkParent = createString("FK_PARENT");

    public final StringPath fkUriParent = createString("FK_URI_PARENT");
    
    public final StringPath dmalmProjectGrpPk = createString("SISS_CURRENT_PROJECTGRP_PK");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public QSissCurrentProjectgroup(String variable) {
        super(SissCurrentProjectgroup.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_PROJGROUP");
    }

    public QSissCurrentProjectgroup(Path<? extends SissCurrentProjectgroup> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_PROJGROUP");
    }

    public QSissCurrentProjectgroup(PathMetadata<?> metadata) {
        super(SissCurrentProjectgroup.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_PROJGROUP");
    }

}

