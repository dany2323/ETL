package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmUserElPersonale;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmUser is a Querydsl query type for DmalmUser
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmUserElPersonale extends com.mysema.query.sql.RelationalPathBase<DmalmUserElPersonale> {

    private static final long serialVersionUID = -1877976618;

    public static final QDmalmUserElPersonale dmalmUserElPersonale = new QDmalmUserElPersonale("DMALM_USER_EL_PERSONALE");

    public final StringPath idUser = createString("ID_USER");
    
    public final NumberPath<Integer> dmalmElPersonalePk = createNumber("DMALM_PERSONALE_PK", Integer.class);
    
    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public QDmalmUserElPersonale(String variable) {
        super(DmalmUserElPersonale.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_USER_EL_PERSONALE");
    }

    public QDmalmUserElPersonale(Path<? extends DmalmUserElPersonale> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_USER_EL_PERSONALE");
    }

    public QDmalmUserElPersonale(PathMetadata<?> metadata) {
        super(DmalmUserElPersonale.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_USER_EL_PERSONALE");
    }

}

