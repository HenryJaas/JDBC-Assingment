package student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/listUsersServlet")
public class ReadUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	PreparedStatement statement;
	@Override
	public void init() {
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi:3306/e2001017_studentdb", "e2001017", "ThehTX8UrtD");
			statement = connection.prepareStatement("SELECT * FROM student");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		try {
			ResultSet rs = statement.executeQuery();
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.print("<b> List of all students: <br>");
			while(rs.next()){
				out.print("Number: " + rs.getInt("number") + ", First Name: " + rs.getString("firstname") + ", Last Name: " + rs.getString("lastname") + "<br>");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
