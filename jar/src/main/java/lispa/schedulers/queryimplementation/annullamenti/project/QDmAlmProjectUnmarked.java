package lispa.schedulers.queryimplementation.annullamenti.project;

import static com.mysema.query.types.PathMetadataFactory.*;

import java.sql.Timestamp;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;



@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmAlmProjectUnmarked extends com.mysema.query.sql.RelationalPathBase<lispa.schedulers.bean.annullamenti.project.DmAlmProjectUnmarked> {

    private static final long serialVersionUID = -1899539286;

    public static final QDmAlmProjectUnmarked projectUnmarked = new QDmAlmProjectUnmarked("DMALM_PROJECT_UNMARKED");

    public final StringPath repository = createString("REPOSITORY");
    
    public final StringPath cTrackerprefix = createString("C_TRACKERPREFIX");
    
    public final StringPath path = createString("PATH");
    
    public final DateTimePath<Timestamp> dataCaricamento = createDateTime("DATA_CARICAMENTO", Timestamp.class);

    public QDmAlmProjectUnmarked(String variable) {
        super(lispa.schedulers.bean.annullamenti.project.DmAlmProjectUnmarked.class, forVariable(variable), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_PROJECT_UNMARKED");
    }

    public QDmAlmProjectUnmarked(Path<? extends lispa.schedulers.bean.annullamenti.project.DmAlmProjectUnmarked> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_PROJECT_UNMARKED");
    }

    public QDmAlmProjectUnmarked(PathMetadata<?> metadata) {
        super(lispa.schedulers.bean.annullamenti.project.DmAlmProjectUnmarked.class, metadata, DmAlmConstants.DMALM_STAGING_SCHEMA, "DMALM_PROJECT_UNMARKED");
    }

}

