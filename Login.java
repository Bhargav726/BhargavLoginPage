import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID=1L;
	protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException {
		try {
			PrintWriter out= response.getWriter();
			response.setContentType("text/html");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","Bhargav$26");
			PreparedStatement ps=con.prepareStatement("select username from loginpage where username=? and password=?");
			String s1=request.getParameter("Username");
			String s2=request.getParameter("Password");
			ps.setString(1, s1);
			ps.setString(2, s2);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				RequestDispatcher rd=request.getRequestDispatcher("welcome.html");
				rd.forward(request, response);
			}
			else {
				out.print("<font color=red size=18>Login failed please register<br>" );
				out.print("<a href=Register.html>Register</a>");
			}
			ps.close();
			rs.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		

}
