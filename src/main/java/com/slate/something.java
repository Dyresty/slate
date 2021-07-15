package com.slate;




import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
	TeachShow teachshow[ ]= new TeachShow[9];
	
	PrintWriter out= res.getWriter();
	
	try {
			int i=0;
			String Dept=req.getParameter("Dept");
			int Sem=Integer.parseInt(req.getParameter("Sem"));
			String Sec=req.getParameter("Sec");
			res.setContentType("text/html");
			Connection con= DatabaseConnection.initializeDatabase(); //static method to connect database
	        //query testing
			Statement statement=con.createStatement();
			out.println(Dept+" "+Sem+" "+Sec);
			String sql ="select t.TeachName, t.TeachID, s.SubName, s.SubID, s.HoursPerWeek "
					+ "from subjects s, teachers t, subteach st "
					+ "where s.Department='"+Dept+"'and s.Semester="+Sem+" and s.SubID=st.SubID and st.TeachID=t.TeachID"; 
	        ResultSet resultSet=null;
			resultSet= statement.executeQuery(sql);
			out.println(Dept+" "+Sem+" "+Sec);
        while(resultSet.next()){
        	String TeachName;
        	String TeachID;
        	String SubName;
        	String SubID;
        	int HoursPerWeek;
        	TeachID=resultSet.getString("TeachID");
        	SubName=resultSet.getString("SubName");
        	SubID=resultSet.getString("SubID");
        	TeachName=resultSet.getString("TeachName");
        	HoursPerWeek=Integer.parseInt(resultSet.getString("HoursPerWeek"));
        	teachshow[i]= new TeachShow(TeachName, TeachID, SubName, SubID, HoursPerWeek);
        	i++;
             }
        int j=0;
        while(j<i) {
        	out.println(teachshow[j].TeachID+" "+teachshow[j].TeachName+" "+teachshow[j].SubName);
        	PreparedStatement ps=con.prepareStatement("insert into teacherallo values(?, ?, ?, ?, ?, ?)");
  	       //setting values to the said statement
  	        ps.setString(1,teachshow[j].TeachID);
  	    	ps.setString(2,teachshow[j].SubID);
  	    	ps.setString(3,Dept);
  	    	ps.setString(4,Integer.toString(Sem));
  	    	ps.setString(5,Sec);
  	    	ps.setString(6,Integer.toString(0));
  	    	int k=ps.executeUpdate();
  	    	j++;
        }
        out.println("End this pain.");
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	//text me if you need anything, WE GOT THIS!
}}  