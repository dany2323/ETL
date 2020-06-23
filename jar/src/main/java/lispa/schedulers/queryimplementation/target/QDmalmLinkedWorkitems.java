package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.*;
import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.path.*;
import com.mysema.query.types.PathMetadata;

import javax.annotation.Generated;

import com.mysema.query.types.Path;

/**
 * QDmalmLinkedWorkitems is a Querydsl query type for DmalmLinkedWorkitems
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmLinkedWorkitems extends
		com.mysema.query.sql.RelationalPathBase<DmalmLinkedWorkitems> {
	private static final long serialVersionUID = 204936779;

	public static final QDmalmLinkedWorkitems dmalmLinkedWorkitems = new QDmalmLinkedWorkitems(
			"DMALM_LINKED_WORKITEMS");

	public final StringPath codiceWiFiglio = createString("CODICE_WI_FIGLIO");
	public final StringPath codiceWiPadre = createString("CODICE_WI_PADRE");
	public final StringPath tipoWiFiglio = createString("TIPO_WI_FIGLIO");
	public final StringPath tipoWiPadre = createString("TIPO_WI_PADRE");
	public final NumberPath<Integer> fkWiFiglio = createNumber("FK_WI_FIGLIO",
			Integer.class);
	public final NumberPath<Integer> fkWiPadre = createNumber("FK_WI_PADRE",
			Integer.class);
	public final StringPath ruolo = createString("RUOLO");
	public final DateTimePath<java.sql.Timestamp> dtCaricamento = createDateTime(
			"DT_CARICAMENTO", java.sql.Timestamp.class);
	public final NumberPath<Integer> linkedWorkitemsPk = createNumber(
			"LINKED_WORKITEMS_PK", Integer.class);
	public final StringPath titoloWiFiglio = createString("TITOLO_WI_FIGLIO");
	public final StringPath titoloWiPadre = createString("TITOLO_WI_PADRE");
	public final StringPath uriWiFiglio = createString("URI_WI_FIGLIO");
	public final StringPath uriWiPadre = createString("URI_WI_PADRE");
	public final StringPath idRepositoryFiglio = createString("ID_REPOSITORY_FIGLIO");
	public final StringPath idRepositoryPadre = createString("ID_REPOSITORY_PADRE");
	public final StringPath codiceProjectFiglio = createString("CODICE_PROJECT_FIGLIO");
	public final StringPath codiceProjectPadre = createString("CODICE_PROJECT_PADRE");

	public QDmalmLinkedWorkitems(String variable) {
		super(DmalmLinkedWorkitems.class, forVariable(variable), DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_LINKED_WORKITEMS");
	}

	public QDmalmLinkedWorkitems(Path<? extends DmalmLinkedWorkitems> path) {
		super(path.getType(), path.getMetadata(), DmAlmConstants.DMALM_TARGET_SCHEMA,
				"DMALM_LINKED_WORKITEMS");
	}

	public QDmalmLinkedWorkitems(PathMetadata<?> metadata) {
		super(DmalmLinkedWorkitems.class, metadata,
				DmAlmConstants.DMALM_TARGET_SCHEMA, "DMALM_LINKED_WORKITEMS");
	}
}
