package code;

import java.io.*;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.nio.file.*;

public class print {
	private connect cn;
	private user u;
	private String filePath;
	private String billMonth;
	private NumberFormat fmt = new DecimalFormat("#0.00");

	// daily summary:
	// daily route: list customer with address,
	public print(connect con) {
		cn = con;
		ResultSet r = cn.userGetfilePath();
		try {
			while (r.next()) {
				filePath = r.getString("FilePath");
			}
		} catch (Exception e) {
			filePath = null;
		}
	}

	// invoice number in name of text file
	public void printAllBills() {
		billMonth = DateTime.getLastMonth();
		String invoiceNum;
		double totalDue;
		try {
			ResultSet allActive = cn.getAll(), s;
			while (allActive.next()) {
				customer tempc = new customer(cn, allActive.getInt("CustomerID"));
				invoiceNum = DateTime.getDateNameFile() + "-" + String.valueOf(tempc.getCID());
				File f = new File(filePath, invoiceNum + ".txt");
				BufferedWriter w = new BufferedWriter(new FileWriter(f));
				w.write(billHeader() + invoiceNum);
				w.write("\r\n\r\nBill/Ship To: \r\n" + tempc.getFullName() + "\r\n" + tempc.getAddress()
						+ "\r\n\r\nPublication Information\r\t\r\tSubscription Period\r\t\r\tPrice\r\n");
				totalDue = 0;
				s = cn.getSubscriptions(tempc.getCID());
				while (s.next()) {
					subscriptions temps = new subscriptions(cn, s.getInt("ItemID"));
					publication tempp = temps.getPubInfo();
					w.write(tempp.getBillTitle() + "\r\t\r\t" + temps.getPeriod() + "\r\t\r\t"
							+ fmt.format(tempp.getPrice()) + "\r\n");
					totalDue += tempp.getPrice();
				}
				w.write("\r\n\r\n\r\n\tTOTAL DUE: " + fmt.format(totalDue));
				w.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String billHeader() {
		u = new user(cn);
		return u.getCompanyName() + "\r\n" + u.getAddress() + "\r\nPhone: " + u.getCSPhone() + "\r\nE-mail: "
				+ u.getCSEmail() + "\r\n\r\nINVOICE\t\t#";
	}
}