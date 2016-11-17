package lispa.schedulers.queryimplementation.target.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.target.elettra.DmalmElAmbienteTecnologico;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QDmalmElAmbienteTecnologico extends com.mysema.query.sql.RelationalPathBase<DmalmElAmbienteTecnologico> {
	private static final long serialVersionUID = -1785765254248354944L;

	public static final QDmalmElAmbienteTecnologico qDmalmElAmbienteTecnologico = new QDmalmElAmbienteTecnologico("DMALM_EL_AMBIENTE_TECNOLOGICO");
	
	public final NumberPath<Integer> ambienteTecnologicoPk = createNumber("DMALM_AMBIENTE_TECNOLOGICO_PK", Integer.class);
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
	public final StringPath annullato = createString("ANNULLATO");
	public final DateTimePath<java.sql.Timestamp> dataAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
	public final NumberPath<Integer> prodottoFk = createNumber("DMALM_PRODOTTO_FK_01", Integer.class);
	public final NumberPath<Integer> moduloFk = createNumber("DMALM_MODULO_FK_02", Integer.class);
	public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

	public final com.mysema.query.sql.PrimaryKey<DmalmElAmbienteTecnologico> dmalmAmbienteTecnologicoPk = createPrimaryKey(ambienteTecnologicoPk);
	
	public QDmalmElAmbienteTecnologico(String variable) {
        super(DmalmElAmbienteTecnologico.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_AMBIENTE_TECNOLOGICO");
    }

    public QDmalmElAmbienteTecnologico(Path<? extends DmalmElAmbienteTecnologico> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_AMBIENTE_TECNOLOGICO");
    }

    public QDmalmElAmbienteTecnologico(PathMetadata<?> metadata) {
        super(DmalmElAmbienteTecnologico.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_EL_AMBIENTE_TECNOLOGICO");
    }
}
