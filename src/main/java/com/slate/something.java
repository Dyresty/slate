package com.slate;

//updating PE form values inside the table and generating form for other subjects
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
	TeachShow teachshow[ ]= new TeachShow[200];
	TeachAllo teachallo[ ]= new TeachAllo[200];
	
	public void service(HttpServletRequest req,HttpServletResponse res)  
	throws ServletException,IOException  
	{  
		
		PrintWriter out= res.getWriter();
		
		try {
			
				int i=0; //loop variable to see how much data we are extracting
				
				//fetching parameters from form
				String Dept=(String) req.getSession().getAttribute("Dept");
				String Sec=(String) req.getSession().getAttribute("Sec");
				int Sem=(int) req.getSession().getAttribute("Sem");
				int tkey=(int) req.getSession().getAttribute("tkey");
				int pkey=(int) req.getSession().getAttribute("pkey");
				res.setContentType("text/html");
				
				Connection con= DatabaseConnection.initializeDatabase(); //static method to connect database
				Statement statement=con.createStatement();
				
				//if pkey was 0, we execute a function to fetch values from last form and update it into the database
				if(pkey==0)
					this.updatePE(con,req,res);
				
				//if tkey is 0, we are generating a form to select all the theory and lab subject teachers
				if(tkey==0) {
					int j=0;
					statement=con.createStatement();
					String sql ="select t.TeachName, t.TeachID, s.SubName, s.SubID, s.HoursPerWeek "
							+ "from subjects s, teachers t, subteach st "
							+ "where (s.Department='"+Dept+"'or s.Department='Math')and s.Semester="+Sem+" and s.SubID=st.SubID and st.TeachID=t.TeachID "
							+ " and NOT (SUBSTRING(s.SubID, 5, 1)='E')"
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
			        			if(!(o<=3))//dick
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
				}
				
				// if we have tkey as 1, we already have assigned teachers so we just forward it to the table generation. need to add options for both this and the previous
				else {
					res.sendRedirect(req.getContextPath() + "/TableGen");
				}
	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	//function to update elective teachers 
	public void updatePE(Connection con, HttpServletRequest req,HttpServletResponse res) {
		
		try {
		String Dept=(String) req.getSession().getAttribute("Dept");
		String Sec=(String) req.getSession().getAttribute("Sec");
		int Sem=(int) req.getSession().getAttribute("Sem");
		int tkey=(int) req.getSession().getAttribute("tkey");
		int i=0;
		Statement statement=con.createStatement();
		String sql ="select SubID "
				+ "from subjects "
				+ "where (Department='"+Dept+"'or Department='Math')and Semester="+Sem+" and (SUBSTRING(SubID, 5, 1)='E')"; 
		
		ResultSet resultSet=null;
		resultSet= statement.executeQuery(sql);
		
		while(resultSet.next()){
			String TeachID;
			String SubID=resultSet.getString("SubID");
			TeachID=req.getParameter(SubID);
        	teachallo[i]=new TeachAllo(TeachID, SubID, Sem, "Z", Dept, -1);
        	i++;
		}
		int j=0;
		while(j<i) {
			PreparedStatement ps=con.prepareStatement("insert into teacherallo values(?, ?, ?, ?, ?, ?)");
	  	        ps.setString(1,teachallo[j].TeachID);
	  	    	ps.setString(2,teachallo[j].SubID);
	  	    	ps.setString(3,teachallo[j].Dept);
	  	    	ps.setInt(4,teachallo[j].Sem);
	  	    	ps.setString(5,teachallo[j].Sec);
	  	    	ps.setInt(6,teachallo[j].Batch);
	  	    	int k=ps.executeUpdate();
	  	    	j++;
    	}
		PreparedStatement ps=con.prepareStatement("Update classallo "
				+ "set PEAllo=1 "
				+ "where Department='"+Dept+"'"); 
		int k=ps.executeUpdate();
		
		resultSet=null;
		resultSet= statement.executeQuery(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
  