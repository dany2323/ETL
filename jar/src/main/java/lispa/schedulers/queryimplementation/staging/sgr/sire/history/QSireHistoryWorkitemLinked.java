package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.sire.history.SireHistoryStructWorkitemLinkedworkitems;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireHistoryWorkitemLinked is a Querydsl query type for SireHistoryWorkitemLinked
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireHistoryWorkitemLinked extends com.mysema.query.sql.RelationalPathBase<SireHistoryStructWorkitemLinkedworkitems> {

    private static final long serialVersionUID = 522453435;
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public static final QSireHistoryWorkitemLinked sireHistoryWorkitemLinked = new QSireHistoryWorkitemLinked("DMALM_SIRE_HISTORY_WORK_LINKED");

    public final StringPath cRevision = createString("C_REVISION");

    public final StringPath cRole = createString("C_ROLE");

//    public final BooleanPath cSuspect = createBoolean("C_SUSPECT");
    public final NumberPath<Integer> sSuspect = createNumber("C_SUSPECT", Integer.class);

    public final StringPath fkPWorkitem = createString("FK_P_WORKITEM");

    public final StringPath fkUriPWorkitem = createString("FK_URI_P_WORKITEM");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");
    
    public final StringPath sireHistoryWorkLinkedPk = createString("SIRE_HISTORY_WORK_LINKED_PK");

    public QSireHistoryWorkitemLinked(String variable) {
        super(SireHistoryStructWorkitemLinkedworkitems.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_WORK_LINKED");
    }

    public QSireHistoryWorkitemLinked(Path<? extends SireHistoryStructWorkitemLinkedworkitems> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_WORK_LINKED");
    }

    public QSireHistoryWorkitemLinked(PathMetadata<?> metadata) {
        super(SireHistoryStructWorkitemLinkedworkitems.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_WORK_LINKED");
    }

}

