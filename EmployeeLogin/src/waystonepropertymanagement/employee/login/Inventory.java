/*
 * CSCI Capstone 2999 Final Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
* Inventory is a Managed Bean Class that includes all of the properties and methods for inventory
* @author KWelzbacher
*/
@ManagedBean (name="inventory")
@ViewScoped
public class Inventory implements Serializable{
    
	private static final long serialVersionUID = 1L;
    private String invItem;
    private String addItem;
    private String purchDate;
    private int assetNum;
    private String itemDesc;
    private String invPaid;
    private boolean isPaid;
    private double itemCost;
    
    private int quantity;
    private String itemType;
    
    public String getInvItem() {
		return invItem;
	}
	public void setInvItem(String invItem) {
		this.invItem = invItem;
	}
	public String getAddItem() {
		return addItem;
	}
	public void setAddItem(String addItem) {
		this.addItem = addItem;
	}
	public double getItemCost() {
		return itemCost;
	}
	public void setItemCost(double itemCost) {
		this.itemCost = itemCost;
	}
	
	public Number getItemCostEntry(){
    	return itemCost;
    }
    public void setItemCostEntry(Number itemCostEntry){
    	itemCost = itemCostEntry.doubleValue();
    }
    
	public String getPurchDate() {
		return purchDate;
	}
	public void setPurchDate(String purchDate) {
		this.purchDate = purchDate;
	}
	public int getAssetNum() {
		return assetNum;
	}
	public void setAssetNum(int assetNum) {
		this.assetNum = assetNum;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getInvPaid(){
	        return invPaid;
	}
	public void setInvPaid(String invPaid){
	   this.invPaid = invPaid;
	    if(invPaid.equals("Yes") || invPaid.equals("YES")){
	    	isPaid= true;
	    } else {
	       	isPaid = false;
	    }
	}
	public boolean getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	
 
    public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	//Insert new request in database
    public String createNewInventory(Inventory newInvObj){
    	return DatabaseOperation.createNewInventoryInDB(newInvObj);
    }
    
    public List<String> viewAllItemTypes(){
    	return DatabaseOperation.viewAllItemTypesInDB();
    }
    public List<Inventory> viewItemsQuantity(){
    	return DatabaseOperation.viewItemsQuantityinDB();
    }
}

