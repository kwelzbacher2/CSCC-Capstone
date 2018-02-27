/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waystonepropertymanagement.employee.login;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Katie
 */
public class Maintenance {
    private int requestID;
    private int tenantID;
    private String building;
    private String aptNum;
    private String jobType;
    private String jobDesc;
    private String dateReq;

    public Maintenance (){
        
    }

    Maintenance(int requestID, int tenantID, String building, String aptNum, String jobDesc, String jobType, String dateReq) {
        super();
        this.requestID = requestID; 
        this.tenantID = tenantID;
        this.building = building;
        this.aptNum = aptNum;
        this.jobDesc = jobDesc;
        this.jobType = jobType;
        this.dateReq = dateReq;
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
    
    private List<Maintenance> tableFill = new ArrayList<Maintenance>();
    
    public List<Maintenance> getTableFill() {
        return tableFill;
    }
    public void setTableFill(List<Maintenance> tableFill){
        this.tableFill = tableFill;
    }
    public List<Maintenance> getMaintenanceRequests() {
        tableFill = DatabaseOperation.getMaintenanceFromDB();
        return tableFill;
    }
    
}
