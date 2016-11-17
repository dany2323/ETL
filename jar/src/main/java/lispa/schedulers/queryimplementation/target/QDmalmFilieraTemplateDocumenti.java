package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.DmalmFilieraTemplateDocumenti;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmFilieraTemplateDocumenti extends
		com.mysema.query.sql.RelationalPathBase<DmalmFilieraTemplateDocumenti> {

	private static final long serialVersionUID = 3200263900776010792L;

	public static final QDmalmFilieraTemplateDocumenti dmalmFilieraTemplateDocumenti = new QDmalmFilieraTemplateDocumenti(
			"DMALM_TEMPLATE_DOCUMENTI");

	public final StringPath template = createString("TEMPLATE");
	public final NumberPath<Integer> idFiliera = createNumber("ID_FILIERA",
			Integer.class);
	public final NumberPath<Integer> livello = createNumber("LIVELLO",
			Integer.class);
	public final NumberPath<Integer> fkWi = createNumber("FK_WI", Integer.class);
	public final StringPath tipoWi = createString("TIPO_WI");
	public final NumberPath<Integer> fkDocumento = createNumber("FK_DOCUMENTO",
			Integer.class);

	public QDmalmFilieraTemplateDocumenti(String variable) {
		super(DmalmFilieraTemplateDocumenti.class, forVariable(variable),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_DOCUMENTI");
	}

	public QDmalmFilieraTemplateDocumenti(
			Path<? extends DmalmFilieraTemplateDocumenti> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_DOCUMENTI");
	}

	public QDmalmFilieraTemplateDocumenti(PathMetadata<?> metadata) {
		super(DmalmFilieraTemplateDocumenti.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPLATE_DOCUMENTI");
	}

}
