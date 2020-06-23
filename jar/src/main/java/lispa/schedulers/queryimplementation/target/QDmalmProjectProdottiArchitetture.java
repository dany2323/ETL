package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.DmalmProjectProdottiArchitetture;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;

public class QDmalmProjectProdottiArchitetture extends com.mysema.query.sql.RelationalPathBase<DmalmProjectProdottiArchitetture> {
	private static final long serialVersionUID = -8252783510652943917L;

	public static final QDmalmProjectProdottiArchitetture qDmalmProjectProdottiArchitetture = new QDmalmProjectProdottiArchitetture(
			"DMALM_PROJECT_PRODOTTI_ARCH");

	public final NumberPath<Integer> dmalmProjectPk = createNumber(
			"DMALM_PROJECT_PK", Integer.class);

	public final NumberPath<Integer> dmalmProdottoPk = createNumber(
			"DMALM_PRODOTTO_PK", Integer.class);

	public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime(
			"DT_FINE_VALIDITA", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime(
			"DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

	public QDmalmProjectProdottiArchitetture(String variable) {
		super(DmalmProjectProdottiArchitetture.class, forVariable(variable),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROJECT_PRODOTTI_ARCH");
	}

	public QDmalmProjectProdottiArchitetture(
			Path<? extends DmalmProjectProdottiArchitetture> path) {
		super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_PROJECT_PRODOTTI_ARCH");
	}

	public QDmalmProjectProdottiArchitetture(PathMetadata<?> metadata) {
		super(DmalmProjectProdottiArchitetture.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_PROJECT_PRODOTTI_ARCH");
	}
}
