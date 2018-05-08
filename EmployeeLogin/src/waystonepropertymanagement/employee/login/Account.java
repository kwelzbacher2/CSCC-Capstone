/*
 * CSCI Capstone 2999 Final Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 * Account class is Managed Bean that contains all of the properties and methods of an account
 * @author KWelzbacher
 * 
 */
@ManagedBean (name="account")
@SessionScoped
public class Account implements Serializable {
    private int accountID;
    private String accountName;
    private String accountType;
    private String accSearchName;
    private String accSearchType;
    private static double accountBalance;
    private String accBalance;
    private static String debitBalance;
    private static String creditBalance;
    private String startDate;
    private String endDate;
    private List<Account> assetsList = new ArrayList();
    private List<Account> liabsList = new ArrayList();
    private String assetBalance = null;
    private String liabsBalance = null;
    private String incBalance = null;
    private String expenseBalance = null;
    private List<Account> incomeList = new ArrayList();
    private List<Account> expenseList = new ArrayList();
    private List<Account> accountList = new ArrayList();
    private List<String> allAccountNames = new ArrayList();
    private String equity = null;
    private String profitLoss = null;
    static DecimalFormat df = new DecimalFormat("0.00");
    private String nameHolder;
    private String typeHolder;
    private String assetName;   
    private List<Account> operatingList = new ArrayList();
    private List<Account> investList = new ArrayList();
    private List<Account> finList = new ArrayList();
    
    
    public List<Account> getOperatingList() {
		return operatingList;
	}
	public void setOperatingList(List<Account> operatingList) {
		this.operatingList = operatingList;
	}
	public List<Account> getInvestList() {
		return investList;
	}
	public void setInvestList(List<Account> investList) {
		this.investList = investList;
	}
	public List<Account> getFinList() {
		return finList;
	}
	public void setFinList(List<Account> finList) {
		this.finList = finList;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
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
    
    public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getAccBalance() {
		return accBalance;
	}
	public void setAccBalance(String accBalance) {
		this.accBalance = accBalance;
	}
	public String getDebitBalance() {
		return debitBalance;
	}
	public void setDebitBalance(String debitBalance) {
		this.debitBalance = debitBalance;
	}
	public String getCreditBalance() {
		return creditBalance;
	}
	public void setCreditBalance(String creditBalance) {
		this.creditBalance = creditBalance;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<Account> getAssetsList() {
		return assetsList;
	}
	public void setAssetsList(List<Account> assetsList) {
		this.assetsList = assetsList;
	}
	public List<Account> getLiabsList() {
		return liabsList;
	}
	public void setLiabsList(List<Account> liabsList) {
		this.liabsList = liabsList;
	}
	public String getAssetBalance() {
		return assetBalance;
	}
	public void setAssetBalance(String assetBalance) {
		this.assetBalance = assetBalance;
	}
	public String getLiabsBalance() {
		return liabsBalance;
	}
	public void setLiabsBalance(String liabsBalance) {
		this.liabsBalance = liabsBalance;
	}
	public String getIncBalance() {
		return incBalance;
	}
	public void setIncBalance(String incBalance) {
		this.incBalance = incBalance;
	}
	public String getExpenseBalance() {
		return expenseBalance;
	}
	public void setExpenseBalance(String expenseBalance) {
		this.expenseBalance = expenseBalance;
	}
	public List<Account> getIncomeList() {
		return incomeList;
	}
	public void setIncomeList(List<Account> incomeList) {
		this.incomeList = incomeList;
	}
	public List<Account> getExpenseList() {
		return expenseList;
	}
	public void setExpenseList(List<Account> expenseList) {
		this.expenseList = expenseList;
	}
	public List<Account> getAccountList (){
        return accountList;
    }
    public void setAccountList(List<Account> accountList){
        this.accountList = accountList;
    }
    
    public String getNameHolder() {
		return nameHolder;
	}
	public void setNameHolder(String nameHolder) {
		this.nameHolder = nameHolder;
	}
	public String getTypeHolder() {
		return typeHolder;
	}
	public void setTypeHolder(String typeHolder) {
		this.typeHolder = typeHolder;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public List<String> getAllAccountNames (){
        return allAccountNames;
    }
    public void setAllAccountNames(List<String> allAccountNames){
        this.allAccountNames = allAccountNames;
    }
    
    public String getEquity() {
		return equity;
	}
	public void setEquity(String equity) {
		this.equity = equity;
	}
	 public String getProfitLoss() {
			return profitLoss;
	}
	public void setProfitLoss(String profitLoss) {
		this.profitLoss = profitLoss;
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
    public List<Account> getAllAccountList(String accType){
    	return DatabaseOperation.getAccountListFromDB("", accType);
    }
    //Method to obtain the information of the individual account chosen
    public String viewAccountInfo(String accountName){
    	if(accountName.equals("No results found")){
    		return "accounts.xhtml?faces-redirect=true";
    	} else {
    		return DatabaseOperation.viewAccountInfoInDB(accountName);
    	}
    }
    //Method that places all account names in a list to be used for selectOneMenu
    public List<String> viewAllAccountNames(){
        return DatabaseOperation.viewAllAccountNamesInDB();
    }
    //Method to update Account information to database
    public String updateAccountDetails(Account updateAccountObj, String placeholder){
        return DatabaseOperation.updateAccountDetailsInDB(updateAccountObj, placeholder);
    }
    //Method to insert new Account into database
    public String createNewAccount(Account newAccountObj){
        return DatabaseOperation.createNewAccountInDB(newAccountObj);
    }
    //Method to delete account from database
    public String deleteAccount(Account deleteAccountObj, int deleteEmp){
    	String deleteAccName = deleteAccountObj.getAccountName();
        return DatabaseOperation.deleteAccountInDB(deleteAccName, deleteEmp);
    }
    
    public String getCurrentCreditBalance(String currentAccount){
    	accountBalance = DatabaseOperation.getCurrentAccountBalance(currentAccount);
    	System.out.println(accountBalance);
    	if(accountBalance >= 0.0){
    		creditBalance = null;
    	} else {
    		creditBalance = df.format(accountBalance * -1);
    	}
    	return creditBalance;
    }
    public static String getCurrentCreditBalance(String currentAccount, String startDate, String endDate){
    	accountBalance = DatabaseOperation.getCurrentAccountBalance(currentAccount, startDate, endDate);
    	System.out.println(accountBalance);
    	if(accountBalance >= 0.0){
    		creditBalance = null;
    	} else {
    		creditBalance = df.format(accountBalance * -1);
    	}
    	return creditBalance;
    }
    public String getCurrentDebitBalance(String currentAccount){
    	accountBalance = DatabaseOperation.getCurrentAccountBalance(currentAccount);
    	if(accountBalance >= 0.0){
    		debitBalance = df.format(accountBalance);
    	} else {
    		debitBalance = null;
    	}
    	return debitBalance;
    }
    public static String getCurrentDebitBalance(String currentAccount, String startDate, String endDate){
    	accountBalance = DatabaseOperation.getCurrentAccountBalance(currentAccount, startDate, endDate);
    	if(accountBalance >= 0.0){
    		debitBalance = df.format(accountBalance);
    	} else {
    		debitBalance = null;
    	}
    	return debitBalance;
    }
    
    
   
    public String getLiabilityBalance(String accType, String startDa, String endDa){
    	List<String> libAcc = DatabaseOperation.getAccountsByType(accType);
    	double libBalance = 0.0;
    	double finalBl = 0.0;
    	for(int i = 0; i < libAcc.size(); i++){
    		libBalance = DatabaseOperation.getCurrentAccountBalance(libAcc.get(i), startDa, endDa);
    		finalBl += libBalance;
    	}
    	if(accType.equals("Income") || accType.equals("Liability")){
    		finalBl = finalBl * -1;
    	}
		return df.format(finalBl);
    }
    public void getSheetBalances(String startD, String endD){
    	assetsList = DatabaseOperation.getCurrentSheet("Asset", startD, endD);
    	assetBalance = getLiabilityBalance("Asset", startD, endD);
    	liabsList = DatabaseOperation.getCurrentSheet("Liability", startD, endD);
    	liabsBalance = getLiabilityBalance("Liability", startD, endD);
    	incomeList = DatabaseOperation.getCurrentSheet("Income", startD, endD);
    	incBalance = getLiabilityBalance("Income", startD, endD);
    	expenseList = DatabaseOperation.getCurrentSheet("Expense", startD, endD);
    	expenseBalance = getLiabilityBalance("Expense", startD, endD);
    
    	equity = df.format(Double.parseDouble(assetBalance) - Double.parseDouble(liabsBalance));
    	profitLoss = df.format(Double.parseDouble(incBalance) - Double.parseDouble(expenseBalance));
    	
    	operatingList.clear();
    	Account cash = new Account();
    	cash.setAccountName("Income");
    	cash.setAccBalance(incBalance);
    	operatingList.add(cash);
    	Account ex = new Account();
    	ex.setAccountName("Expenses");
    	ex.setAccBalance("(" + expenseBalance + ")");
    	operatingList.add(ex);
    	Account net = new Account();
    	net.setAccountName("Net Cash");
    	net.setAccBalance(profitLoss);
    	operatingList.add(net);
    	
    	investList.clear();
    	Account fA = new Account();
    	fA.setAccountName("Purchase of Assets");
    	String bal = df.format(DatabaseOperation.getCurrentAccountBalance("FIXED ASSETS", startD, endD));
    	fA.setAccBalance("(" + bal + ")");
    	investList.add(fA);
    	Account fNet = new Account();
    	fNet.setAccountName("Net Cash");
    	fNet.setAccBalance("(" + bal + ")");
    	investList.add(fNet);
    	
    	finList.clear();
    	Account fin = new Account();
    	fin.setAccountName("End of Period");
    	fin.setAccBalance(df.format(Double.parseDouble(profitLoss) - Double.parseDouble(bal)));
    	finList.add(fin);
    	
    }
    public List<Account> getOperatingActivities(){
    	return DatabaseOperation.getLastSheet("Income");
    }
    public void clearList(){
    	setAccountName(null);
    	setAccountType("");
    }
    
    
}

