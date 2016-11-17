package lispa.schedulers.queryimplementation.staging.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.current.SissCurrentUser;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissCurrentUser is a Querydsl query type for SissCurrentUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissCurrentUser extends com.mysema.query.sql.RelationalPathBase<SissCurrentUser> {

    private static final long serialVersionUID = -346252717;

    public static final QSissCurrentUser sissCurrentUser = new QSissCurrentUser("DMALM_SISS_CURRENT_USER");

    public final StringPath cAvatarfilename = createString("C_AVATARFILENAME");

    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final BooleanPath cDisablednotifications = createBoolean("C_DISABLEDNOTIFICATIONS");

    public final StringPath cEmail = createString("C_EMAIL");

    public final StringPath cId = createString("C_ID");

    public final StringPath cInitials = createString("C_INITIALS");

    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");

    public final StringPath cName = createString("C_NAME");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cUri = createString("C_URI");
    
    public final StringPath dmalmCurrentUserPk = createString("SISS_CURRENT_USER_PK");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public QSissCurrentUser(String variable) {
        super(SissCurrentUser.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_USER");
    }

    public QSissCurrentUser(Path<? extends SissCurrentUser> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_USER");
    }

    public QSissCurrentUser(PathMetadata<?> metadata) {
        super(SissCurrentUser.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_USER");
    }

}

