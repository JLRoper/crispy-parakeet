/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;

/**
 *
 * @author Jacob
 */
public class INT_DatabaseConnector {

    @Test
    public void executeQuery_runQuery_dataReturned() throws SQLException {
        String sql = "SELECT * FROM APP.QUOTE_TEST WHERE SYMBOL = 'XY2'";
        DatabaseConnector.INSTANCE.setupConnection(false);
        ResultSet rs = DatabaseConnector.INSTANCE.executeQuery(sql);
        while (rs != null && rs.next()) {
            System.out.println(rs.getString("SYMBOL"));
            System.out.println(rs.getString("PRICE"));
            System.out.println(rs.getString("VOLUME"));
            System.out.println(rs.getString("TIMESTAMP"));
        }
        DatabaseConnector.INSTANCE.cleanupConnection();
    }

}
