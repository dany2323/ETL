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

    public static final SireHistoryUser user = new SireHistoryUser("DM_ALM_H_SIRE_USER");

    public final StringPath cPk = createString("C_PK");
    
    public final StringPath cUri = createString("C_URI");
    
    public final NumberPath<Long> cRev = createNumber("C_REV", Long.class);

    public final StringPath cAvatarfilename = createString("C_AVATARFILENAME");

    public final StringPath cDeleted = createString("C_DELETED");

    public final NumberPath<Integer> cDisablednotifications = createNumber("C_DISABLEDNOTIFICATIONS",Integer.class);

    public final StringPath cEmail = createString("C_EMAIL");

    public final StringPath cId = createString("C_ID");

    public final StringPath cInitials = createString("C_INITIALS");

    public final StringPath cIsLocal = createString("C_IS_LOCAL");

    public final StringPath cName = createString("C_NAME");

    public SireHistoryUser(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryUser.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_USER");
    }

    public SireHistoryUser(Path<? extends lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryUser> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_USER");
    }

    public SireHistoryUser(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.sire.history.SireHistoryUser.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_H_SIRE_USER");
    }

}

