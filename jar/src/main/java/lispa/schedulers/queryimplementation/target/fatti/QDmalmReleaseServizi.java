package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmReleaseServizi;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmReleaseServizi is a Querydsl query type for DmalmReleaseServizi
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmReleaseServizi extends com.mysema.query.sql.RelationalPathBase<DmalmReleaseServizi> {

    private static final long serialVersionUID = -1430122554;

    public static final QDmalmReleaseServizi dmalmReleaseServizi = new QDmalmReleaseServizi("DMALM_RELEASE_SERVIZI");

    public final StringPath cdRelServizi = createString("CD_REL_SERVIZI");
    
    public final StringPath annullato = createString("ANNULLATO");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final StringPath descrizioneRelServizi = createString("DESCRIZIONE_REL_SERVIZI");

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmRelServiziPk = createNumber("DMALM_REL_SERVIZI_PK", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreRelServizi = createString("DS_AUTORE_REL_SERVIZI");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoRelServizi = createDateTime("DT_CAMBIO_STATO_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoRelServizi = createDateTime("DT_CARICAMENTO_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneRelServizi = createDateTime("DT_CREAZIONE_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaRelServizi = createDateTime("DT_MODIFICA_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneRelServizi = createDateTime("DT_RISOLUZIONE_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaRelServizi = createDateTime("DT_SCADENZA_REL_SERVIZI", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreRelServizi = createString("ID_AUTORE_REL_SERVIZI");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneRelServizi = createString("MOTIVO_RISOLUZIONE_REL_SERVIZI");

    public final StringPath motivoSospensioneReleaseSer = createString("MOTIVO_SOSPENSIONE_RELEASE_SER");

    public final StringPath previstoFermoServizioRel = createString("PREVISTO_FERMO_SERVIZIO_REL");

    public final NumberPath<Double> rankStatoRelServizi = createNumber("RANK_STATO_REL_SERVIZI", Double.class);

    public final NumberPath<Short> rankStatoRelServiziMese = createNumber("RANK_STATO_REL_SERVIZI_MESE", Short.class);

    public final StringPath richiestaAnalisiImpattiRel = createString("RICHIESTA_ANALISI_IMPATTI_REL");

    public final StringPath stgPk = createString("STG_PK");
    public final StringPath changed = createString("CHANGED");    
 
    public final StringPath uri = createString("URI_RELEASE_SERVIZI");


    public final StringPath titoloRelServizi = createString("TITOLO_REL_SERVIZI");

    public final com.mysema.query.sql.PrimaryKey<DmalmReleaseServizi> sysC0022484 = createPrimaryKey(dmalmRelServiziPk);
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);

  //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
        
    public final StringPath priority = createString("PRIORITY");
    
public final StringPath tagAlm = createString("TAG_ALM");
    
    public final DateTimePath<java.sql.Timestamp> tsTagAlm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);
    
    public QDmalmReleaseServizi(String variable) {
        super(DmalmReleaseServizi.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RELEASE_SERVIZI");
    }

    public QDmalmReleaseServizi(Path<? extends DmalmReleaseServizi> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RELEASE_SERVIZI");
    }

    public QDmalmReleaseServizi(PathMetadata<?> metadata) {
        super(DmalmReleaseServizi.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_RELEASE_SERVIZI");
    }

}

