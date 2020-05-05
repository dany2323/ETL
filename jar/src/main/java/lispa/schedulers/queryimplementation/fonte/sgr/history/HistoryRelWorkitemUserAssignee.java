package lispa.schedulers.queryimplementation.fonte.sgr.history;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;


/**
 * SireHistoryRelWorkitemUserAssignee is a Querydsl query type for SireHistoryRelWorkitemUserAssignee
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class HistoryRelWorkitemUserAssignee extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.fonte.sgr.history.HistoryRelWorkitemUserAssignee> {

    private static final long serialVersionUID = -444495222;

    public static final HistoryRelWorkitemUserAssignee relWorkitemUserAssignee = new HistoryRelWorkitemUserAssignee("REL_WORKITEM_USER_ASSIGNEE");

    public final StringPath fkWorkitem = createString("FK_WORKITEM");
    
    public final StringPath fkUriWorkitem = createString("FK_URI_WORKITEM");

    public final StringPath fkUriUser = createString("FK_URI_USER");

    public final StringPath fkUser = createString("FK_USER");

    public HistoryRelWorkitemUserAssignee(String variable) {
        super(lispa.schedulers.bean.fonte.sgr.history.HistoryRelWorkitemUserAssignee.class, forVariable(variable), DmAlmConstants.POLARION_SCHEMA, "REL_WORKITEM_USER_ASSIGNEE");
    }

    public HistoryRelWorkitemUserAssignee(Path<? extends lispa.schedulers.bean.fonte.sgr.history.HistoryRelWorkitemUserAssignee> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.POLARION_SCHEMA, "REL_WORKITEM_USER_ASSIGNEE");
    }

    public HistoryRelWorkitemUserAssignee(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.fonte.sgr.history.HistoryRelWorkitemUserAssignee.class, metadata, DmAlmConstants.POLARION_SCHEMA, "REL_WORKITEM_USER_ASSIGNEE");
    }

}

