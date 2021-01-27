package lispa.schedulers.queryimplementation.fonte.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.constant.DmAlmConstants;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class DmAlmMpsRilasciVerbali extends
		com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.mps.DmAlmMpsRilasciVerbali> {

	private static final long serialVersionUID = -119518718094412479L;

	public static final DmAlmMpsRilasciVerbali dmalmMpsRilasciVerbali = new DmAlmMpsRilasciVerbali(
			"DM_ALM_MPS_RILASCI_VERBALI");

	public final StringPath codVerbale = createString("CODVERBALE");

	public final NumberPath<Integer> idVerbaleValidazione = createNumber(
			"IDVERBALEVALIDAZIONE", Integer.class);

	public final NumberPath<Integer> idRilascio = createNumber(
			"IDRILASCIO", Integer.class);

	public final NumberPath<Integer> importo = createNumber(
			"IMPORTO", Integer.class);

	public DmAlmMpsRilasciVerbali(String variable) {
		super(lispa.schedulers.bean.fonte.mps.DmAlmMpsRilasciVerbali.class, forVariable(variable),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_RILASCI_VERBALI");
	}

	public DmAlmMpsRilasciVerbali(
			Path<? extends lispa.schedulers.bean.fonte.mps.DmAlmMpsRilasciVerbali> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_RILASCI_VERBALI");
	}

	public DmAlmMpsRilasciVerbali(PathMetadata<?> metadata) {
		super(lispa.schedulers.bean.fonte.mps.DmAlmMpsRilasciVerbali.class, metadata,
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_RILASCI_VERBALI");
	}
}