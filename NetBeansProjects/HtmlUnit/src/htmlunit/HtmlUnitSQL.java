/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import org.apache.derby.jdbc.ClientDriver;
import java.sql.*;

/**
 *
 * @author Jacob
 */
public class HtmlUnitSQL {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "apache_derby_net";
    static final String DB_URL = "jdbc:derby://localhost:1527/sample";
    

    //  Database credentials
    static final String USER = "app";
    static final String PASS = "app";

    private static void RegisterDriver(){
        new ClientDriver();
    }
    
    
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            RegisterDriver();
            
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT personid, firstname, l aastname, address FROM PERSONS";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("personid");
                String address = rs.getString("address");
                String first = rs.getString("firstname");
                String last = rs.getString("lastname");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Address: " + address);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
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
}
