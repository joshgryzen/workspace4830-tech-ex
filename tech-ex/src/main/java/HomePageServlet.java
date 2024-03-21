import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HomePageServlet")
public class HomePageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>All Cards</title></head><body>");

        out.println("<h2>All Cards</h2>");

        out.println("<table border='1'>");
        out.println("<tr><th>Color</th><th>Type</th><th>CMC</th><th>Name</th><th>Quantity</th></tr>");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            DBConnection.getDBConnection(getServletContext());
            connection = DBConnection.connection;

            String selectSQL = "SELECT * FROM cardTable";
            preparedStatement = connection.prepareStatement(selectSQL);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String color = resultSet.getString("color");
                String type = resultSet.getString("type");
                int cmc = resultSet.getInt("cmc");
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                
                System.out.println(name);

                out.println("<tr>");
                out.println("<td>" + color + "</td>");
                out.println("<td>" + type + "</td>");
                out.println("<td>" + cmc + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + quantity + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
