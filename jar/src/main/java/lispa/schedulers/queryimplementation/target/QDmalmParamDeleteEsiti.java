package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DatePath;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import lispa.schedulers.bean.target.DmalmParamDeleteEsiti;
import lispa.schedulers.constant.DmAlmConstants;

public class QDmalmParamDeleteEsiti extends
		com.mysema.query.sql.RelationalPathBase<DmalmParamDeleteEsiti> {

	private static final long serialVersionUID = 4585456540561937916L;

	public static final QDmalmParamDeleteEsiti dmalmParamDeleteEsiti = new QDmalmParamDeleteEsiti(
			"DMALM_PARAM_DELETE_ESITI");

	public final NumberPath<Integer> id = createNumber("ID", Integer.class);

	public final DatePath<java.sql.Date> dtesecuzioneOp = createDate(
			"DT_ESECUZIONE_OP", java.sql.Date.class);

	public final StringPath codOperazione = createString("COD_OPERAZIONE");

	public final StringPath entita = createString("ENTITA");

	public final DatePath<java.sql.Date> dtDa = createDate("DATA_DA",
			java.sql.Date.class);

	public final DatePath<java.sql.Date> dtA = createDate("DATA_A",
			java.sql.Date.class);

	public final DatePath<java.sql.Date> delAllDa = createDate(
			"CANC_TUTTO_DT_DA", java.sql.Date.class);

	public final DatePath<java.sql.Date> delAllA = createDate(
			"CANC_TUTTO_DT_A", java.sql.Date.class);

	public final StringPath entitaTarget = createString("ENTITA_TARGET");

	public final StringPath entitaSorgente = createString("ENTITA_SORGENTE");

	public final DatePath<java.sql.Date> dtCaricamento = createDate(
			"DT_CARICAMENTO", java.sql.Date.class);

	public final DateTimePath<java.sql.Timestamp> dtEsecEffettiva = createDateTime(
			"DT_ESEC_EFFETTIVA", java.sql.Timestamp.class);

	public QDmalmParamDeleteEsiti(String string) {
		super(DmalmParamDeleteEsiti.class, forVariable(string),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PARAM_DELETE_ESITI");
	}

	public QDmalmParamDeleteEsiti(Path<? extends DmalmParamDeleteEsiti> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PARAM_DELETE_ESITI");
	}

	public QDmalmParamDeleteEsiti(PathMetadata<?> metadata) {
		super(DmalmParamDeleteEsiti.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PARAM_DELETE_ESITI");
	}

}
