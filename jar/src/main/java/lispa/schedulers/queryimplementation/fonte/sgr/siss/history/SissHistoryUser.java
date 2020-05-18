package lispa.schedulers.queryimplementation.fonte.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryUser;

import com.mysema.query.types.Path;


/**
 * SissCurrentUser is a Querydsl query type for SissHistoryUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SissHistoryUser extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryUser> {

    private static final long serialVersionUID = -1076062280;

    public static final SissHistoryUser user = new SissHistoryUser("DM_ALM_H_SISS_USER");

    public final StringPath cPk = createString("C_PK");
    
    public final StringPath cUri = createString("C_URI");
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cAvatarfilename = createString("C_AVATARFILENAME");

    public final StringPath cDeleted = createString("C_DELETED");

    public final StringPath cDisablednotifications = createString("C_DISABLEDNOTIFICATIONS");

    public final StringPath cEmail = createString("C_EMAIL");

    public final StringPath cId = createString("C_ID");

    public final StringPath cInitials = createString("C_INITIALS");

    public final StringPath cIsLocal = createString("C_IS_LOCAL");

    public final StringPath cName = createString("C_NAME");

    public SissHistoryUser(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryUser.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_USER");
    }

    public SissHistoryUser(Path<? extends lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryUser> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_USER");
    }

    public SissHistoryUser(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.siss.history.SissHistoryUser.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SISS_USER");
    }

}

