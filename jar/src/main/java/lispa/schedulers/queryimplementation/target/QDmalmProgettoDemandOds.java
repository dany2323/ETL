package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmProgettoDemandOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmProgettoDemandOds is a Querydsl query type for DmalmProgettoDemandOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProgettoDemandOds extends com.mysema.query.sql.RelationalPathBase<DmalmProgettoDemandOds> {

    private static final long serialVersionUID = 1340222757;

    public static final QDmalmProgettoDemandOds dmalmProgettoDemandOds = new QDmalmProgettoDemandOds("DMALM_PROGETTO_DEMAND_ODS");

    public final StringPath aoid = createString("AOID");

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

    public final NumberPath<Integer> dmalmProgettoDemandPk = createNumber("DMALM_PROGETTO_DEMAND_PK", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

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

    public final StringPath stgPk = createString("STG_PK");
 
    public final StringPath uri = createString("URI_PROGETTO_DEMAND");
        
    public final StringPath codObiettivoAziendale = createString("CODICE_OBIETTIVO_AZIENDALE");
    
    public final StringPath codObiettivoUtente = createString("CODICE_OBIETTIVO_UTENTE");

    public final NumberPath<Integer> tempoTotaleRisoluzione = createNumber("TEMPO_TOTALE_RISOLUZIONE", Integer.class);

    public final StringPath titoloProgettoDemand = createString("TITOLO_PROGETTO_DEMAND");
    
    public final StringPath cfClassificazione = createString("CF_CLASSIFICAZIONE");

    public final com.mysema.query.sql.PrimaryKey<DmalmProgettoDemandOds> sysC0023174 = createPrimaryKey(dmalmProgettoDemandPk);

  //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
        
    public final StringPath priority = createString("PRIORITY");

    public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmProgettoDemandOds(String variable) {
        super(DmalmProgettoDemandOds.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_DEMAND_ODS");
    }

    public QDmalmProgettoDemandOds(Path<? extends DmalmProgettoDemandOds> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_DEMAND_ODS");
    }

    public QDmalmProgettoDemandOds(PathMetadata<?> metadata) {
        super(DmalmProgettoDemandOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_DEMAND_ODS");
    }

}

