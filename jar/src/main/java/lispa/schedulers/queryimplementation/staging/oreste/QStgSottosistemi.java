package lispa.schedulers.queryimplementation.staging.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.oreste.StgSottosistemi;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QStgSottosistemi is a Querydsl query type for StgSottosistemi
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStgSottosistemi extends com.mysema.query.sql.RelationalPathBase<StgSottosistemi> {

    private static final long serialVersionUID = -2023053581;

    public static final QStgSottosistemi stgSottosistemi = new QStgSottosistemi("DMALM_STG_SOTTOSISTEMI");

    public final StringPath clasfBaseDatiEtl = createString("CLASF_BASE_DATI_ETL");

    public final StringPath clasfBaseDatiLettura = createString("CLASF_BASE_DATI_LETTURA");

    public final StringPath clasfBaseDatiScrittura = createString("CLASF_BASE_DATI_SCRITTURA");

    public final StringPath clasfTipoSottosistema = createString("CLASF_TIPO_SOTTOSISTEMA");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

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
    
    public final StringPath dmalmSottosistemimPk = createString("DMALM_STG_SOTTOSISTEMI_PK");

    public QStgSottosistemi(String variable) {
        super(StgSottosistemi.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_SOTTOSISTEMI");
    }

    public QStgSottosistemi(Path<? extends StgSottosistemi> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_SOTTOSISTEMI");
    }

    public QStgSottosistemi(PathMetadata<?> metadata) {
        super(StgSottosistemi.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_SOTTOSISTEMI");
    }

}

