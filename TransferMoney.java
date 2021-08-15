import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferMoney extends HttpServlet
{
   public void service(HttpServletRequest req , HttpServletResponse res)throws IOException , ServletException
   {
        res.setContentType("text/html");	   
	    PrintWriter out = res.getWriter();
		HttpSession session = req.getSession();
		String accountno1 = req.getParameter("accountno1");
		String accountno2 = req.getParameter("accountno2");
		String amount1 = req.getParameter("money");
		long amount = Long.parseLong(req.getParameter("money"));
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		Date now = new Date();
		String date = dtf.format(now);
				String name = (String)session.getAttribute("name");

	try
	
	{
		 Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","system");
		
		Statement s1 = c.createStatement();
		ResultSet rs = s1.executeQuery("select balance from accountdata where accountno='"+accountno1+"'");
		while(rs.next())
		{
			long balance=Long.parseLong(rs.getString(1));
			
			long newbalance = balance-amount;
			Statement s = c.createStatement();
			s.executeUpdate("update accountdata set balance ="+newbalance+" where accountno = '"+accountno1+"'");
			
		}
		
		Statement s5 = c.createStatement();
		ResultSet rs5 = s5.executeQuery("select username from accountdata where accountno = '"+accountno1+"'");
		while(rs5.next())
		{
			PreparedStatement ps3 = c.prepareStatement("Insert into debit values (?,?,?)");
			ps3.setString(1,rs5.getString(1));
			ps3.setString(2,amount1);
			ps3.setString(3,date);
			ps3.executeUpdate();
		
		}
		
		Statement s2 = c.createStatement();
		ResultSet rs1 = s2.executeQuery("select balance from accountdata where accountno='"+accountno2+"'");
		while(rs1.next())
		{
			long balance1=Long.parseLong(rs1.getString(1));
			
			long newbalance1 = balance1+amount;
			Statement s3 = c.createStatement();
			s3.executeUpdate("update accountdata set balance ="+newbalance1+" where accountno = '"+accountno2+"'");
			
		}
		
		Statement s3 = c.createStatement();
		ResultSet rs4 = s3.executeQuery("select username from accountdata where accountno = '"+accountno2+"'");
		while(rs4.next())
		{
			PreparedStatement ps2 = c.prepareStatement("Insert into credit values (?,?,?)");
			ps2.setString(1,rs4.getString(1));
			ps2.setString(2,amount1);
			ps2.setString(3,date);
			ps2.executeUpdate();
		
		}
		
		PreparedStatement ps = c.prepareStatement("Insert into transaction values (?,?,?,?,?)");
		ps.setString(1,name);
		ps.setString(2,accountno1);
		ps.setString(3,accountno2);
		ps.setString(4,amount1);
		ps.setString(5,date);
		int i = ps.executeUpdate();
			
			 out.println("<p class='error'>Amount Transfered!! </p>"); 
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("AfterLogin.html");
				   dispatcher.include(req,res);
			
         
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	
	}	
	
	


}
