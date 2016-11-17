package lispa.schedulers.queryimplementation.target.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.elettra.DmalmElFunzionalita;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmElFunzionalita extends
com.mysema.query.sql.RelationalPathBase<DmalmElFunzionalita> {

	private static final long serialVersionUID = 5297834063978962601L;

	public static final QDmalmElFunzionalita qDmalmElFunzionalita = new QDmalmElFunzionalita("DMALM_EL_FUNZIONALITA");

    public final NumberPath<Integer> funzionalitaPk = createNumber("DMALM_FUNZIONALITA_PK", Integer.class);
    public final StringPath idFunzionalitaEdma = createString("ID_FUNZIONALITA_EDMA");
    public final StringPath idEdmaPadre = createString("ID_EDMA_PADRE");
    public final StringPath idFunzionalita = createString("ID_FUNZIONALITA");
    public final StringPath tipoOggetto = createString("TIPO_OGGETTO");
    public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");
    public final StringPath siglaSottosistema = createString("SIGLA_SOTTOSISTEMA");
    public final StringPath siglaModulo = createString("SIGLA_MODULO");
    public final StringPath siglaFunzionalita = createString("SIGLA_FUNZIONALITA");
    public final StringPath nome = createString("NOME");
    public final StringPath dsFunzionalita = createString("DS_FUNZIONALITA");
    public final StringPath annullato = createString("ANNULLATO");
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
    public final StringPath categoria = createString("CATEGORIA");
    public final StringPath linguaggi = createString("LINGUAGGI");
    public final StringPath tipiElaborazione = createString("TIPI_ELABORAZIONE");
    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);
    public final NumberPath<Integer> dmalmModuloFk01 = createNumber("DMALM_MODULO_FK_01", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<DmalmElFunzionalita> dmalmFunzionalitaPk = createPrimaryKey(funzionalitaPk);

    public QDmalmElFunzionalita(String variable) {
        super(DmalmElFunzionalita.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_FUNZIONALITA");
    }

    public QDmalmElFunzionalita(Path<? extends DmalmElFunzionalita> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL-FUNZIONALITA");
    }

    public QDmalmElFunzionalita(PathMetadata<?> metadata) {
        super(DmalmElFunzionalita.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_FUNZIONALITA");
    }

}
