/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waystonepropertymanagement.employee.login;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Katie
 */
@ManagedBean (name="employee")
@SessionScoped
public class Employee implements Serializable{
    private static final long serialVersionUID = 1094801825228386363L;
    
    private String pwd;
    private String msg;
    
    
    private String email;
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
    
    public List<Employee>employeeListFromDB;
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
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
    public String validateUsernamePassword() {
         boolean valid = DatabaseOperation.empValidate(email, pwd);
         if(valid) {
             HttpSession session = SessionUtils.getSession();
             session.setAttribute("username", email);
             return "employeeAdmin";
         } else {
             FacesContext.getCurrentInstance().addMessage("loginForm:password",
                     new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username and Password",
                     "Please enter correct Username and Password"));
             return "index";
         }
     }
    
    
    
    
    public List<Employee> getEmployeeRecord(){
        return DatabaseOperation.getEmployeeListFromDB(email);
    }
    
    public String updateEmployeeDetails(Employee updateEmployeeObj){
        return DatabaseOperation.updateEmployeeDetailsInDB(updateEmployeeObj);
    }
    public String updatePassword(){
        return DatabaseOperation.updateEmployeePassword(pwd, email);
    }
    public String logout(){
         HttpSession session = SessionUtils.getSession();
         session.invalidate();
         return "index";
     }
}   
    
    
    
