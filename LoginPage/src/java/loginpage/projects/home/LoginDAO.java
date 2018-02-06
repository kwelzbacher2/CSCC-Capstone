/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginpage.projects.home;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import loginpage.projects.home.DataConnect;
/**
 *
 * @author Katie
 */
public class LoginDAO {
    
    public static boolean validate(String user, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            
            conn = DataConnect.getConnection();
            ps = conn.prepareStatement("Select username, password FROM tenant_login WHERE username = ? and password = ?");
            ps.setString(1, user);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                //result found, means valid inputs
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());
            return false;
        
        } finally {
            
                
              DataConnect.close(conn);
            
            
        }
        return false;
    }
}
