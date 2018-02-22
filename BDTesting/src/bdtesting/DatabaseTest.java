/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdtesting;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Katie
 */
public class DatabaseTest {

    private static Scanner scanner = new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String user;
        String pass;
        String firstName = "didn't change";
        try {
            //load the driver for MySQL
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver loaded");
            
            //make a connection to database
            String connectionUrl = "jdbc:sqlserver://waystonepm.database.windows.net:1433;database=tenant_accounts;"
                                    + "user=waystoneadmin@waystonepm;password=WaystoneMGMT!;"
                                    + "encrypt=true;trustServerCertificate=false;"
                                    + "hostNameInCertificate=*.database.windows.net;loginTimeout=30";
            Connection conn = DriverManager.getConnection(connectionUrl);
            System.out.println("Database connected");
            
            String prompt1 = "Please give a username:";
            String prompt2 = "Please give a password:";
            user = getUserInput(prompt1);
            pass = getUserInput(prompt2);
            
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT username, password FROM employee_login WHERE username = ? and password = ?") ;
             preparedStatement.setString(1, user);
             preparedStatement.setString(2, pass);
            ResultSet rs = preparedStatement.executeQuery();
            
            PreparedStatement prepst = conn.prepareStatement("SELECT firstName FROM employee_accounts WHERE username = 'kwelz@waystone.com'");
                
                ResultSet rset = prepst.executeQuery();
                while(rset.next()){
                firstName = rset.getString("firstName");
                }
                
            if(rs.next()) {
                System.out.println("Correct login credentials");
                
            
                System.out.println("Hello" + firstName);
            } else {
                System.out.println("Incorrect login credentials");
            }
            
            
            
        } catch (ClassNotFoundException  e) {
            e.printStackTrace();
        } catch(SQLException ie){
            ie.printStackTrace();
        }
    }
    
    private static String getUserInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.next();
        return input;
    }
}
