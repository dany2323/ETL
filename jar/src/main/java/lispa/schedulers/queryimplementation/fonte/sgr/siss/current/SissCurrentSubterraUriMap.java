package lispa.schedulers.queryimplementation.fonte.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireSubterraUriMap is a Querydsl query type for SireSubterraUriMap
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissCurrentSubterraUriMap extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentSubterraUriMap> {

    private static final long serialVersionUID = -39681300;

    public static final SissCurrentSubterraUriMap urimap = new SissCurrentSubterraUriMap("DM_ALM_C_SISS_SUBTERRA_URI_MAP");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cId = createNumber("C_ID", Long.class);

    public SissCurrentSubterraUriMap(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentSubterraUriMap.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SISS_SUBTERRA_URI_MAP");
    }

    public SissCurrentSubterraUriMap(Path<? extends lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentSubterraUriMap> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SISS_SUBTERRA_URI_MAP");
    }

    public SissCurrentSubterraUriMap(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.siss.current.SissCurrentSubterraUriMap.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SISS_SUBTERRA_URI_MAP");
    }

}

