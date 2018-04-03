package waystonepropertymanagement.employee.login;

import java.io.Serializable;
import java.text.DecimalFormat;
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
public class Record implements Serializable{
    private int recordID;
    private String recordName;
    private double recordAmount;
    
    private String recordIsCredit;
    private String recordDate;
    private String recordInvNum;  //change to TransactionID
    private String recordTenantID;
    private String recordAccount;
    private String searchRecCrit;
    private String searchRecInfo;
   // private Double recordBalance;
    private boolean recCred;
    private double recordDebitAmount;
    private double recordCreditAmount;
    
    private List<Record> recordList = new ArrayList();
    
    DecimalFormat df = new DecimalFormat("0.00");
    
    @ManagedProperty(value="#{account.accountName}")
    private String accountName;
    public String getAccountName(){
        return accountName;
    }
    public void setAccountName (String accountName){
        this.accountName = accountName;
    }
    
    @ManagedProperty(value="#{tenant.tenantID}")
    private int tenantID;
    public int getTenantID(){
    	return tenantID;
    }
    public void setTenantID(int tenantID){
    	this.tenantID = tenantID;
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
    public double getRecordAmount(){
        return recordAmount;
    }
    public void setRecordAmount(double recordAmount){
        this.recordAmount = recordAmount;
    }
    public String formatRecAmount(){
    	return df.format(getRecordAmount());
    }
    public Number getEntryAmount(){
    	return recordAmount;
    }
    public void setEntryAmount(Number entryAmount){
    	recordAmount = entryAmount.doubleValue();
    }
    public String getRecordIsCredit(){
        return recordIsCredit;
    }
    public void setRecordIsCredit(String recordIsCredit){
    	this.recordIsCredit = recordIsCredit;
        if(recordIsCredit.equals("Debit") || recordIsCredit.equals("DEBIT")){
         	recCred = false;
         } else {
         	recCred = true;
         }
    }
    public boolean getRecCred(){
    	return	recCred;
    }
    public void setRecCred(boolean recCred){
    	 this.recCred = recCred;
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
   /* public Double getRecordBalancet(){
        return recordBalance;
    }
    public void setRecordBalance(Double recordBalance){
        this.recordBalance = recordBalance;
    }
    */
    
    
    public List<Record> getRecordList(){
        return recordList;
    }
    public void setRecordList(List<Record> recordList){
        this.recordList = recordList;
    }
    
    
    public double getRecordDebitAmount(){
        return recordDebitAmount;
    }
    public void setRecordDebitAmount(Double recordDebitAmount){
        this.recordDebitAmount = recordDebitAmount;
    }
    public String formatDebAmount(){
    	String eval;
    	double dD = getRecordDebitAmount();
    	if(dD == 0.0){
    		eval = null;
    	} else{
    		eval = df.format(dD);
    	}
    	return eval;
    }
    
    public double getRecordCreditAmount(){    	
        return recordCreditAmount;
    }
    public void setRecordCreditAmount(Double recordCreditAmount){
        this.recordCreditAmount = recordCreditAmount;
    }
    public String formatCredAmount(){
    	String eval;
    	double dC = getRecordCreditAmount();
    	if(dC == 0.0){
    		eval = null;
    	} else{
    		eval = df.format(dC);
    	}
    	return eval;
    	
    }
    
    public List<Record> getAllRecords(){
    	return DatabaseOperation.getAllRecordsFromDB();
    }
    
    public List<Record> getAllRecords(String accountName){
    	return DatabaseOperation.getAllRecordsFromDB(accountName);
    }
    
    public void getAllRecordList(String searchRecCrit, String searchRecInfo, String accountName){
        recordList = DatabaseOperation.getAllRecordListFromDB(searchRecCrit, searchRecInfo, accountName);
        
    }
    
    public String viewIndivRecord(int recordID){
        return DatabaseOperation.viewIndivRecordInDB(recordID);
    }
    
    public String updateRecordDetails(Record updateRecObj){
        return DatabaseOperation.updateRecordDetailsInDB(updateRecObj);
    }
    
    public String createNewRecord(Record recNewObj){
    	
        return DatabaseOperation.createNewRecordInDB(recNewObj);
    }
    
    public List<Record> getTenantAccRecords(int tenantID){
    	return DatabaseOperation.getTenantAccRecordsInDB(tenantID);
    }
    
    public String deleteRecord(Record deleteRecObj){
    	int recordIDDel = deleteRecObj.getRecordID();
    	System.out.println(recordIDDel);
    	return DatabaseOperation.deleteRecordInDB(recordIDDel);    	
    }
    
    public String postRentToAR(){
    	
    	return DatabaseOperation.postRentToARInDB();
    }
    
    public String postLateFeeToAR(){
    	
    	return DatabaseOperation.postLateFeeToARInDB();
    }
}
