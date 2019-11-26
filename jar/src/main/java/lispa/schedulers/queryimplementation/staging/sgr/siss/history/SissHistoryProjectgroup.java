package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SissHistoryProjectgroup is a Querydsl query type for SissHistoryProjectgroup
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissHistoryProjectgroup extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryProjectgroup> {

    private static final long serialVersionUID = -1506318573;

    public static final SissHistoryProjectgroup projectgroup = new SissHistoryProjectgroup("DM_ALM_H_SISS_PROJECTGROUP");

    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");

    public final StringPath cLocation = createString("C_LOCATION");

    public final StringPath cName = createString("C_NAME");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cUri = createString("C_URI");

    public final StringPath fkParent = createString("FK_PARENT");

    public final StringPath fkUriParent = createString("FK_URI_PARENT");

    public SissHistoryProjectgroup(String variable) {
        super(lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryProjectgroup.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_PROJECTGROUP");
    }

    public SissHistoryProjectgroup(Path<? extends lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryProjectgroup> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_PROJECTGROUP");
    }

    public SissHistoryProjectgroup(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryProjectgroup.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_PROJECTGROUP");
    }

}

