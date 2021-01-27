package lispa.schedulers.queryimplementation.fonte.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.fonte.elettra.ElettraAmbienteTecnologicoClassificatori;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;

public class QElettraAmbienteTecnologicoClassificatori extends com.mysema.query.sql.RelationalPathBase<ElettraAmbienteTecnologicoClassificatori> {
	private static final long serialVersionUID = -8586891346402362047L;

	public static final QElettraAmbienteTecnologicoClassificatori elettraAmbienteTecnologicoClassificatori = new QElettraAmbienteTecnologicoClassificatori("DM_ALM_EL_AMBTECN_CLASSIFIC");
	
	public final StringPath idAmbienteTecnologico = createString("ID_AMB_TECN");
	public final StringPath nomeAmbienteTecnologico = createString("NOME_AMB_TECN");
	public final StringPath descrizioneAmbienteTecnologico = createString("DESCRIZIONE_AMB_TECN");
	public final StringPath descrizioneClassificatore = createString("DESCR_CLASSIFICATORE");
	public final StringPath nomeClassificatore = createString("NOME_CLASSIFICATORE");
	public final StringPath idClassificatore = createString("ID");
	
	public QElettraAmbienteTecnologicoClassificatori(String variable) {
		super(ElettraAmbienteTecnologicoClassificatori.class, forVariable(variable),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_EL_AMBTECN_CLASSIFIC");
	}

	public QElettraAmbienteTecnologicoClassificatori(Path<? extends ElettraAmbienteTecnologicoClassificatori> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_EL_AMBTECN_CLASSIFIC");
	}

	public QElettraAmbienteTecnologicoClassificatori(PathMetadata<?> metadata) {
		super(ElettraAmbienteTecnologicoClassificatori.class, metadata,
				DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_EL_AMBTECN_CLASSIFIC");
	}
}
