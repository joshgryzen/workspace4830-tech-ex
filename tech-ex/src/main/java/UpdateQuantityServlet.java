import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateQuantityServlet")
public class UpdateQuantityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // Retrieve parameters from the AJAX request
        String cardName = request.getParameter("cardName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Connection connection = null;
        String updateSQL = "UPDATE cardTable SET quantity = ? WHERE name = ?";

        try {
            DBConnection.getDBConnection(getServletContext());
            connection = DBConnection.connection;
            PreparedStatement preparedStmt = connection.prepareStatement(updateSQL);
            // Set parameters
            preparedStmt.setInt(1, quantity);
            preparedStmt.setString(2, cardName);
            // Execute update
            int rowsUpdated = preparedStmt.executeUpdate();
            if (rowsUpdated > 0) {
                out.print("Quantity updated successfully.");
            } else {
                out.print("Failed to update quantity.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
