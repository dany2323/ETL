package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.DmalmFilieraTemplateServizi;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmFilieraTemplateServizi extends
com.mysema.query.sql.RelationalPathBase<DmalmFilieraTemplateServizi>{

	private static final long serialVersionUID = -3216232572097195959L;

	public static final QDmalmFilieraTemplateServizi dmalmFilieraTemplateServizi = new QDmalmFilieraTemplateServizi(
			"DMALM_TEMPLATE_SERVIZI");

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

	public QDmalmFilieraTemplateServizi(String variable) {
		super(DmalmFilieraTemplateServizi.class, forVariable(variable),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_SERVIZI");
	}

	public QDmalmFilieraTemplateServizi(
			Path<? extends DmalmFilieraTemplateServizi> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_SERVIZI");
	}

	public QDmalmFilieraTemplateServizi(PathMetadata<?> metadata) {
		super(DmalmFilieraTemplateServizi.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_SERVIZI");
	}
	
}
