package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmRichManutenzioneOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmRichManutenzioneOds is a Querydsl query type for DmalmRichManutenzioneOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmRichManutenzioneOds extends com.mysema.query.sql.RelationalPathBase<DmalmRichManutenzioneOds> {

    private static final long serialVersionUID = 775323874;

    public static final QDmalmRichManutenzioneOds dmalmRichManutenzioneOds = new QDmalmRichManutenzioneOds("DMALM_RICH_MANUTENZIONE_ODS");

    public final StringPath cdRichiestaManutenzione = createString("CD_RICHIESTA_MANUTENZIONE");

    public final StringPath classeDiFornitura = createString("CLASSE_DI_FORNITURA");

    public final StringPath codice = createString("CODICE");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dataChiusuraRichManut = createDateTime("DATA_CHIUSURA_RICH_MANUT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataDispEffettiva = createDateTime("DATA_DISP_EFFETTIVA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataDispPianificata = createDateTime("DATA_DISP_PIANIFICATA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioEffettivo = createDateTime("DATA_INIZIO_EFFETTIVO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataInizioPianificato = createDateTime("DATA_INIZIO_PIANIFICATO", java.sql.Timestamp.class);

    public final StringPath descrizioneRichManutenzione = createString("DESCRIZIONE_RICH_MANUTENZIONE");

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmRichManutenzionePk = createNumber("DMALM_RICH_MANUTENZIONE_PK", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreRichManutenzione = createString("DS_AUTORE_RICH_MANUTENZIONE");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoRichManut = createDateTime("DT_CAMBIO_STATO_RICH_MANUT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoRichManut = createDateTime("DT_CARICAMENTO_RICH_MANUT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneRichManutenzione = createDateTime("DT_CREAZIONE_RICH_MANUTENZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaRichManutenzione = createDateTime("DT_MODIFICA_RICH_MANUTENZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneRichManut = createDateTime("DT_RISOLUZIONE_RICH_MANUT", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaRichManutenzione = createDateTime("DT_SCADENZA_RICH_MANUTENZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final NumberPath<Short> durataEffettivaRichMan = createNumber("DURATA_EFFETTIVA_RICH_MAN", Short.class);

    public final StringPath idAutoreRichManutenzione = createString("ID_AUTORE_RICH_MANUTENZIONE");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneRichManut = createString("MOTIVO_RISOLUZIONE_RICH_MANUT");

    public final NumberPath<Short> rankStatoRichManutMese = createNumber("RANK_STATO_RICH_MANUT_MESE", Short.class);

    public final NumberPath<Double> rankStatoRichManutenzione = createNumber("RANK_STATO_RICH_MANUTENZIONE", Double.class);

    public final StringPath stgPk = createString("STG_PK");
 
        public final StringPath uri = createString("URI_RICH_MANUTENZIONE");


    public final StringPath titoloRichiestaManutenzione = createString("TITOLO_RICHIESTA_MANUTENZIONE");

    public final com.mysema.query.sql.PrimaryKey<DmalmRichManutenzioneOds> sysC0023550 = createPrimaryKey(dmalmRichManutenzionePk);

  //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
        
    public final StringPath priority = createString("PRIORITY");

    public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmRichManutenzioneOds(String variable) {
        super(DmalmRichManutenzioneOds.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RICH_MANUTENZIONE_ODS");
    }

    public QDmalmRichManutenzioneOds(Path<? extends DmalmRichManutenzioneOds> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RICH_MANUTENZIONE_ODS");
    }

    public QDmalmRichManutenzioneOds(PathMetadata<?> metadata) {
        super(DmalmRichManutenzioneOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RICH_MANUTENZIONE_ODS");
    }

}

