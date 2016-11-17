package lispa.schedulers.queryimplementation.target.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import lispa.schedulers.bean.target.mps.DmalmMpsVerbali;
import lispa.schedulers.constant.DmAlmConstants;

public class QDmalmMpsVerbali extends com.mysema.query.sql.RelationalPathBase<DmalmMpsVerbali> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3307872658021124103L;

	public static final QDmalmMpsVerbali dmalmMpsVerbali = new QDmalmMpsVerbali(
			"MPS_BO_VERBALI");

	public final StringPath codVerbale = createString("CODVERBALE");

	public final DateTimePath<java.sql.Timestamp> dataVerbale = createDateTime(
			"DATA_VERBALE", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dataFirma = createDateTime(
			"DATA_FIRMA", java.sql.Timestamp.class);

	public final StringPath note = createString("NOTE");

	public final StringPath conforme = createString("CONFORME");

	public final StringPath tipoVerbale = createString("TIPO_VERBALE");

	public final StringPath statoVerbale = createString("STATO_VERBALE");

	public final NumberPath<Integer> totaleVerbale = createNumber("TOTALE_VERBALE",
			Integer.class);

	public final NumberPath<Integer> fatturatoVerbale = createNumber("FATTURATO_VERBALE",
			Integer.class);

	public final StringPath prossimoFirmatario = createString("PROSSIMO_FIRMATARIO");

	public final StringPath firmaDigitale = createString("FIRMA_DIGITALE");

	public final NumberPath<Integer> idVerbaleValidazione = createNumber("IDVERBALEVALIDAZIONE",
			Integer.class);

	public QDmalmMpsVerbali(String variable) {
		super(DmalmMpsVerbali.class, forVariable(variable),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_VERBALI");
	}

	public QDmalmMpsVerbali(Path<? extends DmalmMpsVerbali> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_VERBALI");
	}

	public QDmalmMpsVerbali(PathMetadata<?> metadata) {
		super(DmalmMpsVerbali.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "MPS_BO_VERBALI");
	}
	
	

}
