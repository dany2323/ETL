package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import lispa.schedulers.bean.target.fatti.DmalmSchedeServizio;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

/**
 * QDmalmSchedeServizio is a Querydsl query type for DmalmPei
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmSchedeServizio extends
		com.mysema.query.sql.RelationalPathBase<DmalmSchedeServizio> {
	
	private static final long serialVersionUID = -8431712206738972013L;

	public static final QDmalmSchedeServizio dmalmSchedeServizio = new QDmalmSchedeServizio("DMALM_STG_SCHEDE_SERVIZIO");
	
	public final StringPath id = createString("ID");

	public final StringPath name = createString("NAME");
	
	public final NumberPath<Integer> dmalm_schedeServizio_Pk = createNumber(
			"DMALM_SCHEDE_SERVIZIO_PK", Integer.class);

	public final NumberPath<Integer> sorter = createNumber(
			"SORTER", Integer.class);
	
	public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	
	public final StringPath repository = createString("REPOSITORY");
	
	public final com.mysema.query.sql.PrimaryKey<DmalmSchedeServizio> sysC0022479 = createPrimaryKey(dmalm_schedeServizio_Pk);
	
	public QDmalmSchedeServizio(String variable) {
		super(DmalmSchedeServizio.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_STG_SCHEDE_SERVIZIO");
	}

	public QDmalmSchedeServizio(Path<? extends DmalmSchedeServizio> path) {
		super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_STG_SCHEDE_SERVIZIO");
	}
	
	public QDmalmSchedeServizio(PathMetadata<?> metadata) {
		super(DmalmSchedeServizio.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_STG_SCHEDE_SERVIZIO");
	}

}
