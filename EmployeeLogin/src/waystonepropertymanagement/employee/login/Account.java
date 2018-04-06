/*
 * CSCI Capstone 2999 Final Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 * Account class is Managed Bean that contains all of the properties and methods of an account
 * @author KWelzbacher
 * 
 */
@ManagedBean (name="account")
@SessionScoped
public class Account implements Serializable {
    
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
    
    //Method to obtain all accounts based on search criteria given
    public void getAllAccountList(String accSearchName, String accSearchType){
        accountList = DatabaseOperation.getAccountListFromDB(accSearchName, accSearchType);
        if(accountList.size() == 0){
        	Account acc = new Account();
        	acc.setAccountName("No results found");
        	accountList.add(acc);
        }
    }
    //Method to obtain the information of the individual account chosen
    public String viewAccountInfo(String accountName){
        return DatabaseOperation.viewAccountInfoInDB(accountName);
    }
    //Method that places all account names in a list to be used for selectOneMenu
    public List<String> viewAllAccountNames(){
        return DatabaseOperation.viewAllAccountNamesInDB();
    }
    //Method to update Account information to database
    public String updateAccountDetails(Account updateAccountObj){
        return DatabaseOperation.updateAccountDetailsInDB(updateAccountObj);
    }
    //Method to insert new Account into database
    public String createNewAccount(Account newAccountObj){
        return DatabaseOperation.createNewAccountInDB(newAccountObj);
    }
    //Method to delete account from database
    public String deleteAccount(Account deleteAccountObj){
    	String deleteAccName = deleteAccountObj.getAccountName();
        return DatabaseOperation.deleteAccountInDB(deleteAccName);
    }

}

