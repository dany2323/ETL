package lispa.schedulers.queryimplementation.target;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import lispa.schedulers.bean.target.DmalmProdotto;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.DmalmProjectProdotto;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;

/**
 * QDmalmProjectProdotto is a Querydsl query type for DmalmProjectProdotto
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QDmalmProjectProdotto extends
		com.mysema.query.sql.RelationalPathBase<DmalmProjectProdotto> {

	private static final long serialVersionUID = -32169004;

	public static final QDmalmProjectProdotto dmalmProjectProdotto = new QDmalmProjectProdotto(
			"DMALM_PROJECT_PRODOTTO");

	public final NumberPath<Integer> dmalmProjectPk = createNumber("DMALM_PROJECT_PK",
			Integer.class);

	public final NumberPath<Integer> dmalmProdottoSeq = createNumber(
			"DMALM_PRODOTTO_SEQ", Integer.class);

	public final DateTimePath<java.sql.Timestamp> dtFineValidita = createDateTime(
			"DT_FINE_VALIDITA", java.sql.Timestamp.class);

	public final DateTimePath<java.sql.Timestamp> dtInizioValidita = createDateTime(
			"DT_INIZIO_VALIDITA", java.sql.Timestamp.class);

	public final com.mysema.query.sql.PrimaryKey<DmalmProjectProdotto> DmalmProjectProdottoPk = createPrimaryKey(
			dmalmProjectPk, dmalmProdottoSeq, dtInizioValidita);

	public final com.mysema.query.sql.ForeignKey<DmalmProject> DmalmProjectProdottoFk01 = createForeignKey(
			dmalmProjectPk, "DMALM_PROJECT_PK");

	public final com.mysema.query.sql.ForeignKey<DmalmProdotto> DmalmProjectProdottoFk02 = createForeignKey(
			dmalmProdottoSeq, "DMALM_PRODOTTO_SEQ");

	public QDmalmProjectProdotto(String variable) {
		super(DmalmProjectProdotto.class, forVariable(variable), "DMALM",
				"DMALM_PROJECT_PRODOTTO");
	}

	public QDmalmProjectProdotto(Path<? extends DmalmProjectProdotto> path) {
		super(path.getType(), path.getMetadata(), "DMALM",
				"DMALM_PROJECT_PRODOTTO");
	}

	public QDmalmProjectProdotto(PathMetadata<?> metadata) {
		super(DmalmProjectProdotto.class, metadata, "DMALM",
				"DMALM_PROJECT_PRODOTTO");
	}

}
