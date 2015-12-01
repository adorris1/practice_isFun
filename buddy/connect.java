package code;

import java.sql.*;
import java.util.Date;

/**
 * Establishes connection between the database and classes. May add, view, and
 * modify fields in the database.
 *
 * @author Lee Katsumata
 * @version 1
 */
public class connect {
	private Connection con;
	private Statement stmt;

	/**
	 * Constructs the connection to the database.
	 **/
	public connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3308/saturdays_db", "root", "12345");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the connection.
	 *
	 * @return Connection
	 **/
	public Connection getConnection() {
		return con;
	}

	/*
	 * add subscriptions now additionally needs two more parameters String start
	 * and String end (strings in form "yyyy-MM-dd")
	 */
	public void addSubscriptions(int CID, int PID, String start, String end) {
		try {
			stmt.executeUpdate("insert into SUBSCRIPTIONS (CustomerID, PublicationID, StartDate, EndDate) values (\""
					+ CID + "\",\"" + PID + "\",  \"" + start + "\", \"" + end + "\")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the SubscriptionID that matches the given CustomerID in the
	 * database.
	 *
	 * @return int
	 * 
	 * @param CID
	 *            The int that identifies the customer who the caller would like
	 *            to find the corresponding SubscriptionID to
	 **/
	public int getSubscriptionID(int CID) {
		try {
			ResultSet rs = stmt.executeQuery("select * from subscriptions where CustomerID = " + CID);
			if (rs.next()) {
				return rs.getInt("ItemID");
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public ResultSet searchCustomerWhoSubscribeTo(int PID) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("select * from coor_cust_pub_scrip where PublicationID = " + PID);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the ResultSet containing the subscription information for the
	 * given CustomerID.
	 *
	 * @return ResultSet
	 * 
	 * @param CID
	 *            The int that identifies the customer who the caller would like
	 *            to get the subscription information for
	 **/

	public ResultSet getSubscriptions(int CID) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("select * from subscriptions where CustomerID = " + CID);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Adds a new customer with the given descriptive information.
	 * 
	 * @param fN
	 *            The customer's first name
	 * @param lN
	 *            The customer's last name
	 * @param pN
	 *            The customer's phone number
	 * @param addLn1
	 *            The customer's address
	 * @param addLn2
	 *            The customer's address line two (optional)
	 * @param c
	 *            The customer's city
	 * @param st
	 *            The customer's state
	 * @param z
	 *            The customer's zip code
	 **/
	public void addCustomer(String fN, String lN, String addLn1, String addLn2, String c, String st, String z,
			String pN) {
		String add;
		try {
			if (addLn2.length() > 0) {
				add = "insert into CUSTOMERS (FirstName, LastName, Address, AddressLineTwo, City, State, Zip, Phone)"
						+ " values (\"" + fN + "\", \"" + lN + "\", \"" + addLn1 + "\", \"" + addLn2 + "\", \"" + c
						+ "\", \"" + st + "\", \"" + z + "\", \"" + pN + "\")";
			} else {
				add = "insert into CUSTOMERS (FirstName, LastName, Address, City, State, Zip, Phone)" + " values (\""
						+ fN + "\", \"" + lN + "\", \"" + addLn1 + "\", \"" + c + "\", \"" + st + "\", \"" + z
						+ "\", \"" + pN + "\")";
			}
			System.out.println(add);
			stmt.executeUpdate(add);

		} catch (Exception e) {
		}
	}

	/**
	 * function populates the coordinates database with generated CID from
	 * customer database lat and long.
	 */
	public void addLatLngToCustomer(int CID) {
		ResultSet rs;
		String addCoordinates;
		String upsformattedAdd = "";
		try {
			if (CID != 0) {
				rs = stmt.executeQuery("select * from customers where CustomerID = " + CID);
				while (rs.next()) {
					upsformattedAdd = rs.getString("Zip") + ", " + rs.getString("Address") + " "
							+ rs.getString("State");
				}

				LatLng points = computeLatLng.getLatLongPositions(upsformattedAdd);
				addCoordinates = "insert into COORDINATES (CustomerID, Latitude, Longitude)" + " values (\"" + CID
						+ "\",\"" + points.lat + "\", \"" + points.lng + "\")";
				System.out.println(addCoordinates);
				stmt.executeUpdate(addCoordinates);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public ResultSet getLatLngValues(int CID) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("select * from coordinates where CustomerID = \"" + CID + "\"");
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet searchCustomerCoordinates(int CID) {
		ResultSet rs;
		try {
			if (CID != 0) {
				rs = stmt.executeQuery("select * from coor_cust_pub_scrip where CustomerID = " + CID);
			}

			else {
				rs = null;
			}
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Retrieves all customers who have the status == "ACTIVE"
	 */
	public ResultSet getAllCustomers() {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("select * from customers where Status =\"" + "ACTIVE" + "\"");
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet getAllPublications() {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("select * from coor_cust_pub_scrip where PublicationStatus =\"" + "ACTIVE" + "\"");
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the ResultSet containing the set of customers that match the
	 * given information. This method is to accept a CustomerID which is an
	 * integer One customer with the given ID will be in the ResultSet.
	 *
	 * @return ResultSet
	 * @param CID
	 *            The int that identifies the customer to search for
	 **/
	public ResultSet searchForCustomerInView(int CID) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("select * from coor_cust_pub_scrip where CustomerID = " + CID);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the ResultSet containing the set of customers that match the
	 * given information. This method is to accept either a CustomerID or a
	 * name. If the CustomerID is provided, one customer with the given ID will
	 * be in the ResultSet. If a name (first, last, or both) is provided, all
	 * customers that match the given information will be in the ResultSet.
	 *
	 * @return ResultSet
	 * 
	 * @param CID
	 *            The int that identifies the customer to search for
	 * @param fN
	 *            The first name of the customer to search for
	 * @param lN
	 *            The last name of the customer to search for
	 **/
	public ResultSet searchCustomer(int CID, String fN, String lN) {
		ResultSet rs;
		try {
			if (CID != 0) {
				rs = stmt.executeQuery("select * from customers where CustomerID = " + CID);
			} else {
				if (fN.length() == 0)
					rs = stmt.executeQuery("select * from customers where LastName = \"" + lN + "\"");
				else {
					if (lN.length() > 0)
						rs = stmt.executeQuery("select * from customers where FirstName = \"" + fN
								+ "\" and LastName = \"" + lN + "\"");

					else
						rs = stmt.executeQuery("select * from customers where FirstName = \"" + fN + "\"");
				}
			}
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Modifies a specified field of the customer's information. Returns whether
	 * the modification was successfully made.
	 *
	 * @return boolean
	 * 
	 * @param CID
	 *            The int that identifies the customer to be modified
	 * @param type
	 *            The type of information to be modified (FirstName, LastName,
	 *            Address, etc.)
	 * @param to
	 *            The string that the specified information will be modified to
	 **/
	public boolean modCustomerInfo(int CID, String type, String to) {
		try {
			stmt.executeUpdate("update customers set " + type + " = \"" + to + "\" where CustomerID = " + CID);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Modifies all of the customer's information at once in the database.
	 * Returns whether the modification was successfully made. Although it is
	 * not likely that all fields will be modified, any combination of them may
	 * be modified using this method. Does not modify customer status.
	 *
	 * @return boolean
	 * 
	 * @param CID
	 *            The int that identifies the customer to be modified
	 * @param fN
	 *            The customer's new first name
	 * @param lN
	 *            The customer's new last name
	 * @param addLn1
	 *            The customer's new address
	 * @param addLn2
	 *            The customer's new address line two (optional)
	 * @param c
	 *            The customer's new city
	 * @param s
	 *            The customer's new state
	 * @param z
	 *            The customer's new zip code
	 * @param pN
	 *            The customer's new phone number
	 **/
	public boolean modCustomerInfo(int CID, String type, Date to) {
		try {
			stmt.executeUpdate("update customers set " + type + " = \"" + to + "\" where CustomerID = " + CID);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Returns the customer's ID based off of the phone number (unique). This is
	 * necessary because the customer ID is automatically generated within the
	 * database.
	 *
	 * @return int
	 * 
	 * @param pN
	 *            The phone number to search for the customer ID by
	 **/
	public int getCustomerID(String pN) {
		try {
			ResultSet rs = stmt.executeQuery("select * from customers where Phone = \"" + pN + "\"");
			if (rs.next()) {
				return rs.getInt("CustomerID");
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Adds a new publication to the database.
	 * 
	 * @param t
	 *            The title of the publication
	 * @param g
	 *            The genre of the publication
	 * @param p
	 *            The price of the publication
	 * @param d
	 *            The array holding an indicator for whether the publication is
	 *            delivered on each day of the week (Monday to Sunday)
	 * @param f
	 *            The frequency of the publication (Daily, Weekly, Monthly)
	 **/

	public void addPublication(String title, String genre, double price, String frequency, String issuedOn) {
		try {
			stmt.executeUpdate(
					"insert into publications (PublicationName, Genre, Price, Frequency, IssueDate) values (\"" + title
							+ "\", \"" + genre + "\", \"" + price + "\", \"" + frequency + "\", \"" + issuedOn + "\")");

		} catch (Exception e) {
			System.out.println("error in adding pub");
			e.printStackTrace();

		}
	}

	/**
	 * Returns the publication ID based off of the title (unique). This is
	 * necessary because the PunlicationID is automatically generated in the
	 * database.
	 *
	 * @return int
	 * 
	 * @param t
	 *            The title of the publication to get the ID for
	 **/
	public int getPublicationID(String t) {
		try {
			ResultSet rs = stmt.executeQuery("select * from publications where PublicationName = \"" + t + "\"");
			if (rs.next()) {
				return rs.getInt("PublicationID");
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Returns the ResultSet containing publications that fall under the
	 * criteria searched for. If the caller provides a PublicationID, one
	 * publication will be in the ResultSet. If the caller provides a title, all
	 * publications that contain the given title will be in the ResultSet
	 *
	 * @return ResultSet
	 * 
	 * @param PID
	 *            The int that identifies the publication to search for
	 * @param t
	 *            The title of the publication to search for
	 **/
	public ResultSet searchPublication(int PID, String t) {
		ResultSet rs;
		try {
			if (PID != 0) {
				rs = stmt.executeQuery("select * from publications where PublicationID = " + PID);
			} else {
				rs = stmt.executeQuery("select * from publications where PublicationName = \"" + t + "\"");
			}
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Modifies publication price. Returns whether the modification was
	 * successfully made.
	 *
	 * @return boolean
	 * 
	 * @param PID
	 *            The int that identifies the publication to modify
	 * @param newPrice
	 *            The publication's new price
	 **/
	public boolean modPublicationInfo(int PID, double newPrice) {
		try {
			stmt.executeUpdate("update publications set Price = " + newPrice + " where PublicationID = " + PID);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Modifies the publication status. Returns whether the modification was
	 * successfully made.
	 *
	 * @return boolean
	 * 
	 * @param PID
	 *            The in that identifies the publication to modify
	 * @param st
	 *            The new status of the publication
	 **/
	public boolean modPublicationInfo(int PID, String st) {
		try {
			stmt.executeUpdate("update publications set Status = \"" + st + "\" where PublicationID = " + PID);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Closes the connection created in this class.
	 *
	 **/
	public void disconnect() {
		try {
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
