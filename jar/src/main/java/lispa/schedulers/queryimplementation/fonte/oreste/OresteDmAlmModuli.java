package lispa.schedulers.queryimplementation.fonte.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * OresteDmAlmModuli is a Querydsl query type for OresteDmAlmModuli
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class OresteDmAlmModuli extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.oreste.OresteDmAlmModuli> {

    private static final long serialVersionUID = -403398494;

    public static final OresteDmAlmModuli dmAlmModuli = new OresteDmAlmModuli("V_DM_ALM_MODULI");

    public final StringPath clasfSottosistemaModulo = createString("CLASF_SOTTOSISTEMA_MODULO");

    public final StringPath clasfTecnologiaModulo = createString("CLASF_TECNOLOGIA_MODULO");

    public final StringPath clasfTipologiaModulo = createString("CLASF_TIPOLOGIA_MODULO");

    public final StringPath descrizioneModulo = createString("DESCRIZIONE_MODULO");

    public final StringPath dfvModuloAnnullato = createString("DFV_MODULO_ANNULLATO");

    public final StringPath edmaRespModulo = createString("EDMA_RESP_MODULO");

    public final NumberPath<Double> idEdmaModulo = createNumber("ID_EDMA_MODULO", Double.class);

    public final NumberPath<Double> idEdmaPadreModulo = createNumber("ID_EDMA_PADRE_MODULO", Double.class);

    public final StringPath idModulo = createString("ID_MODULO");

    public final StringPath moduloAnnullato = createString("MODULO_ANNULLATO");

    public final StringPath nomeModulo = createString("NOME_MODULO");

    public final StringPath siglaModulo = createString("SIGLA_MODULO");

    public final StringPath siglaProdottoModulo = createString("SIGLA_PRODOTTO_MODULO");

    public final StringPath siglaSottosistemaModulo = createString("SIGLA_SOTTOSISTEMA_MODULO");

    public final StringPath tipoOggetto = createString("TIPO_OGGETTO");

    public OresteDmAlmModuli(String variable) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmModuli.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_MODULI");
    }

    public OresteDmAlmModuli(Path<? extends lispa.schedulers.bean.fonte.oreste.OresteDmAlmModuli> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_MODULI");
    }

    public OresteDmAlmModuli(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmModuli.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_MODULI");
    }

}

