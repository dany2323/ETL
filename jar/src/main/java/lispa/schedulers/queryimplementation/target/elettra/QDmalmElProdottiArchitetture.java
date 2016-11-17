package lispa.schedulers.queryimplementation.target.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.elettra.DmalmElProdottiArchitetture;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmElProdottiArchitetture extends com.mysema.query.sql.RelationalPathBase<DmalmElProdottiArchitetture> {
	private static final long serialVersionUID = 8205045385845612541L;

	public static final QDmalmElProdottiArchitetture qDmalmElProdottiArchitetture = new QDmalmElProdottiArchitetture("DMALM_EL_PRODOTTI_ARCH");
	
	public final NumberPath<Integer> prodottoPk = createNumber("DMALM_PRODOTTO_PK", Integer.class);
	public final StringPath idProdottoEdma = createString("ID_PRODOTTO_EDMA");
	public final StringPath idProdotto = createString("ID_PRODOTTO");
	public final StringPath tipoOggetto = createString("TIPO_OGGETTO");
	public final StringPath sigla = createString("SIGLA");
	public final StringPath nome = createString("NOME");
	public final StringPath descrizioneProdotto = createString("DS_PRODOTTO");
	public final StringPath areaProdotto = createString("AREA_PRODOTTO");
	public final StringPath responsabileProdotto = createString("RESPONSABILE_PRODOTTO");
	public final StringPath annullato = createString("ANNULLATO");
	public final DateTimePath<java.sql.Timestamp> dataAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
	public final StringPath ambitoManutenzione = createString("AMBITO_MANUTENZIONE");
	public final StringPath areaTematica = createString("AREA_TEMATICA");
	public final StringPath baseDatiEtl = createString("BASE_DATI_ETL");
	public final StringPath baseDatiLettura = createString("BASE_DATI_LETTURA");
	public final StringPath baseDatiScrittura = createString("BASE_DATI_SCRITTURA");
	public final StringPath categoria = createString("CATEGORIA");
	public final StringPath fornituraRisorseEsterne = createString("FORNITURA_RISORSE_ESTERNE");
	public final StringPath codiceAreaProdotto = createString("CD_AREA_PRODOTTO");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	public final NumberPath<Integer> unitaOrganizzativaFk = createNumber("DMALM_UNITAORGANIZZATIVA_FK_01", Integer.class);
	public final NumberPath<Integer> personaleFk = createNumber("DMALM_PERSONALE_FK_02", Integer.class);
	public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);
	public final NumberPath<Integer> unitaOrgFlatFk = createNumber("DMALM_UNITAORG_FLAT_FK_04", Integer.class);
	
	//Aggiunto per DM_ALM-224
	//TODO creare campi su tabella target e riempire i nomi
	public final StringPath ambitoTecnologico = createString("");
	public final StringPath ambitoManutenzioneDenom = createString("");
	public final StringPath ambitoManutenzioneCodice = createString("");
	public final StringPath stato = createString("");
	
	
	public final com.mysema.query.sql.PrimaryKey<DmalmElProdottiArchitetture> dmalmProdottoPk = createPrimaryKey(prodottoPk);	
	
	public QDmalmElProdottiArchitetture(String variable) {
        super(DmalmElProdottiArchitetture.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_PRODOTTI_ARCH");
    }

    public QDmalmElProdottiArchitetture(Path<? extends DmalmElProdottiArchitetture> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_PRODOTTI_ARCH");
    }

    public QDmalmElProdottiArchitetture(PathMetadata<?> metadata) {
        super(DmalmElProdottiArchitetture.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_PRODOTTI_ARCH");
    }

}
