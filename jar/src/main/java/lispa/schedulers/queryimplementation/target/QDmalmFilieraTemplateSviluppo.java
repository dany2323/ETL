package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;
import lispa.schedulers.bean.target.DmalmFilieraTemplateSviluppo;
import lispa.schedulers.constant.DmAlmConstants;

public class QDmalmFilieraTemplateSviluppo extends
		com.mysema.query.sql.RelationalPathBase<DmalmFilieraTemplateSviluppo> {

	private static final long serialVersionUID = 8787775363586838280L;

	public static final QDmalmFilieraTemplateSviluppo dmalmFilieraTemplateSviluppo = new QDmalmFilieraTemplateSviluppo(
			"DMALM_TEMPLATE_SVILUPPO");

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

	public QDmalmFilieraTemplateSviluppo(String variable) {
		super(DmalmFilieraTemplateSviluppo.class, forVariable(variable),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_SVILUPPO");
	}

	public QDmalmFilieraTemplateSviluppo(
			Path<? extends DmalmFilieraTemplateSviluppo> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_SVILUPPO");
	}

	public QDmalmFilieraTemplateSviluppo(PathMetadata<?> metadata) {
		super(DmalmFilieraTemplateSviluppo.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_SVILUPPO");
	}

}
