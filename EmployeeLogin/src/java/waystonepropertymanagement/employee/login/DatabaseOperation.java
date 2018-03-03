package waystonepropertymanagement.employee.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import waystonepropertymanagement.employee.login.Employee;
/**
 *
 * @author Katie
 */
public class DatabaseOperation {
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
    
    
    public static boolean empValidate(String email, String password) {
        
        
        try {
            
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("Select email, password FROM employee_login WHERE email = ? and password = ?");
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                //result found, means valid inputs
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());
            return false;
        
        } finally {
                            
              DataConnect.close(connObj);
                       
        }
        return false;
    }
    
    public static List<Employee> getEmployeeListFromDB(String username){
       Employee empRecord = null;
       List<Employee> empList = new ArrayList();
       Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();        
        try {
            
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM employee_accounts WHERE email = ?");
            pstmt.setString(1, username);
            
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
                resultSetObj.next();
                empRecord = new Employee();
                empRecord.setEmail(resultSetObj.getString("email"));
                empRecord.setEmployeeID(resultSetObj.getInt("employee_id"));
                empRecord.setFirstName(resultSetObj.getString("firstName"));
                empRecord.setLastName(resultSetObj.getString("lastName"));
                empRecord.setMiddleInit(resultSetObj.getString("middleInt"));
                empRecord.setAddress(resultSetObj.getString("address"));
                empRecord.setCity(resultSetObj.getString("city"));
                empRecord.setState(resultSetObj.getString("state"));
                empRecord.setZipcode(resultSetObj.getString("zipcode"));
                empRecord.setPhone(resultSetObj.getString("phone"));
                empRecord.setDOB(resultSetObj.getString("dob"));
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
    
    public static String updateEmployeeDetailsInDB(Employee updateEmployeeObj){
        try{
                        
                connObj = DataConnect.getConnection();
                pstmt = connObj.prepareStatement("UPDATE employee_accounts SET firstName = ?, lastName = ?, middleInt = ?, "
                    + "address = ?, city = ?, state = ?, zipcode = ?, phone = ?, dob = ? WHERE email = ?");
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
            
                pstmt.executeQuery();
                
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
            pstmt = connObj.prepareStatement("UPDATE employee_login SET password = ? WHERE email = ?");
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
    
    
    
    public static List<Tenant> getTenantListFromDB(String searchCrit, String searchInfo){
       List<Tenant> tenList = new ArrayList<>();
        String query;
        
        switch (searchCrit) {
            case "Tenant ID":
                query = ("SELECT * FROM ten_accounts WHERE tenant_id = ?");
                break;
            case "Last Name":
                query = ("SELECT * FROM ten_accounts WHERE lastName = ?");
                break;
            default:
                query = ("SELECT * FROM ten_accounts WHERE apt_num = ?");
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
                tenObj.setEmail(resultSetObj.getString("email"));
                tenObj.setTenantID(resultSetObj.getInt("tenant_id"));
                tenObj.setFirstName(resultSetObj.getString("firstName"));
                tenObj.setLastName(resultSetObj.getString("lastName"));
                tenObj.setAddress(resultSetObj.getString("address"));
                tenObj.setBuilding(resultSetObj.getString("building"));
                tenObj.setAptNum(resultSetObj.getString("apt_num"));
                tenObj.setCity(resultSetObj.getString("city"));
                tenObj.setState(resultSetObj.getString("state"));
                tenObj.setZipcode(resultSetObj.getString("zipcode"));
                tenObj.setPhone(resultSetObj.getString("phone"));
                tenObj.setDOB(resultSetObj.getString("dob"));
                
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
    
    public static ArrayList getMaintenanceListFromDB(){
        ArrayList maintenanceList = new ArrayList();
        try {
            
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM maintenance_request");
            
            resultSetObj = pstmt.executeQuery();
            
               while( resultSetObj.next()){
                Maintenance mainObj = new Maintenance();
                mainObj.setRequestID(resultSetObj.getInt("request_id"));
                mainObj.setTenantID(resultSetObj.getInt("tenant_id"));
                mainObj.setBuilding(resultSetObj.getString("building"));
                mainObj.setAptNum(resultSetObj.getString("apt_num"));
                mainObj.setJobType(resultSetObj.getString("job_type"));
                mainObj.setJobDesc(resultSetObj.getString("job_desc"));
                mainObj.setDateReq(resultSetObj.getString("date_req"));
                maintenanceList.add(mainObj);
               }
                System.out.println("Total Records Fetched: " + maintenanceList.size());          
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return maintenanceList;
    }
    
    
    public static List<Maintenance> getMaintenanceSearchListFromDB(String searchBuild, String searchType){
        
        List<Maintenance> mainSearchList = new ArrayList<>();
       
        
        try {
            connObj = DataConnect.getConnection();
            //job Type is not required
            if(searchType.equals("")){
                pstmt = connObj.prepareStatement("SELECT * FROM maintenance_request WHERE building = ?"); 
                pstmt.setString(1, searchBuild);
            } else {
                pstmt = connObj.prepareStatement("SELECT * FROM maintenance_request WHERE building = ? AND job_type = ?"); 
                pstmt.setString(1, searchBuild);
                pstmt.setString(2, searchType);
            }
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                Maintenance mainSearchObj = new Maintenance();
                mainSearchObj.setRequestID(resultSetObj.getInt("request_id"));
                mainSearchObj.setTenantID(resultSetObj.getInt("tenant_id"));
                mainSearchObj.setBuilding(resultSetObj.getString("building"));
                mainSearchObj.setAptNum(resultSetObj.getString("apt_num"));
                mainSearchObj.setJobType(resultSetObj.getString("job_type"));
                mainSearchObj.setJobDesc(resultSetObj.getString("job_desc"));
                mainSearchObj.setDateReq(resultSetObj.getString("date_req"));
                
                mainSearchList.add(mainSearchObj);
                
               }
                System.out.println("Total Records Fetched: " + mainSearchList.size());
                
               }
                          
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return mainSearchList;
     
    }
    
    
   //currently not showing anything, maybe try making new Payment bean like with maintenance
    public static List<Tenant> getPaymentListFromDB(String payCrit, String payInfo){
               
        List<Tenant> paymentList = new ArrayList<>();
               
        try {
            
            connObj = DataConnect.getConnection();
            //rentPaid is not required
            if(payCrit.equals("")){
                pstmt = connObj.prepareStatement("SELECT * FROM tenant_payments WHERE building = ?"); 
                pstmt.setString(1, payInfo);
            } else {
                pstmt = connObj.prepareStatement("SELECT * FROM tenant_payments WHERE rentPaid = ? AND building = ?"); 
                pstmt.setString(1, payCrit);
                pstmt.setString(2, payInfo);
            }
            resultSetObj = pstmt.executeQuery();
            
               while( resultSetObj.next()){
                Tenant payObj = new Tenant();
                payObj.setEmail(resultSetObj.getString("email"));
                payObj.setTenantID(resultSetObj.getInt("tenant_id"));
                payObj.setFirstName(resultSetObj.getString("firstName"));
                payObj.setLastName(resultSetObj.getString("lastName"));
                payObj.setBuilding(resultSetObj.getString("building"));
                payObj.setAptNum(resultSetObj.getString("apt_num"));
                payObj.setCity(resultSetObj.getString("city"));
                payObj.setState(resultSetObj.getString("state"));
                payObj.setPhone(resultSetObj.getString("phone"));
                payObj.setRentPaid(resultSetObj.getString("rentPaid"));
                payObj.setAmountDue(resultSetObj.getInt("amountDue"));
                
                paymentList.add(payObj);
                
               }
                System.out.println("Total Records Fetched: " + paymentList.size());
                
               
                          
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return paymentList;
     }
    }

