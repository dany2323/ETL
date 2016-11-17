package lispa.schedulers.queryimplementation.fonte.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * OresteDmAlmSottosistemi is a Querydsl query type for OresteDmAlmSottosistemi
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class OresteDmAlmSottosistemi extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.oreste.OresteDmAlmSottosistemi> {

    private static final long serialVersionUID = 1619273065;

    public static final OresteDmAlmSottosistemi dmAlmSottosistemi = new OresteDmAlmSottosistemi("V_DM_ALM_SOTTOSISTEMI");

    public final StringPath clasfBaseDatiEtl = createString("CLASF_BASE_DATI_ETL");

    public final StringPath clasfBaseDatiLettura = createString("CLASF_BASE_DATI_LETTURA");

    public final StringPath clasfBaseDatiScrittura = createString("CLASF_BASE_DATI_SCRITTURA");

    public final StringPath clasfTipoSottosistema = createString("CLASF_TIPO_SOTTOSISTEMA");

    public final StringPath descrizioneSottosistema = createString("DESCRIZIONE_SOTTOSISTEMA");

    public final StringPath dfvSottosistemaAnnullato = createString("DFV_SOTTOSISTEMA_ANNULLATO");

    public final StringPath edmaRespoSottosistema = createString("EDMA_RESPO_SOTTOSISTEMA");

    public final NumberPath<Double> idEdmaPadreSottosistema = createNumber("ID_EDMA_PADRE_SOTTOSISTEMA", Double.class);

    public final NumberPath<Double> idEdmaSottosistema = createNumber("ID_EDMA_SOTTOSISTEMA", Double.class);

    public final StringPath idSottosistema = createString("ID_SOTTOSISTEMA");

    public final StringPath nomeSottosistema = createString("NOME_SOTTOSISTEMA");

    public final StringPath siglaProdottoSottosistema = createString("SIGLA_PRODOTTO_SOTTOSISTEMA");

    public final StringPath siglaSottosistema = createString("SIGLA_SOTTOSISTEMA");

    public final StringPath sottosistemaAnnullato = createString("SOTTOSISTEMA_ANNULLATO");

    public final StringPath tipoOggetto = createString("TIPO_OGGETTO");

    public OresteDmAlmSottosistemi(String variable) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmSottosistemi.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_SOTTOSISTEMI");
    }

    public OresteDmAlmSottosistemi(Path<? extends lispa.schedulers.bean.fonte.oreste.OresteDmAlmSottosistemi> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_SOTTOSISTEMI");
    }

    public OresteDmAlmSottosistemi(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmSottosistemi.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_SOTTOSISTEMI");
    }

}

