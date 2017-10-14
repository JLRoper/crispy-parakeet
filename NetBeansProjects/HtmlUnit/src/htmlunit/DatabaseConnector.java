/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import org.apache.derby.jdbc.ClientDriver;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Jacob
 */
public enum DatabaseConnector {
    INSTANCE;

    public DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMddhhmmss");
    private Connection CONNECTION;
    private Statement STATEMENT;
//    final private String QUOTEDB_URL = "jdbc:derby://localhost:1527/GlassFishConnPool";
    final private String QUOTEDB_URL = "jdbc:derby://localhost:1599/GlassFishConnPool";
    final private String QUOTEUSER = "admin";
    final private String QUOTEPASS = "admin";

    private static void RegisterDriver() {
        new ClientDriver();
    }

    public void setupConnection(boolean useConPool) {
        startConnection(useConPool);
        startStatement();
    }

    public void cleanupConnection() {
        stopStatement();
        stopConnection();
    }

    public String getFormattedTimestamp() {
        return TIMESTAMP_FORMAT.format(new Date(System.currentTimeMillis()));
    }

    public ResultSet executeQuery(String sql) {
        ResultSet result = null;
        try {
            result = STATEMENT.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int executeStandAloneUpdate(String sql) {
        int success = 0;
        try {
            success = STATEMENT.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return success;
    }

    private void startStatement() {
        STATEMENT = null;
        if (CONNECTION == null) {
            throw new RuntimeException("Failed to start statement, no connection available. ");
        }
        try {
            STATEMENT = CONNECTION.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).
                    log(Level.SEVERE, "Failed to start statement. ", ex);
        }
    }

    private void stopStatement() {
        try {
            STATEMENT.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    private void startConnection(boolean useConPool) {
        RegisterDriver();
        CONNECTION = null;
        // Look up the connection pool data source
        try {
            if (useConPool) {
                javax.naming.InitialContext ctx = new javax.naming.InitialContext();
                javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("jdbc/__default");
                CONNECTION = ds.getConnection();
            } else {
                CONNECTION = DriverManager.getConnection(QUOTEDB_URL, QUOTEUSER, QUOTEPASS);
            }
        } catch (Throwable ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    private void stopConnection() {
        try {
            CONNECTION.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public String testQuery(boolean useConnPool) throws SQLException {
        String sql = "SELECT * FROM APP.QUOTE_TEST WHERE SYMBOL = 'XY2'";
        DatabaseConnector.INSTANCE.setupConnection(useConnPool);
        ResultSet rs = DatabaseConnector.INSTANCE.executeQuery(sql);
        String out = "";
        while (rs != null && rs.next()) {
            out += (rs.getString("SYMBOL"));
            System.out.println(rs.getString("SYMBOL"));
            out += (rs.getString("PRICE"));
            System.out.println(rs.getString("PRICE"));
            out += (rs.getString("VOLUME"));
            System.out.println(rs.getString("VOLUME"));
            out += (rs.getString("TIMESTAMP"));
            System.out.println(rs.getString("TIMESTAMP"));
        }
        DatabaseConnector.INSTANCE.cleanupConnection();
        return out;
    }

}
