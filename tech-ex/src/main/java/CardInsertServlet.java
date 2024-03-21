/**
 * @file CardInsertServlet.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CardInsertServlet")
public class CardInsertServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public CardInsertServlet() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String color = request.getParameter("color");
      String type = request.getParameter("type");
      String cmc = request.getParameter("cmc");
      String name = request.getParameter("name");

      Connection connection = null;
      
      String selectSQL = "SELECT quantity FROM cardTable WHERE name = ?";
      String updateSQL = "UPDATE cardTable SET quantity = quantity + 1 WHERE name = ?";
      String insertSql = "INSERT INTO cardTable (color, type, cmc, name, quantity) VALUES (?, ?, ?, ?, 1)";

      try {
          DBConnection.getDBConnection(getServletContext());
          connection = DBConnection.connection;
          
          // Check if the card already exists in the database
          PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
          selectStmt.setString(1, name);
          ResultSet resultSet = selectStmt.executeQuery();

          if (resultSet.next()) {
              // Card already exists, so increment the quantity
              PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
              updateStmt.setString(1, name);
              updateStmt.executeUpdate();
          } else {
              // Card doesn't exist, so insert a new row
              PreparedStatement insertStmt = connection.prepareStatement(insertSql);
              insertStmt.setString(1, color);
              insertStmt.setString(2, type);
              insertStmt.setString(3, cmc);
              insertStmt.setString(4, name);
              insertStmt.executeUpdate();
          }
          
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
            "<ul>\n" + //

            "  <li><b>Color</b>: " + color + "\n" + //
            "  <li><b>Type</b>: " + type + "\n" + //
            "  <li><b>CMC</b>: " + cmc + "\n" + //
            "  <li><b>Name</b>: " + name + "\n" + //

            "</ul>\n");

      out.println("<a href=/tech-ex/home.jsp>Return Home</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
