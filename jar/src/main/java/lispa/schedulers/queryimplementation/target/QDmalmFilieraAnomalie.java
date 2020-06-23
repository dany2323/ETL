package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.DmalmFilieraAnomalie;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmFilieraAnomalie extends
		com.mysema.query.sql.RelationalPathBase<DmalmFilieraAnomalie> {
	private static final long serialVersionUID = 3209187201986959433L;

	public static final QDmalmFilieraAnomalie dmalmFilieraAnomalie = new QDmalmFilieraAnomalie(
			"DMALM_FILIERA_ANOMALIE");

	public final NumberPath<Integer> idFiliera = createNumber("ID_FILIERA",
			Integer.class);
	public final NumberPath<Integer> livello = createNumber("LIVELLO",
			Integer.class);
	public final NumberPath<Integer> sottoLivello = createNumber(
			"SOTTOLIVELLO", Integer.class);
	public final NumberPath<Integer> fkWi = createNumber("FK_WI", Integer.class);
	public final StringPath codiceWi = createString("CODICE_WI");
	public final StringPath tipoWi = createString("TIPO_WI");
	public final StringPath idRepository = createString("ID_REPOSITORY");
	public final StringPath uriWi = createString("URI_WI");
	public final StringPath codiceProject = createString("CODICE_PROJECT");
	public final StringPath ruolo = createString("RUOLO");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime(
			"DT_CARICAMENTO", java.sql.Timestamp.class);

	public QDmalmFilieraAnomalie(String variable) {
		super(DmalmFilieraAnomalie.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_FILIERA_ANOMALIE");
	}

	public QDmalmFilieraAnomalie(Path<? extends DmalmFilieraAnomalie> path) {
		super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_FILIERA_ANOMALIE");
	}

	public QDmalmFilieraAnomalie(PathMetadata<?> metadata) {
		super(DmalmFilieraAnomalie.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_FILIERA_ANOMALIE");
	}
}
