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
       List<Employee> empList = new ArrayList<Employee>();
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

    
    public static List<Tenant> getTenantListFromDB(String searchCrit, String searchInfo){
       
       List<Tenant> tenList = new ArrayList<>();
       
        switch (searchCrit) {
            case "Tenant ID":
                searchCrit = "tenant_id";
                break;
            case "Last Name":
                searchCrit = "lastName";
                break;
            default:
                searchCrit = "apt_num";
                break;
        }
        try {
            
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM ten_accounts WHERE ? = ?");
            pstmt.setString(1, searchCrit);
            pstmt.setString(1, searchInfo);
            
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                
                tenList.add(new Tenant(resultSetObj.getString(1),resultSetObj.getInt(2), resultSetObj.getString(3), resultSetObj.getString(4),
                resultSetObj.getString(6),resultSetObj.getString(7), resultSetObj.getString(8), resultSetObj.getString(9),
                 resultSetObj.getString(11)));
               }
            }              
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return tenList;
     }
    
    public static List<Maintenance> getMaintenanceFromDB(){
        List<Maintenance> mainTable = new ArrayList();
        try {
            
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM maintenance_request;");
            
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                
                mainTable.add(new Maintenance(resultSetObj.getInt(1),resultSetObj.getInt(2), resultSetObj.getString(3), resultSetObj.getString(4),
                resultSetObj.getString(5), resultSetObj.getString(6),resultSetObj.getString(7)));
               }
            }              
            
        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
              DataConnect.close(connObj);
        }
        return mainTable;
    }
    
    }

