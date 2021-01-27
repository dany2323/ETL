package lispa.schedulers.queryimplementation.fonte.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.constant.DmAlmConstants;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class DmAlmMpsFirmatariVerbale extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.mps.DmAlmMpsFirmatariVerbale> {

	private static final long serialVersionUID = 3688686623065154815L;

	public static final DmAlmMpsFirmatariVerbale dmalmMpsFirmatariVerbale = new DmAlmMpsFirmatariVerbale("DM_ALM_MPS_FIRMAT_VERBALE");

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
	
	public DmAlmMpsFirmatariVerbale(String variable) {
		super(lispa.schedulers.bean.fonte.mps.DmAlmMpsFirmatariVerbale.class, forVariable(variable),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_FIRMAT_VERBALE");
	}

	public DmAlmMpsFirmatariVerbale(Path<? extends lispa.schedulers.bean.fonte.mps.DmAlmMpsFirmatariVerbale> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_FIRMAT_VERBALE");
	}

	public DmAlmMpsFirmatariVerbale(PathMetadata<?> metadata) {
		super(lispa.schedulers.bean.fonte.mps.DmAlmMpsFirmatariVerbale.class, metadata,
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_FIRMAT_VERBALE");
	}

}
