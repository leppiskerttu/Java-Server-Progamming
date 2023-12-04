package vamk.niko;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddNumbersServlet")
public class AddNumbersServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Retrieve the values of the parameters (numbers) from the form
        String num1Str = request.getParameter("num1");
        String num2Str = request.getParameter("num2");

        // Convert the parameters to integers
        int num1 = Integer.parseInt(num1Str);
        int num2 = Integer.parseInt(num2Str);

        // Perform addition
        int sum = num1 + num2;

        // Prepare the response content
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("Result of addition: " + sum);
        out.println("</body></html>");
    }
}
