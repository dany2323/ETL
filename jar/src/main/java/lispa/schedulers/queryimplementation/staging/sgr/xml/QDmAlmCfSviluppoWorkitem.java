package lispa.schedulers.queryimplementation.staging.sgr.xml;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.staging.sgr.xml.DmAlmCfSviluppoWorkitem;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmAlmCfSviluppoWorkitem is a Querydsl query type for DmAlmCfSviluppoWorkitem
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmAlmCfSviluppoWorkitem extends com.mysema.query.sql.RelationalPathBase<DmAlmCfSviluppoWorkitem> {

    private static final long serialVersionUID = -1768701161;

    public static final QDmAlmCfSviluppoWorkitem dmAlmCfSviluppoWorkitem = new QDmAlmCfSviluppoWorkitem("DMALM_CF_SVILUPPO_WORKITEM");

    public final StringPath cName = createString("C_NAME");

    public final StringPath cType = createString("C_TYPE");

    public final NumberPath<Double> dmAlmCfSviluppoWorkitemPk = createNumber("CF_SVILUPPO_WORKITEM_PK", Double.class);

    public final StringPath name = createString("NAME");

    public final StringPath tipoWorkItem = createString("TIPO_WORK_ITEM");

    public final StringPath type = createString("TYPE");

    public final com.mysema.query.sql.PrimaryKey<DmAlmCfSviluppoWorkitem> dmAlmCfSviluppoWorkitPk = createPrimaryKey(dmAlmCfSviluppoWorkitemPk);

    public QDmAlmCfSviluppoWorkitem(String variable) {
        super(DmAlmCfSviluppoWorkitem.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_CF_SVILUPPO_WORKITEM");
    }

    public QDmAlmCfSviluppoWorkitem(Path<? extends DmAlmCfSviluppoWorkitem> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_CF_SVILUPPO_WORKITEM");
    }

    public QDmAlmCfSviluppoWorkitem(PathMetadata<?> metadata) {
        super(DmAlmCfSviluppoWorkitem.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_CF_SVILUPPO_WORKITEM");
    }

}

