package lispa.schedulers.queryimplementation.target.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.mps.DmalmMpsRilasciVerbali;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmMpsRilasciVerbali extends com.mysema.query.sql.RelationalPathBase<DmalmMpsRilasciVerbali> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4406769658267436783L;

	public static final QDmalmMpsRilasciVerbali dmalmMpsRilasciVerbali = new QDmalmMpsRilasciVerbali(
			"MPS_BO_RILASCI_VERBALI");

	public final StringPath codVerbale = createString("CODVERBALE");

	public final NumberPath<Integer> idVerbaleValidazione = createNumber("IDVERBALEVALIDAZIONE",
			Integer.class);

	public final NumberPath<Integer> idRilascio = createNumber("IDRILASCIO",
			Integer.class);

	public final NumberPath<Integer> importo = createNumber("IMPORTO",
			Integer.class);

	public QDmalmMpsRilasciVerbali(String variable) {
		super(DmalmMpsRilasciVerbali.class, forVariable(variable),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_RILASCI_VERBALI");
	}

	public QDmalmMpsRilasciVerbali(Path<? extends DmalmMpsRilasciVerbali> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_RILASCI_VERBALI");
	}

	public QDmalmMpsRilasciVerbali(PathMetadata<?> metadata) {
		super(DmalmMpsRilasciVerbali.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_RILASCI_VERBALI");
	}
	
	
	
	
	
}
