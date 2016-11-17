package lispa.schedulers.queryimplementation.fonte.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * OresteDmAlmProdottiArchitetture is a Querydsl query type for OresteDmAlmProdottiArchitetture
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class OresteDmAlmProdottiArchitetture extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.oreste.OresteDmAlmProdottiArchitetture> {

    private static final long serialVersionUID = 222231847;

    public static final OresteDmAlmProdottiArchitetture dmAlmProdottiArchitetture = new OresteDmAlmProdottiArchitetture("V_DM_ALM_PRODOTTI_ARCHITETTURE");

    public final StringPath clasfAmbitoManutenzione = createString("CLASF_AMBITO_MANUTENZIONE");

    public final StringPath clasfAreaTematica = createString("CLASF_AREA_TEMATICA");

    public final StringPath clasfBaseDatiEtl = createString("CLASF_BASE_DATI_ETL");

    public final StringPath clasfBaseDatiLettura = createString("CLASF_BASE_DATI_LETTURA");

    public final StringPath clasfBaseDatiScrittura = createString("CLASF_BASE_DATI_SCRITTURA");

    public final StringPath clasfCategoria = createString("CLASF_CATEGORIA");

    public final StringPath clasfFornRisEstGara = createString("CLASF_FORN_RIS_EST_GARA");

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

    public OresteDmAlmProdottiArchitetture(String variable) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmProdottiArchitetture.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_PRODOTTI_ARCHITETTURE");
    }

    public OresteDmAlmProdottiArchitetture(Path<? extends lispa.schedulers.bean.fonte.oreste.OresteDmAlmProdottiArchitetture> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_PRODOTTI_ARCHITETTURE");
    }

    public OresteDmAlmProdottiArchitetture(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmProdottiArchitetture.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_PRODOTTI_ARCHITETTURE");
    }

}

