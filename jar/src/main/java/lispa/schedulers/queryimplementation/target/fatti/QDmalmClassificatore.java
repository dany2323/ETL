package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import lispa.schedulers.bean.target.fatti.DmalmClassificatore;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.*;

/**
 * QDmalmClassificatoreDemand is a Querydsl query type for DmalmPei
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmClassificatore extends
		com.mysema.query.sql.RelationalPathBase<DmalmClassificatore> {

	private static final long serialVersionUID = -809853284449229037L;

	public static final QDmalmClassificatore dmalmClassificatore = new QDmalmClassificatore(
			"DMALM_CLASSIFICATORE");

	public final StringPath annullato = createString("ANNULLATO");

	public final StringPath cd_classificatore = createString("CD_CLASSIFICATORE");

	public final StringPath cf_ambito = createString("CF_AMBITO");

	public final StringPath cf_area = createString("CF_AREA");

	public final StringPath cf_riferimenti = createString("CF_RIFERIMENTI");

	public final StringPath cf_scheda_servizio = createString("CF_SCHEDA_SERVIZIO");

	public final StringPath changed = createString("CHANGED");

	public final NumberPath<Integer> dmalmAreaTematicaFk05 = createNumber(
			"DMALM_AREA_TEMATICA_FK_05", Integer.class);

	public final NumberPath<Integer> dmalmClassificatorePk = createNumber(
			"DMALM_CLASSIFICATORE_PK", Integer.class);

	public final NumberPath<Integer> dmalmProjectFk02 = createNumber(
			"DMALM_PROJECT_FK_02", Integer.class);

	public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber(
			"DMALM_STATO_WORKITEM_FK_03", Integer.class);

	public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber(
			"DMALM_STRUTTURA_ORG_FK_01", Integer.class);

	public final NumberPath<Integer> dmalmTempoFk04 = createNumber(
			"DMALM_TEMPO_FK_04", Integer.class);

	public final NumberPath<Integer> dmalmUserFk06 = createNumber(
			"DMALM_USER_FK_06", Integer.class);

	public final StringPath dsAutoreClassificatore = createString("DS_AUTORE_CLASSIFICATORE");

	public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime(
			"DT_ANNULLAMENTO", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtCambioStatoClassif = createDateTime(
			"DT_CAMBIO_STATO_CLASSIF", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtCaricamentoClassif = createDateTime(
			"DT_CARICAMENTO_CLASSIF", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtCreazioneClassif = createDateTime(
			"DT_CREAZIONE_CLASSIF", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtModificaClassif = createDateTime(
			"DT_MODIFICA_CLASSIF", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtRisoluzioneClassif = createDateTime(
			"DT_RISOLUZIONE_CLASSIF", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtScadenzaProgSvil = createDateTime(
			"DT_SCADENZA_PROG_SVIL_D", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime(
			"DT_STORICIZZAZIONE", java.sql.Timestamp.class);

	public final StringPath idAutoreClassificatore = createString("ID_AUTORE_CLASSIFICATORE");

	public final StringPath idRepository = createString("ID_REPOSITORY");

	public final NumberPath<Short> rankStatoClassifMese = createNumber(
			"RANK_STATO_CLASSIF_MESE", Short.class);

	public final NumberPath<Double> rankStatoClassificatore = createNumber(
			"RANK_STATO_CLASSIFICATORE", Double.class);

	public final StringPath stgPk = createString("STG_PK");

	public final StringPath titoloClassificatore = createString("TITOLO_CLASSIFICATORE");

	public final StringPath uriClassficatore = createString("URI_CLASSIFICATORE");

	public final com.mysema.query.sql.PrimaryKey<DmalmClassificatore> sysC0022479 = createPrimaryKey(dmalmClassificatorePk);

	//DM_ALM-239
	public final StringPath rmResponsabiliProgetto = createString("RM_RESPONSABILI_PROGETTO");
	
	public final BooleanPath progettoInDeroga = createBoolean("PROGETTO_IN_DEROGA");
	
	public final StringPath assigneeProgettoItInDeroga = createString("ASSIGNEE_PROGETTO_IT_IN_DEROGA");
	
	public final StringPath locationSorgenti = createString("LOCATION_SORGENTI");

	public final StringPath type = createString("TYPE");

	public QDmalmClassificatore(String variable) {
		super(DmalmClassificatore.class, forVariable(variable), "DMALM",
				"DMALM_CLASSIFICATORE");
	}

	public QDmalmClassificatore(Path<? extends DmalmClassificatore> path) {
		super(path.getType(), path.getMetadata(), "DMALM",
				"DMALM_CLASSIFICATORE");
	}

	public QDmalmClassificatore(PathMetadata<?> metadata) {
		super(DmalmClassificatore.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_CLASSIFICATORE");
	}

}
