/*
 * CSCI Capstone 2999 Final Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
/**
* Timesheet is a Managed Bean Class that includes all of the properties and methods for a timesheet
* @author KWelzbacher
*/
@ManagedBean (name="timesheet")
@ViewScoped
public class Timesheet implements Serializable{
	private int timesheetID;
	private String inTime;
    private String outTime;
    private String clockDate;
    private String sunIn;
    private String sunOut;
    private String monIn;
    private String monOut;
    private String tuesIn;
    private String tuesOut;
    private String wedIn;
    private String wedOut;
    private String thurIn;
    private String thurOut;
    private String friIn;
    private String friOut;
    private String satIn;
    private String satOut;
    private String monDate;
    private String tueDate;
    private String wedDate;
    private String thurDate;
    private String friDate;
    private String satDate;
    private String sunDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    
	
    
    public int getTimesheetID(){
    	return timesheetID;
    }
    public void setTimesheetID(int timesheetID){
    	this.timesheetID= timesheetID;
    }
	public String getInTime(){
    	return inTime;
    }
    public void setInTime(String inTime){
    	this.inTime = inTime;
    }
    public String getOutTime(){
    	return outTime;
    }
    public void setOutTime(String outTime){
    	this.outTime = outTime;
    }
    
    public String getClockDate(){
    	return clockDate;
    }
    public void setClockDate(String clockDate){
    	this.clockDate = clockDate;
    }
    public String getSunIn(){
    	return sunIn;
    }
    public void setSunIn(String sunIn){
    	this.sunIn= sunIn;
    }
    public String getSunOut(){
    	return sunOut;
    }
    public void setSunOut(String sunOut){
    	this.sunOut= sunOut;
    }
    public String getMonIn(){
    	return monIn;
    }
    public void setMonIn(String monIn){
    	this.monIn= monIn;
    }
    public String getMonOut(){
    	return monOut;
    }
    public void setMonOut(String monOut){
    	this.monOut= monOut;
    }
    public String getTuesIn(){
    	return tuesIn;
    }
    public void setTuesIn(String tuesIn){
    	this.tuesIn= tuesIn;
    }
    public String getTuesOut(){
    	return tuesOut;
    }
    public void setTuesOut(String tuesOut){
    	this.tuesOut= tuesOut;
    }
    public String getWedIn(){
    	return wedIn;
    }
    public void setWedIn(String wedIn){
    	
    	this.wedIn= wedIn;
    }
    public String getWedOut(){
    	return wedOut;
    }
    public void setWedOut(String wedOut){
    	this.wedOut= wedOut;
    }
    public String getThurIn(){
    	return thurIn;
    }
    public void setThurIn(String thurIn){
    	this.thurIn= thurIn;
    }
    public String getThurOut(){
    	return thurOut;
    }
    public void setThurOut(String thurOut){
    	this.thurOut= thurOut;
    }
    public String getFriIn(){
    	return friIn;
    }
    public void setFriIn(String friIn){
    	this.friIn= friIn;
    }
    public String getFriOut(){
    	return friOut;
    }
    public void setFriOut(String friOut){
    	this.friOut= friOut;
    }
    public String getSatIn(){
    	return satIn;
    }
    public void setSatIn(String satIn){
    	this.satIn= satIn;
    }
    public String getSatOut(){
    	return satOut;
    }
    public void setSatOut(String satOut){
    	this.satOut= satOut;
    }
    public String getMonDate(){
    	return monDate;
    }
    public void setMonDate(String monDate){
    	this.monDate= monDate;
    }
    public String getTueDate(){
    	return tueDate;
    }
    public void setTueDate(String tueDate){
    	this.tueDate= tueDate;
    }
    public String getWedDate(){
    	return wedDate;
    }
    public void setWedDate(String wedDate){
    	this.wedDate= wedDate;
    }
    public String getThurDate(){
    	return thurDate;
    }
    public void setThurDate(String thurDate){
    	this.thurDate= thurDate;
    }
    public String getFriDate(){
    	return friDate;
    }
    public void setFriDate(String friDate){
    	this.friDate= friDate;
    }
    public String getSatDate(){
    	return satDate;
    }
    public void setSatDate(String satDate){
    	this.satDate= satDate;
    }
    public String getSunDate(){
    	return sunDate;
    }
    public void setSunDate(String sunDate){
    	this.sunDate= sunDate;
    }
   
   
    
   
    //Method to set time and employee ID to clock-in in database
    public String employeeClockIn(int empID){
    	return DatabaseOperation.employeeClockInToDB(empID);
    }
    //Method to set time and employee ID to clock-out in database
    public String employeeClockOut(int empID){
    	return DatabaseOperation.employeeClockOutToDB(empID);
    }
    //Obtain all of employee times for the week
    public List<Timesheet> getEmployeeTimesheet(int empID){
    	return DatabaseOperation.getEmployeeTimesheetInDB(empID);
    }
    //Obtain dates for the current week
     public List<Timesheet> getTimesheetDates(){
    	 return DatabaseOperation.getTimesheetDatesInDB();
     }
}
