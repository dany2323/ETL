package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.DmalmProjectUnitaOrganizzativaEccezioni;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;

public class QDmalmProjectUnitaOrganizzativaEccezioni
		extends
		com.mysema.query.sql.RelationalPathBase<DmalmProjectUnitaOrganizzativaEccezioni> {

	private static final long serialVersionUID = -7879064119010632502L;

	public static final QDmalmProjectUnitaOrganizzativaEccezioni dmalmProjectUnitaOrganizzativaEccezioni = new QDmalmProjectUnitaOrganizzativaEccezioni(
			"DMALM_PROJECT_UNITAORG_ECZ");

	public final StringPath idRepository = createString("ID_REPOSITORY");
	public final StringPath nomeCompletoProject = createString("NOME_COMPLETO_PROJECT");
	public final StringPath template = createString("TEMPLATE");
	public final StringPath codiceArea = createString("CD_AREA");

	public QDmalmProjectUnitaOrganizzativaEccezioni(String variable) {
		super(DmalmProjectUnitaOrganizzativaEccezioni.class,
				forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_PROJECT_UNITAORG_ECZ");
	}

	public QDmalmProjectUnitaOrganizzativaEccezioni(
			Path<? extends DmalmProjectUnitaOrganizzativaEccezioni> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_PROJECT_UNITAORG_ECZ");
	}

	public QDmalmProjectUnitaOrganizzativaEccezioni(PathMetadata<?> metadata) {
		super(DmalmProjectUnitaOrganizzativaEccezioni.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_PROJECT_UNITAORG_ECZ");
	}
}
