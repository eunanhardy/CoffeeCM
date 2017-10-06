package hardy.coffee.db;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.json.JSONObject;
import org.json.JSONTokener;

import hardy.coffee.util.*;

public class ConnectionManager extends ProcessingPod implements ManagerPod  {
	
	public Connection connection = null;
	
	/**
	 * Defines connection information for thise instance of the ConnectionManager class
	 * @param username - the username you will use to login to your sql server
	 * @param password - the password you would use to login to your sql server
	 * @param url - This can be an IP address or domain name you would use to establish a connection to your sql server
	 * @param driver - we use this to define what type of SQL server we are connecting to currently this package only support MySQL(com.mysql.jdbc.Driver) and Microsoft SQLServer Drivers(com.microsoft.sqlserver.jdbc.SQLServerDriver)  
	 * @throws SQLException 
	 *
	 */
	public ConnectionManager(String username,String password,String url,String driver) throws SQLException{
		BasicDataSource bs = new BasicDataSource();
		bs.setUsername(username);
		bs.setPassword(password);
		bs.setUrl(url);
		bs.setDriverClassName(driver);
		
		connection = bs.getConnection();
		System.out.println("Database Connection Established");
	}
	/***
	 * 
	 * If you want to store your database info in a file we have you covered simple reference it absolute file path. database Config files should be in this format 
	 * <pre>
	 * username:YOUR_USERNAME
	 * password:YOUR_PASSWORD
	 * url:DATABASE_URL
	 * driver:DRIVER
	 * </pre>
	 * @param filePath
	 * @throws IOException
	 * @throws SQLException
	 */
	public ConnectionManager(String filePath) throws IOException, SQLException{
		FileInputStream file = new FileInputStream(filePath);
		BasicDataSource bs = new BasicDataSource();
		Properties prop = new Properties();
		
		prop.load(file);
		String user = prop.getProperty("username");
		String pass = prop.getProperty("password");
		String url = prop.getProperty("url");
		String driver = prop.getProperty("driver");
		
		
		
		bs.setUsername(user);
		bs.setPassword(pass);
		bs.setUrl(url);
		bs.setDriverClassName(driver);
		
		connection = bs.getConnection();
		System.out.println("Database Connection Established");
		
	}

	@Override
	public JSONObject querySQL(String responseName, String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject querySQL(String responseName, String sql, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> listQuery(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> listQuery(String sql, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeSQL(String sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeSQL(String sql, Object[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeSQL(String sql, Object param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int executeSQLReturnID(String sql, Object[] params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeSQLReturnID(String sql, Object param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> listQuery(String sql, Object param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String testConnection() {
		// TODO Auto-generated method stub
		return null;
	}



}
