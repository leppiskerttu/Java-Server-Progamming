<%@ page import="java.sql.*" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.PrintWriter" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Product Result</title>
</head>
<body>
    <%
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            String name = request.getParameter("name");
            int id = Integer.parseInt(request.getParameter("id"));
            float price = Float.parseFloat(request.getParameter("price"));

            // JDBC URL, username, and password of MariaDB server
            String url = "jdbc:mariadb://mariadb.vamk.fi/e2102947_productdb";
            String user = "e2102947";
            String password = "rYcBZh3fqSm";

            // Create a connection to the database
            conn = DriverManager.getConnection(url, user, password);

            // SQL query to insert a new product
            String sql = "INSERT INTO products (Name, ID, Price) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.setFloat(3, price);

            // Execute the SQL query
            int rows = pstmt.executeUpdate();

            out.println("<h2>Product added successfully!</h2>");
        } catch (Exception e) {
            out.println("<h2>Error adding product: " + e.getMessage() + "</h2>");
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    %>
</body>
</html>
