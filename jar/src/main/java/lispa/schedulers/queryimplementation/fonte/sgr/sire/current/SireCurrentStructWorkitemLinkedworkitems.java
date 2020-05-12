package lispa.schedulers.queryimplementation.fonte.sgr.sire.current;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireCurrentStructWorkitemLinkedworkitems is a Querydsl query type for SireCurrentStructWorkitemLinkedworkitems
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class SireCurrentStructWorkitemLinkedworkitems extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems> {

    private static final long serialVersionUID = -1264613616;

    public static final SireCurrentStructWorkitemLinkedworkitems structWorkitemLinkedworkitems = new SireCurrentStructWorkitemLinkedworkitems("DM_ALM_C_SIRE_STRUCT_WI_LINKWI");

    public final NumberPath<Long> fkPWorkitem = createNumber("FK_P_WORKITEM",Long.class);
    
    public final NumberPath<Long> fkUriPWorkitem = createNumber("FK_URI_P_WORKITEM",Long.class);

    public final StringPath cRole = createString("C_ROLE");
   
    public final NumberPath<Long> fkWorkitem = createNumber("FK_WORKITEM",Long.class);

    public final NumberPath<Long> fkUriWorkitem = createNumber("FK_URI_WORKITEM",Long.class);

    public final StringPath cRevision = createString("C_REVISION");

    public final NumberPath<Integer> cSuspect = createNumber("C_SUSPECT",Integer.class);


    public SireCurrentStructWorkitemLinkedworkitems(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_STRUCT_WI_LINKWI");
    }

    public SireCurrentStructWorkitemLinkedworkitems(Path<? extends lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_STRUCT_WI_LINKWI");
    }

    public SireCurrentStructWorkitemLinkedworkitems(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.sire.current.SireCurrentStructWorkitemLinkedworkitems.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DM_ALM_C_SIRE_STRUCT_WI_LINKWI");
    }

}

