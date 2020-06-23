package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmAnomaliaAssistenza;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmAnomaliaAssistenza is a Querydsl query type for DmalmAnomaliaAssistenza
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmAnomaliaAssistenza extends com.mysema.query.sql.RelationalPathBase<DmalmAnomaliaAssistenza> {

    private static final long serialVersionUID = 1220234418;

    public static final QDmalmAnomaliaAssistenza dmalmAnomaliaAssistenza = new QDmalmAnomaliaAssistenza("DMALM_ANOMALIA_ASSISTENZA");

    public final StringPath aoid = createString("AOID");
    
    public final StringPath annullato = createString("ANNULLATO");

    public final StringPath ca = createString("CA");

    public final StringPath cdAnomaliaAss = createString("CD_ANOMALIA_ASS");

    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);
    
    public final StringPath cs = createString("CS");

    public final StringPath descrizioneAnomaliaAss = createString("DESCRIZIONE_ANOMALIA_ASS");

    public final NumberPath<Long> dmalmAnomaliaAssPk = createNumber("DMALM_ANOMALIA_ASS_PK", Long.class);

    public final NumberPath<Long> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Long.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreAnomaliaAss = createString("DS_AUTORE_ANOMALIA_ASS");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoAnomaliaAss = createDateTime("DT_CAMBIO_STATO_ANOMALIA_ASS", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoAnomaliaAss = createDateTime("DT_CARICAMENTO_ANOMALIA_ASS", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneAnomaliaAss = createDateTime("DT_CREAZIONE_ANOMALIA_ASS", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaAnomaliaAss = createDateTime("DT_MODIFICA_ANOMALIA_ASS", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneAnomaliaAss = createDateTime("DT_RISOLUZIONE_ANOMALIA_ASS", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaAnomaliaAss = createDateTime("DT_SCADENZA_ANOMALIA_ASS", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath frequenza = createString("FREQUENZA");

    public final StringPath idAutoreAnomaliaAss = createString("ID_AUTORE_ANOMALIA_ASS");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneAnomaliaAs = createString("MOTIVO_RISOLUZIONE_ANOMALIA_AS");

    public final StringPath platform = createString("PLATFORM");

    public final StringPath prodCod = createString("PROD_COD");

    public final NumberPath<Double> rankStatoAnomaliaAss = createNumber("RANK_STATO_ANOMALIA_ASS", Double.class);

    public final NumberPath<Short> rankStatoAnomaliaAssMese = createNumber("RANK_STATO_ANOMALIA_ASS_MESE", Short.class);

    public final StringPath segnalazioni = createString("SEGNALAZIONI");

    public final StringPath so = createString("SO");

    public final DateTimePath<java.sql.Timestamp> stChiuso = createDateTime("ST_CHIUSO", java.sql.Timestamp.class);

    public final StringPath stgPk = createString("STG_PK");
    public final StringPath changed = createString("CHANGED");    
 
        public final StringPath uri = createString("URI_ANOMALIA_ASSISTENZA");


    public final NumberPath<Integer> tempoTotaleRisoluzione = createNumber("TEMPO_TOTALE_RISOLUZIONE", Integer.class);

    public final StringPath ticketid = createString("TICKETID");

    public final StringPath titoloAnomaliaAss = createString("TITOLO_ANOMALIA_ASS");
    
    public final StringPath severity = createString("SEVERITY");
    
    public final StringPath priority = createString("PRIORITY");

    public final com.mysema.query.sql.PrimaryKey<DmalmAnomaliaAssistenza> sysC0022483 = createPrimaryKey(dmalmAnomaliaAssPk);

    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
    
    public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmAnomaliaAssistenza(String variable) {
        super(DmalmAnomaliaAssistenza.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ANOMALIA_ASSISTENZA");
    }

    public QDmalmAnomaliaAssistenza(Path<? extends DmalmAnomaliaAssistenza> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ANOMALIA_ASSISTENZA");
    }

    public QDmalmAnomaliaAssistenza(PathMetadata<?> metadata) {
        super(DmalmAnomaliaAssistenza.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ANOMALIA_ASSISTENZA");
    }

}

