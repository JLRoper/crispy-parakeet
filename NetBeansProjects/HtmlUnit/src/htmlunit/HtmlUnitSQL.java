/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import org.apache.derby.jdbc.ClientDriver;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.derby.jdbc.ClientDataSource;
import java.sql.Timestamp;
import java.time.Instant;

/**
 *
 * @author Jacob
 */
public class HtmlUnitSQL {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "apache_derby_net";
    static final String DB_URL = "jdbc:derby://localhost:1528/sample";

    //  Database credentials
//    static final String QUOTEDB_URL = "jdbc:derby:c:/Users/Jacob/.netbeans-derby/TESTQuoteDB";
    static final String QUOTEDB_URL = "jdbc:derby://localhost:1599/GlassFishConnPool";
    static final String QUOTEUSER = "admin";
    static final String QUOTEPASS = "admin";
    //  Database credentials
    static final String USER = "app";
    static final String PASS = "app";

    private static void RegisterDriver() {
        new ClientDriver();
    }

    public static String TEST_insertQuote() {
        return "INSERT INTO APP.QUOTE_TEST "
                + "(SYMBOL, PRICE, TIMESTAMP) "
                + "VALUES('XY2', 12.34, '" + java.sql.Timestamp.from(Instant.now()).toString().replace("-", "").replace(".", "").substring(0, 14)+ "')";
    }

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
//            RegisterDriver();

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
//            conn = DriverManager.getConnection(QUOTEDB_URL, QUOTEUSER, QUOTEPASS);
//            conn = getJNDIConnection();
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
//            stmt = conn.createStatement();
            String sql;
//            sql = "SELECT personid, firstname, lastname, address FROM PERSONS";

            sql = "SELECT * FROM APP.TABLETESTFROMNB";
            DatabaseConnector.INSTANCE.setupConnection();
            ResultSet rs = DatabaseConnector.INSTANCE.executeQuery(sql);

//            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
//            rs.beforeFirst();
            while (rs != null && rs.next()) {

                System.out.println(rs.getString("TEST"));
                System.out.println(rs.getString("TESTSTRING"));

                //Retrieve by column name
//                int id = rs.getInt("personid");
//                String address = rs.getString("address");
//                String first = rs.getString("firstname");
//                String last = rs.getString("lastname");
                //Display values
//                System.out.print("ID: " + id);
//                System.out.print(", Address: " + address);
//                System.out.print(", First: " + first);
//                System.out.println(", Last: " + last);
            }
            //STEP 6: Clean-up environment
//            rs.close();
//            stmt.close();
//            conn.close();
            DatabaseConnector.INSTANCE.cleanupConnection();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main

    static Connection getJNDIConnection() {
        String DATASOURCE_CONTEXT = "java:comp/DefaultDataSource";
        Connection result = null;
        try {
            Context initialContext = new InitialContext();
            if (initialContext == null) {
                System.out.println("JNDI problem. Cannot get InitialContext.");
            }
            DataSource datasource = (ClientDataSource) initialContext.lookup(DATASOURCE_CONTEXT);
            if (datasource != null) {
                result = datasource.getConnection();
            } else {
                System.out.println("Failed to lookup datasource.");
            }
        } catch (NamingException ex) {
            System.out.println("Cannot get connection: " + ex);
        } catch (SQLException ex) {
            System.out.println("Cannot get connection: " + ex);
        }
        return result;
    }
}
