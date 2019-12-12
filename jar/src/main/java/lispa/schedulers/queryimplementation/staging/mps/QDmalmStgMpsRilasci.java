package lispa.schedulers.queryimplementation.staging.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.staging.mps.DmalmStgMpsRilasci;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmStgMpsRilasci extends
		com.mysema.query.sql.RelationalPathBase<DmalmStgMpsRilasci> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4677677993050674893L;

	public static final QDmalmStgMpsRilasci dmalmStgMpsRilasci = new QDmalmStgMpsRilasci(
			"DM_ALM_MPS_RILASCI");

	public final NumberPath<Integer> idRilascio = createNumber("IDRILASCIO",
			Integer.class);
	public final NumberPath<Integer> idContratto = createNumber("IDCONTRATTO",
			Integer.class);
	public final StringPath codRilascio = createString("CODRILASCIO");
	public final StringPath tipoRilascio = createString("TIPORILASCIO");
	public final StringPath sottoTipoRilascio = createString("SOTTOTIPORILASCIO");
	public final StringPath titoloRilascio = createString("TITOLORILASCIO");
	public final StringPath desAttivita = createString("DESATTIVITA");
	public final DateTimePath<java.sql.Timestamp> dataInizio = createDateTime(
			"DATA_INIZIO", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataRilascio = createDateTime(
			"DATA_RILASCIO", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataValidazione = createDateTime(
			"DATA_VALIDAZIONE", java.sql.Timestamp.class);
	public final StringPath statoFatturazione = createString("STATO_FATTURAZIONE");
	public final StringPath statoFinanziamento = createString("STATO_FINANZIAMENTO");
	public final NumberPath<Integer> importoRilascio = createNumber(
			"IMPORTO_RILASCIO", Integer.class);
	public final NumberPath<Integer> totaleSpalmato = createNumber(
			"TOTALE_SPALMATO", Integer.class);
	public final NumberPath<Integer> totaleVerbalizzato = createNumber(
			"TOTALE_VERBALIZZATO", Integer.class);
	public final NumberPath<Integer> totaleRichiesta = createNumber(
			"TOTALE_RICHIESTA", Integer.class);
	public final NumberPath<Integer> totaleFatturato = createNumber(
			"TOTALE_FATTURATO", Integer.class);
	public final NumberPath<Integer> totaleFatturabile = createNumber(
			"TOTALE_FATTURABILE", Integer.class);
	public final DateTimePath<java.sql.Timestamp> dataRilascioEffettivo = createDateTime(
			"DATA_RILASCIO_EFFETTIVO", java.sql.Timestamp.class);
	public final StringPath varianteMigliorativa = createString("VARIANTE_MIGLIORATIVA");
	public final StringPath statoVerbalizzazione = createString("STATO_VERBALIZZAZIONE");

	public QDmalmStgMpsRilasci(String variable) {
		super(DmalmStgMpsRilasci.class, forVariable(variable),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_RILASCI");
	}

	public QDmalmStgMpsRilasci(Path<? extends DmalmStgMpsRilasci> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_RILASCI");
	}

	public QDmalmStgMpsRilasci(PathMetadata<?> metadata) {
		super(DmalmStgMpsRilasci.class, metadata,
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_RILASCI");
	}

}
