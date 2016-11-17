package lispa.schedulers.queryimplementation.fonte.edma;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * EdmaDmAlmPersonale is a Querydsl query type for EdmaDmAlmPersonale
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class EdmaDmAlmPersonale extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.edma.EdmaDmAlmPersonale> {

    private static final long serialVersionUID = -698752813;

    public static final EdmaDmAlmPersonale dmAlmPersonale = new EdmaDmAlmPersonale("V_DM_ALM_PERSONALE");

    public final StringPath codEnte = createString("COD_ENTE");

    public final StringPath codResponsabile = createString("COD_RESPONSABILE");

    public final StringPath codSuperiore = createString("COD_SUPERIORE");

    public final StringPath codVisibilita = createString("COD_VISIBILITA");

    public final StringPath codice = createString("CODICE");

    public final StringPath codiceFiscale = createString("CODICE_FISCALE");

    public final StringPath cognome = createString("COGNOME");

    public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DATA_INIZIO_VALIDITA", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DATA_FINE_VALIDITA", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dataDisattivazione = createDateTime("DATA_DISATTIVAZIONE", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dataAttivazione = createDateTime("DATA_ATTIVAZIONE", java.sql.Timestamp.class);

    public final StringPath id = createString("ID");

    public final StringPath idGradoUfficio = createString("ID_GRADO_UFFICIO");

    public final StringPath idSede = createString("ID_SEDE");

    public final StringPath identficatore = createString("IDENTFICATORE");

    public final StringPath indirizzoEmail = createString("INDIRIZZO_EMAIL");

    public final StringPath interno = createString("INTERNO");

    public final StringPath matricola = createString("MATRICOLA");

    public final StringPath nome = createString("NOME");

    public final StringPath note = createString("NOTE");

    public final com.mysema.query.sql.PrimaryKey<lispa.schedulers.bean.fonte.edma.EdmaDmAlmPersonale> fontePersonaleEdmaPk = createPrimaryKey(id);

    public EdmaDmAlmPersonale(String variable) {
        super(lispa.schedulers.bean.fonte.edma.EdmaDmAlmPersonale.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_PERSONALE");
    }

    public EdmaDmAlmPersonale(Path<? extends lispa.schedulers.bean.fonte.edma.EdmaDmAlmPersonale> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_PERSONALE");
    }

    public EdmaDmAlmPersonale(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.edma.EdmaDmAlmPersonale.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_PERSONALE");
    }

}

