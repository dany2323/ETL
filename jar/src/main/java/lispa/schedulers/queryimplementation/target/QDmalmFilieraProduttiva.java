package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.DmalmFilieraProduttiva;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmFilieraProduttiva extends
		com.mysema.query.sql.RelationalPathBase<DmalmFilieraProduttiva> {
	private static final long serialVersionUID = 7045417374645807622L;

	public static final QDmalmFilieraProduttiva dmalmFilieraProduttiva = new QDmalmFilieraProduttiva(
			"DMALM_FILIERA_PRODUTTIVA");

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
	public final NumberPath<Integer> fkWiFiglio = createNumber("FK_WI_FIGLIO",
			Integer.class);
	public final StringPath codiceWiFiglio = createString("CODICE_WI_FIGLIO");
	public final StringPath tipoWiFiglio = createString("TIPO_WI_FIGLIO");
	public final StringPath idRepositoryFiglio = createString("ID_REPOSITORY_FIGLIO");
	public final StringPath uriWiFiglio = createString("URI_WI_Figlio");
	public final StringPath codiceProjectFiglio = createString("CODICE_PROJECT_FIGLIO");
	public final StringPath ruolo = createString("RUOLO");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime(
			"DT_CARICAMENTO", java.sql.Timestamp.class);

	public QDmalmFilieraProduttiva(String variable) {
		super(DmalmFilieraProduttiva.class, forVariable(variable), "DMALM",
				"DMALM_FILIERA_PRODUTTIVA");
	}

	public QDmalmFilieraProduttiva(Path<? extends DmalmFilieraProduttiva> path) {
		super(path.getType(), path.getMetadata(), "DMALM",
				"DMALM_FILIERA_PRODUTTIVA");
	}

	public QDmalmFilieraProduttiva(PathMetadata<?> metadata) {
		super(DmalmFilieraProduttiva.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_FILIERA_PRODUTTIVA");
	}
}
