import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Donate extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession sesh = request.getSession(false);

		if(sesh!=null){
		String tabname=request.getParameter("tabletname");
    	String dt=request.getParameter("date").toString();
		String email=(String)sesh.getAttribute("email");
		String hno=request.getParameter("hno");
		String sname=request.getParameter("sname");
		String pincode=request.getParameter("pin");
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","vcepro","system");
			PreparedStatement ps=con.prepareStatement("alter session set nls_date_format='DD-MM-YYYY'");
			ps.executeQuery();
			java.sql.Date date = java.sql.Date.valueOf(dt);
			ps=con.prepareStatement("insert into TABLETS values(?,?,?,?,?,?)");
			ps.setString(1,tabname);
			ps.setDate(2,date);
			ps.setString(3,sname);
			ps.setString(4,hno);
			ps.setString(5,pincode);
			ps.setString(6,email);
			ps.executeQuery();
			out.print("<center><h4><p style=\"color:green\">Donate Successful</p></h1></center>");
			request.getRequestDispatcher("donatenow.html").include(request,response);
		}
		catch(Exception e){System.out.println(e);out.println(e);}
		out.close();
		}
		else{
				out.print("Please login first");
            request.getRequestDispatcher("donorlogin.html").include(request, response);
		}

	}

}
