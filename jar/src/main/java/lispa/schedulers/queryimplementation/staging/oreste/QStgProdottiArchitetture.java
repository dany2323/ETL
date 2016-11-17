package lispa.schedulers.queryimplementation.staging.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.oreste.StgProdottiArchitetture;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QStgProdottiArchitetture is a Querydsl query type for StgProdottiArchitetture
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStgProdottiArchitetture extends com.mysema.query.sql.RelationalPathBase<StgProdottiArchitetture> {

    private static final long serialVersionUID = 1459115185;

    public static final QStgProdottiArchitetture stgProdottiArchitetture = new QStgProdottiArchitetture("DMALM_STG_PROD_ARCHITETTURE");

    public final StringPath clasfAmbitoManutenzione = createString("CLASF_AMBITO_MANUTENZIONE");

    public final StringPath clasfAreaTematica = createString("CLASF_AREA_TEMATICA");

    public final StringPath clasfBaseDatiEtl = createString("CLASF_BASE_DATI_ETL");

    public final StringPath clasfBaseDatiLettura = createString("CLASF_BASE_DATI_LETTURA");

    public final StringPath clasfBaseDatiScrittura = createString("CLASF_BASE_DATI_SCRITTURA");

    public final StringPath clasfCategoria = createString("CLASF_CATEGORIA");

    public final StringPath clasfFornRisEstGara = createString("CLASF_FORN_RIS_EST_GARA");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath descrizioneProdotto = createString("DESCRIZIONE_PRODOTTO");

    public final StringPath dfvProdottoAnnullato = createString("DFV_PRODOTTO_ANNULLATO");

    public final StringPath edmaAreaProdotto = createString("EDMA_AREA_PRODOTTO");

    public final StringPath edmaRespProdotto = createString("EDMA_RESP_PRODOTTO");

    public final NumberPath<Long> idEdmaProdotto = createNumber("ID_EDMA_PRODOTTO", Long.class);

    public final StringPath idProdotto = createString("ID_PRODOTTO");

    public final StringPath nomeProdotto = createString("NOME_PRODOTTO");

    public final StringPath prodottoAnnullato = createString("PRODOTTO_ANNULLATO");

    public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");

    public final StringPath tipoOggetto = createString("TIPO_OGGETTO");
    
    public final StringPath dmalmProdottiArchitetturePk = createString("DMALM_STG_PROD_ARCHITETTURE_PK");

    public QStgProdottiArchitetture(String variable) {
        super(StgProdottiArchitetture.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_PROD_ARCHITETTURE");
    }

    public QStgProdottiArchitetture(Path<? extends StgProdottiArchitetture> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_PROD_ARCHITETTURE");
    }

    public QStgProdottiArchitetture(PathMetadata<?> metadata) {
        super(StgProdottiArchitetture.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_PROD_ARCHITETTURE");
    }

}

