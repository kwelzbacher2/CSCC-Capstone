/*
 * CSCI Capstone 2999 Final Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
* Maintenance is a Managed Bean Class that includes all of the properties and methods for a maintenance
* @author KWelzbacher
*/
@ManagedBean (name="maintenance")
@ViewScoped
public class Maintenance implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private int requestID;
    private int tenantID;
    private String building;
    private String aptNum;
    private String jobType;
    private String jobDesc;
    private String dateReq;
    
    private String startDate;
    private int startEmp;
    private String doneDate;
    private int doneEmp;
    
    
    @ManagedProperty(value="#{employee.employeeID}")
    private int employeeID;
    public int getEmployeeID(){
        return employeeID;
    }
    public void setEmployeeID (int employeeID){
        this.employeeID = employeeID;
    }
  
    
    public int getRequestID(){
        return requestID;
    }
    public void setRequestID(int requestID){
        this.requestID = requestID;
    }
    public int getTenantID(){
        return tenantID;
    }
    public void setTenantID(int tenantID){
        this.tenantID = tenantID;
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
    public String getJobType(){
        return jobType;
    }
    public void setJobType(String jobType){
        this.jobType= jobType;
    }
    public String getJobDesc(){
        return jobDesc;
    }
    public void setJobDesc(String jobDesc){
        this.jobDesc = jobDesc;
    }
    public String getDateReq(){
        return dateReq;
    }
    public void setDateReq(String dateReq){
        this.dateReq = dateReq;
    }
    public String getStartDate(){
        return startDate;
    }
    public void setStartDate(String startDate){
        this.startDate = startDate;
    }
    public int getStartEmp(){
        return startEmp;
    }
    public void setStartEmp(int startEmp){
        this.startEmp = startEmp;
    }
    public String getDoneDate(){
        return doneDate;
    }
    public void setDoneDate(String doneDate){
        this.doneDate = doneDate;
    }
    public int getDoneEmp(){
        return doneEmp;
    }
    public void setDoneEmp(int doneEmp){
        this.doneEmp = doneEmp;
    }
    
    private List<Maintenance> maintenanceSearch = new ArrayList();
    private String searchBuild;
    private String searchType;
    private List<Maintenance> currentMainList = new ArrayList();
    private List<Maintenance> doneMainList = new ArrayList();
    
    
    
    public List<Maintenance> getMaintenanceSearch(){
        return maintenanceSearch;
    }
    public void setMaintenanceSearch(List<Maintenance> maintenanceSearch){
        this.maintenanceSearch = maintenanceSearch;
    }
    public List<Maintenance> getCurrentMainList(){
        return currentMainList;
    }
    public void setCurrentMainList(List<Maintenance> currentMainList){
        this.currentMainList = currentMainList;
    }
    public List<Maintenance> getDoneMainList(){
        return doneMainList;
    }
    public void setDoneMainList(List<Maintenance> doneMainList){
        this.doneMainList = doneMainList;
    }
    
    public String getSearchBuild(){
        return searchBuild;
    }
    public void setSearchBuild(String searchBuild){
        this.searchBuild = searchBuild;
    }
    public String getSearchType(){
        return searchType;
    }
    public void setSearchType(String searchType){
        this.searchType = searchType;
    }
    
    //Obtain list of maintenance requests based on search criteria
    public void getMaintenanceSearchList(String searchBuild, String searchType){
            maintenanceSearch = DatabaseOperation.getMaintenanceSearchListFromDB(searchBuild, searchType); 
    }
    //Add Employee ID and Date Started to the database
    public String startMainRequest(Maintenance request, int employeeID){
    	int reqstID = request.getRequestID();
    	return DatabaseOperation.startMainRequestInDB(reqstID, employeeID);
    }
    //Obtain list of maintenance requests employee is currently working on
    public List<Maintenance> getCurrentEmpMainList(int employeeID){
    	System.out.println(employeeID);
    	currentMainList = DatabaseOperation.getCurrentEmpMainListInDB(employeeID);
    	return currentMainList;
    }
     //Add EmployeeID and Date Ended to the databased and remove it from the list of requests shown   
    public String endMainRequest(Maintenance request, int employeeID){
    	int reqID = request.getRequestID(); 
    	return DatabaseOperation.endMainRequestInDB(reqID, employeeID);
    }
    //Obtain list of maintenance requests completed by employee
    public List<Maintenance> getDoneEmpMainList(int employeeID){
    	System.out.println(employeeID);
    	doneMainList = DatabaseOperation.getDoneEmpMainListInDB(employeeID);
    	return doneMainList;
    }
    //Remove EmployeeID and Date Started from request in database
    public String removeMainRequest(Maintenance request, int employeeID){
    	int reqRemoveID = request.getRequestID(); 
    	return DatabaseOperation.removeMainRequestInDB(reqRemoveID, employeeID);
    }
    //Insert new request in database
    public String createNewRequest(Maintenance newMaintObj){
    	return DatabaseOperation.createNewRequestInDB(newMaintObj);
    }
}
