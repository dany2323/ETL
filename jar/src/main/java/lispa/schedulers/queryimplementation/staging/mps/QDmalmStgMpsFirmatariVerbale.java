package lispa.schedulers.queryimplementation.staging.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.staging.mps.DmalmStgMpsFirmatariVerbale;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmStgMpsFirmatariVerbale extends com.mysema.query.sql.RelationalPathBase<DmalmStgMpsFirmatariVerbale> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3688686623065154815L;

	public static final QDmalmStgMpsFirmatariVerbale dmalmStgMpsFirmatariVerbale = new QDmalmStgMpsFirmatariVerbale(
			"DM_ALM_MPS_FIRMAT_VERBALE");

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

	public final StringPath ente = createString("ENTE");
	
	public QDmalmStgMpsFirmatariVerbale(String variable) {
		super(DmalmStgMpsFirmatariVerbale.class, forVariable(variable),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_FIRMAT_VERBALE");
	}

	public QDmalmStgMpsFirmatariVerbale(Path<? extends DmalmStgMpsFirmatariVerbale> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_FIRMAT_VERBALE");
	}

	public QDmalmStgMpsFirmatariVerbale(PathMetadata<?> metadata) {
		super(DmalmStgMpsFirmatariVerbale.class, metadata,
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_FIRMAT_VERBALE");
	}

}
