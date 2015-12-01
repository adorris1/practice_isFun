package code;

// package java;
// import java.DestinationRouter;
// import java.LatLng;
// import java.connect;
// import java.sql.*;
// import java.util.LinkedList;
// import java.io.BufferedReader;
//
// public class BillGenerator {
// LinkedList<LatLng> latLngList = new LinkedList<LatLng>();
// LinkedList<Integer> cList = new LinkedList<Integer>();
// public void generateAllBills(){
// String text = "Hello world";
// BufferedWriter output = null;
// try {
// File file = new File("example.txt");
// output = new BufferedWriter(new FileWriter(file));
// output.write(text);
// } catch ( IOException e ) {
// e.printStackTrace();
// } finally {
// if ( output != null ) output.close();
// }
// }
// }
// public LinkedList<LatLng> generateCoordinatesList(){
// LatLng coordinates;
// connect cn = new connect();
// ResultSet r = cn.getAll();
// ResultSet rsCoor;
// int custID;
// try{
// while(r.next()){
// custID = r.getInt("CustomerID");
// System.out.println("CustomerId: "+ custID + ", "+r.getString("FirstName") + "
// " + r.getString("LastName"));
// cList.add(custID);
// }
// }
// catch(Exception e){
// e.printStackTrace();
// }
// for(int j =0; j<latLngList.size();j++){
// System.out.println(latLngList.get(j));
// }
// LinkedList<LatLng> sortedLatLng = DestinationRouter.distanceSort(latLngList);
// for(int j =0; j<sortedLatLng.size();j++){
// System.out.println(sortedLatLng.get(j));
// }
// return sortedLatLng;
// }
//
//
// }
//
//
