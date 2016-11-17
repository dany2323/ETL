package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryUser;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissHistoryUser is a Querydsl query type for SissHistoryUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissHistoryUser extends com.mysema.query.sql.RelationalPathBase<SissHistoryUser> {

    private static final long serialVersionUID = -1957540599;

    public static final QSissHistoryUser sissHistoryUser = new QSissHistoryUser("DMALM_SISS_HISTORY_USER");

    public final StringPath cAvatarfilename = createString("C_AVATARFILENAME");

//    public final BooleanPath cDeleted = createBoolean("C_DELETED");
    public final NumberPath<Integer> cDeleted = createNumber("C_DELETED", Integer.class);

//    public final BooleanPath cDisablednotifications = createBoolean("C_DISABLEDNOTIFICATIONS");
    public final NumberPath<Integer> cDisablednotifications = createNumber("C_DISABLEDNOTIFICATIONS", Integer.class);

    public final StringPath cEmail = createString("C_EMAIL");

    public final StringPath cId = createString("C_ID");

    public final StringPath cInitials = createString("C_INITIALS");

//    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");
    public final NumberPath<Integer> cIsLocal = createNumber("C_IS_LOCAL", Integer.class);

    public final StringPath cName = createString("C_NAME");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cUri = createString("C_URI");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath dmalmUserPk = createString("SISS_HISTORY_USER_PK");
    
    public QSissHistoryUser(String variable) {
        super(SissHistoryUser.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_USER");
    }

    public QSissHistoryUser(Path<? extends SissHistoryUser> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_USER");
    }

    public QSissHistoryUser(PathMetadata<?> metadata) {
        super(SissHistoryUser.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_USER");
    }

}

