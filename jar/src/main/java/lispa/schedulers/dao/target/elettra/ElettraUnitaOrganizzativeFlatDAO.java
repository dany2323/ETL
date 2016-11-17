package lispa.schedulers.dao.target.elettra;

import java.sql.Connection;
import java.sql.SQLException;

import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElUnitaOrganizzativeFlat;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class ElettraUnitaOrganizzativeFlatDAO {
	private static Logger logger = Logger
			.getLogger(ElettraUnitaOrganizzativeFlatDAO.class);
	
	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QDmalmElUnitaOrganizzativeFlat qDmalmElUnitaOrganizzativeFlat = QDmalmElUnitaOrganizzativeFlat.qDmalmElUnitaOrganizzativeFlat;

			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLDeleteClause(connection, dialect, qDmalmElUnitaOrganizzativeFlat)
					.execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	

	public static void insert(DmalmElUnitaOrganizzativeFlat unitaOrganizzativaFlat) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			QDmalmElUnitaOrganizzativeFlat qDmalmElUnitaOrganizzativeFlat = QDmalmElUnitaOrganizzativeFlat.qDmalmElUnitaOrganizzativeFlat;

			SQLTemplates dialect = new HSQLDBTemplates();

			connection.setAutoCommit(false);

			Integer uoFK01 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk01();
			String codice01 = unitaOrganizzativaFlat.getCodiceArea01();
			String descrizione01 = unitaOrganizzativaFlat.getDescrizioneArea01();

			Integer uoFK02 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk02();
			String codice02 = unitaOrganizzativaFlat.getCodiceArea02();
			String descrizione02 = unitaOrganizzativaFlat.getDescrizioneArea02();

			Integer uoFK03 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk03();
			String codice03 = unitaOrganizzativaFlat.getCodiceArea03();
			String descrizione03 = unitaOrganizzativaFlat.getDescrizioneArea03();

			Integer uoFK04 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk04();
			String codice04 = unitaOrganizzativaFlat.getCodiceArea04();
			String descrizione04 = unitaOrganizzativaFlat.getDescrizioneArea04();

			Integer uoFK05 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk05();
			String codice05 = unitaOrganizzativaFlat.getCodiceArea05();
			String descrizione05 = unitaOrganizzativaFlat.getDescrizioneArea05();

			Integer uoFK06 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk06();
			String codice06 = unitaOrganizzativaFlat.getCodiceArea06();
			String descrizione06 = unitaOrganizzativaFlat.getDescrizioneArea06();

			Integer uoFK07 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk07();
			String codice07 = unitaOrganizzativaFlat.getCodiceArea07();
			String descrizione07 = unitaOrganizzativaFlat.getDescrizioneArea07();

			Integer uoFK08 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk08();
			String codice08 = unitaOrganizzativaFlat.getCodiceArea08();
			String descrizione08 = unitaOrganizzativaFlat.getDescrizioneArea08();


			if (uoFK01 == null) {
				uoFK01 = new Integer(0);
				codice01 = "Non presente";
				descrizione01 = "Non presente";
			}

			if (uoFK02 == null) {
				uoFK02 = new Integer(0);
				codice02 = "Non presente";
				descrizione02 = "Non presente";
			}
			if (uoFK03 == null) {
				uoFK03 = new Integer(0);
				codice03 = "Non presente";
				descrizione03 = "Non presente";
			}
			if (uoFK04 == null) {
				uoFK04 = new Integer(0);
				codice04 = "Non presente";
				descrizione04 = "Non presente";
			}
			if (uoFK05 == null) {
				uoFK05 = new Integer(0);
				codice05 = "Non presente";
				descrizione05 = "Non presente";
			}
			if (uoFK06 == null) {
				uoFK06 = new Integer(0);
				codice06 = "Non presente";
				descrizione06 = "Non presente";
			}
			if (uoFK07 == null) {
				uoFK07 = new Integer(0);
				codice07 = "Non presente";
				descrizione01 = "Non presente";
			}
			if (uoFK08 == null) {
				uoFK08 = new Integer(0);
				codice08 = "Non presente";
				descrizione08 = "Non presente";
			}

			new SQLInsertClause(connection, dialect, qDmalmElUnitaOrganizzativeFlat)
								.columns(qDmalmElUnitaOrganizzativeFlat.idFlatPk,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk01,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk02,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk03,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk04,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk05,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk06,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk07,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk08,
										qDmalmElUnitaOrganizzativeFlat.codiceArea01,
										qDmalmElUnitaOrganizzativeFlat.codiceArea02,
										qDmalmElUnitaOrganizzativeFlat.codiceArea03,
										qDmalmElUnitaOrganizzativeFlat.codiceArea04,
										qDmalmElUnitaOrganizzativeFlat.codiceArea05,
										qDmalmElUnitaOrganizzativeFlat.codiceArea06,
										qDmalmElUnitaOrganizzativeFlat.codiceArea07,
										qDmalmElUnitaOrganizzativeFlat.codiceArea08,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea01,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea02,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea03,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea04,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea05,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea06,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea07,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea08,
										qDmalmElUnitaOrganizzativeFlat.dataCaricamento,
										qDmalmElUnitaOrganizzativeFlat.dataInizioValidita,
										qDmalmElUnitaOrganizzativeFlat.dataFineValidita)
								.values(unitaOrganizzativaFlat.getIdFlatPk(),
										uoFK01,
										uoFK02,
										uoFK03,
										uoFK04,
										uoFK05,
										uoFK06,
										uoFK07,
										uoFK08,
										codice01,
										codice02,
										codice03,
										codice04,
										codice05,
										codice06,
										codice07,
										codice08,
										descrizione01,
										descrizione02,
										descrizione03,
										descrizione04,
										descrizione05,
										descrizione06,
										descrizione07,
										descrizione08,
										unitaOrganizzativaFlat.getDataCaricamento(),
										unitaOrganizzativaFlat.getDataInizioValidita(),
										unitaOrganizzativaFlat.getDataFineValidita())
								.execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
}
