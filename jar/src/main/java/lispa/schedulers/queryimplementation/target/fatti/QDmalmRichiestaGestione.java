package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaGestione;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmRichiestaGestione is a Querydsl query type for DmalmRichiestaGestione
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmRichiestaGestione extends com.mysema.query.sql.RelationalPathBase<DmalmRichiestaGestione> {

    private static final long serialVersionUID = -1002597959;

    public static final QDmalmRichiestaGestione dmalmRichiestaGestione = new QDmalmRichiestaGestione("DMALM_RICHIESTA_GESTIONE");

    public final StringPath categoria = createString("CATEGORIA");
    
    public final StringPath annullato = createString("ANNULLATO");

    public final StringPath cdRichiestaGest = createString("CD_RICHIESTA_GEST");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dataChiusura = createDateTime("DATA_CHIUSURA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataDisponibilita = createDateTime("DATA_DISPONIBILITA", java.sql.Timestamp.class);

    public final StringPath descrizioneRichiestaGest = createString("DESCRIZIONE_RICHIESTA_GEST");

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmRichiestaGestPk = createNumber("DMALM_RICHIESTA_GEST_PK", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreRichiestaGest = createString("DS_AUTORE_RICHIESTA_GEST");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoRichiestaGest = createDateTime("DT_CAMBIO_STATO_RICHIESTA_GEST", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoRichiestaGest = createDateTime("DT_CARICAMENTO_RICHIESTA_GEST", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneRichiestaGest = createDateTime("DT_CREAZIONE_RICHIESTA_GEST", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaRichiestaGest = createDateTime("DT_MODIFICA_RICHIESTA_GEST", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneRichiestaGest = createDateTime("DT_RISOLUZIONE_RICHIESTA_GEST", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaRichiestaGest = createDateTime("DT_SCADENZA_RICHIESTA_GEST", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreRichiestaGest = createString("ID_AUTORE_RICHIESTA_GEST");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneRichGest = createString("MOTIVO_RISOLUZIONE_RICH_GEST");

    public final NumberPath<Double> rankStatoRichiestaGest = createNumber("RANK_STATO_RICHIESTA_GEST", Double.class);

    public final NumberPath<Short> rankStatoRichiestaGestMese = createNumber("RANK_STATO_RICHIESTA_GEST_MESE", Short.class);

    public final StringPath stgPk = createString("STG_PK");
    public final StringPath changed = createString("CHANGED");    
 
        public final StringPath uri = createString("URI_RICHIESTA_GESTIONE");


    public final StringPath ticketid = createString("TICKETID");

    public final StringPath titoloRichiestaGest = createString("TITOLO_RICHIESTA_GEST");

    public final com.mysema.query.sql.PrimaryKey<DmalmRichiestaGestione> sysC0022482 = createPrimaryKey(dmalmRichiestaGestPk);

    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
    
    public QDmalmRichiestaGestione(String variable) {
        super(DmalmRichiestaGestione.class, forVariable(variable), "DMALM", "DMALM_RICHIESTA_GESTIONE");
    }

    public QDmalmRichiestaGestione(Path<? extends DmalmRichiestaGestione> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_RICHIESTA_GESTIONE");
    }

    public QDmalmRichiestaGestione(PathMetadata<?> metadata) {
        super(DmalmRichiestaGestione.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RICHIESTA_GESTIONE");
    }

}

