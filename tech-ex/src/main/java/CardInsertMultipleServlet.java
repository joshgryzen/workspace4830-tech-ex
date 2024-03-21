import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CardInsertMultipleServlet")
public class CardInsertMultipleServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public CardInsertMultipleServlet() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String cardList = request.getParameter("cardList");

      Connection connection = null;
      String insertSql = "INSERT INTO cardTable (color, type, cmc, name) VALUES (?, ?, ?, ?)";

      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);

         // Split the card list by new lines
         String[] cards = cardList.split("\n");
         for (String card : cards) {
            // Split each card by commas to extract attributes
            String[] attributes = card.split(",");
            if (attributes.length == 4) { // Ensure all attributes are present
               preparedStmt.setString(1, attributes[0].trim());
               preparedStmt.setString(2, attributes[1].trim());
               preparedStmt.setString(3, attributes[2].trim());
               preparedStmt.setString(4, attributes[3].trim());
               preparedStmt.addBatch(); // Add batch for batch insertion
            }
         }

         // Execute batch insert
         preparedStmt.executeBatch();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Card Data to DB";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<p>Successfully inserted cards</p>\n");

      out.println("<a href=/tech-ex/simpleFormSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
