package lispa.schedulers.queryimplementation.staging.sgr;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;
import lispa.schedulers.constant.DmAlmConstants;

public class DmAlmIdWorkitem extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.DmAlmIdWorkitem> {

	private static final long serialVersionUID = -173158280103527886L;

	public static final DmAlmIdWorkitem dmAlmIdWorkitem = new DmAlmIdWorkitem("DM_ALM_ID_WORKITEM");

    public final StringPath idWorkitem = createString("ID_WORKITEM");

    public final StringPath flagCaricamento = createString("FLAG_CARICAMENTO");

    public DmAlmIdWorkitem(String variable) {
        super(lispa.schedulers.bean.staging.sgr.DmAlmIdWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_ID_WORKITEM");
    }

    public DmAlmIdWorkitem(Path<? extends lispa.schedulers.bean.staging.sgr.DmAlmIdWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_ID_WORKITEM");
    }

    public DmAlmIdWorkitem(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.DmAlmIdWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_ID_WORKITEM");
    }
}
