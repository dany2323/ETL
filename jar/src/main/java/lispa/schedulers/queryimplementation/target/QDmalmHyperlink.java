package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmHyperlink;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmHyperlink is a Querydsl query type for DmalmHyperlink
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmHyperlink extends com.mysema.query.sql.RelationalPathBase<DmalmHyperlink> {

    private static final long serialVersionUID = 1684568379;

    public static final QDmalmHyperlink dmalmHyperlink = new QDmalmHyperlink("DMALM_HYPERLINK");

    public final NumberPath<Integer> dmalmFkWorkitem01 = createNumber("DMALM_FK_WORKITEM_01", Integer.class);

    public final StringPath dmalmWorkitemType = createString("DMALM_WORKITEM_TYPE");

    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath ruolo = createString("RUOLO");

    public final StringPath uri = createString("URI");

    public QDmalmHyperlink(String variable) {
        super(DmalmHyperlink.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_HYPERLINK");
    }

    public QDmalmHyperlink(Path<? extends DmalmHyperlink> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_HYPERLINK");
    }

    public QDmalmHyperlink(PathMetadata<?> metadata) {
        super(DmalmHyperlink.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_HYPERLINK");
    }

}

