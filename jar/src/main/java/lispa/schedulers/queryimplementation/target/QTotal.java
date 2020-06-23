package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.Total;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QTotal is a Querydsl query type for Total
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTotal extends com.mysema.query.sql.RelationalPathBase<Total> {

    private static final long serialVersionUID = -1415566251;

    public static final QTotal total = new QTotal("TOTAL");

    public final StringPath codice = createString("CODICE");

    public final NumberPath<Long> dmalmPk = createNumber("DMALM_PK", Long.class);

    public final DateTimePath<java.sql.Timestamp> dtStoricizzazione = createDateTime("DT_STORICIZZAZIONE", java.sql.Timestamp.class);

    public final StringPath idRepository = createString("ID_REPOSITORY");

    public final StringPath stgPk = createString("STG_PK");

    public final StringPath type = createString("TYPE");

    public QTotal(String variable) {
        super(Total.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "TOTAL");
    }

    public QTotal(Path<? extends Total> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "TOTAL");
    }

    public QTotal(PathMetadata<?> metadata) {
        super(Total.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "TOTAL");
    }

}

