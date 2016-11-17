package lispa.schedulers.queryimplementation.staging.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.oreste.StgModuli;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QStgModuli is a Querydsl query type for StgModuli
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStgModuli extends com.mysema.query.sql.RelationalPathBase<StgModuli> {

    private static final long serialVersionUID = -669675092;

    public static final QStgModuli stgModuli = new QStgModuli("DMALM_STG_MODULI");

    public final StringPath clasfSottosistemaModulo = createString("CLASF_SOTTOSISTEMA_MODULO");

    public final StringPath clasfTecnologiaModulo = createString("CLASF_TECNOLOGIA_MODULO");

    public final StringPath clasfTipologiaModulo = createString("CLASF_TIPOLOGIA_MODULO");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

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
    
    public final StringPath dmalmModuliPk = createString("DMALM_STG_MODULI_PK");

    public final StringPath tipoOggetto = createString("TIPO_OGGETTO");

    public QStgModuli(String variable) {
        super(StgModuli.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_MODULI");
    }

    public QStgModuli(Path<? extends StgModuli> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_MODULI");
    }

    public QStgModuli(PathMetadata<?> metadata) {
        super(StgModuli.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_MODULI");
    }

}

