package hardy.coffee.main;

import java.io.IOException;
import java.sql.SQLException;

import hardy.coffee.db.ConnectionManager;

public class CoffeeMain {

	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
		ConnectionManager db = new ConnectionManager("/Users/eunanhardy/Desktop/dbConfig.ccm");
		
		System.out.println(db.querySQL("Test", "SELECT * FROM test.users"));

	}

}
