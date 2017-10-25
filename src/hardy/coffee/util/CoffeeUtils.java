package hardy.coffee.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.jdbc.CallableStatement;


public class CoffeeUtils {
	
	protected JSONArray returnJSON(ResultSet rs)
	{
		JSONArray array = new JSONArray();
		try{
			ResultSetMetaData resultSetMeta = rs.getMetaData();
			int columnCount = resultSetMeta.getColumnCount();
			String[] columnNames = new String[columnCount];
			
			for(int index = 0;index < columnCount;index++)
			{
				columnNames[index] = resultSetMeta.getColumnLabel(index+1);
			}
			
			while(rs.next())
			{
				JSONObject obj = new JSONObject();
				for(int index = 0;index < columnCount;index++)
				{
					String key = columnNames[index];
					Object value = rs.getObject(index+1);
					obj.put(key, value);
				}
				array.put(obj);
			}
			
			
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			closeResultSet(rs);
		}
		
		return array;
		
	}
	
	
	public List<Map<String,Object>> ResultSetToList(ResultSet rs)
	{
		List<Map<String,Object>> list = new ArrayList<>();
		try{
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columnCount = rsMeta.getColumnCount();
			String[] columnNames = new String[columnCount];
		
			for(int index = 0;index < columnCount;index++)
			{
				columnNames[index] = rsMeta.getColumnLabel(index+1);
			}
			
			while(rs.next())
			{
				Map<String, Object> map = new HashMap<>();
				for(int index = 0;index < columnCount;index++)
				{
					String key = columnNames[index];
					Object value = rs.getObject(index+1);
					map.put(key, value);
				}
				list.add(map);
				
			}
		
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public Connection getConnection(ConnectionConfig config) throws SQLException{
			BasicDataSource bs = new BasicDataSource();
			bs.setUsername(config.username);
			bs.setPassword(config.password);
			bs.setUrl(config.url);
			bs.setDriverClassName(config.driver);
			
			return bs.getConnection();
	}
	
	public void closeConnection(Connection comm){
		if(comm != null){
		try {
			comm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	public void closeResultSet(ResultSet rs){
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closePrepStatement(PreparedStatement prep){
		try {
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeCall(java.sql.CallableStatement callStatement){
		if(callStatement != null){
		try {
			callStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	}

}
