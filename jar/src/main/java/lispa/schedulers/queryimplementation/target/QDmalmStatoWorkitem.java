package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmStatoWorkitem;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmStatoWorkitem is a Querydsl query type for DmalmStatoWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmStatoWorkitem extends com.mysema.query.sql.RelationalPathBase<DmalmStatoWorkitem> {

    private static final long serialVersionUID = 1324866996;

    public static final QDmalmStatoWorkitem dmalmStatoWorkitem = new QDmalmStatoWorkitem("DMALM_STATO_WORKITEM");

    public final StringPath cdStato = createString("CD_STATO");

    public final NumberPath<Integer> dmalmStatoWorkitemPrimaryKey = createNumber("DMALM_STATO_WORKITEM_PK", Integer.class);

    public final StringPath dsStato = createString("DS_STATO");
    
    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath origineStato = createString("ORIGINE_STATO");
    
    public final StringPath template = createString("TEMPLATE");

    public final com.mysema.query.sql.PrimaryKey<DmalmStatoWorkitem> dmalmStatoWorkitemPk = createPrimaryKey(dmalmStatoWorkitemPrimaryKey);

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto> _dmalmDifettoProdottoFk03 = createInvForeignKey(dmalmStatoWorkitemPrimaryKey, "DMALM_STATO_WORKITEM_FK_03");

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto> _dmalmAnomaliaProdottoFk03 = createInvForeignKey(dmalmStatoWorkitemPrimaryKey, "DMALM_STATO_WORKITEM_FK_03");

    public QDmalmStatoWorkitem(String variable) {
        super(DmalmStatoWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_STATO_WORKITEM");
    }

    public QDmalmStatoWorkitem(Path<? extends DmalmStatoWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_STATO_WORKITEM");
    }

    public QDmalmStatoWorkitem(PathMetadata<?> metadata) {
        super(DmalmStatoWorkitem.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_STATO_WORKITEM");
    }

}

