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
			
			//if pkey is 0, which means we need to assign electives, so we generate a form for 
			out.println("<!DOCTYPE html>\r\n"
					+ "<html lang=\"en\" dir=\"ltr\">\r\n"
					+ "\r\n"
					+ "<head>\r\n"
					+ "<style>"
					+ "*,\r\n"
					+ "*::before\r\n"
					+ "*::after {\r\n"
					+ "  box-sizing: border-box;\r\n"
					+ "}\r\n"
					+ "html,\r\n"
					+ "body {\r\n"
					+ "  margin: 0;\r\n"
					+ "  width: 100%;\r\n"
					+ "  height: 100%;\r\n"
					+ "  font-size: 16px;\r\n"
					+ "  line-height: 100%;\r\n"
					+ "  font-family: 'Montserrat', 'Roboto', sans-serif;\r\n"
					+ "  color: #333;\r\n"
					+ "  font-weight: 400;\r\n"
					+ "}\r\n"
					+ ".radios {\r\n"
					+ "  width: 100%;\r\n"
					+ "  height: 100%;\r\n"
					+ "  display: flex;\r\n"
					+ "  align-items: center;\r\n"
					+ "  justify-content: center;\r\n"
					+ "  \r\n"
					+ "  @media(max-width: 640px) {\r\n"
					+ "    flex-direction: column;\r\n"
					+ "  }\r\n"
					+ "}\r\n"
					+ ".radio {  \r\n"
					+ "  input {\r\n"
					+ "    position: absolute;\r\n"
					+ "    pointer-events: none;\r\n"
					+ "    visibility: hidden;\r\n"
					+ "  }\r\n"
					+ "  \r\n"
					+ "  input:focus + label {     \r\n"
					+ "    background: #eeeeff;\r\n"
					+ "    \r\n"
					+ "    .checker {\r\n"
					+ "      border-color: #6666ff;\r\n"
					+ "    }\r\n"
					+ "  }\r\n"
					+ "  \r\n"
					+ "  input:checked + label {    \r\n"
					+ "    .checker {\r\n"
					+ "      box-shadow: inset 0 0 0 6px #6666ff;\r\n"
					+ "    }\r\n"
					+ "  }\r\n"
					+ "  \r\n"
					+ "  label {\r\n"
					+ "    display: flex;\r\n"
					+ "    align-items: center;\r\n"
					+ "    height: 28px;\r\n"
					+ "    border-radius: 14px;\r\n"
					+ "    margin: 10px;\r\n"
					+ "    padding: 0 8px 0 6px;\r\n"
					+ "    cursor: pointer;\r\n"
					+ "    transition: background-color .3s ease;\r\n"
					+ "    \r\n"
					+ "    &:hover {\r\n"
					+ "      background: #eeeeff;\r\n"
					+ "      \r\n"
					+ "      .checker {\r\n"
					+ "        box-shadow: inset 0 0 0 2px #6666ff;\r\n"
					+ "      }\r\n"
					+ "    }\r\n"
					+ "  }\r\n"
					+ "  \r\n"
					+ "  .checker {\r\n"
					+ "    width: 18px;\r\n"
					+ "    height: 18px;\r\n"
					+ "    border-radius: 50%;\r\n"
					+ "    margin-right: 8px;\r\n"
					+ "    box-shadow: inset 0 0 0 2px #ccc;\r\n"
					+ "    transition: box-shadow .3s ease;\r\n"
					+ "  }\r\n"
					+ "}"
					+ "</style>"
					+ "  <!-- Latest compiled and minified CSS -->\r\n"
					+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\r\n"
					+ "\r\n"
					+ "<!-- Optional theme -->\r\n"
					+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">\r\n"
					+ "\r\n"
					+ "<!-- Latest compiled and minified JavaScript -->\r\n"
					+ "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>\r\n"
					+ "\r\n"
					+ "  <meta charset=\"utf-8\">\r\n"
					+ "  <title></title>\r\n"
					+ "</head>\r\n"
					+ "\r\n"
					+ "<body style=\"background-image: linear-gradient(180deg, #bac1c5, #2e4052);height:100vh;margin:0;\">\r\n"
					+ "  <div class=\"header\" style=\"\">\r\n"
					+ "    <img src=\"img/header logo.jpg\" alt=\"\" style=\"width:100%;\">\r\n"
					+ "  </div>\r\n"
					+ "  <div class=\"\" style=\"text-align:center;\">\r\n"
					+ "<div class=\"form-div\" style=\"background-color:#bac1c5; display:inline-block;text-align:center; width:1200px;top:100px;position:relative; height:400px;border-radius:15px;\">"
					+ "<h2>Assign Program Elective Teachers:</h2>");
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
					out.println("<input class='radio' type='radio' name='"+Sub+"' value='"+TeachID+"' style='display:inline-block;'");
	    			out.println("<label for='"+TeachID+"'>"+TeachName+"</input><br>");
				}
				out.println("<br><input type='submit'>");
				out.println("</form> </div></div> </body> <html>");
			}
			
			//if pkey is 1, we are just redirecting it to next servlet.
			else {
				res.sendRedirect(req.getContextPath() + "/TForm");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}