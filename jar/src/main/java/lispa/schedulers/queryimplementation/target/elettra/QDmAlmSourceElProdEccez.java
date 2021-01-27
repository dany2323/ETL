package lispa.schedulers.queryimplementation.target.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import com.mysema.query.sql.PrimaryKey;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import lispa.schedulers.bean.target.DmalmProdotto;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.DmalmProjectProdotto;
import lispa.schedulers.bean.target.elettra.DmAlmSourceElProdEccez;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.queryimplementation.target.QDmalmProjectProdotto;

public class QDmAlmSourceElProdEccez extends
	com.mysema.query.sql.RelationalPathBase <DmAlmSourceElProdEccez> {

	private static final long serialVersionUID = 9220620445809356443L;
		
	public static final QDmAlmSourceElProdEccez dmAlmSourceElProd = new QDmAlmSourceElProdEccez("DMALM_SOURCE_EL_PROD_ECCEZ");

	public final StringPath siglaOggettoElettra = createString("SIGLA_OGGETTO_ELETTRA");
	public final NumberPath<Integer> tipoElProdEccezione = createNumber("TIPO_EL_PROD_ECCEZIONE", Integer.class);
	
	
	public final PrimaryKey<DmAlmSourceElProdEccez> siglaOggettoElettraPk = createPrimaryKey(siglaOggettoElettra);

	public QDmAlmSourceElProdEccez(String variable) {
		super(DmAlmSourceElProdEccez.class, forVariable(variable), "DMALM",
				"DMALM_SOURCE_EL_PROD_ECCEZ");
	}

	public QDmAlmSourceElProdEccez(Path<? extends DmAlmSourceElProdEccez> path) {
		super(path.getType(), path.getMetadata(), "DMALM",
				"DMALM_SOURCE_EL_PROD_ECCEZ");
	}

	public QDmAlmSourceElProdEccez(PathMetadata<?> metadata) {
		super(DmAlmSourceElProdEccez.class, metadata, "DMALM",
				"DMALM_SOURCE_EL_PROD_ECCEZ");
	}

	
}
