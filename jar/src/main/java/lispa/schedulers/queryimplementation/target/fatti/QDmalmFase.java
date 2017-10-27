package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmFase;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmFase is a Querydsl query type for DmalmFase
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmFase extends com.mysema.query.sql.RelationalPathBase<DmalmFase> {

    private static final long serialVersionUID = -1878440360;

    public static final QDmalmFase dmalmFase = new QDmalmFase("DMALM_FASE");
    
    public final StringPath annullato = createString("ANNULLATO");

    public final BooleanPath applicabile = createBoolean("APPLICABILE");

    public final StringPath cdFase = createString("CD_FASE");

    public final StringPath codice = createString("CODICE");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dataFineBaseline = createDateTime("DATA_FINE_BASELINE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataFineEffettiva = createDateTime("DATA_FINE_EFFETTIVA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataFinePianificata = createDateTime("DATA_FINE_PIANIFICATA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioBaseline = createDateTime("DATA_INIZIO_BASELINE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioEffettivo = createDateTime("DATA_INIZIO_EFFETTIVO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioPianificato = createDateTime("DATA_INIZIO_PIANIFICATO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataPassaggioInEsecuzione = createDateTime("DATA_PASSAGGIO_IN_ESECUZIONE", java.sql.Timestamp.class);

    public final StringPath descrizioneFase = createString("DESCRIZIONE_FASE");

    public final NumberPath<Integer> dmalmFasePk = createNumber("DMALM_FASE_PK", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreFase = createString("DS_AUTORE_FASE");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoFase = createDateTime("DT_CAMBIO_STATO_FASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoFase = createDateTime("DT_CARICAMENTO_FASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneFase = createDateTime("DT_CREAZIONE_FASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaFase = createDateTime("DT_MODIFICA_FASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneFase = createDateTime("DT_RISOLUZIONE_FASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaFase = createDateTime("DT_SCADENZA_FASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final NumberPath<Short> durataEffettivaFase = createNumber("DURATA_EFFETTIVA_FASE", Short.class);

    public final StringPath idAutoreFase = createString("ID_AUTORE_FASE");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneFase = createString("MOTIVO_RISOLUZIONE_FASE");

    public final NumberPath<Double> rankStatoFase = createNumber("RANK_STATO_FASE", Double.class);

    public final NumberPath<Short> rankStatoFaseMese = createNumber("RANK_STATO_FASE_MESE", Short.class);

    public final StringPath titoloFase = createString("TITOLO_FASE");
    
    public final StringPath stgPk = createString("STG_PK");
    public final StringPath changed = createString("CHANGED");    
 
        public final StringPath uri = createString("URI_FASE");


    public final com.mysema.query.sql.PrimaryKey<DmalmFase> sysC0022477 = createPrimaryKey(dmalmFasePk);
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);

  //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
        
    public final StringPath priority = createString("PRIORITY");
    
    
    public QDmalmFase(String variable) {
        super(DmalmFase.class, forVariable(variable), "DMALM", "DMALM_FASE");
    }

    public QDmalmFase(Path<? extends DmalmFase> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_FASE");
    }

    public QDmalmFase(PathMetadata<?> metadata) {
        super(DmalmFase.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_FASE");
    }

}

