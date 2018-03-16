package waystonepropertymanagement.tenant.portal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import waystonepropertymanagement.tenant.portal.TenantJSFBean;

public class DatabaseOperation {
	public static Statement stmtObj;
	public static Connection connObj;
	public static ResultSet resultSetObj;
	public static PreparedStatement pstmt;

	public static boolean tenantValidate(String email, String password) {

		try {

			connObj = DataConnect.getConnection();
			pstmt = connObj
					.prepareStatement("Select email, password FROM tenant_login WHERE email = ? and password = ?");
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				// result found, means valid inputs
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

	public static List<Tenant> getTenantListFromDB(String searchCrit, String searchInfo) {
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
			if (resultSetObj != null) {
				while (resultSetObj.next()) {
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
					while (rset.next()) {
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

	public static List<Tenant> getTenantListBuilding(String searchInfo) {
		List<Tenant> tenBuildList = new ArrayList<>();

		try {

			connObj = DataConnect.getConnection();
			pstmt = connObj.prepareStatement("SELECT * FROM UNITS WHERE BUILDING = ? AND TENANT_ID IS NOT NULL");
			pstmt.setString(1, searchInfo);

			resultSetObj = pstmt.executeQuery();
			if (resultSetObj != null) {
				while (resultSetObj.next()) {
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
					while (rset.next()) {
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

	public static String viewTenantRecordInDB(int tenantID) {
		Tenant viewTen;
		Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		try {
			connObj = DataConnect.getConnection();
			pstmt = connObj.prepareStatement("SELECT * FROM TENANT WHERE TENANT_ID = ?");
			pstmt.setInt(1, tenantID);
			resultSetObj = pstmt.executeQuery();
			if (resultSetObj != null) {
				while (resultSetObj.next()) {
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
					while (rset.next()) {
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

	public static String updateTenantDetailsInDB(Tenant updateTenObj) {
		try {

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

	public static String viewIndivRecordInDB(int recordID) {
		Record viewRecord;
		Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		try {
			connObj = DataConnect.getConnection();
			pstmt = connObj.prepareStatement("SELECT * FROM RECORDS WHERE RECORD_ID = ?");
			pstmt.setInt(1, recordID);
			resultSetObj = pstmt.executeQuery();
			if (resultSetObj != null) {
				while (resultSetObj.next()) {
					viewRecord = new Record();
					viewRecord.setRecordID(resultSetObj.getInt("RECORD_ID"));
					viewRecord.setRecordName(resultSetObj.getString("RECORD_NAME"));
					viewRecord.setRecordAmount(resultSetObj.getString("AMOUNT"));
					if (resultSetObj.getString("IS_CREDIT").equals("0")) {
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

	public static List<String> viewAllBuildingNamesInDB() {
		List<String> buildNameList = new ArrayList();
		try {
			connObj = DataConnect.getConnection();
			pstmt = connObj.prepareStatement("SELECT DISTINCT BUILDING FROM UNITS");
			resultSetObj = pstmt.executeQuery();
			while (resultSetObj.next()) {
				buildNameList.add(resultSetObj.getString("BUILDING"));
			}
		} catch (SQLException e) {
			System.out.println("Login error -->" + e.getMessage());
		} finally {
			DataConnect.close(connObj);
		}
		return buildNameList;
	}
}