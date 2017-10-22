package hardy.coffee.db;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.json.JSONObject;

import hardy.coffee.util.*;

public class ConnectionManager extends ProcessingPod implements ManagerPod  {
	
	private ConnectionConfig comConfig = null;
	
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
		comConfig = new ConnectionConfig(username,password,url,driver);
		
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
		
		Properties prop = new Properties();
		
		prop.load(file);
		comConfig = new ConnectionConfig();
		comConfig.setUsername(prop.getProperty("username"));
		comConfig.setPassword( prop.getProperty("password"));;
		comConfig.setUrl(prop.getProperty("url"));
		comConfig.setDriver(prop.getProperty("driver"));
		
		
		
	}
	
	/***
	 *  Returns your SQL query as a JSON array.
	 *  
	 * @param responseName - Name of the JSON array that will be returned as your SQL result.
	 * @param sql - your SQL statement.
	 */
	
	@Override
	public JSONObject querySQL(String responseName, String sql) {
		// TODO Auto-generated method stub
		JSONObject jsonResponse = new JSONObject();
		PreparedStatement psStatement = null;
		ResultSet result = null;
		
		
		try{
			Connection connect = getConnection(comConfig);
			psStatement = connect.prepareStatement(sql);
			result = psStatement.executeQuery();
			jsonResponse.put(responseName, returnJSON(result));
			
		}catch(SQLException sql_exception){
			sql_exception.printStackTrace();
		}finally{
			closeResultSet(result);
			closePrepStatement(psStatement);
			
		}
		
		
		return jsonResponse;
	}
	
	/***
	 * Returns your SQL query as a JSON array. You can pass an array of Objects as SQL parameters(?). The order of elements should match.
	 * 
	 * @param responseName - Name of the JSON array that will be returned as your SQL result.
	 * @param sql - your SQL statement.
	 * @param params - Array of Objects as SQL Parameters  
	 * 
	 */

	@Override
	public JSONObject querySQL(String responseName, String sql, Object[] params) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet result = null;
		
		try{
			connect = getConnection(comConfig);
			psStatement = connect.prepareStatement(sql);
			
			for(int index = 0;index < params.length;index++)
			{
				psStatement.setObject(index+1, params[index]);
			}
			
			result = psStatement.executeQuery();
			//System.out.println(ResultSetToJSON(result));
			json.put(responseName, returnJSON(result));
			
		}catch(SQLException sqlEx){
			sqlEx.printStackTrace();
			
		}finally{
			closeResultSet(result);
			closePrepStatement(psStatement);
			closeConnection(connect);
		}
		
		
		
		return json;
	}
	
	/***
	 * Returns your SQL query as a JSON array. You can pass an array of Objects as SQL parameter(?).
	 * 
	 * @param responseName - Name of the JSON array that will be returned as your SQL result.
	 * @param sql - your SQL statement.
	 * @param params - Generic Object as SQL parameter.  
	 * 
	 */
	
	@Override
	public JSONObject querySQL(String responseName, String sql, Object params) {
		JSONObject json = new JSONObject();
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet result = null;
	
	try{
		connect = getConnection(comConfig);
		
		psStatement = connect.prepareStatement(sql);
		

			psStatement.setObject(1, params);

		
		result = psStatement.executeQuery();
		//System.out.println(ResultSetToJSON(result));
		json.put(responseName, returnJSON(result));
		
	}catch(SQLException sqlEx){
		sqlEx.printStackTrace();
		
	}finally{
		closeResultSet(result);
		closePrepStatement(psStatement);
		closeConnection(connect);
	}
	
	
	
	return json;
	}
	
	/***
	 * Returns query results as a List of key value pairs representing rows and columns.
	 * @param sql - SQL statement.
	 * 
	 */
	
	@Override
	public List<Map<String, Object>> listQuery(String sql) {
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet result = null;
		List<Map<String,Object>> list = null;
		
		
		try{
			connect = getConnection(comConfig);
			
			psStatement = connect.prepareStatement(sql);
			result = psStatement.executeQuery();
			list = ResultSetToList(result);
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally{
			closeConnection(connect);
			closePrepStatement(psStatement);
			closeResultSet(result);
		}
		
		return list;
	}

	
	/***
	 * Returns query results as a List of key value pairs representing rows and columns. You may pass an array of Objects as SQL Parameters.
	 * 
	 * @param sql - SQL statement
	 * @param params - array of Objects that represent your SQL parameters 
	 */
	@Override
	public List<Map<String, Object>> listQuery(String sql, Object[] params) {
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet result = null;
		List<Map<String,Object>> list = null;
		
		
		try{
			connect = getConnection(comConfig);
			psStatement = connect.prepareStatement(sql);
			
			for(int index =0;index < params.length;index++)
			{
				psStatement.setObject(index+1, params[index]);
			}
			
			result = psStatement.executeQuery();
			list = ResultSetToList(result);
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally{
			
			closeConnection(connect);
			closePrepStatement(psStatement);
			closeResultSet(result);
		}
		
		return list;
	}
	
	/***
	 * Execute SQL without returning a result. used for UPDATE,DELETE and INSERT actions
	 * @param sql - SQL statement
	 */

	@Override
	public void executeSQL(String sql) {
		// TODO Auto-generated method stub
		Connection connect = null;
		PreparedStatement psStatement = null;
		
		try{
			connect = getConnection(comConfig);
			psStatement = connect.prepareStatement(sql);
			psStatement.executeUpdate();
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			closeConnection(connect);
			closePrepStatement(psStatement);
		}
		
	}
	
	/***
	 * Execute SQL without a result, can pass in SQL parameters via a Object array
	 * 
	 * @param sql - SQL statement
	 * @param params - an array of generic Objects
	 * 
	 */
	
	@Override
	public void executeSQL(String sql, Object[] params) {
		// TODO Auto-generated method stub
		Connection connect = null;
		PreparedStatement psStatement = null;
		
		try{
			connect = getConnection(comConfig);
			psStatement = connect.prepareStatement(sql);
			
			for(int index = 0;index < params.length;index++)
			{
				psStatement.setObject(index+1, params[index]);
			}
			
			psStatement.executeUpdate();
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			closeConnection(connect);
			closePrepStatement(psStatement);
		}
		
	}
	
	/***
	 * 
	 * 
	 * 
	 */

	@Override
	public void executeSQL(String sql, Object param) {
		Connection connect = null;
		PreparedStatement psStatement = null;
		
		try{
			connect = getConnection(comConfig);
			psStatement = connect.prepareStatement(sql);
			
			
				psStatement.setObject(1, param);
			
			
			psStatement.executeUpdate();
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			closeConnection(connect);
			closePrepStatement(psStatement);
		}
		
	}

	@Override
	public int executeSQLReturnID(String sql, Object[] params) {
		
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet rsRow = null;
		int generatedID = 0;
		try{
			connect = getConnection(comConfig);
			psStatement = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			for(int index = 0;index < params.length;index++)
			{
				psStatement.setObject(index+1, params[index]);
			}
			
			psStatement.executeUpdate();
			rsRow = psStatement.getGeneratedKeys();
			
			while(rsRow.next())
			{
				generatedID = rsRow.getInt(1);
			}
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			closeConnection(connect);
			closePrepStatement(psStatement);
		}
		
		return generatedID;
	}

	@Override
	public int executeSQLReturnID(String sql, Object param) {
		// TODO Auto-generated method stub
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet rsRow = null;
		int generatedID = 0;
		try{
			connect = getConnection(comConfig);
			psStatement = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			

			
				psStatement.setObject(1, param);
			
			
			psStatement.executeUpdate();
			rsRow = psStatement.getGeneratedKeys();
			
			while(rsRow.next())
			{
				generatedID = rsRow.getInt(1);
			}
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			closeConnection(connect);
			closePrepStatement(psStatement);
		}
		
		return generatedID;
	}

	@Override
	public List<Map<String, Object>> listQuery(String sql, Object param) {
		// TODO Auto-generated method stub
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet result = null;
		List<Map<String,Object>> list = null;
		
		
		try{			
			connect = getConnection(comConfig);
			psStatement = connect.prepareStatement(sql);
			psStatement.setObject(1, param);

			result = psStatement.executeQuery();
			list = ResultSetToList(result);
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally{
			
			closeConnection(connect);
			closePrepStatement(psStatement);
			closeResultSet(result);
		}
		
		return list;
	}

	
	


	



}
