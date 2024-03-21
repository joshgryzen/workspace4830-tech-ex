<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Card" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<html>
<head>
<style>
header {
    background-color:black;
    color:white;
    text-align:center;
    padding:5px;	 
}
nav {
    line-height:30px;
    background-color:#eeeeee;
    height:300px;
    width:100px;
    float:left;
    padding:5px;	      
}
section {
    width:350px;
    float:left;
    padding:10px;	 	 
}
footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
    padding:5px;	 	 
}
</style>
<body>
    <header>
        <h1> All Cards </h1>
    </header>

    <nav>
        <a href="/tech-ex/home.jsp">Home</a> <br>
        <a href="/tech-ex/simpleFormSearch.html">Card Search</a> <br>
        <a href="/tech-ex/simpleFormInsert.html">Insert Cards</a> <br>
    </nav>

    <section>
        <!-- Call the method to retrieve all cards -->
        <%
		    List<Card> cards = new ArrayList<>();
		    try {
		        Card card = new Card();
		        cards = card.getAllCardsFromDatabase();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		%>

        <table id="cardTable">
            <tr>
                <th>Color</th>
                <th>Type</th>
                <th>CMC</th>
                <th>Name</th>
                <th>Quantity</th>
                <th>Actions</th>
            </tr>
            <!-- Iterate over the list of cards using JSTL -->
            <c:forEach var="card" items="${cards}">
                <tr>
                    <td>${card.color}</td>
                    <td>${card.type}</td>
                    <td>${card.cmc}</td>
                    <td>${card.name}</td>
                    <td>${card.quantity}</td>
                    <td>
                        <!-- You can add buttons for actions here -->
                        <!-- For example: -->
                        <button onclick="decrementQuantity(${card.id})">-</button>
                        <button onclick="incrementQuantity(${card.id})">+</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </section>

    <!-- Your JavaScript code for quantity manipulation -->

    <footer>
        Josh Gryzen
    </footer>
</body>
</html>
