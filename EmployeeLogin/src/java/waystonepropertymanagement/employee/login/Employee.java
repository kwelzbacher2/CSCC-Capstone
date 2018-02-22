/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waystonepropertymanagement.employee.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Katie
 */
@ManagedBean
@SessionScoped
public class Employee {
    private String username;
    private int employeeID;
    private String firstName;
    private String lastName;
    private String middleInit;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String phone;
    private String dob;
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public int getEmployeeID(){
        return employeeID;
    }
    
    public void setEmployeeID(int employeeID){
        this.employeeID = employeeID;
    }
    public String getFirstName(){
        return firstName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getLastName(){
        return lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getMiddleInit(){
        return middleInit;
    }
    
    public void setMiddleInit(String middleInit){
        this.middleInit = middleInit;
    }
    public String getAddress(){
        return address;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    public String getCity(){
        return city;
    }
    
    public void setCity(String city){
        this.city = city;
    }
    public String getState(){
        return state;
    }
    
    public void setState(String state){
        this.state = state;
    }
    public String getZipcode(){
        return zipcode;
    }
    
    public void setZipcode(String zipcode){
        this.zipcode = zipcode;
    }
    
   public String getPhone(){
        return phone;
    }
    
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getDOB(){
        return dob;
    }
    
    public void setDOB(String dob){
        this.dob = dob;
    }
    
    public String updateEmployeeTable(){
        Connection connect = null;
        PreparedStatement prepstmt;        
              
            try {
            
                connect = DataConnect.getConnection();
                prepstmt = connect.prepareStatement("UPDATE employee_accounts SET firstName = ?, lastName = ?, middleInt = ?, "
                    + "address = ?, city = ?, state = ?, zipcode = ?, phone = ?, dob = ? WHERE username = ?");
                prepstmt.setString(1, firstName);
                prepstmt.setString(2, lastName);
                prepstmt.setString(3, middleInit);
                prepstmt.setString(4, address);
                prepstmt.setString(5, city);
                prepstmt.setString(6, state);
                prepstmt.setString(7, zipcode);
                prepstmt.setString(8, phone);
                prepstmt.setString(9, dob);
                prepstmt.setString(10, username);
            
                prepstmt.executeQuery();
                } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
            } finally {    
              DataConnect.close(connect);
            }
            return "employeeProfile";
        
     }
    
    
}
