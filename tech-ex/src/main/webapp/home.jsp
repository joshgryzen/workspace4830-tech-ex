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
            float: left;
            width: calc(100% - 120px); /* Subtracting the width of the sidebar (nav) */
            padding: 5px;       
}

footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
    padding:5px;       
}
</style>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/jquery.dataTables.css">
<script>
    $(document).ready(function() {
        // Initialize DataTable
        var table = $('#cardTable').DataTable();

        // Make AJAX call to fetch data from servlet
        $.ajax({
            url: 'HomePageServlet', // Specify the URL of your servlet
            method: 'POST', // Use POST method to send data
            success: function(response) {
                // Parse the JSON response from servlet
                console.log(response);
                // var cards = JSON.parse(response);
                // console.log(cards);

                // Iterate over the retrieved cards and add them to the table
                response.forEach(function(card) {
                    table.row.add([
                        card.color,
                        card.type,
                        card.cmc,
                        card.name,
                        card.quantity
                    ]).draw();
                });
            },
            error: function(xhr, status, error) {
                // Handle errors
                console.error(error);
            }
        });
    });
</script>
</head>
<body>
    <header>
        <h1>All Cards</h1>
    </header>
    <nav>
        <a href="/tech-ex/home.jsp">Home</a> <br>
		<a href="/tech-ex/insert.html">Insert Cards</a> <br>
    </nav>
    <section>
    <table id="cardTable" class="display">
        <thead>
            <tr>
                <th>Color</th>
                <th>Type</th>
                <th>CMC</th>
                <th>Name</th>
                <th>Quantity</th>
            </tr>
        </thead>
        <tbody>
            <!-- Table body will be populated dynamically -->
        </tbody>
    </table>
    </section>
<footer>
Josh Gryzen Tech Exercise
</footer>
</body>
</html>
