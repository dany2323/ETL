package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmPersonale;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmPersonale is a Querydsl query type for DmalmPersonale
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmPersonale extends com.mysema.query.sql.RelationalPathBase<DmalmPersonale> {

    private static final long serialVersionUID = 1786462970;

    public static final QDmalmPersonale dmalmPersonale = new QDmalmPersonale("DMALM_PERSONALE");

    public final StringPath cdEnte = createString("CD_ENTE");

    public final StringPath cdPersonale = createString("CD_PERSONALE");

    public final StringPath cdResponsabile = createString("CD_RESPONSABILE");

    public final StringPath cdSuperiore = createString("CD_SUPERIORE");

    public final StringPath cdVisibilita = createString("CD_VISIBILITA");

    public final StringPath codiceFiscale = createString("CODICE_FISCALE");

    public final StringPath cognome = createString("COGNOME");

    public final NumberPath<Integer> dmalmPersonalePk = createNumber("DMALM_PERSONALE_PK", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dtAttivazione = createDateTime("DT_ATTIVAZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtDisattivazione = createDateTime("DT_DISATTIVAZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dtFineValiditaEdma = createDateTime("DT_FINE_VALIDITA_EDMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValiditaEdma = createDateTime("DT_INIZIO_VALIDITA_EDMA", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final NumberPath<Integer> idEdma = createNumber("ID_EDMA", Integer.class);

    public final NumberPath<Integer> idGrado = createNumber("ID_GRADO", Integer.class);

    public final NumberPath<Integer> idSede = createNumber("ID_SEDE", Integer.class);

    public final StringPath identificatore = createString("IDENTIFICATORE");

    public final StringPath indirizzoEmail = createString("INDIRIZZO_EMAIL");
    
    public final StringPath annullato = createString("ANNULLATO");

    public final NumberPath<Integer> interno = createNumber("INTERNO", Integer.class);

    public final StringPath matricola = createString("MATRICOLA");

    public final StringPath nome = createString("NOME");

    public final StringPath note = createString("NOTE");

    public QDmalmPersonale(String variable) {
        super(DmalmPersonale.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PERSONALE");
    }

    public QDmalmPersonale(Path<? extends DmalmPersonale> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PERSONALE");
    }

    public QDmalmPersonale(PathMetadata<?> metadata) {
        super(DmalmPersonale.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PERSONALE");
    }

}

