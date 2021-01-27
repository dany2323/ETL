package lispa.schedulers.queryimplementation.fonte.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.constant.DmAlmConstants;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class DmAlmMpsRespContratto extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.mps.DmAlmMpsRespContratto> {
	
	private static final long serialVersionUID = 2925542199019784652L;

	public static final DmAlmMpsRespContratto dmalmMpsRespContratto = new DmAlmMpsRespContratto(
			"DM_ALM_MPS_RESPON_CONTRATTO");

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
	
	public DmAlmMpsRespContratto(String variable) {
		super(lispa.schedulers.bean.fonte.mps.DmAlmMpsRespContratto.class, forVariable(variable),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_RESPON_CONTRATTO");
	}

	public DmAlmMpsRespContratto(Path<? extends lispa.schedulers.bean.fonte.mps.DmAlmMpsRespContratto> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_RESPON_CONTRATTO");
	}

	public DmAlmMpsRespContratto(PathMetadata<?> metadata) {
		super(lispa.schedulers.bean.fonte.mps.DmAlmMpsRespContratto.class, metadata,
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_RESPON_CONTRATTO");
	}

}
