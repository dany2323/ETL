package lispa.schedulers.queryimplementation.staging.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.staging.elettra.StgElAmbienteTecnologico;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QStgElAmbienteTecnologico extends com.mysema.query.sql.RelationalPathBase<StgElAmbienteTecnologico> {
	private static final long serialVersionUID = -4748204633878662316L;

	public static final QStgElAmbienteTecnologico stgElAmbienteTecnologico = new QStgElAmbienteTecnologico("DMALM_STG_EL_AMBIENTE_TECNO");
	
	public final NumberPath<Integer> ambienteTecnologicoPk = createNumber("DMALM_STG_AMB_TECNOLOGICO_PK", Integer.class);
	public final StringPath idAmbienteTecnologicoEdma = createString("ID_AMB_TECNOLOGICO_EDMA");
	public final StringPath idAmbienteTecnologicoEdmaPadre = createString("ID_AMB_TECNOLOGICO_EDMA_PADRE");
	public final StringPath idAmbienteTecnologico = createString("ID_AMB_TECNOLOGICO");
	public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");
	public final StringPath siglaModulo = createString("SIGLA_MODULO");
	public final StringPath nome = createString("NOME");
	public final StringPath architettura = createString("ARCHITETTURA");
	public final StringPath infrastruttura = createString("INFRASTRUTTURA");
	public final StringPath sistemaOperativo = createString("SISTEMA_OPERATIVO");
	public final StringPath tipoLayer = createString("TIPO_LAYER");
	public final StringPath versioneSistemaOperativo = createString("VERSIONE_SO");
	public final StringPath descrizioneAmbienteTecnologico = createString("DS_AMBIENTE_TECNOLOGICO");
	public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);
	
	public QStgElAmbienteTecnologico(String variable) {
        super(StgElAmbienteTecnologico.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_AMBIENTE_TECNO");
    }

    public QStgElAmbienteTecnologico(Path<? extends StgElAmbienteTecnologico> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_AMBIENTE_TECNO");
    }

    public QStgElAmbienteTecnologico(PathMetadata<?> metadata) {
        super(StgElAmbienteTecnologico.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_EL_AMBIENTE_TECNO");
    }
}
