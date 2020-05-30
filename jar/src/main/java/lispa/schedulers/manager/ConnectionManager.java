package lispa.schedulers.manager;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DM_ALM_DB_URL;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DM_ALM_DRIVER_CLASS_NAME;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DM_ALM_USER;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DM_ALM_PSW;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.ELETTRA_DB_URL;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.ELETTRA_DRIVER_CLASS_NAME;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.ELETTRA_USER;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.ELETTRA_PSW;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.H2_DRIVER_CLASS_NAME;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.PROPERTIES_READER_FILE_NAME;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SIRE_CURRENT_PSW;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SIRE_CURRENT_URL;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SIRE_CURRENT_USERNAME;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SIRE_HISTORY_PSW;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SIRE_HISTORY_URL;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SIRE_HISTORY_USERNAME;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SISS_CURRENT_PSW;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SISS_CURRENT_URL;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SISS_CURRENT_USERNAME;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SISS_HISTORY_PSW;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SISS_HISTORY_URL;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SISS_HISTORY_USERNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;

import org.apache.log4j.Logger;

import it.lispa.jotm.datasource.crypt.EncryptionHelper;
import it.lispa.jotm.datasource.crypt.support.BaseEncryptionHelper;

/**
 * Visto che l’applicazione ha necessità di gestire la concorrenzialità
 * nell’accesso, in quanto si è implementato un accesso multithread alle fonti,
 * il ConnectionManager rappresenta un pool di connessioni. Questa tecnica di
 * Data Access Management ha un impatto notevolmente positivo sull’efficienza
 * generale del software ed elimina l’inutile latenza di esecuzione introdotta
 * dalla creazioni di molteplici connessioni che richiedono operazioni
 * dispendiose come il caricamento del driver, l’autenticazione e l’allocazione
 * delle risorse necessarie all’esecuzione delle query. L'idea alla base di
 * questa strategia è quella della presenza di un pool di connessioni. Quando
 * l’ETL richiede una connessione al database, possono verificarsi due
 * situazioni distinte: 1. Il pool è vuoto, non contiene alcuna connessione
 * disponibile 2. Nel pool è presente almeno una connessione disponibile ed
 * attiva Nel primo caso, è necessario creare una nuova connessione daccapo
 * percorrendo tutti i passi visti in precedenza. Nel secondo caso invece, si
 * preleva una connessione dal pool, si controlla se è ancora attiva, ed in caso
 * positivo la si restituisce. Al termine dell'utilizzo della connessione
 * assegnata, il modulo software non chiude tale connessione, ma la rilascia nel
 * pool in modo che sia disponibile per utilizzi successivi. Il pool di
 * connessioni è rappresentato da una coda FIFO, per minimizzare il numero di
 * connessioni che diventano inutilizzabili per TIMEOUT.
 * 
 * @author fdeangel
 * 
 */
public class ConnectionManager{

	private static Logger logger = Logger.getLogger(ConnectionManager.class);

	private static ConnectionManager instance = null;
	private static PropertiesReader propertiesReader = null;

	// POOL DI CONNESSIONI AL DATAMART
	private static List<Connection> connectionOraclePool;

	// POOL DI CONNESSIONI ALLA FONTE ELETTRA
	private static List<Connection> connectionOracleFonteElettraPool;

	// POOL DI CONNESSIONI A SIRE
	private static List<Connection> connectionSireCurrentPool;
	private static List<Connection> connectionSireHistoryPool;

	// POOL DI CONNESSIONI A SISS
	private static List<Connection> connectionSissCurrentPool;
	private static List<Connection> connectionSissHistoryPool;

	// NUMERO DI CONNESSIONI CREATE
	private static long oracle, oracleFonteElettra, sirecurr, sirehist,
			sisscurr, sisshist;

	// GF - Per cifratura password
	private EncryptionHelper encryptionHelper = new BaseEncryptionHelper();
	// GF - Per cifratura password
				
	private ConnectionManager() throws DAOException, PropertiesReaderException {

		loadProps();

		DriverManager.setLoginTimeout(10);

		connectionOraclePool = new LinkedList<Connection>();

		connectionOracleFonteElettraPool = new LinkedList<Connection>();

		connectionSireCurrentPool = new LinkedList<Connection>();
		connectionSireHistoryPool = new LinkedList<Connection>();

		connectionSissCurrentPool = new LinkedList<Connection>();
		connectionSissHistoryPool = new LinkedList<Connection>();

		oracle = 0;
		oracleFonteElettra = 0;
		sirecurr = 0;
		sirehist = 0;
		sisscurr = 0;
		sisshist = 0;
	}

