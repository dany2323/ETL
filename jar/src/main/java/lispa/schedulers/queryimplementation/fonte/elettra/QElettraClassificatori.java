package lispa.schedulers.queryimplementation.fonte.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.fonte.elettra.ElettraClassificatori;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;

public class QElettraClassificatori extends
com.mysema.query.sql.RelationalPathBase<ElettraClassificatori> {
	private static final long serialVersionUID = 1119269792630080031L;

	public static final QElettraClassificatori elettraClassificatori = new QElettraClassificatori(
			"DM_ALM_EL_CLASSIFICATORI");
	
	public final StringPath idClassificatore = createString("ID Classificatore Oreste");
	public final StringPath codiceClassificatore = createString("Codice Classificatore Oreste");
	public final StringPath tipoClassificatore = createString("Tipo Classificatore Oreste");
	
	public QElettraClassificatori(String variable) {
		super(ElettraClassificatori.class, forVariable(variable),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_CLASSIFICATORI");
	}

	public QElettraClassificatori(Path<? extends ElettraClassificatori> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_CLASSIFICATORI");
	}

	public QElettraClassificatori(PathMetadata<?> metadata) {
		super(ElettraClassificatori.class, metadata,
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_CLASSIFICATORI");
	}
}
