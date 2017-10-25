package hardy.coffee.test;

import java.io.IOException;
import java.sql.SQLException;



import hardy.coffee.db.ConnectionManager;

public class CoffeeBrewer {

	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub
		
		//Sandbox for you to test out your examples
		ConnectionManager db = new ConnectionManager("/Users/eunanhardy/Desktop/dbConfig.txt");
		Object[] params = {"asd@asd.com","password2","eunan","hardy",1,"test"};
		 db.executeStoredProcedure("{call project.sp_addAccount(?,?,?,?,?,?)}", params);
		
		
		
		

	}

}
