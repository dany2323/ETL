package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmPeiOds;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmPeiOds is a Querydsl query type for DmalmPeiOds
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmPeiOds extends com.mysema.query.sql.RelationalPathBase<DmalmPeiOds> {

    private static final long serialVersionUID = -1005251883;

    public static final QDmalmPeiOds dmalmPeiOds = new QDmalmPeiOds("DMALM_PEI_ODS");

    public final StringPath cdPei = createString("CD_PEI");

    public final StringPath codice = createString("CODICE");

    public final StringPath descrizionePei = createString("DESCRIZIONE_PEI");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final NumberPath<Integer> dmalmPeiPk = createNumber("DMALM_PEI_PK", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutorePei = createString("DS_AUTORE_PEI");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoPei = createDateTime("DT_CAMBIO_STATO_PEI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoPei = createDateTime("DT_CARICAMENTO_PEI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazionePei = createDateTime("DT_CREAZIONE_PEI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaPei = createDateTime("DT_MODIFICA_PEI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtPrevistaComplReq = createDateTime("DT_PREVISTA_COMPL_REQ", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtPrevistaPassInEs = createDateTime("DT_PREVISTA_PASS_IN_ES", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzionePei = createDateTime("DT_RISOLUZIONE_PEI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaPei = createDateTime("DT_SCADENZA_PEI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutorePei = createString("ID_AUTORE_PEI");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzionePei = createString("MOTIVO_RISOLUZIONE_PEI");

    public final NumberPath<Double> rankStatoPei = createNumber("RANK_STATO_PEI", Double.class);

    public final NumberPath<Short> rankStatoPeiMese = createNumber("RANK_STATO_PEI_MESE", Short.class);

    public final StringPath stgPk = createString("STG_PK");
 
     
    public final StringPath uri = createString("URI_PEI");


    public final StringPath titoloPei = createString("TITOLO_PEI");

    public final com.mysema.query.sql.PrimaryKey<DmalmPeiOds> sysC0023624 = createPrimaryKey(dmalmPeiPk);

    public QDmalmPeiOds(String variable) {
        super(DmalmPeiOds.class, forVariable(variable), "DMALM", "DMALM_PEI_ODS");
    }

    public QDmalmPeiOds(Path<? extends DmalmPeiOds> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_PEI_ODS");
    }

    public QDmalmPeiOds(PathMetadata<?> metadata) {
        super(DmalmPeiOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PEI_ODS");
    }

}

