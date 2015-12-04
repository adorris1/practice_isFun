//
//
//import java.util.LinkedList;
//import java.sql.*;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.lang.Object;
//import java.util.Calendar;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.text.DateFormat;
//import java.time.format.DateTimeFormatter;
//import java.text.SimpleDateFormat;
//
//import java.time.LocalDate; 
//import java.time.LocalDateTime;
//
//import java.util.*;
//public class TodaysCustomerDeliveries {
//	
//	static LinkedList<Union> todaysPubList = new LinkedList<Union>();
//	
//	public static LinkedList<Union> generateTodaysCustDeliveries(){
//		publication todaysPub;
//		Union un;
//		connect cn = new connect();
//		ResultSet rs = cn.getAllPublications();
//		
//		try{
//			while(rs.next()){
//
//				//String issueDate = rsPub.getString("IssueDate");
//				//String freq = rsPub.getString("Frequency");
//				un = new Union(cn,rs.getInt("CustomerID"),rs.getInt("PublicationID"));
//				todaysPub = new publication(cn,rs.getInt("PublicationID"));
//				String nextDate = todaysPub.getNextIssueDate(rs.getString("IssueDate"), rs.getString("Frequency"));
//				Date today = DateTime.getTimeNow();
//				//Date date = DateTime.strToDate(nextDate);
//				String todayStr = DateTime.dateToStr(today);
//				
//				if(nextDate.equals(todayStr)){
//					//todaysPub = new publication(rsPub.getInt("PublicationID"));
//					//System.out.println( "line 32 : "+todaysPub);
//					System.out.println("IssueDate: "+ rs.getString("IssueDate") + " NextIssueDate: "+ nextDate+  " of pub ID: "+ rs.getInt("PublicationID"));
//
//					todaysPubList.add(un);
//				}
//				
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}		
//	return todaysPubList;
//
//}}
