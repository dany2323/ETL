package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmTempo;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmTempo is a Querydsl query type for DmalmTempo
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmTempo extends com.mysema.query.sql.RelationalPathBase<DmalmTempo> {

    private static final long serialVersionUID = 1910934128;

    public static final QDmalmTempo dmalmTempo = new QDmalmTempo("DMALM_TEMPO");

    public final StringPath anno = createString("ANNO");

    public final NumberPath<Integer> dmalmTempoPrimaryKey = createNumber("DMALM_TEMPO_PK", Integer.class);

    public final StringPath dsGiorno = createString("DS_GIORNO");

    public final StringPath dsMese = createString("DS_MESE");

    public final DateTimePath<java.sql.Timestamp> dtOsservazione = createDateTime("DT_OSSERVAZIONE", java.sql.Timestamp.class);

    public final BooleanPath flFestivo = createBoolean("FL_FESTIVO");

    public final NumberPath<Byte> giorno = createNumber("GIORNO", Byte.class);

    public final StringPath mese = createString("MESE");

    public final StringPath semestre = createString("SEMESTRE");

    public final StringPath trimestre = createString("TRIMESTRE");

    public final com.mysema.query.sql.PrimaryKey<DmalmTempo> dmalmTempoPk = createPrimaryKey(dmalmTempoPrimaryKey);

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto> _dmalmDifettoProdottoFk04 = createInvForeignKey(dmalmTempoPrimaryKey, "DMALM_TEMPO_FK_04");

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto> _dmalmAnomaliaProdottoFk04 = createInvForeignKey(dmalmTempoPrimaryKey, "DMALM_TEMPO_FK_04");

    public QDmalmTempo(String variable) {
        super(DmalmTempo.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPO");
    }

    public QDmalmTempo(Path<? extends DmalmTempo> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPO");
    }

    public QDmalmTempo(PathMetadata<?> metadata) {
        super(DmalmTempo.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_TEMPO");
    }

}

