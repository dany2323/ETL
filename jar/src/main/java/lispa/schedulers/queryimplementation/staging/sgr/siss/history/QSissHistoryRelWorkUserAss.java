package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryRelWorkUserAss;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissHistoryRelWorkUserAss is a Querydsl query type for SissHistoryRelWorkUserAss
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissHistoryRelWorkUserAss extends com.mysema.query.sql.RelationalPathBase<SissHistoryRelWorkUserAss> {

    private static final long serialVersionUID = -1686398006;

    public static final QSissHistoryRelWorkUserAss sissHistoryRelWorkUserAss = new QSissHistoryRelWorkUserAss("DMALM_SISS_HISTORY_WORKUSERASS");

    public final StringPath fkUriUser = createString("FK_URI_USER");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkUser = createString("FK_USER");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath dmalmWorkUserAssPk = createString("SISS_HISTORY_WORKUSERASS_PK");
    
    public QSissHistoryRelWorkUserAss(String variable) {
        super(SissHistoryRelWorkUserAss.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_WORKUSERASS");
    }

    public QSissHistoryRelWorkUserAss(Path<? extends SissHistoryRelWorkUserAss> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_WORKUSERASS");
    }

    public QSissHistoryRelWorkUserAss(PathMetadata<?> metadata) {
        super(SissHistoryRelWorkUserAss.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_WORKUSERASS");
    }

}

