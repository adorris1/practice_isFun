import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	 public static void main(String[] args) throws Exception
	  {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    String postcode ="";
	    while (postcode != "****") {
	    	System.out.println("Please enter a location:");
	       postcode=reader.readLine();
	       LatLng points = new LatLng();
	    	 points = LatLngCalc.getLatLongPositions(postcode);
	       System.out.println("Latitude: "+ points.lat+" and Longitude: "+ points.lng);
	    }
	   
	  }

}
