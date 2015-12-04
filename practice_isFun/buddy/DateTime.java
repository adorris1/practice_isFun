package code;

import java.util.Date;
import java.util.GregorianCalendar;
import java.lang.Object;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.*;

public class DateTime {
	public static Date strToDate(String strDate) {
		Date result;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			result = df.parse(strDate);

		} catch (ParseException pe) {
			pe.printStackTrace();
			result = null;
		}
		return result;

	}

	public static String dateToStr(Date date) {

		String newstring = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return newstring;
	}

	public static Date getTimeNow() {
		Date now = new Date();

		return now;
	}

	public static Date addOneYear(Date start) {
		Calendar myCal = Calendar.getInstance();
		myCal.setTime(start);
		myCal.add(Calendar.YEAR, 1);

		return myCal.getTime();
	}

	public static Date addOneWeek(Date fromHere) {
		Calendar myCal = Calendar.getInstance();
		myCal.setTime(fromHere);
		myCal.add(Calendar.WEEK_OF_MONTH, 1);
		return myCal.getTime();
	}

	public static Date addOneDay(Date fromHere) {
		Calendar myCal = Calendar.getInstance();
		myCal.setTime(fromHere);
		myCal.add(Calendar.DAY_OF_MONTH, 1);
		return myCal.getTime();
	}

	public static Date addOneMonth(Date fromHere) {
		Calendar myCal = Calendar.getInstance();
		myCal.setTime(fromHere);
		myCal.add(Calendar.MONTH, 1);

		return myCal.getTime();
	}

	public static int getDayOfTheWeek(Date fromHere) {
		Calendar myCal = Calendar.getInstance();
		myCal.setTime(fromHere);
		return myCal.DAY_OF_WEEK;
	}

	public static String getDayFromNum(int i) {
		String day = "";
		switch (i) {
		case 0:
			day = "Monday";
			break;

		case 1:
			day = "Tuesday";
			break;
		case 2:
			day = "Wednesday";
			break;
		case 3:
			day = "Thursday";
			break;
		case 4:
			day = "Friday";
			break;
		case 5:
			day = "Saturday";
			break;
		case 6:
			day = "Sunday";
			break;
		}
		return day;

	}

	public static String getFirstInstanceOf(int day) {
		String strDay = getDayFromNum(day);
		LocalDate now = LocalDate.now();
		LocalDate date = LocalDate.of(now.getYear(), now.getMonthValue(), 1);
		if (strDay == "Monday") {
			for (int i = 0; i < date.lengthOfMonth(); i++) {
				if ("Monday".equalsIgnoreCase(date.getDayOfWeek().toString())) {
					break;
				} else {
					date = date.plusDays(1);
				}
			}

		} else if (strDay == "Tuesday") {
			for (int i = 0; i < date.lengthOfMonth(); i++) {
				if ("Tueday".equalsIgnoreCase(date.getDayOfWeek().toString())) {
					break;
				} else {
					date = date.plusDays(1);
				}
			}
		} else if (strDay == "Wednesday") {
			for (int i = 0; i < date.lengthOfMonth(); i++) {
				if ("Wednesday".equalsIgnoreCase(date.getDayOfWeek().toString())) {
					break;
				} else {
					date = date.plusDays(1);
				}
			}
		} else if (strDay == "Thursday") {
			for (int i = 0; i < date.lengthOfMonth(); i++) {
				if ("Thursday".equalsIgnoreCase(date.getDayOfWeek().toString())) {
					break;
				} else {
					date = date.plusDays(1);
				}
			}
		} else if (strDay == "Friday") {
			for (int i = 0; i < date.lengthOfMonth(); i++) {
				if ("Friday".equalsIgnoreCase(date.getDayOfWeek().toString())) {
					break;
				} else {
					date = date.plusDays(1);
				}
			}
		} else if (strDay == "Saturday") {
			for (int i = 0; i < date.lengthOfMonth(); i++) {
				if ("Saturday".equalsIgnoreCase(date.getDayOfWeek().toString())) {
					break;
				} else {
					date = date.plusDays(1);
				}
			}
		} else if (strDay == "Sunday") {
			for (int i = 0; i < date.lengthOfMonth(); i++) {
				if ("Sunday".equalsIgnoreCase(date.getDayOfWeek().toString())) {
					break;
				} else {
					date = date.plusDays(1);
				}
			}
		}
		String dayOfMonth = " ";
		if (date.getDayOfMonth() < 10) {
			dayOfMonth = "0" + date.getDayOfMonth();
		} else
			dayOfMonth = "" + date.getDayOfMonth();
		return date.getYear() + "-" + date.getMonthValue() + "-" + dayOfMonth;
	}
	// public Calendar getDayFromIntValue(int dayNum){
	// Calendar weekDay = Calendar.getInstance();
	// switch(dayNum){
	// case 1: weekDay.set
	// }
}
