package lispa.schedulers.queryimplementation.fonte.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * OresteDmAlmAmbTecnologico is a Querydsl query type for OresteDmAlmAmbTecnologico
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class OresteDmAlmAmbTecnologico extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.oreste.OresteDmAlmAmbTecnologico> {

    private static final long serialVersionUID = -1041675136;

    public static final OresteDmAlmAmbTecnologico dmAlmAmbTecnologico = new OresteDmAlmAmbTecnologico("V_DM_ALM_AMB_TECNOLOGICO");

    public final StringPath clasfArchiRiferimento = createString("CLASF_ARCHI_RIFERIMENTO");

    public final StringPath clasfInfrastrutture = createString("CLASF_INFRASTRUTTURE");

    public final StringPath clasfSo = createString("CLASF_SO");

    public final StringPath clasfTipiLayer = createString("CLASF_TIPI_LAYER");

    public final StringPath descrAmbienteTecnologico = createString("DESCR_AMBIENTE_TECNOLOGICO");

    public final StringPath idAmbienteTecnologico = createString("ID_AMBIENTE_TECNOLOGICO");

    public final NumberPath<Long> idEdma = createNumber("ID_EDMA", Long.class);

    public final NumberPath<Double> idEdmaPadre = createNumber("ID_EDMA_PADRE", Double.class);

    public final StringPath nomeAmbienteTecnologico = createString("NOME_AMBIENTE_TECNOLOGICO");

    public final StringPath siglaModulo = createString("SIGLA_MODULO");

    public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");

    public final StringPath versioneSo = createString("VERSIONE_SO");

    public OresteDmAlmAmbTecnologico(String variable) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmAmbTecnologico.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_AMB_TECNOLOGICO");
    }

    public OresteDmAlmAmbTecnologico(Path<? extends lispa.schedulers.bean.fonte.oreste.OresteDmAlmAmbTecnologico> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_AMB_TECNOLOGICO");
    }

    public OresteDmAlmAmbTecnologico(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmAmbTecnologico.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_AMB_TECNOLOGICO");
    }

}

