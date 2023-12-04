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

@WebServlet("/updateStudentServlet")
public class UpdateStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection connection;
    private PreparedStatement updateStatement;

    @Override
    public void init() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2102947_Java", "e2102947", "rYcBZh3fqSm");
            updateStatement = connection.prepareStatement("UPDATE student SET firstname=?, lastname=? WHERE number=?");
            System.out.println("Connection established successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Failed to establish a connection to the database.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String numberToUpdate = req.getParameter("number");
        String newFirstName = req.getParameter("newFirstName");
        String newLastName = req.getParameter("newLastName");

        try {
            updateStatement.setString(1, newFirstName);
            updateStatement.setString(2, newLastName);
            updateStatement.setString(3, numberToUpdate);

            int result = updateStatement.executeUpdate();

            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.print("<b>" + result + " user(s) updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            if (updateStatement != null) {
                updateStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
