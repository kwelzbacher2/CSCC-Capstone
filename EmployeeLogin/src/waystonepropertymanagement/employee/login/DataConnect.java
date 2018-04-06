/*
 * CSCI Capstone 2999 Final Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * DataConnect Class contains method to connect to the database and close the connection
 * @author KWelzbacher
 */
public class DataConnect {
    
    
    
    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://waystonepm.database.windows.net:1433;database=tenant_accounts;"
                                    + "user=waystoneadmin@waystonepm;password=waystoneMGMT!;"
                                    + "encrypt=true;trustServerCertificate=false;"
                                    + "hostNameInCertificate=*.database.windows.net;loginTimeout=30";
            Connection conn = DriverManager.getConnection(connectionUrl);
            return conn;
            
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        }
    }
    
    public static void close(Connection conn) {
        try{
            conn.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
