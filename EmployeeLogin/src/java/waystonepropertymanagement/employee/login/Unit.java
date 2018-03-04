/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waystonepropertymanagement.employee.login;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Katie
 * 
 * 
 */
@ManagedBean (name="unit")
@ViewScoped
public class Unit {
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
    public void getUnitList(String unitBuildSearch, String vacancy){
        unitList = DatabaseOperation.getUnitListFromDB(unitBuildSearch, vacancy);
    }
    public String viewUnitRecord(int unitID){
        return DatabaseOperation.viewUnitRecordInDB(unitID);
    }
    
    public String updateUnitDetails(Unit updateUnitObj){
        return DatabaseOperation.updateUnitDetailsInDB(updateUnitObj);
    }
}
