package lispa.schedulers.queryimplementation.target.sfera;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.sfera.DmalmAsmProdottiArchitetture;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;

public class QDmalmAsmProdottiArchitetture extends com.mysema.query.sql.RelationalPathBase<DmalmAsmProdottiArchitetture> {
	private static final long serialVersionUID = -6210976049748578518L;

	public static final QDmalmAsmProdottiArchitetture qDmalmAsmProdottiArchitetture = new QDmalmAsmProdottiArchitetture("DMALM_ASM_PRODOTTI_ARCH");

    public final NumberPath<Integer> dmalmAsmPk = createNumber("DMALM_ASM_PK", Integer.class);

    public final NumberPath<Integer> dmalmProdottoPk = createNumber("DMALM_PRODOTTO_PK", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public QDmalmAsmProdottiArchitetture(String variable) {
        super(DmalmAsmProdottiArchitetture.class, forVariable(variable), "DMALM", "DMALM_ASM_PRODOTTI_ARCH");
    }

    public QDmalmAsmProdottiArchitetture(Path<? extends DmalmAsmProdottiArchitetture> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_ASM_PRODOTTI_ARCH");
    }

    public QDmalmAsmProdottiArchitetture(PathMetadata<?> metadata) {
        super(DmalmAsmProdottiArchitetture.class, metadata, "DMALM", "DMALM_ASM_PRODOTTI_ARCH");
    }

}
