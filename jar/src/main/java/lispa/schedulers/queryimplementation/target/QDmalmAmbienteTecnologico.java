package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmAmbienteTecnologico;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmAmbienteTecnologico is a Querydsl query type for DmalmAmbienteTecnologico
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmAmbienteTecnologico extends com.mysema.query.sql.RelationalPathBase<DmalmAmbienteTecnologico> {

    private static final long serialVersionUID = -2010934420;

    public static final QDmalmAmbienteTecnologico dmalmAmbienteTecnologico = new QDmalmAmbienteTecnologico("DMALM_AMBIENTE_TECNOLOGICO");

    public final StringPath architettura = createString("ARCHITETTURA");

    public final NumberPath<Integer> dmalmAmbienteTecnologicoPrimaryKey = createNumber("DMALM_AMBIENTE_TECNOLOGICO_PK", Integer.class);

    public final StringPath dsAmbienteTecnologico = createString("DS_AMBIENTE_TECNOLOGICO");

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);
    
    public final DateTimePath<java.sql.Timestamp> dtInserimentoRecord = createDateTime("DT_INSERIMENTO_RECORD", java.sql.Timestamp.class);

    public final StringPath idAmbienteTecnologico = createString("ID_AMBIENTE_TECNOLOGICO");

    //public final NumberPath<Integer> idEdma = createNumber("ID_EDMA", Integer.class);

    public final NumberPath<Integer> dmalmProdottoFk01 = createNumber("DMALM_PRODOTTO_FK_01", Integer.class);

    public final NumberPath<Integer> dmalmModuloFk02 = createNumber("DMALM_MODULO_FK_02", Integer.class);
    
    public final StringPath infrastruttura = createString("INFRASTRUTTURA");

    public final StringPath nome = createString("NOME");

    public final StringPath siglaModulo = createString("SIGLA_MODULO");

    public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");

    public final StringPath sistemaOperativo = createString("SISTEMA_OPERATIVO");

    public final StringPath tipoLayer = createString("TIPO_LAYER");

    public final StringPath tipoOggetto = createString("TIPO_OGGETTO");

    public final StringPath versioneSo = createString("VERSIONE_SO");

    public final com.mysema.query.sql.PrimaryKey<DmalmAmbienteTecnologico> dmalmAmbienteTecnologicoPk = createPrimaryKey(dmalmAmbienteTecnologicoPrimaryKey);

    public final StringPath annullato = createString("ANNULLATO");
    
    public final NumberPath<Integer> dmalmAmbienteTecnologicoSeq = createNumber("DMALM_AMBIENTE_TECNOLOGICO_SEQ", Integer.class);

    
    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);
    
    public QDmalmAmbienteTecnologico(String variable) {
        super(DmalmAmbienteTecnologico.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_AMBIENTE_TECNOLOGICO");
    }

    public QDmalmAmbienteTecnologico(Path<? extends DmalmAmbienteTecnologico> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_AMBIENTE_TECNOLOGICO");
    }

    public QDmalmAmbienteTecnologico(PathMetadata<?> metadata) {
        super(DmalmAmbienteTecnologico.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_AMBIENTE_TECNOLOGICO");
    }

}

