package com.slate;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet{  
	public void service(HttpServletRequest req,HttpServletResponse res)  
			throws ServletException,IOException
	{  
		String mail=req.getParameter("Email");
		String pass=req.getParameter("Password");
		res.setContentType("text/html");
        PrintWriter out = res.getWriter();
		if(mail.equals("root@nmit.ac.in")&&pass.equals("nitte123"))
		{
			res.sendRedirect(req.getContextPath() + "/index.html");
		}
		else {
			out.print("<body><p style='position:absolute;left:1270px;top:530px;color:red;z-index:3;'>Incorrect details.</p></body>");
            RequestDispatcher rd = req.getRequestDispatcher("/login.html");
            rd.include(req, res);  
		}
	}
	
}