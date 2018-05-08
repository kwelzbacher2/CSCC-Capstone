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
import javax.faces.bean.SessionScoped;


/**
* Tenant is a Managed Bean Class that includes all of the properties and methods for a tenant
* @author KWelzbacher
*/
@ManagedBean (name="tenant")
@SessionScoped
public class Tenant implements Serializable{
    private String searchCrit;
    private String searchInfo;
    private String mi;
    private String email;
    private int tenantID;
    private String firstName;
    private String lastName;
    private String building;
    private String aptNum;
    
    private String address;
    private String city;
    private String state;
    private String zipcode;
    
    private String phone;
    private String dob;
    private String rentPaid;
    private long amountDue;
    private String permAddress;
    private String permCity;
    private String permZip;
    private String permState;
    private String payCrit;
    private String payInfo;
    private List<Tenant> tenantList = new ArrayList();
    private List<Tenant> payTenantList = new ArrayList();
   
    DecimalFormat df = new DecimalFormat("#0.00");
    
    public String getSearchCrit() {
        return searchCrit;
    }
    
    public void setSearchCrit(String searchCrit) {
        this.searchCrit = searchCrit;
    }
    public String getSearchInfo() {
        return searchInfo;
    }
    
    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }
    
    public String getMi(){
        return mi;
    }
    public void setMi(String mi){
        this.mi = mi;
    }
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public int getTenantID(){
        return tenantID;
    }
    
    public void setTenantID(int tenantID){
        this.tenantID = tenantID;
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
      
    public String getBuilding(){
        return building;
    }
    public void setBuilding(String building){
        this.building = building;
    }
    
    public String getAptNum(){
        return aptNum;
    }
    public void setAptNum(String aptNum){
        this.aptNum = aptNum;
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
    public String getRentPaid(){
        return rentPaid;
    }
    
    public void setRentPaid(String rentPaid){
        this.rentPaid = rentPaid;
    }
    public long getAmountDue(){
        return amountDue;
    }
    
    public void setAmountDue(long amountDue){
        this.amountDue = amountDue;
    }
    
    
    public String getPayCrit(){
        return payCrit;
    }
    
    public void setPayCrit(String payCrit){
        this.payCrit = payCrit;
    }
    
    public String getPayInfo(){
        return payInfo;
    }
    
    public void setPayInfo(String payInfo){
        this.payInfo = payInfo;
    }
    
    
    public String getPermAddress(){
        return permAddress;
    }
    public void setPermAddress(String permAddress){
        this.permAddress = permAddress;
    }
    
    public String getPermCity(){
        return permCity;
    }
    public void setPermCity(String permCity){
        this.permCity = permCity;
    }
    public String getPermZip(){
        return permZip;
    }
    public void setPermZip(String permZip){
        this.permZip = permZip;
    }
    public String getPermState(){
        return permState;
    }
    public void setPermState(String permState){
        this.permState = permState;
    }
   
   public List getTenantList(){
       return tenantList;
   }
   public void setTenantList(List tenantList){
       this.tenantList = tenantList;
   }
   
   public List getPayTenantList(){
       return payTenantList;
   }
   public void setPayTenantList(List payTenantList){
       this.payTenantList = payTenantList;
   }
   
   //obtain Tenant Records based on search Criteria
    public void getTenantRecord(String searchCrit, String searchInfo) {
       
    	if(searchCrit.equals("Building")){
            tenantList = DatabaseOperation.getTenantListBuilding(searchInfo);
        } else {
        	tenantList =  DatabaseOperation.getTenantListFromDB(searchCrit, searchInfo);
        }
    	
    	if (tenantList.size() == 0){
    		clearList();
    		Tenant ten = new Tenant();
    		ten.setEmail("No results found");
    		tenantList.add(ten);
    	}
    	
    } 
   //Obtain Tenant information
    public String viewTenantRecord(int tenantID, String tenantEmail){
        if(tenantEmail.equals("No results found")){
        	return "tenantAccounts.xhtml?faces-redirect=true";
        } else {
        	return DatabaseOperation.viewTenantRecordInDB(tenantID);
        }
    }
    //Update tenant in database
    public String updateTenantDetails(Tenant updateTenObj){
        return DatabaseOperation.updateTenantDetailsInDB(updateTenObj);
    }
    //Insert new tenant in database
    public String insertNewTenant(Tenant newTenantObj){
        return DatabaseOperation.insertNewTenantInDB(newTenantObj);
    }
    //Delete tenant in database
    public String deleteTenant(Tenant delTenantObj){
    	int delTenantID = delTenantObj.getTenantID();
        return DatabaseOperation.deleteTenantInDB(delTenantID);
    }
    //Obtain tenant record balance
    public String getTenantRecordBalance(int tenantID){
    	double balance =  DatabaseOperation.getTenantRecordBalanceInDB(tenantID);
    	return df.format(balance);
    }
    public void clearList(){
    	setFirstName(null);
    	setMi(null);
    	setLastName(null);
    	setPermAddress(null);
    	setPermCity(null);
    	setPermState("AL");
    	setPermZip(null);
    	setPhone(null);
    	setEmail(null);
    	setDOB(null);
    }
}
