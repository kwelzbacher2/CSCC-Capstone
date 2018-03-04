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
                System.out.println(tenList);
                
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
                System.out.println(tenBuildList);
                
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
                    + "PERM_ADDRESS = ?, PERM_CITY = ?, PERM_STATE = ?, PERM_ZIP = ?, PHONE = ?, EMAIL=?, DOB = ? WHERE TENANT_ID = ?");
                pstmt.setString(1, updateTenObj.getFirstName());
                pstmt.setString(2, updateTenObj.getLastName());
                pstmt.setString(3, updateTenObj.getMi());
                pstmt.setString(4, updateTenObj.getPermAddress());
                pstmt.setString(5, updateTenObj.getPermCity());
                pstmt.setString(6, updateTenObj.getPermState());
                pstmt.setString(7, updateTenObj.getPermZip());
                pstmt.setString(8, updateTenObj.getPhone());
                pstmt.setString(9, updateTenObj.getDOB());
                pstmt.setString(10, updateTenObj.getEmail());
                pstmt.setInt(11, updateTenObj.getTenantID());
            
                pstmt.executeQuery();
                
            } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
            } finally {    
                 DataConnect.close(connObj);
            }
        return "/viewTenant.xhtml?faces-redirect=true";
        }
    public static String insertNewTenantInDB(Tenant newTenantObj){
        int saveResult = 0;
        String navigationResult = "";
        
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
            navigationResult = "tenantAccounts.xhtml?faces=redirect=true";
        } else {
            navigationResult = "createStudent.xhtml?faces=redirect=true";
        }
        return navigationResult;
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
                mainObj.setJobType(resultSetObj.getString("job_type"));
                mainObj.setJobDesc(resultSetObj.getString("job_desc"));
                mainObj.setDateReq(resultSetObj.getString("date_req"));
                PreparedStatement prepst = connObj.prepareStatement("SELECT * FROM unit WHERE tenant_id = ?"); 
                prepst.setInt(1, mainObj.getTenantID());
                ResultSet rset = prepst.executeQuery();
                    while(rset.next()){
                        mainObj.setBuilding(resultSetObj.getString("building"));
                        mainObj.setAptNum(resultSetObj.getString("apt_num"));
                    }
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
                pstmt = connObj.prepareStatement("UPDATE UNIT SET BUILDING = ?, APTNUM = ?, ADDRESS = ?, "
                    + "CITY = ?, STATE = ?, ZIP = ?, RENT = ?, TENANT_ID = ? WHERE UNIT_ID = ?");
                pstmt.setString(1, updateUnitObj.getUnitBuilding());
                pstmt.setString(2, updateUnitObj.getUnitAptNum());
                pstmt.setString(3, updateUnitObj.getUnitAddress());
                pstmt.setString(4, updateUnitObj.getUnitCity());
                pstmt.setString(5, updateUnitObj.getUnitState());
                pstmt.setString(6, updateUnitObj.getUnitZip());
                pstmt.setString(7, updateUnitObj.getUnitRent());
                pstmt.setString(8, updateUnitObj.getUnitTenantID());
                pstmt.setInt(9, updateUnitObj.getUnitID());
                            
                pstmt.executeQuery();
                
            } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
            } finally {    
                 DataConnect.close(connObj);
            }
        return "/viewUnit.xhtml?faces-redirect=true";
     }
    }

