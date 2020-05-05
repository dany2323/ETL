package lispa.schedulers.queryimplementation.fonte.sgr.sire.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireHistoryUser is a Querydsl query type for SireHistoryUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SireHistoryUser extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryUser> {

    private static final long serialVersionUID = 690289880;

    public static final SireHistoryUser user = new SireHistoryUser("T_USER");

    public final StringPath cAvatarfilename = createString("C_AVATARFILENAME");

    public final BooleanPath cDeleted = createBoolean("C_DELETED");

    public final BooleanPath cDisablednotifications = createBoolean("C_DISABLEDNOTIFICATIONS");

    public final StringPath cEmail = createString("C_EMAIL");

    public final StringPath cId = createString("C_ID");

    public final StringPath cInitials = createString("C_INITIALS");

    public final StringPath cName = createString("C_NAME");

    public final StringPath cPk = createString("C_PK");

    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cUri = createString("C_URI");

    public SireHistoryUser(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryUser.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "T_USER");
    }

    public SireHistoryUser(Path<? extends lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryUser> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "T_USER");
    }

    public SireHistoryUser(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryUser.class, metadata, DmAlmConstants.POLARION_SCHEMA, "T_USER");
    }

}