	/**
	 * il Connection Pool, in quanto unico nell'ambito della vita
	 * dell'applicazione, è stato implementato utilizzando un design-pattern
	 * Singleton, con costruttore vuoto privato, variabile statica d'istanza e
	 * metodo getInstance() per la restituzione della stessa.
	 * 
	 * @return
	 * @throws DAOException
	 * @throws PropertiesReaderException
	 */
	public synchronized static ConnectionManager getInstance() {
		try {
			if (instance == null) {
				instance = new ConnectionManager();
				logger.debug("*** ConnectionManager - INSTANZIATO CONNECTION POOL ***");
			}
		} catch (DAOException e) {
			logger.debug(e);
		} catch (PropertiesReaderException pre) {
			logger.debug(pre);
		}

		return instance;
	}

	/**
	 * Restituisce una connessione restituita dal pool. Se tale connessione è
	 * nel frattempo diventata inattiva, il metodo la elimina definitivamente e
	 * chiama se stesso ricorsivamente finchè non trova una connessione valida
	 * ed attiva.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public synchronized Connection getConnectionOracle() throws DAOException {

		Connection c = null;

		try {
			if (!connectionOraclePool.isEmpty()) {
				c = connectionOraclePool.remove(0);
				if (!isAlive(c)) {
					return getConnectionOracle();
				}
			} else {
				logger.debug("*** ConnectionManager - NUOVA CONNESSIONE ORACLE ***");

				Class.forName(propertiesReader
						.getProperty(DM_ALM_DRIVER_CLASS_NAME));

				c = DriverManager.getConnection(
						propertiesReader.getProperty(DM_ALM_DB_URL),
						propertiesReader.getProperty(DM_ALM_USER),
						encryptionHelper.decrypt(propertiesReader.getProperty(DM_ALM_PSW)));
				
				oracle++;
				if (oracle > 50)
					logger.warn("*** ConnectionManager - ATTENZIONE, LE CONNESSIONI A ORACLE SONO "
							+ oracle + "***");
			}

		} catch (SQLException | ClassNotFoundException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		}

		return c;
	}

	/**
	 * Restituisce una connessione restituita dal pool. Se tale connessione è
	 * nel frattempo diventata inattiva, il metodo la elimina definitivamente e
	 * chiama se stesso ricorsivamente finchè non trova una connessione valida
	 * ed attiva.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public synchronized Connection getConnectionOracleFonteElettra()
			throws DAOException {

		Connection c = null;

		try {
			if (!connectionOracleFonteElettraPool.isEmpty()) {
				c = connectionOracleFonteElettraPool.remove(0);
				if (!isAlive(c)) {
					return getConnectionOracleFonteElettra();
				}
			} else {
				logger.debug("*** ConnectionManager - NUOVA CONNESSIONE ORACLE FONTE ELETTRA ***");
				
				Class.forName(propertiesReader
						.getProperty(ELETTRA_DRIVER_CLASS_NAME));

				c = DriverManager.getConnection(
						propertiesReader.getProperty(ELETTRA_DB_URL),
						propertiesReader.getProperty(ELETTRA_USER),
						encryptionHelper.decrypt(propertiesReader.getProperty(ELETTRA_PSW)));
				oracleFonteElettra++;
				if (oracleFonteElettra > 50)
					logger.warn("*** ConnectionManager - ATTENZIONE, LE CONNESSIONI A ORACLE FONTE ELETTRA SONO "
							+ oracleFonteElettra + "***");
			}

		} catch (SQLException | ClassNotFoundException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		}

		return c;
	}

	/**
	 * Restituisce una connessione restituita dal pool. Se tale connessione è
	 * nel frattempo diventata inattiva, il metodo la elimina definitivamente e
	 * chiama se stesso ricorsivamente finchè non trova una connessione valida
	 * ed attiva.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public synchronized Connection getConnectionSIRECurrent()
			throws DAOException {

		Connection c = null;

		try {

			if (!connectionSireCurrentPool.isEmpty()) {
				c = connectionSireCurrentPool.remove(0);
				if (!isAlive(c)) {
					return getConnectionSIRECurrent();
				}
			} else {
				logger.debug("*** ConnectionManager - NUOVA CONNESSIONE H2 SIRE CURRENT ***");
				
				Class.forName(propertiesReader
						.getProperty(H2_DRIVER_CLASS_NAME));

				c = DriverManager.getConnection(
						propertiesReader.getProperty(SIRE_CURRENT_URL),
						propertiesReader.getProperty(SIRE_CURRENT_USERNAME),
						encryptionHelper.decrypt(propertiesReader.getProperty(SIRE_CURRENT_PSW)));
				sirecurr++;
			}

		} catch (SQLException | ClassNotFoundException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		}

		return c;

	}

	/**
	 * Restituisce una connessione restituita dal pool. Se tale connessione è
	 * nel frattempo diventata inattiva, il metodo la elimina definitivamente e
	 * chiama se stesso ricorsivamente finchè non trova una connessione valida
	 * ed attiva.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public synchronized Connection getConnectionSIREHistory()
			throws DAOException {

		Connection c = null;

		try {

			if (!connectionSireHistoryPool.isEmpty()) {
				c = connectionSireHistoryPool.remove(0);
				if (!isAlive(c)) {
					return getConnectionSIREHistory();
				}
			} else {
				logger.debug("*** ConnectionManager - NUOVA CONNESSIONE H2 SIRE HISTORY ***");
				
				Class.forName(propertiesReader
						.getProperty(H2_DRIVER_CLASS_NAME));

				c = DriverManager.getConnection(
						propertiesReader.getProperty(SIRE_HISTORY_URL),
						propertiesReader.getProperty(SIRE_HISTORY_USERNAME),
						encryptionHelper.decrypt(propertiesReader.getProperty(SIRE_HISTORY_PSW)));
				sirehist++;
			}

		} catch (SQLException | ClassNotFoundException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		}

		return c;

	}

	/**
	 * Restituisce una connessione restituita dal pool. Se tale connessione è
	 * nel frattempo diventata inattiva, il metodo la elimina definitivamente e
	 * chiama se stesso ricorsivamente finchè non trova una connessione valida
	 * ed attiva.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public synchronized Connection getConnectionSISSCurrent()
			throws DAOException {

		Connection c = null;

		try {

			if (!connectionSissCurrentPool.isEmpty()) {
				c = connectionSissCurrentPool.remove(0);
				if (!isAlive(c)) {
					return getConnectionSISSCurrent();
				}
			} else {
				logger.debug("*** ConnectionManager - NUOVA CONNESSIONE H2 SISS CURRENT ***");
				
				Class.forName(propertiesReader
						.getProperty(H2_DRIVER_CLASS_NAME));

				c = DriverManager.getConnection(
						propertiesReader.getProperty(SISS_CURRENT_URL),
						propertiesReader.getProperty(SISS_CURRENT_USERNAME),
						encryptionHelper.decrypt(propertiesReader.getProperty(SISS_CURRENT_PSW)));
				sisscurr++;
			}

		} catch (SQLException | ClassNotFoundException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		}

		return c;

	}

	/**
	 * Restituisce una connessione restituita dal pool. Se tale connessione è
	 * nel frattempo diventata inattiva, il metodo la elimina definitivamente e
	 * chiama se stesso ricorsivamente finchè non trova una connessione valida
	 * ed attiva.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public synchronized Connection getConnectionSISSHistory()
			throws DAOException {

		Connection c = null;

		try {

			if (!connectionSissHistoryPool.isEmpty()) {
				c = connectionSissHistoryPool.remove(0);
				if (!isAlive(c)) {
					return getConnectionSISSHistory();
				}
			} else {
				logger.debug("*** ConnectionManager - NUOVA CONNESSIONE H2 SISS HISTORY ***");
				
				Class.forName(propertiesReader
						.getProperty(H2_DRIVER_CLASS_NAME));

				c = DriverManager.getConnection(
						propertiesReader.getProperty(SISS_HISTORY_URL),
						propertiesReader.getProperty(SISS_HISTORY_USERNAME),
						encryptionHelper.decrypt(propertiesReader.getProperty(SISS_HISTORY_PSW)));
				sisshist++;
			}

		} catch (SQLException | ClassNotFoundException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		}

		return c;

	}

	/**
	 * Quando il client ha terminato di effettuare le operazioni necessarie sul
	 * database, esso non elimina le risorse ma le rilascia invocando questo
	 * metodo.
	 * 
	 * @param conn
	 * @throws DAOException
	 */
//	public synchronized void closeConnection(Connection conn) throws DAOException {
//
//		try {
//			if (isAlive(conn)) {
//				try {
//					conn.setAutoCommit(true);
//				} catch (SQLException e) {
//					return;
//				}
//
//				String URL = conn.getMetaData().getURL();
//				String user = conn.getMetaData().getUserName();
//
//				// a seconda dell'url della connessione, la inserisco nel Pool
//				// apposito
//				if (URL.equals(propertiesReader.getProperty(DM_ALM_DB_URL)) && user.equalsIgnoreCase(propertiesReader.getProperty(DM_ALM_USER))) {
//					connectionOraclePool.add(conn);
//				} else if (URL.equals(propertiesReader
//						.getProperty(ELETTRA_DB_URL)) && user.equalsIgnoreCase(propertiesReader.getProperty(ELETTRA_USER))) {
//					connectionOracleFonteElettraPool.add(conn);
//				}
//				// Elimino tutto cio' che nell'URL della connessione viene dopo
//				// il carattere ';'
//				else if (URL.equals(propertiesReader.getProperty(
//						SIRE_CURRENT_URL).split(";")[0])) {
//					connectionSireCurrentPool.add(conn);
//				} else if (URL.equals(propertiesReader.getProperty(
//						SIRE_HISTORY_URL).split(";")[0])) {
//					connectionSireHistoryPool.add(conn);
//				} else if (URL.equals(propertiesReader.getProperty(
//						SISS_CURRENT_URL).split(";")[0])) {
//					connectionSissCurrentPool.add(conn);
//				} else if (URL.equals(propertiesReader.getProperty(
//						SISS_HISTORY_URL).split(";")[0])) {
//					connectionSissHistoryPool.add(conn);
//				}
//			} else {
//
//			}
//		} catch (SQLException e) {
//			throw new DAOException(e);
//		}
//	}

