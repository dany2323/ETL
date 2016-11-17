package lispa.schedulers.queryimplementation.target.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.elettra.DmalmElAmbienteTecnologicoClassificatori;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmElAmbienteTecnologicoClassificatori extends com.mysema.query.sql.RelationalPathBase<DmalmElAmbienteTecnologicoClassificatori> {

	private static final long serialVersionUID = 8197771405546480564L;

	public static final QDmalmElAmbienteTecnologicoClassificatori qDmalmElAmbienteTecnologicoClassificatori = new QDmalmElAmbienteTecnologicoClassificatori("DMALM_EL_AMBTECN_CLASSIF");

	public final NumberPath<Integer> ambienteTecnologicoClassificatoriPk = createNumber("DMALM_AMBTECN_CLASSIF_PK", Integer.class);
	public final StringPath idAmbienteTecnologico = createString("ID_AMB_TECN");
	public final StringPath nomeAmbienteTecnologico = createString("NOME_AMB_TECN");
	public final StringPath descrizioneAmbienteTecnologico = createString("DESCRIZIONE_AMB_TECN");
	public final StringPath idClassificatore = createString("ID_CLASSIFICATORE");
	public final StringPath nomeClassificatore = createString("NOME_CLASSIFICATORE");
	public final StringPath descrizioneClassificatore = createString("DESCR_CLASSIFICATORE");
	public final StringPath annullato = createString("ANNULLATO");
	public final DateTimePath<java.sql.Timestamp> dataAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<DmalmElAmbienteTecnologicoClassificatori> dmalmAmbienteTecnologicoClassificatoriPk = createPrimaryKey(ambienteTecnologicoClassificatoriPk);

    public QDmalmElAmbienteTecnologicoClassificatori(String variable) {
        super(DmalmElAmbienteTecnologicoClassificatori.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_AMBTECN_CLASSIF");
    }

    public QDmalmElAmbienteTecnologicoClassificatori(Path<? extends DmalmElAmbienteTecnologicoClassificatori> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_AMBTECN_CLASSIF");
    }

    public QDmalmElAmbienteTecnologicoClassificatori(PathMetadata<?> metadata) {
        super(DmalmElAmbienteTecnologicoClassificatori.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_AMBTECN_CLASSIF");
    }

	
}
