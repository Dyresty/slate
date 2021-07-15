package com.slate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
  
// This class can be used to initialize the database connection, we ARE using it now, PROGRESS
public class DatabaseConnection {
    protected static Connection initializeDatabase()
        throws SQLException, ClassNotFoundException
    {
        // Initialize all the information regarding database
    	String dbURL = "jdbc:mysql://localhost:3306/";
        String dbName = "slatedbs2";
        String dbUsername = "root";
        String dbPassword = "";
        String dbDriver = "com.mysql.jdbc.Driver";
        Class.forName(dbDriver);
        // Database Connection
        Connection con = DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword);
        return con; //returning the connection
    }
}