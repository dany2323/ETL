package lispa.schedulers.queryimplementation.target.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzative;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmElUnitaOrganizzative extends com.mysema.query.sql.RelationalPathBase<DmalmElUnitaOrganizzative> {
	private static final long serialVersionUID = -59673784605893684L;

	public static final QDmalmElUnitaOrganizzative qDmalmElUnitaOrganizzative = new QDmalmElUnitaOrganizzative("DMALM_EL_UNITA_ORGANIZZATIVE");

	public final NumberPath<Integer> unitaOrganizzativaPk = createNumber("DMALM_UNITA_ORG_PK", Integer.class);
	public final StringPath idEdma = createString("ID_EDMA");
	public final StringPath codiceArea = createString("CD_AREA");
	public final DateTimePath<java.sql.Timestamp> dataInizioValiditaEdma = createDateTime("DT_INIZIO_VALIDITA_EDMA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValiditaEdma = createDateTime("DT_FINE_VALIDITA_EDMA", java.sql.Timestamp.class);
	public final StringPath descrizioneArea = createString("DS_AREA_EDMA");
	public final DateTimePath<java.sql.Timestamp> dataAttivazione = createDateTime("DT_ATTIVAZIONE", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataDisattivazione = createDateTime("DT_DISATTIVAZIONE", java.sql.Timestamp.class);
	public final StringPath note = createString("NOTE");
	public final NumberPath<Short> interno = createNumber("INTERNO", Short.class);
	public final StringPath codiceResponsabile = createString("CD_RESPONSABILE_AREA");
	public final StringPath indirizzoEmail = createString("INDIRIZZO_EMAIL");
	public final NumberPath<Integer> idTipologiaUfficio = createNumber("ID_TIPOLOGIA_UFFICIO", Integer.class);
	public final NumberPath<Integer> idGradoUfficio = createNumber("ID_GRADO_UFFICIO", Integer.class);
	public final NumberPath<Integer> idSede = createNumber("ID_SEDE", Integer.class);
	public final StringPath codiceUOSuperiore = createString("CD_UO_SUPERIORE");
	public final StringPath descrizioneUOSuperiore = createString("DS_UO_SUPERIORE");
	public final StringPath codiceEnte = createString("CD_ENTE");
	public final StringPath codiceVisibilita = createString("CD_VISIBILITA");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);
	public final StringPath annullato = createString("ANNULLATO");
	public final DateTimePath<java.sql.Timestamp> dataAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
    
    public final com.mysema.query.sql.PrimaryKey<DmalmElUnitaOrganizzative> dmalmUnitaOrganizzativaPk = createPrimaryKey(unitaOrganizzativaPk);

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto> _dmalmAnomaliaProdottoFk01 = createInvForeignKey(unitaOrganizzativaPk, "DMALM_STRUTTURA_ORG_FK_01");

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto> _dmalmDifettoProdottoFk01 = createInvForeignKey(unitaOrganizzativaPk, "DMALM_STRUTTURA_ORG_FK_01");

    public QDmalmElUnitaOrganizzative(String variable) {
        super(DmalmElUnitaOrganizzative.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_UNITA_ORGANIZZATIVE");
    }

    public QDmalmElUnitaOrganizzative(Path<? extends DmalmElUnitaOrganizzative> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_UNITA_ORGANIZZATIVE");
    }

    public QDmalmElUnitaOrganizzative(PathMetadata<?> metadata) {
        super(DmalmElUnitaOrganizzative.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_EL_UNITA_ORGANIZZATIVE");
    }

}
