package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmDifettoProdotto is a Querydsl query type for DmalmDifettoProdotto
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmDifettoProdotto extends com.mysema.query.sql.RelationalPathBase<DmalmDifettoProdotto> {

	
    private static final long serialVersionUID = -238545769;

    public static final QDmalmDifettoProdotto dmalmDifettoProdotto = new QDmalmDifettoProdotto("DMALM_DIFETTO_PRODOTTO");

    public final StringPath cdDifetto = createString("CD_DIFETTO");
    
    public final StringPath annullato = createString("ANNULLATO");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final NumberPath<Long> dmalmDifettoProdottoPk = createNumber("DMALM_DIFETTO_PRODOTTO_PK", Long.class);

    public final NumberPath<Long> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Long.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreDifetto = createString("DS_AUTORE_DIFETTO");

    public final StringPath dsDifetto = createString("DS_DIFETTO");
    
    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoDifetto = createDateTime("DT_CAMBIO_STATO_DIFETTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoRecordDifetto = createDateTime("DT_CARICAMENTO_RECORD_DIFETTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtChiusuraDifetto = createDateTime("DT_CHIUSURA_DIFETTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneDifetto = createDateTime("DT_CREAZIONE_DIFETTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaRecordDifetto = createDateTime("DT_MODIFICA_RECORD_DIFETTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneDifetto = createDateTime("DT_RISOLUZIONE_DIFETTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);
    
    public final StringPath idAutoreDifetto = createString("ID_AUTORE_DIFETTO");

    public final NumberPath<Integer> idkAreaTematica = createNumber("DMALM_AREA_TEMATICA_FK_05", Integer.class);

    public final StringPath motivoRisoluzioneDifetto = createString("MOTIVO_RISOLUZIONE_DIFETTO");

    public final NumberPath<Integer> nrGiorniFestivi = createNumber("NR_GIORNI_FESTIVI", Integer.class);

    public final StringPath numeroLineaDifetto = createString("NUMERO_LINEA_DIFETTO");

    public final StringPath numeroTestataDifetto = createString("NUMERO_TESTATA_DIFETTO");

    public final StringPath provenienzaDifetto = createString("PROVENIENZA_DIFETTO");
    
    public final StringPath causaDifetto = createString("CAUSA_DIFETTO");
    
    public final StringPath naturaDifetto = createString("NATURA_DIFETTO");
    
    public final NumberPath<Double> rankStatoDifetto = createNumber("RANK_STATO_DIFETTO", Double.class);

    public final StringPath severity = createString("SEVERITY");

    public final NumberPath<Double> tempoTotRisoluzioneDifetto = createNumber("TEMPO_TOT_RISOLUZIONE_DIFETTO", Double.class);
    
    public final StringPath stgPk = createString("STG_PK");

    public final StringPath changed = createString("CHANGED");    
 
    public final StringPath uri = createString("URI_DIFETTO_PRODOTTO");
    
    public final NumberPath<Integer> effortCostoSviluppo = createNumber("EFFORT_COSTO_SVILUPPO", Integer.class);
    
    public final NumberPath<Short> flagUltimaSituazione = createNumber("FL_ULTIMA_SITUAZIONE", Short.class);
    
    public final com.mysema.query.sql.PrimaryKey<DmalmDifettoProdotto> dmalmDifettoProdottoPrimaryKey = createPrimaryKey(dmalmDifettoProdottoPk);

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.DmalmTempo> dmalmDifettoProdottoFk04 = createForeignKey(dmalmTempoFk04, "DMALM_TEMPO_PK");

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.DmalmProject> dmalmDifettoProdottoFk02 = createForeignKey(dmalmProjectFk02, "DMALM_PROJECT_PK");

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.DmalmStatoWorkitem> dmalmDifettoProdottoFk03 = createForeignKey(dmalmStatoWorkitemFk03, "DMALM_STATO_WORKITEM_PK");

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.DmalmStrutturaOrganizzativa> dmalmDifettoProdottoFk01 = createForeignKey(dmalmStrutturaOrgFk01, "DMALM_STRUTTURA_ORG_PK");
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
    
    //DM_ALM-223
    public final DateTimePath<java.sql.Timestamp> dataDisponibilita = createDateTime("DATA_DISPONIBILITA", java.sql.Timestamp.class);
    
    //DM_ALM-320
    public final StringPath priority = createString("PRIORITY");
    
public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmDifettoProdotto(String variable) {
        super(DmalmDifettoProdotto.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_DIFETTO_PRODOTTO");
    }

    public QDmalmDifettoProdotto(Path<? extends DmalmDifettoProdotto> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_DIFETTO_PRODOTTO");
    }

    public QDmalmDifettoProdotto(PathMetadata<?> metadata) {
        super(DmalmDifettoProdotto.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_DIFETTO_PRODOTTO");
    }

}

