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

@WebServlet("/CardSearchServlet")
public class CardSearchServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public CardSearchServlet() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Card Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM cardTable";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM cardTable WHERE color LIKE ? OR type LIKE ? OR cmc LIKE ? OR name LIKE ?";
            String likeKeyword = "%" + keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, likeKeyword);
            preparedStatement.setString(2, likeKeyword);
            preparedStatement.setString(3, likeKeyword);
            preparedStatement.setString(4, likeKeyword);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            String color = rs.getString("color").trim();
            String type = rs.getString("type").trim();
            String cmc = rs.getString("cmc").trim();
            String name = rs.getString("name").trim();

            if (keyword.isEmpty() || color.contains(keyword) || type.contains(keyword) || cmc.contains(keyword) || name.contains(keyword)) {
               out.println("Color: " + color + ", ");
               out.println("Type: " + type + ", ");
               out.println("CMC: " + cmc + ", ");
               out.println("Name: " + name + "<br>");
            }
         }
         out.println("<a href=/tech-ex/simpleFormSearch.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
