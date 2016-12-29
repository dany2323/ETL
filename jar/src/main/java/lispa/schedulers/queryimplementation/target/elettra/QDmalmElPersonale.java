package lispa.schedulers.queryimplementation.target.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.elettra.DmalmElPersonale;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmElPersonale extends com.mysema.query.sql.RelationalPathBase<DmalmElPersonale> {

	private static final long serialVersionUID = 9186342063449348164L;

	public static final QDmalmElPersonale qDmalmElPersonale = new QDmalmElPersonale("DMALM_EL_PERSONALE");

	public final NumberPath<Integer> personalePk = createNumber("DMALM_PERSONALE_PK", Integer.class);
	public final StringPath idEdma = createString("ID_EDMA");
	public final StringPath codicePersonale = createString("CD_PERSONALE");
	public final DateTimePath<java.sql.Timestamp> dataInizioValiditaEdma = createDateTime("DT_INIZIO_VALIDITA_EDMA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValiditaEdma = createDateTime("DT_FINE_VALIDITA_EDMA", java.sql.Timestamp.class);
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
	public final StringPath cdSuperiore = createString("CD_SUPERIORE");
	public final StringPath cdEnte = createString("CD_ENTE");
	public final StringPath cdVisibilita = createString("CD_VISIBILITA");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);
	public final StringPath annullato = createString("ANNULLATO");
	public final DateTimePath<java.sql.Timestamp> dataAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);

	   //Aggiunte per DM_ALM-237
    public final NumberPath<Integer> unitaOrganizzativaFk = createNumber("DMALM_UNITAORGANIZZATIVA_FK_01", Integer.class);
    public final NumberPath<Integer> unitaOrganizzativaFlatFk = createNumber("DMALM_UNITAORGANIZZ_FLAT_FK_02", Integer.class);

    
    public QDmalmElPersonale(String variable) {
        super(DmalmElPersonale.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_PERSONALE");
    }

    public QDmalmElPersonale(Path<? extends DmalmElPersonale> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_PERSONALE");
    }

    public QDmalmElPersonale(PathMetadata<?> metadata) {
        super(DmalmElPersonale.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_PERSONALE");
    }


}
