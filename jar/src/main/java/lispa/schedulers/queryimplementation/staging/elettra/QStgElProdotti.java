package lispa.schedulers.queryimplementation.staging.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.staging.elettra.StgElProdotti;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QStgElProdotti extends com.mysema.query.sql.RelationalPathBase<StgElProdotti> {
	private static final long serialVersionUID = -4324932298782249197L;
	
	public static final QStgElProdotti stgElProdotti = new QStgElProdotti("DMALM_STG_EL_PRODOTTI_ARCH");
	
	public final NumberPath<Integer> prodottoPk = createNumber("DMALM_STG_PROD_ARCH_PK", Integer.class);
	public final StringPath idProdottoEdma = createString("ID_PRODOTTO_EDMA");
	public final StringPath idProdotto = createString("ID_PRODOTTO");
	public final StringPath tipoOggetto = createString("TIPO_OGGETTO");
	public final StringPath sigla = createString("SIGLA");
	public final StringPath nome = createString("NOME");
	public final StringPath descrizioneProdotto = createString("DS_PRODOTTO");
	public final StringPath areaProdotto = createString("AREA_PRODOTTO");
	public final StringPath responsabileProdotto = createString("RESPONSABILE_PRODOTTO");
	public final StringPath annullato = createString("ANNULLATO");
	public final StringPath dataAnnullamento = createString("DT_ANNULLAMENTO");
	public final StringPath ambitoManutenzione = createString("AMBITO_MANUTENZIONE");
	public final StringPath areaTematica = createString("AREA_TEMATICA");
	public final StringPath BaseDatiEtl = createString("BASE_DATI_ETL");
	public final StringPath BaseDatiLettura = createString("BASE_DATI_LETTURA");
	public final StringPath BaseDatiScrittura = createString("BASE_DATI_SCRITTURA");
	public final StringPath categoria = createString("CATEGORIA");
	public final StringPath fornituraRisorseEsterne = createString("FORNITURA_RISORSE_ESTERNE");
	public final StringPath codiceAreaProdotto = createString("CD_AREA_PRODOTTO");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	
	public QStgElProdotti(String variable) {
        super(StgElProdotti.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_PRODOTTI_ARCH");
    }

    public QStgElProdotti(Path<? extends StgElProdotti> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_PRODOTTI_ARCH");
    }

    public QStgElProdotti(PathMetadata<?> metadata) {
        super(StgElProdotti.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_PRODOTTI_ARCH");
    }
}
