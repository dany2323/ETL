package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;

import lispa.schedulers.bean.target.DmalmAsmProject;
import lispa.schedulers.constant.DmAlmConstants;


@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmAsmProject extends com.mysema.query.sql.RelationalPathBase<DmalmAsmProject> {
	private static final long serialVersionUID = 8347978709259538456L;

	public static final QDmalmAsmProject dmalmAsmProject = new QDmalmAsmProject(
			"DMALM_ASM_PROJECT_PRODOTTO");

	public final NumberPath<Long> dmalmProjectPk = createNumber(
			"DMALM_PROJECT_PK", Long.class);

	public final NumberPath<Integer> dmalmAsmPk = createNumber(
			"DMALM_ASM_PK", Integer.class);

	public final NumberPath<Integer> dmalmProdottoPk = createNumber(
			"DMALM_PRODOTTO_PK", Integer.class);
	
	public QDmalmAsmProject (String variable){
		super(DmalmAsmProject.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_PROJECT_PRODOTTO");
	}
	
	public QDmalmAsmProject(Path<? extends DmalmAsmProject> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_PROJECT_PRODOTTO");
    }

    public QDmalmAsmProject(PathMetadata<?> metadata) {
        super(DmalmAsmProject.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_PROJECT_PRODOTTO");
    }
}
