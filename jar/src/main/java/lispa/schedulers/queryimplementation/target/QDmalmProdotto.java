package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmProdotto;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmProdotto is a Querydsl query type for DmalmProdotto
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProdotto extends com.mysema.query.sql.RelationalPathBase<DmalmProdotto> {

    private static final long serialVersionUID = -835270782;

    public static final QDmalmProdotto dmalmProdotto = new QDmalmProdotto("DMALM_PRODOTTO");

    public final StringPath ambitoManutenzione = createString("AMBITO_MANUTENZIONE");

    public final StringPath annullato = createString("ANNULLATO");

    public final NumberPath<Integer> dmalmUnitaOrganizzativaFk01 = createNumber("DMALM_UNITAORGANIZZATIVA_FK_01", Integer.class);

    public final StringPath areaTematica = createString("AREA_TEMATICA");

    public final StringPath baseDatiEtl = createString("BASE_DATI_ETL");

    public final StringPath baseDatiLettura = createString("BASE_DATI_LETTURA");

    public final StringPath baseDatiScrittura = createString("BASE_DATI_SCRITTURA");

    public final StringPath categoria = createString("CATEGORIA");

    public final NumberPath<Integer> dmalmProdottoPrimaryKey = createNumber("DMALM_PRODOTTO_PK", Integer.class);

    public final StringPath dsProdotto = createString("DS_PRODOTTO");

    public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime("DT_FINE_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime("DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtInserimentoRecord = createDateTime("DT_INSERIMENTO_RECORD", java.sql.Timestamp.class);
    
    public final StringPath fornituraRisorseEsterne = createString("FORNITURA_RISORSE_ESTERNE");

    public final DateTimePath<java.sql.Timestamp> dtAnnullamento = createDateTime("DT_ANNULLAMENTO", java.sql.Timestamp.class);

    public final StringPath idProdotto = createString("ID_PRODOTTO");

    public final StringPath nome = createString("NOME");

    public final StringPath dmalmPersonaleFk02 = createString("DMALM_PERSONALE_FK_02");

    public final StringPath sigla = createString("SIGLA");

    public final StringPath tipoOggetto = createString("TIPO_OGGETTO");
    
    public final NumberPath<Integer> dmalmProdottoSeq = createNumber("DMALM_PRODOTTO_SEQ", Integer.class);

    public final com.mysema.query.sql.PrimaryKey<DmalmProdotto> dmalmProdottoPk = createPrimaryKey(dmalmProdottoPrimaryKey);

    public QDmalmProdotto(String variable) {
        super(DmalmProdotto.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PRODOTTO");
    }

    public QDmalmProdotto(Path<? extends DmalmProdotto> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PRODOTTO");
    }

    public QDmalmProdotto(PathMetadata<?> metadata) {
        super(DmalmProdotto.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_PRODOTTO");
    }

}

