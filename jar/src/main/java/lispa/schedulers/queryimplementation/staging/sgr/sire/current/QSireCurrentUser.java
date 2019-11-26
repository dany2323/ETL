package lispa.schedulers.queryimplementation.staging.sgr.sire.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.sire.current.SireCurrentUser;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireCurrentUser is a Querydsl query type for SireCurrentUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireCurrentUser extends com.mysema.query.sql.RelationalPathBase<SireCurrentUser> {

    private static final long serialVersionUID = 1420099443;
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public static final QSireCurrentUser sireCurrentUser = new QSireCurrentUser("DMALM_SIRE_CURRENT_USER");

    public final StringPath cAvatarfilename = createString("C_AVATARFILENAME");

    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final BooleanPath cDisablednotifications = createBoolean("C_DISABLEDNOTIFICATIONS");

    public final StringPath cEmail = createString("C_EMAIL");

    public final StringPath cId = createString("C_ID");

    public final StringPath cInitials = createString("C_INITIALS");

    public final BooleanPath cIsLocal = createBoolean("C_IS_LOCAL");

    public final StringPath cName = createString("C_NAME");

    public final StringPath cPk = createString("C_PK");
    
    public final StringPath dmalmCurrentUserPk = createString("SIRE_CURRENT_USER_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cUri = createString("C_URI");

    public QSireCurrentUser(String variable) {
        super(SireCurrentUser.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_USER");
    }

    public QSireCurrentUser(Path<? extends SireCurrentUser> path) {
        super(path.getType(), path.getMetadata(),DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_USER");
    }

    public QSireCurrentUser(PathMetadata<?> metadata) {
        super(SireCurrentUser.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_USER");
    }
}

