/*
 * CSCI Capstone 2999 Final Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Unit class is a Managed Bean that contains properties and methods for a unit
 * @author KWelzbacher
 * 
 */
@ManagedBean (name="unit")
@SessionScoped
public class Unit implements Serializable{
	private static final long serialVersionUID = 1L;
	private int unitID;
    private String unitBuilding;
    private String unitAptNum;
    private String unitAddress;
    private String unitCity;
    private String unitState;
    private String unitZip;
    private String unitRent;
    private String unitTenantID;
    private String vacancy;
    private String unitBuildSearch;
    private String unitTenFirstName;
    private String unitTenLastName;
    private List<Unit> unitList = new ArrayList();
    
    public int getUnitID(){
        return unitID;
    }
    public void setUnitID(int unitID){
        this.unitID = unitID;
    }
    public String getUnitBuilding (){
        return unitBuilding;
    }
    public void setUnitBuilding(String unitBuilding){
        this.unitBuilding = unitBuilding;
    }
    public String getUnitAptNum (){
        return unitAptNum;
    }
    public void setUnitAptNum(String unitAptNum){
        this.unitAptNum = unitAptNum;
    }
    public String getUnitAddress (){
        return unitAddress;
    }
    public void setUnitAddress(String unitAddress){
        this.unitAddress = unitAddress;
    }
    public String getUnitCity (){
        return unitCity;
    }
    public void setUnitCity(String unitCity){
        this.unitCity = unitCity;
    }
    public String getUnitState(){
        return unitState;
    }
    public void setUnitState(String unitState){
        this.unitState= unitState;
    }
    public String getUnitZip(){
        return unitZip;
    }
    public void setUnitZip(String unitZip){
        this.unitZip = unitZip;
    }
    public String getUnitRent(){
        return unitRent;
    }
    public void setUnitRent(String unitRent){
        this.unitRent = unitRent;
    }
    public String getUnitTenantID(){
        return unitTenantID;
    }
    public void setUnitTenantID(String unitTenantID){
        this.unitTenantID = unitTenantID;
    }
    
    public String getVacancy(){
        return vacancy;
    }
    public void setVacancy(String vacancy){
        this.vacancy = vacancy;
    }
    public List getUnitList(){
        return unitList;
    }
    public void setUnitList(List unitList){
        this.unitList = unitList;
    }
    public String getUnitBuildSearch(){
        return unitBuildSearch;
    }
    public void setUnitBuildSearch(String unitBuildSearch){
        this.unitBuildSearch = unitBuildSearch;
    }
    public String getUnitTenFirstName(){
        return unitTenFirstName;
    }
    
    public void setUnitTenFirstName(String unitTenFirstName){
        this.unitTenFirstName = unitTenFirstName;
    }
    
    public String getUnitTenLastName(){
        return unitTenLastName;
    }
    
    public void setUnitTenLastName(String unitTenLastName){
        this.unitTenLastName = unitTenLastName;
    }
    
    //Obtain list of units based on search criteria
    public void getUnitList(String unitBuildSearch, String vacancy){
        unitList = DatabaseOperation.getUnitListFromDB(unitBuildSearch, vacancy);
        if(unitList.size() == 0){
        	Unit un = new Unit();
        	un.setUnitBuilding("No results found");
        	unitList.add(un);
        }
    }
    //Obtain information of chosen unit
    public String viewUnitRecord(int unitID, String building){
    	if(building.equals("No results found")){
    		return "units.xhtml?faces-redirect=true";
    	} else {
    		return DatabaseOperation.viewUnitRecordInDB(unitID);
    	}
    }
    //Update unit information in database
    public String updateUnitDetails(Unit updateUnitObj){
        return DatabaseOperation.updateUnitDetailsInDB(updateUnitObj);
    }
    //Insert new unit into database
    public String insertNewUnit(Unit newUnitObj){
        return DatabaseOperation.insertNewUnitInDB(newUnitObj);
    }
    //Delete Unit from database
    public String deleteUnit(Unit delUnitObj){
    	int delUnitID = delUnitObj.getUnitID();
        return DatabaseOperation.deleteUnitInDB(delUnitID);
    }
    //Obtain list of all current Buildings
    public List<String> viewAllBuildingNames(){
        return DatabaseOperation.viewAllBuildingNamesInDB();
    }
    public void clearList(){
    	setUnitBuilding(null);
    	setUnitAptNum(null);
    	setUnitAddress(null);
    	setUnitCity(null);
    	setUnitState("AL");
    	setUnitZip(null);
    	setUnitRent(null);  	
    }
}
