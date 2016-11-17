package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmClassificatori;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmClassificatori is a Querydsl query type for DmalmClassificatori
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmClassificatori extends com.mysema.query.sql.RelationalPathBase<DmalmClassificatori> {

    private static final long serialVersionUID = 369485583;

    public static final QDmalmClassificatori dmalmClassificatori = new QDmalmClassificatori("DMALM_CLASSIFICATORI");

    public final StringPath codiceClassificatore = createString("CODICE_CLASSIFICATORE");

    public final NumberPath<Integer> dmalmClassificatoriPrimaryKey = createNumber("DMALM_CLASSIFICATORI_PK", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dtInserimentoRecord = createDateTime("DT_INSERIMENTO_RECORD", java.sql.Timestamp.class);

    public final NumberPath<Integer> idOreste = createNumber("ID_ORESTE", Integer.class);

    public final StringPath tipoClassificatore = createString("TIPO_CLASSIFICATORE");

    public final com.mysema.query.sql.PrimaryKey<DmalmClassificatori> dmalmClassificatoriPk = createPrimaryKey(dmalmClassificatoriPrimaryKey);

    public QDmalmClassificatori(String variable) {
        super(DmalmClassificatori.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_CLASSIFICATORI");
    }

    public QDmalmClassificatori(Path<? extends DmalmClassificatori> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_CLASSIFICATORI");
    }

    public QDmalmClassificatori(PathMetadata<?> metadata) {
        super(DmalmClassificatori.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_CLASSIFICATORI");
    }

}

