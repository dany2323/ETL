package lispa.schedulers.queryimplementation.staging.sgr.xml;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.constant.DmAlmConstants;
import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * DmalmWorkitemLinkRoles is a Querydsl query type for DmalmWorkitemLinkRoles
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class DmAlmTemplateProject extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.staging.sgr.xml.DmAlmTemplateProject> {

    private static final long serialVersionUID = 1178157647;

    public static final DmAlmTemplateProject dmAlmTemplateProject = new DmAlmTemplateProject("DM_ALM_TEMPLATE_PROJECT");

    public final StringPath pathLocation = createString("PATH_LOCATION");

    public final StringPath templateId = createString("TEMPLATE_ID");
    
    public final NumberPath<Long> rev = createNumber("REV", Long.class);
    
    public final StringPath idRepository = createString("ID_REPOSITORY");

    public DmAlmTemplateProject(String variable) {
        super(lispa.schedulers.bean.staging.sgr.xml.DmAlmTemplateProject.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_TEMPLATE_PROJECT");
    }

    public DmAlmTemplateProject(Path<? extends lispa.schedulers.bean.staging.sgr.xml.DmAlmTemplateProject> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_TEMPLATE_PROJECT");
    }

    public DmAlmTemplateProject(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.staging.sgr.xml.DmAlmTemplateProject.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_TEMPLATE_PROJECT");
    }

}

