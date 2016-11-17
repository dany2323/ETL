package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmChangedWorkitem;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmChangedWorkitem is a Querydsl query type for DmalmChangedWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmChangedWorkitem extends com.mysema.query.sql.RelationalPathBase<DmalmChangedWorkitem> {

    private static final long serialVersionUID = 1924878734;

    public static final QDmalmChangedWorkitem dmalmChangedWorkitem = new QDmalmChangedWorkitem("DMALM_CHANGED_WORKITEM");

    public final StringPath codice = createString("CODICE");

    public final NumberPath<Integer> dmalmPk = createNumber("DMALM_PK", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath stgPk = createString("STG_PK");

    public final StringPath type = createString("TYPE");

    public QDmalmChangedWorkitem(String variable) {
        super(DmalmChangedWorkitem.class, forVariable(variable), "DMALM", "DMALM_CHANGED_WORKITEM");
    }

    public QDmalmChangedWorkitem(Path<? extends DmalmChangedWorkitem> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_CHANGED_WORKITEM");
    }

    public QDmalmChangedWorkitem(PathMetadata<?> metadata) {
        super(DmalmChangedWorkitem.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_CHANGED_WORKITEM");
    }

}

