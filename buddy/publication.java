package code;

import java.sql.ResultSet;
import java.text.*;
import java.util.Date;
import java.util.GregorianCalendar;
import java.lang.Object;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

public class publication {
	private String title;
	private String genre;
	private double price;
	private String frequency;
	protected int PID;
	private String status;
	protected String firstIssuedOn;
	public String nextIssuedOn;
	private connect cn = new connect();
	NumberFormat fmatr = new DecimalFormat("$#.##");

	public publication(String tit, String gen, double prc, String freq, boolean[] weekdays) {

		int intWeekday = 0;
		if (freq == "daily")
			firstIssuedOn = DateTime.getFirstInstanceOf(intWeekday);
		else {
			for (int i = 0; i < weekdays.length; i++) {
				if (weekdays[i])
					intWeekday = i;
			}
			firstIssuedOn = DateTime.getFirstInstanceOf(intWeekday);
		}

		cn.addPublication(tit, gen, prc, freq, firstIssuedOn);
		// firstIssuedOn = issuedOn;
		PID = cn.getPublicationID(title);
		status = "ACTIVE";
		title = tit;
		genre = gen;
		price = prc;
		frequency = freq;
	}

	public publication(int ID) {
		ResultSet r = cn.searchPublication(ID, "");
		try {
			while (r.next()) {
				PID = r.getInt("PublicationID");
				status = r.getString("Status");
				title = r.getString("PublicationName");
				price = r.getDouble("Price");
				frequency = r.getString("Frequency");
				genre = r.getString("Genre");
				firstIssuedOn = r.getString("IssueDate");
				// nextIssuedOn = r.getString(columnIndex)
			}
		} catch (Exception e) {
			PID = 0;
		}
	}

	public String toString() {
		return "[Publication: " + title + ", Genre: " + genre + "\nPrice: " + fmatr.format(price) + ", Frequency: "
				+ frequency + ", firstIssuedOn: " + firstIssuedOn + "]";
	}

	public boolean modPrice(double nPrice) {
		price = nPrice;
		return cn.modPublicationInfo(PID, nPrice);
	}

	/*
	 * This function calculates the next issue date by using the date the
	 * publication was originally issued on (firstIssuedOn) and adding whatever
	 * increment that correlates to it (e.g. Monthly, weekly, daily) enough
	 * times to get to the next issue date.
	 */
	public String getNextIssueDate(String issuedDate, String freq) {

		freq = freq.toLowerCase();
		// System.out.println("freq: " + freq);
		Date today = new Date();
		Date nextDate = DateTime.strToDate(issuedDate);
		if (freq.equals("daily")) {
			while (nextDate.before(today)) {
				String str = DateTime.dateToStr(nextDate);
				String todayStr = DateTime.dateToStr(today);
				if (str.equals(todayStr)) {
					break;
				} else {
					nextDate = DateTime.addOneDay(nextDate);
				}
			}
		} else if (freq.equals("weekly")) {
			while (nextDate.before(today)) {
				String str = DateTime.dateToStr(nextDate);
				String todayStr = DateTime.dateToStr(today);
				if (str.equals(todayStr)) {
					break;
				} else {
					nextDate = DateTime.addOneWeek(nextDate);
					// System.out.println("next date: "+ nextDate + " today : "
					// + today);
				}
			}
		} else if (freq.equals("monthly")) {
			while (nextDate.before(today)) {
				String str = DateTime.dateToStr(nextDate);
				String todayStr = DateTime.dateToStr(today);
				if (str.equals(todayStr)) {
					break;
				} else {
					nextDate = DateTime.addOneMonth(nextDate);
				}
			}
		}
		String next = DateTime.dateToStr(nextDate);
		setNextIssueDate(next);
		return next;
	}

	public boolean setStatus(String st) {
		if (status.equals(st)) {
			return false;
		} else {
			status = st;
			return cn.modPublicationInfo(PID, st);
		}
	}

	public void setNextIssueDate(String next) {
		nextIssuedOn = next;
	}

}
