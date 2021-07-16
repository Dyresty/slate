package com.slate;

//generates PE selection form, redirects to something if PE are already assigned for the department

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
public class PEForm extends HttpServlet{  
	public void service(HttpServletRequest req,HttpServletResponse res)  
			throws ServletException,IOException  	
	{  
		
		PrintWriter out= res.getWriter();
		res.setContentType("text/html");
		try {
			String Dept=(String) req.getSession().getAttribute("Dept");
			String Sec=(String) req.getSession().getAttribute("Sec");
			int Sem=(int) req.getSession().getAttribute("Sem");
			int pkey=(int) req.getSession().getAttribute("pkey");
			
			//if pkey is 0, which means we need to assign electives, so we generate a form for it
			if(pkey==0) {
				Connection con= DatabaseConnection.initializeDatabase(); 
				String sql ="select t.TeachName, t.TeachID, s.SubName, s.SubID, s.HoursPerWeek "
						+ "from subjects s, teachers t, subteach st "
						+ "where s.Department='"+Dept+"'and s.Semester="+Sem+" and s.SubID=st.SubID and st.TeachID=t.TeachID "
						+ " and (SUBSTRING(s.SubID, 5, 1)='E')"
						+" ORDER BY st.SubID DESC";
		        ResultSet resultSet=null;
		        Statement statement=con.createStatement();
				resultSet= statement.executeQuery(sql);
				String Sub="null";
				//setting results of query inside our array of objects
				out.println("<form action='./something'>");
				while(resultSet.next()){
					String SubName=resultSet.getString("SubName");
					String temp=resultSet.getString("SubID");
					if(!temp.equals(Sub))
					{
						out.println("<p>Select Teacher For Subject "+SubName+"<p>");
						Sub=temp;
					}
					String TeachID=resultSet.getString("TeachID");
					String TeachName=resultSet.getString("TeachName");
					out.println("<input type='radio' name='"+Sub+"' value='"+TeachID+"'>");
	    			out.println("<label for='"+TeachID+"'>"+TeachName+"</input>");
				}
				out.println("<input type='submit'>");
				out.println("</form>");
			}
			
			//if pkey is 1, we are just redirecting it to next servlet.
			else {
				res.sendRedirect(req.getContextPath() + "/something");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}