package waystonepropertymanagement.employee.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
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
        String navigationResult = "";
        //java.sql.Date sqlDate = new java.sql.Date(newTenantObj.getDOB().getTime());
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
            navigationResult = "insertTenant.xhtml?faces=redirect=true";
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
                pstmt.setString(1, updateUnitObj.getUnitBuilding());
                pstmt.setString(2, updateUnitObj.getUnitAptNum());
                pstmt.setString(3, updateUnitObj.getUnitAddress());
                pstmt.setString(4, updateUnitObj.getUnitCity());
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
        String navigationResult = "";
        
        try{
            connObj = DataConnect.getConnection();
            
            pstmt = connObj.prepareStatement("INSERT INTO UNITS (BUILDING, APTNUM, ADDRESS, CITY, STATE, ZIP, RENT) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, newUnitObj.getUnitBuilding());
            pstmt.setString(2, newUnitObj.getUnitAptNum());
            pstmt.setString(3, newUnitObj.getUnitAddress());
            pstmt.setString(4, newUnitObj.getUnitCity());
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
            navigationResult = "units.xhtml?faces=redirect=true";
        } else {
            navigationResult = "createUnit.xhtml?faces=redirect=true";
        }
        return navigationResult;
     }
     
     public static List<Account> getAccountListFromDB(String accSearchName, String accSearchType){
         List<Account> accountSearchList = new ArrayList<>();
       
        
        try {
            connObj = DataConnect.getConnection();
            // Type is not required
            if(accSearchName.equals("")){
                if(accSearchType.equals("")){
                    pstmt = connObj.prepareStatement("SELECT * FROM ACCOUNTS");
                } else {
                    pstmt = connObj.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNT_TYPE = ?");
                    pstmt.setString(1, accSearchType);
                }
            } else {
                if(accSearchType.equals("")){
                    pstmt = connObj.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNT_NAME = ? ");
                    pstmt.setString(1, accSearchName);
                } else {
                    pstmt = connObj.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNT_NAME = ? AND ACCOUNT_TYPE = ?");
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
     
     public static String viewAccountInfoInDB(String accountName) {
        Account viewAccount;
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        
        try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNT_NAME = ?");
            pstmt.setString(1, accountName);
            resultSetObj = pstmt.executeQuery();
            if(resultSetObj != null) {
               while( resultSetObj.next()){
                viewAccount = new Account();
                
                viewAccount.setAccountName(resultSetObj.getString("ACCOUNT_NAME"));
                viewAccount.setAccountType(resultSetObj.getString("ACCOUNT_TYPE"));
                               
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
     
     public static List<Record> getAllRecordsFromDB(){
    	 List<Record> allRecords = new ArrayList();
    	 try{
    		 connObj = DataConnect.getConnection();
           	 stmtObj = connObj.createStatement();           	
             resultSetObj = stmtObj.executeQuery("SELECT * FROM RECORDS ORDER BY DATE DESC");
             
             while( resultSetObj.next()) {
                 Record allRecordObj = new Record();
                 allRecordObj.setRecordID(resultSetObj.getInt("RECORD_ID"));
                 allRecordObj.setRecordName(resultSetObj.getString("RECORD_NAME"));
                 allRecordObj.setRecordAmount(resultSetObj.getDouble("AMOUNT"));
                 
                 if(resultSetObj.getString("IS_CREDIT").equals("0")){
                	 allRecordObj.setRecordIsCredit("NO");
                	 allRecordObj.setRecordDebitAmount(allRecordObj.getRecordAmount());
                	 allRecordObj.setRecordCreditAmount(null);
                 } else {
                	 allRecordObj.setRecordIsCredit("YES");
                	 allRecordObj.setRecordCreditAmount(allRecordObj.getRecordAmount());
                	 allRecordObj.setRecordDebitAmount(null);
                	 
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
     
     public static List<Record> getAllRecordListFromDB(String searchRecCrit, String searchRecInfo, String recordAccount){
         List<Record> allRecordList = new ArrayList();
         
         
            try{
                connObj = DataConnect.getConnection();
                switch (searchRecCrit) {
                 case "Record Name":
                     pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE RECORD_NAME = ? AND ACCOUNT_NAME= ?");
                     pstmt.setString(1, searchRecInfo);
                     pstmt.setString(2, recordAccount);
                     break;
                 case "Record Invoice Number":
                     pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE INVNUM = ? AND ACCOUNT_NAME = ?");
                     pstmt.setString(1, searchRecInfo);
                     pstmt.setString(2, recordAccount);
                     break;
                 default:
                     pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE ACCOUNT_NAME = ?");
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
                        viewRecordObj.setRecordIsCredit("NO");
                    } else {
                        viewRecordObj.setRecordIsCredit("YES");
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
                    viewRecord.setRecordIsCredit("No");
                } else {
                    viewRecord.setRecordIsCredit("Yes");
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
     
     public static List<String> viewAllAccountNamesInDB(){
         List<String> accountNameList = new ArrayList();
         try{
            connObj = DataConnect.getConnection();
            pstmt = connObj.prepareStatement("SELECT ACCOUNT_NAME FROM ACCOUNTS");
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
     
     public static String updateAccountDetailsInDB(Account updateAccountObj){
          try {
                        
                connObj = DataConnect.getConnection();
                pstmt = connObj.prepareStatement("UPDATE ACCOUNTS SET ACCOUNT_NAME = ?, ACCOUNT_TYPE = ? WHERE ACCOUNT_NAME= ?");
                    
                pstmt.setString(1, updateAccountObj.getAccountName());
                pstmt.setString(2, updateAccountObj.getAccountType());
                pstmt.setString(3, updateAccountObj.getAccountName());
                
                                            
                pstmt.executeUpdate();
                
            } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
            } finally {    
                 DataConnect.close(connObj);
            }
        return "/viewAccount.xhtml?faces-redirect=true";
     
     }
     
     public static String updateRecordDetailsInDB(Record updateRecObj){
         
         
         try{
                        
                connObj = DataConnect.getConnection();
                pstmt = connObj.prepareStatement("UPDATE RECORDS SET RECORD_NAME = ?, AMOUNT = ?, IS_CREDIT = ?, "
                    + "DATE = ?, INVNUM = ?, ACCOUNT_NAME = ? WHERE RECORD_ID = ?");
                pstmt.setString(1, updateRecObj.getRecordName());
                pstmt.setDouble(2, updateRecObj.getRecordAmount());
                pstmt.setBoolean(3, updateRecObj.getRecCred());
                pstmt.setString(4, updateRecObj.getRecordDate());
                pstmt.setString(5, updateRecObj.getRecordInvNum());
                pstmt.setString(6, updateRecObj.getRecordAccount());
                pstmt.setInt(7, updateRecObj.getRecordID());
                                            
                pstmt.executeUpdate();
                
            } catch (SQLException e) {
                System.out.println("Login error -->" + e.getMessage());        
            } finally {    
                 DataConnect.close(connObj);
            }
        return "/viewRecord.xhtml?faces-redirect=true";
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
    
         
     public static String createNewAccountInDB(Account newAccountObj){
         int saveResult = 0;
        String navigationResult = "";
        
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
            navigationResult = "accounts.xhtml?faces=redirect=true";
        } else {
            navigationResult = "createAccount.xhtml?faces=redirect=true";
        }
        return navigationResult;
     }
     
     
     public static String createNewRecordInDB(Record recNewObj){
         int saveResult = 0;
        String navigationResult = "";
        
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
            navigationResult = viewAccountInfoInDB(recNewObj.getRecordAccount());
        } else {
            navigationResult = "createRecord.xhtml?faces=redirect=true";
        }
        return navigationResult;
     }
     
    public static List<Record> getTenantAccRecordsInDB(int tenantID){
    	List<Record> tenantRecordsList = new ArrayList();
    	
    	 try{
             connObj = DataConnect.getConnection();
             pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE TENANT_ID = ?");
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
    
    public static double getTenantRecordBalanceInDB(int tenantID){
    	Connection connObject = null;
    	double finalAmount =0;
    	double balance = 0;
    	 try{
             connObject = DataConnect.getConnection();
             pstmt = connObject.prepareStatement("SELECT AMOUNT, IS_CREDIT FROM RECORDS WHERE TENANT_ID = ?");
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
                // System.out.println("Tenant Ledger Balance " + balance);
             }
         } catch (SQLException e) {
                     System.out.println("Login error -->" + e.getMessage());        
         } finally {
        	 DataConnect.close(connObject);
         }
    	 
     return  balance;
    }
    
    
    public static String deleteRecordInDB(int recordID){
    	System.out.println("deleteRecordinDB() : Record ID:" + recordID);
    	
    	try{
    		connObj = DataConnect.getConnection();
    		pstmt = connObj.prepareStatement("DELETE FROM RECORDS WHERE RECORD_ID = ?" );
    		pstmt.setInt(1, recordID);
    		pstmt.executeUpdate();
    	} catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
    	} finally {    
    		DataConnect.close(connObj);
    	}
    	return "/viewAccount.xhtml?faces-redirect=true";
    }
    
    public static String deleteAccountInDB(String deleteAccName){
    	System.out.println("deleteAccountinDB() : Account Name:" + deleteAccName);
    	
    	try{
    		connObj = DataConnect.getConnection();
    		pstmt = connObj.prepareStatement("DELETE FROM RECORDS WHERE ACCOUNT_NAME = ?" );
    		pstmt.setString(1, deleteAccName);
    		pstmt.executeUpdate();
    		
    		PreparedStatement prepst = connObj.prepareStatement("DELETE FROM ACCOUNTS WHERE ACCOUNT_NAME = ?" );
    		prepst.setString(1, deleteAccName);
    		prepst.executeUpdate();
    	} catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());        
    	} finally {    
    		DataConnect.close(connObj);
    	}
    	return "/accounts.xhtml?faces-redirect=true";
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
    		pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE ACCOUNT_NAME = 'ACCOUNTS RECEIVABLE' AND RECORD_NAME = 'TenantRent' AND YEAR(DATE) = ? AND MONTH(DATE) = ?" );
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
    				
    				PreparedStatement pst = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, TENANT_ID, ACCOUNT_NAME)"
    						+ "VALUES ('TenantRent', ?, '0', ?, ?, ?, 'ACCOUNTS RECEIVABLE')");
    				pst.setString(1, rentUnitObj.getUnitRent());
    				pst.setObject(2, today);
    				pst.setString(3, "UNIT"+rentUnitObj.getUnitID()+":RENT"+month+yearInt);
    				pst.setString(4, rentUnitObj.getUnitTenantID());
    				pst.executeUpdate();
    				
    				PreparedStatement prep = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, ACCOUNT_NAME)"
    						+ "VALUES ('TenantRentExpected', ?, '1', ?, ?, 'RENTAL INCOME')");
    					prep.setString(1, rentUnitObj.getUnitRent());
    	    			prep.setObject(2, today);
    	    			prep.setString(3, "UNIT"+rentUnitObj.getUnitID()+":EXPECTED"+month+yearInt);
    	    			prep.executeUpdate();
    			}
    			System.out.println("Rent posted");
    			
    			
    		} else {
    			System.out.println("Rent already posted");
    			FacesContext.getCurrentInstance().addMessage("actionForm:rentButton",
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Rent was already posted for this month",
                        "Rent was already posted for this month"));
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
    	
    	double lateFee = 50;
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
    		pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE ACCOUNT_NAME = 'ACCOUNTS RECEIVABLE' AND RECORD_NAME = 'TenantLateFee' AND YEAR(DATE) = ? AND MONTH(DATE) = ?" );
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
    				
    					if(searchBalance > 0.0){
    				
    						PreparedStatement pst = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, TENANT_ID, ACCOUNT_NAME)"
    								+ "VALUES ('TenantLateFee', ?, '0', ?, ?, ?, 'ACCOUNTS RECEIVABLE')");
    						pst.setDouble(1, lateFee);
    						pst.setObject(2, today);
    						pst.setString(3, "UNIT"+lateUnitObj.getUnitID()+":LateFee" + month+yearInt);
    						pst.setString(4, lateUnitObj.getUnitTenantID());
    						pst.executeUpdate();
    						
    						PreparedStatement prep = connObj.prepareStatement("INSERT INTO RECORDS (RECORD_NAME, AMOUNT, IS_CREDIT, DATE, INVNUM, ACCOUNT_NAME)"
    	    						+ "VALUES ('TenantRentExpected', ?, '1', ?, ?, 'RENTAL INCOME')");
    	    				prep.setDouble(1, lateFee);
    	    	    		prep.setObject(2, today);
    	    	    		prep.setString(3, "UNIT"+lateUnitObj.getUnitID()+":FEEq"+month+yearInt);
    	    	    		prep.executeUpdate();
    					}
    				}
    			}
    			System.out.println("late fee posted");
    			
    			
    		} else {
    			System.out.println("late fee already posted");
    			FacesContext.getCurrentInstance().addMessage("actionForm:lateFeeBtn",
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Late Fees were already posted for this month",
                        "Late Fees were already posted for this month"));
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
    }

