package lispa.schedulers.queryimplementation.target.fatti;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaManutenzione;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;

/**
 * QDmalmRichiestaManutenzione is a Querydsl query type for
 * DmalmRichiestaManutenzione
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmRichiestaManutenzione extends
		com.mysema.query.sql.RelationalPathBase<DmalmRichiestaManutenzione> {

	private static final long serialVersionUID = 1940934610;

	public static final QDmalmRichiestaManutenzione dmalmRichiestaManutenzione = new QDmalmRichiestaManutenzione(
			"DMALM_RICHIESTA_MANUTENZIONE");

	public final StringPath cdRichiestaManutenzione = createString("CD_RICHIESTA_MANUTENZIONE");

	public final StringPath classeDiFornitura = createString("CLASSE_DI_FORNITURA");

	public final StringPath annullato = createString("ANNULLATO");

	public final StringPath codice = createString("CODICE");

	public final NumberPath<Integer> dmalmUserFk06 = createNumber(
			"DMALM_USER_FK_06", Integer.class);

	public final DateTimePath<java.sql.Timestamp> dataChiusuraRichManut = createDateTime(
			"DATA_CHIUSURA_RICH_MANUT", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dataDispEffettiva = createDateTime(
			"DATA_DISP_EFFETTIVA", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dataDispPianificata = createDateTime(
			"DATA_DISP_PIANIFICATA", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dataInizioEffettivo = createDateTime(
			"DATA_INIZIO_EFFETTIVO", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dataInizioPianificato = createDateTime(
			"DATA_INIZIO_PIANIFICATO", java.sql.Timestamp.class);

	public final StringPath descrizioneRichManutenzione = createString("DESCRIZIONE_RICH_MANUTENZIONE");

	public final NumberPath<Integer> dmalmProjectFk02 = createNumber(
			"DMALM_PROJECT_FK_02", Integer.class);

	public final NumberPath<Integer> dmalmRichManutenzionePk = createNumber(
			"DMALM_RICH_MANUTENZIONE_PK", Integer.class);

	public final NumberPath<Integer> dmalmStatoWorkitemFk03 = createNumber(
			"DMALM_STATO_WORKITEM_FK_03", Integer.class);

	public final NumberPath<Integer> dmalmStrutturaOrgFk01 = createNumber(
			"DMALM_STRUTTURA_ORG_FK_01", Integer.class);

	public final NumberPath<Integer> dmalmTempoFk04 = createNumber(
			"DMALM_TEMPO_FK_04", Integer.class);

	public final StringPath dsAutoreRichManutenzione = createString("DS_AUTORE_RICH_MANUTENZIONE");

	public final DateTimePath<java.sql.Timestamp> dtCambioStatoRichManut = createDateTime(
			"DT_CAMBIO_STATO_RICH_MANUT", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtCaricamentoRichManut = createDateTime(
			"DT_CARICAMENTO_RICH_MANUT", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtCreazioneRichManutenzione = createDateTime(
			"DT_CREAZIONE_RICH_MANUTENZIONE", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtModificaRichManutenzione = createDateTime(
			"DT_MODIFICA_RICH_MANUTENZIONE", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtRisoluzioneRichManut = createDateTime(
			"DT_RISOLUZIONE_RICH_MANUT", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtScadenzaRichManutenzione = createDateTime(
			"DT_SCADENZA_RICH_MANUTENZIONE", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime(
			"DT_STORICIZZAZIONE", java.sql.Timestamp.class);

	public final NumberPath<Short> durataEffettivaRichMan = createNumber(
			"DURATA_EFFETTIVA_RICH_MAN", Short.class);

	public final StringPath idAutoreRichManutenzione = createString("ID_AUTORE_RICH_MANUTENZIONE");

	public final StringPath idRepository = createString("ID_REPOSITORY");

	public final StringPath motivoRisoluzioneRichManut = createString("MOTIVO_RISOLUZIONE_RICH_MANUT");

	public final NumberPath<Short> rankStatoRichManutMese = createNumber(
			"RANK_STATO_RICH_MANUT_MESE", Short.class);

	public final NumberPath<Double> rankStatoRichManutenzione = createNumber(
			"RANK_STATO_RICH_MANUTENZIONE", Double.class);

	public final StringPath titoloRichiestaManutenzione = createString("TITOLO_RICHIESTA_MANUTENZIONE");

	public final com.mysema.query.sql.PrimaryKey<DmalmRichiestaManutenzione> sysC0022475 = createPrimaryKey(dmalmRichManutenzionePk);

	public final StringPath stgPk = createString("STG_PK");
	public final StringPath changed = createString("CHANGED");

	public final StringPath uri = createString("URI_RICHIESTA_MANUTENZIONE");

	public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime(
			"DT_ANNULLAMENTO", java.sql.Timestamp.class);

	//DM_ALM-320
	public final StringPath severity = createString("SEVERITY");
	    
	public final StringPath priority = createString("PRIORITY");
	
	
	public QDmalmRichiestaManutenzione(String variable) {
		super(DmalmRichiestaManutenzione.class, forVariable(variable), "DMALM",
				"DMALM_RICHIESTA_MANUTENZIONE");
	}

	public QDmalmRichiestaManutenzione(
			Path<? extends DmalmRichiestaManutenzione> path) {
		super(path.getType(), path.getMetadata(), "DMALM",
				"DMALM_RICHIESTA_MANUTENZIONE");
	}

	public QDmalmRichiestaManutenzione(PathMetadata<?> metadata) {
		super(DmalmRichiestaManutenzione.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_RICHIESTA_MANUTENZIONE");
	}

}
