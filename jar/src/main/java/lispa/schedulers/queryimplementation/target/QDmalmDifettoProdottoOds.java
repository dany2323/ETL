package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmDifettoProdottoOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmDifettoProdottoOds is a Querydsl query type for DmalmDifettoProdottoOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmDifettoProdottoOds extends com.mysema.query.sql.RelationalPathBase<DmalmDifettoProdottoOds> {

	
    private static final long serialVersionUID = 1764195283;

    public static final QDmalmDifettoProdottoOds dmalmDifettoProdottoOds = new QDmalmDifettoProdottoOds("DMALM_DIFETTO_PRODOTTO_ODS");
    
    public final StringPath cdDifetto = createString("IDENTIFICATIVO_DIFETTO");
    
    public final NumberPath<Long> dmalmDifettoProdottoPk = createNumber("DMALM_DIFETTO_PRODOTTO_PK", Long.class);
    
    public final NumberPath<Long> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Long.class);
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);
    
    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);
    
    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);
    
    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);
    
    public final StringPath dsAutoreDifetto = createString("NOME_AUTORE_DIFETTO");
    
    public final StringPath causaDifetto = createString("CAUSA_DIFETTO");
    
    public final StringPath naturaDifetto = createString("NATURA_DIFETTO");
    
    public final StringPath dsDifetto = createString("DESCRIZIONE_DIFETTO");
    
    public final StringPath idRepository = createString("IDENTIFICATIVO_REPOSITORY");
    
    public final DateTimePath<java.sql.Timestamp> dtCambioStatoDifetto = createDateTime("DATA_CAMBIO_STATO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoRecordDifetto = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtChiusuraDifetto = createDateTime("DATA_CHIUSURA_DIFETTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneDifetto = createDateTime("DATA_CREAZIONE_DIFETTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaRecordDifetto = createDateTime("DATA_MODIFICA_RECORD", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneDifetto = createDateTime("DATA_RISOLUZIONE_DIFETTO", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreDifetto = createString("USERID_AUTORE_DIFETTO");
    
    public final NumberPath<Integer>  idkAreaTematica = createNumber("DMALM_AREA_TEMATICA_FK_05", Integer.class);
    
    public final StringPath descrizioneStrutturaOrg = createString("CODICE_AREA_ORESTE");
    
    public final StringPath motivoRisoluzioneDifetto = createString("MOTIVO_RISOLUZIONE");
    
    public final NumberPath<Integer> nrGiorniFestivi = createNumber("GIORNI_FESTIVI", Integer.class);

    public final StringPath numeroLineaDifetto = createString("NUMERO_LINEA_RDI");

    public final StringPath numeroTestataDifetto = createString("NUMERO_TESTATA_RDI");

    public final StringPath provenienzaDifetto = createString("PROVENIENZA_DIFETTO");

    public final NumberPath<Double> rankStatoDifetto = createNumber("RANK_STATO_DIFETTO", Double.class);

    public final StringPath severity = createString("SEVERITY_DIFETTO");

    public final NumberPath<Double> tempoTotRisoluzioneDifetto = createNumber("TEMPO_TOTALE_RISOLUZIONE", Double.class);
    
    public final StringPath idProject = createString("ID_PROJECT");

    public final StringPath stgPk = createString("STG_PK");
 
    public final StringPath uri = createString("URI_DIFETTO_PRODOTTO");

    public final NumberPath<Integer> effortCostoSviluppo = createNumber("EFFORT_COSTO_SVILUPPO", Integer.class);
    
    public final NumberPath<Short> flagUltimaSituazione = createNumber("FL_ULTIMA_SITUAZIONE", Short.class);
    
	//DM_ALM-223
	public final DateTimePath<java.sql.Timestamp> dtDisponibilita = createDateTime(
			"DATA_DISPONIBILITA", java.sql.Timestamp.class);
	
	 //DM_ALM-320
    public final StringPath priority = createString("PRIORITY");

    public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmDifettoProdottoOds(String variable) {
        super(DmalmDifettoProdottoOds.class, forVariable(variable), "DMALM", "DMALM_DIFETTO_PRODOTTO_ODS");
    }

    public QDmalmDifettoProdottoOds(Path<? extends DmalmDifettoProdottoOds> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_DIFETTO_PRODOTTO_ODS");
    }

    public QDmalmDifettoProdottoOds(PathMetadata<?> metadata) {
        super(DmalmDifettoProdottoOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_DIFETTO_PRODOTTO_ODS");
    }

}

