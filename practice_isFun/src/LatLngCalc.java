import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;

/**
 * This class will get the lat long values.
 * @author SANTHOSH REDDY MANDADI
 * @version 1.0
 * @since 20-Sep-2012
 */
public class LatLngCalc
{
	
//  public static void main(String[] args) throws Exception
//  {
//    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//    String postcode ="";
//    while (postcode != "****") {
//    	System.out.println("Please enter a location:");
//       postcode=reader.readLine();
//       LatLng points = new LatLng();
//    	 points = getLatLongPositions(postcode);
//       System.out.println("Latitude: "+ points.lat+" and Longitude: "+ points.lng);
//    }
//   
//  }
  
  public static LatLng getLatLongPositions(final String address) throws Exception
  {
    int responseCode = 0;
    String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
    URL url = new URL(api);
    HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
    httpConnection.connect();
    responseCode = httpConnection.getResponseCode();
    if(responseCode == 200)
    {
    	System.out.println("in computeLatLng responsecode 200");
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
      Document document = builder.parse(httpConnection.getInputStream());
      XPathFactory xPathfactory = XPathFactory.newInstance();
      XPath xpath = xPathfactory.newXPath();
      XPathExpression expr = xpath.compile("/GeocodeResponse/status");
      String status = (String)expr.evaluate(document, XPathConstants.STRING);
      if(status.equals("OK"))
      {
	    	System.out.println("in computeLatLng status OK");

	    	expr = xpath.compile("//geometry/location/lat");
	         String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
	         expr = xpath.compile("//geometry/location/lng");
	         String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
	         //return new String[] {latitude, longitude};
         LatLng newCoor = new LatLng(latitude, longitude);
         System.out.print("hello from computerLatLng   Lat: " + newCoor.lat + " long: " + newCoor.lng);
         return newCoor;
        // return new String[] {latitude, longitude};
      }
      else
      {
	    	System.out.println("in computeLatLng exception");

         throw new Exception("Error from the API - response status: "+status);
      }
    }
    return null;
  }

}
