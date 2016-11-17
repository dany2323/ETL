package lispa.schedulers.queryimplementation.staging.sgr.siss.current;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.current.SissCurrentCfWorkitem;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissCurrentCfWorkitem is a Querydsl query type for SissCurrentCfWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissCurrentCfWorkitem extends com.mysema.query.sql.RelationalPathBase<SissCurrentCfWorkitem> {

    private static final long serialVersionUID = 1671255983;

    public static final QSissCurrentCfWorkitem sissCurrentCfWorkitem = new QSissCurrentCfWorkitem("DMALM_SISS_CURRENT_CF_WORKITEM");

    //public final BooleanPath cBooleanValue = createBoolean("C_BOOLEAN_VALUE");
    public final NumberPath<Integer> cBooleanValue = createNumber("C_BOOLEAN_VALUE", Integer.class);

    public final NumberPath<java.math.BigDecimal> cCurrencyValue = createNumber("C_CURRENCY_VALUE", java.math.BigDecimal.class);

    public final DateTimePath<java.sql.Timestamp> cDateValue = createDateTime("C_DATE_VALUE", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> cDateonlyValue = createDateTime("C_DATEONLY_VALUE", java.sql.Timestamp.class);

    public final NumberPath<Long> cDurationtimeValue = createNumber("C_DURATIONTIME_VALUE", Long.class);

    public final NumberPath<Float> cFloatValue = createNumber("C_FLOAT_VALUE", Float.class);

    public final NumberPath<Long> cLongValue = createNumber("C_LONG_VALUE", Long.class);

    public final StringPath cName = createString("C_NAME");

    public final StringPath cStringValue = createString("C_STRING_VALUE");

    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");
    
    public final StringPath dmalmSissCurrentCfWorkitemPk = createString("SISS_CURR_CF_WORKITEM_PK");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public QSissCurrentCfWorkitem(String variable) {
        super(SissCurrentCfWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_CF_WORKITEM");
    }

    public QSissCurrentCfWorkitem(Path<? extends SissCurrentCfWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_CF_WORKITEM");
    }

    public QSissCurrentCfWorkitem(PathMetadata<?> metadata) {
        super(SissCurrentCfWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_CURRENT_CF_WORKITEM");
    }

}

