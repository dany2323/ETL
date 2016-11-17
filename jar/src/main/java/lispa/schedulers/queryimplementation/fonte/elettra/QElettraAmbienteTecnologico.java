package lispa.schedulers.queryimplementation.fonte.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.fonte.elettra.ElettraAmbienteTecnologico;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.StringPath;

public class QElettraAmbienteTecnologico extends com.mysema.query.sql.RelationalPathBase<ElettraAmbienteTecnologico> {
	private static final long serialVersionUID = -5125433476810991267L;

	public static final QElettraAmbienteTecnologico elettraAmbienteTecnologico = new QElettraAmbienteTecnologico("DM_ALM_EL_AMB_TECNOLOGICO");
	
	public final StringPath idAmbienteTecnologico = createString("Id AmbTecnOr");
	public final StringPath idEdmaAmbienteTecnologico = createString("Id EDMA AmbTecnOr");
	public final StringPath idEdmaPadreAmbienteTecnologico = createString("Id Edma Padre AmbTecnOr");
	public final StringPath siglaProdArchPadreAmbienteTecnologico	 = createString("Sigla ProdArch Padre AmbTecnOr");
	public final StringPath siglaModuloAmbienteTecnologico = createString("Sigla Modulo AmbTecnOr");
	public final StringPath nomeAmbienteTecnologico = createString("Nome AmbTecnOr");
	public final StringPath classifArchitetturaRiferimentoAmbienteTecnologico = createString("Cls Archit Riferim AmbTecnOr");
	public final StringPath classifInfrastruttureAmbienteTecnologico = createString("Cls Infrastrutture AmbTecnOr");
	public final StringPath classifSistemaOperativoAmbienteTecnologico = createString("Cls SO AmbTecnOr");
	public final StringPath classifTipiLayerAmbienteTecnologico	 = createString("Cls Tipi Layer AmbTecnOr");
	public final StringPath versioneSistemaOperativoAmbienteTecnologico = createString("Versione SO AmbTecnOr");
	public final StringPath descrizioneAmbienteTecnologico	 = createString("Descrizione AmbTecnOr");
	
	public QElettraAmbienteTecnologico(String variable) {
		super(ElettraAmbienteTecnologico.class, forVariable(variable),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_AMB_TECNOLOGICO");
	}

	public QElettraAmbienteTecnologico(Path<? extends ElettraAmbienteTecnologico> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_AMB_TECNOLOGICO");
	}

	public QElettraAmbienteTecnologico(PathMetadata<?> metadata) {
		super(ElettraAmbienteTecnologico.class, metadata,
				DmAlmConstants.FONTE_ELETTRA_SCHEMA, "DM_ALM_EL_AMB_TECNOLOGICO");
	}
}
