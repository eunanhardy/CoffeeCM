package hardy.coffee.util;

public class ConnectionConfig {
	
	protected  String username;
	protected  String password;
	protected String url;
	protected String driver;
	
	public ConnectionConfig(){
		
	}
	
	public ConnectionConfig(String _username,String _password,String _url,String _driver){
		this.username = _username;
		this.password = _password;
		this.url = _url;
		this.driver = _driver;
		
	}
	
	
	public void setUsername(String _username){
		this.username = _username;
		
	}
	
	public void setPassword(String _password){
		this.password = _password;
		
	}
	
	public void setUrl(String _url){
		this.url = _url;
		
	}
	
	public void setDriver(String _driver)
	{
		this.driver = _driver;
	}
	

}
