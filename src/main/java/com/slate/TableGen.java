package com.slate;
//under construction. supposed to use all the data gathered to generate a form, probably gonna have another servlet to show table after this.
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

public class TableGen extends HttpServlet{  
	public void service(HttpServletRequest req,HttpServletResponse res)  
			throws ServletException,IOException{
		try {
			String Dept=(String) req.getSession().getAttribute("Dept");
			String Sec=(String) req.getSession().getAttribute("Sec");
			int Sem=(int) req.getSession().getAttribute("Sem");
			PrintWriter out= res.getWriter();
			String Sub=null;
			res.setContentType("text/html");
			Connection con= DatabaseConnection.initializeDatabase();
			Statement statement=con.createStatement();
			String sql ="select SubID, TeachID, Batch "
					+ "from teacherallo "
					+ "where (Department='"+Dept+"'or Department='Math')and Sem="+Sem+" and (Section='B' "
					+ " or SUBSTRING(SubID, 5, 1)='E')"; 
			
			ResultSet resultSet=null;
			resultSet= statement.executeQuery(sql);
			
			int i=0;
			
			//setting results of query inside our array of objects
			while(resultSet.next()){
			}

			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}