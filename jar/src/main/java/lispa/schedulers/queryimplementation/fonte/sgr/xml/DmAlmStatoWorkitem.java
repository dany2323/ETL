package lispa.schedulers.queryimplementation.fonte.sgr.xml;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * DmAlmStatoWorkitem is a Querydsl query type for DmAlmStatoWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class DmAlmStatoWorkitem extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.xml.DmAlmStatoWorkitem> {

    private static final long serialVersionUID = 1545915439;

    public static final DmAlmStatoWorkitem dmAlmStatoWorkitem = new DmAlmStatoWorkitem("DM_ALM_STATO_WORKITEM_SVN");

    public final StringPath descrizione = createString("DESCRIZIONE");

    public final StringPath iconUrl = createString("ICON_URL");
    
    public final StringPath repository = createString("REPOSITORY");
    
    public final StringPath workitemType = createString("WORKITEM_TYPE");

    public final StringPath id = createString("ID");

    public final StringPath name = createString("NAME");
    
    public final StringPath template = createString("TEMPLATE");

    public final NumberPath<Double> sortOrder = createNumber("SORT_ORDER", Double.class);

    public DmAlmStatoWorkitem(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.xml.DmAlmStatoWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_STATO_WORKITEM_SVN");
    }

    public DmAlmStatoWorkitem(Path<? extends lispa.schedulers.bean.fonte.sgr.xml.DmAlmStatoWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_STATO_WORKITEM_SVN");
    }

    public DmAlmStatoWorkitem(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.xml.DmAlmStatoWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_STATO_WORKITEM_SVN");
    }

}

