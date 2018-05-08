/*
 * CSCI 2999 Capstone Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import waystonepropertymanagement.employee.login.Employee;
/**
 * Database Operation Class includes all of the methods that connect to the database
 * @author KWelzbacher
 */
public class DatabaseOperation {
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
    
    
    //Employee Database Operations
    public static int getUserAttempts(String email){
    	
    	int userAttempts = 0;
    	LocalDateTime lastDateTime = null;
    	LocalDateTime now = LocalDateTime.now(); 
    	
    	try {
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT convert(VARCHAR, last_datetime, 126) AS last_datetime, attempts FROM emp_login WHERE email = ?");
            pstmt.setString(1, email);
            resultSetObj = pstmt.executeQuery();
            	if(resultSetObj.next()) {
            		String dateT = resultSetObj.getString("last_datetime");
            		if (dateT != null){
            			lastDateTime = LocalDateTime.parse(resultSetObj.getString("last_datetime"));
            			if(lastDateTime.isAfter(now.plus(2, ChronoUnit.HOURS))){
                			userAttempts = 0;
                			PreparedStatement pst = connObj.prepareStatement("UPDATE emp_login  SET attempts = ?  WHERE email = ?");
                			pst.setInt(1, userAttempts);
                            pst.setString(2, email);
                            pst.executeUpdate();
                		} else{
                			userAttempts = resultSetObj.getInt("attempts");
                		}
            		} else {
            			userAttempts = 0;
            		}
            		
            		
            	}
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());
            
        } finally {        
              DataConnect.close(connObj);       
        }
        return userAttempts;
    }
    
   public static void addUserAttempt(String email){
    	int userAttempts = getUserAttempts(email);
    	LocalDateTime now = LocalDateTime.now(); 
    	userAttempts++;
    	try {
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("UPDATE emp_login SET attempts = ?, last_datetime = ? WHERE email = ?");
            pstmt.setInt(1, userAttempts);
            pstmt.setObject(2, now);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            	
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());
            
        } finally {        
              DataConnect.close(connObj);       
        }
    	
    }
    
 
    public static boolean empValidate(String email, String password, String active) {
        
        try {
            connObj = DataConnect.getConnection();
            if(active.equals("ACTIVE") || active.equals("RESET")){
            	pstmt = connObj.prepareStatement("SELECT email, password FROM emp_login WHERE email = ? and password = ?");
            	pstmt.setString(1, email);
            	pstmt.setString(2, password);
            	
            	ResultSet rs = pstmt.executeQuery();
            	if(rs.next()) {
            		//result found, means valid inputs
            		PreparedStatement pst = connObj.prepareStatement("UPDATE emp_login SET attempts = '0' WHERE email = ?");
                	pst.setString(1, email);
                	pst.executeUpdate();
            		return true;
            	}
            }
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());
            return false;
        } finally {        
              DataConnect.close(connObj);       
        }
        return false;
    }
    
    public static List<Employee> getEmployeeStatInDB(String email) {
        Employee empStat = null;
        List<Employee> empStatList = new ArrayList();
        try {
            
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("Select acc_stat FROM emp_login WHERE email = ?");
            pstmt.setString(1, email);
                        
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
            	while(resultSetObj.next()){
            		empStat = new Employee();
            		empStat.setAccStatus(resultSetObj.getString("acc_stat"));
            		empStatList.add(empStat);
            	}
            }
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());
                   
        } finally {
              DataConnect.close(connObj);
        }
        return empStatList;
    }
    
    public static String empAccStatus(String email){
    	String empStat;
    	try {
    		connObj = DataConnect.getConnection();
    		pstmt = connObj.prepareStatement("SELECT acc_stat FROM emp_login WHERE email = ?");
    		pstmt.setString(1, email);
    		resultSetObj = pstmt.executeQuery();
    		if(resultSetObj.next()){
    			String empValid = resultSetObj.getString("acc_stat");
    			//account is currently active
    			if(empValid.equals("ACTIVE")){
    				empStat = "ACTIVE";
    				return empStat;
    				
    			} else if(empValid.equals("RESET")){
    				empStat = "RESET";
    				return empStat;
    			} else {
    				FacesContext.getCurrentInstance().addMessage("loginForm:password",
    	                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Account is locked. Please call IT to unlock account.",
    	                    "Account is locked. Please call IT to unlock account."));
    				return "/index.xhtml?faces-redirect=true";
    			}
    		}
    	} catch (SQLException e) {
        System.out.println("Login error -->" + e.getMessage());
        
    	} finally {        
          DataConnect.close(connObj);       
    	}
    	FacesContext.getCurrentInstance().addMessage("loginForm:password",
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Email not found. Please enter correct Email and Password.",
                "Email not found. Please enter correct Email and Password."));
		return "/index.xhtml?faces-redirect=true";
    }
    
    public static void lockEmpAccount(String email){
    	try {
    		connObj = DataConnect.getConnection();
    		pstmt = connObj.prepareStatement("UPDATE emp_login SET acc_stat = 'LOCK' WHERE email = ?");
    		pstmt.setString(1, email);
    		pstmt.executeUpdate();
    		System.out.println("Account with email: " + email + " is now locked");
    	} catch (SQLException e) {
        System.out.println("Login error -->" + e.getMessage());
        
    	} finally {        
          DataConnect.close(connObj);       
    	}
    }
    
    public static List<Employee> getEmployeeRole(String email) {
        Employee empRole = null;
        List<Employee> empRoleList = new ArrayList();
        Map<String, Object> sessMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        try {
            
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("Select role FROM emp_login WHERE email = ?");
            pstmt.setString(1, email);
                        
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
            	resultSetObj.next();
            	empRole = new Employee();
                empRole.setRole(resultSetObj.getString("role"));
                empRoleList.add(empRole);
            }
            sessMapObj.put("empRoleObj", empRole);
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());
                   
        } finally {
              DataConnect.close(connObj);
        }
        System.out.println(empRole.getRole());
        return empRoleList;
    }
   
    
    public static List<Employee> getAllEmployeesFromDB(String searchCriteria, String searchRole, String searchInform){
    	List<Employee> empSearchList = new ArrayList();
    	String query = "";
        
        switch (searchCriteria) {
            case "Employee ID":
                query = "SELECT * FROM EMPLOYEE WHERE EMPID = ?";
                break;
            case "Last Name":
                query = "SELECT * FROM EMPLOYEE WHERE LASTNAME = ?";
                break;
            case "Role":
            	query = "SELECT employee.*, emp_login.role FROM employee FULL OUTER JOIN emp_login ON employee.email=emp_login.email WHERE emp_login.role = ?";
            	searchInform = searchRole;
            	break;
        }
        try {
            
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement(query); 
            pstmt.setString(1, searchInform);
            
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                Employee empSearchObj = new Employee();
                
                empSearchObj.setEmployeeID(resultSetObj.getInt("EMPID"));
                empSearchObj.setFirstName(resultSetObj.getString("FIRSTNAME"));
                empSearchObj.setLastName(resultSetObj.getString("LASTNAME"));
                empSearchObj.setMiddleInit(resultSetObj.getString("MI"));
                empSearchObj.setAddress(resultSetObj.getString("ADDRESS"));
                empSearchObj.setCity(resultSetObj.getString("CITY"));
                empSearchObj.setState(resultSetObj.getString("STATE"));
                empSearchObj.setZipcode(resultSetObj.getString("ZIP"));
                empSearchObj.setPhone(resultSetObj.getString("PHONE"));
                empSearchObj.setEmail(resultSetObj.getString("EMAIL"));
                empSearchObj.setDOB(resultSetObj.getString("DOB"));
                
                empSearchList.add(empSearchObj);
               }
                System.out.println("Total Records Fetched: " + empSearchList.size());
               } 
                          
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        empSearchList.removeIf(empSearchObj -> empSearchObj.getEmployeeID()==0);
        return empSearchList;
    }
    
    public static String viewEmployeeRecordFromDB(int currentEmpID){
    	 Employee viewEmp;
         Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
         
         try{
             connObj = DataConnect.getConnection();
             pstmt = connObj.prepareStatement("SELECT EMPLOYEE.*, emp_login.role FROM EMPLOYEE FULL OUTER JOIN emp_login ON employee.email=emp_login.email WHERE EMPID = ?");
             pstmt.setInt(1, currentEmpID);
             resultSetObj = pstmt.executeQuery();
             if(resultSetObj != null) {
                while( resultSetObj.next()){
                	viewEmp= new Employee();
                 
                	viewEmp.setEmployeeID(resultSetObj.getInt("EMPID"));
                	viewEmp.setFirstName(resultSetObj.getString("FIRSTNAME"));
                	viewEmp.setLastName(resultSetObj.getString("LASTNAME"));
                	viewEmp.setMiddleInit(resultSetObj.getString("MI"));
                	viewEmp.setAddress(resultSetObj.getString("ADDRESS"));
                	viewEmp.setCity(resultSetObj.getString("CITY"));
                	viewEmp.setState(resultSetObj.getString("STATE"));
                	viewEmp.setZipcode(resultSetObj.getString("ZIP"));
                	viewEmp.setPhone(resultSetObj.getString("PHONE"));
                	viewEmp.setEmail(resultSetObj.getString("EMAIL"));
                	viewEmp.setDOB(resultSetObj.getString("DOB"));
                	viewEmp.setRole(resultSetObj.getString("role"));
                	
                 sessionMapObj.put("employeeViewObj", viewEmp);
                }             
             }
         } catch (SQLException e) {
             System.out.println("Login error -->" + e.getMessage());        
         } finally {    
               DataConnect.close(connObj);
         }
         return "/viewEmployee.xhtml?faces-redirect=true";
    }
    
    public static String deleteEmployeeInDB(int delEmployeeID){
    	System.out.println("deleteEmployeeinDB() : Employee ID:" + delEmployeeID);
    	String empEmail ="";
    	try{
    		connObj = DataConnect.getConnection();
    		PreparedStatement pst = connObj.prepareStatement("SELECT EMAIL FROM EMPLOYEE WHERE EMPID = ?");
    		pst.setInt(1, delEmployeeID);
    		resultSetObj=pst.executeQuery();
    		while(resultSetObj.next()){
    			empEmail = resultSetObj.getString("EMAIL");
    		}
    		
    		pstmt = connObj.prepareStatement("DELETE FROM EMPLOYEE WHERE EMPID = ?" );
    		pstmt.setInt(1, delEmployeeID);
    		pstmt.executeUpdate();
    		
    		PreparedStatement prepst = connObj.prepareStatement("DELETE FROM emp_login WHERE email = " );
    		prepst.setString(1, empEmail);
    		prepst.executeUpdate();
    	} catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
    	} finally {    
    		DataConnect.close(connObj);
    	}
    	return "/searchEmployees.xhtml?faces-redirect=true";
    }
    
    public static final String AB ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static SecureRandom rnd = new SecureRandom();
    public static String randomString(int len){
    	StringBuilder sb = new StringBuilder(len);
    	for(int i = 0; i < len; i++){
    		sb.append(AB.charAt(rnd.nextInt(AB.length())));
    	}
    	return sb.toString();
    }
    
    public static String insertNewEmployeeInDB(Employee newEmpObj) {
        int saveResult = 0;
        String pword = randomString(12);
        
       try{
           connObj = DataConnect.getConnection();
           pstmt = connObj.prepareStatement("INSERT INTO EMPLOYEE (FIRSTNAME, LASTNAME, MI, ADDRESS, CITY, STATE, ZIP, PHONE, EMAIL, DOB) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
           pstmt.setString(1, newEmpObj.getFirstName());
           pstmt.setString(2, newEmpObj.getLastName());
           pstmt.setString(3, newEmpObj.getMiddleInit());
           pstmt.setString(4, newEmpObj.getAddress());
           pstmt.setString(5, newEmpObj.getCity());
           pstmt.setString(6, newEmpObj.getState());
           pstmt.setString(7, newEmpObj.getZipcode());
           pstmt.setString(8, newEmpObj.getPhone());
           pstmt.setString(9, newEmpObj.getNewEmail());
           pstmt.setString(10, newEmpObj.getDOB());
           saveResult = pstmt.executeUpdate();
           
           PreparedStatement pst = connObj.prepareStatement("INSERT INTO emp_login (EMAIL, PASSWORD, ROLE, acc_stat) VALUES (?, ?, ?, ?)");
           pst.setString(1, newEmpObj.getNewEmail());
           pst.setString(2, pword);
           pst.setString(3, newEmpObj.getRole());
           pst.setString(4, "RESET");
           saveResult = pst.executeUpdate();
       } catch (SQLException e) {
               System.out.println("Login error -->" + e.getMessage());        
       } finally {    
                DataConnect.close(connObj);
       }
       
       if(saveResult !=0){
       	FacesContext.getCurrentInstance().addMessage("createEmpForm:empFirstName",
                   new FacesMessage(FacesMessage.SEVERITY_WARN, "New Employee was successfully created",
                   "New Employee was successfully created"));
       	clearList(newEmpObj);
           
       } else {
       	FacesContext.getCurrentInstance().addMessage("createEmpForm:empFirstName",
                   new FacesMessage(FacesMessage.SEVERITY_WARN, "There was a problem when creating the new Employee",
                   "There was a problem when creating the new Employee"));
       }
       return "createEmployee.xhtml?faces=redirect=true";
    }
    
    public static void clearList(Employee clearEmp){
    	clearEmp.setFirstName(null);
    	clearEmp.setLastName(null);
    	clearEmp.setMiddleInit(null);
    	clearEmp.setAddress(null);
    	clearEmp.setCity(null);
    	clearEmp.setZipcode(null);
    	clearEmp.setState("AL");
    	clearEmp.setRole("");
    	clearEmp.setPhone(null);
    	clearEmp.setNewEmail(null);
    	clearEmp.setDOB(null);
    }
    
    public static String unlockEmpAccountStatusInDB(String accEmpEmail){
    	int saveResult = 0;
    	try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("UPDATE emp_login SET acc_stat = ?, attempts = '0' WHERE EMAIL = ?");
            pstmt.setString(1, "ACTIVE");
            pstmt.setString(2, accEmpEmail);
            saveResult = pstmt.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
        } finally {    
                 DataConnect.close(connObj);
        }
    	if(saveResult !=0){
    		FacesContext.getCurrentInstance().addMessage("viewEmpForm:unlockBtn",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Employee Account was successfully Unlocked",
                    "Employee Account was successfully Unlocked"));
               
           } else {
        	   FacesContext.getCurrentInstance().addMessage("viewEmpForm:unlockBtn",
                       new FacesMessage(FacesMessage.SEVERITY_WARN, "There was a problem unlocking the Employee Account",
                       "There was a problem unlocking the Employee Account"));
           }
    	
    	return "viewEmployee";
    }
    
    public static String lockEmpAccountStatusInDB(String accEmpEmail){
    	int saveResult = 0;
    	try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("UPDATE emp_login SET acc_stat = ? WHERE EMAIL = ?");
            pstmt.setString(1, "LOCKED");
            pstmt.setString(2, accEmpEmail);
            saveResult = pstmt.executeUpdate();
            	
        } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
        } finally {    
                 DataConnect.close(connObj);
        }
        if(saveResult !=0){
    		FacesContext.getCurrentInstance().addMessage("viewEmpForm:unlockBtn",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Employee Account was successfully Locked",
                    "Employee Account was successfully Locked"));
               
           } else {
        	   FacesContext.getCurrentInstance().addMessage("viewEmpForm:unlockBtn",
                       new FacesMessage(FacesMessage.SEVERITY_WARN, "There was a problem locking the Employee Account",
                       "There was a problem locking the Employee Account"));
           }
    	return "viewEmployee";
    }
    
    public static String resetPasswordInDB(String empEmail){
    	int saveResult = 0;
    	String pword = randomString(12);
    	try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("UPDATE emp_login SET PASSWORD = ?, acc_stat = ?, attempts = '0' WHERE EMAIL = ?");
            pstmt.setString(1, pword);
            pstmt.setString(2, "RESET");
            pstmt.setString(3, empEmail);
            saveResult = pstmt.executeUpdate(); 
            
            SendMail.sendMail(empEmail, pword);
            	
        } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
        } finally {    
                 DataConnect.close(connObj);
        }
        if(saveResult !=0){
    		FacesContext.getCurrentInstance().addMessage("viewEmpForm:unlockBtn",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Employee password was successfully reset",
                    "Employee password was successfully reset"));
               
           } else {
        	   FacesContext.getCurrentInstance().addMessage("viewEmpForm:unlockBtn",
                       new FacesMessage(FacesMessage.SEVERITY_WARN, "There was a problem resetting the Employee password",
                       "There was a problem resetting the Employee password"));
           } 
        
    	return "viewEmployee"; 
    }
    
    
    public static List<Employee> getEmployeeListFromDB(String username){
       Employee empRecord = null;
       List<Employee> empList = new ArrayList();
       Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();        
        try {
            
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM EMPLOYEE WHERE EMAIL = ?");
            pstmt.setString(1, username);
            
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
                resultSetObj.next();
                empRecord = new Employee();
                empRecord.setEmployeeID(resultSetObj.getInt("EMPID"));
                empRecord.setFirstName(resultSetObj.getString("firstName"));
                empRecord.setLastName(resultSetObj.getString("lastName"));
                empRecord.setMiddleInit(resultSetObj.getString("MI"));
                empRecord.setAddress(resultSetObj.getString("address"));
                empRecord.setCity(resultSetObj.getString("city"));
                empRecord.setState(resultSetObj.getString("state"));
                empRecord.setZipcode(resultSetObj.getString("zip"));
                empRecord.setPhone(resultSetObj.getString("phone"));
                empRecord.setEmail(resultSetObj.getString("email"));
                empRecord.setDOB(resultSetObj.getString("DOB"));
                
                empList.add(empRecord);
            }              
            sessionMapObj.put("empRecordObj", empRecord);
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        
        return empList;
     }
    
    public static List<String> viewAllEmpRolesInDB(){
        List<String> roleNameList = new ArrayList();
        try{
           connObj = DataConnect.getConnection();
           pstmt = connObj.prepareStatement("SELECT DISTINCT role FROM emp_login");
           resultSetObj = pstmt.executeQuery();
           while( resultSetObj.next()){
               roleNameList.add(resultSetObj.getString("role"));
           }
       } catch (SQLException e) {
           System.out.println("Login error -->" + e.getMessage());        
       } finally {    
             DataConnect.close(connObj);
       }
       return roleNameList;
    }
    
    public static String updateEmployeeDetailsInDB(Employee updateEmployeeObj){
        try{      
                connObj = DataConnect.getConnection();
                pstmt = connObj.prepareStatement("UPDATE EMPLOYEE SET firstName = ?, lastName = ?, MI = ?, "
                    + "address = ?, city = ?, state = ?, zip = ?, phone = ?, dob = ? WHERE email = ?");
                pstmt.setString(1, updateEmployeeObj.getFirstName());
                pstmt.setString(2, updateEmployeeObj.getLastName());
                pstmt.setString(3, updateEmployeeObj.getMiddleInit());
                pstmt.setString(4, updateEmployeeObj.getAddress());
                pstmt.setString(5, updateEmployeeObj.getCity());
                pstmt.setString(6, updateEmployeeObj.getState());
                pstmt.setString(7, updateEmployeeObj.getZipcode());
                pstmt.setString(8, updateEmployeeObj.getPhone());
                pstmt.setString(9, updateEmployeeObj.getDOB());
                pstmt.setString(10, updateEmployeeObj.getEmail());
            
                pstmt.executeUpdate();
                
            } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
            } finally {    
                 DataConnect.close(connObj);
            }
        return "/employeeProfile.xhtml?faces-redirect=true";
        }

    public static String updateEmployeePassword(String password,String email){
        try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("UPDATE emp_login SET password = ? WHERE email = ?");
            pstmt.setString(1, password);
            pstmt.setString(2, email);
            pstmt.executeQuery();
        } catch(SQLException e){
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
            DataConnect.close(connObj);
        }
        return "/employeeProfile.xhtml?faces-redirect=true";
    }
    
    public static String updateEmployeePasswordToAdmin(String password,String email){
        int saveResult = 0;
    	try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("UPDATE emp_login SET password = ?, acc_stat = ? WHERE email = ?");
            pstmt.setString(1, password);
            pstmt.setString(2, "ACTIVE");
            pstmt.setString(3, email);
            saveResult = pstmt.executeUpdate();
            
            if(saveResult !=0){
            	return "/employeeAdmin.xhtml?faces-redirect=true";    
               } else {
            	   FacesContext.getCurrentInstance().addMessage("resetForm:resetPassword",
                           new FacesMessage(FacesMessage.SEVERITY_WARN, "There was a problem resetting the Employee password. Please try again or call IT.",
                           "There was a problem resetting the Employee password. Please try again or call IT."));
               }
            
        } catch(SQLException e){
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
            DataConnect.close(connObj);
        }
        return "/resetPassword.xhtml?faces-redirect=true";
    }
    
    
    //Tenant Database Operations
    public static List<Tenant> getTenantListFromDB(String searchCrit, String searchInfo){
       List<Tenant> tenList = new ArrayList<>();
        String query;
        
        switch (searchCrit) {
            case "Tenant ID":
                query = ("SELECT * FROM TENANT WHERE TENANT_ID = ?");
                break;
            case "Last Name":
                query = ("SELECT * FROM TENANT WHERE LASTNAME = ?");
                break;
            default:
                query = ("SELECT * FROM TENANT WHERE PHONE = ?");
                break; 
        }
        try {
            
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement(query); 
            pstmt.setString(1, searchInfo);
            
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                Tenant tenObj = new Tenant();
                
                tenObj.setTenantID(resultSetObj.getInt("TENANT_ID"));
                tenObj.setFirstName(resultSetObj.getString("FIRSTNAME"));
                tenObj.setLastName(resultSetObj.getString("LASTNAME"));
                tenObj.setMi(resultSetObj.getString("MI"));
                tenObj.setPermAddress(resultSetObj.getString("PERM_ADDRESS"));
                tenObj.setPermCity(resultSetObj.getString("PERM_CITY"));
                tenObj.setPermState(resultSetObj.getString("PERM_STATE"));
                tenObj.setPermZip(resultSetObj.getString("PERM_ZIP"));
                tenObj.setPhone(resultSetObj.getString("PHONE"));
                tenObj.setEmail(resultSetObj.getString("EMAIL"));
                tenObj.setDOB(resultSetObj.getString("DOB"));
                PreparedStatement prepst = connObj.prepareStatement("SELECT * FROM UNITS WHERE TENANT_ID = ?"); 
                prepst.setInt(1, tenObj.getTenantID());
                ResultSet rset = prepst.executeQuery();
                    while(rset.next()){
                        tenObj.setBuilding(rset.getString("BUILDING"));
                        tenObj.setAptNum(rset.getString("APTNUM"));
                    }
                tenList.add(tenObj);
               }
                System.out.println("Total Records Fetched: " + tenList.size());
               } 
                          
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return tenList;
      }
    
    public static List<Tenant> getTenantListBuilding(String searchInfo){
       List<Tenant> tenBuildList = new ArrayList<>();
               
        try {
            
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM UNITS WHERE BUILDING = ? AND TENANT_ID IS NOT NULL"); 
            pstmt.setString(1, searchInfo);
            
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                Tenant tenObj = new Tenant();
                tenObj.setBuilding(resultSetObj.getString("BUILDING"));
                tenObj.setAptNum(resultSetObj.getString("APTNUM"));
                tenObj.setAddress(resultSetObj.getString("ADDRESS"));
                tenObj.setCity(resultSetObj.getString("CITY"));
                tenObj.setState(resultSetObj.getString("STATE"));
                tenObj.setZipcode(resultSetObj.getString("ZIP"));
                tenObj.setTenantID(resultSetObj.getInt("TENANT_ID"));
                
                PreparedStatement prepst = connObj.prepareStatement("SELECT * FROM TENANT WHERE TENANT_ID = ?"); 
                prepst.setInt(1, tenObj.getTenantID());
                ResultSet rset = prepst.executeQuery();
                    while(rset.next()){
                        tenObj.setFirstName(rset.getString("FIRSTNAME"));
                        tenObj.setLastName(rset.getString("LASTNAME"));
                        tenObj.setMi(rset.getString("MI"));
                        tenObj.setPermAddress(rset.getString("PERM_ADDRESS"));
                        tenObj.setPermCity(rset.getString("PERM_CITY"));
                        tenObj.setPermState(rset.getString("PERM_STATE"));
                        tenObj.setPermZip(rset.getString("PERM_ZIP"));
                        tenObj.setPhone(rset.getString("PHONE"));
                        tenObj.setEmail(rset.getString("EMAIL"));
                        tenObj.setDOB(rset.getString("DOB"));
                    }
                tenBuildList.add(tenObj);
                
               }
                System.out.println("Total Records Fetched: " + tenBuildList.size());
               }
                          
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return tenBuildList;
      }
    
    public static String viewTenantRecordInDB(int tenantID){
        Tenant viewTen;
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        
        try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM TENANT WHERE TENANT_ID = ?");
            pstmt.setInt(1, tenantID);
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                viewTen = new Tenant();
                
                viewTen.setTenantID(resultSetObj.getInt("TENANT_ID"));
                viewTen.setFirstName(resultSetObj.getString("FIRSTNAME"));
                viewTen.setLastName(resultSetObj.getString("LASTNAME"));
                viewTen.setMi(resultSetObj.getString("MI"));
                viewTen.setPermAddress(resultSetObj.getString("PERM_ADDRESS"));
                viewTen.setPermCity(resultSetObj.getString("PERM_CITY"));
                viewTen.setPermState(resultSetObj.getString("PERM_STATE"));
                viewTen.setPermZip(resultSetObj.getString("PERM_ZIP"));
                viewTen.setPhone(resultSetObj.getString("PHONE"));
                viewTen.setEmail(resultSetObj.getString("EMAIL"));
                
                viewTen.setDOB(resultSetObj.getString("DOB"));
                PreparedStatement prepst = connObj.prepareStatement("SELECT * FROM UNITS WHERE TENANT_ID = ?"); 
                prepst.setInt(1, viewTen.getTenantID());
                ResultSet rset = prepst.executeQuery();
                    while(rset.next()){
                        viewTen.setBuilding(rset.getString("BUILDING"));
                        viewTen.setAptNum(rset.getString("APTNUM"));
                    }
                sessionMapObj.put("tenantViewObj", viewTen);
               }             
            }
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return "/viewTenant.xhtml?faces-redirect=true";
    }
    
    public static String updateTenantDetailsInDB(Tenant updateTenObj){
        try{       
                connObj = DataConnect.getConnection();
                pstmt = connObj.prepareStatement("UPDATE TENANT SET FIRSTNAME = ?, LASTNAME = ?, MI = ?, "
                    + "PERM_ADDRESS = ?, PERM_CITY = ?, PERM_STATE = ?, PERM_ZIP = ?, PHONE = ?, EMAIL= ?, DOB = ? WHERE TENANT_ID = ?");
                pstmt.setString(1, updateTenObj.getFirstName());
                pstmt.setString(2, updateTenObj.getLastName());
                pstmt.setString(3, updateTenObj.getMi());
                pstmt.setString(4, updateTenObj.getPermAddress());
                pstmt.setString(5, updateTenObj.getPermCity());
                pstmt.setString(6, updateTenObj.getPermState());
                pstmt.setString(7, updateTenObj.getPermZip());
                pstmt.setString(8, updateTenObj.getPhone());
                pstmt.setString(9, updateTenObj.getEmail());
                pstmt.setString(10, updateTenObj.getDOB());                
                pstmt.setInt(11, updateTenObj.getTenantID());
            
                pstmt.executeUpdate();
                
            } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
            } finally {    
                 DataConnect.close(connObj);
            }
        return "/viewTenant.xhtml?faces-redirect=true";
        }
    
    public static String insertNewTenantInDB(Tenant newTenantObj){
        int saveResult = 0;
        try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("INSERT INTO TENANT (FIRSTNAME, LASTNAME, MI, PERM_ADDRESS, PERM_CITY, PERM_STATE, PERM_ZIP, PHONE, EMAIL, DOB) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, newTenantObj.getFirstName());
            pstmt.setString(2, newTenantObj.getLastName());
            pstmt.setString(3, newTenantObj.getMi());
            pstmt.setString(4, newTenantObj.getPermAddress());
            pstmt.setString(5, newTenantObj.getPermCity());
            pstmt.setString(6, newTenantObj.getPermState());
            pstmt.setString(7, newTenantObj.getPermZip());
            pstmt.setString(8, newTenantObj.getPhone());
            pstmt.setString(9, newTenantObj.getEmail());
            pstmt.setString(10, newTenantObj.getDOB());
            saveResult = pstmt.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
        } finally {    
                 DataConnect.close(connObj);
        }
        if(saveResult !=0){
           	FacesContext.getCurrentInstance().addMessage("createTenForm:newFirstName",
                       new FacesMessage(FacesMessage.SEVERITY_WARN, "New Tenant was successfully created",
                       "New Tenant was successfully created"));
           	clearTenantList(newTenantObj);
               
           } else {
           	FacesContext.getCurrentInstance().addMessage("createTenForm:newFirstName",
                       new FacesMessage(FacesMessage.SEVERITY_WARN, "There was a problem when creating the new Tenant",
                       "There was a problem when creating the new Tenant"));
           }
        
        return "insertTenant.xhtml?faces=redirect=true";
    }
    public static void clearTenantList(Tenant clearTen){
    	clearTen.setFirstName(null);
    	clearTen.setLastName(null);
    	clearTen.setMi(null);
    	clearTen.setPermAddress(null);
    	clearTen.setPermCity(null);
    	clearTen.setPermZip(null);
    	clearTen.setPermState("AL");
    	clearTen.setPhone(null);
    	clearTen.setEmail(null);
    	clearTen.setDOB(null);
    }
    
    public static double getTenantRecordBalanceInDB(int tenantID){
    	Connection connObject = null;
    	double finalAmount =0;
    	double balance = 0;
    	 try{
             connObject = DataConnect.getConnection();
             pstmt = connObject.prepareStatement("SELECT AMOUNT, IS_CREDIT FROM RECORDS WHERE TENANT_ID = ? AND (IS_DELETED IS NULL OR IS_DELETED='1')");
             pstmt.setInt(1, tenantID);
             resultSetObj = pstmt.executeQuery();
             if(resultSetObj != null) {
            	 while( resultSetObj.next()) {
            		 double amount = resultSetObj.getDouble("AMOUNT");
            		 boolean isCred = resultSetObj.getBoolean("IS_CREDIT");                 
                 
            		 	if(isCred == true){
            		 		finalAmount = amount * -1;
            			} else {
            				finalAmount = amount;
            			}
            		 	balance += finalAmount; 
            	 }
             }
         } catch (SQLException e) {
                     System.out.println("Login error -->" + e.getMessage());        
         } finally {
        	 DataConnect.close(connObject);
         }
     return  balance;
    }
 
    public static String deleteTenantInDB(int delTenantID){
    	System.out.println("deleteTenantinDB() : Tenant ID:" + delTenantID);
    	
    	try{
    		connObj = DataConnect.getConnection();
    		pstmt = connObj.prepareStatement("DELETE FROM TENANT WHERE TENANT_ID = ?" );
    		pstmt.setInt(1, delTenantID);
    		pstmt.executeUpdate();
    		
    		PreparedStatement prepst = connObj.prepareStatement("UPDATE UNITS SET TENANT_ID = NULL WHERE TENANT_ID = ?" );
    		prepst.setInt(1, delTenantID);
    		prepst.executeUpdate();
    	} catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
    	} finally {    
    		DataConnect.close(connObj);
    	}
    	return "/tenantAccounts.xhtml?faces-redirect=true";
    }
    
    
    
    //Unit Database Operation
     public static List<Unit> getUnitListFromDB(String unitBuildSearch, String vacancy){
        
        List<Unit> unitSearchList = new ArrayList<>();
        try {
            connObj = DataConnect.getConnection();
            //job Type is not required
            if(unitBuildSearch.equals("")){
                switch (vacancy) {    
                    case "All":
                        pstmt = connObj.prepareStatement("SELECT * FROM UNITS");
                        break;
                    case "Vacant":
                        pstmt = connObj.prepareStatement("SELECT * FROM UNITS WHERE TENANT_ID IS NULL");
                        break;
                    default:
                        pstmt = connObj.prepareStatement("SELECT * FROM UNITS WHERE TENANT_ID IS NOT NULL");
                        break;
                }
            } else {
                switch (vacancy) {
                    case "All":
                        pstmt = connObj.prepareStatement("SELECT * FROM UNITS WHERE BUILDING= ?");
                        pstmt.setString(1, unitBuildSearch);
                        break;
                    case "Vacant":
                        pstmt = connObj.prepareStatement("SELECT * FROM UNITS WHERE building = ? AND TENANT_ID IS NULL");
                        pstmt.setString(1, unitBuildSearch);
                        break;
                    default:
                        pstmt = connObj.prepareStatement("SELECT * FROM UNITS WHERE BUILDING = ? AND TENANT_ID IS NOT NULL");
                        pstmt.setString(1, unitBuildSearch);
                        break;
            }
            }
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                Unit unitSearchObj = new Unit();
                unitSearchObj.setUnitID(resultSetObj.getInt("UNIT_ID"));
                unitSearchObj.setUnitBuilding(resultSetObj.getString("Building"));
                unitSearchObj.setUnitAptNum(resultSetObj.getString("APTNUM"));
                unitSearchObj.setUnitAddress(resultSetObj.getString("ADDRESS"));
                unitSearchObj.setUnitCity(resultSetObj.getString("CITY"));
                unitSearchObj.setUnitState(resultSetObj.getString("STATE"));
                unitSearchObj.setUnitZip(resultSetObj.getString("ZIP"));
                unitSearchObj.setUnitRent(resultSetObj.getString("RENT"));
                unitSearchObj.setUnitTenantID(resultSetObj.getString("TENANT_ID"));
                PreparedStatement prepst = connObj.prepareStatement("SELECT FIRSTNAME, LASTNAME FROM TENANT WHERE TENANT_ID = ?"); 
                prepst.setString(1, unitSearchObj.getUnitTenantID());
                ResultSet rset = prepst.executeQuery();
                    while(rset.next()){
                        
                        unitSearchObj.setUnitTenFirstName(rset.getString("FIRSTNAME"));
                        unitSearchObj.setUnitTenLastName(rset.getString("LASTNAME"));
                    }
                unitSearchList.add(unitSearchObj);
                
               }
                System.out.println("Total Records Fetched: " + unitSearchList.size());
                
               }
                          
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return unitSearchList;
    }
    
     public static String viewUnitRecordInDB(int unitID){
        Unit viewUnit;
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        
        try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM UNITS WHERE UNIT_ID = ?");
            pstmt.setInt(1, unitID);
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                viewUnit = new Unit();
                
                viewUnit.setUnitID(resultSetObj.getInt("UNIT_ID"));
                viewUnit.setUnitBuilding(resultSetObj.getString("BUILDING"));
                viewUnit.setUnitAptNum(resultSetObj.getString("APTNUM"));
                viewUnit.setUnitAddress(resultSetObj.getString("ADDRESS"));
                viewUnit.setUnitCity(resultSetObj.getString("CITY"));
                viewUnit.setUnitState(resultSetObj.getString("STATE"));
                viewUnit.setUnitZip(resultSetObj.getString("ZIP"));
                viewUnit.setUnitRent(resultSetObj.getString("RENT"));
                viewUnit.setUnitTenantID(resultSetObj.getString("TENANT_ID"));
                PreparedStatement prepst = connObj.prepareStatement("SELECT FIRSTNAME, LASTNAME FROM TENANT WHERE TENANT_ID = ?"); 
                prepst.setString(1, viewUnit.getUnitTenantID());
                ResultSet rset = prepst.executeQuery();
                    while(rset.next()){
                        
                        viewUnit.setUnitTenFirstName(rset.getString("FIRSTNAME"));
                        viewUnit.setUnitTenLastName(rset.getString("LASTNAME"));
                    }
                                
                sessionMapObj.put("unitViewObj", viewUnit);
                
               }             
            }        
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return "/viewUnit.xhtml?faces-redirect=true";
     }
    
     public static String updateUnitDetailsInDB(Unit updateUnitObj){
          try{
                connObj = DataConnect.getConnection();
                pstmt = connObj.prepareStatement("UPDATE UNITS SET BUILDING = ?, APTNUM = ?, ADDRESS = ?, "
                    + "CITY = ?, STATE = ?, ZIP = ?, RENT = ?, TENANT_ID = ? WHERE UNIT_ID = ?");
                pstmt.setString(1, updateUnitObj.getUnitBuilding().toUpperCase());
                pstmt.setString(2, updateUnitObj.getUnitAptNum());
                pstmt.setString(3, updateUnitObj.getUnitAddress().toUpperCase());
                pstmt.setString(4, updateUnitObj.getUnitCity().toUpperCase());
                pstmt.setString(5, updateUnitObj.getUnitState());
                pstmt.setString(6, updateUnitObj.getUnitZip());
                pstmt.setString(7, updateUnitObj.getUnitRent());
                pstmt.setString(8, updateUnitObj.getUnitTenantID());
                pstmt.setInt(9, updateUnitObj.getUnitID());
                            
                pstmt.executeUpdate();
                
            } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
            } finally {    
                 DataConnect.close(connObj);
            }
        return "/viewUnit.xhtml?faces-redirect=true";
     }
     
     public static String insertNewUnitInDB(Unit newUnitObj) {
         int saveResult = 0;
        try{
            connObj = DataConnect.getConnection();
            
            pstmt = connObj.prepareStatement("INSERT INTO UNITS (BUILDING, APTNUM, ADDRESS, CITY, STATE, ZIP, RENT) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, newUnitObj.getUnitBuilding().toUpperCase());
            pstmt.setString(2, newUnitObj.getUnitAptNum());
            pstmt.setString(3, newUnitObj.getUnitAddress().toUpperCase());
            pstmt.setString(4, newUnitObj.getUnitCity().toUpperCase());
            pstmt.setString(5, newUnitObj.getUnitState());
            pstmt.setString(6, newUnitObj.getUnitZip());
            pstmt.setString(7, newUnitObj.getUnitRent());
            
            
            saveResult = pstmt.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
        } finally {    
                 DataConnect.close(connObj);
        }
        
        if(saveResult !=0){
        	FacesContext.getCurrentInstance().addMessage("unitForm:newUnitBuilding",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "New Unit was successfully created",
                    "New Unit was successfully created"));
        	clearUnit(newUnitObj);
            
        } else {
        	FacesContext.getCurrentInstance().addMessage("unitForm:newUnitBuilding",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "There was a problem when creating the new Unit",
                    "There was a problem when creating the new Unit"));
        }
        return "createUnit.xhtml?faces=redirect=true";
     }
     public static void clearUnit(Unit clearUn){
    	 clearUn.setUnitBuilding(null);
    	 clearUn.setUnitAptNum(null);
    	 clearUn.setUnitAddress(null);
    	 clearUn.setUnitCity(null);
    	 clearUn.setUnitState(null);
    	 clearUn.setUnitZip(null);
    	 clearUn.setUnitRent(null);
     }
     
     public static List<String> viewAllBuildingNamesInDB(){
         List<String> buildNameList = new ArrayList();
         try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT DISTINCT BUILDING FROM UNITS");
            resultSetObj = pstmt.executeQuery();
            while( resultSetObj.next()){
                buildNameList.add(resultSetObj.getString("BUILDING"));
            }
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return buildNameList;
     }
     
     public static String deleteUnitInDB(int delUnitID){
     	System.out.println("deleteUnitinDB() : Unit ID:" + delUnitID);
     	
     	try{
     		connObj = DataConnect.getConnection();
     		pstmt = connObj.prepareStatement("DELETE FROM UNITS WHERE UNIT_ID = ?" );
     		pstmt.setInt(1, delUnitID);
     		pstmt.executeUpdate();
     		
     		
     	} catch (SQLException e) {
             System.out.println("Login error -->" + e.getMessage());        
     	} finally {    
     		DataConnect.close(connObj);
     	}
     	return "/units.xhtml?faces-redirect=true";
     }
     
     
     
     //Account Database Operation
   //Account Database Operation
     public static List<Account> getAccountListFromDB(String accSearchName, String accSearchType){
         List<Account> accountSearchList = new ArrayList<>();
       
        try {
            connObj = DataConnect.getConnection();
            // Type is not required
            if(accSearchName.equals("")){
                if(accSearchType.equals("")){
                    pstmt = connObj.prepareStatement("SELECT ACCOUNT_NAME, ACCOUNT_TYPE, acc_id FROM ACCOUNTS WHERE (IS_DELETED IS NULL OR IS_DELETED = '0')");
                } else {
                    pstmt = connObj.prepareStatement("SELECT ACCOUNT_NAME, ACCOUNT_TYPE, acc_id FROM ACCOUNTS WHERE ACCOUNT_TYPE = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0')");
                    pstmt.setString(1, accSearchType);
                }
            } else {
                if(accSearchType.equals("")){
                    pstmt = connObj.prepareStatement("SELECT ACCOUNT_NAME, ACCOUNT_TYPE, acc_id FROM ACCOUNTS WHERE ACCOUNT_NAME = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0')");
                    pstmt.setString(1, accSearchName);
                } else {
                    pstmt = connObj.prepareStatement("SELECT ACCOUNT_NAME, ACCOUNT_TYPE, acc_id FROM ACCOUNTS WHERE ACCOUNT_NAME = ? AND ACCOUNT_TYPE = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0')");
                    pstmt.setString(1, accSearchName);
                    pstmt.setString(2, accSearchType);
                }
            }
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                Account accSearchObj = new Account();
                accSearchObj.setAccountName(resultSetObj.getString("ACCOUNT_NAME"));
                accSearchObj.setAccountType(resultSetObj.getString("ACCOUNT_TYPE"));
                accSearchObj.setAccountID(resultSetObj.getInt("acc_id"));
                accountSearchList.add(accSearchObj);
               }
                System.out.println("Total Records Fetched: " + accountSearchList.size());
               }
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return accountSearchList;
     }
     
     public static List<String> getAccountsByType( String accType){
         List<String> accountList = new ArrayList<>();
       
        try {
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNT_TYPE = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0')");
            pstmt.setString(1, accType);
            
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                String newAcc;
                newAcc = resultSetObj.getString("ACCOUNT_NAME");
                
                accountList.add(newAcc);
               }
                System.out.println("Total Records Fetched: " + accountList.size());
               }
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return accountList;
     }
     
     public static String viewAccountInfoInDB(String accountName) {
        Account viewAccount;
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        
        try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNT_NAME = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0')");
            pstmt.setString(1, accountName);
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                viewAccount = new Account();
                viewAccount.setAccountName(resultSetObj.getString("ACCOUNT_NAME"));
                viewAccount.setAccountType(resultSetObj.getString("ACCOUNT_TYPE"));
                viewAccount.setNameHolder(viewAccount.getAccountName()); 
                viewAccount.setTypeHolder(viewAccount.getAccountType());
                sessionMapObj.put("accViewObj", viewAccount);
               }            
            }        
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return "/viewAccount.xhtml?faces-redirect=true";
     }
     
     public static List<String> viewAllAccountNamesInDB(){
         List<String> accountNameList = new ArrayList();
         try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT ACCOUNT_NAME FROM ACCOUNTS WHERE (IS_DELETED IS NULL OR IS_DELETED = '0') ");
            resultSetObj = pstmt.executeQuery();
            while( resultSetObj.next()){
                accountNameList.add(resultSetObj.getString("ACCOUNT_NAME"));
            }
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return accountNameList;
         
     }
     
     public static String updateAccountDetailsInDB(Account updateAccountObj, String oldName){
          try {
                        
                connObj = DataConnect.getConnection();
                pstmt = connObj.prepareStatement("UPDATE ACCOUNTS SET ACCOUNT_NAME = ?, ACCOUNT_TYPE = ? WHERE ACCOUNT_NAME= ?");
                pstmt.setString(1, updateAccountObj.getAccountName());
                pstmt.setString(2, updateAccountObj.getAccountType());
                pstmt.setString(3, oldName);
                pstmt.executeUpdate();
                
                PreparedStatement pst = connObj.prepareStatement("UPDATE RECORDS SET ACCOUNT_NAME = ? WHERE ACCOUNT_NAME = ?");
                pst.setString(1, updateAccountObj.getAccountName());
                pst.setString(2, oldName);
                pst.executeUpdate();
                
            } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
            } finally {    
                 DataConnect.close(connObj);
            }
        return "/viewAccount.xhtml?faces-redirect=true";
     }
     
     
     public static String createNewAccountInDB(Account newAccountObj){
         int saveResult = 0;
        try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("INSERT INTO ACCOUNTS (ACCOUNT_NAME, ACCOUNT_TYPE) VALUES (?, ?)");
            pstmt.setString(1, newAccountObj.getAccountName());
            pstmt.setString(2, newAccountObj.getAccountType());
            saveResult = pstmt.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
        } finally {    
                 DataConnect.close(connObj);
        }
        if(saveResult !=0){
        	FacesContext.getCurrentInstance().addMessage("accountForm:newAccName",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "The New Account was successfully created",
                    "The New Account was successfully created"));
            clearAccount(newAccountObj);
        } else {
        	FacesContext.getCurrentInstance().addMessage("accountForm:newAccName",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "There was a problem when creating the new Account",
                    "There was a problem when creating the new Account"));
        }
        return "createAccount.xhtml?faces=redirect=true";
     }
     public static void clearAccount(Account clearAcc){
    	 clearAcc.setAccountName(null);
    	 clearAcc.setAccountType("");
     }
     
     public static String deleteAccountInDB(String deleteAccName, int deleteEmp){
     	System.out.println("deleteAccountinDB() : Account Name:" + deleteAccName);
     	try{
     		connObj = DataConnect.getConnection();
     		pstmt = connObj.prepareStatement("UPDATE RECORDS SET IS_DELETED = '1', EMP_ALTER = ? WHERE ACCOUNT_NAME = ?" );
     		pstmt.setInt(1, deleteEmp);
     		pstmt.setString(2, deleteAccName);
     		pstmt.executeUpdate();
     		
     		PreparedStatement prepst = connObj.prepareStatement("UPDATE ACCOUNTS SET IS_DELETED = '1', EMP_ALTER = ? WHERE ACCOUNT_NAME = ?" );
     		prepst.setInt(1, deleteEmp);
     		prepst.setString(2, deleteAccName);
     		prepst.executeUpdate();
     	} catch (SQLException e) {
             System.out.println("Login error -->" + e.getMessage());        
     	} finally {    
     		DataConnect.close(connObj);
     	}
     	return "/accounts.xhtml?faces-redirect=true";
     }
     public static double getCurrentAccountBalance(String accountName){
    	 double recAmount;
    	 double finalAmount;
    	 boolean isCredit;
    	 double recBalance = 0.0;
    	 try{
    		 connObj = DataConnect.getConnection();
           	 stmtObj = connObj.createStatement();
           	 pstmt = connObj.prepareStatement("SELECT AMOUNT, IS_CREDIT FROM RECORDS WHERE ACCOUNT_NAME = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0') ORDER BY DATE DESC");
           	 pstmt.setString(1, accountName);
             resultSetObj = pstmt.executeQuery();
             while(resultSetObj.next()) {
                 recAmount = resultSetObj.getDouble("AMOUNT");
                 isCredit = resultSetObj.getBoolean("IS_CREDIT");
                 if(isCredit == true){
                	 finalAmount = recAmount * -1;
                	 
                 } else {
                	 finalAmount = recAmount;
                 }
                 
                 recBalance += finalAmount;
             }
             
         } catch (SQLException e) {
                     System.out.println("Login error -->" + e.getMessage());        
         } finally {    
           DataConnect.close(connObj);
         }
     return recBalance;
     }
     
     public static double getCurrentAccountBalance(String accountName, String startDate, String endDate){
    	 double recAmount;
    	 double finalAmount;
    	 boolean isCredit;
    	 double recBalance = 0.0;
    	 try{
    		 connObj = DataConnect.getConnection();
           	 stmtObj = connObj.createStatement();
           	 pstmt = connObj.prepareStatement("SELECT AMOUNT, IS_CREDIT FROM RECORDS WHERE ACCOUNT_NAME = ? AND DATE BETWEEN ? AND ? AND (IS_DELETED IS NULL OR IS_DELETED = '0') ORDER BY DATE DESC");
           	 pstmt.setString(1, accountName);
           	 pstmt.setString(2, startDate);
           	 pstmt.setString(3,  endDate);
             resultSetObj = pstmt.executeQuery();
             while(resultSetObj.next()) {
                 recAmount = resultSetObj.getDouble("AMOUNT");
                 isCredit = resultSetObj.getBoolean("IS_CREDIT");
                 if(isCredit == true){
                	 finalAmount = recAmount * -1;
                	 
                 } else {
                	 finalAmount = recAmount;
                 }
                 
                 recBalance += finalAmount;
             }
             
         } catch (SQLException e) {
            System.out.println("Login error balance -->" + e.getMessage());        
         } finally {
        	 DataConnect.close(connObj);
         }
     return recBalance;
     }
     
     public static List<Account> getCurrentSheet(String account, String startDate, String endDate){
    	 List<Account> sheetList = new ArrayList<>();
         double balance = 0.0;
         DecimalFormat df = new DecimalFormat("0.00");
         Connection conn = null;
         try {
             conn = DataConnect.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT ACCOUNT_NAME FROM ACCOUNTS WHERE ACCOUNT_TYPE = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0')");
             pst.setString(1, account);
              
             ResultSet rs = pst.executeQuery();
             if(rs != null) {
                while( rs.next()){
                 Account sheetObj = new Account();
                 sheetObj.setAccountName(rs.getString("ACCOUNT_NAME"));
                 balance = getCurrentAccountBalance(sheetObj.getAccountName(), startDate, endDate);
                 if(account.equals("Liability")||account.equals("Income")){
                	 balance = balance * -1;
                 }
                 sheetObj.setAccBalance(df.format(balance));
                 
                 sheetList.add(sheetObj);
                }
                 System.out.println("Total Records Fetched: " + sheetList.size());
                 
             }
          } catch (SQLException e) {
             System.out.println("Login error sheet -->" + e.getMessage());        
          } finally {    
              DataConnect.close(conn);
           }
         return sheetList;
     }
     public static double getLastSheetBalance(String accType){
    	 Date date = new Date();
      	 LocalDate today = date.toInstant().atZone(ZoneId.of( "America/Montreal" )).toLocalDate();
      	 int monthInt = today.getMonthValue();
      	 int yearInt = today.getYear();
    	 double recAmount;
    	 double finalAmount;
    	 boolean isCredit;
    	 double balance = 0.0;
    	 Connection connt = null;
    	 try{
    		 connt = DataConnect.getConnection();
           	 PreparedStatement ps = connObj.prepareStatement("SELECT AMOUNT, IS_CREDIT FROM RECORDS WHERE ACCOUNT_NAME = ? AND MONTH(DATE)  = ? AND YEAR(DATE) = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0') ORDER BY DATE DESC");
           	 ps.setString(1, accType);
           	 ps.setInt(2, monthInt - 1);
           	 ps.setInt(3,  yearInt);
             ResultSet result = ps.executeQuery();
             while(result.next()) {
                 recAmount = result.getDouble("AMOUNT");
                 isCredit = result.getBoolean("IS_CREDIT");
                 if(isCredit == true){
                	 finalAmount = recAmount * -1;
                	 
                 } else {
                	 finalAmount = recAmount;
                 }
                 
                 balance += finalAmount;
             }
    	
    	 } catch (SQLException e) {
    		 System.out.println("Login error sheet -->" + e.getMessage());        
    	 } finally {    
    		 DataConnect.close(connt);
    	 }
    	
         return balance;
     }
     public static List<Account> getLastSheet(String account){
    	List<Account> opList = new ArrayList();
     	 double balance = 0.0;
         DecimalFormat df = new DecimalFormat("0.00");
         Connection conn = null;
             	 
    	 Account netInc = new Account();
      	 netInc.setAccountName("Last Month Net Income");
      	 netInc.setAccBalance(df.format(DatabaseOperation.getLastSheet("Income")));
      	  opList.add(netInc);
         try {
             conn = DataConnect.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT ACCOUNT_NAME FROM ACCOUNTS WHERE ACCOUNT_TYPE = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0')");
             pst.setString(1, account);
              
             ResultSet rs = pst.executeQuery();
             if(rs != null) {
                while( rs.next()){
                 Account opObj = new Account();
                 opObj.setAccountName(rs.getString("ACCOUNT_NAME"));
                 
            		 
                     
                 balance = getLastSheetBalance(opObj.getAccountName());
                 if(balance < 0 && (account.equals("Liability")||account.equals("Income"))){
                	 balance = balance * -1;
                 }
                 opObj.setAccBalance(df.format(balance));
                 
                 opList.add(opObj);
                }
                 System.out.println("Total Records Fetched: " + opList.size());
                 
             }
          } catch (SQLException e) {
             System.out.println("Login error sheet -->" + e.getMessage());        
          } finally {    
              DataConnect.close(conn);
           }
         return opList;
     }
     
     //Record Database Operation
     public static List<Record> getAllRecordsFromDB(String startDate, String endDate){
    	 List<Record> allRecords = new ArrayList();
    	 try{
    		 connObj = DataConnect.getConnection();
           	 pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE DATE BETWEEN ? AND ? AND (IS_DELETED IS NULL OR IS_DELETED = '0') ORDER BY DATE DESC");
             pstmt.setString(1, startDate);
             pstmt.setString(2, endDate);
             resultSetObj = pstmt.executeQuery();
             while( resultSetObj.next()) {
                 Record allRecordObj = new Record();
                 allRecordObj.setRecordID(resultSetObj.getInt("RECORD_ID"));
                 allRecordObj.setRecordName(resultSetObj.getString("RECORD_NAME"));
                 allRecordObj.setRecordAmount(resultSetObj.getDouble("AMOUNT"));
                 
                 if(resultSetObj.getString("IS_CREDIT").equals("0")){
                	 allRecordObj.setRecordIsCredit("Debit");
                	 allRecordObj.setRecordDebitAmount(allRecordObj.getRecordAmount());
                	 allRecordObj.setRecordCreditAmount(0.0);
                 } else {
                	 allRecordObj.setRecordIsCredit("Credit");
                	 allRecordObj.setRecordCreditAmount(allRecordObj.getRecordAmount());
                	 allRecordObj.setRecordDebitAmount(0.0);
                 }
                 allRecordObj.setRecordDate(resultSetObj.getString("DATE"));
                 allRecordObj.setRecordInvNum(resultSetObj.getString("INVNUM"));
                 allRecordObj.setRecordTenantID(resultSetObj.getString("TENANT_ID"));
                 allRecords.add(allRecordObj);
             }
             
             System.out.println("Total Records Fetched: " + allRecords.size());
         } catch (SQLException e) {
                     System.out.println("Login error -->" + e.getMessage());        
         } finally {    
           DataConnect.close(connObj);
         }
     return allRecords;
     }
     
     public static List<Record> getAllRecordsFromDB(String accountName, String startDate, String endDate){
    	 List<Record> allAccountRecords = new ArrayList();
    	
    	 try{
    		 connObj = DataConnect.getConnection();
           	 stmtObj = connObj.createStatement();
           	 pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE ACCOUNT_NAME = ? AND DATE BETWEEN ? AND ? AND (IS_DELETED IS NULL OR IS_DELETED = '0') ORDER BY DATE DESC");
           	 pstmt.setString(1, accountName);
           	 pstmt.setString(2, startDate);
           	 pstmt.setString(3, endDate);
             resultSetObj = pstmt.executeQuery();
             while(resultSetObj.next()) {
                 Record allRecordObj = new Record();
                 allRecordObj.setRecordID(resultSetObj.getInt("RECORD_ID"));
                 allRecordObj.setRecordName(resultSetObj.getString("RECORD_NAME"));
                 allRecordObj.setRecordAmount(resultSetObj.getDouble("AMOUNT"));
                 
                 if(resultSetObj.getString("IS_CREDIT").equals("0")){
                	 allRecordObj.setRecordIsCredit("Debit");
                	 allRecordObj.setRecordDebitAmount(allRecordObj.getRecordAmount());
                	 allRecordObj.setRecordCreditAmount(0.0);
                	
                	 
                 } else {
                	 allRecordObj.setRecordIsCredit("Credit");
                	 allRecordObj.setRecordCreditAmount(allRecordObj.getRecordAmount());
                	 allRecordObj.setRecordDebitAmount(0.0);
                	 
                 }
                 allRecordObj.setRecordDate(resultSetObj.getString("DATE"));
                 allRecordObj.setRecordInvNum(resultSetObj.getString("INVNUM"));
                 allRecordObj.setRecordTenantID(resultSetObj.getString("TENANT_ID"));
                 
                 allAccountRecords.add(allRecordObj);
                 
             }
             
             System.out.println("Total Records Fetched: " + allAccountRecords.size());
            
         } catch (SQLException e) {
                     System.out.println("Login error -->" + e.getMessage());        
         } finally {    
           DataConnect.close(connObj);
         }
     return allAccountRecords;
     }
    
     
     public static List<Record> getAllRecordListFromDB(String searchRecCrit, String searchRecInfo, String recordAccount){
         List<Record> allRecordList = new ArrayList();
         
            try{
                connObj = DataConnect.getConnection();
                switch (searchRecCrit) {
                 case "Record Name":
                     pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE RECORD_NAME = ? AND ACCOUNT_NAME= ? AND (IS_DELETED IS NULL OR IS_DELETED = '0') ORDER BY DATE DESC");
                     pstmt.setString(1, searchRecInfo);
                     pstmt.setString(2, recordAccount);
                     break;
                 case "Record Date":
                     pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE DATE = ? AND ACCOUNT_NAME = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0') ORDER BY DATE DESC");
                     pstmt.setString(1, searchRecInfo);
                     pstmt.setString(2, recordAccount);
                     break;
                 default:
                     pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE ACCOUNT_NAME = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0') ORDER BY DATE DESC");
                     pstmt.setString(1, recordAccount);
                     break;
                }
                resultSetObj = pstmt.executeQuery();
                if(resultSetObj     != null) {
                while( resultSetObj.next()) {
                    Record viewRecordObj = new Record();
                    viewRecordObj.setRecordID(resultSetObj.getInt("RECORD_ID"));
                    viewRecordObj.setRecordName(resultSetObj.getString("RECORD_NAME"));
                    
                    viewRecordObj.setRecordAmount(resultSetObj.getDouble("AMOUNT"));
                    
                    if(resultSetObj.getString("IS_CREDIT").equals("0")){
                   	 viewRecordObj.setRecordIsCredit("Debit");
                   	 viewRecordObj.setRecordDebitAmount(viewRecordObj.getRecordAmount());
                   	 viewRecordObj.setRecordCreditAmount(0.0);
                    } else {
                   	 viewRecordObj.setRecordIsCredit("Credit");
                   	 viewRecordObj.setRecordCreditAmount(viewRecordObj.getRecordAmount());
                   	 viewRecordObj.setRecordDebitAmount(0.0);
                   	 
                    }
                    viewRecordObj.setRecordDate(resultSetObj.getString("DATE"));
                    viewRecordObj.setRecordInvNum(resultSetObj.getString("INVNUM"));
                    viewRecordObj.setRecordTenantID(resultSetObj.getString("TENANT_ID"));
                    allRecordList.add(viewRecordObj);
                }
                    System.out.println("Total Records Fetched: " + allRecordList.size());
                }
            } catch (SQLException e) {
                        System.out.println("Login error -->" + e.getMessage());        
            } finally {    
              DataConnect.close(connObj);
            }
        return allRecordList;
     }
     
     public static String viewIndivRecordInDB(int recordID){
        Record viewRecord;
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        
        try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE RECORD_ID = ?");
            pstmt.setInt(1, recordID);
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                viewRecord = new Record();
                viewRecord.setRecordID(resultSetObj.getInt("RECORD_ID"));
                viewRecord.setRecordName(resultSetObj.getString("RECORD_NAME"));
                viewRecord.setRecordAmount(resultSetObj.getDouble("AMOUNT"));
                if(resultSetObj.getString("IS_CREDIT").equals("0")){
                    viewRecord.setRecordIsCredit("Debit");
                } else {
                    viewRecord.setRecordIsCredit("Credit");
                }
                viewRecord.setRecordDate(resultSetObj.getString("DATE"));
                viewRecord.setRecordInvNum(resultSetObj.getString("INVNUM"));
                viewRecord.setRecordTenantID(resultSetObj.getString("TENANT_ID"));
                viewRecord.setRecordAccount(resultSetObj.getString("ACCOUNT_NAME"));
                sessionMapObj.put("recViewObj", viewRecord);
                
               }                
            }
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return "/viewRecord.xhtml?faces-redirect=true";
     }
     
     public static String updateRecordDetailsInDB(Record updateRecObj, int alterEmp){
    	 String navigation = "";
    	 Record newRec= new Record();
         try{       
                connObj = DataConnect.getConnection();
                pstmt = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, TENANT_ID, ACCOUNT_NAME) VALUES (?, ?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, updateRecObj.getRecordName());
                pstmt.setDouble(2, updateRecObj.getRecordAmount());
                pstmt.setBoolean(3, updateRecObj.getRecCred());
                pstmt.setString(4, updateRecObj.getRecordDate());
                pstmt.setString(5, updateRecObj.getRecordInvNum());
                pstmt.setString(6, updateRecObj.getRecordTenantID());
                pstmt.setString(7, updateRecObj.getRecordAccount());
                pstmt.executeUpdate();
                
                PreparedStatement pmt = connObj.prepareStatement("SELECT TOP 1 RECORD_ID FROM RECORDS ORDER BY RECORD_ID DESC");
                resultSetObj = pmt.executeQuery();
                resultSetObj.next();
                newRec.setRecordID(resultSetObj.getInt("RECORD_ID"));
                
                PreparedStatement pst = connObj.prepareStatement("UPDATE RECORDS SET IS_DELETED = '1', REASON = ?, EMP_ALTER = ?, NEW_ID = ? WHERE RECORD_ID = ?");
                pst.setString(1, updateRecObj.getRecordReason());
                pst.setInt(2, alterEmp);
                pst.setInt(3, newRec.getRecordID());
                pst.setInt(4, updateRecObj.getRecordID());
                pst.executeUpdate();
                
            } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
            } finally {    
                 DataConnect.close(connObj);
            }
         navigation = viewIndivRecordInDB(newRec.getRecordID());
        return navigation;
     }
    
     public static String createNewRecordInDB(Record recNewObj){
         int saveResult = 0;
        
        try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, TENANT_ID, ACCOUNT_NAME) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, recNewObj.getRecordName());
            pstmt.setDouble(2, recNewObj.getRecordAmount());
            pstmt.setBoolean(3, recNewObj.getRecCred()); 
            pstmt.setString(4, recNewObj.getRecordDate());
            pstmt.setString(5, recNewObj.getRecordInvNum());
            pstmt.setString(6, recNewObj.getRecordTenantID());
            pstmt.setString(7, recNewObj.getRecordAccount());
            saveResult = pstmt.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
        } finally {    
                 DataConnect.close(connObj);
        }
        
        if(saveResult !=0){
        	FacesContext.getCurrentInstance().addMessage("recordForm:recNewName",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "The New Record was successfully created",
                    "The New Record was successfully created"));
            clearRecord(recNewObj);
        } else {
        	FacesContext.getCurrentInstance().addMessage("recordForm:recNewName",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "There was a problem creating the new Record",
                    "There was a problem creating the new Record"));
        }
        return"createRecord.xhtml?faces=redirect=true";
     }
     public static void clearRecord(Record clearRec){
    	 clearRec.setRecordName(null);
    	 clearRec.setEntryAmount(0);
    	 clearRec.setRecordIsCredit("Credit");
    	 clearRec.setRecordDate(null);
    	 clearRec.setRecordInvNum(null);
    	 clearRec.setRecordTenantID(null);
    	 clearRec.setRecordAccount("");
     }
     
    public static List<Record> getTenantAccRecordsInDB(int tenantID){
    	List<Record> tenantRecordsList = new ArrayList();
    	
    	 try{
             connObj = DataConnect.getConnection();
             pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE TENANT_ID = ? AND (IS_DELETED IS NULL OR IS_DELETED = '0')");
             pstmt.setInt(1, tenantID);
             resultSetObj = pstmt.executeQuery();
             if(resultSetObj     != null) {
             while( resultSetObj.next()) {
                 Record tenAllRecordsObj = new Record();
                 tenAllRecordsObj.setRecordID(resultSetObj.getInt("RECORD_ID"));
                 tenAllRecordsObj.setRecordName(resultSetObj.getString("RECORD_NAME"));
                 double amount = resultSetObj.getDouble("AMOUNT");
                 boolean isCred = resultSetObj.getBoolean("IS_CREDIT"); 
                 tenAllRecordsObj.setRecordDate(resultSetObj.getString("DATE"));
                 tenAllRecordsObj.setRecordInvNum(resultSetObj.getString("INVNUM"));
                 tenAllRecordsObj.setRecordTenantID(resultSetObj.getString("TENANT_ID"));
                 tenAllRecordsObj.setRecordAccount(resultSetObj.getString("ACCOUNT_NAME"));
            		 if(isCred == true){
            			 tenAllRecordsObj.setRecordAmount(amount * -1);
            		 } else {
            			 tenAllRecordsObj.setRecordAmount(amount);
            		 }
            	                   
                 tenantRecordsList.add(tenAllRecordsObj);
             }
                 System.out.println("Total Records Fetched: " + tenantRecordsList.size());
             }
         } catch (SQLException e) {
                     System.out.println("Login error -->" + e.getMessage());        
         } finally {    
           DataConnect.close(connObj);
         }
    	 
     return  tenantRecordsList;
    }
    
    public static String deleteRecordInDB(int recordID, int deleteEmp){
    	System.out.println("deleteRecordinDB() : Record ID:" + recordID);
    	
    	try{
    		connObj = DataConnect.getConnection();
    		pstmt = connObj.prepareStatement("UPDATE RECORDS SET IS_DELETED = '1', EMP_ALTER = ? WHERE RECORD_ID = ?" );
    		pstmt.setInt(1, deleteEmp);
    		pstmt.setInt(2, recordID);
    		pstmt.executeUpdate();
    	} catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
    	} finally {    
    		DataConnect.close(connObj);
    	}
    	return "/viewAccount.xhtml?faces-redirect=true";
    }
  
    
    public static String postRentToARInDB(){
    	List<Unit> unitRentList = new ArrayList();
    	Date date = new Date();
    	LocalDate today = date.toInstant().atZone(ZoneId.of( "America/Montreal" )).toLocalDate();
    	System.out.println( "today : " + today );
    	int monthInt = today.getMonthValue();
    	int yearInt = today.getYear();
    	java.text.DecimalFormat nft = new java.text.DecimalFormat("00");
    	String month = nft.format(monthInt);
    	System.out.println(month);
    	System.out.println(yearInt);
    	
    	try{
    		connObj = DataConnect.getConnection();
    		pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE ACCOUNT_NAME = 'ACCOUNTS RECEIVABLE' AND RECORD_NAME = 'Tenant Rent' AND YEAR(DATE) = ? AND MONTH(DATE) = ? "
    				+ "AND (IS_DELETED IS NULL OR IS_DELETED = '0')" );
    		pstmt.setInt(1, yearInt);
    		pstmt.setString(2, month);
    		resultSetObj = pstmt.executeQuery();
    		if(!resultSetObj.next()){
    			PreparedStatement prepst = connObj.prepareStatement("SELECT UNIT_ID, RENT, TENANT_ID FROM UNITS WHERE TENANT_ID IS NOT NULL");
    			ResultSet rs = prepst.executeQuery();
    			while(rs.next()){
    				Unit rentUnitObj = new Unit();
    				rentUnitObj.setUnitID(rs.getInt("UNIT_ID"));
    				rentUnitObj.setUnitRent(rs.getString("RENT"));
    				rentUnitObj.setUnitTenantID(rs.getString("TENANT_ID"));
    				unitRentList.add(rentUnitObj);
    				
    				PreparedStatement pst = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM,  ACCOUNT_NAME)"
    						+ "VALUES ('Tenant Rent', ?, '0', ?, ?, 'ACCOUNTS RECEIVABLE')");
    				pst.setString(1, rentUnitObj.getUnitRent());
    				pst.setObject(2, today);
    				pst.setString(3, "UNIT"+rentUnitObj.getUnitID()+":RENT"+month+yearInt);    				
    				pst.executeUpdate();
    				
    				PreparedStatement prep = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, TENANT_ID, ACCOUNT_NAME)"
    						+ "VALUES ('Tenant Rent Expected', ?, '1', ?, ?, ?, 'RENTAL INCOME')");
    					prep.setString(1, rentUnitObj.getUnitRent());
    	    			prep.setObject(2, today);
    	    			prep.setString(3, "UNIT"+rentUnitObj.getUnitID()+":EXPECTED"+month+yearInt);
    	    			prep.setString(4, rentUnitObj.getUnitTenantID());
    	    			prep.executeUpdate();
    			}
    			System.out.println("Rent posted");
    		} else {
    			System.out.println("Rent already posted");
    			FacesContext.getCurrentInstance().addMessage("actionForm:rentButton",
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Rent was already posted for this month and did not post again",
                        "Rent was already posted for this month and did not post again"));
    			return "actionCenter";
    		}
    	} catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
    	} finally {    
    		DataConnect.close(connObj);
    	} 
    	FacesContext.getCurrentInstance().addMessage("actionForm:rentButton",
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Rent was correctly posted for this month",
                "Rent was successfully posted for this month"));
    	return "actionCenter";
    	
    	
    }
    
    public static String postLateFeeToARInDB(){
    	double lateFee = 150;
    	Date date = new Date();
    	LocalDate today = date.toInstant().atZone(ZoneId.of( "America/Montreal" )).toLocalDate();
    	System.out.println( "today : " + today );
    	int monthInt = today.getMonthValue();
    	int yearInt = today.getYear();
    	java.text.DecimalFormat nft = new java.text.DecimalFormat("00");
    	String month = nft.format(monthInt);
    	
    	try{
    		connObj = DataConnect.getConnection();
    		pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE ACCOUNT_NAME = 'ACCOUNTS RECEIVABLE' AND RECORD_NAME = 'Tenant Late Fee' AND YEAR(DATE) = ? AND MONTH(DATE) = ? "
    				+ "AND (IS_DELETED IS NULL OR IS_DELETED = '0')" );
    		pstmt.setInt(1, yearInt);
    		pstmt.setString(2, month);
    		resultSetObj = pstmt.executeQuery();
    		if(!resultSetObj.next()){
    			PreparedStatement prepst = connObj.prepareStatement("SELECT UNIT_ID, TENANT_ID FROM UNITS WHERE TENANT_ID IS NOT NULL");
    			ResultSet rs = prepst.executeQuery();
    			if(rs != null){
    				while(rs.next()){
    					Unit lateUnitObj = new Unit();
    					lateUnitObj.setUnitID(rs.getInt("UNIT_ID"));
    					lateUnitObj.setUnitTenantID(rs.getString("TENANT_ID"));
    					int tenantIDSearch = Integer.parseInt(lateUnitObj.getUnitTenantID());
    					double searchBalance = getTenantRecordBalanceInDB(tenantIDSearch);
    				
    					if(searchBalance < 0.0){
    				
    						PreparedStatement pst = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM,  ACCOUNT_NAME)"
    								+ "VALUES ('Tenant Late Fee', ?, '0', ?, ?, 'ACCOUNTS RECEIVABLE')");
    						pst.setDouble(1, lateFee);
    						pst.setObject(2, today);
    						pst.setString(3, "UNIT"+lateUnitObj.getUnitID()+":LateFee" + month+yearInt);
    						pst.executeUpdate();
    						
    						PreparedStatement prep = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, TENANT_ID, ACCOUNT_NAME)"
    	    						+ "VALUES ('Tenant Fee Expected', ?, '1', ?, ?, ?, 'RENTAL INCOME')");
    	    				prep.setDouble(1, lateFee);
    	    	    		prep.setObject(2, today);
    	    	    		prep.setString(3, "UNIT"+lateUnitObj.getUnitID()+":FEE"+month+yearInt);
    	    	    		prep.setString(4, lateUnitObj.getUnitTenantID());
    	    	    		prep.executeUpdate();
    					}
    				}
    			}
    			System.out.println("late fee posted");
    		} else {
    			System.out.println("late fee already posted");
    			FacesContext.getCurrentInstance().addMessage("actionForm:lateFeeBtn",
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Late Fees were already posted for this month and did not post again",
                        "Late Fees were already posted for this month and did not post again"));
    			return "actionCenter";
    		}
    	} catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
    	} finally {    
    		DataConnect.close(connObj);
    	} 
    	FacesContext.getCurrentInstance().addMessage("actionForm:lateFeeBtn",
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Late Fees were correctly posted for this month",
                "Late Fees were successfully posted for this month"));
    	return "actionCenter";
    }
    
    
    
   //Maintenance Database Operation
     public static List<Maintenance> getMaintenanceSearchListFromDB(String searchBuild, String searchType){
        List<Maintenance> mainSearchList = new ArrayList<>();
       
        try {
            connObj = DataConnect.getConnection();
            //job Type is not required
            if(searchType.equals("")){
                pstmt = connObj.prepareStatement("SELECT MAINTENANCE.REQUEST_ID, MAINTENANCE.TENANT_ID, MAINTENANCE.JOBTYPE, MAINTENANCE.JOBDESC, MAINTENANCE.DATEREQ, "
                		+ "MAINTENANCE.START_EMP, UNITS.BUILDING, UNITS.APTNUM FROM MAINTENANCE LEFT OUTER JOIN UNITS ON MAINTENANCE.TENANT_ID = UNITS.TENANT_ID WHERE UNITS.BUILDING = ? AND MAINTENANCE.DONE_EMP IS NULL"); 
                pstmt.setString(1, searchBuild);
            } else {
                pstmt = connObj.prepareStatement("SELECT MAINTENANCE.REQUEST_ID, MAINTENANCE.TENANT_ID, MAINTENANCE.JOBTYPE, MAINTENANCE.JOBDESC, MAINTENANCE.DATEREQ, "
                		+ "MAINTENANCE.START_EMP, UNITS.BUILDING, UNITS.APTNUM FROM MAINTENANCE LEFT OUTER JOIN UNITS ON MAINTENANCE.TENANT_ID = UNITS.TENANT_ID WHERE UNITS.BUILDING = ? AND MAINTENANCE.JOBTYPE = ? AND MAINTENANCE.DONE_EMP IS NULL"); 
                pstmt.setString(1, searchBuild);
                pstmt.setString(2, searchType);
            }
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                Maintenance mainSearchObj = new Maintenance();
                mainSearchObj.setRequestID(resultSetObj.getInt("REQUEST_ID"));
                mainSearchObj.setTenantID(resultSetObj.getInt("TENANT_ID"));
                mainSearchObj.setJobType(resultSetObj.getString("JOBTYPE"));
                mainSearchObj.setJobDesc(resultSetObj.getString("JOBDESC"));
                mainSearchObj.setDateReq(resultSetObj.getString("DATEREQ"));
                mainSearchObj.setStartEmp(resultSetObj.getInt("START_EMP"));
                mainSearchObj.setBuilding(resultSetObj.getString("BUILDING"));
                mainSearchObj.setAptNum(resultSetObj.getString("APTNUM"));
                
                mainSearchList.add(mainSearchObj);
               }
                System.out.println("Total Records Fetched: " + mainSearchList.size());
               }
        } catch (SQLException e) {
            System.out.println("Login error searchList-->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return mainSearchList;
    }
    
    public static String startMainRequestInDB(int requestID, int employeeID){
    	Date date = new Date();
    	LocalDate today = date.toInstant().atZone(ZoneId.of( "America/Montreal" )).toLocalDate();
    	System.out.println( "today : " + today );
    	
    	try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("UPDATE MAINTENANCE SET START_DATE = ?, START_EMP = ? WHERE REQUEST_ID = ?");
            pstmt.setObject(1, today);
            pstmt.setInt(2, employeeID);
            pstmt.setInt(3, requestID);                                      
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
             DataConnect.close(connObj);
        }
    	return "/maintenanceRequests.xhtml?faces-redirect=true";
    }
    
    public static String endMainRequestInDB(int requestID, int employeeID){
    	Date date = new Date();
    	LocalDate today = date.toInstant().atZone(ZoneId.of( "America/Montreal" )).toLocalDate();
    	System.out.println( "today : " + today );
    	
    	try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("UPDATE MAINTENANCE SET DONE_DATE = ?, DONE_EMP = ? WHERE REQUEST_ID = ?");
            pstmt.setObject(1, today);
            pstmt.setInt(2, employeeID);
            pstmt.setInt(3, requestID);                                  
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
             DataConnect.close(connObj);
        }
    	return "/maintenanceRequests.xhtml?faces-redirect=true";
    }
    
    public static List<Maintenance> getCurrentEmpMainListInDB(int employeeID){
    	List<Maintenance> employeeMainList = new ArrayList();
    	
   	 try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT MAINTENANCE.REQUEST_ID, MAINTENANCE.TENANT_ID, MAINTENANCE.JOBTYPE, MAINTENANCE.JOBDESC, MAINTENANCE.DATEREQ, "
                		+ "MAINTENANCE.START_DATE, MAINTENANCE.START_EMP, UNITS.BUILDING, UNITS.APTNUM FROM MAINTENANCE LEFT OUTER JOIN UNITS ON MAINTENANCE.TENANT_ID = UNITS.TENANT_ID WHERE  MAINTENANCE.START_EMP = ? AND MAINTENANCE.DONE_EMP IS NULL");
            pstmt.setInt(1, employeeID);
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj     != null) {
            while( resultSetObj.next()) {
                Maintenance empCurrMainObj = new Maintenance();
                empCurrMainObj.setRequestID(resultSetObj.getInt("REQUEST_ID"));
                empCurrMainObj.setTenantID(resultSetObj.getInt("TENANT_ID"));
                empCurrMainObj.setJobType(resultSetObj.getString("JOBTYPE"));
                empCurrMainObj.setJobDesc(resultSetObj.getString("JOBDESC"));
                empCurrMainObj.setDateReq(resultSetObj.getString("DATEREQ"));
                empCurrMainObj.setStartDate(resultSetObj.getString("START_DATE"));
                empCurrMainObj.setStartEmp(resultSetObj.getInt("START_EMP"));
                empCurrMainObj.setBuilding(resultSetObj.getString("BUILDING"));
                empCurrMainObj.setAptNum(resultSetObj.getString("APTNUM"));
                employeeMainList.add(empCurrMainObj);
            }
                System.out.println("Total Records Fetched: " + employeeMainList.size());
            }
        } catch (SQLException e) {
                    System.out.println("Login error currentList -->" + e.getMessage());        
        } finally {    
          DataConnect.close(connObj);
        }
   	 
    return  employeeMainList;
    }
    
    public static List<Maintenance> getDoneEmpMainListInDB(int employeeID){
    	List<Maintenance> employeeDoneList = new ArrayList();
    	
   	 try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT MAINTENANCE.REQUEST_ID, MAINTENANCE.TENANT_ID, MAINTENANCE.JOBTYPE, MAINTENANCE.JOBDESC, "
                		+ "MAINTENANCE.DONE_DATE, MAINTENANCE.DONE_EMP, UNITS.BUILDING, UNITS.APTNUM FROM MAINTENANCE LEFT OUTER JOIN UNITS ON MAINTENANCE.TENANT_ID = UNITS.TENANT_ID WHERE  MAINTENANCE.DONE_EMP = ?");
            pstmt.setInt(1, employeeID);
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
            while( resultSetObj.next()) {
                Maintenance empDoneMainObj = new Maintenance();
                empDoneMainObj.setRequestID(resultSetObj.getInt("REQUEST_ID"));
                empDoneMainObj.setTenantID(resultSetObj.getInt("TENANT_ID"));
                empDoneMainObj.setJobType(resultSetObj.getString("JOBTYPE"));
                empDoneMainObj.setJobDesc(resultSetObj.getString("JOBDESC"));
                empDoneMainObj.setDoneDate(resultSetObj.getString("DONE_DATE"));
                empDoneMainObj.setDoneEmp(resultSetObj.getInt("DONE_EMP"));
                PreparedStatement prepst = connObj.prepareStatement("SELECT * FROM UNITS WHERE TENANT_ID = ?"); 
                prepst.setInt(1, empDoneMainObj.getTenantID());
                ResultSet rset = prepst.executeQuery();
                    while(rset.next()){
                    	empDoneMainObj.setBuilding(resultSetObj.getString("BUILDING"));
                    	empDoneMainObj.setAptNum(resultSetObj.getString("APTNUM"));
                    }
                    employeeDoneList.add(empDoneMainObj);
            }
                System.out.println("Total Records Fetched: " + employeeDoneList.size());
            }
        } catch (SQLException e) {
                    System.out.println("Login error doneList -->" + e.getMessage());        
        } finally {    
          DataConnect.close(connObj);
        }
    return  employeeDoneList;
    }
    
    public static String removeMainRequestInDB(int requestID, int employeeID){
    	try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("UPDATE MAINTENANCE SET START_DATE = NULL, START_EMP = NULL WHERE REQUEST_ID = ?");
            pstmt.setInt(1, requestID);                                        
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
             DataConnect.close(connObj);
        }
    	return "/maintenanceRequests.xhtml?faces-redirect=true";
    }
    
    public static String createNewRequestInDB(Maintenance newMaintObj){
   	 int saveResult = 0;
        try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("INSERT INTO MAINTENANCE (TENANT_ID, JOBTYPE, JOBDESC, DATEREQ) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, newMaintObj.getTenantID());
            pstmt.setString(2, newMaintObj.getJobType());
            pstmt.setString(3, newMaintObj.getJobDesc()); 
            pstmt.setString(4, newMaintObj.getDateReq()); 
            saveResult = pstmt.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
        } finally {    
                 DataConnect.close(connObj);
        }
        
        if(saveResult !=0){
        	FacesContext.getCurrentInstance().addMessage("newReqForm:newJobType",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "The New Request was successfully created",
                    "The New Request was successfully created"));
        	clearMaintenance(newMaintObj);
        } else {
        	FacesContext.getCurrentInstance().addMessage("newReqForm:newJobType",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "There was a problem creating the new Request",
                    "There was a problem creating the new Request. Make sure you have the correct Tenant ID"));
        }
        return"createMaintenance.xhtml?faces=redirect=true";
     }
    public static void clearMaintenance(Maintenance clearMain){
    	clearMain.setJobType("");
    	clearMain.setJobDesc(null);
    	clearMain.setDateReq(null);
    	clearMain.setTenantID(0);
    }
    
  //Contact Database Operations
    public static List<Contact> getContactListFromDB(){
    	List<Contact> contactList = new ArrayList();
    	try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM CONTACT WHERE DATE_CONT IS NULL");
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
            while( resultSetObj.next()) {
                Contact contactObj = new Contact();
                contactObj.setContactID(resultSetObj.getInt("CONTACT_ID"));
                contactObj.setConFirstName(resultSetObj.getString("FIRSTNAME"));
                contactObj.setConLastName(resultSetObj.getString("LASTNAME"));
                contactObj.setConEmail(resultSetObj.getString("EMAIL"));
                contactObj.setConPhone(resultSetObj.getString("PHONE"));
                contactObj.setConMessages(resultSetObj.getString("MESSAGES"));
                contactList.add(contactObj);
            }
                System.out.println("Total Records Fetched: " + contactList.size());
            }
        } catch (SQLException e) {
                    System.out.println("Login error doneList -->" + e.getMessage());        
        } finally {    
          DataConnect.close(connObj);
        }
    	return contactList;
    }
    
    public static String endContactRequestInDB(int endContID, int endEmpID){
    	Date date = new Date();
    	LocalDate today = date.toInstant().atZone(ZoneId.of( "America/Montreal" )).toLocalDate();
    	System.out.println( "today : " + today );
    	
    	try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("UPDATE CONTACT SET EMPID = ?, DATE_CONT = ? WHERE CONTACT_ID = ?");
            pstmt.setInt(1, endEmpID);
            pstmt.setObject(2, today);
            pstmt.setInt(3, endContID);                                    
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
             DataConnect.close(connObj);
        }
    	return "/contactReview.xhtml?faces-redirect=true";
    }
    
    
    //Inventory methods
    public static String createNewInventoryInDB(Inventory newInvObj){
	    int saveResult = 0;
	    if(newInvObj.getInvItem().equals("Other")){
	    	newInvObj.setInvItem(newInvObj.getAddItem());
	    }
	    try{
	        connObj = DataConnect.getConnection();
	        pstmt = connObj.prepareStatement("INSERT INTO INVENTORY (ITEM_TYPE, COST, PCH_DATE, ITEM_DESC, IS_PAID) VALUES (?, ?, ?, ?, ?)");
	        pstmt.setString(1, newInvObj.getInvItem());
	        pstmt.setDouble(2, newInvObj.getItemCost());
	        pstmt.setString(3, newInvObj.getPurchDate());
	        pstmt.setString(4, newInvObj.getItemDesc());
	        pstmt.setBoolean(5, newInvObj.getIsPaid());
	        saveResult = pstmt.executeUpdate();
	        
	        
	        PreparedStatement pst = connObj.prepareStatement("SELECT TOP 1 ASSET_NUM FROM INVENTORY ORDER BY ASSET_NUM DESC");
	        resultSetObj = pst.executeQuery();
	        if(resultSetObj.next()){
	        	newInvObj.setAssetNum(resultSetObj.getInt("ASSET_NUM"));
	        	
		        PreparedStatement prep = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, ACCOUNT_NAME) VALUES ('OFFICE INVENTORY', ?, '0', ?, ?, 'FIXED ASSETS')");
		        prep.setDouble(1, newInvObj.getItemCost());
		        prep.setString(2, newInvObj.getPurchDate());
		        prep.setString(3, "ASSET "+newInvObj.getAssetNum());
		        prep.executeUpdate();
		        
		        PreparedStatement prepst = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, ACCOUNT_NAME) VALUES ('OFFICE INV PURCH', ?, '1', ?, ?, 'ACCOUNTS PAYABLE')");
		        prepst.setDouble(1, newInvObj.getItemCost());
		        prepst.setString(2, newInvObj.getPurchDate());
		        prepst.setString(3, "ASSET "+newInvObj.getAssetNum()+" PURCH");
		        prepst.executeUpdate();
		        System.out.println("Got Here TOO");
		        if(newInvObj.getIsPaid()){
		        	PreparedStatement preps = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, ACCOUNT_NAME) VALUES ('OFFICE INV PAY', ?, '0', ?, ?, 'ACCOUNTS PAYABLE')");
		        	preps.setDouble(1, newInvObj.getItemCost());
		        	preps.setString(2, newInvObj.getPurchDate());
		        	preps.setString(3, "ASSET "+newInvObj.getAssetNum()+" PAY");
		        	preps.executeUpdate();
		        	
		        	PreparedStatement pres = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, ACCOUNT_NAME) VALUES ('OFFICE INV PAID', ?, '1', ?, ?, 'BANK ACCOUNT')");
		        	pres.setDouble(1, newInvObj.getItemCost());
		        	pres.setString(2, newInvObj.getPurchDate());
		        	pres.setString(3, "ASSET "+newInvObj.getAssetNum()+" PAID");
		        	pres.executeUpdate();
		        }
	        } else {
	        	System.out.println("No resultset");
	        }
	        
	    } catch (SQLException e) {
	            System.out.println("Login error -->" + e.getMessage());        
	    } finally {    
	             DataConnect.close(connObj);
	    }
	    
	    if(saveResult !=0){
	    	FacesContext.getCurrentInstance().addMessage("inventForm:newItem",
	                new FacesMessage(FacesMessage.SEVERITY_WARN, "The New Inventory was successfully created. Assigned Asset Number: " + newInvObj.getAssetNum(),
	                "The New Inventory was successfully created. Assigned Asset Number:  "+ newInvObj.getAssetNum()));
	        clearInventory(newInvObj);
	    } else {
	    	FacesContext.getCurrentInstance().addMessage("inventForm:newItem",
	                new FacesMessage(FacesMessage.SEVERITY_WARN, "There was a problem creating the new Inventory",
	                "There was a problem creating the new Inventory"));
	    }
	    return"inventory.xhtml?faces=redirect=true";
	 }
    public static void clearInventory(Inventory clearInv){
    	clearInv.setInvItem("");
    	clearInv.setAddItem(null);
    	clearInv.setItemCostEntry(0);
    	clearInv.setPurchDate(null);
    	clearInv.setItemDesc(null);
    	clearInv.setInvPaid("Yes");
    }
    public static List<String> viewAllItemTypesInDB(){
    	List<String> typeList = new ArrayList();
        try{
           connObj = DataConnect.getConnection();
           pstmt = connObj.prepareStatement("SELECT DISTINCT ITEM_TYPE FROM INVENTORY");
           resultSetObj = pstmt.executeQuery();
           while( resultSetObj.next()){
               typeList.add(resultSetObj.getString("ITEM_TYPE"));
           }
       } catch (SQLException e) {
           System.out.println("Login error -->" + e.getMessage());        
       } finally {    
             DataConnect.close(connObj);
       }
       return typeList;
    }
    
    
    public static List<Inventory> viewItemsQuantityinDB(){
    	List<Inventory> invList = new ArrayList();
    	int quantity = 0;
    	try{
    		connObj = DataConnect.getConnection();
    		pstmt = connObj.prepareStatement("SELECT DISTINCT ITEM_TYPE FROM INVENTORY");
    		resultSetObj = pstmt.executeQuery();
    		while(resultSetObj.next()){
    			Inventory invListObj = new Inventory();
    			String item = resultSetObj.getString("ITEM_TYPE");
    			invListObj.setItemType(item);
    			PreparedStatement pst = connObj.prepareStatement("SELECT COUNT(*) as 'QUANTITY' FROM INVENTORY WHERE ITEM_TYPE = ?");
    	        pst.setString(1, item);
    	        ResultSet result = pst.executeQuery();
    	          while(result.next()){
    	        	  	System.out.println(item);
    	        	  	invListObj.setQuantity(result.getInt("QUANTITY"));
    	          }
    			invList.add(invListObj);
    		}
    	} catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return invList;
    }
    
    public static String deleteInventoryInDB(int delAssetNum){
    	System.out.println("delete Inventory Asset Number:" + delAssetNum);
    	try{
    		connObj = DataConnect.getConnection();
    		    		
    		pstmt = connObj.prepareStatement("DELETE FROM INVENTORY WHERE ASSET_NUM = ?" );
    		pstmt.setInt(1, delAssetNum);
    		pstmt.executeUpdate();
    		
    	} catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
    	} finally {    
    		DataConnect.close(connObj);
    	}
    	return "/searchInventory.xhtml?faces-redirect=true";
    }
    public static List<Inventory> getInventoryListInDB(String searchCrit, String searchInf, String searchType){
    	List<Inventory> searchInvList = new ArrayList();
    	String query = "";
    	switch (searchCrit) {
        	case "Asset Number":
        		query = "SELECT * FROM INVENTORY WHERE ASSET_NUM = ?";
        		break;
        	case "Item Type":
        		query = "SELECT * FROM INVENTORY WHERE ITEM_TYPE = ?";
        		searchInf = searchType;
        		break;
        
    	}
    	try{
    		connObj = DataConnect.getConnection();
    		pstmt = connObj.prepareStatement(query);
    		pstmt.setString(1, searchInf);
    		resultSetObj = pstmt.executeQuery();
    		while(resultSetObj.next()){
    			Inventory invObj = new Inventory();
    			invObj.setAssetNum(resultSetObj.getInt("ASSET_NUM"));
    			invObj.setItemType(resultSetObj.getString("ITEM_TYPE"));
    			invObj.setItemCost(resultSetObj.getDouble("COST"));
    			invObj.setPurchDate(resultSetObj.getString("PCH_DATE"));
    			invObj.setItemDesc(resultSetObj.getString("ITEM_DESC"));
    			
    			searchInvList.add(invObj);
    		}
    	} catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
    	return searchInvList;
    }
}
