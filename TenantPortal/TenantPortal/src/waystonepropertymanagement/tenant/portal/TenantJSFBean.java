/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waystonepropertymanagement.tenant.portal;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean; // default import
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Roxanne
 */
@ManagedBean(name = "tenant") // @ManagedBean is default, it let me add (named="tenant")
@SessionScoped

public class TenantJSFBean implements Serializable {
	private static final long serialVersionUID = 1094801825228386363L;

	private String email;
	private String pwd;
	private String msg;

	private String firstName;
	private String lastName;
	private String mi;
	private String phone;
	private String dob;
	private String permAddress;
	private String permCity;
	private String permState;
	private String permZip;

	private String building;
	private String apt;
	private String rentAmount;

	private String maintenanceJobType;
	private String maintJobDesc;

	private String paymentType;
	private String creditCardType;
	private String creditCardNum;
	private String expDate;
	private String cvvCode;
	private String bankType;
	private String bankNum;
	private String bankRouting;

	/**
	 * Creates a new instance of TenantJSFBean
	 */
	public TenantJSFBean() {
	}

	public List<Tenant> tenantListFromDB;

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

	public String getFirstname() {
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

	public String getRentAmount() {
		return rentAmount;
	}

	public void setRentAmount(String rentAmount) {
		this.rentAmount = rentAmount;
	}

	public String getMaintenanceJobType() {
		return maintenanceJobType;
	}

	public void setMaintenanceJobType(String maintenanceJobType) {
		this.maintenanceJobType = maintenanceJobType;
	}

	public String getMainJobDesc() {
		return maintJobDesc;
	}

	public void setMainJobDesc(String maintJobDesc) {
		this.maintJobDesc = maintJobDesc;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymenetType(String paymentType) {
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

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
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

	//validate login
	public String validateUsernamePassword() {
		boolean valid = DatabaseOperation.tenantValidate(email, pwd);
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", email);
			return "admin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect Username and Password", "Please enter correct Username and Password"));
			return "index";
		}
	}

	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "index";
	}
}
