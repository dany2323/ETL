package lispa.schedulers.queryimplementation.staging.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.sire.history.SireHistoryRelWorkitemUserAssignee;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireHistoryRelWorkUserAss is a Querydsl query type for SireHistoryRelWorkUserAss
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireHistoryRelWorkUserAss extends com.mysema.query.sql.RelationalPathBase<SireHistoryRelWorkitemUserAssignee> {

    private static final long serialVersionUID = -505407254;
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public static final QSireHistoryRelWorkUserAss sireHistoryRelWorkUserAss = new QSireHistoryRelWorkUserAss("DMALM_SIRE_HISTORY_WORKUSERASS");

    public final StringPath fkUriUser = createString("FK_URI_USER");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkUser = createString("FK_USER");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");
    
    public final StringPath workitemUserAssignedPK = createString("WORKITEM_USER_ASSIGNED_PK");

    public QSireHistoryRelWorkUserAss(String variable) {
        super(SireHistoryRelWorkitemUserAssignee.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_WORKUSERASS");
    }

    public QSireHistoryRelWorkUserAss(Path<? extends SireHistoryRelWorkitemUserAssignee> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_HISTORY_WORKUSERASS");
    }

    public QSireHistoryRelWorkUserAss(PathMetadata<?> metadata) {
        super(SireHistoryRelWorkitemUserAssignee.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA , "DMALM_SIRE_HISTORY_WORKUSERASS");
    }

}

