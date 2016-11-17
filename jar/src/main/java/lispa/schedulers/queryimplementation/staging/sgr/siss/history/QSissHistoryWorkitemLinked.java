package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryWorkitemLinked;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissHistoryWorkitemLinked is a Querydsl query type for SissHistoryWorkitemLinked
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissHistoryWorkitemLinked extends com.mysema.query.sql.RelationalPathBase<SissHistoryWorkitemLinked> {

    private static final long serialVersionUID = -658537317;

    public static final QSissHistoryWorkitemLinked sissHistoryWorkitemLinked = new QSissHistoryWorkitemLinked("DMALM_SISS_HISTORY_WORK_LINKED");

    public final StringPath cRevision = createString("C_REVISION");

    public final StringPath cRole = createString("C_ROLE");

//    public final BooleanPath cSuspect = createBoolean("C_SUSPECT");
    public final NumberPath<Integer> cSuspect = createNumber("C_SUSPECT", Integer.class);

    public final StringPath fkPWorkitem = createString("FK_P_WORKITEM");

    public final StringPath fkUriPWorkitem = createString("FK_URI_P_WORKITEM");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");
    
    public final StringPath dmalmWorkLinkedPk = createString("SISS_HISTORY_WORK_LINKED_PK");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public QSissHistoryWorkitemLinked(String variable) {
        super(SissHistoryWorkitemLinked.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_WORK_LINKED");
    }

    public QSissHistoryWorkitemLinked(Path<? extends SissHistoryWorkitemLinked> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_WORK_LINKED");
    }

    public QSissHistoryWorkitemLinked(PathMetadata<?> metadata) {
        super(SissHistoryWorkitemLinked.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_WORK_LINKED");
    }

}

