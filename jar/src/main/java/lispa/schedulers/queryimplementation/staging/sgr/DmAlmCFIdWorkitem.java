package lispa.schedulers.queryimplementation.staging.sgr;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;
import lispa.schedulers.constant.DmAlmConstants;

public class DmAlmCFIdWorkitem extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.DmAlmCFIdWorkitem> {

	private static final long serialVersionUID = -173158280103527886L;

	public static final DmAlmCFIdWorkitem dmAlmCFIdWorkitem = new DmAlmCFIdWorkitem("DM_ALM_CF_ID_WORKITEM");

    public final StringPath idWorkitem = createString("ID_WORKITEM");

    public final StringPath customField = createString("CUSTOM_FIELD");
    
    public final StringPath flagCaricamento = createString("FLAG_CARICAMENTO");

    public DmAlmCFIdWorkitem(String variable) {
        super(lispa.schedulers.bean.staging.sgr.DmAlmCFIdWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_CF_ID_WORKITEM");
    }

    public DmAlmCFIdWorkitem(Path<? extends lispa.schedulers.bean.staging.sgr.DmAlmCFIdWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_CF_ID_WORKITEM");
    }

    public DmAlmCFIdWorkitem(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.DmAlmCFIdWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_CF_ID_WORKITEM");
    }
}
