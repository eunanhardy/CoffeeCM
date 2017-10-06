package hardy.coffee.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ToolsPod {
	
	
	public void closeConnection(Connection comm){
		try {
			comm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
