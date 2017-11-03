package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmTestcaseOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmTestcaseOds is a Querydsl query type for DmalmTestcaseOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmTestcaseOds extends com.mysema.query.sql.RelationalPathBase<DmalmTestcaseOds> {

    private static final long serialVersionUID = 2020556465;

    public static final QDmalmTestcaseOds dmalmTestcaseOds = new QDmalmTestcaseOds("DMALM_TESTCASE_ODS");

    public final StringPath cdTestcase = createString("CD_TESTCASE");

    public final StringPath codice = createString("CODICE");

    public final DateTimePath<java.sql.Timestamp> dataEsecuzioneTestcase = createDateTime("DATA_ESECUZIONE_TESTCASE", java.sql.Timestamp.class);

    public final StringPath descrizioneTestcase = createString("DESCRIZIONE_TESTCASE");

    public final NumberPath<Integer> dmalmAreaTematicaFk05 = createNumber("DMALM_AREA_TEMATICA_FK_05", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final NumberPath<Integer> dmalmTestcasePk = createNumber("DMALM_TESTCASE_PK", Integer.class);

    public final StringPath dsAutoreTestcase = createString("DS_AUTORE_TESTCASE");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoTestcase = createDateTime("DT_CAMBIO_STATO_TESTCASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoTestcase = createDateTime("DT_CARICAMENTO_TESTCASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneTestcase = createDateTime("DT_CREAZIONE_TESTCASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaTestcase = createDateTime("DT_MODIFICA_TESTCASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneTestcase = createDateTime("DT_RISOLUZIONE_TESTCASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaTestcase = createDateTime("DT_SCADENZA_TESTCASE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreTestcase = createString("ID_AUTORE_TESTCASE");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneTestcase = createString("MOTIVO_RISOLUZIONE_TESTCASE");

    public final StringPath numeroLinea = createString("NUMERO_LINEA");

    public final StringPath numeroTestata = createString("NUMERO_TESTATA");

    public final NumberPath<Double> rankStatoTestcase = createNumber("RANK_STATO_TESTCASE", Double.class);

    public final NumberPath<Short> rankStatoTestcaseMese = createNumber("RANK_STATO_TESTCASE_MESE", Short.class);

    public final StringPath stgPk = createString("STG_PK");
 
    public final StringPath uri = createString("URI_TESTCASE");

    public final StringPath titoloTestcase = createString("TITOLO_TESTCASE");

    public final com.mysema.query.sql.PrimaryKey<DmalmTestcaseOds> sysC0023105 = createPrimaryKey(dmalmTestcasePk);

    //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
    
    public final StringPath priority = createString("PRIORITY");
    
    
    public QDmalmTestcaseOds(String variable) {
        super(DmalmTestcaseOds.class, forVariable(variable), "DMALM", "DMALM_TESTCASE_ODS");
    }

    public QDmalmTestcaseOds(Path<? extends DmalmTestcaseOds> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_TESTCASE_ODS");
    }

    public QDmalmTestcaseOds(PathMetadata<?> metadata) {
        super(DmalmTestcaseOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TESTCASE_ODS");
    }

}

