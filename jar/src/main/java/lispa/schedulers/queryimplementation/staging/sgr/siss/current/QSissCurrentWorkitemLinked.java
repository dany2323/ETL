package lispa.schedulers.queryimplementation.staging.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.current.SissCurrentWorkitemLinked;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissCurrentWorkitemLinked is a Querydsl query type for SissCurrentWorkitemLinked
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissCurrentWorkitemLinked extends com.mysema.query.sql.RelationalPathBase<SissCurrentWorkitemLinked> {

    private static final long serialVersionUID = 136920421;

    public static final QSissCurrentWorkitemLinked sissCurrentWorkitemLinked = new QSissCurrentWorkitemLinked("DMALM_SISS_CURRENT_WORK_LINKED");

    public final StringPath cRevision = createString("C_REVISION");

    public final StringPath cRole = createString("C_ROLE");

    public final NumberPath<Integer> cSuspect = createNumber("C_SUSPECT",Integer.class);

    public final StringPath fkPWorkitem = createString("FK_P_WORKITEM");

    public final StringPath fkUriPWorkitem = createString("FK_URI_P_WORKITEM");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");
    
    public final StringPath dmalmCurrentWorkitemLinkedPk = createString("SISS_CURRENT_WORK_LINKED_PK");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public QSissCurrentWorkitemLinked(String variable) {
        super(SissCurrentWorkitemLinked.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_WORK_LINKED");
    }

    public QSissCurrentWorkitemLinked(Path<? extends SissCurrentWorkitemLinked> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_WORK_LINKED");
    }

    public QSissCurrentWorkitemLinked(PathMetadata<?> metadata) {
        super(SissCurrentWorkitemLinked.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_WORK_LINKED");
    }

}

