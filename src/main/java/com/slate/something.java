package com.slate;




import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
			String name=null;
			String Sub=null;
			int hours=0;
			String Dept=req.getParameter("Dept");
			int Sem=Integer.parseInt(req.getParameter("Sem"));
			String Sec=req.getParameter("Sec");
			res.setContentType("text/html");
			Connection con= DatabaseConnection.initializeDatabase(); //static method to connect database
	        //query testing
			Statement statement=con.createStatement();
			out.println(Dept+" "+Sem+" "+Sec);
			String sql ="select t.TeachName, s.SubName, s.HoursPerWeek "
					+ "from subjects s, teachers t, subteach st "
					+ "where s.Department='"+Dept+"'and s.Semester="+Sem+" and s.SubID=st.SubID and st.TeachID=t.TeachID"; 
	        ResultSet resultSet=null;
			resultSet= statement.executeQuery(sql);
			out.println(Dept+" "+Sem+" "+Sec);
        while(resultSet.next()){
        	name=resultSet.getString("TeachName");
        	Sub=resultSet.getString("SubName");
        	hours=Integer.parseInt(resultSet.getString("HoursPerWeek"));
             }
        out.println(name+" "+Sub+" "+hours);
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	//text me if you need anything, WE GOT THIS!
}}  