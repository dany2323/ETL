package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmReleaseDiProgettoOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmReleaseDiProgettoOds is a Querydsl query type for DmalmReleaseDiProgettoOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmReleaseDiProgettoOds extends com.mysema.query.sql.RelationalPathBase<DmalmReleaseDiProgettoOds> {

    private static final long serialVersionUID = -1019992487;

    public static final QDmalmReleaseDiProgettoOds dmalmReleaseDiProgettoOds = new QDmalmReleaseDiProgettoOds("DMALM_RELEASE_DI_PROGETTO_ODS");

    public final StringPath cdReleasediprog = createString("CD_RELEASEDIPROG");

    public final StringPath codice = createString("CODICE");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final DateTimePath<java.sql.Timestamp> dataDisponibilitaEff = createDateTime("DATA_DISPONIBILITA_EFF", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dataPassaggioInEsercizio = createDateTime("DATA_PASSAGGIO_IN_ESERCIZIO", java.sql.Timestamp.class);

    public final StringPath descrizioneReleasediprog = createString("DESCRIZIONE_RELEASEDIPROG");

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmReleasediprogPk = createNumber("DMALM_RELEASEDIPROG_PK", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final NumberPath<Integer> dmalmAreaTematicaFk05 = createNumber("DMALM_AREA_TEMATICA_FK_05", Integer.class);
    
    public final StringPath dsAutoreReleasediprog = createString("DS_AUTORE_RELEASEDIPROG");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoReleasediprog = createDateTime("DT_CAMBIO_STATO_RELEASEDIPROG", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoReleasediprog = createDateTime("DT_CARICAMENTO_RELEASEDIPROG", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneReleasediprog = createDateTime("DT_CREAZIONE_RELEASEDIPROG", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaReleasediprog = createDateTime("DT_MODIFICA_RELEASEDIPROG", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneReleasediprog = createDateTime("DT_RISOLUZIONE_RELEASEDIPROG", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaReleasediprog = createDateTime("DT_SCADENZA_RELEASEDIPROG", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath fornitura = createString("FORNITURA");

    public final BooleanPath fr = createBoolean("FR");

    public final StringPath idAutoreReleasediprog = createString("ID_AUTORE_RELEASEDIPROG");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneRelProg = createString("MOTIVO_RISOLUZIONE_REL_PROG");

    public final StringPath numeroLinea = createString("NUMERO_LINEA");

    public final StringPath numeroTestata = createString("NUMERO_TESTATA");

    public final NumberPath<Double> rankStatoReleasediprog = createNumber("RANK_STATO_RELEASEDIPROG", Double.class);

    public final NumberPath<Short> rankStatoReleasediprogMese = createNumber("RANK_STATO_RELEASEDIPROG_MESE", Short.class);

    public final StringPath stgPk = createString("STG_PK");
 
        public final StringPath uri = createString("URI_RELEASE_DI_PROGETTO");


    public final StringPath titoloReleasediprog = createString("TITOLO_RELEASEDIPROG");

    public final StringPath versione = createString("VERSIONE");
    
    public final DateTimePath<java.sql.Timestamp> dtInizioQF = createDateTime(
			"DT_INIZIO_QUICKFIX", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtFineQF = createDateTime(
			"DT_FINE_QUICKFIX", java.sql.Timestamp.class);

	public final NumberPath<Integer> numQuickFix = createNumber(
			"NUM_QUICKFIX", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<DmalmReleaseDiProgettoOds> sysC0023176 = createPrimaryKey(dmalmReleasediprogPk);

    public QDmalmReleaseDiProgettoOds(String variable) {
        super(DmalmReleaseDiProgettoOds.class, forVariable(variable), "DMALM", "DMALM_RELEASE_DI_PROGETTO_ODS");
    }

    public QDmalmReleaseDiProgettoOds(Path<? extends DmalmReleaseDiProgettoOds> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_RELEASE_DI_PROGETTO_ODS");
    }

    public QDmalmReleaseDiProgettoOds(PathMetadata<?> metadata) {
        super(DmalmReleaseDiProgettoOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RELEASE_DI_PROGETTO_ODS");
    }

}

