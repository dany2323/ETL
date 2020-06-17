package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.bean.target.fatti.DmalmProgettoDemand;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * QDmalmProgettoDemand is a Querydsl query type for DmalmProgettoDemand
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProgettoDemand extends com.mysema.query.sql.RelationalPathBase<DmalmProgettoDemand> {

    private static final long serialVersionUID = 533618009;

    public static final QDmalmProgettoDemand dmalmProgettoDemand = new QDmalmProgettoDemand("DMALM_PROGETTO_DEMAND");

    public final StringPath aoid = createString("AOID");
    
    public final StringPath annullato = createString("ANNULLATO");

    public final StringPath cdProgettoDemand = createString("CD_PROGETTO_DEMAND");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final DateTimePath<java.sql.Timestamp> cfDtDisponibilita = createDateTime("CF_DT_DISPONIBILITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> cfDtDisponibilitaEff = createDateTime("CF_DT_DISPONIBILITA_EFF", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> cfDtEnunciazione = createDateTime("CF_DT_ENUNCIAZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> cfDtValidazione = createDateTime("CF_DT_VALIDAZIONE", java.sql.Timestamp.class);

    public final StringPath cfOwnerDemand = createString("CF_OWNER_DEMAND");

    public final StringPath cfReferenteEsercizio = createString("CF_REFERENTE_ESERCIZIO");

    public final StringPath cfReferenteSviluppo = createString("CF_REFERENTE_SVILUPPO");

    public final StringPath codice = createString("CODICE");

    public final StringPath descrizioneProgettoDemand = createString("DESCRIZIONE_PROGETTO_DEMAND");

    public final NumberPath<Long> dmalmProgettoDemandPk = createNumber("DMALM_PROGETTO_DEMAND_PK", Long.class);

    public final NumberPath<Long> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Long.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreProgettoDemand = createString("DS_AUTORE_PROGETTO_DEMAND");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoProgettoDem = createDateTime("DT_CAMBIO_STATO_PROGETTO_DEM", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoProgettoDemand = createDateTime("DT_CARICAMENTO_PROGETTO_DEMAND", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtChiusuraProgettoDemand = createDateTime("DT_CHIUSURA_PROGETTO_DEMAND", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneProgettoDemand = createDateTime("DT_CREAZIONE_PROGETTO_DEMAND", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaProgettoDemand = createDateTime("DT_MODIFICA_PROGETTO_DEMAND", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneProgettoDemand = createDateTime("DT_RISOLUZIONE_PROGETTO_DEMAND", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaProgettoDemand = createDateTime("DT_SCADENZA_PROGETTO_DEMAND", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath fornitura = createString("FORNITURA");

    public final StringPath idAutoreProgettoDemand = createString("ID_AUTORE_PROGETTO_DEMAND");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneProgDem = createString("MOTIVO_RISOLUZIONE_PROG_DEM");

    public final NumberPath<Short> rankStatoProgettoDemMese = createNumber("RANK_STATO_PROGETTO_DEM_MESE", Short.class);

    public final NumberPath<Double> rankStatoProgettoDemand = createNumber("RANK_STATO_PROGETTO_DEMAND", Double.class);

    public final NumberPath<Integer> tempoTotaleRisoluzione = createNumber("TEMPO_TOTALE_RISOLUZIONE", Integer.class);

    public final StringPath titoloProgettoDemand = createString("TITOLO_PROGETTO_DEMAND");

    public final StringPath stgPk = createString("STG_PK");
    public final StringPath changed = createString("CHANGED");    
 
    public final StringPath uri = createString("URI_PROGETTO_DEMAND");
    
    public final StringPath codObiettivoAziendale = createString("CODICE_OBIETTIVO_AZIENDALE");
    
    public final StringPath codObiettivoUtente = createString("CODICE_OBIETTIVO_UTENTE");
    
    public final StringPath cfClassificazione = createString("CF_CLASSIFICAZIONE");

    public final com.mysema.query.sql.PrimaryKey<DmalmProgettoDemand> sysC0022472 = createPrimaryKey(dmalmProgettoDemandPk);

    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
    
  //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
        
    public final StringPath priority = createString("PRIORITY");
    
public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmProgettoDemand(String variable) {
        super(DmalmProgettoDemand.class, forVariable(variable), "DMALM", "DMALM_PROGETTO_DEMAND");
    }

    public QDmalmProgettoDemand(Path<? extends DmalmProgettoDemand> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_PROGETTO_DEMAND");
    }

    public QDmalmProgettoDemand(PathMetadata<?> metadata) {
        super(DmalmProgettoDemand.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_DEMAND");
    }

}

