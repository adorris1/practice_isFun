package code;

import java.sql.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class generateCoordinatesList {
	static LinkedList<LatLng> latLngList = new LinkedList<LatLng>();

	public static LinkedList<LatLng> generateList(LinkedList<Integer> customerList) {
		LatLng coordinates;
		connect cn = new connect();
		ResultSet rsCoor;
		// int custID;
		// try{
		// while(r.next()){
		// custID = r.getInt("CustomerID");
		// System.out.println("CustomerId: "+ custID + ",
		// "+r.getString("FirstName") + " " + r.getString("LastName"));
		// cList.add(custID);
		// }
		// }
		// catch(Exception e){
		// e.printStackTrace();
		// }
		for (int i = 0; i < customerList.size(); i++) {
			int ID = customerList.get(i);
			rsCoor = cn.searchCustomerCoordinates(ID);
			try {
				if (rsCoor.next()) {
					double latitude = rsCoor.getDouble("Latitude");
					double longitude = rsCoor.getDouble("Longitude");
					coordinates = new LatLng(ID, latitude, longitude);
					latLngList.add(coordinates);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		LinkedList<LatLng> sortedLatLng = DestinationRouter.distanceSort(latLngList);
		return sortedLatLng;
	}

}
