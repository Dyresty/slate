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
	TeachShow teachshow[ ]= new TeachShow[100];
	
	PrintWriter out= res.getWriter();
	
	try {
			int i=0; //loop variable to see how much data we are extracting
			
			
			//fetching parameters from form
			String Dept=req.getParameter("Dept");
			int Sem=Integer.parseInt(req.getParameter("Sem"));
			String Sec=req.getParameter("Sec");
			res.setContentType("text/html");
			Connection con= DatabaseConnection.initializeDatabase(); //static method to connect database
			
			
	        //query to fetch Sub-teacher relations (with names and hours per week, as well)
			Statement statement=con.createStatement();
			String sql ="select t.TeachName, t.TeachID, s.SubName, s.SubID, s.HoursPerWeek "
					+ "from subjects s, teachers t, subteach st "
					+ "where (s.Department='"+Dept+"'or s.Department='Math')and s.Semester="+Sem+" and s.SubID=st.SubID and st.TeachID=t.TeachID "
					+"ORDER BY st.SubID DESC";
	        ResultSet resultSet=null;
			resultSet= statement.executeQuery(sql);
			
			
			//setting results of query inside our array of objects
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
	        	teachshow[i]= new TeachShow(TeachName, TeachID, SubName, SubID, HoursPerWeek); //such complex much java
	        	i++;
	        	System.out.println(SubID);
			}
        
			
        //generating forms with the data, pls dont ask me how
	        int l=0;
	        int m=0;
	        String Sub="null";
	        out.println("<form action='./Allo'>");
	        while(l<i) {
	        	if(!Sub.equals(teachshow[l].SubID)) {
	        		Sub=teachshow[l].SubID;
	        		
	        		//checking for labs, m acts as a flag and becomes 1 if lab found
	        		if(Sub.substring(4, 5).equals("L"))
	        		{
	        			m=1;
	        		}
	        		out.println("<p>Select Teacher For Subject "+teachshow[l].SubName+"<p>");
	        	}
	        	
	        	//a lot of weird lab listing code. Pain.
	        	if(m==1)
	        	{ 
	        		int o=1;
	        		
	        		while(teachshow[o+l-1].SubID.equals(Sub))
	        		{
	        			int p=0;
	        			out.println("<p>For Batch "+o+"<p>");
	        			while(teachshow[p+l].SubID.equals(Sub)) {
	        				out.println("<input type='radio' name='"+Sub+Integer.toString(o)+"' value='"+teachshow[p+l].TeachID+"'>");
	        				out.println("<label for='"+teachshow[l+p].TeachID+"'>"+teachshow[l+p].TeachName+"</input>");
	        				p++;
	        			}
	        			o++;
	        			if(!(o<=3))
	        			{
	        				l+=p-1;
	        				break;
	        			}
	        		}
	        		m=0;
	        	}
	        	else
	        	{
	        		out.println("<input type='radio' name='"+Sub+"' value='"+teachshow[l].TeachID+"'>");
	    			out.println("<label for='"+teachshow[l].TeachID+"'>"+teachshow[l].TeachName+"</input>");
	        	}
	        	l++;
	        }
	        out.println("<input type='submit'> </form>");
	        
	        
	        
	      //setting attributes for next servlet
	        req.getSession().setAttribute("Sec", Sec);
	        req.getSession().setAttribute("Sem", Sem);
	        req.getSession().setAttribute("Dept", Dept);
	        getServletContext().getRequestDispatcher("/Allo");
	        
	        //this code is not for this servlet, it is just a reference for the allocation servlet.
//	        int j=0;
//	        while(j<i) {
//	        	PreparedStatement ps=con.prepareStatement("insert into teacherallo values(?, ?, ?, ?, ?, ?)");
//	  	       //setting values to the said statement
//	  	        ps.setString(1,teachshow[j].TeachID);
//	  	    	ps.setString(2,teachshow[j].SubID);
//	  	    	ps.setString(3,Dept);
//	  	    	ps.setString(4,Integer.toString(Sem));
//	  	    	ps.setString(5,Sec);
//	  	    	ps.setString(6,Integer.toString(0));
//	  	    	int k=ps.executeUpdate();
//	  	    	j++;
//	        }

	}
	catch(Exception e) {
		e.printStackTrace();
	}
}}  