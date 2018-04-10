package waystonepropertymanagement.tenant.portal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class DatabaseOperation {
	public static Statement stmt;
	public static Connection con;
	public static ResultSet resultSetObj;
	public static PreparedStatement ps;

	public static boolean tenantValidate(String email, String password) {

		try {

			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select email, password FROM ten_login WHERE email = ? and password = ?");
			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// result found, means valid inputs
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
			return false;

		} finally {

			DataConnect.close(con);

		}
		return false;
	}

	public static List<Tenant> getTenantListFromDB(String username) {
		Tenant tenRecord = null;
		List<Tenant> tenList = new ArrayList<>();
		Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		try {

			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT * FROM TENANT WHERE email = ?");
			ps.setString(1, username);

			resultSetObj = ps.executeQuery();
			if (resultSetObj != null) {
				resultSetObj.next();
				tenRecord = new Tenant();

				tenRecord.setTenantID(resultSetObj.getInt("TENANT_ID"));
				tenRecord.setFirstName(resultSetObj.getString("FIRSTNAME"));
				tenRecord.setLastName(resultSetObj.getString("LASTNAME"));
				tenRecord.setMi(resultSetObj.getString("MI"));
				tenRecord.setPermAddress(resultSetObj.getString("PERM_ADDRESS"));
				tenRecord.setPermCity(resultSetObj.getString("PERM_CITY"));
				tenRecord.setPermState(resultSetObj.getString("PERM_STATE"));
				tenRecord.setPermZip(resultSetObj.getString("PERM_ZIP"));
				tenRecord.setPhone(resultSetObj.getString("PHONE"));
				tenRecord.setEmail(resultSetObj.getString("EMAIL"));
				tenRecord.setDob(resultSetObj.getString("DOB"));

				tenList.add(tenRecord);
			}
			sessionMapObj.put("tenantProfObj", tenRecord);
		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return tenList;
	}

	public static String updateTenantDetailsInDB(Tenant updateTenantObj) {
		try {

			con = DataConnect.getConnection();
			ps = con.prepareStatement("UPDATE TENANT SET FIRSTNAME = ?, LASTNAME = ?, MI = ?, "
					+ "PERM_ADDRESS = ?, PERM_CITY = ?, PERM_STATE = ?, PERM_ZIP = ?, PHONE = ?, EMAIL = ?, DOB = ?  WHERE TENANT_ID = ?");

			ps.setString(1, updateTenantObj.getFirstName());
			ps.setString(2, updateTenantObj.getLastName());
			ps.setString(3, updateTenantObj.getMi());
			ps.setString(4, updateTenantObj.getPermAddress());
			ps.setString(5, updateTenantObj.getPermCity());
			ps.setString(6, updateTenantObj.getPermState());
			ps.setString(7, updateTenantObj.getPermZip());
			ps.setString(8, updateTenantObj.getPhone());
			ps.setString(9, updateTenantObj.getEmail());
			ps.setString(10, updateTenantObj.getDob());
			ps.setInt(11, updateTenantObj.getTenantID());

			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return "/TenantProfile.xhtml?faces-redirect=true";
	}
	
	public static String updateTenantPassword(String password,String email){
        try{
            con = DataConnect.getConnection();
            ps = con.prepareStatement("UPDATE emp_login SET password = ? WHERE email = ?");
            ps.setString(1, password);
            ps.setString(2, email);
            ps.executeQuery();
        } catch(SQLException e){
            System.out.println("Login error -->" + e.getMessage());        
        } finally {    
            DataConnect.close(con);
        }
        return "/TenantProfile.xhtml?faces-redirect=true";
    }

	public static String getTenantRecordBalanceInDB(int tenantID) {
		Connection con = null;
		double finalAmount = 0;
		double balance = 0;
		DecimalFormat df = new DecimalFormat("#0.00");
		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT AMOUNT, IS_CREDIT FROM RECORDS WHERE TENANT_ID = ?");
			ps.setInt(1, tenantID);
			resultSetObj = ps.executeQuery();
			if (resultSetObj != null) {
				while (resultSetObj.next()) {

					double amount = resultSetObj.getDouble("AMOUNT");

					boolean isCred = resultSetObj.getBoolean("IS_CREDIT");

					if (isCred == true) {
						finalAmount = amount;

					} else {
						finalAmount = amount * -1;

					}

					balance += finalAmount;
				}

			}
		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
		} finally {
			DataConnect.close(con);
		}

		return df.format(balance);
	}

	public static List<Tenant> getTenantAccRecordsInDB(int tenantID) {
		List<Tenant> tenantRecordsList = new ArrayList();

		DecimalFormat df = new DecimalFormat("#0.00");
		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT RECORD_NAME, DATE, AMOUNT, IS_CREDIT FROM RECORDS WHERE TENANT_ID = ?");
			ps.setInt(1, tenantID);
			resultSetObj = ps.executeQuery();
			if (resultSetObj != null) {
				while (resultSetObj.next()) {
					Tenant tenAllRecordsObj = new Tenant();
					tenAllRecordsObj.setRecordName(resultSetObj.getString("RECORD_NAME"));
					tenAllRecordsObj.setRecordDate(resultSetObj.getString("DATE"));

					double amount = resultSetObj.getDouble("AMOUNT");

					boolean isCred = resultSetObj.getBoolean("IS_CREDIT");
					if (isCred == true) {

						tenAllRecordsObj.setRecordAmount(df.format(amount));
					} else {

						tenAllRecordsObj.setRecordAmount(df.format(amount * -1));
					}

					tenantRecordsList.add(tenAllRecordsObj);
				}
				// System.out.println("Total Records Fetched: " + tenantRecordsList.size());
			}
		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
		} finally {
			DataConnect.close(con);
		}

		return tenantRecordsList;
	}

	// the first one I tried to use
	public static List<Tenant> getTenantMaintFromDB(int maintTenID) {
		Tenant tenMaint = null;
		List<Tenant> tenMaintList = new ArrayList<>();
		Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		try {

			con = DataConnect.getConnection();
			ps = con.prepareStatement(
					"SELECT request_id, tenant_id, jobType, jobDesc, datereq FROM maintenance WHERE tenant_id = ?");
			ps.setInt(1, maintTenID);

			resultSetObj = ps.executeQuery();
			if (resultSetObj != null) {
				while (resultSetObj.next()) {
					tenMaint = new Tenant();

					tenMaint.setRequestID(resultSetObj.getInt("request_id"));
					tenMaint.setTenantID(resultSetObj.getInt("tenant_id"));
					tenMaint.setMaintJobType(resultSetObj.getString("jobType"));
					tenMaint.setMaintJobDesc(resultSetObj.getString("jobDesc"));
					tenMaint.setMaintDate(resultSetObj.getString("datereq"));

					tenMaintList.add(tenMaint);
				}
			}
			sessionMapObj.put("tenantMaintObj", tenMaint);
		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return tenMaintList;
	}

	public static List<Tenant> getTenantPaymentsFromDB(String username) {
		Tenant tenPay = null;
		List<Tenant> tenPayList = new ArrayList<>();
		Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		try {

			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT * FROM payment_records WHERE tenant_id = ?");
			ps.setString(1, username);

			resultSetObj = ps.executeQuery();
			if (resultSetObj != null) {
				resultSetObj.next();
				tenPay = new Tenant();

				tenPay.setPaymentID(resultSetObj.getInt("payment_id"));
				tenPay.setTenantID(resultSetObj.getInt("tenant_id"));
				tenPay.setPaymentType(resultSetObj.getString("tenant_id"));
				tenPay.setCreditCardType(resultSetObj.getString("cc_type"));
				tenPay.setCreditCardNum(resultSetObj.getString("cc_num"));
				tenPay.setExpMonth(resultSetObj.getString("exp_month"));
				tenPay.setExpYear(resultSetObj.getString("exp_year"));
				tenPay.setCvvCode(resultSetObj.getString("cvv_code"));
				tenPay.setBankType(resultSetObj.getString("bank_type"));
				tenPay.setBankNum(resultSetObj.getString("bank_num"));
				tenPay.setBankRouting(resultSetObj.getString("bank_routing"));
				tenPay.setBillingAddress(resultSetObj.getString("billing_address"));
				tenPay.setBillingCity(resultSetObj.getString("billing_city"));
				tenPay.setBillingState(resultSetObj.getString("billing_state"));
				tenPay.setBillingZip(resultSetObj.getString("billing_zip"));

				tenPayList.add(tenPay);
			}
			sessionMapObj.put("tenantRecordObj", tenPay);
		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return tenPayList;
	}

	public static String insertIntoMaintInDB(Tenant tenantMaintObj, int maintTenID) {
		int saveResult = 0;
		String navigation;
		try {
			con = DataConnect.getConnection();

			ps = con.prepareStatement("INSERT INTO MAINTENANCE(TENANT_ID, JOBTYPE, JOBDESC, DATEREQ) VALUES(?,?,?,?)");
			ps.setInt(1, maintTenID);
			ps.setString(2, tenantMaintObj.getMaintJobType());
			ps.setString(3, tenantMaintObj.getMaintJobDesc());
			ps.setString(4, tenantMaintObj.getMaintDate());

			saveResult = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
		} finally {
			DataConnect.close(con);
		}

		if (saveResult != 0) {
			FacesContext.getCurrentInstance().addMessage("maintForm:jobType",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Your Maintenance Request Was Successfully Received",
							"Your Maintenance Request Was Successfully Received"));
			// navigation = "/TenantMaintenanceConfirmation.xhtml?face-redirect=true";

		} else {
			FacesContext.getCurrentInstance().addMessage("maintForm:jobType",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "There Was An Error, Please Resubmit Your Request",
							"There Was An Error, Please Resubmit Your Request"));
			// navigation = "/TenantMaintenance.xhtml?faces-redirect=true";
		}

		return "/tenantAdmin.xhtml?faces-redirect=true";
		// return navigation;

	}

	public static String insertIntoPaymentInDB(Tenant tenantRecordObj, int payTenID) {
		int saveResult = 0;
		Date date = new Date();
		LocalDate today = date.toInstant().atZone(ZoneId.of("America/Montreal")).toLocalDate();
		System.out.println("today : " + today);
		int monthInt = today.getMonthValue();
		int yearInt = today.getYear();
		java.text.DecimalFormat nft = new java.text.DecimalFormat("00");
		String month = nft.format(monthInt);
		try {
			con = DataConnect.getConnection();

			ps = con.prepareStatement(
					"INSERT INTO payment_records(tenant_id, payment_type, cc_type, cc_num, exp_month, exp_year, cvv_code, bank_type, bank_num, bank_routing, billing_address, billing_city, billing_state, billing_zip) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, payTenID);
			ps.setString(2, tenantRecordObj.getPaymentType());
			ps.setString(3, tenantRecordObj.getCreditCardType());
			ps.setString(4, tenantRecordObj.getCreditCardNum());
			ps.setString(5, tenantRecordObj.getExpMonth());
			ps.setString(6, tenantRecordObj.getExpYear());
			ps.setString(7, tenantRecordObj.getCvvCode());
			ps.setString(8, tenantRecordObj.getBankType());
			ps.setString(9, tenantRecordObj.getBankNum());
			ps.setString(10, tenantRecordObj.getBankRouting());
			ps.setString(11, tenantRecordObj.getBillingAddress());
			ps.setString(12, tenantRecordObj.getBillingCity());
			ps.setString(13, tenantRecordObj.getBillingState());
			ps.setString(14, tenantRecordObj.getBillingZip());

			saveResult = ps.executeUpdate();

			PreparedStatement prepst = con.prepareStatement("SELECT UNIT_ID FROM UNITS WHERE TENANT_ID = ?");
			prepst.setInt(1, payTenID);
			ResultSet rs = prepst.executeQuery();
			while (rs.next()) {
				Tenant rentUnitObj = new Tenant();
				rentUnitObj.setUnit(rs.getInt("UNIT_ID"));

				PreparedStatement pstmt = con.prepareStatement(
						"INSERT INTO RECORDS(record_name, amount, is_credit, date, invnum, tenant_id, account_name) values('TenantRentPaid',?,'0',?,?,?,'RENT CASH ACCOUNT')");
				pstmt.setString(1, tenantRecordObj.getAmountPaid());
				pstmt.setObject(2, today);
				pstmt.setString(3, "UNIT" + rentUnitObj.getUnit() + "PAID" + month + yearInt);
				pstmt.setInt(4, payTenID);

				pstmt.executeUpdate();

				PreparedStatement ppstmt = con.prepareStatement(
						"INSERT INTO RECORDS(record_name, amount, is_credit, date, invnum, account_name) values('TenantRentDeposit',?,'1',?,?,'ACCOUNTS RECEIVABLE')");
				ppstmt.setString(1, tenantRecordObj.getAmountPaid());
				ppstmt.setObject(2, today);
				ppstmt.setString(3, "UNIT" + rentUnitObj.getUnit() + "DEPOSIT" + month + yearInt);

				ppstmt.executeUpdate();
			}
			System.out.println("Rent Posted");

		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
		} finally {
			DataConnect.close(con);
		}

		if (saveResult != 0) {
			FacesContext.getCurrentInstance().addMessage("payForm:payemntType",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Your Payment Request Was Successfully Processed",
							"Your Payment Request Was Successfully Processed"));
			// navigation = "/TenantPaymentConfirmation.xhtml?face-redirect=true";

		} else {
			FacesContext.getCurrentInstance().addMessage("payForm:paymentType",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "There Was An Error, Please Resubmit Your Payment",
							"There Was An Error, Please Resubmit Your Payment"));
			// navigation = "/TenantPayment.xhtml?faces-redirect=true";
		}

		return "/tenantAdmin.xhtml?faces-redirect=true";
		// return navigation;

	}

	public static String getTenantPropertyUnitFromDB(int tenantID) {
		String property = null;
		Tenant tenUnit = null;
		Connection con = null;
		Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT building FROM UNITS WHERE TENANT_ID = ?");
			ps.setObject(1, tenantID);
			resultSetObj = ps.executeQuery();

			if (resultSetObj != null) {
				resultSetObj.next();
				tenUnit = new Tenant();

				tenUnit.setBuilding(resultSetObj.getString("BUILDING"));

				PreparedStatement ppstmt = con.prepareStatement("SELECT * from waystone_properties WHERE building = ?");
				ppstmt.setString(1, tenUnit.getBuilding());
				ResultSet rs = ppstmt.executeQuery();
				while (rs.next()) {
					tenUnit.setPropertyAddress(resultSetObj.getString("address"));
					tenUnit.setPropertyCity(resultSetObj.getString("city"));
					tenUnit.setPropertyState(resultSetObj.getString("state"));
					tenUnit.setPropertyZip(resultSetObj.getString("zip"));
					tenUnit.setPropertyPhone(resultSetObj.getString("phone"));
				}
				sessionMapObj.put("tenantUnitObj", tenUnit);
			}
		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
		} finally {
			DataConnect.close(con);
		}

		return property;
	}
}