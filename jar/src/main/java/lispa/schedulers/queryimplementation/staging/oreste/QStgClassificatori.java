package lispa.schedulers.queryimplementation.staging.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.oreste.StgClassificatori;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QStgClassificatori is a Querydsl query type for StgClassificatori
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStgClassificatori extends com.mysema.query.sql.RelationalPathBase<StgClassificatori> {

    private static final long serialVersionUID = -410830336;

    public static final QStgClassificatori stgClassificatori = new QStgClassificatori("DMALM_STG_CLASSIFICATORI");

    public final StringPath codiceClassificatore = createString("CODICE_CLASSIFICATORE");

    public final StringPath codiceTipologia = createString("CODICE_TIPOLOGIA");
    
    public final StringPath dmalmStgClassificatoriPk = createString("DMALM_STG_CLASSIFICATORI_PK");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final NumberPath<Double> id = createNumber("ID", Double.class);

    public QStgClassificatori(String variable) {
        super(StgClassificatori.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_CLASSIFICATORI");
    }

    public QStgClassificatori(Path<? extends StgClassificatori> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_CLASSIFICATORI");
    }

    public QStgClassificatori(PathMetadata<?> metadata) {
        super(StgClassificatori.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_CLASSIFICATORI");
    }

}

