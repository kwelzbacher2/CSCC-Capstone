/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waystonepropertymanagement.employee.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Katie
 * 
 * 
 */
@ManagedBean (name="account")
@SessionScoped
public class Account {
    
    private String accountName;
    private String accountType;
    private String accSearchName;
    private String accSearchType;
       
    private List<Account> accountList = new ArrayList();
    private List<String> allAccountNames = new ArrayList();
    
       
    
    public String getAccountName(){
        return accountName;
    }
    public void setAccountName(String accountName){
        this.accountName = accountName;
    }
    public String getAccountType (){
        return accountType;
    }
    public void setAccountType(String accountType){
        this.accountType = accountType;
    }
    public String getAccSearchName (){
        return accSearchName;
    }
    public void setAccSearchName(String accSearchName){
        this.accSearchName= accSearchName;
    }
    public String getAccSearchType (){
        return accSearchType;
    }
    public void setAccSearchType(String accSearchType){
        this.accSearchType = accSearchType;
    }
    
    public List getAccountList (){
        return accountList;
    }
    public void setAccountList(List accountList){
        this.accountList = accountList;
    }
    
    public List getAllAccountNames (){
        return allAccountNames;
    }
    public void setAllAccountNames(List allAccountNames){
        this.allAccountNames = allAccountNames;
    }
    
    
    public void getAllAccountList(String accSearchName, String accSearchType){
        accountList = DatabaseOperation.getAccountListFromDB(accSearchName, accSearchType);
    }
    public String viewAccountInfo(String accountName){
        return DatabaseOperation.viewAccountInfoInDB(accountName);
    }
    
    public List<String> viewAllAccountNames(){
        return DatabaseOperation.viewAllAccountNamesInDB();
    }
    
   
    public String updateAccountDetails(Account updateAccountObj){
        return DatabaseOperation.updateAccountDetailsInDB(updateAccountObj);
    }
    
    
    public String createNewAccount(Account newAccountObj){
        return DatabaseOperation.createNewAccountInDB(newAccountObj);
    }

}

