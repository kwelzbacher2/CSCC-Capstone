/**
 * Roxanne Woodruff
 * CSCI 2999 Capstone 
 * Waystone Property Management Tenant Portal
 * DataConnect.java
 */
package waystonepropertymanagement.tenant.portal;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Data Connect is a Class that connects to the database.
 * @author Roxanne
 *
 */
public class DataConnect {

	public static Connection getConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = "jdbc:sqlserver://waystonepm.database.windows.net:1433;database=tenant_accounts;"
					+ "user=waystoneadmin@waystonepm;password=waystoneMGMT!;"
					+ "encrypt=true;trustServerCertificate=false;"
					+ "hostNameInCertificate=*.database.windows.net;loginTimeout=30";
			Connection con = DriverManager.getConnection(connectionUrl);
			return con;

		} catch (Exception ex) {
			System.out.println("Database.getConnection() Error -->" + ex.getMessage());
			return null;
		}
	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
