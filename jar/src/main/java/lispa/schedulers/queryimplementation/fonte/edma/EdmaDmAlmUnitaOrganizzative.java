package lispa.schedulers.queryimplementation.fonte.edma;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * EdmaDmAlmUnitaOrganizzative is a Querydsl query type for EdmaDmAlmUnitaOrganizzative
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class EdmaDmAlmUnitaOrganizzative extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.edma.EdmaDmAlmUnitaOrganizzative> {

    private static final long serialVersionUID = -616633918;

    public static final EdmaDmAlmUnitaOrganizzative dmAlmUnitaOrganizzative = new EdmaDmAlmUnitaOrganizzative("V_DM_ALM_UNITA_ORGANIZZATIVE");

    public final StringPath codEnte = createString("COD_ENTE");

    public final StringPath codResponsabile = createString("COD_RESPONSABILE");

    public final StringPath codSuperiore = createString("COD_SUPERIORE");

    public final StringPath codVisibilita = createString("COD_VISIBILITA");

    public final StringPath codice = createString("CODICE");

    public final StringPath dataAttivazione = createString("DATA_ATTIVAZIONE");

    public final StringPath dataDisattivazione = createString("DATA_DISATTIVAZIONE");

    public final StringPath dataFineValidita = createString("DATA_FINE_VALIDITA");

    public final StringPath dataInizioValidita = createString("DATA_INIZIO_VALIDITA");

    public final StringPath descrizione = createString("DESCRIZIONE");

    public final StringPath id = createString("ID");

    public final NumberPath<Integer> idGradoUfficio = createNumber("ID_GRADO_UFFICIO", Integer.class);

    public final StringPath idSede = createString("ID_SEDE");

    public final NumberPath<Integer> idTipologiaUfficio = createNumber("ID_TIPOLOGIA_UFFICIO", Integer.class);

    public final StringPath indirizzoEmail = createString("INDIRIZZO_EMAIL");

    public final NumberPath<Integer> interno = createNumber("INTERNO", Integer.class);

    public final StringPath note = createString("NOTE");

   // public final StringPath tipoPersona = createString("TIPO_PERSONA");

    public EdmaDmAlmUnitaOrganizzative(String variable) {
        super(lispa.schedulers.bean.fonte.edma.EdmaDmAlmUnitaOrganizzative.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_UNITA_ORGANIZZATIVE");
    }

    public EdmaDmAlmUnitaOrganizzative(Path<? extends lispa.schedulers.bean.fonte.edma.EdmaDmAlmUnitaOrganizzative> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_UNITA_ORGANIZZATIVE");
    }

    public EdmaDmAlmUnitaOrganizzative(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.edma.EdmaDmAlmUnitaOrganizzative.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_UNITA_ORGANIZZATIVE");
    }

}

