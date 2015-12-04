package code;

import java.sql.*;

public class customer {

	private String firstName;
	private String lastName;
	private String addrLineOne;
	private String addrLineTwo;
	private String city;
	private String state;
	private String zip;
	private String phoneNum;
	protected int CID;
	protected int PID;
	protected String publicationName;
	private String status;
	private subscriptions mySubs;
	private LatLng myPoints;
	private connect cn = new connect();
	private boolean encapsulation = false;

	// create new customer AddTypeOne
	public customer(String fN, String lN, String addLn1, String c, String st, String z, String pN) {
		cn.addCustomer(fN, lN, addLn1, "", c, st, z, pN);

		CID = cn.getCustomerID(pN);
		myPoints = new LatLng(cn, CID);
		status = "ACTIVE";
		firstName = fN;
		lastName = lN;
		addrLineOne = addLn1;
		addrLineTwo = null;
		city = c;
		state = st;
		zip = z;
		phoneNum = pN;
	}

	// create new customer AddTypeTwo
	public customer(String fN, String lN, String addLn1, String addLn2, String c, String st, String z, String pN) {
		cn.addCustomer(fN, lN, addLn1, addLn2, c, st, z, pN);
		CID = cn.getCustomerID(pN);
		myPoints = new LatLng(cn, CID);
		// mySubs = new subscriptions (cn, CID, pubID);
		status = "ACTIVE";
		firstName = fN;
		lastName = lN;
		addrLineOne = addLn1;
		addrLineTwo = null;
		city = c;
		state = st;
		zip = z;
		phoneNum = pN;
	}

	// encapsulation constructor for lat long with addLn2
	public customer(int cID, int pID, String fN, String lN, String addLn1, String addLn2, String c, String st, String z,
			boolean encap) {
		PID = pID;
		CID = cID;
		ResultSet r = cn.searchPublication(PID, "");
		try {
			while (r.next()) {
				publicationName = r.getString("PublicationName");
			}
		} catch (Exception e) {

		}
		encapsulation = encap;
		firstName = fN;
		lastName = lN;
		addrLineOne = addLn1;
		addrLineTwo = addLn2;
		city = c;
		state = st;
		zip = z;
	}

	public customer(int cID, int pID, String fN, String lN, String addLn1, String c, String st, String z,
			boolean encap) {
		PID = pID;
		CID = cID;
		ResultSet r = cn.searchPublication(PID, "");
		try {
			while (r.next()) {
				publicationName = r.getString("PublicationName");
			}
		} catch (Exception e) {

		}
		encapsulation = encap;
		firstName = fN;
		lastName = lN;
		addrLineOne = addLn1;
		city = c;
		state = st;
		zip = z;
	}

	// select customer with specified customer ID
	public customer(int ID) {
		ResultSet r = cn.searchCustomer(ID, "", "");
		ResultSet subs = cn.getSubscriptions(ID);
		ResultSet points = cn.getLatLngValues(ID);
		try {
			while (r.next()) {
				CID = r.getInt("CustomerID");
				status = r.getString("Status");
				firstName = r.getString("FirstName");
				lastName = r.getString("LastName");
				addrLineOne = r.getString("Address");
				if ((addrLineTwo = r.getString("AddressLineTwo")).length() == 0)
					addrLineTwo = null;
				city = r.getString("City");
				state = r.getString("State");
				zip = r.getString("Zip");
				phoneNum = r.getString("Phone");
			}
			while (subs.next()) {
				mySubs = new subscriptions(subs.getInt("ItemID"), ID, subs.getDouble("TotalAmount"));
			}
			while (points.next()) {
				myPoints = new LatLng(points.getDouble("Latitude"), points.getDouble("Longitude"));
			}
		} catch (Exception e) {
			CID = 0;
		}
	}

	public LatLng getLatLng() {
		return myPoints;
	}

	public subscriptions getMySubscriptions() {
		return mySubs;
	}

	public String toString() {
		if (addrLineTwo != null) {
			return "Customer ID: " + CID + "\nName: " + firstName + " " + lastName + "\nAddress: " + addrLineOne + "\n"
					+ addrLineTwo + "\n" + city + ", " + state + " " + zip + "\nPhone Number: " + phoneNum
					+ "\nStatus: " + status;
		} else if (encapsulation && addrLineTwo != null) {
			return "[Customer ID: " + CID + " Publication ID: " + PID + " Publication Name: " + publicationName
					+ " Name: " + firstName + " " + lastName + "\nAddress: " + addrLineOne + " " + addrLineTwo + " "
					+ city + ", " + state + " " + zip + "]";
		} else if (encapsulation && addrLineTwo == null) {
			return "[Customer ID: " + CID + " Publication ID: " + PID + " Publication Name: " + publicationName
					+ " Name: " + firstName + " " + lastName + "\nAddress: " + addrLineOne + " " + city + ", " + state
					+ " " + zip + "]";
		}

		else {
			return "Customer ID: " + CID + "\nName: " + firstName + " " + lastName + "\nAddress: " + addrLineOne + "\n"
					+ city + ", " + state + " " + zip + "\nPhone Number: " + phoneNum + "\nStatus: " + status;
		}
	}

	public boolean modFirstName(String fN) {
		firstName = fN;
		return cn.modCustomerInfo(CID, "FirstName", fN);
	}

	public boolean modLastName(String lN) {
		lastName = lN;
		return cn.modCustomerInfo(CID, "LastName", lN);
	}

	public boolean modAddress(String addLn1, String c, String s, String z) {
		addrLineOne = addLn1;
		city = c;
		state = s;
		zip = z;
		if (cn.modCustomerInfo(CID, "Address", addLn1) && cn.modCustomerInfo(CID, "City", c)
				&& cn.modCustomerInfo(CID, "State", s) && cn.modCustomerInfo(CID, "Zip", z)) {
			return true;
		} else
			return false;
	}

	public boolean modAddress(String addLn1, String addLn2, String c, String s, String z) {
		addrLineOne = addLn1;
		addrLineTwo = addLn2;
		city = c;
		state = s;
		zip = z;
		if (cn.modCustomerInfo(CID, "Address", addLn1) && cn.modCustomerInfo(CID, "AddressLineTwo", addLn2)
				&& cn.modCustomerInfo(CID, "City", c) && cn.modCustomerInfo(CID, "State", s)
				&& cn.modCustomerInfo(CID, "Zip", z)) {
			return true;
		} else
			return false;
	}

	public boolean modPhoneNum(String pN) {
		phoneNum = pN;
		return cn.modCustomerInfo(CID, "Phone", pN);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return addrLineOne;
	}

	public int getCustID() {
		return CID;
	}

	public boolean setStatus(String st) {
		if (status.equals(st)) {
			return false;
		} else {
			status = st;
			return cn.modCustomerInfo(CID, "Status", st);
		}
	}
}/*
	 * private String firstName; private String lastName; private String
	 * addrLineOne; private String addrLineTwo; private String city; private
	 * String state; private String zip; private String phoneNum;
	 */