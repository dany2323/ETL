package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmModulo;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmModulo is a Querydsl query type for DmalmModulo
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmModulo extends com.mysema.query.sql.RelationalPathBase<DmalmModulo> {

    private static final long serialVersionUID = -1082016319;

    public static final QDmalmModulo dmalmModulo = new QDmalmModulo("DMALM_MODULO");

    public final StringPath annullato = createString("ANNULLATO");

    public final NumberPath<Integer> dmalmModuloPrimaryKey = createNumber("DMALM_MODULO_PK", Integer.class);

    public final StringPath dsModulo = createString("DS_MODULO");

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInserimentoRecord = createDateTime("DT_INSERIMENTO_RECORD", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);

    public final NumberPath<Integer> dmalmProdottoFk02 = createNumber("DMALM_PRODOTTO_FK_02", Integer.class);
    
    public final NumberPath<Integer> dmalmSottosistemaFk03 = createNumber("DMALM_SOTTOSISTEMA_FK_03", Integer.class);

    public final StringPath idModulo = createString("ID_MODULO");

    public final StringPath nome = createString("NOME");

    public final StringPath dmalmPersonaleFk01 = createString("DMALM_PERSONALE_FK_01");

    public final StringPath siglaModulo = createString("SIGLA_MODULO");

    public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");

    public final StringPath siglaSottosistema = createString("SIGLA_SOTTOSISTEMA");

    public final StringPath sottosistema = createString("SOTTOSISTEMA");

    public final StringPath tecnologie = createString("TECNOLOGIE");

    public final StringPath tipoModulo = createString("TIPO_MODULO");

    public final StringPath tipoOggetto = createString("TIPO_OGGETTO");

    public final com.mysema.query.sql.PrimaryKey<DmalmModulo> dmalmModuloPk = createPrimaryKey(dmalmModuloPrimaryKey);

    public final NumberPath<Integer> dmalmModuloSeq = createNumber("DMALM_MODULO_SEQ", Integer.class);
    
    public QDmalmModulo(String variable) {
        super(DmalmModulo.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_MODULO");
    }

    public QDmalmModulo(Path<? extends DmalmModulo> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_MODULO");
    }

    public QDmalmModulo(PathMetadata<?> metadata) {
        super(DmalmModulo.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_MODULO");
    }

}

