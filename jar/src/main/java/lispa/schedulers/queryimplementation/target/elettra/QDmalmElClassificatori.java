package lispa.schedulers.queryimplementation.target.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.elettra.DmalmElClassificatori;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmElClassificatori extends
		com.mysema.query.sql.RelationalPathBase<DmalmElClassificatori> {
	private static final long serialVersionUID = -63588926516312345L;

	public static final QDmalmElClassificatori qDmalmElClassificatori = new QDmalmElClassificatori(
			"DMALM_EL_CLASSIFICATORI");

	public final NumberPath<Integer> classificatorePk = createNumber("DMALM_CLASSIFICATORI_PK", Integer.class);
	public final StringPath idClassificatore = createString("ID_CLASSIFICATORE_ORESTE");
	public final StringPath codiceClassificatore = createString("CODICE_CLASSIFICATORE");
	public final StringPath tipoClassificatore = createString("TIPO_CLASSIFICATORE");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

	public final com.mysema.query.sql.PrimaryKey<DmalmElClassificatori> dmalmClassificatorePk = createPrimaryKey(classificatorePk);

	public QDmalmElClassificatori(String variable) {
		super(DmalmElClassificatori.class, forVariable(variable),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_CLASSIFICATORI");
	}

	public QDmalmElClassificatori(Path<? extends DmalmElClassificatori> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_CLASSIFICATORI");
	}

	public QDmalmElClassificatori(PathMetadata<?> metadata) {
		super(DmalmElClassificatori.class, metadata,
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_CLASSIFICATORI");
	}

}
