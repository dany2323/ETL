package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import lispa.schedulers.bean.target.DmalmClassificatoreDemOds;
import lispa.schedulers.constant.DmAlmConstants;

/**
 * QDmalmClassificatoreDemand is a Querydsl query type for DmalmPei
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmClassificatoreDemOds extends
		com.mysema.query.sql.RelationalPathBase<DmalmClassificatoreDemOds> {

	private static final long serialVersionUID = 4289461653051025521L;

	public static final QDmalmClassificatoreDemOds dmalmClassificatoreDemand = new QDmalmClassificatoreDemOds(
			"DMALM_CLASSIFICATORE_DEM_ODS");
	
	public final StringPath cd_classificatore = createString("CD_CLASSIFICATORE");

	public final StringPath cf_ambito = createString("CF_AMBITO");

	public final StringPath cf_area = createString("CF_AREA");

	public final StringPath cf_riferimenti = createString("CF_RIFERIMENTI");

	public final StringPath cf_scheda_servizio = createString("CF_SCHEDA_SERVIZIO");

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

	public final com.mysema.query.sql.PrimaryKey<DmalmClassificatoreDemOds> sysC0022479 = createPrimaryKey(dmalmClassificatorePk);

	public QDmalmClassificatoreDemOds(Path<? extends DmalmClassificatoreDemOds> path) {
		super(path.getType(), path.getMetadata(), "DMALM",
				"DMALM_CLASSIFICATORE_DEM_ODS");
	}
	
	public QDmalmClassificatoreDemOds (String variable) {
		super(DmalmClassificatoreDemOds.class, forVariable(variable), "DMALM",
				"DMALM_CLASSIFICATORE_DEM_ODS");
	}
	
	public QDmalmClassificatoreDemOds(PathMetadata<?> metadata) {
		super(DmalmClassificatoreDemOds.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_CLASSIFICATORE_DEM_ODS");
	}
	
}
