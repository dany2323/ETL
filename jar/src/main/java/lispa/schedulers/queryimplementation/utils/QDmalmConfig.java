package lispa.schedulers.queryimplementation.utils;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;

import lispa.schedulers.bean.utils.DmalmConfig;
import lispa.schedulers.constant.DmAlmConstants;


/**
 * QDmalmAmbienteTecnologico is a Querydsl query type for DmalmConfig
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmConfig extends com.mysema.query.sql.RelationalPathBase<DmalmConfig> {

	private static final long serialVersionUID = -3879845434380929147L;

	public static final QDmalmConfig dmalmConfig = new QDmalmConfig("CONFIG");

    public final StringPath chiave = createString("chiave");

    public final StringPath valore = createString("valore");

    
    public QDmalmConfig(String variable) {
        super(DmalmConfig.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "CONFIG");
    }

    public QDmalmConfig(Path<? extends DmalmConfig> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "CONFIG");
    }

    public QDmalmConfig(PathMetadata<?> metadata) {
        super(DmalmConfig.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "CONFIG");
    }

}

