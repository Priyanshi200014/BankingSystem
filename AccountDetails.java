import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
public class AccountDetails extends HttpServlet
{
  public void service(HttpServletRequest req , HttpServletResponse res)throws IOException , ServletException
  {
	res.setContentType("text/html");	
	PrintWriter out = res.getWriter();
HttpSession session = req.getSession();
		String name = (String)session.getAttribute("name");
	try
	{
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","system");
		
		
		PreparedStatement ps = con.prepareStatement("select * from accountdata");
		
		 ResultSet rs=ps.executeQuery(); 
		out.print("<table width=80% border=1 class='ttable1'>");  
			 
              
										/* Printing column names */  
			ResultSetMetaData rsmd=rs.getMetaData();  
			int total=rsmd.getColumnCount();  
			out.print("<tr>");
			int ser=1; 			
            out.print("<th>"+ser+"</th>");			
			for(int i=1;i<=total;i++)  
			{  
			out.print("<th>"+rsmd.getColumnName(i)+"</th>");  
			}  
  
			out.print("</tr>");  
              
			/* Printing result */  
  
			while(rs.next())  
			{  
		     
			out.print("<tr><td>"+ser+"</td><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td><td>"+rs.getString(7)+"</td><td>"+rs.getString(8)+"</td><td>"+rs.getString(9)+"</td><td><a href='update.html'>Update</a></td></tr>");  
                 ser++;  
			}  
  
										out.println("</table>");  
			
	
			 out.println("<p class='error'> Welcome "+name+" </p>");
			  RequestDispatcher dispatcher = req.getRequestDispatcher("Transactions.html");
			   dispatcher.include(req,res);
			   
		  
		
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
  }

 
}