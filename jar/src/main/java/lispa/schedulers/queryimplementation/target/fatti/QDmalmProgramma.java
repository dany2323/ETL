package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.bean.target.fatti.DmalmProgramma;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * QDmalmProgramma is a Querydsl query type for DmalmProgramma
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProgramma extends com.mysema.query.sql.RelationalPathBase<DmalmProgramma> {

    private static final long serialVersionUID = -35505171;
 
    public static final QDmalmProgramma dmalmProgramma = new QDmalmProgramma("DMALM_PROGRAMMA");

    public final StringPath assignee = createString("ASSIGNEE");

    public final StringPath cdProgramma = createString("CD_PROGRAMMA");
    
    public final StringPath annullato = createString("ANNULLATO");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final StringPath descrizioneProgramma = createString("DESCRIZIONE_PROGRAMMA");

    public final NumberPath<Long> dmalmProgrammaPk = createNumber("DMALM_PROGRAMMA_PK", Long.class);

    public final NumberPath<Long> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Long.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreProgramma = createString("DS_AUTORE_PROGRAMMA");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoProgramma = createDateTime("DT_CAMBIO_STATO_PROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoProgramma = createDateTime("DT_CARICAMENTO_PROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneProgramma = createDateTime("DT_CREAZIONE_PROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaProgramma = createDateTime("DT_MODIFICA_PROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneProgramma = createDateTime("DT_RISOLUZIONE_PROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaProgramma = createDateTime("DT_SCADENZA_PROGRAMMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreProgramma = createString("ID_AUTORE_PROGRAMMA");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneProgramma = createString("MOTIVO_RISOLUZIONE_PROGRAMMA");

    public final StringPath numeroLinea = createString("NUMERO_LINEA");

    public final StringPath numeroTestata = createString("NUMERO_TESTATA");

    public final NumberPath<Double> rankStatoProgramma = createNumber("RANK_STATO_PROGRAMMA", Double.class);

    public final NumberPath<Short> rankStatoProgrammaMese = createNumber("RANK_STATO_PROGRAMMA_MESE", Short.class);

    public final StringPath titoloProgramma = createString("TITOLO_PROGRAMMA");
    
    public final StringPath cfTipologia = createString("CF_TIPOLOGIA");

    public final StringPath cfServiceManager = createString("CF_SERVICE_MANAGER");
    
    public final StringPath cfReferenteRegionale = createString("CF_REFERENTE_REGIONALE");		
    		
    public final StringPath cfContratto = createString("CF_CONTRATTO");
    
    public final StringPath stgPk = createString("STG_PK");
    public final StringPath changed = createString("CHANGED");    
 
        public final StringPath uri = createString("URI_PROGRAMMA");

    
    public final StringPath codice = createString("CODICE");
    		
    public final com.mysema.query.sql.PrimaryKey<DmalmProgramma> sysC0022467 = createPrimaryKey(dmalmProgrammaPk);
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);

  //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
    
    public final StringPath priority = createString("PRIORITY");
    
public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmProgramma(String variable) {
        super(DmalmProgramma.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGRAMMA");
    }

    public QDmalmProgramma(Path<? extends DmalmProgramma> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGRAMMA");
    }

    public QDmalmProgramma(PathMetadata<?> metadata) {
        super(DmalmProgramma.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGRAMMA");
    }

}

