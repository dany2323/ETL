package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryProjectgroup;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissHistoryProjectgroup is a Querydsl query type for SissHistoryProjectgroup
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissHistoryProjectgroup extends com.mysema.query.sql.RelationalPathBase<SissHistoryProjectgroup> {

    private static final long serialVersionUID = 1328412228;

    public static final QSissHistoryProjectgroup sissHistoryProjectgroup = new QSissHistoryProjectgroup("DMALM_SISS_HISTORY_PROJGROUP");

//    public final BooleanPath cDeleted = createBoolean("C_DELETED");
    public final NumberPath<Integer> cDeleted = createNumber("C_DELETED", Integer.class);

//    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");
    public final NumberPath<Integer> cIsLocal = createNumber("C_IS_LOCAL", Integer.class);

    public final StringPath cLocation = createString("C_LOCATION");

    public final StringPath cName = createString("C_NAME");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cUri = createString("C_URI");

    public final StringPath fkParent = createString("FK_PARENT");

    public final StringPath fkUriParent = createString("FK_URI_PARENT");
    
    public final StringPath dmalmProjgroupPk = createString("SISS_HISTORY_PROJECTGRP_PK");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public QSissHistoryProjectgroup(String variable) {
        super(SissHistoryProjectgroup.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_PROJGROUP");
    }

    public QSissHistoryProjectgroup(Path<? extends SissHistoryProjectgroup> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_PROJGROUP");
    }

    public QSissHistoryProjectgroup(PathMetadata<?> metadata) {
        super(SissHistoryProjectgroup.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_PROJGROUP");
    }

}

