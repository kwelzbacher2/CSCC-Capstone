package waystonepropertymanagement.tenant.portal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import waystonepropertymanagement.tenant.portal.TenantJSFBean;
import waystonepropertymanagement.tenant.portal.DataConnect;

public class DatabaseOperation {
	public static Statement stmtObj;
	public static Connection conObj;
	public static ResultSet resultSetObj;
	public static PreparedStatement pstmt;

	public static boolean tenantValidate(String email, String password) {

		Connection con = null;
		PreparedStatement ps = null;
		
		try {

			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select email, password FROM tenant_login WHERE email = ? and password = ?");
			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// result found, means valid inputs
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
			return false;

		} finally {

			DataConnect.close(conObj);

		}
		return false;
	}
}