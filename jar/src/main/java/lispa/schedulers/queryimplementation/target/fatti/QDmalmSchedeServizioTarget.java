package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import lispa.schedulers.bean.target.fatti.DmalmSchedeServizioTarget;
import lispa.schedulers.constant.DmAlmConstants;

public class QDmalmSchedeServizioTarget extends com.mysema.query.sql.RelationalPathBase<DmalmSchedeServizioTarget>{

	private static final long serialVersionUID = 2511913337557298588L;

	public static final QDmalmSchedeServizioTarget dmalmSchedeServizioTarget = new QDmalmSchedeServizioTarget("DMALM_SCHEDE_SERVIZIO");
	
	public final StringPath id = createString("ID");

	public final StringPath name = createString("NAME");
	
	public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	
	public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
	
	public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);
	
	public final NumberPath<Integer> dmalm_schedeServizio_Pk = createNumber(
			"DMALM_SCHEDE_SERVIZIO_PK", Integer.class);

	public final NumberPath<Integer> sorter = createNumber(
			"SORTER", Integer.class);
	
	public final StringPath repository = createString("REPOSITORY");
	
	public final com.mysema.query.sql.PrimaryKey<DmalmSchedeServizioTarget> sysC0022479 = createPrimaryKey(dmalm_schedeServizio_Pk);
	
	public QDmalmSchedeServizioTarget(String variable) {
		super(DmalmSchedeServizioTarget.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_SCHEDE_SERVIZIO");
	}

	public QDmalmSchedeServizioTarget(Path<? extends DmalmSchedeServizioTarget> path) {
		super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_SCHEDE_SERVIZIO");
	}
	
	public QDmalmSchedeServizioTarget(PathMetadata<?> metadata) {
		super(DmalmSchedeServizioTarget.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_SCHEDE_SERVIZIO");
	}
	
}
