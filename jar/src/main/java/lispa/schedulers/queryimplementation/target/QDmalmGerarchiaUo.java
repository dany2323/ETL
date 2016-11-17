package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmGerarchiaUo;


import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDmalmGerarchiaUo is a Querydsl query type for DmalmGerarchiaUo
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmGerarchiaUo extends com.mysema.query.sql.RelationalPathBase<DmalmGerarchiaUo> {

    private static final long serialVersionUID = -1155161583;

    public static final QDmalmGerarchiaUo dmalmGerarchiaUo = new QDmalmGerarchiaUo("DMALM_GERARCHIA_UO");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dmalmDtFineValidita = createDateTime("DMALM_DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dmalmDtInizioValidita = createDateTime("DMALM_DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final NumberPath<Short> livello = createNumber("LIVELLO", Short.class);

    public final NumberPath<Double> pkPredecessore = createNumber("PK_PREDECESSORE", Double.class);

    public final StringPath struttura = createString("STRUTTURA");

    public final StringPath uoPrecessore = createString("UO_PRECESSORE");

    public QDmalmGerarchiaUo(String variable) {
        super(DmalmGerarchiaUo.class, forVariable(variable), "DMALM", "DMALM_GERARCHIA_UO");
    }

    public QDmalmGerarchiaUo(Path<? extends DmalmGerarchiaUo> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_GERARCHIA_UO");
    }

    public QDmalmGerarchiaUo(PathMetadata<?> metadata) {
        super(DmalmGerarchiaUo.class, metadata, "DMALM", "DMALM_GERARCHIA_UO");
    }

}

