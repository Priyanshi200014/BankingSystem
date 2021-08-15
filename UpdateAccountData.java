import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
public class UpdateAccountData extends HttpServlet
{
  public void service(HttpServletRequest req , HttpServletResponse res)throws IOException , ServletException
  {
	res.setContentType("text/html");	
	PrintWriter out = res.getWriter();
    String User = req.getParameter("user");
	String field = req.getParameter("txtname");
	String newValue = req.getParameter("newvalue");
		
	try
	{
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","system");
		
		
		PreparedStatement ps = con.prepareStatement("Update accountdata set "+field+" = '"+newValue+"' where username = '"+User+"'");
		/*ps.setString(1,field);
		ps.setString(2,newValue);
		ps.setString(3,User);
		*/
		ps.executeUpdate();
		
		
			 out.println("<p class='error'> Record Updated!!! </p>");
			 RequestDispatcher dispatcher1 = req.getRequestDispatcher("userdata");
			 dispatcher1.include(req,res);
			
		  
		
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
  }

 
}