package lispa.schedulers.queryimplementation.staging.sgr;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;
import lispa.schedulers.constant.DmAlmConstants;

public class DmAlmTemplateWorkitem extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.DmAlmTemplateWorkitem> {

	private static final long serialVersionUID = -173158280103527886L;

	public static final DmAlmTemplateWorkitem dmAlmTemplateWorkitem = new DmAlmTemplateWorkitem("DM_ALM_TEMPLATE_WORKITEM");

	public final StringPath template = createString("TEMPLATE");
	
    public final StringPath idWorkitem = createString("ID_WORKITEM");

    public final StringPath flagCaricamento = createString("FLAG_CARICAMENTO");

    public DmAlmTemplateWorkitem(String variable) {
        super(lispa.schedulers.bean.staging.sgr.DmAlmTemplateWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_TEMPLATE_WORKITEM");
    }

    public DmAlmTemplateWorkitem(Path<? extends lispa.schedulers.bean.staging.sgr.DmAlmTemplateWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_TEMPLATE_WORKITEM");
    }

    public DmAlmTemplateWorkitem(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.DmAlmTemplateWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_TEMPLATE_WORKITEM");
    }
}
