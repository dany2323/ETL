package lispa.schedulers.queryimplementation.staging.sgr.siss.history;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.siss.history.SissHistoryCfWorkitem;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QSissHistoryCfWorkitem is a Querydsl query type for SissHistoryCfWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QSissHistoryCfWorkitem extends com.mysema.query.sql.RelationalPathBase<SissHistoryCfWorkitem> {

    private static final long serialVersionUID = 682863077;

    public static final QSissHistoryCfWorkitem sissHistoryCfWorkitem = new QSissHistoryCfWorkitem("DMALM_SISS_HISTORY_CF_WORKITEM");

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
    
    public final StringPath dmalmCfWorkitemPk = createString("SISS_HISTORY_CF_WORKITEM_PK");
    
    public final DateTimePath<java.sql.Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", java.sql.Timestamp.class);

    public QSissHistoryCfWorkitem(String variable) {
        super(SissHistoryCfWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_CF_WORKITEM");
    }

    public QSissHistoryCfWorkitem(Path<? extends SissHistoryCfWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_CF_WORKITEM");
    }

    public QSissHistoryCfWorkitem(PathMetadata<?> metadata) {
        super(SissHistoryCfWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_SISS_HISTORY_CF_WORKITEM");
    }

}

