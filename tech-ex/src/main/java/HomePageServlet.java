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
        response.setContentType("application/json");
        // Set character encoding before writing response
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            DBConnection.getDBConnection(getServletContext());
            connection = DBConnection.connection;

            String selectSQL = "SELECT * FROM cardTable";
            preparedStatement = connection.prepareStatement(selectSQL);
            resultSet = preparedStatement.executeQuery();

            StringBuilder jsonData = new StringBuilder("[");
            while (resultSet.next()) {
                String color = resultSet.getString("color");
                String type = resultSet.getString("type");
                int cmc = resultSet.getInt("cmc");
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                
                jsonData.append("{\"color\":\"").append(color).append("\",")
                        .append("\"type\":\"").append(type).append("\",")
                        .append("\"cmc\":").append(cmc).append(",")
                        .append("\"name\":\"").append(name).append("\",")
                        .append("\"quantity\":").append(quantity).append("},");
            }
            if (jsonData.charAt(jsonData.length() - 1) == ',') {
                jsonData.setCharAt(jsonData.length() - 1, ']'); // Replace last comma with ']'
            } else {
                jsonData.append("]");
            }
            System.out.println(jsonData.toString());
            out.println(jsonData);
            
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
