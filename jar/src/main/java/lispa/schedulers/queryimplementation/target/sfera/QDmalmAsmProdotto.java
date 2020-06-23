package lispa.schedulers.queryimplementation.target.sfera;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import lispa.schedulers.bean.target.DmalmProdotto;
import lispa.schedulers.bean.target.sfera.DmalmAsm;
import lispa.schedulers.bean.target.sfera.DmalmAsmProdotto;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;

/**
 * QDmalmAsmProdotto is a Querydsl query type for DmalmAsmProdotto
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmAsmProdotto extends com.mysema.query.sql.RelationalPathBase<DmalmAsmProdotto> {

    private static final long serialVersionUID = -32169004;

    public static final QDmalmAsmProdotto dmalmAsmProdotto = new QDmalmAsmProdotto("DMALM_ASM_PRODOTTO");

    public final NumberPath<Integer> dmalmAsmPk = createNumber("DMALM_ASM_PK", Integer.class);

    public final NumberPath<Integer> dmalmProdottoSeq = createNumber("DMALM_PRODOTTO_SEQ", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<DmalmAsmProdotto> dmalmAsmProdottoPk = createPrimaryKey(dmalmAsmPk, dmalmProdottoSeq, dtInizioValidita);

    public final com.mysema.query.sql.ForeignKey<DmalmAsm> dmalmAsmProdottoFk01 = createForeignKey(dmalmAsmPk, "DMALM_ASM_PK");

    public final com.mysema.query.sql.ForeignKey<DmalmProdotto> dmalmAsmProdottoFk02 = createForeignKey(dmalmProdottoSeq, "DMALM_PRODOTTO_SEQ");

    public QDmalmAsmProdotto(String variable) {
        super(DmalmAsmProdotto.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_PRODOTTO");
    }

    public QDmalmAsmProdotto(Path<? extends DmalmAsmProdotto> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_PRODOTTO");
    }

    public QDmalmAsmProdotto(PathMetadata<?> metadata) {
        super(DmalmAsmProdotto.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ASM_PRODOTTO");
    }

}

