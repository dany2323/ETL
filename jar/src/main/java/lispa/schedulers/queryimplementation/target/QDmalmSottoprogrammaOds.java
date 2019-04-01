package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmSottoprogrammaOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmSottoprogrammaOds is a Querydsl query type for DmalmSottoprogrammaOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmSottoprogrammaOds extends com.mysema.query.sql.RelationalPathBase<DmalmSottoprogrammaOds> {

    private static final long serialVersionUID = -1948589970;

    public static final QDmalmSottoprogrammaOds dmalmSottoprogrammaOds = new QDmalmSottoprogrammaOds("DMALM_SOTTOPROGRAMMA_ODS");

    public final StringPath cdSottoprogramma = createString("CD_SOTTOPROGRAMMA");

    public final StringPath codice = createString("CODICE");
    
    public final StringPath numeroLinea = createString("NUMERO_LINEA");

    public final StringPath numeroTestata = createString("NUMERO_TESTATA");
    
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

    public final BooleanPath rankStatoSottoprogramma = createBoolean("RANK_STATO_SOTTOPROGRAMMA");

    public final NumberPath<Short> rankStatoSottoprogrammaMese = createNumber("RANK_STATO_SOTTOPROGRAMMA_MESE", Short.class);

    public final StringPath stgPk = createString("STG_PK");
 
        public final StringPath uri = createString("URI_SOTTOPROGRAMMA");


    public final StringPath titoloSottoprogramma = createString("TITOLO_SOTTOPROGRAMMA");

    public final com.mysema.query.sql.PrimaryKey<DmalmSottoprogrammaOds> sysC0023025 = createPrimaryKey(dmalmSottoprogrammaPk);

  //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
        
    public final StringPath priority = createString("PRIORITY");

    public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmSottoprogrammaOds(String variable) {
        super(DmalmSottoprogrammaOds.class, forVariable(variable), "DMALM", "DMALM_SOTTOPROGRAMMA_ODS");
    }

    public QDmalmSottoprogrammaOds(Path<? extends DmalmSottoprogrammaOds> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_SOTTOPROGRAMMA_ODS");
    }

    public QDmalmSottoprogrammaOds(PathMetadata<?> metadata) {
        super(DmalmSottoprogrammaOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_SOTTOPROGRAMMA_ODS");
    }

}

