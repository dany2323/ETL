package lispa.schedulers.queryimplementation.fonte.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.fonte.elettra.ElettraModuli;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;

public class QElettraModuli extends com.mysema.query.sql.RelationalPathBase<ElettraModuli> {
	private static final long serialVersionUID = -1999744518523686832L;

	public static final QElettraModuli elettraModuli = new QElettraModuli("DM_ALM_EL_MODULI");
	
	public final StringPath idEdmaModuloOreste = createString("Id Edma Modulo Oreste");
	public final StringPath idEdmaPadreModuloOreste = createString("Id Edma Padre Modulo Oreste");
	public final StringPath idModuloOreste = createString("Id_Modulo Oreste");
	public final StringPath tipoModuloOreste = createString("Tipo Modulo Oreste");
	public final StringPath siglaProdottoModulo = createString("Sigla Prodotto del Modulo");
	public final StringPath siglaSottosistemaModulo = createString("SIGLA SOTTOSISTEMA del Modulo");
	public final StringPath siglaModuloOreste = createString("Sigla Modulo Oreste");
	public final StringPath nomeModuloOreste = createString("Nome Modulo Oreste");
	public final StringPath descrizioneModuloOreste = createString("Descrizione Modulo Oreste");
	public final StringPath moduloOresteAnnullato = createString("Modulo Oreste Annullato");
	public final StringPath dfvModuloOresteAnnullato = createString("dfv Modulo Oreste Annullato");
	public final StringPath responsabileOreste = createString("Resp Oreste");
	public final StringPath sottosistema = createString("Sottosistema");
	public final StringPath tecnologia = createString("tecnologia");
	public final StringPath tipoModulo = createString("tipo modulo");
	
	public QElettraModuli(String variable) {
		super(ElettraModuli.class, forVariable(variable),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_MODULI");
	}

	public QElettraModuli(Path<? extends ElettraModuli> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_MODULI");
	}

	public QElettraModuli(PathMetadata<?> metadata) {
		super(ElettraModuli.class, metadata,
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_MODULI");
	}
}
