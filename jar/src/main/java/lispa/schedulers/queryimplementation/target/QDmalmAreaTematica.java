package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmAreaTematica;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmAreaTematica is a Querydsl query type for DmalmAreaTematica
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmAreaTematica extends com.mysema.query.sql.RelationalPathBase<DmalmAreaTematica> {

    private static final long serialVersionUID = -388975216;

    public static final QDmalmAreaTematica dmalmAreaTematica = new QDmalmAreaTematica("DMALM_AREA_TEMATICA");

    public final StringPath cdAreaTematica = createString("CD_AREA_TEMATICA");

    public final NumberPath<Integer> dmalmAreaTematicaPrimaryKey = createNumber("DMALM_AREA_TEMATICA_PK", Integer.class);

    public final StringPath dsAreaTematica = createString("DS_AREA_TEMATICA");

    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final com.mysema.query.sql.PrimaryKey<DmalmAreaTematica> dmalmAreaTematicaPk = createPrimaryKey(dmalmAreaTematicaPrimaryKey);

    public final com.mysema.query.sql.ForeignKey<lispa.schedulers.bean.target.DmalmProject> _dmalmProjectFk01 = createInvForeignKey(dmalmAreaTematicaPrimaryKey, "DMALM_AREA_TEMATICA_FK_01");

    public QDmalmAreaTematica(String variable) {
        super(DmalmAreaTematica.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_AREA_TEMATICA");
    }

    public QDmalmAreaTematica(Path<? extends DmalmAreaTematica> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_AREA_TEMATICA");
    }

    public QDmalmAreaTematica(PathMetadata<?> metadata) {
        super(DmalmAreaTematica.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_AREA_TEMATICA");
    }

}

