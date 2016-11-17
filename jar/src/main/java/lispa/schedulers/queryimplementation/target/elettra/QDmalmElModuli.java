package lispa.schedulers.queryimplementation.target.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.elettra.DmalmElModuli;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmElModuli extends
		com.mysema.query.sql.RelationalPathBase<DmalmElModuli> {
	static final long serialVersionUID = 1114796852763231415L;

	public static final QDmalmElModuli qDmalmElModuli = new QDmalmElModuli("DMALM_EL_MODULI");

	public final NumberPath<Integer> moduloPk = createNumber("DMALM_MODULO_PK", Integer.class);
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
	public final DateTimePath<java.sql.Timestamp> dataAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
	public final StringPath responsabile = createString("RESPONSABILE");
	public final StringPath sottosistema = createString("SOTTOSISTEMA");
	public final StringPath tecnologie = createString("TECNOLOGIE");
	public final StringPath tipoModulo = createString("TIPO_MODULO");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	public final NumberPath<Integer> personaleFk = createNumber("DMALM_PERSONALE_FK_01", Integer.class);
	public final NumberPath<Integer> prodottoFk = createNumber("DMALM_PRODOTTO_FK_02", Integer.class);
	public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

	public final com.mysema.query.sql.PrimaryKey<DmalmElModuli> dmalmModuloPk = createPrimaryKey(moduloPk);
	
	public QDmalmElModuli(String variable) {
		super(DmalmElModuli.class, forVariable(variable),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_MODULI");
	}

	public QDmalmElModuli(Path<? extends DmalmElModuli> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_MODULI");
	}

	public QDmalmElModuli(PathMetadata<?> metadata) {
		super(DmalmElModuli.class, metadata,
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_MODULI");
	}

}
