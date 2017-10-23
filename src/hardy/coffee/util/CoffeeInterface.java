package hardy.coffee.util;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface CoffeeInterface {
	
	
	public JSONObject querySQL(String responseName,String sql);
	
	public JSONObject querySQL(String responseName,String sql,Object[] params);
	
	public JSONObject querySQL(String responseName,String sql,Object params);
	
	public List<Map<String,Object>> listQuery(String sql);
	
	public List<Map<String,Object>> listQuery(String sql,Object[] params);
		
	public void executeSQL(String sql);
	
	public void executeSQL(String sql,Object[] params);

	void executeSQL(String sql, Object param);

	int executeSQLReturnID(String sql, Object[] params);

	int executeSQLReturnID(String sql, Object param);

	List<Map<String, Object>> listQuery(String sql, Object param);
	

}
