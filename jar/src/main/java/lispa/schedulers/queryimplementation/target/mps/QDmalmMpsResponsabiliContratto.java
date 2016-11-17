package lispa.schedulers.queryimplementation.target.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.mps.DmalmMpsResponsabiliContratto;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmMpsResponsabiliContratto extends com.mysema.query.sql.RelationalPathBase<DmalmMpsResponsabiliContratto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1161593472830744610L;

	public static final QDmalmMpsResponsabiliContratto dmalmMpsResponsabiliContratto = new QDmalmMpsResponsabiliContratto(
			"MPS_BO_RESPONSABILI_CONTRATTO");

	public final NumberPath<Integer> idContratto = createNumber("IDCONTRATTO",
			Integer.class);

	public final NumberPath<Integer> idUtente = createNumber("IDUTENTE",
			Integer.class);

	public final StringPath responsabile = createString("RESPONSABILE");

	public final StringPath desTipologiaResponsabile = createString("DESTIPOLOGIARESPONSABILE");

	public final StringPath firmatario = createString("FIRMATARIO");

	public final NumberPath<Integer> ordineFirma = createNumber("ORDINE_FIRMA",
			Integer.class);

	public final StringPath firmato = createString("FIRMATO");

	public final DateTimePath<java.sql.Timestamp> dataFirma = createDateTime(
			"DATA_FIRMA", java.sql.Timestamp.class);

	public final NumberPath<Integer> idEnte = createNumber("IDENTE",
			Integer.class);

	public final StringPath ente = createString("ENTE");
	
	public QDmalmMpsResponsabiliContratto(String variable) {
		super(DmalmMpsResponsabiliContratto.class, forVariable(variable),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_RESPONSABILI_CONTRATTO");
	}

	public QDmalmMpsResponsabiliContratto(Path<? extends DmalmMpsResponsabiliContratto> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_RESPONSABILI_CONTRATTO");
	}

	public QDmalmMpsResponsabiliContratto(PathMetadata<?> metadata) {
		super(DmalmMpsResponsabiliContratto.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_RESPONSABILI_CONTRATTO");
	}
	
	
}
