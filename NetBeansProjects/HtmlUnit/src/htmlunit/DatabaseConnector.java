/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Jacob
 */
public enum DatabaseConnector {
    INSTANCE;

    private Connection CONNECTION;
    private Statement STATEMENT;
    final private String QUOTEDB_URL = "jdbc:derby://localhost:1540/GlassFishConnPool";
    final private String QUOTEUSER = "admin";
    final private String QUOTEPASS = "admin";

    private static void RegisterDriver() {
        new ClientDriver();
    }

    public ResultSet executeSingleQuery(String sql) {
        ResultSet result = null;
        startStatement();
        startConnection();
        try {
            result = STATEMENT.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        stopStatement();
        stopConnection();
        return result;
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

    private void startConnection() {
        RegisterDriver();
        CONNECTION = null;
        try {
            CONNECTION = DriverManager.getConnection(QUOTEDB_URL, QUOTEUSER, QUOTEPASS);
        } catch (SQLException ex) {
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

}
