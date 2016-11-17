package lispa.schedulers.queryimplementation.staging.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.staging.elettra.StgElAmbienteTecnologicoClassificatori;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QStgElAmbienteTecnologicoClassificatori extends com.mysema.query.sql.RelationalPathBase<StgElAmbienteTecnologicoClassificatori> {
	private static final long serialVersionUID = -3541819181606775603L;

	public static final QStgElAmbienteTecnologicoClassificatori stgElAmbienteTecnologicoClassificatori = new QStgElAmbienteTecnologicoClassificatori("DMALM_STG_EL_AMBTECN_CLASSIF");
	
	public final NumberPath<Integer> ambienteTecnologicoClassificatoriPk = createNumber("DMALM_STG_CLASSIFICATORI_PK", Integer.class);
	public final StringPath idAmbienteTecnologico = createString("ID_AMB_TECN");
	public final StringPath nomeAmbienteTecnologico = createString("NOME_AMB_TECN");
	public final StringPath descrizioneAmbienteTecnologico = createString("DESCRIZIONE_AMB_TECN");
	public final StringPath idClassificatore = createString("ID_CLASSIFICATORE");
	public final StringPath nomeClassificatore = createString("NOME_CLASSIFICATORE");
	public final StringPath descrizioneClassificatore = createString("DESCR_CLASSIFICATORE");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	
	public QStgElAmbienteTecnologicoClassificatori(String variable) {
        super(StgElAmbienteTecnologicoClassificatori.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_AMBTECN_CLASSIF");
    }

    public QStgElAmbienteTecnologicoClassificatori(Path<? extends StgElAmbienteTecnologicoClassificatori> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_AMBTECN_CLASSIF");
    }

    public QStgElAmbienteTecnologicoClassificatori(PathMetadata<?> metadata) {
        super(StgElAmbienteTecnologicoClassificatori.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_AMBTECN_CLASSIF");
    }
}
