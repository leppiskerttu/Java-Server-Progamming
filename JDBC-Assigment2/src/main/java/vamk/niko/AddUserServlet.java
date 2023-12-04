package vamk.niko;

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

@WebServlet(urlPatterns = "/addUserServlet")
public class AddUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection connection;
    private PreparedStatement statement;

    @Override
    public void init() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2102947_Java", "e2102947", "rYcBZh3fqSm");
            statement = connection.prepareStatement("INSERT INTO student (number, lastname, firstname) VALUES (?, ?, ?)");
            System.out.println("Connection established successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Failed to establish a connection to the database.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String number = req.getParameter("number");  // Assuming "number" is the first column in the table
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        try {
            statement.setString(1, number);
            statement.setString(2, firstName);
            statement.setString(3, lastName);

            int result = statement.executeUpdate();
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.print("<b>" + result + " users created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

