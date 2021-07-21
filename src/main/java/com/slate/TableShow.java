package com.slate;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class TableShow extends HttpServlet{
	
	TableShowVals tableVal[][][]= new TableShowVals[6][6][6];



	public void service(HttpServletRequest req,HttpServletResponse res)  
			throws ServletException,IOException{
		
		String Dept=(String) req.getSession().getAttribute("Dept");
		String Sec=(String) req.getSession().getAttribute("Sec");
		int Sem=(int) req.getSession().getAttribute("Sem");
	
		
		try{
			
			PrintWriter out= res.getWriter();
			res.setContentType("text/html");
			Connection con= DatabaseConnection.initializeDatabase();
			Statement statement=con.createStatement();
			String sql="select ts.SubID, ts.TeachID, ts.Batch, t.TeachName, s.SubName, ts.Hour, ts.Day "
					+ "from timeslots ts, subjects s, teachers t  "
					+ "where ts.Department='"+Dept+"'and ts.Semester="+Sem+" and (Section='"+Sec+"') "
					+ " and (s.SubID=ts.SubID ) and ts.TeachID=t.TeachID "
					+ "ORDER BY ts.Day, ts.Hour" ; 
			ResultSet resultSet= statement.executeQuery(sql);
			while(resultSet.next())
			{
				String SubID=resultSet.getString("SubID");
				String SubName;
				if(SubID.equals("18CSE46"))
					SubName="Program Elective";
				else
					SubName=resultSet.getString("SubName");
				String TeachID=resultSet.getString("TeachID");
				String TeachName=resultSet.getString("TeachName");
				int batch=resultSet.getInt("Batch");
				
				int i=resultSet.getInt("Day");
				int j=resultSet.getInt("Hour");
				System.out.println(i+" "+j+" "+SubName+" "+batch);
				out.println(tableVal[i][j].length+" "+i+" "+j+" "+SubName+" "+TeachName+"<br>");
				if(batch>0)
					tableVal[i][j][batch-1]= new TableShowVals(SubID,SubName,TeachID,TeachName, Dept, Sem,Sec, batch);
				
				else if(batch==0)
					tableVal[i][j][0]= new TableShowVals(SubID,SubName,TeachID,TeachName, Dept, Sem,Sec, batch);
			}
			
			statement=con.createStatement();
			sql="select ts.SubID, ts.TeachID, ts.Batch, t.TeachName, ts.Hour, ts.Day "
					+ "from timeslots ts, teachers t "
					+ "where ts.Department='"+Dept+"'and ts.Semester="+Sem+" and Section='Z' "
					+ " and  ts.TeachID=t.TeachID "
					+ "ORDER BY ts.Day, ts.Hour" ; 
			resultSet= statement.executeQuery(sql);
			while(resultSet.next()){
				String SubID=resultSet.getString("SubID");
				String SubName="Program Elective";
				String TeachID=resultSet.getString("TeachID");
				String TeachName=resultSet.getString("TeachName");
				int batch=resultSet.getInt("Batch");
				
				int i=resultSet.getInt("Day");
				int j=resultSet.getInt("Hour");
						int x=0;
						while(tableVal[i][j][x]!=null)
						{
							System.out.println(x+" "+SubName);
							x++;
						}
						tableVal[i][j][x]= new TableShowVals(SubID,SubName,TeachID,TeachName, Dept, Sem,Sec, batch);
						
			}
			out.println("<table>");
			for(int i=0;i<tableVal.length-1;i++)
			{
				out.println("<tr>");
				for(int j=0;j<tableVal[i].length-1;j++)
				{
					out.println("<td>");
					for(int k=0;k<tableVal[i][j].length-1;k++)
					{	if(tableVal[i][j][k]!=null)
							out.println(tableVal[i][j][k].SubName+"<br>");
					}
					out.println("</td>");
				}
				out.println("</tr>");
			}
			out.println("<table>");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}