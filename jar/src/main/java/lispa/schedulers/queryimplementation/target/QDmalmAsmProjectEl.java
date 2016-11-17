package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;

import lispa.schedulers.bean.target.DmalmAsmProjectEl;
import lispa.schedulers.constant.DmAlmConstants;

@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmAsmProjectEl extends com.mysema.query.sql.RelationalPathBase<DmalmAsmProjectEl>{
	private static final long serialVersionUID = 1899939258946422115L;

	public static final QDmalmAsmProjectEl dmalmAsmProject = new QDmalmAsmProjectEl(
			"DMALM_ASM_PROJECT_PRODOTTI_ARC");

	public final NumberPath<Integer> dmalmProjectPk = createNumber(
			"DMALM_PROJECT_PK", Integer.class);

	public final NumberPath<Integer> dmalmAsmPk = createNumber(
			"DMALM_ASM_PK", Integer.class);

	public final NumberPath<Integer> dmalmProdottoPk = createNumber(
			"DMALM_PRODOTTO_PK", Integer.class);
	
	public QDmalmAsmProjectEl (String variable){
		super(DmalmAsmProjectEl.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_PROJECT_PRODOTTI_ARC");
	}
	
	public QDmalmAsmProjectEl(Path<? extends DmalmAsmProjectEl> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_PROJECT_PRODOTTI_ARC");
    }

    public QDmalmAsmProjectEl(PathMetadata<?> metadata) {
        super(DmalmAsmProjectEl.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_PROJECT_PRODOTTI_ARC");
    }
}
