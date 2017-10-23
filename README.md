# CoffeeCM(Coffee Connection Manager) For Java
## Version 1.0

Coffee is a utility designed to streamline the process of connecting,managing and closing MySQL and SQLServer connections within your java applications.
Coffee is designed to help developers design and create RESTful web services, quick and easily. simply use one of the built-in methods to execute your SQL query and it will output the result in a json format ready for output.

## Installation

download dependencies.zip and CoffeeCM.jar and include them in your projects build path.
### dependencies - 23/10/2017
linked below are the source of all required dependencies. 
- [commons-dbcp-1.4.jar](https://mvnrepository.com/artifact/commons-dbcp/commons-dbcp/1.4)
- [commons-pool-1.6.jar](https://mvnrepository.com/artifact/commons-pool/commons-pool/1.6)
- [json-20151123.jar](https://mvnrepository.com/artifact/org.json/json/20151123)
- [mysql-connector-java-5.1.13.jar - For MySQL Connections](https://mvnrepository.com/artifact/mysql/mysql-connector-java/5.1.13)
- [sqljdbc4.jar - For SQLServer Connections](https://mvnrepository.com/artifact/com.microsoft.sqlserver/sqljdbc4/4.0)

---
Follow progress on trello: https://trello.com/b/YvkpDXep

# Usage Examples
## Setting up  
 
#### Load config from file 
```sh
ConnectionManager db = new ConnectionManager("/Users/eunanhardy/Desktop/dbConfig.txt");
```
#### dbConfig.txt
```sh
username: SQL_USER
password: SQL_PASS
url:jdbc:mysql://127.0.0.1
driver: com.mysql.jdbc.Driver
```

#### Passing in Config
```sh
ConnectionManager db = new ConnectionManager("SQL_USER","SQL_PASS","jdbc:mysql://127.0.0.1","com.mysql.jdbc.Driver");
```
## Querying the Database

#### simple query
```sh
System.out.println(db.querySQL("Test", "SELECT * FROM test.users"));
```
#### Response
```sh
{"Test":[{"firstname":"John","id":1,"lastname":"Doe"},{"firstname":"Jane","id":2,"lastname":"Doe"}]}
```
#### Query with single parameter 
```sh
System.out.println(db.querySQL("Test", "SELECT * FROM test.users WHERE id = ?",1));
```

#### Response
```sh
{"Test":[{"firstname":"John","id":1,"lastname":"Doe"}]}
```

#### Querying with multiple parameters
```sh
		Object[] params = {1,"jane"};
        System.out.println(db.querySQL("Test", "SELECT * FROM test.users WHERE id = ? AND firstname = ?",params));
```

all methods use this same format shown above to handle SQL parameters 

This is only small fraction of the features offered by CoffeeCM so please feel free to download the jar, inspect the code and check it out for yourself.
---
