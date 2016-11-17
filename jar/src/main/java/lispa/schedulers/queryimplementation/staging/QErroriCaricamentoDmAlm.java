package lispa.schedulers.queryimplementation.staging;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.ErroriCaricamentoDmAlm;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QErroriCaricamentoDmAlm is a Querydsl query type for ErroriCaricamentoDmAlm
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QErroriCaricamentoDmAlm extends com.mysema.query.sql.RelationalPathBase<ErroriCaricamentoDmAlm> {

    private static final long serialVersionUID = 218736414;

    public static final QErroriCaricamentoDmAlm erroriCaricamentoDmAlm = new QErroriCaricamentoDmAlm("DMALM_ERRORI_CARICAMENTO");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath entitaSorgente = createString("ENTITA_SORGENTE");

    public final StringPath entitaTarget = createString("ENTITA_TARGET");

    public final StringPath flagErrore = createString("FLAG_ERRORE");

    public final StringPath motivoErrore = createString("MOTIVO_ERRORE");

    public final StringPath recordErrore = createString("RECORD_ERRORE");
    
    public final NumberPath<Integer> errorePk = createNumber("ERRORE_PK", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<ErroriCaricamentoDmAlm> erroriCaricamentoDmAlmPk = createPrimaryKey(dataCaricamento, entitaTarget);

    public QErroriCaricamentoDmAlm(String variable) {
        super(ErroriCaricamentoDmAlm.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_ERRORI_CARICAMENTO");
    }

    public QErroriCaricamentoDmAlm(Path<? extends ErroriCaricamentoDmAlm> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_ERRORI_CARICAMENTO");
    }

    public QErroriCaricamentoDmAlm(PathMetadata<?> metadata) {
        super(ErroriCaricamentoDmAlm.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_ERRORI_CARICAMENTO");
    }

}

