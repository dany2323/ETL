package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.bean.target.DmalmProgrammaOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * QDmalmProgrammaOds is a Querydsl query type for DmalmProgrammaOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProgrammaOds extends com.mysema.query.sql.RelationalPathBase<DmalmProgrammaOds> {

    private static final long serialVersionUID = 1923841456;

    public static final QDmalmProgrammaOds dmalmProgrammaOds = new QDmalmProgrammaOds("DMALM_PROGRAMMA_ODS");

    public final StringPath assignee = createString("ASSIGNEE");

    public final StringPath cdProgramma = createString("CD_PROGRAMMA");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final StringPath descrizioneProgramma = createString("DESCRIZIONE_PROGRAMMA");

    public final StringPath codice = createString("CODICE");
    
    public final NumberPath<Integer> dmalmProgrammaPk = createNumber("DMALM_PROGRAMMA_PK", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

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

    public final StringPath stgPk = createString("STG_PK");
 
        public final StringPath uri = createString("URI_PROGRAMMA");


    public final StringPath titoloProgramma = createString("TITOLO_PROGRAMMA");
    
    public final StringPath cfTipologia = createString("CF_TIPOLOGIA");

    public final StringPath cfServiceManager = createString("CF_SERVICE_MANAGER");
    
    public final StringPath cfReferenteRegionale = createString("CF_REFERENTE_REGIONALE");		
    		
    public final StringPath cfContratto = createString("CF_CONTRATTO");

    public final com.mysema.query.sql.PrimaryKey<DmalmProgrammaOds> sysC0023109 = createPrimaryKey(dmalmProgrammaPk);

    public QDmalmProgrammaOds(String variable) {
        super(DmalmProgrammaOds.class, forVariable(variable), "DMALM", "DMALM_PROGRAMMA_ODS");
    }

    public QDmalmProgrammaOds(Path<? extends DmalmProgrammaOds> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_PROGRAMMA_ODS");
    }

    public QDmalmProgrammaOds(PathMetadata<?> metadata) {
        super(DmalmProgrammaOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGRAMMA_ODS");
    }

}

