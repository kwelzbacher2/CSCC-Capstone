/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginpage.projects.home;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import loginpage.projects.home.LoginDAO;
import loginpage.projects.home.SessionUtils;
import loginpage.projects.home.EmployeeLoginDAO;



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
    private String empUser;
    private String empPwd;
    
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
    
    public String getEmpUser(){
        return empUser;
    }
     public void setEmpUser(String empUser){
         this.empUser = empUser;
     }
     public String getEmpPwd(){
         return empPwd;
     }
     public void setEmpPwd(String empPwd){
         this.empPwd = empPwd;
     }
    
    //validate login
     public String validateUsernamePassword() {
         boolean valid = LoginDAO.validate(user, pwd);
         if(valid) {
             HttpSession session = SessionUtils.getSession();
             session.setAttribute("username", user);
             return "admin";
         } else {
             FacesContext.getCurrentInstance().addMessage(null,
                     new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username and Password",
                     "Please enter correct Username and Password"));
             return "index";
         }
     }
     
     public String empValidateUsernamePassword() {
         boolean valid = EmployeeLoginDAO.empValidate(empUser, empPwd);
         if(valid) {
             HttpSession session = SessionUtils.getSession();
             session.setAttribute("username", empUser);
             return "empAdmin";
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
}
