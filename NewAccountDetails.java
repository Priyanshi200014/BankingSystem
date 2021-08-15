import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.util.Random;

public class  NewAccountDetails extends HttpServlet
{
  public void service(HttpServletRequest req , HttpServletResponse res)throws IOException , ServletException
  {
	res.setContentType("text/html");	
	PrintWriter out = res.getWriter();

	String user = req.getParameter("txtname");	
	String adhar = req.getParameter("adharno");	
	String mobile = req.getParameter("mobileno");	
	String email = req.getParameter("emailname");	
	String father = req.getParameter("fathername");	
	String acctype = req.getParameter("accounttype");	
	String balance = req.getParameter("balanceno");	
	String gender = req.getParameter("gendername");	
	String update="";
	
	
	Random random = new Random();
	String s="1234567890";
	char[] otp = new char[11];
	for(int i=0;i<11;i++)
	{
		otp[i]=s.charAt(random.nextInt(s.length()));
	}

	String strArray[] = new String[otp.length];
	for(int i=0;i<otp.length;i++)
	{
		strArray[i] = String.valueOf(otp[i]);
		
	}
	String s1 = Arrays.toString(strArray);
	
	String res1="";
	
	for(String num : strArray)
	{
		res1+=num;
	}
	
	try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","system");
		
		PreparedStatement ps = con.prepareStatement("insert into accountdata values(?,?,?,?,?,?,?,?,?,?)");
		ps.setString(1,user);
		ps.setString(2,res1);
		ps.setString(3,adhar);
		ps.setString(4,mobile);
		ps.setString(5,email);
		ps.setString(6,father);
		ps.setString(7,acctype);
		ps.setString(8,balance);
		ps.setString(9,gender);
		ps.setString(10,update);
		ps.executeUpdate();
		
		 ps = con.prepareStatement("select * from accountdata where ACCOUNTNO = ?");
		  ps.setString(1,res1);
		  
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			out.println("<br> Name = "+rs.getString(1));
			out.println("<br> Account No. = "+rs.getString(2));
			out.println("<br> Aadhar No. = "+rs.getString(3));
			out.println("<br> Mobile No. = "+rs.getString(4));
			out.println("<br> Email Id = "+rs.getString(5));
			out.println("<br> Father Name = "+rs.getString(6));
			out.println("<br> Account Type = "+rs.getString(7));
			out.println("<br> Balance = "+rs.getString(8));
			out.println("<br> Gender = "+rs.getString(9));
		}
			RequestDispatcher disp = req.getRequestDispatcher("AfterLogin.html");
			disp.include(req,res);
			out.println("<br> Registered Succesfully!!! ");
		
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
  }

 
}