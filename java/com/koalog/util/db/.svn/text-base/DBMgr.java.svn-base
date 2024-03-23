package com.koalog.util.db;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Category;

/**
 * A utility class used to access a database.
 *
 * @author Matthieu Philip
 */
public class DBMgr {
    // TODO: move in util
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(DBMgr.class);
    private static Map _connections = new HashMap();
    private static Map _contexts = new HashMap();

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The connection properties. */
    private Properties _props;
    /** The datasource. */
    private DataSource _dataSource;

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * A factory method that constructs a new <code>QueryContext</code>
     * instance.
     * @return a new <code>QueryContext</code>.
     */
    public QueryContext newQueryContext() throws SQLException {
        return new QueryContext();
    }

    /**
     * Indicate whether this manager has loaded the properties.
     * @return <code>true</code> if the properties have been
     * loaded, false otherwise.
     */
    public boolean isLoaded() {
        return (_props != null) || (_dataSource != null);
    }

    /**
     * Load the configuration properties and check the connection with the
     * database.
     * @param props a set of properties
     * @throws SQLException when the connection to the database fails.
     * @throws ClassNotFoundException when the driver class cannot be found.
     */
    public void load(Properties props) throws ClassNotFoundException,
        SQLException {
        _dataSource = null;
        _props = props;
        Class.forName(_props.getProperty("connection.driver"));
        checkConnection();
    }

    /**
     * Load the configuration properties and check the connection with the
     * database.
     * @param loc a properties file
     * @throws IOException when the properties cannot be loaded.
     * @throws SQLException when the connection to the database fails.
     * @throws ClassNotFoundException when the driver class cannot be found.
     */
    public void load(String loc) throws ClassNotFoundException,
        SQLException, IOException {
        _dataSource = null;
        _props = new Properties();
        _props.load(DBMgr.class.getClassLoader().getResourceAsStream(loc));
        Class.forName(_props.getProperty("connection.driver"));
        checkConnection();
    }

    /**
     * Load the configuration properties frrom a <code>DataSource</code>
     * and check the connection with the database.
     * @param dataSource the <code>DataSource</code> to be used.
     * @throws SQLException when the connection to the database fails.
     */
    public void load(DataSource dataSource) throws SQLException {
        _props = null;
        _dataSource = dataSource;
        checkConnection();
    }

    /**
     * Get a new database connection.
     * @return a valid <code>Connection</code>.
     * @throws SQLException When the connection cannot be established.
     */
    private synchronized Connection getConnection() throws SQLException {
        // Try to get a connection already used by the current thread
        Connection con = (Connection)_connections.get(Thread.currentThread());
        if (con == null) {
            if (_props != null) {
                con = DriverManager.getConnection(
                    _props.getProperty("connection.url"), _props);
            } else {
                con = _dataSource.getConnection();
            }
            _connections.put(Thread.currentThread(), con);
        }
        return con;
    }

    /**
     * Close a database connection.
     * @return a valid <code>Connection</code>.
     * @throws SQLException When the connection cannot be established.
     */
    private synchronized void closeConnection(Connection con) 
        throws SQLException {
        _connections.remove(Thread.currentThread());
        con.close();
    }

    /**
     * Check that a connection with the database can be established.
     * @exception SQLException When the connection cannot be established.
     */
    public synchronized void checkConnection() throws SQLException {
            QueryContext qc = new QueryContext();
            qc.close();
    }

    /**
     * Escape a string so that it can be used inside a quoted statement
     * of a sql query. In other words, single quotes are escaped.
     * @param s The string to process.
     * @return A string that can be quoted in sql, or <code>null</code> if the
     * input string is <code>null</code>.
     */
    public String sqlEscape(String s) {
        if (s == null) {
            return null;
        }
        int idx = s.indexOf('\'');
        while (idx > -1) {
            s = s.substring(0, idx)
                + "\\'"
                + s.substring(idx + 1);
            idx = s.indexOf('\'', idx + 2);
        }
        return s;
    }

    /**
     * An encapsulation of the context required to perform a query.
     * The idea behind this encapsulation is to use connection pools.
     * @author Matthieu Philip
     */
    public class QueryContext {
        private Connection con = null;
        private Statement stmt = null;
        private ResultSet rs = null;

        /**
         * Default constructor. Creates a new connection and a new statement.
         * @throws SQLException when the connection to the database cannot be
         * established.
         */
        public QueryContext() throws SQLException {
            con = getConnection();
            synchronized(_contexts) {
                // Add the context to the set of contexts using that connection
                Set contexts = (Set)_contexts.get(con);
                if (contexts == null) {
                    contexts = new HashSet();
                    _contexts.put(con, contexts);
                }
                contexts.add(QueryContext.this);
            }
            stmt = con.createStatement();
        }

        /**
         * Execute a SQL query.
         * @param sqlReq the query to execute.
         * @return a <code>ResultSet</code> containing the found rows.
         * @throws SQLException when the query cannot be executed.
         */
        public final ResultSet query(String sqlReq) throws SQLException {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    cat.error("Could not close result set.", e);
                }
                rs = null;
            }
            rs = stmt.executeQuery(sqlReq);
            return rs;
        }

        /**
         * Execute a SQL update.
         * @param sqlReq the update to execute.
         * @throws SQLException when the update cannot be executed.
         */
        public final void update(String sqlReq)
            throws SQLException {
            stmt.executeUpdate(sqlReq);
        }

        /**
         * Execute a SQL update that return generated keys.
         * @param sqlReq the update to execute.
         * @return a <code>ResultSet</code> containing the generated keys.
         * @throws SQLException when the update cannot be executed.
         */
        public final ResultSet updateWithKeys(String sqlReq)
            throws SQLException {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    cat.error("Could not close result set.", e);
                }
                rs = null;
            }
            stmt.executeUpdate(sqlReq, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            return rs;
        }

        /**
         * Close this query context.
         */
        public void close() {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    cat.error("Could not close result set.", e);
                }
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    cat.error("Could not close statement.", e);
                }
                stmt = null;
            }
            if (con != null) {
                try {
                    synchronized(_contexts) {
                        Set contexts = (Set)_contexts.get(con);
                        contexts.remove(QueryContext.this);
                        if (contexts.size() == 0) {
                            _contexts.remove(con);
                            closeConnection(con);
                        }
                    }
                } catch (SQLException e) {
                    cat.error("Could not close connection.", e);
                }
                con = null;
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.7  2005/06/08 20:00:26  mat
 * Fixed bug: escape not working on null strings.
 *
 * Revision 1.6  2005/02/23 13:59:02  mat
 * Added a query context factory constructor.
 *
 * Revision 1.5  2005/02/16 10:57:19  mat
 * Updated the connection management.
 *
 * Revision 1.4  2005/02/09 11:54:09  mat
 * Added the possibility to load from a datasource or a property set.
 *
 * Revision 1.3  2005/01/12 11:29:04  mat
 * fixed bug #0021223: saved quote unid not retrieved.
 *
 * Revision 1.2  2004/12/09 10:29:09  yan
 * added comment
 *
 * Revision 1.1  2004/11/30 15:41:40  mat
 * Initial revision.
 *
 */
