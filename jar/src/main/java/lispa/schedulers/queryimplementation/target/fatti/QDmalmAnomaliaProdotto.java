package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmAnomaliaProdotto is a Querydsl query type for DmalmAnomaliaProdotto
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmAnomaliaProdotto extends com.mysema.query.sql.RelationalPathBase<DmalmAnomaliaProdotto> {

    private static final long serialVersionUID = -1899584881;

    public static final QDmalmAnomaliaProdotto dmalmAnomaliaProdotto = new QDmalmAnomaliaProdotto("DMALM_ANOMALIA_PRODOTTO");

    public final StringPath annullato = createString("ANNULLATO");

    public final StringPath cdAnomalia = createString("CD_ANOMALIA");

    public final StringPath descrizioneAnomalia = createString("DESCRIZIONE_ANOMALIA");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final NumberPath<Integer> dmalmAnomaliaProdottoPk = createNumber("DMALM_ANOMALIA_PRODOTTO_PK", Integer.class);

    public final NumberPath<Integer> dmalmAreaTematicaFk05 = createNumber("DMALM_AREA_TEMATICA_FK_05", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAnomalia = createString("DS_ANOMALIA");

    public final StringPath dsAutoreAnomalia = createString("DS_AUTORE_ANOMALIA");

    public final DateTimePath<java.sql.Timestamp> dtAperturaTicket = createDateTime("DT_APERTURA_TICKET", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoAnomalia = createDateTime("DT_CAMBIO_STATO_ANOMALIA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoRecordAnomalia = createDateTime("DT_CARICAMENTO_RECORD_ANOMALIA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtChiusuraAnomalia = createDateTime("DT_CHIUSURA_ANOMALIA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtChiusuraTicket = createDateTime("DT_CHIUSURA_TICKET", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneAnomalia = createDateTime("DT_CREAZIONE_ANOMALIA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaRecordAnomalia = createDateTime("DT_MODIFICA_RECORD_ANOMALIA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneAnomalia = createDateTime("DT_RISOLUZIONE_ANOMALIA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final NumberPath<Double> effortAnalisi = createNumber("EFFORT_ANALISI", Double.class);

    public final NumberPath<Integer> effortCostoSviluppo = createNumber("EFFORT_COSTO_SVILUPPO", Integer.class);

    public final StringPath idAnomaliaAssistenza = createString("ID_ANOMALIA_ASSISTENZA");

    public final StringPath idAutoreAnomalia = createString("ID_AUTORE_ANOMALIA");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneAnomalia = createString("MOTIVO_RISOLUZIONE_ANOMALIA");

    public final NumberPath<Integer> nrGiorniFestivi = createNumber("NR_GIORNI_FESTIVI", Integer.class);

    public final StringPath numeroLineaAnomalia = createString("NUMERO_LINEA_ANOMALIA");

    public final StringPath numeroTestataAnomalia = createString("NUMERO_TESTATA_ANOMALIA");

    public final NumberPath<Double> rankStatoAnomalia = createNumber("RANK_STATO_ANOMALIA", Double.class);

    public final NumberPath<Short> rankStatoAnomaliaMese = createNumber("RANK_STATO_ANOMALIA_MESE", Short.class);

    public final StringPath severity = createString("SEVERITY");

    public final StringPath stgPk = createString("STG_PK");
    
    public final StringPath changed = createString("CHANGED");    
 
    public final StringPath uri = createString("URI_ANOMALIA_PRODOTTO");

    public final NumberPath<Integer> tempoTotRisoluzioneAnomalia = createNumber("TEMPO_TOT_RISOLUZIONE_ANOMALIA", Integer.class);

    public final StringPath ticketSiebelAnomaliaAss = createString("TICKET_SIEBEL_ANOMALIA_ASS");

    public final NumberPath<Short> flagUltimaSituazione = createNumber("FL_ULTIMA_SITUAZIONE", Short.class);
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
    
    public final StringPath contestazione = createString("CONTESTAZIONE");
    
    public final StringPath noteContestazione = createString("NOTE_CONTESTAZIONE");
    
    //DM_ALM-223
    public final DateTimePath<java.sql.Timestamp> dataDisponibilita = createDateTime("DATA_DISPONIBILITA", java.sql.Timestamp.class);
    
    //DM_ALM-320
    public final StringPath priority = createString("PRIORITY");
    
    public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    
    public QDmalmAnomaliaProdotto(String variable) {
        super(DmalmAnomaliaProdotto.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ANOMALIA_PRODOTTO");
    }

    public QDmalmAnomaliaProdotto(Path<? extends DmalmAnomaliaProdotto> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ANOMALIA_PRODOTTO");
    }

    public QDmalmAnomaliaProdotto(PathMetadata<?> metadata) {
        super(DmalmAnomaliaProdotto.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ANOMALIA_PRODOTTO");
    }

}

