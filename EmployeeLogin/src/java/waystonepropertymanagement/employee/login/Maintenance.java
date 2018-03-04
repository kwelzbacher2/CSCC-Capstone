/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waystonepropertymanagement.employee.login;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Katie
 */
@ManagedBean (name="maintenance")
@ViewScoped
public class Maintenance {
    private int requestID;
    private int tenantID;
    private String building;
    private String aptNum;
    private String jobType;
    private String jobDesc;
    private String dateReq;
    
    
    

  
    
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
    
    private List<Maintenance> maintenanceSearch = new ArrayList();
    private String searchBuild;
    private String searchType;
    
    public List getMaintenanceSearch(){
        return maintenanceSearch;
    }
    public void setMaintenanceSearch(List maintenanceSearch){
        this.maintenanceSearch = maintenanceSearch;
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
    
    //originally to obtain all maintenance requests
    public List<Maintenance> getMaintenanceRecord(){
        return DatabaseOperation.getMaintenanceListFromDB();
    }
    
    public void getMaintenanceSearchList(String searchBuild, String searchType){
        
            maintenanceSearch = DatabaseOperation.getMaintenanceSearchListFromDB(searchBuild, searchType);
                
    }
    
    
}
