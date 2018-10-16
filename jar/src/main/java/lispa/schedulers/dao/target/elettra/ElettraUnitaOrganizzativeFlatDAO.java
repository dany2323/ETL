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
					.where(qDmalmElUnitaOrganizzativeFlat.idFlatPk.gt(0))  //Aggiunta per DM_ALM-313 il record tappo non lo cancello
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
			
			Integer uoFK09 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk09();
			String codice09 = unitaOrganizzativaFlat.getCodiceArea09();
			String descrizione09 = unitaOrganizzativaFlat.getDescrizioneArea09();
			
			Integer uoFK10 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk10();
			String codice10 = unitaOrganizzativaFlat.getCodiceArea10();
			String descrizione10 = unitaOrganizzativaFlat.getDescrizioneArea10();
			
			Integer uoFK11 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk11();
			String codice11 = unitaOrganizzativaFlat.getCodiceArea11();
			String descrizione11 = unitaOrganizzativaFlat.getDescrizioneArea11();
			
			Integer uoFK12 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk12();
			String codice12 = unitaOrganizzativaFlat.getCodiceArea12();
			String descrizione12 = unitaOrganizzativaFlat.getDescrizioneArea12();
			
			Integer uoFK13 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk13();
			String codice13 = unitaOrganizzativaFlat.getCodiceArea13();
			String descrizione13 = unitaOrganizzativaFlat.getDescrizioneArea13();
			
			Integer uoFK14 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk14();
			String codice14 = unitaOrganizzativaFlat.getCodiceArea14();
			String descrizione14 = unitaOrganizzativaFlat.getDescrizioneArea14();
			
			Integer uoFK15 = unitaOrganizzativaFlat.getUnitaOrganizzativaFk15();
			String codice15 = unitaOrganizzativaFlat.getCodiceArea15();
			String descrizione15 = unitaOrganizzativaFlat.getDescrizioneArea15();

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
				descrizione07 = "Non presente";
			}
			if (uoFK08 == null) {
				uoFK08 = new Integer(0);
				codice08 = "Non presente";
				descrizione08 = "Non presente";
			}
			
			if (uoFK09 == null) {
				uoFK09 = new Integer(0);
				codice09 = "Non presente";
				descrizione09 = "Non presente";
			}
			
			if (uoFK10 == null) {
				uoFK10 = new Integer(0);
				codice10 = "Non presente";
				descrizione10 = "Non presente";
			}
			
			if (uoFK11 == null) {
				uoFK11 = new Integer(0);
				codice11 = "Non presente";
				descrizione11 = "Non presente";
			}
			
			if (uoFK12 == null) {
				uoFK12 = new Integer(0);
				codice12 = "Non presente";
				descrizione12 = "Non presente";
			}
			
			if (uoFK13 == null) {
				uoFK13 = new Integer(0);
				codice13 = "Non presente";
				descrizione13 = "Non presente";
			}
			
			if (uoFK14 == null) {
				uoFK14 = new Integer(0);
				codice14 = "Non presente";
				descrizione14 = "Non presente";
			}
			
			if (uoFK15 == null) {
				uoFK15 = new Integer(0);
				codice15 = "Non presente";
				descrizione15 = "Non presente";
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
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk09,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk10,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk11,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk12,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk13,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk14,
										qDmalmElUnitaOrganizzativeFlat.unitaOrganizzativaFk15,
										qDmalmElUnitaOrganizzativeFlat.codiceArea01,
										qDmalmElUnitaOrganizzativeFlat.codiceArea02,
										qDmalmElUnitaOrganizzativeFlat.codiceArea03,
										qDmalmElUnitaOrganizzativeFlat.codiceArea04,
										qDmalmElUnitaOrganizzativeFlat.codiceArea05,
										qDmalmElUnitaOrganizzativeFlat.codiceArea06,
										qDmalmElUnitaOrganizzativeFlat.codiceArea07,
										qDmalmElUnitaOrganizzativeFlat.codiceArea08,
										qDmalmElUnitaOrganizzativeFlat.codiceArea09,
										qDmalmElUnitaOrganizzativeFlat.codiceArea10,
										qDmalmElUnitaOrganizzativeFlat.codiceArea11,
										qDmalmElUnitaOrganizzativeFlat.codiceArea12,
										qDmalmElUnitaOrganizzativeFlat.codiceArea13,
										qDmalmElUnitaOrganizzativeFlat.codiceArea14,
										qDmalmElUnitaOrganizzativeFlat.codiceArea15,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea01,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea02,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea03,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea04,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea05,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea06,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea07,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea08,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea09,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea10,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea11,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea12,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea13,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea14,
										qDmalmElUnitaOrganizzativeFlat.descrizioneArea15,
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
										uoFK09,
										uoFK10,
										uoFK11,
										uoFK12,
										uoFK13,
										uoFK14,
										uoFK15,
										codice01,
										codice02,
										codice03,
										codice04,
										codice05,
										codice06,
										codice07,
										codice08,
										codice09,
										codice10,
										codice11,
										codice12,
										codice13,
										codice14,
										codice15,
										descrizione01,
										descrizione02,
										descrizione03,
										descrizione04,
										descrizione05,
										descrizione06,
										descrizione07,
										descrizione08,
										descrizione09,
										descrizione10,
										descrizione11,
										descrizione12,
										descrizione13,
										descrizione14,
										descrizione15,
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
