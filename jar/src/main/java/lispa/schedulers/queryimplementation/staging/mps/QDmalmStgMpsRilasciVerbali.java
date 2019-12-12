package lispa.schedulers.queryimplementation.staging.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.staging.mps.DmalmStgMpsRilasciVerbali;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmStgMpsRilasciVerbali extends
		com.mysema.query.sql.RelationalPathBase<DmalmStgMpsRilasciVerbali> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -119518718094412479L;

	public static final QDmalmStgMpsRilasciVerbali dmalmStgMpsRilasciVerbali = new QDmalmStgMpsRilasciVerbali(
			"DM_ALM_MPS_RILASCI_VERBALI");

	public final StringPath codVerbale = createString("CODVERBALE");

	public final NumberPath<Integer> idVerbaleValidazione = createNumber(
			"IDVERBALEVALIDAZIONE", Integer.class);

	public final NumberPath<Integer> idRilascio = createNumber(
			"IDRILASCIO", Integer.class);

	public final NumberPath<Integer> importo = createNumber(
			"IMPORTO", Integer.class);

	public QDmalmStgMpsRilasciVerbali(String variable) {
		super(DmalmStgMpsRilasciVerbali.class, forVariable(variable),
				DmAlmConstants.DMALM_STAGING_SCHEMA,
				"DM_ALM_MPS_RILASCI_VERBALI");
	}

	public QDmalmStgMpsRilasciVerbali(
			Path<? extends DmalmStgMpsRilasciVerbali> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_STAGING_SCHEMA,
				"DM_ALM_MPS_RILASCI_VERBALI");
	}

	public QDmalmStgMpsRilasciVerbali(PathMetadata<?> metadata) {
		super(DmalmStgMpsRilasciVerbali.class, metadata,
				DmAlmConstants.DMALM_STAGING_SCHEMA,
				"DM_ALM_MPS_RILASCI_VERBALI");
	}

}
