package com.slate;




import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class Form extends HttpServlet{  
public void service(HttpServletRequest req,HttpServletResponse res)  
throws ServletException,IOException  
{  
	try {
			//servlet requests
			String a= req.getParameter("Lemail");
			String b= req.getParameter("Lpass");
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			String passwordvar=null;
			  // Database name to access
	        String dbURL = "jdbc:mysql://localhost:3307/";
	        String dbName = "professors";
	        String dbUsername = "root";
	        String dbPassword = "";
	        String dbDriver = "com.mysql.jdbc.Driver";
	        Class.forName(dbDriver);
	        Connection con = DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword);
	        System.out.println(a+"\n"+b+"\n");//checking for database connection+if servlet returned values
	        
	        
	        //query testing
	        String sql ="select * from profdeets where email_ID='"+a+"'"; //a is the email we are looking for
	        Statement statement=con.createStatement();
	        ResultSet resultSet=null;
	        resultSet= statement.executeQuery(sql);
	        
	        String name=null;
	        while(resultSet.next()){
	        	name=resultSet.getString("Name");
	             passwordvar = resultSet.getString("password");
	         }
	        System.out.println(b+"\n"+ passwordvar+"\n");
	        if(b.equals(passwordvar)&&!passwordvar.equals(null)) {
	        	out.println("<h2> Welcome, "+name+"<br> Happy to have you here!</h2>");
	        	
	        }
	        else
	        {
	        	//alert("Wrong Email ID or Password has been entered. Redirecting...");
	        	res.sendRedirect("submit3.html");
	        }
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	//text me if you need anything, WE GOT THIS!
}}  