package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmStrutturaOrganizzativa;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmStrutturaOrganizzativa is a Querydsl query type for DmalmStrutturaOrganizzativa
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmStrutturaOrganizzativa extends com.mysema.query.sql.RelationalPathBase<DmalmStrutturaOrganizzativa> {

    private static final long serialVersionUID = 726974772;

    public static final QDmalmStrutturaOrganizzativa dmalmStrutturaOrganizzativa = new QDmalmStrutturaOrganizzativa("DMALM_UNITA_ORGANIZZATIVA");

    public final StringPath cdArea = createString("CD_AREA");

    public final StringPath cdEnte = createString("CD_ENTE");

    public final StringPath cdResponsabileArea = createString("CD_RESPONSABILE_AREA");

    public final StringPath cdUoSuperiore = createString("CD_UO_SUPERIORE");

    public final StringPath cdVisibilita = createString("CD_VISIBILITA");

    public final NumberPath<Integer> dmalmStrutturaOrgPk = createNumber("DMALM_UNITA_ORG_PK", Integer.class);

    public final StringPath dnResponsabileArea = createString("DN_RESPONSABILE_AREA");

    public final StringPath dsAreaEdma = createString("DS_AREA_EDMA");
    
    public final StringPath email = createString("INDIRIZZO_EMAIL");
    
    public final StringPath dsUoSuperiore = createString("DS_UO_SUPERIORE");

    public final DateTimePath<java.sql.Timestamp> dtAttivazione = createDateTime("DT_ATTIVAZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtDisattivazione = createDateTime("DT_DISATTIVAZIONE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dtFineValiditaEdma = createDateTime("DT_FINE_VALIDITA_EDMA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValiditaEdma = createDateTime("DT_INIZIO_VALIDITA_EDMA", java.sql.Timestamp.class);

    public final NumberPath<Integer> idEdma = createNumber("ID_EDMA", Integer.class);
    
    public final NumberPath<Integer> interno = createNumber("INTERNO", Integer.class);

    public final NumberPath<Integer> idGradoUfficio = createNumber("ID_GRADO_UFFICIO", Integer.class);

    public final NumberPath<Integer> idSede = createNumber("ID_SEDE", Integer.class);

    public final NumberPath<Integer> idTipologiaUfficio = createNumber("ID_TIPOLOGIA_UFFICIO", Integer.class); 

    public final StringPath note = createString("NOTE");

    public final StringPath annullato = createString("ANNULLATO");
    
    public final com.mysema.query.sql.PrimaryKey<DmalmStrutturaOrganizzativa> dmalmStrutturaOrgPrimaryKey = createPrimaryKey(dmalmStrutturaOrgPk);

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto> _dmalmAnomaliaProdottoFk01 = createInvForeignKey(dmalmStrutturaOrgPk, "DMALM_STRUTTURA_ORG_FK_01");

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto> _dmalmDifettoProdottoFk01 = createInvForeignKey(dmalmStrutturaOrgPk, "DMALM_STRUTTURA_ORG_FK_01");

    public QDmalmStrutturaOrganizzativa(String variable) {
        super(DmalmStrutturaOrganizzativa.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_UNITA_ORGANIZZATIVA");
    }

    public QDmalmStrutturaOrganizzativa(Path<? extends DmalmStrutturaOrganizzativa> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_UNITA_ORGANIZZATIVA");
    }

    public QDmalmStrutturaOrganizzativa(PathMetadata<?> metadata) {
        super(DmalmStrutturaOrganizzativa.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_UNITA_ORGANIZZATIVA");
    }

}

