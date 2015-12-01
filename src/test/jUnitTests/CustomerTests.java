package jUnitTests;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertThat;

import java.sql.Statement;

public class CustomerTests {
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/saturdays_db", "root", "12345");
}



	public ResultSet searchCustomerTest(int CID, String fN, String lN) throws SQLException {
		Statement stmt = getConnection().createStatement();
		ResultSet rs;
		try{
			if(CID!=0){
				rs = stmt.executeQuery("select * from customers where CustomerID = " + CID);
			}
			else{
				if(fN.length()==0)
					rs = stmt.executeQuery("select * from customers where LastName = \"" + lN + "\"");
				else{
					if(lN.length()>0)
						rs = stmt.executeQuery("select * from customers where FirstName = \"" + fN + "\" and LastName = \"" + lN + "\"");
					else
						rs = stmt.executeQuery("select * from customers where FirstName = \"" + fN + "\"");
				}
			}
			return rs;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		}
	}
	
