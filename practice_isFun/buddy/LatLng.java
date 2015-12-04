package code;

public class LatLng {
	public int CID;
	public double lat;
	public double lng;

	public LatLng(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
		CID = 0;
	}

	public LatLng() {
		lat = 0.0;
		lng = 0.0;
		CID = 0;
	}

	public LatLng(connect cn, int CID) {
		cn.addLatLngToCustomer(CID);
	}

	public LatLng(int CID, double lat, double lng) {
		this.CID = CID;
		this.lat = lat;
		this.lng = lng;

	}

	public String toString() {
		return "[CustomerID: " + CID + " Latitude: " + lat + ", Longitude: " + lng + "]";
	}

}