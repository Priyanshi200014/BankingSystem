import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
public class LogoutServlet extends HttpServlet
{
  public void doGet(HttpServletRequest req , HttpServletResponse res)throws IOException , ServletException
  {
	res.setContentType("text/html");	
	PrintWriter out = res.getWriter();

	out.println("<p class='error'>Successfully Logged Out</p>");
	 RequestDispatcher dispatcher = req.getRequestDispatcher("home.html");
	dispatcher.include(req,res);
				   
	HttpSession session = req.getSession(false);
	session.invalidate();
	
	
  }

 
}