package vamk.niko;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/viewStudentsServlet")
public class ViewStudentsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection connection;
    private PreparedStatement viewStatement;

    @Override
    public void init() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2102947_Java", "e2102947", "rYcBZh3fqSm");
            viewStatement = connection.prepareStatement("SELECT number, firstname, lastname FROM student");
            System.out.println("Connection established successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Failed to establish a connection to the database.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            ResultSet resultSet = viewStatement.executeQuery();

            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.print("<html><head><title>Student List</title></head><body><h1>Student List</h1><ul>");

            while (resultSet.next()) {
                String number = resultSet.getString("number");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");

                out.print("<li>" + number + " - " + firstName + " " + lastName + "</li>");
            }

            out.print("</ul></body></html>");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            if (viewStatement != null) {
                viewStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
