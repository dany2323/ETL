package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmAttachment;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;


/**
 * QDmalmAttachment is a Querydsl query type for DmalmAttachment
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmAttachment extends com.mysema.query.sql.RelationalPathBase<DmalmAttachment> {

    private static final long serialVersionUID = 1198183150;

    public static final QDmalmAttachment dmalmAttachment = new QDmalmAttachment("DMALM_ATTACHMENT");

    public final StringPath cdAttachment = createString("CD_ATTACHMENT");

    public final NumberPath<Integer> dimensioneFile = createNumber("DIMENSIONE_FILE", Integer.class);

    public final NumberPath<Integer> dmalmFkWorkitem01 = createNumber("DMALM_FK_WORKITEM_01", Integer.class);

    public final StringPath dmalmWorkitemType = createString("DMALM_WORKITEM_TYPE");

    public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime("DT_CARICAMENTO", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> dtModificaAttachment = createDateTime("DT_MODIFICA_ATTACHMENT", java.sql.Timestamp.class);

    public final StringPath idProject = createString("ID_PROJECT");

    public final StringPath nomeAutore = createString("NOME_AUTORE");

    public final StringPath nomeFile = createString("NOME_FILE");

    public final BooleanPath statoCancellato = createBoolean("STATO_CANCELLATO");

    public final StringPath titolo = createString("TITOLO");

    public final StringPath url = createString("URL");

    public QDmalmAttachment(String variable) {
        super(DmalmAttachment.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ATTACHMENT");
    }

    public QDmalmAttachment(Path<? extends DmalmAttachment> path) {
        super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ATTACHMENT");
    }

    public QDmalmAttachment(PathMetadata<?> metadata) {
        super(DmalmAttachment.class, metadata, DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_ATTACHMENT");
    }

}

