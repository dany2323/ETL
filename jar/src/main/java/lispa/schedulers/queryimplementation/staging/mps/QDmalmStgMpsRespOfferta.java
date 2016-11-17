package lispa.schedulers.queryimplementation.staging.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.staging.mps.DmalmStgMpsRespOfferta;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmStgMpsRespOfferta extends
		com.mysema.query.sql.RelationalPathBase<DmalmStgMpsRespOfferta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4895584109351088017L;

	public static final QDmalmStgMpsRespOfferta dmalmStgMpsRespOfferta = new QDmalmStgMpsRespOfferta(
			"DMALM_STG_MPS_RESPON_OFFERTA");

	public final NumberPath<Integer> idContratto = createNumber("IDCONTRATTO",
			Integer.class);
	public final StringPath amNome = createString("AM_NOME");
	public final DateTimePath<java.sql.Timestamp> amDataFirma = createDateTime(
			"AM_DATAFIRMA", java.sql.Timestamp.class);
	public final StringPath amFirmato = createString("AM_FIRMATO");

	public final StringPath samNome = createString("SAM_NOME");
	public final DateTimePath<java.sql.Timestamp> samDataFirma = createDateTime(
			"SAM_DATAFIRMA", java.sql.Timestamp.class);
	public final StringPath samFirmato = createString("SAM_FIRMATO");

	public final StringPath vdgNome = createString("VDG_NOME");
	public final DateTimePath<java.sql.Timestamp> vdgDataFirma = createDateTime(
			"VDG_DATAFIRMA", java.sql.Timestamp.class);
	public final StringPath vdgFirmato = createString("VDG_FIRMATO");
	
	public final StringPath dgNome = createString("DG_NOME");
	public final DateTimePath<java.sql.Timestamp> dgDataFirma = createDateTime(
			"DG_DATAFIRMA", java.sql.Timestamp.class);
	public final StringPath dgFirmato = createString("DG_FIRMATO");

	public final StringPath cdNome = createString("CD_NOME");
	public final DateTimePath<java.sql.Timestamp> cdDataFirma = createDateTime(
			"CD_DATAFIRMA", java.sql.Timestamp.class);
	public final StringPath cdFirmato = createString("CD_FIRMATO");
	
	public final StringPath copertinaFirmata = createString("COPERTINA_FIRMATA");
	public final DateTimePath<java.sql.Timestamp> dataFirmaCopertina = createDateTime(
			"DATA_FIRMA_COPERTINA", java.sql.Timestamp.class);
	public final StringPath statoRazionale = createString("STATO_RAZIONALE");
	public final StringPath controller = createString("CONTROLLER");
	public final DateTimePath<java.sql.Timestamp> dataVerifica = createDateTime(
			"DATA_VERIFICA", java.sql.Timestamp.class);
	public final StringPath prossimoFirmatarioRazionale = createString("PROSSIMO_FIRMATARIO_RAZIONALE");
	public final StringPath razionaleDigitale = createString("RAZIONALE_DIGITALE");
	public final StringPath notaRazionale = createString("NOTA_RAZIONALE");
	public final StringPath motivazioneRigetto = createString("MOTIVAZIONE_RIGETTO");

	public QDmalmStgMpsRespOfferta(String variable) {
		super(DmalmStgMpsRespOfferta.class, forVariable(variable),
				DmAlmConstants.DMALM_STAGING_SCHEMA,
				"DMALM_STG_MPS_RESPON_OFFERTA");
	}

	public QDmalmStgMpsRespOfferta(Path<? extends DmalmStgMpsRespOfferta> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_STAGING_SCHEMA,
				"DMALM_STG_MPS_RESPON_OFFERTA");
	}

	public QDmalmStgMpsRespOfferta(PathMetadata<?> metadata) {
		super(DmalmStgMpsRespOfferta.class, metadata,
				DmAlmConstants.DMALM_STAGING_SCHEMA,
				"DMALM_STG_MPS_RESPON_OFFERTA");
	}

}
