/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import waystonepropertymanagement.employee.login.DataConnect;
import waystonepropertymanagement.employee.login.Employee;
import waystonepropertymanagement.employee.login.EmployeeLoginDAO;
import waystonepropertymanagement.employee.login.SessionUtils;
import waystonepropertymanagement.employee.login.SessionUtils;



/**
 *
 * @author Katie
 */
@ManagedBean
@SessionScoped
public class Login implements Serializable{

    private static final long serialVersionUID = 1094801825228386363L;
    
    private String pwd;
    private String msg;
    private String user;

       
    
    
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public String getUser() {
        return user;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
     
    //validate login
     public String validateUsernamePassword() {
         boolean valid = EmployeeLoginDAO.empValidate(user, pwd);
         if(valid) {
             HttpSession session = SessionUtils.getSession();
             session.setAttribute("username", user);
             return "employeeAdmin";
         } else {
             FacesContext.getCurrentInstance().addMessage(null,
                     new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username and Password",
                     "Please enter correct Username and Password"));
             return "index";
         }
     }
     
          
     //logout even, invalidate session
     public String logout(){
         HttpSession session = SessionUtils.getSession();
         session.invalidate();
         return "index";
     }
     
    
     public List<Employee> getEmployeeList(){
        List<Employee> list = new ArrayList<Employee>();
        Connection con = null;
        PreparedStatement prepst;        
               
        try {
            
            con = DataConnect.getConnection();
            prepst = con.prepareStatement("SELECT * FROM employee_accounts WHERE username = ?");
            prepst.setString(1, user);
            
            ResultSet rset = prepst.executeQuery();
            while(rset.next()) {
                Employee emp = new Employee();
                emp.setUsername(rset.getString("username"));
                emp.setEmployeeID(rset.getInt("employee_id"));
                emp.setFirstName(rset.getString("firstName"));
                emp.setLastName(rset.getString("lastName"));
                emp.setMiddleInit(rset.getString("middleInt"));
                emp.setAddress(rset.getString("address"));
                emp.setCity(rset.getString("city"));
                emp.setState(rset.getString("state"));
                emp.setZipcode(rset.getString("zipcode"));
                emp.setPhone(rset.getString("phone"));
                emp.setDOB(rset.getString("dob"));
                list.add(emp);
            }              
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(con);
        }
        return list;
     }
          

}
