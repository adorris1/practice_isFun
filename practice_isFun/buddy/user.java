package code;

import java.sql.ResultSet;
import java.nio.file.*;

public class user {
	private String name;
	private String companyName;
	private String pass;
	private String addr;
	private String city;
	private String state;
	private String zip;
	private String email;
	private String CSPhone;
	private String CSEmail;
	private String filePath;
	private connect cn;

	// add user data; sets name to null if failure to add to db or set file path
	public user(connect con, String n, String cN, String p, String a, String c, String s, String z, String e,
			String csp, String cse, String fP) {
		cn = con;
		name = n;
		companyName = cN;
		pass = p;
		addr = a;
		city = c;
		state = s;
		zip = z;
		email = e;
		CSPhone = csp;
		CSEmail = cse;
		filePath = fP + "/PaperBoyPrints";
		if (!cn.userSetProfile(n, cN, p, a, c, s, z, e, csp, cse, filePath)) {
			name = null;
			System.out.println("FAILED");
		}
		try {
			Files.createDirectory(Paths.get(filePath));
		} catch (Exception E) {

		}
	}

	// get user data; sets name to null if no data exists
	public user(connect con) {
		cn = con;
		ResultSet r = cn.userGetProfile();
		try {
			while (r.next()) {
				name = r.getString("Name");
				companyName = r.getString("CompanyName");
				pass = r.getString("Password");
				addr = r.getString("Address");
				city = r.getString("City");
				state = r.getString("State");
				zip = r.getString("Zip");
				email = r.getString("Email");
				CSPhone = r.getString("CSPhone");
				CSEmail = r.getString("CSEmail");
				filePath = r.getString("filePath");
			}
		} catch (Exception e) {
			name = null;
		}
	}

	public String toString() {
		return "Username: " + name + "\nPassword: " + pass + "\nAddress: " + addr + "\n" + city + ", " + state + " "
				+ zip + "\nE-mail: " + email + "\nSave Location: " + filePath;
	}

	public boolean modUserName(String n) {
		name = n;
		return cn.userModData("Name", n);
	}

	public boolean modCompanyName(String cN) {
		companyName = cN;
		return cn.userModData("CompanyName", cN);
	}

	public boolean modUserPassword(String p) {
		pass = p;
		return cn.userModData("Password", p);
	}

	public boolean modUserAddress(String a, String c, String s, String z) {
		addr = a;
		city = c;
		state = s;
		zip = z;
		return cn.userModData("Address", a) && cn.userModData("City", c) && cn.userModData("State", s)
				&& cn.userModData("Zip", z);
	}

	public boolean modUserEmail(String e) {
		email = e;
		return cn.userModData("Email", e);
	}

	public boolean modCSPhone(String csp) {
		CSPhone = csp;
		return cn.userModData("CSPhone", csp);
	}

	public boolean modCSEmail(String cse) {
		CSEmail = cse;
		return cn.userModData("CSEmail", cse);
	}

	public boolean modUserFilePath(String fP) {
		filePath = fP + "/PaperBoyPrints";
		try {
			Files.createDirectory(Paths.get(filePath));
		} catch (Exception e) {
			return false;
		}
		return cn.userModData("FilePath", fP);
	}

	public String getUserName() {
		return name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getPassword() {
		return pass;
	}

	public String getAddress() {
		return addr + "\n" + city + ", " + state + " " + zip;
	}

	public String getEmail() {
		return email;
	}

	public String getCSPhone() {
		return CSPhone;
	}

	public String getCSEmail() {
		return CSEmail;
	}

	public String getFilePath() {
		return filePath;
	}
}