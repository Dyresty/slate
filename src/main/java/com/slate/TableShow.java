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
			String[] Days = {"Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday"}; 

			out.println("<!DOCTYPE html>\r\n"
					+ "<html lang=\"en\" dir=\"ltr\">\r\n"
					+ "\r\n"
					+ "<head>\r\n"
					+ "        <meta charset=\"utf-8\">\r\n"
					+ "        <title>Display</title>\r\n"
					+ "        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0\" crossorigin=\"anonymous\">\r\n"
					+ "        <link rel=\"stylesheet\" href=\"css/display.css\">\r\n"
					+ "\r\n"
					+ "</head>\r\n"
					+ "\r\n"
					+ "<body style=\"background-color:teal;\">\r\n"
					+ "        <div class=\"\" style='width:100vw; pading-left:20px;'>\r\n"
					+ "                <nav class=\"navbar navbar-expand-lg fixed-top navbar-light bg-secondary style='margin:0;width:100vw;'\">\r\n"
					+ "                        <a class=\"navbar-brand nb\" href=\"#\"><img src=\"img/cornor logo.jpg\" style='width:120px;padding-left:20px;'></a>\r\n"
					+ "                        <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\r\n"
					+ "                                <span class=\"navbar-toggler-icon\"></span>\r\n"
					+ "                        </button>\r\n"
					+ "                        <div class=\"collapse navbar-collapse mx-auto\" style=\"width:200px;padding-left:100px;\" id=\"navbarSupportedContent\">\r\n"
					+ "                                <ul class=\"navbar-nav nav-auto me-5\">\r\n"
					+ "                                        <li class=\"nav-item\">\r\n"
					+ "                                                <a class=\"nav-link\" style='color:white;' href=\"#\">Department: "+Dept+ "</a>\r\n"
					+ "                                        </li>\r\n"
					+ "                                        <li class=\"nav-item ms-5\">\r\n"
					+ "                                                <a class=\"nav-link\" style='color:white;' href=\"#\">Semester: "+Sem+" </a>\r\n"
					+ "                                        </li>\r\n"
					+ "                                        <li class=\"nav-item ms-5\">\r\n"
					+ "                                                <a class=\"nav-link\" style='color:white;' href=\"#\">Section: "+Sec+" </a>\r\n"
					+ "                                        </li>\r\n"
					+ "                                </ul>\r\n"
					+ "                        </div>\r\n"
					+ "                </nav>\r\n"
					+ "        </div>\r\n"
					+ "        <div class=\"\" style=\"padding-top:180px;\">\r\n"
					+ "                <div class=\"mx-auto exy\" style=\";\">\r\n"
					+ "\r\n"
					+ "                        <table class=\"table table-hover table-dark\">\r\n"
					+ "                                <thead class=>\r\n"
					+ "                                        <tr>\r\n"
					+ "                                                <th scope=\"col\" class=\"bg-dark\">Day/Hour</th>\r\n"
					+ "                                                <th scope=\"col\">1</th>\r\n"
					+ "                                                <th scope=\"col\">2</th>\r\n"
					+ "                                                <th scope=\"col\">Short break</th>\r\n"
					+ "                                                <th scope=\"col\">3</th>\r\n"
					+ "                                                <th scope=\"col\">4</th>\r\n"
					+ "                                                <th scope=\"col\">Lunch</th>\r\n"
					+ "                                                <th scope=\"col\">5</th>\r\n"
					+ "                                                <th scope=\"col\">6</th>\r\n"
					+ "                                        </tr>\r\n"
					+ "                                </thead>\r\n"
					+ "                                <tbody>");
			
			
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
				if(batch>0)
					tableVal[i][j][batch-1]= new TableShowVals(SubID,SubName,TeachID,TeachName, Dept, Sem,Sec, batch);
				
				else if(batch==0)
					tableVal[i][j][0]= new TableShowVals(SubID,SubName,TeachID,TeachName, Dept, Sem,Sec, batch);
			}
			
			statement=con.createStatement();
			sql="select ts.SubID, ts.TeachID, ts.Batch, t.TeachName, ts.Hour, ts.Day "
					+ "from timeslots ts, teachers t "
					+ "where ts.Department='"+Dept+"'and ts.Semester="+Sem+" and Section='"+Sec+"' and Batch=-1 "
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
					
						tableVal[i][j][x]= new TableShowVals(SubID,SubName,TeachID,TeachName, Dept, Sem,Sec, batch);
						
			}
		
			out.println("");
			for(int i=0;i<tableVal.length;i++)
			{
				out.println("<tr>"
						+ "  <td class=\"bg-primary\">"+Days[i]+"</td>");
				
				for(int j=0;j<tableVal[i].length;j++)
				{
					if(j==2||j==4)
						out.println("<td></td>");
					out.println("<td class=\"table-light\">");
//					for(int k=0;k<tableVal[i][j].length-1;k++)
					{	if(tableVal[i][j][0]!=null)
						{
							out.println(tableVal[i][j][0].SubName+"<br>");
							System.out.println(tableVal[i][j][0].SubID+" "+i+" l"+j);
						}
					}
					out.println("</td>");
				}
				out.println("</tr>");
			}
			out.println("</div> </div></tbody></table>");
			

			out.println( "        <div class=\"\" style=\"padding-top:20px;\">\r\n"
			+ "                <div class=\"mx-auto exy\" style=\";\">\r\n"
			+ "\r\n"
			+ "                        <table class=\"table table-hover table-dark\">\r\n"
			+ "                                <thead class=>\r\n"
			+ "                                        <tr>\r\n"
			+ "                                                <th scope=\"col\" class=\"bg-dark\">S.No</th>\r\n"
			+ "                                                <th scope=\"col\">Subject Name</th>\r\n"
			+ "                                                <th scope=\"col\">Teacher's Name</th>\r\n"                                             
			+ "                                        </tr>\r\n"
			+ "                                </thead>\r\n"
			+ "                                <tbody>");
			
			statement=con.createStatement();
			sql="select t.TeachName, s.SubName, ta.Batch "
					+ "from subjects s, teacherallo ta, teachers t "
					+ "where ta.Department='"+Dept+"'and ta.Sem="+Sem+" and (ta.Section='"+Sec+"' or ta.Section='Z') "
					+ " and  ta.TeachID=t.TeachID and ta.SubID=s.SubID"; 
			resultSet= statement.executeQuery(sql);
			int x=1;
			while(resultSet.next()){
				int batch=resultSet.getInt("Batch");
				if(batch==0||batch==-1)
					out.println("<tr>"
						+ "  <td class='bg-primary'>"+x+"</td>"
						+ " <td class='table-light'>"+resultSet.getString("SubName")+"</td>"
						+ " <td class='table-light'>"+resultSet.getString("TeachName")+"</td>"
								+ "</tr>");		
				else
					out.println("<tr>"
						+ "  <td class='bg-primary'>"+x+"</td>"
						+ " <td class='table-light'>"+resultSet.getString("SubName")+" Batch-"+batch+"</td>"
						+ " <td class='table-light'>"+resultSet.getString("TeachName")+"</td>"
								+ "</tr>");	
			x++;
			}
			out.println("</div> </div></tbody></table>");
	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}