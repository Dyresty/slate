package com.slate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbQ {
	public static void teachID(Connection con, String SubID ) throws SQLException, ClassNotFoundException{
		
		Statement statement=con.createStatement();
		String sql ="select s.SubID "
				+ "from subjects"
				+ "where (Department=''or Department='Math')and Semester="; 
		
		ResultSet resultSet=null;
		resultSet= statement.executeQuery(sql);
	}
}
