package com.slate;

//checking if electives and teachers assigned already or not and redirecting acordingly

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
public class PEAllo extends HttpServlet{  
	public void service(HttpServletRequest req,HttpServletResponse res)  
			throws ServletException,IOException
	{  
		
		PrintWriter out= res.getWriter();
	
		try {
				int pkey=0, tkey=0; 
				
				
				//fetching Department, sem, sec
				String Dept=req.getParameter("Dept");
				int Sem=Integer.parseInt(req.getParameter("Sem"));
				String Sec=req.getParameter("Sec");
				res.setContentType("text/html");
				out.println(Sem+" "+Dept+" "+Sec);
				Connection con= DatabaseConnection.initializeDatabase(); //static method to connect database
				
				
				//checking if teachers are already alloted to this Dept+Sem+Sec
				Statement statement=con.createStatement();
				String sql ="select TeachersAllo, PEAllo "
						+ "from classallo "
						+ "where Department='"+Dept+"' and Semester="+Sem+" and Section='"+Sec+"'";
		        ResultSet resultSet=null;
				resultSet= statement.executeQuery(sql);
					while(resultSet.next()){
						tkey=resultSet.getInt("TeachersAllo");
						System.out.println(tkey);
							if(tkey==1)
								break;
						
					}
					
				//checking if electives are already alloted to this Dept+Sem
				sql ="select TeachersAllo, PEAllo "
						+ "from classallo "
						+ "where Department='"+Dept+"' and Semester="+Sem;
			    resultSet=null;
				resultSet= statement.executeQuery(sql);
				while(resultSet.next()){
					pkey=resultSet.getInt("PEAllo");
					System.out.println(pkey);
					if(pkey==1)
						break;	
				}
				
				
				sql ="select TeachersAllo, PEAllo"
						+ " from classallo "
						+ " where Department='"+Dept+"' and Semester="+Sem+" and Section='"+Sec+"' ";
		        resultSet=null;
				resultSet= statement.executeQuery(sql);
				
				if(resultSet.next()==false)// if no results fetched, insert into table
				{
					PreparedStatement ps=con.prepareStatement("insert into classallo values(?,?,?,?,?)");
		  	        ps.setString(1,Dept);
		  	        ps.setInt(2,Sem);
		  	        ps.setString(3,Sec);
		  	        ps.setInt(4,0); //tkey 0 because the section has not had any past assignments
		  	        ps.setInt(5,pkey);
		  	        int k=ps.executeUpdate();
				}
				
				//fetching tkey from the table if it exists
				else {
					while(resultSet.next()){
						tkey=resultSet.getInt("TeachersAllo");
					}
				}
				
				//setting session attributes to fetch in other servlets
				req.getSession().setAttribute("Sec", Sec);
				req.getSession().setAttribute("Sem", Sem);
				req.getSession().setAttribute("Dept", Dept);
				req.getSession().setAttribute("tkey", tkey);
				req.getSession().setAttribute("pkey", pkey);
				res.sendRedirect(req.getContextPath() + "/PEForm");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}