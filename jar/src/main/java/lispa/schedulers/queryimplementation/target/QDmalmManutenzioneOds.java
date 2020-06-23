package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmManutenzioneOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmManutenzioneOds is a Querydsl query type for DmalmManutenzioneOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmManutenzioneOds extends com.mysema.query.sql.RelationalPathBase<DmalmManutenzioneOds> {

    private static final long serialVersionUID = 1167202270;

    public static final QDmalmManutenzioneOds dmalmManutenzioneOds = new QDmalmManutenzioneOds("DMALM_MANUTENZIONE_ODS");

    public final StringPath assigneesManutenzione = createString("ASSIGNEES_MANUTENZIONE");

    public final StringPath cdManutenzione = createString("CD_MANUTENZIONE");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final StringPath codice = createString("CODICE");

    public final DateTimePath<java.sql.Timestamp> dataDispEff = createDateTime("DATA_DISP_EFF", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataDisponibilita = createDateTime("DATA_DISPONIBILITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizio = createDateTime("DATA_INIZIO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioEff = createDateTime("DATA_INIZIO_EFF", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataRilascioInEs = createDateTime("DATA_RILASCIO_IN_ES", java.sql.Timestamp.class);

    public final StringPath descrizioneManutenzione = createString("DESCRIZIONE_MANUTENZIONE");

    public final NumberPath<Integer> dmalmAreaTematicaFk05 = createNumber("DMALM_AREA_TEMATICA_FK_05", Integer.class);

    public final NumberPath<Integer> dmalmManutenzionePk = createNumber("DMALM_MANUTENZIONE_PK", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreManutenzione = createString("DS_AUTORE_MANUTENZIONE");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoManutenzione = createDateTime("DT_CAMBIO_STATO_MANUTENZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoManutenzione = createDateTime("DT_CARICAMENTO_MANUTENZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneManutenzione = createDateTime("DT_CREAZIONE_MANUTENZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaManutenzione = createDateTime("DT_MODIFICA_MANUTENZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneManutenzione = createDateTime("DT_RISOLUZIONE_MANUTENZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaManutenzione = createDateTime("DT_SCADENZA_MANUTENZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath fornitura = createString("FORNITURA");

    public final StringPath idAutoreManutenzione = createString("ID_AUTORE_MANUTENZIONE");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneManutenzion = createString("MOTIVO_RISOLUZIONE_MANUTENZION");

    public final StringPath numeroLinea = createString("NUMERO_LINEA");

    public final StringPath numeroTestata = createString("NUMERO_TESTATA");

    public final StringPath priorityManutenzione = createString("PRIORITY_MANUTENZIONE");

    public final NumberPath<Double> rankStatoManutenzione = createNumber("RANK_STATO_MANUTENZIONE", Double.class);

    public final NumberPath<Short> rankStatoManutenzioneMese = createNumber("RANK_STATO_MANUTENZIONE_MESE", Short.class);

    public final StringPath severityManutenzione = createString("SEVERITY_MANUTENZIONE");

    public final StringPath stgPk = createString("STG_PK");
 
        public final StringPath uri = createString("URI_MANUTENZIONE");


    public final NumberPath<Integer> tempoTotaleRisoluzione = createNumber("TEMPO_TOTALE_RISOLUZIONE", Integer.class);

    public final StringPath titoloManutenzione = createString("TITOLO_MANUTENZIONE");

    public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public final com.mysema.query.sql.PrimaryKey<DmalmManutenzioneOds> sysC0023103 = createPrimaryKey(dmalmManutenzionePk);

    public QDmalmManutenzioneOds(String variable) {
        super(DmalmManutenzioneOds.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_MANUTENZIONE_ODS");
    }

    public QDmalmManutenzioneOds(Path<? extends DmalmManutenzioneOds> path) {
        super(path.getType(), path.getMetadata(),DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_MANUTENZIONE_ODS");
    }

    public QDmalmManutenzioneOds(PathMetadata<?> metadata) {
        super(DmalmManutenzioneOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_MANUTENZIONE_ODS");
    }

}

