package lispa.schedulers.queryimplementation.staging.edma;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.edma.StgPersonale;
import lispa.schedulers.constant.DmAlmConstants;
import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QStgPersonale is a Querydsl query type for StgPersonale
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStgPersonale extends com.mysema.query.sql.RelationalPathBase<StgPersonale>{

    private static final long serialVersionUID = -1467551208;
    
    public static final QStgPersonale stgPersonale = new QStgPersonale("DMALM_STG_PERSONALE");

    public final StringPath codEnte = createString("COD_ENTE");

    public final StringPath codResponsabile = createString("COD_RESPONSABILE");

    public final StringPath codSuperiore = createString("COD_SUPERIORE");

    public final StringPath codVisibilita = createString("COD_VISIBILITA");

    public final StringPath codice = createString("CODICE");

    public final StringPath codiceFiscale = createString("CODICE_FISCALE");

    public final StringPath cognome = createString("COGNOME");

    public final StringPath dataAttivazione = createString("DATA_ATTIVAZIONE");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath dataDisattivazione = createString("DATA_DISATTIVAZIONE");

    public final StringPath dataFineValidita = createString("DATA_FINE_VALIDITA");

    public final StringPath dataInizioValidita = createString("DATA_INIZIO_VALIDITA");

    public final StringPath id = createString("ID");

    public final StringPath idGradoUfficio = createString("ID_GRADO_UFFICIO");

    public final StringPath idSede = createString("ID_SEDE");

    public final StringPath identficatore = createString("IDENTFICATORE");

    public final StringPath indirizzoEmail = createString("INDIRIZZO_EMAIL");

    public final StringPath interno = createString("INTERNO");

    public final StringPath matricola = createString("MATRICOLA");

    public final StringPath nome = createString("NOME");

    public final StringPath note = createString("NOTE");

    public final StringPath tipopersona = createString("TIPOPERSONA");
    
    public final StringPath dmalmStgPersonalePk = createString("DMALM_STG_PERSONALE_PK");

    public QStgPersonale(String variable)  {
    	
        super(StgPersonale.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_PERSONALE");
    	
    }

    public QStgPersonale(Path<? extends StgPersonale> path) {
        super(path.getType(), path.getMetadata(),  DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_PERSONALE");
    }

    public QStgPersonale(PathMetadata<?> metadata)  {
        super(StgPersonale.class, metadata,  DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_PERSONALE");
    }

}

