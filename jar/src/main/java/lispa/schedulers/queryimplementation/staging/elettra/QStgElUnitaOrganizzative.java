package lispa.schedulers.queryimplementation.staging.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import lispa.schedulers.bean.staging.elettra.StgElUnitaOrganizzative;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStgElUnitaOrganizzative extends com.mysema.query.sql.RelationalPathBase<StgElUnitaOrganizzative> {
	private static final long serialVersionUID = 3138848448597974077L;

	public static final QStgElUnitaOrganizzative stgElUnitaOrganizzative = new QStgElUnitaOrganizzative("DMALM_STG_EL_UNITA_ORGANIZZ");

	public final NumberPath<Integer> unitaOrganizzativaPk = createNumber("DMALM_STG_UNITA_ORG_PK", Integer.class);
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
	public final StringPath codiceEnte = createString("CD_ENTE");
	public final StringPath codiceVisibilita = createString("CD_VISIBILITA");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	
	public QStgElUnitaOrganizzative(String variable) {
        super(StgElUnitaOrganizzative.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_UNITA_ORGANIZZ");
    }

    public QStgElUnitaOrganizzative(Path<? extends StgElUnitaOrganizzative> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_UNITA_ORGANIZZ");
    }

    public QStgElUnitaOrganizzative(PathMetadata<?> metadata) {
        super(StgElUnitaOrganizzative.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_UNITA_ORGANIZZ");
    }
}
