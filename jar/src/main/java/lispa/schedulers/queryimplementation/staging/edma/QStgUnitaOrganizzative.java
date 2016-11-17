package lispa.schedulers.queryimplementation.staging.edma;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.edma.StgUnitaOrganizzative;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QStgUnitaOrganizzative is a Querydsl query type for StgUnitaOrganizzative
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStgUnitaOrganizzative extends com.mysema.query.sql.RelationalPathBase<StgUnitaOrganizzative> {

    private static final long serialVersionUID = -469731811;

    public static final QStgUnitaOrganizzative stgUnitaOrganizzative = new QStgUnitaOrganizzative("DMALM_STG_UNITA_ORGANIZZATIVE");

    public final StringPath codEnte = createString("COD_ENTE");

    public final StringPath codResponsabile = createString("COD_RESPONSABILE");

    public final StringPath codSuperiore = createString("COD_SUPERIORE");

    public final StringPath codVisibilita = createString("COD_VISIBILITA");

    public final StringPath codice = createString("CODICE");

    public final StringPath dataAttivazione = createString("DATA_ATTIVAZIONE");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath dataDisattivazione = createString("DATA_DISATTIVAZIONE");

    public final StringPath dataFineValidita = createString("DATA_FINE_VALIDITA");

    public final StringPath dataInizioValidita = createString("DATA_INIZIO_VALIDITA");

    public final StringPath descrizione = createString("DESCRIZIONE");

    public final StringPath id = createString("ID");

    public final StringPath idGradoUfficio = createString("ID_GRADO_UFFICIO");

    public final StringPath idSede = createString("ID_SEDE");

    public final StringPath idTipologiaUfficio = createString("ID_TIPOLOGIA_UFFICIO");

    public final StringPath indirizzoEmail = createString("INDIRIZZO_EMAIL");

    public final StringPath interno = createString("INTERNO");

    public final StringPath note = createString("NOTE");

    public final StringPath tipoPersona = createString("TIPO_PERSONA");
    
    public final StringPath dmalmStgUnitaOrgPk = createString("DMALM_STG_UNITA_ORG_PK");

    public QStgUnitaOrganizzative(String variable) {
        super(StgUnitaOrganizzative.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_UNITA_ORGANIZZATIVE");
    }

    public QStgUnitaOrganizzative(Path<? extends StgUnitaOrganizzative> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_UNITA_ORGANIZZATIVE");
    }

    public QStgUnitaOrganizzative(PathMetadata<?> metadata) {
        super(StgUnitaOrganizzative.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_UNITA_ORGANIZZATIVE");
    }

}

