/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package loginpage.projects.home;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Katie
 */
@ManagedBean
@SessionScoped
public class NewUser {
    private String newUser;
    private String newPwd;
    
    public String getNewUser(){
        return newUser;
    }
    
    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }
    
    public String getNewPwd(){
        return newPwd;
    }
    
    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
    
    public void validateUserInfo(){
        
    }
}
