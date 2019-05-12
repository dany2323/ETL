package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmAnomaliaAssistenzaOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmAnomaliaAssistenzaOds is a Querydsl query type for DmalmAnomaliaAssistenzaOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmAnomaliaAssistenzaOds extends com.mysema.query.sql.RelationalPathBase<DmalmAnomaliaAssistenzaOds> {

    private static final long serialVersionUID = -599567572;

    public static final QDmalmAnomaliaAssistenzaOds dmalmAnomaliaAssistenzaOds = new QDmalmAnomaliaAssistenzaOds("DMALM_ANOMALIA_ASSISTENZA_ODS");

    public final StringPath aoid = createString("AOID");

    public final StringPath ca = createString("CA");

    public final StringPath cdAnomaliaAss = createString("CD_ANOMALIA_ASS");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final StringPath cs = createString("CS");

    public final StringPath descrizioneAnomaliaAss = createString("DESCRIZIONE_ANOMALIA_ASS");

    public final NumberPath<Integer> dmalmAnomaliaAssPk = createNumber("DMALM_ANOMALIA_ASS_PK", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

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
 
        public final StringPath uri = createString("URI_ANOMALIA_ASSISTENZA");


    public final NumberPath<Integer> tempoTotaleRisoluzione = createNumber("TEMPO_TOTALE_RISOLUZIONE", Integer.class);

    public final StringPath ticketid = createString("TICKETID");

    public final StringPath titoloAnomaliaAss = createString("TITOLO_ANOMALIA_ASS");
    
    public final StringPath severity = createString("SEVERITY");
    
    public final StringPath priority = createString("PRIORITY");

public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public final com.mysema.query.sql.PrimaryKey<DmalmAnomaliaAssistenzaOds> sysC0024517 = createPrimaryKey(dmalmAnomaliaAssPk);

    public QDmalmAnomaliaAssistenzaOds(String variable) {
        super(DmalmAnomaliaAssistenzaOds.class, forVariable(variable), "DMALM", "DMALM_ANOMALIA_ASSISTENZA_ODS");
    }

    public QDmalmAnomaliaAssistenzaOds(Path<? extends DmalmAnomaliaAssistenzaOds> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_ANOMALIA_ASSISTENZA_ODS");
    }

    public QDmalmAnomaliaAssistenzaOds(PathMetadata<?> metadata) {
        super(DmalmAnomaliaAssistenzaOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ANOMALIA_ASSISTENZA_ODS");
    }

}

