package waystonepropertymanagement.tenant.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import waystonepropertymanagement.tenant.login.Tenant;

public class DatabaseOperation {
    public static Statement stmtObj;
    public static Connection conObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
    
    
    
    public static boolean tenantValidate(String email, String password) {
        
        
        try {
            
            conObj = DataConnect.getConnection();
            pstmt = conObj.prepareStatement("Select email, password FROM tenant_login WHERE email = ? and password = ?");
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                //result found, means valid inputs
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
