package com.slate;




import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//@WebServlet("/form")
public class something extends HttpServlet{  
public void service(HttpServletRequest req,HttpServletResponse res)  
throws ServletException,IOException  
{  
	
	PrintWriter out= res.getWriter();
	
	try {
			
			Connection con= DatabaseConnection.initializeDatabase(); //static method to connect database
			System.out.println("connected");  
	        
	        //query testing
//	        String sql ="select * from profdeets where email_ID='"+a+"'"; //a is the email we are looking for
//	        Statement statement=con.createStatement();
//	        ResultSet resultSet=null;
//	        resultSet= statement.executeQuery(sql);
//	        
//	        String name=null;
//	        while(resultSet.next()){
//	        	name=resultSet.getString("Name");
//	             passwordvar = resultSet.getString("password");
//	         }
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	//text me if you need anything, WE GOT THIS!
}}  