package lispa.schedulers.queryimplementation.fonte.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.fonte.elettra.ElettraFunzionalita;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;

public class QElettraFunzionalita extends com.mysema.query.sql.RelationalPathBase<ElettraFunzionalita> {
	private static final long serialVersionUID = -1377003640591965396L;
	
	public static final QElettraFunzionalita elettraFunzionalita = new QElettraFunzionalita("DM_ALM_EL_FUNZIONALITA");
	
	public final StringPath idEdmaFunzionalitaOreste = createString("Id Edma FUNZIONALITA Oreste");
	public final StringPath idEdmaPadreFunzionalitaOreste = createString("Id Edma Padre FN Oreste");
	public final StringPath idFunzionalitaOreste = createString("Id_FUNZIONALITA Oreste");
	public final StringPath tipoFunzionalitaOreste = createString("Tipo FUNZIONALITA Oreste");
	public final StringPath siglaProdottoFunzionalita = createString("Sigla Prodotto della FN");
	public final StringPath siglaSottosistemaFunzionalita = createString("SIGLA SOTTOSISTEMA della FN");
	public final StringPath siglaModuloFunzionalita = createString("SIGLA modulo della FN");
	public final StringPath siglaFunzionalitaOreste = createString("Sigla FUNZIONALITA Oreste");
	public final StringPath nomeFunzionalitaOreste = createString("Nome FUNZIONALITA Oreste");
	public final StringPath descrizioneFunzionalitaOreste = createString("Descrizione FN Oreste");
	public final StringPath funzionalitaOresteAnnullato = createString("FUNZIONALITA Oreste Annullato");
	public final StringPath dfvFunzionalitaOresteAnnullato = createString("dfv FN Oreste Annullato");
	public final StringPath categoriaFunzionalita = createString("Categoria Funzionalità");
	public final StringPath linguaggioProgrammazione = createString("Linguaggio di programmazione");
	public final StringPath tipoElaborazione = createString("Tipo Elaborazione");
	
	public QElettraFunzionalita(String variable) {
		super(ElettraFunzionalita.class, forVariable(variable),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_FUNZIONALITA");
	}

	public QElettraFunzionalita(Path<? extends ElettraFunzionalita> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_FUNZIONALITA");
	}

	public QElettraFunzionalita(PathMetadata<?> metadata) {
		super(ElettraFunzionalita.class, metadata,
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_FUNZIONALITA");
	}
}
