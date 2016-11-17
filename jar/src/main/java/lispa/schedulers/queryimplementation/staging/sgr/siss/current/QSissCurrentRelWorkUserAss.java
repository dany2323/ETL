package lispa.schedulers.queryimplementation.staging.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.current.SissCurrentRelWorkUserAss;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissCurrentRelWorkUserAss is a Querydsl query type for SissCurrentRelWorkUserAss
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissCurrentRelWorkUserAss extends com.mysema.query.sql.RelationalPathBase<SissCurrentRelWorkUserAss> {

    private static final long serialVersionUID = -890940268;

    public static final QSissCurrentRelWorkUserAss sissCurrentRelWorkUserAss = new QSissCurrentRelWorkUserAss("DMALM_SISS_CURRENT_WORKUSERASS");

    public final StringPath fkUriUser = createString("FK_URI_USER");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkUser = createString("FK_USER");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");
    
    public final StringPath dmalmCurrentWorkuserassPk = createString("SISS_CURRENT_WORKUSERASS_PK");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public QSissCurrentRelWorkUserAss(String variable) {
        super(SissCurrentRelWorkUserAss.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_WORKUSERASS");
    }

    public QSissCurrentRelWorkUserAss(Path<? extends SissCurrentRelWorkUserAss> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_WORKUSERASS");
    }

    public QSissCurrentRelWorkUserAss(PathMetadata<?> metadata) {
        super(SissCurrentRelWorkUserAss.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_WORKUSERASS");
    }

}

