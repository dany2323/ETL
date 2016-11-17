package lispa.schedulers.queryimplementation.staging.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.staging.elettra.StgElPersonale;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QStgElPersonale extends com.mysema.query.sql.RelationalPathBase<StgElPersonale> {
	private static final long serialVersionUID = 5292435143005538882L;

	public static final QStgElPersonale stgElPersonale = new QStgElPersonale("DMALM_STG_EL_PERSONALE");

	public final NumberPath<Integer> personalePk = createNumber("DMALM_STG_PERSONALE_PK", Integer.class);
	public final StringPath idEdma = createString("ID_EDMA");
	public final StringPath codicePersonale = createString("CD_PERSONALE");
	public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DT_INIZIO_VALIDITA_EDMA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DT_FINE_VALIDITA_EDMA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataAttivazione = createDateTime("DT_ATTIVAZIONE", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataDisattivazione = createDateTime("DT_DISATTIVAZIONE", java.sql.Timestamp.class);
	public final StringPath note = createString("NOTE");
	public final NumberPath<Short> interno = createNumber("INTERNO", Short.class);
	public final StringPath codiceResponsabile = createString("CD_RESPONSABILE");
	public final StringPath indirizzoEmail = createString("INDIRIZZO_EMAIL");
	public final StringPath nome = createString("NOME");
	public final StringPath cognome = createString("COGNOME");
	public final StringPath matricola = createString("MATRICOLA");
	public final StringPath codiceFiscale = createString("CODICE_FISCALE");
	public final StringPath identificatore = createString("IDENTIFICATORE");
	public final NumberPath<Integer> idGrado = createNumber("ID_GRADO", Integer.class);
	public final NumberPath<Integer> idSede = createNumber("ID_SEDE", Integer.class);
	public final StringPath codiceUOSuperiore = createString("CD_SUPERIORE");
	public final StringPath codiceEnte = createString("CD_ENTE");
	public final StringPath codiceVisibilita = createString("CD_VISIBILITA");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	
	public QStgElPersonale(String variable) {
        super(StgElPersonale.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_PERSONALE");
    }

    public QStgElPersonale(Path<? extends StgElPersonale> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_PERSONALE");
    }

    public QStgElPersonale(PathMetadata<?> metadata) {
        super(StgElPersonale.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_PERSONALE");
    }
}
