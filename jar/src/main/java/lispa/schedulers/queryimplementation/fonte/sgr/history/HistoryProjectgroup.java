package lispa.schedulers.queryimplementation.fonte.sgr.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireHistoryProjectgroup is a Querydsl query type for SireHistoryProjectgroup
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class HistoryProjectgroup extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.history.HistoryProjectgroup> {

    private static final long serialVersionUID = -1506318573;

    public static final HistoryProjectgroup projectgroup = new HistoryProjectgroup("PROJECTGROUP");
    
    public final StringPath cPk = createString("C_PK");
    
    public final StringPath cUri = createString("C_URI");
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final StringPath cLocation = createString("C_LOCATION");

    public final StringPath cName = createString("C_NAME");

    public final StringPath fkParent = createString("FK_PARENT");

    public final StringPath fkUriParent = createString("FK_URI_PARENT");

    public HistoryProjectgroup(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.history.HistoryProjectgroup.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "PROJECTGROUP");
    }

    public HistoryProjectgroup(Path<? extends lispa.schedulers.bean.fonte.sgr.history.HistoryProjectgroup> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "PROJECTGROUP");
    }

    public HistoryProjectgroup(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.history.HistoryProjectgroup.class, metadata, DmAlmConstants.POLARION_SCHEMA, "PROJECTGROUP");
    }

}

