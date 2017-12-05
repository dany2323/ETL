package lispa.schedulers.queryimplementation.staging.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.staging.elettra.StgElModuli;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QStgElModuli extends com.mysema.query.sql.RelationalPathBase<StgElModuli> {
	private static final long serialVersionUID = -1787004843502857304L;

	public static final QStgElModuli stgElModuli = new QStgElModuli("DMALM_STG_EL_MODULI");
	
	public final NumberPath<Integer> moduloPk = createNumber("DMALM_STG_MODULI_PK", Integer.class);
	public final StringPath idModuloEdma = createString("ID_MODULO_EDMA");
	public final StringPath idModuloEdmaPadre = createString("ID_MODULO_EDMA_PADRE");
	public final StringPath idModulo = createString("ID_MODULO");
	public final StringPath tipoOggetto = createString("TIPO_OGGETTO");
	public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");
	public final StringPath siglaSottosistema = createString("SIGLA_SOTTOSISTEMA");
	public final StringPath siglaModulo = createString("SIGLA_MODULO");
	public final StringPath nome = createString("NOME");
	public final StringPath descrizioneModulo = createString("DS_MODULO");
	public final StringPath annullato = createString("ANNULLATO");
	public final StringPath dataAnnullamento = createString("DT_ANNULLAMENTO");
	public final StringPath responsabile = createString("RESPONSABILE");
	public final StringPath sottosistema = createString("SOTTOSISTEMA");
	public final StringPath tecnologie = createString("TECNOLOGIE");
	public final StringPath tipoModulo = createString("TIPO_MODULO");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	public final StringPath stato = createString("STATO");
	
	public QStgElModuli(String variable) {
        super(StgElModuli.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_MODULI");
    }

    public QStgElModuli(Path<? extends StgElModuli> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_MODULI");
    }

    public QStgElModuli(PathMetadata<?> metadata) {
        super(StgElModuli.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_MODULI");
    }
}
