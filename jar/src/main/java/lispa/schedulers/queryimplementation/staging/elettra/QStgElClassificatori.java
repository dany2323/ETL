package lispa.schedulers.queryimplementation.staging.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.staging.elettra.StgElClassificatori;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QStgElClassificatori extends com.mysema.query.sql.RelationalPathBase<StgElClassificatori> {
	private static final long serialVersionUID = -1020453833423604205L;

	public static final QStgElClassificatori stgElClassificatori = new QStgElClassificatori("DMALM_STG_EL_CLASSIFICATORI");
	
	public final NumberPath<Integer> classificatorePk = createNumber("DMALM_STG_CLASSIFICATORI_PK", Integer.class);
	public final StringPath idClassificatore = createString("ID_CLASSIFICATORE_ORESTE");
	public final StringPath codiceClassificatore = createString("CODICE_CLASSIFICATORE");
	public final StringPath tipoClassificatore = createString("TIPO_CLASSIFICATORE");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

	public QStgElClassificatori(String variable) {
        super(StgElClassificatori.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_CLASSIFICATORI");
    }

    public QStgElClassificatori(Path<? extends StgElClassificatori> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_CLASSIFICATORI");
    }

    public QStgElClassificatori(PathMetadata<?> metadata) {
        super(StgElClassificatori.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_CLASSIFICATORI");
    }
}
