package lispa.schedulers.queryimplementation.staging.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.staging.elettra.StgElFunzionalita;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QStgElFunzionalita extends com.mysema.query.sql.RelationalPathBase<StgElFunzionalita> {
	private static final long serialVersionUID = 5721608847961739141L;

	public static final QStgElFunzionalita stgElFunzionalita = new QStgElFunzionalita("DMALM_STG_EL_FUNZIONALITA");
	
	public final NumberPath<Integer> funzionaliaPk = createNumber("DMALM_STG_FUNZIONALITA_PK", Integer.class);
	public final StringPath idFunzionalitaEdma = createString("ID_FUNZIONALITA_EDMA");
	public final StringPath idEdmaPadre = createString("ID_EDMA_PADRE");
	public final StringPath idFunzionalia = createString("ID_FUNZIONALITA");
	public final StringPath tipoOggetto = createString("TIPO_OGGETTO");
	public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");
	public final StringPath siglaSottosistema = createString("SIGLA_SOTTOSISTEMA");
	public final StringPath siglaModulo = createString("SIGLA_MODULO");
	public final StringPath siglaFunzionalita = createString("SIGLA_FUNZIONALITA");
	public final StringPath nome = createString("NOME");
	public final StringPath descrizioneFunzionalita = createString("DS_FUNZIONALITA");
	public final StringPath annullato = createString("ANNULLATO");
	public final StringPath dataAnnullamento = createString("DT_ANNULLAMENTO");
	public final StringPath categoria = createString("CATEGORIA");
	public final StringPath linguaggi = createString("LINGUAGGI");
	public final StringPath tipiElaborazione = createString("TIPI_ELABORAZIONE");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	
	public QStgElFunzionalita(String variable) {
        super(StgElFunzionalita.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_FUNZIONALITA");
    }

    public QStgElFunzionalita(Path<? extends StgElFunzionalita> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_FUNZIONALITA");
    }

    public QStgElFunzionalita(PathMetadata<?> metadata) {
        super(StgElFunzionalita.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_FUNZIONALITA");
    }
}
