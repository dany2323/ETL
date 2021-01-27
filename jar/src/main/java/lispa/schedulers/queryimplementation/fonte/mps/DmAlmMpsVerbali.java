package lispa.schedulers.queryimplementation.fonte.mps;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.constant.DmAlmConstants;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class DmAlmMpsVerbali extends
		com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.mps.DmAlmMpsVerbali> {

	private static final long serialVersionUID = 60974724922678381L;

	public static final DmAlmMpsVerbali dmalmMpsVerbali = new DmAlmMpsVerbali("DM_ALM_MPS_VERBALI");

	public final StringPath codVerbale = createString("CODVERBALE");
	public final DateTimePath<java.sql.Timestamp> dataVerbale = createDateTime(
			"DATA_VERBALE", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFirma = createDateTime(
			"DATA_FIRMA", java.sql.Timestamp.class);
	public final StringPath note = createString("NOTE");
	public final StringPath conforme = createString("CONFORME");
	public final StringPath tipoVerbale = createString("TIPO_VERBALE");
	public final StringPath statoVerbale = createString("STATO_VERBALE");
	public final NumberPath<Integer> totaleVerbale = createNumber(
			"TOTALE_VERBALE", Integer.class);
	public final NumberPath<Integer> fatturatoVerbale = createNumber(
			"FATTURATO_VERBALE", Integer.class);
	public final StringPath prossimoFirmatario = createString("PROSSIMO_FIRMATARIO");
	public final StringPath firmaDigitale = createString("FIRMA_DIGITALE");
	public final NumberPath<Integer> idVerbaleValidazione = createNumber(
			"IDVERBALEVALIDAZIONE", Integer.class);

	public DmAlmMpsVerbali(String variable) {
		super(lispa.schedulers.bean.fonte.mps.DmAlmMpsVerbali.class, forVariable(variable),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_VERBALI");
	}

	public DmAlmMpsVerbali(Path<? extends lispa.schedulers.bean.fonte.mps.DmAlmMpsVerbali> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_VERBALI");
	}

	public DmAlmMpsVerbali(PathMetadata<?> metadata) {
		super(lispa.schedulers.bean.fonte.mps.DmAlmMpsVerbali.class, metadata,
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_MPS_VERBALI");
	}

}
