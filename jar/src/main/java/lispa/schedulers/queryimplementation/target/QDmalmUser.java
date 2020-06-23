package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmUser;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmUser is a Querydsl query type for DmalmUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmUser extends com.mysema.query.sql.RelationalPathBase<DmalmUser> {

    private static final long serialVersionUID = -1877976618;

    public static final QDmalmUser dmalmUser = new QDmalmUser("DMALM_USER");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final BooleanPath deleted = createBoolean("DELETED");

    public final BooleanPath disabledNotification = createBoolean("DISABLED_NOTIFICATION");

    public final NumberPath<Integer> dmalmUserPk = createNumber("DMALM_USER_PK", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final StringPath idUser = createString("ID_USER");

    public final StringPath initialsUser = createString("INITIALS_USER");

    public final StringPath userAvatarFilename = createString("USER_AVATAR_FILENAME");

    public final StringPath userEmail = createString("USER_EMAIL");

    public final StringPath userName = createString("USER_NAME");
   
    public final StringPath idRepository = createString("ID_REPOSITORY");

    public QDmalmUser(String variable) {
        super(DmalmUser.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_USER");
    }

    public QDmalmUser(Path<? extends DmalmUser> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_USER");
    }

    public QDmalmUser(PathMetadata<?> metadata) {
        super(DmalmUser.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_USER");
    }

}

