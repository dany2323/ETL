package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmProgettoEse;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmProgettoEse is a Querydsl query type for DmalmProgettoEse
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProgettoEse extends com.mysema.query.sql.RelationalPathBase<DmalmProgettoEse> {

    private static final long serialVersionUID = 2137077928;

    public static final QDmalmProgettoEse dmalmProgettoEse = new QDmalmProgettoEse("DMALM_PROGETTO_ESE");

    public final StringPath stgPk = createString("STG_PK");
    public final StringPath changed = createString("CHANGED");    
 
        public final StringPath uri = createString("URI_PROGETTO_ESE");

    
    public final StringPath annullato = createString("ANNULLATO");

    public final StringPath cdProgettoEse = createString("CD_PROGETTO_ESE");

    public final StringPath cfCodice = createString("CF_CODICE");
    
    public final NumberPath<Integer> dmalmUserFk06 = createNumber("DMALM_USER_FK_06", Integer.class);

    public final DateTimePath<java.sql.Timestamp> cfDtUltimaSottomissione = createDateTime("CF_DT_ULTIMA_SOTTOMISSIONE", java.sql.Timestamp.class);

    public final StringPath descrizioneProgettoEse = createString("DESCRIZIONE_PROGETTO_ESE");

    public final NumberPath<Integer> dmalmProgettoEsePk = createNumber("DMALM_PROGETTO_ESE_PK", Integer.class);

    public final NumberPath<Integer> dmalmProjectFk02 = createNumber("DMALM_PROJECT_FK_02", Integer.class);

    public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber("DMALM_STATO_WORKITEM_FK_03", Integer.class);

    public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber("DMALM_STRUTTURA_ORG_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmTempoFk04 = createNumber("DMALM_TEMPO_FK_04", Integer.class);

    public final StringPath dsAutoreProgettoEse = createString("DS_AUTORE_PROGETTO_ESE");

    public final DateTimePath<java.sql.Timestamp> dtCambioStatoProgettoEse = createDateTime("DT_CAMBIO_STATO_PROGETTO_ESE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamentoProgettoEse = createDateTime("DT_CARICAMENTO_PROGETTO_ESE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCreazioneProgettoEse = createDateTime("DT_CREAZIONE_PROGETTO_ESE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaProgettoEse = createDateTime("DT_MODIFICA_PROGETTO_ESE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtRisoluzioneProgettoEse = createDateTime("DT_RISOLUZIONE_PROGETTO_ESE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtScadenzaProgettoEse = createDateTime("DT_SCADENZA_PROGETTO_ESE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idAutoreProgettoEse = createString("ID_AUTORE_PROGETTO_ESE");

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath motivoRisoluzioneProgEse = createString("MOTIVO_RISOLUZIONE_PROG_ESE");

    public final NumberPath<Double> rankStatoProgettoEse = createNumber("RANK_STATO_PROGETTO_ESE", Double.class);

    public final NumberPath<Short> rankStatoProgettoEseMese = createNumber("RANK_STATO_PROGETTO_ESE_MESE", Short.class);

    public final StringPath titoloProgettoEse = createString("TITOLO_PROGETTO_ESE");

    public final com.mysema.query.sql.PrimaryKey<DmalmProgettoEse> sysC0022481 = createPrimaryKey(dmalmProgettoEsePk);
    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);

  //DM_ALM-320
    public final StringPath severity = createString("SEVERITY");
        
    public final StringPath priority = createString("PRIORITY");
    
    public final DateTimePath<java.sql.Timestamp> ts_tag_alm = createDateTime("TS_TAG_ALM", java.sql.Timestamp.class);

    public final StringPath tag_alm = createString("TAG_ALM");

    
    public QDmalmProgettoEse(String variable) {
        super(DmalmProgettoEse.class, forVariable(variable), "DMALM", "DMALM_PROGETTO_ESE");
    }

    public QDmalmProgettoEse(Path<? extends DmalmProgettoEse> path) {
        super(path.getType(), path.getMetadata(), "DMALM", "DMALM_PROGETTO_ESE");
    }

    public QDmalmProgettoEse(PathMetadata<?> metadata) {
        super(DmalmProgettoEse.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PROGETTO_ESE");
    }

}

