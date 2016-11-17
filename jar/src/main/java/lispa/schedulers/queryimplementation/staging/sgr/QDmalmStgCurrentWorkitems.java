package lispa.schedulers.queryimplementation.staging.sgr;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.DmalmStgCurrentWorkitems;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmStgCurrentWorkitems is a Querydsl query type for DmalmStgCurrentWorkitems
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmStgCurrentWorkitems extends com.mysema.query.sql.RelationalPathBase<DmalmStgCurrentWorkitems> {

    private static final long serialVersionUID = -1044438059;

    public static final QDmalmStgCurrentWorkitems dmalmStgCurrentWorkitems = new QDmalmStgCurrentWorkitems("DMALM_STG_CURRENT_WORKITEMS");

    public final StringPath stgPk = createString("STG_PK");
    
    public final StringPath codice = createString("CODICE");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath type = createString("TYPE");

    public QDmalmStgCurrentWorkitems(String variable) {
        super(DmalmStgCurrentWorkitems.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_CURRENT_WORKITEMS");
    }

    public QDmalmStgCurrentWorkitems(Path<? extends DmalmStgCurrentWorkitems> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_CURRENT_WORKITEMS");
    }

    public QDmalmStgCurrentWorkitems(PathMetadata<?> metadata) {
        super(DmalmStgCurrentWorkitems.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_CURRENT_WORKITEMS");
    }

}

