package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.DmalmFilieraTemplateIntTecnica;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmFilieraTemplateIntTecnica extends
com.mysema.query.sql.RelationalPathBase<DmalmFilieraTemplateIntTecnica>{

	private static final long serialVersionUID = -20322652677184992L;

	public static final QDmalmFilieraTemplateIntTecnica dmalmFilieraTemplateIntTecnica = new QDmalmFilieraTemplateIntTecnica(
			"DMALM_TEMPLATE_INT_TECNICA");

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

	public QDmalmFilieraTemplateIntTecnica(String variable) {
		super(DmalmFilieraTemplateIntTecnica.class, forVariable(variable),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_INT_TECNICA");
	}

	public QDmalmFilieraTemplateIntTecnica(
			Path<? extends DmalmFilieraTemplateIntTecnica> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_INT_TECNICA");
	}

	public QDmalmFilieraTemplateIntTecnica(PathMetadata<?> metadata) {
		super(DmalmFilieraTemplateIntTecnica.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_INT_TECNICA");
	}
}
