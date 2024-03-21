import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteCardServlet")
public class DeleteCardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // Retrieve card name from request parameter
        String cardName = request.getParameter("cardName");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish database connection
            DBConnection.getDBConnection(getServletContext());
            connection = DBConnection.connection;

            // SQL query to delete the card from the database
            String deleteSQL = "DELETE FROM cardTable WHERE name = ?";

            // Create prepared statement
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, cardName);

            // Execute the SQL statement to delete the card
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                out.println("Card " + cardName + " deleted successfully");
            } else {
                out.println("Failed to delete card " + cardName);
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            out.println("Failed to delete card due to SQL error: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            out.println("Failed to delete card due to unexpected error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
