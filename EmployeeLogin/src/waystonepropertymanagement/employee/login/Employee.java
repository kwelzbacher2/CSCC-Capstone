/*
 * CSCI Capstone 2999 Final Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Employee is a Managed Bean Class that includes all of the properties and methods for an employee
 * @author KWelzbacher
 */
@ManagedBean (name="employee")
@SessionScoped
public class Employee implements Serializable{
    private static final long serialVersionUID = 1094801825228386363L;
    
    private String pwd;
    private String msg;    
    private String email;
	private String role;
    private int employeeID;
    private String firstName;
    private String lastName;
    private String middleInit;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String phone;
    private String dob;
    private String accStatus;
    private String searchCriteria;
    private String searchInform;
    private String newEmail;
    private String searchRole;
    public List<Employee>employeeListFromDB;
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    public String getRole(){
        return role;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public int getEmployeeID(){
        return employeeID;
    }
    
    public void setEmployeeID(int employeeID){
        this.employeeID = employeeID;
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
    public String getMiddleInit(){
        return middleInit;
    }
    
    public void setMiddleInit(String middleInit){
        this.middleInit = middleInit;
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
    
    public String getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	public String getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public String getSearchInform() {
		return searchInform;
	}

	public void setSearchInform(String searchInform) {
		this.searchInform = searchInform;
	}
	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}
	public String getSearchRole() {
		return searchRole;
	}

	public void setSearchRole(String searchRole) {
		this.searchRole = searchRole;
	}
	public List<Employee> employeeList;
	

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	//initialize attempt counter and the maximum number of login attempts allowed
	public int totalAttempts;
    public int getTotalAttempts() {
		return totalAttempts;
	}

	public void setTotalAttempts(int totalAttempts) {
		this.totalAttempts = totalAttempts;
	}
	public static final int MAX_ATTEMPTS=3;
    //Method to count login attempts as well as compare email and password against the database
    public String validateUsernamePassword() {
    	
    	DatabaseOperation.addUserAttempt(email);
    	totalAttempts = DatabaseOperation.getUserAttempts(email);
    	System.out.println(totalAttempts);
    	if(totalAttempts < MAX_ATTEMPTS){
    		String active = DatabaseOperation.empAccStatus(email);
    		boolean valid = DatabaseOperation.empValidate(email, pwd, active);
    		if(valid && active.equals("ACTIVE")) {
    			HttpSession session = SessionUtils.getSession();
    			session.setAttribute("username", email);
    			DatabaseOperation.getEmployeeRole(email);
    			return "employeeAdmin";
    		} else if(valid && active.equals("RESET")) {
    			HttpSession session = SessionUtils.getSession();
    			session.setAttribute("username", email);
    			DatabaseOperation.getEmployeeRole(email);
    			return "resetPassword";
    		} else {
    			System.out.println(totalAttempts);
    			FacesContext.getCurrentInstance().addMessage("loginForm:password",
                     new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username and Password",
                     "Please enter correct Username and Password"));
    			totalAttempts ++;
    			return "index";
    		}
    	} else {
    		DatabaseOperation.lockEmpAccount(email);
    		FacesContext.getCurrentInstance().addMessage("loginForm:password",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Maximum number of attempts exceeded",
                    "Maximum number of attempts exceeded. Please call IT to unlock account."));
    		return "index";
    	}
     }
    //Method to allow guest users into intranet
    public String guestUsernamePassword(){
    	email = "kwelz@waystone.com";
    	pwd = "capstone29";
    	boolean valid = DatabaseOperation.empValidate(email, pwd, "ACTIVE");
        if(valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", email);
            DatabaseOperation.getEmployeeRole(email);
            return "employeeAdmin";
        } else {
            FacesContext.getCurrentInstance().addMessage("loginForm:password",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username and Password",
                    "Please enter correct Username and Password"));
            return "index";
        }
    } 
    //Obtain employee information from database
    public List<Employee> getEmployeeRecord(){
        return DatabaseOperation.getEmployeeListFromDB(email);
    }
    //Update employee details in the database
    public String updateEmployeeDetails(Employee updateEmployeeObj){
        return DatabaseOperation.updateEmployeeDetailsInDB(updateEmployeeObj);
    }
    //Update employee password in database
    public String updatePassword(){
        return DatabaseOperation.updateEmployeePassword(pwd, email);
    }
     //Update employee password in database if forced to reset
    public String updatePasswordToAdmin(){
    	return DatabaseOperation.updateEmployeePasswordToAdmin(pwd, email);
    }
    //Logout and end session
    public String logout(){
         HttpSession session = SessionUtils.getSession();
         session.invalidate();
         return "index";
     }
    //Obtain list of employees based on search criteria given   
    public void getEmployeeList(String searchCriteria, String searchRole, String searchInform){
        employeeList = DatabaseOperation.getAllEmployeesFromDB(searchCriteria, searchRole, searchInform);
        
    	if (employeeList.size() == 0){
    		Employee emp = new Employee();
    		emp.setEmail("No results found");
    		employeeList.add(emp);
    	}
    }
    //Obtain information of chosen employee from database
    public String viewEmployeeRecord(int currentEmpID, String empEmail){
    	if(empEmail.equals("No results found")){
    		return "searchEmployees.xhtml?faces-redirect=true";
    	} else {
    		return DatabaseOperation.viewEmployeeRecordFromDB(currentEmpID);
    	}
    }
    //Delete the employee in the database
    public String deleteEmployee(Employee delEmployeeObj){
    	int delEmployeeID = delEmployeeObj.getEmployeeID();
        return DatabaseOperation.deleteEmployeeInDB(delEmployeeID);
    }
    //Insert a new employee in the database
    public String insertNewEmployee(Employee newEmpObj){
    	return DatabaseOperation.insertNewEmployeeInDB(newEmpObj);
    }
    //Unlock or set employee account status to 'ACTIVE' in database
    public String unlockEmpAccountStatus(Employee accEmpObj){
    	String accEmpEmail = accEmpObj.getEmail();
    	return DatabaseOperation.unlockEmpAccountStatusInDB(accEmpEmail);
    }
    //Lock or set employee account status to 'LOCKED' in database
    public String lockEmpAccountStatus(Employee accEmpObj){
    	String accEmpEmail = accEmpObj.getEmail();
    	return DatabaseOperation.lockEmpAccountStatusInDB(accEmpEmail);
    }
    //Reset employee password to randomly generated temporary password
    public String resetPassword(String empEmail){
    	return DatabaseOperation.resetPasswordInDB(empEmail);
    }
    //Obtain role and status of employee
    public List<Employee> getEmployeeStat(String statEmpEmail){
    	return DatabaseOperation.getEmployeeStatInDB(statEmpEmail);
    }
  //Obtain list of all current employee roles
    public List<String> viewAllEmpRoles(){
        return DatabaseOperation.viewAllEmpRolesInDB();
    }
    
    public void clearList(){
    	setFirstName(null);
    	setMiddleInit(null);
    	setLastName(null);
    	setAddress(null);
    	setCity(null);
    	setState("AL");
    	setZipcode(null);
    	setPhone(null);
    	setNewEmail(null);
    	setDOB(null);
    	setRole("");
    	
    }
    public String undoChanges(){
    	getEmployeeRecord();
    	return "employeeProfileEdit.xhtml?faces-redirect=true";
    }
}   
    
    
    
