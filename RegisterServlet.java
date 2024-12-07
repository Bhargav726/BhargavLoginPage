package com;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/com.RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String INSERT_QUERY = "INSERT INTO loginpage(email, password) VALUES(?, ?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set Content type
        res.setContentType("text/html");

        // Read the form values
        String email = req.getParameter("User");
        String password = req.getParameter("Pswd");

        // Basic validation
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            pw.println("Email and Password cannot be empty.");
            return;
        }

        // JDBC operations
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create connection
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "Bhargav$26");
                 PreparedStatement ps = con.prepareStatement(INSERT_QUERY)) {

                ps.setString(1, email);
                ps.setString(2, password);

                // Execute query
                int count = ps.executeUpdate();
                if (count > 0) {
                    pw.println("Record stored into the database successfully.");
                } else {
                    pw.println("Failed to store the record.");
                }
            }
        } catch (ClassNotFoundException e) {
            pw.println("Database driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            pw.println("Error accessing the database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            pw.close();
        }
    }
}
