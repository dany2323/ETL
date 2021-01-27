package lispa.schedulers.queryimplementation.fonte.sgr.xml;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import javax.annotation.Generated;
import lispa.schedulers.constant.DmAlmConstants;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

/**
 * DmAlmSchedeServizio is a Querydsl query type for DmalmPei
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class DmAlmSchedeServizio extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.xml.DmAlmSchedeServizio> {
	
	private static final long serialVersionUID = -8431712206738972013L;

	public static final DmAlmSchedeServizio dmalmSchedeServizio = new DmAlmSchedeServizio("DM_ALM_SCHEDE_SERVIZIO");
	
	public final StringPath id = createString("ID");

	public final StringPath name = createString("NAME");
	
	public final NumberPath<Integer> sort_order = createNumber("SORT_ORDER", Integer.class);
	
	public final StringPath repository = createString("REPOSITORY");
	
	public DmAlmSchedeServizio(String variable) {
		super(lispa.schedulers.bean.fonte.sgr.xml.DmAlmSchedeServizio.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_SCHEDE_SERVIZIO");
	}

	public DmAlmSchedeServizio(Path<? extends lispa.schedulers.bean.fonte.sgr.xml.DmAlmSchedeServizio> path) {
		super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_SCHEDE_SERVIZIO");
	}
	
	public DmAlmSchedeServizio(PathMetadata<?> metadata) {
		super(lispa.schedulers.bean.fonte.sgr.xml.DmAlmSchedeServizio.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_SCHEDE_SERVIZIO");
	}

}
