package lispa.schedulers.queryimplementation.fonte.sgr.sire.current;

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
public class SireCurrentSubterraUriMap extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentSubterraUriMap> {

    private static final long serialVersionUID = -39681300;

    public static final SireCurrentSubterraUriMap urimap = new SireCurrentSubterraUriMap("SUBTERRA_URI_MAP");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cId = createNumber("C_ID", Long.class);

    public SireCurrentSubterraUriMap(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentSubterraUriMap.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "SUBTERRA_URI_MAP");
    }

    public SireCurrentSubterraUriMap(Path<? extends lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentSubterraUriMap> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "SUBTERRA_URI_MAP");
    }

    public SireCurrentSubterraUriMap(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentSubterraUriMap.class, metadata, DmAlmConstants.POLARION_SCHEMA, "SUBTERRA_URI_MAP");
    }

}

