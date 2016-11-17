package lispa.schedulers.queryimplementation.staging;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.DmalmEsitiCaricamenti;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmEsitiCaricamenti is a Querydsl query type for DmalmEsitiCaricamenti
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmEsitiCaricamenti extends com.mysema.query.sql.RelationalPathBase<DmalmEsitiCaricamenti> {

    private static final long serialVersionUID = -1121946529;

    public static final QDmalmEsitiCaricamenti dmalmEsitiCaricamenti = new QDmalmEsitiCaricamenti("DMALM_ESITI_CARICAMENTI");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtFineCaricamento = createDateTime("DT_FINE_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioCaricamento = createDateTime("DT_INIZIO_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath entitaTarget = createString("ENTITA_TARGET");

    public final NumberPath<Integer> righeCaricate = createNumber("RIGHE_CARICATE", Integer.class);

    public final NumberPath<Integer> righeErrate = createNumber("RIGHE_ERRATE", Integer.class);

    public final NumberPath<Integer> righeModificate = createNumber("RIGHE_MODIFICATE", Integer.class);

    public final NumberPath<Integer> righeScartate = createNumber("RIGHE_SCARTATE", Integer.class);

    public final StringPath statoEsecuzione = createString("STATO_ESECUZIONE");

    public final com.mysema.query.sql.PrimaryKey<DmalmEsitiCaricamenti> dmalmEsitiCaricamentiPk = createPrimaryKey(dataCaricamento, entitaTarget);

    public QDmalmEsitiCaricamenti(String variable) 
    {
        super(DmalmEsitiCaricamenti.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ESITI_CARICAMENTI");
    }

    public QDmalmEsitiCaricamenti(Path<? extends DmalmEsitiCaricamenti> path) 
    {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ESITI_CARICAMENTI");
    }

    public QDmalmEsitiCaricamenti(PathMetadata<?> metadata) 
    {
        super(DmalmEsitiCaricamenti.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ESITI_CARICAMENTI");
    }

}

