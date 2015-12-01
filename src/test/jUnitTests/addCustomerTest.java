package jUnitTests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import code.customer;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import code.connect;
import java.sql.*;
import static org.junit.Assert.assertThat;
import static org.h2.engine.Constants.UTF8;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.BeforeClass;



/** * * @author Munna * Test class for SalaryCalcutation using DBUNIT * */
public class addCustomerTest extends DatabaseTestCase {
	connect cn = new connect();

	public static final String TABLE_LOGIN = "salarydetails";
	private FlatXmlDataSet loadedDataSet;
	private customer customer;
	private CustomerTests CustomerTests;
	private Connection jdbcConnection;

	/** * Provide a connection to the database * @return IDatabaseConnection */
	protected IDatabaseConnection getConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		jdbcConnection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/saturdays_db", "root", "12345");
		return new DatabaseConnection(jdbcConnection);
	}

	private IDataSet readDataSet() throws Exception {
		loadedDataSet = new FlatXmlDataSetBuilder().build(new File("customerX.xml"));
		return loadedDataSet;
	}

	/** * Load the data which will be inserted for the test * @return IDataSet */
	

	/** * Test case for calculator * positive scenario---Valid Employee */
	@Test
	public void testSearchCustomer() throws SQLException {
		ResultSet rs = CustomerTests.searchCustomerTest(0, "Ashley", "");
		
		try{
			while(rs.next()){
				int id = rs.getInt("CustomerID");
				 customer = new customer(id);
			}
		}catch(Exception e){
			e.printStackTrace();}		
		assertThat(customer.getFirstName(), is("Ashely"));
		assertThat(customer.getLastName(), is("Dorris"));
		assertThat(customer.getAddress(), is("607 Robert Ave"));
	}
	/** *Test case for calculator *negative scenario---InValid Employee */
	@Test
	public void testSearchCustomerNeg() throws SQLException {
		ResultSet rs = cn.searchCustomer(0, "Charlie", "");
		try{
			while(rs.next()){
				int id = rs.getInt("CustomerID");
				 customer = new customer(id);
			}
		}catch(Exception e){
			e.printStackTrace();}		
		assertThat(customer.getFirstName(), is("Charlie"));
		
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}