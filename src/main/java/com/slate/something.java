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
					+ "where s.Department='"+Dept+"'and s.Semester="+Sem+" and s.SubID=st.SubID and st.TeachID=t.TeachID "
					+"ORDER BY st.SubID DESC";
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
        int l=0;
        int m=0;
        String Sub="null";
        out.println("<form action=''>");
        while(l<i) {
        	if(!Sub.equals(teachshow[l].SubID)) {
        		Sub=teachshow[l].SubID;
        		System.out.println(Sub.substring(4, 5));
        		if(Sub.substring(4, 5).equals("L"))
        		{
        			m=1;
        			System.out.println("something wrong i can feel it");
        		}
        		out.println("<p>Select Teacher For Subject "+teachshow[l].SubName+"<p>");
        	}
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
        				l+=p;
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
        getServletContext().setAttribute("Sec", Sec);
        getServletContext().setAttribute("Sem", Sem);
        getServletContext().setAttribute("Dept", Dept);
        out.println("<input type='submit'> </form>");
        int j=0;
        while(j<i) {
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

	}
	catch(Exception e) {
		e.printStackTrace();
	}
	//text me if you need anything, WE GOT THIS!
}}  