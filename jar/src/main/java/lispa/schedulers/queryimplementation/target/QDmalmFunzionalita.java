package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmFunzionalita;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmFunzionalita is a Querydsl query type for DmalmFunzionalita
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmFunzionalita extends com.mysema.query.sql.RelationalPathBase<DmalmFunzionalita> {

    private static final long serialVersionUID = 2002980969;

    public static final QDmalmFunzionalita dmalmFunzionalita = new QDmalmFunzionalita("DMALM_FUNZIONALITA");

    public final StringPath annullato = createString("ANNULLATO");

    public final StringPath categoria = createString("CATEGORIA");

    public final NumberPath<Integer> dmalmFunzionalitaPrimaryKey = createNumber("DMALM_FUNZIONALITA_PK", Integer.class);

    public final StringPath dsFunzionalita = createString("DS_FUNZIONALITA");

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInserimentoRecord = createDateTime("DT_INSERIMENTO_RECORD", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
    
    public final NumberPath<Integer> dmalmModuloFk01 = createNumber("DMALM_MODULO_FK_01", Integer.class);

    public final StringPath idFunzionalita = createString("ID_FUNZIONALITA");

    public final StringPath linguaggi = createString("LINGUAGGI");

    public final StringPath nome = createString("NOME");

    public final StringPath siglaFunzionalita = createString("SIGLA_FUNZIONALITA");

    public final StringPath siglaModulo = createString("SIGLA_MODULO");

    public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");

    public final StringPath siglaSottosistema = createString("SIGLA_SOTTOSISTEMA");

    public final StringPath tipiElaborazione = createString("TIPI_ELABORAZIONE");

    public final StringPath tipoOggetto = createString("TIPO_OGGETTO");

    public final com.mysema.query.sql.PrimaryKey<DmalmFunzionalita> dmalmFunzionalitaPk = createPrimaryKey(dmalmFunzionalitaPrimaryKey);

    public final NumberPath<Integer> dmalmFunzionalitaSeq = createNumber("DMALM_FUNZIONALITA_SEQ", Integer.class);

    public QDmalmFunzionalita(String variable) {
        super(DmalmFunzionalita.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_FUNZIONALITA");
    }

    public QDmalmFunzionalita(Path<? extends DmalmFunzionalita> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_FUNZIONALITA");
    }

    public QDmalmFunzionalita(PathMetadata<?> metadata) {
        super(DmalmFunzionalita.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_FUNZIONALITA");
    }

}

