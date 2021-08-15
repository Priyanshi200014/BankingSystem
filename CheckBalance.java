import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class CheckBalance extends HttpServlet
{
   public void service(HttpServletRequest req , HttpServletResponse res)throws IOException , ServletException
   {
        res.setContentType("text/html");	   
	    PrintWriter out = res.getWriter();
		String accountno = req.getParameter("accountno");
		
		
	try
	{
		 Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","system");
		
		 Statement s1 = c.createStatement();
		 ResultSet rs = s1.executeQuery("select balance from accountdata where accountno='"+accountno+"'");
		 
		 while(rs.next())
		 {
			out.println("<p class='bal'>Your balance is Rs. "+rs.getString(1)+"</p>");
			//JOptionPane.showMessageDialog(null,"Your balance is Rs. "+rs.getString(1),"Alert Message",JOptionPane.WARNING_MESSAGE);

		 }
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("AfterLogin.html");
				   dispatcher.include(req,res);
			
         	
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	
	}	
	
	


}
