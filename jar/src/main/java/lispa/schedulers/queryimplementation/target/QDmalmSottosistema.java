package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmSottosistema;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmSottosistema is a Querydsl query type for DmalmSottosistema
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmSottosistema extends com.mysema.query.sql.RelationalPathBase<DmalmSottosistema> {

    private static final long serialVersionUID = -645706822;

    public static final QDmalmSottosistema dmalmSottosistema = new QDmalmSottosistema("DMALM_SOTTOSISTEMA");

    public final StringPath annullato = createString("ANNULLATO");

    public final StringPath baseDatiEtl = createString("BASE_DATI_ETL");

    public final StringPath baseDatiLettura = createString("BASE_DATI_LETTURA");

    public final StringPath baseDatiScrittura = createString("BASE_DATI_SCRITTURA");

    public final NumberPath<Integer> dmalmSottosistemaPrimaryKey = createNumber("DMALM_SOTTOSISTEMA_PK", Integer.class);

    public final StringPath dsSottosistema = createString("DS_SOTTOSISTEMA");

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInserimentoRecord = createDateTime("DT_INSERIMENTO_RECORD", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);

    public final NumberPath<Integer> dmalmProdottoFk01 = createNumber("DMALM_PRODOTTO_FK_01", Integer.class);

    public final StringPath idSottosistema = createString("ID_SOTTOSISTEMA");

    public final StringPath nome = createString("NOME");

    public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");

    public final StringPath siglaSottosistema = createString("SIGLA_SOTTOSISTEMA");

    public final StringPath tipo = createString("TIPO");

    public final StringPath tipoOggetto = createString("TIPO_OGGETTO");

    public final com.mysema.query.sql.PrimaryKey<DmalmSottosistema> dmalmSottosistemaPk = createPrimaryKey(dmalmSottosistemaPrimaryKey);

    public QDmalmSottosistema(String variable) {
        super(DmalmSottosistema.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_SOTTOSISTEMA");
    }

    public QDmalmSottosistema(Path<? extends DmalmSottosistema> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_SOTTOSISTEMA");
    }

    public QDmalmSottosistema(PathMetadata<?> metadata) {
        super(DmalmSottosistema.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_SOTTOSISTEMA");
    }

}

