
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
                data: { action: 'getAllCards' }, // Provide any necessary data
                success: function(response) {
                    // Parse the JSON response from servlet
                    var cards = JSON.parse(response);

                    // Iterate over the retrieved cards and add them to the table
                    $.each(cards, function(index, card) {
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
        <a href="/tech-ex/simpleFormSearch.html">Card Search</a> <br>
        <a href="/tech-ex/simpleFormInsert.html">Insert Cards</a> <br>
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
            <!-- Your servlet will populate the table rows dynamically -->
        </tbody>
    </table>
    </section>
    
</body>
</html>
