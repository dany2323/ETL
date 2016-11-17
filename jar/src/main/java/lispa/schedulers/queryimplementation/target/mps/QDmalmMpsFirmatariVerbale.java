package lispa.schedulers.queryimplementation.target.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.mps.DmalmMpsFirmatariVerbale;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmMpsFirmatariVerbale extends com.mysema.query.sql.RelationalPathBase<DmalmMpsFirmatariVerbale> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9043188251720617737L;

	public static final QDmalmMpsFirmatariVerbale dmalmMpsFirmatariVerbale = new QDmalmMpsFirmatariVerbale(
			"MPS_BO_FIRMATARI_VERBALE");

	public final NumberPath<Integer> idVerbaleValidazione = createNumber("IDVERBALEVALIDAZIONE",
			Integer.class);

	public final NumberPath<Integer> idUtente = createNumber("IDUTENTE",
			Integer.class);

	public final StringPath firmatarioVerbale = createString("FIRMATARIO_VERBALE");

	public final StringPath tipologiaResponsabile = createString("TIPOLOGIA_RESPONSABILE");

	public final NumberPath<Integer> ordineFirma = createNumber("ORDINE_FIRMA",
			Integer.class);

	public final StringPath firmato = createString("FIRMATO");

	public final DateTimePath<java.sql.Timestamp> dataFirma = createDateTime(
			"DATA_FIRMA", java.sql.Timestamp.class);

	public final NumberPath<Integer> idEnte = createNumber("IDENTE",
			Integer.class);

	public QDmalmMpsFirmatariVerbale(String variable) {
		super(DmalmMpsFirmatariVerbale.class, forVariable(variable),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_FIRMATARI_VERBALE");
	}

	public QDmalmMpsFirmatariVerbale(Path<? extends DmalmMpsFirmatariVerbale> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_FIRMATARI_VERBALE");
	}

	public QDmalmMpsFirmatariVerbale(PathMetadata<?> metadata) {
		super(DmalmMpsFirmatariVerbale.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_FIRMATARI_VERBALE");
	}
	
	
}
