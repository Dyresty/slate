package com.slate;




import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//@WebServlet("/form")
public class AlloServ extends HttpServlet{  
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException  
	{  
		
		TeachAllo teachallo[ ]= new TeachAllo[200];
		try {
			
			
			String Dept=(String) req.getSession().getAttribute("Dept");
			String Sec=(String) req.getSession().getAttribute("Sec");
			int Sem=(int) req.getSession().getAttribute("Sem");
			
			res.setContentType("text/html");
			Connection con= DatabaseConnection.initializeDatabase(); //static method to connect database
			
			Statement statement=con.createStatement();
			String sql ="select SubID "
					+ "from subjects "
					+ "where (Department='"+Dept+"'or Department='Math')and Semester="+Sem; 
			
			ResultSet resultSet=null;
			resultSet= statement.executeQuery(sql);
			
			int i=0;
			
			//setting results of query inside our array of objects
			while(resultSet.next()){
				int batch=0;
				String Sub=null;
				String TeachID;
				String SubID=resultSet.getString("SubID");
				
				//Condition where the subject is a lab
	        	if(SubID.substring(4,5).equals("L"))
	        	{
	        		int j=1;
	        		while(j<=3) {
	        		TeachID=req.getParameter(SubID+Integer.toString(j));
	        		batch=j;
	        		teachallo[i+j-1]=new TeachAllo(TeachID, SubID.substring(0,7), Sem, Sec, Dept, batch);
	        		j++;
	        		}	
	        		i+=j-1;
	        	}
	        	
	        	//where subject is theory
	        	else {
	        	TeachID=req.getParameter(SubID);
	        	teachallo[i]=new TeachAllo(TeachID, SubID, Sem, Sec, Dept, batch);
	        	i++;
	        	}
	        	
	        	
	        }
			
			//storing the values in TeachAllocation table, NOTE:might be a good idea to make something which tells us if we have allocated teachers to a class or not
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
			  res.sendRedirect(req.getContextPath() + "/TableGen");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException   {
			doPost(req, res);
		}
	
}