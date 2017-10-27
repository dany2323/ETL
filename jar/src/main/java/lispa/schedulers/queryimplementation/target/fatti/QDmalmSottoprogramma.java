package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmSottoprogramma;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmSottoprogramma is a Querydsl query type for DmalmSottoprogramma
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmSottoprogramma extends com.mysema.query.sql.RelationalPathBase<DmalmSottoprogramma> {

    private static final long serialVersionUID = 1449775759;

    public static final QDmalmSottoprogramma dmalmSottoprogramma = new QDmalmSottoprogramma("DMALM_SOTTOPROGRAMMA");

    public final StringPath cdSottoprogramma = createString("CD_SOTTOPROGRAMMA");
    
    public final StringPath annullato = createString("ANNULLATO");

    public final StringPath codice = createString("CODICE");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final StringPath descrizioneSottoprogramma = createString("DESCRIZIONE_SOTTOPROGRAMMA");

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmSottoprogrammaPk = createNumber("DMALM_SOTTOPROGRAMMA_PK", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreSottoprogramma = createString("DS_AUTORE_SOTTOPROGRAMMA");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoSottoprogramma = createDateTime("DT_CAMBIO_STATO_SOTTOPROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoSottoprogramma = createDateTime("DT_CARICAMENTO_SOTTOPROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCompletamento = createDateTime("DT_COMPLETAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneSottoprogramma = createDateTime("DT_CREAZIONE_SOTTOPROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaSottoprogramma = createDateTime("DT_MODIFICA_SOTTOPROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneSottoprogramma = createDateTime("DT_RISOLUZIONE_SOTTOPROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaSottoprogramma = createDateTime("DT_SCADENZA_SOTTOPROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreSottoprogramma = createString("ID_AUTORE_SOTTOPROGRAMMA");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneSottoprogr = createString("MOTIVO_RISOLUZIONE_SOTTOPROGR");

    public final StringPath numeroLinea = createString("NUMERO_LINEA");

    public final StringPath numeroTestata = createString("NUMERO_TESTATA");

    public final NumberPath<Double> rankStatoSottoprogramma = createNumber("RANK_STATO_SOTTOPROGRAMMA", Double.class);

    public final NumberPath<Short> rankStatoSottoprogrammaMese = createNumber("RANK_STATO_SOTTOPROGRAMMA_MESE", Short.class);

    public final StringPath titoloSottoprogramma = createString("TITOLO_SOTTOPROGRAMMA");
    
    public final StringPath stgPk = createString("STG_PK");
    public final StringPath changed = createString("CHANGED");    
 
        public final StringPath uri = createString("URI_SOTTOPROGRAMMA");


    public final com.mysema.query.sql.PrimaryKey<DmalmSottoprogramma> sysC0022470 = createPrimaryKey(dmalmSottoprogrammaPk);
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);

  //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
        
    public final StringPath priority = createString("PRIORITY");
    
    public QDmalmSottoprogramma(String variable) {
        super(DmalmSottoprogramma.class, forVariable(variable), "DMALM", "DMALM_SOTTOPROGRAMMA");
    }

    public QDmalmSottoprogramma(Path<? extends DmalmSottoprogramma> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_SOTTOPROGRAMMA");
    }

    public QDmalmSottoprogramma(PathMetadata<?> metadata) {
        super(DmalmSottoprogramma.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_SOTTOPROGRAMMA");
    }

}

