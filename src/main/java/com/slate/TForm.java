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
public class TForm extends HttpServlet{  
	TeachShow teachshow[ ]= new TeachShow[200];
	TeachAllo teachallo[ ]= new TeachAllo[200];
	
	public void service(HttpServletRequest req,HttpServletResponse res)  
	throws ServletException,IOException  
	{  
		
		PrintWriter out= res.getWriter();
		
		try {
			
				int i=0; //loop variable to see how much data we are extracting
				
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
						+ "body,p {\r\n"
						+ "  margin: 0;\r\n"
						+ "  width: 100%;\r\n"
						+ " height:100%;\r\n"
						+ "  font-size: 20px;\r\n"
						+ "background-repeat:no-repeat;"
						+ "background-size:cover;"
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
						+ "<body >\r\n"
						+ "  <div class=\"header\" style=\"\">\r\n"
						+ "    <img src=\"img/header logo.jpg\" alt=\"\" style=\"width:100%;\">\r\n"
						+ "  </div>\r\n"
						+ "  <div class=\"\" style='text-align:center;background-image: linear-gradient(180deg, #bac1c5, #2e4052);'>\r\n"
						+ "<br>"
						+ "<br>"
						+ "<br>"
						+ "<br>"
						+ "<div class=\"radios\" style=\"background-color:#bac1c5; display:inline-block;text-align:center; width:1200px;;position:relative;border-radius:15px;\">"
						+ "<h2>Assign Teachers:</h2>"
						+ "<div style=''>");
				
				
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
			        		out.println("<br><br><p>Select Teacher For Subject "+teachshow[l].SubName+"</p>");
			        	}
			        	
			        	//a lot of weird lab listing code. Pain.
			        	if(m==1)
			        	{ 
			        		int o=1;
			        		
			        		while(teachshow[o+l-1].SubID.equals(Sub))
			        		{
			        			int p=0;
			        			out.println("<br><p>For Batch "+o+"</p>");
			        			while(teachshow[p+l].SubID.equals(Sub)) {
			        				out.println("<label for='\"+teachshow[l+p].TeachID+\"'><input class='radio' type='radio' name='"+Sub+Integer.toString(o)+"' value='"+teachshow[p+l].TeachID+"' style='display:inline-block;'>");
			        				out.println(""+teachshow[l+p].TeachName+"</input><br>");
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
			        		out.println("<label for='\"+teachshow[l].TeachID+\"'><input class='radio' type='radio' name='"+Sub+"' value='"+teachshow[l].TeachID+"' style='display:inline-block;'>");
			    			out.println(""+teachshow[l].TeachName+"</input><br>");
			        	}
			        	l++;
			        }
			        out.println("<br><input type='submit' class='btn btn-primary'>");
					out.println("</form> </div> </div></div> </body> <html>");
			        
			        
			        
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
  