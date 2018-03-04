/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waystonepropertymanagement.employee.login;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Katie
 */
@ManagedBean (name="tenant")
@SessionScoped
public class Tenant {
    private String searchCrit;
    private String searchInfo;
    private String mi;
    private String email;
    private int tenantID;
    private String firstName;
    private String lastName;
    private String address;
    private String building;
    private String aptNum;
    private String city;
    private String state;
    private String zipcode;
    private String phone;
    private String dob;
    private String rentPaid;
    private long amountDue;
    
    
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
    
    public String getMI(){
        return mi;
    }
    public void setMI(String mi){
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
   
    public String getAddress(){
        return address;
    }
    
    public void setAddress(String address){
        this.address = address;
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
    
    private String payCrit;
    private String payInfo;
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
    
    
   private List<Tenant> tenantList = new ArrayList();
   private List<Tenant> payTenantList = new ArrayList();
   
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
    } 
    
    public void getTenantPayments(String payCrit, String payInfo){
        
        payTenantList = DatabaseOperation.getPaymentListFromDB(payCrit, payInfo);
        
    }
    
    public String viewTenantRecord(int tenantID){
        return DatabaseOperation.viewTenantRecordInDB(tenantID);
    }
}
