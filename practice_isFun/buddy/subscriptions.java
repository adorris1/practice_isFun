package code;

import java.text.*;
import java.util.Date;
import java.util.GregorianCalendar;
import java.lang.Object;
import java.util.Calendar;
import java.util.*;

public class subscriptions {
	private int CID;
	private int SID;
	private int PID;
	public double totalPrice;
	protected static Date startDate;
	protected static Date endDate;
	NumberFormat fmatr = new DecimalFormat("#0.00");
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	public subscriptions(connect cn, int CID, int PID) {
		startDate = DateTime.getTimeNow();
		setStartDate(startDate);
		endDate = DateTime.addOneYear(startDate);
		setEndDate(endDate);
		cn.addSubscriptions(CID, PID, DateTime.dateToStr(startDate), DateTime.dateToStr(endDate));
		SID = cn.getSubscriptionID(CID);
		totalPrice = 0;

	}

	public subscriptions(int SD, int ID, double tot) {
		CID = ID;
		SID = SD;
		totalPrice = tot;

	}

	public double getTotal() {
		return totalPrice;
	}

	public static void setStartDate(Date start) {
		startDate = start;
	}

	public static void setEndDate(Date end) {
		endDate = end;
	}

	public static Date getStartDate() {
		return startDate;
	}

	public static Date getEndDate() {
		return endDate;
	}

}
