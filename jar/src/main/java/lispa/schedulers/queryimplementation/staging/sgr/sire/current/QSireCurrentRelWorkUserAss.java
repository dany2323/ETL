package lispa.schedulers.queryimplementation.staging.sgr.sire.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.sire.current.SireCurrentRelWorkUserAss;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSireCurrentRelWorkUserAss is a Querydsl query type for SireCurrentRelWorkUserAss
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSireCurrentRelWorkUserAss extends com.mysema.query.sql.RelationalPathBase<SireCurrentRelWorkUserAss> {

    private static final long serialVersionUID = 290050484;
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public static final QSireCurrentRelWorkUserAss sireCurrentRelWorkUserAss = new QSireCurrentRelWorkUserAss("DMALM_SIRE_CURRENT_WORKUSERASS");

    public final StringPath fkUriUser = createString("FK_URI_USER");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkUser = createString("FK_USER");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");
    
    public final StringPath dmalmUserAssignedPk = createString("WORKITEM_USER_ASSIGNED_PK");

    public QSireCurrentRelWorkUserAss(String variable) {
        super(SireCurrentRelWorkUserAss.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_WORKUSERASS");
    }

    public QSireCurrentRelWorkUserAss(Path<? extends SireCurrentRelWorkUserAss> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_WORKUSERASS");
    }

    public QSireCurrentRelWorkUserAss(PathMetadata<?> metadata) {
        super(SireCurrentRelWorkUserAss.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SIRE_CURRENT_WORKUSERASS");
    }

}

