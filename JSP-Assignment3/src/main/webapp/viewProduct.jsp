<%@ page import="java.sql.*" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.PrintWriter" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Products</title>
</head>
<body>
    <%
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Register the JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            // JDBC URL, username, and password of MariaDB server
            String url = "jdbc:mariadb://mariadb.vamk.fi/e2102947_productdb";
            String user = "e2102947";
            String password = "rYcBZh3fqSm";

            // Create a connection to the database
            conn = DriverManager.getConnection(url, user, password);

            // SQL query to retrieve all products
            String sql = "SELECT * FROM products";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            out.println("<h1>View Products</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Name</th><th>ID</th><th>Price</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("Name") + "</td>");
                out.println("<td>" + rs.getInt("ID") + "</td>");
                out.println("<td>" + rs.getFloat("Price") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
        } catch (Exception e) {
            out.println("<h2>Error viewing products: " + e.getMessage() + "</h2>");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                out.println("<h2>Error closing resources: " + ex.getMessage() + "</h2>");
            }
        }
    %>
</body>
</html>
