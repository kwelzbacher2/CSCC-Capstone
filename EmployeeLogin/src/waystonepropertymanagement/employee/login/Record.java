/*
 * CSCI Capstone 2999 Final Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
* Record is a Managed Bean Class that includes all of the properties and methods for a record
* @author KWelzbacher
*/
@ManagedBean (name="record")
@ViewScoped
public class Record implements Serializable{
    private int recordID;
    private String recordName;
    private double recordAmount;
    
    private String recordIsCredit;
    private String recordDate;
    private String recordInvNum;  
    private String recordTenantID;
    private String recordAccount;
    private String searchRecCrit;
    private String searchRecInfo;
    private boolean recCred;
    private double recordDebitAmount;
    private double recordCreditAmount;
    private String recordReason; 
    
    public String getRecordReason() {
		return recordReason;
	}
	public void setRecordReason(String recordReason) {
		this.recordReason = recordReason;
	}

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
    //Format Record Amount to 2 decimal places
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
    //Format Debit Amount to 2 deciaml places
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
    //format Credit Amount to 2 decimal places
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
    //Obtain all records from database
    public static List<Record> getAllRecords(){
    	return DatabaseOperation.getAllRecordsFromDB();
    }
    //Obtain all records for account from database
    public List<Record> getAllRecords(String accountName){
    	
    	return DatabaseOperation.getAllRecordsFromDB(accountName);
    }
    //Obtain all records based on search criteria
    public void getAllRecordList(String searchRecCrit, String searchRecInfo, String accountName){
        recordList = DatabaseOperation.getAllRecordListFromDB(searchRecCrit, searchRecInfo, accountName);
        if(recordList.size() ==0){
        	Record rec = new Record();
        	rec.setRecordName("No results found");
        	recordList.add(rec);
        }
    }
    //Obtain chosen record information
    public String viewIndivRecord(int recordID){
        return DatabaseOperation.viewIndivRecordInDB(recordID);
    }
    //Update record information in database
    public String updateRecordDetails(Record updateRecObj, int alterEmp){
        return DatabaseOperation.updateRecordDetailsInDB(updateRecObj, alterEmp);
    }
    //Insert new record into database
    public String createNewRecord(Record recNewObj){
        return DatabaseOperation.createNewRecordInDB(recNewObj);
    }
    //Obtain all records for chosen tenant
    public List<Record> getTenantAccRecords(int tenantID){
    	return DatabaseOperation.getTenantAccRecordsInDB(tenantID);
    }
    //Delete record in the database
    public String deleteRecord(Record deleteRecObj, int deleteEmp){
    	int recordIDDel = deleteRecObj.getRecordID();
    	return DatabaseOperation.deleteRecordInDB(recordIDDel, deleteEmp);    	
    }
    //Method to post all rent charges to respective accounts
    public String postRentToAR(){
    	return DatabaseOperation.postRentToARInDB();
    }
    //Method to post all late fee charges to respective accounts
    public String postLateFeeToAR(){
    	return DatabaseOperation.postLateFeeToARInDB();
    }
    public void clearList(){
    	setRecordName(null);
    	setEntryAmount(0);
    	setRecordIsCredit("Credit");
    	setRecordDate(null);
    	setRecordInvNum(null);
    	setRecordTenantID(null);
    	setRecordAccount("");
    }
 
}
