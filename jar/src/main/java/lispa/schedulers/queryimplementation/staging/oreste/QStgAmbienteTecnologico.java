package lispa.schedulers.queryimplementation.staging.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.oreste.StgAmbienteTecnologico;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QStgAmbienteTecnologico is a Querydsl query type for StgAmbienteTecnologico
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStgAmbienteTecnologico extends com.mysema.query.sql.RelationalPathBase<StgAmbienteTecnologico> {

    private static final long serialVersionUID = -1519816933;

    public static final QStgAmbienteTecnologico stgAmbienteTecnologico = new QStgAmbienteTecnologico("DMALM_STG_AMBIENTE_TECNOLOGICO");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

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
    
    public final StringPath idAmbTecnologicoPk = createString("ID_AMB_TECNOLOGICO_PK");

    public QStgAmbienteTecnologico(String variable) {
        super(StgAmbienteTecnologico.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_AMBIENTE_TECNOLOGICO");
    }

    public QStgAmbienteTecnologico(Path<? extends StgAmbienteTecnologico> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_AMBIENTE_TECNOLOGICO");
    }

    public QStgAmbienteTecnologico(PathMetadata<?> metadata) {
        super(StgAmbienteTecnologico.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_AMBIENTE_TECNOLOGICO");
    }

}

