package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmDocumentoOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmDocumentoOds is a Querydsl query type for DmalmDocumentoOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmDocumentoOds extends com.mysema.query.sql.RelationalPathBase<DmalmDocumentoOds> {

    private static final long serialVersionUID = 364423413;

    public static final QDmalmDocumentoOds dmalmDocumentoOds = new QDmalmDocumentoOds("DMALM_DOCUMENTO_ODS");

    public final StringPath assigneesDocumento = createString("ASSIGNEES_DOCUMENTO");

    public final StringPath cdDocumento = createString("CD_DOCUMENTO");

    public final StringPath classificazione = createString("CLASSIFICAZIONE");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final StringPath codice = createString("CODICE");

    public final StringPath descrizioneDocumento = createString("DESCRIZIONE_DOCUMENTO");

    public final NumberPath<Integer> dmalmAreaTematicaFk05 = createNumber("DMALM_AREA_TEMATICA_FK_05", Integer.class);

    public final NumberPath<Integer> dmalmDocumentoPk = createNumber("DMALM_DOCUMENTO_PK", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreDocumento = createString("DS_AUTORE_DOCUMENTO");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoDocumento = createDateTime("DT_CAMBIO_STATO_DOCUMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoDocumento = createDateTime("DT_CARICAMENTO_DOCUMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneDocumento = createDateTime("DT_CREAZIONE_DOCUMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaDocumento = createDateTime("DT_MODIFICA_DOCUMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneDocumento = createDateTime("DT_RISOLUZIONE_DOCUMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaDocumento = createDateTime("DT_SCADENZA_DOCUMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreDocumento = createString("ID_AUTORE_DOCUMENTO");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneDocumento = createString("MOTIVO_RISOLUZIONE_DOCUMENTO");

    public final StringPath numeroLinea = createString("NUMERO_LINEA");

    public final StringPath numeroTestata = createString("NUMERO_TESTATA");

    public final NumberPath<Double> rankStatoDocumento = createNumber("RANK_STATO_DOCUMENTO", Double.class);

    public final NumberPath<Short> rankStatoDocumentoMese = createNumber("RANK_STATO_DOCUMENTO_MESE", Short.class);

    public final StringPath stgPk = createString("STG_PK");
 
        public final StringPath uri = createString("URI_DOCUMENTO");


    public final StringPath tipo = createString("TIPO");

    public final StringPath titoloDocumento = createString("TITOLO_DOCUMENTO");

    public final StringPath versione = createString("VERSIONE");

    public final com.mysema.query.sql.PrimaryKey<DmalmDocumentoOds> sysC0023023 = createPrimaryKey(dmalmDocumentoPk);
    
    //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");

    public final StringPath priority = createString("PRIORITY");

    public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmDocumentoOds(String variable) {
        super(DmalmDocumentoOds.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_DOCUMENTO_ODS");
    }

    public QDmalmDocumentoOds(Path<? extends DmalmDocumentoOds> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_DOCUMENTO_ODS");
    }

    public QDmalmDocumentoOds(PathMetadata<?> metadata) {
        super(DmalmDocumentoOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_DOCUMENTO_ODS");
    }

}

