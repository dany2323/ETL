package lispa.schedulers.queryimplementation.staging.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.oreste.StgFunzionalita;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QStgFunzionalita is a Querydsl query type for StgFunzionalita
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QStgFunzionalita extends com.mysema.query.sql.RelationalPathBase<StgFunzionalita> {

    private static final long serialVersionUID = 625634202;

    public static final QStgFunzionalita stgFunzionalita = new QStgFunzionalita("DMALM_STG_FUNZIONALITA");

    public final StringPath clasfCategoria = createString("CLASF_CATEGORIA");

    public final StringPath clasfLinguaggioDiProg = createString("CLASF_LINGUAGGIO_DI_PROG");

    public final StringPath clasfTipoElabor = createString("CLASF_TIPO_ELABOR");

    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public final StringPath descrizione = createString("DESCRIZIONE");

    public final StringPath dfvFunzionalitaAnnullata = createString("DFV_FUNZIONALITA_ANNULLATA");

    public final StringPath funzionalitaAnnullata = createString("FUNZIONALITA_ANNULLATA");

    public final NumberPath<Long> idEdmaFunzionalita = createNumber("ID_EDMA_FUNZIONALITA", Long.class);

    public final NumberPath<Double> idEdmaPadre = createNumber("ID_EDMA_PADRE", Double.class);

    public final StringPath idFunzionalita = createString("ID_FUNZIONALITA");

    public final StringPath nomeFunzionalita = createString("NOME_FUNZIONALITA");

    public final StringPath siglaFunzionalita = createString("SIGLA_FUNZIONALITA");

    public final StringPath siglaModulo = createString("SIGLA_MODULO");

    public final StringPath siglaProdotto = createString("SIGLA_PRODOTTO");

    public final StringPath siglaSottosistema = createString("SIGLA_SOTTOSISTEMA");

    public final StringPath tipoFunzionalita = createString("TIPO_FUNZIONALITA");
    
    public final StringPath dmalmFunzionalitaPk = createString("DMALM_STG_FUNZIONALITA_PK");

    public QStgFunzionalita(String variable) {
        super(StgFunzionalita.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_FUNZIONALITA");
    }

    public QStgFunzionalita(Path<? extends StgFunzionalita> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_FUNZIONALITA");
    }

    public QStgFunzionalita(PathMetadata<?> metadata) {
        super(StgFunzionalita.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_STG_FUNZIONALITA");
    }

}

