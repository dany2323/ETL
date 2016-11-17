package lispa.schedulers.queryimplementation.fonte.elettra;

import static com.mysema.query.types.PathMetadataFactory.forVariable;
import lispa.schedulers.bean.fonte.elettra.ElettraUnitaOrganizzative;
import lispa.schedulers.constant.DmAlmConstants;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

public class QElettraUnitaOrganizzative extends
		com.mysema.query.sql.RelationalPathBase<ElettraUnitaOrganizzative> {
	private static final long serialVersionUID = -1982384167490994219L;

	public static final QElettraUnitaOrganizzative elettraUnitaOrganizzative = new QElettraUnitaOrganizzative(
			"DM_ALM_EL_UNITA_ORGANIZZATIVE");

	public final StringPath idEdma = createString("Id Edma UO EDMA_LISPA");
	public final StringPath codiceUnitaOrganizzativa = createString("Codice UO EDMA_LISPA");
	public final DateTimePath<java.sql.Timestamp> dataInizioValidita = createDateTime(
			"DT IN Validita UO EDMA_LISPA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataFineValidita = createDateTime(
			"DT FN Validita UO EDMA_LISPA", java.sql.Timestamp.class);
	public final StringPath descrizioneUnitaOrganizzativa = createString("Descrizione UO EDMA_LISPA");
	public final DateTimePath<java.sql.Timestamp> dataAttivazione = createDateTime(
			"DT Attivaz UO EDMA_LISPA", java.sql.Timestamp.class);
	public final DateTimePath<java.sql.Timestamp> dataDisattivazione = createDateTime(
			"DT Disattivaz UO EDMA_LISPA", java.sql.Timestamp.class);
	public final StringPath note = createString("Note UO EDMA_LISPA");
	public final NumberPath<Short> flagInterno = createNumber(
			"Flag Interno UO EDMA_LISPA", Short.class);
	public final StringPath codiceResponsabile = createString("Cod Resp UO EDMA_LISPA");
	public final StringPath indirizzoEmail = createString("E-Mail UO EDMA_LISPA");
	public final NumberPath<Integer> tipologiaUfficio = createNumber(
			"Tipologia UO EDMA_LISPA", Integer.class);
	public final NumberPath<Integer> gradoUfficio = createNumber(
			"Grado UO EDMA_LISPA", Integer.class);
	public final NumberPath<Integer> idSede = createNumber(
			"Id Sede UO EDMA_LISPA", Integer.class);
	public final StringPath codiceUOSuperiore = createString("Cod UO Sup UO EDMA_LISPA");
	public final StringPath codiceEnte = createString("Codice Ente UO EDMA_LISPA");
	public final StringPath codiceVisibilita = createString("Cod Visibilita UO EDMA_LISPA");

	public QElettraUnitaOrganizzative(String variable) {
		super(ElettraUnitaOrganizzative.class, forVariable(variable),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA,
				"DM_ALM_EL_UNITA_ORGANIZZATIVE");
	}

	public QElettraUnitaOrganizzative(
			Path<? extends ElettraUnitaOrganizzative> path) {
		super(path.getType(), path.getMetadata(),
				DmAlmConstants.FONTE_ELETTRA_SCHEMA,
				"DM_ALM_EL_UNITA_ORGANIZZATIVE");
	}

	public QElettraUnitaOrganizzative(PathMetadata<?> metadata) {
		super(ElettraUnitaOrganizzative.class, metadata,
				DmAlmConstants.FONTE_ELETTRA_SCHEMA,
				"DM_ALM_EL_UNITA_ORGANIZZATIVE");
	}
}
