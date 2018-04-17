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
* Contact is a Managed Bean Class that includes all of the properties and methods for a contact
* @author KWelzbacher
*/
@ManagedBean (name="contact")
@ViewScoped
public class Contact implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int contactID;
	private String conFirstName;
	private String conLastName;
	private String conEmail;
	private String conPhone;
	private String conMessages;
	
	public int getContactID() {
		return contactID;
	}
	public void setContactID(int contactID) {
		this.contactID = contactID;
	}
	public String getConFirstName() {
		return conFirstName;
	}
	public void setConFirstName(String conFirstName) {
		this.conFirstName = conFirstName;
	}
	public String getConLastName() {
		return conLastName;
	}
	public void setConLastName(String conLastName) {
		this.conLastName = conLastName;
	}
	public String getConEmail() {
		return conEmail;
	}
	public void setConEmail(String conEmail) {
		this.conEmail = conEmail;
	}
	public String getConPhone() {
		return conPhone;
	}
	public void setConPhone(String conPhone) {
		this.conPhone = conPhone;
	}
	public String getConMessages() {
		return conMessages;
	}
	public void setConMessages(String conMessages) {
		this.conMessages = conMessages;
	}
	//Method to obtain all of the contact requests from the database
	public List<Contact> getContactList(){
		return DatabaseOperation.getContactListFromDB();
	}
	//Method to mark certain request as contacted 
	public String endContactRequest(Contact endCont, int endEmpID){
		int contID = endCont.getContactID();
		return DatabaseOperation.endContactRequestInDB(contID, endEmpID);
	}
	
}
