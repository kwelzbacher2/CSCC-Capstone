package waystonepropertymanagement.employee.login;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Katie
 */
@ManagedBean (name="record")
@ViewScoped
public class Record {
    private int recordID;
    private String recordName;
    private String recordAmount;
    private String recordIsCredit;
    private String recordDate;
    private String recordInvNum;
    private String recordTenantID;
    private String recordAccount;
    private String searchRecCrit;
    private String searchRecInfo;
    
    private List<Record> recordList = new ArrayList();
    
    @ManagedProperty(value="#{account.accountName}")
    private String accountName;
    public String getAccountName(){
        return accountName;
    }
    public void setAccountName (String accountName){
        this.accountName = accountName;
    }
    
    
    public int getRecordID(){
        return recordID;
    }
    public void setRecordID(int recordID){
        this.recordID = recordID;
    }
    public String getRecordName(){
        return recordName;
    }
    public void setRecordName(String recordName){
        this.recordName = recordName;
    }
    public String getRecordAmount(){
        return recordAmount;
    }
    public void setRecordAmount(String recordAmount){
        this.recordAmount = recordAmount;
    }
    public String getRecordIsCredit(){
        return recordIsCredit;
    }
    public void setRecordIsCredit(String recordIsCredit){
        this.recordIsCredit = recordIsCredit;
    }
    public String getRecordDate(){
        return recordDate;
    }
    public void setRecordDate(String recordDate){
        this.recordDate = recordDate;
    }
    public String getRecordInvNum(){
        return recordInvNum;
    }
    public void setRecordInvNum(String recordInvNum){
        this.recordInvNum = recordInvNum;
    }
    public String getRecordTenantID(){
        return recordTenantID;
    }
    public void setRecordTenantID(String recordTenantID){
        this.recordTenantID= recordTenantID;
    }
    public String getRecordAccount(){
        return recordAccount;
    }
    public void setRecordAccount(String recordAccount){
        this.recordAccount = recordAccount;
    }
    public String getSearchRecCrit(){
        return searchRecCrit;
    }
    public void setSearchRecCrit(String searchRecCrit){
        this.searchRecCrit = searchRecCrit;
    }
    public String getSearchRecInfo(){
        return searchRecInfo;
    }
    public void setSearchRecInfo(String searchRecInfo){
        this.searchRecInfo = searchRecInfo;
    }
    
    public List getRecordList(){
        return recordList;
    }
    public void setRecordList(List recordList){
        this.recordList = recordList;
    }
    
    
    public void getAllRecordList(String searchRecCrit, String searchRecInfo, String accountName){
        recordList = DatabaseOperation.getAllRecordListFromDB(searchRecCrit, searchRecInfo, accountName);
        System.out.println(accountName);
    }
    
    public String viewIndivRecord(int recordID){
        return DatabaseOperation.viewIndivRecordInDB(recordID);
    }
    
    public String updateRecordDetails(Record updateRecObj){
        return DatabaseOperation.updateRecordDetailsInDB(updateRecObj);
    }
}
