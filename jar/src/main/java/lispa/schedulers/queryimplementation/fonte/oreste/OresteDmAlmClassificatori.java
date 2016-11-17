package lispa.schedulers.queryimplementation.fonte.oreste;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * OresteDmAlmClassificatori is a Querydsl query type for OresteDmAlmClassificatori
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class OresteDmAlmClassificatori extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.oreste.OresteDmAlmClassificatori> {

    private static final long serialVersionUID = -533269770;

    public static final OresteDmAlmClassificatori dmAlmClassificatori = new OresteDmAlmClassificatori("V_DM_ALM_CLASSIFICATORI");

    public final StringPath codiceClassificatore = createString("CODICE_CLASSIFICATORE");

    public final StringPath codiceTipologia = createString("CODICE_TIPOLOGIA");

    public final NumberPath<Double> id = createNumber("ID", Double.class);

    public OresteDmAlmClassificatori(String variable) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmClassificatori.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_CLASSIFICATORI");
    }

    public OresteDmAlmClassificatori(Path<? extends lispa.schedulers.bean.fonte.oreste.OresteDmAlmClassificatori> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_CLASSIFICATORI");
    }

    public OresteDmAlmClassificatori(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.oreste.OresteDmAlmClassificatori.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "V_DM_ALM_CLASSIFICATORI");
    }

}

