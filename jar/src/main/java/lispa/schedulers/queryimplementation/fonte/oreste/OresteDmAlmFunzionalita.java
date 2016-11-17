package lispa.schedulers.queryimplementation.fonte.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * OresteDmAlmFunzionalita is a Querydsl query type for OresteDmAlmFunzionalita
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class OresteDmAlmFunzionalita extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.oreste.OresteDmAlmFunzionalita> {

    private static final long serialVersionUID = -27006448;

    public static final OresteDmAlmFunzionalita dmAlmFunzionalita = new OresteDmAlmFunzionalita("V_DM_ALM_FUNZIONALITA");

    public final StringPath clasfCategoria = createString("CLASF_CATEGORIA");

    public final StringPath clasfLinguaggioDiProg = createString("CLASF_LINGUAGGIO_DI_PROG");

    public final StringPath clasfTipoElabor = createString("CLASF_TIPO_ELABOR");

    public final StringPath descrizione = createString("DESCRIZIONE");

    public final StringPath dfvFunzionalitaAnnullata = createString("DFV_FUNZIONALITA_ANNULLATA");

    public final StringPath funzionalitaAnnullata = createString("FUNZIONALITA_ANNULLATA");

    public final NumberPath<Long> idEdmaFunzionalita = createNumber("ID_EDMA_FUNZIONALITA", Long.class);

    public final NumberPath<Double> idEdmaPadre = createNumber("ID_EDMA_PADRE", Double.class);

    public final StringPath idFunzionalita = createString("ID_FUNZIONALITA");

    public final StringPath nomeFunzionalita = createString("NOME_FUNZIONALITA");

    public final StringPath siglaFunzionalita = createString("SIGLA_FUNZIONALITA");

    public final StringPath siglaModulo = createString("SIGLA_MODULO");

    public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");

    public final StringPath siglaSottosistema = createString("SIGLA_SOTTOSISTEMA");

    public final StringPath tipoFunzionalita = createString("TIPO_FUNZIONALITA");

    public OresteDmAlmFunzionalita(String variable) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmFunzionalita.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_FUNZIONALITA");
    }

    public OresteDmAlmFunzionalita(Path<? extends lispa.schedulers.bean.fonte.oreste.OresteDmAlmFunzionalita> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_FUNZIONALITA");
    }

    public OresteDmAlmFunzionalita(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmFunzionalita.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_FUNZIONALITA");
    }

}

