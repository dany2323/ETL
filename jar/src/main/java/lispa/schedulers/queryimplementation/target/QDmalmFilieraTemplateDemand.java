package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.DmalmFilieraTemplateDemand;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmFilieraTemplateDemand extends
	com.mysema.query.sql.RelationalPathBase<DmalmFilieraTemplateDemand> {

	private static final long serialVersionUID = -978118207298814051L;

	public static final QDmalmFilieraTemplateDemand dmalmFilieraTemplateDemand = new QDmalmFilieraTemplateDemand(
		"DMALM_TEMPLATE_DEMAND");
	
	public final NumberPath<Integer> idFiliera = createNumber("ID_FILIERA", Integer.class);
	public final NumberPath<Integer> livello = createNumber("LIVELLO", Integer.class);
	public final NumberPath<Integer> sottoLivello = createNumber("SOTTOLIVELLO", Integer.class);
	public final NumberPath<Integer> fkWi = createNumber("FK_WI", Integer.class);
	public final StringPath codiceWi = createString("CODICE_WI");
	public final StringPath tipoWi = createString("TIPO_WI");
	public final StringPath idRepository = createString("ID_REPOSITORY");
	public final StringPath uriWi = createString("URI_WI");
	public final StringPath codiceProject = createString("CODICE_PROJECT");
	public final StringPath ruolo = createString("RUOLO");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	
	public QDmalmFilieraTemplateDemand(String variable) {
	super(DmalmFilieraTemplateDemand.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA,
			"DMALM_TEMPLATE_DEMAND");
	}
	
	public QDmalmFilieraTemplateDemand(Path<? extends DmalmFilieraTemplateDemand> path) {
	super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA,
			"DMALM_TEMPLATE_DEMAND");
	}
	
	public QDmalmFilieraTemplateDemand(PathMetadata<?> metadata) {
	super(DmalmFilieraTemplateDemand.class, metadata,
			DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_DEMAND");
	}
}
