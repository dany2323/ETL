package lispa.schedulers.queryimplementation.target.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.mps.DmalmMpsContratti;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmMpsContratti extends com.mysema.query.sql.RelationalPathBase<DmalmMpsContratti> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 552219106401369649L;

	public static final QDmalmMpsContratti dmalmMpsContratti = new QDmalmMpsContratti(
			"MPS_BO_CONTRATTI");

	public final NumberPath<Integer> idContratto = createNumber("IDCONTRATTO",
			Integer.class);

	public final StringPath codContratto = createString("CODCONTRATTO");

	public final StringPath titoloContratto = createString("TITOLOCONTRATTO");

	public final NumberPath<Integer> annoRiferimento = createNumber(
			"ANNO_RIFERIMENTO", Integer.class);

	public final DateTimePath<java.sql.Timestamp> dataInizio = createDateTime(
			"DATA_INIZIO", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dataFine = createDateTime(
			"DATA_FINE", java.sql.Timestamp.class);

	public final StringPath tipo = createString("TIPO");

	public final StringPath stato = createString("STATO");

	public final StringPath firmaDigitale = createString("FIRMA_DIGITALE");

	public final StringPath variato = createString("VARIATO");

	public final NumberPath<Integer> numVariazioni = createNumber(
			"NUM_VARIAZIONI", Integer.class);

	public final StringPath codDirezione = createString("CODDIREZIONE");

	public final StringPath desDirezione = createString("DESDIREZIONE");

	public final StringPath codUo = createString("CODUO");

	public final StringPath desUo = createString("DESUO");

	public final StringPath codStruttura = createString("CODSTRUTTURA");

	public final StringPath desStruttura = createString("DESSTRUTTURA");

	public final NumberPath<Integer> totaleContratto = createNumber(
			"TOTALE_CONTRATTO", Integer.class);

	public final NumberPath<Integer> totaleImpegnato = createNumber(
			"TOTALE_IMPEGNATO", Integer.class);

	public final NumberPath<Integer> totaleSpalmato = createNumber(
			"TOTALE_SPALMATO", Integer.class);

	public final NumberPath<Integer> totaleVerbalizzato = createNumber(
			"TOTALE_VERBALIZZATO", Integer.class);

	public final NumberPath<Integer> totaleRichiesto = createNumber(
			"TOTALE_RICHIESTO", Integer.class);

	public final NumberPath<Integer> totaleFatturato = createNumber(
			"TOTALE_FATTURATO", Integer.class);

	public final NumberPath<Integer> totaleFatturabile = createNumber(
			"TOTALE_FATTURABILE", Integer.class);

	public final StringPath prossimoFirmatario = createString("PROSSIMO_FIRMATARIO");

	public final DateTimePath<java.sql.Timestamp> inCorsoIl = createDateTime(
			"IN_CORSO_IL", java.sql.Timestamp.class);

	public final NumberPath<Integer> numeroRilasci = createNumber(
			"NUMERO_RILASCI", Integer.class);

	public final NumberPath<Integer> numeroRilasciForfait = createNumber(
			"NUMERO_RILASCI_FORFAIT", Integer.class);

	public final NumberPath<Integer> numeroRilasciCanone = createNumber(
			"NUMERO_RILASCI_CANONE", Integer.class);

	public final NumberPath<Integer> numeroRilasciConsumo = createNumber(
			"NUMERO_RILASCI_CONSUMO", Integer.class);

	public final NumberPath<Integer> numeroVerbali = createNumber(
			"NUMERO_VERBALI", Integer.class);

	public final NumberPath<Integer> numeroVerbaliForfait = createNumber(
			"NUMERO_VERBALI_FORFAIT", Integer.class);

	public final NumberPath<Integer> numeroVerbaliConsumo = createNumber(
			"NUMERO_VERBALI_CONSUMO", Integer.class);

	public final StringPath desMotivoVariazione = createString("DESMOTIVOVARIAZIONE");

	public final StringPath repository = createString("REPOSITORY");

	public final StringPath priorita = createString("PRIORITA");

	public final NumberPath<Integer> idSm = createNumber("ID_SM", Integer.class);

	public final StringPath serviceManager = createString("SERVICE_MANAGER");

	public QDmalmMpsContratti(String variable) {
		super(DmalmMpsContratti.class, forVariable(variable),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_CONTRATTI");
	}

	public QDmalmMpsContratti(Path<? extends DmalmMpsContratti> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_CONTRATTI");
	}

	public QDmalmMpsContratti(PathMetadata<?> metadata) {
		super(DmalmMpsContratti.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_CONTRATTI");
	}

}