	public synchronized boolean isAlive(Connection conn) {

		try {
			return (conn != null && (!conn.isClosed()) && conn.isValid(5));
		} catch (SQLException e) {
			return false;
		}

	}

	public synchronized void dismiss() {

		while (!connectionOraclePool.isEmpty()) {
			try {
				connectionOraclePool.remove(0).close();

			} catch (SQLException ex) {
			}
		}
		oracle = 0;

		logger.debug("*** ConnectionManager - IL CONNECTION POOL E' STATO SVUOTATO. LA SUA SIZE ORA E' "
				+ connectionOraclePool.size());

		while (!connectionOracleFonteElettraPool.isEmpty()) {
			try {
				connectionOracleFonteElettraPool.remove(0).close();

			} catch (SQLException ex) {
			}
		}
		oracleFonteElettra = 0;

		logger.debug("*** ConnectionManager - IL CONNECTION POOL FONTE ELETTRA E' STATO SVUOTATO. LA SUA SIZE ORA E' "
				+ connectionOracleFonteElettraPool.size());
	}

	private static void loadProps() throws DAOException {
		try {
			propertiesReader = new PropertiesReader(PROPERTIES_READER_FILE_NAME);

		} catch (PropertiesReaderException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		}
	}

	public static void printInfo() {
		logger.debug("___*** STATO DEL CONNECTION MANAGER ***___");

		logger.debug("___*** CONNESSIONI ORACLE CREATE " + oracle);

		logger.debug("___*********** DI CUI RILASCIATE "
				+ connectionOraclePool.size());

		logger.debug("___*** CONNESSIONI ORACLE FONTE ELETTRA CREATE "
				+ oracleFonteElettra);

		logger.debug("___*********** DI CUI RILASCIATE "
				+ connectionOracleFonteElettraPool.size());

		logger.debug("___*** CONNESSIONI SIRE HISTORY CREATE " + sirehist);

		logger.debug("___***************** DI CUI RILASCIATE "
				+ connectionSireHistoryPool.size());

		logger.debug("___*** CONNESSIONI SIRE CURRENT CREATE " + sirecurr);

		logger.debug("___***************** DI CUI RILASCIATE "
				+ connectionSireCurrentPool.size());

		logger.debug("___*** CONNESSIONI SISS HISTORY CREATE " + sisshist);

		logger.debug("___***************** DI CUI RILASCIATE "
				+ connectionSissHistoryPool.size());

		logger.debug("___*** CONNESSIONI SISS CURRENT CREATE " + sisscurr);

		logger.debug("___***************** DI CUI RILASCIATE "
				+ connectionSissCurrentPool.size());

		logger.debug("___*** |||||||||||||||||||||||||||||||||||||||||||||||| ***___");

	}

	public static void sysoutInfo() {

	}
	
	public void close(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
            logger.debug("*** ConnectionManager - CONNESSIONE ORACLE CHIUSA ***");
        }
    }

    public void closeQuietly(Connection conn) {
        try {
            close(conn);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }
    
    public void close(Statement stat) throws SQLException {
        if (stat != null) {
            stat.close();
        }
    }

    public void closeQuietly(Statement stat) {
        try {
            close(stat);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }
    
    public void close(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    public void closeQuietly(ResultSet rs) {
        try {
            close(rs);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }

}
