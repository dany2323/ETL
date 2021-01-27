package lispa.schedulers.queryimplementation.staging.sgr.sire.current;

import static com.mysema.query.types.PathMetadataFactory.*;

import lispa.schedulers.bean.staging.sgr.DmalmCurrentSubterraUriMap;
import lispa.schedulers.constant.DmAlmConstants;
import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireCurrentWorkitemLinked is a Querydsl query type for SireCurrentWorkitemLinked
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireDmalmCurrentSubterraUriMap extends com.mysema.query.sql.RelationalPathBase<DmalmCurrentSubterraUriMap> {

    private static final long serialVersionUID = 1317911173;

    public static final QSireDmalmCurrentSubterraUriMap currentSubterraUriMap = new QSireDmalmCurrentSubterraUriMap("DM_ALM_C_SIRE_SUBTERRA_URI_MAP");

    public final StringPath cPk = createString("C_PK");
    
    public final NumberPath<Long> cId = createNumber("C_ID", Long.class);
    
    public final StringPath dmalmCurrentSubterraUriMapPk = createString("CURR_SUB_URI_MAP_PK");


    public QSireDmalmCurrentSubterraUriMap(String variable) {
        super(DmalmCurrentSubterraUriMap.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_SUBTERRA_URI_MAP");
    }

    public QSireDmalmCurrentSubterraUriMap(Path<? extends DmalmCurrentSubterraUriMap> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_SUBTERRA_URI_MAP");
    }

    public QSireDmalmCurrentSubterraUriMap(PathMetadata<?> metadata) {
        super(DmalmCurrentSubterraUriMap.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_SUBTERRA_URI_MAP");
    }

}

