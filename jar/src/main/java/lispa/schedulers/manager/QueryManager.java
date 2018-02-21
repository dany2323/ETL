package lispa.schedulers.manager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;

import org.apache.log4j.Logger;

public class QueryManager {
	private static QueryManager instance;
	private static Logger logger = Logger.getLogger(QueryManager.class);

	private QueryManager() {

	}

	public synchronized static QueryManager getInstance()
			throws PropertiesReaderException, DAOException {
		if (instance == null) {
			instance = new QueryManager();
		}
		
		return instance;
	}

	public synchronized List<String> getQueryList(String file, String separator)
			throws Exception {

		List<String> out = new ArrayList<String>();
		String fileContent = "";
		String inputLine;

		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(
					file);
			BufferedReader in = new BufferedReader(new InputStreamReader(is));

			while ((inputLine = in.readLine()) != null) {
				fileContent += inputLine;
			}
			
			in.close();

			out = Arrays.asList(fileContent.split(separator));
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}

		return out;

	}

	public synchronized String getQuery(String file) throws Exception {

		String out = "";
		String inputLine;
		
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(
					file);
			BufferedReader in = new BufferedReader(new InputStreamReader(is));

			while ((inputLine = in.readLine()) != null) {
				out += inputLine;
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}

		return out;
	}

	public synchronized void executeQueryFromFile(String sqlfile)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			
			String sql = getQuery(sqlfile);
			
			ps = conn.prepareStatement(sql);
			ps.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (ps != null) {
				ps.close();
			}
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}
	}

	public synchronized void executeStatement(String query)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			
			ps = conn.prepareStatement(query);
			ps.execute();
			
			conn.commit();
			
			logger.info("ESEGUITO: " + query);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}
	}

	public synchronized void executeMultipleStatementsFromFile(String file,
			String separator) throws Exception {

		List<String> statements = getQueryList(file, separator);

		for (String statement : statements) {
			if (statement.length() < 5) {
				continue;
			}
			
			executeStatement(statement);
		}
	}
	
	public synchronized boolean executeMultipleStatementsFromFile(String file,
			String separatorTable, String separatorLine) throws Exception {

		List<String> records = getQueryList(file, separatorLine);

		for (String record : records) {
			String[] splitRecord = record.split(":");
			boolean flag = executeProcedure(splitRecord[0], splitRecord[1]);
			if (!flag) {
				return flag;
			}
		}
		return true;
	}
	
	public synchronized boolean executeProcedure(String backupTable, String targetTable)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection conn = null;
		CallableStatement cstmt = null;
		boolean flag = false;

		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			
			cstmt = conn.prepareCall("{? = call BACKUP_TARGET(?, ?, ?)}");
			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.setString(2, DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase());
			cstmt.setString(3, backupTable);
			cstmt.setString(4, targetTable);
			cstmt.executeUpdate();
			
			String stringFlag = cstmt.getString(1);
			if(stringFlag.equals("TRUE")) {
				flag = true;
			} else {
				flag = false;
			}
			conn.commit();
			logger.info("ESEGUITA PROCEDURE PER LE TABELLE: " + backupTable + " e " + targetTable);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		} finally {
			if (cstmt != null) {
				cstmt.close();
			}
			if (cm != null) {
				cm.closeConnection(conn);
			}
		}
		return flag;
	}
}
