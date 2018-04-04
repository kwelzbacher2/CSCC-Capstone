package waystonepropertymanagement.tenant.portal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Roxanne
 */
@ManagedBean(name = "tenant")
@SessionScoped

public class Tenant implements Serializable {
	private static final long serialVersionUID = 1094801825228386363L;

	private String email;
	private String pwd;
	private String msg;

	private int tenantID;
	private String firstName;
	private String lastName;
	private String mi;
	private String phone;
	private String dob;
	private String permAddress;
	private String permCity;
	private String permState;
	private String permZip;

	private int unit;
	private String building;
	private String apt;
	private String propertyAddress;
	private String propertyCity;
	private String propertyState;
	private String propertyZip;
	private String propertyPhone;

	private int requestID; // maint
	private String maintJobType;
	private String maintJobDesc;
	private String maintDate;

	private String amountPaid;
	private String recordAmount;
	private String recordDate;
	private String recordName;

	private int paymentID;
	private String paymentType;
	private String creditCardType;
	private String creditCardNum;
	private String expMonth;
	private String expYear;
	private String cvvCode;
	private String bankType;
	private String bankNum;
	private String bankRouting;
	private String billingAddress;
	private String billingCity;
	private String billingState;
	private String billingZip;

	/**
	 * Creates a new instance of TenantJSFBean
	 */
	public Tenant() {
	}

	private List<Tenant> tenantListFromDB;

	public List<Tenant> getTenantListFromDB() {
		return tenantListFromDB;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getTenantID() {
		return tenantID;
	}

	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMi() {
		return mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPermAddress() {
		return permAddress;
	}

	public void setPermAddress(String permAddress) {
		this.permAddress = permAddress;
	}

	public String getPermCity() {
		return permCity;
	}

	public void setPermCity(String permCity) {
		this.permCity = permCity;
	}

	public String getPermState() {
		return permState;
	}

	public void setPermState(String permState) {
		this.permState = permState;
	}

	public String getPermZip() {
		return permZip;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public void setPermZip(String permZip) {
		this.permZip = permZip;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getApt() {
		return apt;
	}

	public void setApt(String apt) {
		this.apt = apt;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public String getPropertyCity() {
		return propertyCity;
	}

	public void setPropertyCity(String propertyCity) {
		this.propertyCity = propertyCity;
	}

	public String getPropertyState() {
		return propertyState;
	}

	public void setPropertyState(String propertyState) {
		this.propertyState = propertyState;
	}

	public String getPropertyZip() {
		return propertyZip;
	}

	public void setPropertyZip(String propertyZip) {
		this.propertyZip = propertyZip;
	}

	public String getPropertyPhone() {
		return propertyPhone;
	}

	public void setPropertyPhone(String propertyPhone) {
		this.propertyPhone = propertyPhone;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public String getMaintJobType() {
		return maintJobType;
	}

	public void setMaintJobType(String maintJobType) {
		this.maintJobType = maintJobType;
	}

	public String getMaintJobDesc() {
		return maintJobDesc;
	}

	public void setMaintJobDesc(String maintJobDesc) {
		this.maintJobDesc = maintJobDesc;
	}

	public String getMaintDate() {
		return maintDate;
	}

	public void setMaintDate(String maintDate) {
		this.maintDate = maintDate;
	}

	public String getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getRecordAmount() {
		return recordAmount;
	}

	public void setRecordAmount(String recordAmount) {
		this.recordAmount = recordAmount;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public int getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	public String getExpMonth() {
		return expMonth;
	}

	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	public String getExpYear() {
		return expYear;
	}

	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	public String getCvvCode() {
		return cvvCode;
	}

	public void setCvvCode(String cvvCode) {
		this.cvvCode = cvvCode;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getBankRouting() {
		return bankRouting;
	}

	public void setBankRouting(String bankRouting) {
		this.bankRouting = bankRouting;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public String getBillingZip() {
		return billingZip;
	}

	public void setBillingZip(String billingZip) {
		this.billingZip = billingZip;
	}

	// validate login
	public String validateUsernamePassword() {
		boolean valid = DatabaseOperation.tenantValidate(email, pwd);
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", email);
			return "tenantAdmin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect Username and Password", "Please enter correct Username and Password"));
			return "index";
		}
	}

	public List<Tenant> getTenantRecord() {
		return DatabaseOperation.getTenantListFromDB(email);
	}

	public List<Tenant> getTenantMaint(int maintTenID) {
		return DatabaseOperation.getTenantMaintFromDB(maintTenID);
	}
	public List<Tenant> getTenantAcctRecords(int maintRecordID) {
		return DatabaseOperation.getTenantAccRecordsInDB(maintRecordID);
	}
	

	public String updateTenantDetails(Tenant updateTenantObj) {
		return DatabaseOperation.updateTenantDetailsInDB(updateTenantObj);
	}

	public String updatePassword() {
		return DatabaseOperation.updateTenantPassword(pwd, email);
	}

	public String insertMaintenanceRequest(Tenant tenantMaintObj, int maintTenID) {
		return DatabaseOperation.insertIntoMaintInDB(tenantMaintObj, maintTenID);
	}

	public Double getTenantRecordBalance(int tenantID) {
		return DatabaseOperation.getTenantRecordBalanceInDB(tenantID);
	}

	public String insertTenantPayment(Tenant tenantRecordObj, int payTenID) {
		return DatabaseOperation.insertIntoPaymentInDB(tenantRecordObj, payTenID);
	}
	
	public String getTenantPropertyUnit(int tenantID) {
		return DatabaseOperation.getTenantPropertyUnitFromDB(tenantID);
	}
	
	



	// logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "index";
	}

}
